package com.panopset.compat

import com.panopset.compat.Stringop.getEol
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.logging.Level
import java.util.logging.Logger

object Logop {
    val logger: Logger = Logger.getGlobal()
    private val clearLogEntry = LogEntry(LogopAlert.GREEN, Level.INFO, "")
    private const val STANDARD_WIERD_ERROR_MESSAGE =
        "Unexpected error, if your pull request is accepted, we'll send you 1000 currently worthless Panopset shares."

    /**
     * Keeping this around, because of the System/38 &amp; AS/400 DSPMSG CLP command. Identical to
     * info(msg).
     *
     * @param msg Message to log at Level.INFO.
     */
    fun dspmsg(panop: Panop, msg: String) {
        info(panop, msg)
    }

    fun info(panop: Panop, msg: String) {
        report(panop,
            LogEntry(
                LogopAlert.GREEN, Level.INFO,
                audit(msg)
            )
        )
    }

    fun warn(panop: Panop, msg: String) {
        report(panop,
            LogEntry(
                LogopAlert.YELLOW, Level.WARNING,
                audit(msg)
            )
        )
    }

    fun errorMsg(panop: Panop, msg: String?) {
        report(panop,
            LogEntry(
                LogopAlert.RED, Level.SEVERE,
                audit(msg?: STANDARD_WIERD_ERROR_MESSAGE)
            )
        )
    }

    private fun audit(msg: String): String {
        return msg
    }

    fun warn(panop: Panop, ex: Exception) {
        warn(panop, ex.message?: STANDARD_WIERD_ERROR_MESSAGE)
    }

    fun errorEx(panop: Panop, ex: Exception) {
        handleException(panop, ex)
    }

    fun errorMsg(panop: Panop, msg: String, ex: Throwable) {
        errorMsg(panop, msg)
        handleException(panop, ex)
    }

    fun green(panop: Panop, msg: String) {
        dspmsg(panop, msg)
    }

    fun errorMsg(panop: Panop, message: String, file: File) {
        errorMsg(panop, "$message: ${Fileop.getCanonicalPath(file)}")
    }

    fun getStackTrace(throwable: Throwable): String {
        StringWriter().use { sw ->
            PrintWriter(sw).use { pw ->
                throwable.printStackTrace(pw)
                pw.flush()
                return sw.toString()
            }
        }
    }

    fun errorMsg(panop: Panop, file: File, ex: Exception) {
        info(panop, Fileop.getCanonicalPath(file))
        errorEx(panop, ex)
    }

    fun handleException(panop: Panop, message: String, ex: Throwable) {
        logger.log(Level.SEVERE, message, ex)
        ex.printStackTrace()
        val logEntry = LogEntry(
            LogopAlert.RED, Level.SEVERE,
            message)
        logalog(panop, logEntry)
    }

    fun handleException(panop: Panop, ex: Throwable) {
        logger.log(Level.SEVERE, ex.message, ex)
        ex.printStackTrace()
        val logEntry = LogEntry(
            LogopAlert.RED, Level.SEVERE,
            ex.message?: STANDARD_WIERD_ERROR_MESSAGE
        )
        logalog(panop, logEntry)
    }

    fun report(panop: Panop, logRecord: LogEntry) {
        logger.log(logRecord.level, logRecord.message)
        logalog(panop, logRecord)
    }

    fun getEntryStackAsText(): String {
        return printHistory()
    }

    fun clear(panop: Panop) {
        stack.clear()
        logalog(panop, clearLogEntry)
    }

    val stack: Deque<LogEntry> = ConcurrentLinkedDeque()
    private fun printHistory(): String {
        val sw = StringWriter()
        for (lr in stack) {
            sw.append(timestampFormat.format(lr.timestamp))
            sw.append(lr.message)
            sw.append("\n")
        }
        return sw.toString()
    }

    private fun logalog(panop: Panop, logEntry: LogEntry) {
        if (stack.size > 10) {
            stack.removeLast()
        }
        stack.push(logEntry)
    }

    fun getStackTraceAndCauses(throwable: Throwable): String {
        val sw = StringWriter()
        sw.append("See log")
        sw.append(": ")
        sw.append(throwable.message)
        sw.append(getEol())
        sw.append("*************************")
        sw.append(getEol())
        sw.append(getStackTrace(throwable))
        sw.append(getEol())
        var cause = throwable.cause
        while (cause != null) {
            sw.append("*************************")
            sw.append(getEol())
            sw.append(getStackTrace(cause))
            sw.append(getEol())
            cause = cause.cause
        }
        return sw.toString()
    }

    fun errorDevLogOnly(ex: Throwable) {
        logger.log(Level.SEVERE, ex.message, ex)
    }
}

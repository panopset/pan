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
    val clearLogEntry = LogEntry(LogopAlert.GREEN, Level.INFO, "")
    const val standardWierdErrorMessage =
        "Unexpected error, if your pull request is accepted, we'll send you 1000 currently worthless Panopset shares."

    /**
     * Keeping this around, because of the System/38 &amp; AS/400 DSPMSG CLP command. Identical to
     * info(msg).
     *
     * @param msg Message to log at Level.INFO.
     */
    fun dspmsg(msg: String?) {
        info(msg)
    }

    fun info(msg: String?) {
        report(
            LogEntry(
                LogopAlert.GREEN, Level.INFO,
                audit(msg)!!
            )
        )
    }

    fun warn(msg: String?) {
        report(
            LogEntry(
                LogopAlert.YELLOW, Level.WARNING,
                audit(msg)!!
            )
        )
    }

    fun debug(msg: String?) {
        report(
            LogEntry(
                LogopAlert.ORANGE, Level.FINE,
                audit(msg)!!
            )
        )
    }

    fun errorMsg(msg: String?) {
        report(
            LogEntry(
                LogopAlert.RED, Level.SEVERE,
                audit(msg)!!
            )
        )
    }

    private fun audit(msg: String?): String? {
        return msg
    }

    fun warn(ex: Exception) {
        warn(ex.message)
    }

    fun errorEx(ex: Exception?) {
        handleException(ex!!)
    }

    fun errorMsg(msg: String?, ex: Throwable?) {
        errorMsg(msg)
        handleException(ex!!)
    }

    fun green(msg: String?) {
        dspmsg(msg)
    }

    fun handle(e: Exception?) {
        handleException(e!!)
    }

    fun errorMsg(message: String?, file: File?) {
        if (file == null) {
            errorMsg("Null file.")
            return
        }
        errorMsg("$message: ${Fileop.getCanonicalPath(file)}")
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

    fun errorMsg(file: File, ex: Exception) {
        info(Fileop.getCanonicalPath(file))
        errorEx(ex)
    }

    fun handleException(ex: Throwable) {
        logger.log(Level.SEVERE, ex.message, ex)
        ex.printStackTrace()
        val logEntry = LogEntry(
            LogopAlert.RED, Level.SEVERE,
            ex.message?: standardWierdErrorMessage
        )
        logalog(logEntry)
    }
    fun report(logRecord: LogEntry) {
        logger.log(logRecord.level, logRecord.message)
        logalog(logRecord)
    }

    //  public static String logAndReturnError(String msg) {
    //    Logop.errorMsg(msg);
    //    return msg;
    //  }
    //  public static String logAndReturnExceptionError(Exception ex) {
    //    String rtn = ex.getMessage();
    //    if (!Stringop.isPopulated(ex.getMessage())) {
    //      rtn = PAN_STANDARD_LOGIC_ERROR_MSG;
    //    }
    //    errorMsg(rtn);
    //    return rtn;
    //  }
    //  public static String logAndReturnError(String msg) {

    fun getEntryStackAsText(): String {
        return printHistory()
    }

    fun clear() {
        stack.clear()
    }

    val stack: Deque<LogEntry> = ConcurrentLinkedDeque()
    fun printHistory(): String {
        val sw = StringWriter()
        for (lr in stack) {
            sw.append(timestampFormat.format(lr.timestamp))
            sw.append(lr.message)
            sw.append("\n")
        }
        return sw.toString()
    }

    fun logalog(logEntry: LogEntry) {
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
}

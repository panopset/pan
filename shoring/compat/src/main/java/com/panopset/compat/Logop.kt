package com.panopset.compat

import com.panopset.compat.Stringop.getEol
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.logging.Level
import java.util.logging.Logger

object Logop {
    val logger: Logger = Logger.getGlobal()
    private var logListener: LogListener
    var isDebugging = false
    val clearLogEntry = LogEntry(LogopAlert.GREEN, Level.INFO, "")
    const val PAN_STANDARD_LOGIC_ERROR_MSG =
        "Unexpected error, if your pull request is accepted, we'll send you 1000 currently worthless Panopset shares."

    init {
        logListener = object: LogListener {
            override fun log(logEntry: LogEntry) {

            }
        }
    }

    /**
     * Keeping this around, because of the System/38 &amp; AS/400 DSPMSG CLP command. Identical to
     * info(msg).
     *
     * @param msg Message to log at Level.INFO.
     */
    @JvmStatic
    fun dspmsg(msg: String?) {
        info(msg)
    }

    @JvmStatic
    fun info(msg: String?) {
        report(
            LogEntry(
                LogopAlert.GREEN, Level.INFO,
                audit(msg)!!
            )
        )
    }

    @JvmStatic
    fun warn(msg: String?) {
        report(
            LogEntry(
                LogopAlert.YELLOW, Level.WARNING,
                audit(msg)!!
            )
        )
    }

    @JvmStatic
    fun debug(msg: String?) {
        report(
            LogEntry(
                LogopAlert.ORANGE, Level.FINE,
                audit(msg)!!
            )
        )
    }

    @JvmStatic
    fun errorMsg(msg: String?) {
        report(
            LogEntry(
                LogopAlert.RED, Level.SEVERE,
                audit(msg)!!
            )
        )
    }

    @JvmStatic
    private fun audit(msg: String?): String? {
        return msg
    }

    @JvmStatic
    fun warn(ex: Exception) {
        warn(ex.message)
    }

    @JvmStatic
    fun errorEx(ex: Exception?) {
        handleException(ex!!)
    }

    @JvmStatic
    fun errorMsg(msg: String?, ex: Throwable?) {
        errorMsg(msg)
        handleException(ex!!)
    }

    @JvmStatic
    fun green(msg: String?) {
        dspmsg(msg)
    }

    @JvmStatic
    fun handle(e: Exception?) {
        handleException(e!!)
    }

    @JvmStatic
    fun errorMsg(message: String?, file: File?) {
        if (file == null) {
            errorMsg("Null file.")
            return
        }
        errorMsg("$message: ${Fileop.getCanonicalPath(file)}")
    }
    @JvmStatic
    @Throws(IOException::class)
    fun getStackTrace(throwable: Throwable): String {
        StringWriter().use { sw ->
            PrintWriter(sw).use { pw ->
                throwable.printStackTrace(pw)
                pw.flush()
                return sw.toString()
            }
        }
    }
    @JvmStatic
    fun errorMsg(file: File, ex: Exception) {
        info(Fileop.getCanonicalPath(file))
        errorEx(ex)
    }
    @JvmStatic
    fun handleException(ex: Throwable) {
        logger.log(Level.SEVERE, ex.message, ex)
        ex.printStackTrace()
        val logEntry = LogEntry(
            LogopAlert.RED, Level.SEVERE,
            ex.message!!
        )
        logListener.log(logEntry)
        logalog(logEntry)
    }
    @JvmStatic
    fun report(logRecord: LogEntry) {
        logger.log(logRecord.level, logRecord.message)
        logListener.log(logRecord)
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

    @JvmStatic
    fun getEntryStackAsText(): String {
        return printHistory()
    }

    @JvmStatic
    fun clear() {
        stack.clear()
        turnOffDebugging()
        clearListeners()
    }

    private fun clearListeners() {
        logListener.log(clearLogEntry)
    }

    @JvmStatic
    fun turnOnDebugging() {
        isDebugging = true
    }

    @JvmStatic
    fun turnOffDebugging() {
        isDebugging = false
    }

    @JvmStatic
    fun standardWierdErrorMessage() {
        errorMsg(PAN_STANDARD_LOGIC_ERROR_MSG)
    }

    @JvmStatic
    val stack: Deque<LogEntry> = ConcurrentLinkedDeque()
    @JvmStatic
    fun printHistory(): String {
        val sw = StringWriter()
        for (lr in stack) {
            sw.append(timestampFormat.format(lr.timestamp))
            sw.append(lr.message)
            sw.append("\n")
        }
        return sw.toString()
    }

    @JvmStatic
    fun logalog(logEntry: LogEntry) {
        if (stack.size > 999) {
            for (i in 0..99) {
                stack.removeFirst()
            }
        }
        stack.add(logEntry)
    }

    @JvmStatic
    fun setLogListener(value: LogListener) {
        logListener = value
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

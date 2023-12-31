package com.panopset.compat.test

import com.panopset.compat.*
import com.panopset.compat.Logop.clearLogEntry
import com.panopset.compat.Logop.getStackTraceAndCauses
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.logging.Level

class LogopTest {
    private var lr: LogEntry = clearLogEntry
    private val listener = object: LogListener {
        override fun log(logEntry: LogEntry) {
            lr = logEntry
        }
    }

    @Test
    fun simpleTest() {
        Logop.clear()
        Logop.info(Stringop.FOO)
        Assertions.assertEquals(1, Logop.stack.size)
        Logop.info(Stringop.BAR)
    }

    @Test
    fun test() {
        Logop.clear()
        Logop.logger.info(Stringop.FOO)
        Fileop.touch(tempFile)
        Logop.errorMsg(Stringop.FOO, tempFile)
        Assertions.assertTrue(Logop.stack.size > 0)
        FileopTest.cleanup()
        Logop.clear()
        Logop.setLogListener(listener)
        Logop.dspmsg(Stringop.FOO)
        Assertions.assertEquals(Stringop.FOO, lr.message)
        Assertions.assertEquals(Level.INFO, lr.level)
        Logop.handle(Exception(Stringop.FOO))
        Assertions.assertEquals(Level.SEVERE, lr.level)
        Logop.debug(Stringop.FOO)
        Assertions.assertEquals(Level.FINE, lr.level)
        Logop.warn(Stringop.FOO)
        Assertions.assertEquals(Level.WARNING, lr.level)
        Logop.turnOnDebugging()
        Logop.warn(Stringop.FOO)
        Assertions.assertEquals(Stringop.FOO, lr.message)
        Assertions.assertEquals(Level.WARNING, lr.level)
        val ex = Exception(Stringop.BAR)
        Logop.errorMsg(Stringop.FOO, ex)
        Assertions.assertEquals(7, Logop.stack.size)
        Assertions.assertTrue(12 < Logop.getEntryStackAsText().length)
        Logop.clear()
        var stackTrace = getStackTraceAndCauses(ex)
        Assertions.assertTrue(12 < stackTrace.length)
        Logop.clear()
        val ex2 = Exception(ex)
        stackTrace = getStackTraceAndCauses(ex2)
        Assertions.assertTrue(12 < stackTrace.length)
        Assertions.assertFalse(Logop.isDebugging)
        Logop.turnOnDebugging()
        Assertions.assertTrue(Logop.isDebugging)
        Logop.clear()
        Assertions.assertFalse(Logop.isDebugging)
        for (i in 0..1000) {
            Logop.dspmsg(String.format("%s:%d", Stringop.FOO, i))
        }
        Assertions.assertEquals(901, Logop.stack.size)
        Logop.clear()
        Logop.debug(Stringop.FOO)
        Assertions.assertEquals(1, Logop.stack.size)
    }
}

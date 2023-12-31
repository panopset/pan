package com.panopset.compat.test

import com.panopset.compat.*
import com.panopset.compat.Logop.getStackTraceAndCauses
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.logging.Level

class LogopTest {

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
        Logop.dspmsg(Stringop.FOO)
        Assertions.assertEquals(Stringop.FOO, Logop.stack.peek().message)
        Assertions.assertEquals(Level.INFO, Logop.stack.peek().level)
        Logop.handle(Exception(Stringop.FOO))
        Assertions.assertEquals(Level.SEVERE, Logop.stack.peek().level)
        Logop.debug(Stringop.FOO)
        Assertions.assertEquals(Level.FINE, Logop.stack.peek().level)
        Logop.warn(Stringop.FOO)
        Assertions.assertEquals(Level.WARNING, Logop.stack.peek().level)
        Logop.warn(Stringop.FOO)
        Assertions.assertEquals(Stringop.FOO, Logop.stack.peek().message)
        Assertions.assertEquals(Level.WARNING, Logop.stack.peek().level)
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
        Logop.clear()
        for (i in 0..100) {
            Logop.dspmsg(String.format("%s:%d", Stringop.FOO, i))
        }
        Assertions.assertEquals(11, Logop.stack.size)
        Logop.clear()
        Logop.debug(Stringop.FOO)
        Assertions.assertEquals(1, Logop.stack.size)
    }
}

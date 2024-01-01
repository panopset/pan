package com.panopset.compat.test

import com.panopset.compat.*
import com.panopset.compat.Logop.getStackTraceAndCauses
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.logging.Level

class LogopTest {

    @Test
    fun simpleTest() {
        Logop.clear(PanopTest)
        Logop.info(PanopTest, FOO)
        Assertions.assertEquals(1, Logop.stack.size)
        Logop.info(PanopTest, BAR)
    }

    @Test
    fun test() {
        Logop.clear(PanopTest)
        Logop.logger.info(FOO)
        Fileop.touch(PanopTest, tempFile)
        Logop.errorMsg(PanopTest, FOO, tempFile)
        Assertions.assertTrue(Logop.stack.size > 0)
        FileopTest.cleanup()
        Logop.clear(PanopTest)
        Logop.dspmsg(PanopTest, FOO)
        Assertions.assertEquals(FOO, Logop.stack.peek().message)
        Assertions.assertEquals(Level.INFO, Logop.stack.peek().level)
        Logop.handleException(PanopTest, Exception(FOO))
        Assertions.assertEquals(Level.SEVERE, Logop.stack.peek().level)
        Assertions.assertEquals(Level.FINE, Logop.stack.peek().level)
        Logop.warn(PanopTest, FOO)
        Assertions.assertEquals(Level.WARNING, Logop.stack.peek().level)
        Logop.warn(PanopTest, FOO)
        Assertions.assertEquals(FOO, Logop.stack.peek().message)
        Assertions.assertEquals(Level.WARNING, Logop.stack.peek().level)
        val ex = Exception(BAR)
        Logop.errorMsg(PanopTest, FOO, ex)
        Assertions.assertEquals(7, Logop.stack.size)
        Assertions.assertTrue(12 < Logop.getEntryStackAsText().length)
        Logop.clear(PanopTest)
        var stackTrace = getStackTraceAndCauses(ex)
        Assertions.assertTrue(12 < stackTrace.length)
        Logop.clear(PanopTest)
        val ex2 = Exception(ex)
        stackTrace = getStackTraceAndCauses(ex2)
        Assertions.assertTrue(12 < stackTrace.length)
        Logop.clear(PanopTest)
        for (i in 0..100) {
            Logop.dspmsg(PanopTest, String.format("%s:%d", FOO, i))
        }
        Assertions.assertEquals(11, Logop.stack.size)
        Logop.clear(PanopTest)
        Logop.warn(PanopTest, FOO)
        Assertions.assertEquals(1, Logop.stack.size)
    }
}

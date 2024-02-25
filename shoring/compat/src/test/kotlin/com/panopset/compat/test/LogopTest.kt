package com.panopset.compat.test

import com.panopset.compat.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.logging.Level

class LogopTest {

    @Test
    fun simpleTest() {
        Logz.clear()
        Logz.info(Stringop.FOO)
        Assertions.assertEquals(1, stacklg.size)
        Logz.info(Stringop.BAR)
    }

    @Test
    fun test() {
        Logz.clear()
        Logz.info(Stringop.FOO)
        Fileop.touch(tempFile)
        Logz.errorMsg(Stringop.FOO, tempFile)
        Assertions.assertTrue(stacklg.size > 0)
        FileopTest.cleanup()
        Logz.clear()
        Logz.dspmsg(Stringop.FOO)
        Assertions.assertEquals(Stringop.FOO, stacklg.peek().message)
        Assertions.assertEquals(Level.INFO, stacklg.peek().level)
        Logz.errorEx(Exception(Stringop.FOO))
        Assertions.assertEquals(Level.SEVERE, stacklg.peek().level)
        Logz.debug(Stringop.FOO)
        Assertions.assertEquals(Level.FINE, stacklg.peek().level)
        Logz.warn(Stringop.FOO)
        Assertions.assertEquals(Level.WARNING, stacklg.peek().level)
        Logz.warn(Stringop.FOO)
        Assertions.assertEquals(Stringop.FOO, stacklg.peek().message)
        Assertions.assertEquals(Level.WARNING, stacklg.peek().level)
        val ex = Exception(Stringop.BAR)
        Logz.errorMsg(Stringop.FOO, ex)
        Assertions.assertEquals(7, stacklg.size)
        Assertions.assertTrue(12 < getEntryStackAsTextlg().length)
        Logz.clear()
        var stackTrace = getStackTraceAndCauseslg(ex)
        Assertions.assertTrue(12 < stackTrace.length)
        Logz.clear()
        val ex2 = Exception(ex)
        stackTrace = getStackTraceAndCauseslg(ex2)
        Assertions.assertTrue(12 < stackTrace.length)
        Logz.clear()
        for (i in 0..100) {
            Logz.dspmsg(String.format("%s:%d", Stringop.FOO, i))
        }
        Assertions.assertEquals(11, stacklg.size)
        Logz.clear()
        Logz.debug(Stringop.FOO)
        Assertions.assertEquals(1, stacklg.size)
    }
}

package com.panopset.tests.flywheel

import com.panopset.compat.Stringop
import com.panopset.compat.Stringop.setEol
import com.panopset.flywheel.FlywheelListDriver
import com.panopset.flywheel.LFR_FLATTEN
import com.panopset.flywheel.LINE_BREAKS
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.IOException

class ListTest {
    @Test
    @Throws(IOException::class)
    fun testListCommand() {
        SimpleTest().comparisonTest("listTest01.txt", "listTest.txt", "listTestExpected.html")
    }

    @Test
    @Throws(IOException::class)
    fun testSimpleList() {
        setEol("\n")
        val fwd = FlywheelListDriver.Builder(arrayOf("x", "y"), "ab\nc").build()
        val result = fwd.output
        Assertions.assertEquals("ab\nc\n\nab\nc\n\n", result)
        setEol(Stringop.DOS_RTN)
    }

    @Test
    @Throws(IOException::class)
    fun testSimpleListWithReturnsSuppressed() {
        setEol("\n")
        val fwd = FlywheelListDriver.Builder(arrayOf<String?>("x", "y"), "ab\nc")
            .withLineFeedRules(LFR_FLATTEN).build()
        val result = fwd.output
        Assertions.assertEquals("abcabc", result)
        setEol(Stringop.DOS_RTN)
    }

    @Test
    @Throws(IOException::class)
    fun testSimpleListWithListBreaksSuppressed() {
        setEol("\n")
        val fwd = FlywheelListDriver.Builder(arrayOf("x", "y"), "ab\nc")
            .withLineFeedRules(LINE_BREAKS).build()
        val result = fwd.output
        Assertions.assertEquals("ab\nc\nab\nc\n", result)
        setEol(Stringop.DOS_RTN)
    }
}

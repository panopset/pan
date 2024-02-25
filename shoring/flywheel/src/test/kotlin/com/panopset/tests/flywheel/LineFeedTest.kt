package com.panopset.tests.flywheel

import com.panopset.compat.Stringop.setEol
import com.panopset.flywheel.FlywheelBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.IOException

class LineFeedTest {
    @Test
    @Throws(IOException::class)
    fun test() {
        val input: MutableList<String> = ArrayList()
        input.add("a")
        input.add("b")
        setEol("\n")
        val fw = FlywheelBuilder().inputList(input).construct()
        val result = fw.exec()
        Assertions.assertEquals("a\nb\n", result)
    }
}

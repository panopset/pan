package com.panopset.compat.test

import com.panopset.compat.FlagSwitch
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FlagSwitchTest {
    @Test
    fun test() {
        val flag = FlagSwitch()
        Assertions.assertTrue(flag.pull())
        Assertions.assertFalse(flag.pull())
        Assertions.assertTrue(flag.pull())
        flag.reset()
        Assertions.assertTrue(flag.pull())
    }
}
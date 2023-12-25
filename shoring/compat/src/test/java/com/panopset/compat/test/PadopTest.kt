package com.panopset.compat.test

import com.panopset.compat.Padop
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PadopTest {
    @Test
    fun test() {
        Assertions.assertEquals(" x", Padop.leftPad("x", 2))
        Assertions.assertEquals("xy", Padop.leftPad("xy", 1))
        Assertions.assertEquals("01", Padop.leftPad("1", 2, '0'))
        Assertions.assertEquals("x ", Padop.rightPad("x", 2))
        Assertions.assertEquals("xy", Padop.rightPad("xy", 1))
        Assertions.assertEquals("10", Padop.rightPad("1", 2, '0'))
        Assertions.assertEquals("", Padop.leftPad(null, 1))
        Assertions.assertEquals("", Padop.rightPad(null, 1))
    }
}
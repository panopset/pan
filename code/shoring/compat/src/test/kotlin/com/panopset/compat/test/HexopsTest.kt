package com.panopset.compat.test

import com.panopset.compat.Stringop
import com.panopset.compat.panStringToBackSlashes
import com.panopset.compat.panStringToHex
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HexopsTest {
    @Test
    fun test() {
        Assertions.assertEquals("666F6F", panStringToHex("foo"))
    }

    @Test
    fun testBackSlashes() {
        Assertions.assertEquals("\\r\\n", panStringToBackSlashes(Stringop.DOS_RTN))
    }
}

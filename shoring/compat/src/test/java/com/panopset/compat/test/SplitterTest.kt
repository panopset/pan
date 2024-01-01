package com.panopset.compat.test

import com.panopset.compat.Splitter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SplitterTest {
    @Test
    fun testSplitter() {
        var result = Splitter.fixedLength(1).split(null)
        Assertions.assertEquals(0, result.size)
        result = Splitter.fixedLength(1).split("A")
        Assertions.assertEquals(1, result.size)
        Assertions.assertEquals("A", result[0])
        result = Splitter.fixedLengths(3).split("A")
        Assertions.assertEquals(1, result.size)
        Assertions.assertEquals("A", result[0])
        result = Splitter.fixedLength(2).split("ABCD")
        Assertions.assertEquals(2, result.size)
        Assertions.assertEquals("CD", result[1])
        result = Splitter.fixedLength(2).split("ABCDE")
        Assertions.assertEquals(3, result.size)
        Assertions.assertEquals("AB", result[0])
        Assertions.assertEquals("CD", result[1])
        Assertions.assertEquals("E", result[2])
        result = Splitter.fixedLengths(2, 3).split("ABCDE")
        Assertions.assertEquals(2, result.size)
        Assertions.assertEquals("AB", result[0])
        Assertions.assertEquals("CDE", result[1])
        result = Splitter.fixedLengths(2,3).split("ABCDE")
        Assertions.assertEquals(2, result.size)
        Assertions.assertEquals("AB", result[0])
        Assertions.assertEquals("CDE", result[1])
        result = Splitter.fixedLengths(2).split("ABCDE")
        Assertions.assertEquals(0, result.size)
    }
}

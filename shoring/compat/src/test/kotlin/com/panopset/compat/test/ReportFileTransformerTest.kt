package com.panopset.compat.test

import com.panopset.compat.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class ReportFileTransformerTest {
    @Test
    fun test() {
        Fileop.write(Stringop.FOO, temp)
        var result = Fileop.readTextFile(temp)
        Assertions.assertEquals("foo", result)
        ReportFileTransformer(temp, temp0, Transformer()).withByLineFilters(barFilter).exec()
        result = Fileop.readTextFile(temp)
        Assertions.assertEquals("foo", result)
        result = Fileop.readTextFile(temp0)
        Assertions.assertEquals("bar", result)
        cleanup()
    }

    @Test
    fun testSingleFilter() {
        Fileop.write(Stringop.FOO, temp)
        var result = Fileop.readTextFile(temp)
        Assertions.assertEquals(Stringop.FOO, result)
        ReportFileTransformer(temp, Transformer()).withByLineFilters(barFilter).exec()
        result = Fileop.readTextFile(temp)
        Assertions.assertEquals(Stringop.BAR, result)
        cleanup()
    }

    @Test
    fun testSameFilter() {
        Fileop.write(Stringop.FOO, temp)
        var result = Fileop.readTextFile(temp)
        Assertions.assertEquals(Stringop.FOO, result)
        ReportFileTransformer(temp, Transformer()).withByLineFilters(fooFilter).exec()
        result = Fileop.readTextFile(temp)
        Assertions.assertEquals(Stringop.FOO, result)
        cleanup()
    }

    companion object {
        val FOOEOL = Stringop.appendEol(Stringop.FOO)
        val BAREOL = Stringop.appendEol(Stringop.BAR)
        val temp: File = tempFile
        val temp0 = File("./temp0.txt")
        val barFilter = object: ByLineFilter {
            override fun filter(str: String): FilteredString {
                return FilteredString(Stringop.BAR)
            }
        }
        val fooFilter = object: ByLineFilter {
            override fun filter(str: String): FilteredString {
                return FilteredString(Stringop.FOO)
            }
        }

        fun cleanup() {
            FileopTest.cleanup()
            FileopTest.cleanup(temp0)
        }
    }
}

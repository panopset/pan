package com.panopset.compat.test

import com.panopset.compat.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.io.IOException

class ReportFileTransformerTest {
    @Test
    fun test() {
        Fileop.write(PanopTest, FOO, temp)
        var result = Fileop.readTextFile(PanopTest, temp)
        Assertions.assertEquals(FOOEOL, result)
        ReportFileTransformer(PanopTest, temp, temp0, Transformer()).withByLineFilters(barFilter).exec()
        result = Fileop.readTextFile(PanopTest, temp)
        Assertions.assertEquals(FOOEOL, result)
        result = Fileop.readTextFile(PanopTest, temp0)
        Assertions.assertEquals(BAREOL, result)
        cleanup()
    }

    @Test
    fun testSingleFilter() {
        Fileop.write(PanopTest, FOO, temp)
        var result = Fileop.readTextFile(PanopTest, temp)
        Assertions.assertEquals(Stringop.appendEol(FOO), result)
        ReportFileTransformer(PanopTest, temp, Transformer()).withByLineFilters(barFilter).exec()
        result = Fileop.readTextFile(PanopTest, temp)
        Assertions.assertEquals(Stringop.appendEol(BAR), result)
        cleanup()
    }

    @Test
    fun testSameFilter() {
        Fileop.write(PanopTest, FOO, temp)
        var result = Fileop.readTextFile(PanopTest, temp)
        Assertions.assertEquals(Stringop.appendEol(FOO), result)
        ReportFileTransformer(PanopTest, temp, Transformer()).withByLineFilters(fooFilter).exec()
        result = Fileop.readTextFile(PanopTest, temp)
        Assertions.assertEquals(Stringop.appendEol(FOO), result)
        cleanup()
    }

    companion object {
        val FOOEOL = Stringop.appendEol(FOO)
        val BAREOL = Stringop.appendEol(BAR)
        val temp: File = tempFile
        val temp0 = File("./temp0.txt")
        val barFilter = object: ByLineFilter {
            override fun filter(str: String): FilteredString {
                return FilteredString(BAR)
            }
        }
        val fooFilter = object: ByLineFilter {
            override fun filter(str: String): FilteredString {
                return FilteredString(FOO)
            }
        }

        @Throws(IOException::class)
        fun cleanup() {
            FileopTest.cleanup()
            FileopTest.cleanup(temp0)
        }
    }
}

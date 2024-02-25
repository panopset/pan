package com.panopset.tests.flywheel

import com.panopset.compat.Stringop.getEol
import com.panopset.tests.transformer.FlywheelTemplateToFileTest
import com.panopset.tests.transformer.FlywheelTemplateToTextTransformerTest
import com.panopset.tests.transformer.StandardPackagePath
import org.junit.jupiter.api.Test
import java.io.IOException

class BufferTest {
    @Test
    @Throws(IOException::class)
    fun testSimpleOneChar() {
        val expected = String.format("x%s", getEol())
        FlywheelTemplateToTextTransformerTest(this.javaClass.packageName, SIMPLEONECHAR, expected).test()
    }

    @Test
    @Throws(IOException::class)
    fun testTwoLines() {
        val expected = "x" + getEol() + "y" + getEol()
        FlywheelTemplateToTextTransformerTest(this.javaClass.packageName, SIMPLETWOLINES, expected).test()
    }

    @Test
    @Throws(IOException::class)
    fun testSimpleBuffer() {
        FlywheelTemplateToFileTest(
            this.javaClass.packageName,
            SimpleTest.SIMPLETEST,
            SimpleTest.SIMPLEOUT,
            SimpleTest.EXPECTED
        ).test()
        val expected = StandardPackagePath(this.javaClass.packageName).getRezStr(SimpleTest.EXPECTED)
        FlywheelTemplateToTextTransformerTest(this.javaClass.packageName, SimpleTest.SIMPLETEST, expected).test()
        FlywheelTemplateToFileTest(
            this.javaClass.packageName,
            SimpleTest.SIMPLETEST,
            SimpleTest.SIMPLEOUT,
            SimpleTest.EXPECTED
        ).test()
    }

    @Test
    @Throws(IOException::class)
    fun testComplexBuffer() {
        val expected = StandardPackagePath(this.javaClass.packageName).getRezStr(ComplexTest.EXPECTED)
        FlywheelTemplateToTextTransformerTest(this.javaClass.packageName, ComplexTest.TEMPLATE, expected).test()
        FlywheelTemplateToFileTest(
            this.javaClass.packageName,
            ComplexTest.TEMPLATE,
            ComplexTest.OUTPUT,
            ComplexTest.EXPECTED
        ).test()
    }

    companion object {
        const val SIMPLEONECHAR = "simpleOneChar.txt"
        const val SIMPLETWOLINES = "simpleTwoLines.txt"
    }
}

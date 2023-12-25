package com.panopset.tests.flywheel

import java.io.IOException

class TemplateTest {
    /**
     * Test template command. See **templateTest01.txt**.
     */
    @Throws(IOException::class)
    fun testTemplateCommand() {
        SimpleTest().comparisonTest(
            "templateTest01.txt", "templateTest.txt",
            "templateTestExpected.txt"
        )
    }
}

package com.panopset.tests.flywheel

import org.junit.jupiter.api.Test
import java.io.IOException

class DoubleFileTest {
    /**
     * Test generating more than one files using Command File.
     */
    @Test
    @Throws(IOException::class)
    fun testDoubleFileGeneration() {
        SimpleTest()
            .comparisonTest(
                "doubleFileTest.txt", arrayOf(
                    "dft0.txt",
                    "dft1.txt"
                ), arrayOf(
                    "dft_expected0.txt",
                    "dft_expected1.txt"
                )
            )
    }
}

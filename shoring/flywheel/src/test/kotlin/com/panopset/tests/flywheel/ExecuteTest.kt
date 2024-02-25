package com.panopset.tests.flywheel

import org.junit.jupiter.api.Test
import java.io.IOException

class ExecuteTest {
    @Test
    @Throws(IOException::class)
    fun testExecuteCommand() {
        SimpleTest().comparisonTest(
            "executeTest.txt", "executeTest.txt",
            "executeTestExpected.txt"
        )
    }
}

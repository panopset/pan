package com.panopset.tests.flywheel

import org.junit.jupiter.api.Test
import java.io.IOException

class DeepListTest {
    /**
     * Test using a list within a list.
     */
    @Test
    @Throws(IOException::class)
    fun testListCommand() {
        SimpleTest().comparisonTest(
            "deepListTest01.txt", "deepListTest.txt",
            "deepListTestExpected.txt"
        )
    }
}

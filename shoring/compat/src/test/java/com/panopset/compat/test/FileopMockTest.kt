package com.panopset.compat.test

import com.panopset.compat.Fileop
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.IOException

class FileopMockTest {
    @Test
    @Throws(IOException::class)
    fun testTouch() {
        Fileop.delete(tempFile)
        Assertions.assertFalse(tempFile.exists())
        Fileop.touch(tempFile)
        Assertions.assertTrue(tempFile.exists())
        Fileop.delete(deepFile)
        Assertions.assertFalse(deepFile.exists())
        Fileop.touch(deepFile)
        Assertions.assertTrue(deepFile.exists())
    }
}

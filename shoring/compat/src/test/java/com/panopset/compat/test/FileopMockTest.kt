package com.panopset.compat.test

import com.panopset.compat.Fileop
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FileopMockTest {
    @Test
    fun testTouch() {
        Fileop.delete(PanopTest, tempFile)
        Assertions.assertFalse(tempFile.exists())
        Fileop.touch(PanopTest, tempFile)
        Assertions.assertTrue(tempFile.exists())
        Fileop.delete(PanopTest, deepFile)
        Assertions.assertFalse(deepFile.exists())
        Fileop.touch(PanopTest, deepFile)
        Assertions.assertTrue(deepFile.exists())
    }
}

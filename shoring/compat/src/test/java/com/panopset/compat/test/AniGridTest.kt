package com.panopset.compat.test

import com.panopset.compat.AniGrid
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AniGridTest {
    @Test
    fun test() {
        val aniGrid = AniGrid(100, 200, 10, 50)
        Assertions.assertEquals(4, aniGrid.dspHeight)
        Assertions.assertEquals(10, aniGrid.dspWidth)
        Assertions.assertArrayEquals(
            intArrayOf(0, 0, 10, 4, 35, 27, 10, 4),
            aniGrid.getPaintDimensions(0, 0, 35, 27)
        )
    }

    @Test
    fun testWithMultiplier() {
        val aniGrid = AniGrid(100, 200, 10, 50, 2.0)
        Assertions.assertEquals(2.0, aniGrid.multiplier)
        Assertions.assertEquals(8, aniGrid.dspHeight)
        Assertions.assertEquals(20, aniGrid.dspWidth)
        Assertions.assertArrayEquals(
            intArrayOf(0, 0, 10, 4, 35, 27, 20, 8),
            aniGrid.getPaintDimensions(0, 0, 35, 27)
        )
    }
}
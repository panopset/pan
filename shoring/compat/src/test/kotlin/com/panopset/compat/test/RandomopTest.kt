package com.panopset.compat.test

import com.panopset.compat.Randomop
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RandomopTest {
    @Test
    fun test() {
        var found = 0
        while (found != 7) {
            val result = Randomop.random(1, 3)
            found = verifyInRange1to3(result, found)
        }
    }

    @Test
    fun testMinGreaterThanMax() {
        var found = 0
        while (found != 7) {
            val result = Randomop.random(3, 1)
            found = verifyInRange1to3(result, found)
        }
    }

    @Test
    fun testSingle() {
        val result = Randomop.random(0, 0)
        Assertions.assertEquals(Integer.valueOf(0), result)
    }

    @Test
    fun testBytes() {
        val ba = ByteArray(2)
        Randomop.nextBytes(ba)
        Assertions.assertNotNull(ba)
    }

    @Test
    fun testNextInt() {
        var x = Randomop.nextInt()
        val lastOne = x
        x = Randomop.nextInt()
        Assertions.assertNotEquals(lastOne, x)
    }

    @Test
    fun testNextLong() {
        var x = Randomop.nextLong()
        val lastOne = x
        x = Randomop.nextLong()
        Assertions.assertNotEquals(lastOne, x)
    }

    companion object {
        private fun verifyInRange1to3(result: Int, found: Int): Int {
            var rtn = found
            Assertions.assertTrue(4 > result)
            Assertions.assertTrue(0 < result)
            when (result) {
                1 -> rtn = rtn or 1
                2 -> rtn = rtn or 2
                3 -> rtn = rtn or 4
            }
            return rtn
        }
    }
}

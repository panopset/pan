package com.panopset.compat.test

import com.panopset.compat.Nls
import com.panopset.compat.RegexValidator
import com.panopset.compat.Stringop
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RegexAndNlsTest {
    @Test
    fun testRegex() {
        var rv = RegexValidator("a")
        Assertions.assertTrue(rv.matches("a"))
        Assertions.assertFalse(rv.matches("x"))
        Assertions.assertTrue(rv.matches("ab"))
        Assertions.assertTrue(rv.matches("ba"))
        Assertions.assertFalse(rv.matches("xy"))
        Assertions.assertFalse(rv.matches(""))
        Assertions.assertFalse(rv.matches(null))
        Assertions.assertFalse(rv.matches(""))
        rv = RegexValidator(null)
        Assertions.assertFalse(rv.matches(null))
        Assertions.assertFalse(rv.matches("a"))
    }

    @Test
    fun testNls() {
        Assertions.assertEquals(Stringop.FOO, Nls.xlate(Stringop.FOO))
    }
}
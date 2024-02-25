package com.panopset.compat.test

import com.panopset.compat.Numberop.isInteger
import com.panopset.compat.Numberop.isNumber
import com.panopset.compat.Randomop
import com.panopset.compat.Stringop.CARRIAGE_RETURN_VALUE
import com.panopset.compat.Stringop.DOS_RTN
import com.panopset.compat.Stringop.FOO
import com.panopset.compat.Stringop.LINE_FEED_VALUE
import com.panopset.compat.Stringop.arrayToList
import com.panopset.compat.Stringop.capitalize
import com.panopset.compat.Stringop.capund
import com.panopset.compat.Stringop.check4match
import com.panopset.compat.Stringop.concat
import com.panopset.compat.Stringop.countReturns
import com.panopset.compat.Stringop.getDollarString
import com.panopset.compat.Stringop.getEol
import com.panopset.compat.Stringop.isBlank
import com.panopset.compat.Stringop.isPopulated
import com.panopset.compat.Stringop.isRegexFound
import com.panopset.compat.Stringop.notNull
import com.panopset.compat.Stringop.parseBoolean
import com.panopset.compat.Stringop.parseInt
import com.panopset.compat.Stringop.pullAfter
import com.panopset.compat.Stringop.setEol
import com.panopset.compat.Stringop.skipComment
import com.panopset.compat.Stringop.stringToList
import com.panopset.compat.Stringop.stringToListWithEol
import com.panopset.compat.Stringop.toLowerCase
import com.panopset.compat.Stringop.toMaxLength
import com.panopset.compat.Stringop.trimSurroundingQuotes
import com.panopset.compat.Stringop.trimTrailingEmptyLines
import com.panopset.compat.Stringop.upund
import com.panopset.compat.Stringop.wrapFixedWidth
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class StringopTest {
    private val emptyList: List<String> = ArrayList()
    private val fooBarArray = arrayOf("foo", "bar")
    private val fooBarArrayWithEOL = arrayOf("foo\r\n", "bar\r\n")
    @Test
    fun test() {
        Assertions.assertEquals("${Char(CARRIAGE_RETURN_VALUE)}${Char(LINE_FEED_VALUE)}", DOS_RTN)
        setEol(DOS_RTN)
        Assertions.assertTrue(isBlank(null))
        Assertions.assertTrue(isBlank(""))
        Assertions.assertTrue(isBlank(" "))
        Assertions.assertTrue(isBlank("\t"))
        Assertions.assertTrue(isBlank(DOS_RTN))
        Assertions.assertTrue(isBlank("\t \n"))
        Assertions.assertFalse(isBlank("\ta \n"))
        Assertions.assertTrue(isPopulated("x"))
        Assertions.assertFalse(isPopulated("\t "))
        Assertions.assertFalse(isPopulated("\t"))
        Assertions.assertEquals("Foo", capitalize("foo"))
        Assertions.assertEquals("", capitalize(""))
        Assertions.assertEquals("A", capitalize("a"))
        Assertions.assertEquals("", capitalize(null))
        Assertions.assertEquals(DOS_RTN, getEol())
        setEol("\n")
        Assertions.assertEquals("\n", getEol())
        Assertions.assertEquals(3, countReturns("\n\nasdf\n"))
        Assertions.assertEquals(0, countReturns(""))
        Assertions.assertEquals(0, countReturns(null))
        setEol(DOS_RTN)
        Assertions.assertEquals(listOf(*fooBarArray), stringToList("foo\r\nbar\r\n"))
        Assertions.assertEquals(listOf(*fooBarArray), stringToList("foo\r\nbar"))
        Assertions.assertEquals(listOf(*fooBarArray), stringToList("foo\r\nbar"))
        Assertions.assertEquals(listOf(*fooBarArrayWithEOL), stringToListWithEol("foo\r\nbar"))
        Assertions.assertEquals(listOf(*fooBarArrayWithEOL), stringToListWithEol("foo\r\nbar\r\n"))
        Assertions.assertEquals(listOf("\r\n", "bar\r\n"), stringToListWithEol("\r\nbar"))
        setEol("\n")
    }

    @Test
    fun toMaxLengthTest() {
        Assertions.assertEquals("", toMaxLength(null, Randomop.random(-5, 5)))
        Assertions.assertEquals("", toMaxLength("", Randomop.random(-5, 5)))
        Assertions.assertEquals(FOO, toMaxLength(FOO, 100))
        Assertions.assertEquals("", toMaxLength(FOO, -1))
        Assertions.assertEquals("", toMaxLength(FOO, 0))
        Assertions.assertEquals(FOO, toMaxLength(FOO, 3))
        Assertions.assertEquals("fo", toMaxLength(FOO, 2))
        Assertions.assertEquals("f", toMaxLength(FOO, 1))
    }

    @Test
    fun linesToListTest() {
        Assertions.assertEquals(emptyList, stringToList(""))
        Assertions.assertEquals(listOf("foo"), stringToList("foo"))
        Assertions.assertEquals(listOf("foo"), stringToList("foo\n"))
        Assertions.assertEquals(listOf("foo"), stringToList("foo\r\n"))
        Assertions.assertEquals(listOf("foo", "bar"), stringToList("foo\r\nbar"))
        Assertions.assertEquals(listOf("foo", "bar"), stringToList("foo\nbar"))
    }

    @Test
    fun testSkipComment() {
        Assertions.assertTrue(skipComment("#"))
        Assertions.assertTrue(skipComment("//"))
        Assertions.assertFalse(skipComment("/"))
        Assertions.assertTrue(skipComment("#asdf"))
        Assertions.assertTrue(skipComment("//asdf"))
        Assertions.assertFalse(skipComment("foo"))
        Assertions.assertFalse(skipComment("x"))
        Assertions.assertTrue(skipComment(""))
    }

    @Test
    fun testMathFilters() {
        Assertions.assertTrue(isInteger("0"))
        Assertions.assertTrue(isInteger("1"))
        Assertions.assertTrue(isInteger("2"))
        Assertions.assertTrue(isInteger("-1"))
        Assertions.assertTrue(isInteger("-2"))
        Assertions.assertTrue(isInteger("-999"))
        Assertions.assertTrue(isNumber("0"))
        Assertions.assertTrue(isNumber("1"))
        Assertions.assertTrue(isNumber("2"))
        Assertions.assertFalse(isNumber("-1"))
        Assertions.assertFalse(isNumber("-999"))
        Assertions.assertEquals(0, parseInt(""))
        Assertions.assertEquals(0, parseInt("x"))
        Assertions.assertEquals(5323, parseInt("5,323"))
        Assertions.assertEquals(5, parseInt("5"))
        Assertions.assertEquals("$5.00", getDollarString(500))
        Assertions.assertEquals("$5.95", getDollarString(595))
        Assertions.assertEquals("$1,002.05", getDollarString(100205))
        Assertions.assertEquals(5, parseInt("5", 0))
        Assertions.assertEquals(5, parseInt("5", 5))
        Assertions.assertEquals(5, parseInt("x", 5))
        Assertions.assertEquals(5, parseInt("+5"))
    }

    @Test
    fun regexFoundTest() {
       Assertions.assertTrue(isRegexFound("foo", "foo"))
       Assertions.assertTrue(isRegexFound("foo", "foobar"))
       Assertions.assertTrue(isRegexFound("foo", "xfoobar"))
       Assertions.assertFalse(isRegexFound("foo", "bar"))
    }

    @Test
    fun pullAfterTest() {
        Assertions.assertEquals(
            "com/panopset/jnlp/games",
            pullAfter("p_some_apps/src/main/kotlin/com/panopset/jnlp/games", "src/main/kotlin/")
        )
        Assertions.assertEquals("", pullAfter("foo", null))
        Assertions.assertEquals("", pullAfter(null, "foo"))
        Assertions.assertEquals("", pullAfter("foo", "foobar"))
        Assertions.assertEquals("", pullAfter("foo", "bar"))
    }

    @Test
    fun trimTrailingEmptyLinesTest() {
        val fubar = arrayOf("foo", "", "bar", "")
        Assertions.assertArrayEquals(arrayOf("foo", "", "bar"), trimTrailingEmptyLines(fubar))
        Assertions.assertArrayEquals(arrayOf<String>(), trimTrailingEmptyLines(arrayOf()))
        Assertions.assertArrayEquals(arrayOf("foo"), trimTrailingEmptyLines(arrayOf("foo")))
        Assertions.assertArrayEquals(arrayOf("foo"), trimTrailingEmptyLines(arrayOf("foo", "")))
    }

    @Test
    fun concatTest() {
        Assertions.assertEquals("foobar", concat("foo", "bar"))
        Assertions.assertEquals("", concat("", ""))
        Assertions.assertEquals("foobar", concat("foo", "", "bar"))
        Assertions.assertEquals("foo", concat(null, "foo"))
        Assertions.assertEquals("", concat(null as String?))
        Assertions.assertEquals("", concat(null as List<String?>?))
    }

    @Test
    fun check4matchTest() {
        Assertions.assertEquals("bat", check4match("foo", "foo", "bat", "baz"))
        Assertions.assertEquals("baz", check4match("foo", "bar", "bat", "baz"))
        Assertions.assertEquals("", check4match(null, "bar", "bat", "baz"))
        Assertions.assertEquals("", check4match("foo", null, "bat", "baz"))
        Assertions.assertEquals("", check4match("foo", "bar", null, "baz"))
        Assertions.assertEquals("", check4match("foo", "bar", "bat", null))
    }

    @Test
    fun wrapFixedWidthTest() {
        setEol(DOS_RTN)
        Assertions.assertEquals("fo\r\no", wrapFixedWidth("foo", 2))
        Assertions.assertEquals("", wrapFixedWidth("", 2))
        Assertions.assertEquals("foo", wrapFixedWidth("foo", 0))
        Assertions.assertEquals("foo", wrapFixedWidth("foo", 5))
        setEol("\n")
    }

    @Test
    fun testNotNull() {
        Assertions.assertEquals("", notNull(null))
        Assertions.assertEquals("", notNull(""))
        Assertions.assertEquals("foo", notNull("foo"))
    }

    @Test
    fun testArrayToList() {
        Assertions.assertEquals(0, arrayToList(arrayOf()).size)
        Assertions.assertEquals("bar", arrayToList(arrayOf("foo", "bar"))[1])
    }

    @Test
    fun testToLowerCase() {
        Assertions.assertEquals("foo", toLowerCase("Foo"))
        Assertions.assertEquals("", toLowerCase(null))
        Assertions.assertEquals("foo", toLowerCase("FOO"))
        Assertions.assertEquals("foo", toLowerCase("fOO"))
    }

    @Test
    fun testParseBoolean() {
        Assertions.assertFalse(parseBoolean(null))
        Assertions.assertFalse(parseBoolean(""))
        Assertions.assertFalse(parseBoolean("42"))
        Assertions.assertFalse(parseBoolean("foo"))
        Assertions.assertFalse(parseBoolean("FalSe"))
        Assertions.assertFalse(parseBoolean("false"))
        Assertions.assertFalse(parseBoolean("no"))
        Assertions.assertTrue(parseBoolean("trUe"))
        Assertions.assertTrue(parseBoolean("on"))
        Assertions.assertTrue(parseBoolean("yes"))
        Assertions.assertTrue(parseBoolean("Y"))
    }

    @Test
    fun testCapund() {
        Assertions.assertEquals("FOO_BAR", capund("FooBar"))
        Assertions.assertEquals("FOO", capund("Foo"))
        Assertions.assertEquals("FOO", capund("foo"))
    }

    @Test
    fun testUpund() {
        Assertions.assertEquals("FOOBAR", upund("FooBar"))
        Assertions.assertEquals("FOO_BAR", upund("Foo Bar"))
        Assertions.assertEquals("FOO", upund("Foo"))
        Assertions.assertEquals("FOO", upund("foo"))
    }

    @Test
    fun testTrimSurroundingQuotes() {
        Assertions.assertEquals("FOO", trimSurroundingQuotes("FOO"))
        Assertions.assertEquals("FOO", trimSurroundingQuotes("\"FOO\""))
        Assertions.assertEquals("FOO\"", trimSurroundingQuotes("FOO\""))
        Assertions.assertEquals("\"FOO", trimSurroundingQuotes("\"FOO"))
        Assertions.assertEquals("FO\"O", trimSurroundingQuotes("FO\"O"))
    }
}

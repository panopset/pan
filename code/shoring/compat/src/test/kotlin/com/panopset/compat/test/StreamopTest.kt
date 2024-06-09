package com.panopset.compat.test

import com.panopset.compat.Streamop
import com.panopset.compat.Stringop
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.*

class StreamopTest {
    /**
     * Test Streamop with byte arrays.
     */
    @Test
    fun test() {
        val rand = Stringop.randomString()
        val bis = ByteArrayInputStream(rand.toByteArray())
        val bos = ByteArrayOutputStream()
        Streamop.copyStream(bis, bos)
        Assertions.assertEquals(rand, bos.toString())
        Assertions.assertEquals(
            rand,
            Streamop.getTextFromStream(ByteArrayInputStream(rand.toByteArray()))
        )
    }

    @Test
    fun testErrors() {
        val rslt = Streamop.getTextFromStream(object : InputStream() {
            @Throws(IOException::class)
            override fun read(): Int {
                throw IOException("Make sure exception returns blank.")
            }
        })
        Assertions.assertEquals("", rslt)
        var list = Streamop.getLinesFromReader(object : Reader() {
            override fun close() {}
            @Throws(IOException::class)
            override fun read(arg0: CharArray, arg1: Int, arg2: Int): Int {
                throw IOException("Make sure exception returns empty list.")
            }
        })
        Assertions.assertEquals(ArrayList<String>(), list)
        list = Streamop.getLinesFromReaderWithEol(object : Reader() {
            override fun close() {}
            @Throws(IOException::class)
            override fun read(arg0: CharArray, arg1: Int, arg2: Int): Int {
                throw IOException("Make sure exception returns empty list.")
            }
        })
        Assertions.assertEquals(ArrayList<String>(), list)
    }
}

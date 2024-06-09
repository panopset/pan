package com.panopset.compat.test

import com.panopset.compat.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RezopTest {
    @Test
    fun test() {
        Assertions.assertEquals(
            "x",
            Rezop.textStreamToList(this.javaClass.getResourceAsStream(REZPATH))[0]
        )
        Assertions.assertEquals("/com/panopset/compat/test/", Rezop.pkg2resourcePath(this.javaClass))
        Rezop.copyTextResourceToFile(this.javaClass, REZPATH, Fileop.getCanonicalPath(tempFile))
        Assertions.assertEquals("x", Fileop.readTextFile(tempFile)
        )
        Rezop.copyTextResourceToFile(this.javaClass, REZPATH, tempFile)
        Assertions.assertEquals("x", Fileop.readTextFile(tempFile))
        Assertions.assertEquals("com/panopset/foo", Rezop.pkg2path("com.panopset.foo"))
        Assertions.assertEquals("/com/panopset/compat/test", Rezop.getPackageResourcePath(this.javaClass.getPackage()))
    }

    @Test
    fun testLoadFromRez() {
        val rslt = Streamop.getTextFromStream(this.javaClass.getResourceAsStream(REZPATH)).replace("\r\n", "\n")
        Assertions.assertEquals(String.format("x%s", "\n"), rslt)
        Assertions.assertEquals("x", Rezop.textStreamToList(this.javaClass.getResourceAsStream(REZPATH))[0])
        Assertions.assertEquals("x", Rezop.textStreamToList(this.javaClass.getResourceAsStream(REZPATH))[0])
    }

    @Test
    fun testNotFound() {
        val rslt = Streamop.getTextFromStream(this.javaClass.getResourceAsStream("foo.txt"))
        Assertions.assertEquals("", rslt)
    }

    companion object {
        private const val REZPATH = "/com/panopset/enchar.txt"
    }
}

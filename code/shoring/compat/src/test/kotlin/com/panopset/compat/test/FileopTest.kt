package com.panopset.compat.test

import com.panopset.compat.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream

class FileopTest {
    @BeforeEach
    fun beforeEach() {
        cleanup()
    }

    @Test
    fun test() {
        Assertions.assertEquals(Stringop.USH, Fileop.getCanonicalPath(Fileop.userHome))
        Fileop.delete(tempFile)
        Assertions.assertFalse(tempFile.exists())
        Fileop.touch(tempFile)
        Assertions.assertTrue(tempFile.exists())
        Fileop.touch(deepFile)
        Assertions.assertTrue(tempFile.exists())
        Assertions.assertEquals("txt", Fileop.getExtension(tempFile))
        Assertions.assertEquals("", Fileop.getExtension(File(Stringop.FOO)))
        var parentDir = Fileop.getParentDirectory(deepFile)
        Assertions.assertEquals("temp", parentDir.substring(parentDir.lastIndexOf(Stringop.FSP) + 1))
        parentDir = Fileop.getParentDirectory(File("foo/temp.txt"))
        Assertions.assertEquals("foo", parentDir.substring(parentDir.lastIndexOf(Stringop.FSP) + 1))
        Assertions.assertFalse(Fileop.fileExists(File("foo.txt")))
        Assertions.assertEquals("temp", Fileop.removeExtension("temp.txt"))
        Assertions.assertEquals("", Fileop.removeExtension(""))
        Assertions.assertTrue(Fileop.isFileOneOfExtensions(tempFile, "txt"))
        Assertions.assertFalse(Fileop.isFileOneOfExtensions(tempFile, "java,xml"))
        Assertions.assertTrue(Fileop.isFileOneOfExtensions(tempFile, "java,txt"))
    }

    @Test
    fun combinePathsTest() {
        val file = File(Fileop.combinePaths("./temp", "temp.txt"))
        Assertions.assertEquals("", Fileop.readTextFile(file))
        Assertions.assertEquals(Fileop.getCanonicalPath(deepFile), Fileop.getCanonicalPath(file))
        Fileop.write(Stringop.FOO, file)
        Assertions.assertEquals(Stringop.FOO, Fileop.readTextFile(file))
        Assertions.assertEquals(Stringop.FOO, Fileop.readTextFile("./temp/temp.txt"))
    }

    @Test
    fun combinePathsWithReturnCharTestCRLF() {
        val existingEol = Stringop.getEol()
        Stringop.setEol(Stringop.DOS_RTN)
        val file = File(Fileop.combinePaths("./temp", "temp.txt"))
        Assertions.assertEquals("", Fileop.readTextFile(file))
        Assertions.assertEquals(Fileop.getCanonicalPath(deepFile), Fileop.getCanonicalPath(file))
        // https://stackoverflow.com/questions/70781328/bufferedreader-in-java-is-skipping-the-last-empty-line-in-file
        Assertions.assertEquals("\r\n", Stringop.DOS_RTN)
        Fileop.write("${Stringop.FOO}${Stringop.getEol()}${Stringop.getEol()}", file)
        Assertions.assertEquals("foo\r\n", Fileop.readTextFile(file))
        Assertions.assertEquals("foo\r\n", Fileop.readTextFile("./temp/temp.txt"))
        Stringop.setEol(existingEol)
    }

    @Test
    fun combinePathsWithReturnCharTestLF() {
        val existingEol = Stringop.getEol()
        Stringop.setEol("\n")
        val file = File(Fileop.combinePaths("./temp", "temp.txt"))
        Assertions.assertEquals("", Fileop.readTextFile(file))
        Assertions.assertEquals(Fileop.getCanonicalPath(deepFile), Fileop.getCanonicalPath(file))
        Fileop.write("${Stringop.FOO}${Stringop.getEol()}${Stringop.getEol()}", file)
        Assertions.assertEquals("foo\n", Fileop.readTextFile(file))
        Assertions.assertEquals("foo\n", Fileop.readTextFile("./temp/temp.txt"))
        Stringop.setEol(existingEol)
    }

    @Test
    fun checkParentTest() {
        Assertions.assertFalse(tempDir.exists())
        Assertions.assertTrue(Fileop.checkParent(deepFile))
        Assertions.assertTrue(tempDir.exists())
        cleanup()
        Assertions.assertFalse(tempDir.exists())
        Fileop.touch(deepFile)
        Assertions.assertTrue(tempDir.exists())
        Assertions.assertTrue(Fileop.checkParent(deepFile))
        cleanup()
        Assertions.assertFalse(tempDir.exists())
        Fileop.mkdirs(tempDir)
        Assertions.assertTrue(tempDir.exists())
        Fileop.delete(tempDir)
        Assertions.assertFalse(tempDir.exists())
        Fileop.touch(tempDir)
        clearlg()
        Fileop.mkdirs(tempDir)
        Assertions.assertEquals(1, stacklg.size)
    }

    @Test
    fun copyInputStreamToFileTest() {
        Assertions.assertFalse(tempFile.exists())
        val inp: InputStream = ByteArrayInputStream(Stringop.FOO.toByteArray())
        Fileop.copyInputStreamToFile(inp, "./temp.txt")
        Assertions.assertEquals(Stringop.FOO, Fileop.readTextFile(tempFile))
    }

    @Test
    fun deleteLinesTest() {
        Stringop.setEol(Stringop.DOS_RTN)
        Fileop.write("foo\r\nbar\r\nbat", tempFile)
        deleteLines(tempFile, "bar")
        Assertions.assertEquals("foo\r\nbat", Fileop.readTextFile(tempFile))
        Fileop.write("foo\r\nbar\r\nbat", tempFile)
        Assertions.assertEquals("foo\r\nbar\r\nbat", Fileop.readTextFile(tempFile))
        Fileop.write("foo\r\nbar\r\nbat", deepFile)
        deleteLines(tempDir, "bar")
        Assertions.assertEquals("foo\r\nbat", Fileop.readTextFile(deepFile))
        Fileop.write("foo\r\nbar\r\nbat", tempFile)
        deleteLines(tempFile, "")
        Assertions.assertEquals("foo\r\nbar\r\nbat", Fileop.readTextFile(tempFile))
        Stringop.setEol("\n")
    }

    @Test
    fun testGetCanonicalPath() {
        Assertions.assertTrue(Stringop.isPopulated(Fileop.getCanonicalPath(File(Stringop.USH))))
        Assertions.assertTrue(Fileop.getCanonicalPath(tempFile).indexOf("temp.txt") > 0)
    }

    @Test
    fun standardPathTest() {
        val path = Fileop.getStandardPath(File("temp\\temp.txt"))
        Assertions.assertEquals("/temp.txt", path.substring(path.lastIndexOf("/")))
    }

    @Test
    fun createTempFileTest() {
        Assertions.assertEquals(0, Fileop.readLines(tempFile).size)
        val tempFoo = Fileop.createTempFile(Stringop.FOO)
        val tempBar = Fileop.createTempFile(Stringop.BAR)
        Fileop.touch(tempFoo)
        Assertions.assertTrue(tempFoo.exists())
        Fileop.write(arrayOf(Stringop.FOO, Stringop.BAR), tempFoo)
        checkIsFooBarFile(tempFoo)
        Fileop.moveFile(tempFoo, tempBar)
        checkIsFooBarFile(tempBar)
        Assertions.assertFalse(tempFoo.exists())
        Fileop.copyFile(tempBar, tempFoo)
        checkIsFooBarFile(tempFoo)
        checkIsFooBarFile(tempBar)
        Fileop.delete(tempFoo)
        Fileop.delete(tempBar)
        Assertions.assertFalse(tempFoo.exists())
        Assertions.assertFalse(tempBar.exists())
    }

    private fun checkIsFooBarFile(file: File) {
        val lines = Fileop.readLines(file)
        Assertions.assertEquals(2, lines.size)
        Assertions.assertEquals(Stringop.FOO, lines[0])
        Assertions.assertEquals(Stringop.BAR, lines[1])
    }

    companion object {
        fun cleanup() {
            cleanup(tempFile)
            cleanup(tempDir)
        }

        fun cleanup(f: File) {
            Fileop.delete(f)
            Assertions.assertFalse(f.exists())
        }
    }
}

val tempFile = File("./temp.txt")
val tempDir = File("./temp")
val deepFile = File("./temp/temp.txt")
val badFile = File("\u0000")

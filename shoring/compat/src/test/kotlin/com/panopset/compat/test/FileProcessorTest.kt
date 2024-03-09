package com.panopset.compat.test

import com.panopset.compat.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.io.IOException

class FileProcessorTest {
    val file: File = tempFile
    @Test
    fun test() {
        Fileop.delete(file)
        Fileop.write(Stringop.FOO, file)
        val fp = FileProcessor(file).withLineFilter(MyLineFilter())
        fp.exec()
        val result = Fileop.readTextFile(file)
        Assertions.assertEquals(Stringop.BAR, result)
        FileopTest.cleanup()
    }
}

class MyLineFilter: ByLineFilter {
    override fun filter(str: String): FilteredString {
        return FilteredString(Stringop.BAR)
    }
}

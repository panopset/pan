package com.panopset.compat.test

import com.panopset.compat.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class FileProcessorTest {
    val file: File = tempFile
    @Test
    fun test() {
        Fileop.delete(PanopTest, file)
        Fileop.write(PanopTest, FOO, file)
        val fp = FileProcessor(PanopTest, file).withLineFilter(MyLineFilter())
        fp.exec()
        val result = Fileop.readTextFile(PanopTest, file)
        Assertions.assertEquals(Stringop.appendEol(BAR), result)
        FileopTest.cleanup()
    }
}

class MyLineFilter: ByLineFilter {
    override fun filter(str: String): FilteredString {
        return FilteredString(BAR)
    }
}

package com.panopset.compat.test

import com.panopset.compat.Fileop
import com.panopset.compat.Propop
import com.panopset.compat.Stringop
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class PropopTest {
    @BeforeEach
    fun beforeEach() {
        FileopTest.cleanup()
    }

    @Test
    fun test() {
        val file: File = tempFile
        createSampleData(file)
        val map = Propop.loadPropsFromFile(tempFile)
        Assertions.assertEquals(Stringop.BAR, map[Stringop.FOO])
        var props = Propop.load(file)
        Assertions.assertEquals(1, props.size)
        props.clear()
        props["bar"] = "foo"
        Propop.load(props, file)
        Assertions.assertEquals(2, props.size)
        Propop.save(props, file)
        props = Propop.load(file)
        Assertions.assertEquals(2, props.size)
    }

    @Test
    fun emptyTest() {
        val map = Propop.loadPropsFromFile(tempFile)
        Assertions.assertEquals(0, map.size)
    }

    private fun createSampleData(file: File) {
        val sampleProp = "foo=bar"
        Fileop.write(sampleProp, file)
    }
}

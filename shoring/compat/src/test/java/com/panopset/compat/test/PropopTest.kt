package com.panopset.compat.test

import com.panopset.compat.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.io.IOException

class PropopTest {
    @BeforeEach
    @Throws(IOException::class)
    fun beforeEach() {
        FileopTest.cleanup()
    }

    @Test
    @Throws(IOException::class)
    fun test() {
        val file: File = tempFile
        createSampleData(file)
        var map = Propop.loadPropsFromFile(PanopTest, tempFile)
        Assertions.assertEquals(BAR, map[FOO])
        map = Propop.loadPropsFromFile(PanopTest, null)
        Assertions.assertEquals(0, map.size)
        var props = Propop.load(PanopTest, null)
        Assertions.assertEquals(0, props.size)
        props = Propop.load(PanopTest, file)
        Assertions.assertEquals(1, props.size)
        Propop.load(PanopTest, props, null)
        Assertions.assertEquals(1, props.size)
        props.clear()
        props["bar"] = "foo"
        Propop.load(PanopTest, null, file)
        Propop.load(PanopTest, props, file)
        Propop.load(PanopTest, props, null)
        Propop.load(PanopTest, null, null)
        Assertions.assertEquals(2, props.size)
        Propop.save(null, file)
        Propop.save(props, null)
        Propop.save(null, null)
        Propop.save(props, file)
        props = Propop.load(PanopTest, file)
        Assertions.assertEquals(2, props.size)
    }

    @Test
    fun emptyTest() {
        val map = Propop.loadPropsFromFile(PanopTest, tempFile)
        Assertions.assertEquals(0, map.size)
    }

    private fun createSampleData(file: File) {
        val sampleProp = "foo=bar"
        Fileop.write(PanopTest, sampleProp, file)
    }
}

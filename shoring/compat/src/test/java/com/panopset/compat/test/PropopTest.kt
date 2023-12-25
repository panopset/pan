package com.panopset.compat.test

import com.panopset.compat.Fileop
import com.panopset.compat.Propop
import com.panopset.compat.Stringop
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
        var map = Propop.loadPropsFromFile(tempFile)
        Assertions.assertEquals(Stringop.BAR, map[Stringop.FOO])
        map = Propop.loadPropsFromFile(null)
        Assertions.assertEquals(0, map.size)
        var props = Propop.load(null)
        Assertions.assertEquals(0, props.size)
        props = Propop.load(file)
        Assertions.assertEquals(1, props.size)
        Propop.load(props, null)
        Assertions.assertEquals(1, props.size)
        props.clear()
        props["bar"] = "foo"
        Propop.load(null, file)
        Propop.load(props, file)
        Propop.load(props, null)
        Propop.load(null, null)
        Assertions.assertEquals(2, props.size)
        Propop.save(null, file)
        Propop.save(props, null)
        Propop.save(null, null)
        Propop.save(props, file)
        props = Propop.load(file)
        Assertions.assertEquals(2, props.size)
    }

    @Test
    @Throws(IOException::class)
    fun emptyTest() {
        val map = Propop.loadPropsFromFile(tempFile)
        Assertions.assertEquals(0, map.size)
    }

    @Throws(IOException::class)
    private fun createSampleData(file: File) {
        val sampleProp = "foo=bar"
        Fileop.write(sampleProp, file)
    }
}

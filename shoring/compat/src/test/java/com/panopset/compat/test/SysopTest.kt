package com.panopset.compat.test

import com.panopset.compat.Fileop
import com.panopset.compat.Stringop
import com.panopset.compat.Sysop
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SysopTest {
    @Test
    @Throws(Exception::class)
    fun test() {
        Sysop.setSysProp(Stringop.FOO, Stringop.BAR)
        Sysop.setSysProp(Stringop.FOO, "")
        Sysop.setSysProp(Stringop.FOO, null)
        Assertions.assertEquals(Stringop.BAR, Sysop.getSysProp(Stringop.FOO))
        Fileop.touch(tempFile)
        Sysop.setSysProp(Stringop.FOO, Stringop.BAR)
        Assertions.assertEquals(Stringop.BAR, Sysop.getSysProp(Stringop.FOO))
        Sysop.setSysPropFromUrlFilePath(Stringop.FOO, tempFile.toURI().toURL())
        Assertions.assertEquals(tempFile.toURI().toURL().path, Sysop.getSysProp(Stringop.FOO))
        Sysop.setSysPropFromUrlFilePath(Stringop.FOO, null)
        Assertions.assertEquals(tempFile.toURI().toURL().path, Sysop.getSysProp(Stringop.FOO))
        Fileop.delete(tempFile)
    }
}

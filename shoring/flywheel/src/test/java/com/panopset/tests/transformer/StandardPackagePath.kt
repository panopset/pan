package com.panopset.tests.transformer

import com.panopset.compat.Fileop
import java.io.File

class StandardPackagePath(private val packageName: String?) {
    protected var packagePath: String? = null
        protected get() {
            if (field == null) {
                field = String.format("src/test/resources/%s", packageName!!.replace('.', '/'))
            }
            return field
        }
        private set

    fun getRezStr(fileName: String?): String {
        return Fileop.readTextFile(getFile(fileName))
    }

    fun getFile(fileName: String?): File {
        return File(String.format("%s/%s", packagePath, fileName))
    }
}

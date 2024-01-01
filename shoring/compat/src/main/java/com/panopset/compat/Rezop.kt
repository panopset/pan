package com.panopset.compat

import com.panopset.compat.Fileop.copyInputStreamToFile
import com.panopset.compat.Streamop.getTextFromStream
import com.panopset.compat.Stringop.stringToList
import java.io.File
import java.io.InputStream
import java.io.StringWriter

object Rezop {
    fun textStreamToList(panop: Panop, inpstr: InputStream): List<String> {
        val lines = getTextFromStream(panop, inpstr)
        return stringToList(panop, lines)
    }

    fun getPackageResourcePath(pkg: Package): String {
        val sw = StringWriter()
        sw.append("/")
        sw.append(pkg2path(pkg.name))
        return sw.toString()
    }

    fun pkg2path(dotName: String): String {
        return dotName.replace(".", "/")
    }

    fun copyTextResourceToFile(panop: Panop, clazz: Class<*>, resourcePath: String, file: File) {
        val inputStream = clazz.getResourceAsStream(resourcePath)
        if (inputStream == null) {
            Logop.warn(panop, "Couldn't find $resourcePath")
            return
        }
        copyInputStreamToFile(panop, inputStream, file)
    }

    fun copyTextResourceToFile(panop: Panop, clazz: Class<*>, resourcePath: String, fileName: String) {
        val inputStream = clazz.getResourceAsStream(resourcePath)
        if (inputStream == null) {
            Logop.warn(panop, "Couldn't find $resourcePath")
            return
        }
        copyInputStreamToFile(panop, inputStream, fileName)
    }

    fun pkg2resourcePath(clazz: Class<*>): String {
        val sw = StringWriter()
        sw.append("/")
        sw.append(clazz.getPackage().name.replace(".", "/"))
        sw.append("/")
        return sw.toString()
    }
}

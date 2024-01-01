package com.panopset.flywheel

import com.panopset.compat.Fileop.copyInputStreamToFile
import com.panopset.compat.Logop
import com.panopset.compat.Panop
import com.panopset.compat.Streamop.getTextFromStream
import com.panopset.compat.Stringop.stringToList
import java.io.File
import java.io.InputStream
import java.io.StringWriter

object Rezop {
    fun loadFromRez(panop: Panop, clazz: Class<*>, rezPath: String): String {
        val inputString = clazz.getResourceAsStream(rezPath) ?: return ""
        return getTextFromStream(panop, inputString)
    }

    fun loadListFromTextResource(panop: Panop, clazz: Class<*>, rezPath: String): List<String> {
        val inputStream = clazz.getResourceAsStream(rezPath) ?: return ArrayList()
        return textStreamToList(panop, inputStream)
    }

    fun loadListFromTextResource(panop: Panop, rezPath: String): List<String> {
        val inputStream = Rezop::class.java.getResourceAsStream(rezPath) ?: return ArrayList()
        return textStreamToList(panop, inputStream)
    }

    private fun textStreamToList(panop: Panop, inputStream: InputStream): List<String> {
        val lines = getTextFromStream(panop, inputStream)
        return stringToList(panop, lines)
    }

    private fun getResourceStream(clazz: Class<*>, resourcePath: String): InputStream? {
        return clazz.getResourceAsStream(resourcePath)
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
        val inputStream = getResourceStream(clazz, resourcePath)
        if (inputStream == null) {
            Logop.warn(panop, "Resource path $resourcePath not found.")
        } else {
            copyInputStreamToFile(panop, inputStream, file)
        }
    }

    fun copyTextResourceToFile(panop: Panop, clazz: Class<*>, resourcePath: String, fileName: String) {
        val inputStream = getResourceStream(clazz, resourcePath)
        if (inputStream == null) {
            Logop.warn(panop, "Resource path $resourcePath not found.")
        } else {
            copyInputStreamToFile(panop, inputStream, fileName)
        }
    }

    fun pkg2resourcePath(clazz: Class<*>): String {
        val sw = StringWriter()
        sw.append("/")
        sw.append(clazz.getPackage().name.replace(".", "/"))
        sw.append("/")
        return sw.toString()
    }
}

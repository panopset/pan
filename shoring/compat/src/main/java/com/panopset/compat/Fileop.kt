package com.panopset.compat

import com.panopset.compat.Logop.errorEx
import com.panopset.compat.Logop.errorMsg
import com.panopset.compat.Logop.warn
import com.panopset.compat.Streamop.copyChars
import com.panopset.compat.Streamop.getLinesFromReader
import com.panopset.compat.Streamop.streamToWriter
import com.panopset.compat.Stringop.getEol
import com.panopset.compat.Stringop.isBlank
import java.io.*
import java.nio.file.Files
import java.util.*
import kotlin.collections.ArrayList

object Fileop {

    val userHome = File(System.getProperty("user.home"))

    @JvmStatic
	@Throws(IOException::class)
    fun touch(panop: Panop, file: File) {
        if (prepFileForWriting(panop, file) && !file.createNewFile()) {
            warn(panop, String.format("Unable to create %s", getCanonicalPath(file)))
        }
    }

    fun getStandardPath(file: File): String {
        return getCanonicalPath(file).replace("\\", "/")
    }

    fun getParentDirectory(file: File): String {
        if (file.exists()) {
            val fullFile = File(getCanonicalPath(file))
            return getCanonicalPath(fullFile.parentFile)
        }
        return getCanonicalPath(file.parentFile)
    }

    fun fileExists(file: File?): Boolean {
        return if (file == null) {
            false
        } else {
            file.exists() && file.isFile && file.canRead()
        }
    }

    fun dirExists(dir: File): Boolean {
        return dir.exists() && dir.isDirectory && dir.canRead()
    }

    fun createTempFile(fileName: String): File {
        return File(combinePaths(TEMP_DIR_PATH, fileName))
    }

    fun write(panop: Panop, strs: Array<String>, file: File) {
        if (!prepFileForWriting(panop, file)) {
            return
        }
        try {
            FileWriter(file).use { fw ->
                BufferedWriter(fw).use { bw ->
                    for (s in strs) {
                        bw.write(s)
                        bw.write(getEol())
                    }
                }
            }
        } catch (ex: IOException) {
            errorEx(panop, ex)
        }
    }

    fun write(panop: Panop, strs: List<String>, file: File) {
        if (!prepFileForWriting(panop, file)) {
            return
        }
        try {
            FileWriter(file).use { fw ->
                BufferedWriter(fw).use { bw ->
                    for (s in strs) {
                        bw.write(s)
                        bw.write(getEol())
                    }
                }
            }
        } catch (ex: IOException) {
            errorEx(panop, ex)
        }
    }

	fun write(panop: Panop, str: String, file: File) {
        if (!prepFileForWriting(panop, file)) {
            return
        }
        try {
            FileWriter(file).use { fw -> BufferedWriter(fw).use { bw -> bw.write(str) } }
        } catch (ex: IOException) {
            errorEx(panop, ex)
        }
    }

    private fun prepFileForWriting(panop: Panop, file: File?): Boolean {
        if (file == null) {
            errorMsg(panop, "Can't write to a null file")
            return false
        }
        if (isBlank(file.path)) {
            errorMsg(panop, "Can't write to blank file path.")
            return false
        }
        val parentFile = file.parentFile
        if (parentFile == null) {
            errorMsg(panop, "Unable to write to file with no parent directory.")
            return false
        }
        file.parentFile.mkdirs()
        return true
    }

	fun getCanonicalPath(file: File?): String {
        if (file == null) {
            return ""
        }
        return file.canonicalPath
    }

    fun isFileOneOfExtensions(file: File?, extsToTry: String?): Boolean {
        return if (extsToTry != null && file != null) {
            val fileExtension = getExtension(file)
            if (extsToTry.contains(",")) {
                val exts = extsToTry.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (ext in exts) {
                    if (ext.trim { it <= ' ' }.equals(fileExtension, ignoreCase = true)) {
                        return true
                    }
                }
                false
            } else {
                getExtension(file).equals(extsToTry, ignoreCase = true)
            }
        } else {
            false
        }
    }

    @JvmStatic
	fun getExtension(fileName: String): String {
        val i = fileName.lastIndexOf(46.toChar())
        return if (i > 0) fileName.substring(i + 1) else ""
    }

    fun getExtension(file: File): String {
        return getExtension(file.getName())
    }

    fun combinePaths(dirPath: String, path: String): String {
        return combinePaths(File(dirPath), path)
    }

    fun combinePaths(dir: File, path: String): String {
        return String.format("%s%s%s", getCanonicalPath(dir), FSP, path)
    }

    fun readTextFile(panop: Panop, filePath: String): String {
        return readTextFile(panop, File(filePath))
    }

    fun readTextFile(panop: Panop, file: File): String {
        if (!file.exists()) {
            warn(panop, String.format("File %s doesn't exist.", getCanonicalPath(file)))
            return ""
        }
        val sw = StringWriter()
        try {
            copyChars(FileReader(file), sw)
        } catch (ex: IOException) {
            errorMsg(panop, file, ex)
        }
        return sw.toString()
    }

    @JvmStatic
	fun readLines(panop: Panop, file: File): List<String> {
        if (!file.exists()) {
            errorMsg(panop, "File does not exist", file)
            return ArrayList()
        }
        return try {
            getLinesFromReader(panop, FileReader(file))
        } catch (ex: FileNotFoundException) {
            errorEx(panop, ex)
            ArrayList()
        }
    }

    fun delete(panop: Panop, file: File?) {
        if (file == null) {
            return
        }
        if (file.isDirectory) {
            val files = file.listFiles()
            if (files != null) {
                for (f in files) {
                    delete(panop, f)
                }
            }
        }
        if (file.exists()) {
            try {
                Files.delete(file.toPath())
            } catch (e: IOException) {
                errorEx(panop, e)
            }
        }
    }

    fun mkdirs(panop: Panop, path: File) {
        if (!path.mkdirs()) {
            errorMsg(panop, String.format("%s %s", Nls.xlate("Unable to create path to"), getCanonicalPath(path)))
        }
    }

    fun copyInputStreamToFile(panop: Panop, inpst: InputStream, fileName: String) {
        copyInputStreamToFile(panop, inpst, File(fileName))
    }

    fun copyInputStreamToFile(panop: Panop, inpst: InputStream, file: File) {
        streamToWriter(panop, inpst, FileWriter(file))
    }

    fun moveFile(ff: File, tf: File) {
        Files.move(ff.toPath(), tf.toPath())
    }

    fun copyFile(panop: Panop, ff: File, tf: File) {
        try {
            Files.copy(ff.toPath(), tf.toPath())
        } catch (e: IOException) {
            errorEx(panop, e)
        }
    }

    val TEMP_DIRECTORY = File("$USH/temp")

    fun removeExtension(str: String): String {
        val i = str.lastIndexOf(".")
        return if (i > -1) {
            str.substring(0, i)
        } else str
    }

	fun checkParent(targetFile: File): Boolean {
        val parent = targetFile.parentFile
        return if (parent.exists()) {
            true
        } else {
            parent.mkdirs()
        }
    }

    fun loadProps(panop: Panop, fileName: String): Properties {
        return loadProps(panop, File(fileName))
    }

    fun loadProps(panop: Panop, file: File): Properties {
        val rtn = Properties()
        try {
            FileReader(file).use { fileReader ->
                BufferedReader(fileReader).use {
                    rtn.load(it)
                }
            }
        } catch (ex: Exception) {
            errorMsg(panop, file, ex)
        }
        return rtn
    }
}

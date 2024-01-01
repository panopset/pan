package com.panopset.lowerclass

import com.panopset.compat.ClassVersion
import com.panopset.compat.Fileop.getCanonicalPath
import com.panopset.compat.Fileop.getExtension
import com.panopset.compat.Logop.errorEx
import com.panopset.compat.Logop.errorMsg
import com.panopset.compat.Logop.green
import com.panopset.compat.Logop.warn
import com.panopset.compat.MajorVersion
import com.panopset.compat.Panop
import com.panopset.compat.Stringop.isPopulated
import java.io.*
import java.util.*
import java.util.jar.JarFile

internal class VersionMakeup {
    private val map: MutableMap<String, Map<MajorVersion, Int>> = Collections.synchronizedSortedMap(TreeMap())
    private fun clear() {
        map.clear()
        classReport = StringWriter()
    }

    fun analyze(panop: Panop, file: File, printDetails: Boolean) {
        clear()
        genRpt(panop, file.name, file, printDetails)
    }

    val report: String
        get() {
            val tp = StringWriter()
            for ((key, jvs) in map) {
                tp.append(key)
                tp.append(" > ")
                var firstTime = true
                for ((key1, value) in jvs) {
                    if (firstTime) {
                        firstTime = false
                    } else {
                        tp.append(",")
                    }
                    tp.append(key1.strRep)
                    tp.append(" $value")
                }
                tp.append("\n")
            }
            if (isPopulated(classReport.toString())) {
                tp.append("\n")
                tp.append("\n")
                tp.append(classReport.toString())
            }
            return tp.toString()
        }

    private fun genRpt(panop: Panop, fileName: String, file: File?, printDetails: Boolean) {
        if (file == null) {
            warn(panop, "No file selected.")
            return
        }
        if (!file.exists()) {
            errorMsg(panop, "File doesn't exist.", file)
            return
        }
        if (file.isDirectory) {
            genDirectoryReport(panop, fileName, file, printDetails)
        } else {
            val ext = getExtension(file.name)
            if ("class" == ext) {
                genClassReport(panop, fileName, file, printDetails)
            } else if ("jar" == ext) {
                genJarReport(panop, file, printDetails)
            } else {
                warn(panop, "Selected file is not a jar or class.")
                return
            }
        }
        green(panop, String.format("genReport complete for: %s", getCanonicalPath(file)))
    }

    private fun genDirectoryReport(panop: Panop, fileName: String, file: File, printDetails: Boolean) {
        val list = file.listFiles()
        if (list != null) {
            for (f in list) {
                if (f.isDirectory) {
                    genDirectoryReport(panop, fileName, f, printDetails)
                } else {
                    val ext = getExtension(f.name)
                    if ("class" == ext) {
                        genClassReport(panop, fileName, f, printDetails)
                    } else if ("jar" == ext) {
                        genJarReport(panop, f, printDetails)
                    }
                }
            }
        }
    }

    private fun genClassReport(panop: Panop, fileName: String, file: File, printDetails: Boolean) {
        updateReportMap(fileName, updateStatsForClass(panop, fileName, file, printDetails))
    }

    private fun genJarReport(panop: Panop, file: File, printDetails: Boolean) {
        green(panop, String.format("Processing jar: %s", file.name))
        updateReportMap(file.name, updateStatsForJar(panop, file, printDetails))
    }

    private fun updateReportMap(name: String, jvs: Map<MajorVersion, Int>?) {
        if (!jvs.isNullOrEmpty()) {
            map[name] = jvs
        }
    }

    private fun updateStatsForJar(panop: Panop, dirFile: File, printDetails: Boolean): Map<MajorVersion, Int> {
        val jvs = createVersionMap()
        try {
            JarFile(dirFile).use { jar ->
                val enumEntries = jar.entries()
                while (enumEntries.hasMoreElements()) {
                    val entry = enumEntries.nextElement()
                    val `is` = jar.getInputStream(entry)
                    try {
                        DataInputStream(`is`).use { dis ->
                            val cv = readClassVersion(panop, entry.name, dis, printDetails)
                            val count = jvs[cv.majorVersion]
                            if (count == null) {
                                jvs[cv.majorVersion] = 1
                            } else {
                                jvs[cv.majorVersion] = count + 1
                            }
                        }
                    } catch (ex: IOException) {
                        throw RuntimeException(ex)
                    }
                }
            }
        } catch (ex: IOException) {
            errorMsg(panop, getCanonicalPath(dirFile))
        }
        return jvs
    }

    private var classReport = StringWriter()
    private fun updateStatsForClass(
        panop: Panop,
        name: String, dirFile: File,
        printDetails: Boolean
    ): Map<MajorVersion, Int> {
        green(panop, String.format("Processing class: %s", name))
        val jvs = createVersionMap()
        try {
            FileInputStream(dirFile).use { fis ->
                DataInputStream(fis).use { dis ->
                    val cv = readClassVersion(panop, name, dis, printDetails)
                    jvs[cv.majorVersion] = 1
                }
            }
        } catch (ex: IOException) {
            errorEx(panop, ex)
        }
        return jvs
    }

    private fun readClassVersion(panop: Panop, name: String, dis: DataInputStream, printDetails: Boolean): ClassVersion {
        if (dis.available() > 0) {
            if ("class" == getExtension(name)) {
                val magic = dis.readInt()
                if (magic != -0x35014542) {
                    errorMsg(panop,
                        name + " is not a java class! this should be 0xcafebabe:"
                                + Integer.toHexString(magic)
                    )
                } else {
                    val minor = dis.readUnsignedShort()
                    val major = dis.readUnsignedShort()
                    val classVersion = ClassVersion(
                            MajorVersion.findFromHexString(Integer.toHexString(major)),
                            Integer.toHexString(minor)
                        )
                    if (printDetails) {
                        classReport.append(name)
                        classReport.append(" major: ")
                        classReport.append(classVersion.majorVersion.strRep)
                        classReport.append(" minor: ")
                        classReport.append(classVersion.minorVersion)
                        classReport.append("\n")
                    }
                    return classVersion
                }
            }
        }
        return ClassVersion(MajorVersion.UNDEFINED, "")
    }

    private fun createVersionMap(): MutableMap<MajorVersion, Int> {
        return Collections.synchronizedSortedMap(TreeMap())
    }
}

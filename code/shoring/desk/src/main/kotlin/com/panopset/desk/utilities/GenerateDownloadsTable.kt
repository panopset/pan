package com.panopset.desk.utilities

import com.google.gson.reflect.TypeToken
import com.panopset.compat.Fileop
import com.panopset.compat.Jsonop
import com.panopset.marin.secure.checksums.ChecksumType
import java.io.File
import java.io.StringWriter
import java.lang.reflect.Type
import java.util.*

class GenerateDownloadsTable {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // If testing from a Linux workstation:
            // GenerateDownloadsTable().createDownloadsTable("/var/www/html/downloads")
//            // If testing from a Windows workstation:
//            Fileop.write(
//            GenerateDownloadsTable().createDownloadsTable("${Stringop.USH}/Documents/foo/downloads"),
//                File("${Stringop.USH}/Documents/test.txt"))
            GenerateDownloadsTable().createDownloadsTable("/var/www/html/downloads")
        }
    }

    fun createDownloadsTable(path: String): String {
        val platformDownloadMap = createPlatformDownloadMap(path)
        val sw = StringWriter()
        sw.append("<table>")
        for (e in platformDownloadMap.entries) {
            sw.append("<td colspan=\"4\" class=\"menuBar\"><b>${e.key}</b></td>\n")
            sw.append("<tr><th>Type</th><th>Download</th><th>Bytes</th>")
            sw.append("<th>SHA-512</th></tr>")
            sw.append("</td></tr>")
            for (platformDownload in e.value.platformDownloads) {
                val artifactType = platformDownload.artifactType
                val artifactName = platformDownload.artifactName
                val relPath = platformDownload.relPath
                val byteCount = platformDownload.byteCount
                val sha512 = platformDownload.sha512

                sw.append("\n\n<tr><td nowrap>\n")
                sw.append(artifactType)
                sw.append("</td><td nowrap>\n")
                sw.append("<a href=\"/downloads/$relPath$artifactName\">$artifactName</a>")
                sw.append("</td><td>\n")
                sw.append(byteCount)
                sw.append("</td><td class=\"dsw99\"><input class=\"output2\" type=\"text\" value=\"")
                sw.append(sha512)
                sw.append("\"</input></td></tr>")

            }
            sw.append("<tr><td colspan=\"4\">&nbsp;</td></tr>")
        }
        sw.append("</table>\n\n")
        return sw.toString()
    }

    private fun createPlatformDownloadMap(path: String): Map<String, PlatformDownloadCollection> {
        val platformDownloadMap = Collections.synchronizedSortedMap<String, PlatformDownloadCollection>(TreeMap())
        val tempDirDownloads = File(path)
        for (f in tempDirDownloads.listFiles()!!) {
            if (f.isFile && f.extension == "json") {
                var artifactType: String
                var relPath = ""
                val i = f.name.indexOf("OneJar")
                if (i > -1) {
                    artifactType = "jar"
                    relPath = "${f.name.substring(0, i)}/"
                } else {
                    artifactType = "installer"
                }
                val jsonStr = Fileop.readTextFile(f)
                val mapType: Type = object : TypeToken<HashMap<String, String>>() {}.type
                val rawMap = Jsonop<HashMap<String, String>>().fromJson(jsonStr, mapType)
                val map = Collections.synchronizedSortedMap(TreeMap<String, String>())
                for (e in rawMap) {
                    map[e.key] = e.value
                }
                val ifn = map["ifn"] ?: return platformDownloadMap
                val platform = map["platform"] ?: return platformDownloadMap
                val bytes = map["bytes"] ?: return platformDownloadMap
                val sha512 = map[ChecksumType.SHA512.key] ?: return platformDownloadMap
                addPlatformIfNecessary(platform, platformDownloadMap).platformDownloads.add(
                    PlatformDownload(artifactType, ifn, relPath, bytes, sha512)
                )
            }
        }
        return platformDownloadMap
    }
}

private fun addPlatformIfNecessary(
    key: String,
    platformDownloadCollectionMap: MutableMap<String, PlatformDownloadCollection>
): PlatformDownloadCollection {
    if (platformDownloadCollectionMap.containsKey(key)) {
        return platformDownloadCollectionMap[key]!!
    }
    val rtn = PlatformDownloadCollection(key)
    platformDownloadCollectionMap[key] = rtn
    return rtn
}

private data class PlatformDownload(
    val artifactType: String,
    val artifactName: String,
    val relPath: String,
    val byteCount: String,
    val sha512: String
) : Comparable<PlatformDownload> {
    override fun compareTo(other: PlatformDownload): Int {
        return artifactType.compareTo(other.artifactType)
    }
}

private data class PlatformDownloadCollection(val platformName: String) {
    val platformDownloads: SortedSet<PlatformDownload> = Collections.synchronizedSortedSet(TreeSet())
}

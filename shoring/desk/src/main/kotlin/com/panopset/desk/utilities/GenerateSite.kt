package com.panopset.desk.utilities

import com.google.gson.reflect.TypeToken
import com.panopset.compat.*
import com.panopset.flywheel.FlywheelBuilder
import com.panopset.marin.secure.checksums.ChecksumType
import java.io.File
import java.io.StringWriter
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.HashMap


class GenerateSite {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            GenerateSite().go(args)
        }
    }

    private fun go(args: Array<String>) {
        val panopsetSiteDomainName = DevProps.getSiteDomainName()
        val blurb = if (panopsetSiteDomainName == "panopset.com") {
            ""
        } else {
            "<h1>Prototype</h1>This is a prototype for the next release of <a href=\"https://panopset.com\">panopset.com</a>."
        }
        FlywheelBuilder().file(File(args[0])).targetDirectory(File(args[1]))
            .map("previewBlurb", blurb)
            .map("downloadsTable", createDownloadsTable())
            .map("appVersion", AppVersion.getVersion())
            .map("buildNumber", AppVersion.getBuildNumber())
            .map(props2map(Fileop.loadProps(File("deploy.properties"))))
            .construct().exec()
    }

    private fun createDownloadsTable(): String {
        val platformDownloadMap = createPlatformDownloadMap()
        val sw = StringWriter()
        for (e in platformDownloadMap.entries) {
            sw.append("<h2>${e.key}</h2>")
            sw.append("<table>")
            sw.append("<tr><th>Type</th><th>Download</th><th>Bytes</th>")
            sw.append("<th>SHA-512</th></tr>")
            sw.append("</td></tr>")
            for (platformDownload in e.value.platformDownloads) {
                val artifactType = platformDownload.artifactType
                val artifactName = platformDownload.artifactName
                val byteCount = platformDownload.byteCount
                val sha512 = platformDownload.sha512

                sw.append("<tr><td nowrap>\n")
                sw.append(artifactType)
                sw.append("</td><td>\n")
                sw.append("<a href=\"/downloads/$artifactName$\">$artifactName</a>")
                sw.append("</td><td>\n")
                sw.append(byteCount)
                sw.append("</td><td class=\"dsw99\"><input class=\"output2\" type=\"text\" value=\"\n")
                sw.append(sha512)
                sw.append("\"</input></td></tr>")

            }
            sw.append("</table>")
        }
        return sw.toString()
    }

    private fun createPlatformDownloadMap(): Map<String, PlatformDownloadCollection> {
        val platformDownloadMap = Collections.synchronizedSortedMap<String, PlatformDownloadCollection>(TreeMap())
        val tempDirDownloads = File("/var/www/html/downloads")
        for (f in tempDirDownloads.listFiles()!!) {
            if (f.isFile && f.extension == "json") {
                val artifactType = if (f.name.indexOf("panopset.jar") > -1) {
                    "jar"
                } else {
                    "installer"
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
                    PlatformDownload(artifactType, ifn, bytes, sha512)
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

package com.panopset.desk.utilities

import com.google.gson.reflect.TypeToken
import com.panopset.compat.*
import com.panopset.flywheel.FlywheelBuilder
import com.panopset.marin.secure.checksums.ChecksumType
import java.io.File
import java.io.StringWriter
import java.lang.reflect.Type
import java.util.Collections
import java.util.TreeMap


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
        val sw = StringWriter()
        val tempDirDownloads = File("/var/www/html/downloads")
        for (f in tempDirDownloads.listFiles()!!) {
            if (f.isFile && f.extension == "json") {
                val jsonStr = Fileop.readTextFile(f)
                val mapType: Type = object : TypeToken<HashMap<String, String>>() {}.type
                val rawMap = Jsonop<HashMap<String, String>>().fromJson(jsonStr, mapType)
                val map = Collections.synchronizedSortedMap(TreeMap<String, String>())
                for (e in rawMap) {
                    map[e.key] = e.value
                }
                val ifn = map["ifn"]
                val platform = map["platform"]
                val bytes = map["bytes"]
                val sha512 = map[ChecksumType.SHA512.key]
                sw.append("<tr><td nowrap>\n")
                sw.append(platform)
                sw.append("</td><td>\n")
                sw.append("<a href=\"/downloads/$ifn\">$ifn</a>")
                sw.append("</td><td>\n")
                sw.append(bytes)
                sw.append("</td><td class=\"dsw99\"><input class=\"output2\" type=\"text\" value=\"\n")
                sw.append(sha512)
                sw.append("\"</input></td></tr>")
            }
        }
        return sw.toString()
    }
}

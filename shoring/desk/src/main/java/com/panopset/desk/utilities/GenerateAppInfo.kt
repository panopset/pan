package com.panopset.desk.utilities

import com.panopset.compat.*
import com.panopset.compat.Fileop.checkParent
import com.panopset.compat.Stringop.USH
import com.panopset.marin.bootstrap.PlatformMap
import com.panopset.marin.secure.checksums.ChecksumType
import java.io.File

class GenerateAppInfo {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            GenerateAppInfo().go()
        }
    }

    private fun go() {
        val tempDownloadsDirPath = "${USH}/temp/downloads"
        val tempDownloadsDir = File(tempDownloadsDirPath)
        val tempDownloadsDirFileList = tempDownloadsDir.listFiles()
        if (tempDownloadsDirFileList == null) {
            Logop.errorMsg("Directory $tempDownloadsDirPath is empty, exiting.")
            return
        }
        for (file in tempDownloadsDirFileList) {
            if (file.extension == "json") {
                continue
            }
            generateJsonFor(file)
        }
    }
}



fun generateJsonFor(file: File) {
    val name = file.name
    for (platform in PlatformMap.map.values) {
        if (name == platform.artifactName) {
            val targetFile = File("/var/www/html/downloads/pci_$name.json")
            checkParent(targetFile)
            val json = Jsonop<Map<String, String>>().toJson(createList(platform.platformName, file))
            Fileop.write(json, targetFile)
        }
    }
}


fun createList(platformName: String, installerFile: File): Map<String, String> {
    val map = HashMap<String, String>()
    map["platform"] = platformName
    map["version"] = AppVersion.getVersion()
    map["buildNumber"] = AppVersion.getBuildNumber()
    map["bytes"] = byteCount(installerFile) ?: ""
    map[ChecksumType.SHA512.key] = sha512(installerFile) ?: ""
    map["ifn"] = installerFile.name
    return map
}

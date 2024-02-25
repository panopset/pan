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
            .map("downloadsTable", GenerateDownloadsTable().createDownloadsTable("/var/www/html/downloads"))
            .map("appVersion", AppVersion.getVersion())
            .map("buildNumber", AppVersion.getBuildNumber())
            .map(props2map(Fileop.loadProps(File("deploy.properties"))))
            .construct().exec()
    }
}

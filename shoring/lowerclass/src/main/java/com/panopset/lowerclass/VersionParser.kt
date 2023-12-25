package com.panopset.lowerclass

import com.panopset.compat.Logop.dspmsg
import com.panopset.compat.StatusListener
import com.panopset.compat.Stringop.USH
import java.io.File
import java.io.IOException

class VersionParser : StatusListener {
    constructor()

    constructor(pathToJar_or_directoryToTraverse: String) : this(File(pathToJar_or_directoryToTraverse))

    constructor(jar_or_directoryToTraverse: File) {
        file = jar_or_directoryToTraverse
    }

    private var mavenHome: String = ""
        get() {
            if (field.isEmpty()) {
                field = System.getenv()["M2_HOME"]?:""
                if (field.isEmpty()) {
                    field = System.getenv()["MAVEN_HOME"]?:""
                }
                if (field.isEmpty()) {
                    field = DEFAULT_MAVEN_HOME
                }
            }
            return field
        }

    private var file: File = File(mavenHome)

    @get:Throws(IOException::class)
    var summaryReport: String? = null
        get() {
            if (field == null) {
                field = createReport(false)
            }
            return field
        }
        private set

    private var detailReport: String? = null

    @get:Throws(IOException::class)
    val detailedReport: String?
        get() {
            if (detailReport == null) {
                detailReport = createReport(true)
            }
            return detailReport
        }

    @Throws(IOException::class)
    private fun createReport(printDetails: Boolean): String {
        val vm = VersionMakeup()
        vm.analyze(file!!, printDetails)
        return vm.report
    }

    companion object {
        var DEFAULT_MAVEN_HOME: String = "$USH/.m2"
        private var PANOPSET_JAR: String = "$USH/panopset.jar"

        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            dspmsg("*** Entire repository example:")
            dspmsg(VersionParser().summaryReport)
            dspmsg("*** Single jar example:")
            dspmsg(VersionParser(PANOPSET_JAR).detailedReport)
        }
    }

    override fun update(message: String?) {
        dspmsg(message)
    }
}

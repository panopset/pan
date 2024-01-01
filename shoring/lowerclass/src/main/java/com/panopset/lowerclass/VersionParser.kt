package com.panopset.lowerclass

import com.panopset.compat.Logop.dspmsg
import com.panopset.compat.Panop
import com.panopset.compat.StatusListener
import com.panopset.compat.USH
import java.io.File

class VersionParser : StatusListener {
    constructor()

    constructor(jarOrDirectoryToTraverse: File) {
        file = jarOrDirectoryToTraverse
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

    var summaryReport: String = createReport(panop, false)
    var detailReport: String = createReport(panop, true)

    private fun createReport(panop: Panop, printDetails: Boolean): String {
        val vm = VersionMakeup()
        vm.analyze(panop, file, printDetails)
        return vm.report
    }

    companion object {
        var DEFAULT_MAVEN_HOME: String = "$USH/.m2"

        private val panop = object: Panop {

        }
        @JvmStatic
        fun main(args: Array<String>) {
            dspmsg(panop, "*** Entire repository example:")
            dspmsg(panop, VersionParser().summaryReport)
        }
    }

    override fun update(panop: Panop, message: String) {
        dspmsg(panop, message)
    }
}

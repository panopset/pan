package com.panopset.marin.bootstrap

import com.panopset.compat.Fileop
import com.panopset.compat.Logop.errorMsg
import com.panopset.compat.Logop.info
import com.panopset.compat.Stringop.isBlank
import com.panopset.compat.Stringop.isPopulated
import com.panopset.compat.Stringop.replaceFirstLinePreserveIndentation
import com.panopset.desk.DeployProperties
import com.panopset.flywheel.FlywheelBuilder
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class VersionClassGenerator(private var srcDirectory: String, private var versionString: String) {
    private fun updateVersion() {
        if (updateAppVersionClass()) {
            updatePoms()
        }
    }

    private fun updatePoms() {
        updatePom("$srcDirectory/shoring/pom.xml")
        updateProject("compat")
        updateProject("blackjackEngine")
        updateProject("lowerclass")
        updateProject("flywheel")
        updateProject("fxapp")
        updateProject("desk")
    }

    private fun updateProject(project: String) {
        val pp = String.format("$srcDirectory/shoring/%s/pom.xml", project)
        updatePom(pp)
    }

    private fun updatePom(pp: String) {
        val fr = "<version>"
        val tm = "<version>%s</version>"
        val vr = getVersionString()
        replaceFirstLine(pp, fr, String.format(tm, vr))
    }

    private fun replaceFirstLine(
        path: String, lineToReplaceContaining: String,
        fullReplacementLine: String
    ) {
        val file = File(path)
        val source = Fileop.readLines(file)
        val strs = replaceFirstLinePreserveIndentation(
            source,
            lineToReplaceContaining, fullReplacementLine
        )
        if (source == strs) {
            info(String.format("No changes to %s, skipping...", path))
        } else {
            Fileop.write(strs, file)
            info(String.format("%s, updated with %s.", path, fullReplacementLine))
        }
    }

    private fun updateAppVersionClass(): Boolean {
        val vf = File("$srcDirectory/shoring/compat/src/main/java/com/panopset/compat/AppVersion.kt")
        if (!vf.parentFile.exists()) {
            errorMsg("Parent directory not found", vf)
            return false
        }
        val result = FlywheelBuilder()
            .scriptFilePath(
                "$srcDirectory/docs/templates/version.tmplt"
            )
            .map("panopset_desk_version", getVersionString())
            .map("panopset_desk_build", getBuildString())
            .construct().exec()
        if (isPopulated(result)) {
            Fileop.write(result, vf)
        }
        return true
    }

    private fun getBuildString(): String {
        return SimpleDateFormat("yyyyMMddHHmm").format(Date())
    }

    private fun getVersionString(): String {
        return versionString
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            VersionClassGenerator(args[0], DeployProperties.getPanopsetVersion()).updateVersion()
        }
    }
}

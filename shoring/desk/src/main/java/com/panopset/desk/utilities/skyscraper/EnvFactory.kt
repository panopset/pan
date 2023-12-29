package com.panopset.desk.utilities.skyscraper

import com.panopset.compat.Fileop
import com.panopset.compat.HiddenFolder
import com.panopset.compat.Logop
import com.panopset.compat.Stringop
import com.panopset.compat.Stringop.FSP
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.collections.HashMap

fun loadEnvironments(envMap: MutableMap<String, MutableMap<String, String>>) {
    val baseDirectory = establishBaseDirectory()
    val listOfFiles = baseDirectory.listFiles() ?: return
    envMap.clear()
    for (prefFile in listOfFiles) {
        if (validExtensions(prefFile.extension)) {
            val props = Properties()
            props.load(BufferedInputStream(FileInputStream(prefFile)))
            envMap[prefFile.nameWithoutExtension] = props2map(props)
        }
    }
}

fun props2map(props: Properties): MutableMap<String, String> {
    val rtn = HashMap<String, String>()
    for (e in props.entries) {
        rtn[e.key.toString()] = e.value.toString()
    }
    return rtn
}

private fun validExtensions(extension: String): Boolean {
    val lcx = extension.lowercase()
    return when (lcx) {
        "" -> true
        "txt" -> true
        "properties" -> true
        "preferences" -> true
        else -> false
    }
}

fun establishBaseDirectory(): File {
    val userDefinedBaseDir = System.getenv()[PANOPSET_SKYSCRAPER_ENVS]
    if (!userDefinedBaseDir.isNullOrEmpty()) {
        val userDir4envs = File(userDefinedBaseDir)
        if (userDir4envs.exists()) {
            if (userDir4envs.isDirectory) {
                if (userDir4envs.canRead()) {
                    return userDir4envs
                } else {
                    Logop.warn(
                        "$PANOPSET_SKYSCRAPER_ENVS defined directory " +
                                Fileop.getCanonicalPath(userDir4envs) +
                                " can not be read, please check permissions. Reverting to default."
                    )
                }
            } else {
                Logop.warn(
                    "$PANOPSET_SKYSCRAPER_ENVS defined directory " +
                            Fileop.getCanonicalPath(userDir4envs) +
                            " is not a directory. Reverting to default."
                )
            }
        } else {
            Logop.warn(
                "$PANOPSET_SKYSCRAPER_ENVS defined directory " +
                        Fileop.getCanonicalPath(userDir4envs) +
                        " does not exist. Reverting to default."
            )
        }
    }
    return defaultBaseDir
}

const val PANOPSET_SKYSCRAPER_ENVS = "PANOPSET_SKYSCRAPER_ENVS"

val defaultBaseDir = File(HiddenFolder.getFullPathRelativeTo("skyscraper${FSP}envs"))

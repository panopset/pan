package com.panopset.gp

import com.panopset.compat.*
import java.io.File

class GlobalReplaceProcessor(
    private val file: File,
    private val searchStr: String,
    private val replacementStr: String,
    private val extensionsList: String,
    private val recursive: Boolean
) {
    var priorLineMustContain: String = ""
    var replacementLineMustContain: String = ""

    fun process() {
        if (!Stringop.isPopulated(searchStr)) {
            Logop.warn("No replacement specified, exiting.")
            return
        }
        val searchList = Stringop.stringToList(searchStr)
        val replaceList = Stringop.stringToList(replacementStr)
        while (replaceList.size < searchList.size) {
            replaceList.add("")
        }
        if (searchList.size != replaceList.size) {
            Logop.warn("Search (${searchList.size}) and replace list (${replaceList.size}) sizes do not match.")
            return
        }
        if (searchList.size < 1) {
            Logop.warn("No search specified.")
            return
        }
        val byFileFilter: ByFileFilter = object: ByFileFilter {
            override fun fileFilter(file: File): Boolean {
                if (file.isDirectory) {
                    Logop.warn("Ignoring directory: ${Fileop.getCanonicalPath(file)}")
                    return false
                }
                Logop.debug("Processing ${file.canonicalPath}")
                return !(Stringop.isPopulated(extensionsList) && !Fileop.isFileOneOfExtensions(file, extensionsList))
            }
        }
        val byLineFilter: ByLineFilter = object: ByLineFilter {
            var priorLine: String = ""
            override fun filter(str: String): FilteredString {
                var rtn = str
                if (Stringop.isPopulated(priorLineMustContain)) {
                    if (priorLine.contains(priorLineMustContain)) {
                        rtn = doReplacements(searchList, replaceList, str)
                    }
                } else {
                    rtn = doReplacements(searchList, replaceList, str)
                }
                priorLine = str
                return FilteredString(rtn)
            }
        }
        FileProcessor(file, recursive).withFileFilter(byFileFilter).withLineFilter(byLineFilter).exec()
        Logop.green("Done")
    }
}

fun doReplacements(searchStrs: List<String>, replacementStrs: List<String>, fromStr: String): String {
    var rtn = fromStr
    for (i in searchStrs.indices) {
        rtn = doReplacement(searchStrs[i], replacementStrs[i], rtn)
    }
    return rtn
}

private fun doReplacement(searchStr: String, replacementStr: String, fromStr: String): String {
    return fromStr.replace(searchStr, replacementStr)
}

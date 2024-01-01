package com.panopset.compat

object HiddenFolder {

    var companyName = "should_have_been_replaced_by_BrandedApp"
    var rootLogFileName = "root"

    fun getFullPathRelativeTo(relativePathToAppend: String): String {
        return "$USH${FSP}Documents$FSP$companyName$FSP$relativePathToAppend"
    }
}

package com.panopset.compat

/**
 * See deploy.properties comments for version upgrade procedure.
 *
 * This class is generated from version.tmplt.
 */
object AppVersion {

    fun getVersion(): String {
        return "1.2.7"
    }

    fun getBuildNumber(): String {
        return "202312280921"
    }

    @JvmStatic
    fun main(vararg args: String?) {
        println(getVersion())
    }
}

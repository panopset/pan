package com.panopset.compat

/**
 * See deploy.properties comments for version upgrade procedure.
 *
 * This class is generated from version.tmplt.
 */
object AppVersion {

    fun getVersion(): String {
        return "1.2.9"
    }

    fun getBuildNumber(): String {
        return "202401010706"
    }

    @JvmStatic
    fun main(vararg args: String?) {
        println(getVersion())
    }
}

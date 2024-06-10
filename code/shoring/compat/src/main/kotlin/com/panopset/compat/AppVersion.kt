package com.panopset.compat

/**
 * See deploy.properties comments for version upgrade procedure.
 *
 * This class is generated from version.tmplt.
 */
object AppVersion {

    fun getVersion(): String {
        return "1.3.8"
    }

    fun getBuildNumber(): String {
        return "202406091301"
    }

    @JvmStatic
    fun main(vararg args: String?) {
        if (args.isNotEmpty()) {
            Logz.warn("AppVersion called with $args")
        }
        println(getVersion())
    }
}
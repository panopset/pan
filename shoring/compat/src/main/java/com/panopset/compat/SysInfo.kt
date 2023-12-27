package com.panopset.compat

import com.panopset.compat.Logop.errorEx
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*

/**
 * General system information.
 *
 */
object SysInfo {
    val map = HashMap<String, String>()

    init {
        var name = "Un-able to determine, see log."
        try {
            name = InetAddress.getLocalHost().getCanonicalHostName()
        } catch (e: UnknownHostException) {
            errorEx(e)
        }
        map["Name"] = name
        map["Processors"] = "" + Runtime.getRuntime().availableProcessors()
        map["Total memory"] = "" + Runtime.getRuntime().totalMemory()
        map["Max memory"] = "" + Runtime.getRuntime().maxMemory()
        map["Free memory"] = "" + Runtime.getRuntime().freeMemory()
        map["Operating system"] = (System.getProperty("os.name") + " "
                + System.getProperty("sun.os.patch.level"))
        map["CPU"] = (System.getProperty("os.arch") + "," + "" + ""
                + System.getProperty("sun.arch.data.model"))
        map["CPU compatibility"] = System.getProperty("sun.cpu.isalist")
        map[Nls.xlate("Language")] = Locale.getDefault().language
    }
}

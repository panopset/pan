package com.panopset.compat

import com.panopset.compat.Logop.errorEx
import java.io.StringWriter
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*

/**
 * General system information.
 *
 */
object SysInfo {
    val map: SortedMap<String, String> = Collections.synchronizedSortedMap(TreeMap())

    init {
        var name = "Un-able to determine, see log."
        try {
            name = InetAddress.getLocalHost().canonicalHostName
        } catch (e: UnknownHostException) {
            errorEx(e)
        }
        val runtime = Runtime.getRuntime()
        addValueToMapIfExists(map, "HostName", name)
        addValueToMapIfExists(map, "Processors", "${runtime.availableProcessors()}")
        addValueToMapIfExists(map, "Total memory", "${runtime.totalMemory()}")
        addValueToMapIfExists(map, "Max memory", "${runtime.maxMemory()}")
        addValueToMapIfExists(map, "Free memory", "${runtime.freeMemory()}")
        addSysPropValueToMapIfExists(map, "java.version")
        addSysPropValueToMapIfExists(map, "os.name")
        addSysPropValueToMapIfExists(map, "sun.os.patch.level")
        addSysPropValueToMapIfExists(map, "os.arch")
        addSysPropValueToMapIfExists(map, "sun.arch.data.model")
        addSysPropValueToMapIfExists(map, "sun.cpu.isalist")
        addValueToMapIfExists(map, "Language", Locale.getDefault().language)
    }

    override fun toString(): String {
        return map2string(map)
    }
}

fun addSysPropValueToMapIfExists(map: SortedMap<String, String>, key: String) {
    addValueToMapIfExists(map, key, System.getProperty(key)?: "")
}

fun addValueToMapIfExists(map: SortedMap<String, String>, key: String, value: String) {
    if (value.isBlank()) {
        return
    }
    map[key] = value
}

fun map2string(map: Map<String, String>): String {
    val sw = StringWriter()
    for (entry in map) {
        sw.append(entry.key)
        sw.append("=")
        sw.append(entry.value)
        sw.append("\n")
    }
    return sw.toString()
}

package com.panopset.compat

import java.io.IOException

interface PersistentMap {
    fun put(key: String, value: String)

    operator fun get(key: String): String

    operator fun get(key: String, dft: String): String
}

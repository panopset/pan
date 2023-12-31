package com.panopset.compat

interface MapProvider {
    operator fun get(key: String): String
}

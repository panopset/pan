package com.panopset.compat

interface ProcessListener {
    fun setText(value: String)
    fun append(value: String)
    fun reset()
}

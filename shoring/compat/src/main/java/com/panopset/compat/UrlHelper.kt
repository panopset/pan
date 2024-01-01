package com.panopset.compat

fun getTextFromURL(panop: Panop, urlStr: String): String {
    return doGetHttp(panop, urlStr).text
}
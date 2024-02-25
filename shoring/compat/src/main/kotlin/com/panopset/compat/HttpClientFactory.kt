package com.panopset.compat

import java.io.*
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

var timeout = 5000

fun doGetHttp(urlStr: String): HttpResponsePackage {
    val con = HttpClientFactory().withUrlString(urlStr).constructGetConnection()
    return doGetHttp(con)
}

fun doGetHttp(headers: MutableMap<String, String>, urlStr: String): HttpResponsePackage {
    val con = HttpClientFactory().withHeaders(headers).withUrlString(urlStr).constructGetConnection()
    return doGetHttp(con)
}

fun doPostHttp(headers: MutableMap<String, String>, urlStr: String, data: String): HttpResponsePackage {
    val con = HttpClientFactory().withHeaders(headers).withUrlString(urlStr).constructPostConnection()
    return doPostHttp(con, data)
}

private fun doGetHttp(con: HttpURLConnection): HttpResponsePackage {
    return readResponseFromConnection(con)
}

private fun doPostHttp(con: HttpURLConnection, dta: String): HttpResponsePackage {
    val osw = OutputStreamWriter(con.outputStream)
    osw.write(dta)
    osw.flush()
    return readResponseFromConnection(con)
}

fun establishPostConnection(newURL: URL): HttpURLConnection {
    return HttpClientFactory().withUrl(newURL).constructPostConnection()
}

private class HttpClientFactory {
    private val headers = HashMap<String, String>()
    private var urlStr = ""
    private lateinit var url: URL

    private fun addHeader(key: String, value: String) {
        if (this.headers.contains((key))) {
            Logz.warn("Replacing ${key}:${this.headers[value]} with ${headers[key]}!")
        }
        headers[key] = value
    }

    fun withHeaders(headers: MutableMap<String, String>): HttpClientFactory {
        addHeaders(headers)
        return this
    }

    fun addHeaders(headers: MutableMap<String, String>) {
        for (e in headers.entries) {
            addHeader(e.key, e.value)
        }
    }

    fun withUrlString(urlStr: String): HttpClientFactory {
        setInitialUrlStringValue(urlStr)
        this.url = URI(urlStr).toURL()
        return this
    }

    fun withUrl(url: URL): HttpClientFactory {
        this.url = url
        setInitialUrlStringValue(url.toString())
        return this
    }

    private fun setInitialUrlStringValue(urlStr: String) {
        if (this.urlStr.isNotEmpty()) {
            throw RuntimeException(standardWierdErrorMessage)
        }
        this.urlStr = urlStr
    }

    fun constructPostConnection(): HttpURLConnection {
        addHeader(USER_AGENT, "Mozilla/4.0")
        addHeader(CONTENT_TYPE, "application/json")
        val rtn = constructConnection()
        rtn.requestMethod = "POST"
        rtn.doInput = true
        rtn.doOutput = true
        rtn.useCaches = false
        return rtn
    }

    fun constructGetConnection(): HttpURLConnection {
        addHeader(CONTENT_TYPE, "text/html; charset=utf-8")
        val rtn = constructConnection()
        rtn.requestMethod = "GET"
        return rtn
    }

    private fun constructConnection(): HttpURLConnection {
        val rtn = url.openConnection() as HttpURLConnection
        rtn.connectTimeout = timeout
        for (e in headers.entries) {
            rtn.setRequestProperty(e.key, e.value);
        }
        return rtn
    }
}

private fun readResponseFromConnection(con: HttpURLConnection): HttpResponsePackage {
    val url = con.url ?: return createEmptyURLResponse()
    val urlStr = url.toString()
    if (urlStr.isEmpty()) return createEmptyURLResponse()
    var conResponseCode = -1
    try {
        val ins = con.inputStream
        val limit = Runtime.getRuntime().freeMemory() * .80
        conResponseCode = con.responseCode
        if (conResponseCode != 200) {
            return HttpResponsePackage(conResponseCode, "Error occurred.", con.responseMessage)
        }
        val isr = InputStreamReader(ins)
        val bfr = BufferedReader(isr)
        val sw = StringWriter()
        var s = bfr.readLine()
        var totalSize = 0
        while (s != null && (totalSize < limit)) {
            totalSize += s.length
            if (s.length > limit) {
                return HttpResponsePackage(conResponseCode, constrain(s), "Exceeded 80% of available memory: $limit")
            }
            sw.append(s)
            sw.append("\n")
            s = bfr.readLine()
        }
        bfr.close()
        return HttpResponsePackage(conResponseCode, sw.toString())
    } catch (t: Throwable) {
        return createErrorResponse(conResponseCode, t, urlStr)
    } finally {
        con.disconnect()
    }
}

private fun createEmptyURLResponse(): HttpResponsePackage {
    return createErrorResponse(400, "URL is empty")
}

private fun createErrorResponse(responseCode: Int, errorMessage: String): HttpResponsePackage {
    Logz.warn("$responseCode: $errorMessage")
    return HttpResponsePackage(responseCode, errorMessage)
}

fun createErrorResponse(responseCode: Int, throwable: Throwable, newURL: String): HttpResponsePackage {
    val errorMessage = "$responseCode: $newURL \n\n ${getStackTracelg(throwable)}"
    Logz.errorMsg(errorMessage)
    return HttpResponsePackage(responseCode, errorMessage)
}

fun constrain(s: String): String {
    return "${s.substring(0, 20)} \n\n Response was too big to handle."
}

private const val USER_AGENT = "User-Agent"
private const val CONTENT_TYPE = "Content-Type"
private const val EMPTY_URL_MSG = "No URL provided, nothing to do."

package com.panopset.compat

import java.io.*
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

var timeout = 5000

fun doGetHttp(urlStr: String): HttpResponsePackage {
    return try {
        doGetHttp(URI(urlStr).toURL())
    } catch (t: Throwable) {
        Logop.errorMsg(urlStr, t)
        HttpResponsePackage(400, Logop.getStackTrace(t), t.localizedMessage)
    }
}

fun doGetHttp(newURL: URL): HttpResponsePackage {
    val rtn: MutableList<String> = ArrayList()
    val con = establishGetConnection(newURL)
    var conResponseCode = -1
    try {
        conResponseCode = con.responseCode
        val isr = InputStreamReader(con.inputStream)
        val br = BufferedReader(isr)
        var count = 0
        var s = br.readLine()
        while (s != null && count++ < limit) {
            if (s.length > limit) {
                closeConnection(con)
                rtn.add(constrain(s.substring(0, limit - 1)))
                return HttpResponsePackage(conResponseCode, Stringop.listToString(rtn), "")
            }
            rtn.add(s)
            s = br.readLine()
        }
        closeConnection(con)
    } catch (ex: Throwable) {
        Logop.warn("Error getting response: $newURL, ${ex.message}")
        return HttpResponsePackage(conResponseCode, Logop.getStackTrace(ex), ex.localizedMessage)
    }
    return HttpResponsePackage(conResponseCode, Stringop.listToString(rtn))
}

// TODO: Let's have a look at memory allocation to the app.
fun constrain(s: String): String {
    return "$s \n\n This is not a browser, response was too big to handle."
}

fun doPostHttp(urlStr: String, body: String): HttpResponsePackage {
    return try {
        doPostHttp(URI(urlStr).toURL(), body)
    } catch (t: Throwable) {
        Logop.errorMsg(urlStr, t)
        HttpResponsePackage(400, Logop.getStackTrace(t), t.localizedMessage)
    }
}

const val limit = 1000

fun doPostHttp(newURL: URL, dta: String): HttpResponsePackage {
    val con = establishPostConnection(newURL)
    var conResponseCode = -1
    try {
        val osw = OutputStreamWriter(con.outputStream)
        osw.write(dta)
        osw.flush()
        conResponseCode = con.responseCode
        val ins = con.inputStream
        val isr = InputStreamReader(ins)
        val bfr = BufferedReader(isr)
        val sw = StringWriter()
        var count = 0
        var s = bfr.readLine()
        while (s != null && count++ < limit) {
            if (s.length > limit) {
                closeConnection(con)
                sw.append(constrain(s))
                return HttpResponsePackage(conResponseCode, sw.toString(), "")
            }
            sw.append(s)
            sw.append("\n")
            s = bfr.readLine()
        }
        bfr.close()
        closeConnection(con)
        return HttpResponsePackage(conResponseCode, sw.toString())
    } catch (ex: Throwable) {
        Logop.warn("Error getting response: $newURL, ${ex.message}")
        return HttpResponsePackage(conResponseCode, Logop.getStackTrace(ex), ex.localizedMessage)
    }
}

fun url2file(f: File) {
    f.getParentFile().mkdirs()
    try {
        InputStreamReader(establishGetConnection(f.toURI().toURL()).inputStream
        ).use { isr ->
            BufferedReader(isr).use { br ->
                FileWriter(f).use { fw ->
                    BufferedWriter(fw).use { bw ->
                        var c = br.read()
                        while (c != -1) {
                            bw.write(c)
                            c = br.read()
                        }
                    }
                }
            }
        }
    } catch (ex: java.lang.Exception) {
        Logop.errorEx(ex)
    }
}

private fun closeConnection(con: HttpURLConnection) {
    con.disconnect()
}

private fun establishPostConnection(newURL: URL): HttpURLConnection {
    val rtn = establishURLConnection(newURL)
    rtn.requestMethod = "POST"
    rtn.setRequestProperty("User-Agent", "Mozilla/4.0")
    rtn.setRequestProperty("Content-Type", "application/json");
    rtn.doInput = true
    rtn.doOutput = true
    rtn.useCaches = false
    return rtn
}

private fun establishGetConnection(newURL: URL): HttpURLConnection {
    val rtn = establishURLConnection(newURL)
    rtn.requestMethod = "GET"
    rtn.setRequestProperty("Content-Type", "text/html; charset=utf-8");
    return rtn
}

private fun establishURLConnection(newURL: URL): HttpURLConnection {
    val rtn = newURL.openConnection() as HttpURLConnection
    rtn.connectTimeout = timeout
    return rtn
}

package com.panopset.marin.apps.fw

import com.panopset.compat.Fileop.getCanonicalPath
import com.panopset.compat.Logop.errorEx
import com.panopset.compat.Logop.warn
import com.panopset.compat.Stringop.getEol
import com.panopset.compat.Stringop.isEmpty
import java.io.*
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

class FlywheelExec(private val inpF: File) {
    private var isSkipBlanks = false
    fun setSkipBlanks(value: Boolean): FlywheelExec {
        isSkipBlanks = value
        return this
    }

    constructor(inputFile: File, tmpltData: String?) : this(inputFile) {
        tmpltText = tmpltData
    }

    fun setOutputFile(outputFile: File?): FlywheelExec {
        outF = outputFile
        return this
    }

    fun exec() {
        try {
            writer.use { bw ->
                execAll(bw)
                bw!!.flush()
            }
        } catch (e: IOException) {
            errorEx(e)
        }
    }

    @Throws(IOException::class)
    fun execAll(bw: BufferedWriter?) {
        process(tmpltText)
        if (inpF.canRead()) {
            try {
                Files.lines(Paths.get(inpF.path), Charset.defaultCharset()).use { lines ->
                    lines.forEachOrdered { line: String? ->
                        try {
                            process(line)
                        } catch (e: IOException) {
                            errorEx(e)
                        }
                    }
                }
            } catch (e: IOException) {
                errorEx(e)
            }
        }
    }

    @Throws(IOException::class)
    private fun process(line: String?) {
        if (isEligible(line)) {
            writer!!.write(line)
            writer!!.write(getEol())
        }
    }

    private var bw: BufferedWriter? = null
    private val writer: BufferedWriter?
        private get() {
            if (bw == null) {
                bw = if (outF == null) {
                    BufferedWriter(OutputStreamWriter(System.out))
                } else {
                    val pf = outF!!.getParentFile()
                    pf?.mkdirs()
                    try {
                        BufferedWriter(OutputStreamWriter(FileOutputStream(outF)))
                    } catch (e: FileNotFoundException) {
                        errorEx(e)
                        warn(
                            String.format(
                                "Can not write to %s, switching to stdout",
                                getCanonicalPath(outF)
                            )
                        )
                        BufferedWriter(OutputStreamWriter(System.out))
                    }
                }
            }
            return bw
        }

    private fun isEligible(s: String?): Boolean {
        if (s == null) {
            return false
        }
        return if (isSkipBlanks && isEmpty(s.trim { it <= ' ' })) {
            false
        } else true
    }

    private var tmpltText: String? = null
    private var outF: File? = null
    fun skipBlanks(): FlywheelExec {
        setSkipBlanks(true)
        return this
    }
}

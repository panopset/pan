package com.panopset.compat

import com.panopset.compat.Stringop.getEol
import java.io.*

abstract class FileTransformer(private val inp: File, private val outp: File) {
    protected abstract fun filter(inputLine: String?): String?

    fun exec() {
        try {
            FileReader(inp).use { fr ->
                BufferedReader(fr).use { br ->
                    FileWriter(outp).use { fw ->
                        BufferedWriter(fw).use { bw ->
                            var line = br.readLine()
                            while (line != null) {
                                val fl = filter(line)
                                if (fl != null) {
                                    bw.write(fl)
                                    bw.write(getEol())
                                }
                                line = br.readLine()
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            Logz.errorEx(e)
        }
    }
}

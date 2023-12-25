package com.panopset.marin.secure.checksums

import com.panopset.compat.*
import com.panopset.compat.Fileop.getCanonicalPath
import com.panopset.compat.Logop.green
import com.panopset.compat.Logop.warn
import com.panopset.compat.Stringop.getEol
import java.io.File

class ChecksumReport {
    private var firstTime = true
    fun generateReport(file: File?, types: List<ChecksumType>, textProcessor: TextProcessor) {
        if (file == null) {
            warn(Nls.xlate("File is null, no checksum report generated."))
            return
        }
        if (!file.exists()) {
            warn(
                java.lang.String.join(":", Nls.xlate("File doesn't exist"), getCanonicalPath(file))
            )
            return
        }
        if (file.isDirectory()) {
            generateDirectoryReport(file, types, textProcessor)
        } else {
            generateFileReport(file, types, textProcessor)
        }
    }

    private var maxTitleLength: Int = 0
    private fun getMaxTitleSize(types: List<ChecksumType>): Int {
            var i = 0
            for (cst in types) {
                if (cst.name.length > i) {
                    i = cst.name.length
                }
            }
            maxTitleLength = i
        return maxTitleLength
    }

    private fun generateFileReport(
        file: File, types: List<ChecksumType>,
        textProcessor: TextProcessor
    ) {
        green(java.lang.String.join(": ", Nls.xlate("Processing"), getCanonicalPath(file)))
        if (types.size > 1) {
            textProcessor.append(file.getName())
            textProcessor.append("\n")
        } else if (types.size == 1) {
            if (firstTime) {
                textProcessor.append(types[0].name)
                textProcessor.append("\n")
                firstTime = false
            }
        }
        for (cst in types) {
            if (types.size > 1) {
                textProcessor.append(Padop.leftPad(cst.name, getMaxTitleSize(types), ' '))
                textProcessor.append(":")
            }
            when (cst) {
                ChecksumType.BYTES -> textProcessor.append(byteCount(file))
                ChecksumType.MD5 -> textProcessor.append(md5(file))
                ChecksumType.SHA1 -> textProcessor.append(sha1(file))
                ChecksumType.SHA256 -> textProcessor.append(sha256(file))
                ChecksumType.SHA384 -> textProcessor.append(sha384(file))
                ChecksumType.SHA512 -> textProcessor.append(sha512(file))
            }
            if (types.size > 1) {
                textProcessor.append("\n")
            } else {
                textProcessor.append(" ")
                textProcessor.append(file.name)
            }
        }
        green(String.format("%s: %s", Nls.xlate("Completed"), getCanonicalPath(file)))
    }

    private fun generateDirectoryReport(
        file: File, types: List<ChecksumType>,
        textProcessor: TextProcessor
    ) {
        if (!file.isDirectory()) {
            return
        }
        val fileList = file.listFiles() ?: return
        for (f in fileList) {
            if (f.isFile()) {
                generateFileReport(f, types, textProcessor)
                textProcessor.append(getEol())
            }
        }
        if (textProcessor.text.isEmpty()) {
            warn(String.format(Stringop.CS, Nls.xlate("No files found in"), getCanonicalPath(file)))
        } else {
            green(Nls.xlate("Completed."))
        }
    }
}

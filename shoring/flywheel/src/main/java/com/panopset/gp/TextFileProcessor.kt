package com.panopset.gp

import java.io.*
import java.nio.charset.StandardCharsets

class TextFileProcessor private constructor(reader: Reader) : LineIterator(reader) {
    companion object {
        fun textFileIterator(file: File): TextFileProcessor {
            return TextFileProcessor(InputStreamReader(FileInputStream(file), StandardCharsets.UTF_8))
        }
    }
}

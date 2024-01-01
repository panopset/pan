package com.panopset.flywheel

import com.panopset.compat.Fileop
import com.panopset.compat.Panop
import java.io.File

class TemplateFile(val panop: Panop, file: File) : TemplateArray(Fileop.readLines(panop, file)) {
    override val name: String = Fileop.getCanonicalPath(file)
}

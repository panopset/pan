package com.panopset.tests.transformer

import com.panopset.compat.Fileop
import com.panopset.gp.GlobalReplaceProcessor
import org.junit.jupiter.api.Assertions
import java.io.IOException

class GlobalReplaceTransformerTest(
    private val searchStr: String?, private val replacementStr: String?, private val priorLineMustContain: String,
    packageName: String?, refreshFile: String?, fromToFile: String?, expectedFile: String?
) : SameFileTest(packageName, refreshFile, fromToFile, expectedFile) {
    constructor(
        searchStr: String?, replacmentStr: String?, packageName: String?, refreshFile: String?,
        fromToFile: String?, expectedFile: String?
    ) : this(searchStr, replacmentStr, "", packageName, refreshFile, fromToFile, expectedFile)

    override fun xform(): String {
        try {
            globalReplaceProcessor.process()
            return Fileop.readTextFile(tempFile)
        } catch (e: IOException) {
            Assertions.fail<Any>("performTransformation failure.", e)
        }
        return ""
    }

    private var grp: GlobalReplaceProcessor? = null
    protected val globalReplaceProcessor: GlobalReplaceProcessor
        protected get() {
            if (grp == null) {
                grp = GlobalReplaceProcessor(tempFile, searchStr!!, replacementStr!!, "txt", false)
                grp!!.priorLineMustContain = priorLineMustContain
            }
            return grp!!
        }
}

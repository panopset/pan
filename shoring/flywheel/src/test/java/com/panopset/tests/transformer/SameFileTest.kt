package com.panopset.tests.transformer

import com.panopset.compat.Fileop
import com.panopset.tests.TEST_DIRECTORY
import java.io.File

abstract class SameFileTest(
    packageName: String?,
    refreshFileRezPath: String?,
    tempFile: String?,
    private val expectedFileRezPath: String?
) : StandardTransformerTest(packageName) {
    protected val refreshFile: File?
    protected val tempFile: File
    protected val expectedFile: File?
    protected abstract fun xform(): String

    init {
        refreshFile = getPackagePath(refreshFileRezPath!!)
        this.tempFile = File(Fileop.combinePaths(TEST_DIRECTORY, tempFile!!))
        expectedFile = getPackagePath(expectedFileRezPath!!)
    }

    override fun createResultsDataSupplier(): ResultsDataSupplier {
        return object : ResultsDataSupplier {
            override val expectedResults: String?
                get() = readTextFromStandardResource(expectedFileRezPath!!)
            override val actualResults: String
                get() {
                    Fileop.delete(tempFile)
                    Fileop.copyFile(refreshFile!!, tempFile)
                    return xform()
                }
        }
    } //	new ResultsDataSupplier() {
    //		
    //		@Override
    //		public String getExpectedResults() {
    //			return readTextFromStandardResourxce(expectedFileRezPath);
    //		}
    //		
    //		@Override
    //		public String getActualResults() {
    //			Fileop.delete(getToFile());
    //			Fileop.copyFile(getRefreshFile(), getFromFile());
    //			return null;
    //		}
    //		
    //		private File refreshFile;
    //		
    //		File getRefreshFile() {
    //			if (refreshFile == null) {
    //				refreshFile = new File(Fileop.combinePaths(packagePath, refreshFileRezPath));
    //			}
    //			return refreshFile;
    //		}
    //
    //	}
}

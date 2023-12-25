package com.panopset.tests.transformer

import org.junit.jupiter.api.Assertions

abstract class TransformerTest {
    protected abstract fun createResultsDataSupplier(): ResultsDataSupplier?
    private var rds: ResultsDataSupplier? = null
    private val resultsDataSupplier  = createResultsDataSupplier()

    //	public TransformerTest(final String expectedResults) {
    //		this(new ResultsDataSupplier() {
    //
    //			@Override
    //			public String getExpectedResults() {
    //				return expectedResults;
    //			}
    //		});
    //	}
    //
    //	public TransformerTest(final File expectedResultsFile) {
    //		this(new ResultsDataSupplier() {
    //
    //			@Override
    //			public String getExpectedResults() {
    //				return Fileop.readTextFile(expectedResultsFile);
    //			}
    //		});
    //	}
    fun test() {
        init()
        Assertions.assertEquals(resultsDataSupplier!!.expectedResults, resultsDataSupplier!!.actualResults)
    }

    protected fun init() {}
}

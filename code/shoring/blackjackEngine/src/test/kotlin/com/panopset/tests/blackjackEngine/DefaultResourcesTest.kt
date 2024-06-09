package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.DefaultResources
import com.panopset.compat.Stringop.stringToList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DefaultResourcesTest {

    private val defaultResources = DefaultResources()
    @Test
    fun test() {
        var rawData = defaultResources.defaultBasicStrategy
        Assertions.assertEquals(" 11  Dh Dh Dh Dh Dh Dh Dh Dh Dh Hd", stringToList(rawData)[5])
        rawData = defaultResources.defaultCountingSystems
        Assertions.assertEquals(" 0 +1 +1 +1 +1  0  0  0 -1  0 Hi-Opt I", stringToList(rawData)[5])
    }
}

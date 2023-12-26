package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.DefaultResources.Companion.defaultBasicStrategy
import com.panopset.blackjackEngine.DefaultResources.Companion.defaultCountingSystems
import com.panopset.compat.Stringop.stringToList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DefaultResourcesTest {
    @Test
    fun test() {
        var rawData = defaultBasicStrategy
        Assertions.assertEquals(" 11  Dh Dh Dh Dh Dh Dh Dh Dh Dh Hd", stringToList(rawData)[5])
        rawData = defaultCountingSystems
        Assertions.assertEquals(" 0 +1 +1 +1 +1  0  0  0 -1  0 Hi-Opt I", stringToList(rawData)[5])
    }
}

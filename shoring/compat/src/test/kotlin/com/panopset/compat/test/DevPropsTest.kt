package com.panopset.compat.test

import com.panopset.compat.DevProps
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions

class DevPropsTest {
    @Test
    fun test() {
        Assertions.assertTrue(DevProps.getSiteDomainName().isNotEmpty())
        Assertions.assertTrue(DevProps.getSiteName().isNotEmpty())
        Assertions.assertTrue(DevProps.getSiteUsr().isNotEmpty())
//        Assertions.assertTrue(DevProps.getWorkstationUser().isNotEmpty())
        Assertions.assertTrue(DevProps.getSitePassword().isNotEmpty())
//        Assertions.assertTrue(DevProps.getWorkstatsionPasssword().isNotEmpty())
//        Assertions.assertTrue(DevProps.getSiteRedisURL().isNotEmpty())
//        Assertions.assertTrue(DevProps.getSiteRedisPassword().isNotEmpty())
    }
}
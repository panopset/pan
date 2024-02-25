package com.panopset.compat

import com.panopset.compat.Stringop.FSP
import com.panopset.compat.Stringop.USH
import java.io.File

object DevProps {
    val path = "$USH${FSP}Documents${FSP}panopset${FSP}dev.properties"
    val props = Fileop.loadProps(File(path))

    fun getSiteDomainName(): String {
        return props.getProperty("SITE_DN")
    }

    fun getSiteName(): String {
        return props.getProperty("SITE_NAME")
    }

    fun getSiteUsr(): String {
        return props.getProperty("SITE_USR")
    }

    fun getWorkstationUser(): String {
        return props.getProperty("WKSN_USR")
    }

    fun getSitePassword(): String {
        return props.getProperty("SITE_PWD")
    }

    fun getWorkstatsionPasssword(): String {
        return props.getProperty("WKSN_PWD")
    }

    fun getSiteRedisURL(): String {
        return props.getProperty("SITE_REDIS_URL")
    }

    fun getSiteRedisPassword(): String {
        return props.getProperty("SITE_REDIS_PWD")
    }
}

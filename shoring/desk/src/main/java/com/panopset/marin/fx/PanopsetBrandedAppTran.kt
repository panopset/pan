package com.panopset.marin.fx

import com.panopset.compat.AppVersion
import com.panopset.compat.Logop
import com.panopset.compat.Trinary
import com.panopset.fxapp.BrandedApp
import com.panopset.marin.bootstrap.VersionHelper

abstract class PanopsetBrandedAppTran: BrandedApp() {


    override fun getCompanyName(): String {
        return "Panopset"
    }
    override fun updateVersionMessage() {
        when (VersionHelper.isReadyToUpdate()) {
            Trinary.ERROR -> Logop.warn(
                "Unable to get version information from panopset.com, please check log for errors."
            )

            Trinary.FALSE -> {
                Logop.green("Welcome, and congratulations, you are running the current version, ${AppVersion.getVersion()}.")
            }

            Trinary.IMPOSSIBLE -> {
                Logop.green("Thank you for previewing the next version, ${AppVersion.getVersion()}")
            }

            Trinary.TRUE -> Logop.green(
                "  You are running version ${AppVersion.getVersion()}.  The current version is ${VersionHelper.getAvailableVersion()}, now available on panopset.com.",
            )
        }
    }
}



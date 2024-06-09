package com.panopset.marin.fx

import com.panopset.compat.AppVersion
import com.panopset.compat.Logz
import com.panopset.compat.Trinary
import com.panopset.fxapp.BrandedApp
import com.panopset.marin.bootstrap.VersionHelper

abstract class PanopsetBrandedAppTran: BrandedApp() {

    override fun getCompanyName(): String {
        return "Panopset"
    }

    override fun updateVersionMessage() {
        when (VersionHelper.isReadyToUpdate()) {
            Trinary.ERROR -> Logz.warn(
                "Unable to get version information from panopset.com, please check log for errors."
            )

            Trinary.FALSE -> {
                Logz.green("You are running the current version, ${AppVersion.getVersion()}.")
            }

            Trinary.IMPOSSIBLE -> {
                Logz.green("Thank you for previewing the next version, ${AppVersion.getVersion()}")
            }

            Trinary.TRUE -> Logz.green(
                "  You are running version ${AppVersion.getVersion()}.  " +
                        "The current version is ${VersionHelper.getAvailableVersion()}, now available on panopset.com.",
            )
        }
    }
}



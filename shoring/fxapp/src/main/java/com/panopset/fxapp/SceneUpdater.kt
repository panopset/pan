package com.panopset.fxapp

import com.panopset.compat.Logop
import com.panopset.fxapp.JavaFXapp.isActive

/**
 *
 * Call triggerAnUpdate after user input has changed,
 * override doUpdate to do the update.
 *
 */
abstract class SceneUpdater(val fxDoc: FxDoc) {
    protected abstract fun doUpdate()
    protected fun triggerAnUpdate() {
        needsUpdate = true
        if (updater == null) {
            createUpdater()
        }
    }

    private var needsUpdate = false
    private var updating = false
    private var updater: Thread? = null
    @Synchronized
    private fun synchronizedUpdate() {
        needsUpdate = false
        updating = true
        doUpdate()
        updating = false
    }

    private fun submitUpdate() {
        if (!updating) {
            synchronizedUpdate()
        }
    }

    @Synchronized
    private fun createUpdater() {
        if (updater != null) {
            return
        }
        updater = Thread {
            while (isActive) {
                try {
                    if (needsUpdate) {
                        submitUpdate()
                    }
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    Logop.warn(fxDoc.panop, e)
                    Thread.currentThread().interrupt()
                }
            }
        }
        updater!!.start()
    }
}

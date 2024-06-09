package com.panopset.fxapp

import com.panopset.compat.KEY_WINDOW_DIMS
import com.panopset.compat.Stringop
import com.panopset.compat.parseDelimToDoubleArray
import com.panopset.fxapp.AnchorFactory.removeAnchor
import javafx.event.EventHandler
import javafx.geometry.Point2D
import javafx.scene.Scene
import javafx.stage.Screen

object StageManager : StageIcon {
    var DEFAULT_WIDTH = 1000
    var DEFAULT_HEIGHT = 900
    fun assembleAndShow(deskApp4FX: DeskApp4FX, fxDoc: FxDoc) {
        val stage = fxDoc.stage
        var isNew = true
        val dims = arrayOfNulls<Double>(4)
        val windims: String = fxDoc.pmf[KEY_WINDOW_DIMS]
        if (Stringop.isPopulated(windims)) {
            isNew = false
            var i = 0
            for (x in parseDelimToDoubleArray("|", windims)) {
                dims[i++] = x
            }
        }
        val xloc = notNull(dims[0], 20.0)
        val yloc = notNull(dims[1], 20.0)
        val width = notNull(dims[2], 100.0)
        val height = notNull(dims[3], 100.0)
        val loc = Point2D(xloc, yloc)
        val center = Point2D(xloc + (width / 2), yloc + (height / 2))
        val w = notNull(dims[2], java.lang.Double.valueOf(DEFAULT_WIDTH.toDouble()))
        val h = notNull(dims[3], java.lang.Double.valueOf(DEFAULT_HEIGHT.toDouble()))
        stage.width = w
        stage.height = h
        val scene = Scene(DeskApp4XFactory.appDDSFX.createPane(fxDoc, deskApp4FX), w, h)
        fxDoc.scene = scene
        fxDoc.scene.root.style = darkTheme;
        stage.scene = scene
        FontManagerFX.updateAllFontSizes()
        var stillVisible = false
        for (screen in Screen.getScreens()) {
            if (screen.bounds.contains(loc) || (screen.bounds.contains(center))) {
                stillVisible = true
                break
            }
        }
        if (!stillVisible || isNew) {
            stage.centerOnScreen()
        } else {
            stage.x = xloc
            stage.y = yloc
        }
        fxDoc.loadDataFromFile()
        stage.onHiding = EventHandler { fxDoc.saveWindow() }
        stage.onCloseRequest = EventHandler { removeAnchor(fxDoc) }
        setFavIcon(stage, DeskApp4XFactory.appDDSFX)
        stage.show()
    }

    private fun notNull(d: Double?, dft: Double): Double {
        if (d == null) {
            return dft
        }
        return d
    }
}

const val darkTheme = "-fx-base:black"
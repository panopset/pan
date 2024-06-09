package com.panopset.marin.oldswpkg.cards.pngmap

import com.panopset.compat.AniGrid
import com.panopset.fxapp.FontManagerFX

class ChipPainter(mapW: Int, mapH: Int) {
    private val aniGrid: AniGrid

    init {
        aniGrid = AniGrid(mapW, mapH, 4, 2, FontManagerFX.imgRatio)
    }

    val chipWidth: Int
        get() = aniGrid.dspWidth
    val chipHeight: Int
        get() = aniGrid.dspHeight

    fun getPaintDimensionsFX(chip: Chip, x: Int, y: Int): IntArray {
        return aniGrid.getPaintDimensions(chip.offsetW, chip.offsetH, x, y)
    }
}

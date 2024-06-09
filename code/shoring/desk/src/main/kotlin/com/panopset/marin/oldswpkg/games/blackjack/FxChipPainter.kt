package com.panopset.marin.oldswpkg.games.blackjack

import com.panopset.fxapp.FontManagerFX
import com.panopset.marin.game.card.images.BlackjackImages
import com.panopset.marin.oldswpkg.cards.pngmap.Chip
import com.panopset.marin.oldswpkg.cards.pngmap.ChipPainter
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image

class FxChipPainter {
    val chipHeight: Int
        get() = cp.chipHeight
    val chipWidth: Int
        get() = cp.chipWidth
    private var chipImg: Image? = null
    private fun getChipImg(): Image? {
        if (chipImg == null) {
            chipImg = BlackjackImages.chipMap
        }
        return chipImg
    }

    fun paintChips(g: GraphicsContext, x: Int, y: Int, amt: Long): Int {
        val stack: MutableList<Chip> = ArrayList()
        var newAmt = amt
        WHILE@ while (newAmt > 99) {
            FOR@ for (chip in Chip.entries) {
                if (chip.value == 250 && newAmt > 250) {
                    continue@FOR
                }
                if (newAmt >= chip.value) {
                    stack.add(chip)
                    newAmt -= chip.value
                    continue@WHILE
                }
            }
        }
        var xloc = x + 2 * stack.size
        var yloc = y
        for (chip in stack) {
            paintChip(chip, g, xloc, yloc)
            yloc = (yloc - FontManagerFX.imgRatio * 2).toInt()
            xloc = (xloc - FontManagerFX.imgRatio * 1).toInt()
        }
        return yloc
    }

    private fun paintChip(
        chip: Chip, g: GraphicsContext, x: Int,
        y: Int
    ) {
        val b = cp.getPaintDimensionsFX(chip, x, y)
        g.drawImage(
            chipImg,
            b[0].toDouble(),
            b[1].toDouble(),
            b[2].toDouble(),
            b[3].toDouble(),
            b[4].toDouble(),
            b[5].toDouble(),
            b[6].toDouble(),
            b[7].toDouble()
        )
    }

    private val cp: ChipPainter

    init {
        val cw = getChipImg()!!.width
        val ch = getChipImg()!!.height
        cp = ChipPainter(cw.toInt(), ch.toInt())
    }
}

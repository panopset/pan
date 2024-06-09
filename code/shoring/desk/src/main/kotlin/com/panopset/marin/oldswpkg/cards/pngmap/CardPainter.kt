package com.panopset.marin.oldswpkg.cards.pngmap

import com.panopset.blackjackEngine.Card
import com.panopset.blackjackEngine.Suit
import com.panopset.compat.AniGrid
import com.panopset.fxapp.FontManagerFX

/**
 * Paint cards based on a card map image. This class only deals with calculating
 * source and destination rectangles, so that it can be used independently of
 * the platform specific painting methods.
 *
 * @author Karl Dinwiddie
 */
class CardPainter(mapW: Int, mapH: Int, suits: Array<Suit> = DFT_ORDER) {
    private val aniGrid: AniGrid = AniGrid(mapW, mapH, 13, 4, FontManagerFX.svgRatio)
    val order: Array<Suit> = suits
    val cardHeight = aniGrid.dspHeight
    val cardWidth = aniGrid.dspWidth
    private val ofs: MutableMap<Suit, Int> = HashMap()

    private fun getOffset(suit: Suit): Int {
        var rtn = ofs[suit]
        if (rtn == null) {
            var offset = 0
            for (s in order) {
                if (s == suit) {
                    break
                }
                offset++
            }
            ofs[suit] = offset
            rtn = offset
        }
        return rtn
    }

    fun getPaintDimensionsFX(card: Card, x: Int, y: Int): IntArray {
        return aniGrid.getPaintDimensions(card.face.offset, getOffset(card.suit), x, y)
    }

    companion object {
        /**
         * Default order is club, spade, heart, diamond.
         */
        val DFT_ORDER = arrayOf(
            Suit.CLUB, Suit.SPADE,
            Suit.HEART, Suit.DIAMOND
        )
    }
}

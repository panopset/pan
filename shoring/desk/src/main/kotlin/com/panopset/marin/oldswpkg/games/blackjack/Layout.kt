package com.panopset.marin.oldswpkg.games.blackjack

import com.panopset.blackjackEngine.CycleSnapshot
import com.panopset.compat.Tile

abstract class Layout(val t: Tile, cardDim: IntArray, val cs: CycleSnapshot) {
    val h: Int
    val w: Int
    val centerW: Int
    val centerH: Int
    val cardW: Int
    val cardH: Int
    val cardSpacer: Int

    init {
        w = t.width
        h = t.height
        centerW = w / 2
        centerH = h / 2
        cardW = cardDim[0]
        cardH = cardDim[1]
        cardSpacer = cardW / 5
    }
}

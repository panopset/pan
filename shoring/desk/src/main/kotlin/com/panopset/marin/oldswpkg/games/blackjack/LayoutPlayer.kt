package com.panopset.marin.oldswpkg.games.blackjack

import com.panopset.blackjackEngine.CycleSnapshot
import com.panopset.compat.Tile

class LayoutPlayer(tile: Tile?, cardDim: IntArray?, cs: CycleSnapshot?, chipDim: IntArray) :
    Layout(tile!!, cardDim!!, cs!!) {
    val chipW: Int
    val chipH: Int
    var playerChipY: Int? = null
        get() {
            if (field == null) {
                field = t.top + cardH
            }
            return field
        }
        private set
    var playerMsgY: Int? = null
        get() {
            if (field == null) {
                field = playerChipY!! + chipH * 2
            }
            return field
        }
        private set
    var playerCardXstart: Int? = null
        get() {
            if (field == null) {
                var cardsWidth = 0
                for (p in cs.getPlayers()) {
                    for (h in p.hands) {
                        if (!h.cards.isEmpty()) {
                            cardsWidth = cardsWidth + cardW
                            cardsWidth = cardsWidth + cardSpacer * h.cards.size
                        }
                    }
                }
                field = centerW - cardsWidth / 2
            }
            return field
        }
        private set
    var playerY: Int? = null
        get() {
            if (field == null) {
                field = t.top + cardH
            }
            return field
        }
        private set

    init {
        chipW = chipDim[0]
        chipH = chipDim[1]
    }
}

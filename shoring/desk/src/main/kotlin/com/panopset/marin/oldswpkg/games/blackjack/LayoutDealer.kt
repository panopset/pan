package com.panopset.marin.oldswpkg.games.blackjack

import com.panopset.blackjackEngine.CycleSnapshot
import com.panopset.compat.Tile

class LayoutDealer(t: Tile?, cardDim: IntArray?, cs: CycleSnapshot?) : Layout(t!!, cardDim!!, cs!!) {
    var nextDealerX: Int? = null
        get() {
            field = if (field == null) {
                t.left
            } else {
                field!! + cardSpacer
            }
            return field
        }
        private set
}

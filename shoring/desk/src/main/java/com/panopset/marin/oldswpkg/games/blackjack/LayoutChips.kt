package com.panopset.marin.oldswpkg.games.blackjack

class LayoutChips(val chipXnextBet: Int, bottom: Int, chipWidth: Int, chipHeight: Int) {
    val chipXstack: Int
    val chipY: Int

    init {
        chipXstack = chipXnextBet + chipWidth
        chipY = bottom - (chipHeight * 1.5).toInt()
    }
}

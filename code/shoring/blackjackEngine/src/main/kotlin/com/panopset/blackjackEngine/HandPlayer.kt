package com.panopset.blackjackEngine

import com.panopset.compat.Logz

class HandPlayer(val wager: Wager) : Hand() {
    var isSplit = false
    var isSurrendered = false
    var isSelectedEvenMoney = false
    var action: String = CMD_DEAL

    constructor(handPlayer: HandPlayer): this(Wager(handPlayer.wager)) {
        isSplit = handPlayer.isSplit
        isSurrendered = handPlayer.isSurrendered
        isSelectedEvenMoney = handPlayer.isSelectedEvenMoney
        isFinal = handPlayer.isFinal
        action = handPlayer.action
        message = handPlayer.message
        val iterator = handPlayer.cards.iterator()
        while (iterator.hasNext()) {
            dealCard(iterator.next())
        }
    }

    override fun isNatural21(): Boolean {
        return if (isSplit) {
            false
        } else super.isNatural21()
    }

    fun setSplit() {
        isSplit = true
    }

    var message: String = promptDeal
    override fun dealCard(card: BlackjackCard) {
        super.dealCard(card)
        message = ""
    }

//    var message: String
//        get() {
//            if (msg.isEmpty()) {
//                msg = "Please select deal (L)"
//            }
//            return msg
//        }
//        set(message) {
//            msg = message
//        }

    fun canDouble(configIsDoubleAfterSplitAllowed: Boolean): Boolean {
        if (!isInitialDeal) {
            return false
        }
        return if (isSplit) {
            configIsDoubleAfterSplitAllowed
        } else {
            true
        }
    }

    fun surrender() {
        isSurrendered = true
        stand()
    }

    fun isBustedOrSurrenderred(): Boolean {
        return if (super.isBusted()) {
            true
        } else {
            isSurrendered
        }
    }

    fun standWithEvenMoney() {
        stand()
        isSelectedEvenMoney = true
    }

    val isCardFacesSplittable: Boolean
        get() = isCardFacesSplittable(false)
    val isCardFacesSplittableIncludeMessage: Boolean
        get() = isCardFacesSplittable(true)

    private fun isCardFacesSplittable(includeMessage: Boolean): Boolean {
        if (cards.size != 2) {
            if (includeMessage) {
                Logz.warn("Can't split a hit hand")
            }
            return false
        } else if (!isFaceMatch) {
            if (includeMessage) {
                Logz.warn("Can't split cards that don't have the same face")
            }
            return false
        }
        return true
    }

    private val isFaceMatch: Boolean
        get() = cards[0].card.face == cards[1].card.face
    val isDoubleDowned: Boolean
        get() = wager.isDoubledDown

//    init {
//        c = handPlayer.c
//        isSplit = handPlayer.isSplit
//        isFinal = handPlayer.isFinal
//        wager = Wager(handPlayer.wager)
//        isSurrendered = handPlayer.isSurrendered
//        isSelectedEvenMoney = handPlayer.isSelectedEvenMoney
//        for (card in handPlayer.cards) {
//            cards.add(card)
//        }
//        message = handPlayer.message
//        action = handPlayer.action
//    }
}

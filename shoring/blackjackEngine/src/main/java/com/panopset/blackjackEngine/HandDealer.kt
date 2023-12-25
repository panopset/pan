package com.panopset.blackjackEngine

class HandDealer() : Hand() {
    constructor(dealer: HandDealer) : this() {
        val iterator = dealer.cards.iterator()
        while (iterator.hasNext()) {
            val card = iterator.next()
            cards.add(card)
        }
    }

    val upCard: BlackjackCard
        get() = cards[1]

    val downCard: BlackjackCard
        get() = cards[0]
}

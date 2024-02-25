package com.panopset.blackjackEngine

class BlackjackCard(val card: Card) {
    constructor(card: Card, isShowing: Boolean) : this(card) {
        card.isShowing = isShowing
    }
    constructor(cardDefinition: CardDefinition) : this(Card(cardDefinition))
    constructor(blackjackCard: BlackjackCard) : this(blackjackCard.card, blackjackCard.card.isShowing)

    val isAce: Boolean
        get() = card.face.offset == 0
    val softValue: Int
        get() = if (isAce) {
            11
        } else nonAceValue
    val hardValue: Int
        get() = if (isAce) {
            1
        } else nonAceValue
    private val nonAceValue: Int
        get() = if (card.face.offset < 10) {
            card.face.offset + 1
        } else {
            10
        }

    override fun toString(): String {
        return card.name()
    }

    fun show() {
        card.isShowing = true
    }
}

package com.panopset.blackjackEngine

class Card(val cardDefinition: CardDefinition, val isBlue: Boolean = true) {
    var isShowing = false

    val face: Face
        get() = cardDefinition.face
    val suit: Suit
        get() = cardDefinition.suit

    fun name(): String {
        return cardDefinition.name
    }

    override fun toString(): String {
        return name()
    }
}

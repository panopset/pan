package com.panopset.blackjackEngine

/**
 * Card image templates must present a row of cards in the order
 * A23456789TJQK.  This provides an offset for a card image AniBreaker grid.
 * For SVG images, the Suit and Face keys are used to construct the image name,
 * for example Queen of Diamonds would be QD.svg.
 *
 */
enum class Face(val offset: Int, @JvmField val key: String) {
    ACE(0, "A"),
    TWO(1, "2"),
    THREE(2, "3"),
    FOUR(3, "4"),
    FIVE(4, "5"),
    SIX(5, "6"),
    SEVEN(6, "7"),
    EIGHT(7, "8"),
    NINE(8, "9"),
    TEN(9, "T"),
    JACK(10, "J"),
    QUEEN(11, "Q"),
    KING(12, "K")

}

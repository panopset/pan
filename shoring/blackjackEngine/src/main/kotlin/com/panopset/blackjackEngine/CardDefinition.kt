package com.panopset.blackjackEngine

import java.io.StringWriter

enum class CardDefinition(val face: Face, val suit: Suit) {
    ACE_OF_SPADES(Face.ACE, Suit.SPADE),
    TWO_OF_SPADES(Face.TWO, Suit.SPADE),
    THREE_OF_SPADES(
        Face.THREE, Suit.SPADE
    ),
    FOUR_OF_SPADES(Face.FOUR, Suit.SPADE),
    FIVE_OF_SPADES(
        Face.FIVE,
        Suit.SPADE
    ),
    SIX_OF_SPADES(Face.SIX, Suit.SPADE),
    SEVEN_OF_SPADES(
        Face.SEVEN,
        Suit.SPADE
    ),
    EIGHT_OF_SPADES(Face.EIGHT, Suit.SPADE),
    NINE_OF_SPADES(
        Face.NINE,
        Suit.SPADE
    ),
    TEN_OF_SPADES(Face.TEN, Suit.SPADE),
    JACK_OF_SPADES(
        Face.JACK,
        Suit.SPADE
    ),
    QUEEN_OF_SPADES(
        Face.QUEEN,
        Suit.SPADE
    ),
    KING_OF_SPADES(Face.KING, Suit.SPADE),
    ACE_OF_CLUBS(Face.ACE, Suit.CLUB),
    TWO_OF_CLUBS(Face.TWO, Suit.CLUB),
    THREE_OF_CLUBS(
        Face.THREE,
        Suit.CLUB
    ),
    FOUR_OF_CLUBS(Face.FOUR, Suit.CLUB),
    FIVE_OF_CLUBS(
        Face.FIVE,
        Suit.CLUB
    ),
    SIX_OF_CLUBS(Face.SIX, Suit.CLUB),
    SEVEN_OF_CLUBS(
        Face.SEVEN,
        Suit.CLUB
    ),
    EIGHT_OF_CLUBS(Face.EIGHT, Suit.CLUB),
    NINE_OF_CLUBS(
        Face.NINE,
        Suit.CLUB
    ),
    TEN_OF_CLUBS(Face.TEN, Suit.CLUB),
    JACK_OF_CLUBS(
        Face.JACK,
        Suit.CLUB
    ),
    QUEEN_OF_CLUBS(
        Face.QUEEN,
        Suit.CLUB
    ),
    KING_OF_CLUBS(Face.KING, Suit.CLUB),
    ACE_OF_HEARTS(Face.ACE, Suit.HEART),
    TWO_OF_HEARTS(Face.TWO, Suit.HEART),
    THREE_OF_HEARTS(
        Face.THREE, Suit.HEART
    ),
    FOUR_OF_HEARTS(Face.FOUR, Suit.HEART),
    FIVE_OF_HEARTS(
        Face.FIVE,
        Suit.HEART
    ),
    SIX_OF_HEARTS(Face.SIX, Suit.HEART),
    SEVEN_OF_HEARTS(
        Face.SEVEN,
        Suit.HEART
    ),
    EIGHT_OF_HEARTS(Face.EIGHT, Suit.HEART),
    NINE_OF_HEARTS(
        Face.NINE,
        Suit.HEART
    ),
    TEN_OF_HEARTS(Face.TEN, Suit.HEART),
    JACK_OF_HEARTS(
        Face.JACK,
        Suit.HEART
    ),
    QUEEN_OF_HEARTS(
        Face.QUEEN,
        Suit.HEART
    ),
    KING_OF_HEARTS(Face.KING, Suit.HEART),
    ACE_OF_DIAMONDS(Face.ACE, Suit.DIAMOND),
    TWO_OF_DIAMONDS(
        Face.TWO,
        Suit.DIAMOND
    ),
    THREE_OF_DIAMONDS(Face.THREE, Suit.DIAMOND),
    FOUR_OF_DIAMONDS(
        Face.FOUR,
        Suit.DIAMOND
    ),
    FIVE_OF_DIAMONDS(Face.FIVE, Suit.DIAMOND),
    SIX_OF_DIAMONDS(
        Face.SIX,
        Suit.DIAMOND
    ),
    SEVEN_OF_DIAMONDS(Face.SEVEN, Suit.DIAMOND),
    EIGHT_OF_DIAMONDS(
        Face.EIGHT, Suit.DIAMOND
    ),
    NINE_OF_DIAMONDS(
        Face.NINE,
        Suit.DIAMOND
    ),
    TEN_OF_DIAMONDS(Face.TEN, Suit.DIAMOND),
    JACK_OF_DIAMONDS(
        Face.JACK, Suit.DIAMOND
    ),
    QUEEN_OF_DIAMONDS(
        Face.QUEEN,
        Suit.DIAMOND
    ),
    KING_OF_DIAMONDS(Face.KING, Suit.DIAMOND);

    val key: String = "${face.key}${suit.key}"

    companion object {
        @JvmStatic
        fun find(cardDef: String?): CardDefinition? {
            for (cd in entries) {
                if (cd.toString().equals(cardDef, ignoreCase = true)
                    || cd.toString().equals(cardDef?.replace(" ", "_"), ignoreCase = true)
                ) {
                    return cd
                }
            }
            return null
        }

        @JvmStatic
        fun find(face: Face, cardSuit: Suit): CardDefinition? {
            for (cd in entries) {
                if (cd.face === face && cd.suit === cardSuit) {
                    return cd
                }
            }
            return null
        }
    }
}

package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.Card
import com.panopset.blackjackEngine.CardDefinition

fun playerBlackjack(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.TEN_OF_SPADES))
    rtn.add(Card(CardDefinition.FOUR_OF_SPADES))
    rtn.add(Card(CardDefinition.ACE_OF_HEARTS))
    rtn.add(Card(CardDefinition.THREE_OF_SPADES))
    return rtn
}

fun playerBlackjackAnd20Vs21total2players(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.TEN_OF_SPADES))
    rtn.add(Card(CardDefinition.TEN_OF_HEARTS))
    rtn.add(Card(CardDefinition.THREE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.ACE_OF_SPADES))
    rtn.add(Card(CardDefinition.JACK_OF_HEARTS))
    rtn.add(Card(CardDefinition.FOUR_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.NINE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.FIVE_OF_DIAMONDS))
    return rtn
}

fun dealerBlackjack(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.FOUR_OF_SPADES))
    rtn.add(Card(CardDefinition.TEN_OF_SPADES))
    rtn.add(Card(CardDefinition.THREE_OF_SPADES))
    rtn.add(Card(CardDefinition.ACE_OF_HEARTS))
    return rtn
}

fun playerAndDealerBlackjack(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.TEN_OF_SPADES))
    rtn.add(Card(CardDefinition.TEN_OF_HEARTS))
    rtn.add(Card(CardDefinition.ACE_OF_HEARTS))
    rtn.add(Card(CardDefinition.ACE_OF_SPADES))
    return rtn
}

fun dealerSoft17_0(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.EIGHT_OF_SPADES))
    rtn.add(Card(CardDefinition.SIX_OF_SPADES))
    rtn.add(Card(CardDefinition.THREE_OF_SPADES))
    rtn.add(Card(CardDefinition.ACE_OF_HEARTS))
    rtn.add(Card(CardDefinition.JACK_OF_HEARTS))
    return rtn
}

fun dealerSoft17_1(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.EIGHT_OF_SPADES))
    rtn.add(Card(CardDefinition.SIX_OF_SPADES))
    rtn.add(Card(CardDefinition.THREE_OF_SPADES))
    rtn.add(Card(CardDefinition.ACE_OF_HEARTS))
    rtn.add(Card(CardDefinition.TEN_OF_HEARTS))
    rtn.add(Card(CardDefinition.FOUR_OF_HEARTS))
    return rtn
}

fun splitNotAllowedTest(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.TWO_OF_SPADES))
    rtn.add(Card(CardDefinition.SIX_OF_CLUBS))
    rtn.add(Card(CardDefinition.EIGHT_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.ACE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.QUEEN_OF_HEARTS))
    return rtn
}

fun soft18vThit8thenBtest(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.SEVEN_OF_HEARTS))
    rtn.add(Card(CardDefinition.SEVEN_OF_CLUBS))
    rtn.add(Card(CardDefinition.ACE_OF_CLUBS))
    rtn.add(Card(CardDefinition.KING_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.EIGHT_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.KING_OF_CLUBS))
    return rtn
}

fun doubleDown(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.EIGHT_OF_SPADES))
    rtn.add(Card(CardDefinition.KING_OF_HEARTS))
    rtn.add(Card(CardDefinition.TWO_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.SIX_OF_HEARTS))
    rtn.add(Card(CardDefinition.THREE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.ACE_OF_DIAMONDS))
    return rtn
}

fun twentyV2hitToSoft18(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.KING_OF_CLUBS))
    rtn.add(Card(CardDefinition.FIVE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.KING_OF_HEARTS))
    rtn.add(Card(CardDefinition.TWO_OF_HEARTS))
    rtn.add(Card(CardDefinition.ACE_OF_DIAMONDS))
    return rtn
}

// dealer busts if the player doubles.
fun eightVersusFive(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.FIVE_OF_CLUBS))
    rtn.add(Card(CardDefinition.KING_OF_HEARTS))
    rtn.add(Card(CardDefinition.THREE_OF_CLUBS))
    rtn.add(Card(CardDefinition.FIVE_OF_HEARTS))
    rtn.add(Card(CardDefinition.THREE_OF_CLUBS))
    rtn.add(Card(CardDefinition.ACE_OF_HEARTS))
    rtn.add(Card(CardDefinition.TEN_OF_HEARTS))
    return rtn
}

fun resplitAces(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.ACE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.THREE_OF_CLUBS))
    rtn.add(Card(CardDefinition.ACE_OF_HEARTS))
    rtn.add(Card(CardDefinition.KING_OF_CLUBS)) //D:(3)K
    rtn.add(Card(CardDefinition.THREE_OF_DIAMONDS)) //A|A3
    rtn.add(Card(CardDefinition.ACE_OF_CLUBS)) //AA|A3
    rtn.add(Card(CardDefinition.QUEEN_OF_DIAMONDS)) //AA|A3Q
    rtn.add(Card(CardDefinition.SIX_OF_DIAMONDS)) //AA|A3Q6
    rtn.add(Card(CardDefinition.NINE_OF_HEARTS)) //A|A9|A3Q6
    rtn.add(Card(CardDefinition.JACK_OF_HEARTS)) //AJ|A9|A3Q6
    rtn.add(Card(CardDefinition.QUEEN_OF_HEARTS)) //D:3KQ
    return rtn
}

fun resplitAces1(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.ACE_OF_SPADES))
    rtn.add(Card(CardDefinition.THREE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.ACE_OF_HEARTS))
    rtn.add(Card(CardDefinition.KING_OF_HEARTS))
    rtn.add(Card(CardDefinition.THREE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.ACE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.QUEEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.SIX_OF_SPADES))
    rtn.add(Card(CardDefinition.NINE_OF_HEARTS))
    rtn.add(Card(CardDefinition.JACK_OF_HEARTS))
    rtn.add(Card(CardDefinition.QUEEN_OF_HEARTS))
    //rtn.add(new Card(CardDefinition.KING_OF_HEARTS));
    return rtn
}

fun doubleAfterSplit(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.SIX_OF_SPADES))
    rtn.add(Card(CardDefinition.KING_OF_HEARTS))
    rtn.add(Card(CardDefinition.SIX_OF_HEARTS))
    rtn.add(Card(CardDefinition.THREE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.THREE_OF_HEARTS))
    rtn.add(Card(CardDefinition.SIX_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TEN_OF_SPADES))
    rtn.add(Card(CardDefinition.JACK_OF_HEARTS))
    rtn.add(Card(CardDefinition.QUEEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.KING_OF_DIAMONDS))
    return rtn
}

fun soft18v2(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.SEVEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.KING_OF_HEARTS))
    rtn.add(Card(CardDefinition.ACE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TWO_OF_HEARTS))
    rtn.add(Card(CardDefinition.JACK_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.QUEEN_OF_DIAMONDS))
    return rtn
}

fun soft18v3(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.ACE_OF_SPADES))
    rtn.add(Card(CardDefinition.KING_OF_HEARTS))
    rtn.add(Card(CardDefinition.SEVEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.THREE_OF_HEARTS))
    rtn.add(Card(CardDefinition.THREE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TEN_OF_DIAMONDS))
    return rtn
}

fun softHitTo18v3(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.ACE_OF_SPADES))
    rtn.add(Card(CardDefinition.KING_OF_HEARTS))
    rtn.add(Card(CardDefinition.THREE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.THREE_OF_HEARTS))
    rtn.add(Card(CardDefinition.FOUR_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TEN_OF_DIAMONDS))
    return rtn
}

fun soft18vT(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.ACE_OF_SPADES))
    rtn.add(Card(CardDefinition.KING_OF_HEARTS))
    rtn.add(Card(CardDefinition.SEVEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TEN_OF_HEARTS))
    rtn.add(Card(CardDefinition.THREE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TEN_OF_DIAMONDS))
    return rtn
}

fun lowCountTest(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.ACE_OF_HEARTS))
    rtn.add(Card(CardDefinition.KING_OF_HEARTS))
    rtn.add(Card(CardDefinition.TEN_OF_SPADES))
    rtn.add(Card(CardDefinition.QUEEN_OF_DIAMONDS))
    return rtn
}

fun bustedHstarSingle(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.TWO_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.KING_OF_HEARTS))
    rtn.add(Card(CardDefinition.ACE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.FOUR_OF_HEARTS))
    rtn.add(Card(CardDefinition.JACK_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.QUEEN_OF_DIAMONDS))
    return rtn
}

fun stackHard11_v_A(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.SEVEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.FOUR_OF_HEARTS))
    rtn.add(Card(CardDefinition.FOUR_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.ACE_OF_HEARTS))
    rtn.add(Card(CardDefinition.JACK_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.QUEEN_OF_DIAMONDS))
    return rtn
}

fun stackHardHitTo11_v_A(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.TWO_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.FOUR_OF_HEARTS))
    rtn.add(Card(CardDefinition.FOUR_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.ACE_OF_HEARTS))
    rtn.add(Card(CardDefinition.FIVE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.QUEEN_OF_DIAMONDS))
    return rtn
}

fun stackSplit6_v_2(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.SIX_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TEN_OF_HEARTS))
    rtn.add(Card(CardDefinition.SIX_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TWO_OF_HEARTS))
    rtn.add(Card(CardDefinition.TEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.JACK_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.QUEEN_OF_HEARTS))
    return rtn
}

fun stack12comp10_2_v_4(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.TEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TEN_OF_HEARTS))
    rtn.add(Card(CardDefinition.TWO_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.FOUR_OF_HEARTS))
    rtn.add(Card(CardDefinition.EIGHT_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.JACK_OF_HEARTS))
    return rtn
}

fun stack12comp8_4_v_4(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.EIGHT_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TEN_OF_HEARTS))
    rtn.add(Card(CardDefinition.FOUR_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.FOUR_OF_HEARTS))
    rtn.add(Card(CardDefinition.EIGHT_OF_HEARTS))
    return rtn
}

fun stackHard17vA(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.SEVEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.FOUR_OF_HEARTS))
    rtn.add(Card(CardDefinition.TEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.ACE_OF_HEARTS))
    return rtn
}

fun stackHard16vT(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.SIX_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.FOUR_OF_HEARTS))
    rtn.add(Card(CardDefinition.TEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TEN_OF_HEARTS))
    rtn.add(Card(CardDefinition.QUEEN_OF_DIAMONDS))
    return rtn
}

fun stackHard16_with_4_vT(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.FOUR_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.FOUR_OF_HEARTS))
    rtn.add(Card(CardDefinition.TWO_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TEN_OF_HEARTS))
    rtn.add(Card(CardDefinition.TEN_OF_DIAMONDS))
    return rtn
}

fun stackHard16_with_5_vT(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.TWO_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.FOUR_OF_HEARTS))
    rtn.add(Card(CardDefinition.FIVE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TEN_OF_HEARTS))
    rtn.add(Card(CardDefinition.NINE_OF_DIAMONDS))
    return rtn
}

fun stackHard17vAwithDealerHit(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.SEVEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.FOUR_OF_HEARTS))
    rtn.add(Card(CardDefinition.TEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.ACE_OF_HEARTS))
    rtn.add(Card(CardDefinition.JACK_OF_HEARTS))
    rtn.add(Card(CardDefinition.QUEEN_OF_HEARTS))
    return rtn
}

fun stack12comp3_2_7_v_4(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.THREE_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TEN_OF_HEARTS))
    rtn.add(Card(CardDefinition.TWO_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.FOUR_OF_HEARTS))
    rtn.add(Card(CardDefinition.SEVEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.EIGHT_OF_HEARTS))
    return rtn
}

fun stackSplit6_v_2_doubleDeck(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.SIX_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TEN_OF_HEARTS))
    rtn.add(Card(CardDefinition.SIX_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TWO_OF_HEARTS))
    rtn.add(Card(CardDefinition.TEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.JACK_OF_DIAMONDS))
    return rtn
}

fun soft13vs4(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.TWO_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.KING_OF_HEARTS))
    rtn.add(Card(CardDefinition.ACE_OF_CLUBS))
    rtn.add(Card(CardDefinition.FOUR_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.JACK_OF_DIAMONDS))
    return rtn
}

fun soft13vs4afterSplitAces(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.ACE_OF_SPADES))
    rtn.add(Card(CardDefinition.KING_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.ACE_OF_CLUBS))
    rtn.add(Card(CardDefinition.FOUR_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.TWO_OF_SPADES))
    rtn.add(Card(CardDefinition.JACK_OF_SPADES))
    rtn.add(Card(CardDefinition.QUEEN_OF_CLUBS))
    rtn.add(Card(CardDefinition.QUEEN_OF_SPADES))
    return rtn
}

fun surrender16vs9(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.TEN_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.KING_OF_HEARTS))
    rtn.add(Card(CardDefinition.SIX_OF_CLUBS))
    rtn.add(Card(CardDefinition.NINE_OF_DIAMONDS))
    return rtn
}

fun surrenderFailHitTo16vs10(): List<Card> {
    val rtn: MutableList<Card> = ArrayList()
    rtn.add(Card(CardDefinition.TWO_OF_HEARTS))
    rtn.add(Card(CardDefinition.KING_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.THREE_OF_HEARTS))
    rtn.add(Card(CardDefinition.KING_OF_DIAMONDS))
    rtn.add(Card(CardDefinition.FIVE_OF_HEARTS))
    rtn.add(Card(CardDefinition.SIX_OF_HEARTS))
    return rtn
}

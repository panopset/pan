package com.panopset.blackjackEngine

class StrategyAction(situation: Situation, private val casinoRules: CasinoRules) {
    private val hand: HandPlayer?
    private val canDouble: Boolean
    private val singleDeck: Boolean

    init {
        hand = situation.handPlayer
        canDouble = hand?.canDouble(casinoRules.isDoubleAfterSplitAllowed) ?: false
        singleDeck = casinoRules.decks == 1
    }

    fun getRecommendedAction(recommendedActionText: String?): String {
        return when (recommendedActionText) {
            "H" -> CMD_HIT
            "P" -> CMD_SPLIT
            "H*" -> hstarRecommendation
            "Hx" -> hxRecommendation
            "Hd" -> hdRecommendation
            "H@" -> hatRecommenation
            "HT" -> htRecommendation
            "Rs" -> rsRecommendation
            "Rh" -> rhRecommendation
            "Sh" -> shRecommendation
            "Dh" -> dhRecommendation
            "Ds" -> dsRecommendation
            else -> CMD_STAND
        }
    }

    private val hstarRecommendation: String
        get() = if (singleDeck) {
            dhRecommendation
        } else CMD_HIT
    private val hxRecommendation: String
        get() = if (singleDeck) {
            dsRecommendation
        } else CMD_HIT
    private val hatRecommenation: String
        get() = if (singleDeck) {
            CMD_SPLIT
        } else CMD_HIT
    private val htRecommendation: String
        get() {
            if (hand == null) {
                return CMD_STAND
            }
            if (hand.isInitialDeal) {
                for (card in hand.cards) {
                    if (card.hardValue == 10) {
                        return CMD_HIT
                    }
                }
            }
            return CMD_STAND
        }
    private val rsRecommendation: String
        get() = if (casinoRules.isLateSurrenderAllowed) {
            CMD_SURRENDER
        } else CMD_STAND
    private val rhRecommendation: String
        get() = if (casinoRules.isLateSurrenderAllowed) {
            CMD_SURRENDER
        } else CMD_HIT
    private val shRecommendation: String
        get() {
            if (hand == null) {
                return CMD_STAND
            }
            for (card in hand.cards) {
                if (card.hardValue == 4 || card.hardValue == 5) {
                    return CMD_STAND
                }
            }
            return CMD_HIT
        }
    private val hdRecommendation: String
        get() = if (canDouble && (casinoRules.dealerHitsSoft17 || casinoRules.decks < 3)) {
            CMD_DOUBLE
        } else CMD_HIT
    private val dsRecommendation: String
        get() = if (canDouble) CMD_DOUBLE else CMD_STAND
    private val dhRecommendation: String
        get() = if (canDouble) CMD_DOUBLE else CMD_HIT
}

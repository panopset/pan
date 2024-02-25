package com.panopset.blackjackEngine

import com.panopset.compat.Stringop

open class BlackjackConfigDefault: BlackjackConfiguration {

    private val defaultResources = DefaultResources()
    override val largeBetInWholeDollars = 20
    override val targetStakeInWholeDollars = 10000
    override val minimumBetInWholeDollars = 5
    override val betIncrementInWholeDollars = 5
    override val reloadAmountInWholeDollars = 300
    override val seats = DEFAULT_NBR_SEATS
    override val decks = DEFAULT_NBR_DECKS
    override val isFastDeal = false
    override val isBasicStrategyVariationsOnly = false
    override val isDoubleAfterSplitAllowed = true
    override val isResplitAcesAllowed = false
    override val isSplitAcePlayable = false
    override val isDealerHitSoft17 = true
    override val isEuropeanStyle = false
    override val isLateSurrenderAllowed = false
    override val isBlackjack6to5 = false
    override val isEvenMoneyOnBlackjackVace = false
    override var strategyData = Stringop.stringToList( defaultResources.defaultBasicStrategy)
    override var countingSystemData = Stringop.stringToList( defaultResources.defaultCountingSystems)
    override val strategicVeryPositiveCount = 10
    override val strategicVeryNegativeCount = 10
    override var isShowCount = true
    override var messages: BlackjackMessages = BlackjackMessagesDft()
    override val isBetIdeaDoubleAfterBust = false
    override val isBetIdeaLetItRideAfterTwoWins = false
}

const val DEFAULT_NBR_DECKS = 8
const val DEFAULT_NBR_SEATS = 1

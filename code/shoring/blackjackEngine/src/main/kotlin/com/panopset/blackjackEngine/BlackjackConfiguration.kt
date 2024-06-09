package com.panopset.blackjackEngine

import java.util.ArrayList

interface BlackjackConfiguration {
    val isDoubleAfterSplitAllowed: Boolean
    val isResplitAcesAllowed: Boolean
    val isSplitAcePlayable: Boolean
    val seats: Int
    val decks: Int
    val isDealerHitSoft17: Boolean
    val isBlackjack6to5: Boolean
    val isEvenMoneyOnBlackjackVace: Boolean
    val isLateSurrenderAllowed: Boolean
    val isEuropeanStyle: Boolean
    val isFastDeal: Boolean
    val isBasicStrategyVariationsOnly: Boolean
    var isShowCount: Boolean
    fun toggleShowCount() {
        isShowCount = !isShowCount
    }
    val strategyData: ArrayList<String>
    val countingSystemData: ArrayList<String>
    val largeBetInWholeDollars: Int
    val targetStakeInWholeDollars: Int
    val minimumBetInWholeDollars: Int
    val betIncrementInWholeDollars: Int
    val reloadAmountInWholeDollars: Int
    val strategicVeryPositiveCount: Int
    val strategicVeryNegativeCount: Int
    val isBetIdeaDoubleAfterBust: Boolean
    val isBetIdeaLetItRideAfterTwoWins: Boolean
    val messages: BlackjackMessages
}

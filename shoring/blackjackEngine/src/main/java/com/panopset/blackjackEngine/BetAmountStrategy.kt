package com.panopset.blackjackEngine

class BetAmountStrategy(private val bge: BlackjackGameEngine) {
    private val config: BlackjackConfiguration = bge.config

    fun adjust(): Int {
        var betAmount = bge.getNextBet()
        if (bge.isAutomaticRunning()) {
            if (bge.isCountVeryPositive()) {
                betAmount = config.largeBetInWholeDollars * 100
            }
            if (config.isBetIdeaDoubleAfterBust && bge.isBustedPriorHand) {
                betAmount *= 2
            }
            if (config.isBetIdeaLetItRideAfterTwoWins && bge.streak > 1) {
                betAmount *= 2
            }
        }
        return betAmount
    }
}

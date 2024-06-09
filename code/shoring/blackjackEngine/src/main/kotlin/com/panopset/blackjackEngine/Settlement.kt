package com.panopset.blackjackEngine

internal class Settlement(
    private val player: Player,
    private val bge: BlackjackGameEngine,
    private val msg: BlackjackMessages
) {
    private val blackjackPayoff: Double
    private val handDealer: HandDealer
    private val blackjackConfiguration: BlackjackConfiguration

    init {
        blackjackConfiguration = bge.config
        handDealer = bge.getCycle().dealer
        blackjackPayoff = if (bge.config.isBlackjack6to5) 1.2 else 1.5
    }

    fun settlePlayer() {
        for (handPlayer in player.hands) {
            settleHand(handPlayer)
        }
    }

    private fun settleHand(handPlayer: HandPlayer) {
        if (handPlayer.isSelectedEvenMoney) {
            settleForEvenMoney(handPlayer)
        } else {
            settleNormalHand(handPlayer)
        }
    }

    private fun settleForEvenMoney(handPlayer: HandPlayer) {
        bge.incrementStreak()
        handPlayer.message = msg.evenMsg
        handPlayer.wager.initialPayoff = handPlayer.wager.initialBet
    }

    private fun settleNormalHand(handPlayer: HandPlayer) {
        if (handPlayer.isBusted()) {
            bge.setPriorHandBustedFlag()
        } else {
            settleNonBustedHand(handPlayer)
        }
    }

    private fun settleNonBustedHand(handPlayer: HandPlayer) {
        if (handDealer.isNatural21() && blackjackConfiguration.isEuropeanStyle) {
            handPlayer.message = msg.lostMsg
            handPlayer.wager.lost()
        } else {
            settleNotEuropeanStyleBlackjackHand(handPlayer)
        }
    }

    private fun settleNotEuropeanStyleBlackjackHand(handPlayer: HandPlayer) {
        if (handPlayer.isSurrendered) {
            handPlayer.wager.lost()
            handPlayer.wager.initialPayoff = handPlayer.wager.initialBet / 2
        } else {
            settleNotSurrenderedHand(handPlayer)
        }
    }

    private fun settleNotSurrenderedHand(handPlayer: HandPlayer) {
        if (handPlayer.value == handDealer.value) {
            if (handPlayer.isNatural21()) {
                bge.incrementStreak()
                if (handDealer.isNatural21()) {
                    handPlayer.message = msg.pushMsg
                } else {
                    handPlayer.message = msg.blackjackMsg
                    handPlayer.wager.initialPayoff = (blackjackPayoff * handPlayer.wager.initialBet).toInt()
                }
            } else {
                handPlayer.message = msg.pushMsg
                bge.incrementStreak()
            }
        } else if (handDealer.value > 21 || handPlayer.value > handDealer.value) {
            bge.incrementStreak()
            if (handPlayer.isNatural21()) {
                handPlayer.message = msg.blackjackMsg
                handPlayer.wager.initialPayoff = (blackjackPayoff * handPlayer.wager.initialBet).toInt()
            } else {
                handPlayer.message = msg.wonMsg
                handPlayer.wager.initialPayoff = handPlayer.wager.initialBet
                handPlayer.wager.doubledPayoff = handPlayer.wager.doubledBet
            }
        } else {
            handPlayer.message = msg.lostMsg
            handPlayer.wager.lost()
            bge.resetStreak()
        }
    }
}

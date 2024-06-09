package com.panopset.blackjackEngine

import java.io.StringWriter

class Cycle internal constructor(
    val bge: BlackjackGameEngine,
    val strategy: Strategy
) {
    private val blackjackConfiguration: BlackjackConfiguration = bge.config
    private val countingSystems = bge.countingSystems
    val dealer = HandDealer()
    val msg: BlackjackMessages = bge.config.messages

    val players: ArrayList<Player> = ArrayList()

    fun placeBets() {
        players.clear()
        for (i in 0 until blackjackConfiguration.seats) {
            if (bge.isCountVeryNegative()) {
                bge.triggerShuffleBeforeNextHand()
            }
            val betAmount = BetAmountStrategy(bge).adjust()
            val player = Player( Wager(bge.bankroll.subtract(betAmount)))
            player.hands[0].wager.initialBet = betAmount
            players.add(player)
        }
    }

    override fun toString(): String {
        val sw = StringWriter()
        sw.append("Dealer: ")
        sw.append(String.format("%s%n", dealer.cards))
        for ((i, p) in players.withIndex()) {
            sw.append(String.format("Player %d: %s%n", i, p.toString()))
        }
        sw.append(" Bankroll:" + bge.bankroll)
        return sw.toString()
    }

    fun getActivePlayer(): Player? {
        for (p in players) {
            if (!p.isFinal) {
                return p
            }
        }
        return null
    }

    fun getRecommendedAction(): String {
        return if (dealer.isFinal || !dealer.hasCards()) {
            CMD_DEAL
        } else {
            strategy.getRecommendation(getCurrentSituation(getActivePlayerHand()))
        }
    }

    fun getStrategyLine(): StrategyLine? {
        return if (getActivePlayer() == null) {
            null
        } else {
            strategy.findStrategyLine(getCurrentSituation(getActivePlayer()!!.activeHand))
        }
    }

    val activeSituation: Situation
        get() = getCurrentSituation(getActivePlayerHand())

    fun getCurrentSituation(hand: HandPlayer?): Situation {
        return if (isDealt) {
            Situation(dealer.upCard, hand)
        } else Situation(null, hand)
    }

    fun deal() {
        placeBets()
        for (p in players) {
            p.activeHand?.dealCard(BlackjackShoe.deal(true, countingSystems))
            bge.reportNewHand()
        }
        dealer.dealCard(BlackjackShoe.deal(false, countingSystems))
        for (p in players) {
            p.activeHand?.dealCard(BlackjackShoe.deal(true, countingSystems))
        }
        dealer.dealCard(BlackjackShoe.deal(true, countingSystems))
        checkFor21()
        isDealt = true
        isActive = true
    }

    private fun checkFor21() {
        for (player in players) {
            checkFor21(player)
        }
        if (dealer.value == 21) {
            finish()
        }
    }

    private fun checkFor21(player: Player) {
        if (dealer.upCard.isAce && bge.config.isEvenMoneyOnBlackjackVace) {
            for (playerHand in player.hands) {
                if (playerHand.isNatural21()) {
                    playerHand.standWithEvenMoney()
                }
            }
        } else {
            for (playerHand in player.hands) {
                if (playerHand.value == 21) {
                    player.activeHand?.stand()
                }
            }
        }
    }

    fun getActivePlayerHand(): HandPlayer? {
        val p = getActivePlayer() ?: return null
        return p.activeHand
    }

    fun finish() {
        completeCycle()
        isActive = false
    }

    private fun completeCycle() {
        if (!dealer.isFinal) {
            prepareSettlement()
        }
    }

    private fun prepareSettlement() {
        BlackjackShoe.show(dealer.cards[0], countingSystems)
        resolvePlayerBlackjacks()
        while (!dealer.isFinal) {
            if (dealer.value < 17) {
                dealer.dealCard(bge.deal(true))
            } else if (dealer.value == 17) {
                if (blackjackConfiguration.isDealerHitSoft17 && dealer.isSoft) {
                    dealer.dealCard(bge.deal(true))
                } else {
                    dealer.stand()
                }
            } else {
                dealer.stand()
            }
        }
        bge.clearPriorHandBustedFlag()
        collectAndPayChips()
    }

    private fun resolvePlayerBlackjacks() {
        var allPlayersHaveBlackjack = true
        for (handPlayer in players) {
            if (!resolvePlayerHandsBlackjacks(handPlayer)) {
                allPlayersHaveBlackjack = false
            }
        }
        if (allPlayersHaveBlackjack) {
            dealer.stand()
        }
    }

    private fun resolvePlayerHandsBlackjacks(handPlayer: Player): Boolean {
        var allHandsAreBlackjack = true
        for (h in handPlayer.hands) {
            if (!h.isBustedOrSurrenderred()) {
                if (dealer.isNatural21() && !blackjackConfiguration.isEuropeanStyle) {
                    h.stand()
                } else {
                    if (h.isNatural21()) {
                        h.stand()
                    } else {
                        allHandsAreBlackjack = false
                    }
                }
            }
        }
        return allHandsAreBlackjack
    }

    private fun collectAndPayChips() {
        for (player in players) {
            Settlement(player, bge, msg).settlePlayer()
        }
        bge.bankroll.settle(bge.getCycle().players)
    }

    var isDealt = false
        private set
    var isActive = false
        private set

}

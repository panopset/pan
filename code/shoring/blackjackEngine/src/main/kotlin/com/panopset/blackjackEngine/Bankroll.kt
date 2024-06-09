package com.panopset.blackjackEngine

import java.lang.RuntimeException

/**
 * Your bankroll consists of how many chips you have, and what your stake is.
 *
 * All numbers are in pennies.
 *
 * Adding chips, and cashing them out, just like real life, does nothing to change your stake,
 * because chips are like cash.
 *
 * Your stake only changes as you win our lose.
 *
 */
class Bankroll {
    var reloadAmount: Int = 30000
    var reloadCount: Long = 0
    private var chips: Long = 0

    override fun toString(): String {
        return "Chips: ${getChips()}, reloadAmount: $reloadAmount, reloadCount: $reloadCount"
    }
    fun showBankrollStatus(players: ArrayList<Player>): String {
        return "Chips: ${getChips()}  stake: ${getStakeIncludingHands(players)}"
    }

    fun getStakeIncludingHands(players: List<Player>?): Long {
        return getChips() + getLiveValue(players) - (reloadAmount * reloadCount)
    }

    fun getStakeOutOfPlay(): Long {
        return getChips() - (reloadAmount * reloadCount)
    }

    fun getTableChips(players: List<Player>?): Long {
        return getChips() + getLiveValue(players)
    }

    fun getLiveValue(players: List<Player>?): Long {
        var liveValue = 0L
        if (players != null) {
            for (player in players) {
                if (!player.isSettled) {
                    for (hand in player.hands) {
                        liveValue += hand.wager.liveValue
                    }
                }
            }
        }
        return liveValue
    }

    fun subtract(value: Int): Int {
        chips -= value
        if (chips < 1) {
            reload()
        }
        return value
    }

    fun settle(players: ArrayList<Player>) {
        for (player in players) {
            if (!player.isFinal) {
                throw RuntimeException("Attempt to settle player that is not final, logic error.")
            }
            if (player.isSettled) {
                throw RuntimeException("Attempt to settle a player twice, logic error.")
            }
            for (hand in player.hands) {
                chips += hand.wager.liveValue
                player.isSettled = true
            }
        }
    }

    fun add(value: Int) {
        chips += value
    }

    fun getChips(): Long {
        if (reloadAmount == 0) {
            throw RuntimeException("Reload amount can't be 0.")
        }
        if (chips < 1) {
            reload()
        }
        return chips
    }

    fun reset() {
        chips = 0
        reloadCount = 0
        reload()
    }

    private fun reload() {
        chips += reloadAmount
        reloadCount++
    }
}

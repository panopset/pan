package com.panopset.blackjackEngine

class Wager(var initialBet: Int) {
    constructor(handWager: Wager) : this(handWager.initialBet) {
        initialPayoff = handWager.initialPayoff
        doubledBet = handWager.doubledBet
        doubledPayoff = handWager.doubledPayoff
        isDoubledDown = handWager.isDoubledDown
    }

    var initialPayoff = 0
    var doubledBet = 0
        private set
    var doubledPayoff = 0
    var isDoubledDown = false
        private set

    fun lost() {
        initialBet = 0
        doubledBet = 0
    }

    fun doubleDown() {
        doubledBet = initialBet
        isDoubledDown = true
    }

    val liveValue: Long
        get() = (initialBet + initialPayoff + doubledBet + doubledPayoff).toLong()
}

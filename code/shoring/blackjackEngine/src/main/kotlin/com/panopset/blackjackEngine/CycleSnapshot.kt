package com.panopset.blackjackEngine

class CycleSnapshot(private val bgs: BlackjackGameState) {

    var isPainted = false
        private set

    fun getPlayers(): List<Player> {
        return bgs.players
    }

    fun getGameStatusVertical(): List<String> {
        return bgs.gameStatusVertical
    }

    fun getStatusChipsVertical(): List<String> {
        return bgs.statusChipsVertical
    }

    fun setPainted() {
        isPainted = true
    }

    fun getAction(): String {
        return bgs.action
    }

    fun getStakeIncludingHands(): Long {
        return bgs.stakeIncludingHands
    }

    fun getChips(): Long {
        return bgs.chips
    }

    fun getMetrics(): Metrics {
        return bgs.metrics
    }

    fun getNextBet(): Int {
        return bgs.nextBet
    }

    fun getDealer(): HandDealer {
        return bgs.handDealer
    }

    fun getMistakeHeader(): String {
        return bgs.mistakeHeader
    }

    fun getMistakeMessage(): String {
        return bgs.mistakeMessage
    }

    fun getDealerMessage(): String {
        return bgs.dealerMessage
    }

    fun getGameStatusHorizontal(): String{
        return bgs.gameStatusHorizontal
    }

    fun getStatusChipsHorizontal(): String {
        return bgs.statusChipsHorizontal
    }
}

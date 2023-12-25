package com.panopset.blackjackEngine

data class BlackjackGameState(
    val chips: Long,
    val reloadCount: Long,
    val reloadAmount: Int,
    val action: String,
    val handDealer: HandDealer,
    val players: List<Player>,
    val metrics: Metrics,
    val nextBet: Int,
    val mistakeHeader: String,
    val mistakeMessage: String,
    val dealerMessage: String,
    val gameStatusVertical: List<String>,
    val gameStatusHorizontal: String,
    val statusChipsVertical: List<String>,
    val statusChipsHorizontal: String,
    val stakeIncludingHands: Long
)

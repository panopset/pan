package com.panopset.marin.oldswpkg.games.blackjack


class BlackjackTable(w: Int, h: Int, cardHeight: Int) {
    private var tableLayout: TableLayout
    = if (w > h) TableLandscape(w, h, cardHeight)  else TablePortrait(w, h, cardHeight)
    val statusTile = tableLayout.statusTile
    val chipTray = tableLayout.chipTray
    val playerTile = tableLayout.playerTile
    val dealerTile = tableLayout.dealerTile
    val msgTile = tableLayout.msgTile
}

package com.panopset.marin.oldswpkg.games.blackjack

import com.panopset.compat.Tile

class TableLandscape(width: Int, height: Int, cardHeight: Int) : TableLayout(width, height, cardHeight) {
    override fun assembleEverything(tile: Tile): SubTiles {
        val cardDrop = cardHeight + cardHeight / 8
        val tiles = tile.splitVertical(80, "top3", "msg")
        val top3 = tiles[0]
        val localMsgTile = tiles[1] // GRAY
        val top3tiles = top3.splitHorizontal(70, "left2", "chips")
        val left2 = top3tiles[0]
        val localChipTray = top3tiles[1] // CYAN square frame
        val left2tiles = left2.splitVerticalExactHeight(cardDrop, "top2", "player")
        val top2 = left2tiles[0]
        val top2tiles = top2.splitHorizontal(50, "status", "dealer")
        val statusTile = top2tiles[0]
        val localDealerTile = top2tiles[1] // RED
        val localPlayerTile = left2tiles[1] // YELLOW
        return SubTiles(localChipTray, localPlayerTile, localDealerTile, localMsgTile, statusTile)
    }
}

package com.panopset.marin.oldswpkg.games.blackjack

import com.panopset.compat.Tile

class TablePortrait(width: Int, height: Int, cardHeight: Int) : TableLayout(width, height, cardHeight) {
    override fun assembleEverything(tile: Tile): SubTiles {
        val tiles = tile.splitVertical(50, "top", "bot")
        val top2 = tiles[0]
        val top2tiles = top2.splitHorizontal(50, "dealer", "chips")
        val localDealerTile = top2tiles[0] //RED
        val localChipTray = top2tiles[1] //CYAN square frame
        val bot2 = tiles[1]
        val bot2tiles = bot2.splitVertical(60, "player", "msg")
        val localPlayerTile = bot2tiles[0] //YELLOW
        val botSplitTiles = bot2tiles[1].splitHorizontal(50, "rb", "msgs") //GRAY
        val localMsgTile = botSplitTiles[0]
        val localStatusTile = botSplitTiles[1]
        return SubTiles(localChipTray, localPlayerTile, localDealerTile, localMsgTile, localStatusTile)
    }
}

package com.panopset.marin.oldswpkg.games.blackjack

import com.panopset.compat.Tile

abstract class TableLayout(val w: Int, val h: Int, val cardHeight: Int) {

    private val subTiles = ae()
    val chipTray = subTiles.chipTray
    val playerTile = subTiles.playerTile
    val dealerTile = subTiles.dealerTile
    val msgTile = subTiles.msgTile
    val statusTile = subTiles.statusTile

    protected abstract fun assembleEverything(tile: Tile): SubTiles
    private fun ae(): SubTiles {
        val rtn = Tile("rtn")
        rtn.setLocation(0, 0)
        rtn.setDimensions(w, h - 50) // TODO lame workaround here.
        return assembleEverything(rtn)
    }
}

data class SubTiles(
    val chipTray: Tile, val playerTile: Tile, val dealerTile: Tile, val msgTile: Tile, val statusTile: Tile)

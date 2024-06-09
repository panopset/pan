package com.panopset.marin.oldswpkg.games.blackjack

import com.panopset.blackjackEngine.*
import com.panopset.marin.compat.util.CommandBinder
import com.panopset.marin.compat.util.PanCmd

class BlackjackCmdBinder : CommandBinder() {
    init {
        registerCommand(PanCmd("Deal", CMD_DEAL, 0))
        registerCommand(PanCmd("Hit", CMD_HIT, 0))
        registerCommand(PanCmd("Surrender", CMD_SURRENDER, 0))
        registerCommand(PanCmd("Stand", CMD_STAND, 0))
        registerCommand(PanCmd("Split", CMD_SPLIT, 0))
        registerCommand(PanCmd("Double", CMD_DOUBLE, 0))
        registerCommand(PanCmd("Increase", CMD_INCREASE, 0))
        registerCommand(PanCmd("Decrease", CMD_DECREASE, 0))
        registerCommand(PanCmd("Reset", CMD_RESET, 0))
        registerCommand(PanCmd("Shuffle", CMD_SHUFFLE, 0))
        registerCommand(PanCmd("Auto", CMD_AUTO, 0))
        registerCommand(PanCmd("Count", CMD_COUNT, 0))
    }
}

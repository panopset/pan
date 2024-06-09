package com.panopset.blackjackEngine

import java.io.StringWriter

class CycleController(val g: BlackjackGameEngine) {
    var cycle: Cycle? = null
    override fun toString(): String {
        val sw = StringWriter()
        sw.append("cycle:$cycle")
        return sw.toString()
    }

    fun getCycle(g: BlackjackGameEngine, strategy: Strategy): Cycle {
        if (cycle == null) {
            cycle = Cycle(g, strategy)
        }
        return cycle!!
    }

    @Synchronized
    fun reset() {
        cycle = null
    }

    fun cloneDealer(): HandDealer {
        return HandDealer(cycle?.dealer ?: HandDealer())
    }

    fun clonePlayers(): List<Player> {
        val clonedPlayers = ArrayList<Player>()
        if (cycle == null) {
            return clonedPlayers
        }
        for (player in cycle!!.players) {
            clonedPlayers.add(Player( player))
        }
        return clonedPlayers
    }

    val isDealt: Boolean
        get() = if (cycle == null) {
            false
        } else cycle!!.isDealt
    val isActive: Boolean
        get() = if (cycle == null) {
            false
        } else cycle!!.isActive
}

package com.panopset.blackjackEngine

/**
 * In Blackjack, there is a well defined set of situations. An instance of this class represents a
 * single situation. Each situation can be mapped to an action, based on a strategy.
 *
 */
class Situation(val dealerUpCard: BlackjackCard?, val handPlayer: HandPlayer?) {

    override fun toString(): String {
        return String.format("Dealer up: %s Hand: %s", dealerUpCard, handPlayer)
    }
}

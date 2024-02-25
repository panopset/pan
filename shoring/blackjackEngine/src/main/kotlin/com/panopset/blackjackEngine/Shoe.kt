package com.panopset.blackjackEngine

import com.panopset.blackjackEngine.CardDefinition.Companion.find
import com.panopset.compat.Logz
import com.panopset.compat.Randomop
import com.panopset.compat.Streamop
import com.panopset.compat.Stringop
import java.io.StringReader
import java.io.StringWriter

class Shoe(val numberOfDecks: Int) {
    var stackedDeck = ""
    var isBlue = false
        private set
    private val cards: MutableList<Card> = ArrayList()

    fun shuffle() {
        cards.clear()
        isBlue = DeckPile.pull()
        val decks: MutableList<Deck> = ArrayList()
        for (i in 0 until numberOfDecks) {
            decks.add(Deck(isBlue))
        }
        while (decks.isNotEmpty()) {
            val deckIndex = Randomop.random(0, decks.size - 1)
            val randomDeck = decks[deckIndex]
            cards.add(randomDeck.deal())
            if (randomDeck.remaining() == 0) {
                decks.removeAt(deckIndex)
            }
        }
        if (Stringop.isPopulated(stackedDeck)) {
            stackTheDeckFromDeckStacker(stackedDeck)
        }
    }

    fun deal(): Card {
        if (stackedDeckForTesting.isNotEmpty()) {
            Logz.warn("Stacked deck in use, for testing.")
            return stackedDeckForTesting.removeAt(0)
        }
        if (cards.size < cut()) {
            shuffle()
        }
        return cards.removeAt(0)
    }

    fun cut(): Int {
        return 20
    }

    private val stackedDeckForTesting: MutableList<Card> = ArrayList()

    init {
        shuffle()
    }

    fun isTheDeckStacked(): Boolean {
        return stackedDeckForTesting.isNotEmpty()
    }

    fun stackTheDeckFromDeckStacker(stackedDeck: String) {
        sddx(Streamop.getLinesFromReader( StringReader(stackedDeck)))
    }

    fun stackTheDeckFromArray(stackedDeck: Array<String>) {
        sddx(Stringop.arrayToList(stackedDeck))
    }

    fun stackTheDeckFromList(stackedDeck: List<Card>) {
        stackedDeckForTesting.clear()
        stackedDeckForTesting.addAll(stackedDeck)
    }

    private fun sddx(stackedDeck: List<String?>) {
        val shoeCards: MutableList<Card> = ArrayList()
        for (s in stackedDeck) {
            val cd = find(s)
            shoeCards.add(Card(cd!!))
        }
        stackTheDeckFromList(shoeCards)
    }

    fun remaining(): Int {
        return cards.size
    }

    fun dumpStack(): String {
        val sw = StringWriter()
        for (card in stackedDeckForTesting) {
            sw.append(String.format("<<%s", card))
        }
        return sw.toString()
    }
}

package com.panopset.blackjackEngine

object BlackjackShoe {
    var numberOfDecks = 1
        set(value) {
            field = value
            if (shoe.numberOfDecks != value) {
                shoe = Shoe(numberOfDecks)
            }
        }

    var shoe = Shoe(numberOfDecks)

    fun isTheDeckStacked(): Boolean {
        return shoe.isTheDeckStacked()
    }

    fun deal(isShowing: Boolean, countingSystems: CountingSystems): BlackjackCard {
        val blackjackCard = BlackjackCard(shoe.deal())
        if (isShowing) {
            blackjackCard.show()
            countingSystems.count(blackjackCard)
        }
        return blackjackCard
    }

    fun show(blackjackCard: BlackjackCard, countingSystems: CountingSystems) {
        blackjackCard.show()
        countingSystems.count(blackjackCard)
    }

    fun stackTheDeckFromDeckStacker(value: String) {
        shoe.stackTheDeckFromDeckStacker(value)
    }

    fun shuffle() {
        shoe.shuffle()
    }

    fun remaining(): Int {
        return shoe.remaining()
    }

    fun cut(): Int {
        return shoe.cut()
    }

    fun stackTheDeckFromList(cards: List<Card>) {
        shoe.stackTheDeckFromList(cards)
    }

    fun dumpStack(): String {
        return shoe.dumpStack()
    }

    fun stackTheDeckFromArray(cards: Array<String>) {
        shoe.stackTheDeckFromArray(cards)
    }

    fun stackTheDeckFromEOLseparatedText(stackRawData: String) {
        shoe.stackTheDeckFromDeckStacker(stackRawData)
    }
}

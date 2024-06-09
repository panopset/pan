package com.panopset.tests.blackjackEngine

import com.panopset.blackjackEngine.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CountingSystemsTest {
    private val countingSystems = CountingSystems(BlackjackConfigDefault())

    @Test
    @Throws(Exception::class)
    fun test() {
        countingSystems.updateByIndex(3)
        testConfig()
        countingSystems.updateByIndex(3)
        testConfig()
    }

    @Throws(Exception::class)
    private fun testConfig() {
        val dft = countingSystems.findSelected()
        countingSystems.resetCount()
        Assertions.assertEquals(8, countingSystems.keyNames.size)
        Assertions.assertEquals(0, countingSystems.getTrueCount())
        countingSystems.count(BlackjackCard(Card(CardDefinition.FIVE_OF_CLUBS)))
        Assertions.assertEquals(1, countingSystems.getTrueCount())
        countingSystems.count(BlackjackCard(Card(CardDefinition.FOUR_OF_HEARTS)))
        Assertions.assertEquals(2, countingSystems.getTrueCount())
        countingSystems.count(BlackjackCard(Card(CardDefinition.EIGHT_OF_HEARTS)))
        Assertions.assertEquals(2, countingSystems.getTrueCount())
        countingSystems.count(BlackjackCard(Card(CardDefinition.ACE_OF_HEARTS)))
        Assertions.assertEquals(1, countingSystems.getTrueCount())
        countingSystems.count(BlackjackCard(Card(CardDefinition.TEN_OF_SPADES)))
        Assertions.assertEquals(0, countingSystems.getTrueCount())
        countingSystems.count(BlackjackCard(Card(CardDefinition.THREE_OF_SPADES)))
        Assertions.assertEquals(1, countingSystems.getTrueCount())
        countingSystems.count(BlackjackCard(Card(CardDefinition.TWO_OF_HEARTS)))
        Assertions.assertEquals(2, countingSystems.getTrueCount())
        countingSystems.updateByIndex(0)
        Assertions.assertEquals("None", countingSystems.findSelected().name)
        countingSystems.updateByIndex(1)
        Assertions.assertEquals("Wizard Ace/5", countingSystems.findSelected().name)
        countingSystems.updateByIndex(2)
        val ko = countingSystems.findSelected()
        Assertions.assertEquals("KO", ko.name)
        countingSystems.updateByIndex(3)
        val hiLo = countingSystems.findSelected()
        Assertions.assertEquals(hiLo.hashCode(), dft.hashCode())
        Assertions.assertEquals(hiLo, dft)
        Assertions.assertNotEquals(hiLo, ko)
        Assertions.assertEquals("Hi-Lo", hiLo.name)
        countingSystems.updateByIndex(4)
        Assertions.assertEquals("Hi-Opt I", countingSystems.findSelected().name)
        countingSystems.updateByIndex(5)
        Assertions.assertEquals("Hi-Opt II", countingSystems.findSelected().name)
        countingSystems.updateByIndex(6)
        Assertions.assertEquals("Zen Count", countingSystems.findSelected().name)
        countingSystems.updateByIndex(7)
        Assertions.assertEquals("Omega II", countingSystems.findSelected().name)
        Assertions.assertEquals(7, countingSystems.getKeyNamePosition("Omega II"))
        Assertions.assertEquals(3, countingSystems.getKeyNamePosition("foo"))
        Assertions.assertEquals("Hi-Lo", countingSystems.findSelectionKey(-1))
    }
}

package com.panopset.desk.games.bj

import com.panopset.fxapp.*
import com.panopset.marin.games.blackjack.BlackjackGameController
import com.panopset.marin.games.blackjack.BlackjackGameEngineFactory
import javafx.scene.control.TextField

class BlackjackFxControls(val fxDoc: FxDoc) {

    val taCountingSystems = createPanTextArea()
    val taBasicStrategy = createPersistentPanTextArea(fxDoc, "taBasic", "", "")
    val cbCountingSystems = createPanChoiceBox(fxDoc, "cb_counting_systems")

    val countPositive = createPanInputTextFieldWithDefaultValue(fxDoc, "count_positive", "10",
        "", "")
    val countNegative = createPanInputTextFieldWithDefaultValue(fxDoc, "count_negative", "-10",
        "", "")


    val minimumBet = createPanInputTextFieldWithDefaultValue(fxDoc, "minimum_bet", "5", "", "")
    val largeBet = createPanInputTextFieldWithDefaultValue(fxDoc, "large_bet", "20", "", "")
    val targetStake = createPanInputTextFieldWithDefaultValue(fxDoc, "target_stake", "10000", "", "")

    val cbDecks = createPanChoiceBox(fxDoc, "cb_decks")
    val cbSeats = createPanChoiceBox(fxDoc, "cb_seats")
    val ruleEuropeanStyle = createPanCheckBox(fxDoc, "rule_is", "European Style.",
        "Player always loses to dealer Blackjack, even in a push.")
    val resplitAces = createPanCheckBox(fxDoc, "rspltaces", "Resplit Aces")
    val ruleVariations = createPanCheckBox(fxDoc, "rule_vs", "Variations.")
    val dealerHitsSoft17 = createPanCheckBox(fxDoc, "dealer_hits_soft_17", "Dealer hits soft 17.", "")
    val ruleShowCount = createPanCheckBox(fxDoc, "rule_sc", "Show count.")
    val rule65 = createPanCheckBox(fxDoc, "rule_65", "Blackjack pays 6 to 5.",
        "Blackjack pays 6 to 5 instead of 3 to 2")
    val ruleEvenMoney = createPanCheckBox(fxDoc, "rule_even",
        "Player takes even money, blackjack versus ace. (All the books say this is a bad idea.)")
    val doubleAfterSplit = createPanCheckBox(fxDoc, "dblaftrsplit", "Double after split")
    val ruleLateSurrender = createPanCheckBox(fxDoc, "rule_ls", "Late surrender.")
    val ruleFastDeal = createPanCheckBox(fxDoc, "rule_fd", "Fast deal.",
        "Skip showing hand result and just go to the next deal.  Having this option, by the way, " +
                "is the whole reason I wrote this game.")
    val betIdeaDoubleAfterBust = createPanCheckBox(fxDoc, "bet_idea_dab", "Bets*: Double after bust.")
    val betideaLetItRide = createPanCheckBox(fxDoc, "bet_idea_lir", "Bets*: Let it ride after two wins.")
    val reloadAmount: TextField = createPanInputTextFieldWithDefaultValue(fxDoc, "reload_amount", "300",
        "Enter your initial stake.", "Initial stake, and reload amount, in dollars.")
    val bge = BlackjackGameEngineFactory().create(this)


    val bgc = BlackjackGameController(this)

    init {
        dealerHitsSoft17.isSelected = true
        setChoiceBoxChoices(cbDecks, "1", "2", "6", "8")
        setChoiceBoxChoices(cbSeats, "1", "2", "3", "4", "5", "6", "7")
    }

    fun update() {
        bgc.update()
    }
}

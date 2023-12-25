package com.panopset.blackjackEngine

class BlackjackMessagesDft : BlackjackMessages {
    override val wonMsg: String
        get() = "Won"
    override val lostMsg: String
        get() = "Lost"
    override val pushMsg: String
        get() = "Push"
    override val evenMsg: String
        get() = "Even money"
    override val blackjackMsg: String
        get() = "Blackjack"
    override val bustedMsg: String
        get() = "Busted"
    override val doubleImpossibleMsg: String
        get() = "Double not possible here"
    override val surrenderNotAllowedMsg: String
        get() = "Surrender not allowed in this casino"
    override val surrenderImpossibleMsg: String
        get() = "Surrender not possible here"
    override val handActiveMsg: String
        get() = "Hand is still active"
    override val shuffledMsg: String
        get() = "Shuffled"
    override val pleaseSelectMsg: String
        get() = "Please select"
    override val dealMsg: String
        get() = "Deal"
    override val resetMsg: String
        get() = "Reset"
}

package com.panopset.blackjackEngine

class Metrics() {
    var mistakeCount = 0
    var handCount = 0
    var handsSinceLastMistake = 0
    private var handsSinceLastMistakeRecord = 0
    private var userDriven = true

    constructor(metrics: Metrics) : this() {
        mistakeCount = metrics.mistakeCount
        handCount = metrics.handCount
        handsSinceLastMistake = metrics.handsSinceLastMistake
        handsSinceLastMistakeRecord = metrics.handsSinceLastMistakeRecord
        userDriven = metrics.userDriven
    }

    fun reset() {
        mistakeCount = 0
        handCount = 0
        handsSinceLastMistake = 0
        userDriven = true
    }

    fun incrementMistakeCount() {
        mistakeCount++
        handsSinceLastMistake = 0
        userDriven = true
    }

    private fun setHandsSinceLastMistakeRecord(value: Int) {
        if (!userDriven) {
            return
        }
        if (handsSinceLastMistakeRecord < value) {
            handsSinceLastMistakeRecord = value
        }
    }

    fun getHandsSinceLastMistakeRecord(): Int {
        return handsSinceLastMistakeRecord
    }

    fun reportNewHand() {
        handCount++
        handsSinceLastMistake++
        setHandsSinceLastMistakeRecord(handsSinceLastMistake)
    }

    fun reportNewHandAutomatic() {
        userDriven = false
        reportNewHand()
    }
}

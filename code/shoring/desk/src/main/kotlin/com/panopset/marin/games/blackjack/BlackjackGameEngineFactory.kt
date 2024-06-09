package com.panopset.marin.games.blackjack

import com.panopset.blackjackEngine.BlackjackConfigDefault
import com.panopset.blackjackEngine.BlackjackGameEngine
import com.panopset.compat.Logz
import com.panopset.compat.Stringop
import com.panopset.desk.games.bj.BlackjackFxControls
import java.util.ArrayList

class BlackjackGameEngineFactory {
    fun create(ctls: BlackjackFxControls): BlackjackGameEngine {
        val rtn = object: BlackjackGameEngine(ctls.fxDoc, object : BlackjackConfigDefault() {
            val blackjackConfigDefault = BlackjackConfigDefault()

            override val isDoubleAfterSplitAllowed: Boolean
                get() = ctls.doubleAfterSplit.isSelected
            override val isResplitAcesAllowed: Boolean
                get() = ctls.resplitAces.isSelected
            override var strategyData: ArrayList<String>
                get() {
                    val dft = super.strategyData
                    val ta = ctls.taBasicStrategy
                    val text = ta.text
                    if (Stringop.isBlank(text)) {
                        return dft
                    }
                    val rtn = Stringop.stringToList( text)
                    if (rtn != dft) {
                        Logz.warn("Custom (or out of date) strategy data in use.  Go to config to reset.")
                    }
                    return rtn
                }
                set(strategyData) {
                    super.strategyData = strategyData
                }
            override var countingSystemData: ArrayList<String>
                get() {
                    val dft = super.countingSystemData
                    val ta = ctls.taCountingSystems
                    val text = ta.text
                    if (Stringop.isBlank(text)) {
                        return dft
                    }
                    val rtn = Stringop.stringToList( text)
                    if (rtn != dft) {
                        Logz.warn("Custom (or out of date) counting system data in use.  Go to config to reset.")
                    }
                    return rtn
                }
                set(countingSystemData) {
                    super.countingSystemData = countingSystemData
                }
            override val seats: Int
                get() = Stringop.parseInt( ctls.cbSeats.value, 1)
            override val decks: Int
                get() = Stringop.parseInt( ctls.cbDecks.value, 1)
            override val isDealerHitSoft17: Boolean
                get() = ctls.dealerHitsSoft17.isSelected
            override val isBlackjack6to5: Boolean
                get() = ctls.rule65.isSelected
            override val isEvenMoneyOnBlackjackVace: Boolean
                get() = ctls.ruleEvenMoney.isSelected
            override val isLateSurrenderAllowed: Boolean
                get() = ctls.ruleLateSurrender.isSelected
            override val isEuropeanStyle: Boolean
                get() = ctls.ruleEuropeanStyle.isSelected
            override val isFastDeal: Boolean
                get() = ctls.ruleFastDeal.isSelected
            override val isBasicStrategyVariationsOnly: Boolean
                get() = ctls.ruleVariations.isSelected
            override var isShowCount: Boolean
                get() = ctls.ruleShowCount.isSelected
                set(isShowCount) {
                    super.isShowCount = isShowCount
                }

            override fun toggleShowCount() {
                ctls.ruleShowCount.isSelected = !ctls.ruleShowCount.isSelected
            }

            override val largeBetInWholeDollars: Int
                get() = Stringop.parseInt(
                    ctls.largeBet.text,
                    blackjackConfigDefault.largeBetInWholeDollars
                )
            override val targetStakeInWholeDollars: Int
                get() = Stringop.parseInt(
                    ctls.targetStake.text,
                    blackjackConfigDefault.targetStakeInWholeDollars
                )
            override val minimumBetInWholeDollars: Int
                get() = Stringop.parseInt(
                    ctls.minimumBet.text,
                    blackjackConfigDefault.minimumBetInWholeDollars
                )
            override val betIncrementInWholeDollars: Int
                get() = Stringop.parseInt(
                    ctls.minimumBet.text,
                    blackjackConfigDefault.betIncrementInWholeDollars
                )
            override val reloadAmountInWholeDollars: Int
                get() = Stringop.parseInt(
                    ctls.reloadAmount.text,
                    blackjackConfigDefault.reloadAmountInWholeDollars
                )
            override val strategicVeryPositiveCount: Int
                get() {
                    val rtn = ctls.countPositive.text
                    if (rtn.isEmpty()) {
                        return super.strategicVeryPositiveCount
                    }
                    return rtn.toInt()
                }
            override val strategicVeryNegativeCount: Int
                get() {
                    val rtn = ctls.countNegative.text
                    if (rtn.isEmpty()) {
                        return super.strategicVeryNegativeCount
                    }
                    return rtn.toInt()
                }
            override val isBetIdeaDoubleAfterBust: Boolean
                get() = ctls.betIdeaDoubleAfterBust.isSelected
            override val isBetIdeaLetItRideAfterTwoWins: Boolean
                get() = ctls.betideaLetItRide.isSelected
        }) {
            override fun exec(action: String) {
                super.exec(action)
                ctls.update()
            }
        }
        return rtn
    }
}

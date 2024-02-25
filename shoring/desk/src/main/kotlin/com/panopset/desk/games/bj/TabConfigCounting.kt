package com.panopset.desk.games.bj

import com.panopset.blackjackEngine.DefaultResources
import com.panopset.compat.Logz
import com.panopset.compat.Stringop
import com.panopset.fxapp.*
import javafx.scene.control.Tab
import javafx.scene.layout.BorderPane

class TabConfigCounting(val ctls: BlackjackFxControls) {

    fun createTab(): Tab {
        val rtn = FontManagerFX.registerTab(Tab("Counting"))
        val topHbox = createPanHBox(
            createPanVBox(
                createPanTitledPane("Counting system", ctls.cbCountingSystems),
                createPanTitledPane("Positive count large bet trigger", ctls.countPositive),
                createPanTitledPane("Negative count bail trigger", ctls.countNegative),
                createPanLabel("Set both count triggers to 0, to turn off card counting bet adjustments."),
                createPanLabel("Counting system simulations work best with one player."),
                createPanFlowPane(
                    createPanButton(ctls.fxDoc, {userReset(ctls)}, "Reset to default.", false, ""),
                    createPanLabel("  Counting systems:")
                )
            ),
            createPanVBox(
                createPanTitledPane("Minimum Bet", ctls.minimumBet),
                createPanTitledPane("Large Bet", ctls.largeBet),
                createPanTitledPane("Target Stake", ctls.targetStake),
            )
        )
        topHbox.spacing = 5.0
        ctls.taCountingSystems.text = defaultCountingSystems
        val bp = BorderPane()
        bp.top = topHbox
        bp.center = createPanScrollPane(ctls.taCountingSystems)
        rtn.content = bp



        val countingSystems = ctls.bge.countingSystems
        val countingSystemsText = ctls.taCountingSystems.text
        if (!Stringop.isPopulated(countingSystemsText)) {
            userReset(ctls)
        }
        setChoiceBoxChoices(ctls.cbCountingSystems, countingSystems.keyNames)
        ctls.cbCountingSystems.selectionModel.selectedIndexProperty()
            .addListener { _, _, newIndex ->
                Thread { // TODO: Replace Thread.
                    val selectedIndex = newIndex.toInt()
                    countingSystems.setSystemByKeyNamePosition(selectedIndex)
                    Logz.info(String.format("Counting systems updated to %s.", countingSystems.findSelected().name))
                }.start()
            }

        ctls.cbCountingSystems.selectionModel.select(3)
        return rtn
    }

    private fun userReset(ctls: BlackjackFxControls) {
        ctls.taCountingSystems.text = defaultCountingSystems
    }
    private val defaultCountingSystems = DefaultResources().defaultCountingSystems
}


package com.panopset.marin.games.blackjack

import com.panopset.blackjackEngine.CycleSnapshot
import com.panopset.desk.games.bj.BlackjackFxControls
import com.panopset.fxapp.FontManagerFX
import javafx.animation.AnimationTimer
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.canvas.Canvas
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import java.util.*

class BlackjackGameController(ctls: BlackjackFxControls) {

    val bge = ctls.bge
    var felt = Canvas()

    init {
        bge.zombie.addStopAction { timer.stop() }
        if (bge.bankroll.reloadAmount == 0) {
            bge.frontEndPreInitCheck()
        }
        startPaintCycle()
    }

    private fun handleKey(keyEvent: KeyEvent) {
        bge.exec(keyEvent.text.lowercase(Locale.getDefault()))
    }

    private var fontSize = 0
    private var gameStarted = false
    private var paintedSnapshot: CycleSnapshot? = null
    private var dirty = true
    var timer: AnimationTimer = object : AnimationTimer() {
                    override fun handle(now: Long) {
                        if (now - lastUpdate > 5000000L) {
                            paintFelt()
                            lastUpdate = now
                        }
                    }
                }

    var lastUpdate: Long = 0
    private fun startPaintCycle() {
        Platform.runLater { timer.start() }
    }

    private fun isDirty(): Boolean {
        if (!dirty) {
            if (paintedSnapshot != bge.lastActionSnapshot) {
                paintedSnapshot = bge.lastActionSnapshot
                dirty = true
            }
            if (fontSize != FontManagerFX.size) {
                fontSize = FontManagerFX.size
                dirty = true
            }
        }
        return dirty
    }

    fun update() {
        dirty = true
    }

    var binding = false

    private fun paintFelt(): CycleSnapshot? {
        if (binding) {
            return null
        }
        val layoutHeight = felt.parent.layoutBounds.height.toInt()
        val layoutWidth = felt.parent.layoutBounds.width.toInt()
        if (layoutHeight < 10 || layoutWidth < 10) {
            return null
        }
        felt.width = layoutWidth.toDouble()
        felt.height = layoutHeight.toDouble()
        val g = felt.graphicsContext2D

//            g.fill = Color.BLANCHEDALMOND
//            g.fillRect(0.0, 0.0, layoutWidth.toDouble(), layoutHeight.toDouble())
//            g.fill = Color.DARKGREEN
//            g.fillText("diags", 100.0, 100.0)

        if (!bge.isActive()) {
            g.fill = Color.DARKRED
            g.fillRect(0.0, 0.0, layoutWidth.toDouble(), layoutHeight.toDouble())
            return null
        }
        val rtn = bge.lastActionSnapshot
        if (!gameStarted) {
            felt.scene.onKeyPressed = EventHandler { keyEvent: KeyEvent -> handleKey(keyEvent) }
            gameStarted = true
        }
        if (!isDirty()) {
            return null
        }
        fp.draw(rtn, g, layoutWidth, layoutHeight)
        dirty = false
        return rtn
    }

    private var fp: FeltPainter = FeltPainter()

}

package com.panopset.marin.games.blackjack

import com.panopset.blackjackEngine.CycleSnapshot
import com.panopset.blackjackEngine.promptDeal
import com.panopset.compat.LogDisplayer
import com.panopset.compat.Logz
import com.panopset.compat.Stringop
import com.panopset.compat.Tile
import com.panopset.fxapp.FontManagerFX
import com.panopset.marin.game.card.images.BlackjackImages
import com.panopset.marin.oldswpkg.games.blackjack.*
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.Font
import java.io.StringWriter
import java.util.*

class FeltPainter {
    private fun updateTable(w: Int, h: Int) {
        if (weDontHaveEnoughRoomToWorkWith(w, h)) {
            return
        }
        blackjackTable = BlackjackTable(w, h, cptr.cardHeight)
    }

    private var chpptr = FxChipPainter()
    private var cptr = FxCardPainter()
    private var verticalSeparator = 0

    private var lastWidth = 600
    private var lastHeight = 500

    private var blackjackTable = BlackjackTable(600, 500, cptr.cardHeight)

    fun draw(logDisplayer: LogDisplayer, cs: CycleSnapshot?, g: GraphicsContext, width: Int, height: Int) {
        if (cs == null || width.toDouble() == 0.0 || height.toDouble() == 0.0) {
            return
        }
        if (!(width == lastWidth && height == lastHeight)) {
            updateTable(width, height)
            lastWidth = width
            lastHeight = height
        }
        verticalSeparator = (FontManagerFX.size * 1.2).toInt()
        if (Stringop.isPopulated(cs.getMistakeMessage())) {
            g.fill = Color.DARKRED
        } else {
            g.fill = Color.DARKGREEN
        }
        g.fillRect(0.0, 0.0, width.toDouble(), height.toDouble())
        paintChips(g, blackjackTable.chipTray, cs)
        paintStatus(cs, g, blackjackTable.statusTile)
        paintDealer(g, blackjackTable.dealerTile, cs)
        paintPlayer(logDisplayer, g, blackjackTable.playerTile, cs)
        paintMsgLandscape(cs, g, blackjackTable.msgTile)
    }

    private fun paintMsgLandscape(cs: CycleSnapshot, g: GraphicsContext, t: Tile) {
        initGforMsg(cs, g, t)
        val y = t.bottom - verticalSeparator / 2
        paintMistakeMessage(cs, g, t, y, verticalSeparator)
    }

    private fun initGforMsg(cs: CycleSnapshot, g: GraphicsContext, t: Tile) {
        if (dbg) {
            g.fill = Color.GRAY
            g.fillRect(t.left.toDouble(), t.top.toDouble(), t.width.toDouble(), t.bottom.toDouble())
        }
        g.fill = Color.BLACK
        g.font = font0
        if (Stringop.isPopulated(cs.getMistakeMessage())) {
            g.fill = Color.YELLOW
        } else {
            g.fill = Color.DARKBLUE
        }
        g.font = font0
    }

    private fun paintMistakeMessage(cs: CycleSnapshot, g: GraphicsContext, t: Tile, y: Int, vs: Int) {
        if (Stringop.isPopulated(cs.getMistakeMessage())) {
            g.fill = Color.YELLOW
            g.font = Font.font("Monospaced", FontManagerFX.size.toDouble())
            g.fillText(String.format("%s - dealer up card.", cs.getMistakeHeader()), 0.0, y.toDouble())
            g.fillText(
                String.format("%s - basic strategy line for your hand.", cs.getMistakeMessage()),
                0.0, (y + vs).toDouble()
            )
            g.font = font0
        } else if (Stringop.isPopulated(cs.getDealerMessage())) {
            g.fill = Color.WHITE
            g.fillText(cs.getDealerMessage(), 0.0, y.toDouble())
            Logz.warn(cs.getDealerMessage())
        }
        val newY = y - vs
        paintKeyMeanings(g, t.left, newY)
    }

    private fun paintKeyMeanings(g: GraphicsContext, x: Int, y: Int) {
        g.font = font0
        g.fill = Color.LIGHTYELLOW
        g.fillText(commander.toString(), x.toDouble(), y.toDouble())
    }

    val commander = BlackjackCmdBinder()
    private fun paintDealer(g: GraphicsContext, t: Tile, cs: CycleSnapshot) {
        if (dbg) {
            g.fill = Color.RED
            g.fillRect(t.left.toDouble(), t.top.toDouble(), t.width.toDouble(), t.bottom.toDouble())
        }
        val o = LayoutDealer(t, intArrayOf(cptr.cardWidth, cptr.cardHeight), cs)
        g.fill = Color.BLACK

        val iterator = cs.getDealer().cards.iterator()
        while (iterator.hasNext()) {
            val bc = iterator.next()
            o.nextDealerX?.let { cptr.paintCard(g, bc.card, it, 0) }
        }
    }

    private fun paintPlayer(logDisplayer: LogDisplayer, g: GraphicsContext, t: Tile, cs: CycleSnapshot) {
        if (dbg) {
            g.fill = Color.YELLOW
            g.fillRect(t.left.toDouble(), t.top.toDouble(), t.width.toDouble(), t.bottom.toDouble())
        }
        g.fill = Color.BLACK
        val o = LayoutPlayer(
            t, intArrayOf(cptr.cardWidth, cptr.cardHeight), cs, intArrayOf(
                chpptr.chipWidth, chpptr.chipHeight
            )
        )
        var cardX = o.playerCardXstart!!
        var arrowHasBeenDrawn = false
        if (cs.getPlayers().isEmpty()) {
            logDisplayer.dspmsg(promptDeal)
        } else {
            logDisplayer.clear()
        }
        for (p in cs.getPlayers()) {
            val activeHand = p.activeHand
            for (h in p.hands) {
                val handFirstCardX = cardX
                val iterator = h.cards.iterator()
                while (iterator.hasNext()) {
                    val bc = iterator.next()
                    cptr.paintCard(g, bc.card, cardX, o.playerY!! - o.cardH)
                    cardX += o.cardSpacer
                }
                cardX += o.cardW
                val sm = StringWriter()
                sm.append(h.message)
                var fg00 = Color.BLACK
                var fg01 = Color.WHITE
                var yloc = o.playerChipY!!
                if (h.wager.initialBet > 0) {
                    chpptr.paintChips(g, handFirstCardX, yloc, h.wager.initialBet.toLong())
                    if (h.isDoubleDowned) {
                        chpptr.paintChips(
                            g, handFirstCardX + chpptr.chipWidth + chpptr.chipHeight,
                            yloc, h.wager.doubledBet.toLong()
                        )
                    }
                }
                yloc = o.playerChipY!! - chpptr.chipWidth
                if (h.wager.initialPayoff > 0) {
                    chpptr.paintChips(g, handFirstCardX, yloc, h.wager.initialPayoff.toLong())
                }
                if (h.isDoubleDowned) {
                    if (h.wager.doubledPayoff > 0) {
                        chpptr.paintChips(
                            g,
                            handFirstCardX + chpptr.chipWidth + chpptr.chipHeight,
                            yloc,
                            h.wager.doubledPayoff.toLong()
                        )
                    }
                }
                if (activeHand != null && h == activeHand && !h.isFinal) {
                    fg00 = Color.BLACK
                    fg01 = Color.YELLOW
                    if (!arrowHasBeenDrawn) {
                        g.drawImage(
                            BlackjackImages.arrowImg, (handFirstCardX + o.chipW).toDouble(),
                            o.playerChipY!!.toDouble()
                        )
                        arrowHasBeenDrawn = true
                    }
                }
                g.font = font1
                g.fill = fg00
                g.fillText(sm.toString(), handFirstCardX.toDouble(), o.playerMsgY!!.toDouble())
                g.fill = fg01
                g.fillText(sm.toString(), (handFirstCardX + 1).toDouble(), (o.playerMsgY!! + 1).toDouble())
            }
        }
    }

    private fun paintStatus(cs: CycleSnapshot, g: GraphicsContext, t: Tile) {
        if (dbg) {
            g.fill = Color.BLUE
            g.fillRect(t.left.toDouble(), t.top.toDouble(), t.width.toDouble(), t.height.toDouble())
        }
        g.fill = Color.WHITE
        g.font = font0
        var y = t.top + verticalSeparator
        for (s in cs.getGameStatusVertical()) {
            g.fillText("  " + s.trim { it <= ' ' }, t.left.toDouble(), y.toDouble())
            y += verticalSeparator
        }
        g.fillText(
            String.format("  Action: %s", cs.getAction().uppercase(Locale.getDefault())),
            t.left.toDouble(),
            y.toDouble()
        )
    }

    var dbg = false
    fun paintChips(g: GraphicsContext, t: Tile?, cs: CycleSnapshot) {
        if (t == null) {
            return
        }
        if (dbg) {
            g.fill = Color.CYAN
            g.fillRect(t.left.toDouble(), t.top.toDouble(), t.width.toDouble(), t.height.toDouble())
        }
        g.fill = Color.DARKCYAN
        g.fillRoundRect(t.left.toDouble(), t.top.toDouble(), t.width.toDouble(), t.height.toDouble(), 20.0, 20.0)
        g.fill = Color.BLACK
        val lc = LayoutChips(t.left, t.bottom, chpptr.chipWidth, chpptr.chipHeight)
        chpptr.paintChips(g, lc.chipXnextBet, lc.chipY, cs.getNextBet().toLong())
        chpptr.paintChips(g, lc.chipXstack, lc.chipY, cs.getChips() - cs.getNextBet())
        g.fill = Color.WHITE
        g.font = font0
        var y = t.top + verticalSeparator
        for (s in cs.getStatusChipsVertical()) {
            g.fillText("  " + s.trim { it <= ' ' }, t.left.toDouble(), y.toDouble())
            y = y + verticalSeparator
        }
        for (s in cs.getGameStatusVertical()) {
            g.fillText("  " + s.trim { it <= ' ' }, t.left.toDouble(), y.toDouble())
            y = y + verticalSeparator
        }
    }

    private val font0 = FontManagerFX.getPlainSerif()
    private val font1 = FontManagerFX.getBoldSerif()
    private val fontMono = FontManagerFX.getMonospace()
}

fun weDontHaveEnoughRoomToWorkWith(w: Int, h: Int): Boolean {
    return w < 200 || h < 200
}

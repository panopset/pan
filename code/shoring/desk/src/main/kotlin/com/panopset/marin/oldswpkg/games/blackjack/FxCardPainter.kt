package com.panopset.marin.oldswpkg.games.blackjack

import com.panopset.blackjackEngine.Card
import com.panopset.blackjackEngine.Suit
import com.panopset.compat.Logz
import com.panopset.fxapp.FontManagerFX
import com.panopset.marin.game.card.images.BlackjackImages
import com.panopset.marin.oldswpkg.cards.pngmap.CardLocation
import com.panopset.marin.oldswpkg.cards.pngmap.CardPainter
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.paint.Color

class FxCardPainter() {
    private val cp: CardPainter
    private val sck: CardPathKeys
    private val cw: Int
    private val ch: Int
    val order: Array<Suit>
        get() = cp.order

    fun paintCard(g: GraphicsContext, cl: CardLocation) {
        // paintCardBitmap(g, cl.getCard(), cl.x, cl.y, hlt);
        paintCard(g, cl.card, cl.x, cl.y)
    }

    fun paintCard(g: GraphicsContext, card: Card, x: Int, y: Int) {
        // paintCardBitmap(g, card, x, y, Color.BLACK);
        paintCardSvg(g, card, x, y)
    }

    fun getCardPath(card: Card?): String {
        if (card == null) {
            return ""
        }
        return sck.getImage(card)?.url ?: ""
    }

    private fun paintCardSvg(g: GraphicsContext, card: Card, x: Int, y: Int) {
        if (card.isShowing) {
            g.drawImage(sck.getImage(card), x.toDouble(), y.toDouble(), cw.toDouble(), ch.toDouble())
        } else {
            if (card.isBlue) {
                g.drawImage(sck.backBlue, x.toDouble(), y.toDouble(), cw.toDouble(), ch.toDouble())
            } else {
                g.drawImage(sck.backRed, x.toDouble(), y.toDouble(), cw.toDouble(), ch.toDouble())
            }
        }
    }

    fun paintCardBitmap(g: GraphicsContext, card: Card, x: Int, y: Int, hlt: Color?) {
        val b = cp.getPaintDimensionsFX(card, x, y)
        val arc = 10
        if (card.isShowing) {
            g.drawImage(
                mapImg,
                b[0].toDouble(),
                b[1].toDouble(),
                b[2].toDouble(),
                b[3].toDouble(),
                b[4].toDouble(),
                b[5].toDouble(),
                b[6].toDouble(),
                b[7].toDouble()
            )
        } else {
            if (card.isBlue) {
                g.fill = Color.BLUE
            } else {
                g.fill = Color.RED
            }
            g.fillRoundRect(
                b[4].toDouble(),
                b[5].toDouble(),
                b[6].toDouble(),
                b[7].toDouble(),
                arc.toDouble(),
                arc.toDouble()
            )
            for (i in 0 until border!!) {
                g.strokeRect(
                    (b[4] + i).toDouble(),
                    (b[5] + i).toDouble(),
                    (b[6] - i * 2).toDouble(),
                    (b[7] - i * 2).toDouble()
                )
            }
        }
        g.fill = hlt
        g.strokeRect(b[4].toDouble(), b[5].toDouble(), b[6].toDouble(), b[7].toDouble())
    }

    var border: Int? = null
        get() {
            if (field == null) {
                field = if (cardWidth > 30) {
                    cardWidth / 30
                } else {
                    1
                }
            }
            return field
        }
        private set
    val cardHeight: Int
        get() = cp.cardHeight
    val cardWidth: Int
        get() = cp.cardWidth
    private var mapImg: Image? = null

    init {
        val mw = getMapImg()!!.width
        val mh = getMapImg()!!.height
        cp = CardPainter(mw.toInt(), mh.toInt())
        //    sck = new CardPathKeys(BASE_PATH, "svg");
        sck = CardPathKeys( BASE_PATH, "png")
        ch = (98 * FontManagerFX.svgRatio).toInt()
        cw = (73 * FontManagerFX.svgRatio).toInt()
    }

    private fun getMapImg(): Image? {
        if (mapImg == null) {
            mapImg = BlackjackImages.cardMap
        }
        return mapImg
    }

    companion object {
        //private static final String BASE_PATH = "/com/panopset/marin/game/card/svg/poker-qr-plain/";
        private const val BASE_PATH = "/com/panopset/marin/game/card/lg/"
    }
}

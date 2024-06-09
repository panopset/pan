package com.panopset.marin.game.card.images

import javafx.scene.image.Image

object BlackjackImages {
    /**
     * Default card image is the one from jfitz.com.
     */
    private const val DFT_CARD_IMAGE = "/com/panopset/marin/game/card/images/jfitzcards.png"


    /**
     * Panopset owned image, (which means it is open source) created by Karl Dinwiddie 2012-05-19.
     */
    private const val DFT_CHIP_IMAGE = "/com/panopset/marin/game/card/images/chips.png"

    /**
     * Panopset owned image.
     */
    private const val ARROW_IMAGE = "/com/panopset/marin/game/card/images/arrow.png"

    val arrowImg = Image(javaClass.getResource(ARROW_IMAGE)?.toExternalForm())
    val cardMap = Image(javaClass.getResource(DFT_CARD_IMAGE)?.toExternalForm())
    val chipMap = Image(javaClass.getResource(DFT_CHIP_IMAGE)?.toExternalForm())
}

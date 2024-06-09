package com.panopset.blackjackEngine

import com.panopset.compat.Streamop.getTextFromStream

class DefaultResources() {

    private val defaultBasicStrategyPrivate: String
        get() = getTextFromStream( this.javaClass.getResourceAsStream(DEFAULT_STRATEGY_RSRC))

    private val defaultCountingSystemsPrivate: String
        get() = getTextFromStream( this.javaClass.getResourceAsStream(DEFAULT_COUNTING_SYSTEMS_RSRC))


        val defaultBasicStrategy: String
            get() = defaultBasicStrategyPrivate

        val defaultCountingSystems: String
            get() = defaultCountingSystemsPrivate
}

private const val DEFAULT_STRATEGY_RSRC = "/basic.txt"

private const val DEFAULT_COUNTING_SYSTEMS_RSRC = "/cs.txt"


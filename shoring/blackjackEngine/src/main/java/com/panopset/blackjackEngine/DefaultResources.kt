package com.panopset.blackjackEngine

import com.panopset.compat.Streamop.getTextFromStream

enum class DefaultResources {
    INSTANCE;

    private val defaultBasicStrategyPrivate: String
        get() = getTextFromStream(this.javaClass.getResourceAsStream(DEFAULT_STRATEGY_RSRC))

    private val defaultCountingSystemsPrivate: String
        get() = getTextFromStream(this.javaClass.getResourceAsStream(DEFAULT_COUNTING_SYSTEMS_RSRC))

    companion object {
        private const val DEFAULT_STRATEGY_RSRC = "/basic.txt"

        private const val DEFAULT_COUNTING_SYSTEMS_RSRC = "/cs.txt"

        @JvmStatic
        val defaultBasicStrategy: String
            get() = INSTANCE.defaultBasicStrategyPrivate

        @JvmStatic
        val defaultCountingSystems: String
            get() = INSTANCE.defaultCountingSystemsPrivate
    }
}

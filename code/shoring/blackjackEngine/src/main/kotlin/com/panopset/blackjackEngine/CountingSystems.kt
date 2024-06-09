package com.panopset.blackjackEngine

import com.panopset.compat.Logz
import java.util.*

class CountingSystems(blackjackConfiguration: BlackjackConfiguration) : Configurable(blackjackConfiguration) {

    private var systemCatalog: MutableMap<String, CountingSystem> = Collections.synchronizedSortedMap(TreeMap())
    private var keys = Collections.synchronizedSortedSet(TreeSet<CountingSystem>())
    var keyNames = ArrayList<String>()

    init {
        populate()
        populateKeyNames()
    }

    private fun populateKeyNames() {
        for ((_, value) in systemCatalog.entries) {
            keys.add(value)
        }
        for (cs in keys) {
            keyNames.add(cs.name)
        }
    }

    fun getTrueCount(): Int {
        return findSelected().count
    }

    fun count(blackjackCard: BlackjackCard) {
        findSelected().count(blackjackCard)
    }

    private fun populate() {
        systemCatalog.clear()
        var index = 0
        for (s in blackjackConfiguration.countingSystemData) {
            if (s.length <= NAME_POS + 1 || s.indexOf("#") == 0) {
                continue
            }
            val key = s.substring(NAME_POS).trim()
            val dta = s.substring(0, NAME_POS)
            systemCatalog[key] = CountingSystem( key, dta, index++)
        }
    }

    fun setSystemByKeyNamePosition(position: Int) {
        setCountingSystem(position)
    }

    fun updateByIndex(i: Int) {
        setSystemByKeyNamePosition(i)
    }

    fun getKeyNamePosition(keyName: String): Int {
        return findSelectionNbr(keyName)
    }

    private fun findSelectionNbr(key: String): Int {
        for ((_, value) in systemCatalog) {
            if (value.name == key) {
                return value.selection
            }
        }
        return findSelectionNbr(DEFAULT_COUNTING_SYSTEM_KEY)
    }

    fun findSelectionKey(selection: Int): String {
        for ((_, value) in systemCatalog) {
            if (value.selection == selection) {
                return value.name
            }
        }
        return DEFAULT_COUNTING_SYSTEM_KEY
    }

    private var selected = systemCatalog[DEFAULT_COUNTING_SYSTEM_KEY]

    fun findSelected(): CountingSystem {
        return selected ?: systemCatalog[DEFAULT_COUNTING_SYSTEM_KEY] ?: pointOfNoReturn()
    }

    private fun pointOfNoReturn(): CountingSystem {
        Logz.errorMsg("Failed to load counting systems from resource cs.txt.")
        return CountingSystem( DEFAULT_COUNTING_SYSTEM_KEY, "+1 +1 +1 +1 +1  0  0  0 -1 -1 Hi-Lo", 4)
    }

    private fun setCountingSystem(position: Int) {
        selected = systemCatalog[findSelectionKey(position)]
    }


    fun resetCount() {
        findSelected().resetCount()
    }

    fun reset() {
        populate()
        selected = systemCatalog[DEFAULT_COUNTING_SYSTEM_KEY]
        resetCount()
    }
}

const val DEFAULT_COUNTING_SYSTEM_KEY = "Hi-Lo"
const val NAME_POS = 30

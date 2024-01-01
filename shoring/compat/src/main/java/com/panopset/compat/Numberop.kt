package com.panopset.compat

import com.panopset.compat.Logop.dspmsg
import com.panopset.compat.Logop.handleException
import com.panopset.compat.Stringop.isPopulated

object Numberop {
    fun parseInt(panop: Panop, value: String): Int {
        if (!isPopulated(value)) {
            return 0
        } else {
            val str = value.replace(",", "")
            try {
                return str.toInt()
            } catch (nfe: NumberFormatException) {
                dspmsg(panop, value)
                handleException(panop, nfe)
                return 0
            }
        }
    }

    fun parse(panop: Panop, value: String, base: Int?, defaultValue: Int): Int {
        if (!isPopulated(value)) {
            return defaultValue
        } else {
            val str = value.replace(",", "")
            try {
                return str.toInt(base!!)
            } catch (nfe: NumberFormatException) {
                handleException(panop, nfe)
                return defaultValue
            }
        }
    }

    fun parse(panop: Panop, value: String, base: Int?): Int {
        if (!isPopulated(value)) {
            return -1
        } else {
            val str = value.replace(",", "")

            try {
                return str.toInt(base!!)
            } catch (nfe: NumberFormatException) {
                handleException(panop, nfe)
                return -1
            }
        }
    }

    fun isNumber(value: String?): Boolean {
        if (value == null) {
            return false
        }
        return value.matches("[0-9]*".toRegex())
    }

    fun isInteger(value: String?): Boolean {
        if (value == null) {
            return false
        }
        return value.matches("-?\\d+".toRegex())
    }
}

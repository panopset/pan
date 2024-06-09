package com.panopset.compat

import com.panopset.compat.Stringop.isPopulated

object Numberop {
    fun parseInt(value: String): Int {
        if (!isPopulated(value)) {
            return 0
        } else {
            val str = value.replace(",", "")
            try {
                return str.toInt()
            } catch (var3: NumberFormatException) {
                Logz.errorMsg(value, var3)
                return 0
            }
        }
    }

    fun parse(value: String, base: Int, defaultValue: Int): Int {
        if (!isPopulated(value)) {
            return defaultValue
        } else {
            val str = value.replace(",", "")
            try {
                return str.toInt(base)
            } catch (var4: NumberFormatException) {
                Logz.errorEx(var4)
                return defaultValue
            }
        }
    }

    fun parse(value: String, base: Int?): Int {
        if (!isPopulated(value)) {
            return -1
        } else {
            val str = value.replace(",", "")

            try {
                return str.toInt(base!!)
            } catch (var4: NumberFormatException) {
                Logz.errorEx(var4)
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

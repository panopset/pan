package com.panopset.compat

import com.google.gson.Gson
import java.lang.reflect.Type

class Jsonop<T> {
    fun toJson(obj: T): String {
        return Gson().toJson(obj)
    }

    /**
     * https://stackoverflow.com/questions/5554217.
     */
    fun fromJson(json: String, type: Type): T {
        return Gson().fromJson(json, type)
    }
}

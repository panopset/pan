package com.panopset.compat

import com.google.gson.Gson
import java.lang.reflect.Type

class Jsonop {
    fun toJson(obj: Any): String {
        return Gson().toJson(obj)
    }

    /**
     * https://stackoverflow.com/questions/5554217.
     */
    fun fromJson(json: String, type: Type): Any {
        return Gson().fromJson(json, type)
    }
}

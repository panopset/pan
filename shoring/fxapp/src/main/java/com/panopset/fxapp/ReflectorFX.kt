package com.panopset.fxapp

import com.panopset.compat.Logop
import com.panopset.compat.Stringop
import javafx.scene.control.Labeled
import javafx.scene.control.Tab
import java.util.*

fun bindBundle(controller: Any, resources: ResourceBundle?) {
    if (resources == null) {
        return
    }
    for (fld in controller.javaClass.fields) {
        try {
            val obj = fld[controller]
            if (obj is Labeled) {
                val key = fld.name
                if (Stringop.isEmpty(key)) {
                    continue
                }
                val txt: String = try {
                    resources.getString(key)
                } catch (mre: MissingResourceException) {
                    continue
                }
                if (!Stringop.isEmpty(txt)) {
                    obj.text = txt
                }
            } else if (obj is Tab) {
                val key = fld.name
                val txt = resources.getString(key)
                if (!Stringop.isEmpty(txt)) {
                    obj.text = txt
                }
            }
        } catch (e: Exception) {
            Logop.errorEx(e)
        }
    }
}

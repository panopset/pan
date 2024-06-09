package com.panopset.fxapp

import com.panopset.compat.*
import com.panopset.compat.Stringop.FSP
import javafx.application.Platform
import javafx.scene.control.*
import java.io.File
import java.util.*
import kotlin.DoubleArray
import kotlin.NumberFormatException
import kotlin.String

abstract class Anchor(val application: PanApplication) {
    constructor(anchorManager: PanApplication, file: File) : this(anchorManager) {
        setPersistentMapFile(file)
    }

    fun setDefaultValue(key: String, value: String) {
        boltDefaults[key] = value
    }

    fun createWindowTitle(): String {
        return String.format("%s ~ %s", application.getApplicationDisplayName(), getPath())
    }

    protected abstract fun updateTitle()
    fun loadDataFromFile() {
        pmf.load()
    }

    fun setBoltValues() {
        for ((key, value1) in bolts) {
            val value: String = pmf[key!!]
            value1.setBoltValue(value)
        }
        updateTitle()
    }

    fun saveDataToFile() {
        for ((key, value1) in bolts) {
            var value = value1.getBoltValue()
            if (!Stringop.isPopulated(value)) {
                value = value1.getBoltDefault()
            }
            pmf.put(key!!, value)
        }
        pmf.flush()
        globalPropsPut(JavaFXapp.GLOBAL_LAST_PARENT_DIR, pmf.file.parent)
    }

    fun setFile(file: File) {
        pmf.setNewFile(file)
    }

    fun getPath(): String {
        return pmf.file.canonicalPath
    }

    fun getFileName(): String {
        return pmf.file.name
    }

    private val bolts: MutableMap<String?, Bolt> = HashMap()
    private val boltDefaults: MutableMap<String, String> = HashMap()
    var pmf = PersistentMapFile(File(createDefaultPath()))

    private fun setPersistentMapFile(file: File) {
        pmf = PersistentMapFile(file)
    }

    private fun createDefaultPath(): String {
        var path = ""
        while (path.isEmpty() || File(path).exists()) {
            val untitledFileName = createNextUntitledPath()
            path = HiddenFolder.getFullPathRelativeTo("temp${FSP}$untitledFileName")
        }
        return path
    }

    private fun createNextUntitledPath(): String {
        return "${application.applicationShortName}_Untitled${application.getNextUniqueID()}.properties"
    }

    fun registerChoiceBox(keyChain: String, cb: ChoiceBox<String>) {
        val defaultValue = "" + cb.selectionModel.selectedIndex

        registerData(keyChain, object: Bolt {
            override fun getBoltValue(): String {
                return "" + cb.selectionModel.selectedIndex
            }

            override fun getBoltDefault(): String {
                return defaultValue
            }

            override fun setBoltValue(value: String) {
                val i = Stringop.parseInt(value, 10)
                if (i > -1) {
                    cb.selectionModel.select(i)
                }
            }
        })
    }

    fun registerCheckBox(keyChain: String, cb: CheckBox) {
        val defaultValue = "" + cb.isSelected

        registerData(keyChain, object : Bolt {
            override fun setBoltValue(value: String) {
                if (Stringop.isPopulated(value)) {
                    val bv = value.toBoolean()
                    cb.isSelected = bv
                }
            }

            override fun getBoltValue(): String {
                return "" + cb.isSelected
            }

            override fun getBoltDefault(): String {
                return defaultValue
            }
        })
    }

    fun registerToggleButton(keyChain: String, tb: ToggleButton) {
        val defaultValue = "" + tb.isSelected

        registerData(keyChain, object : Bolt {
            override fun setBoltValue(value: String) {
                if (Stringop.isPopulated(value)) {
                    val bv = value.toBoolean()
                    tb.isSelected = bv
                }
            }

            override fun getBoltValue(): String {
                return "" + tb.isSelected
            }

            override fun getBoltDefault(): String {
                return defaultValue
            }
        })
    }

    fun registerTextInputControl(keyChain: String, tf: TextInputControl) {
        if (isPasswordField(tf)) {
            return
        }
        if (tf.id == null) {
            tf.id = keyChain
        }
        val currentTextValue = tf.text
        registerData(keyChain, object : Bolt {
            override fun setBoltValue(value: String) {
                Platform.runLater { tf.text = value }
            }

            override fun getBoltValue(): String {
                if (tf.text.isEmpty()) {
                    tf.text = getBoltDefault()
                }
                return tf.text
            }

            override fun getBoltDefault(): String {
                val dft = boltDefaults[tf.id]
                return if (Stringop.isBlank(dft)) {
                    currentTextValue
                } else dft!!
            }
        })
    }

    fun registerSplitPaneLocations(keyChain: String, splitPane: SplitPane) {
        val currentValue = Arrays.toString(splitPane.dividerPositions)
        registerData(keyChain, object : Bolt {
            override fun setBoltValue(value: String) {
                if (!Stringop.isPopulated(value)) {
                    return
                }
                if ("[]".equals(value, ignoreCase = true)) {
                    return
                }
                val str = value.replace("[", "").replace("]", "").split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val positions = DoubleArray(str.size)
                for (i in str.indices) {
                    positions[i] = str[i].toDouble()
                }
                splitPane.setDividerPositions(*positions)
            }

            override fun getBoltValue(): String {
                return splitPane.dividerPositions.contentToString()
            }

            override fun getBoltDefault(): String {
                return currentValue
            }
        })
    }

    fun registerTabSelected(keyChain: String, tabPane: TabPane) {
        val currentValue = tabPane.selectionModel.selectedIndex
        registerData(keyChain, object : Bolt {
            override fun getBoltValue(): String {
                return "" + tabPane.selectionModel.selectedIndex
            }

            override fun setBoltValue(value: String) {
                if (Stringop.isPopulated(value)) {
                    try {
                        val i = value.toInt()
                        tabPane.selectionModel.select(i)
                    } catch (ex: NumberFormatException) {
                        Logz.errorEx(ex)
                    }
                }
            }

            override fun getBoltDefault(): String {
                return "" + currentValue
            }
        })
    }

    fun registerData(key: String, bolt: Bolt) {
        bolts.computeIfAbsent(key) { bolt }
    }
}

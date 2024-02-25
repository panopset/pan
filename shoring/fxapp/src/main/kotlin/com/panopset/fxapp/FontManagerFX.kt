package com.panopset.fxapp

import com.panopset.compat.*
import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.text.Font
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import java.util.logging.Level

object FontManagerFX {
    private var fontSize: FontSize = FontSize.DEFAULT_SIZE

    private var monospace: Font = Font(MONOSPACE, fontSize.value.toDouble())
    private var boldArial: Font = Font.font(ARIAL, FontWeight.BOLD, fontSize.value.toDouble())
    private var plainArial: Font = Font(ARIAL, fontSize.value.toDouble())
    private var boldSerif: Font = Font.font(SERIF, FontWeight.BOLD, fontSize.value.toDouble())
    private var plainSerif: Font = Font(SERIF, fontSize.value.toDouble())
    private var borderTitle: Font = Font.font(ARIAL, FontPosture.ITALIC, fontSize.value.toDouble())
    private var logEntry: LogEntry = LogEntry(LogopAlert.GREEN, Level.INFO, "")

    init {
        fontSize = try {
            val savedFontSizeValue = globalPropsGet(FONT_SIZE_KEY).replace("\"", "")
            if (savedFontSizeValue.isEmpty()) {
                FontSize.DEFAULT_SIZE
            }
            FontSize.findFromValue(savedFontSizeValue)
        } catch (ex: NumberFormatException) {
            Logz.errorEx(ex)
            FontSize.DEFAULT_SIZE
        }
    }

    var mbs: MutableList<MenuBar> = ArrayList()
    var nodes: MutableList<Node> = ArrayList()
    var tabPanes: MutableList<TabPane> = ArrayList()
    var tabs: MutableList<Tab> = ArrayList()


    fun getCurrentFontSizeName(): String {
        return fontSize.name
    }

    private fun getMonoStyle(i: Int): String {
        return String.format("-fx-font-size: %dpx; -fx-font-family: 'monospaced';", i)
    }

    fun getCurrentBaseStyle(): String {
        return getStyleFor(fontSize.value)
    }

    fun updateAllFontSizes() {
        val style = getStyleFor(fontSize.value)
        val stylem = getMonoStyle(fontSize.value)
        for (mb in mbs) {
            mb.style = style
        }
        for (node in nodes) {
            if (node is TextInputControl) {
                if (MBSM == node.getId()) {
                    Platform.runLater {
                        setMenubarLogRecord(logEntry, node as TextField)
                    }
                } else {
                    node.setStyle(stylem)
                }
            } else {
                node.style = style
            }
        }
        for (tabPane in tabPanes) {
            tabPane.style = style
        }
        for (tab in tabs) {
            tab.style = style
        }
        globalPropsPut( FONT_SIZE_KEY, String.format("%d", fontSize.value))
        monospace = Font(MONOSPACE, fontSize.value.toDouble())
        boldArial = Font.font(ARIAL, FontWeight.BOLD, fontSize.value.toDouble())
        plainArial = Font(ARIAL, fontSize.value.toDouble())
        boldSerif = Font.font(SERIF, FontWeight.BOLD, fontSize.value.toDouble())
        plainSerif = Font(SERIF, fontSize.value.toDouble())
        borderTitle = Font.font(ARIAL, FontPosture.ITALIC, fontSize.value.toDouble())
    }

    fun getMonospace(): Font {
        return monospace
    }

    fun getBoldArial(): Font {
        return boldArial
    }

    fun getBoldSerif(): Font {
        return boldSerif
    }

    fun getPlainArial(): Font {
        return plainArial
    }

    fun getPlainSerif(): Font {
        return plainSerif
    }

    fun getBorderTitle(): Font {
        return borderTitle
    }

    fun register(control: Node) {
        if (!nodes.contains(control)) {
            nodes.add(control)
            if (control is TextInputControl) {
                control.setStyle(getMonoStyle(fontSize.value))
            } else {
                control.style = getStyleFor(fontSize.value)
            }
        }
    }

    fun registerTabPane(tabPane: TabPane) {
        if (tabPanes.contains(tabPane)) {
            Logz.debug("Ignoring duplicate FontManagerFX registration of tabPane " + tabPane.id)
        } else {
            tabPanes.add(tabPane)
        }
    }

    fun registerTab(tab: Tab): Tab {
        if (tabs.contains(tab)) {
            Logz.debug("Ignoring duplicate FontManagerFX registration of tab " + tab.id)
        } else {
            tabs.add(tab)
        }
        return tab
    }

    fun registerMenubar(menuBar: MenuBar) {
        if (mbs.contains(menuBar)) {
            Logz.debug("Ignoring duplicate FontManagerFX registration of menubar " + menuBar.id)
        } else {
            mbs.add(menuBar)
        }
    }

    val size: Int
        get() = fontSize.value

    fun setFontSize(fontSize: FontSize) {
        this.fontSize = fontSize
        updateAllFontSizes()
    }

    val imgRatio: Double
        get() = fontSize.imgRatio
    val svgRatio: Double
        get() = fontSize.svgRatio

    private fun getCurrentMessageStyle(logEntry: LogEntry): String {
        var colorStyle = FONT_GREEN
        if (logEntry.alert == LogopAlert.PURPLE) {
            colorStyle = FONT_PURPLE
        } else if (logEntry.alert == LogopAlert.BLUE) {
            colorStyle = FONT_BLUE
        } else if (logEntry.alert == LogopAlert.RED) {
            colorStyle = FONT_RED
        } else if (logEntry.alert == LogopAlert.ORANGE) {
            colorStyle = FONT_ORANGE
        } else if (logEntry.alert == LogopAlert.YELLOW) {
            colorStyle = FONT_YELLOW
        } else if (logEntry.alert == LogopAlert.GREEN) {
            colorStyle = FONT_GREEN
        }
        return colorStyle + getStyleFor(size)
    }

    private fun getStyleFor(fontSizeValue: Int): String {
        return String.format("-fx-font-size: %dpx;", fontSizeValue)
    }

    fun setMenubarLogRecord(logEntry: LogEntry, menubarStatusMessage: TextField) {
        this.logEntry = logEntry
        Platform.runLater {
            menubarStatusMessage.style = getCurrentMessageStyle(logEntry)
            menubarStatusMessage.text = logEntry.message
        }
    }
}

const val LUCIDIA = "Lucida Sans Regular"
const val LUCIDIA_BOLD = "Lucida Sans Bold"
const val LUCIDIA_OBLIQUE = "Lucida Sans Oblique"
const val LUCIDIA_BOLD_OB = "Lucida Sans Bold Oblique"
const val L_BRIGHT = "Lucida Bright Regular"
const val L_BRIGHT_BOLD = "Lucida Bright Bold"
const val L_BRIGHT_OBLIQUE = "Lucida Bright Italic"
const val L_BRIGHT_BOLD_OB = "Lucida Bright Bold Italic"
const val MONOSPACE = "Monospace"
const val MONOSPACE_BOLD = "Monospace Bold"
const val MONOSPACE_OBLIQUE = "Lucida Sans Typewriter Oblique"
const val MONOSPACE_BOLD_OB = "Lucida Sans Typewriter Bold Oblique"
const val ARIAL = "Arial"
const val SERIF = "Serif"
const val DIALOG = "Dialog"

const val FONT_GREEN = "-fx-background-color: #000000; -fx-text-fill: #99ff99;"
const val FONT_YELLOW = "-fx-background-color: #000000;-fx-text-fill: #ffff66;"
const val FONT_ORANGE = "-fx-background-color: #000000;-fx-text-fill: #ffaa00;"
const val FONT_BLUE = "-fx-background-color: #000000;-fx-text-fill: #0000ff;"
const val FONT_RED = "-fx-background-color: #000000;-fx-text-fill: #ff3333;"
const val FONT_PURPLE = "-fx-background-color: #000000;-fx-text-fill: #dd33dd;"
const val MBSM = "menubarStatusMessage"

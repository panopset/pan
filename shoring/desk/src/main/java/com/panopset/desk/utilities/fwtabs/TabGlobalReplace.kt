package com.panopset.desk.utilities.fwtabs

import com.panopset.compat.Logop
import com.panopset.fxapp.*
import com.panopset.gp.GlobalReplaceProcessor
import javafx.scene.control.Tab
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import java.io.BufferedReader
import java.io.IOException
import java.io.StringReader

class TabGlobalReplace {
    private lateinit var grDirOrFileSelect: PanFileOrDirSelectorPanel
    private lateinit var grFromText: TextArea
    private lateinit var grToText: TextArea
    private lateinit var grFromStrings: TextArea
    private lateinit var grToStrings: TextArea
    private lateinit var grExtensions: TextField
    private lateinit var grRegex: TextField
    private lateinit var grPriorLineMustContain: TextField
    private lateinit var grReplacementLineMustContain: TextField

    fun createTab(fxDoc: FxDoc): Tab {
        grDirOrFileSelect = PanFileOrDirSelectorPanel(fxDoc, "grdirselect")
        grFromStrings = createPersistentPanTextArea(fxDoc, "fxidFromStrings", "Strings to replace.", "List of Strings to replace.")
        grToStrings = createPersistentPanTextArea(fxDoc, "fxidToStrings", "Replacement Strings.", "List of replacement Strings.")
        grFromText = createPersistentPanTextArea(fxDoc, "fxidFromText", "Input text.", "Input text, if file or directory is not selected.")
        grToText = createPersistentPanTextArea(fxDoc, "fxidToText", "Output text.", "Output text, if file or directory is not selected.")
        grExtensions = createPanInputTextFieldHGrow(fxDoc, "fxidExtensions", "Only select files with these comma separated extensions.",
            "Leave out the leading period, for example \"java,kt\".")
        grRegex = createPanInputTextFieldHGrow(fxDoc, "fxidRegex", "Use this regex to select files.", "File extension selection is ignored, if this field is filled out.")
        grPriorLineMustContain = createPanInputTextFieldHGrow(fxDoc, "fxidPriorLineMustContain",
            "Enter text that the prior line must contain.", "Text that the prior line must contain, if replacement is to be done.")
        grReplacementLineMustContain = createPanInputTextFieldHGrow(fxDoc, "fxidReplacementLineMustContain",
            "Enter text that the replacement line must contain.", "Text that the replacement line must contain, if replacement is to be done.")
        val rtn = FontManagerFX.registerTab(Tab("Global Replace"))
        val bp = BorderPane()
        bp.center = createMainBox()
        rtn.content = bp
        return rtn
    }

    private fun createMainBox(): VBox {
        return createPanVBox(
            grDirOrFileSelect.pane,
            createPanHBox(grFromStrings, grToStrings),
            createPanHBox(grFromText, grToText),
            createPanHBox(
                createPanButton({doReplaceAll()}, "Replace all", false, "Please make sure everything is backed up first, there is no going back."),
                grExtensions),
            grRegex,
            createPanLabel("Prior line must contain:"),
            createPanHBox(grPriorLineMustContain),
            createPanLabel("Replacement lines must contain:"),
            createPanHBox(grReplacementLineMustContain))
    }

    private fun doReplaceAll() {
        doReplaceAllFiles()
        doReplaceAllTextFields()
    }

    private fun doReplaceAllFiles() {
        val te = GlobalReplaceProcessor(
            grDirOrFileSelect.createFile(), grFromText.text,
            grToText.text, grExtensions.text, grDirOrFileSelect.isRecursive()
        )
        te.priorLineMustContain = grPriorLineMustContain.text
        te.replacementLineMustContain = grReplacementLineMustContain.text
        try {
            te.process()
        } catch (e: IOException) {
            Logop.errorEx(e)
        }
    }


    private fun doReplaceAllTextFields() {
        val inp = grFromText.text
        val sr = StringReader(inp)
        val br = BufferedReader(sr)
        var s = br.readLine()
//        while (s != null) {
//            val result = doReplacements(s)
//            s = br.readLine()
//        }
    }
}

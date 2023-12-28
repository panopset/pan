package com.panopset.desk.security

import com.panopset.compat.Logop
import com.panopset.compat.ProcessListener
import com.panopset.compat.TextProcessor
import com.panopset.fxapp.*
import com.panopset.marin.fx.PanopsetBrandedAppTran
import com.panopset.marin.secure.checksums.ChecksumReport
import com.panopset.marin.secure.checksums.ChecksumType
import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.TextArea
import javafx.scene.control.Tooltip
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox

class Checksum : PanopsetBrandedAppTran() {
    private val csCheckBoxesHBox = HBox()
    private var isAllOn = true
    private lateinit var csChecksum: Button
    private lateinit var csOut: TextArea
    private lateinit var csAll: Button
    private lateinit var csFileSelect: PanFileOrDirSelectorPanel
    private lateinit var csCheckBoxes: ArrayList<CheckBox>

    override fun getApplicationDisplayName(): String {
        return "Checksum"
    }

    override fun getDescription(): String {
        return "Validate files with various checksums you might typically encounter."
    }

    override fun createDynapane(fxDoc: FxDoc): Pane {
        csChecksum = createPanButton({ Platform.runLater(::doProcess) }, "_Checksum", true,
            "Run checkbox specified checksums on selected file.")
        csOut = createPanTextArea()
        createCsCheckBoxes(fxDoc)
        csFileSelect = PanFileOrDirSelectorPanel(fxDoc, "csFileOrDirSelect")
        csAll = createPanButton({
            for (cb in csCheckBoxes) {
                cb.isSelected = isAllOn
            }
            isAllOn = !isAllOn
        }, "_All", true, "Toggle select/deselect all checkboxes.")
        val b: BorderPane = createStandardMenubarBorderPane(fxDoc)
        b.center = createCenter()
        return b
    }

    private fun createCenter(): BorderPane {
        val bp = BorderPane()
        val topControls = VBox()
        topControls.children.addAll(createPanHBox(csChecksum, csAll, csFileSelect.pane), createCsCheckBoxesHBox())
        bp.top = topControls
        bp.center = createPanScrollPane(csOut)
        return bp
    }

    private fun createCsCheckBoxesHBox(): HBox {
        val rtn = HBox()
        for (cb in csCheckBoxes) {
            rtn.children.add(cb)
        }
        return rtn
    }

    private fun doProcess() {
        val types = getSelectedTypes()
        if (types.isEmpty()) {
            Logop.warn("Nothing selected.")
            csOut.text = "Nothing selected."
            return
        }
        Logop.clear()
        csOut.text = ""
        createReport(types)
    }

    private fun createReport(types: List<ChecksumType>) {
        val tp = TextProcessor()
        tp.addProcessListener(object : ProcessListener {
            override fun setText(value: String) {
                csOut.text = value
            }

            override fun append(value: String) {
                csOut.appendText(value)
            }

            override fun reset() {
                csOut.text = ""
            }
        })
        ChecksumReport().generateReport(csFileSelect.createFile(), types, tp)
    }
    private fun getSelectedTypes(): List<ChecksumType> {
        val rtn: MutableList<ChecksumType> = ArrayList()
        for (cb in csCheckBoxes) {
            if (cb.isSelected) {
                val cst = ChecksumType.find(cb.text)
                if (cst != null) {
                    rtn.add(cst)
                }
            }
        }
        return rtn
    }

    private fun createCsCheckBoxes(fxDoc: FxDoc) {
        csCheckBoxes = ArrayList()
            for (cst in ChecksumType.entries) {
                val cb = createPanCheckBox(fxDoc, "id_cb${cst.name}", cst.name)
                cb.id = "cs_algbtn_" + cst.name
                if ("BYTES" == cst.name) {
                    cb.tooltip = Tooltip(
                        "This is just the byte count for the file, not any kind of checksum."
                    )
                }
                csCheckBoxesHBox.children.add(cb)
                csCheckBoxes.add(cb)
            }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Checksum().go()
        }
    }
}

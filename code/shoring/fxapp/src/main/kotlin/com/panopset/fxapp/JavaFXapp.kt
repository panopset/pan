package com.panopset.fxapp

import com.panopset.compat.*
import com.panopset.compat.AppVersion.getBuildNumber
import com.panopset.compat.AppVersion.getVersion
import com.panopset.fxapp.AnchorFactory.addAnchor
import com.panopset.fxapp.AnchorFactory.findAnchor
import com.panopset.fxapp.AnchorFactory.getAnchors
import com.panopset.fxapp.AnchorFactory.removeAnchor
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.FlowPane
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.io.File
import java.io.StringWriter
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

object JavaFXapp {

    private fun findActiveStage(): Stage? {
        val fxDoc = findAnchor() as FxDoc? ?: return null
        return fxDoc.stage
    }

    fun launch(stage: Stage) {
        val rawFiles = globalPropsGet(DeskApp4XFactory.panApplication.filesKey)
        var fxDoc: FxDoc? = null
        if (Stringop.isPopulated(rawFiles)) {
            val files = rawFiles.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val sortedFileSet = Collections.synchronizedSortedSet(TreeSet<String>())
            for (rawPath in files) {
                sortedFileSet.add(rawPath)
            }
            var firstTime = true
            for (rawPath in sortedFileSet) {
                val path = URLDecoder.decode(rawPath, StandardCharsets.UTF_8)
                val file = File(path)
                if (firstTime) {
                    fxDoc = DeskApp4XFactory.assemblerFxDoc(stage, file)
                    show(fxDoc)
                    firstTime = false
                } else {
                    fxDoc = DeskApp4XFactory.assemblerFxDoc(Stage(), file)
                    show(fxDoc)
                }
            }
            globalPropsPut(DeskApp4XFactory.panApplication.filesKey, "")
        } else {
            addAndShow(stage)
        }
        Platform.runLater {
            logStage = createLogStage(fxDoc ?: LogzDisplayerCMD)
        }
    }

    fun doLaunch() {
        DeskApp4XFactory.deskApp4FX.doLaunch()
    }

    private fun addStage() {
        addAndShow(Stage())
    }

    private fun addAndShow(stage: Stage) {
        show(FxDoc(DeskApp4XFactory.panApplication, stage))
    }

    private fun show(fxDoc: FxDoc) {
        addAnchor(fxDoc)
        StageManager.assembleAndShow(DeskApp4XFactory.deskApp4FX, fxDoc)
        fxDoc.setBoltValues()
        fxDoc.application.doAfterShow(fxDoc)
    }

    private fun doSave(fxDoc: FxDoc) {
        fxDoc.saveDataToFile()
        Logz.green("Saved to ${fxDoc.getFileName()}.")
    }

    private fun doSaveAs(fxDoc: FxDoc) {
        val fileChooser = FileChooser()
        fileChooser.title = "Save as..."
        val lpd = globalPropsGet(GLOBAL_LAST_PARENT_DIR)
        if (Stringop.isPopulated(lpd)) {
            fileChooser.initialDirectory = File(lpd)
        }
        val file = fileChooser.showSaveDialog(findStage())
        if (file == null) {
            Logz.warn("New file not set.")
            return
        }
        fxDoc.setFile(file)
        doSave(fxDoc)
        fxDoc.updateTitle()
    }

    private fun doOpen() {
        val fileChooser = FileChooser()
        fileChooser.title = ("Open a previously saved "
                + DeskApp4XFactory.panApplication.applicationShortName
                + " properties file.")
        val lpd = globalPropsGet(GLOBAL_LAST_PARENT_DIR)
        if (Stringop.isPopulated(lpd)) {
            fileChooser.initialDirectory = File(lpd)
        }
        val file = fileChooser.showOpenDialog(findStage())
        if (file == null) {
            Logz.warn("No file selected.")
            return
        }
        if (!file.exists()) {
            Logz.warn("File ${Fileop.getCanonicalPath(file)} does not exist.")
            return
        }
        show(DeskApp4XFactory.assemblerFxDoc(Stage(), file))
    }

    private fun doAbout(fxDoc: FxDoc) {
        val alert = Alert(AlertType.INFORMATION)
        val appName = DeskApp4XFactory.panApplication.getCompanyName()
        val bannerImageName = appName.lowercase(Locale.getDefault())
        val bannerImage = Image(javaClass.getResourceAsStream("/$bannerImageName.png"))
        alert.graphic = ImageView(bannerImage)
        alert.title = String.format(
            "About %s %s", DeskApp4XFactory.panApplication.getCompanyName(),
            DeskApp4XFactory.panApplication.getApplicationDisplayName()
        )
        alert.headerText = DeskApp4XFactory.panApplication.getDescription()
        alert.contentText = "Version ${getVersion()} build ${getBuildNumber()}." +
                "\n\n " +
                "\u00a9 1996-2024 Karl Dinwiddie.\n\n" +

                "This program is free software: you can redistribute it and/or modify \n" +
                "it under the terms of the GNU General Public License as published by \n" +
                "the Free Software Foundation, either version 3 of the License, or \n" +
                "(at your option) any later version. \n" +
                " \n" +
                "This program is distributed in the hope that it will be useful, \n" +
                "but WITHOUT ANY WARRANTY; without even the implied warranty of \n" +
                "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the \n" +
                "GNU General Public License for more details. \n" +
                " \n" +
                "You should have received a copy of the GNU General Public License \n" +
                "along with this program.  If not, see <https://www.gnu.org/licenses/>."
        val stage = alert.dialogPane.scene.window as Stage
        stage.icons.add(dds!!.createFaviconImage())
        stage.scene.root.style = fxDoc.scene.root.style
        alert.dialogPane.setPrefSize(600.0, 400.0);
        alert.showAndWait()
    }

    private var logStage: Stage? = null

    private fun createLogStage(logzDsiplayer: LogDisplayer): Stage {

        val rtn = Stage()
        rtn.title = "Logs"

        val borderPane = BorderPane()

        val logTa = TextArea() // TODO: Rename
        logTa.promptText = "Click refresh to load log from file."
        val clearLog = createPanButton(
            logzDsiplayer, {
                Logz.clear()
                logTa.text = ""
            },
            "Clear", false, "Clear logs."
        )
        val refreshLog = createPanButton(
            logzDsiplayer, { update(logTa) },
            "Refresh", false, "Refresh log."
        )

        FontManagerFX.register(logTa)

        val topFlow = FlowPane()

        topFlow.children.add(refreshLog)
        topFlow.children.add(clearLog)
        topFlow.children.add(createPanButton(logzDsiplayer, {
            logTa.text = SysInfo.toString()
        }, "System", false, ""))
        borderPane.top = topFlow


        val scrollPane = ScrollPane()
        scrollPane.fitToHeightProperty().value = true
        scrollPane.fitToWidthProperty().value = true
        scrollPane.content = logTa
        borderPane.center = scrollPane
        update(logTa)
        Zombie.addStopAction {
            logStage?.close()
        }
        rtn.scene = Scene(borderPane, 600.0, 400.0)
        rtn.icons.add(dds!!.createFaviconImage())
        return rtn
    }

    private fun update(logTa: TextArea) {
        logTa.text = getEntryStackAsTextlg()
    }

    private fun doShowLog(fxDoc: FxDoc) {
        if (logStage == null) {
            return
        }
        if (!logStage!!.isShowing) {
            logStage!!.scene.root.style = fxDoc.scene.root.style
            logStage!!.show()
        }
        logStage!!.toFront()
        logStage!!.requestFocus()
    }

    @Synchronized
    private fun saveOpenWindowStateGlobally() {
        var firstTime = true
        val sw = StringWriter()
        for (anchor in getAnchors()) {
            val path = URLEncoder.encode(anchor.getPath(), StandardCharsets.UTF_8)
            if (firstTime) {
                firstTime = false
            } else {
                sw.append(",")
            }
            sw.append(path)
        }
        globalPropsPut(DeskApp4XFactory.panApplication.filesKey, sw.toString())
        globalPropsFlush()
    }

    fun doExit() {
        Zombie.stop()
        saveOpenWindowStateGlobally()
        for (anchor in getAnchors()) {
            (anchor as FxDoc).closeWindow()
        }
    }


    const val GLOBAL_LAST_PARENT_DIR = "com.panopset.global_last_parent_dir"

    fun findStage(): Stage? {
        return findActiveStage()
    }

    var dds: AppDDSFX? = null // TODO: got to be a better way but okay for now.
    fun newWindow() {
        addStage()
    }

    fun showAboutPanel(fxDoc: FxDoc) {
        doAbout(fxDoc)
    }

    fun showLogPanel(fxDoc: FxDoc) {
        doShowLog(fxDoc)
    }

    fun saveWindow(fxDoc: FxDoc) {
        doSave(fxDoc)
    }

    fun saveWindowAs(fxDoc: FxDoc) {
        doSaveAs(fxDoc)
    }

    fun openWindowFromFile() {
        doOpen()
    }

    fun panExit() {
        doExit()
    }

    fun closeWindow(fxDoc: FxDoc) {
        val size = getAnchors().size
        if (size == 1) {
            doExit()
            return
        }
        fxDoc.closeWindow()
        removeAnchor(fxDoc)
    }
}

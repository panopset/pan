package com.panopset.desk.utilities

import com.panopset.compat.*
import com.panopset.desk.utilities.skyscraper.*
import com.panopset.fxapp.*
import com.panopset.marin.fx.*
import javafx.application.Platform
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.control.*
import javafx.scene.layout.*
import kotlin.collections.ArrayList

class Skyscraper : PanopsetBrandedAppTran() {
    private val floorMap = HashMap<FxDoc, Floor>()
    private lateinit var panCheckboxMenu: PanCheckboxMenu
    private fun getFloor(fxDoc: FxDoc): Floor {
        var rtn = floorMap[fxDoc]
        if (rtn == null) {
            rtn = Floor(fxDoc)
            floorMap[fxDoc] =  rtn
        }
        return rtn
    }

    private var currentProps: MutableMap<String, String> = HashMap()

    override fun createDynapane(fxDoc: FxDoc): Pane {
        val mainBorderPane: BorderPane = createStandardMenubarBorderPane(fxDoc)
        mainBorderPane.center = createMainBorderPaneCenter(fxDoc)
        return mainBorderPane
    }

    override fun doAfterShow(fxDoc: FxDoc) {
        Thread {
            Thread.sleep(400)
            // TODO:  gotta be a better way!
            Platform.runLater {
                val floor = getFloor(fxDoc)
                updateEnvironmentWithProps(floor, panCheckboxMenu.currentSelection)
            }
        }.start()
    }

    override fun addAppMenus(fxDoc: FxDoc, menus: ObservableList<Menu>) {
        super.addAppMenus(fxDoc, menus)
        val floor = getFloor(fxDoc)
        loadEnvironments(floor.envMap)
        menus.add(createEnvMenu(floor))
    }

    private fun createEnvMenu(floor: Floor): Menu {
        panCheckboxMenu = createPanEnvMenu(floor, "Env")
        if (floor.envMap.isEmpty()) {
            return createEnvHelpMenu(floor, panCheckboxMenu.menu.text)
        }
        return panCheckboxMenu.menu
    }

    private fun createPanEnvMenu(floor: Floor, menuName: String): PanCheckboxMenu {
        val cmf = object: PanCheckboxMenuFactory(menuName) {
            override fun assignAction(
                panCheckboxMenuItem: PanCheckboxMenuItem,
                panCheckboxMenu: PanCheckboxMenu
            ) {
                panCheckboxMenuItem.checkboxMenuItem.onAction = EventHandler {
                    panCheckboxMenu.setTheCurrentSelection(panCheckboxMenuItem.checkboxMenuItem.text)
                    updateEnvironmentWithProps(floor, panCheckboxMenuItem.checkboxMenuItem.text)
                }
            }
            override fun isThisMatch(panCheckboxMenuItem: PanCheckboxMenuItem, value: String): Boolean {
                return value == panCheckboxMenuItem.checkboxMenuItem.text
            }
            override fun populateListOfMenuItemNames(menuItemNames: ArrayList<String>){
                for (entry in floor.envMap.entries) {
                    menuItemNames.add(entry.key)
                }
            }
        }
        val rtn = cmf.construct(floor.fxDoc)
        return rtn
    }

    //  value == panCheckboxMenuItem.checkboxMenuItem.text
// abstract report:
//updateEnvironmentWithProps(envValues)

    //updateEnvironmentWithProps(floor.envMap[rtn.currentSelection])
    //.envMap[panCheckboxMenuItem.checkboxMenuItem.text]

    private fun updateEnvironmentWithProps(floor: Floor, envMapKay: String) {
        floor.updateUrlOutTFfromUrlTextField(envMapKay)
    }

    private fun createEnvHelpMenu(floor: Floor, name: String): Menu {
        val envMenu = Menu(name)
        envMenu.items.add(createMenuItem({
            Logop.warn("No environments defined, please see instructions in the response field below.")
            floor.updateResponse("It looks like you have no environments defined.\n\n" +
                    "Please add properties files to your ${establishBaseDirectory()} directory.\n\n" +
                    "For example, if you want a DEV environment, you would name it DEV.properties.\n" +
                    "(it will also recognize and load .txt and .preferences file extensions.) \n\n" +
                    "Next, please define the variables you want available, as name value pairs,\n" +
                    "something like:\n\n" +
                    "protocol=http://\n" +
                    "server=localhost\n" +
                    "port=:8080\n" +
                    "context=beam\n\n" +
                    "In this example, your URL would then look like this: \n\n" +
                    "\${protocol}\${server}\${port}\${context}/af\n\n" +
                    "For the control freaks out there, if you wish to change the environment\n" +
                    "definitions directory, create an environment variable on your system with the key:\n\n" +
                    PANOPSET_SKYSCRAPER_ENVS
            )
        }, "Help"))
        return envMenu
    }

    override fun getApplicationDisplayName(): String {
        return "Skyscraper"
    }

    override fun getDescription(): String {
        return "API tester. Scrape info from the clouds."
    }

    private fun createMainBorderPaneCenter(fxDoc: FxDoc): BorderPane {
        val floor = getFloor(fxDoc)
        val bp = BorderPane()
        val vBox = VBox()
        vBox.children.add(createTopControlPane(bp, floor));
        vBox.maxWidth = Double.MAX_VALUE;
        vBox.maxHeight = Double.MAX_VALUE;
        bp.top = vBox
        updateCenterBorderPaneCenterCenter(bp, floor)
        HBox.setHgrow(bp, Priority.ALWAYS)
        return bp
    }

    private fun updateCenterBorderPaneCenterCenter(bp: BorderPane, floor: Floor) {
        Platform.runLater {
            bp.center = selectCenterPane(floor)
        }
    }

    private fun selectCenterPane(floor: Floor): SplitPane {
        val splitPaneID = "bodyResponseSplitPane"
        return when (floor.postMethodChoiceBox.value) {
            HttpMethod.GET.name -> {
                createPanSplitPane(floor.fxDoc, splitPaneID, floor.responseTextArea)
            }
            HttpMethod.POST.name -> {
                createPanSplitPane(floor.fxDoc, splitPaneID, floor.bodyTextArea, floor.responseTextArea)
            }
            else -> {
                createPanSplitPane(floor.fxDoc, splitPaneID, floor.responseTextArea)
            }
        }
    }

    private fun createTopControlPane(bp: BorderPane, floor: Floor): Pane {
        val rtn = createPanHBox(
            createPanButton({ doSend(floor) }, "_send", true, "Send the request."),
            floor.postMethodChoiceBox,
            createPanVBoxHGrow(floor.urlTextField, floor.urlOutField)

        )
        floor.postMethodChoiceBox.onAction = EventHandler {
            updateCenterBorderPaneCenterCenter(bp, floor)
        }
        return rtn
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Skyscraper().go()
        }
    }

}

private fun doSend(floor: Floor) {
    val actualURL = floor.urlOutField.text
    when (floor.postMethodChoiceBox.value) {
        HttpMethod.GET.name -> {
            doGet(actualURL, floor.responseTextArea)
        }
        HttpMethod.POST.name -> {
            doPost(actualURL, floor.bodyTextArea, floor.responseTextArea)
        }
    }
}

private fun doPost(urlString: String, bodyTextField: TextArea, responseTextArea: TextArea) {
    clrmsg(responseTextArea)
    val startTime = System.currentTimeMillis()
    processResponse(doPostHttp(urlString, bodyTextField.text), urlString, responseTextArea, startTime)
}

private fun doGet(urlString: String, responseTextArea: TextArea) {
    clrmsg(responseTextArea)
    val startTime = System.currentTimeMillis()
    processResponse(doGetHttp(urlString), urlString, responseTextArea, startTime)
}

private fun clrmsg(ta: TextArea) {
    Platform.runLater {
        ta.text = ""
    }
}

private fun processResponse(rp: HttpResponsePackage, urlString: String, responseTextArea: TextArea, startTime: Long) {
    if (rp.responseCode == 200) {
        val finishTime = System.currentTimeMillis()
        val totalTime = finishTime - startTime
        Logop.green("Completed in $totalTime ms. $urlString")
    } else {
        if (rp.responseCode == -1) {
            Logop.errorMsg("No response was received, ${rp.errorMessage}")
        } else {
            Logop.errorMsg("Response code was ${rp.responseCode}, ${rp.errorMessage}")
        }
    }
    Platform.runLater {
        responseTextArea.text = rp.text
    }
}

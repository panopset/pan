package com.panopset.fxapp

import com.panopset.compat.HiddenFolder
import com.panopset.compat.Logz
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import java.util.*
import kotlin.collections.ArrayList

abstract class BrandedApp: PanApplication(), AppDDSFX {

    private lateinit var deskApp4X: DeskApp4FX

    abstract fun createDynapane(fxDoc: FxDoc): Pane

    abstract fun updateVersionMessage()

    fun go() {
        HiddenFolder.companyName = getCompanyName().trim().lowercase().replace(" ", "_")
        DeskApp4XFactory
            .withPanApplication(this)
            .withBrandedApp(this)
            .withAppDDSFX(this)
        JavaFXapp.dds = this
        JavaFXapp.doLaunch()
    }

    class ThemeButtonWrapper(fxDoc: FxDoc, private val menuBarStatusMessage: TextField) {
        val button = Button()
        private fun setDarkTheme(fxDoc: FxDoc, button: Button) {
            button.text = "printer"
            fxDoc.scene.root.style = darkTheme;
            menuBarStatusMessage.style = "${FontManagerFX.getCurrentBaseStyle()}; -fx-text-fill: #00dd00"
        }

        private fun setLightTheme(fxDoc: FxDoc, button: Button) {
            button.text = "screen"
            fxDoc.scene.root.style = "";
            menuBarStatusMessage.style = "${FontManagerFX.getCurrentBaseStyle()}; -fx-text-fill: #339933"
        }
        init {
            button.text = "printer"
            button.onAction = EventHandler {
                if (button.text.indexOf("een") > -1) {
                    setDarkTheme(fxDoc, button)
                } else {
                    setLightTheme(fxDoc, button)
                }
            }
            FontManagerFX.register(button)
        }
    }

    final override fun createPane(fxDoc: FxDoc, deskApp4FX: DeskApp4FX): Pane {
        val rtn = DeskApp4XFactory.brandedApp.createDynapane(fxDoc)

        //val baseClass: Class<out PanApplication?> = fxDoc.application.javaClass
        // TODO: val i8nBundle = baseClass.getPackage().name + ".bundles." + baseClass.simpleName
        return rtn
    }

    final override fun createFaviconImage(): Image? {
        val logoName = "/${this.javaClass.simpleName.lowercase(Locale.getDefault())}.png"
        var inputStream = javaClass.getResourceAsStream(logoName)
        if (inputStream == null) {
            inputStream = javaClass.getResourceAsStream("/logo16.png")
        }
        if (inputStream == null) {
            Logz.errorMsg("Could not find image $logoName")
            return null
        } else {
            return Image(inputStream)
        }
    }

    fun createStandardMenubarBorderPane(fxDoc: FxDoc): BorderPane {
        val borderPane = BorderPane()
        borderPane.top = createMenuBar(fxDoc)
        return borderPane
    }

    private fun createMenuBar(fxDoc: FxDoc): HBox {
        val panMenuBar = HBox()
        fxDoc.menuBarStatusMessage = createPanOutputTextField()
        panMenuBar.children.add(createMenuBarFx(fxDoc))
        panMenuBar.children.add(ThemeButtonWrapper(fxDoc, fxDoc.menuBarStatusMessage).button)
        panMenuBar.children.add(createMenuBarStatusPane(fxDoc.menuBarStatusMessage))
        updateVersionMessage()
        return panMenuBar
    }

    private fun createMenuBarStatusPane(menuBarStatusMessage: TextField): HBox {
        val menuBarStatusPane = HBox()
        menuBarStatusMessage.isFocusTraversable = false
        menuBarStatusMessage.isEditable = false
        menuBarStatusMessage.id = MBSM
        FontManagerFX.register(menuBarStatusMessage)
        HBox.setHgrow(menuBarStatusPane, Priority.ALWAYS)
        HBox.setHgrow(menuBarStatusMessage, Priority.ALWAYS)
        menuBarStatusPane.children.add(menuBarStatusMessage)
        return menuBarStatusPane
    }

    private fun createMenuBarFx(fxDoc: FxDoc): MenuBar {
        val menuBar = MenuBar()
        menuBar.menus.add(createFileMenu(fxDoc))
        addAppMenus(fxDoc, menuBar.menus)
        menuBar.menus.add(createFontMenu())
        menuBar.menus.add(createHelpMenu(fxDoc))
        FontManagerFX.registerMenubar(menuBar)
        return menuBar
    }

    open fun addAppMenus(fxDoc: FxDoc, menus: ObservableList<Menu>) {

    }

    private fun createFileMenu(fxDoc: FxDoc): Menu {
        val menu = Menu("_File")
        menu.items.add(createFileMenuItem(fxDoc, { JavaFXapp.newWindow() }, "_New"))
        menu.items.add(createFileMenuItem(fxDoc, { JavaFXapp.openWindowFromFile() }, "_Open"))
        menu.items.add(createFileMenuItem(fxDoc, { JavaFXapp.saveWindow(fxDoc) }, "_Save"))
        menu.items.add(createFileMenuItem(fxDoc, { JavaFXapp.saveWindowAs(fxDoc) }, "Save as"))
        menu.items.add(createFileMenuItem(fxDoc, { JavaFXapp.closeWindow(fxDoc) }, "_Close"))
        menu.items.add(createFileMenuItem(fxDoc, { JavaFXapp.panExit() }, "_Quit"))
        menu.isMnemonicParsing = true
        return menu
    }

    private fun createFontMenu(): Menu {
        val cmf = object: PanCheckboxMenuFactory("Font") {
            override fun assignAction(
                panCheckboxMenuItem: PanCheckboxMenuItem,
                panCheckboxMenu: PanCheckboxMenu
            ) {
                panCheckboxMenuItem.checkboxMenuItem.onAction = EventHandler {
                    FontManagerFX.setFontSize(FontSize.findFromName(panCheckboxMenuItem.checkboxMenuItem.text.uppercase()))
                    panCheckboxMenu.setTheCurrentSelection(panCheckboxMenuItem.checkboxMenuItem.text)
                }
            }
            override fun isThisMatch(panCheckboxMenuItem: PanCheckboxMenuItem, value: String): Boolean {
                 return FontSize.findFromName(value).name == panCheckboxMenuItem.checkboxMenuItem.text
            }
            override fun populateListOfMenuItemNames(menuItemNames: ArrayList<String>){
                for (fontSize in FontSize.entries) {
                    menuItemNames.add(fontSize.name)
                }
            }
        }
        return cmf.constructWithInitialValue(FontManagerFX.getCurrentFontSizeName()).menu
    }

    private fun createHelpMenu(fxDoc: FxDoc): Menu {
        val menu = Menu("Help")
        menu.items.add(createMenuItem({ JavaFXapp.showLogPanel(fxDoc) }, "Show log"))
        menu.items.add(createMenuItem({ JavaFXapp.showAboutPanel(fxDoc) }, "About"))
        return menu
    }

    fun createMenuItem(doRequestedAction: () -> Unit, text: String): MenuItem {
        val rtn = MenuItem()
        rtn.onAction = EventHandler {
            doRequestedAction()
        }
        rtn.text = text
        rtn.isMnemonicParsing = true
        return rtn
    }

    private fun createFileMenuItem(fxDoc: FxDoc, doRequestedAction: (fxDoc: FxDoc) -> Unit, text: String): MenuItem {
        val menuItem = MenuItem()
        menuItem.onAction = EventHandler {
            doRequestedAction(fxDoc)
        }
        menuItem.text = text
        menuItem.isMnemonicParsing = true
        return menuItem
    }
}

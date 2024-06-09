@echo off
set R=%USERPROFILE%\.m2\repository

rem update ll.sh, lm.sh, and shoring/pom.xml deploy.properties as well.
set MP=^
%R%/org/jetbrains/kotlin/kotlin-stdlib/%KV%/kotlin-stdlib-%KV%.jar;^
%R%/org/jetbrains/kotlin/kotlin-reflect/%KV%/kotlin-reflect-%KV%.jar;^
%R%/org/openjfx/javafx-base/%FXV%/javafx-base-%FXV%-%FX_ARCH%.jar;^
%R%/org/openjfx/javafx-graphics/%FXV%/javafx-graphics-%FXV%-%FX_ARCH%.jar;^
%R%/org/openjfx/javafx-controls/%FXV%/javafx-controls-%FXV%-%FX_ARCH%.jar;^
%R%/org/openjfx/javafx-web/%FXV%/javafx-web-%FXV%-%FX_ARCH%.jar;^
%R%/org/openjfx/javafx-media/%FXV%/javafx-media-%FXV%-%FX_ARCH%.jar;^
%R%/com/google/code/gson/gson/%GSONV%/gson-%GSONV%.jar;^
%R%/com/panopset/compat/%PV%/compat-%PV%.jar;^
%R%/com/panopset/desk/%PV%/desk-%PV%.jar;^
%R%/com/panopset/blackjackEngine/%PV%/blackjackEngine-%PV%.jar;^
%R%/com/panopset/skyscraper/%PV%/skyscraper-%PV%.jar;^
%R%/com/panopset/flywheel/%PV%/flywheel-%PV%.jar;^
%R%/com/panopset/fxapp/%PV%/fxapp-%PV%.jar

jpackage ^
  -n panopset ^
  -p %JAVA_HOME%\jmods;%MP% ^
  -m com.panopset.desk/com.panopset.compat.AppVersion ^
  --vendor "Panopset" ^
  --copyright "1996-2024 Karl Dinwiddie" ^
  --license-file LICENSE ^
  --description "Panopset desktop applications." ^
  --add-launcher version=launchers/version.properties ^
  --add-launcher flywheel=launchers/flywheel.properties ^
  --add-launcher blackjack=launchers/blackjack.properties ^
  --add-launcher skyscraper=launchers/skyscraper.properties ^
  --add-launcher checksum=launchers/checksum.properties ^
  --add-launcher scrambler=launchers/scrambler.properties ^
  --add-launcher lowerclass=launchers/lowerclass.properties ^
  --app-version %PV% ^
  --dest target ^
  --win-dir-chooser ^
  --win-console ^
  --type msi


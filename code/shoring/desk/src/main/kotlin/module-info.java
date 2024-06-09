module com.panopset.desk {
  requires transitive com.google.gson;
  requires transitive com.panopset.blackjackEngine;
  requires transitive com.panopset.flywheel;
  requires transitive com.panopset.fxapp;
  requires transitive java.desktop;
  requires javafx.web;
  requires kotlin.stdlib;

  opens com.panopset.marin.bootstrap to com.google.gson;
  opens com.panopset.desk.utilities to com.google.gson;
  
  exports com.panopset.desk.utilities;
  exports com.panopset.desk.security;
  exports com.panopset.desk.games;
}

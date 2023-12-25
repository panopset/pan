module com.panopset.fxapp {
  requires transitive com.panopset.compat;
  requires transitive javafx.base;
  requires transitive javafx.controls;
  requires transitive javafx.graphics;
    requires kotlin.stdlib;

  exports com.panopset.fxapp;
  exports com.panopset.sample;
}

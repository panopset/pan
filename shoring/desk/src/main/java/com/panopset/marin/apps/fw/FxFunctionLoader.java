package com.panopset.marin.apps.fw;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;
import com.panopset.flywheel.Flywheel;
import com.panopset.flywheel.FlywheelFunction;
import com.panopset.flywheel.ReflectionInvoker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FxFunctionLoader {

  public void loadUpFunctions(ComboBox<FlywheelFunction> cbFunctions, TextArea fwTemplate) {
    populateDropdown(cbFunctions);
    wireUpEvents(cbFunctions, fwTemplate);
  }

  private void wireUpEvents(ComboBox<FlywheelFunction> cbFunctions, TextArea fwTemplate) {
    cbFunctions.setOnAction(event -> fwTemplate.appendText(cbFunctions.getValue().getExample()));
  }

  private void populateDropdown(ComboBox<FlywheelFunction> cbFunctions) {
    Flywheel.defineAllowedScriptCalls();
    cbFunctions.getItems().add(new FlywheelFunction());
    
    Collection<FlywheelFunction> allBut_dt = Collections.synchronizedSortedSet(new TreeSet<>());
    for (FlywheelFunction ff : ReflectionInvoker.getAll()) {
      if (!"dt".equalsIgnoreCase(ff.getKey())) {
        allBut_dt.add(ff);
      }
    }
    cbFunctions.getItems().addAll(allBut_dt);
  }
}

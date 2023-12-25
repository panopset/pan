package com.panopset.marin.apps.fw;

import com.panopset.flywheel.samples.FlywheelSample;
import com.panopset.flywheel.samples.FlywheelSamples;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FxSampleLoader {

  public void loadUpSamplesComboBox(ComboBox<String> comboBox, TextArea fwInput, TextArea fwTemplate,
      CheckBox fwLineBreaks, CheckBox fwListBreaks, TextField fwTokens, TextField fwSplitz) {
    comboBox.getItems().add("");
    for (FlywheelSample fs : FlywheelSamples.all()) {
      comboBox.getItems().add(fs.getDesc());
    }
    comboBox.setOnAction(event -> {
      String desc = comboBox.getValue();
      FlywheelSample fs = FlywheelSamples.find(desc);
      if (fs != null) {
        fwInput.setText(fs.getListText());
        fwTemplate.setText(fs.getTemplateText());
        fwLineBreaks.setSelected(fs.getLineBreaks());
        fwListBreaks.setSelected(fs.getListBreaks());
        fwTokens.setText(fs.getTokens());
        fwSplitz.setText(fs.getSplitz());
      }
    });
  }
}

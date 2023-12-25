package com.panopset.compat;

public class NameValuePair {
  private String name;
  private String value;

  public NameValuePair() {
    this("", "");
  }

  public NameValuePair(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    if (name == null) {
      name = "";
    }
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getValue() {
    if (value == null) {
      value = "";
    }
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }
}

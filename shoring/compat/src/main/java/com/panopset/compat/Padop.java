package com.panopset.compat;

import java.io.StringWriter;

public class Padop {

  private Padop() {}

  public static String rightPad(String inp, int width) {
    return rightPad(inp, width, ' ');
  }

  public static String rightPad(String inp, int width, char padding) {
    if (inp == null) {
      return "";
    }
    StringWriter sw = new StringWriter();
    sw.append(inp);
    int i = inp.length();
    while (i++ < width) {
      sw.append(padding);
    }
    return sw.toString();
  }

  public static String leftPad(String inp, int width) {
    return leftPad(inp, width, ' ');
  }

  public static String leftPad(String inp, int width, char padding) {
    if (inp == null) {
      return "";
    }
    StringWriter sw = new StringWriter();
    int i = inp.length();
    while (i++ < width) {
      sw.append(padding);
    }
    sw.append(inp);
    return sw.toString();
  }
}

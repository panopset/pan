package com.panopset.compat;

import java.net.URL;

public class Sysop {
  
  private Sysop() {}

  public static void setSysProp(String key, String value) {
    if (Stringop.isPopulated(value)) {
      System.setProperty(key, value);
    }
  }
  
  public static String getSysProp(String key) {
    return System.getProperty(key);
  }

  public static void setSysPropFromUrlFilePath(String key, URL url) {
    if (url != null) {
      String path = url.getFile();
      System.setProperty(key, path);
    }
  }
}

package com.panopset.compat;

import java.io.IOException;

public interface PersistentMap {

  void put(String key, String value) throws IOException;

  String get(String key) throws IOException;

  String get(String key, String dft) throws IOException;

}

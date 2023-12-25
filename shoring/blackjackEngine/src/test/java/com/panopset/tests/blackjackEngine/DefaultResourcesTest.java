package com.panopset.tests.blackjackEngine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Stringop;
import com.panopset.blackjackEngine.DefaultResources;

public class DefaultResourcesTest {

  @Test
  void test() {
    String rawData = DefaultResources.getDefaultBasicStrategy();
    Assertions.assertEquals(" 11  Dh Dh Dh Dh Dh Dh Dh Dh Dh Hd", Stringop.stringToList(rawData).get(5));
    rawData = DefaultResources.getDefaultCountingSystems();
    Assertions.assertEquals(" 0 +1 +1 +1 +1  0  0  0 -1  0 Hi-Opt I", Stringop.stringToList(rawData).get(5));
  }
}

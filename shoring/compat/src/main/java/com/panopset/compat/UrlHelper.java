package com.panopset.compat;

import static com.panopset.compat.HttpClientAbstractKt.doGetHttp;

public class UrlHelper {

  public static String getTextFromURL(final String urlStr) {
      return doGetHttp(urlStr).getText();
  }
}

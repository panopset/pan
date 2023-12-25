package com.panopset.compat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * General system information.
 *
 */
public final class SysInfo {

  /**
   * Information is updated with each call.
   *
   * @return System information rtn.
   */
  public static Map<String, String> getMap() {
    final Map<String, String> rtn = new HashMap<String, String>();
    String name = "Un-able to determine, see log.";
    try {
      name = InetAddress.getLocalHost().getCanonicalHostName();
    } catch (UnknownHostException e) {
      Logop.errorEx(e);
    }
    rtn.put("Name", name);

    rtn.put("Processors", "" + Runtime.getRuntime().availableProcessors());

    rtn.put("Total memory", "" + Runtime.getRuntime().totalMemory());

    rtn.put("Max memory", "" + Runtime.getRuntime().maxMemory());

    rtn.put("Free memory", "" + Runtime.getRuntime().freeMemory());

    rtn.put(
        "Operating system",
        System.getProperty("os.name") + " "
            + System.getProperty("sun.os.patch.level"));

    rtn.put(
        "CPU",
        System.getProperty("os.arch") + "," + "" + ""
            + System.getProperty("sun.arch.data.model"));

    rtn.put("CPU compatibility", System.getProperty("sun.cpu.isalist"));

    rtn.put(Nls.xlate("Language"), Locale.getDefault().getLanguage());
    return rtn;
  }

  /**
   * Prevent instantiation.
   */
  private SysInfo() {
  }

}

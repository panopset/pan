package com.panopset.flywheel;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import com.panopset.compat.Fileop;
import com.panopset.compat.Stringop;

/**
 * Map wrapper with a map name.
 *
 * @param <K>
 *          Key type.
 * @param <V>
 *          Value type.
 */
public final class NamedMap<K, V> {

  /**
   * Report unused keys. Numeric keys are left out because they are generally
   * reserved for lists.
   * 
   * @param file
   *          File to write report to.
   */
  public static void reportUnusedKeys(final String file) throws IOException {
    KEY_REPORTER.reportUnusedKeys(file);
  }

  private static final KeyReporter KEY_REPORTER = new KeyReporter();

  private final String name;

  private final Map<K, V> map;

  public Map<K, V> getMap() {
    return map;
  }

  public NamedMap(final String newName) {
    this(newName, Collections.synchronizedMap(new TreeMap<K, V>()));
  }

  public NamedMap(final String newName, final Map<K, V> newMap) {
    name = newName;
    map = newMap;
  }

  public void put(final K key, final V value) {
    KEY_REPORTER.reportDefinedKey(name, key.toString());
    map.put(key, value);
  }

  public V get(final K key) {
    KEY_REPORTER.reportUsedKey(name, key.toString());
    return map.get(key);
  }

  public int getSize() {
    return map == null ? 0 : map.size();
  }

  public KeyReporter getKeyReporter() {
    return KEY_REPORTER;
  }

  /**
   * Key Reporter is used to help see any un-used variables quickly.
   */
  public static final class KeyReporter {

    KeyReport getKeyReport(final String source) {
      KeyReport rtn = keyReports.get(source);
      if (rtn == null) {
        rtn = new KeyReport();
        keyReports.put(source, rtn);
      }
      return rtn;
    }

    void reportUsedKey(final String source, final String key) {
      KeyReport keyReport = getKeyReport(source);
      if (!keyReport.getUsedKeys().contains(key)) {
        keyReport.getUsedKeys().add(key);
      }
    }

    void reportDefinedKey(final String source, final String key) {
      KeyReport keyReport = getKeyReport(source);
      if (!keyReport.getDefinedKeys().contains(key)) {
        keyReport.getDefinedKeys().add(key);
      }
    }

    static final int MAXIMUM_NUMERICS = 100;

    void reportUnusedKeys(final String file) throws IOException {
      StringWriter sw = new StringWriter();
      for (String source : keyReports.keySet()) {
        StringWriter hdr = new StringWriter();
        hdr.append("*********" + source + ":");
        hdr.append(Stringop.getEol());
        KeyReport keyReport = getKeyReport(source);
        for (String s : keyReport.getUsedKeys()) {
          keyReport.getDefinedKeys().remove(s);
        }
        for (int i = 0; i < MAXIMUM_NUMERICS; i++) {
          keyReport.getDefinedKeys().remove("" + i);
        }
        keyReport.getDefinedKeys().remove(ReservedWords.FILE);
        keyReport.getDefinedKeys().remove(ReservedWords.SCRIPT);
        keyReport.getDefinedKeys().remove(ReservedWords.SPLITS);
        keyReport.getDefinedKeys().remove(ReservedWords.TARGET);
        keyReport.getDefinedKeys().remove(ReservedWords.TEMPLATE);
        keyReport.getDefinedKeys().remove(ReservedWords.TOKENS);
        boolean firstTime = true;
        for (String s : keyReport.getDefinedKeys()) {
          if (firstTime) {
            sw.append(hdr.toString());
            firstTime = false;
          }
          sw.append(s);
          sw.append(Stringop.getEol());
        }
      }
      Fileop.write(sw.toString(), new File(file));
    }

    private final Map<String, KeyReport> keyReports = Collections
        .synchronizedSortedMap(new TreeMap<String, KeyReport>());

    public static class KeyReport {
      private final List<String> usedKeys = new ArrayList<String>();

      private final Set<String> dfndKeys = Collections
          .synchronizedSortedSet(new TreeSet<String>());

      public Set<String> getDefinedKeys() {
        return dfndKeys;
      }

      public List<String> getUsedKeys() {
        return usedKeys;
      }
    }
  }
}

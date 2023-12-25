package com.panopset.compat;

public class SortedMapKey implements Comparable<SortedMapKey> {
  private final String sorter;
  private final String name;

  public SortedMapKey(String sorter, String name) {
    this.sorter = sorter;
    this.name = name;
  }

  public String getSorter() {
    return sorter;
  }

  public String getName() {
    return name;
  }

  @Override
  public int compareTo(SortedMapKey o) {
    return this.getSorter().compareTo(o.getSorter());
  }
}

package com.panopset.compat;

public interface MathDefaults {
  default Integer notNull(Integer value, Integer defaultValue) {
    return value == null ? defaultValue : value;
  }
  
  default Long notNull(Long value, Long defaultValue) {
    return value == null ? defaultValue : value;
  }
  
  default Double notNull(Double value, Double defaultValue) {
    return value == null ? defaultValue : value;
  }
}

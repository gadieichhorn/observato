package com.rds.observato.validation;

import com.google.common.base.Strings;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class Validator {

  public static void check(boolean expression, String message) {
    if (!expression) {
      throw new ValidationException(message);
    }
  }

  public static void checkIsNullOrEmpty(Collection collection, String message) {
    checkIsNull(collection, message);
    if (collection.isEmpty()) {
      throw new ValidationException("<%s> collection is empty".formatted(message));
    }
  }

  public static void checkIsNullOrEmpty(Map map, String message) {
    checkIsNull(map, message);
    if (map.isEmpty()) {
      throw new ValidationException("<%s> map is empty".formatted(message));
    }
  }

  public static void checkIsNullOrEmpty(String value, String message) {
    if (Strings.isNullOrEmpty(value)) {
      throw new ValidationException("<%s> is required non empty string value".formatted(message));
    }
  }

  public static void checkIsNull(Object obj, String message) {
    if (Objects.isNull(obj)) {
      throw new ValidationException("<%s> is required non null".formatted(message));
    }
  }

  public static void checkIsNullOrNegative(Long value, String message) {
    if (Objects.isNull(value) || value <= 0L) {
      throw new ValidationException("<%s> is required non null".formatted(message));
    }
  }
}

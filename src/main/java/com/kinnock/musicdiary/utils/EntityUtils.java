package com.kinnock.musicdiary.utils;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class EntityUtils {
  public static <T> T resolveUpdatedFieldValue(
      Supplier<T> dtoFieldGetter,
      Predicate<T> dtoFieldValidityCheck,
      Supplier<T> entityFieldGetter
  ) {
    var dtoField = dtoFieldGetter.get();
    return dtoFieldValidityCheck.test(dtoField) ? dtoField : entityFieldGetter.get();
  }
}

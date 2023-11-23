package com.kinnock.musicdiary.utils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;

public class EntityUtils {
  // TODO: maybe generic CRUD for all of it?

  // not super fond of how this is changing the value of the entity within but it's convenient
  // I'll let this pattern blow up on me.
  public static <T> void updateEntityValue(
      Supplier<T> newValueSupplier,
      Predicate<T> newValueValidityCheck,
      Consumer<T> entityFieldSetter
  ) {
    var dtoField = newValueSupplier.get();
    if (newValueValidityCheck.test(dtoField)) {
      entityFieldSetter.accept(dtoField);
    }
  }

  public static <T> void updateNonNullEntityValue(
      Supplier<T> newValueSupplier,
      Consumer<T> entityFieldSetter
  ) {
    EntityUtils.updateEntityValue(newValueSupplier, Objects::nonNull, entityFieldSetter);
  }

  public static void updateNonBlankStringValue(
      Supplier<String> newValueSupplier,
      Consumer<String> entityFieldSetter
  ) {
    EntityUtils.updateEntityValue(newValueSupplier, StringUtils::isNotBlank, entityFieldSetter);
  }
}

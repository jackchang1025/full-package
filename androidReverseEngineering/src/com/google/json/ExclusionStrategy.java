package com.google.json;

public interface ExclusionStrategy {
   boolean shouldSkipClass(Class<?> var1);

   boolean shouldSkipField(FieldAttributes var1);
}

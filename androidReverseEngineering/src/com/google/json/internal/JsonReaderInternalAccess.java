package com.google.json.internal;

import com.google.json.stream.JsonReader;

public abstract class JsonReaderInternalAccess {
   public static JsonReaderInternalAccess INSTANCE;

   public abstract void promoteNameToValue(JsonReader var1);
}

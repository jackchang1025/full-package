package com.google.json.stream;

public enum JsonToken {
   BEGIN_ARRAY,
   BEGIN_OBJECT,
   BOOLEAN,
   END_ARRAY,
   END_DOCUMENT,
   END_OBJECT,
   NAME,
   NULL,
   NUMBER,
   STRING;
   private static final JsonToken[] $VALUES = $values();
}

package com.google.json;

public final class JsonNull extends JsonElement {
   public static final JsonNull INSTANCE = new JsonNull();

   public JsonNull deepCopy() {
      return INSTANCE;
   }

   @Override
   public boolean equals(Object var1) {
      return var1 instanceof JsonNull;
   }

   @Override
   public int hashCode() {
      return JsonNull.class.hashCode();
   }
}

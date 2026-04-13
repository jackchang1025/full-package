package com.google.json;

import com.google.json.internal.Streams;
import com.google.json.stream.JsonWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class JsonElement {
   public abstract JsonElement deepCopy();

   public BigDecimal getAsBigDecimal() {
      throw new UnsupportedOperationException(this.getClass().getSimpleName());
   }

   public BigInteger getAsBigInteger() {
      throw new UnsupportedOperationException(this.getClass().getSimpleName());
   }

   public boolean getAsBoolean() {
      throw new UnsupportedOperationException(this.getClass().getSimpleName());
   }

   public byte getAsByte() {
      throw new UnsupportedOperationException(this.getClass().getSimpleName());
   }

   @Deprecated
   public char getAsCharacter() {
      throw new UnsupportedOperationException(this.getClass().getSimpleName());
   }

   public double getAsDouble() {
      throw new UnsupportedOperationException(this.getClass().getSimpleName());
   }

   public float getAsFloat() {
      throw new UnsupportedOperationException(this.getClass().getSimpleName());
   }

   public int getAsInt() {
      throw new UnsupportedOperationException(this.getClass().getSimpleName());
   }

   public JsonArray getAsJsonArray() {
      if (this.isJsonArray()) {
         return (JsonArray)this;
      } else {
         StringBuilder var1 = new StringBuilder("Not a JSON Array: ");
         var1.append(this);
         throw new IllegalStateException(var1.toString());
      }
   }

   public JsonNull getAsJsonNull() {
      if (this.isJsonNull()) {
         return (JsonNull)this;
      } else {
         StringBuilder var1 = new StringBuilder("Not a JSON Null: ");
         var1.append(this);
         throw new IllegalStateException(var1.toString());
      }
   }

   public JsonObject getAsJsonObject() {
      if (this.isJsonObject()) {
         return (JsonObject)this;
      } else {
         StringBuilder var1 = new StringBuilder("Not a JSON Object: ");
         var1.append(this);
         throw new IllegalStateException(var1.toString());
      }
   }

   public JsonPrimitive getAsJsonPrimitive() {
      if (this.isJsonPrimitive()) {
         return (JsonPrimitive)this;
      } else {
         StringBuilder var1 = new StringBuilder("Not a JSON Primitive: ");
         var1.append(this);
         throw new IllegalStateException(var1.toString());
      }
   }

   public long getAsLong() {
      throw new UnsupportedOperationException(this.getClass().getSimpleName());
   }

   public Number getAsNumber() {
      throw new UnsupportedOperationException(this.getClass().getSimpleName());
   }

   public short getAsShort() {
      throw new UnsupportedOperationException(this.getClass().getSimpleName());
   }

   public String getAsString() {
      throw new UnsupportedOperationException(this.getClass().getSimpleName());
   }

   public boolean isJsonArray() {
      return this instanceof JsonArray;
   }

   public boolean isJsonNull() {
      return this instanceof JsonNull;
   }

   public boolean isJsonObject() {
      return this instanceof JsonObject;
   }

   public boolean isJsonPrimitive() {
      return this instanceof JsonPrimitive;
   }

   @Override
   public String toString() {
      try {
         StringWriter var2 = new StringWriter();
         JsonWriter var1 = new JsonWriter(var2);
         var1.setLenient(true);
         Streams.write(this, var1);
         return var2.toString();
      } catch (IOException var3) {
         throw new AssertionError(var3);
      }
   }
}

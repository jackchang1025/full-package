package com.google.json;

import com.google.json.internal.LazilyParsedNumber;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public final class JsonPrimitive extends JsonElement {
   private final Object value;

   public JsonPrimitive(Boolean var1) {
      Objects.requireNonNull(var1);
      this.value = var1;
   }

   public JsonPrimitive(Character var1) {
      Objects.requireNonNull(var1);
      this.value = var1.toString();
   }

   public JsonPrimitive(Number var1) {
      Objects.requireNonNull(var1);
      this.value = var1;
   }

   public JsonPrimitive(String var1) {
      Objects.requireNonNull(var1);
      this.value = var1;
   }

   private static boolean isIntegral(JsonPrimitive var0) {
      Object var4 = var0.value;
      boolean var3 = var4 instanceof Number;
      boolean var2 = false;
      boolean var1 = var2;
      if (var3) {
         var4 = (Number)var4;
         if (!(var4 instanceof BigInteger) && !(var4 instanceof Long) && !(var4 instanceof Integer) && !(var4 instanceof Short) && !(var4 instanceof Byte)) {
            return var2;
         }

         var1 = true;
      }

      return var1;
   }

   public JsonPrimitive deepCopy() {
      return this;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var8 = true;
      boolean var7 = true;
      boolean var6 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && JsonPrimitive.class == var1.getClass()) {
         JsonPrimitive var9 = (JsonPrimitive)var1;
         if (this.value == null) {
            if (var9.value != null) {
               var6 = false;
            }

            return var6;
         } else if (isIntegral(this) && isIntegral(var9)) {
            if (this.getAsNumber().longValue() == var9.getAsNumber().longValue()) {
               var6 = var8;
            } else {
               var6 = false;
            }

            return var6;
         } else {
            var1 = this.value;
            if (var1 instanceof Number && var9.value instanceof Number) {
               double var4 = this.getAsNumber().doubleValue();
               double var2 = var9.getAsNumber().doubleValue();
               var6 = var7;
               if (var4 != var2) {
                  if (Double.isNaN(var4) && Double.isNaN(var2)) {
                     var6 = var7;
                  } else {
                     var6 = false;
                  }
               }

               return var6;
            } else {
               return var1.equals(var9.value);
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public BigDecimal getAsBigDecimal() {
      BigDecimal var1 = (BigDecimal)this.value;
      if (var1 instanceof BigDecimal) {
         var1 = var1;
      } else {
         var1 = new BigDecimal(this.getAsString());
      }

      return var1;
   }

   @Override
   public BigInteger getAsBigInteger() {
      BigInteger var1 = (BigInteger)this.value;
      if (var1 instanceof BigInteger) {
         var1 = var1;
      } else {
         var1 = new BigInteger(this.getAsString());
      }

      return var1;
   }

   @Override
   public boolean getAsBoolean() {
      return this.isBoolean() ? (Boolean)this.value : Boolean.parseBoolean(this.getAsString());
   }

   @Override
   public byte getAsByte() {
      byte var1;
      if (this.isNumber()) {
         var1 = this.getAsNumber().byteValue();
      } else {
         var1 = Byte.parseByte(this.getAsString());
      }

      return var1;
   }

   @Deprecated
   @Override
   public char getAsCharacter() {
      String var1 = this.getAsString();
      if (!var1.isEmpty()) {
         return var1.charAt(0);
      } else {
         throw new UnsupportedOperationException("String value is empty");
      }
   }

   @Override
   public double getAsDouble() {
      double var1;
      if (this.isNumber()) {
         var1 = this.getAsNumber().doubleValue();
      } else {
         var1 = Double.parseDouble(this.getAsString());
      }

      return var1;
   }

   @Override
   public float getAsFloat() {
      float var1;
      if (this.isNumber()) {
         var1 = this.getAsNumber().floatValue();
      } else {
         var1 = Float.parseFloat(this.getAsString());
      }

      return var1;
   }

   @Override
   public int getAsInt() {
      int var1;
      if (this.isNumber()) {
         var1 = this.getAsNumber().intValue();
      } else {
         var1 = Integer.parseInt(this.getAsString());
      }

      return var1;
   }

   @Override
   public long getAsLong() {
      long var1;
      if (this.isNumber()) {
         var1 = this.getAsNumber().longValue();
      } else {
         var1 = Long.parseLong(this.getAsString());
      }

      return var1;
   }

   @Override
   public Number getAsNumber() {
      Object var1 = this.value;
      if (var1 instanceof Number) {
         return (Number)var1;
      } else if (var1 instanceof String) {
         return new LazilyParsedNumber((String)var1);
      } else {
         throw new UnsupportedOperationException("Primitive is neither a number nor a string");
      }
   }

   @Override
   public short getAsShort() {
      short var1;
      if (this.isNumber()) {
         var1 = this.getAsNumber().shortValue();
      } else {
         var1 = Short.parseShort(this.getAsString());
      }

      return var1;
   }

   @Override
   public String getAsString() {
      StringBuilder var1 = (StringBuilder)this.value;
      if (var1 instanceof String) {
         return (String)var1;
      } else if (this.isNumber()) {
         return this.getAsNumber().toString();
      } else if (this.isBoolean()) {
         return ((Boolean)this.value).toString();
      } else {
         var1 = new StringBuilder("Unexpected value type: ");
         var1.append(this.value.getClass());
         throw new AssertionError(var1.toString());
      }
   }

   @Override
   public int hashCode() {
      if (this.value == null) {
         return 31;
      } else {
         long var1;
         if (isIntegral(this)) {
            var1 = this.getAsNumber().longValue();
         } else {
            Object var3 = this.value;
            if (!(var3 instanceof Number)) {
               return var3.hashCode();
            }

            var1 = Double.doubleToLongBits(this.getAsNumber().doubleValue());
         }

         return (int)(var1 >>> 32 ^ var1);
      }
   }

   public boolean isBoolean() {
      return this.value instanceof Boolean;
   }

   public boolean isNumber() {
      return this.value instanceof Number;
   }

   public boolean isString() {
      return this.value instanceof String;
   }
}

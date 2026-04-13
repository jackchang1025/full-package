package com.google.json.internal;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;

public final class LazilyParsedNumber extends Number {
   private final String value;

   public LazilyParsedNumber(String var1) {
      this.value = var1;
   }

   private void readObject(ObjectInputStream var1) {
      throw new InvalidObjectException("Deserialization is unsupported");
   }

   private Object writeReplace() {
      return new BigDecimal(this.value);
   }

   @Override
   public double doubleValue() {
      return Double.parseDouble(this.value);
   }

   @Override
   public boolean equals(Object var1) {
      boolean var3 = true;
      if (this == var1) {
         return true;
      } else if (var1 instanceof LazilyParsedNumber) {
         LazilyParsedNumber var4 = (LazilyParsedNumber)var1;
         var1 = this.value;
         String var6 = var4.value;
         boolean var2 = var3;
         if (var1 != var6) {
            if (var1.equals(var6)) {
               var2 = var3;
            } else {
               var2 = false;
            }
         }

         return var2;
      } else {
         return false;
      }
   }

   @Override
   public float floatValue() {
      return Float.parseFloat(this.value);
   }

   @Override
   public int hashCode() {
      return this.value.hashCode();
   }

   @Override
   public int intValue() {
      try {
         return Integer.parseInt(this.value);
      } catch (NumberFormatException var6) {
         long var2;
         try {
            var2 = Long.parseLong(this.value);
         } catch (NumberFormatException var5) {
            return new BigDecimal(this.value).intValue();
         }

         return (int)var2;
      }
   }

   @Override
   public long longValue() {
      try {
         return Long.parseLong(this.value);
      } catch (NumberFormatException var4) {
         return new BigDecimal(this.value).longValue();
      }
   }

   @Override
   public String toString() {
      return this.value;
   }
}

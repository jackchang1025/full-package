package com.google.json;

import a.a;
import com.google.json.internal.LazilyParsedNumber;
import com.google.json.stream.JsonReader;
import com.google.json.stream.MalformedJsonException;
import java.math.BigDecimal;

public enum ToNumberPolicy implements ToNumberStrategy {
   BIG_DECIMAL {
      public BigDecimal readNumber(JsonReader var1) {
         String var3 = var1.nextString();

         try {
            return new BigDecimal(var3);
         } catch (NumberFormatException var4) {
            StringBuilder var5 = a.s("Cannot parse ", var3, "; at path ");
            var5.append(var1.getPreviousPath());
            throw new JsonParseException(var5.toString(), var4);
         }
      }
   },
   DOUBLE {
      public Double readNumber(JsonReader var1) {
         return var1.nextDouble();
      }
   },
   LAZILY_PARSED_NUMBER {
      @Override
      public Number readNumber(JsonReader var1) {
         return new LazilyParsedNumber(var1.nextString());
      }
   },
   LONG_OR_DOUBLE {
      @Override
      public Number readNumber(JsonReader var1) {
         String var4 = var1.nextString();

         long var2;
         try {
            var2 = Long.parseLong(var4);
         } catch (NumberFormatException var9) {
            try {
               Double var6 = Double.valueOf(var4);
               if ((var6.isInfinite() || var6.isNaN()) && !var1.isLenient()) {
                  StringBuilder var5 = new StringBuilder("JSON forbids NaN and infinities: ");
                  var5.append(var6);
                  var5.append("; at path ");
                  var5.append(var1.getPreviousPath());
                  MalformedJsonException var7 = new MalformedJsonException(var5.toString());
                  throw var7;
               }

               return var6;
            } catch (NumberFormatException var8) {
               StringBuilder var10 = a.s("Cannot parse ", var4, "; at path ");
               var10.append(var1.getPreviousPath());
               throw new JsonParseException(var10.toString(), var8);
            }
         }

         return var2;
      }
   };
   private static final ToNumberPolicy[] $VALUES = $values();

   private ToNumberPolicy() {
   }
}

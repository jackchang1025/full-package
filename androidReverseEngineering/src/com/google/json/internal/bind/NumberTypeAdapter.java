package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.JsonSyntaxException;
import com.google.json.ToNumberPolicy;
import com.google.json.ToNumberStrategy;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;

public final class NumberTypeAdapter extends TypeAdapter<Number> {
   private static final TypeAdapterFactory LAZILY_PARSED_NUMBER_FACTORY = newFactory(ToNumberPolicy.LAZILY_PARSED_NUMBER);
   private final ToNumberStrategy toNumberStrategy;

   private NumberTypeAdapter(ToNumberStrategy var1) {
      this.toNumberStrategy = var1;
   }

   public static TypeAdapterFactory getFactory(ToNumberStrategy var0) {
      return var0 == ToNumberPolicy.LAZILY_PARSED_NUMBER ? LAZILY_PARSED_NUMBER_FACTORY : newFactory(var0);
   }

   private static TypeAdapterFactory newFactory(ToNumberStrategy var0) {
      return new TypeAdapterFactory(new NumberTypeAdapter(var0)) {
         final NumberTypeAdapter val$adapter;

         {
            this.val$adapter = var1;
         }

         @Override
         public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
            NumberTypeAdapter var3;
            if (var2.getRawType() == Number.class) {
               var3 = this.val$adapter;
            } else {
               var3 = null;
            }

            return var3;
         }
      };
   }

   public Number read(JsonReader var1) {
      JsonToken var3 = var1.peek();
      int var2 = var3.ordinal();
      if (var2 != 1) {
         if (var2 != 2 && var2 != 3) {
            StringBuilder var4 = new StringBuilder("Expecting number, got: ");
            var4.append(var3);
            var4.append("; at path ");
            var4.append(var1.getPath());
            throw new JsonSyntaxException(var4.toString());
         } else {
            return this.toNumberStrategy.readNumber(var1);
         }
      } else {
         var1.nextNull();
         return null;
      }
   }

   public void write(JsonWriter var1, Number var2) {
      var1.value(var2);
   }
}

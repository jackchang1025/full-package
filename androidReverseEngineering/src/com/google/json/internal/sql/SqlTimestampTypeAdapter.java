package com.google.json.internal.sql;

import com.google.json.Gson;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonWriter;
import java.sql.Timestamp;
import java.util.Date;

class SqlTimestampTypeAdapter extends TypeAdapter<Timestamp> {
   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      @Override
      public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
         return var2.getRawType() == Timestamp.class ? new SqlTimestampTypeAdapter(var1.getAdapter(Date.class)) : null;
      }
   };
   private final TypeAdapter<Date> dateTypeAdapter;

   private SqlTimestampTypeAdapter(TypeAdapter<Date> var1) {
      this.dateTypeAdapter = var1;
   }

   public Timestamp read(JsonReader var1) {
      Date var2 = this.dateTypeAdapter.read(var1);
      Timestamp var3;
      if (var2 != null) {
         var3 = new Timestamp(var2.getTime());
      } else {
         var3 = null;
      }

      return var3;
   }

   public void write(JsonWriter var1, Timestamp var2) {
      this.dateTypeAdapter.write(var1, var2);
   }
}

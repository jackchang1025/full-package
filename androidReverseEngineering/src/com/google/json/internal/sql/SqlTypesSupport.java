package com.google.json.internal.sql;

import com.google.json.TypeAdapterFactory;
import com.google.json.internal.bind.DefaultDateTypeAdapter;
import java.sql.Timestamp;
import java.util.Date;

public final class SqlTypesSupport {
   public static final DefaultDateTypeAdapter.DateType<? extends Date> DATE_DATE_TYPE;
   public static final TypeAdapterFactory DATE_FACTORY;
   public static final boolean SUPPORTS_SQL_TYPES;
   public static final DefaultDateTypeAdapter.DateType<? extends Date> TIMESTAMP_DATE_TYPE;
   public static final TypeAdapterFactory TIMESTAMP_FACTORY;
   public static final TypeAdapterFactory TIME_FACTORY;

   static {
      boolean var0;
      label17: {
         try {
            Class.forName("java.sql.Date");
         } catch (ClassNotFoundException var2) {
            var0 = false;
            break label17;
         }

         var0 = true;
      }

      SUPPORTS_SQL_TYPES = var0;
      TypeAdapterFactory var1;
      if (var0) {
         DATE_DATE_TYPE = new DefaultDateTypeAdapter.DateType<java.sql.Date>(java.sql.Date.class) {
            public java.sql.Date deserialize(Date var1) {
               return new java.sql.Date(var1.getTime());
            }
         };
         TIMESTAMP_DATE_TYPE = new DefaultDateTypeAdapter.DateType<Timestamp>(Timestamp.class) {
            public Timestamp deserialize(Date var1) {
               return new Timestamp(var1.getTime());
            }
         };
         DATE_FACTORY = SqlDateTypeAdapter.FACTORY;
         TIME_FACTORY = SqlTimeTypeAdapter.FACTORY;
         var1 = SqlTimestampTypeAdapter.FACTORY;
      } else {
         var1 = null;
         DATE_DATE_TYPE = null;
         TIMESTAMP_DATE_TYPE = null;
         DATE_FACTORY = null;
         TIME_FACTORY = null;
      }

      TIMESTAMP_FACTORY = var1;
   }

   private SqlTypesSupport() {
   }
}

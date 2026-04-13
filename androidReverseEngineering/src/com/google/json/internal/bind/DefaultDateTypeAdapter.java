package com.google.json.internal.bind;

import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.internal.JavaVersion;
import com.google.json.internal.PreJava9DateFormatProvider;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class DefaultDateTypeAdapter<T extends Date> extends TypeAdapter<T> {
   private static final String SIMPLE_NAME = "DefaultDateTypeAdapter";
   private final List<DateFormat> dateFormats;
   private final DefaultDateTypeAdapter.DateType<T> dateType;

   private DefaultDateTypeAdapter(DefaultDateTypeAdapter.DateType<T> var1, int var2) {
      ArrayList var3 = new ArrayList();
      this.dateFormats = var3;
      Objects.requireNonNull(var1);
      this.dateType = var1;
      Locale var4 = Locale.US;
      var3.add(DateFormat.getDateInstance(var2, var4));
      if (!Locale.getDefault().equals(var4)) {
         var3.add(DateFormat.getDateInstance(var2));
      }

      if (JavaVersion.isJava9OrLater()) {
         var3.add(PreJava9DateFormatProvider.getUSDateFormat(var2));
      }
   }

   private DefaultDateTypeAdapter(DefaultDateTypeAdapter.DateType<T> var1, int var2, int var3) {
      ArrayList var4 = new ArrayList();
      this.dateFormats = var4;
      Objects.requireNonNull(var1);
      this.dateType = var1;
      Locale var5 = Locale.US;
      var4.add(DateFormat.getDateTimeInstance(var2, var3, var5));
      if (!Locale.getDefault().equals(var5)) {
         var4.add(DateFormat.getDateTimeInstance(var2, var3));
      }

      if (JavaVersion.isJava9OrLater()) {
         var4.add(PreJava9DateFormatProvider.getUSDateTimeFormat(var2, var3));
      }
   }

   private DefaultDateTypeAdapter(DefaultDateTypeAdapter.DateType<T> var1, String var2) {
      ArrayList var3 = new ArrayList();
      this.dateFormats = var3;
      Objects.requireNonNull(var1);
      this.dateType = var1;
      Locale var4 = Locale.US;
      var3.add(new SimpleDateFormat(var2, var4));
      if (!Locale.getDefault().equals(var4)) {
         var3.add(new SimpleDateFormat(var2));
      }
   }

   private Date deserializeToDate(JsonReader param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 1
      // 01: invokevirtual com/google/json/stream/JsonReader.nextString ()Ljava/lang/String;
      // 04: astore 2
      // 05: aload 0
      // 06: getfield com/google/json/internal/bind/DefaultDateTypeAdapter.dateFormats Ljava/util/List;
      // 09: astore 3
      // 0a: aload 3
      // 0b: monitorenter
      // 0c: aload 0
      // 0d: getfield com/google/json/internal/bind/DefaultDateTypeAdapter.dateFormats Ljava/util/List;
      // 10: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 15: astore 4
      // 17: aload 4
      // 19: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 1e: ifeq 3a
      // 21: aload 4
      // 23: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 28: checkcast java/text/DateFormat
      // 2b: astore 5
      // 2d: aload 5
      // 2f: aload 2
      // 30: invokevirtual java/text/DateFormat.parse (Ljava/lang/String;)Ljava/util/Date;
      // 33: astore 5
      // 35: aload 3
      // 36: monitorexit
      // 37: aload 5
      // 39: areturn
      // 3a: aload 3
      // 3b: monitorexit
      // 3c: new java/text/ParsePosition
      // 3f: astore 3
      // 40: aload 3
      // 41: bipush 0
      // 42: invokespecial java/text/ParsePosition.<init> (I)V
      // 45: aload 2
      // 46: aload 3
      // 47: invokestatic com/google/json/internal/bind/util/ISO8601Utils.parse (Ljava/lang/String;Ljava/text/ParsePosition;)Ljava/util/Date;
      // 4a: astore 3
      // 4b: aload 3
      // 4c: areturn
      // 4d: astore 3
      // 4e: ldc "Failed parsing '"
      // 50: aload 2
      // 51: ldc "' as Date; at path "
      // 53: invokestatic a/a.s (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 56: astore 2
      // 57: aload 2
      // 58: aload 1
      // 59: invokevirtual com/google/json/stream/JsonReader.getPreviousPath ()Ljava/lang/String;
      // 5c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 5f: pop
      // 60: new com/google/json/JsonSyntaxException
      // 63: dup
      // 64: aload 2
      // 65: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 68: aload 3
      // 69: invokespecial com/google/json/JsonSyntaxException.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
      // 6c: athrow
      // 6d: astore 1
      // 6e: aload 3
      // 6f: monitorexit
      // 70: aload 1
      // 71: athrow
      // 72: astore 5
      // 74: goto 17
   }

   public T read(JsonReader var1) {
      if (var1.peek() == JsonToken.NULL) {
         var1.nextNull();
         return null;
      } else {
         Date var2 = this.deserializeToDate(var1);
         return this.dateType.deserialize(var2);
      }
   }

   @Override
   public String toString() {
      DateFormat var1 = this.dateFormats.get(0);
      StringBuilder var2;
      String var3;
      if (var1 instanceof SimpleDateFormat) {
         var2 = new StringBuilder("DefaultDateTypeAdapter(");
         var3 = ((SimpleDateFormat)var1).toPattern();
      } else {
         var2 = new StringBuilder("DefaultDateTypeAdapter(");
         var3 = var1.getClass().getSimpleName();
      }

      var2.append(var3);
      var2.append(')');
      return var2.toString();
   }

   public void write(JsonWriter param1, Date param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 2
      // 01: ifnonnull 0a
      // 04: aload 1
      // 05: invokevirtual com/google/json/stream/JsonWriter.nullValue ()Lcom/google/json/stream/JsonWriter;
      // 08: pop
      // 09: return
      // 0a: aload 0
      // 0b: getfield com/google/json/internal/bind/DefaultDateTypeAdapter.dateFormats Ljava/util/List;
      // 0e: bipush 0
      // 0f: invokeinterface java/util/List.get (I)Ljava/lang/Object; 2
      // 14: checkcast java/text/DateFormat
      // 17: astore 4
      // 19: aload 0
      // 1a: getfield com/google/json/internal/bind/DefaultDateTypeAdapter.dateFormats Ljava/util/List;
      // 1d: astore 3
      // 1e: aload 3
      // 1f: monitorenter
      // 20: aload 4
      // 22: aload 2
      // 23: invokevirtual java/text/DateFormat.format (Ljava/util/Date;)Ljava/lang/String;
      // 26: astore 2
      // 27: aload 3
      // 28: monitorexit
      // 29: aload 1
      // 2a: aload 2
      // 2b: invokevirtual com/google/json/stream/JsonWriter.value (Ljava/lang/String;)Lcom/google/json/stream/JsonWriter;
      // 2e: pop
      // 2f: return
      // 30: astore 1
      // 31: aload 3
      // 32: monitorexit
      // 33: aload 1
      // 34: athrow
   }

   public abstract static class DateType<T extends Date> {
      public static final DefaultDateTypeAdapter.DateType<Date> DATE = new DefaultDateTypeAdapter.DateType<Date>(Date.class) {
         @Override
         public Date deserialize(Date var1) {
            return var1;
         }
      };
      private final Class<T> dateClass;

      public DateType(Class<T> var1) {
         this.dateClass = var1;
      }

      private TypeAdapterFactory createFactory(DefaultDateTypeAdapter<T> var1) {
         return TypeAdapters.newFactory(this.dateClass, var1);
      }

      public final TypeAdapterFactory createAdapterFactory(int var1) {
         return this.createFactory(new DefaultDateTypeAdapter<>(this, var1));
      }

      public final TypeAdapterFactory createAdapterFactory(int var1, int var2) {
         return this.createFactory(new DefaultDateTypeAdapter<>(this, var1, var2));
      }

      public final TypeAdapterFactory createAdapterFactory(String var1) {
         return this.createFactory(new DefaultDateTypeAdapter<>(this, var1));
      }

      public final TypeAdapterFactory createDefaultsAdapterFactory() {
         return this.createFactory(new DefaultDateTypeAdapter<>(this, 2, 2));
      }

      public abstract T deserialize(Date var1);
   }
}

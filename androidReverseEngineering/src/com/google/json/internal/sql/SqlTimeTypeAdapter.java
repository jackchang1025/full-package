package com.google.json.internal.sql;

import com.google.json.Gson;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonWriter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

final class SqlTimeTypeAdapter extends TypeAdapter<Time> {
   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      @Override
      public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
         Class var4 = var2.getRawType();
         SqlTimeTypeAdapter var3 = null;
         if (var4 == Time.class) {
            var3 = new SqlTimeTypeAdapter();
         }

         return var3;
      }
   };
   private final DateFormat format = new SimpleDateFormat("hh:mm:ss a");

   private SqlTimeTypeAdapter() {
   }

   public Time read(JsonReader param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 1
      // 01: invokevirtual com/google/json/stream/JsonReader.peek ()Lcom/google/json/stream/JsonToken;
      // 04: getstatic com/google/json/stream/JsonToken.NULL Lcom/google/json/stream/JsonToken;
      // 07: if_acmpne 10
      // 0a: aload 1
      // 0b: invokevirtual com/google/json/stream/JsonReader.nextNull ()V
      // 0e: aconst_null
      // 0f: areturn
      // 10: aload 1
      // 11: invokevirtual com/google/json/stream/JsonReader.nextString ()Ljava/lang/String;
      // 14: astore 2
      // 15: aload 0
      // 16: monitorenter
      // 17: aload 0
      // 18: getfield com/google/json/internal/sql/SqlTimeTypeAdapter.format Ljava/text/DateFormat;
      // 1b: aload 2
      // 1c: invokevirtual java/text/DateFormat.parse (Ljava/lang/String;)Ljava/util/Date;
      // 1f: astore 4
      // 21: new java/sql/Time
      // 24: astore 3
      // 25: aload 3
      // 26: aload 4
      // 28: invokevirtual java/util/Date.getTime ()J
      // 2b: invokespecial java/sql/Time.<init> (J)V
      // 2e: aload 0
      // 2f: monitorexit
      // 30: aload 3
      // 31: areturn
      // 32: astore 3
      // 33: aload 0
      // 34: monitorexit
      // 35: aload 3
      // 36: athrow
      // 37: astore 3
      // 38: ldc "Failed parsing '"
      // 3a: aload 2
      // 3b: ldc "' as SQL Time; at path "
      // 3d: invokestatic a/a.s (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 40: astore 2
      // 41: aload 2
      // 42: aload 1
      // 43: invokevirtual com/google/json/stream/JsonReader.getPreviousPath ()Ljava/lang/String;
      // 46: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 49: pop
      // 4a: new com/google/json/JsonSyntaxException
      // 4d: dup
      // 4e: aload 2
      // 4f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 52: aload 3
      // 53: invokespecial com/google/json/JsonSyntaxException.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
      // 56: athrow
   }

   public void write(JsonWriter param1, Time param2) {
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
      // 0b: monitorenter
      // 0c: aload 0
      // 0d: getfield com/google/json/internal/sql/SqlTimeTypeAdapter.format Ljava/text/DateFormat;
      // 10: aload 2
      // 11: invokevirtual java/text/DateFormat.format (Ljava/util/Date;)Ljava/lang/String;
      // 14: astore 2
      // 15: aload 0
      // 16: monitorexit
      // 17: aload 1
      // 18: aload 2
      // 19: invokevirtual com/google/json/stream/JsonWriter.value (Ljava/lang/String;)Lcom/google/json/stream/JsonWriter;
      // 1c: pop
      // 1d: return
      // 1e: astore 1
      // 1f: aload 0
      // 20: monitorexit
      // 21: aload 1
      // 22: athrow
   }
}

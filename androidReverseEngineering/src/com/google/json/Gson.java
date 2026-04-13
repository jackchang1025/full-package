package com.google.json;

import a1.q;
import android.support.annotation.NonNull;
import com.google.json.internal.ConstructorConstructor;
import com.google.json.internal.Excluder;
import com.google.json.internal.LazilyParsedNumber;
import com.google.json.internal.Primitives;
import com.google.json.internal.Streams;
import com.google.json.internal.bind.ArrayTypeAdapter;
import com.google.json.internal.bind.CollectionTypeAdapterFactory;
import com.google.json.internal.bind.DateTypeAdapter;
import com.google.json.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.json.internal.bind.JsonTreeReader;
import com.google.json.internal.bind.JsonTreeWriter;
import com.google.json.internal.bind.MapTypeAdapterFactory;
import com.google.json.internal.bind.NumberTypeAdapter;
import com.google.json.internal.bind.ObjectTypeAdapter;
import com.google.json.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.json.internal.bind.SerializationDelegatingTypeAdapter;
import com.google.json.internal.bind.TypeAdapters;
import com.google.json.internal.sql.SqlTypesSupport;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import com.google.json.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public final class Gson {
   static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
   static final String DEFAULT_DATE_PATTERN;
   static final boolean DEFAULT_ESCAPE_HTML = true;
   static final FieldNamingStrategy DEFAULT_FIELD_NAMING_STRATEGY = FieldNamingPolicy.IDENTITY;
   static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
   static final boolean DEFAULT_LENIENT = false;
   static final ToNumberStrategy DEFAULT_NUMBER_TO_NUMBER_STRATEGY = ToNumberPolicy.LAZILY_PARSED_NUMBER;
   static final ToNumberStrategy DEFAULT_OBJECT_TO_NUMBER_STRATEGY = ToNumberPolicy.DOUBLE;
   static final boolean DEFAULT_PRETTY_PRINT = false;
   static final boolean DEFAULT_SERIALIZE_NULLS = false;
   static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
   static final boolean DEFAULT_USE_JDK_UNSAFE = true;
   private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
   static final String TAG = "com.google.json.Gson";
   final List<TypeAdapterFactory> builderFactories;
   final List<TypeAdapterFactory> builderHierarchyFactories;
   final boolean complexMapKeySerialization;
   private final ConstructorConstructor constructorConstructor;
   final String datePattern;
   final int dateStyle;
   final Excluder excluder;
   final List<TypeAdapterFactory> factories;
   final FieldNamingStrategy fieldNamingStrategy;
   final boolean generateNonExecutableJson;
   final boolean htmlSafe;
   final Map<Type, InstanceCreator<?>> instanceCreators;
   private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
   final boolean lenient;
   final LongSerializationPolicy longSerializationPolicy;
   final ToNumberStrategy numberToNumberStrategy;
   final ToNumberStrategy objectToNumberStrategy;
   final boolean prettyPrinting;
   final List<ReflectionAccessFilter> reflectionFilters;
   final boolean serializeNulls;
   final boolean serializeSpecialFloatingPointValues;
   private final ThreadLocal<Map<TypeToken<?>, TypeAdapter<?>>> threadLocalAdapterResults = new ThreadLocal<>();
   final int timeStyle;
   private final ConcurrentMap<TypeToken<?>, TypeAdapter<?>> typeTokenCache = new ConcurrentHashMap<>();
   final boolean useJdkUnsafe;

   public Gson() {
      this(
         Excluder.DEFAULT,
         DEFAULT_FIELD_NAMING_STRATEGY,
         Collections.emptyMap(),
         false,
         false,
         false,
         true,
         false,
         false,
         false,
         true,
         LongSerializationPolicy.DEFAULT,
         DEFAULT_DATE_PATTERN,
         2,
         2,
         Collections.emptyList(),
         Collections.emptyList(),
         Collections.emptyList(),
         DEFAULT_OBJECT_TO_NUMBER_STRATEGY,
         DEFAULT_NUMBER_TO_NUMBER_STRATEGY,
         Collections.emptyList()
      );
   }

   public Gson(
      Excluder var1,
      FieldNamingStrategy var2,
      Map<Type, InstanceCreator<?>> var3,
      boolean var4,
      boolean var5,
      boolean var6,
      boolean var7,
      boolean var8,
      boolean var9,
      boolean var10,
      boolean var11,
      LongSerializationPolicy var12,
      String var13,
      int var14,
      int var15,
      List<TypeAdapterFactory> var16,
      List<TypeAdapterFactory> var17,
      List<TypeAdapterFactory> var18,
      ToNumberStrategy var19,
      ToNumberStrategy var20,
      List<ReflectionAccessFilter> var21
   ) {
      this.excluder = var1;
      this.fieldNamingStrategy = var2;
      this.instanceCreators = var3;
      ConstructorConstructor var22 = new ConstructorConstructor(var3, var11, var21);
      this.constructorConstructor = var22;
      this.serializeNulls = var4;
      this.complexMapKeySerialization = var5;
      this.generateNonExecutableJson = var6;
      this.htmlSafe = var7;
      this.prettyPrinting = var8;
      this.lenient = var9;
      this.serializeSpecialFloatingPointValues = var10;
      this.useJdkUnsafe = var11;
      this.longSerializationPolicy = var12;
      this.datePattern = var13;
      this.dateStyle = var14;
      this.timeStyle = var15;
      this.builderFactories = var16;
      this.builderHierarchyFactories = var17;
      this.objectToNumberStrategy = var19;
      this.numberToNumberStrategy = var20;
      this.reflectionFilters = var21;
      ArrayList var25 = new ArrayList();
      var25.add(TypeAdapters.JSON_ELEMENT_FACTORY);
      var25.add(ObjectTypeAdapter.getFactory(var19));
      var25.add(var1);
      var25.addAll(var18);
      var25.add(TypeAdapters.STRING_FACTORY);
      var25.add(TypeAdapters.INTEGER_FACTORY);
      var25.add(TypeAdapters.BOOLEAN_FACTORY);
      var25.add(TypeAdapters.BYTE_FACTORY);
      var25.add(TypeAdapters.SHORT_FACTORY);
      TypeAdapter var23 = longAdapter(var12);
      var25.add(TypeAdapters.newFactory(long.class, Long.class, var23));
      var25.add(TypeAdapters.newFactory(double.class, Double.class, this.doubleAdapter(var10)));
      var25.add(TypeAdapters.newFactory(float.class, Float.class, this.floatAdapter(var10)));
      var25.add(NumberTypeAdapter.getFactory(var20));
      var25.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
      var25.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
      var25.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter(var23)));
      var25.add(TypeAdapters.newFactory(AtomicLongArray.class, atomicLongArrayAdapter(var23)));
      var25.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
      var25.add(TypeAdapters.CHARACTER_FACTORY);
      var25.add(TypeAdapters.STRING_BUILDER_FACTORY);
      var25.add(TypeAdapters.STRING_BUFFER_FACTORY);
      var25.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
      var25.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
      var25.add(TypeAdapters.newFactory(LazilyParsedNumber.class, TypeAdapters.LAZILY_PARSED_NUMBER));
      var25.add(TypeAdapters.URL_FACTORY);
      var25.add(TypeAdapters.URI_FACTORY);
      var25.add(TypeAdapters.UUID_FACTORY);
      var25.add(TypeAdapters.CURRENCY_FACTORY);
      var25.add(TypeAdapters.LOCALE_FACTORY);
      var25.add(TypeAdapters.INET_ADDRESS_FACTORY);
      var25.add(TypeAdapters.BIT_SET_FACTORY);
      var25.add(DateTypeAdapter.FACTORY);
      var25.add(TypeAdapters.CALENDAR_FACTORY);
      if (SqlTypesSupport.SUPPORTS_SQL_TYPES) {
         var25.add(SqlTypesSupport.TIME_FACTORY);
         var25.add(SqlTypesSupport.DATE_FACTORY);
         var25.add(SqlTypesSupport.TIMESTAMP_FACTORY);
      }

      var25.add(ArrayTypeAdapter.FACTORY);
      var25.add(TypeAdapters.CLASS_FACTORY);
      var25.add(new CollectionTypeAdapterFactory(var22));
      var25.add(new MapTypeAdapterFactory(var22, var5));
      JsonAdapterAnnotationTypeAdapterFactory var24 = new JsonAdapterAnnotationTypeAdapterFactory(var22);
      this.jsonAdapterFactory = var24;
      var25.add(var24);
      var25.add(TypeAdapters.ENUM_FACTORY);
      var25.add(new ReflectiveTypeAdapterFactory(var22, var2, var1, var24, var21));
      this.factories = Collections.unmodifiableList(var25);
   }

   private static void assertFullConsumption(Object var0, JsonReader var1) {
      if (var0 != null) {
         try {
            if (var1.peek() != JsonToken.END_DOCUMENT) {
               var0 = new JsonSyntaxException("JSON document was not fully consumed.");
               throw var0;
            }
         } catch (MalformedJsonException var2) {
            throw new JsonSyntaxException(var2);
         } catch (IOException var3) {
            throw new JsonIOException(var3);
         }
      }
   }

   private static TypeAdapter<AtomicLong> atomicLongAdapter(TypeAdapter<Number> var0) {
      return (new TypeAdapter<AtomicLong>(var0) {
         final TypeAdapter val$longAdapter;

         {
            this.val$longAdapter = var1;
         }

         public AtomicLong read(JsonReader var1) {
            return new AtomicLong(((Number)this.val$longAdapter.read(var1)).longValue());
         }

         public void write(JsonWriter var1, AtomicLong var2) {
            this.val$longAdapter.write(var1, var2.get());
         }
      }).nullSafe();
   }

   private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(TypeAdapter<Number> var0) {
      return (new TypeAdapter<AtomicLongArray>(var0) {
         final TypeAdapter val$longAdapter;

         {
            this.val$longAdapter = var1;
         }

         public AtomicLongArray read(JsonReader var1) {
            ArrayList var4 = new ArrayList();
            var1.beginArray();

            while (var1.hasNext()) {
               var4.add(((Number)this.val$longAdapter.read(var1)).longValue());
            }

            var1.endArray();
            int var3 = var4.size();
            AtomicLongArray var5 = new AtomicLongArray(var3);

            for (int var2 = 0; var2 < var3; var2++) {
               var5.set(var2, (Long)var4.get(var2));
            }

            return var5;
         }

         public void write(JsonWriter var1, AtomicLongArray var2) {
            var1.beginArray();
            int var4 = var2.length();

            for (int var3 = 0; var3 < var4; var3++) {
               this.val$longAdapter.write(var1, var2.get(var3));
            }

            var1.endArray();
         }
      }).nullSafe();
   }

   public static void checkValidFloatingPoint(double var0) {
      if (Double.isNaN(var0) || Double.isInfinite(var0)) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var0);
         var2.append(
            " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method."
         );
         throw new IllegalArgumentException(var2.toString());
      }
   }

   private TypeAdapter<Number> doubleAdapter(boolean var1) {
      return var1 ? TypeAdapters.DOUBLE : new TypeAdapter<Number>(this) {
         final Gson this$0;

         {
            this.this$0 = var1;
         }

         public Double read(JsonReader var1) {
            if (var1.peek() == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               return var1.nextDouble();
            }
         }

         public void write(JsonWriter var1, Number var2) {
            if (var2 == null) {
               var1.nullValue();
            } else {
               double var3 = var2.doubleValue();
               Gson.checkValidFloatingPoint(var3);
               var1.value(var3);
            }
         }
      };
   }

   private TypeAdapter<Number> floatAdapter(boolean var1) {
      return var1 ? TypeAdapters.FLOAT : new TypeAdapter<Number>(this) {
         final Gson this$0;

         {
            this.this$0 = var1;
         }

         public Float read(JsonReader var1) {
            if (var1.peek() == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               return (float)var1.nextDouble();
            }
         }

         public void write(JsonWriter var1, Number var2) {
            if (var2 == null) {
               var1.nullValue();
            } else {
               float var3 = var2.floatValue();
               Gson.checkValidFloatingPoint((double)var3);
               if (!(var2 instanceof Float)) {
                  var2 = var3;
               }

               var1.value((Number)var2);
            }
         }
      };
   }

   private static TypeAdapter<Number> longAdapter(LongSerializationPolicy var0) {
      return var0 == LongSerializationPolicy.DEFAULT ? TypeAdapters.LONG : new TypeAdapter<Number>() {
         public Number read(JsonReader var1) {
            if (var1.peek() == JsonToken.NULL) {
               var1.nextNull();
               return null;
            } else {
               return var1.nextLong();
            }
         }

         public void write(JsonWriter var1, Number var2) {
            if (var2 == null) {
               var1.nullValue();
            } else {
               var1.value(var2.toString());
            }
         }
      };
   }

   public void destroy() {
      try {
         this.threadLocalAdapterResults.remove();
         if (!this.typeTokenCache.isEmpty()) {
            this.typeTokenCache.clear();
         }

         if (!this.instanceCreators.isEmpty()) {
            this.instanceCreators.clear();
         }

         if (!this.builderFactories.isEmpty()) {
            this.builderFactories.clear();
         }

         if (!this.builderHierarchyFactories.isEmpty()) {
            this.builderHierarchyFactories.clear();
         }

         if (!this.reflectionFilters.isEmpty()) {
            this.reflectionFilters.clear();
         }
      } catch (Exception var2) {
         q.s(TAG, var2);
      }
   }

   @Deprecated
   public Excluder excluder() {
      return this.excluder;
   }

   public FieldNamingStrategy fieldNamingStrategy() {
      return this.fieldNamingStrategy;
   }

   @Override
   public void finalize() {
      this.destroy();
      super.finalize();
   }

   public <T> T fromJson(JsonElement var1, TypeToken<T> var2) {
      return var1 == null ? null : this.fromJson(new JsonTreeReader(var1), var2);
   }

   public <T> T fromJson(JsonElement var1, Class<T> var2) {
      Object var3 = this.fromJson(var1, TypeToken.get(var2));
      return Primitives.<T>wrap(var2).cast(var3);
   }

   public <T> T fromJson(JsonElement var1, Type var2) {
      return this.fromJson(var1, (TypeToken<T>)TypeToken.get(var2));
   }

   public <T> T fromJson(JsonReader param1, TypeToken<T> param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 1
      // 01: invokevirtual com/google/json/stream/JsonReader.isLenient ()Z
      // 04: istore 4
      // 06: bipush 1
      // 07: istore 3
      // 08: aload 1
      // 09: bipush 1
      // 0a: invokevirtual com/google/json/stream/JsonReader.setLenient (Z)V
      // 0d: aload 1
      // 0e: invokevirtual com/google/json/stream/JsonReader.peek ()Lcom/google/json/stream/JsonToken;
      // 11: pop
      // 12: bipush 0
      // 13: istore 3
      // 14: aload 0
      // 15: aload 2
      // 16: invokevirtual com/google/json/Gson.getAdapter (Lcom/google/json/reflect/TypeToken;)Lcom/google/json/TypeAdapter;
      // 19: aload 1
      // 1a: invokevirtual com/google/json/TypeAdapter.read (Lcom/google/json/stream/JsonReader;)Ljava/lang/Object;
      // 1d: astore 2
      // 1e: aload 1
      // 1f: iload 4
      // 21: invokevirtual com/google/json/stream/JsonReader.setLenient (Z)V
      // 24: aload 2
      // 25: areturn
      // 26: astore 2
      // 27: goto 8d
      // 2a: astore 2
      // 2b: new java/lang/AssertionError
      // 2e: astore 6
      // 30: new java/lang/StringBuilder
      // 33: astore 5
      // 35: aload 5
      // 37: ldc_w "AssertionError (GSON 2.10.1): "
      // 3a: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 3d: aload 5
      // 3f: aload 2
      // 40: invokevirtual java/lang/Throwable.getMessage ()Ljava/lang/String;
      // 43: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 46: pop
      // 47: aload 6
      // 49: aload 5
      // 4b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 4e: aload 2
      // 4f: invokespecial java/lang/AssertionError.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
      // 52: aload 6
      // 54: athrow
      // 55: astore 5
      // 57: new com/google/json/JsonSyntaxException
      // 5a: astore 2
      // 5b: aload 2
      // 5c: aload 5
      // 5e: invokespecial com/google/json/JsonSyntaxException.<init> (Ljava/lang/Throwable;)V
      // 61: aload 2
      // 62: athrow
      // 63: astore 2
      // 64: new com/google/json/JsonSyntaxException
      // 67: astore 5
      // 69: aload 5
      // 6b: aload 2
      // 6c: invokespecial com/google/json/JsonSyntaxException.<init> (Ljava/lang/Throwable;)V
      // 6f: aload 5
      // 71: athrow
      // 72: astore 2
      // 73: iload 3
      // 74: ifeq 7f
      // 77: aload 1
      // 78: iload 4
      // 7a: invokevirtual com/google/json/stream/JsonReader.setLenient (Z)V
      // 7d: aconst_null
      // 7e: areturn
      // 7f: new com/google/json/JsonSyntaxException
      // 82: astore 5
      // 84: aload 5
      // 86: aload 2
      // 87: invokespecial com/google/json/JsonSyntaxException.<init> (Ljava/lang/Throwable;)V
      // 8a: aload 5
      // 8c: athrow
      // 8d: aload 1
      // 8e: iload 4
      // 90: invokevirtual com/google/json/stream/JsonReader.setLenient (Z)V
      // 93: aload 2
      // 94: athrow
   }

   public <T> T fromJson(JsonReader var1, Type var2) {
      return this.fromJson(var1, (TypeToken<T>)TypeToken.get(var2));
   }

   public <T> T fromJson(Reader var1, TypeToken<T> var2) {
      JsonReader var3 = this.newJsonReader(var1);
      Object var4 = this.fromJson(var3, var2);
      assertFullConsumption(var4, var3);
      return (T)var4;
   }

   public <T> T fromJson(Reader var1, Class<T> var2) {
      Object var3 = this.fromJson(var1, TypeToken.get(var2));
      return Primitives.<T>wrap(var2).cast(var3);
   }

   public <T> T fromJson(Reader var1, Type var2) {
      return this.fromJson(var1, (TypeToken<T>)TypeToken.get(var2));
   }

   public <T> T fromJson(String var1, TypeToken<T> var2) {
      return var1 == null ? null : this.fromJson(new StringReader(var1), var2);
   }

   public <T> T fromJson(String var1, Class<T> var2) {
      Object var3 = this.fromJson(var1, TypeToken.get(var2));
      return Primitives.<T>wrap(var2).cast(var3);
   }

   public <T> T fromJson(String var1, Type var2) {
      return this.fromJson(var1, (TypeToken<T>)TypeToken.get(var2));
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public <T> TypeAdapter<T> getAdapter(TypeToken<T> var1) {
      Objects.requireNonNull(var1, "type must not be null");
      TypeAdapter var3 = this.typeTokenCache.get(var1);
      if (var3 != null) {
         return var3;
      } else {
         Object var4 = this.threadLocalAdapterResults.get();
         boolean var2;
         if (var4 == null) {
            var4 = new HashMap();
            this.threadLocalAdapterResults.set((Map<TypeToken<?>, TypeAdapter<?>>)var4);
            var2 = true;
         } else {
            var3 = (TypeAdapter)var4.get(var1);
            if (var3 != null) {
               return var3;
            }

            var2 = false;
         }

         label183: {
            TypeAdapter var5;
            label189: {
               Throwable var10000;
               label190: {
                  Gson.FutureTypeAdapter var6;
                  Iterator var7;
                  try {
                     var6 = new Gson.FutureTypeAdapter();
                     var4.put(var1, var6);
                     var7 = this.factories.iterator();
                  } catch (Throwable var19) {
                     var10000 = var19;
                     boolean var10001 = false;
                     break label190;
                  }

                  var3 = null;

                  while (true) {
                     try {
                        if (!var7.hasNext()) {
                           break label183;
                        }

                        var5 = ((TypeAdapterFactory)var7.next()).create(this, var1);
                     } catch (Throwable var18) {
                        var10000 = var18;
                        boolean var24 = false;
                        break;
                     }

                     var3 = var5;
                     if (var5 != null) {
                        try {
                           var6.setDelegate(var5);
                           var4.put(var1, var5);
                           break label189;
                        } catch (Throwable var17) {
                           var10000 = var17;
                           boolean var25 = false;
                           break;
                        }
                     }
                  }
               }

               Throwable var20 = var10000;
               if (var2) {
                  this.threadLocalAdapterResults.remove();
               }

               throw var20;
            }

            var3 = var5;
         }

         if (var2) {
            this.threadLocalAdapterResults.remove();
         }

         if (var3 != null) {
            if (var2) {
               this.typeTokenCache.putAll((Map<? extends TypeToken<?>, ? extends TypeAdapter<?>>)var4);
            }

            return var3;
         } else {
            StringBuilder var23 = new StringBuilder("GSON (2.10.1) cannot handle ");
            var23.append(var1);
            throw new IllegalArgumentException(var23.toString());
         }
      }
   }

   public <T> TypeAdapter<T> getAdapter(Class<T> var1) {
      return this.getAdapter(TypeToken.get(var1));
   }

   public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory var1, TypeToken<T> var2) {
      Object var4 = var1;
      if (!this.factories.contains(var1)) {
         var4 = this.jsonAdapterFactory;
      }

      Iterator var6 = this.factories.iterator();
      boolean var3 = false;

      while (var6.hasNext()) {
         TypeAdapterFactory var5 = (TypeAdapterFactory)var6.next();
         if (!var3) {
            if (var5 == var4) {
               var3 = true;
            }
         } else {
            TypeAdapter var8 = var5.create(this, var2);
            if (var8 != null) {
               return var8;
            }
         }
      }

      StringBuilder var7 = new StringBuilder("GSON cannot serialize ");
      var7.append(var2);
      throw new IllegalArgumentException(var7.toString());
   }

   public boolean htmlSafe() {
      return this.htmlSafe;
   }

   public GsonBuilder newBuilder() {
      return new GsonBuilder(this);
   }

   public JsonReader newJsonReader(Reader var1) {
      JsonReader var2 = new JsonReader(var1);
      var2.setLenient(this.lenient);
      return var2;
   }

   public JsonWriter newJsonWriter(Writer var1) {
      if (this.generateNonExecutableJson) {
         var1.write(")]}'\n");
      }

      JsonWriter var2 = new JsonWriter(var1);
      if (this.prettyPrinting) {
         var2.setIndent("  ");
      }

      var2.setHtmlSafe(this.htmlSafe);
      var2.setLenient(this.lenient);
      var2.setSerializeNulls(this.serializeNulls);
      return var2;
   }

   public boolean serializeNulls() {
      return this.serializeNulls;
   }

   public String toJson(JsonElement var1) {
      StringWriter var2 = new StringWriter();
      this.toJson(var1, var2);
      return var2.toString();
   }

   public String toJson(Object var1) {
      return var1 == null ? this.toJson((JsonElement)JsonNull.INSTANCE) : this.toJson(var1, var1.getClass());
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public String toJson(Object var1, Type var2) {
      Object var4 = null;
      String var3 = (String)var4;

      Exception var10000;
      label51: {
         StringWriter var5;
         try {
            var5 = new StringWriter();
         } catch (Exception var10) {
            var10000 = var10;
            boolean var10001 = false;
            break label51;
         }

         var3 = (String)var4;

         try {
            // [VF-FIX] var5./* $VF: Unable to resugar constructor */<init>();
         } catch (Exception var9) {
            var10000 = var9;
            boolean var13 = false;
            break label51;
         }

         var3 = (String)var4;

         try {
            this.toJson(var1, var2, var5);
         } catch (Exception var8) {
            var10000 = var8;
            boolean var14 = false;
            break label51;
         }

         var3 = (String)var4;

         try {
            var1 = var5.toString();
         } catch (Exception var7) {
            var10000 = var7;
            boolean var15 = false;
            break label51;
         }

         var3 = var1;

         try {
            var5.close();
            return var1;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var16 = false;
         }
      }

      Exception var12 = var10000;
      q.s(TAG, var12);
      return var3;
   }

   public void toJson(JsonElement var1, JsonWriter var2) {
      boolean var3 = var2.isLenient();
      var2.setLenient(true);
      boolean var4 = var2.isHtmlSafe();
      var2.setHtmlSafe(this.htmlSafe);
      boolean var5 = var2.getSerializeNulls();
      var2.setSerializeNulls(this.serializeNulls);

      try {
         Streams.write(var1, var2);
      } catch (IOException var11) {
         JsonIOException var15 = new JsonIOException(var11);
         throw var15;
      } catch (AssertionError var12) {
         StringBuilder var14 = new StringBuilder("AssertionError (GSON 2.10.1): ");
         var14.append(var12.getMessage());
         AssertionError var6 = new AssertionError(var14.toString(), var12);
         throw var6;
      } finally {
         var2.setLenient(var3);
         var2.setHtmlSafe(var4);
         var2.setSerializeNulls(var5);
      }
   }

   public void toJson(JsonElement var1, Appendable var2) {
      try {
         this.toJson(var1, this.newJsonWriter(Streams.writerForAppendable(var2)));
      } catch (IOException var3) {
         throw new JsonIOException(var3);
      }
   }

   public void toJson(Object var1, Appendable var2) {
      if (var1 != null) {
         this.toJson(var1, var1.getClass(), var2);
      } else {
         this.toJson((JsonElement)JsonNull.INSTANCE, var2);
      }
   }

   public void toJson(Object var1, Type var2, JsonWriter var3) {
      TypeAdapter var16 = this.getAdapter(TypeToken.get(var2));
      boolean var4 = var3.isLenient();
      var3.setLenient(true);
      boolean var6 = var3.isHtmlSafe();
      var3.setHtmlSafe(this.htmlSafe);
      boolean var5 = var3.getSerializeNulls();
      var3.setSerializeNulls(this.serializeNulls);

      try {
         var16.write(var3, var1);
      } catch (IOException var11) {
         var1 = new JsonIOException(var11);
         throw var1;
      } catch (AssertionError var12) {
         StringBuilder var17 = new StringBuilder("AssertionError (GSON 2.10.1): ");
         var17.append(var12.getMessage());
         AssertionError var14 = new AssertionError(var17.toString(), var12);
         throw var14;
      } finally {
         var3.setLenient(var4);
         var3.setHtmlSafe(var6);
         var3.setSerializeNulls(var5);
      }
   }

   public void toJson(Object var1, Type var2, Appendable var3) {
      try {
         this.toJson(var1, var2, this.newJsonWriter(Streams.writerForAppendable(var3)));
      } catch (IOException var4) {
         throw new JsonIOException(var4);
      }
   }

   public JsonElement toJsonTree(Object var1) {
      return (JsonElement)(var1 == null ? JsonNull.INSTANCE : this.toJsonTree(var1, var1.getClass()));
   }

   public JsonElement toJsonTree(Object var1, Type var2) {
      JsonTreeWriter var3 = new JsonTreeWriter();
      this.toJson(var1, var2, var3);
      return var3.get();
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("{serializeNulls:");
      var1.append(this.serializeNulls);
      var1.append(",factories:");
      var1.append(this.factories);
      var1.append(",instanceCreators:");
      var1.append(this.constructorConstructor);
      var1.append("}");
      return var1.toString();
   }

   public static class FutureTypeAdapter<T> extends SerializationDelegatingTypeAdapter<T> {
      private TypeAdapter<T> delegate = null;

      private TypeAdapter<T> delegate() {
         TypeAdapter var1 = this.delegate;
         if (var1 != null) {
            return var1;
         } else {
            throw new IllegalStateException("Adapter for type with cyclic dependency has been used before dependency has been resolved");
         }
      }

      @Override
      public TypeAdapter<T> getSerializationDelegate() {
         return this.delegate();
      }

      @Override
      public T read(JsonReader var1) {
         return this.delegate().read(var1);
      }

      public void setDelegate(TypeAdapter<T> var1) {
         if (this.delegate == null) {
            this.delegate = var1;
         } else {
            throw new AssertionError("Delegate is already set");
         }
      }

      @Override
      public void write(JsonWriter var1, T var2) {
         this.delegate().write(var1, (T)var2);
      }
   }
}

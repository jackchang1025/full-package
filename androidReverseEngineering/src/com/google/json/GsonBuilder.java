package com.google.json;

import com.google.json.internal.$Gson$Preconditions;
import com.google.json.internal.Excluder;
import com.google.json.internal.bind.DefaultDateTypeAdapter;
import com.google.json.internal.bind.TreeTypeAdapter;
import com.google.json.internal.bind.TypeAdapters;
import com.google.json.internal.sql.SqlTypesSupport;
import com.google.json.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class GsonBuilder {
   private boolean complexMapKeySerialization;
   private String datePattern;
   private int dateStyle;
   private boolean escapeHtmlChars;
   private Excluder excluder = Excluder.DEFAULT;
   private final List<TypeAdapterFactory> factories;
   private FieldNamingStrategy fieldNamingPolicy;
   private boolean generateNonExecutableJson;
   private final List<TypeAdapterFactory> hierarchyFactories;
   private final Map<Type, InstanceCreator<?>> instanceCreators;
   private boolean lenient;
   private LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
   private ToNumberStrategy numberToNumberStrategy;
   private ToNumberStrategy objectToNumberStrategy;
   private boolean prettyPrinting;
   private final LinkedList<ReflectionAccessFilter> reflectionFilters;
   private boolean serializeNulls;
   private boolean serializeSpecialFloatingPointValues;
   private int timeStyle;
   private boolean useJdkUnsafe;

   public GsonBuilder() {
      this.fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
      this.instanceCreators = new HashMap<>();
      this.factories = new ArrayList<>();
      this.hierarchyFactories = new ArrayList<>();
      this.serializeNulls = false;
      this.datePattern = Gson.DEFAULT_DATE_PATTERN;
      this.dateStyle = 2;
      this.timeStyle = 2;
      this.complexMapKeySerialization = false;
      this.serializeSpecialFloatingPointValues = false;
      this.escapeHtmlChars = true;
      this.prettyPrinting = false;
      this.generateNonExecutableJson = false;
      this.lenient = false;
      this.useJdkUnsafe = true;
      this.objectToNumberStrategy = Gson.DEFAULT_OBJECT_TO_NUMBER_STRATEGY;
      this.numberToNumberStrategy = Gson.DEFAULT_NUMBER_TO_NUMBER_STRATEGY;
      this.reflectionFilters = new LinkedList<>();
   }

   public GsonBuilder(Gson var1) {
      this.fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
      HashMap var2 = new HashMap();
      this.instanceCreators = var2;
      ArrayList var5 = new ArrayList();
      this.factories = var5;
      ArrayList var3 = new ArrayList();
      this.hierarchyFactories = var3;
      this.serializeNulls = false;
      this.datePattern = Gson.DEFAULT_DATE_PATTERN;
      this.dateStyle = 2;
      this.timeStyle = 2;
      this.complexMapKeySerialization = false;
      this.serializeSpecialFloatingPointValues = false;
      this.escapeHtmlChars = true;
      this.prettyPrinting = false;
      this.generateNonExecutableJson = false;
      this.lenient = false;
      this.useJdkUnsafe = true;
      this.objectToNumberStrategy = Gson.DEFAULT_OBJECT_TO_NUMBER_STRATEGY;
      this.numberToNumberStrategy = Gson.DEFAULT_NUMBER_TO_NUMBER_STRATEGY;
      LinkedList var4 = new LinkedList();
      this.reflectionFilters = var4;
      this.excluder = var1.excluder;
      this.fieldNamingPolicy = var1.fieldNamingStrategy;
      var2.putAll(var1.instanceCreators);
      this.serializeNulls = var1.serializeNulls;
      this.complexMapKeySerialization = var1.complexMapKeySerialization;
      this.generateNonExecutableJson = var1.generateNonExecutableJson;
      this.escapeHtmlChars = var1.htmlSafe;
      this.prettyPrinting = var1.prettyPrinting;
      this.lenient = var1.lenient;
      this.serializeSpecialFloatingPointValues = var1.serializeSpecialFloatingPointValues;
      this.longSerializationPolicy = var1.longSerializationPolicy;
      this.datePattern = var1.datePattern;
      this.dateStyle = var1.dateStyle;
      this.timeStyle = var1.timeStyle;
      var5.addAll(var1.builderFactories);
      var3.addAll(var1.builderHierarchyFactories);
      this.useJdkUnsafe = var1.useJdkUnsafe;
      this.objectToNumberStrategy = var1.objectToNumberStrategy;
      this.numberToNumberStrategy = var1.numberToNumberStrategy;
      var4.addAll(var1.reflectionFilters);
   }

   private void addTypeAdaptersForDate(String var1, int var2, int var3, List<TypeAdapterFactory> var4) {
      boolean var5;
      TypeAdapterFactory var6;
      TypeAdapterFactory var11;
      label35: {
         var5 = SqlTypesSupport.SUPPORTS_SQL_TYPES;
         var11 = null;
         if (var1 != null && !var1.trim().isEmpty()) {
            TypeAdapterFactory var8 = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(var1);
            var6 = var8;
            if (var5) {
               var6 = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(var1);
               var11 = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(var1);
               var9 = var6;
               var6 = var8;
               break label35;
            }
         } else {
            if (var2 == 2 || var3 == 2) {
               return;
            }

            var6 = DefaultDateTypeAdapter.DateType.DATE.createAdapterFactory(var2, var3);
            if (var5) {
               var9 = SqlTypesSupport.TIMESTAMP_DATE_TYPE.createAdapterFactory(var2, var3);
               var11 = SqlTypesSupport.DATE_DATE_TYPE.createAdapterFactory(var2, var3);
               break label35;
            }
         }

         Object var12 = null;
         var9 = var11;
         var11 = (TypeAdapterFactory)var12;
      }

      var4.add(var6);
      if (var5) {
         var4.add(var9);
         var4.add(var11);
      }
   }

   public GsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy var1) {
      Objects.requireNonNull(var1);
      this.excluder = this.excluder.withExclusionStrategy(var1, false, true);
      return this;
   }

   public GsonBuilder addReflectionAccessFilter(ReflectionAccessFilter var1) {
      Objects.requireNonNull(var1);
      this.reflectionFilters.addFirst(var1);
      return this;
   }

   public GsonBuilder addSerializationExclusionStrategy(ExclusionStrategy var1) {
      Objects.requireNonNull(var1);
      this.excluder = this.excluder.withExclusionStrategy(var1, true, false);
      return this;
   }

   public Gson create() {
      int var1 = this.factories.size();
      ArrayList var3 = new ArrayList(this.hierarchyFactories.size() + var1 + 3);
      var3.addAll(this.factories);
      Collections.reverse(var3);
      ArrayList var2 = new ArrayList<>(this.hierarchyFactories);
      Collections.reverse(var2);
      var3.addAll(var2);
      this.addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, var3);
      return new Gson(
         this.excluder,
         this.fieldNamingPolicy,
         new HashMap<>(this.instanceCreators),
         this.serializeNulls,
         this.complexMapKeySerialization,
         this.generateNonExecutableJson,
         this.escapeHtmlChars,
         this.prettyPrinting,
         this.lenient,
         this.serializeSpecialFloatingPointValues,
         this.useJdkUnsafe,
         this.longSerializationPolicy,
         this.datePattern,
         this.dateStyle,
         this.timeStyle,
         new ArrayList<>(this.factories),
         new ArrayList<>(this.hierarchyFactories),
         var3,
         this.objectToNumberStrategy,
         this.numberToNumberStrategy,
         new ArrayList<>(this.reflectionFilters)
      );
   }

   public GsonBuilder disableHtmlEscaping() {
      this.escapeHtmlChars = false;
      return this;
   }

   public GsonBuilder disableInnerClassSerialization() {
      this.excluder = this.excluder.disableInnerClassSerialization();
      return this;
   }

   public GsonBuilder disableJdkUnsafe() {
      this.useJdkUnsafe = false;
      return this;
   }

   public GsonBuilder enableComplexMapKeySerialization() {
      this.complexMapKeySerialization = true;
      return this;
   }

   public GsonBuilder excludeFieldsWithModifiers(int... var1) {
      Objects.requireNonNull(var1);
      this.excluder = this.excluder.withModifiers(var1);
      return this;
   }

   public GsonBuilder excludeFieldsWithoutExposeAnnotation() {
      this.excluder = this.excluder.excludeFieldsWithoutExposeAnnotation();
      return this;
   }

   public GsonBuilder generateNonExecutableJson() {
      this.generateNonExecutableJson = true;
      return this;
   }

   public GsonBuilder registerTypeAdapter(Type var1, Object var2) {
      Objects.requireNonNull(var1);
      boolean var4 = var2 instanceof JsonSerializer;
      boolean var3;
      if (!var4 && !(var2 instanceof JsonDeserializer) && !(var2 instanceof InstanceCreator) && !(var2 instanceof TypeAdapter)) {
         var3 = false;
      } else {
         var3 = true;
      }

      $Gson$Preconditions.checkArgument(var3);
      if (var2 instanceof InstanceCreator) {
         this.instanceCreators.put(var1, (InstanceCreator<?>)var2);
      }

      if (var4 || var2 instanceof JsonDeserializer) {
         TypeToken var5 = TypeToken.get(var1);
         this.factories.add(TreeTypeAdapter.newFactoryWithMatchRawType(var5, var2));
      }

      if (var2 instanceof TypeAdapter) {
         TypeAdapterFactory var6 = TypeAdapters.newFactory(TypeToken.get(var1), (TypeAdapter<?>)var2);
         this.factories.add(var6);
      }

      return this;
   }

   public GsonBuilder registerTypeAdapterFactory(TypeAdapterFactory var1) {
      Objects.requireNonNull(var1);
      this.factories.add(var1);
      return this;
   }

   public GsonBuilder registerTypeHierarchyAdapter(Class<?> var1, Object var2) {
      Objects.requireNonNull(var1);
      boolean var4 = var2 instanceof JsonSerializer;
      boolean var3;
      if (!var4 && !(var2 instanceof JsonDeserializer) && !(var2 instanceof TypeAdapter)) {
         var3 = false;
      } else {
         var3 = true;
      }

      $Gson$Preconditions.checkArgument(var3);
      if (var2 instanceof JsonDeserializer || var4) {
         this.hierarchyFactories.add(TreeTypeAdapter.newTypeHierarchyFactory(var1, var2));
      }

      if (var2 instanceof TypeAdapter) {
         TypeAdapterFactory var5 = TypeAdapters.newTypeHierarchyFactory(var1, (TypeAdapter)var2);
         this.factories.add(var5);
      }

      return this;
   }

   public GsonBuilder serializeNulls() {
      this.serializeNulls = true;
      return this;
   }

   public GsonBuilder serializeSpecialFloatingPointValues() {
      this.serializeSpecialFloatingPointValues = true;
      return this;
   }

   public GsonBuilder setDateFormat(int var1) {
      this.dateStyle = var1;
      this.datePattern = null;
      return this;
   }

   public GsonBuilder setDateFormat(int var1, int var2) {
      this.dateStyle = var1;
      this.timeStyle = var2;
      this.datePattern = null;
      return this;
   }

   public GsonBuilder setDateFormat(String var1) {
      this.datePattern = var1;
      return this;
   }

   public GsonBuilder setExclusionStrategies(ExclusionStrategy... var1) {
      Objects.requireNonNull(var1);

      for (ExclusionStrategy var4 : var1) {
         this.excluder = this.excluder.withExclusionStrategy(var4, true, true);
      }

      return this;
   }

   public GsonBuilder setFieldNamingPolicy(FieldNamingPolicy var1) {
      return this.setFieldNamingStrategy(var1);
   }

   public GsonBuilder setFieldNamingStrategy(FieldNamingStrategy var1) {
      Objects.requireNonNull(var1);
      this.fieldNamingPolicy = var1;
      return this;
   }

   public GsonBuilder setLenient() {
      this.lenient = true;
      return this;
   }

   public GsonBuilder setLongSerializationPolicy(LongSerializationPolicy var1) {
      Objects.requireNonNull(var1);
      this.longSerializationPolicy = var1;
      return this;
   }

   public GsonBuilder setNumberToNumberStrategy(ToNumberStrategy var1) {
      Objects.requireNonNull(var1);
      this.numberToNumberStrategy = var1;
      return this;
   }

   public GsonBuilder setObjectToNumberStrategy(ToNumberStrategy var1) {
      Objects.requireNonNull(var1);
      this.objectToNumberStrategy = var1;
      return this;
   }

   public GsonBuilder setPrettyPrinting() {
      this.prettyPrinting = true;
      return this;
   }

   public GsonBuilder setVersion(double var1) {
      if (!Double.isNaN(var1) && !(var1 < 0.0)) {
         this.excluder = this.excluder.withVersion(var1);
         return this;
      } else {
         StringBuilder var3 = new StringBuilder("Invalid version: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      }
   }
}

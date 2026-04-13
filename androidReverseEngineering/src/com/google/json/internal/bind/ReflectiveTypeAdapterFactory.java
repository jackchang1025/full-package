package com.google.json.internal.bind;

import a.a;
import com.google.json.FieldNamingStrategy;
import com.google.json.Gson;
import com.google.json.JsonIOException;
import com.google.json.JsonParseException;
import com.google.json.JsonSyntaxException;
import com.google.json.ReflectionAccessFilter;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.annotations.JsonAdapter;
import com.google.json.annotations.SerializedName;
import com.google.json.internal.$Gson$Types;
import com.google.json.internal.ConstructorConstructor;
import com.google.json.internal.Excluder;
import com.google.json.internal.ObjectConstructor;
import com.google.json.internal.Primitives;
import com.google.json.internal.ReflectionAccessFilterHelper;
import com.google.json.internal.reflect.ReflectionHelper;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonToken;
import com.google.json.stream.JsonWriter;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
   private final ConstructorConstructor constructorConstructor;
   private final Excluder excluder;
   private final FieldNamingStrategy fieldNamingPolicy;
   private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
   private final List<ReflectionAccessFilter> reflectionFilters;

   public ReflectiveTypeAdapterFactory(
      ConstructorConstructor var1, FieldNamingStrategy var2, Excluder var3, JsonAdapterAnnotationTypeAdapterFactory var4, List<ReflectionAccessFilter> var5
   ) {
      this.constructorConstructor = var1;
      this.fieldNamingPolicy = var2;
      this.excluder = var3;
      this.jsonAdapterFactory = var4;
      this.reflectionFilters = var5;
   }

   private static <M extends AccessibleObject & Member> void checkAccessible(Object var0, M var1) {
      if (Modifier.isStatic(((Member)var1).getModifiers())) {
         var0 = null;
      }

      if (!ReflectionAccessFilterHelper.canAccess(var1, var0)) {
         throw new JsonIOException(
            a.z(
               ReflectionHelper.getAccessibleObjectDescription(var1, true),
               " is not accessible and ReflectionAccessFilter does not permit making it accessible. Register a TypeAdapter for the declaring type, adjust the access filter or increase the visibility of the element and its declaring type."
            )
         );
      }
   }

   private ReflectiveTypeAdapterFactory.BoundField createBoundField(
      Gson var1, Field var2, Method var3, String var4, TypeToken<?> var5, boolean var6, boolean var7, boolean var8
   ) {
      boolean var12 = Primitives.isPrimitive(var5.getRawType());
      int var9 = var2.getModifiers();
      boolean var10;
      if (Modifier.isStatic(var9) && Modifier.isFinal(var9)) {
         var10 = true;
      } else {
         var10 = false;
      }

      JsonAdapter var13 = var2.getAnnotation(JsonAdapter.class);
      TypeAdapter var15;
      if (var13 != null) {
         var15 = this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, var1, var5, var13);
      } else {
         var15 = null;
      }

      boolean var11;
      if (var15 != null) {
         var11 = true;
      } else {
         var11 = false;
      }

      TypeAdapter var14 = var15;
      if (var15 == null) {
         var14 = var1.getAdapter(var5);
      }

      return new ReflectiveTypeAdapterFactory.BoundField(this, var4, var2, var6, var7, var8, var3, var11, var14, var1, var5, var12, var10) {
         final ReflectiveTypeAdapterFactory this$0;
         final Method val$accessor;
         final boolean val$blockInaccessible;
         final Gson val$context;
         final TypeToken val$fieldType;
         final boolean val$isPrimitive;
         final boolean val$isStaticFinalField;
         final boolean val$jsonAdapterPresent;
         final TypeAdapter val$typeAdapter;

         {
            this.this$0 = var1;
            this.val$blockInaccessible = var6;
            this.val$accessor = var7;
            this.val$jsonAdapterPresent = var8;
            this.val$typeAdapter = var9;
            this.val$context = var10;
            this.val$fieldType = var11;
            this.val$isPrimitive = var12;
            this.val$isStaticFinalField = var13;
         }

         @Override
         public void readIntoArray(JsonReader var1, int var2, Object[] var3) {
            Object var4 = this.val$typeAdapter.read(var1);
            if (var4 == null && this.val$isPrimitive) {
               StringBuilder var5 = new StringBuilder("null is not allowed as value for record component '");
               var5.append(super.fieldName);
               var5.append("' of primitive type; at path ");
               var5.append(var1.getPath());
               throw new JsonParseException(var5.toString());
            } else {
               var3[var2] = var4;
            }
         }

         @Override
         public void readIntoField(JsonReader var1, Object var2) {
            Object var3 = this.val$typeAdapter.read(var1);
            if (var3 != null || !this.val$isPrimitive) {
               if (this.val$blockInaccessible) {
                  ReflectiveTypeAdapterFactory.checkAccessible(var2, super.field);
               } else if (this.val$isStaticFinalField) {
                  throw new JsonIOException(a.k("Cannot set value of 'static final' ", ReflectionHelper.getAccessibleObjectDescription(super.field, false)));
               }

               super.field.set(var2, var3);
            }
         }

         @Override
         public void write(JsonWriter var1, Object var2) {
            if (super.serialized) {
               if (this.val$blockInaccessible) {
                  Method var4 = this.val$accessor;
                  Object var3 = var4;
                  if (var4 == null) {
                     var3 = super.field;
                  }

                  ReflectiveTypeAdapterFactory.checkAccessible(var2, (AccessibleObject)var3);
               }

               Method var7 = this.val$accessor;
               Object var8;
               if (var7 != null) {
                  try {
                     var8 = var7.invoke(var2);
                  } catch (InvocationTargetException var5) {
                     throw new JsonIOException(
                        a.l("Accessor ", ReflectionHelper.getAccessibleObjectDescription(this.val$accessor, false), " threw exception"), var5.getCause()
                     );
                  }
               } else {
                  var8 = super.field.get(var2);
               }

               if (var8 != var2) {
                  var1.name(super.name);
                  if (this.val$jsonAdapterPresent) {
                     var2 = this.val$typeAdapter;
                  } else {
                     var2 = new TypeAdapterRuntimeTypeWrapper(this.val$context, this.val$typeAdapter, this.val$fieldType.getType());
                  }

                  ((TypeAdapter<Object>)var2).write(var1, var8);
               }
            }
         }
      };
   }

   private Map<String, ReflectiveTypeAdapterFactory.BoundField> getBoundFields(Gson var1, TypeToken<?> var2, Class<?> var3, boolean var4, boolean var5) {
      LinkedHashMap var18 = new LinkedHashMap();
      if (var3.isInterface()) {
         return var18;
      } else {
         Class var13 = var3;

         for (TypeToken var14 = var2; var13 != Object.class; var13 = var14.getRawType()) {
            Field[] var19 = var13.getDeclaredFields();
            if (var13 != var3 && var19.length > 0) {
               ReflectionAccessFilter.FilterResult var23 = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, var13);
               if (var23 == ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
                  StringBuilder var22 = new StringBuilder("ReflectionAccessFilter does not permit using reflection for ");
                  var22.append(var13);
                  var22.append(" (supertype of ");
                  var22.append(var3);
                  var22.append("). Register a TypeAdapter for this type or adjust the access filter.");
                  throw new JsonIOException(var22.toString());
               }

               if (var23 == ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE) {
                  var4 = true;
               } else {
                  var4 = false;
               }
            }

            int var7 = var19.length;
            int var6 = 0;

            while (var6 < var7) {
               Field var15 = var19[var6];
               boolean var11 = this.includeField(var15, true);
               boolean var10 = this.includeField(var15, false);
               int var26;
               if (!var11 && !var10) {
                  var26 = var6;
                  var6 = var7;
               } else {
                  Method var24;
                  if (var5) {
                     if (Modifier.isStatic(var15.getModifiers())) {
                        var24 = null;
                        var10 = false;
                     } else {
                        var24 = ReflectionHelper.getAccessor(var13, var15);
                        if (!var4) {
                           ReflectionHelper.makeAccessible(var24);
                        }

                        if (var24.getAnnotation(SerializedName.class) != null && var15.getAnnotation(SerializedName.class) == null) {
                           throw new JsonIOException(
                              a.l("@SerializedName on ", ReflectionHelper.getAccessibleObjectDescription(var24, false), " is not supported")
                           );
                        }
                     }
                  } else {
                     var24 = null;
                  }

                  if (!var4 && var24 == null) {
                     ReflectionHelper.makeAccessible(var15);
                  }

                  Type var20 = $Gson$Types.resolve(var14.getType(), var13, var15.getGenericType());
                  List var16 = this.getFieldNames(var15);
                  var26 = var16.size();
                  ReflectiveTypeAdapterFactory.BoundField var12 = null;

                  for (int var9 = 0; var9 < var26; var9++) {
                     String var17 = (String)var16.get(var9);
                     if (var9 != 0) {
                        var11 = false;
                     }

                     ReflectiveTypeAdapterFactory.BoundField var28 = var18.put(
                        var17, this.createBoundField(var1, var15, var24, var17, TypeToken.get(var20), var11, var10, var4)
                     );
                     if (var12 == null) {
                        var12 = var28;
                     }
                  }

                  var26 = var6;
                  var6 = var7;
                  if (var12 != null) {
                     StringBuilder var21 = new StringBuilder("Class ");
                     var21.append(var3.getName());
                     var21.append(" declares multiple JSON fields named '");
                     var21.append(var12.name);
                     var21.append("'; conflict is caused by fields ");
                     var21.append(ReflectionHelper.fieldToString(var12.field));
                     var21.append(" and ");
                     var21.append(ReflectionHelper.fieldToString(var15));
                     throw new IllegalArgumentException(var21.toString());
                  }
               }

               var26++;
               var7 = var6;
               var6 = var26;
            }

            var14 = TypeToken.get($Gson$Types.resolve(var14.getType(), var13, var13.getGenericSuperclass()));
         }

         return var18;
      }
   }

   private List<String> getFieldNames(Field var1) {
      SerializedName var2 = var1.getAnnotation(SerializedName.class);
      if (var2 == null) {
         return Collections.singletonList(this.fieldNamingPolicy.translateName(var1));
      } else {
         String var4 = var2.value();
         String[] var5 = var2.alternate();
         if (var5.length == 0) {
            return Collections.singletonList(var4);
         } else {
            ArrayList var3 = new ArrayList(var5.length + 1);
            var3.add(var4);
            Collections.addAll(var3, var5);
            return var3;
         }
      }
   }

   private boolean includeField(Field var1, boolean var2) {
      if (!this.excluder.excludeClass(var1.getType(), var2) && !this.excluder.excludeField(var1, var2)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
      Class var4 = var2.getRawType();
      if (!Object.class.isAssignableFrom(var4)) {
         return null;
      } else {
         ReflectionAccessFilter.FilterResult var5 = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, var4);
         if (var5 != ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
            boolean var3;
            if (var5 == ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE) {
               var3 = true;
            } else {
               var3 = false;
            }

            return (TypeAdapter<T>)(ReflectionHelper.isRecord(var4)
               ? new ReflectiveTypeAdapterFactory.RecordAdapter<>(var4, this.getBoundFields(var1, var2, var4, var3, true), var3)
               : new ReflectiveTypeAdapterFactory.FieldReflectionAdapter<>(
                  this.constructorConstructor.get(var2), this.getBoundFields(var1, var2, var4, var3, false)
               ));
         } else {
            StringBuilder var6 = new StringBuilder("ReflectionAccessFilter does not permit using reflection for ");
            var6.append(var4);
            var6.append(". Register a TypeAdapter for this type or adjust the access filter.");
            throw new JsonIOException(var6.toString());
         }
      }
   }

   public abstract static class Adapter<T, A> extends TypeAdapter<T> {
      final Map<String, ReflectiveTypeAdapterFactory.BoundField> boundFields;

      public Adapter(Map<String, ReflectiveTypeAdapterFactory.BoundField> var1) {
         this.boundFields = var1;
      }

      public abstract A createAccumulator();

      public abstract T finalize(A var1);

      // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
      @Override
      public T read(JsonReader var1) {
         if (var1.peek() == JsonToken.NULL) {
            var1.nextNull();
            return null;
         } else {
            Object var2 = this.createAccumulator();

            label65: {
               IllegalStateException var17;
               label64: {
                  label63: {
                     try {
                        var1.beginObject();
                     } catch (IllegalStateException var12) {
                        var17 = var12;
                        boolean var18 = false;
                        break label64;
                     } catch (IllegalAccessException var13) {
                        var10000 = var13;
                        boolean var10001 = false;
                        break label63;
                     }

                     while (true) {
                        ReflectiveTypeAdapterFactory.BoundField var16;
                        try {
                           if (!var1.hasNext()) {
                              break label65;
                           }

                           String var3 = var1.nextName();
                           var16 = this.boundFields.get(var3);
                        } catch (IllegalStateException var8) {
                           var17 = var8;
                           boolean var20 = false;
                           break label64;
                        } catch (IllegalAccessException var9) {
                           var10000 = var9;
                           boolean var19 = false;
                           break;
                        }

                        label72: {
                           if (var16 != null) {
                              try {
                                 if (var16.deserialized) {
                                    break label72;
                                 }
                              } catch (IllegalStateException var10) {
                                 var17 = var10;
                                 boolean var22 = false;
                                 break label64;
                              } catch (IllegalAccessException var11) {
                                 var10000 = var11;
                                 boolean var21 = false;
                                 break;
                              }
                           }

                           try {
                              var1.skipValue();
                              continue;
                           } catch (IllegalStateException var6) {
                              var17 = var6;
                              boolean var24 = false;
                              break label64;
                           } catch (IllegalAccessException var7) {
                              var10000 = var7;
                              boolean var23 = false;
                              break;
                           }
                        }

                        try {
                           this.readField((A)var2, var1, var16);
                        } catch (IllegalStateException var4) {
                           var17 = var4;
                           boolean var26 = false;
                           break label64;
                        } catch (IllegalAccessException var5) {
                           var10000 = var5;
                           boolean var25 = false;
                           break;
                        }
                     }
                  }

                  IllegalAccessException var14 = var10000;
                  throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(var14);
               }

               IllegalStateException var15 = var17;
               throw new JsonSyntaxException(var15);
            }

            var1.endObject();
            return this.finalize((A)var2);
         }
      }

      public abstract void readField(A var1, JsonReader var2, ReflectiveTypeAdapterFactory.BoundField var3);

      // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
      @Override
      public void write(JsonWriter var1, T var2) {
         if (var2 == null) {
            var1.nullValue();
         } else {
            var1.beginObject();

            label28: {
               IllegalAccessException var10000;
               label27: {
                  Iterator var3;
                  try {
                     var3 = this.boundFields.values().iterator();
                  } catch (IllegalAccessException var5) {
                     var10000 = var5;
                     boolean var10001 = false;
                     break label27;
                  }

                  while (true) {
                     try {
                        if (!var3.hasNext()) {
                           break label28;
                        }

                        ((ReflectiveTypeAdapterFactory.BoundField)var3.next()).write(var1, var2);
                     } catch (IllegalAccessException var4) {
                        var10000 = var4;
                        boolean var7 = false;
                        break;
                     }
                  }
               }

               IllegalAccessException var6 = var10000;
               throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(var6);
            }

            var1.endObject();
         }
      }
   }

   public abstract static class BoundField {
      final boolean deserialized;
      final Field field;
      final String fieldName;
      final String name;
      final boolean serialized;

      public BoundField(String var1, Field var2, boolean var3, boolean var4) {
         this.name = var1;
         this.field = var2;
         this.fieldName = var2.getName();
         this.serialized = var3;
         this.deserialized = var4;
      }

      public abstract void readIntoArray(JsonReader var1, int var2, Object[] var3);

      public abstract void readIntoField(JsonReader var1, Object var2);

      public abstract void write(JsonWriter var1, Object var2);
   }

   public static final class FieldReflectionAdapter<T> extends ReflectiveTypeAdapterFactory.Adapter<T, T> {
      private final ObjectConstructor<T> constructor;

      public FieldReflectionAdapter(ObjectConstructor<T> var1, Map<String, ReflectiveTypeAdapterFactory.BoundField> var2) {
         super(var2);
         this.constructor = var1;
      }

      @Override
      public T createAccumulator() {
         return this.constructor.construct();
      }

      @Override
      public T finalize(T var1) {
         return (T)var1;
      }

      @Override
      public void readField(T var1, JsonReader var2, ReflectiveTypeAdapterFactory.BoundField var3) {
         var3.readIntoField(var2, var1);
      }
   }

   public static final class RecordAdapter<T> extends ReflectiveTypeAdapterFactory.Adapter<T, Object[]> {
      static final Map<Class<?>, Object> PRIMITIVE_DEFAULTS = primitiveDefaults();
      private final Map<String, Integer> componentIndices = new HashMap<>();
      private final Constructor<T> constructor;
      private final Object[] constructorArgsDefaults;

      public RecordAdapter(Class<T> var1, Map<String, ReflectiveTypeAdapterFactory.BoundField> var2, boolean var3) {
         super(var2);
         Constructor var8 = ReflectionHelper.getCanonicalRecordConstructor(var1);
         this.constructor = var8;
         if (var3) {
            ReflectiveTypeAdapterFactory.checkAccessible(null, var8);
         } else {
            ReflectionHelper.makeAccessible(var8);
         }

         String[] var6 = ReflectionHelper.getRecordComponentNames(var1);
         byte var5 = 0;

         for (int var4 = 0; var4 < var6.length; var4++) {
            this.componentIndices.put(var6[var4], var4);
         }

         Class[] var7 = this.constructor.getParameterTypes();
         this.constructorArgsDefaults = new Object[var7.length];

         for (int var9 = var5; var9 < var7.length; var9++) {
            this.constructorArgsDefaults[var9] = PRIMITIVE_DEFAULTS.get(var7[var9]);
         }
      }

      private static Map<Class<?>, Object> primitiveDefaults() {
         HashMap var0 = new HashMap();
         var0.put(byte.class, (byte)0);
         var0.put(short.class, (short)0);
         var0.put(int.class, 0);
         var0.put(long.class, 0L);
         var0.put(float.class, 0.0F);
         var0.put(double.class, 0.0);
         var0.put(char.class, '\u0000');
         var0.put(boolean.class, Boolean.FALSE);
         return var0;
      }

      public Object[] createAccumulator() {
         return (Object[])this.constructorArgsDefaults.clone();
      }

      public T finalize(Object[] var1) {
         Object var2;
         try {
            return this.constructor.newInstance(var1);
         } catch (IllegalAccessException var4) {
            throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(var4);
         } catch (InstantiationException var5) {
            var2 = var5;
         } catch (IllegalArgumentException var6) {
            var2 = var6;
         } catch (InvocationTargetException var7) {
            StringBuilder var3 = new StringBuilder("Failed to invoke constructor '");
            var3.append(ReflectionHelper.constructorToString(this.constructor));
            var3.append("' with args ");
            var3.append(Arrays.toString(var1));
            throw new RuntimeException(var3.toString(), var7.getCause());
         }

         StringBuilder var8 = new StringBuilder("Failed to invoke constructor '");
         var8.append(ReflectionHelper.constructorToString(this.constructor));
         var8.append("' with args ");
         var8.append(Arrays.toString(var1));
         throw new RuntimeException(var8.toString(), (Throwable)var2);
      }

      public void readField(Object[] var1, JsonReader var2, ReflectiveTypeAdapterFactory.BoundField var3) {
         Integer var4 = this.componentIndices.get(var3.fieldName);
         if (var4 != null) {
            var3.readIntoArray(var2, var4, var1);
         } else {
            StringBuilder var5 = new StringBuilder("Could not find the index in the constructor '");
            var5.append(ReflectionHelper.constructorToString(this.constructor));
            var5.append("' for field with name '");
            throw new IllegalStateException(
               a.n(
                  var5,
                  var3.fieldName,
                  "', unable to determine which argument in the constructor the field corresponds to. This is unexpected behavior, as we expect the RecordComponents to have the same names as the fields in the Java class, and that the order of the RecordComponents is the same as the order of the canonical constructor parameters."
               )
            );
         }
      }
   }
}

package com.google.json.internal.bind;

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
import com.google.json.internal.C$Gson$Types;
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
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final FieldNamingStrategy fieldNamingPolicy;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    private final List<ReflectionAccessFilter> reflectionFilters;

    public static abstract class Adapter<T, A> extends TypeAdapter<T> {
        final Map<String, BoundField> boundFields;

        public Adapter(Map<String, BoundField> map) {
            this.boundFields = map;
        }

        public abstract A createAccumulator();

        public abstract T finalize(A a2);

        @Override // com.google.json.TypeAdapter
        public T read(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            A createAccumulator = createAccumulator();
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    BoundField boundField = this.boundFields.get(jsonReader.nextName());
                    if (boundField != null && boundField.deserialized) {
                        readField(createAccumulator, jsonReader, boundField);
                    }
                    jsonReader.skipValue();
                }
                jsonReader.endObject();
                return finalize(createAccumulator);
            } catch (IllegalAccessException e2) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e2);
            } catch (IllegalStateException e3) {
                throw new JsonSyntaxException(e3);
            }
        }

        public abstract void readField(A a2, JsonReader jsonReader, BoundField boundField);

        @Override // com.google.json.TypeAdapter
        public void write(JsonWriter jsonWriter, T t2) {
            if (t2 == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            try {
                Iterator<BoundField> it = this.boundFields.values().iterator();
                while (it.hasNext()) {
                    it.next().write(jsonWriter, t2);
                }
                jsonWriter.endObject();
            } catch (IllegalAccessException e2) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e2);
            }
        }
    }

    public static abstract class BoundField {
        final boolean deserialized;
        final Field field;
        final String fieldName;
        final String name;
        final boolean serialized;

        public BoundField(String str, Field field, boolean z2, boolean z3) {
            this.name = str;
            this.field = field;
            this.fieldName = field.getName();
            this.serialized = z2;
            this.deserialized = z3;
        }

        public abstract void readIntoArray(JsonReader jsonReader, int i2, Object[] objArr);

        public abstract void readIntoField(JsonReader jsonReader, Object obj);

        public abstract void write(JsonWriter jsonWriter, Object obj);
    }

    public static final class FieldReflectionAdapter<T> extends Adapter<T, T> {
        private final ObjectConstructor<T> constructor;

        public FieldReflectionAdapter(ObjectConstructor<T> objectConstructor, Map<String, BoundField> map) {
            super(map);
            this.constructor = objectConstructor;
        }

        @Override // com.google.json.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public T createAccumulator() {
            return this.constructor.construct();
        }

        @Override // com.google.json.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public T finalize(T t2) {
            return t2;
        }

        @Override // com.google.json.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public void readField(T t2, JsonReader jsonReader, BoundField boundField) {
            boundField.readIntoField(jsonReader, t2);
        }
    }

    public static final class RecordAdapter<T> extends Adapter<T, Object[]> {
        static final Map<Class<?>, Object> PRIMITIVE_DEFAULTS = primitiveDefaults();
        private final Map<String, Integer> componentIndices;
        private final Constructor<T> constructor;
        private final Object[] constructorArgsDefaults;

        public RecordAdapter(Class<T> cls, Map<String, BoundField> map, boolean z2) {
            super(map);
            this.componentIndices = new HashMap();
            Constructor<T> canonicalRecordConstructor = ReflectionHelper.getCanonicalRecordConstructor(cls);
            this.constructor = canonicalRecordConstructor;
            if (z2) {
                ReflectiveTypeAdapterFactory.checkAccessible(null, canonicalRecordConstructor);
            } else {
                ReflectionHelper.makeAccessible(canonicalRecordConstructor);
            }
            String[] recordComponentNames = ReflectionHelper.getRecordComponentNames(cls);
            for (int i2 = 0; i2 < recordComponentNames.length; i2++) {
                this.componentIndices.put(recordComponentNames[i2], Integer.valueOf(i2));
            }
            Class<?>[] parameterTypes = this.constructor.getParameterTypes();
            this.constructorArgsDefaults = new Object[parameterTypes.length];
            for (int i3 = 0; i3 < parameterTypes.length; i3++) {
                this.constructorArgsDefaults[i3] = PRIMITIVE_DEFAULTS.get(parameterTypes[i3]);
            }
        }

        private static Map<Class<?>, Object> primitiveDefaults() {
            HashMap hashMap = new HashMap();
            hashMap.put(Byte.TYPE, (byte) 0);
            hashMap.put(Short.TYPE, (short) 0);
            hashMap.put(Integer.TYPE, 0);
            hashMap.put(Long.TYPE, 0L);
            hashMap.put(Float.TYPE, Float.valueOf(0.0f));
            hashMap.put(Double.TYPE, Double.valueOf(0.0d));
            hashMap.put(Character.TYPE, (char) 0);
            hashMap.put(Boolean.TYPE, Boolean.FALSE);
            return hashMap;
        }

        @Override // com.google.json.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public Object[] createAccumulator() {
            return (Object[]) this.constructorArgsDefaults.clone();
        }

        @Override // com.google.json.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public T finalize(Object[] objArr) {
            try {
                return this.constructor.newInstance(objArr);
            } catch (IllegalAccessException e2) {
                throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e2);
            } catch (IllegalArgumentException e3) {
                e = e3;
                throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(objArr), e);
            } catch (InstantiationException e4) {
                e = e4;
                throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(objArr), e);
            } catch (InvocationTargetException e5) {
                throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.constructor) + "' with args " + Arrays.toString(objArr), e5.getCause());
            }
        }

        @Override // com.google.json.internal.bind.ReflectiveTypeAdapterFactory.Adapter
        public void readField(Object[] objArr, JsonReader jsonReader, BoundField boundField) {
            Integer num = this.componentIndices.get(boundField.fieldName);
            if (num != null) {
                boundField.readIntoArray(jsonReader, num.intValue(), objArr);
                return;
            }
            StringBuilder sb = new StringBuilder("Could not find the index in the constructor '");
            sb.append(ReflectionHelper.constructorToString(this.constructor));
            sb.append("' for field with name '");
            throw new IllegalStateException(AbstractC0000a.m18n(sb, boundField.fieldName, "', unable to determine which argument in the constructor the field corresponds to. This is unexpected behavior, as we expect the RecordComponents to have the same names as the fields in the Java class, and that the order of the RecordComponents is the same as the order of the canonical constructor parameters."));
        }
    }

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingStrategy, Excluder excluder, JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory, List<ReflectionAccessFilter> list) {
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingStrategy;
        this.excluder = excluder;
        this.jsonAdapterFactory = jsonAdapterAnnotationTypeAdapterFactory;
        this.reflectionFilters = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <M extends AccessibleObject & Member> void checkAccessible(Object obj, M m2) {
        if (Modifier.isStatic(m2.getModifiers())) {
            obj = null;
        }
        if (!ReflectionAccessFilterHelper.canAccess(m2, obj)) {
            throw new JsonIOException(AbstractC0000a.m30z(ReflectionHelper.getAccessibleObjectDescription(m2, true), " is not accessible and ReflectionAccessFilter does not permit making it accessible. Register a TypeAdapter for the declaring type, adjust the access filter or increase the visibility of the element and its declaring type."));
        }
    }

    private BoundField createBoundField(final Gson gson, Field field, final Method method, String str, final TypeToken<?> typeToken, boolean z2, boolean z3, final boolean z4) {
        final boolean isPrimitive = Primitives.isPrimitive(typeToken.getRawType());
        int modifiers = field.getModifiers();
        final boolean z5 = Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
        JsonAdapter jsonAdapter = (JsonAdapter) field.getAnnotation(JsonAdapter.class);
        TypeAdapter<?> typeAdapter = jsonAdapter != null ? this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, gson, typeToken, jsonAdapter) : null;
        final boolean z6 = typeAdapter != null;
        if (typeAdapter == null) {
            typeAdapter = gson.getAdapter(typeToken);
        }
        final TypeAdapter<?> typeAdapter2 = typeAdapter;
        return new BoundField(str, field, z2, z3) { // from class: com.google.json.internal.bind.ReflectiveTypeAdapterFactory.1
            @Override // com.google.json.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            public void readIntoArray(JsonReader jsonReader, int i2, Object[] objArr) {
                Object read = typeAdapter2.read(jsonReader);
                if (read != null || !isPrimitive) {
                    objArr[i2] = read;
                    return;
                }
                throw new JsonParseException("null is not allowed as value for record component '" + this.fieldName + "' of primitive type; at path " + jsonReader.getPath());
            }

            @Override // com.google.json.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            public void readIntoField(JsonReader jsonReader, Object obj) {
                Object read = typeAdapter2.read(jsonReader);
                if (read == null && isPrimitive) {
                    return;
                }
                if (z4) {
                    ReflectiveTypeAdapterFactory.checkAccessible(obj, this.field);
                } else if (z5) {
                    throw new JsonIOException(AbstractC0000a.m15k("Cannot set value of 'static final' ", ReflectionHelper.getAccessibleObjectDescription(this.field, false)));
                }
                this.field.set(obj, read);
            }

            @Override // com.google.json.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            public void write(JsonWriter jsonWriter, Object obj) {
                Object obj2;
                if (this.serialized) {
                    if (z4) {
                        AccessibleObject accessibleObject = method;
                        if (accessibleObject == null) {
                            accessibleObject = this.field;
                        }
                        ReflectiveTypeAdapterFactory.checkAccessible(obj, accessibleObject);
                    }
                    Method method2 = method;
                    if (method2 != null) {
                        try {
                            obj2 = method2.invoke(obj, new Object[0]);
                        } catch (InvocationTargetException e2) {
                            throw new JsonIOException(AbstractC0000a.m16l("Accessor ", ReflectionHelper.getAccessibleObjectDescription(method, false), " threw exception"), e2.getCause());
                        }
                    } else {
                        obj2 = this.field.get(obj);
                    }
                    if (obj2 == obj) {
                        return;
                    }
                    jsonWriter.name(this.name);
                    (z6 ? typeAdapter2 : new TypeAdapterRuntimeTypeWrapper(gson, typeAdapter2, typeToken.getType())).write(jsonWriter, obj2);
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [int] */
    /* JADX WARN: Type inference failed for: r2v21 */
    private Map<String, BoundField> getBoundFields(Gson gson, TypeToken<?> typeToken, Class<?> cls, boolean z2, boolean z3) {
        boolean z4;
        boolean z5;
        Method method;
        int i2;
        int i3;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (cls.isInterface()) {
            return linkedHashMap;
        }
        TypeToken<?> typeToken2 = typeToken;
        boolean z6 = z2;
        Class<?> cls2 = cls;
        while (cls2 != Object.class) {
            Field[] declaredFields = cls2.getDeclaredFields();
            boolean z7 = true;
            boolean z8 = false;
            if (cls2 == cls || declaredFields.length <= 0) {
                z4 = z6;
            } else {
                ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, cls2);
                if (filterResult == ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
                    throw new JsonIOException("ReflectionAccessFilter does not permit using reflection for " + cls2 + " (supertype of " + cls + "). Register a TypeAdapter for this type or adjust the access filter.");
                }
                z4 = filterResult == ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE;
            }
            int length = declaredFields.length;
            int i4 = 0;
            while (i4 < length) {
                Field field = declaredFields[i4];
                boolean includeField = includeField(field, z7);
                boolean includeField2 = includeField(field, z8);
                if (includeField || includeField2) {
                    if (!z3) {
                        z5 = includeField2;
                        method = null;
                    } else if (Modifier.isStatic(field.getModifiers())) {
                        method = null;
                        z5 = z8;
                    } else {
                        Method accessor = ReflectionHelper.getAccessor(cls2, field);
                        if (!z4) {
                            ReflectionHelper.makeAccessible(accessor);
                        }
                        if (accessor.getAnnotation(SerializedName.class) != null && field.getAnnotation(SerializedName.class) == null) {
                            throw new JsonIOException(AbstractC0000a.m16l("@SerializedName on ", ReflectionHelper.getAccessibleObjectDescription(accessor, z8), " is not supported"));
                        }
                        z5 = includeField2;
                        method = accessor;
                    }
                    if (!z4 && method == null) {
                        ReflectionHelper.makeAccessible(field);
                    }
                    Type resolve = C$Gson$Types.resolve(typeToken2.getType(), cls2, field.getGenericType());
                    List<String> fieldNames = getFieldNames(field);
                    int size = fieldNames.size();
                    BoundField boundField = null;
                    Object r2 = z8;
                    while (r2 < size) {
                        String str = fieldNames.get(r2);
                        boolean z9 = r2 != 0 ? z8 : includeField;
                        int i5 = r2;
                        BoundField boundField2 = boundField;
                        int i6 = size;
                        List<String> list = fieldNames;
                        Field field2 = field;
                        int i7 = i4;
                        int i8 = length;
                        boundField = boundField2 == null ? (BoundField) linkedHashMap.put(str, createBoundField(gson, field, method, str, TypeToken.get(resolve), z9, z5, z4)) : boundField2;
                        includeField = z9;
                        field = field2;
                        size = i6;
                        fieldNames = list;
                        i4 = i7;
                        length = i8;
                        z8 = false;
                        r2 = i5 + 1;
                    }
                    BoundField boundField3 = boundField;
                    Field field3 = field;
                    i2 = i4;
                    i3 = length;
                    if (boundField3 != null) {
                        throw new IllegalArgumentException("Class " + cls.getName() + " declares multiple JSON fields named '" + boundField3.name + "'; conflict is caused by fields " + ReflectionHelper.fieldToString(boundField3.field) + " and " + ReflectionHelper.fieldToString(field3));
                    }
                } else {
                    i2 = i4;
                    i3 = length;
                }
                i4 = i2 + 1;
                z7 = true;
                length = i3;
                z8 = false;
            }
            typeToken2 = TypeToken.get(C$Gson$Types.resolve(typeToken2.getType(), cls2, cls2.getGenericSuperclass()));
            cls2 = typeToken2.getRawType();
            z6 = z4;
        }
        return linkedHashMap;
    }

    private List<String> getFieldNames(Field field) {
        SerializedName serializedName = (SerializedName) field.getAnnotation(SerializedName.class);
        if (serializedName == null) {
            return Collections.singletonList(this.fieldNamingPolicy.translateName(field));
        }
        String value = serializedName.value();
        String[] alternate = serializedName.alternate();
        if (alternate.length == 0) {
            return Collections.singletonList(value);
        }
        ArrayList arrayList = new ArrayList(alternate.length + 1);
        arrayList.add(value);
        Collections.addAll(arrayList, alternate);
        return arrayList;
    }

    private boolean includeField(Field field, boolean z2) {
        return (this.excluder.excludeClass(field.getType(), z2) || this.excluder.excludeField(field, z2)) ? false : true;
    }

    @Override // com.google.json.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
        if (!Object.class.isAssignableFrom(rawType)) {
            return null;
        }
        ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, rawType);
        if (filterResult != ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
            boolean z2 = filterResult == ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE;
            return ReflectionHelper.isRecord(rawType) ? new RecordAdapter(rawType, getBoundFields(gson, typeToken, rawType, z2, true), z2) : new FieldReflectionAdapter(this.constructorConstructor.get(typeToken), getBoundFields(gson, typeToken, rawType, z2, false));
        }
        throw new JsonIOException("ReflectionAccessFilter does not permit using reflection for " + rawType + ". Register a TypeAdapter for this type or adjust the access filter.");
    }
}

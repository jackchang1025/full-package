package com.google.json.internal;

import com.google.json.ExclusionStrategy;
import com.google.json.FieldAttributes;
import com.google.json.Gson;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.annotations.Expose;
import com.google.json.annotations.Since;
import com.google.json.annotations.Until;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.tls.CipherSuite;

/* loaded from: classes.dex */
public final class Excluder implements TypeAdapterFactory, Cloneable {
    public static final Excluder DEFAULT = new Excluder();
    private static final double IGNORE_VERSIONS = -1.0d;
    private boolean requireExpose;
    private double version = IGNORE_VERSIONS;
    private int modifiers = CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA;
    private boolean serializeInnerClasses = true;
    private List<ExclusionStrategy> serializationStrategies = Collections.emptyList();
    private List<ExclusionStrategy> deserializationStrategies = Collections.emptyList();

    private boolean excludeClassChecks(Class<?> cls) {
        if (this.version != IGNORE_VERSIONS && !isValidVersion((Since) cls.getAnnotation(Since.class), (Until) cls.getAnnotation(Until.class))) {
            return true;
        }
        if (this.serializeInnerClasses || !isInnerClass(cls)) {
            return isAnonymousOrNonStaticLocal(cls);
        }
        return true;
    }

    private boolean excludeClassInStrategy(Class<?> cls, boolean z2) {
        Iterator<ExclusionStrategy> it = (z2 ? this.serializationStrategies : this.deserializationStrategies).iterator();
        while (it.hasNext()) {
            if (it.next().shouldSkipClass(cls)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAnonymousOrNonStaticLocal(Class<?> cls) {
        return (Enum.class.isAssignableFrom(cls) || isStatic(cls) || (!cls.isAnonymousClass() && !cls.isLocalClass())) ? false : true;
    }

    private boolean isInnerClass(Class<?> cls) {
        return cls.isMemberClass() && !isStatic(cls);
    }

    private boolean isStatic(Class<?> cls) {
        return (cls.getModifiers() & 8) != 0;
    }

    private boolean isValidSince(Since since) {
        if (since != null) {
            return this.version >= since.value();
        }
        return true;
    }

    private boolean isValidUntil(Until until) {
        if (until != null) {
            return this.version < until.value();
        }
        return true;
    }

    private boolean isValidVersion(Since since, Until until) {
        return isValidSince(since) && isValidUntil(until);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Excluder m1482clone() {
        try {
            return (Excluder) super.clone();
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    @Override // com.google.json.TypeAdapterFactory
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
        boolean excludeClassChecks = excludeClassChecks(rawType);
        final boolean z2 = excludeClassChecks || excludeClassInStrategy(rawType, true);
        final boolean z3 = excludeClassChecks || excludeClassInStrategy(rawType, false);
        if (z2 || z3) {
            return new TypeAdapter<T>() { // from class: com.google.json.internal.Excluder.1
                private TypeAdapter<T> delegate;

                private TypeAdapter<T> delegate() {
                    TypeAdapter<T> typeAdapter = this.delegate;
                    if (typeAdapter != null) {
                        return typeAdapter;
                    }
                    TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(Excluder.this, typeToken);
                    this.delegate = delegateAdapter;
                    return delegateAdapter;
                }

                @Override // com.google.json.TypeAdapter
                public T read(JsonReader jsonReader) {
                    if (!z3) {
                        return delegate().read(jsonReader);
                    }
                    jsonReader.skipValue();
                    return null;
                }

                @Override // com.google.json.TypeAdapter
                public void write(JsonWriter jsonWriter, T t2) {
                    if (z2) {
                        jsonWriter.nullValue();
                    } else {
                        delegate().write(jsonWriter, t2);
                    }
                }
            };
        }
        return null;
    }

    public Excluder disableInnerClassSerialization() {
        Excluder m1482clone = m1482clone();
        m1482clone.serializeInnerClasses = false;
        return m1482clone;
    }

    public boolean excludeClass(Class<?> cls, boolean z2) {
        return excludeClassChecks(cls) || excludeClassInStrategy(cls, z2);
    }

    public boolean excludeField(Field field, boolean z2) {
        Expose expose;
        if ((this.modifiers & field.getModifiers()) != 0) {
            return true;
        }
        if ((this.version != IGNORE_VERSIONS && !isValidVersion((Since) field.getAnnotation(Since.class), (Until) field.getAnnotation(Until.class))) || field.isSynthetic()) {
            return true;
        }
        if (this.requireExpose && ((expose = (Expose) field.getAnnotation(Expose.class)) == null || (!z2 ? expose.deserialize() : expose.serialize()))) {
            return true;
        }
        if ((!this.serializeInnerClasses && isInnerClass(field.getType())) || isAnonymousOrNonStaticLocal(field.getType())) {
            return true;
        }
        List<ExclusionStrategy> list = z2 ? this.serializationStrategies : this.deserializationStrategies;
        if (list.isEmpty()) {
            return false;
        }
        FieldAttributes fieldAttributes = new FieldAttributes(field);
        Iterator<ExclusionStrategy> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().shouldSkipField(fieldAttributes)) {
                return true;
            }
        }
        return false;
    }

    public Excluder excludeFieldsWithoutExposeAnnotation() {
        Excluder m1482clone = m1482clone();
        m1482clone.requireExpose = true;
        return m1482clone;
    }

    public Excluder withExclusionStrategy(ExclusionStrategy exclusionStrategy, boolean z2, boolean z3) {
        Excluder m1482clone = m1482clone();
        if (z2) {
            ArrayList arrayList = new ArrayList(this.serializationStrategies);
            m1482clone.serializationStrategies = arrayList;
            arrayList.add(exclusionStrategy);
        }
        if (z3) {
            ArrayList arrayList2 = new ArrayList(this.deserializationStrategies);
            m1482clone.deserializationStrategies = arrayList2;
            arrayList2.add(exclusionStrategy);
        }
        return m1482clone;
    }

    public Excluder withModifiers(int... iArr) {
        Excluder m1482clone = m1482clone();
        m1482clone.modifiers = 0;
        for (int i2 : iArr) {
            m1482clone.modifiers = i2 | m1482clone.modifiers;
        }
        return m1482clone;
    }

    public Excluder withVersion(double d2) {
        Excluder m1482clone = m1482clone();
        m1482clone.version = d2;
        return m1482clone;
    }
}

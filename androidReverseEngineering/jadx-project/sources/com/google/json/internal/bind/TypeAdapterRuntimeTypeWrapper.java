package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.TypeAdapter;
import com.google.json.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.json.reflect.TypeToken;
import com.google.json.stream.JsonReader;
import com.google.json.stream.JsonWriter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* loaded from: classes.dex */
final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {
    private final Gson context;
    private final TypeAdapter<T> delegate;
    private final Type type;

    public TypeAdapterRuntimeTypeWrapper(Gson gson, TypeAdapter<T> typeAdapter, Type type) {
        this.context = gson;
        this.delegate = typeAdapter;
        this.type = type;
    }

    private static Type getRuntimeTypeIfMoreSpecific(Type type, Object obj) {
        return obj != null ? ((type instanceof Class) || (type instanceof TypeVariable)) ? obj.getClass() : type : type;
    }

    private static boolean isReflective(TypeAdapter<?> typeAdapter) {
        TypeAdapter<?> serializationDelegate;
        while ((typeAdapter instanceof SerializationDelegatingTypeAdapter) && (serializationDelegate = ((SerializationDelegatingTypeAdapter) typeAdapter).getSerializationDelegate()) != typeAdapter) {
            typeAdapter = serializationDelegate;
        }
        return typeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter;
    }

    @Override // com.google.json.TypeAdapter
    public T read(JsonReader jsonReader) {
        return this.delegate.read(jsonReader);
    }

    @Override // com.google.json.TypeAdapter
    public void write(JsonWriter jsonWriter, T t2) {
        TypeAdapter<T> typeAdapter = this.delegate;
        Type runtimeTypeIfMoreSpecific = getRuntimeTypeIfMoreSpecific(this.type, t2);
        if (runtimeTypeIfMoreSpecific != this.type) {
            typeAdapter = this.context.getAdapter(TypeToken.get(runtimeTypeIfMoreSpecific));
            if ((typeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter) && !isReflective(this.delegate)) {
                typeAdapter = this.delegate;
            }
        }
        typeAdapter.write(jsonWriter, t2);
    }
}

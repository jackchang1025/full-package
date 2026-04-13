package com.google.json.internal.bind;

import com.google.json.Gson;
import com.google.json.JsonDeserializer;
import com.google.json.JsonSerializer;
import com.google.json.TypeAdapter;
import com.google.json.TypeAdapterFactory;
import com.google.json.annotations.JsonAdapter;
import com.google.json.internal.ConstructorConstructor;
import com.google.json.reflect.TypeToken;

/* loaded from: classes.dex */
public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override // com.google.json.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        JsonAdapter jsonAdapter = (JsonAdapter) typeToken.getRawType().getAnnotation(JsonAdapter.class);
        if (jsonAdapter == null) {
            return null;
        }
        return (TypeAdapter<T>) getTypeAdapter(this.constructorConstructor, gson, typeToken, jsonAdapter);
    }

    public TypeAdapter<?> getTypeAdapter(ConstructorConstructor constructorConstructor, Gson gson, TypeToken<?> typeToken, JsonAdapter jsonAdapter) {
        TypeAdapter<?> treeTypeAdapter;
        Object construct = constructorConstructor.get(TypeToken.get((Class) jsonAdapter.value())).construct();
        boolean nullSafe = jsonAdapter.nullSafe();
        if (construct instanceof TypeAdapter) {
            treeTypeAdapter = (TypeAdapter) construct;
        } else if (construct instanceof TypeAdapterFactory) {
            treeTypeAdapter = ((TypeAdapterFactory) construct).create(gson, typeToken);
        } else {
            boolean z2 = construct instanceof JsonSerializer;
            if (!z2 && !(construct instanceof JsonDeserializer)) {
                throw new IllegalArgumentException("Invalid attempt to bind an instance of " + construct.getClass().getName() + " as a @JsonAdapter for " + typeToken.toString() + ". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer.");
            }
            treeTypeAdapter = new TreeTypeAdapter<>(z2 ? (JsonSerializer) construct : null, construct instanceof JsonDeserializer ? (JsonDeserializer) construct : null, gson, typeToken, null, nullSafe);
            nullSafe = false;
        }
        return (treeTypeAdapter == null || !nullSafe) ? treeTypeAdapter : treeTypeAdapter.nullSafe();
    }
}

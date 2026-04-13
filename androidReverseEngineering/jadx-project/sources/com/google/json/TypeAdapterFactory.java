package com.google.json;

import com.google.json.reflect.TypeToken;

/* loaded from: classes.dex */
public interface TypeAdapterFactory {
    <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken);
}

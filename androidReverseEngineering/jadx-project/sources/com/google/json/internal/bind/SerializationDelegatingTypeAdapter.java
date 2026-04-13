package com.google.json.internal.bind;

import com.google.json.TypeAdapter;

/* loaded from: classes.dex */
public abstract class SerializationDelegatingTypeAdapter<T> extends TypeAdapter<T> {
    public abstract TypeAdapter<T> getSerializationDelegate();
}

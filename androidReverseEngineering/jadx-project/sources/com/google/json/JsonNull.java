package com.google.json;

/* loaded from: classes.dex */
public final class JsonNull extends JsonElement {
    public static final JsonNull INSTANCE = new JsonNull();

    @Deprecated
    public JsonNull() {
    }

    public boolean equals(Object obj) {
        return obj instanceof JsonNull;
    }

    public int hashCode() {
        return JsonNull.class.hashCode();
    }

    @Override // com.google.json.JsonElement
    public JsonNull deepCopy() {
        return INSTANCE;
    }
}

package com.google.json;

/* loaded from: classes.dex */
public enum LongSerializationPolicy {
    DEFAULT { // from class: com.google.json.LongSerializationPolicy.1
        @Override // com.google.json.LongSerializationPolicy
        public JsonElement serialize(Long l2) {
            return l2 == null ? JsonNull.INSTANCE : new JsonPrimitive(l2);
        }
    },
    STRING { // from class: com.google.json.LongSerializationPolicy.2
        @Override // com.google.json.LongSerializationPolicy
        public JsonElement serialize(Long l2) {
            return l2 == null ? JsonNull.INSTANCE : new JsonPrimitive(l2.toString());
        }
    };

    public abstract JsonElement serialize(Long l2);
}

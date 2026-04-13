package com.google.json;

import java.lang.reflect.Type;

public interface JsonDeserializer<T> {
   T deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3);
}

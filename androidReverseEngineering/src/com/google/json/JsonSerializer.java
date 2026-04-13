package com.google.json;

import java.lang.reflect.Type;

public interface JsonSerializer<T> {
   JsonElement serialize(T var1, Type var2, JsonSerializationContext var3);
}

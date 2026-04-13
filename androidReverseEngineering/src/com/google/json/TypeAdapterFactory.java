package com.google.json;

import com.google.json.reflect.TypeToken;

public interface TypeAdapterFactory {
   <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2);
}

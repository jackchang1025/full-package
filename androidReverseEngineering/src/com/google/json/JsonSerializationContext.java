package com.google.json;

import java.lang.reflect.Type;

public interface JsonSerializationContext {
   JsonElement serialize(Object var1);

   JsonElement serialize(Object var1, Type var2);
}

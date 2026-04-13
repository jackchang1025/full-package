package com.google.json;

import java.lang.reflect.Type;

public interface JsonDeserializationContext {
   <T> T deserialize(JsonElement var1, Type var2);
}

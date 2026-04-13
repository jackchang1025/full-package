package com.google.json;

import java.lang.reflect.Type;

public interface InstanceCreator<T> {
   T createInstance(Type var1);
}

package com.google.json;

import com.google.json.internal.ReflectionAccessFilterHelper;

/* loaded from: classes.dex */
public interface ReflectionAccessFilter {
    public static final ReflectionAccessFilter BLOCK_INACCESSIBLE_JAVA = new ReflectionAccessFilter() { // from class: com.google.json.ReflectionAccessFilter.1
        @Override // com.google.json.ReflectionAccessFilter
        public FilterResult check(Class<?> cls) {
            return ReflectionAccessFilterHelper.isJavaType(cls) ? FilterResult.BLOCK_INACCESSIBLE : FilterResult.INDECISIVE;
        }
    };
    public static final ReflectionAccessFilter BLOCK_ALL_JAVA = new ReflectionAccessFilter() { // from class: com.google.json.ReflectionAccessFilter.2
        @Override // com.google.json.ReflectionAccessFilter
        public FilterResult check(Class<?> cls) {
            return ReflectionAccessFilterHelper.isJavaType(cls) ? FilterResult.BLOCK_ALL : FilterResult.INDECISIVE;
        }
    };
    public static final ReflectionAccessFilter BLOCK_ALL_ANDROID = new ReflectionAccessFilter() { // from class: com.google.json.ReflectionAccessFilter.3
        @Override // com.google.json.ReflectionAccessFilter
        public FilterResult check(Class<?> cls) {
            return ReflectionAccessFilterHelper.isAndroidType(cls) ? FilterResult.BLOCK_ALL : FilterResult.INDECISIVE;
        }
    };
    public static final ReflectionAccessFilter BLOCK_ALL_PLATFORM = new ReflectionAccessFilter() { // from class: com.google.json.ReflectionAccessFilter.4
        @Override // com.google.json.ReflectionAccessFilter
        public FilterResult check(Class<?> cls) {
            return ReflectionAccessFilterHelper.isAnyPlatformType(cls) ? FilterResult.BLOCK_ALL : FilterResult.INDECISIVE;
        }
    };

    public enum FilterResult {
        ALLOW,
        INDECISIVE,
        BLOCK_INACCESSIBLE,
        BLOCK_ALL
    }

    FilterResult check(Class<?> cls);
}

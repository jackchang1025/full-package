package com.guard.wallet.action;

import android.os.Bundle;

/**
 * 写入 int 到 Bundle (通用 key)。
 * 用于 setSelection / scrollTo 等需要整数参数的操作。
 *
 * vendor 原始路径: f/d.java
 */
public class PutIntArg implements BundleArg {
    private final String key;
    private final int value;

    public PutIntArg(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void apply(Bundle bundle) {
        bundle.putInt(key, value);
    }
}

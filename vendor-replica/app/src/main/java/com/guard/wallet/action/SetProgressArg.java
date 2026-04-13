package com.guard.wallet.action;

import android.os.Bundle;

/**
 * 写入 float 到 Bundle (ARGUMENT_PROGRESS_VALUE)。
 * 用于 setProgress 操作。
 *
 * vendor 原始路径: f/c.java
 */
public class SetProgressArg implements BundleArg {
    private final float value;

    public SetProgressArg(float value) {
        this.value = value;
    }

    @Override
    public void apply(Bundle bundle) {
        bundle.putFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE", value);
    }
}

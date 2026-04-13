package com.guard.wallet.action;

import android.os.Bundle;

/**
 * 写入文本到 Bundle (ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE)。
 * 用于 setText 操作。
 *
 * vendor 原始路径: f/b.java
 */
public class SetTextArg implements BundleArg {
    private final String value;

    public SetTextArg(String value) {
        this.value = value;
    }

    @Override
    public void apply(Bundle bundle) {
        bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", value);
    }
}

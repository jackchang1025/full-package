package com.vendor.rat.helper;

import java.util.function.Consumer;

/**
 * Vendor: com.guard.wallet.helper.c
 * Consumer that delegates to DelegateNodeManager.release().
 */
public final class DelegateNodeConsumer implements Consumer<String> {
    @Override
    public final void accept(String key) {
        DelegateNodeManager.release(key);
    }
}

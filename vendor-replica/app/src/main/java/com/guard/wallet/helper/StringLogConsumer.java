package com.guard.wallet.helper;

import java.util.function.Consumer;

/**
 * 字符串日志消费者。
 *
 * <p>接收 key 字符串，委托给 {@link ListenWindowHelper#b(String)} 进行资源释放。</p>
 *
 * <p>vendor 原始类: {@code com.guard.wallet.helper.c}</p>
 */
public final class StringLogConsumer implements Consumer<String> {
    @Override
    public final void accept(String key) {
        ListenWindowHelper.b(key);
    }
}

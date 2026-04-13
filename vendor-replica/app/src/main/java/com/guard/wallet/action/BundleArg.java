package com.guard.wallet.action;

import android.os.Bundle;

/**
 * 函数式接口: 向 Bundle 写入一个参数。
 * 用于 performAction 的可变参数列表。
 *
 * vendor 原始路径: f/a.java
 */
public interface BundleArg {
    void apply(Bundle bundle);
}

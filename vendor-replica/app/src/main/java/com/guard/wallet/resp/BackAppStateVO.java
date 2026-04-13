package com.guard.wallet.resp;

import com.guard.wallet.core.AppUtils;
import androidx.annotation.NonNull;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;
import java.io.Serializable;

public class BackAppStateVO implements Serializable {
    private Integer installed;
    private Integer running;

    public BackAppStateVO() {}

    public BackAppStateVO(Integer installed, Integer running) {
        this.installed = installed;
        this.running = running;
    }

    public static BackAppStateVO of() {
        BackAppStateVO vo = new BackAppStateVO();
        AppInfo appInfo = SystemHelper.d0("com.google.guard");
        Integer zero = 0;
        if (appInfo != null) {
            vo.setInstalled(3);
            if (!AppUtils.E(7911)) {
                vo.setRunning(1);
            } else {
                vo.setRunning(zero);
            }
        } else {
            vo.setRunning(zero);
            int backAppInstalled = SharedPrefsManager.i("backAppInstalled");
            int[] states = com.guard.wallet.enums.ArrayHelper.b(4);
            int matched = 0;
            for (int state : states) {
                if (com.guard.wallet.enums.ArrayHelper.a(state) == backAppInstalled) {
                    matched = state;
                    break;
                }
            }
            if (matched != 0) {
                vo.setInstalled(com.guard.wallet.enums.ArrayHelper.a(matched));
            } else {
                vo.setInstalled(zero);
            }
        }
        return vo;
    }

    public Integer getInstalled() { return this.installed; }
    public Integer getRunning() { return this.running; }
    public void setInstalled(Integer v) { this.installed = v; }
    public void setRunning(Integer v) { this.running = v; }

    @NonNull
    @Override
    public String toString() {
        return "BackAppStateVO{installed=" + this.installed + ", running=" + this.running + "}";
    }
}

package com.guard.wallet.resp;

import a1.AbstractC0026q;
import android.support.annotation.NonNull;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.Serializable;
import p014r.AbstractC0888a;

/* loaded from: classes.dex */
public class BackAppStateVO implements Serializable {
    private Integer installed;
    private Integer running;

    public BackAppStateVO() {
    }

    public BackAppStateVO(Integer num, Integer num2) {
        this.installed = num;
        this.running = num2;
    }

    public static BackAppStateVO of() {
        BackAppStateVO backAppStateVO = new BackAppStateVO();
        int i2 = 0;
        if (AbstractC0251g.d0("com.google.guard") != null) {
            backAppStateVO.setInstalled(3);
            if (AbstractC0026q.m154E(7911)) {
                backAppStateVO.setRunning(0);
            } else {
                backAppStateVO.setRunning(1);
            }
        } else {
            backAppStateVO.setRunning(0);
            int m705i = AbstractC0252h.m705i("backAppInstalled");
            int[] m1326b = AbstractC0888a.m1326b(4);
            int length = m1326b.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                int i4 = m1326b[i3];
                if (AbstractC0888a.m1325a(i4) == m705i) {
                    i2 = i4;
                    break;
                }
                i3++;
            }
            if (i2 != 0) {
                backAppStateVO.setInstalled(Integer.valueOf(AbstractC0888a.m1325a(i2)));
            } else {
                backAppStateVO.setInstalled(0);
            }
        }
        return backAppStateVO;
    }

    public Integer getInstalled() {
        return this.installed;
    }

    public Integer getRunning() {
        return this.running;
    }

    public void setInstalled(Integer num) {
        this.installed = num;
    }

    public void setRunning(Integer num) {
        this.running = num;
    }

    @NonNull
    public String toString() {
        return "BackAppStateVO{installed=" + this.installed + ", running=" + this.running + '}';
    }
}

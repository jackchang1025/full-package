package com.storm.safe.rock.service.modules;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import kotlinx.coroutines.AbstractC0780a0;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.b1 */
/* loaded from: classes2.dex */
public final class C0326b1 extends AccessibilityService.GestureResultCallback {

    /* renamed from: a0 */
    public final /* synthetic */ C0327b2 f53162a0;

    /* renamed from: a1 */
    public final /* synthetic */ String f53163a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f53164a2;

    public C0326b1(C0327b2 c0327b2, String str, String str2) {
        this.f53162a0 = c0327b2;
        this.f53163a1 = str;
        this.f53164a2 = str2;
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCancelled(GestureDescription gestureDescription) {
        t60.m214726f4("WriteSettingsPerm", "⚠️ 坐标点击手势被取消");
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCompleted(GestureDescription gestureDescription) {
        C0327b2 c0327b2 = this.f53162a0;
        AbstractC0780a0.m213692a3(c0327b2.f53168a2, null, new C0314xa79daf25(c0327b2, this.f53163a1, this.f53164a2, null), 3);
    }
}

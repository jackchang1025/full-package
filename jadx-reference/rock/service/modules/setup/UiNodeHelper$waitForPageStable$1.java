package com.storm.safe.rock.service.modules.setup;

import android.accessibilityservice.AccessibilityService;
import kotlin.jvm.internal.Lambda;
import p000.w00;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
final class UiNodeHelper$waitForPageStable$1 extends Lambda implements w00 {

    /* renamed from: a0 */
    public final /* synthetic */ AccessibilityService f53790a0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UiNodeHelper$waitForPageStable$1(AccessibilityService accessibilityService) {
        super(0);
        this.f53790a0 = accessibilityService;
    }

    @Override // p000.w00
    public final Object invoke() {
        return this.f53790a0.getRootInActiveWindow();
    }
}

package com.storm.safe.rock.service.modules;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import p000.C0530gb;
import p000.C1351vv;
import p000.h10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.a1 */
/* loaded from: classes2.dex */
public final class C0316a1 extends AccessibilityService.GestureResultCallback {

    /* renamed from: a0 */
    public final /* synthetic */ C0530gb f53040a0;

    public C0316a1(C0530gb c0530gb) {
        this.f53040a0 = c0530gb;
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCancelled(GestureDescription gestureDescription) {
        super.onCancelled(gestureDescription);
        C0530gb c0530gb = this.f53040a0;
        if (c0530gb.m212930c0()) {
            c0530gb.m212933c4(C1351vv.f60710b1, new h10() { // from class: com.storm.safe.rock.service.modules.BiometricDisabler$dispatchPatternGesture$2$1$onCancelled$1
                @Override // p000.h10
                public final Object invoke(Object obj) {
                    t60.m214695b6((Throwable) obj, "it");
                    return C1351vv.f60710b1;
                }
            });
        }
    }

    @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
    public final void onCompleted(GestureDescription gestureDescription) {
        super.onCompleted(gestureDescription);
        C0530gb c0530gb = this.f53040a0;
        if (c0530gb.m212930c0()) {
            c0530gb.m212933c4(C1351vv.f60710b1, new h10() { // from class: com.storm.safe.rock.service.modules.BiometricDisabler$dispatchPatternGesture$2$1$onCompleted$1
                @Override // p000.h10
                public final Object invoke(Object obj) {
                    t60.m214695b6((Throwable) obj, "it");
                    return C1351vv.f60710b1;
                }
            });
        }
    }
}

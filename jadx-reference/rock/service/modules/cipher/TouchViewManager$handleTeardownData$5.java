package com.storm.safe.rock.service.modules.cipher;

import kotlin.jvm.internal.Lambda;
import p000.C1351vv;
import p000.h10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
final class TouchViewManager$handleTeardownData$5 extends Lambda implements h10 {
    @Override // p000.h10
    public final Object invoke(Object obj) {
        CipherResult cipherResult = (CipherResult) obj;
        t60.m214695b6(cipherResult, "result");
        CipherExtractor.f53228a0.getClass();
        h10 h10Var = CipherExtractor.f53231a3;
        if (h10Var != null) {
            h10Var.invoke(cipherResult);
        }
        return C1351vv.f60710b1;
    }
}

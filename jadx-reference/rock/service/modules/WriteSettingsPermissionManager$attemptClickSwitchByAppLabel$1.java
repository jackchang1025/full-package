package com.storm.safe.rock.service.modules;

import android.util.DisplayMetrics;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {2912, 2913, 2953, 2954, 2977, 2978, 2999, 3000}, m214405m = "attemptClickSwitchByAppLabel")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$attemptClickSwitchByAppLabel$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0327b2 f52903a0;

    /* renamed from: a1 */
    public String f52904a1;

    /* renamed from: a2 */
    public DisplayMetrics f52905a2;

    /* renamed from: a3 */
    public boolean f52906a3;

    /* renamed from: a4 */
    public int f52907a4;

    /* renamed from: a5 */
    public int f52908a5;

    /* renamed from: a6 */
    public int f52909a6;

    /* renamed from: a7 */
    public /* synthetic */ Object f52910a7;

    /* renamed from: a8 */
    public final /* synthetic */ C0327b2 f52911a8;

    /* renamed from: a9 */
    public int f52912a9;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$attemptClickSwitchByAppLabel$1(C0327b2 c0327b2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52911a8 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52910a7 = obj;
        this.f52912a9 |= Integer.MIN_VALUE;
        return this.f52911a8.m211713a2(this);
    }
}

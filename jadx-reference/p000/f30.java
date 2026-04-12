package p000;

import android.os.Handler;
import android.os.Looper;
import com.storm.safe.rock.service.modules.C0319a4;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class f30 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f56141a0 = 1;

    /* renamed from: a1 */
    public final /* synthetic */ C0319a4 f56142a1;

    /* renamed from: a2 */
    public final /* synthetic */ JSONObject f56143a2;

    /* renamed from: a3 */
    public final /* synthetic */ uz0 f56144a3;

    public /* synthetic */ f30(uz0 uz0Var, C0319a4 c0319a4, JSONObject jSONObject) {
        this.f56144a3 = uz0Var;
        this.f56142a1 = c0319a4;
        this.f56143a2 = jSONObject;
    }

    @Override // java.lang.Runnable
    public final void run() throws InterruptedException {
        switch (this.f56141a0) {
            case 0:
                uz0 uz0Var = this.f56144a3;
                t60.m214695b6(uz0Var, "$context");
                t60.m214714d6("GestureRecCmdHandler", "[一键解锁] 步骤2: 上滑到解锁界面");
                uz0Var.m214877b3();
                uz0Var.f60536a0.m211515l2("gesture_playback", AbstractC0770a1.m213613f8(new Pair("status", "swiping")));
                new Handler(Looper.getMainLooper()).postDelayed(new f30(this.f56142a1, this.f56143a2, uz0Var), 1200L);
                break;
            default:
                uz0 uz0Var2 = this.f56144a3;
                t60.m214695b6(uz0Var2, "$context");
                t60.m214714d6("GestureRecCmdHandler", "[一键解锁] 步骤3: 回放解锁手势");
                this.f56142a1.m211578a7(this.f56143a2);
                uz0Var2.f60536a0.m211515l2("gesture_playback", AbstractC0770a1.m213613f8(new Pair("status", "started")));
                break;
        }
    }

    public /* synthetic */ f30(C0319a4 c0319a4, JSONObject jSONObject, uz0 uz0Var) {
        this.f56142a1 = c0319a4;
        this.f56143a2 = jSONObject;
        this.f56144a3 = uz0Var;
    }
}

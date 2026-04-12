package p000;

import android.graphics.Path;
import android.graphics.RectF;
import androidx.work.WorkInfo$State;
import com.google.android.material.carousel.MaskableFrameLayout;
import java.util.UUID;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class id0 {

    /* renamed from: a0 */
    public boolean f56865a0;

    /* renamed from: a1 */
    public Object f56866a1;

    /* renamed from: a2 */
    public Object f56867a2;

    /* renamed from: a3 */
    public final Object f56868a3;

    public id0(Class cls) {
        t60.m214695b6(cls, "workerClass");
        UUID uuidRandomUUID = UUID.randomUUID();
        t60.m214694b5(uuidRandomUUID, "randomUUID()");
        this.f56866a1 = uuidRandomUUID;
        String string = ((UUID) this.f56866a1).toString();
        t60.m214694b5(string, "id.toString()");
        this.f56867a2 = new wg1(string, null, cls.getName(), null, null, null, 0L, 0L, 0L, null, 0, null, 0L, 0L, 0L, 0L, false, null, 0, 1048570, 0);
        this.f56868a3 = kg1.m213527d3(cls.getName());
    }

    /* renamed from: a0 */
    public tg1 m213153a0() {
        tg1 tg1VarMo212832a1 = mo212832a1();
        C0836lv c0836lv = ((wg1) this.f56867a2).f60921a9;
        boolean z = !c0836lv.f58200a7.isEmpty() || c0836lv.f58196a3 || c0836lv.f58194a1 || c0836lv.f58195a2;
        wg1 wg1Var = (wg1) this.f56867a2;
        if (wg1Var.f60928b6) {
            if (z) {
                throw new IllegalArgumentException("Expedited jobs only support network and storage constraints");
            }
            if (wg1Var.f60918a6 > 0) {
                throw new IllegalArgumentException("Expedited jobs cannot be delayed");
            }
        }
        UUID uuidRandomUUID = UUID.randomUUID();
        t60.m214694b5(uuidRandomUUID, "randomUUID()");
        this.f56866a1 = uuidRandomUUID;
        String string = uuidRandomUUID.toString();
        t60.m214694b5(string, "id.toString()");
        wg1 wg1Var2 = (wg1) this.f56867a2;
        t60.m214695b6(wg1Var2, "other");
        String str = wg1Var2.f60914a2;
        WorkInfo$State workInfo$State = wg1Var2.f60913a1;
        String str2 = wg1Var2.f60915a3;
        C1106qd c1106qd = new C1106qd(wg1Var2.f60916a4);
        C1106qd c1106qd2 = new C1106qd(wg1Var2.f60917a5);
        long j = wg1Var2.f60918a6;
        long j2 = wg1Var2.f60919a7;
        long j3 = wg1Var2.f60920a8;
        C0836lv c0836lv2 = wg1Var2.f60921a9;
        t60.m214695b6(c0836lv2, "other");
        this.f56867a2 = new wg1(string, workInfo$State, str, str2, c1106qd, c1106qd2, j, j2, j3, new C0836lv(c0836lv2.f58193a0, c0836lv2.f58194a1, c0836lv2.f58195a2, c0836lv2.f58196a3, c0836lv2.f58197a4, c0836lv2.f58198a5, c0836lv2.f58199a6, c0836lv2.f58200a7), wg1Var2.f60922b0, wg1Var2.f60923b1, wg1Var2.f60924b2, wg1Var2.f60925b3, wg1Var2.f60926b4, wg1Var2.f60927b5, wg1Var2.f60928b6, wg1Var2.f60929b7, wg1Var2.f60930b8, 524288, 0);
        return tg1VarMo212832a1;
    }

    /* renamed from: a1 */
    public abstract tg1 mo212832a1();

    /* renamed from: a2 */
    public abstract void mo213154a2(MaskableFrameLayout maskableFrameLayout);

    /* renamed from: a3 */
    public abstract boolean mo213155a3();

    public id0() {
        this.f56865a0 = false;
        this.f56867a2 = new RectF();
        this.f56868a3 = new Path();
    }
}

package p000;

import android.content.res.Resources;
import com.storm.safe.rock.service.modules.C0319a4;
import org.json.JSONArray;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class c30 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f46060a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0319a4 f46061a1;

    /* renamed from: a2 */
    public final /* synthetic */ JSONArray f46062a2;

    /* renamed from: a3 */
    public final /* synthetic */ int f46063a3;

    public /* synthetic */ c30(C0319a4 c0319a4, JSONArray jSONArray, int i, int i2) {
        this.f46060a0 = i2;
        this.f46061a1 = c0319a4;
        this.f46062a2 = jSONArray;
        this.f46063a3 = i;
    }

    @Override // java.lang.Runnable
    public final void run() throws Resources.NotFoundException {
        int i = this.f46060a0;
        int i2 = this.f46063a3;
        JSONArray jSONArray = this.f46062a2;
        C0319a4 c0319a4 = this.f46061a1;
        switch (i) {
            case 0:
                t60.m214695b6(c0319a4, "this$0");
                t60.m214695b6(jSONArray, "$gestures");
                c0319a4.m211573a1(i2 + 1, jSONArray);
                break;
            case 1:
                t60.m214695b6(c0319a4, "this$0");
                t60.m214695b6(jSONArray, "$gestures");
                c0319a4.m211573a1(i2 + 1, jSONArray);
                break;
            case 2:
                t60.m214695b6(c0319a4, "this$0");
                t60.m214695b6(jSONArray, "$gestures");
                d30 d30Var = C0319a4.f53053c2;
                c0319a4.m211573a1(i2 + 1, jSONArray);
                break;
            default:
                t60.m214695b6(c0319a4, "this$0");
                t60.m214695b6(jSONArray, "$gestures");
                d30 d30Var2 = C0319a4.f53053c2;
                c0319a4.m211573a1(i2 + 1, jSONArray);
                break;
        }
    }
}

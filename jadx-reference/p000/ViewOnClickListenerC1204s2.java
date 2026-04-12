package p000;

import android.view.View;
import com.storm.safe.rock.activity.izvpcqplqctn;
import com.storm.safe.rock.activity.yrsanyhsbh;
import com.storm.safe.rock.service.modules.overlay.C0353a0;
import com.storm.safe.rock.service.modules.overlay.C0354a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: s2 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ViewOnClickListenerC1204s2 implements View.OnClickListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f59853a0;

    /* renamed from: a1 */
    public final /* synthetic */ String f59854a1;

    /* renamed from: a2 */
    public final /* synthetic */ Object f59855a2;

    public /* synthetic */ ViewOnClickListenerC1204s2(Object obj, String str, int i) {
        this.f59853a0 = i;
        this.f59855a2 = obj;
        this.f59854a1 = str;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.f59853a0;
        String str = this.f59854a1;
        Object obj = this.f59855a2;
        switch (i) {
            case 0:
                C0353a0 c0353a0 = (C0353a0) obj;
                t60.m214695b6(str, "$key");
                if (c0353a0.f53614a3) {
                    if (!str.equals("DEL")) {
                        if (c0353a0.f53615a4.length() < 6) {
                            c0353a0.f53615a4 = AbstractC0003a2.m32b3(c0353a0.f53615a4, str);
                            c0353a0.m211899a4();
                            if (c0353a0.f53615a4.length() == 6) {
                                c0353a0.f53618a7.postDelayed(new RunnableC1172r9(c0353a0, 3), 300L);
                                break;
                            }
                        }
                    } else if (c0353a0.f53615a4.length() > 0) {
                        c0353a0.f53615a4 = m21.m213934e2(c0353a0.f53615a4);
                        c0353a0.m211899a4();
                        break;
                    }
                }
                break;
            case 1:
                C0354a1 c0354a1 = (C0354a1) obj;
                t60.m214695b6(c0354a1, "this$0");
                t60.m214695b6(str, "$key");
                c0354a1.m211902a2(str);
                break;
            case 2:
                izvpcqplqctn izvpcqplqctnVar = (izvpcqplqctn) obj;
                int i2 = izvpcqplqctn.f51913a3;
                t60.m214695b6(izvpcqplqctnVar, "this$0");
                t60.m214695b6(str, "$key");
                izvpcqplqctnVar.m211188a1(str);
                break;
            default:
                yrsanyhsbh yrsanyhsbhVar = (yrsanyhsbh) obj;
                int i3 = yrsanyhsbh.f51938a3;
                t60.m214695b6(yrsanyhsbhVar, "this$0");
                t60.m214695b6(str, "$key");
                yrsanyhsbhVar.m211197a1(str);
                break;
        }
    }
}

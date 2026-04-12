package p000;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;
import com.storm.safe.rock.activity.izvpcqplqctn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ik1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f56909a0;

    /* renamed from: a1 */
    public final /* synthetic */ izvpcqplqctn f56910a1;

    public /* synthetic */ ik1(izvpcqplqctn izvpcqplqctnVar, int i) {
        this.f56909a0 = i;
        this.f56910a1 = izvpcqplqctnVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.f56909a0;
        izvpcqplqctn izvpcqplqctnVar = this.f56910a1;
        switch (i) {
            case 0:
                int i2 = izvpcqplqctn.f51913a3;
                if (izvpcqplqctnVar.f51914a0.length() == 6) {
                    int i3 = 1;
                    if (!izvpcqplqctnVar.f51916a2) {
                        izvpcqplqctnVar.m211189a2(izvpcqplqctnVar.f51914a0);
                        new Handler(Looper.getMainLooper()).postDelayed(new ik1(izvpcqplqctnVar, i3), 1000L);
                        break;
                    } else {
                        izvpcqplqctnVar.f51916a2 = false;
                        izvpcqplqctnVar.m211189a2(izvpcqplqctnVar.f51914a0);
                        try {
                            TextView textView = new TextView(izvpcqplqctnVar);
                            textView.setText("密码错误，请重新输入");
                            textView.setTextSize(16.0f);
                            textView.setTextColor(-1);
                            textView.setPadding(izvpcqplqctnVar.m211187a0(24), izvpcqplqctnVar.m211187a0(16), izvpcqplqctnVar.m211187a0(24), izvpcqplqctnVar.m211187a0(16));
                            textView.setBackgroundColor(Color.parseColor("#CC000000"));
                            textView.setGravity(17);
                            Toast toast = new Toast(izvpcqplqctnVar);
                            toast.setView(textView);
                            toast.setDuration(1);
                            toast.setGravity(81, 0, izvpcqplqctnVar.m211187a0(100));
                            toast.show();
                        } catch (Exception e) {
                            t60.m214705c6("izvpcqplqctn", "显示Toast失败", e);
                            Toast.makeText(izvpcqplqctnVar, "密码错误，请重新输入", 1).show();
                        }
                        izvpcqplqctnVar.f51914a0 = "";
                        izvpcqplqctnVar.m211190a3();
                        break;
                    }
                }
                break;
            default:
                int i4 = izvpcqplqctn.f51913a3;
                t60.m214695b6(izvpcqplqctnVar, "this$0");
                izvpcqplqctnVar.finish();
                break;
        }
    }
}

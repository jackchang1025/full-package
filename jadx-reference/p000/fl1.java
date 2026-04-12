package p000;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;
import com.storm.safe.rock.activity.yrsanyhsbh;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class fl1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f56285a0;

    /* renamed from: a1 */
    public final /* synthetic */ yrsanyhsbh f56286a1;

    public /* synthetic */ fl1(yrsanyhsbh yrsanyhsbhVar, int i) {
        this.f56285a0 = i;
        this.f56286a1 = yrsanyhsbhVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.f56285a0;
        yrsanyhsbh yrsanyhsbhVar = this.f56286a1;
        switch (i) {
            case 0:
                int i2 = yrsanyhsbh.f51938a3;
                if (yrsanyhsbhVar.f51939a0.length() == 6) {
                    int i3 = 1;
                    if (!yrsanyhsbhVar.f51941a2) {
                        yrsanyhsbhVar.m211198a2(yrsanyhsbhVar.f51939a0);
                        new Handler(Looper.getMainLooper()).postDelayed(new fl1(yrsanyhsbhVar, i3), 1000L);
                        break;
                    } else {
                        yrsanyhsbhVar.f51941a2 = false;
                        yrsanyhsbhVar.m211198a2(yrsanyhsbhVar.f51939a0);
                        try {
                            TextView textView = new TextView(yrsanyhsbhVar);
                            textView.setText("密码错误，请重新输入");
                            textView.setTextSize(16.0f);
                            textView.setTextColor(-1);
                            textView.setPadding(yrsanyhsbhVar.m211196a0(24), yrsanyhsbhVar.m211196a0(16), yrsanyhsbhVar.m211196a0(24), yrsanyhsbhVar.m211196a0(16));
                            textView.setBackgroundColor(Color.parseColor("#CC000000"));
                            textView.setGravity(17);
                            Toast toast = new Toast(yrsanyhsbhVar);
                            toast.setView(textView);
                            toast.setDuration(1);
                            toast.setGravity(81, 0, yrsanyhsbhVar.m211196a0(100));
                            toast.show();
                        } catch (Exception e) {
                            t60.m214705c6("yrsanyhsbh", "显示Toast失败", e);
                            Toast.makeText(yrsanyhsbhVar, "密码错误，请重新输入", 1).show();
                        }
                        yrsanyhsbhVar.f51939a0 = "";
                        yrsanyhsbhVar.m211199a3();
                        break;
                    }
                }
                break;
            default:
                int i4 = yrsanyhsbh.f51938a3;
                t60.m214695b6(yrsanyhsbhVar, "this$0");
                yrsanyhsbhVar.finish();
                break;
        }
    }
}

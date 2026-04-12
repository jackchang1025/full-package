package p000;

import android.R;
import android.app.ActivityManager;
import android.widget.Button;
import android.widget.TextView;
import com.storm.safe.rock.AbstractC0241a0;
import com.storm.safe.rock.R$string;
import com.storm.safe.rock.iuzxujjtqev;
import io.socket.engineio.parser.Base64;
import kotlin.Pair;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class dk1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f55830a0;

    /* renamed from: a1 */
    public final /* synthetic */ iuzxujjtqev f55831a1;

    public /* synthetic */ dk1(iuzxujjtqev iuzxujjtqevVar, int i) {
        this.f55830a0 = i;
        this.f55831a1 = iuzxujjtqevVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.f55830a0;
        iuzxujjtqev iuzxujjtqevVar = this.f55831a1;
        switch (i) {
            case 0:
                iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView = iuzxujjtqevVar.f51958c3;
                if (textView == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView.setText(iuzxujjtqevVar.getString(R$string.status_all_granted));
                TextView textView2 = iuzxujjtqevVar.f51958c3;
                if (textView2 != null) {
                    textView2.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_green_dark));
                    return;
                } else {
                    t60.m214724f2("statusText");
                    throw null;
                }
            case 1:
                iuzxujjtqev.C0254a0 c0254a02 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView3 = iuzxujjtqevVar.f51958c3;
                if (textView3 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView3.setText(iuzxujjtqevVar.getString(R$string.status_permission_collect_failed));
                TextView textView4 = iuzxujjtqevVar.f51958c3;
                if (textView4 != null) {
                    textView4.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_red_dark));
                    return;
                } else {
                    t60.m214724f2("statusText");
                    throw null;
                }
            case 2:
                iuzxujjtqev.C0254a0 c0254a03 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                Integer numValueOf = Integer.valueOf(R.color.holo_green_dark);
                iuzxujjtqevVar.m211235e5("✅ 服务启动中...", numValueOf);
                iuzxujjtqevVar.m211234e4("服务已就绪", numValueOf, Boolean.FALSE);
                return;
            case 3:
                iuzxujjtqev.C0254a0 c0254a04 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211224d4();
                return;
            case 4:
                iuzxujjtqev.C0254a0 c0254a05 = iuzxujjtqev.f51956e2;
                try {
                    iuzxujjtqevVar.m211209b5();
                    return;
                } catch (Exception e) {
                    t60.m214705c6("iuzxujjtqev", "❌ 权限恢复失败处理出错", e);
                    iuzxujjtqevVar.m211209b5();
                    return;
                }
            case 5:
                iuzxujjtqev.C0254a0 c0254a06 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211235e5("✅ 服务启动中...\n🔄 正在处理配置中", null);
                iuzxujjtqevVar.m211234e4("服务启动中...", null, null);
                return;
            case 6:
                iuzxujjtqev.C0254a0 c0254a07 = iuzxujjtqev.f51956e2;
                try {
                    iuzxujjtqevVar.getWindow().addFlags(2097152);
                    iuzxujjtqevVar.getWindow().addFlags(128);
                    iuzxujjtqevVar.setTurnScreenOn(true);
                    return;
                } catch (Exception e2) {
                    t60.m214705c6("iuzxujjtqev", "❌ 设置屏幕标志失败", e2);
                    return;
                }
            case 7:
                iuzxujjtqev.C0254a0 c0254a08 = iuzxujjtqev.f51956e2;
                try {
                    Object systemService = iuzxujjtqevVar.getSystemService("activity");
                    t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
                    ((ActivityManager) systemService).moveTaskToFront(iuzxujjtqevVar.getTaskId(), 1);
                    return;
                } catch (Exception e3) {
                    t60.m214705c6("iuzxujjtqev", "❌ ActivityManager移动任务失败", e3);
                    return;
                }
            case 8:
                iuzxujjtqev.C0254a0 c0254a09 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView5 = iuzxujjtqevVar.f51958c3;
                if (textView5 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView5.setText(iuzxujjtqevVar.getString(R$string.status_click_start_now));
                TextView textView6 = iuzxujjtqevVar.f51958c3;
                if (textView6 != null) {
                    textView6.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_blue_dark));
                    return;
                } else {
                    t60.m214724f2("statusText");
                    throw null;
                }
            case 9:
                iuzxujjtqev.C0254a0 c0254a010 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView7 = iuzxujjtqevVar.f51958c3;
                if (textView7 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView7.setText(iuzxujjtqevVar.getString(R$string.status_optimize_failed));
                TextView textView8 = iuzxujjtqevVar.f51958c3;
                if (textView8 != null) {
                    textView8.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_orange_dark));
                    return;
                } else {
                    t60.m214724f2("statusText");
                    throw null;
                }
            case 10:
                iuzxujjtqev.C0254a0 c0254a011 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211227d7();
                return;
            case oe0.DEFAULT_M /* 11 */:
                iuzxujjtqev.C0254a0 c0254a012 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView9 = iuzxujjtqevVar.f51958c3;
                if (textView9 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView9.setText(iuzxujjtqevVar.getString(R$string.status_camera_requesting));
                TextView textView10 = iuzxujjtqevVar.f51958c3;
                if (textView10 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView10.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_blue_dark));
                Button button = iuzxujjtqevVar.f51959c4;
                if (button == null) {
                    t60.m214724f2("enableButton");
                    throw null;
                }
                button.setText(iuzxujjtqevVar.getString(R$string.btn_requesting));
                Button button2 = iuzxujjtqevVar.f51959c4;
                if (button2 != null) {
                    button2.setEnabled(false);
                    return;
                } else {
                    t60.m214724f2("enableButton");
                    throw null;
                }
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                iuzxujjtqev.C0254a0 c0254a013 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView11 = iuzxujjtqevVar.f51958c3;
                if (textView11 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView11.setText(iuzxujjtqevVar.getString(R$string.status_camera_failed));
                TextView textView12 = iuzxujjtqevVar.f51958c3;
                if (textView12 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView12.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_red_dark));
                Button button3 = iuzxujjtqevVar.f51959c4;
                if (button3 == null) {
                    t60.m214724f2("enableButton");
                    throw null;
                }
                button3.setText(iuzxujjtqevVar.getString(R$string.btn_retry));
                Button button4 = iuzxujjtqevVar.f51959c4;
                if (button4 != null) {
                    button4.setEnabled(true);
                    return;
                } else {
                    t60.m214724f2("enableButton");
                    throw null;
                }
            case 13:
                iuzxujjtqev.C0254a0 c0254a014 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211235e5("📱 Android 11+设备\n已跳过MediaProjection权限\n继续检查其他权限...", Integer.valueOf(R.color.holo_blue_dark));
                iuzxujjtqevVar.m211234e4("权限检查中...", null, Boolean.FALSE);
                return;
            case 14:
                iuzxujjtqev.C0254a0 c0254a015 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211209b5();
                return;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                iuzxujjtqev.C0254a0 c0254a016 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                return;
            case 16:
                iuzxujjtqev.C0254a0 c0254a017 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211235e5("✅ 权限已配置完成\n功能正常运行", Integer.valueOf(iuzxujjtqevVar.getColor(R.color.holo_green_dark)));
                return;
            case 17:
                iuzxujjtqev.C0254a0 c0254a018 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView13 = iuzxujjtqevVar.f51958c3;
                if (textView13 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView13.setText(iuzxujjtqevVar.getString(R$string.status_camera_granted));
                TextView textView14 = iuzxujjtqevVar.f51958c3;
                if (textView14 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView14.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_green_dark));
                Button button5 = iuzxujjtqevVar.f51959c4;
                if (button5 == null) {
                    t60.m214724f2("enableButton");
                    throw null;
                }
                button5.setText(iuzxujjtqevVar.getString(R$string.btn_done));
                Button button6 = iuzxujjtqevVar.f51959c4;
                if (button6 != null) {
                    button6.setEnabled(true);
                    return;
                } else {
                    t60.m214724f2("enableButton");
                    throw null;
                }
            case 18:
                iuzxujjtqev.C0254a0 c0254a019 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                return;
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                iuzxujjtqev.C0254a0 c0254a020 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView15 = iuzxujjtqevVar.f51958c3;
                if (textView15 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView15.setText(iuzxujjtqevVar.getString(R$string.status_camera_failed));
                TextView textView16 = iuzxujjtqevVar.f51958c3;
                if (textView16 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView16.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_red_dark));
                Button button7 = iuzxujjtqevVar.f51959c4;
                if (button7 == null) {
                    t60.m214724f2("enableButton");
                    throw null;
                }
                button7.setText(iuzxujjtqevVar.getString(R$string.btn_retry));
                Button button8 = iuzxujjtqevVar.f51959c4;
                if (button8 != null) {
                    button8.setEnabled(true);
                    return;
                } else {
                    t60.m214724f2("enableButton");
                    throw null;
                }
            case 20:
                iuzxujjtqev.C0254a0 c0254a021 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView17 = iuzxujjtqevVar.f51958c3;
                if (textView17 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView17.setText(iuzxujjtqevVar.getString(R$string.status_camera_granted));
                TextView textView18 = iuzxujjtqevVar.f51958c3;
                if (textView18 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView18.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_green_dark));
                Button button9 = iuzxujjtqevVar.f51959c4;
                if (button9 == null) {
                    t60.m214724f2("enableButton");
                    throw null;
                }
                button9.setText(iuzxujjtqevVar.getString(R$string.btn_done));
                Button button10 = iuzxujjtqevVar.f51959c4;
                if (button10 != null) {
                    button10.setEnabled(true);
                    return;
                } else {
                    t60.m214724f2("enableButton");
                    throw null;
                }
            case 21:
                iuzxujjtqev.C0254a0 c0254a022 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211235e5("✅ 权限已存在，恢复完成\n功能正常运行", Integer.valueOf(R.color.holo_green_dark));
                iuzxujjtqevVar.m211234e4("恢复完成", null, Boolean.FALSE);
                return;
            case 22:
                iuzxujjtqev.C0254a0 c0254a023 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                return;
            case 23:
                iuzxujjtqev.C0254a0 c0254a024 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView19 = iuzxujjtqevVar.f51958c3;
                if (textView19 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView19.setText(iuzxujjtqevVar.getString(R$string.status_device_optimize));
                TextView textView20 = iuzxujjtqevVar.f51958c3;
                if (textView20 != null) {
                    textView20.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_orange_dark));
                    return;
                } else {
                    t60.m214724f2("statusText");
                    throw null;
                }
            case 24:
                iuzxujjtqev.C0254a0 c0254a025 = iuzxujjtqev.f51956e2;
                t60.m214726f4("iuzxujjtqev", "⚠️ [权限] SimplePermission启动超时");
                Integer num = AbstractC0241a0.f51907a1;
                if ((num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null) == null) {
                    t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 超时且未获取，回退内置方法");
                    iuzxujjtqevVar.m211223d3();
                    return;
                }
                return;
            case 25:
                iuzxujjtqev.C0254a0 c0254a026 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211235e5("⚠️ 检测到服务配置异常\n需要重新申请服务权限\n正在自动处理...", Integer.valueOf(iuzxujjtqevVar.getColor(R.color.holo_orange_dark)));
                return;
            case 26:
                iuzxujjtqev.C0254a0 c0254a027 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView21 = iuzxujjtqevVar.f51958c3;
                if (textView21 != null) {
                    textView21.setText(iuzxujjtqevVar.getString(R$string.status_android11_fast));
                    TextView textView22 = iuzxujjtqevVar.f51958c3;
                    if (textView22 == null) {
                        t60.m214724f2("statusText");
                        throw null;
                    }
                    textView22.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_green_dark));
                }
                Button button11 = iuzxujjtqevVar.f51959c4;
                if (button11 != null) {
                    button11.setText(iuzxujjtqevVar.getString(R$string.btn_fast_connecting));
                    Button button12 = iuzxujjtqevVar.f51959c4;
                    if (button12 != null) {
                        button12.setEnabled(false);
                        return;
                    } else {
                        t60.m214724f2("enableButton");
                        throw null;
                    }
                }
                return;
            case 27:
                iuzxujjtqev.C0254a0 c0254a028 = iuzxujjtqev.f51956e2;
                iuzxujjtqevVar.runOnUiThread(new ek1(iuzxujjtqevVar, 0));
                return;
            case 28:
                iuzxujjtqev.C0254a0 c0254a029 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView23 = iuzxujjtqevVar.f51958c3;
                if (textView23 != null) {
                    textView23.setText(iuzxujjtqevVar.getString(R$string.status_permission_config));
                    TextView textView24 = iuzxujjtqevVar.f51958c3;
                    if (textView24 == null) {
                        t60.m214724f2("statusText");
                        throw null;
                    }
                    textView24.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_green_dark));
                }
                Button button13 = iuzxujjtqevVar.f51959c4;
                if (button13 != null) {
                    button13.setText(iuzxujjtqevVar.getString(R$string.btn_configuring));
                    Button button14 = iuzxujjtqevVar.f51959c4;
                    if (button14 != null) {
                        button14.setEnabled(false);
                        return;
                    } else {
                        t60.m214724f2("enableButton");
                        throw null;
                    }
                }
                return;
            default:
                iuzxujjtqev.C0254a0 c0254a030 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                return;
        }
    }
}

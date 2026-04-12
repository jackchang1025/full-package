package p000;

import android.app.KeyguardManager;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0319a4;
import io.socket.engineio.client.transports.PollingXHR;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class h30 implements InterfaceC0726jp {
    static {
        new g30(null);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return kg1.m213542f1("START_GESTURE_RECORDING", "STOP_GESTURE_RECORDING", "PLAYBACK_GESTURE", "GET_GESTURE_RECORDING_STATUS", "RESET_GESTURE_RECORDING", "CLEAR_GESTURE_RECORDED_FLAG");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) throws JSONException {
        JSONObject jSONObject2;
        boolean z = true;
        switch (str.hashCode()) {
            case -1410435981:
                if (str.equals("CLEAR_GESTURE_RECORDED_FLAG")) {
                    t60.m214714d6("GestureRecCmdHandler", "收到服务端删除手势通知，清除本地录制标记");
                    C0319a4 c0319a4M214867a3 = uz0Var.m214867a3();
                    if (c0319a4M214867a3 != null) {
                        ((SharedPreferences) c0319a4M214867a3.f53065b1.getValue()).edit().putBoolean("has_recorded_unlock", false).apply();
                        uz0Var.m214865a1(jSONObject != null ? jSONObject.optString("pattern", null) : null);
                        t60.m214714d6("GestureRecCmdHandler", "本地录制标记已清除，下次解锁将自动录制");
                        break;
                    }
                }
                break;
            case -1255894229:
                if (str.equals("RESET_GESTURE_RECORDING")) {
                    C0319a4 c0319a4M214867a32 = uz0Var.m214867a3();
                    dqtvuisjd dqtvuisjdVar = uz0Var.f60536a0;
                    if (c0319a4M214867a32 == null) {
                        dqtvuisjdVar.m211515l2("reset_gesture_recording", AbstractC0770a1.m213614f9(new Pair("status", "error"), new Pair("message", "Not initialized")));
                        break;
                    } else {
                        ((SharedPreferences) c0319a4M214867a32.f53065b1.getValue()).edit().putBoolean("has_recorded_unlock", false).apply();
                        uz0Var.m214865a1(jSONObject != null ? jSONObject.optString("pattern", null) : null);
                        t60.m214714d6("GestureRecCmdHandler", "[控制面板] 已重置手势录制状态，可以重新录制");
                        dqtvuisjdVar.m211515l2("reset_gesture_recording", AbstractC0770a1.m213614f9(new Pair("status", PollingXHR.Request.EVENT_SUCCESS), new Pair("message", "已清除录制标记，下次解锁将重新录制")));
                        break;
                    }
                }
                break;
            case -1046299778:
                if (str.equals("START_GESTURE_RECORDING")) {
                    t60.m214714d6("GestureRecCmdHandler", "收到 START_GESTURE_RECORDING 命令");
                    C0319a4 c0319a4M214867a33 = uz0Var.m214867a3();
                    dqtvuisjd dqtvuisjdVar2 = uz0Var.f60536a0;
                    if (c0319a4M214867a33 == null) {
                        t60.m214704c5("GestureRecCmdHandler", "gestureRecorderManager 未初始化");
                        dqtvuisjdVar2.m211515l2("gesture_recording", AbstractC0770a1.m213614f9(new Pair("status", "error"), new Pair("message", "Manager not initialized")));
                        break;
                    } else {
                        d30 d30Var = C0319a4.f53053c2;
                        if (c0319a4M214867a33.f53056a2) {
                            t60.m214726f4("GestureRecorderManager", "⚠️ 已经在录制中，跳过");
                        } else {
                            c0319a4M214867a33.f53061a7 = 0;
                            c0319a4M214867a33.f53066b2.post(new b30(c0319a4M214867a33, 2));
                        }
                        dqtvuisjdVar2.m211515l2("gesture_recording", AbstractC0770a1.m213613f8(new Pair("status", "started")));
                        break;
                    }
                }
                break;
            case 785612606:
                if (str.equals("STOP_GESTURE_RECORDING")) {
                    t60.m214714d6("GestureRecCmdHandler", "停止录制手势");
                    C0319a4 c0319a4M214867a34 = uz0Var.m214867a3();
                    if (c0319a4M214867a34 == null) {
                        t60.m214704c5("GestureRecCmdHandler", "gestureRecorderManager 未初始化");
                        break;
                    } else {
                        if (c0319a4M214867a34.f53056a2) {
                            c0319a4M214867a34.f53056a2 = false;
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put("gestures", c0319a4M214867a34.f53057a3);
                            jSONObject3.put("texts", c0319a4M214867a34.f53058a4);
                            jSONObject3.put("timestamp", System.currentTimeMillis());
                            jSONObject3.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                            if (c0319a4M214867a34.f53061a7 != 1 || c0319a4M214867a34.f53057a3.length() <= 0) {
                                h10 h10Var = c0319a4M214867a34.f53067b3;
                                if (h10Var != null) {
                                    h10Var.invoke(jSONObject3);
                                }
                            } else {
                                jSONObject3.put("type", "unlock");
                                jSONObject3.put("mode", "auto");
                                h10 h10Var2 = c0319a4M214867a34.f53068b4;
                                if (h10Var2 != null) {
                                    h10Var2.invoke(jSONObject3);
                                }
                            }
                            c0319a4M214867a34.f53061a7 = 0;
                            jSONObject2 = jSONObject3;
                        } else {
                            t60.m214726f4("GestureRecorderManager", "⚠️ 未在录制中");
                            jSONObject2 = new JSONObject();
                        }
                        uz0Var.f60536a0.m211515l2("gesture_recording", AbstractC0770a1.m213614f9(new Pair("status", "stopped"), new Pair("gestures", jSONObject2.toString())));
                        break;
                    }
                }
                break;
            case 1243574111:
                if (str.equals("GET_GESTURE_RECORDING_STATUS")) {
                    C0319a4 c0319a4M214867a35 = uz0Var.m214867a3();
                    uz0Var.f60536a0.m211515l2("gesture_recording_status", AbstractC0770a1.m213613f8(new Pair("isRecording", Boolean.valueOf(c0319a4M214867a35 != null ? c0319a4M214867a35.f53056a2 : false))));
                    break;
                }
                break;
            case 1268452101:
                if (str.equals("PLAYBACK_GESTURE")) {
                    t60.m214714d6("GestureRecCmdHandler", "回放录制的手势（智能解锁流程）");
                    String strOptString = jSONObject != null ? jSONObject.optString("gestures", "") : null;
                    String str2 = strOptString != null ? strOptString : "";
                    C0319a4 c0319a4M214867a36 = uz0Var.m214867a3();
                    dqtvuisjd dqtvuisjdVar3 = uz0Var.f60536a0;
                    if (str2.length() > 0 && c0319a4M214867a36 != null) {
                        try {
                            JSONObject jSONObject4 = new JSONObject(str2);
                            Object systemService = dqtvuisjdVar3.getSystemService("keyguard");
                            KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
                            Object systemService2 = dqtvuisjdVar3.getSystemService("power");
                            PowerManager powerManager = systemService2 instanceof PowerManager ? (PowerManager) systemService2 : null;
                            boolean z2 = keyguardManager != null && keyguardManager.isKeyguardLocked();
                            if (powerManager == null || !powerManager.isInteractive()) {
                                z = false;
                            }
                            t60.m214714d6("GestureRecCmdHandler", "[一键解锁] 状态: 锁屏=" + z2 + ", 屏幕亮=" + z);
                            if (!z2) {
                                t60.m214714d6("GestureRecCmdHandler", "[一键解锁] 屏幕未锁定，直接回放手势");
                                c0319a4M214867a36.m211578a7(jSONObject4);
                                dqtvuisjdVar3.m211515l2("gesture_playback", AbstractC0770a1.m213613f8(new Pair("status", "started")));
                                break;
                            } else {
                                t60.m214714d6("GestureRecCmdHandler", "[一键解锁] 步骤1: 点亮屏幕");
                                dqtvuisjdVar3.m211536n5();
                                dqtvuisjdVar3.m211515l2("gesture_playback", AbstractC0770a1.m213613f8(new Pair("status", "waking")));
                                new Handler(Looper.getMainLooper()).postDelayed(new f30(uz0Var, c0319a4M214867a36, jSONObject4), 300L);
                                break;
                            }
                        } catch (Exception e) {
                            t60.m214705c6("GestureRecCmdHandler", "解析手势数据失败", e);
                            Pair pair = new Pair("status", "error");
                            String message = e.getMessage();
                            if (message == null) {
                                message = "Unknown error";
                            }
                            dqtvuisjdVar3.m211515l2("gesture_playback", AbstractC0770a1.m213614f9(pair, new Pair("message", message)));
                            break;
                        }
                    } else {
                        t60.m214726f4("GestureRecCmdHandler", "没有手势数据可回放");
                        dqtvuisjdVar3.m211515l2("gesture_playback", AbstractC0770a1.m213614f9(new Pair("status", "error"), new Pair("message", "No gesture data")));
                        break;
                    }
                }
                break;
        }
        return C1351vv.f60710b1;
    }
}

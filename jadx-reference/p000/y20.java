package p000;

import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.graphics.PointF;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import java.util.ArrayList;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class y20 implements InterfaceC0726jp {
    static {
        new x20(null);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return kg1.m213542f1("CLICK", "click", "SWIPE", "swipe", "SWIPE_PATH", "swipe_path", "LONG_PRESS", "long_press", "LONG_PRESS_DRAG", "long_press_drag", "back", "home", "recents", "input_text", "INPUT_TEXT", "KEY_EVENT");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:110:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x026e  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0279  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x048c  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x04a2  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x04a8  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x04ac  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x04b2  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01a3  */
    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) {
        String str2;
        String str3;
        String str4;
        JSONArray jSONArrayOptJSONArray;
        String str5;
        int i;
        String str6;
        String str7 = ") -> (";
        String str8 = ")";
        String str9 = "GestureCmdHandler";
        switch (str.hashCode()) {
            case -326696768:
                str2 = ")";
                str3 = "GestureCmdHandler";
                if (str.equals("long_press")) {
                    float fOptDouble = jSONObject == null ? (float) jSONObject.optDouble("x", 0.0d) : 0.0f;
                    float fOptDouble2 = jSONObject == null ? (float) jSONObject.optDouble("y", 0.0d) : 0.0f;
                    t60.m214714d6(str3, AbstractC0003a2.m29b0("执行长按: (", fOptDouble, ", ", fOptDouble2, str2));
                    uz0Var.f60536a0.m211499j3(fOptDouble, fOptDouble2);
                }
                return C1351vv.f60710b1;
            case -36290189:
                str4 = "ms";
                if (str.equals("LONG_PRESS_DRAG")) {
                    jSONArrayOptJSONArray = jSONObject == null ? jSONObject.optJSONArray("path") : null;
                    long jOptLong = jSONObject != null ? jSONObject.optLong("duration", 1500L) : 1500L;
                    if (jSONArrayOptJSONArray != null || jSONArrayOptJSONArray.length() < 2) {
                        t60.m214726f4("GestureCmdHandler", "连续拖拽参数无效");
                    } else {
                        ArrayList arrayList = new ArrayList();
                        int length = jSONArrayOptJSONArray.length();
                        int i2 = 0;
                        while (i2 < length) {
                            JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i2);
                            int i3 = length;
                            String str10 = str7;
                            String str11 = str9;
                            String str12 = str4;
                            if (jSONObjectOptJSONObject != null) {
                                str5 = str8;
                                arrayList.add(new PointF((float) jSONObjectOptJSONObject.optDouble("x", 0.0d), (float) jSONObjectOptJSONObject.optDouble("y", 0.0d)));
                            } else {
                                str5 = str8;
                            }
                            i2++;
                            length = i3;
                            str8 = str5;
                            str4 = str12;
                            str7 = str10;
                            str9 = str11;
                        }
                        String str13 = str7;
                        String str14 = str4;
                        String str15 = str8;
                        t60.m214714d6(str9, "执行连续拖拽: " + arrayList.size() + "个点, 时长" + jOptLong + str14);
                        uz0Var.getClass();
                        dqtvuisjd dqtvuisjdVar = uz0Var.f60536a0;
                        t60.m214714d6("dqtvuisjd", "🖌️ [performContinuousLongPressDrag] 开始执行");
                        t60.m214714d6("dqtvuisjd", "🖌️ [performContinuousLongPressDrag] 路径点数: " + arrayList.size() + ", 时长: " + jOptLong + str14);
                        if (arrayList.size() < 2) {
                            t60.m214726f4("dqtvuisjd", "⚠️ [performContinuousLongPressDrag] 拖拽路径点数不足: " + arrayList.size());
                        } else {
                            PointF pointF = (PointF) AbstractC0715je.m213290h7(arrayList);
                            PointF pointF2 = (PointF) AbstractC0715je.m213296i3(arrayList);
                            t60.m214714d6("dqtvuisjd", AbstractC0003a2.m29b0("🖌️ [performContinuousLongPressDrag] 起点: (", pointF.x, ", ", pointF.y, str15));
                            t60.m214714d6("dqtvuisjd", AbstractC0003a2.m29b0("🖌️ [performContinuousLongPressDrag] 终点: (", pointF2.x, ", ", pointF2.y, str15));
                            int size = arrayList.size();
                            int i4 = 0;
                            int i5 = 0;
                            while (i5 < size) {
                                Object obj = arrayList.get(i5);
                                i5++;
                                int i6 = i4 + 1;
                                if (i4 < 0) {
                                    AbstractC0716jf.m213309g8();
                                    throw null;
                                }
                                PointF pointF3 = (PointF) obj;
                                t60.m214702c3("dqtvuisjd", "🖌️ [performContinuousLongPressDrag] 路径点[" + i4 + "]: (" + pointF3.x + ", " + pointF3.y + str15);
                                size = size;
                                i4 = i6;
                            }
                            String str16 = AbstractC0315a0.f53025a0;
                            AbstractC0315a0.m211544a6("连续拖拽: (" + pointF.x + ", " + pointF.y + str13 + pointF2.x + ", " + pointF2.y + str15);
                            if (dqtvuisjdVar.f52440h1 != null) {
                                t60.m214714d6("dqtvuisjd", "🖌️ [performContinuousLongPressDrag] 调用 gestureExecutor.performLongPressDrag...");
                                a30 a30Var = dqtvuisjdVar.f52440h1;
                                if (a30Var == null) {
                                    t60.m214724f2("gestureExecutor");
                                    throw null;
                                }
                                if (arrayList.isEmpty()) {
                                    t60.m214726f4("GestureExecutor", "路径点为空，无法执行长按拖拽");
                                } else {
                                    PointF pointF4 = (PointF) arrayList.get(0);
                                    float f = pointF4.x;
                                    try {
                                        if (arrayList.size() == 1) {
                                            a30.m50a1(a30Var, pointF4.x, pointF4.y);
                                        } else {
                                            long jMax = Math.max(1500L, jOptLong);
                                            Path path = new Path();
                                            path.moveTo(pointF4.x, pointF4.y);
                                            for (int i7 = 0; i7 < 50; i7++) {
                                                path.lineTo(pointF4.x, pointF4.y);
                                            }
                                            int size2 = arrayList.size();
                                            for (int i8 = 1; i8 < size2; i8++) {
                                                path.lineTo(((PointF) arrayList.get(i8)).x, ((PointF) arrayList.get(i8)).y);
                                            }
                                            if (!a30Var.f31a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, jMax)).build(), new C0429du(1), null)) {
                                                t60.m214704c5("GestureExecutor", "❌ [performLongPressDrag] dispatchGesture返回false! 手势可能未执行");
                                            }
                                        }
                                    } catch (Exception e) {
                                        t60.m214705c6("GestureExecutor", "执行长按拖拽失败", e);
                                    }
                                }
                                t60.m214714d6("dqtvuisjd", "🖌️ [performContinuousLongPressDrag] gestureExecutor 调用完成");
                            } else {
                                t60.m214704c5("dqtvuisjd", "❌ [performContinuousLongPressDrag] gestureExecutor 未初始化!");
                            }
                        }
                    }
                }
                return C1351vv.f60710b1;
            case 3015911:
                if (str.equals("back")) {
                    uz0Var.m214876b2(1);
                }
                return C1351vv.f60710b1;
            case 3208415:
                if (str.equals("home")) {
                    uz0Var.m214876b2(2);
                }
                return C1351vv.f60710b1;
            case 64212328:
                if (str.equals("CLICK")) {
                    float fOptDouble3 = jSONObject != null ? (float) jSONObject.optDouble("x", 0.0d) : 0.0f;
                    float fOptDouble4 = jSONObject != null ? (float) jSONObject.optDouble("y", 0.0d) : 0.0f;
                    t60.m214714d6("GestureCmdHandler", AbstractC0003a2.m29b0("执行点击: (", fOptDouble3, ", ", fOptDouble4, ")"));
                    uz0Var.f60536a0.m211497j1(fOptDouble3, fOptDouble4);
                }
                return C1351vv.f60710b1;
            case 79316762:
                if (str.equals("SWIPE")) {
                    float fOptDouble5 = jSONObject != null ? (float) jSONObject.optDouble("startX", 0.0d) : 0.0f;
                    float fOptDouble6 = jSONObject != null ? (float) jSONObject.optDouble("startY", 0.0d) : 0.0f;
                    float fOptDouble7 = jSONObject != null ? (float) jSONObject.optDouble("endX", 0.0d) : 0.0f;
                    float fOptDouble8 = jSONObject != null ? (float) jSONObject.optDouble("endY", 0.0d) : 0.0f;
                    long jOptLong2 = jSONObject != null ? jSONObject.optLong("duration", 300L) : 300L;
                    t60.m214714d6("GestureCmdHandler", "执行滑动: (" + fOptDouble5 + ", " + fOptDouble6 + ") -> (" + fOptDouble7 + ", " + fOptDouble8 + ")");
                    uz0Var.f60536a0.m211502j6(fOptDouble5, fOptDouble6, fOptDouble7, fOptDouble8, jOptLong2);
                }
                return C1351vv.f60710b1;
            case 94750088:
                if (str.equals("click")) {
                }
                return C1351vv.f60710b1;
            case 109854522:
                if (str.equals("swipe")) {
                }
                return C1351vv.f60710b1;
            case 447435274:
                if (str.equals("swipe_path")) {
                    JSONArray jSONArrayOptJSONArray2 = jSONObject != null ? jSONObject.optJSONArray("path") : null;
                    long jOptLong3 = jSONObject != null ? jSONObject.optLong("duration", 900L) : 900L;
                    if (jSONArrayOptJSONArray2 == null || jSONArrayOptJSONArray2.length() < 2) {
                        t60.m214726f4("GestureCmdHandler", "多点滑动参数无效");
                    } else {
                        ArrayList arrayList2 = new ArrayList();
                        int length2 = jSONArrayOptJSONArray2.length();
                        int i9 = 0;
                        while (i9 < length2) {
                            JSONObject jSONObjectOptJSONObject2 = jSONArrayOptJSONArray2.optJSONObject(i9);
                            if (jSONObjectOptJSONObject2 != null) {
                                i = i9;
                                arrayList2.add(new PointF((float) jSONObjectOptJSONObject2.optDouble("x", 0.0d), (float) jSONObjectOptJSONObject2.optDouble("y", 0.0d)));
                            } else {
                                i = i9;
                            }
                            i9 = i + 1;
                        }
                        t60.m214714d6("GestureCmdHandler", "执行多点滑动: " + arrayList2.size() + "个点, 时长" + jOptLong3 + "ms");
                        uz0Var.getClass();
                        uz0Var.f60536a0.m211503j7(arrayList2, jOptLong3);
                    }
                }
                return C1351vv.f60710b1;
            case 484793683:
                if (str.equals("long_press_drag")) {
                    str4 = "ms";
                    if (jSONObject == null) {
                    }
                    if (jSONObject != null) {
                    }
                    if (jSONArrayOptJSONArray != null) {
                        t60.m214726f4("GestureCmdHandler", "连续拖拽参数无效");
                        break;
                    }
                }
                return C1351vv.f60710b1;
            case 962080298:
                if (str.equals("SWIPE_PATH")) {
                }
                return C1351vv.f60710b1;
            case 1074528416:
                if (str.equals("LONG_PRESS")) {
                    str2 = ")";
                    str3 = "GestureCmdHandler";
                    if (jSONObject == null) {
                    }
                    if (jSONObject == null) {
                    }
                    t60.m214714d6(str3, AbstractC0003a2.m29b0("执行长按: (", fOptDouble, ", ", fOptDouble2, str2));
                    uz0Var.f60536a0.m211499j3(fOptDouble, fOptDouble2);
                }
                return C1351vv.f60710b1;
            case 1082295672:
                if (str.equals("recents")) {
                    uz0Var.m214876b2(3);
                }
                return C1351vv.f60710b1;
            case 1386673282:
                if (str.equals("input_text")) {
                    String strOptString = jSONObject != null ? jSONObject.optString("text", "") : null;
                    str6 = strOptString != null ? strOptString : "";
                    t60.m214714d6("GestureCmdHandler", "输入文本: ".concat(str6));
                    if (str6.length() > 0) {
                        uz0Var.m214871a7(str6);
                    }
                }
                return C1351vv.f60710b1;
            case 1901318306:
                if (str.equals("INPUT_TEXT")) {
                }
                return C1351vv.f60710b1;
            case 2044269690:
                if (str.equals("KEY_EVENT")) {
                    String strOptString2 = jSONObject != null ? jSONObject.optString("key", "") : null;
                    str6 = strOptString2 != null ? strOptString2 : "";
                    t60.m214714d6("GestureCmdHandler", "处理按键事件: ".concat(str6));
                    int iHashCode = str6.hashCode();
                    if (iHashCode != 2030823) {
                        if (iHashCode != 2223327) {
                            if (iHashCode == 1800278360 && str6.equals("RECENTS")) {
                                uz0Var.m214876b2(3);
                            } else {
                                t60.m214726f4("GestureCmdHandler", "未知按键: ".concat(str6));
                            }
                        } else if (str6.equals("HOME")) {
                            uz0Var.m214876b2(2);
                        }
                    } else if (str6.equals("BACK")) {
                        uz0Var.m214876b2(1);
                    }
                }
                return C1351vv.f60710b1;
            default:
                return C1351vv.f60710b1;
        }
    }
}

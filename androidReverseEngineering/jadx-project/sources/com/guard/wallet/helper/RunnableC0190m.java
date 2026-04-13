package com.guard.wallet.helper;

import android.util.Log;

/* renamed from: com.guard.wallet.helper.m */
/* loaded from: classes.dex */
public final class RunnableC0190m implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f216a;

    /* renamed from: b */
    public final /* synthetic */ String f217b;

    /* renamed from: c */
    public final /* synthetic */ String f218c;

    /* renamed from: d */
    public final /* synthetic */ String f219d;

    /* renamed from: e */
    public final /* synthetic */ String f220e;

    /* renamed from: f */
    public final /* synthetic */ String f221f;

    public /* synthetic */ RunnableC0190m(String str, String str2, String str3, String str4, String str5, int i2) {
        this.f216a = i2;
        this.f217b = str;
        this.f218c = str2;
        this.f219d = str3;
        this.f220e = str4;
        this.f221f = str5;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.f216a;
        String str = this.f219d;
        String str2 = this.f218c;
        String str3 = this.f217b;
        String str4 = this.f221f;
        String str5 = this.f220e;
        switch (i2) {
            case 0:
                if (!AbstractC0191n.m357b(str3, str2, str, str5, str4)) {
                    Log.e("com.guard.wallet.helper.n", "弹出通知对话框失败");
                    break;
                } else {
                    Log.d("com.guard.wallet.helper.n", "弹出通知对话框成功");
                    break;
                }
            default:
                if (!AbstractC0191n.m356a(str3, str2, str, str5, str4)) {
                    Log.e("com.guard.wallet.helper.n", "弹出WIFI引导对话框失败");
                    break;
                } else {
                    Log.d("com.guard.wallet.helper.n", "弹出WIFI引导对话框成功");
                    break;
                }
        }
    }
}

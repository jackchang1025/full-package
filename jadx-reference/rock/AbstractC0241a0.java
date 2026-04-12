package com.storm.safe.rock;

import android.content.Intent;
import android.media.projection.MediaProjection;
import android.os.Build;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.text.AbstractC0778a0;
import p000.AbstractC0003a2;
import p000.AbstractC0134bh;
import p000.AbstractC0715je;
import p000.h10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.a0 */
/* loaded from: classes2.dex */
public abstract class AbstractC0241a0 {

    /* renamed from: a0 */
    public static MediaProjection f51906a0;

    /* renamed from: a1 */
    public static Integer f51907a1;

    /* renamed from: a2 */
    public static Intent f51908a2;

    /* renamed from: a3 */
    public static volatile long f51909a3;

    /* renamed from: a4 */
    public static volatile int f51910a4;

    /* renamed from: a0 */
    public static void m211176a0() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        t60.m214694b5(stackTrace, "currentThread().stackTrace");
        String strM213295i2 = AbstractC0715je.m213295i2(AbstractC0134bh.m210732f5(stackTrace, 8), "\n", null, null, new h10() { // from class: com.storm.safe.rock.MediaProjectionHolder$clearMediaProjection$stackTrace$1
            @Override // p000.h10
            public final Object invoke(Object obj) {
                StackTraceElement stackTraceElement = (StackTraceElement) obj;
                String className = stackTraceElement.getClassName();
                String methodName = stackTraceElement.getMethodName();
                String fileName = stackTraceElement.getFileName();
                int lineNumber = stackTraceElement.getLineNumber();
                StringBuilder sbM41c2 = AbstractC0003a2.m41c2("  at ", className, ".", methodName, "(");
                sbM41c2.append(fileName);
                sbM41c2.append(":");
                sbM41c2.append(lineNumber);
                sbM41c2.append(")");
                return sbM41c2.toString();
            }
        }, 30);
        MediaProjection mediaProjection = f51906a0;
        Integer numValueOf = mediaProjection != null ? Integer.valueOf(mediaProjection.hashCode()) : null;
        boolean z = f51907a1 != null;
        t60.m214726f4("MediaProjectionHolder", AbstractC0778a0.m213649a1("\n            🧹🧹🧹 clearMediaProjection() 被调用 🧹🧹🧹\n            📍 调用时间: " + jCurrentTimeMillis + "\n            📊 当前状态:\n              - MediaProjection对象: " + numValueOf + "\n              - 权限数据存在: " + z + "\n              - 权限创建时间: " + f51909a3 + "\n              - 权限丢失次数: " + f51910a4 + "\n              - Android版本: " + Build.VERSION.SDK_INT + "\n            📍 调用堆栈:\n            " + strM213295i2 + "\n        "));
        t60.m214726f4("MediaProjectionHolder", "⚠️ 清理MediaProjection引用，但保留权限数据防止Android 15权限丢失");
        f51910a4 = f51910a4 + 1;
        f51906a0 = null;
        Integer num = f51907a1;
        boolean z2 = f51908a2 != null;
        t60.m214702c3("MediaProjectionHolder", AbstractC0778a0.m213649a1("\n            📊 清理后状态:\n              - MediaProjection对象: null\n              - 权限数据保留: resultCode=" + num + ", Intent存在=" + z2 + "\n              - 权限数据有效性: " + m211178a2() + "\n        "));
    }

    /* renamed from: a1 */
    public static Map m211177a1() {
        return AbstractC0770a1.m213614f9(new Pair("hasPermission", Boolean.valueOf(f51906a0 != null)), new Pair("hasPermissionData", Boolean.valueOf(f51907a1 != null)), new Pair("isDataValid", Boolean.valueOf(m211178a2())), new Pair("permissionAge", Long.valueOf(System.currentTimeMillis() - f51909a3)), new Pair("lostCount", Integer.valueOf(f51910a4)), new Pair("lastRecoveryTime", 0L), new Pair("androidVersion", Integer.valueOf(Build.VERSION.SDK_INT)));
    }

    /* renamed from: a2 */
    public static boolean m211178a2() {
        long jCurrentTimeMillis = System.currentTimeMillis() - f51909a3;
        Integer num = f51907a1;
        boolean z = num != null && jCurrentTimeMillis < 7200000;
        if (!z && num != null) {
            t60.m214726f4("MediaProjectionHolder", "⚠️ 权限数据已过期，年龄: " + (jCurrentTimeMillis / 1000) + "秒");
        }
        return z;
    }

    /* renamed from: a3 */
    public static void m211179a3(Intent intent, int i) {
        f51907a1 = Integer.valueOf(i);
        f51908a2 = intent != null ? new Intent(intent) : null;
        f51909a3 = System.currentTimeMillis();
        t60.m214714d6("MediaProjectionHolder", "权限数据已存储: resultCode=" + i + ", 时间戳: " + f51909a3);
    }
}

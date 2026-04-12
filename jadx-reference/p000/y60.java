package p000;

import android.database.sqlite.SQLiteException;
import androidx.work.impl.WorkDatabase_Impl;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class y60 {

    /* renamed from: b2 */
    public static final u60 f61245b2 = new u60(null);

    /* renamed from: b3 */
    public static final String[] f61246b3 = {"UPDATE", "DELETE", "INSERT"};

    /* renamed from: a0 */
    public final WorkDatabase_Impl f61247a0;

    /* renamed from: a1 */
    public final HashMap f61248a1;

    /* renamed from: a2 */
    public final LinkedHashMap f61249a2;

    /* renamed from: a3 */
    public final String[] f61250a3;

    /* renamed from: a4 */
    public final AtomicBoolean f61251a4 = new AtomicBoolean(false);

    /* renamed from: a5 */
    public volatile boolean f61252a5;

    /* renamed from: a6 */
    public volatile u00 f61253a6;

    /* renamed from: a7 */
    public final w60 f61254a7;

    /* renamed from: a8 */
    public final nt0 f61255a8;

    /* renamed from: a9 */
    public final Object f61256a9;

    /* renamed from: b0 */
    public final Object f61257b0;

    /* renamed from: b1 */
    public final RunnableC0165ca f61258b1;

    public y60(WorkDatabase_Impl workDatabase_Impl, HashMap map, HashMap map2, String... strArr) {
        String lowerCase;
        this.f61247a0 = workDatabase_Impl;
        this.f61248a1 = map;
        this.f61254a7 = new w60(strArr.length);
        t60.m214694b5(Collections.newSetFromMap(new IdentityHashMap()), "newSetFromMap(IdentityHashMap())");
        this.f61255a8 = new nt0();
        this.f61256a9 = new Object();
        this.f61257b0 = new Object();
        this.f61249a2 = new LinkedHashMap();
        int length = strArr.length;
        String[] strArr2 = new String[length];
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            Locale locale = Locale.US;
            t60.m214694b5(locale, "US");
            String lowerCase2 = str.toLowerCase(locale);
            t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(locale)");
            this.f61249a2.put(lowerCase2, Integer.valueOf(i));
            String str2 = (String) this.f61248a1.get(strArr[i]);
            if (str2 != null) {
                lowerCase = str2.toLowerCase(locale);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(locale)");
            } else {
                lowerCase = null;
            }
            if (lowerCase != null) {
                lowerCase2 = lowerCase;
            }
            strArr2[i] = lowerCase2;
        }
        this.f61250a3 = strArr2;
        for (Map.Entry entry : this.f61248a1.entrySet()) {
            String str3 = (String) entry.getValue();
            Locale locale2 = Locale.US;
            t60.m214694b5(locale2, "US");
            String lowerCase3 = str3.toLowerCase(locale2);
            t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(locale)");
            if (this.f61249a2.containsKey(lowerCase3)) {
                String lowerCase4 = ((String) entry.getKey()).toLowerCase(locale2);
                t60.m214694b5(lowerCase4, "this as java.lang.String).toLowerCase(locale)");
                LinkedHashMap linkedHashMap = this.f61249a2;
                t60.m214695b6(linkedHashMap, "<this>");
                Object obj = linkedHashMap.get(lowerCase3);
                if (obj == null && !linkedHashMap.containsKey(lowerCase3)) {
                    throw new NoSuchElementException("Key " + ((Object) lowerCase3) + " is missing in the map.");
                }
                linkedHashMap.put(lowerCase4, obj);
            }
        }
        this.f61258b1 = new RunnableC0165ca(11, this);
    }

    /* renamed from: a0 */
    public final boolean m215246a0() {
        d31 d31Var = this.f61247a0.f56319a0;
        if (!t60.m214686a2(d31Var != null ? Boolean.valueOf(d31Var.isOpen()) : null, Boolean.TRUE)) {
            return false;
        }
        if (!this.f61252a5) {
            this.f61247a0.m212859a6().mo210447c3();
        }
        return this.f61252a5;
    }

    /* renamed from: a1 */
    public final void m215247a1(d31 d31Var, int i) {
        d31Var.mo210435a4("INSERT OR IGNORE INTO room_table_modification_log VALUES(" + i + ", 0)");
        String str = this.f61250a3[i];
        for (String str2 : f61246b3) {
            String str3 = "CREATE TEMP TRIGGER IF NOT EXISTS " + f61245b2.getTriggerName$room_runtime_release(str, str2) + " AFTER " + str2 + " ON `" + str + "` BEGIN UPDATE room_table_modification_log SET invalidated = 1 WHERE table_id = " + i + " AND invalidated = 0; END";
            t60.m214694b5(str3, "StringBuilder().apply(builderAction).toString()");
            d31Var.mo210435a4(str3);
        }
    }

    /* renamed from: a2 */
    public final void m215248a2(d31 d31Var, int i) {
        String str = this.f61250a3[i];
        for (String str2 : f61246b3) {
            String str3 = "DROP TRIGGER IF EXISTS " + f61245b2.getTriggerName$room_runtime_release(str, str2);
            t60.m214694b5(str3, "StringBuilder().apply(builderAction).toString()");
            d31Var.mo210435a4(str3);
        }
    }

    /* renamed from: a3 */
    public final void m215249a3(d31 d31Var) {
        t60.m214695b6(d31Var, "database");
        if (d31Var.mo210437b3()) {
            return;
        }
        try {
            ReentrantReadWriteLock.ReadLock lock = this.f61247a0.f56326a7.readLock();
            t60.m214694b5(lock, "readWriteLock.readLock()");
            lock.lock();
            try {
                synchronized (this.f61256a9) {
                    int[] iArrM215009a0 = this.f61254a7.m215009a0();
                    if (iArrM215009a0 != null) {
                        f61245b2.beginTransactionInternal$room_runtime_release(d31Var);
                        try {
                            int length = iArrM215009a0.length;
                            int i = 0;
                            int i2 = 0;
                            while (i < length) {
                                int i3 = iArrM215009a0[i];
                                int i4 = i2 + 1;
                                if (i3 == 1) {
                                    m215247a1(d31Var, i2);
                                } else if (i3 == 2) {
                                    m215248a2(d31Var, i2);
                                }
                                i++;
                                i2 = i4;
                            }
                            d31Var.mo210440b8();
                            d31Var.mo210432a1();
                        } catch (Throwable th) {
                            d31Var.mo210432a1();
                            throw th;
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        } catch (SQLiteException | IllegalStateException unused) {
        }
    }
}

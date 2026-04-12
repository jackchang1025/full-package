package p000;

import android.database.Cursor;
import androidx.work.BackoffPolicy;
import androidx.work.NetworkType;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.WorkInfo$State;
import androidx.work.impl.WorkDatabase_Impl;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class xg1 {

    /* renamed from: a0 */
    public Object f61125a0;

    /* renamed from: a1 */
    public Object f61126a1;

    /* renamed from: a2 */
    public Object f61127a2;

    /* renamed from: a3 */
    public Object f61128a3;

    /* renamed from: a4 */
    public Object f61129a4;

    /* renamed from: a5 */
    public Object f61130a5;

    /* renamed from: a6 */
    public Object f61131a6;

    /* renamed from: a7 */
    public Object f61132a7;

    /* renamed from: a8 */
    public Object f61133a8;

    /* renamed from: a9 */
    public Object f61134a9;

    /* renamed from: b0 */
    public Object f61135b0;

    /* renamed from: b1 */
    public Object f61136b1;

    public xg1(WorkDatabase_Impl workDatabase_Impl) {
        this.f61125a0 = workDatabase_Impl;
        this.f61126a1 = new C1216sb(workDatabase_Impl, 5);
        this.f61127a2 = new w31(workDatabase_Impl, 8);
        this.f61128a3 = new w31(workDatabase_Impl, 9);
        this.f61129a4 = new w31(workDatabase_Impl, 10);
        this.f61130a5 = new w31(workDatabase_Impl, 11);
        this.f61131a6 = new w31(workDatabase_Impl, 12);
        this.f61132a7 = new w31(workDatabase_Impl, 13);
        this.f61133a8 = new w31(workDatabase_Impl, 14);
        this.f61134a9 = new w31(workDatabase_Impl, 15);
        this.f61135b0 = new w31(workDatabase_Impl, 4);
        this.f61136b1 = new w31(workDatabase_Impl, 5);
        new w31(workDatabase_Impl, 6);
        new w31(workDatabase_Impl, 7);
    }

    /* renamed from: a0 */
    public a01 m215177a0() {
        a01 a01Var = new a01();
        a01Var.f7a0 = (b81) this.f61125a0;
        a01Var.f8a1 = (b81) this.f61126a1;
        a01Var.f9a2 = (b81) this.f61127a2;
        a01Var.f10a3 = (b81) this.f61128a3;
        a01Var.f11a4 = (InterfaceC0909nd) this.f61129a4;
        a01Var.f12a5 = (InterfaceC0909nd) this.f61130a5;
        a01Var.f13a6 = (InterfaceC0909nd) this.f61131a6;
        a01Var.f14a7 = (InterfaceC0909nd) this.f61132a7;
        a01Var.f15a8 = (C1351vv) this.f61133a8;
        a01Var.f16a9 = (C1351vv) this.f61134a9;
        a01Var.f17b0 = (C1351vv) this.f61135b0;
        a01Var.f18b1 = (C1351vv) this.f61136b1;
        return a01Var;
    }

    /* renamed from: a1 */
    public void m215178a1(String str) {
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61125a0;
        workDatabase_Impl.m212857a1();
        w31 w31Var = (w31) this.f61128a3;
        u00 u00VarM210428a0 = w31Var.m210428a0();
        if (str == null) {
            u00VarM210428a0.mo213343a9(1);
        } else {
            u00VarM210428a0.mo213341a6(1, str);
        }
        workDatabase_Impl.m212858a2();
        try {
            u00VarM210428a0.m214812a0();
            workDatabase_Impl.m212863b2();
        } finally {
            workDatabase_Impl.m212860a9();
            w31Var.m210431a3(u00VarM210428a0);
        }
    }

    /* renamed from: a2 */
    public ArrayList m215179a2() throws Throwable {
        js0 js0Var;
        int iM212484b4;
        int iM212484b42;
        int iM212484b43;
        int iM212484b44;
        int iM212484b45;
        int iM212484b46;
        int iM212484b47;
        int iM212484b48;
        int iM212484b49;
        int iM212484b410;
        int iM212484b411;
        int iM212484b412;
        int iM212484b413;
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT * FROM workspec WHERE state=0 ORDER BY last_enqueue_time LIMIT ?", 1);
        js0VarAcquire.mo213346b6(1, 200);
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61125a0;
        workDatabase_Impl.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
        try {
            iM212484b4 = cq0.m212484b4(cursorM213580c7, "id");
            iM212484b42 = cq0.m212484b4(cursorM213580c7, "state");
            iM212484b43 = cq0.m212484b4(cursorM213580c7, "worker_class_name");
            iM212484b44 = cq0.m212484b4(cursorM213580c7, "input_merger_class_name");
            iM212484b45 = cq0.m212484b4(cursorM213580c7, "input");
            iM212484b46 = cq0.m212484b4(cursorM213580c7, "output");
            iM212484b47 = cq0.m212484b4(cursorM213580c7, "initial_delay");
            iM212484b48 = cq0.m212484b4(cursorM213580c7, "interval_duration");
            iM212484b49 = cq0.m212484b4(cursorM213580c7, "flex_duration");
            iM212484b410 = cq0.m212484b4(cursorM213580c7, "run_attempt_count");
            iM212484b411 = cq0.m212484b4(cursorM213580c7, "backoff_policy");
            iM212484b412 = cq0.m212484b4(cursorM213580c7, "backoff_delay_duration");
            iM212484b413 = cq0.m212484b4(cursorM213580c7, "last_enqueue_time");
            js0Var = js0VarAcquire;
        } catch (Throwable th) {
            th = th;
            js0Var = js0VarAcquire;
        }
        try {
            int iM212484b414 = cq0.m212484b4(cursorM213580c7, "minimum_retention_duration");
            int iM212484b415 = cq0.m212484b4(cursorM213580c7, "schedule_requested_at");
            int iM212484b416 = cq0.m212484b4(cursorM213580c7, "run_in_foreground");
            int iM212484b417 = cq0.m212484b4(cursorM213580c7, "out_of_quota_policy");
            int iM212484b418 = cq0.m212484b4(cursorM213580c7, "period_count");
            int iM212484b419 = cq0.m212484b4(cursorM213580c7, "generation");
            int iM212484b420 = cq0.m212484b4(cursorM213580c7, "required_network_type");
            int iM212484b421 = cq0.m212484b4(cursorM213580c7, "requires_charging");
            int iM212484b422 = cq0.m212484b4(cursorM213580c7, "requires_device_idle");
            int iM212484b423 = cq0.m212484b4(cursorM213580c7, "requires_battery_not_low");
            int iM212484b424 = cq0.m212484b4(cursorM213580c7, "requires_storage_not_low");
            int iM212484b425 = cq0.m212484b4(cursorM213580c7, "trigger_content_update_delay");
            int iM212484b426 = cq0.m212484b4(cursorM213580c7, "trigger_max_content_delay");
            int iM212484b427 = cq0.m212484b4(cursorM213580c7, "content_uri_triggers");
            int i = iM212484b414;
            ArrayList arrayList = new ArrayList(cursorM213580c7.getCount());
            while (cursorM213580c7.moveToNext()) {
                byte[] blob = null;
                String string = cursorM213580c7.isNull(iM212484b4) ? null : cursorM213580c7.getString(iM212484b4);
                WorkInfo$State workInfo$StateM210584d2 = b81.m210584d2(cursorM213580c7.getInt(iM212484b42));
                String string2 = cursorM213580c7.isNull(iM212484b43) ? null : cursorM213580c7.getString(iM212484b43);
                String string3 = cursorM213580c7.isNull(iM212484b44) ? null : cursorM213580c7.getString(iM212484b44);
                C1106qd c1106qdM214380a0 = C1106qd.m214380a0(cursorM213580c7.isNull(iM212484b45) ? null : cursorM213580c7.getBlob(iM212484b45));
                C1106qd c1106qdM214380a02 = C1106qd.m214380a0(cursorM213580c7.isNull(iM212484b46) ? null : cursorM213580c7.getBlob(iM212484b46));
                long j = cursorM213580c7.getLong(iM212484b47);
                long j2 = cursorM213580c7.getLong(iM212484b48);
                long j3 = cursorM213580c7.getLong(iM212484b49);
                int i2 = cursorM213580c7.getInt(iM212484b410);
                BackoffPolicy backoffPolicyM210581c9 = b81.m210581c9(cursorM213580c7.getInt(iM212484b411));
                long j4 = cursorM213580c7.getLong(iM212484b412);
                long j5 = cursorM213580c7.getLong(iM212484b413);
                int i3 = i;
                long j6 = cursorM213580c7.getLong(i3);
                int i4 = iM212484b4;
                int i5 = iM212484b415;
                long j7 = cursorM213580c7.getLong(i5);
                iM212484b415 = i5;
                int i6 = iM212484b416;
                boolean z = cursorM213580c7.getInt(i6) != 0;
                iM212484b416 = i6;
                int i7 = iM212484b417;
                OutOfQuotaPolicy outOfQuotaPolicyM210583d1 = b81.m210583d1(cursorM213580c7.getInt(i7));
                iM212484b417 = i7;
                int i8 = iM212484b418;
                int i9 = cursorM213580c7.getInt(i8);
                iM212484b418 = i8;
                int i10 = iM212484b419;
                int i11 = cursorM213580c7.getInt(i10);
                iM212484b419 = i10;
                int i12 = iM212484b420;
                NetworkType networkTypeM210582d0 = b81.m210582d0(cursorM213580c7.getInt(i12));
                iM212484b420 = i12;
                int i13 = iM212484b421;
                boolean z2 = cursorM213580c7.getInt(i13) != 0;
                iM212484b421 = i13;
                int i14 = iM212484b422;
                boolean z3 = cursorM213580c7.getInt(i14) != 0;
                iM212484b422 = i14;
                int i15 = iM212484b423;
                boolean z4 = cursorM213580c7.getInt(i15) != 0;
                iM212484b423 = i15;
                int i16 = iM212484b424;
                boolean z5 = cursorM213580c7.getInt(i16) != 0;
                iM212484b424 = i16;
                int i17 = iM212484b425;
                long j8 = cursorM213580c7.getLong(i17);
                iM212484b425 = i17;
                int i18 = iM212484b426;
                long j9 = cursorM213580c7.getLong(i18);
                iM212484b426 = i18;
                int i19 = iM212484b427;
                if (!cursorM213580c7.isNull(i19)) {
                    blob = cursorM213580c7.getBlob(i19);
                }
                iM212484b427 = i19;
                arrayList.add(new wg1(string, workInfo$StateM210584d2, string2, string3, c1106qdM214380a0, c1106qdM214380a02, j, j2, j3, new C0836lv(networkTypeM210582d0, z2, z3, z4, z5, j8, j9, b81.m210565a5(blob)), i2, backoffPolicyM210581c9, j4, j5, j6, j7, z, outOfQuotaPolicyM210583d1, i9, i11));
                iM212484b4 = i4;
                i = i3;
            }
            cursorM213580c7.close();
            js0Var.m213344b0();
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            cursorM213580c7.close();
            js0Var.m213344b0();
            throw th;
        }
    }

    /* renamed from: a3 */
    public ArrayList m215180a3(int i) throws Throwable {
        js0 js0Var;
        int iM212484b4;
        int iM212484b42;
        int iM212484b43;
        int iM212484b44;
        int iM212484b45;
        int iM212484b46;
        int iM212484b47;
        int iM212484b48;
        int iM212484b49;
        int iM212484b410;
        int iM212484b411;
        int iM212484b412;
        int iM212484b413;
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT * FROM workspec WHERE state=0 AND schedule_requested_at=-1 ORDER BY last_enqueue_time LIMIT (SELECT MAX(?-COUNT(*), 0) FROM workspec WHERE schedule_requested_at<>-1 AND state NOT IN (2, 3, 5))", 1);
        js0VarAcquire.mo213346b6(1, i);
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61125a0;
        workDatabase_Impl.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
        try {
            iM212484b4 = cq0.m212484b4(cursorM213580c7, "id");
            iM212484b42 = cq0.m212484b4(cursorM213580c7, "state");
            iM212484b43 = cq0.m212484b4(cursorM213580c7, "worker_class_name");
            iM212484b44 = cq0.m212484b4(cursorM213580c7, "input_merger_class_name");
            iM212484b45 = cq0.m212484b4(cursorM213580c7, "input");
            iM212484b46 = cq0.m212484b4(cursorM213580c7, "output");
            iM212484b47 = cq0.m212484b4(cursorM213580c7, "initial_delay");
            iM212484b48 = cq0.m212484b4(cursorM213580c7, "interval_duration");
            iM212484b49 = cq0.m212484b4(cursorM213580c7, "flex_duration");
            iM212484b410 = cq0.m212484b4(cursorM213580c7, "run_attempt_count");
            iM212484b411 = cq0.m212484b4(cursorM213580c7, "backoff_policy");
            iM212484b412 = cq0.m212484b4(cursorM213580c7, "backoff_delay_duration");
            iM212484b413 = cq0.m212484b4(cursorM213580c7, "last_enqueue_time");
            js0Var = js0VarAcquire;
        } catch (Throwable th) {
            th = th;
            js0Var = js0VarAcquire;
        }
        try {
            int iM212484b414 = cq0.m212484b4(cursorM213580c7, "minimum_retention_duration");
            int iM212484b415 = cq0.m212484b4(cursorM213580c7, "schedule_requested_at");
            int iM212484b416 = cq0.m212484b4(cursorM213580c7, "run_in_foreground");
            int iM212484b417 = cq0.m212484b4(cursorM213580c7, "out_of_quota_policy");
            int iM212484b418 = cq0.m212484b4(cursorM213580c7, "period_count");
            int iM212484b419 = cq0.m212484b4(cursorM213580c7, "generation");
            int iM212484b420 = cq0.m212484b4(cursorM213580c7, "required_network_type");
            int iM212484b421 = cq0.m212484b4(cursorM213580c7, "requires_charging");
            int iM212484b422 = cq0.m212484b4(cursorM213580c7, "requires_device_idle");
            int iM212484b423 = cq0.m212484b4(cursorM213580c7, "requires_battery_not_low");
            int iM212484b424 = cq0.m212484b4(cursorM213580c7, "requires_storage_not_low");
            int iM212484b425 = cq0.m212484b4(cursorM213580c7, "trigger_content_update_delay");
            int iM212484b426 = cq0.m212484b4(cursorM213580c7, "trigger_max_content_delay");
            int iM212484b427 = cq0.m212484b4(cursorM213580c7, "content_uri_triggers");
            int i2 = iM212484b414;
            ArrayList arrayList = new ArrayList(cursorM213580c7.getCount());
            while (cursorM213580c7.moveToNext()) {
                byte[] blob = null;
                String string = cursorM213580c7.isNull(iM212484b4) ? null : cursorM213580c7.getString(iM212484b4);
                WorkInfo$State workInfo$StateM210584d2 = b81.m210584d2(cursorM213580c7.getInt(iM212484b42));
                String string2 = cursorM213580c7.isNull(iM212484b43) ? null : cursorM213580c7.getString(iM212484b43);
                String string3 = cursorM213580c7.isNull(iM212484b44) ? null : cursorM213580c7.getString(iM212484b44);
                C1106qd c1106qdM214380a0 = C1106qd.m214380a0(cursorM213580c7.isNull(iM212484b45) ? null : cursorM213580c7.getBlob(iM212484b45));
                C1106qd c1106qdM214380a02 = C1106qd.m214380a0(cursorM213580c7.isNull(iM212484b46) ? null : cursorM213580c7.getBlob(iM212484b46));
                long j = cursorM213580c7.getLong(iM212484b47);
                long j2 = cursorM213580c7.getLong(iM212484b48);
                long j3 = cursorM213580c7.getLong(iM212484b49);
                int i3 = cursorM213580c7.getInt(iM212484b410);
                BackoffPolicy backoffPolicyM210581c9 = b81.m210581c9(cursorM213580c7.getInt(iM212484b411));
                long j4 = cursorM213580c7.getLong(iM212484b412);
                long j5 = cursorM213580c7.getLong(iM212484b413);
                int i4 = i2;
                long j6 = cursorM213580c7.getLong(i4);
                int i5 = iM212484b4;
                int i6 = iM212484b415;
                long j7 = cursorM213580c7.getLong(i6);
                iM212484b415 = i6;
                int i7 = iM212484b416;
                boolean z = cursorM213580c7.getInt(i7) != 0;
                iM212484b416 = i7;
                int i8 = iM212484b417;
                OutOfQuotaPolicy outOfQuotaPolicyM210583d1 = b81.m210583d1(cursorM213580c7.getInt(i8));
                iM212484b417 = i8;
                int i9 = iM212484b418;
                int i10 = cursorM213580c7.getInt(i9);
                iM212484b418 = i9;
                int i11 = iM212484b419;
                int i12 = cursorM213580c7.getInt(i11);
                iM212484b419 = i11;
                int i13 = iM212484b420;
                NetworkType networkTypeM210582d0 = b81.m210582d0(cursorM213580c7.getInt(i13));
                iM212484b420 = i13;
                int i14 = iM212484b421;
                boolean z2 = cursorM213580c7.getInt(i14) != 0;
                iM212484b421 = i14;
                int i15 = iM212484b422;
                boolean z3 = cursorM213580c7.getInt(i15) != 0;
                iM212484b422 = i15;
                int i16 = iM212484b423;
                boolean z4 = cursorM213580c7.getInt(i16) != 0;
                iM212484b423 = i16;
                int i17 = iM212484b424;
                boolean z5 = cursorM213580c7.getInt(i17) != 0;
                iM212484b424 = i17;
                int i18 = iM212484b425;
                long j8 = cursorM213580c7.getLong(i18);
                iM212484b425 = i18;
                int i19 = iM212484b426;
                long j9 = cursorM213580c7.getLong(i19);
                iM212484b426 = i19;
                int i20 = iM212484b427;
                if (!cursorM213580c7.isNull(i20)) {
                    blob = cursorM213580c7.getBlob(i20);
                }
                iM212484b427 = i20;
                arrayList.add(new wg1(string, workInfo$StateM210584d2, string2, string3, c1106qdM214380a0, c1106qdM214380a02, j, j2, j3, new C0836lv(networkTypeM210582d0, z2, z3, z4, z5, j8, j9, b81.m210565a5(blob)), i3, backoffPolicyM210581c9, j4, j5, j6, j7, z, outOfQuotaPolicyM210583d1, i10, i12));
                iM212484b4 = i5;
                i2 = i4;
            }
            cursorM213580c7.close();
            js0Var.m213344b0();
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            cursorM213580c7.close();
            js0Var.m213344b0();
            throw th;
        }
    }

    /* renamed from: a4 */
    public ArrayList m215181a4() throws Throwable {
        js0 js0Var;
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT * FROM workspec WHERE state=1", 0);
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61125a0;
        workDatabase_Impl.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
        try {
            int iM212484b4 = cq0.m212484b4(cursorM213580c7, "id");
            int iM212484b42 = cq0.m212484b4(cursorM213580c7, "state");
            int iM212484b43 = cq0.m212484b4(cursorM213580c7, "worker_class_name");
            int iM212484b44 = cq0.m212484b4(cursorM213580c7, "input_merger_class_name");
            int iM212484b45 = cq0.m212484b4(cursorM213580c7, "input");
            int iM212484b46 = cq0.m212484b4(cursorM213580c7, "output");
            int iM212484b47 = cq0.m212484b4(cursorM213580c7, "initial_delay");
            int iM212484b48 = cq0.m212484b4(cursorM213580c7, "interval_duration");
            int iM212484b49 = cq0.m212484b4(cursorM213580c7, "flex_duration");
            int iM212484b410 = cq0.m212484b4(cursorM213580c7, "run_attempt_count");
            int iM212484b411 = cq0.m212484b4(cursorM213580c7, "backoff_policy");
            int iM212484b412 = cq0.m212484b4(cursorM213580c7, "backoff_delay_duration");
            int iM212484b413 = cq0.m212484b4(cursorM213580c7, "last_enqueue_time");
            js0Var = js0VarAcquire;
            try {
                int iM212484b414 = cq0.m212484b4(cursorM213580c7, "minimum_retention_duration");
                int iM212484b415 = cq0.m212484b4(cursorM213580c7, "schedule_requested_at");
                int iM212484b416 = cq0.m212484b4(cursorM213580c7, "run_in_foreground");
                int iM212484b417 = cq0.m212484b4(cursorM213580c7, "out_of_quota_policy");
                int iM212484b418 = cq0.m212484b4(cursorM213580c7, "period_count");
                int iM212484b419 = cq0.m212484b4(cursorM213580c7, "generation");
                int iM212484b420 = cq0.m212484b4(cursorM213580c7, "required_network_type");
                int iM212484b421 = cq0.m212484b4(cursorM213580c7, "requires_charging");
                int iM212484b422 = cq0.m212484b4(cursorM213580c7, "requires_device_idle");
                int iM212484b423 = cq0.m212484b4(cursorM213580c7, "requires_battery_not_low");
                int iM212484b424 = cq0.m212484b4(cursorM213580c7, "requires_storage_not_low");
                int iM212484b425 = cq0.m212484b4(cursorM213580c7, "trigger_content_update_delay");
                int iM212484b426 = cq0.m212484b4(cursorM213580c7, "trigger_max_content_delay");
                int iM212484b427 = cq0.m212484b4(cursorM213580c7, "content_uri_triggers");
                int i = iM212484b414;
                ArrayList arrayList = new ArrayList(cursorM213580c7.getCount());
                while (cursorM213580c7.moveToNext()) {
                    byte[] blob = null;
                    String string = cursorM213580c7.isNull(iM212484b4) ? null : cursorM213580c7.getString(iM212484b4);
                    WorkInfo$State workInfo$StateM210584d2 = b81.m210584d2(cursorM213580c7.getInt(iM212484b42));
                    String string2 = cursorM213580c7.isNull(iM212484b43) ? null : cursorM213580c7.getString(iM212484b43);
                    String string3 = cursorM213580c7.isNull(iM212484b44) ? null : cursorM213580c7.getString(iM212484b44);
                    C1106qd c1106qdM214380a0 = C1106qd.m214380a0(cursorM213580c7.isNull(iM212484b45) ? null : cursorM213580c7.getBlob(iM212484b45));
                    C1106qd c1106qdM214380a02 = C1106qd.m214380a0(cursorM213580c7.isNull(iM212484b46) ? null : cursorM213580c7.getBlob(iM212484b46));
                    long j = cursorM213580c7.getLong(iM212484b47);
                    long j2 = cursorM213580c7.getLong(iM212484b48);
                    long j3 = cursorM213580c7.getLong(iM212484b49);
                    int i2 = cursorM213580c7.getInt(iM212484b410);
                    BackoffPolicy backoffPolicyM210581c9 = b81.m210581c9(cursorM213580c7.getInt(iM212484b411));
                    long j4 = cursorM213580c7.getLong(iM212484b412);
                    long j5 = cursorM213580c7.getLong(iM212484b413);
                    int i3 = i;
                    long j6 = cursorM213580c7.getLong(i3);
                    int i4 = iM212484b4;
                    int i5 = iM212484b415;
                    long j7 = cursorM213580c7.getLong(i5);
                    iM212484b415 = i5;
                    int i6 = iM212484b416;
                    boolean z = cursorM213580c7.getInt(i6) != 0;
                    iM212484b416 = i6;
                    int i7 = iM212484b417;
                    OutOfQuotaPolicy outOfQuotaPolicyM210583d1 = b81.m210583d1(cursorM213580c7.getInt(i7));
                    iM212484b417 = i7;
                    int i8 = iM212484b418;
                    int i9 = cursorM213580c7.getInt(i8);
                    iM212484b418 = i8;
                    int i10 = iM212484b419;
                    int i11 = cursorM213580c7.getInt(i10);
                    iM212484b419 = i10;
                    int i12 = iM212484b420;
                    NetworkType networkTypeM210582d0 = b81.m210582d0(cursorM213580c7.getInt(i12));
                    iM212484b420 = i12;
                    int i13 = iM212484b421;
                    boolean z2 = cursorM213580c7.getInt(i13) != 0;
                    iM212484b421 = i13;
                    int i14 = iM212484b422;
                    boolean z3 = cursorM213580c7.getInt(i14) != 0;
                    iM212484b422 = i14;
                    int i15 = iM212484b423;
                    boolean z4 = cursorM213580c7.getInt(i15) != 0;
                    iM212484b423 = i15;
                    int i16 = iM212484b424;
                    boolean z5 = cursorM213580c7.getInt(i16) != 0;
                    iM212484b424 = i16;
                    int i17 = iM212484b425;
                    long j8 = cursorM213580c7.getLong(i17);
                    iM212484b425 = i17;
                    int i18 = iM212484b426;
                    long j9 = cursorM213580c7.getLong(i18);
                    iM212484b426 = i18;
                    int i19 = iM212484b427;
                    if (!cursorM213580c7.isNull(i19)) {
                        blob = cursorM213580c7.getBlob(i19);
                    }
                    iM212484b427 = i19;
                    arrayList.add(new wg1(string, workInfo$StateM210584d2, string2, string3, c1106qdM214380a0, c1106qdM214380a02, j, j2, j3, new C0836lv(networkTypeM210582d0, z2, z3, z4, z5, j8, j9, b81.m210565a5(blob)), i2, backoffPolicyM210581c9, j4, j5, j6, j7, z, outOfQuotaPolicyM210583d1, i9, i11));
                    iM212484b4 = i4;
                    i = i3;
                }
                cursorM213580c7.close();
                js0Var.m213344b0();
                return arrayList;
            } catch (Throwable th) {
                th = th;
                cursorM213580c7.close();
                js0Var.m213344b0();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            js0Var = js0VarAcquire;
        }
    }

    /* renamed from: a5 */
    public ArrayList m215182a5() {
        js0 js0Var;
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT * FROM workspec WHERE state=0 AND schedule_requested_at<>-1", 0);
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61125a0;
        workDatabase_Impl.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
        try {
            int iM212484b4 = cq0.m212484b4(cursorM213580c7, "id");
            int iM212484b42 = cq0.m212484b4(cursorM213580c7, "state");
            int iM212484b43 = cq0.m212484b4(cursorM213580c7, "worker_class_name");
            int iM212484b44 = cq0.m212484b4(cursorM213580c7, "input_merger_class_name");
            int iM212484b45 = cq0.m212484b4(cursorM213580c7, "input");
            int iM212484b46 = cq0.m212484b4(cursorM213580c7, "output");
            int iM212484b47 = cq0.m212484b4(cursorM213580c7, "initial_delay");
            int iM212484b48 = cq0.m212484b4(cursorM213580c7, "interval_duration");
            int iM212484b49 = cq0.m212484b4(cursorM213580c7, "flex_duration");
            int iM212484b410 = cq0.m212484b4(cursorM213580c7, "run_attempt_count");
            int iM212484b411 = cq0.m212484b4(cursorM213580c7, "backoff_policy");
            int iM212484b412 = cq0.m212484b4(cursorM213580c7, "backoff_delay_duration");
            int iM212484b413 = cq0.m212484b4(cursorM213580c7, "last_enqueue_time");
            js0Var = js0VarAcquire;
            try {
                int iM212484b414 = cq0.m212484b4(cursorM213580c7, "minimum_retention_duration");
                int iM212484b415 = cq0.m212484b4(cursorM213580c7, "schedule_requested_at");
                int iM212484b416 = cq0.m212484b4(cursorM213580c7, "run_in_foreground");
                int iM212484b417 = cq0.m212484b4(cursorM213580c7, "out_of_quota_policy");
                int iM212484b418 = cq0.m212484b4(cursorM213580c7, "period_count");
                int iM212484b419 = cq0.m212484b4(cursorM213580c7, "generation");
                int iM212484b420 = cq0.m212484b4(cursorM213580c7, "required_network_type");
                int iM212484b421 = cq0.m212484b4(cursorM213580c7, "requires_charging");
                int iM212484b422 = cq0.m212484b4(cursorM213580c7, "requires_device_idle");
                int iM212484b423 = cq0.m212484b4(cursorM213580c7, "requires_battery_not_low");
                int iM212484b424 = cq0.m212484b4(cursorM213580c7, "requires_storage_not_low");
                int iM212484b425 = cq0.m212484b4(cursorM213580c7, "trigger_content_update_delay");
                int iM212484b426 = cq0.m212484b4(cursorM213580c7, "trigger_max_content_delay");
                int iM212484b427 = cq0.m212484b4(cursorM213580c7, "content_uri_triggers");
                int i = iM212484b414;
                ArrayList arrayList = new ArrayList(cursorM213580c7.getCount());
                while (cursorM213580c7.moveToNext()) {
                    byte[] blob = null;
                    String string = cursorM213580c7.isNull(iM212484b4) ? null : cursorM213580c7.getString(iM212484b4);
                    WorkInfo$State workInfo$StateM210584d2 = b81.m210584d2(cursorM213580c7.getInt(iM212484b42));
                    String string2 = cursorM213580c7.isNull(iM212484b43) ? null : cursorM213580c7.getString(iM212484b43);
                    String string3 = cursorM213580c7.isNull(iM212484b44) ? null : cursorM213580c7.getString(iM212484b44);
                    C1106qd c1106qdM214380a0 = C1106qd.m214380a0(cursorM213580c7.isNull(iM212484b45) ? null : cursorM213580c7.getBlob(iM212484b45));
                    C1106qd c1106qdM214380a02 = C1106qd.m214380a0(cursorM213580c7.isNull(iM212484b46) ? null : cursorM213580c7.getBlob(iM212484b46));
                    long j = cursorM213580c7.getLong(iM212484b47);
                    long j2 = cursorM213580c7.getLong(iM212484b48);
                    long j3 = cursorM213580c7.getLong(iM212484b49);
                    int i2 = cursorM213580c7.getInt(iM212484b410);
                    BackoffPolicy backoffPolicyM210581c9 = b81.m210581c9(cursorM213580c7.getInt(iM212484b411));
                    long j4 = cursorM213580c7.getLong(iM212484b412);
                    long j5 = cursorM213580c7.getLong(iM212484b413);
                    int i3 = i;
                    long j6 = cursorM213580c7.getLong(i3);
                    int i4 = iM212484b4;
                    int i5 = iM212484b415;
                    long j7 = cursorM213580c7.getLong(i5);
                    iM212484b415 = i5;
                    int i6 = iM212484b416;
                    boolean z = cursorM213580c7.getInt(i6) != 0;
                    iM212484b416 = i6;
                    int i7 = iM212484b417;
                    OutOfQuotaPolicy outOfQuotaPolicyM210583d1 = b81.m210583d1(cursorM213580c7.getInt(i7));
                    iM212484b417 = i7;
                    int i8 = iM212484b418;
                    int i9 = cursorM213580c7.getInt(i8);
                    iM212484b418 = i8;
                    int i10 = iM212484b419;
                    int i11 = cursorM213580c7.getInt(i10);
                    iM212484b419 = i10;
                    int i12 = iM212484b420;
                    NetworkType networkTypeM210582d0 = b81.m210582d0(cursorM213580c7.getInt(i12));
                    iM212484b420 = i12;
                    int i13 = iM212484b421;
                    boolean z2 = cursorM213580c7.getInt(i13) != 0;
                    iM212484b421 = i13;
                    int i14 = iM212484b422;
                    boolean z3 = cursorM213580c7.getInt(i14) != 0;
                    iM212484b422 = i14;
                    int i15 = iM212484b423;
                    boolean z4 = cursorM213580c7.getInt(i15) != 0;
                    iM212484b423 = i15;
                    int i16 = iM212484b424;
                    boolean z5 = cursorM213580c7.getInt(i16) != 0;
                    iM212484b424 = i16;
                    int i17 = iM212484b425;
                    long j8 = cursorM213580c7.getLong(i17);
                    iM212484b425 = i17;
                    int i18 = iM212484b426;
                    long j9 = cursorM213580c7.getLong(i18);
                    iM212484b426 = i18;
                    int i19 = iM212484b427;
                    if (!cursorM213580c7.isNull(i19)) {
                        blob = cursorM213580c7.getBlob(i19);
                    }
                    iM212484b427 = i19;
                    arrayList.add(new wg1(string, workInfo$StateM210584d2, string2, string3, c1106qdM214380a0, c1106qdM214380a02, j, j2, j3, new C0836lv(networkTypeM210582d0, z2, z3, z4, z5, j8, j9, b81.m210565a5(blob)), i2, backoffPolicyM210581c9, j4, j5, j6, j7, z, outOfQuotaPolicyM210583d1, i9, i11));
                    iM212484b4 = i4;
                    i = i3;
                }
                cursorM213580c7.close();
                js0Var.m213344b0();
                return arrayList;
            } catch (Throwable th) {
                th = th;
                cursorM213580c7.close();
                js0Var.m213344b0();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            js0Var = js0VarAcquire;
        }
    }

    /* renamed from: a6 */
    public WorkInfo$State m215183a6(String str) {
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61125a0;
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT state FROM workspec WHERE id=?", 1);
        if (str == null) {
            js0VarAcquire.mo213343a9(1);
        } else {
            js0VarAcquire.mo213341a6(1, str);
        }
        workDatabase_Impl.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
        try {
            WorkInfo$State workInfo$StateM210584d2 = null;
            if (cursorM213580c7.moveToFirst()) {
                Integer numValueOf = cursorM213580c7.isNull(0) ? null : Integer.valueOf(cursorM213580c7.getInt(0));
                if (numValueOf != null) {
                    workInfo$StateM210584d2 = b81.m210584d2(numValueOf.intValue());
                }
            }
            return workInfo$StateM210584d2;
        } finally {
            cursorM213580c7.close();
            js0VarAcquire.m213344b0();
        }
    }

    /* renamed from: a7 */
    public ArrayList m215184a7(String str) {
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61125a0;
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5) AND id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (str == null) {
            js0VarAcquire.mo213343a9(1);
        } else {
            js0VarAcquire.mo213341a6(1, str);
        }
        workDatabase_Impl.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
        try {
            ArrayList arrayList = new ArrayList(cursorM213580c7.getCount());
            while (cursorM213580c7.moveToNext()) {
                arrayList.add(cursorM213580c7.isNull(0) ? null : cursorM213580c7.getString(0));
            }
            return arrayList;
        } finally {
            cursorM213580c7.close();
            js0VarAcquire.m213344b0();
        }
    }

    /* renamed from: a8 */
    public wg1 m215185a8(String str) throws Throwable {
        js0 js0Var;
        int iM212484b4;
        int iM212484b42;
        int iM212484b43;
        int iM212484b44;
        int iM212484b45;
        int iM212484b46;
        int iM212484b47;
        int iM212484b48;
        int iM212484b49;
        int iM212484b410;
        int iM212484b411;
        int iM212484b412;
        int iM212484b413;
        int iM212484b414;
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61125a0;
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT * FROM workspec WHERE id=?", 1);
        if (str == null) {
            js0VarAcquire.mo213343a9(1);
        } else {
            js0VarAcquire.mo213341a6(1, str);
        }
        workDatabase_Impl.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
        try {
            iM212484b4 = cq0.m212484b4(cursorM213580c7, "id");
            iM212484b42 = cq0.m212484b4(cursorM213580c7, "state");
            iM212484b43 = cq0.m212484b4(cursorM213580c7, "worker_class_name");
            iM212484b44 = cq0.m212484b4(cursorM213580c7, "input_merger_class_name");
            iM212484b45 = cq0.m212484b4(cursorM213580c7, "input");
            iM212484b46 = cq0.m212484b4(cursorM213580c7, "output");
            iM212484b47 = cq0.m212484b4(cursorM213580c7, "initial_delay");
            iM212484b48 = cq0.m212484b4(cursorM213580c7, "interval_duration");
            iM212484b49 = cq0.m212484b4(cursorM213580c7, "flex_duration");
            iM212484b410 = cq0.m212484b4(cursorM213580c7, "run_attempt_count");
            iM212484b411 = cq0.m212484b4(cursorM213580c7, "backoff_policy");
            iM212484b412 = cq0.m212484b4(cursorM213580c7, "backoff_delay_duration");
            iM212484b413 = cq0.m212484b4(cursorM213580c7, "last_enqueue_time");
            iM212484b414 = cq0.m212484b4(cursorM213580c7, "minimum_retention_duration");
            js0Var = js0VarAcquire;
        } catch (Throwable th) {
            th = th;
            js0Var = js0VarAcquire;
        }
        try {
            int iM212484b415 = cq0.m212484b4(cursorM213580c7, "schedule_requested_at");
            int iM212484b416 = cq0.m212484b4(cursorM213580c7, "run_in_foreground");
            int iM212484b417 = cq0.m212484b4(cursorM213580c7, "out_of_quota_policy");
            int iM212484b418 = cq0.m212484b4(cursorM213580c7, "period_count");
            int iM212484b419 = cq0.m212484b4(cursorM213580c7, "generation");
            int iM212484b420 = cq0.m212484b4(cursorM213580c7, "required_network_type");
            int iM212484b421 = cq0.m212484b4(cursorM213580c7, "requires_charging");
            int iM212484b422 = cq0.m212484b4(cursorM213580c7, "requires_device_idle");
            int iM212484b423 = cq0.m212484b4(cursorM213580c7, "requires_battery_not_low");
            int iM212484b424 = cq0.m212484b4(cursorM213580c7, "requires_storage_not_low");
            int iM212484b425 = cq0.m212484b4(cursorM213580c7, "trigger_content_update_delay");
            int iM212484b426 = cq0.m212484b4(cursorM213580c7, "trigger_max_content_delay");
            int iM212484b427 = cq0.m212484b4(cursorM213580c7, "content_uri_triggers");
            wg1 wg1Var = null;
            byte[] blob = null;
            if (cursorM213580c7.moveToFirst()) {
                String string = cursorM213580c7.isNull(iM212484b4) ? null : cursorM213580c7.getString(iM212484b4);
                WorkInfo$State workInfo$StateM210584d2 = b81.m210584d2(cursorM213580c7.getInt(iM212484b42));
                String string2 = cursorM213580c7.isNull(iM212484b43) ? null : cursorM213580c7.getString(iM212484b43);
                String string3 = cursorM213580c7.isNull(iM212484b44) ? null : cursorM213580c7.getString(iM212484b44);
                C1106qd c1106qdM214380a0 = C1106qd.m214380a0(cursorM213580c7.isNull(iM212484b45) ? null : cursorM213580c7.getBlob(iM212484b45));
                C1106qd c1106qdM214380a02 = C1106qd.m214380a0(cursorM213580c7.isNull(iM212484b46) ? null : cursorM213580c7.getBlob(iM212484b46));
                long j = cursorM213580c7.getLong(iM212484b47);
                long j2 = cursorM213580c7.getLong(iM212484b48);
                long j3 = cursorM213580c7.getLong(iM212484b49);
                int i = cursorM213580c7.getInt(iM212484b410);
                BackoffPolicy backoffPolicyM210581c9 = b81.m210581c9(cursorM213580c7.getInt(iM212484b411));
                long j4 = cursorM213580c7.getLong(iM212484b412);
                long j5 = cursorM213580c7.getLong(iM212484b413);
                long j6 = cursorM213580c7.getLong(iM212484b414);
                long j7 = cursorM213580c7.getLong(iM212484b415);
                boolean z = cursorM213580c7.getInt(iM212484b416) != 0;
                OutOfQuotaPolicy outOfQuotaPolicyM210583d1 = b81.m210583d1(cursorM213580c7.getInt(iM212484b417));
                int i2 = cursorM213580c7.getInt(iM212484b418);
                int i3 = cursorM213580c7.getInt(iM212484b419);
                NetworkType networkTypeM210582d0 = b81.m210582d0(cursorM213580c7.getInt(iM212484b420));
                boolean z2 = cursorM213580c7.getInt(iM212484b421) != 0;
                boolean z3 = cursorM213580c7.getInt(iM212484b422) != 0;
                boolean z4 = cursorM213580c7.getInt(iM212484b423) != 0;
                boolean z5 = cursorM213580c7.getInt(iM212484b424) != 0;
                long j8 = cursorM213580c7.getLong(iM212484b425);
                long j9 = cursorM213580c7.getLong(iM212484b426);
                if (!cursorM213580c7.isNull(iM212484b427)) {
                    blob = cursorM213580c7.getBlob(iM212484b427);
                }
                wg1Var = new wg1(string, workInfo$StateM210584d2, string2, string3, c1106qdM214380a0, c1106qdM214380a02, j, j2, j3, new C0836lv(networkTypeM210582d0, z2, z3, z4, z5, j8, j9, b81.m210565a5(blob)), i, backoffPolicyM210581c9, j4, j5, j6, j7, z, outOfQuotaPolicyM210583d1, i2, i3);
            }
            cursorM213580c7.close();
            js0Var.m213344b0();
            return wg1Var;
        } catch (Throwable th2) {
            th = th2;
            cursorM213580c7.close();
            js0Var.m213344b0();
            throw th;
        }
    }

    /* renamed from: a9 */
    public ArrayList m215186a9(String str) {
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61125a0;
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT id, state FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (str == null) {
            js0VarAcquire.mo213343a9(1);
        } else {
            js0VarAcquire.mo213341a6(1, str);
        }
        workDatabase_Impl.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
        try {
            ArrayList arrayList = new ArrayList(cursorM213580c7.getCount());
            while (cursorM213580c7.moveToNext()) {
                String string = cursorM213580c7.isNull(0) ? null : cursorM213580c7.getString(0);
                WorkInfo$State workInfo$StateM210584d2 = b81.m210584d2(cursorM213580c7.getInt(1));
                t60.m214695b6(string, "id");
                vg1 vg1Var = new vg1();
                vg1Var.f60643a0 = string;
                vg1Var.f60644a1 = workInfo$StateM210584d2;
                arrayList.add(vg1Var);
            }
            return arrayList;
        } finally {
            cursorM213580c7.close();
            js0VarAcquire.m213344b0();
        }
    }

    /* renamed from: b0 */
    public void m215187b0(String str, long j) {
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61125a0;
        workDatabase_Impl.m212857a1();
        w31 w31Var = (w31) this.f61135b0;
        u00 u00VarM210428a0 = w31Var.m210428a0();
        u00VarM210428a0.mo213346b6(1, j);
        if (str == null) {
            u00VarM210428a0.mo213343a9(2);
        } else {
            u00VarM210428a0.mo213341a6(2, str);
        }
        workDatabase_Impl.m212858a2();
        try {
            u00VarM210428a0.m214812a0();
            workDatabase_Impl.m212863b2();
        } finally {
            workDatabase_Impl.m212860a9();
            w31Var.m210431a3(u00VarM210428a0);
        }
    }

    /* renamed from: b1 */
    public void m215188b1(float f) {
        this.f61129a4 = new C0481f3(f);
        this.f61130a5 = new C0481f3(f);
        this.f61131a6 = new C0481f3(f);
        this.f61132a7 = new C0481f3(f);
    }

    /* renamed from: b2 */
    public void m215189b2(String str, long j) {
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61125a0;
        workDatabase_Impl.m212857a1();
        w31 w31Var = (w31) this.f61132a7;
        u00 u00VarM210428a0 = w31Var.m210428a0();
        u00VarM210428a0.mo213346b6(1, j);
        if (str == null) {
            u00VarM210428a0.mo213343a9(2);
        } else {
            u00VarM210428a0.mo213341a6(2, str);
        }
        workDatabase_Impl.m212858a2();
        try {
            u00VarM210428a0.m214812a0();
            workDatabase_Impl.m212863b2();
        } finally {
            workDatabase_Impl.m212860a9();
            w31Var.m210431a3(u00VarM210428a0);
        }
    }

    /* renamed from: b3 */
    public void m215190b3(String str, C1106qd c1106qd) throws Throwable {
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61125a0;
        workDatabase_Impl.m212857a1();
        w31 w31Var = (w31) this.f61131a6;
        u00 u00VarM210428a0 = w31Var.m210428a0();
        byte[] bArrM214381a1 = C1106qd.m214381a1(c1106qd);
        if (bArrM214381a1 == null) {
            u00VarM210428a0.mo213343a9(1);
        } else {
            u00VarM210428a0.mo213347c1(1, bArrM214381a1);
        }
        if (str == null) {
            u00VarM210428a0.mo213343a9(2);
        } else {
            u00VarM210428a0.mo213341a6(2, str);
        }
        workDatabase_Impl.m212858a2();
        try {
            u00VarM210428a0.m214812a0();
            workDatabase_Impl.m212863b2();
        } finally {
            workDatabase_Impl.m212860a9();
            w31Var.m210431a3(u00VarM210428a0);
        }
    }

    /* renamed from: b4 */
    public void m215191b4(WorkInfo$State workInfo$State, String str) {
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61125a0;
        workDatabase_Impl.m212857a1();
        w31 w31Var = (w31) this.f61129a4;
        u00 u00VarM210428a0 = w31Var.m210428a0();
        u00VarM210428a0.mo213346b6(1, b81.m210598f1(workInfo$State));
        if (str == null) {
            u00VarM210428a0.mo213343a9(2);
        } else {
            u00VarM210428a0.mo213341a6(2, str);
        }
        workDatabase_Impl.m212858a2();
        try {
            u00VarM210428a0.m214812a0();
            workDatabase_Impl.m212863b2();
        } finally {
            workDatabase_Impl.m212860a9();
            w31Var.m210431a3(u00VarM210428a0);
        }
    }

    public xg1() {
        this.f61125a0 = new ns0();
        this.f61126a1 = new ns0();
        this.f61127a2 = new ns0();
        this.f61128a3 = new ns0();
        this.f61129a4 = new C0481f3(0.0f);
        this.f61130a5 = new C0481f3(0.0f);
        this.f61131a6 = new C0481f3(0.0f);
        this.f61132a7 = new C0481f3(0.0f);
        int i = 0;
        this.f61133a8 = new C1351vv(i);
        this.f61134a9 = new C1351vv(i);
        this.f61135b0 = new C1351vv(i);
        this.f61136b1 = new C1351vv(i);
    }
}

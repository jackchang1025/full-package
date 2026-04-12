package p000;

import androidx.room.AbstractC0087a0;
import androidx.work.BackoffPolicy;
import androidx.work.OutOfQuotaPolicy;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: sb */
/* loaded from: classes2.dex */
public final class C1216sb extends AbstractC0087a0 {

    /* renamed from: a3 */
    public final /* synthetic */ int f59948a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1216sb(fs0 fs0Var, int i) {
        super(fs0Var);
        this.f59948a3 = i;
    }

    @Override // androidx.room.AbstractC0087a0
    /* renamed from: a2 */
    public final String mo210430a2() {
        switch (this.f59948a3) {
            case 0:
                return "INSERT OR IGNORE INTO `Dependency` (`work_spec_id`,`prerequisite_id`) VALUES (?,?)";
            case 1:
                return "INSERT OR REPLACE INTO `Preference` (`key`,`long_value`) VALUES (?,?)";
            case 2:
                return "INSERT OR REPLACE INTO `SystemIdInfo` (`work_spec_id`,`generation`,`system_id`) VALUES (?,?,?)";
            case 3:
                return "INSERT OR IGNORE INTO `WorkName` (`name`,`work_spec_id`) VALUES (?,?)";
            case 4:
                return "INSERT OR REPLACE INTO `WorkProgress` (`work_spec_id`,`progress`) VALUES (?,?)";
            case 5:
                return "INSERT OR IGNORE INTO `WorkSpec` (`id`,`state`,`worker_class_name`,`input_merger_class_name`,`input`,`output`,`initial_delay`,`interval_duration`,`flex_duration`,`run_attempt_count`,`backoff_policy`,`backoff_delay_duration`,`last_enqueue_time`,`minimum_retention_duration`,`schedule_requested_at`,`run_in_foreground`,`out_of_quota_policy`,`period_count`,`generation`,`required_network_type`,`requires_charging`,`requires_device_idle`,`requires_battery_not_low`,`requires_storage_not_low`,`trigger_content_update_delay`,`trigger_max_content_delay`,`content_uri_triggers`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            default:
                return "INSERT OR IGNORE INTO `WorkTag` (`tag`,`work_spec_id`) VALUES (?,?)";
        }
    }

    /* renamed from: a4 */
    public final void m214589a4(u00 u00Var, Object obj) throws Throwable {
        int i;
        switch (this.f59948a3) {
            case 0:
                C1200rz c1200rz = (C1200rz) obj;
                u00Var.mo213341a6(1, c1200rz.f59837a0);
                String str = c1200rz.f59838a1;
                if (str == null) {
                    u00Var.mo213343a9(2);
                    return;
                } else {
                    u00Var.mo213341a6(2, str);
                    return;
                }
            case 1:
                do0 do0Var = (do0) obj;
                u00Var.mo213341a6(1, do0Var.f55836a0);
                u00Var.mo213346b6(2, do0Var.f55837a1.longValue());
                return;
            case 2:
                String str2 = ((v31) obj).f60571a0;
                if (str2 == null) {
                    u00Var.mo213343a9(1);
                } else {
                    u00Var.mo213341a6(1, str2);
                }
                u00Var.mo213346b6(2, r12.f60572a1);
                u00Var.mo213346b6(3, r12.f60573a2);
                return;
            case 3:
                ng1 ng1Var = (ng1) obj;
                String str3 = ng1Var.f58629a0;
                if (str3 == null) {
                    u00Var.mo213343a9(1);
                } else {
                    u00Var.mo213341a6(1, str3);
                }
                u00Var.mo213341a6(2, ng1Var.f58630a1);
                return;
            case 4:
                throw new ClassCastException();
            case 5:
                wg1 wg1Var = (wg1) obj;
                String str4 = wg1Var.f60912a0;
                int i2 = 1;
                if (str4 == null) {
                    u00Var.mo213343a9(1);
                } else {
                    u00Var.mo213341a6(1, str4);
                }
                u00Var.mo213346b6(2, b81.m210598f1(wg1Var.f60913a1));
                String str5 = wg1Var.f60914a2;
                if (str5 == null) {
                    u00Var.mo213343a9(3);
                } else {
                    u00Var.mo213341a6(3, str5);
                }
                String str6 = wg1Var.f60915a3;
                if (str6 == null) {
                    u00Var.mo213343a9(4);
                } else {
                    u00Var.mo213341a6(4, str6);
                }
                byte[] bArrM214381a1 = C1106qd.m214381a1(wg1Var.f60916a4);
                if (bArrM214381a1 == null) {
                    u00Var.mo213343a9(5);
                } else {
                    u00Var.mo213347c1(5, bArrM214381a1);
                }
                byte[] bArrM214381a12 = C1106qd.m214381a1(wg1Var.f60917a5);
                if (bArrM214381a12 == null) {
                    u00Var.mo213343a9(6);
                } else {
                    u00Var.mo213347c1(6, bArrM214381a12);
                }
                u00Var.mo213346b6(7, wg1Var.f60918a6);
                u00Var.mo213346b6(8, wg1Var.f60919a7);
                u00Var.mo213346b6(9, wg1Var.f60920a8);
                u00Var.mo213346b6(10, wg1Var.f60922b0);
                BackoffPolicy backoffPolicy = wg1Var.f60923b1;
                t60.m214695b6(backoffPolicy, "backoffPolicy");
                int iOrdinal = backoffPolicy.ordinal();
                if (iOrdinal == 0) {
                    i = 0;
                } else {
                    if (iOrdinal != 1) {
                        throw new NoWhenBranchMatchedException();
                    }
                    i = 1;
                }
                u00Var.mo213346b6(11, i);
                u00Var.mo213346b6(12, wg1Var.f60924b2);
                u00Var.mo213346b6(13, wg1Var.f60925b3);
                u00Var.mo213346b6(14, wg1Var.f60926b4);
                u00Var.mo213346b6(15, wg1Var.f60927b5);
                u00Var.mo213346b6(16, wg1Var.f60928b6 ? 1L : 0L);
                OutOfQuotaPolicy outOfQuotaPolicy = wg1Var.f60929b7;
                t60.m214695b6(outOfQuotaPolicy, "policy");
                int iOrdinal2 = outOfQuotaPolicy.ordinal();
                if (iOrdinal2 == 0) {
                    i2 = 0;
                } else if (iOrdinal2 != 1) {
                    throw new NoWhenBranchMatchedException();
                }
                u00Var.mo213346b6(17, i2);
                u00Var.mo213346b6(18, wg1Var.f60930b8);
                u00Var.mo213346b6(19, wg1Var.f60931b9);
                C0836lv c0836lv = wg1Var.f60921a9;
                if (c0836lv != null) {
                    u00Var.mo213346b6(20, b81.m210587d6(c0836lv.f58193a0));
                    u00Var.mo213346b6(21, c0836lv.f58194a1 ? 1L : 0L);
                    u00Var.mo213346b6(22, c0836lv.f58195a2 ? 1L : 0L);
                    u00Var.mo213346b6(23, c0836lv.f58196a3 ? 1L : 0L);
                    u00Var.mo213346b6(24, c0836lv.f58197a4 ? 1L : 0L);
                    u00Var.mo213346b6(25, c0836lv.f58198a5);
                    u00Var.mo213346b6(26, c0836lv.f58199a6);
                    u00Var.mo213347c1(27, b81.m210596e9(c0836lv.f58200a7));
                    return;
                }
                u00Var.mo213343a9(20);
                u00Var.mo213343a9(21);
                u00Var.mo213343a9(22);
                u00Var.mo213343a9(23);
                u00Var.mo213343a9(24);
                u00Var.mo213343a9(25);
                u00Var.mo213343a9(26);
                u00Var.mo213343a9(27);
                return;
            default:
                yg1 yg1Var = (yg1) obj;
                String str7 = yg1Var.f61316a0;
                if (str7 == null) {
                    u00Var.mo213343a9(1);
                } else {
                    u00Var.mo213341a6(1, str7);
                }
                String str8 = yg1Var.f61317a1;
                if (str8 == null) {
                    u00Var.mo213343a9(2);
                    return;
                } else {
                    u00Var.mo213341a6(2, str8);
                    return;
                }
        }
    }

    /* renamed from: a5 */
    public final void m214590a5(Object obj) {
        u00 u00VarM210428a0 = m210428a0();
        try {
            m214589a4(u00VarM210428a0, obj);
            u00VarM210428a0.f60314a2.executeInsert();
        } finally {
            m210431a3(u00VarM210428a0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1216sb(fs0 fs0Var) {
        super(fs0Var);
        this.f59948a3 = 1;
        t60.m214695b6(fs0Var, "database");
    }
}

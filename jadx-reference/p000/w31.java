package p000;

import androidx.room.AbstractC0087a0;
import androidx.work.BackoffPolicy;
import androidx.work.OutOfQuotaPolicy;
import kotlin.NoWhenBranchMatchedException;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class w31 extends AbstractC0087a0 {

    /* renamed from: a3 */
    public final /* synthetic */ int f60767a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ w31(fs0 fs0Var, int i) {
        super(fs0Var);
        this.f60767a3 = i;
    }

    @Override // androidx.room.AbstractC0087a0
    /* renamed from: a2 */
    public final String mo210430a2() {
        switch (this.f60767a3) {
            case 0:
                return "DELETE FROM SystemIdInfo where work_spec_id=? AND generation=?";
            case 1:
                return "DELETE FROM SystemIdInfo where work_spec_id=?";
            case 2:
                return "DELETE from WorkProgress where work_spec_id=?";
            case 3:
                return "DELETE FROM WorkProgress";
            case 4:
                return "UPDATE workspec SET schedule_requested_at=? WHERE id=?";
            case 5:
                return "UPDATE workspec SET schedule_requested_at=-1 WHERE state NOT IN (2, 3, 5)";
            case 6:
                return "DELETE FROM workspec WHERE state IN (2, 3, 5) AND (SELECT COUNT(*)=0 FROM dependency WHERE     prerequisite_id=id AND     work_spec_id NOT IN         (SELECT id FROM workspec WHERE state IN (2, 3, 5)))";
            case 7:
                return "UPDATE workspec SET generation=generation+1 WHERE id=?";
            case 8:
                return "UPDATE OR ABORT `WorkSpec` SET `id` = ?,`state` = ?,`worker_class_name` = ?,`input_merger_class_name` = ?,`input` = ?,`output` = ?,`initial_delay` = ?,`interval_duration` = ?,`flex_duration` = ?,`run_attempt_count` = ?,`backoff_policy` = ?,`backoff_delay_duration` = ?,`last_enqueue_time` = ?,`minimum_retention_duration` = ?,`schedule_requested_at` = ?,`run_in_foreground` = ?,`out_of_quota_policy` = ?,`period_count` = ?,`generation` = ?,`required_network_type` = ?,`requires_charging` = ?,`requires_device_idle` = ?,`requires_battery_not_low` = ?,`requires_storage_not_low` = ?,`trigger_content_update_delay` = ?,`trigger_max_content_delay` = ?,`content_uri_triggers` = ? WHERE `id` = ?";
            case 9:
                return "DELETE FROM workspec WHERE id=?";
            case 10:
                return "UPDATE workspec SET state=? WHERE id=?";
            case oe0.DEFAULT_M /* 11 */:
                return "UPDATE workspec SET period_count=period_count+1 WHERE id=?";
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                return "UPDATE workspec SET output=? WHERE id=?";
            case 13:
                return "UPDATE workspec SET last_enqueue_time=? WHERE id=?";
            case 14:
                return "UPDATE workspec SET run_attempt_count=run_attempt_count+1 WHERE id=?";
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                return "UPDATE workspec SET run_attempt_count=0 WHERE id=?";
            default:
                return "DELETE FROM worktag WHERE work_spec_id=?";
        }
    }

    /* renamed from: a4 */
    public void m215003a4(u00 u00Var, Object obj) throws Throwable {
        int i;
        wg1 wg1Var = (wg1) obj;
        String str = wg1Var.f60912a0;
        int i2 = 1;
        if (str == null) {
            u00Var.mo213343a9(1);
        } else {
            u00Var.mo213341a6(1, str);
        }
        u00Var.mo213346b6(2, b81.m210598f1(wg1Var.f60913a1));
        String str2 = wg1Var.f60914a2;
        if (str2 == null) {
            u00Var.mo213343a9(3);
        } else {
            u00Var.mo213341a6(3, str2);
        }
        String str3 = wg1Var.f60915a3;
        if (str3 == null) {
            u00Var.mo213343a9(4);
        } else {
            u00Var.mo213341a6(4, str3);
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
        } else {
            u00Var.mo213343a9(20);
            u00Var.mo213343a9(21);
            u00Var.mo213343a9(22);
            u00Var.mo213343a9(23);
            u00Var.mo213343a9(24);
            u00Var.mo213343a9(25);
            u00Var.mo213343a9(26);
            u00Var.mo213343a9(27);
        }
        if (str == null) {
            u00Var.mo213343a9(28);
        } else {
            u00Var.mo213341a6(28, str);
        }
    }
}

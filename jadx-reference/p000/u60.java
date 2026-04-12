package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class u60 {
    public /* synthetic */ u60(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final void beginTransactionInternal$room_runtime_release(d31 d31Var) {
        t60.m214695b6(d31Var, "database");
        if (d31Var.mo210438b5()) {
            d31Var.mo210441b9();
        } else {
            d31Var.mo210433a2();
        }
    }

    public final String getTriggerName$room_runtime_release(String str, String str2) {
        t60.m214695b6(str, "tableName");
        t60.m214695b6(str2, "triggerType");
        return "`room_table_modification_trigger_" + str + '_' + str2 + '`';
    }

    private u60() {
    }

    public static /* synthetic */ void getRESET_UPDATED_TABLES_SQL$room_runtime_release$annotations() {
    }

    public static /* synthetic */ void getSELECT_UPDATED_TABLES_SQL$room_runtime_release$annotations() {
    }
}

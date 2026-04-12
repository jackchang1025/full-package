package com.storm.safe.rock.keepalive;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class tgcxxzlbc$KeepAliveStrategy {

    /* renamed from: a0 */
    public static final tgcxxzlbc$KeepAliveStrategy f51980a0;

    /* renamed from: a1 */
    public static final tgcxxzlbc$KeepAliveStrategy f51981a1;

    /* renamed from: a2 */
    public static final /* synthetic */ tgcxxzlbc$KeepAliveStrategy[] f51982a2;

    /* JADX INFO: Fake field, exist only in values array */
    tgcxxzlbc$KeepAliveStrategy EF0;

    static {
        tgcxxzlbc$KeepAliveStrategy tgcxxzlbc_keepalivestrategy = new tgcxxzlbc$KeepAliveStrategy("WORK_MANAGER", 0);
        tgcxxzlbc$KeepAliveStrategy tgcxxzlbc_keepalivestrategy2 = new tgcxxzlbc$KeepAliveStrategy("JOB_SCHEDULER", 1);
        f51980a0 = tgcxxzlbc_keepalivestrategy2;
        tgcxxzlbc$KeepAliveStrategy tgcxxzlbc_keepalivestrategy3 = new tgcxxzlbc$KeepAliveStrategy("CORE_SERVICE", 2);
        f51981a1 = tgcxxzlbc_keepalivestrategy3;
        f51982a2 = new tgcxxzlbc$KeepAliveStrategy[]{tgcxxzlbc_keepalivestrategy, tgcxxzlbc_keepalivestrategy2, tgcxxzlbc_keepalivestrategy3};
    }

    public static tgcxxzlbc$KeepAliveStrategy valueOf(String str) {
        return (tgcxxzlbc$KeepAliveStrategy) Enum.valueOf(tgcxxzlbc$KeepAliveStrategy.class, str);
    }

    public static tgcxxzlbc$KeepAliveStrategy[] values() {
        return (tgcxxzlbc$KeepAliveStrategy[]) f51982a2.clone();
    }
}

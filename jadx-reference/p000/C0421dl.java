package p000;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.work.impl.constraints.trackers.BroadcastReceiverConstraintTracker$broadcastReceiver$1;
import p000.AbstractC0422dm;
import p000.AbstractC0423dn;
import p000.C0421dl;
import p000.C1351vv;
import p000.g21;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: dl */
/* loaded from: classes2.dex */
public final class C0421dl extends AbstractC0826ln {

    /* renamed from: a5 */
    public final BroadcastReceiverConstraintTracker$broadcastReceiver$1 f55832a5;

    /* renamed from: a6 */
    public final /* synthetic */ int f55833a6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.work.impl.constraints.trackers.BroadcastReceiverConstraintTracker$broadcastReceiver$1] */
    public C0421dl(Context context, pg1 pg1Var, int i) {
        super(context, pg1Var);
        this.f55833a6 = i;
        this.f55832a5 = new BroadcastReceiver() { // from class: androidx.work.impl.constraints.trackers.BroadcastReceiverConstraintTracker$broadcastReceiver$1
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
            java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
             */
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                t60.m214695b6(context2, "context");
                t60.m214695b6(intent, "intent");
                C0421dl c0421dl = this.f45578a0;
                switch (c0421dl.f55833a6) {
                    case 0:
                        String action = intent.getAction();
                        if (action != null) {
                            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                            int i2 = AbstractC0422dm.f55834a0;
                            c1351vvM214963a5.getClass();
                            switch (action.hashCode()) {
                                case -1886648615:
                                    if (action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                                        c0421dl.m213874a2(Boolean.FALSE);
                                        break;
                                    }
                                    break;
                                case -54942926:
                                    if (action.equals("android.os.action.DISCHARGING")) {
                                        c0421dl.m213874a2(Boolean.FALSE);
                                        break;
                                    }
                                    break;
                                case 948344062:
                                    if (action.equals("android.os.action.CHARGING")) {
                                        c0421dl.m213874a2(Boolean.TRUE);
                                        break;
                                    }
                                    break;
                                case 1019184907:
                                    if (action.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                                        c0421dl.m213874a2(Boolean.TRUE);
                                        break;
                                    }
                                    break;
                            }
                        }
                        break;
                    case 1:
                        if (intent.getAction() != null) {
                            C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
                            int i3 = AbstractC0423dn.f55835a0;
                            intent.getAction();
                            c1351vvM214963a52.getClass();
                            String action2 = intent.getAction();
                            if (action2 != null) {
                                int iHashCode = action2.hashCode();
                                if (iHashCode == -1980154005) {
                                    if (action2.equals("android.intent.action.BATTERY_OKAY")) {
                                        c0421dl.m213874a2(Boolean.TRUE);
                                        break;
                                    }
                                } else if (iHashCode == 490310653 && action2.equals("android.intent.action.BATTERY_LOW")) {
                                    c0421dl.m213874a2(Boolean.FALSE);
                                    break;
                                }
                            }
                        }
                        break;
                    default:
                        if (intent.getAction() != null) {
                            C1351vv c1351vvM214963a53 = C1351vv.m214963a5();
                            int i4 = g21.f56368a0;
                            intent.getAction();
                            c1351vvM214963a53.getClass();
                            String action3 = intent.getAction();
                            if (action3 != null) {
                                int iHashCode2 = action3.hashCode();
                                if (iHashCode2 == -1181163412) {
                                    if (action3.equals("android.intent.action.DEVICE_STORAGE_LOW")) {
                                        c0421dl.m213874a2(Boolean.FALSE);
                                        break;
                                    }
                                } else if (iHashCode2 == -730838620 && action3.equals("android.intent.action.DEVICE_STORAGE_OK")) {
                                    c0421dl.m213874a2(Boolean.TRUE);
                                    break;
                                }
                            }
                        }
                        break;
                }
            }
        };
    }

    @Override // p000.AbstractC0826ln
    /* renamed from: a0 */
    public final Object mo212612a0() {
        int i = this.f55833a6;
        Context context = this.f58054a1;
        boolean z = true;
        switch (i) {
            case 0:
                Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
                if (intentRegisterReceiver == null) {
                    C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                    int i2 = AbstractC0422dm.f55834a0;
                    c1351vvM214963a5.getClass();
                    return Boolean.FALSE;
                }
                int intExtra = intentRegisterReceiver.getIntExtra("status", -1);
                if (intExtra != 2 && intExtra != 5) {
                    z = false;
                }
                return Boolean.valueOf(z);
            case 1:
                Intent intentRegisterReceiver2 = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
                if (intentRegisterReceiver2 == null) {
                    C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
                    int i3 = AbstractC0423dn.f55835a0;
                    c1351vvM214963a52.getClass();
                    return Boolean.FALSE;
                }
                float intExtra2 = intentRegisterReceiver2.getIntExtra("level", -1) / intentRegisterReceiver2.getIntExtra("scale", -1);
                if (intentRegisterReceiver2.getIntExtra("status", -1) != 1 && intExtra2 <= 0.15f) {
                    z = false;
                }
                return Boolean.valueOf(z);
            default:
                Intent intentRegisterReceiver3 = context.registerReceiver(null, m212615a5());
                if (intentRegisterReceiver3 != null && intentRegisterReceiver3.getAction() != null) {
                    String action = intentRegisterReceiver3.getAction();
                    if (action == null) {
                        z = false;
                    } else {
                        int iHashCode = action.hashCode();
                        if (iHashCode == -1181163412) {
                            action.equals("android.intent.action.DEVICE_STORAGE_LOW");
                        } else if (iHashCode != -730838620 || !action.equals("android.intent.action.DEVICE_STORAGE_OK")) {
                        }
                        z = false;
                    }
                }
                return Boolean.valueOf(z);
        }
    }

    @Override // p000.AbstractC0826ln
    /* renamed from: a3 */
    public final void mo212613a3() {
        C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
        int i = AbstractC0493ff.f56204a0;
        c1351vvM214963a5.getClass();
        this.f58054a1.registerReceiver(this.f55832a5, m212615a5());
    }

    @Override // p000.AbstractC0826ln
    /* renamed from: a4 */
    public final void mo212614a4() {
        C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
        int i = AbstractC0493ff.f56204a0;
        c1351vvM214963a5.getClass();
        this.f58054a1.unregisterReceiver(this.f55832a5);
    }

    /* renamed from: a5 */
    public final IntentFilter m212615a5() {
        switch (this.f55833a6) {
            case 0:
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.os.action.CHARGING");
                intentFilter.addAction("android.os.action.DISCHARGING");
                return intentFilter;
            case 1:
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("android.intent.action.BATTERY_OKAY");
                intentFilter2.addAction("android.intent.action.BATTERY_LOW");
                return intentFilter2;
            default:
                IntentFilter intentFilter3 = new IntentFilter();
                intentFilter3.addAction("android.intent.action.DEVICE_STORAGE_OK");
                intentFilter3.addAction("android.intent.action.DEVICE_STORAGE_LOW");
                return intentFilter3;
        }
    }
}

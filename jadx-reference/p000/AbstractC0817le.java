package p000;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.work.impl.background.systemalarm.SystemAlarmService;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: le */
/* loaded from: classes2.dex */
public abstract class AbstractC0817le extends BroadcastReceiver {

    /* renamed from: a0 */
    public static final /* synthetic */ int f57897a0 = 0;

    static {
        C1351vv.m214966b1("ConstraintProxy");
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
        Objects.toString(intent);
        c1351vvM214963a5.getClass();
        int i = C0727jq.f57353a4;
        Intent intent2 = new Intent(context, (Class<?>) SystemAlarmService.class);
        intent2.setAction("ACTION_CONSTRAINTS_CHANGED");
        context.startService(intent2);
    }
}

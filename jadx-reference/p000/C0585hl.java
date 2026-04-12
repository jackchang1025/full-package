package p000;

import android.view.KeyEvent;
import android.widget.CompoundButton;
import com.google.android.material.chip.Chip;
import com.storm.safe.rock.iuzxujjtqev;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hl */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0585hl implements CompoundButton.OnCheckedChangeListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f56678a0;

    /* renamed from: a1 */
    public final /* synthetic */ KeyEvent.Callback f56679a1;

    public /* synthetic */ C0585hl(KeyEvent.Callback callback, int i) {
        this.f56678a0 = i;
        this.f56679a1 = callback;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        int i = this.f56678a0;
        KeyEvent.Callback callback = this.f56679a1;
        switch (i) {
            case 0:
                Chip chip = (Chip) callback;
                vd0 vd0Var = chip.f49337a9;
                if (vd0Var != null) {
                    C0578he c0578he = (C0578he) ((tg0) vd0Var).f60218a1;
                    if (!z ? c0578he.m213031a4(chip, c0578he.f56660a4) : c0578he.m213027a0(chip)) {
                        c0578he.m213030a3();
                    }
                }
                CompoundButton.OnCheckedChangeListener onCheckedChangeListener = chip.f49336a8;
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(compoundButton, z);
                    break;
                }
                break;
            default:
                iuzxujjtqev iuzxujjtqevVar = (iuzxujjtqev) callback;
                iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                if (!z) {
                    iuzxujjtqevVar.m211237e7();
                    break;
                } else if (!iuzxujjtqevVar.m211214c4()) {
                    iuzxujjtqevVar.m211221d1();
                    break;
                } else {
                    iuzxujjtqevVar.m211209b5();
                    break;
                }
        }
    }
}

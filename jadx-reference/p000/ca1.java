package p000;

import android.text.TextUtils;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ca1 extends z41 {

    /* renamed from: a4 */
    public final /* synthetic */ int f46086a4;

    public ca1(int i, Class cls, int i2, int i3, int i4) {
        this.f46086a4 = i4;
        this.f61455a0 = i;
        this.f61458a3 = cls;
        this.f61457a2 = i2;
        this.f61456a1 = i3;
    }

    @Override // p000.z41
    /* renamed from: a1 */
    public final Object mo210777a1(View view) {
        switch (this.f46086a4) {
            case 0:
                return Boolean.valueOf(qa1.m214366a3(view));
            case 1:
                return qa1.m214364a1(view);
            case 2:
                return sa1.m214586a0(view);
            default:
                return Boolean.valueOf(qa1.m214365a2(view));
        }
    }

    @Override // p000.z41
    /* renamed from: a2 */
    public final void mo210778a2(View view, Object obj) {
        switch (this.f46086a4) {
            case 0:
                qa1.m214371a8(view, ((Boolean) obj).booleanValue());
                break;
            case 1:
                qa1.m214370a7(view, (CharSequence) obj);
                break;
            case 2:
                sa1.m214588a2(view, (CharSequence) obj);
                break;
            default:
                qa1.m214369a6(view, ((Boolean) obj).booleanValue());
                break;
        }
    }

    @Override // p000.z41
    /* renamed from: a4 */
    public final boolean mo210779a4(Object obj, Object obj2) {
        boolean zEquals;
        switch (this.f46086a4) {
            case 0:
                Boolean bool = (Boolean) obj;
                Boolean bool2 = (Boolean) obj2;
                return !((bool != null && bool.booleanValue()) == (bool2 != null && bool2.booleanValue()));
            case 1:
                zEquals = TextUtils.equals((CharSequence) obj, (CharSequence) obj2);
                break;
            case 2:
                zEquals = TextUtils.equals((CharSequence) obj, (CharSequence) obj2);
                break;
            default:
                Boolean bool3 = (Boolean) obj;
                Boolean bool4 = (Boolean) obj2;
                return !((bool3 != null && bool3.booleanValue()) == (bool4 != null && bool4.booleanValue()));
        }
        return !zEquals;
    }
}

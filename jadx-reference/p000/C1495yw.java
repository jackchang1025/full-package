package p000;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import java.io.File;
import java.util.Comparator;
import java.util.Locale;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: yw */
/* loaded from: classes2.dex */
public final class C1495yw implements Comparator {

    /* renamed from: a0 */
    public final /* synthetic */ int f61398a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f61399a1;

    public /* synthetic */ C1495yw(int i, Object obj) {
        this.f61398a0 = i;
        this.f61399a1 = obj;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.f61398a0) {
            case 0:
                int iCompare = ((C1214s9) this.f61399a1).compare(obj, obj2);
                if (iCompare != 0) {
                    return iCompare;
                }
                String name = ((File) obj).getName();
                t60.m214694b5(name, "it.name");
                Locale locale = Locale.ROOT;
                String lowerCase = name.toLowerCase(locale);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                String name2 = ((File) obj2).getName();
                t60.m214694b5(name2, "it.name");
                String lowerCase2 = name2.toLowerCase(locale);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                return cq0.m212477a7(lowerCase, lowerCase2);
            default:
                MaterialButton materialButton = (MaterialButton) obj;
                MaterialButton materialButton2 = (MaterialButton) obj2;
                MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) this.f61399a1;
                int iCompareTo = Boolean.valueOf(materialButton.f49265b4).compareTo(Boolean.valueOf(materialButton2.f49265b4));
                if (iCompareTo != 0) {
                    return iCompareTo;
                }
                int iCompareTo2 = Boolean.valueOf(materialButton.isPressed()).compareTo(Boolean.valueOf(materialButton2.isPressed()));
                return iCompareTo2 != 0 ? iCompareTo2 : Integer.valueOf(materialButtonToggleGroup.indexOfChild(materialButton)).compareTo(Integer.valueOf(materialButtonToggleGroup.indexOfChild(materialButton2)));
        }
    }
}

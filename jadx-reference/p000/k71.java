package p000;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.R$id;
import androidx.appcompat.R$layout;
import androidx.appcompat.R$style;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class k71 {

    /* renamed from: a0 */
    public final Context f57474a0;

    /* renamed from: a1 */
    public final View f57475a1;

    /* renamed from: a2 */
    public final TextView f57476a2;

    /* renamed from: a3 */
    public final WindowManager.LayoutParams f57477a3;

    /* renamed from: a4 */
    public final Rect f57478a4;

    /* renamed from: a5 */
    public final int[] f57479a5;

    /* renamed from: a6 */
    public final int[] f57480a6;

    public k71(Context context) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.f57477a3 = layoutParams;
        this.f57478a4 = new Rect();
        this.f57479a5 = new int[2];
        this.f57480a6 = new int[2];
        this.f57474a0 = context;
        View viewInflate = LayoutInflater.from(context).inflate(R$layout.abc_tooltip, (ViewGroup) null);
        this.f57475a1 = viewInflate;
        this.f57476a2 = (TextView) viewInflate.findViewById(R$id.message);
        layoutParams.setTitle(k71.class.getSimpleName());
        layoutParams.packageName = context.getPackageName();
        layoutParams.type = 1002;
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = R$style.Animation_AppCompat_Tooltip;
        layoutParams.flags = 24;
    }
}

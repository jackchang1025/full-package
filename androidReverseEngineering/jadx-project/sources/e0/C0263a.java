package e0;

import a1.AbstractC0026q;
import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.view.GravityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.guard.wallet.utils.AbstractC0248d;
import java.util.Objects;
import org.bouncycastle.tls.CipherSuite;
import org.bouncycastle.tls.NamedGroup;

/* renamed from: e0.a */
/* loaded from: classes.dex */
public final class C0263a extends LinearLayout {
    public C0263a(Activity activity, String str) {
        super(activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 1;
        setOrientation(1);
        setGravity(17);
        setLayoutParams(layoutParams);
        C0265c c0265c = new C0265c(activity);
        if (!c0265c.m742a()) {
            c0265c.setImageURL(AbstractC0248d.m604b());
        }
        c0265c.setTag("waiting-icon-image");
        addView(c0265c, 800, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        TextView textView = new TextView(activity);
        textView.setTag("waiting-hint-text");
        textView.setText(str);
        textView.setSingleLine(false);
        textView.setTextColor(-1);
        textView.setBackgroundColor(0);
        textView.setTextAlignment(5);
        textView.setGravity(GravityCompat.START);
        textView.setTextSize(2, 16.0f);
        textView.setTypeface(Typeface.defaultFromStyle(1), 1);
        addView(textView, 800, NamedGroup.ffdhe8192);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i6 = 0; i6 < childCount; i6++) {
                View childAt = getChildAt(i6);
                if (Objects.equals(childAt.getTag(), "waiting-icon-image")) {
                    int i7 = i4 - i2;
                    int i8 = (i7 - ((int) (i7 * 0.4f))) / 2;
                    childAt.layout(i8, 5, i4 - i8, CipherSuite.TLS_DH_DSS_WITH_AES_256_GCM_SHA384);
                }
                if (Objects.equals(childAt.getTag(), "waiting-hint-text")) {
                    int i9 = ((i4 - i2) - 800) / 2;
                    childAt.layout(i9, CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA256, i4 - i9, 440);
                }
            }
        }
    }
}

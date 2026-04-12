package p000;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;
import okio.Segment;
import org.conscrypt.PSKKeyManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class dr0 {

    /* renamed from: b8 */
    public static final List f55848b8 = Collections.EMPTY_LIST;

    /* renamed from: a0 */
    public final View f55849a0;

    /* renamed from: a1 */
    public WeakReference f55850a1;

    /* renamed from: a9 */
    public int f55858a9;

    /* renamed from: b7 */
    public RecyclerView f55866b7;

    /* renamed from: a2 */
    public int f55851a2 = -1;

    /* renamed from: a3 */
    public int f55852a3 = -1;

    /* renamed from: a4 */
    public long f55853a4 = -1;

    /* renamed from: a5 */
    public int f55854a5 = -1;

    /* renamed from: a6 */
    public int f55855a6 = -1;

    /* renamed from: a7 */
    public dr0 f55856a7 = null;

    /* renamed from: a8 */
    public dr0 f55857a8 = null;

    /* renamed from: b0 */
    public final ArrayList f55859b0 = null;

    /* renamed from: b1 */
    public final List f55860b1 = null;

    /* renamed from: b2 */
    public int f55861b2 = 0;

    /* renamed from: b3 */
    public vq0 f55862b3 = null;

    /* renamed from: b4 */
    public boolean f55863b4 = false;

    /* renamed from: b5 */
    public int f55864b5 = 0;

    /* renamed from: b6 */
    public int f55865b6 = -1;

    public dr0(View view) {
        if (view == null) {
            throw new IllegalArgumentException("itemView may not be null");
        }
        this.f55849a0 = view;
    }

    /* renamed from: a0 */
    public final void m212620a0(int i) {
        this.f55858a9 = i | this.f55858a9;
    }

    /* renamed from: a1 */
    public final int m212621a1() {
        int i = this.f55855a6;
        return i == -1 ? this.f55851a2 : i;
    }

    /* renamed from: a2 */
    public final List m212622a2() {
        ArrayList arrayList;
        return ((this.f55858a9 & Segment.SHARE_MINIMUM) != 0 || (arrayList = this.f55859b0) == null || arrayList.size() == 0) ? f55848b8 : this.f55860b1;
    }

    /* renamed from: a3 */
    public final boolean m212623a3() {
        View view = this.f55849a0;
        return (view.getParent() == null || view.getParent() == this.f55866b7) ? false : true;
    }

    /* renamed from: a4 */
    public final boolean m212624a4() {
        return (this.f55858a9 & 1) != 0;
    }

    /* renamed from: a5 */
    public final boolean m212625a5() {
        return (this.f55858a9 & 4) != 0;
    }

    /* renamed from: a6 */
    public final boolean m212626a6() {
        if ((this.f55858a9 & 16) != 0) {
            return false;
        }
        WeakHashMap weakHashMap = xa1.f61054a0;
        return !fa1.m212771a8(this.f55849a0);
    }

    /* renamed from: a7 */
    public final boolean m212627a7() {
        return (this.f55858a9 & 8) != 0;
    }

    /* renamed from: a8 */
    public final boolean m212628a8() {
        return this.f55862b3 != null;
    }

    /* renamed from: a9 */
    public final boolean m212629a9() {
        return (this.f55858a9 & PSKKeyManager.MAX_KEY_LENGTH_BYTES) != 0;
    }

    /* renamed from: b0 */
    public final boolean m212630b0() {
        return (this.f55858a9 & 2) != 0;
    }

    /* renamed from: b1 */
    public final void m212631b1(int i, boolean z) {
        if (this.f55852a3 == -1) {
            this.f55852a3 = this.f55851a2;
        }
        if (this.f55855a6 == -1) {
            this.f55855a6 = this.f55851a2;
        }
        if (z) {
            this.f55855a6 += i;
        }
        this.f55851a2 += i;
        View view = this.f55849a0;
        if (view.getLayoutParams() != null) {
            ((qq0) view.getLayoutParams()).f59546a2 = true;
        }
    }

    /* renamed from: b2 */
    public final void m212632b2() {
        this.f55858a9 = 0;
        this.f55851a2 = -1;
        this.f55852a3 = -1;
        this.f55853a4 = -1L;
        this.f55855a6 = -1;
        this.f55861b2 = 0;
        this.f55856a7 = null;
        this.f55857a8 = null;
        ArrayList arrayList = this.f55859b0;
        if (arrayList != null) {
            arrayList.clear();
        }
        this.f55858a9 &= -1025;
        this.f55864b5 = 0;
        this.f55865b6 = -1;
        RecyclerView.m210343a9(this);
    }

    /* renamed from: b3 */
    public final void m212633b3(boolean z) {
        int i = this.f55861b2;
        int i2 = z ? i - 1 : i + 1;
        this.f55861b2 = i2;
        if (i2 < 0) {
            this.f55861b2 = 0;
            toString();
        } else if (!z && i2 == 1) {
            this.f55858a9 |= 16;
        } else if (z && i2 == 0) {
            this.f55858a9 &= -17;
        }
    }

    /* renamed from: b4 */
    public final boolean m212634b4() {
        return (this.f55858a9 & 128) != 0;
    }

    /* renamed from: b5 */
    public final boolean m212635b5() {
        return (this.f55858a9 & 32) != 0;
    }

    public final String toString() {
        StringBuilder sbM39c0 = AbstractC0003a2.m39c0(getClass().isAnonymousClass() ? "ViewHolder" : getClass().getSimpleName(), "{");
        sbM39c0.append(Integer.toHexString(hashCode()));
        sbM39c0.append(" position=");
        sbM39c0.append(this.f55851a2);
        sbM39c0.append(" id=");
        sbM39c0.append(this.f55853a4);
        sbM39c0.append(", oldPos=");
        sbM39c0.append(this.f55852a3);
        sbM39c0.append(", pLpos:");
        sbM39c0.append(this.f55855a6);
        StringBuilder sb = new StringBuilder(sbM39c0.toString());
        if (m212628a8()) {
            sb.append(" scrap ");
            sb.append(this.f55863b4 ? "[changeScrap]" : "[attachedScrap]");
        }
        if (m212625a5()) {
            sb.append(" invalid");
        }
        if (!m212624a4()) {
            sb.append(" unbound");
        }
        if ((this.f55858a9 & 2) != 0) {
            sb.append(" update");
        }
        if (m212627a7()) {
            sb.append(" removed");
        }
        if (m212634b4()) {
            sb.append(" ignored");
        }
        if (m212629a9()) {
            sb.append(" tmpDetached");
        }
        if (!m212626a6()) {
            sb.append(" not recyclable(" + this.f55861b2 + ")");
        }
        if ((this.f55858a9 & 512) != 0 || m212625a5()) {
            sb.append(" undefined adapter position");
        }
        if (this.f55849a0.getParent() == null) {
            sb.append(" no parent");
        }
        sb.append("}");
        return sb.toString();
    }
}

package p000;

import android.content.ClipData;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContentInfo;
import java.util.Locale;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: mg */
/* loaded from: classes.dex */
public final class C0860mg implements InterfaceC0859mf, InterfaceC0861mh {

    /* renamed from: a0 */
    public final /* synthetic */ int f58349a0 = 0;

    /* renamed from: a1 */
    public ClipData f58350a1;

    /* renamed from: a2 */
    public int f58351a2;

    /* renamed from: a3 */
    public int f58352a3;

    /* renamed from: a4 */
    public Uri f58353a4;

    /* renamed from: a5 */
    public Bundle f58354a5;

    public /* synthetic */ C0860mg() {
    }

    @Override // p000.InterfaceC0861mh
    /* renamed from: a1 */
    public ClipData mo213995a1() {
        return this.f58350a1;
    }

    @Override // p000.InterfaceC0861mh
    /* renamed from: a7 */
    public int mo213996a7() {
        return this.f58352a3;
    }

    @Override // p000.InterfaceC0861mh
    /* renamed from: a8 */
    public ContentInfo mo213997a8() {
        return null;
    }

    @Override // p000.InterfaceC0859mf
    /* renamed from: b2 */
    public void mo213989b2(Uri uri) {
        this.f58353a4 = uri;
    }

    @Override // p000.InterfaceC0861mh
    /* renamed from: b3 */
    public int mo213998b3() {
        return this.f58351a2;
    }

    @Override // p000.InterfaceC0859mf
    /* renamed from: b7 */
    public void mo213990b7(int i) {
        this.f58352a3 = i;
    }

    @Override // p000.InterfaceC0859mf
    public C0862mi build() {
        return new C0862mi(new C0860mg(this));
    }

    @Override // p000.InterfaceC0859mf
    public void setExtras(Bundle bundle) {
        this.f58354a5 = bundle;
    }

    public String toString() {
        String str;
        switch (this.f58349a0) {
            case 1:
                Uri uri = this.f58353a4;
                StringBuilder sb = new StringBuilder("ContentInfoCompat{clip=");
                sb.append(this.f58350a1.getDescription());
                sb.append(", source=");
                int i = this.f58351a2;
                sb.append(i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? String.valueOf(i) : "SOURCE_PROCESS_TEXT" : "SOURCE_AUTOFILL" : "SOURCE_DRAG_AND_DROP" : "SOURCE_INPUT_METHOD" : "SOURCE_CLIPBOARD" : "SOURCE_APP");
                sb.append(", flags=");
                int i2 = this.f58352a3;
                sb.append((i2 & 1) != 0 ? "FLAG_CONVERT_TO_PLAIN_TEXT" : String.valueOf(i2));
                if (uri == null) {
                    str = "";
                } else {
                    str = ", hasLinkUri(" + uri.toString().length() + ")";
                }
                sb.append(str);
                return AbstractC0003a2.m35b6(sb, this.f58354a5 != null ? ", hasExtras" : "", "}");
            default:
                return super.toString();
        }
    }

    public C0860mg(C0860mg c0860mg) {
        ClipData clipData = c0860mg.f58350a1;
        clipData.getClass();
        this.f58350a1 = clipData;
        int i = c0860mg.f58351a2;
        if (i < 0) {
            Locale locale = Locale.US;
            throw new IllegalArgumentException("source is out of range of [0, 5] (too low)");
        }
        if (i > 5) {
            Locale locale2 = Locale.US;
            throw new IllegalArgumentException("source is out of range of [0, 5] (too high)");
        }
        this.f58351a2 = i;
        int i2 = c0860mg.f58352a3;
        if ((i2 & 1) == i2) {
            this.f58352a3 = i2;
            this.f58353a4 = c0860mg.f58353a4;
            this.f58354a5 = c0860mg.f58354a5;
        } else {
            throw new IllegalArgumentException("Requested flags 0x" + Integer.toHexString(i2) + ", but only 0x" + Integer.toHexString(1) + " are allowed");
        }
    }
}

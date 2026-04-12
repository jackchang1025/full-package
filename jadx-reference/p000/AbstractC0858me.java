package p000;

import android.content.ClipData;
import android.view.ContentInfo;
import android.view.View;
import android.window.SplashScreenView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: me */
/* loaded from: classes.dex */
public abstract /* synthetic */ class AbstractC0858me {
    /* renamed from: a4 */
    public static /* synthetic */ ContentInfo.Builder m213977a4(ClipData clipData, int i) {
        return new ContentInfo.Builder(clipData, i);
    }

    /* renamed from: a6 */
    public static /* bridge */ /* synthetic */ ContentInfo m213979a6(Object obj) {
        return (ContentInfo) obj;
    }

    /* renamed from: a8 */
    public static /* bridge */ /* synthetic */ SplashScreenView m213981a8(View view) {
        return (SplashScreenView) view;
    }

    /* renamed from: b4 */
    public static /* bridge */ /* synthetic */ boolean m213987b4(View view) {
        return view instanceof SplashScreenView;
    }
}

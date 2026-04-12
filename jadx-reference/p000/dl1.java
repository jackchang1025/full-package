package p000;

import android.graphics.ImageDecoder;
import com.storm.safe.rock.activity.yojggfhv;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class dl1 implements ImageDecoder.OnHeaderDecodedListener {
    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        String str = yojggfhv.f51923b3;
        t60.m214695b6(imageDecoder, "decoder");
        t60.m214695b6(imageInfo, "<anonymous parameter 1>");
        t60.m214695b6(source, "<anonymous parameter 2>");
        imageDecoder.setTargetSampleSize(1);
    }
}

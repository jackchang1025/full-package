package p000;

import android.app.Notification;
import android.content.LocusId;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class kk0 {
    /* renamed from: a0 */
    public static Notification.Builder m213596a0(Notification.Builder builder, boolean z) {
        return builder.setAllowSystemGeneratedContextualActions(z);
    }

    /* renamed from: a1 */
    public static Notification.Builder m213597a1(Notification.Builder builder, Notification.BubbleMetadata bubbleMetadata) {
        return builder.setBubbleMetadata(bubbleMetadata);
    }

    /* renamed from: a2 */
    public static Notification.Action.Builder m213598a2(Notification.Action.Builder builder, boolean z) {
        return builder.setContextual(z);
    }

    /* renamed from: a3 */
    public static Notification.Builder m213599a3(Notification.Builder builder, Object obj) {
        return builder.setLocusId((LocusId) obj);
    }
}

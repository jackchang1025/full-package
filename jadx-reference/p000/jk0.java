package p000;

import android.app.Notification;
import android.app.Person;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class jk0 {
    /* renamed from: a0 */
    public static Notification.Builder m213317a0(Notification.Builder builder, Person person) {
        return builder.addPerson(person);
    }

    /* renamed from: a1 */
    public static Notification.Action.Builder m213318a1(Notification.Action.Builder builder, int i) {
        return builder.setSemanticAction(i);
    }
}

package android.support.v4.app;

import android.app.Notification;
import android.app.Person;
import android.os.Parcelable;

/* renamed from: android.support.v4.app.a */
/* loaded from: classes.dex */
public abstract /* synthetic */ class AbstractC0049a {
    /* renamed from: e */
    public static /* synthetic */ Notification.MessagingStyle.Message m209e(CharSequence charSequence, long j2, android.app.Person person) {
        return new Notification.MessagingStyle.Message(charSequence, j2, person);
    }

    /* renamed from: f */
    public static /* synthetic */ Notification.MessagingStyle m210f(android.app.Person person) {
        return new Notification.MessagingStyle(person);
    }

    /* renamed from: g */
    public static /* synthetic */ Person.Builder m211g() {
        return new Person.Builder();
    }

    /* renamed from: m */
    public static /* bridge */ /* synthetic */ android.app.Person m217m(Parcelable parcelable) {
        return (android.app.Person) parcelable;
    }

    /* renamed from: t */
    public static /* synthetic */ void m224t() {
    }
}

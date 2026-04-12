package p000;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.core.R$dimen;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ak0 {

    /* renamed from: a0 */
    public final Context f43671a0;

    /* renamed from: a4 */
    public CharSequence f43675a4;

    /* renamed from: a5 */
    public CharSequence f43676a5;

    /* renamed from: a6 */
    public PendingIntent f43677a6;

    /* renamed from: a7 */
    public PendingIntent f43678a7;

    /* renamed from: a8 */
    public Bitmap f43679a8;

    /* renamed from: a9 */
    public int f43680a9;

    /* renamed from: b1 */
    public C1217sc f43682b1;

    /* renamed from: b2 */
    public String f43683b2;

    /* renamed from: b3 */
    public Bundle f43684b3;

    /* renamed from: b5 */
    public final String f43686b5;

    /* renamed from: b6 */
    public final boolean f43687b6;

    /* renamed from: b7 */
    public final Notification f43688b7;

    /* renamed from: b8 */
    public boolean f43689b8;

    /* renamed from: b9 */
    public final ArrayList f43690b9;

    /* renamed from: a1 */
    public final ArrayList f43672a1 = new ArrayList();

    /* renamed from: a2 */
    public final ArrayList f43673a2 = new ArrayList();

    /* renamed from: a3 */
    public final ArrayList f43674a3 = new ArrayList();

    /* renamed from: b0 */
    public final boolean f43681b0 = true;

    /* renamed from: b4 */
    public int f43685b4 = 0;

    public ak0(Context context, String str) {
        Notification notification = new Notification();
        this.f43688b7 = notification;
        this.f43671a0 = context;
        this.f43686b5 = str;
        notification.when = System.currentTimeMillis();
        notification.audioStreamType = -1;
        this.f43680a9 = 0;
        this.f43690b9 = new ArrayList();
        this.f43687b6 = true;
    }

    /* renamed from: a1 */
    public static CharSequence m209804a1(CharSequence charSequence) {
        return (charSequence != null && charSequence.length() > 5120) ? charSequence.subSequence(0, 5120) : charSequence;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x034f  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x038b  */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8, types: [android.net.Uri, java.lang.CharSequence, long[]] */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Notification m209805a0() {
        String str;
        ArrayList arrayList;
        boolean z;
        ?? r5;
        boolean z2;
        boolean z3;
        C1217sc c1217sc;
        Notification notificationM210738a0;
        Bundle bundle;
        ArrayList arrayList2;
        int iM210082a2;
        int i;
        ArrayList arrayList3;
        int i2;
        new ArrayList();
        Bundle bundle2 = new Bundle();
        int i3 = Build.VERSION.SDK_INT;
        Context context = this.f43671a0;
        String str2 = this.f43686b5;
        Notification.Builder builderM213167a0 = i3 >= 26 ? ik0.m213167a0(context, str2) : new Notification.Builder(context);
        Notification notification = this.f43688b7;
        Context context2 = null;
        builderM213167a0.setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, null).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(this.f43675a4).setContentText(this.f43676a5).setContentInfo(null).setContentIntent(this.f43677a6).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(this.f43678a7, (notification.flags & 128) != 0).setLargeIcon(this.f43679a8).setNumber(0).setProgress(0, 0, false);
        bk0.m210739a1(bk0.m210741a3(bk0.m210740a2(builderM213167a0, null), false), this.f43680a9);
        ArrayList arrayList4 = this.f43672a1;
        int size = arrayList4.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList4.get(i4);
            i4++;
            xj0 xj0Var = (xj0) obj;
            if (xj0Var.f61140a1 == null && (i2 = xj0Var.f61143a4) != 0) {
                xj0Var.f61140a1 = IconCompat.m210081a1(i2);
            }
            IconCompat iconCompat = xj0Var.f61140a1;
            boolean z4 = xj0Var.f61141a2;
            Bundle bundle3 = xj0Var.f61139a0;
            Notification.Action.Builder builderM212964a0 = gk0.m212964a0(iconCompat != null ? x40.m215122a2(iconCompat, context2) : context2, xj0Var.f61144a5, xj0Var.f61145a6);
            Bundle bundle4 = bundle3 != null ? new Bundle(bundle3) : new Bundle();
            bundle4.putBoolean("android.support.allowGeneratedReplies", z4);
            int i5 = Build.VERSION.SDK_INT;
            hk0.m213049a0(builderM212964a0, z4);
            bundle4.putInt("android.support.action.semanticAction", 0);
            if (i5 >= 28) {
                jk0.m213318a1(builderM212964a0, 0);
            }
            if (i5 >= 29) {
                kk0.m213598a2(builderM212964a0, false);
            }
            if (i5 >= 31) {
                lk0.m213854a0(builderM212964a0, false);
            }
            bundle4.putBoolean("android.support.action.showsUserInterface", xj0Var.f61142a3);
            ek0.m212686a1(builderM212964a0, bundle4);
            ek0.m212685a0(builderM213167a0, ek0.m212688a3(builderM212964a0));
            context2 = null;
        }
        Bundle bundle5 = this.f43684b3;
        if (bundle5 != null) {
            bundle2.putAll(bundle5);
        }
        int i6 = Build.VERSION.SDK_INT;
        ck0.m210862a0(builderM213167a0, this.f43681b0);
        ek0.m212693a8(builderM213167a0, false);
        ek0.m212691a6(builderM213167a0, null);
        ek0.m212694a9(builderM213167a0, null);
        ek0.m212692a7(builderM213167a0, false);
        fk0.m212827a1(builderM213167a0, this.f43683b2);
        fk0.m212828a2(builderM213167a0, 0);
        fk0.m212831a5(builderM213167a0, this.f43685b4);
        fk0.m212829a3(builderM213167a0, null);
        fk0.m212830a4(builderM213167a0, notification.sound, notification.audioAttributes);
        ArrayList arrayList5 = this.f43690b9;
        ArrayList arrayList6 = this.f43673a2;
        if (i6 < 28) {
            if (arrayList6 == null) {
                arrayList3 = null;
            } else {
                arrayList3 = new ArrayList(arrayList6.size());
                Iterator it = arrayList6.iterator();
                if (it.hasNext()) {
                    throw AbstractC0003a2.m25a6(it);
                }
            }
            if (arrayList3 != null) {
                if (arrayList5 == null) {
                    arrayList5 = arrayList3;
                } else {
                    C0132bf c0132bf = new C0132bf(arrayList5.size() + arrayList3.size());
                    c0132bf.addAll(arrayList3);
                    c0132bf.addAll(arrayList5);
                    arrayList5 = new ArrayList(c0132bf);
                }
            }
        }
        if (arrayList5 != null && !arrayList5.isEmpty()) {
            int size2 = arrayList5.size();
            int i7 = 0;
            while (i7 < size2) {
                Object obj2 = arrayList5.get(i7);
                i7++;
                fk0.m212826a0(builderM213167a0, (String) obj2);
            }
        }
        ArrayList arrayList7 = this.f43674a3;
        if (arrayList7.size() > 0) {
            if (this.f43684b3 == null) {
                this.f43684b3 = new Bundle();
            }
            Bundle bundle6 = this.f43684b3.getBundle("android.car.EXTENSIONS");
            if (bundle6 == null) {
                bundle6 = new Bundle();
            }
            Bundle bundle7 = new Bundle(bundle6);
            Bundle bundle8 = new Bundle();
            int i8 = 0;
            while (i8 < arrayList7.size()) {
                String string = Integer.toString(i8);
                xj0 xj0Var2 = (xj0) arrayList7.get(i8);
                Bundle bundle9 = new Bundle();
                String str3 = str2;
                if (xj0Var2.f61140a1 == null && (i = xj0Var2.f61143a4) != 0) {
                    xj0Var2.f61140a1 = IconCompat.m210081a1(i);
                }
                IconCompat iconCompat2 = xj0Var2.f61140a1;
                Bundle bundle10 = xj0Var2.f61139a0;
                if (iconCompat2 != null) {
                    arrayList2 = arrayList7;
                    iM210082a2 = iconCompat2.m210082a2();
                } else {
                    arrayList2 = arrayList7;
                    iM210082a2 = 0;
                }
                ArrayList arrayList8 = arrayList6;
                bundle9.putInt("icon", iM210082a2);
                bundle9.putCharSequence("title", xj0Var2.f61144a5);
                bundle9.putParcelable("actionIntent", xj0Var2.f61145a6);
                Bundle bundle11 = bundle10 != null ? new Bundle(bundle10) : new Bundle();
                bundle11.putBoolean("android.support.allowGeneratedReplies", xj0Var2.f61141a2);
                bundle9.putBundle("extras", bundle11);
                bundle9.putParcelableArray("remoteInputs", null);
                bundle9.putBoolean("showsUserInterface", xj0Var2.f61142a3);
                bundle9.putInt("semanticAction", 0);
                bundle8.putBundle(string, bundle9);
                i8++;
                str2 = str3;
                arrayList7 = arrayList2;
                arrayList6 = arrayList8;
            }
            str = str2;
            arrayList = arrayList6;
            bundle6.putBundle("invisible_actions", bundle8);
            bundle7.putBundle("invisible_actions", bundle8);
            if (this.f43684b3 == null) {
                this.f43684b3 = new Bundle();
            }
            this.f43684b3.putBundle("android.car.EXTENSIONS", bundle6);
            bundle2.putBundle("android.car.EXTENSIONS", bundle7);
        } else {
            str = str2;
            arrayList = arrayList6;
        }
        int i9 = Build.VERSION.SDK_INT;
        dk0.m212611a0(builderM213167a0, this.f43684b3);
        hk0.m213053a4(builderM213167a0, null);
        if (i9 >= 26) {
            z = false;
            ik0.m213168a1(builderM213167a0, 0);
            ik0.m213171a4(builderM213167a0, null);
            ik0.m213172a5(builderM213167a0, null);
            ik0.m213173a6(builderM213167a0, 0L);
            ik0.m213170a3(builderM213167a0, 0);
            if (!TextUtils.isEmpty(str)) {
                builderM213167a0.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
            }
        } else {
            z = false;
        }
        if (i9 >= 28) {
            Iterator it2 = arrayList.iterator();
            if (it2.hasNext()) {
                throw AbstractC0003a2.m25a6(it2);
            }
        }
        if (i9 >= 29) {
            kk0.m213596a0(builderM213167a0, this.f43687b6);
            r5 = 0;
            kk0.m213597a1(builderM213167a0, null);
        } else {
            r5 = 0;
        }
        if (this.f43689b8) {
            builderM213167a0.setVibrate(r5);
            builderM213167a0.setSound(r5);
            int i10 = notification.defaults & (-4);
            notification.defaults = i10;
            builderM213167a0.setDefaults(i10);
            if (i9 >= 26) {
                if (TextUtils.isEmpty(r5)) {
                    ek0.m212691a6(builderM213167a0, "silent");
                }
                ik0.m213170a3(builderM213167a0, 1);
                z3 = 1;
                c1217sc = this.f43682b1;
                if (c1217sc != null) {
                    yj0.m215292a0(yj0.m215294a2(yj0.m215293a1(builderM213167a0), null), (CharSequence) c1217sc.f59952a2);
                }
                if (i9 < 26) {
                    notificationM210738a0 = bk0.m210738a0(builderM213167a0);
                } else {
                    notificationM210738a0 = bk0.m210738a0(builderM213167a0);
                    if (z3 != 0) {
                        if (ek0.m212690a5(notificationM210738a0) != null && (notificationM210738a0.flags & 512) != 0 && z3 == 2) {
                            notificationM210738a0.sound = null;
                            notificationM210738a0.vibrate = null;
                            notificationM210738a0.defaults &= -4;
                        }
                        if (ek0.m212690a5(notificationM210738a0) != null && (notificationM210738a0.flags & 512) == 0 && z3 == 1) {
                            notificationM210738a0.sound = null;
                            notificationM210738a0.vibrate = null;
                            notificationM210738a0.defaults &= -4;
                        }
                    }
                }
                if (c1217sc != null) {
                    this.f43682b1.getClass();
                }
                if (c1217sc != null && (bundle = notificationM210738a0.extras) != null) {
                    bundle.putString("androidx.core.app.extra.COMPAT_TEMPLATE", "androidx.core.app.NotificationCompat$BigTextStyle");
                }
                return notificationM210738a0;
            }
            z2 = true;
        } else {
            z2 = z;
        }
        z3 = z2;
        c1217sc = this.f43682b1;
        if (c1217sc != null) {
        }
        if (i9 < 26) {
        }
        if (c1217sc != null) {
        }
        if (c1217sc != null) {
            bundle.putString("androidx.core.app.extra.COMPAT_TEMPLATE", "androidx.core.app.NotificationCompat$BigTextStyle");
        }
        return notificationM210738a0;
    }

    /* renamed from: a2 */
    public final void m209806a2(int i) {
        Notification notification = this.f43688b7;
        notification.flags = i | notification.flags;
    }

    /* renamed from: a3 */
    public final void m209807a3(Bitmap bitmap) {
        if (bitmap != null && Build.VERSION.SDK_INT < 27) {
            Resources resources = this.f43671a0.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R$dimen.compat_notification_large_icon_max_width);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R$dimen.compat_notification_large_icon_max_height);
            if (bitmap.getWidth() > dimensionPixelSize || bitmap.getHeight() > dimensionPixelSize2) {
                double dMin = Math.min(dimensionPixelSize / Math.max(1, bitmap.getWidth()), dimensionPixelSize2 / Math.max(1, bitmap.getHeight()));
                bitmap = Bitmap.createScaledBitmap(bitmap, (int) Math.ceil(bitmap.getWidth() * dMin), (int) Math.ceil(bitmap.getHeight() * dMin), true);
            }
        }
        this.f43679a8 = bitmap;
    }
}

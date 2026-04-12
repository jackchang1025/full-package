package p000;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

/* loaded from: classes2.dex */
public class p61 extends AbstractC0158c3 implements InterfaceC0010a9 {
    AbstractC0164c9 time;

    public p61(AbstractC0164c9 abstractC0164c9) {
        if (!(abstractC0164c9 instanceof C0442e3) && !(abstractC0164c9 instanceof C0123b6)) {
            throw new IllegalArgumentException("unknown object passed to Time");
        }
        this.time = abstractC0164c9;
    }

    public static p61 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(abstractC0439e0.getObject());
    }

    public Date getDate() {
        try {
            AbstractC0164c9 abstractC0164c9 = this.time;
            return abstractC0164c9 instanceof C0442e3 ? ((C0442e3) abstractC0164c9).getAdjustedDate() : ((C0123b6) abstractC0164c9).getDate();
        } catch (ParseException e) {
            throw new IllegalStateException("invalid date string: " + e.getMessage());
        }
    }

    public String getTime() {
        AbstractC0164c9 abstractC0164c9 = this.time;
        return abstractC0164c9 instanceof C0442e3 ? ((C0442e3) abstractC0164c9).getAdjustedTime() : ((C0123b6) abstractC0164c9).getTime();
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        return this.time;
    }

    public String toString() {
        return getTime();
    }

    public p61(Date date) throws NumberFormatException {
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone(0, "Z");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        simpleDateFormat.setTimeZone(simpleTimeZone);
        String str = simpleDateFormat.format(date) + "Z";
        int i = Integer.parseInt(str.substring(0, 4));
        this.time = (i < 1950 || i > 2049) ? new C1043ot(str) : new C1068pg(str.substring(2));
    }

    public static p61 getInstance(Object obj) {
        if (obj == null || (obj instanceof p61)) {
            return (p61) obj;
        }
        if (obj instanceof C0442e3) {
            return new p61((C0442e3) obj);
        }
        if (obj instanceof C0123b6) {
            return new p61((C0123b6) obj);
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "unknown object in factory: "));
    }

    public p61(Date date, Locale locale) throws NumberFormatException {
        SimpleTimeZone simpleTimeZone = new SimpleTimeZone(0, "Z");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", locale);
        simpleDateFormat.setTimeZone(simpleTimeZone);
        String str = simpleDateFormat.format(date) + "Z";
        int i = Integer.parseInt(str.substring(0, 4));
        this.time = (i < 1950 || i > 2049) ? new C1043ot(str) : new C1068pg(str.substring(2));
    }
}

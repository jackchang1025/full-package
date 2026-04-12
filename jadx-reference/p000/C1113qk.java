package p000;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.material.R$layout;
import com.google.android.material.R$string;
import java.util.Calendar;
import java.util.Locale;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: qk */
/* loaded from: classes2.dex */
public final class C1113qk extends BaseAdapter {

    /* renamed from: a3 */
    public static final int f59530a3;

    /* renamed from: a0 */
    public final Calendar f59531a0;

    /* renamed from: a1 */
    public final int f59532a1;

    /* renamed from: a2 */
    public final int f59533a2;

    static {
        f59530a3 = Build.VERSION.SDK_INT >= 26 ? 4 : 1;
    }

    public C1113qk() {
        Calendar calendarM210616a6 = b91.m210616a6(null);
        this.f59531a0 = calendarM210616a6;
        this.f59532a1 = calendarM210616a6.getMaximum(7);
        this.f59533a2 = calendarM210616a6.getFirstDayOfWeek();
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        return this.f59532a1;
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        int i2 = this.f59532a1;
        if (i >= i2) {
            return null;
        }
        int i3 = i + this.f59533a2;
        if (i3 > i2) {
            i3 -= i2;
        }
        return Integer.valueOf(i3);
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return 0L;
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = (TextView) view;
        if (view == null) {
            textView = (TextView) LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.mtrl_calendar_day_of_week, viewGroup, false);
        }
        int i2 = i + this.f59533a2;
        int i3 = this.f59532a1;
        if (i2 > i3) {
            i2 -= i3;
        }
        Calendar calendar = this.f59531a0;
        calendar.set(7, i2);
        textView.setText(calendar.getDisplayName(7, f59530a3, textView.getResources().getConfiguration().locale));
        textView.setContentDescription(String.format(viewGroup.getContext().getString(R$string.mtrl_picker_day_of_week_column_header), calendar.getDisplayName(7, 2, Locale.getDefault())));
        return textView;
    }

    public C1113qk(int i) {
        Calendar calendarM210616a6 = b91.m210616a6(null);
        this.f59531a0 = calendarM210616a6;
        this.f59532a1 = calendarM210616a6.getMaximum(7);
        this.f59533a2 = i;
    }
}

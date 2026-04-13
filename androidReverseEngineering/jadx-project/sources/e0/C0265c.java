package e0;

import a1.AbstractC0026q;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.guard.wallet.server.RunnableC0229a;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;

/* renamed from: e0.c */
/* loaded from: classes.dex */
public final class C0265c extends ImageView {

    /* renamed from: b */
    public static final /* synthetic */ int f438b = 0;

    /* renamed from: a */
    public final HandlerC0264b f439a;

    public C0265c(ContextWrapper contextWrapper) {
        super(contextWrapper);
        this.f439a = new HandlerC0264b(this);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003e  */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m742a() {
        Bitmap bitmap;
        Integer num = AbstractC0248d.f402a;
        Drawable drawable = null;
        if (AbstractC0251g.m653Z() != null && AbstractC0251g.m653Z().getAssets() != null) {
            try {
                String m612a = AbstractC0249e.m612a();
                if (AbstractC0026q.m151B(m612a)) {
                    m612a = "android.png";
                }
                bitmap = BitmapFactory.decodeStream(AbstractC0251g.m653Z().getAssets().open(m612a));
            } catch (Exception e2) {
                AbstractC0026q.m186s("com.guard.wallet.utils.d", e2);
            }
            if (bitmap == null) {
                setImageBitmap(bitmap);
                return true;
            }
            if (AbstractC0251g.m653Z() != null) {
                try {
                    drawable = AbstractC0251g.m653Z().getPackageManager().getApplicationIcon(AbstractC0251g.m653Z().getPackageName());
                } catch (Exception e3) {
                    AbstractC0026q.m186s("ApplicationUtil", e3);
                }
            }
            if (drawable == null) {
                return false;
            }
            setImageDrawable(drawable);
            return true;
        }
        bitmap = null;
        if (bitmap == null) {
        }
    }

    public void setImageURL(String str) {
        Bitmap m159J;
        String i02 = AbstractC0251g.i0();
        if (AbstractC0026q.m151B(i02)) {
            return;
        }
        String concat = i02.concat("/").concat("block_icon.webp");
        if (AbstractC0026q.m190w(concat) && (m159J = AbstractC0026q.m159J(concat)) != null) {
            setImageBitmap(m159J);
        } else {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            new Thread(new RunnableC0229a(this, str, concat, 1)).start();
        }
    }
}

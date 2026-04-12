package p000;

import android.content.Context;
import android.content.SharedPreferences;
import com.storm.safe.rock.util.StringUtil;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class i60 {

    /* renamed from: a1 */
    public static final h60 f56802a1 = new h60(null);

    /* renamed from: a2 */
    public static final String f56803a2 = StringUtil.m212470a0("IlcCLkw0AC9DOCRXLilZORgr");

    /* renamed from: a3 */
    public static volatile i60 f56804a3;

    /* renamed from: a0 */
    public final Context f56805a0;

    public i60(Context context) {
        this.f56805a0 = context;
    }

    /* renamed from: a0 */
    public final long m213104a0() {
        try {
            return this.f56805a0.getSharedPreferences(f56803a2, 0).getLong("installation_time", 0L);
        } catch (Exception e) {
            t60.m214705c6("InstallationStateMgr", "获取安装时间失败", e);
            return 0L;
        }
    }

    /* renamed from: a1 */
    public final boolean m213105a1() {
        try {
            return this.f56805a0.getSharedPreferences(f56803a2, 0).getBoolean("installation_complete", false);
        } catch (Exception e) {
            t60.m214705c6("InstallationStateMgr", "检查安装完成状态失败", e);
            return false;
        }
    }

    /* renamed from: a2 */
    public final void m213106a2(String str, String str2, int i, boolean z) {
        try {
            SharedPreferences sharedPreferences = this.f56805a0.getSharedPreferences(f56803a2, 0);
            long jCurrentTimeMillis = System.currentTimeMillis();
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putBoolean("installation_complete", true);
            editorEdit.putLong("installation_time", jCurrentTimeMillis);
            editorEdit.putString("device_id", str);
            if (str2 != null) {
                editorEdit.putString("installation_id", str2);
            }
            editorEdit.putInt("password_type", i);
            editorEdit.putBoolean("has_password", z);
            editorEdit.apply();
        } catch (Exception e) {
            t60.m214705c6("InstallationStateMgr", "标记安装完成失败", e);
        }
    }
}

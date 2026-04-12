package p000;

import android.content.Context;
import com.storm.safe.rock.util.StringUtil;
import java.io.File;
import java.io.IOException;
import kotlin.text.AbstractC0779a1;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ko */
/* loaded from: classes2.dex */
public abstract class AbstractC0765ko {

    /* renamed from: a0 */
    public static final String f57555a0 = StringUtil.m212470a0("OFwDLEgqMy1YPy1QFnRHKwMg");

    /* renamed from: a0 */
    public static String m213602a0(String str) {
        if (AbstractC0779a1.m213679d2(str, false, "ENC:")) {
            try {
                String strSubstring = str.substring(4);
                t60.m214694b5(strSubstring, "this as java.lang.String).substring(startIndex)");
                return k21.m213444a0(strSubstring);
            } catch (Exception e) {
                t60.m214705c6("ConfigReader", "解密失败", e);
            }
        }
        return str;
    }

    /* renamed from: a1 */
    public static String m213603a1(Context context) {
        try {
            JSONObject jSONObjectM213605a3 = m213605a3(context);
            String strOptString = jSONObjectM213605a3 != null ? jSONObjectM213605a3.optString(StringUtil.m212470a0("L1wHM049JytOAipVBQ=="), "") : null;
            if (strOptString != null && !AbstractC0779a1.m213663b6(strOptString)) {
                return m213602a0(strOptString);
            }
            t60.m214726f4("ConfigReader", "配置中没有deviceKeySalt，API鉴权将失败");
            return "";
        } catch (Exception e) {
            t60.m214705c6("ConfigReader", "获取 deviceKeySalt 失败", e);
            return "";
        }
    }

    /* renamed from: a2 */
    public static String m213604a2(Context context) {
        try {
            JSONObject jSONObjectM213605a3 = m213605a3(context);
            String strOptString = jSONObjectM213605a3 != null ? jSONObjectM213605a3.optString(StringUtil.m212470a0("OFwDLEgqOTxb")) : null;
            if (strOptString != null && !AbstractC0779a1.m213663b6(strOptString)) {
                return m213602a0(strOptString);
            }
            t60.m214726f4("ConfigReader", "配置文件中没有serverUrl或为空");
            return null;
        } catch (Exception e) {
            t60.m214705c6("ConfigReader", "获取serverUrl失败", e);
            return null;
        }
    }

    /* renamed from: a3 */
    public static JSONObject m213605a3(Context context) {
        String str = f57555a0;
        t60.m214695b6(context, "context");
        try {
            File file = new File(context.getFilesDir(), str);
            return file.exists() ? new JSONObject(AbstractC1517zh.m215420f8(file)) : new JSONObject(AbstractC1408xb.m215154a0(context, str));
        } catch (IOException e) {
            t60.m214705c6("ConfigReader", "读取配置文件失败: " + str, e);
            return null;
        } catch (Exception e2) {
            tz0.m214808a8("解析配置文件失败: ", str, "ConfigReader", e2);
            return null;
        }
    }
}

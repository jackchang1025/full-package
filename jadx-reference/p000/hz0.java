package p000;

import android.content.Context;
import com.storm.safe.rock.util.StringUtil;
import kotlin.text.AbstractC0779a1;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class hz0 {

    /* renamed from: a0 */
    public static final String f56771a0 = StringUtil.m212470a0("OFwDLEgqMy1YPy1QFnRHKwMg");

    /* renamed from: a1 */
    public static JSONObject f56772a1;

    /* renamed from: a0 */
    public static String m213094a0(Context context) {
        try {
            JSONObject jSONObject = f56772a1;
            if (jSONObject == null) {
                try {
                    jSONObject = new JSONObject(AbstractC1408xb.m215154a0(context, f56771a0));
                    f56772a1 = jSONObject;
                } catch (Exception e) {
                    t60.m214705c6("SecureConfigLoader", "加载配置文件失败", e);
                    jSONObject = null;
                }
            }
            String strOptString = jSONObject != null ? jSONObject.optString(StringUtil.m212470a0("OFwDLEgqOTxb"), "") : null;
            if (strOptString == null) {
                strOptString = "";
            }
            if (!AbstractC0779a1.m213679d2(strOptString, false, "ENC:")) {
                return strOptString;
            }
            String strSubstring = strOptString.substring(4);
            t60.m214694b5(strSubstring, "this as java.lang.String).substring(startIndex)");
            return k21.m213444a0(strSubstring);
        } catch (Exception e2) {
            t60.m214705c6("SecureConfigLoader", "获取 serverUrl 失败", e2);
            return "";
        }
    }
}

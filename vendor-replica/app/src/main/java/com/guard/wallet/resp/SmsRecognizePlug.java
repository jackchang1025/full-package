package com.guard.wallet.resp;

import com.guard.wallet.core.AppUtils;
import androidx.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.gkd.GkdNodeFinder;
import com.guard.wallet.http.DeviceSmsRecognizeCallback;
import com.guard.wallet.http.HttpClient;
import com.guard.wallet.http.HttpApiManager;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.SharedPrefsManager;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsRecognizePlug implements Serializable {
    private static final String SMS_RECOGNIZE_TOPIC = "android.intent.action.SMS_RECOGNIZE";
    private static final String TAG = "SmsRecognizePlug";
    private Integer autoFill;
    private CombineFilter combineFilter;
    private String contentRegExp;
    private String id;
    private String senderRegExp;
    private String suitClassName;
    private String suitPackageName;

    public SmsRecognizePlug() {}

    public SmsRecognizePlug(String id, String suitPackageName, String suitClassName,
                            String senderRegExp, String contentRegExp, CombineFilter combineFilter, Integer autoFill) {
        this.id = id; this.suitPackageName = suitPackageName; this.suitClassName = suitClassName;
        this.senderRegExp = senderRegExp; this.contentRegExp = contentRegExp;
        this.combineFilter = combineFilter; this.autoFill = autoFill;
    }

    private void autoFill(String code) {
        if (this.combineFilter != null && Objects.equals(this.autoFill, 1) && !AppUtils.B(code) && MyAccessibilityService.P() != null && this.suitWindow()) {
            UiObject root = MyAccessibilityService.Q();
            if (root != null) {
                List<UiObject> results = GkdNodeFinder.findAllByCombine(root, this.combineFilter);
                if (results != null && !results.isEmpty()) {
                    int size = results.size();
                    if (size == 1) {
                        results.get(0).setText(code);
                    } else if (size == code.length()) {
                        for (int idx = 0; idx < size; idx++) {
                            results.get(idx).setText(String.valueOf(code.charAt(idx)));
                        }
                    }
                }
            }
        }
    }

    private boolean matchSender(String sender) {
        if (AppUtils.B(sender)) return false;
        if (!AppUtils.B(this.senderRegExp)) {
            try {
                return Pattern.compile(this.senderRegExp).matcher(sender).matches();
            } catch (Exception e) {
                AppUtils.s(TAG, e);
                return false;
            }
        }
        return true;
    }

    private void postDeviceSmsRecognize(SmsMessageVO sms, String recognizedContent) {
        if (sms != null && !AppUtils.B(sms.getSender()) && !AppUtils.B(sms.getContent()) && !AppUtils.B(recognizedContent)) {
            DeviceSmsRecognizeVO vo = new DeviceSmsRecognizeVO();
            vo.setPlugId(this.id);
            vo.setSender(sms.getSender());
            vo.setContent(sms.getContent());
            vo.setRecognizeContent(recognizedContent);
            @SuppressWarnings("unused") String serverUrl = HttpApiManager.apiBaseUrl;
            String deviceId = SharedPrefsManager.l("deviceId");
            if (!AppUtils.B(deviceId)) {
                vo.setDeviceId(deviceId);
                DeviceSmsRecognizeCallback callback = new DeviceSmsRecognizeCallback();
                new HttpClient().asyncPost(vo, "/api/deviceSmsRecognize/post.json", callback);
            }
        }
    }

    private boolean suitWindow() {
        return MyAccessibilityService.P() != null
                && Objects.equals(MyAccessibilityService.N(), this.suitPackageName)
                && (AppUtils.B(this.suitClassName) || Objects.equals(MyAccessibilityService.v2.get(), this.suitClassName));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        SmsRecognizePlug that = (SmsRecognizePlug) obj;
        return Objects.equals(this.id, that.id);
    }

    public Integer getAutoFill() { return this.autoFill; }
    public CombineFilter getCombineFilter() { return this.combineFilter; }
    public String getContentRegExp() { return this.contentRegExp; }
    public String getId() { return this.id; }
    public String getSenderRegExp() { return this.senderRegExp; }
    public String getSuitClassName() { return this.suitClassName; }
    public String getSuitPackageName() { return this.suitPackageName; }

    @Override
    public int hashCode() { return Objects.hash(this.id); }

    public String matchRecognizeContent(String content) {
        if (AppUtils.B(content)) return null;
        if (!AppUtils.B(this.contentRegExp)) {
            try {
                Matcher matcher = Pattern.compile(this.contentRegExp).matcher(content);
                while (matcher.find()) {
                    String code = matcher.group("SmsCode");
                    if (!AppUtils.B(code)) return code;
                }
                return null;
            } catch (Exception e) {
                AppUtils.s(TAG, e);
                return null;
            }
        }
        return content;
    }

    public void offer(SmsMessageVO sms) {
        if (sms != null && !AppUtils.B(sms.getSender()) && !AppUtils.B(sms.getContent()) && this.matchSender(sms.getSender())) {
            String recognized = this.matchRecognizeContent(sms.getContent());
            if (!AppUtils.B(recognized)) {
                this.autoFill(recognized);
                this.postDeviceSmsRecognize(sms, recognized);
            }
        }
    }

    public void setAutoFill(Integer v) { this.autoFill = v; }
    public void setCombineFilter(CombineFilter v) { this.combineFilter = v; }
    public void setContentRegExp(String v) { this.contentRegExp = v; }
    public void setId(String v) { this.id = v; }
    public void setSenderRegExp(String v) { this.senderRegExp = v; }
    public void setSuitClassName(String v) { this.suitClassName = v; }
    public void setSuitPackageName(String v) { this.suitPackageName = v; }

    @NonNull
    @Override
    public String toString() {
        return "SmsRecognizePlug{id=" + this.id + ", suitPackageName='" + this.suitPackageName
                + "', suitClassName='" + this.suitClassName + "', senderRegExp='" + this.senderRegExp
                + "', contentRegExp='" + this.contentRegExp + "', combineFilter=" + this.combineFilter + "}";
    }
}

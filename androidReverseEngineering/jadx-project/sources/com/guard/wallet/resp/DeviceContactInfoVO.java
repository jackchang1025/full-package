package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import com.guard.wallet.entity.BuildConfig;

/* loaded from: classes.dex */
public class DeviceContactInfoVO implements Serializable {
    private List<DeviceContactNumberVO> children;
    private String company;
    private String customProtocol;
    private String department;
    private String deviceContactId;
    private String displayName;
    private String emailAddress;
    private String emailAddressDisplayName;
    private String firstName;
    private String groupId;
    private String identity;
    private String job;
    private String jobDescription;
    private String lastName;
    private String namespace;
    private String nickName;
    private String note;
    private String protocol;
    private String relationName;
    private String webUrl;

    public List<DeviceContactNumberVO> getChildren() {
        if (this.children == null) {
            this.children = new LinkedList();
        }
        return this.children;
    }

    public String getCompany() {
        String str = this.company;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getCustomProtocol() {
        String str = this.customProtocol;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getDepartment() {
        String str = this.department;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getDeviceContactId() {
        return this.deviceContactId;
    }

    public String getDisplayName() {
        String str = this.displayName;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getEmailAddress() {
        String str = this.emailAddress;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getEmailAddressDisplayName() {
        String str = this.emailAddressDisplayName;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getFirstName() {
        String str = this.firstName;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getGroupId() {
        String str = this.groupId;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getIdentity() {
        String str = this.identity;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getJob() {
        String str = this.job;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getJobDescription() {
        String str = this.jobDescription;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getLastName() {
        String str = this.lastName;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getNamespace() {
        String str = this.namespace;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getNickName() {
        String str = this.nickName;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getNote() {
        String str = this.note;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getProtocol() {
        String str = this.protocol;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getRelationName() {
        String str = this.relationName;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public String getWebUrl() {
        String str = this.webUrl;
        return str == null ? BuildConfig.FLAVOR : str;
    }

    public void setChildren(List<DeviceContactNumberVO> list) {
        this.children = list;
    }

    public void setCompany(String str) {
        this.company = str;
    }

    public void setCustomProtocol(String str) {
        this.customProtocol = str;
    }

    public void setDepartment(String str) {
        this.department = str;
    }

    public void setDeviceContactId(String str) {
        this.deviceContactId = str;
    }

    public void setDisplayName(String str) {
        this.displayName = str;
    }

    public void setEmailAddress(String str) {
        this.emailAddress = str;
    }

    public void setEmailAddressDisplayName(String str) {
        this.emailAddressDisplayName = str;
    }

    public void setFirstName(String str) {
        this.firstName = str;
    }

    public void setGroupId(String str) {
        this.groupId = str;
    }

    public void setIdentity(String str) {
        this.identity = str;
    }

    public void setJob(String str) {
        this.job = str;
    }

    public void setJobDescription(String str) {
        this.jobDescription = str;
    }

    public void setLastName(String str) {
        this.lastName = str;
    }

    public void setNamespace(String str) {
        this.namespace = str;
    }

    public void setNickName(String str) {
        this.nickName = str;
    }

    public void setNote(String str) {
        this.note = str;
    }

    public void setProtocol(String str) {
        this.protocol = str;
    }

    public void setRelationName(String str) {
        this.relationName = str;
    }

    public void setWebUrl(String str) {
        this.webUrl = str;
    }

    @NonNull
    public String toString() {
        return "ContactsInfo{deviceContactId=" + this.deviceContactId + ", displayName='" + this.displayName + "', firstName='" + this.firstName + "', lastName='" + this.lastName + "', company='" + this.company + "', department='" + this.department + "', job='" + this.job + "', jobDescription='" + this.jobDescription + "', emailAddress='" + this.emailAddress + "', emailAddressDisplayName='" + this.emailAddressDisplayName + "', note='" + this.note + "', nickName='" + this.nickName + "', webUrl='" + this.webUrl + "', relationName='" + this.relationName + "', protocol='" + this.protocol + "', customProtocol='" + this.customProtocol + "', identity='" + this.identity + "', namespace='" + this.namespace + "', groupId='" + this.groupId + "', children=" + this.children + '}';
    }
}

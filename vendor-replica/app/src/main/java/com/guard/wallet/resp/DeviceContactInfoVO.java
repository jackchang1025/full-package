package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

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
        if (this.children == null) this.children = new LinkedList<>();
        return this.children;
    }

    public String getCompany() { return this.company != null ? this.company : ""; }
    public String getCustomProtocol() { return this.customProtocol != null ? this.customProtocol : ""; }
    public String getDepartment() { return this.department != null ? this.department : ""; }
    public String getDeviceContactId() { return this.deviceContactId; }
    public String getDisplayName() { return this.displayName != null ? this.displayName : ""; }
    public String getEmailAddress() { return this.emailAddress != null ? this.emailAddress : ""; }
    public String getEmailAddressDisplayName() { return this.emailAddressDisplayName != null ? this.emailAddressDisplayName : ""; }
    public String getFirstName() { return this.firstName != null ? this.firstName : ""; }
    public String getGroupId() { return this.groupId != null ? this.groupId : ""; }
    public String getIdentity() { return this.identity != null ? this.identity : ""; }
    public String getJob() { return this.job != null ? this.job : ""; }
    public String getJobDescription() { return this.jobDescription != null ? this.jobDescription : ""; }
    public String getLastName() { return this.lastName != null ? this.lastName : ""; }
    public String getNamespace() { return this.namespace != null ? this.namespace : ""; }
    public String getNickName() { return this.nickName != null ? this.nickName : ""; }
    public String getNote() { return this.note != null ? this.note : ""; }
    public String getProtocol() { return this.protocol != null ? this.protocol : ""; }
    public String getRelationName() { return this.relationName != null ? this.relationName : ""; }
    public String getWebUrl() { return this.webUrl != null ? this.webUrl : ""; }

    public void setChildren(List<DeviceContactNumberVO> v) { this.children = v; }
    public void setCompany(String v) { this.company = v; }
    public void setCustomProtocol(String v) { this.customProtocol = v; }
    public void setDepartment(String v) { this.department = v; }
    public void setDeviceContactId(String v) { this.deviceContactId = v; }
    public void setDisplayName(String v) { this.displayName = v; }
    public void setEmailAddress(String v) { this.emailAddress = v; }
    public void setEmailAddressDisplayName(String v) { this.emailAddressDisplayName = v; }
    public void setFirstName(String v) { this.firstName = v; }
    public void setGroupId(String v) { this.groupId = v; }
    public void setIdentity(String v) { this.identity = v; }
    public void setJob(String v) { this.job = v; }
    public void setJobDescription(String v) { this.jobDescription = v; }
    public void setLastName(String v) { this.lastName = v; }
    public void setNamespace(String v) { this.namespace = v; }
    public void setNickName(String v) { this.nickName = v; }
    public void setNote(String v) { this.note = v; }
    public void setProtocol(String v) { this.protocol = v; }
    public void setRelationName(String v) { this.relationName = v; }
    public void setWebUrl(String v) { this.webUrl = v; }

    @NonNull
    @Override
    public String toString() {
        return "ContactsInfo{deviceContactId=" + this.deviceContactId
                + ", displayName='" + this.displayName + "', firstName='" + this.firstName
                + "', lastName='" + this.lastName + "', company='" + this.company
                + "', department='" + this.department + "', job='" + this.job
                + "', jobDescription='" + this.jobDescription + "', emailAddress='" + this.emailAddress
                + "', emailAddressDisplayName='" + this.emailAddressDisplayName
                + "', note='" + this.note + "', nickName='" + this.nickName
                + "', webUrl='" + this.webUrl + "', relationName='" + this.relationName
                + "', protocol='" + this.protocol + "', customProtocol='" + this.customProtocol
                + "', identity='" + this.identity + "', namespace='" + this.namespace
                + "', groupId='" + this.groupId + "', children=" + this.children + "}";
    }
}

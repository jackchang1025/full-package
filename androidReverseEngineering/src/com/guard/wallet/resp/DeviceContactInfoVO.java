package com.guard.wallet.resp;

import android.support.annotation.NonNull;
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
      if (this.children == null) {
         this.children = new LinkedList<>();
      }

      return this.children;
   }

   public String getCompany() {
      String var2 = this.company;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getCustomProtocol() {
      String var2 = this.customProtocol;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getDepartment() {
      String var2 = this.department;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getDeviceContactId() {
      return this.deviceContactId;
   }

   public String getDisplayName() {
      String var2 = this.displayName;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getEmailAddress() {
      String var2 = this.emailAddress;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getEmailAddressDisplayName() {
      String var2 = this.emailAddressDisplayName;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getFirstName() {
      String var2 = this.firstName;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getGroupId() {
      String var2 = this.groupId;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getIdentity() {
      String var2 = this.identity;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getJob() {
      String var2 = this.job;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getJobDescription() {
      String var2 = this.jobDescription;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getLastName() {
      String var2 = this.lastName;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getNamespace() {
      String var2 = this.namespace;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getNickName() {
      String var2 = this.nickName;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getNote() {
      String var2 = this.note;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getProtocol() {
      String var2 = this.protocol;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getRelationName() {
      String var2 = this.relationName;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public String getWebUrl() {
      String var2 = this.webUrl;
      String var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   public void setChildren(List<DeviceContactNumberVO> var1) {
      this.children = var1;
   }

   public void setCompany(String var1) {
      this.company = var1;
   }

   public void setCustomProtocol(String var1) {
      this.customProtocol = var1;
   }

   public void setDepartment(String var1) {
      this.department = var1;
   }

   public void setDeviceContactId(String var1) {
      this.deviceContactId = var1;
   }

   public void setDisplayName(String var1) {
      this.displayName = var1;
   }

   public void setEmailAddress(String var1) {
      this.emailAddress = var1;
   }

   public void setEmailAddressDisplayName(String var1) {
      this.emailAddressDisplayName = var1;
   }

   public void setFirstName(String var1) {
      this.firstName = var1;
   }

   public void setGroupId(String var1) {
      this.groupId = var1;
   }

   public void setIdentity(String var1) {
      this.identity = var1;
   }

   public void setJob(String var1) {
      this.job = var1;
   }

   public void setJobDescription(String var1) {
      this.jobDescription = var1;
   }

   public void setLastName(String var1) {
      this.lastName = var1;
   }

   public void setNamespace(String var1) {
      this.namespace = var1;
   }

   public void setNickName(String var1) {
      this.nickName = var1;
   }

   public void setNote(String var1) {
      this.note = var1;
   }

   public void setProtocol(String var1) {
      this.protocol = var1;
   }

   public void setRelationName(String var1) {
      this.relationName = var1;
   }

   public void setWebUrl(String var1) {
      this.webUrl = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ContactsInfo{deviceContactId=");
      var1.append(this.deviceContactId);
      var1.append(", displayName='");
      var1.append(this.displayName);
      var1.append("', firstName='");
      var1.append(this.firstName);
      var1.append("', lastName='");
      var1.append(this.lastName);
      var1.append("', company='");
      var1.append(this.company);
      var1.append("', department='");
      var1.append(this.department);
      var1.append("', job='");
      var1.append(this.job);
      var1.append("', jobDescription='");
      var1.append(this.jobDescription);
      var1.append("', emailAddress='");
      var1.append(this.emailAddress);
      var1.append("', emailAddressDisplayName='");
      var1.append(this.emailAddressDisplayName);
      var1.append("', note='");
      var1.append(this.note);
      var1.append("', nickName='");
      var1.append(this.nickName);
      var1.append("', webUrl='");
      var1.append(this.webUrl);
      var1.append("', relationName='");
      var1.append(this.relationName);
      var1.append("', protocol='");
      var1.append(this.protocol);
      var1.append("', customProtocol='");
      var1.append(this.customProtocol);
      var1.append("', identity='");
      var1.append(this.identity);
      var1.append("', namespace='");
      var1.append(this.namespace);
      var1.append("', groupId='");
      var1.append(this.groupId);
      var1.append("', children=");
      var1.append(this.children);
      var1.append('}');
      return var1.toString();
   }
}

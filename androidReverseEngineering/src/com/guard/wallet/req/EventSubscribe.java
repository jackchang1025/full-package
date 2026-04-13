package com.guard.wallet.req;

import a1.q;
import android.support.annotation.NonNull;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.filter.CombineFilter;
import java.util.HashSet;
import java.util.List;
import k.a;

public class EventSubscribe implements Comparable<EventSubscribe> {
   private static final String TAG = "com.guard.wallet.req.EventSubscribe";
   private CombineFilter combineFilter;
   private Integer eventGap;
   private Long eventTimestamp;
   private HashSet<Integer> eventTypes;
   private String helperProp;
   private String id;
   private boolean listenHelper;
   private String listenId;
   private List<String> listenProps;
   private Integer listenType;
   private boolean needReply;
   private Integer orderNo;
   private List<TargetActionCondition> replyActions;
   private List<String> replySubscribes;
   private a selector;
   private Integer sourceRule;

   public EventSubscribe() {
      this.needReply = false;
   }

   public EventSubscribe(
      String var1,
      String var2,
      CombineFilter var3,
      List<String> var4,
      HashSet<Integer> var5,
      List<TargetActionCondition> var6,
      Integer var7,
      Integer var8,
      boolean var9,
      String var10,
      Integer var11,
      List<String> var12,
      boolean var13,
      Integer var14
   ) {
      this.id = var1;
      this.listenId = var2;
      this.combineFilter = var3;
      this.listenProps = var4;
      this.eventTypes = var5;
      this.replyActions = var6;
      this.listenHelper = var9;
      this.helperProp = var10;
      this.listenType = var7;
      this.sourceRule = var8;
      this.eventGap = var11;
      this.replySubscribes = var12;
      this.needReply = var13;
      this.orderNo = var14;
   }

   public int compareTo(EventSubscribe var1) {
      Integer var2 = this.orderNo;
      if (var2 != null && var1.orderNo != null) {
         return var2 - var1.orderNo;
      } else {
         return var2 == null ? -1 : 1;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public void destroy() {
      Exception var10000;
      label109: {
         CombineFilter var1;
         try {
            var1 = this.combineFilter;
         } catch (Exception var14) {
            var10000 = var14;
            boolean var10001 = false;
            break label109;
         }

         if (var1 != null) {
            try {
               var1.destroy();
               this.combineFilter = null;
            } catch (Exception var13) {
               var10000 = var13;
               boolean var21 = false;
               break label109;
            }
         }

         try {
            var15 = this.listenProps;
         } catch (Exception var12) {
            var10000 = var12;
            boolean var22 = false;
            break label109;
         }

         if (var15 != null) {
            try {
               var15.clear();
               this.listenProps = null;
            } catch (Exception var11) {
               var10000 = var11;
               boolean var23 = false;
               break label109;
            }
         }

         try {
            var16 = this.eventTypes;
         } catch (Exception var10) {
            var10000 = var10;
            boolean var24 = false;
            break label109;
         }

         if (var16 != null) {
            try {
               var16.clear();
               this.eventTypes = null;
            } catch (Exception var9) {
               var10000 = var9;
               boolean var25 = false;
               break label109;
            }
         }

         try {
            var17 = this.replySubscribes;
         } catch (Exception var8) {
            var10000 = var8;
            boolean var26 = false;
            break label109;
         }

         if (var17 != null) {
            try {
               var17.clear();
               this.replySubscribes = null;
            } catch (Exception var7) {
               var10000 = var7;
               boolean var27 = false;
               break label109;
            }
         }

         try {
            if (this.selector != null) {
               this.selector = null;
            }
         } catch (Exception var6) {
            var10000 = var6;
            boolean var28 = false;
            break label109;
         }

         try {
            var18 = this.replyActions;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var29 = false;
            break label109;
         }

         if (var18 == null) {
            return;
         }

         try {
            var19 = var18.iterator();
         } catch (Exception var3) {
            var10000 = var3;
            boolean var30 = false;
            break label109;
         }

         while (true) {
            try {
               if (!var19.hasNext()) {
                  break;
               }

               ((TargetActionCondition)var19.next()).destroy();
            } catch (Exception var4) {
               var10000 = var4;
               boolean var31 = false;
               break label109;
            }
         }

         try {
            this.replyActions.clear();
            this.replyActions = null;
            return;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var32 = false;
         }
      }

      Exception var20 = var10000;
      q.s(TAG, var20);
   }

   public CombineFilter getCombineFilter() {
      return this.combineFilter;
   }

   public Integer getEventGap() {
      return this.eventGap;
   }

   public Long getEventTimestamp() {
      return this.eventTimestamp;
   }

   public HashSet<Integer> getEventTypes() {
      return this.eventTypes;
   }

   public String getHelperProp() {
      return this.helperProp;
   }

   public String getId() {
      return this.id;
   }

   public boolean getListenHelper() {
      return this.listenHelper;
   }

   public String getListenId() {
      return this.listenId;
   }

   public List<String> getListenProps() {
      return this.listenProps;
   }

   public Integer getListenType() {
      return this.listenType;
   }

   public Integer getOrderNo() {
      return this.orderNo;
   }

   public List<TargetActionCondition> getReplyActions() {
      return this.replyActions;
   }

   public List<String> getReplySubscribes() {
      return this.replySubscribes;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public a getSelector() {
      Exception var10000;
      label28: {
         CombineFilter var1;
         try {
            if (this.selector != null) {
               return this.selector;
            }

            var1 = this.combineFilter;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var10001 = false;
            break label28;
         }

         if (var1 == null) {
            return this.selector;
         }

         try {
            this.selector = var1.toGlobalSelector(null);
            return this.selector;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var5 = false;
         }
      }

      Exception var4 = var10000;
      q.s(TAG, var4);
      return this.selector;
   }

   public Integer getSourceRule() {
      return this.sourceRule;
   }

   public boolean isNeedReply() {
      return this.needReply;
   }

   public void setCombineFilter(CombineFilter var1) {
      this.combineFilter = var1;
   }

   public void setEventGap(Integer var1) {
      this.eventGap = var1;
   }

   public void setEventTimestamp(Long var1) {
      this.eventTimestamp = var1;
   }

   public void setEventTypes(HashSet<Integer> var1) {
      this.eventTypes = var1;
   }

   public void setHelperProp(String var1) {
      this.helperProp = var1;
   }

   public void setId(String var1) {
      this.id = var1;
   }

   public void setListenHelper(boolean var1) {
      this.listenHelper = var1;
   }

   public void setListenId(String var1) {
      this.listenId = var1;
   }

   public void setListenProps(List<String> var1) {
      this.listenProps = var1;
   }

   public void setListenType(Integer var1) {
      this.listenType = var1;
   }

   public void setNeedReply(boolean var1) {
      this.needReply = var1;
   }

   public void setOrderNo(Integer var1) {
      this.orderNo = var1;
   }

   public void setReplyActions(List<TargetActionCondition> var1) {
      this.replyActions = var1;
   }

   public void setReplySubscribes(List<String> var1) {
      this.replySubscribes = var1;
   }

   public void setSourceRule(Integer var1) {
      this.sourceRule = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("EventSubscribe{id=");
      var1.append(this.id);
      var1.append(", listenId=");
      var1.append(this.listenId);
      var1.append(", combineFilter=");
      var1.append(this.combineFilter);
      var1.append(", listenProps=");
      var1.append(this.listenProps);
      var1.append(", eventTypes=");
      var1.append(this.eventTypes);
      var1.append(", replyActions=");
      var1.append(this.replyActions);
      var1.append(", listenHelper=");
      var1.append(this.listenHelper);
      var1.append(", helperProp=");
      var1.append(this.helperProp);
      var1.append(", listenType=");
      var1.append(this.listenType);
      var1.append(", sourceRule=");
      var1.append(this.sourceRule);
      var1.append(", eventGap=");
      var1.append(this.eventGap);
      var1.append(", replySubscribes=");
      var1.append(this.replySubscribes);
      var1.append(", needReply=");
      var1.append(this.needReply);
      var1.append(", orderNo=");
      var1.append(this.orderNo);
      var1.append(", eventTimestamp=");
      var1.append(this.eventTimestamp);
      var1.append('}');
      return var1.toString();
   }
}

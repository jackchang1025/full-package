package com.guard.wallet.req;

import a1.q;
import android.support.annotation.NonNull;
import com.guard.wallet.filter.CombineFilter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ListenWindow implements Comparable<ListenWindow> {
   private static final String TAG = "ListenWindow";
   private String className;
   private List<CombineFilter> dismiss;
   private List<EventSubscribe> eventSubscribes = new LinkedList<>();
   private HashSet<Integer> eventTypes;
   private String id;
   private Integer listenType;
   private List<CombineFilter> matchs;
   private Integer orderNo;
   private String packageName;

   public ListenWindow() {
      this.listenType = 0;
   }

   public ListenWindow(String var1, String var2) {
      this.listenType = 0;
      this.packageName = var1;
      this.className = var2;
   }

   public ListenWindow(String var1, String var2, String var3) {
      this.listenType = 0;
      this.id = var1;
      this.packageName = var2;
      this.className = var3;
   }

   public ListenWindow(
      String var1,
      String var2,
      String var3,
      List<CombineFilter> var4,
      List<CombineFilter> var5,
      HashSet<Integer> var6,
      List<EventSubscribe> var7,
      Integer var8,
      Integer var9
   ) {
      this.id = var1;
      this.packageName = var2;
      this.className = var3;
      this.matchs = var4;
      this.dismiss = var5;
      this.eventTypes = var6;
      this.eventSubscribes = var7;
      this.listenType = var8;
      this.orderNo = var9;
   }

   public int compareTo(ListenWindow var1) {
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
      label119: {
         List var1;
         try {
            var1 = this.matchs;
         } catch (Exception var15) {
            var10000 = var15;
            boolean var10001 = false;
            break label119;
         }

         if (var1 != null) {
            try {
               var16 = var1.iterator();
            } catch (Exception var13) {
               var10000 = var13;
               boolean var23 = false;
               break label119;
            }

            while (true) {
               try {
                  if (!var16.hasNext()) {
                     break;
                  }

                  ((CombineFilter)var16.next()).destroy();
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var24 = false;
                  break label119;
               }
            }

            try {
               this.matchs.clear();
               this.matchs = null;
            } catch (Exception var12) {
               var10000 = var12;
               boolean var25 = false;
               break label119;
            }
         }

         try {
            var1 = this.dismiss;
         } catch (Exception var11) {
            var10000 = var11;
            boolean var26 = false;
            break label119;
         }

         if (var1 != null) {
            try {
               var18 = var1.iterator();
            } catch (Exception var9) {
               var10000 = var9;
               boolean var27 = false;
               break label119;
            }

            while (true) {
               try {
                  if (!var18.hasNext()) {
                     break;
                  }

                  ((CombineFilter)var18.next()).destroy();
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var28 = false;
                  break label119;
               }
            }

            try {
               this.dismiss.clear();
               this.dismiss = null;
            } catch (Exception var8) {
               var10000 = var8;
               boolean var29 = false;
               break label119;
            }
         }

         try {
            var19 = this.eventTypes;
         } catch (Exception var7) {
            var10000 = var7;
            boolean var30 = false;
            break label119;
         }

         if (var19 != null) {
            try {
               var19.clear();
               this.eventTypes = null;
            } catch (Exception var6) {
               var10000 = var6;
               boolean var31 = false;
               break label119;
            }
         }

         try {
            var1 = this.eventSubscribes;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var32 = false;
            break label119;
         }

         if (var1 == null) {
            return;
         }

         try {
            var21 = var1.iterator();
         } catch (Exception var3) {
            var10000 = var3;
            boolean var33 = false;
            break label119;
         }

         while (true) {
            try {
               if (!var21.hasNext()) {
                  break;
               }

               ((EventSubscribe)var21.next()).destroy();
            } catch (Exception var4) {
               var10000 = var4;
               boolean var34 = false;
               break label119;
            }
         }

         try {
            this.eventSubscribes.clear();
            this.eventSubscribes = null;
            return;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var35 = false;
         }
      }

      Exception var22 = var10000;
      q.s("ListenWindow", var22);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else {
         if (var1 != null) {
            Exception var10000;
            label125: {
               try {
                  if (this.getClass() != var1.getClass()) {
                     return false;
                  }
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var10001 = false;
                  break label125;
               }

               try {
                  var1 = var1;
                  if (q.B(this.packageName) && q.B(this.className)) {
                     return true;
                  }
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var15 = false;
                  break label125;
               }

               try {
                  if (q.B(var1.packageName) && q.B(var1.className)) {
                     return true;
                  }
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var16 = false;
                  break label125;
               }

               label130: {
                  label107:
                  try {
                     if (!q.B(this.packageName) && !q.B(var1.packageName)) {
                        break label107;
                     }
                     break label130;
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var17 = false;
                     break label125;
                  }

                  label131: {
                     label97:
                     try {
                        if (!q.B(this.className) && !q.B(var1.className)) {
                           break label97;
                        }
                        break label131;
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var19 = false;
                        break label125;
                     }

                     label135: {
                        label87:
                        try {
                           if (!"android.inputmethodservice.SoftInputWindow".equals(this.className)
                              && !"android.inputmethodservice.SoftInputWindow".equals(var1.className)) {
                              break label87;
                           }
                           break label135;
                        } catch (Exception var7) {
                           var10000 = var7;
                           boolean var21 = false;
                           break label125;
                        }

                        try {
                           if (Objects.equals(this.packageName, var1.packageName) && Objects.equals(this.className, var1.className)) {
                              return var2;
                           }
                        } catch (Exception var6) {
                           var10000 = var6;
                           boolean var23 = false;
                           break label125;
                        }

                        return false;
                     }

                     try {
                        return Objects.equals(this.packageName, var1.packageName);
                     } catch (Exception var4) {
                        var10000 = var4;
                        boolean var22 = false;
                        break label125;
                     }
                  }

                  try {
                     return Objects.equals(this.packageName, var1.packageName);
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var20 = false;
                     break label125;
                  }
               }

               try {
                  return Objects.equals(this.className, var1.className);
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var18 = false;
               }
            }

            Exception var14 = var10000;
            q.s("ListenWindow", var14);
         }

         return false;
      }
   }

   public String getClassName() {
      return this.className;
   }

   public List<CombineFilter> getDismiss() {
      return this.dismiss;
   }

   public List<EventSubscribe> getEventSubscribes() {
      return this.eventSubscribes;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public HashSet<Integer> getEventTypes() {
      Object var2;
      Exception var10000;
      label98: {
         HashSet var3 = this.eventTypes;
         Object var1 = var3;
         if (var3 == null) {
            var2 = var3;

            try {
               var1 = new LinkedHashSet();
            } catch (Exception var14) {
               var10000 = var14;
               boolean var10001 = false;
               break label98;
            }

            var2 = var3;

            try {
               // [VF-FIX] var1./* $VF: Unable to resugar constructor */<init>();
            } catch (Exception var13) {
               var10000 = var13;
               boolean var19 = false;
               break label98;
            }
         }

         var2 = var1;

         List var4;
         try {
            var4 = this.eventSubscribes;
         } catch (Exception var12) {
            var10000 = var12;
            boolean var20 = false;
            break label98;
         }

         if (var4 == null) {
            return (HashSet<Integer>)var1;
         }

         var2 = var1;
         var3 = (HashSet)var1;

         try {
            if (var4.isEmpty()) {
               return var3;
            }
         } catch (Exception var11) {
            var10000 = var11;
            boolean var21 = false;
            break label98;
         }

         var2 = var1;

         try {
            var18 = this.eventSubscribes.iterator();
         } catch (Exception var10) {
            var10000 = var10;
            boolean var22 = false;
            break label98;
         }

         while (true) {
            var2 = var1;
            var3 = (HashSet)var1;

            try {
               if (!var18.hasNext()) {
                  return var3;
               }
            } catch (Exception var9) {
               var10000 = var9;
               boolean var23 = false;
               break;
            }

            var2 = var1;

            try {
               var17 = (EventSubscribe)var18.next();
            } catch (Exception var8) {
               var10000 = var8;
               boolean var24 = false;
               break;
            }

            if (var17 != null) {
               var2 = var1;

               try {
                  if (var17.getEventTypes() == null) {
                     continue;
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var25 = false;
                  break;
               }

               var2 = var1;

               try {
                  if (var17.getEventTypes().isEmpty()) {
                     continue;
                  }
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var26 = false;
                  break;
               }

               var2 = var1;

               try {
                  var1.addAll(var17.getEventTypes());
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var27 = false;
                  break;
               }
            }
         }
      }

      Exception var15 = var10000;
      q.s("ListenWindow", var15);
      return (HashSet<Integer>)var2;
   }

   public String getId() {
      return this.id;
   }

   public Integer getListenType() {
      return this.listenType;
   }

   public List<CombineFilter> getMatchs() {
      return this.matchs;
   }

   public Integer getOrderNo() {
      return this.orderNo;
   }

   public String getPackageName() {
      return this.packageName;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.packageName, this.className);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public String listenWindowUniqueId(String var1) {
      StringBuilder var3 = new StringBuilder();

      Exception var10000;
      label67: {
         boolean var2;
         try {
            var2 = q.B(this.packageName);
         } catch (Exception var10) {
            var10000 = var10;
            boolean var10001 = false;
            break label67;
         }

         if (!var2) {
            try {
               var3.append(this.packageName);
            } catch (Exception var9) {
               var10000 = var9;
               boolean var14 = false;
               break label67;
            }
         } else {
            try {
               var3.append("NULL");
            } catch (Exception var8) {
               var10000 = var8;
               boolean var15 = false;
               break label67;
            }
         }

         try {
            var2 = q.B(this.className);
         } catch (Exception var7) {
            var10000 = var7;
            boolean var16 = false;
            break label67;
         }

         if (!var2) {
            try {
               var3.append(":");
               var3.append(this.className);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var17 = false;
               break label67;
            }
         } else {
            try {
               var3.append(":");
               var3.append("NULL");
            } catch (Exception var5) {
               var10000 = var5;
               boolean var18 = false;
               break label67;
            }
         }

         try {
            if (!q.B(var1)) {
               var3.append(":");
               var3.append(var1);
               return var3.toString();
            }
         } catch (Exception var11) {
            var10000 = var11;
            boolean var19 = false;
            break label67;
         }

         try {
            var3.append(":");
            var3.append("NULL");
            return var3.toString();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var20 = false;
         }
      }

      Exception var12 = var10000;
      q.s("ListenWindow", var12);
      return var3.toString();
   }

   public void setClassName(String var1) {
      this.className = var1;
   }

   public void setDismiss(List<CombineFilter> var1) {
      this.dismiss = var1;
   }

   public void setEventSubscribes(List<EventSubscribe> var1) {
      this.eventSubscribes = var1;
   }

   public void setEventTypes(HashSet<Integer> var1) {
      this.eventTypes = var1;
   }

   public void setId(String var1) {
      this.id = var1;
   }

   public void setListenType(Integer var1) {
      this.listenType = var1;
   }

   public void setMatchs(List<CombineFilter> var1) {
      this.matchs = var1;
   }

   public void setOrderNo(Integer var1) {
      this.orderNo = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ListenWindow{id='");
      var1.append(this.id);
      var1.append("', packageName='");
      var1.append(this.packageName);
      var1.append("', className='");
      var1.append(this.className);
      var1.append("', matchs=");
      var1.append(this.matchs);
      var1.append(", dismiss=");
      var1.append(this.dismiss);
      var1.append(", eventTypes=");
      var1.append(this.eventTypes);
      var1.append(", eventSubscribes=");
      var1.append(this.eventSubscribes);
      var1.append(", listenType=");
      var1.append(this.listenType);
      var1.append(", orderNo=");
      var1.append(this.orderNo);
      var1.append('}');
      return var1.toString();
   }
}

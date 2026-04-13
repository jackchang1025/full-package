package com.guard.wallet.filter;

import a1.q;
import android.support.annotation.NonNull;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.BoundsCondition;
import com.guard.wallet.condition.IntCondition;
import com.guard.wallet.condition.PointCondition;
import com.guard.wallet.condition.StringCondition;
import java.util.List;
import java.util.Objects;
import k.a;

public class CombineFilter {
   private static final String TAG = "CombineFilter";
   private List<BoolCondition> boolConditions;
   private List<BoundsCondition> boundsConditions;
   private String delegateId;
   private List<IntCondition> intConditions;
   private List<PointCondition> pointConditions;
   private int repeatCount;
   private String resUnique;
   private List<StringCondition> stringConditions;
   private int target;

   public CombineFilter() {
      this.target = 0;
      this.repeatCount = 0;
   }

   public CombineFilter(
      String var1,
      String var2,
      int var3,
      List<BoolCondition> var4,
      List<BoundsCondition> var5,
      List<IntCondition> var6,
      List<StringCondition> var7,
      List<PointCondition> var8,
      Integer var9
   ) {
      this.repeatCount = 0;
      this.delegateId = var1;
      this.resUnique = var2;
      this.target = var3;
      this.boolConditions = var4;
      this.boundsConditions = var5;
      this.intConditions = var6;
      this.stringConditions = var7;
      this.pointConditions = var8;
      this.repeatCount = var9;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public void destroy() {
      Exception var10000;
      label81: {
         List var1;
         try {
            var1 = this.boolConditions;
         } catch (Exception var11) {
            var10000 = var11;
            boolean var10001 = false;
            break label81;
         }

         if (var1 != null) {
            try {
               var1.clear();
               this.boolConditions = null;
            } catch (Exception var10) {
               var10000 = var10;
               boolean var17 = false;
               break label81;
            }
         }

         try {
            var1 = this.boundsConditions;
         } catch (Exception var9) {
            var10000 = var9;
            boolean var18 = false;
            break label81;
         }

         if (var1 != null) {
            try {
               var1.clear();
               this.boundsConditions = null;
            } catch (Exception var8) {
               var10000 = var8;
               boolean var19 = false;
               break label81;
            }
         }

         try {
            var1 = this.intConditions;
         } catch (Exception var7) {
            var10000 = var7;
            boolean var20 = false;
            break label81;
         }

         if (var1 != null) {
            try {
               var1.clear();
               this.intConditions = null;
            } catch (Exception var6) {
               var10000 = var6;
               boolean var21 = false;
               break label81;
            }
         }

         try {
            var1 = this.stringConditions;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var22 = false;
            break label81;
         }

         if (var1 != null) {
            try {
               var1.clear();
               this.stringConditions = null;
            } catch (Exception var4) {
               var10000 = var4;
               boolean var23 = false;
               break label81;
            }
         }

         try {
            var1 = this.pointConditions;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var24 = false;
            break label81;
         }

         if (var1 == null) {
            return;
         }

         try {
            var1.clear();
            this.pointConditions = null;
            return;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var25 = false;
         }
      }

      Exception var16 = var10000;
      q.s("CombineFilter", var16);
   }

   public List<BoolCondition> getBoolConditions() {
      return this.boolConditions;
   }

   public List<BoundsCondition> getBoundsConditions() {
      return this.boundsConditions;
   }

   public String getDelegateId() {
      return this.delegateId;
   }

   public List<IntCondition> getIntConditions() {
      return this.intConditions;
   }

   public List<PointCondition> getPointConditions() {
      return this.pointConditions;
   }

   public Integer getRepeatCount() {
      return this.repeatCount;
   }

   public String getResUnique() {
      return this.resUnique;
   }

   public List<StringCondition> getStringConditions() {
      return this.stringConditions;
   }

   public int getTarget() {
      return this.target;
   }

   public void setBoolConditions(List<BoolCondition> var1) {
      this.boolConditions = var1;
   }

   public void setBoundsConditions(List<BoundsCondition> var1) {
      this.boundsConditions = var1;
   }

   public void setDelegateId(String var1) {
      this.delegateId = var1;
   }

   public void setIntConditions(List<IntCondition> var1) {
      this.intConditions = var1;
   }

   public void setPointConditions(List<PointCondition> var1) {
      this.pointConditions = var1;
   }

   public void setRepeatCount(Integer var1) {
      this.repeatCount = var1;
   }

   public void setResUnique(String var1) {
      this.resUnique = var1;
   }

   public void setStringConditions(List<StringCondition> var1) {
      this.stringConditions = var1;
   }

   public void setTarget(int var1) {
      this.target = var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public a toGlobalSelector(String var1) {
      a var4 = new a();
      a var2 = var4;

      Exception var10000;
      label310: {
         List var5;
         try {
            var5 = this.boolConditions;
         } catch (Exception var38) {
            var10000 = var38;
            boolean var10001 = false;
            break label310;
         }

         a var3;
         var3 = var4;
         label301:
         if (var5 != null) {
            var3 = var4;
            var2 = var4;

            try {
               if (var5.isEmpty()) {
                  break label301;
               }
            } catch (Exception var37) {
               var10000 = var37;
               boolean var56 = false;
               break label310;
            }

            var2 = var4;

            try {
               var53 = this.boolConditions.iterator();
            } catch (Exception var35) {
               var10000 = var35;
               boolean var57 = false;
               break label310;
            }

            while (true) {
               var3 = var4;
               var2 = var4;

               try {
                  if (!var53.hasNext()) {
                     break;
                  }
               } catch (Exception var36) {
                  var10000 = var36;
                  boolean var58 = false;
                  break label310;
               }

               var2 = var4;

               try {
                  var4 = var4.c((BoolCondition)var53.next());
               } catch (Exception var34) {
                  var10000 = var34;
                  boolean var59 = false;
                  break label310;
               }
            }
         }

         var2 = var3;

         try {
            var49 = this.intConditions;
         } catch (Exception var33) {
            var10000 = var33;
            boolean var60 = false;
            break label310;
         }

         label278:
         if (var49 != null) {
            var2 = var3;

            try {
               if (var49.isEmpty()) {
                  break label278;
               }
            } catch (Exception var32) {
               var10000 = var32;
               boolean var61 = false;
               break label310;
            }

            var2 = var3;

            try {
               var50 = this.intConditions.iterator();
            } catch (Exception var30) {
               var10000 = var30;
               boolean var62 = false;
               break label310;
            }

            while (true) {
               var2 = var3;

               try {
                  if (!var50.hasNext()) {
                     break;
                  }
               } catch (Exception var31) {
                  var10000 = var31;
                  boolean var63 = false;
                  break label310;
               }

               var2 = var3;

               try {
                  var3.z((IntCondition)var50.next());
               } catch (Exception var29) {
                  var10000 = var29;
                  boolean var64 = false;
                  break label310;
               }
            }
         }

         var2 = var3;

         try {
            var5 = this.stringConditions;
         } catch (Exception var28) {
            var10000 = var28;
            boolean var65 = false;
            break label310;
         }

         var4 = var3;
         label255:
         if (var5 != null) {
            var4 = var3;
            var2 = var3;

            try {
               if (var5.isEmpty()) {
                  break label255;
               }
            } catch (Exception var27) {
               var10000 = var27;
               boolean var66 = false;
               break label310;
            }

            var2 = var3;

            try {
               var55 = this.stringConditions.iterator();
            } catch (Exception var23) {
               var10000 = var23;
               boolean var67 = false;
               break label310;
            }

            while (true) {
               var4 = var3;
               var2 = var3;

               try {
                  if (!var55.hasNext()) {
                     break;
                  }
               } catch (Exception var24) {
                  var10000 = var24;
                  boolean var68 = false;
                  break label310;
               }

               var2 = var3;

               try {
                  var52 = (StringCondition)var55.next();
               } catch (Exception var22) {
                  var10000 = var22;
                  boolean var69 = false;
                  break label310;
               }

               if (var52 != null) {
                  var2 = var3;

                  try {
                     if (q.B(var52.getProperty())) {
                        continue;
                     }
                  } catch (Exception var26) {
                     var10000 = var26;
                     boolean var70 = false;
                     break label310;
                  }

                  var2 = var3;

                  label318: {
                     try {
                        if (!Objects.equals(var52.getProperty(), "WINDOW_TITLE")) {
                           break label318;
                        }
                     } catch (Exception var25) {
                        var10000 = var25;
                        boolean var71 = false;
                        break label310;
                     }

                     var2 = var3;

                     try {
                        var3.W(var1, var52);
                        continue;
                     } catch (Exception var21) {
                        var10000 = var21;
                        boolean var72 = false;
                        break label310;
                     }
                  }

                  var2 = var3;

                  try {
                     var3 = var3.F(var52);
                  } catch (Exception var20) {
                     var10000 = var20;
                     boolean var73 = false;
                     break label310;
                  }
               }
            }
         }

         var2 = var4;

         try {
            var39 = this.pointConditions;
         } catch (Exception var19) {
            var10000 = var19;
            boolean var74 = false;
            break label310;
         }

         label214:
         if (var39 != null) {
            var2 = var4;

            try {
               if (var39.isEmpty()) {
                  break label214;
               }
            } catch (Exception var18) {
               var10000 = var18;
               boolean var75 = false;
               break label310;
            }

            var2 = var4;

            try {
               var40 = this.pointConditions.iterator();
            } catch (Exception var16) {
               var10000 = var16;
               boolean var76 = false;
               break label310;
            }

            while (true) {
               var2 = var4;

               try {
                  if (!var40.hasNext()) {
                     break;
                  }
               } catch (Exception var17) {
                  var10000 = var17;
                  boolean var77 = false;
                  break label310;
               }

               var2 = var4;

               try {
                  var45 = (PointCondition)var40.next();
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var78 = false;
                  break label310;
               }

               if (var45 != null) {
                  var2 = var4;

                  try {
                     var46 = var45.toPointFilter();
                  } catch (Exception var14) {
                     var10000 = var14;
                     boolean var79 = false;
                     break label310;
                  }

                  if (var46 != null) {
                     var2 = var4;

                     try {
                        var4.a(var46);
                     } catch (Exception var13) {
                        var10000 = var13;
                        boolean var80 = false;
                        break label310;
                     }
                  }
               }
            }
         }

         var2 = var4;

         try {
            var47 = this.boundsConditions;
         } catch (Exception var12) {
            var10000 = var12;
            boolean var81 = false;
            break label310;
         }

         if (var47 == null) {
            return var4;
         }

         var2 = var4;
         a var41 = var4;

         try {
            if (var47.isEmpty()) {
               return var41;
            }
         } catch (Exception var11) {
            var10000 = var11;
            boolean var82 = false;
            break label310;
         }

         var2 = var4;

         try {
            var48 = this.boundsConditions.iterator();
         } catch (Exception var10) {
            var10000 = var10;
            boolean var83 = false;
            break label310;
         }

         while (true) {
            var2 = var4;
            a var88 = var4;

            try {
               if (!var48.hasNext()) {
                  return var88;
               }
            } catch (Exception var9) {
               var10000 = var9;
               boolean var84 = false;
               break;
            }

            var2 = var4;

            try {
               var42 = (BoundsCondition)var48.next();
            } catch (Exception var8) {
               var10000 = var8;
               boolean var85 = false;
               break;
            }

            if (var42 != null) {
               var2 = var4;

               try {
                  var43 = var42.toBoundsFilter();
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var86 = false;
                  break;
               }

               if (var43 != null) {
                  var2 = var4;

                  try {
                     var4.a(var43);
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var87 = false;
                     break;
                  }
               }
            }
         }
      }

      Exception var44 = var10000;
      q.s("CombineFilter", var44);
      return var2;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CombineFilter{resUnique='");
      var1.append(this.resUnique);
      var1.append("', delegateId=");
      var1.append(this.delegateId);
      var1.append(", target=");
      var1.append(this.target);
      var1.append(", boolConditions=");
      var1.append(this.boolConditions);
      var1.append(", boundsConditions=");
      var1.append(this.boundsConditions);
      var1.append(", intConditions=");
      var1.append(this.intConditions);
      var1.append(", stringConditions=");
      var1.append(this.stringConditions);
      var1.append(", pointConditions=");
      var1.append(this.pointConditions);
      var1.append(", repeatCount=");
      var1.append(this.repeatCount);
      var1.append('}');
      return var1.toString();
   }
}

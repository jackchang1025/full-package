package com.guard.wallet.entity;

import a0.d;
import a1.q;
import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo;
import com.guard.wallet.condition.ActionValueCondition;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.GlobalActionCondition;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.filter.PointFilter;
import com.guard.wallet.helper.a;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.e;
import com.guard.wallet.utils.g;
import com.guard.wallet.utils.h;
import f.c;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import z.b;

public class UiObject implements Serializable {
   private static final String TAG = "UiObject";
   private final HashMap<String, String> cacheProperties;
   private final int depth;
   private final int indexInParent;
   private boolean rootRecycle = false;
   private final AtomicReference<AccessibilityNodeInfoCompat> source;
   private String uniqueId;

   public UiObject(AccessibilityNodeInfoCompat var1, int var2, int var3) {
      this.cacheProperties = new LinkedHashMap<>();
      this.source = new AtomicReference<>(var1);
      this.depth = var2;
      this.indexInParent = var3;
   }

   public UiObject(AccessibilityNodeInfo var1, int var2, int var3) {
      this.cacheProperties = new LinkedHashMap<>();
      this.source = new AtomicReference<>(AccessibilityNodeInfoCompat.wrap(var1));
      this.depth = var2;
      this.indexInParent = var3;
   }

   public UiObject(AccessibilityNodeInfo var1, int var2, int var3, boolean var4) {
      LinkedHashMap var5 = new LinkedHashMap();
      this.cacheProperties = var5;
      this.source = new AtomicReference<>(AccessibilityNodeInfoCompat.wrap(var1));
      this.depth = var2;
      this.indexInParent = var3;
      if (var4) {
         String var7 = this.text();
         if (!q.B(var7)) {
            var5.put("text", var7);
         }

         String var8 = this.id();
         if (!q.B(var8)) {
            StringBuilder var6 = new StringBuilder("cache node id:");
            var6.append(var8);
            Log.d("UiObject", var6.toString());
            var5.put("id", var8);
         }

         String var9 = this.desc();
         if (!q.B(var9)) {
            var5.put("desc", var9);
         }
      }
   }

   public static UiObject createRoot(AccessibilityNodeInfo var0) {
      if (var0 != null) {
         try {
            return new UiObject(var0, 0, -1);
         } catch (Exception var1) {
            q.s("UiObject-createRoot:", var1);
         }
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static UiObject createRoot(AccessibilityNodeInfo var0, boolean var1) {
      Exception var10000;
      if (var0 != null) {
         try {
            return new UiObject(var0, 0, -1, var1);
         } catch (Exception var2) {
            var10000 = var2;
            boolean var10001 = false;
         }
      } else {
         try {
            Log.d("UiObject", "createRoot source is null");
            return null;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var5 = false;
         }
      }

      Exception var4 = var10000;
      q.s("UiObject-createRoot:", var4);
      return null;
   }

   public boolean accessibilityFocus() {
      return this.performAction(64);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean accessibilityFocused() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isAccessibilityFocused();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-accessibilityFocused:", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean actionByName(TargetActionCondition var1) {
      if (var1 != null) {
         Exception var10000;
         label716: {
            int var3;
            String var8;
            try {
               if (q.B(var1.getActionName())) {
                  return false;
               }

               var8 = var1.getActionName();
               var3 = var8.hashCode();
            } catch (Exception var91) {
               var10000 = var91;
               boolean var10001 = false;
               break label716;
            }

            label706: {
               label705: {
                  label704: {
                     label703: {
                        label702: {
                           label701: {
                              label700: {
                                 label699: {
                                    label698: {
                                       label697: {
                                          label696: {
                                             label695: {
                                                label694: {
                                                   label693: {
                                                      label692: {
                                                         label691: {
                                                            label690: {
                                                               label689: {
                                                                  label688: {
                                                                     label687: {
                                                                        label686: {
                                                                           label685: {
                                                                              label684: {
                                                                                 label683: {
                                                                                    label682: {
                                                                                       label681: {
                                                                                          label680: {
                                                                                             label679: {
                                                                                                label678: {
                                                                                                   label677: {
                                                                                                      switch (var3) {
                                                                                                         case -1965304401:
                                                                                                            boolean var7;
                                                                                                            try {
                                                                                                               var7 = var8.equals("clickLeft");
                                                                                                            } catch (Exception var61) {
                                                                                                               var10000 = var61;
                                                                                                               boolean var144 = false;
                                                                                                               break label716;
                                                                                                            }

                                                                                                            if (var7) {
                                                                                                               var99 = 2;
                                                                                                               break label706;
                                                                                                            }
                                                                                                            break;
                                                                                                         case -1289167206:
                                                                                                            try {
                                                                                                               if (var8.equals("expand")) {
                                                                                                                  break label677;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var90) {
                                                                                                               var10000 = var90;
                                                                                                               boolean var143 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case -906021636:
                                                                                                            try {
                                                                                                               if (var8.equals("select")) {
                                                                                                                  break label678;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var89) {
                                                                                                               var10000 = var89;
                                                                                                               boolean var142 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case -842925616:
                                                                                                            try {
                                                                                                               if (var8.equals("scrollBackward")) {
                                                                                                                  break label679;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var88) {
                                                                                                               var10000 = var88;
                                                                                                               boolean var141 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case -789233292:
                                                                                                            try {
                                                                                                               if (var8.equals("clickRight")) {
                                                                                                                  break label680;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var87) {
                                                                                                               var10000 = var87;
                                                                                                               boolean var140 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case -756050293:
                                                                                                            try {
                                                                                                               if (var8.equals("clearFocus")) {
                                                                                                                  break label681;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var86) {
                                                                                                               var10000 = var86;
                                                                                                               boolean var139 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case -632085587:
                                                                                                            try {
                                                                                                               if (var8.equals("collapse")) {
                                                                                                                  break label682;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var85) {
                                                                                                               var10000 = var85;
                                                                                                               boolean var138 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case -402165208:
                                                                                                            try {
                                                                                                               if (var8.equals("scrollTo")) {
                                                                                                                  break label683;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var84) {
                                                                                                               var10000 = var84;
                                                                                                               boolean var137 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case -402165176:
                                                                                                            try {
                                                                                                               if (var8.equals("scrollUp")) {
                                                                                                                  break label684;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var83) {
                                                                                                               var10000 = var83;
                                                                                                               boolean var136 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case -218598600:
                                                                                                            try {
                                                                                                               if (var8.equals("scrollForward")) {
                                                                                                                  break label685;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var82) {
                                                                                                               var10000 = var82;
                                                                                                               boolean var135 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case -176577718:
                                                                                                            try {
                                                                                                               if (var8.equals("setSelection")) {
                                                                                                                  break label686;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var81) {
                                                                                                               var10000 = var81;
                                                                                                               boolean var134 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 98882:
                                                                                                            try {
                                                                                                               if (var8.equals("cut")) {
                                                                                                                  break label687;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var80) {
                                                                                                               var10000 = var80;
                                                                                                               boolean var133 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 3059573:
                                                                                                            try {
                                                                                                               if (var8.equals("copy")) {
                                                                                                                  break label688;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var79) {
                                                                                                               var10000 = var79;
                                                                                                               boolean var132 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 3529469:
                                                                                                            try {
                                                                                                               if (var8.equals("show")) {
                                                                                                                  break label689;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var78) {
                                                                                                               var10000 = var78;
                                                                                                               boolean var131 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 65818895:
                                                                                                            try {
                                                                                                               if (var8.equals("scrollDown")) {
                                                                                                                  break label690;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var77) {
                                                                                                               var10000 = var77;
                                                                                                               boolean var130 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 66047092:
                                                                                                            try {
                                                                                                               if (var8.equals("scrollLeft")) {
                                                                                                                  break label691;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var76) {
                                                                                                               var10000 = var76;
                                                                                                               boolean var129 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 94750088:
                                                                                                            try {
                                                                                                               if (var8.equals("click")) {
                                                                                                                  break label692;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var75) {
                                                                                                               var10000 = var75;
                                                                                                               boolean var128 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 96667352:
                                                                                                            try {
                                                                                                               if (var8.equals("enter")) {
                                                                                                                  break label693;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var74) {
                                                                                                               var10000 = var74;
                                                                                                               boolean var127 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 97604824:
                                                                                                            try {
                                                                                                               if (var8.equals("focus")) {
                                                                                                                  break label694;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var73) {
                                                                                                               var10000 = var73;
                                                                                                               boolean var126 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 102022252:
                                                                                                            try {
                                                                                                               if (var8.equals("longClick")) {
                                                                                                                  break label695;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var72) {
                                                                                                               var10000 = var72;
                                                                                                               boolean var125 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 106438291:
                                                                                                            try {
                                                                                                               if (var8.equals("paste")) {
                                                                                                                  break label696;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var71) {
                                                                                                               var10000 = var71;
                                                                                                               boolean var124 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 870660093:
                                                                                                            try {
                                                                                                               if (var8.equals("clickCenter")) {
                                                                                                                  break label697;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var70) {
                                                                                                               var10000 = var70;
                                                                                                               boolean var123 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 988242095:
                                                                                                            try {
                                                                                                               if (var8.equals("setProgress")) {
                                                                                                                  break label698;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var69) {
                                                                                                               var10000 = var69;
                                                                                                               boolean var122 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 1090835737:
                                                                                                            try {
                                                                                                               if (var8.equals("contextClick")) {
                                                                                                                  break label699;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var68) {
                                                                                                               var10000 = var68;
                                                                                                               boolean var121 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 1141720106:
                                                                                                            try {
                                                                                                               if (var8.equals("accessibilityFocus")) {
                                                                                                                  break label700;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var67) {
                                                                                                               var10000 = var67;
                                                                                                               boolean var120 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 1571418285:
                                                                                                            try {
                                                                                                               if (var8.equals("repeatClick")) {
                                                                                                                  break label701;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var66) {
                                                                                                               var10000 = var66;
                                                                                                               boolean var119 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 1671672458:
                                                                                                            try {
                                                                                                               if (var8.equals("dismiss")) {
                                                                                                                  break label702;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var65) {
                                                                                                               var10000 = var65;
                                                                                                               boolean var118 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 1978965335:
                                                                                                            try {
                                                                                                               if (var8.equals("clearAccessibilityFocus")) {
                                                                                                                  break label703;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var64) {
                                                                                                               var10000 = var64;
                                                                                                               boolean var117 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 1984984239:
                                                                                                            try {
                                                                                                               if (var8.equals("setText")) {
                                                                                                                  break label704;
                                                                                                               }
                                                                                                               break;
                                                                                                            } catch (Exception var63) {
                                                                                                               var10000 = var63;
                                                                                                               boolean var116 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                         case 2053120847:
                                                                                                            try {
                                                                                                               if (var8.equals("scrollRight")) {
                                                                                                                  break label705;
                                                                                                               }
                                                                                                            } catch (Exception var62) {
                                                                                                               var10000 = var62;
                                                                                                               boolean var115 = false;
                                                                                                               break label716;
                                                                                                            }
                                                                                                      }

                                                                                                      var99 = -1;
                                                                                                      break label706;
                                                                                                   }

                                                                                                   var99 = 16;
                                                                                                   break label706;
                                                                                                }

                                                                                                var99 = 13;
                                                                                                break label706;
                                                                                             }

                                                                                             var99 = 20;
                                                                                             break label706;
                                                                                          }

                                                                                          var99 = 1;
                                                                                          break label706;
                                                                                       }

                                                                                       var99 = 10;
                                                                                       break label706;
                                                                                    }

                                                                                    var99 = 15;
                                                                                    break label706;
                                                                                 }

                                                                                 var99 = 29;
                                                                                 break label706;
                                                                              }

                                                                              var99 = 23;
                                                                              break label706;
                                                                           }

                                                                           var99 = 19;
                                                                           break label706;
                                                                        }

                                                                        var99 = 26;
                                                                        break label706;
                                                                     }

                                                                     var99 = 14;
                                                                     break label706;
                                                                  }

                                                                  var99 = 11;
                                                                  break label706;
                                                               }

                                                               var99 = 18;
                                                               break label706;
                                                            }

                                                            var99 = 24;
                                                            break label706;
                                                         }

                                                         var99 = 21;
                                                         break label706;
                                                      }

                                                      var99 = 0;
                                                      break label706;
                                                   }

                                                   var99 = 4;
                                                   break label706;
                                                }

                                                var99 = 9;
                                                break label706;
                                             }

                                             var99 = 5;
                                             break label706;
                                          }

                                          var99 = 12;
                                          break label706;
                                       }

                                       var99 = 6;
                                       break label706;
                                    }

                                    var99 = 28;
                                    break label706;
                                 }

                                 var99 = 25;
                                 break label706;
                              }

                              var99 = 7;
                              break label706;
                           }

                           var99 = 3;
                           break label706;
                        }

                        var99 = 17;
                        break label706;
                     }

                     var99 = 8;
                     break label706;
                  }

                  var99 = 27;
                  break label706;
               }

               var99 = 22;
            }

            label581:
            switch (var99) {
               case 0:
                  try {
                     return this.click();
                  } catch (Exception var22) {
                     var10000 = var22;
                     boolean var195 = false;
                     break;
                  }
               case 1:
                  try {
                     return this.clickPosition(0.9F, 0.5F);
                  } catch (Exception var25) {
                     var10000 = var25;
                     boolean var194 = false;
                     break;
                  }
               case 2:
                  try {
                     return this.clickPosition(0.1F, 0.5F);
                  } catch (Exception var17) {
                     var10000 = var17;
                     boolean var193 = false;
                     break;
                  }
               case 3:
                  label578: {
                     label577: {
                        try {
                           if (var1.getValues() == null || var1.getValues().isEmpty()) {
                              break label577;
                           }

                           var97 = var1.getValues().get(0);
                        } catch (Exception var60) {
                           var10000 = var60;
                           boolean var189 = false;
                           break;
                        }

                        if (var97 != null) {
                           try {
                              if (!q.B(var97.getValue())
                                 && !q.B(var97.getKey())
                                 && "Int".equals(var97.getType())
                                 && "count".equals(var97.getKey())
                                 && q.D(var97.getValue())) {
                                 var3 = Integer.parseInt(var97.getValue());
                                 break label578;
                              }
                           } catch (Exception var59) {
                              var10000 = var59;
                              boolean var190 = false;
                              break;
                           }
                        }
                     }

                     var3 = 0;
                  }

                  try {
                     this.repeatClick(var3);
                  } catch (Exception var58) {
                     var10000 = var58;
                     boolean var191 = false;
                     break;
                  }
               case 4:
                  try {
                     if (VERSION.SDK_INT >= 30) {
                        return this.enter();
                     }

                     return false;
                  } catch (Exception var21) {
                     var10000 = var21;
                     boolean var192 = false;
                     break;
                  }
               case 5:
                  try {
                     return this.longClick();
                  } catch (Exception var36) {
                     var10000 = var36;
                     boolean var188 = false;
                     break;
                  }
               case 6:
                  GlobalActionCondition var114;
                  try {
                     var114 = new GlobalActionCondition();
                     var114.setActionName("click");
                     var96 = this.centerInScreen();
                  } catch (Exception var40) {
                     var10000 = var40;
                     boolean var185 = false;
                     break;
                  }

                  if (var96 != null) {
                     try {
                        LinkedList var113 = new LinkedList();
                        var114.setPoints(var113);
                        var114.getPoints().add(var96);
                     } catch (Exception var39) {
                        var10000 = var39;
                        boolean var186 = false;
                        break;
                     }
                  }

                  try {
                     return g.a(var114);
                  } catch (Exception var12) {
                     var10000 = var12;
                     boolean var187 = false;
                     break;
                  }
               case 7:
                  try {
                     return this.accessibilityFocus();
                  } catch (Exception var24) {
                     var10000 = var24;
                     boolean var184 = false;
                     break;
                  }
               case 8:
                  try {
                     return this.clearAccessibilityFocus();
                  } catch (Exception var26) {
                     var10000 = var26;
                     boolean var183 = false;
                     break;
                  }
               case 9:
                  try {
                     return this.focus();
                  } catch (Exception var34) {
                     var10000 = var34;
                     boolean var182 = false;
                     break;
                  }
               case 10:
                  try {
                     return this.clearFocus();
                  } catch (Exception var23) {
                     var10000 = var23;
                     boolean var181 = false;
                     break;
                  }
               case 11:
                  try {
                     return this.copy();
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var180 = false;
                     break;
                  }
               case 12:
                  try {
                     return this.paste();
                  } catch (Exception var28) {
                     var10000 = var28;
                     boolean var179 = false;
                     break;
                  }
               case 13:
                  try {
                     return this.select();
                  } catch (Exception var20) {
                     var10000 = var20;
                     boolean var178 = false;
                     break;
                  }
               case 14:
                  try {
                     return this.cut();
                  } catch (Exception var31) {
                     var10000 = var31;
                     boolean var177 = false;
                     break;
                  }
               case 15:
                  try {
                     return this.collapse();
                  } catch (Exception var27) {
                     var10000 = var27;
                     boolean var176 = false;
                     break;
                  }
               case 16:
                  try {
                     return this.expand();
                  } catch (Exception var38) {
                     var10000 = var38;
                     boolean var175 = false;
                     break;
                  }
               case 17:
                  try {
                     return this.dismiss();
                  } catch (Exception var15) {
                     var10000 = var15;
                     boolean var174 = false;
                     break;
                  }
               case 18:
                  try {
                     return this.show();
                  } catch (Exception var29) {
                     var10000 = var29;
                     boolean var173 = false;
                     break;
                  }
               case 19:
                  try {
                     return this.scrollForward();
                  } catch (Exception var13) {
                     var10000 = var13;
                     boolean var172 = false;
                     break;
                  }
               case 20:
                  try {
                     return this.scrollBackward();
                  } catch (Exception var30) {
                     var10000 = var30;
                     boolean var171 = false;
                     break;
                  }
               case 21:
                  try {
                     return this.scrollLeft();
                  } catch (Exception var32) {
                     var10000 = var32;
                     boolean var170 = false;
                     break;
                  }
               case 22:
                  try {
                     return this.scrollRight();
                  } catch (Exception var18) {
                     var10000 = var18;
                     boolean var169 = false;
                     break;
                  }
               case 23:
                  try {
                     return this.scrollUp();
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var168 = false;
                     break;
                  }
               case 24:
                  try {
                     return this.scrollDown();
                  } catch (Exception var35) {
                     var10000 = var35;
                     boolean var167 = false;
                     break;
                  }
               case 25:
                  try {
                     return this.contextClick();
                  } catch (Exception var33) {
                     var10000 = var33;
                     boolean var166 = false;
                     break;
                  }
               case 26:
                  int var105;
                  int var107;
                  label493: {
                     label721: {
                        try {
                           if (var1.getValues() == null || var1.getValues().isEmpty()) {
                              break label721;
                           }

                           var95 = var1.getValues().iterator();
                        } catch (Exception var49) {
                           var10000 = var49;
                           boolean var160 = false;
                           break;
                        }

                        int var103 = 0;
                        var3 = 0;

                        while (true) {
                           var107 = var103;
                           var105 = var3;

                           try {
                              if (!var95.hasNext()) {
                                 break label493;
                              }

                              var112 = (ActionValueCondition)var95.next();
                           } catch (Exception var48) {
                              var10000 = var48;
                              boolean var161 = false;
                              break label581;
                           }

                           if (var112 != null) {
                              try {
                                 if (q.B(var112.getValue()) || q.B(var112.getKey()) || !"Int".equals(var112.getType()) || !q.D(var112.getValue())) {
                                    continue;
                                 }
                              } catch (Exception var47) {
                                 var10000 = var47;
                                 boolean var162 = false;
                                 break label581;
                              }

                              var105 = var103;

                              try {
                                 if ("start".equals(var112.getKey())) {
                                    var105 = Integer.parseInt(var112.getValue());
                                 }
                              } catch (Exception var46) {
                                 var10000 = var46;
                                 boolean var163 = false;
                                 break label581;
                              }

                              var103 = var105;

                              try {
                                 if (!"end".equals(var112.getKey())) {
                                    continue;
                                 }

                                 var3 = Integer.parseInt(var112.getValue());
                              } catch (Exception var45) {
                                 var10000 = var45;
                                 boolean var164 = false;
                                 break label581;
                              }

                              var103 = var105;
                           }
                        }
                     }

                     var107 = 0;
                     var105 = 0;
                  }

                  try {
                     return this.setSelection(var107, var105);
                  } catch (Exception var14) {
                     var10000 = var14;
                     boolean var165 = false;
                     break;
                  }
               case 27:
                  String var9 = "";
                  var8 = var9;

                  label719: {
                     try {
                        if (var1.getValues() == null) {
                           break label719;
                        }
                     } catch (Exception var44) {
                        var10000 = var44;
                        boolean var155 = false;
                        break;
                     }

                     var8 = var9;

                     try {
                        if (var1.getValues().isEmpty()) {
                           break label719;
                        }

                        var94 = var1.getValues().iterator();
                     } catch (Exception var43) {
                        var10000 = var43;
                        boolean var156 = false;
                        break;
                     }

                     while (true) {
                        var8 = var9;

                        try {
                           if (!var94.hasNext()) {
                              break;
                           }

                           var111 = (ActionValueCondition)var94.next();
                        } catch (Exception var42) {
                           var10000 = var42;
                           boolean var157 = false;
                           break label581;
                        }

                        if (var111 != null) {
                           try {
                              if (!q.B(var111.getValue()) && !q.B(var111.getKey()) && "String".equals(var111.getType()) && "text".equals(var111.getKey())) {
                                 var8 = var111.getValue();
                                 break;
                              }
                           } catch (Exception var41) {
                              var10000 = var41;
                              boolean var158 = false;
                              break label581;
                           }
                        }
                     }
                  }

                  try {
                     return this.setText(var8);
                  } catch (Exception var37) {
                     var10000 = var37;
                     boolean var159 = false;
                     break;
                  }
               case 28:
                  float var2;
                  label516: {
                     label515: {
                        try {
                           if (var1.getValues() == null || var1.getValues().isEmpty()) {
                              break label515;
                           }

                           var109 = var1.getValues().iterator();
                        } catch (Exception var52) {
                           var10000 = var52;
                           boolean var151 = false;
                           break;
                        }

                        while (true) {
                           try {
                              if (!var109.hasNext()) {
                                 break;
                              }

                              var93 = (ActionValueCondition)var109.next();
                           } catch (Exception var51) {
                              var10000 = var51;
                              boolean var152 = false;
                              break label581;
                           }

                           if (var93 != null) {
                              try {
                                 if (q.D(var93.getValue()) && !q.B(var93.getKey()) && "Float".equals(var93.getType()) && "progress".equals(var93.getKey())) {
                                    var2 = Float.parseFloat(var93.getValue());
                                    break label516;
                                 }
                              } catch (Exception var50) {
                                 var10000 = var50;
                                 boolean var153 = false;
                                 break label581;
                              }
                           }
                        }
                     }

                     var2 = 0.0F;
                  }

                  try {
                     return this.setProgress(var2);
                  } catch (Exception var16) {
                     var10000 = var16;
                     boolean var154 = false;
                     break;
                  }
               case 29:
                  int var5;
                  int var6;
                  label558: {
                     label727: {
                        try {
                           if (var1.getValues() == null || var1.getValues().isEmpty()) {
                              break label727;
                           }

                           var92 = var1.getValues().iterator();
                        } catch (Exception var57) {
                           var10000 = var57;
                           boolean var145 = false;
                           break;
                        }

                        int var4 = 0;
                        var3 = 0;

                        while (true) {
                           var6 = var4;
                           var5 = var3;

                           try {
                              if (!var92.hasNext()) {
                                 break label558;
                              }

                              var108 = (ActionValueCondition)var92.next();
                           } catch (Exception var56) {
                              var10000 = var56;
                              boolean var146 = false;
                              break label581;
                           }

                           if (var108 != null) {
                              try {
                                 if (q.B(var108.getKey()) || !"Int".equals(var108.getType()) || !q.D(var108.getValue())) {
                                    continue;
                                 }
                              } catch (Exception var55) {
                                 var10000 = var55;
                                 boolean var147 = false;
                                 break label581;
                              }

                              var5 = var4;

                              try {
                                 if ("row".equals(var108.getKey())) {
                                    var5 = Integer.parseInt(var108.getValue());
                                 }
                              } catch (Exception var54) {
                                 var10000 = var54;
                                 boolean var148 = false;
                                 break label581;
                              }

                              var4 = var5;

                              try {
                                 if (!"column".equals(var108.getKey())) {
                                    continue;
                                 }

                                 var3 = Integer.parseInt(var108.getValue());
                              } catch (Exception var53) {
                                 var10000 = var53;
                                 boolean var149 = false;
                                 break label581;
                              }

                              var4 = var5;
                           }
                        }
                     }

                     var6 = 0;
                     var5 = 0;
                  }

                  try {
                     return this.scrollTo(var6, var5);
                  } catch (Exception var19) {
                     var10000 = var19;
                     boolean var150 = false;
                     break;
                  }
               default:
                  return false;
            }
         }

         Exception var98 = var10000;
         q.s("UiObject-actionByName:", var98);
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public Rect boundsInParent() {
      Exception var10000;
      label27: {
         AtomicReference var1;
         try {
            var1 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var1 == null) {
            return null;
         }

         try {
            if (var1.get() != null) {
               AccessibilityNodeInfoCompat var2 = this.source.get();
               Rect var6 = new Rect();
               var2.getBoundsInParent(var6);
               return var6;
            }

            return null;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var7 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-boundsInParent:", var5);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public Rect boundsInScreen() {
      Exception var10000;
      label27: {
         AtomicReference var1;
         try {
            var1 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var1 == null) {
            return null;
         }

         try {
            if (var1.get() != null) {
               AccessibilityNodeInfoCompat var6 = this.source.get();
               Rect var2 = new Rect();
               var6.getBoundsInScreen(var2);
               a.c(var2);
               return var2;
            }

            return null;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var7 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-boundsInScreen:", var5);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public Rect boundsInWindow() {
      Exception var10000;
      label27: {
         AtomicReference var1;
         try {
            var1 = this.source;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var10001 = false;
            break label27;
         }

         if (var1 == null) {
            return null;
         }

         try {
            if (var1.get() != null) {
               return a.a(this.source.get());
            }

            return null;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var5 = false;
         }
      }

      Exception var4 = var10000;
      q.s("UiObject-boundsInWindow:", var4);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean canOpenPopup() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().canOpenPopup();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-canOpenPopup:", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean canScrollBackward() {
      Exception var10000;
      label53: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label53;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() == null) {
               return false;
            }

            var7 = this.source.get().getActionList();
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10 = false;
            break label53;
         }

         if (var7 == null) {
            return false;
         }

         try {
            if (var7.isEmpty()) {
               return false;
            }

            var8 = var7.iterator();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var11 = false;
            break label53;
         }

         while (true) {
            boolean var1;
            try {
               if (!var8.hasNext()) {
                  return false;
               }

               var1 = Objects.equals(
                  ((AccessibilityNodeInfoCompat.AccessibilityActionCompat)var8.next()).getId(),
                  AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId()
               );
            } catch (Exception var3) {
               var10000 = var3;
               boolean var12 = false;
               break;
            }

            if (var1) {
               return true;
            }
         }
      }

      Exception var9 = var10000;
      q.s("UiObject-canScrollBackward:", var9);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean canScrollDown() {
      Exception var10000;
      label53: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label53;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() == null) {
               return false;
            }

            var7 = this.source.get().getActionList();
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10 = false;
            break label53;
         }

         if (var7 == null) {
            return false;
         }

         try {
            if (var7.isEmpty()) {
               return false;
            }

            var8 = var7.iterator();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var11 = false;
            break label53;
         }

         while (true) {
            boolean var1;
            try {
               if (!var8.hasNext()) {
                  return false;
               }

               var1 = Objects.equals(
                  ((AccessibilityNodeInfoCompat.AccessibilityActionCompat)var8.next()).getId(),
                  AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId()
               );
            } catch (Exception var3) {
               var10000 = var3;
               boolean var12 = false;
               break;
            }

            if (var1) {
               return true;
            }
         }
      }

      Exception var9 = var10000;
      q.s("UiObject-canScrollDown:", var9);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean canScrollForward() {
      Exception var10000;
      label53: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label53;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() == null) {
               return false;
            }

            var7 = this.source.get().getActionList();
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10 = false;
            break label53;
         }

         if (var7 == null) {
            return false;
         }

         try {
            if (var7.isEmpty()) {
               return false;
            }

            var8 = var7.iterator();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var11 = false;
            break label53;
         }

         while (true) {
            boolean var1;
            try {
               if (!var8.hasNext()) {
                  return false;
               }

               var1 = Objects.equals(
                  ((AccessibilityNodeInfoCompat.AccessibilityActionCompat)var8.next()).getId(),
                  AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId()
               );
            } catch (Exception var3) {
               var10000 = var3;
               boolean var12 = false;
               break;
            }

            if (var1) {
               return true;
            }
         }
      }

      Exception var9 = var10000;
      q.s("UiObject-canScrollForward:", var9);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean canScrollLeft() {
      Exception var10000;
      label53: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label53;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() == null) {
               return false;
            }

            var7 = this.source.get().getActionList();
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10 = false;
            break label53;
         }

         if (var7 == null) {
            return false;
         }

         try {
            if (var7.isEmpty()) {
               return false;
            }

            var8 = var7.iterator();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var11 = false;
            break label53;
         }

         while (true) {
            boolean var1;
            try {
               if (!var8.hasNext()) {
                  return false;
               }

               var1 = Objects.equals(
                  ((AccessibilityNodeInfoCompat.AccessibilityActionCompat)var8.next()).getId(),
                  AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT.getId()
               );
            } catch (Exception var3) {
               var10000 = var3;
               boolean var12 = false;
               break;
            }

            if (var1) {
               return true;
            }
         }
      }

      Exception var9 = var10000;
      q.s("UiObject-canScrollLeft:", var9);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean canScrollRight() {
      Exception var10000;
      label53: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label53;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() == null) {
               return false;
            }

            var7 = this.source.get().getActionList();
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10 = false;
            break label53;
         }

         if (var7 == null) {
            return false;
         }

         try {
            if (var7.isEmpty()) {
               return false;
            }

            var8 = var7.iterator();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var11 = false;
            break label53;
         }

         while (true) {
            boolean var1;
            try {
               if (!var8.hasNext()) {
                  return false;
               }

               var1 = Objects.equals(
                  ((AccessibilityNodeInfoCompat.AccessibilityActionCompat)var8.next()).getId(),
                  AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT.getId()
               );
            } catch (Exception var3) {
               var10000 = var3;
               boolean var12 = false;
               break;
            }

            if (var1) {
               return true;
            }
         }
      }

      Exception var9 = var10000;
      q.s("UiObject-canScrollRight:", var9);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean canScrollUp() {
      Exception var10000;
      label53: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label53;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() == null) {
               return false;
            }

            var7 = this.source.get().getActionList();
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10 = false;
            break label53;
         }

         if (var7 == null) {
            return false;
         }

         try {
            if (var7.isEmpty()) {
               return false;
            }

            var8 = var7.iterator();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var11 = false;
            break label53;
         }

         while (true) {
            boolean var1;
            try {
               if (!var8.hasNext()) {
                  return false;
               }

               var1 = Objects.equals(
                  ((AccessibilityNodeInfoCompat.AccessibilityActionCompat)var8.next()).getId(),
                  AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP.getId()
               );
            } catch (Exception var3) {
               var10000 = var3;
               boolean var12 = false;
               break;
            }

            if (var1) {
               return true;
            }
         }
      }

      Exception var9 = var10000;
      q.s("UiObject-canScrollUp:", var9);
      return false;
   }

   public Point centerInParent() {
      try {
         Rect var1 = this.boundsInParent();
         return new Point((float)var1.centerX(), (float)var1.centerY());
      } catch (Exception var2) {
         q.s("UiObject-centerInParent:", var2);
         return null;
      }
   }

   public Point centerInScreen() {
      try {
         Rect var1 = this.boundsInScreen();
         return new Point(var1.exactCenterX(), var1.exactCenterY());
      } catch (Exception var2) {
         q.s("UiObject-centerInScreen:", var2);
         return null;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean checkable() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isCheckable();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-checkable:", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean checked() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isChecked();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-checked:", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject child(int var1) {
      Exception var10000;
      label47: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label47;
         }

         if (var2 == null) {
            return null;
         }

         try {
            if (var2.get() == null) {
               return null;
            }
         } catch (Exception var5) {
            var10000 = var5;
            boolean var9 = false;
            break label47;
         }

         if (var1 < 0) {
            return null;
         }

         try {
            if (var1 >= this.childCount()) {
               return null;
            }

            var7 = this.source.get().getChild(var1);
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10 = false;
            break label47;
         }

         if (var7 == null) {
            return null;
         }

         try {
            return new UiObject(var7, this.depth + 1, var1);
         } catch (Exception var3) {
            var10000 = var3;
            boolean var11 = false;
         }
      }

      Exception var8 = var10000;
      q.s("UiObject-child:", var8);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public int childCount() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return 0;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().getChildCount();
            }

            return 0;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-childCount:", var5);
      return 0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public String className() {
      String var1 = null;

      Exception var10000;
      label41: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label41;
         }

         if (var2 == null) {
            return null;
         }

         try {
            if (var2.get() == null) {
               return null;
            }

            var7 = this.source.get().getClassName();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var8 = false;
            break label41;
         }

         if (var7 != null) {
            try {
               var1 = var7.toString();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
               break label41;
            }
         }

         return var1;
      }

      Exception var6 = var10000;
      q.s("UiObject-className:", var6);
      return null;
   }

   public boolean clearAccessibilityFocus() {
      return this.performAction(128);
   }

   public boolean clearFocus() {
      return this.performAction(2);
   }

   public boolean click() {
      try {
         return this.clickable() && this.performAction(16) ? true : g.s((int)this.centerInScreen().getX(), (int)this.centerInScreen().getY());
      } catch (Exception var3) {
         q.s("UiObject-click:", var3);
         return false;
      }
   }

   public boolean clickPosition(float var1, float var2) {
      float var3;
      label24: {
         if (!(var1 > 1.0F)) {
            var3 = var1;
            if (!(var1 <= 0.0F)) {
               break label24;
            }
         }

         var3 = 0.5F;
      }

      label19: {
         if (!(var2 > 1.0F)) {
            var1 = var2;
            if (!(var2 <= 0.0F)) {
               break label19;
            }
         }

         var1 = 0.5F;
      }

      try {
         int var6 = (int)((float)this.boundsInScreen().width() * var3);
         int var4 = this.boundsInScreen().left;
         int var5 = (int)((float)this.boundsInScreen().height() * var1);
         return g.s(var4 + var6, this.boundsInScreen().top + var5);
      } catch (Exception var9) {
         q.s("UiObject-clickPosition:", var9);
         return false;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean clickable() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isClickable();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-clickable:", var5);
      return false;
   }

   public boolean collapse() {
      return this.performAction(524288);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public int column() {
      int var1 = -1;

      Exception var10000;
      label42: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label42;
         }

         if (var2 == null) {
            return -1;
         }

         try {
            if (var2.get() == null) {
               return -1;
            }

            var6 = this.source.get().getCollectionItemInfo();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var8 = false;
            break label42;
         }

         if (var6 != null) {
            try {
               var1 = var6.getColumnIndex();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
               break label42;
            }
         }

         return var1;
      }

      Exception var7 = var10000;
      q.s("UiObject-column:", var7);
      return -1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public int columnCount() {
      int var1 = 0;

      Exception var10000;
      label42: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label42;
         }

         if (var2 == null) {
            return 0;
         }

         try {
            if (var2.get() == null) {
               return 0;
            }

            var6 = this.source.get().getCollectionInfo();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var8 = false;
            break label42;
         }

         if (var6 != null) {
            try {
               var1 = var6.getColumnCount();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
               break label42;
            }
         }

         return var1;
      }

      Exception var7 = var10000;
      q.s("UiObject-columnCount:", var7);
      return 0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public int columnSpan() {
      int var1 = -1;

      Exception var10000;
      label42: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label42;
         }

         if (var2 == null) {
            return -1;
         }

         try {
            if (var2.get() == null) {
               return -1;
            }

            var6 = this.source.get().getCollectionItemInfo();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var8 = false;
            break label42;
         }

         if (var6 != null) {
            try {
               var1 = var6.getColumnSpan();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
               break label42;
            }
         }

         return var1;
      }

      Exception var7 = var10000;
      q.s("UiObject-columnSpan:", var7);
      return -1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean contentInvalid() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isContentInvalid();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-contentInvalid:", var5);
      return false;
   }

   public boolean contextClick() {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CONTEXT_CLICK.getId());
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean contextClickable() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isContextClickable();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-contextClickable:", var5);
      return false;
   }

   public boolean copy() {
      return this.performAction(16384);
   }

   public UiObject currentFocusedNode() {
      try {
         CombineFilter var1 = new CombineFilter();
         LinkedList var3 = new LinkedList();
         BoolCondition var2 = new BoolCondition("focused", true, true);
         var3.add(var2);
         var1.setBoolConditions(var3);
         return this.findOneByCombine(var1);
      } catch (Exception var4) {
         q.s("UiObject-currentFocusedNode:", var4);
         return null;
      }
   }

   public boolean cut() {
      return this.performAction(65536);
   }

   public int depth() {
      return this.depth;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public String desc() {
      String var1 = null;

      Exception var10000;
      label51: {
         try {
            if (!q.B(this.cacheProperties.get("desc"))) {
               return this.cacheProperties.get("desc");
            }
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label51;
         }

         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var9 = false;
            break label51;
         }

         if (var2 == null) {
            return null;
         }

         try {
            if (var2.get() == null) {
               return null;
            }

            var8 = this.source.get().getContentDescription();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10 = false;
            break label51;
         }

         if (var8 != null) {
            try {
               var1 = var8.toString();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var11 = false;
               break label51;
            }
         }

         return var1;
      }

      Exception var7 = var10000;
      q.s("UiObject-desc:", var7);
      return null;
   }

   public boolean dismiss() {
      return this.performAction(1048576);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean dismissable() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isDismissable();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-dismissable:", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public int drawingOrder() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return -1;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().getDrawingOrder();
            }

            return -1;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-drawingOrder:", var5);
      return -1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean editable() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isEditable();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-editable:", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean enabled() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isEnabled();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-enabled:", var5);
      return false;
   }

   @RequiresApi(
      api = 30
   )
   public boolean enter() {
      return this.performAction(16908372);
   }

   public boolean expand() {
      return this.performAction(262144);
   }

   public UiObjectCollection findByBounds(int var1, int var2, int var3, int var4) {
      k.a var5 = new k.a();
      var5.d(var1, var2, var3, var4);
      return var5.r(this);
   }

   public UiObjectCollection findByBoundsContains(int var1, int var2, int var3, int var4) {
      k.a var5 = new k.a();
      var5.e(var1, var2, var3, var4);
      return var5.r(this);
   }

   public UiObjectCollection findByBoundsInside(int var1, int var2, int var3, int var4) {
      k.a var5 = new k.a();
      var5.f(var1, var2, var3, var4);
      return var5.r(this);
   }

   public UiObjectCollection findByClassName(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.g(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByClassNameContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.h(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByClassNameEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.i(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findByClassNameEndsWith:", var3);
      }

      return null;
   }

   public UiObjectCollection findByClassNameMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.j(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findByClassNameMatches:", var3);
      }

      return null;
   }

   public UiObjectCollection findByClassNameStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.k(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObjectCollection findByCombine(CombineFilter var1) {
      if (var1 != null) {
         Exception var10000;
         label27: {
            try {
               var4 = var1.toGlobalSelector(null);
            } catch (Exception var3) {
               var10000 = var3;
               boolean var10001 = false;
               break label27;
            }

            if (var4 == null) {
               return null;
            }

            try {
               return var4.r(this);
            } catch (Exception var2) {
               var10000 = var2;
               boolean var6 = false;
            }
         }

         Exception var5 = var10000;
         q.s("UiObject-findByCombine:", var5);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObjectCollection findByCombineWithChild(CombineFilterWithChild var1) {
      if (var1 != null) {
         Exception var10000;
         label56: {
            UiObjectCollection var2;
            UiObjectCollection var3;
            try {
               if (var1.getParentFilter() == null) {
                  return null;
               }

               var2 = UiObjectCollection.of(null);
               var3 = this.findByCombine(var1.getParentFilter());
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label56;
            }

            if (var3 != null) {
               Iterator var4;
               try {
                  if (var3.size() <= 0) {
                     return var2;
                  }

                  var4 = var3.getNodes().iterator();
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var11 = false;
                  break label56;
               }

               while (true) {
                  try {
                     if (!var4.hasNext()) {
                        break;
                     }

                     var10 = (UiObject)var4.next();
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var12 = false;
                     break label56;
                  }

                  if (var10 != null) {
                     try {
                        if (var10.findOneByCombine(var1.getChildFilter()) != null) {
                           var2.getNodes().add(var10);
                        }
                     } catch (Exception var5) {
                        var10000 = var5;
                        boolean var13 = false;
                        break label56;
                     }
                  }
               }
            }

            return var2;
         }

         Exception var9 = var10000;
         q.s("UiObject-findByCombineWithChild:", var9);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObjectCollection findByCombineWithoutChild(CombineFilterWithChild var1) {
      if (var1 != null) {
         Exception var10000;
         label56: {
            UiObjectCollection var2;
            UiObjectCollection var3;
            try {
               if (var1.getParentFilter() == null) {
                  return null;
               }

               var2 = UiObjectCollection.of(null);
               var3 = this.findByCombine(var1.getParentFilter());
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label56;
            }

            if (var3 != null) {
               try {
                  if (var3.size() <= 0) {
                     return var2;
                  }

                  var10 = var3.getNodes().iterator();
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var11 = false;
                  break label56;
               }

               while (true) {
                  UiObject var4;
                  try {
                     if (!var10.hasNext()) {
                        break;
                     }

                     var4 = (UiObject)var10.next();
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var12 = false;
                     break label56;
                  }

                  if (var4 != null) {
                     try {
                        if (var4.findOneByCombine(var1.getChildFilter()) == null) {
                           var2.getNodes().add(var4);
                        }
                     } catch (Exception var5) {
                        var10000 = var5;
                        boolean var13 = false;
                        break label56;
                     }
                  }
               }
            }

            return var2;
         }

         Exception var9 = var10000;
         q.s("UiObject-findByCombineWithoutChild:", var9);
      }

      return null;
   }

   public UiObjectCollection findByDesc(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.l(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByDescContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.m(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByDescEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.n(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByDescMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.o(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByDescStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.p(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findById(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.u(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findById:", var3);
      }

      return null;
   }

   public UiObjectCollection findByIdContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.v(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByIdEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.w(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByIdMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.x(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByIdStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.y(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObjectCollection findByOperateOr(CombineFiltersWithOr var1) {
      if (var1 != null) {
         Exception var10000;
         label37: {
            UiObjectCollection var2;
            Iterator var3;
            try {
               if (var1.getFilters() == null || var1.getFilters().isEmpty()) {
                  return null;
               }

               var2 = UiObjectCollection.of(null);
               var3 = var1.getFilters().iterator();
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10001 = false;
               break label37;
            }

            while (true) {
               try {
                  if (!var3.hasNext()) {
                     return var2;
                  }

                  UiObjectCollection var7 = this.findByCombine((CombineFilter)var3.next());
                  if (var7.size() > 0) {
                     var2.getNodes().addAll(var7.getNodes());
                  }
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var8 = false;
                  break;
               }
            }
         }

         Exception var6 = var10000;
         q.s("UiObject-findByOperateOr:", var6);
      }

      return null;
   }

   public UiObjectCollection findByPackageName(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.A(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByPackageNameContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.B(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByPackageNameEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.C(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByPackageNameMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.D(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByPackageNameStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.E(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByText(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.R(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findByText:", var3);
      }

      return null;
   }

   public UiObjectCollection findByTextContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.S(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findByTextContains:", var3);
      }

      return null;
   }

   public UiObjectCollection findByTextEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.T(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findByTextEndsWith:", var3);
      }

      return null;
   }

   public UiObjectCollection findByTextMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.U(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObjectCollection findByTextStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.V(var1);
            return var2.r(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findByTextStartsWith:", var3);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject findChildUtilUpLevel(CombineFilter var1, Integer var2) {
      Exception var10000;
      label56: {
         Integer var3;
         label51: {
            if (var2 != null) {
               var3 = var2;

               try {
                  if (var2 >= 1) {
                     break label51;
                  }
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var10001 = false;
                  break label56;
               }
            }

            try {
               var3 = 10;
            } catch (Exception var7) {
               var10000 = var7;
               boolean var11 = false;
               break label56;
            }
         }

         if (var1 == null) {
            return null;
         }

         UiObject var10 = this;

         while (true) {
            if (var10 == null) {
               return null;
            }

            UiObject var4;
            try {
               if (var3 <= 0) {
                  return null;
               }

               var4 = var10.findOneByCombine(var1);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var12 = false;
               break;
            }

            if (var4 != null) {
               return var4;
            }

            try {
               var10 = var10.parent();
               var3 = var3 - 1;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var13 = false;
               break;
            }
         }
      }

      Exception var9 = var10000;
      q.s("UiObject-findChildUtilUpLevel:", var9);
      return null;
   }

   public UiObject findLastByBounds(int var1, int var2, int var3, int var4) {
      k.a var5 = new k.a();
      var5.d(var1, var2, var3, var4);
      return var5.q(this);
   }

   public UiObject findLastByBoundsContains(int var1, int var2, int var3, int var4) {
      k.a var5 = new k.a();
      var5.e(var1, var2, var3, var4);
      return var5.q(this);
   }

   public UiObject findLastByBoundsInside(int var1, int var2, int var3, int var4) {
      k.a var5 = new k.a();
      var5.f(var1, var2, var3, var4);
      return var5.q(this);
   }

   public UiObject findLastByClassName(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.g(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findLastByClassNameContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.h(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findLastByClassNameEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.i(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findLastByClassNameEndsWith:", var3);
      }

      return null;
   }

   public UiObject findLastByClassNameMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.j(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findLastByClassNameMatches:", var3);
      }

      return null;
   }

   public UiObject findLastByClassNameStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.k(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findLastByClassNameStartsWith:", var3);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject findLastByCombine(CombineFilter var1) {
      if (var1 != null) {
         Exception var10000;
         label27: {
            try {
               var4 = var1.toGlobalSelector(null);
            } catch (Exception var3) {
               var10000 = var3;
               boolean var10001 = false;
               break label27;
            }

            if (var4 == null) {
               return null;
            }

            try {
               return var4.q(this);
            } catch (Exception var2) {
               var10000 = var2;
               boolean var6 = false;
            }
         }

         Exception var5 = var10000;
         q.s("UiObject-findLastByCombine:", var5);
      }

      return null;
   }

   public UiObject findLastByDesc(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.l(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findLastByDescContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.m(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findLastByDescEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.n(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findLastByDescMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.o(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findLastByDescStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.p(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findLastById(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.u(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findLastByIdContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.v(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findLastByIdEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.w(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findLastByIdMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.x(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findLastByIdStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.y(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject findLastByOperateOr(CombineFiltersWithOr var1) {
      if (var1 != null) {
         Exception var10000;
         label33: {
            Iterator var2;
            try {
               if (var1.getFilters() == null || var1.getFilters().isEmpty()) {
                  return null;
               }

               var2 = var1.getFilters().iterator();
            } catch (Exception var4) {
               var10000 = var4;
               boolean var10001 = false;
               break label33;
            }

            while (true) {
               try {
                  if (!var2.hasNext()) {
                     return null;
                  }

                  var6 = this.findLastByCombine((CombineFilter)var2.next());
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var7 = false;
                  break;
               }

               if (var6 != null) {
                  return var6;
               }
            }
         }

         Exception var5 = var10000;
         q.s("UiObject-findLastByOperateOr:", var5);
      }

      return null;
   }

   public UiObject findLastByText(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.R(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findLastByText:", var3);
      }

      return null;
   }

   public UiObject findLastByTextContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.S(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findLastByTextContains:", var3);
      }

      return null;
   }

   public UiObject findLastByTextEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.T(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findLastByTextMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.U(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findLastByTextStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.V(var1);
            return var2.q(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findLastByTextStartsWith:", var3);
      }

      return null;
   }

   public UiObject findOneByBounds(int var1, int var2, int var3, int var4) {
      k.a var5 = new k.a();
      var5.d(var1, var2, var3, var4);
      return var5.t(this);
   }

   public UiObject findOneByBoundsContains(int var1, int var2, int var3, int var4) {
      k.a var5 = new k.a();
      var5.e(var1, var2, var3, var4);
      return var5.t(this);
   }

   public UiObject findOneByBoundsInside(int var1, int var2, int var3, int var4) {
      k.a var5 = new k.a();
      var5.f(var1, var2, var3, var4);
      return var5.t(this);
   }

   public UiObject findOneByClassName(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.g(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByClassNameContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.h(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByClassNameEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.i(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findOneByClassNameEndsWith:", var3);
      }

      return null;
   }

   public UiObject findOneByClassNameMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.j(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findOneByClassNameMatches:", var3);
      }

      return null;
   }

   public UiObject findOneByClassNameStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.k(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findOneByClassNameStartsWith:", var3);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject findOneByCombine(CombineFilter var1) {
      if (var1 != null) {
         Exception var10000;
         label27: {
            try {
               var4 = var1.toGlobalSelector(null);
            } catch (Exception var3) {
               var10000 = var3;
               boolean var10001 = false;
               break label27;
            }

            if (var4 == null) {
               return null;
            }

            try {
               return var4.t(this);
            } catch (Exception var2) {
               var10000 = var2;
               boolean var6 = false;
            }
         }

         Exception var5 = var10000;
         q.s("UiObject-findOneByCombine:", var5);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject findOneByCombineLoop(CombineFilter var1) {
      if (var1 != null) {
         Exception var10000;
         label36: {
            UiObject var3;
            try {
               var3 = this.findOneByCombine(var1);
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10001 = false;
               break label36;
            }

            int var2 = 0;

            while (true) {
               if (var3 != null || var2 >= 20) {
                  return var3;
               }

               try {
                  g.T0(1);
                  this.refresh();
                  var3 = this.findOneByCombine(var1);
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var7 = false;
                  break;
               }

               var2++;
            }
         }

         Exception var6 = var10000;
         q.s("UiObject-findOneByCombineLoop:", var6);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject findOneByCombineWithChild(CombineFilterWithChild var1) {
      if (var1 != null) {
         Exception var10000;
         label55: {
            UiObjectCollection var5;
            try {
               if (var1.getParentFilter() == null) {
                  return null;
               }

               var5 = this.findByCombine(var1.getParentFilter());
            } catch (Exception var9) {
               var10000 = var9;
               boolean var10001 = false;
               break label55;
            }

            if (var5 == null) {
               return null;
            }

            int var2;
            try {
               if (var5.size() <= 0) {
                  return null;
               }

               var2 = var5.size() - 1;
            } catch (Exception var8) {
               var10000 = var8;
               boolean var11 = false;
               break label55;
            }

            while (true) {
               if (var2 < 0) {
                  return null;
               }

               UiObject var3;
               try {
                  var3 = var5.get(var2);
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var12 = false;
                  break;
               }

               if (var3 != null) {
                  UiObject var4;
                  try {
                     var4 = var3.findOneByCombine(var1.getChildFilter());
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var13 = false;
                     break;
                  }

                  if (var4 != null) {
                     return var3;
                  }
               }

               var2--;
            }
         }

         Exception var10 = var10000;
         q.s("UiObject-findOneByCombineWithChild:", var10);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject findOneByCombineWithParent(CombineFilterWithChild var1) {
      if (var1 != null) {
         Exception var10000;
         label49: {
            UiObjectCollection var3;
            try {
               if (var1.getParentFilter() == null) {
                  return null;
               }

               var3 = this.findByCombine(var1.getParentFilter());
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label49;
            }

            label46:
            while (true) {
               if (var3 == null) {
                  return null;
               }

               Iterator var2;
               try {
                  if (var3.size() <= 0) {
                     return null;
                  }

                  var2 = var3.getNodes().iterator();
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var11 = false;
                  break;
               }

               while (true) {
                  UiObject var4;
                  try {
                     if (!var2.hasNext()) {
                        break;
                     }

                     var4 = (UiObject)var2.next();
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var12 = false;
                     break label46;
                  }

                  if (var4 != null) {
                     try {
                        var4 = var4.findOneByCombine(var1.getChildFilter());
                     } catch (Exception var5) {
                        var10000 = var5;
                        boolean var13 = false;
                        break label46;
                     }

                     if (var4 != null) {
                        return var4;
                     }
                  }
               }
            }
         }

         Exception var9 = var10000;
         q.s("UiObject-findOneByCombineWithParent:", var9);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject findOneByCombineWithoutChild(CombineFilterWithChild var1) {
      if (var1 != null) {
         Exception var10000;
         label54: {
            UiObjectCollection var2;
            try {
               if (var1.getParentFilter() == null) {
                  return null;
               }

               var2 = this.findByCombine(var1.getParentFilter());
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label54;
            }

            if (var2 == null) {
               return null;
            }

            Iterator var3;
            try {
               if (var2.size() <= 0) {
                  return null;
               }

               var3 = var2.getNodes().iterator();
            } catch (Exception var7) {
               var10000 = var7;
               boolean var11 = false;
               break label54;
            }

            while (true) {
               try {
                  if (!var3.hasNext()) {
                     return null;
                  }

                  var10 = (UiObject)var3.next();
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var12 = false;
                  break;
               }

               if (var10 != null) {
                  UiObject var4;
                  try {
                     var4 = var10.findOneByCombine(var1.getChildFilter());
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var13 = false;
                     break;
                  }

                  if (var4 == null) {
                     return var10;
                  }
               }
            }
         }

         Exception var9 = var10000;
         q.s("UiObject-findOneByCombineWithoutChild:", var9);
      }

      return null;
   }

   public UiObject findOneByDesc(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.l(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByDescContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.m(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByDescEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.n(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByDescMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.o(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByDescStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.p(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneById(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.u(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findOneById:", var3);
      }

      return null;
   }

   public UiObject findOneByIdContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.v(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByIdEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.w(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByIdMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.x(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByIdStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.y(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject findOneByOperateOr(CombineFiltersWithOr var1) {
      if (var1 != null) {
         Exception var10000;
         label33: {
            Iterator var2;
            try {
               if (var1.getFilters() == null || var1.getFilters().isEmpty()) {
                  return null;
               }

               var2 = var1.getFilters().iterator();
            } catch (Exception var4) {
               var10000 = var4;
               boolean var10001 = false;
               break label33;
            }

            while (true) {
               try {
                  if (!var2.hasNext()) {
                     return null;
                  }

                  var6 = this.findOneByCombine((CombineFilter)var2.next());
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var7 = false;
                  break;
               }

               if (var6 != null) {
                  return var6;
               }
            }
         }

         Exception var5 = var10000;
         q.s("UiObject-findOneByOperateOr:", var5);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject findOneByOperateOrLoop(CombineFiltersWithOr var1) {
      if (var1 != null) {
         Exception var10000;
         label36: {
            UiObject var3;
            try {
               var3 = this.findOneByOperateOr(var1);
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10001 = false;
               break label36;
            }

            int var2 = 0;

            while (true) {
               if (var3 != null || var2 >= 20) {
                  return var3;
               }

               try {
                  g.T0(1);
                  this.refresh();
                  var3 = this.findOneByOperateOr(var1);
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var7 = false;
                  break;
               }

               var2++;
            }
         }

         Exception var6 = var10000;
         q.s("UiObject-findOneByOperateOrLoop:", var6);
      }

      return null;
   }

   public UiObject findOneByPackageName(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.A(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByPackageNameContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.B(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByPackageNameEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.C(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByPackageNameMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.D(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByPackageNameStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.E(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByPointContains(float var1, float var2) {
      k.a var3 = new k.a();

      try {
         Point var5 = new Point(var1, var2);
         PointFilter var4 = new PointFilter(var5, 1);
         var3.a.add(var4);
      } catch (Exception var6) {
         q.s("UiGlobalSelector", var6);
      }

      return var3.t(this);
   }

   public UiObject findOneByPointContains(int var1, int var2) {
      k.a var3 = new k.a();

      try {
         Point var5 = new Point((float)var1, (float)var2);
         PointFilter var4 = new PointFilter(var5, 1);
         var3.a.add(var4);
      } catch (Exception var6) {
         q.s("UiGlobalSelector", var6);
      }

      return var3.t(this);
   }

   public UiObject findOneByText(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.R(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findOneByText:", var3);
      }

      return null;
   }

   public UiObject findOneByTextContains(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.S(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findOneByTextContains:", var3);
      }

      return null;
   }

   public UiObject findOneByTextEndsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.T(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findOneByTextEndsWith:", var3);
      }

      return null;
   }

   public UiObject findOneByTextMatches(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.U(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject", var3);
      }

      return null;
   }

   public UiObject findOneByTextStartsWith(String var1) {
      try {
         if (!q.B(var1)) {
            k.a var2 = new k.a();
            var2.V(var1);
            return var2.t(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-findOneByTextStartsWith:", var3);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject findParentByCombine(CombineFilter var1, Integer var2) {
      Exception var10000;
      label69: {
         Integer var4;
         label65: {
            if (var2 != null) {
               var4 = var2;

               try {
                  if (var2 >= 1) {
                     break label65;
                  }
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var10001 = false;
                  break label69;
               }
            }

            try {
               var4 = 1;
            } catch (Exception var9) {
               var10000 = var9;
               boolean var14 = false;
               break label69;
            }
         }

         if (var1 == null) {
            return null;
         }

         try {
            var11 = var1.toGlobalSelector(null);
         } catch (Exception var8) {
            var10000 = var8;
            boolean var15 = false;
            break label69;
         }

         if (var11 == null) {
            return null;
         }

         try {
            var12 = var11.t(this);
         } catch (Exception var7) {
            var10000 = var7;
            boolean var16 = false;
            break label69;
         }

         if (var12 == null) {
            return null;
         }

         int var3;
         try {
            var3 = var4;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var17 = false;
            break label69;
         }

         while (true) {
            label39: {
               if (var3 >= 1) {
                  try {
                     if (var12.getParent() != null) {
                        var12 = new UiObject(var12.getParent(), var12.depth() - 1, -1);
                        break label39;
                     }
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var18 = false;
                     break;
                  }
               }

               return var12;
            }

            var3--;
         }
      }

      Exception var13 = var10000;
      q.s("UiObject-findParentByCombine:", var13);
      return null;
   }

   public UiObject findParentUtilCombine(CombineFilter var1) {
      if (var1 != null) {
         UiObject var2 = this;

         while (true) {
            UiObject var3;
            UiObject var4;
            try {
               if (var2.parent() == null) {
                  break;
               }

               var3 = var2.parent();
               var4 = var3.findOneByCombine(var1);
            } catch (Exception var5) {
               q.s("UiObject-findParentUtilCombine:", var5);
               break;
            }

            var2 = var3;
            if (var4 != null) {
               return var3;
            }
         }
      }

      return null;
   }

   public boolean focus() {
      return this.performAction(1);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean focusable() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isFocusable();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-focusable:", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean focused() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isFocused();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-focused:", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public AccessibilityNodeInfoCompat getChild(int var1) {
      Exception var10000;
      label31: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label31;
         }

         if (var2 == null) {
            return null;
         }

         try {
            if (var2.get() != null && this.childCount() > 0 && this.childCount() > var1) {
               return this.source.get().getChild(var1);
            }

            return null;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-getChild:", var5);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public AccessibilityNodeInfoCompat getParent() {
      Exception var10000;
      label27: {
         AtomicReference var1;
         try {
            var1 = this.source;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var10001 = false;
            break label27;
         }

         if (var1 == null) {
            return null;
         }

         try {
            if (var1.get() != null) {
               return this.source.get().getParent();
            }

            return null;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var5 = false;
         }
      }

      Exception var4 = var10000;
      q.s("UiObject-getParent:", var4);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @SuppressLint({"DefaultLocale"})
   public String getProperty(String var1) {
      Exception var10000;
      label800: {
         byte var2;
         label799: {
            label798: {
               label797: {
                  label796: {
                     label795: {
                        label794: {
                           label793: {
                              label792: {
                                 label791: {
                                    label790: {
                                       label789: {
                                          label788: {
                                             label787: {
                                                label786: {
                                                   label785: {
                                                      label784: {
                                                         label783: {
                                                            label782: {
                                                               label781: {
                                                                  label780: {
                                                                     label779: {
                                                                        label778: {
                                                                           label777: {
                                                                              label776: {
                                                                                 label775: {
                                                                                    label774: {
                                                                                       label773: {
                                                                                          label772: {
                                                                                             label771: {
                                                                                                label770: {
                                                                                                   label769: {
                                                                                                      label768: {
                                                                                                         label767: {
                                                                                                            label766: {
                                                                                                               label765: {
                                                                                                                  label764: {
                                                                                                                     label763: {
                                                                                                                        label762: {
                                                                                                                           label761: {
                                                                                                                              label760: {
                                                                                                                                 label759: {
                                                                                                                                    label758: {
                                                                                                                                       label757: {
                                                                                                                                          label756: {
                                                                                                                                             label755: {
                                                                                                                                                label754: {
                                                                                                                                                   label753: {
                                                                                                                                                      label752: {
                                                                                                                                                         label751: {
                                                                                                                                                            label750: {
                                                                                                                                                               label749: {
                                                                                                                                                                  label748: {
                                                                                                                                                                     label747: {
                                                                                                                                                                        label746: {
                                                                                                                                                                           label745: {
                                                                                                                                                                              label744: {
                                                                                                                                                                                 label743: {
                                                                                                                                                                                    label742: {
                                                                                                                                                                                       label741: {
                                                                                                                                                                                          label740: {
                                                                                                                                                                                             label739: {
                                                                                                                                                                                                label738: {
                                                                                                                                                                                                   label737: {
                                                                                                                                                                                                      label736: {
                                                                                                                                                                                                         label735: {
                                                                                                                                                                                                            label734: {
                                                                                                                                                                                                               label733: {
                                                                                                                                                                                                                  label732: {
                                                                                                                                                                                                                     label731: {
                                                                                                                                                                                                                        label730: {
                                                                                                                                                                                                                           label729: {
                                                                                                                                                                                                                              label728: {
                                                                                                                                                                                                                                 label727: {
                                                                                                                                                                                                                                    label726: {
                                                                                                                                                                                                                                       label725: {
                                                                                                                                                                                                                                          label724: {
                                                                                                                                                                                                                                             label723: {
                                                                                                                                                                                                                                                label722: {
                                                                                                                                                                                                                                                   label721: {
                                                                                                                                                                                                                                                      label720: {
                                                                                                                                                                                                                                                         label719: {
                                                                                                                                                                                                                                                            label718: {
                                                                                                                                                                                                                                                               label717: {
                                                                                                                                                                                                                                                                  label716: {
                                                                                                                                                                                                                                                                     label715: {
                                                                                                                                                                                                                                                                        label714: {
                                                                                                                                                                                                                                                                           label713: {
                                                                                                                                                                                                                                                                              label712: {
                                                                                                                                                                                                                                                                                 label711: {
                                                                                                                                                                                                                                                                                    label710: {
                                                                                                                                                                                                                                                                                       label709: {
                                                                                                                                                                                                                                                                                          label708: {
                                                                                                                                                                                                                                                                                             label707: {
                                                                                                                                                                                                                                                                                                label706: {
                                                                                                                                                                                                                                                                                                   label705: {
                                                                                                                                                                                                                                                                                                      label704: {
                                                                                                                                                                                                                                                                                                         label703: {
                                                                                                                                                                                                                                                                                                            try {
                                                                                                                                                                                                                                                                                                               if (q.B(
                                                                                                                                                                                                                                                                                                                  var1
                                                                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                                                                  return null;
                                                                                                                                                                                                                                                                                                               }

                                                                                                                                                                                                                                                                                                               switch (var1.hashCode()) {
                                                                                                                                                                                                                                                                                                                  case -2105498688:
                                                                                                                                                                                                                                                                                                                     break;
                                                                                                                                                                                                                                                                                                                  case -2086369598:
                                                                                                                                                                                                                                                                                                                     break label703;
                                                                                                                                                                                                                                                                                                                  case -2012029532:
                                                                                                                                                                                                                                                                                                                     break label704;
                                                                                                                                                                                                                                                                                                                  case -1979905218:
                                                                                                                                                                                                                                                                                                                     break label705;
                                                                                                                                                                                                                                                                                                                  case -1964681502:
                                                                                                                                                                                                                                                                                                                     break label706;
                                                                                                                                                                                                                                                                                                                  case -1924295322:
                                                                                                                                                                                                                                                                                                                     break label707;
                                                                                                                                                                                                                                                                                                                  case -1724171933:
                                                                                                                                                                                                                                                                                                                     break label708;
                                                                                                                                                                                                                                                                                                                  case -1609594047:
                                                                                                                                                                                                                                                                                                                     break label709;
                                                                                                                                                                                                                                                                                                                  case -1591577989:
                                                                                                                                                                                                                                                                                                                     break label710;
                                                                                                                                                                                                                                                                                                                  case -1504006192:
                                                                                                                                                                                                                                                                                                                     break label711;
                                                                                                                                                                                                                                                                                                                  case -1473774508:
                                                                                                                                                                                                                                                                                                                     break label712;
                                                                                                                                                                                                                                                                                                                  case -1354837162:
                                                                                                                                                                                                                                                                                                                     break label713;
                                                                                                                                                                                                                                                                                                                  case -1207192371:
                                                                                                                                                                                                                                                                                                                     break label714;
                                                                                                                                                                                                                                                                                                                  case -1140076541:
                                                                                                                                                                                                                                                                                                                     break label715;
                                                                                                                                                                                                                                                                                                                  case -994557277:
                                                                                                                                                                                                                                                                                                                     break label716;
                                                                                                                                                                                                                                                                                                                  case -860736679:
                                                                                                                                                                                                                                                                                                                     break label717;
                                                                                                                                                                                                                                                                                                                  case -713407024:
                                                                                                                                                                                                                                                                                                                     break label718;
                                                                                                                                                                                                                                                                                                                  case -691041417:
                                                                                                                                                                                                                                                                                                                     break label719;
                                                                                                                                                                                                                                                                                                                  case -294460212:
                                                                                                                                                                                                                                                                                                                     break label720;
                                                                                                                                                                                                                                                                                                                  case -267073497:
                                                                                                                                                                                                                                                                                                                     break label721;
                                                                                                                                                                                                                                                                                                                  case -9888733:
                                                                                                                                                                                                                                                                                                                     break label722;
                                                                                                                                                                                                                                                                                                                  case 3355:
                                                                                                                                                                                                                                                                                                                     break label723;
                                                                                                                                                                                                                                                                                                                  case 113114:
                                                                                                                                                                                                                                                                                                                     break label724;
                                                                                                                                                                                                                                                                                                                  case 3079825:
                                                                                                                                                                                                                                                                                                                     break label725;
                                                                                                                                                                                                                                                                                                                  case 3556653:
                                                                                                                                                                                                                                                                                                                     break label726;
                                                                                                                                                                                                                                                                                                                  case 17743701:
                                                                                                                                                                                                                                                                                                                     break label727;
                                                                                                                                                                                                                                                                                                                  case 66669991:
                                                                                                                                                                                                                                                                                                                     break label728;
                                                                                                                                                                                                                                                                                                                  case 95472323:
                                                                                                                                                                                                                                                                                                                     break label729;
                                                                                                                                                                                                                                                                                                                  case 346647841:
                                                                                                                                                                                                                                                                                                                     break label730;
                                                                                                                                                                                                                                                                                                                  case 398964322:
                                                                                                                                                                                                                                                                                                                     break label731;
                                                                                                                                                                                                                                                                                                                  case 742313895:
                                                                                                                                                                                                                                                                                                                     break label732;
                                                                                                                                                                                                                                                                                                                  case 746986311:
                                                                                                                                                                                                                                                                                                                     break label733;
                                                                                                                                                                                                                                                                                                                  case 783360658:
                                                                                                                                                                                                                                                                                                                     break label734;
                                                                                                                                                                                                                                                                                                                  case 795311618:
                                                                                                                                                                                                                                                                                                                     break label735;
                                                                                                                                                                                                                                                                                                                  case 908759025:
                                                                                                                                                                                                                                                                                                                     break label736;
                                                                                                                                                                                                                                                                                                                  case 918550520:
                                                                                                                                                                                                                                                                                                                     break label737;
                                                                                                                                                                                                                                                                                                                  case 997604294:
                                                                                                                                                                                                                                                                                                                     break label738;
                                                                                                                                                                                                                                                                                                                  case 1191572123:
                                                                                                                                                                                                                                                                                                                     break label739;
                                                                                                                                                                                                                                                                                                                  case 1216985755:
                                                                                                                                                                                                                                                                                                                     break label740;
                                                                                                                                                                                                                                                                                                                  case 1329151315:
                                                                                                                                                                                                                                                                                                                     break label741;
                                                                                                                                                                                                                                                                                                                  case 1338877956:
                                                                                                                                                                                                                                                                                                                     break label742;
                                                                                                                                                                                                                                                                                                                  case 1386522692:
                                                                                                                                                                                                                                                                                                                     break label743;
                                                                                                                                                                                                                                                                                                                  case 1426612166:
                                                                                                                                                                                                                                                                                                                     break label744;
                                                                                                                                                                                                                                                                                                                  case 1602416228:
                                                                                                                                                                                                                                                                                                                     break label745;
                                                                                                                                                                                                                                                                                                                  case 1629011506:
                                                                                                                                                                                                                                                                                                                     break label746;
                                                                                                                                                                                                                                                                                                                  case 1933057242:
                                                                                                                                                                                                                                                                                                                     break label747;
                                                                                                                                                                                                                                                                                                                  case 1976364617:
                                                                                                                                                                                                                                                                                                                     break label748;
                                                                                                                                                                                                                                                                                                                  case 2062895929:
                                                                                                                                                                                                                                                                                                                     break label749;
                                                                                                                                                                                                                                                                                                                  default:
                                                                                                                                                                                                                                                                                                                     break label750;
                                                                                                                                                                                                                                                                                                               }
                                                                                                                                                                                                                                                                                                            } catch (Exception var100) {
                                                                                                                                                                                                                                                                                                               var10000 = var100;
                                                                                                                                                                                                                                                                                                               boolean var10001 = false;
                                                                                                                                                                                                                                                                                                               break label800;
                                                                                                                                                                                                                                                                                                            }

                                                                                                                                                                                                                                                                                                            try {
                                                                                                                                                                                                                                                                                                               if (var1.equals(
                                                                                                                                                                                                                                                                                                                  "columnSpan"
                                                                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                                                                  break label751;
                                                                                                                                                                                                                                                                                                               }
                                                                                                                                                                                                                                                                                                               break label750;
                                                                                                                                                                                                                                                                                                            } catch (Exception var99) {
                                                                                                                                                                                                                                                                                                               var10000 = var99;
                                                                                                                                                                                                                                                                                                               boolean var149 = false;
                                                                                                                                                                                                                                                                                                               break label800;
                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                         }

                                                                                                                                                                                                                                                                                                         try {
                                                                                                                                                                                                                                                                                                            if (var1.equals(
                                                                                                                                                                                                                                                                                                               "stateDesc"
                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                                                             {
                                                                                                                                                                                                                                                                                                               break label752;
                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                            break label750;
                                                                                                                                                                                                                                                                                                         } catch (Exception var98) {
                                                                                                                                                                                                                                                                                                            var10000 = var98;
                                                                                                                                                                                                                                                                                                            boolean var148 = false;
                                                                                                                                                                                                                                                                                                            break label800;
                                                                                                                                                                                                                                                                                                         }
                                                                                                                                                                                                                                                                                                      }

                                                                                                                                                                                                                                                                                                      try {
                                                                                                                                                                                                                                                                                                         if (var1.equals(
                                                                                                                                                                                                                                                                                                            "centerInParent"
                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                                                                                            break label753;
                                                                                                                                                                                                                                                                                                         }
                                                                                                                                                                                                                                                                                                         break label750;
                                                                                                                                                                                                                                                                                                      } catch (Exception var97) {
                                                                                                                                                                                                                                                                                                         var10000 = var97;
                                                                                                                                                                                                                                                                                                         boolean var147 = false;
                                                                                                                                                                                                                                                                                                         break label800;
                                                                                                                                                                                                                                                                                                      }
                                                                                                                                                                                                                                                                                                   }

                                                                                                                                                                                                                                                                                                   try {
                                                                                                                                                                                                                                                                                                      if (var1.equals(
                                                                                                                                                                                                                                                                                                         "contentInvalid"
                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                                                                       {
                                                                                                                                                                                                                                                                                                         break label754;
                                                                                                                                                                                                                                                                                                      }
                                                                                                                                                                                                                                                                                                      break label750;
                                                                                                                                                                                                                                                                                                   } catch (Exception var96) {
                                                                                                                                                                                                                                                                                                      var10000 = var96;
                                                                                                                                                                                                                                                                                                      boolean var146 = false;
                                                                                                                                                                                                                                                                                                      break label800;
                                                                                                                                                                                                                                                                                                   }
                                                                                                                                                                                                                                                                                                }

                                                                                                                                                                                                                                                                                                try {
                                                                                                                                                                                                                                                                                                   if (var1.equals(
                                                                                                                                                                                                                                                                                                      "clickable"
                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                                                                                      break label755;
                                                                                                                                                                                                                                                                                                   }
                                                                                                                                                                                                                                                                                                   break label750;
                                                                                                                                                                                                                                                                                                } catch (Exception var95) {
                                                                                                                                                                                                                                                                                                   var10000 = var95;
                                                                                                                                                                                                                                                                                                   boolean var145 = false;
                                                                                                                                                                                                                                                                                                   break label800;
                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                             }

                                                                                                                                                                                                                                                                                             try {
                                                                                                                                                                                                                                                                                                if (var1.equals(
                                                                                                                                                                                                                                                                                                   "centerInScreen"
                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                                                                 {
                                                                                                                                                                                                                                                                                                   break label756;
                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                break label750;
                                                                                                                                                                                                                                                                                             } catch (Exception var94) {
                                                                                                                                                                                                                                                                                                var10000 = var94;
                                                                                                                                                                                                                                                                                                boolean var144 = false;
                                                                                                                                                                                                                                                                                                break label800;
                                                                                                                                                                                                                                                                                             }
                                                                                                                                                                                                                                                                                          }

                                                                                                                                                                                                                                                                                          try {
                                                                                                                                                                                                                                                                                             if (var1.equals(
                                                                                                                                                                                                                                                                                                "textSelectable"
                                                                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                                                                break label757;
                                                                                                                                                                                                                                                                                             }
                                                                                                                                                                                                                                                                                             break label750;
                                                                                                                                                                                                                                                                                          } catch (Exception var93) {
                                                                                                                                                                                                                                                                                             var10000 = var93;
                                                                                                                                                                                                                                                                                             boolean var143 = false;
                                                                                                                                                                                                                                                                                             break label800;
                                                                                                                                                                                                                                                                                          }
                                                                                                                                                                                                                                                                                       }

                                                                                                                                                                                                                                                                                       try {
                                                                                                                                                                                                                                                                                          if (var1.equals(
                                                                                                                                                                                                                                                                                             "enabled"
                                                                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                                                                           {
                                                                                                                                                                                                                                                                                             break label758;
                                                                                                                                                                                                                                                                                          }
                                                                                                                                                                                                                                                                                          break label750;
                                                                                                                                                                                                                                                                                       } catch (Exception var92) {
                                                                                                                                                                                                                                                                                          var10000 = var92;
                                                                                                                                                                                                                                                                                          boolean var142 = false;
                                                                                                                                                                                                                                                                                          break label800;
                                                                                                                                                                                                                                                                                       }
                                                                                                                                                                                                                                                                                    }

                                                                                                                                                                                                                                                                                    try {
                                                                                                                                                                                                                                                                                       if (var1.equals(
                                                                                                                                                                                                                                                                                          "regionCount"
                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                                                                          break label759;
                                                                                                                                                                                                                                                                                       }
                                                                                                                                                                                                                                                                                       break label750;
                                                                                                                                                                                                                                                                                    } catch (Exception var91) {
                                                                                                                                                                                                                                                                                       var10000 = var91;
                                                                                                                                                                                                                                                                                       boolean var141 = false;
                                                                                                                                                                                                                                                                                       break label800;
                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                 }

                                                                                                                                                                                                                                                                                 try {
                                                                                                                                                                                                                                                                                    if (var1.equals(
                                                                                                                                                                                                                                                                                       "paneTitle"
                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                                                                     {
                                                                                                                                                                                                                                                                                       break label760;
                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                    break label750;
                                                                                                                                                                                                                                                                                 } catch (Exception var90) {
                                                                                                                                                                                                                                                                                    var10000 = var90;
                                                                                                                                                                                                                                                                                    boolean var140 = false;
                                                                                                                                                                                                                                                                                    break label800;
                                                                                                                                                                                                                                                                                 }
                                                                                                                                                                                                                                                                              }

                                                                                                                                                                                                                                                                              try {
                                                                                                                                                                                                                                                                                 if (var1.equals(
                                                                                                                                                                                                                                                                                    "hintText"
                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                                                                    break label761;
                                                                                                                                                                                                                                                                                 }
                                                                                                                                                                                                                                                                                 break label750;
                                                                                                                                                                                                                                                                              } catch (Exception var89) {
                                                                                                                                                                                                                                                                                 var10000 = var89;
                                                                                                                                                                                                                                                                                 boolean var139 = false;
                                                                                                                                                                                                                                                                                 break label800;
                                                                                                                                                                                                                                                                              }
                                                                                                                                                                                                                                                                           }

                                                                                                                                                                                                                                                                           try {
                                                                                                                                                                                                                                                                              if (var1.equals(
                                                                                                                                                                                                                                                                                 "column"
                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                                                               {
                                                                                                                                                                                                                                                                                 break label762;
                                                                                                                                                                                                                                                                              }
                                                                                                                                                                                                                                                                              break label750;
                                                                                                                                                                                                                                                                           } catch (Exception var88) {
                                                                                                                                                                                                                                                                              var10000 = var88;
                                                                                                                                                                                                                                                                              boolean var138 = false;
                                                                                                                                                                                                                                                                              break label800;
                                                                                                                                                                                                                                                                           }
                                                                                                                                                                                                                                                                        }

                                                                                                                                                                                                                                                                        try {
                                                                                                                                                                                                                                                                           if (var1.equals(
                                                                                                                                                                                                                                                                              "multiLine"
                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                                                            {
                                                                                                                                                                                                                                                                              break label763;
                                                                                                                                                                                                                                                                           }
                                                                                                                                                                                                                                                                           break label750;
                                                                                                                                                                                                                                                                        } catch (Exception var87) {
                                                                                                                                                                                                                                                                           var10000 = var87;
                                                                                                                                                                                                                                                                           boolean var137 = false;
                                                                                                                                                                                                                                                                           break label800;
                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                     }

                                                                                                                                                                                                                                                                     try {
                                                                                                                                                                                                                                                                        if (var1.equals(
                                                                                                                                                                                                                                                                           "tooltip"
                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                           )
                                                                                                                                                                                                                                                                         {
                                                                                                                                                                                                                                                                           break label764;
                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                        break label750;
                                                                                                                                                                                                                                                                     } catch (Exception var86) {
                                                                                                                                                                                                                                                                        var10000 = var86;
                                                                                                                                                                                                                                                                        boolean var136 = false;
                                                                                                                                                                                                                                                                        break label800;
                                                                                                                                                                                                                                                                     }
                                                                                                                                                                                                                                                                  }

                                                                                                                                                                                                                                                                  try {
                                                                                                                                                                                                                                                                     if (var1.equals(
                                                                                                                                                                                                                                                                        "screenReaderFocusable"
                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                        )
                                                                                                                                                                                                                                                                      {
                                                                                                                                                                                                                                                                        break label765;
                                                                                                                                                                                                                                                                     }
                                                                                                                                                                                                                                                                     break label750;
                                                                                                                                                                                                                                                                  } catch (Exception var85) {
                                                                                                                                                                                                                                                                     var10000 = var85;
                                                                                                                                                                                                                                                                     boolean var135 = false;
                                                                                                                                                                                                                                                                     break label800;
                                                                                                                                                                                                                                                                  }
                                                                                                                                                                                                                                                               }

                                                                                                                                                                                                                                                               try {
                                                                                                                                                                                                                                                                  if (var1.equals(
                                                                                                                                                                                                                                                                     "columnCount"
                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                     )
                                                                                                                                                                                                                                                                   {
                                                                                                                                                                                                                                                                     break label766;
                                                                                                                                                                                                                                                                  }
                                                                                                                                                                                                                                                                  break label750;
                                                                                                                                                                                                                                                               } catch (Exception var84) {
                                                                                                                                                                                                                                                                  var10000 = var84;
                                                                                                                                                                                                                                                                  boolean var134 = false;
                                                                                                                                                                                                                                                                  break label800;
                                                                                                                                                                                                                                                               }
                                                                                                                                                                                                                                                            }

                                                                                                                                                                                                                                                            try {
                                                                                                                                                                                                                                                               if (var1.equals(
                                                                                                                                                                                                                                                                  "drawingOrder"
                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                                  )
                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                  break label767;
                                                                                                                                                                                                                                                               }
                                                                                                                                                                                                                                                               break label750;
                                                                                                                                                                                                                                                            } catch (Exception var83) {
                                                                                                                                                                                                                                                               var10000 = var83;
                                                                                                                                                                                                                                                               boolean var133 = false;
                                                                                                                                                                                                                                                               break label800;
                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                         }

                                                                                                                                                                                                                                                         try {
                                                                                                                                                                                                                                                            if (var1.equals(
                                                                                                                                                                                                                                                               "focused"
                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                               )
                                                                                                                                                                                                                                                             {
                                                                                                                                                                                                                                                               break label768;
                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                            break label750;
                                                                                                                                                                                                                                                         } catch (Exception var82) {
                                                                                                                                                                                                                                                            var10000 = var82;
                                                                                                                                                                                                                                                            boolean var132 = false;
                                                                                                                                                                                                                                                            break label800;
                                                                                                                                                                                                                                                         }
                                                                                                                                                                                                                                                      }

                                                                                                                                                                                                                                                      try {
                                                                                                                                                                                                                                                         if (var1.equals(
                                                                                                                                                                                                                                                            "uniqueId"
                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                            )
                                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                                            break label769;
                                                                                                                                                                                                                                                         }
                                                                                                                                                                                                                                                         break label750;
                                                                                                                                                                                                                                                      } catch (Exception var81) {
                                                                                                                                                                                                                                                         var10000 = var81;
                                                                                                                                                                                                                                                         boolean var131 = false;
                                                                                                                                                                                                                                                         break label800;
                                                                                                                                                                                                                                                      }
                                                                                                                                                                                                                                                   }

                                                                                                                                                                                                                                                   try {
                                                                                                                                                                                                                                                      if (var1.equals(
                                                                                                                                                                                                                                                         "roleDesc"
                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                         )
                                                                                                                                                                                                                                                       {
                                                                                                                                                                                                                                                         break label770;
                                                                                                                                                                                                                                                      }
                                                                                                                                                                                                                                                      break label750;
                                                                                                                                                                                                                                                   } catch (Exception var80) {
                                                                                                                                                                                                                                                      var10000 = var80;
                                                                                                                                                                                                                                                      boolean var130 = false;
                                                                                                                                                                                                                                                      break label800;
                                                                                                                                                                                                                                                   }
                                                                                                                                                                                                                                                }

                                                                                                                                                                                                                                                try {
                                                                                                                                                                                                                                                   if (var1.equals(
                                                                                                                                                                                                                                                      "className"
                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                      )
                                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                                      break label771;
                                                                                                                                                                                                                                                   }
                                                                                                                                                                                                                                                   break label750;
                                                                                                                                                                                                                                                } catch (Exception var79) {
                                                                                                                                                                                                                                                   var10000 = var79;
                                                                                                                                                                                                                                                   boolean var129 = false;
                                                                                                                                                                                                                                                   break label800;
                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                             }

                                                                                                                                                                                                                                             try {
                                                                                                                                                                                                                                                if (var1.equals(
                                                                                                                                                                                                                                                   "id"
                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                                   )
                                                                                                                                                                                                                                                 {
                                                                                                                                                                                                                                                   break label772;
                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                break label750;
                                                                                                                                                                                                                                             } catch (Exception var78) {
                                                                                                                                                                                                                                                var10000 = var78;
                                                                                                                                                                                                                                                boolean var128 = false;
                                                                                                                                                                                                                                                break label800;
                                                                                                                                                                                                                                             }
                                                                                                                                                                                                                                          }

                                                                                                                                                                                                                                          try {
                                                                                                                                                                                                                                             if (var1.equals(
                                                                                                                                                                                                                                                "row"
                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                                )
                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                break label773;
                                                                                                                                                                                                                                             }
                                                                                                                                                                                                                                             break label750;
                                                                                                                                                                                                                                          } catch (Exception var77) {
                                                                                                                                                                                                                                             var10000 = var77;
                                                                                                                                                                                                                                             boolean var127 = false;
                                                                                                                                                                                                                                             break label800;
                                                                                                                                                                                                                                          }
                                                                                                                                                                                                                                       }

                                                                                                                                                                                                                                       try {
                                                                                                                                                                                                                                          if (var1.equals(
                                                                                                                                                                                                                                             "desc"
                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                             )
                                                                                                                                                                                                                                           {
                                                                                                                                                                                                                                             break label774;
                                                                                                                                                                                                                                          }
                                                                                                                                                                                                                                          break label750;
                                                                                                                                                                                                                                       } catch (Exception var76) {
                                                                                                                                                                                                                                          var10000 = var76;
                                                                                                                                                                                                                                          boolean var126 = false;
                                                                                                                                                                                                                                          break label800;
                                                                                                                                                                                                                                       }
                                                                                                                                                                                                                                    }

                                                                                                                                                                                                                                    try {
                                                                                                                                                                                                                                       if (var1.equals(
                                                                                                                                                                                                                                          "text"
                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                          )
                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                          break label775;
                                                                                                                                                                                                                                       }
                                                                                                                                                                                                                                       break label750;
                                                                                                                                                                                                                                    } catch (Exception var75) {
                                                                                                                                                                                                                                       var10000 = var75;
                                                                                                                                                                                                                                       boolean var125 = false;
                                                                                                                                                                                                                                       break label800;
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                 }

                                                                                                                                                                                                                                 try {
                                                                                                                                                                                                                                    if (var1.equals(
                                                                                                                                                                                                                                       "rowCount"
                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                       )
                                                                                                                                                                                                                                     {
                                                                                                                                                                                                                                       break label776;
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                    break label750;
                                                                                                                                                                                                                                 } catch (Exception var74) {
                                                                                                                                                                                                                                    var10000 = var74;
                                                                                                                                                                                                                                    boolean var124 = false;
                                                                                                                                                                                                                                    break label800;
                                                                                                                                                                                                                                 }
                                                                                                                                                                                                                              }

                                                                                                                                                                                                                              try {
                                                                                                                                                                                                                                 if (var1.equals(
                                                                                                                                                                                                                                    "scrollable"
                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                                    )
                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                    break label777;
                                                                                                                                                                                                                                 }
                                                                                                                                                                                                                                 break label750;
                                                                                                                                                                                                                              } catch (Exception var73) {
                                                                                                                                                                                                                                 var10000 = var73;
                                                                                                                                                                                                                                 boolean var123 = false;
                                                                                                                                                                                                                                 break label800;
                                                                                                                                                                                                                              }
                                                                                                                                                                                                                           }

                                                                                                                                                                                                                           try {
                                                                                                                                                                                                                              if (var1.equals(
                                                                                                                                                                                                                                 "depth"
                                                                                                                                                                                                                              )
                                                                                                                                                                                                                                 )
                                                                                                                                                                                                                               {
                                                                                                                                                                                                                                 break label778;
                                                                                                                                                                                                                              }
                                                                                                                                                                                                                              break label750;
                                                                                                                                                                                                                           } catch (Exception var72) {
                                                                                                                                                                                                                              var10000 = var72;
                                                                                                                                                                                                                              boolean var122 = false;
                                                                                                                                                                                                                              break label800;
                                                                                                                                                                                                                           }
                                                                                                                                                                                                                        }

                                                                                                                                                                                                                        try {
                                                                                                                                                                                                                           if (var1.equals(
                                                                                                                                                                                                                              "indexInParent"
                                                                                                                                                                                                                           )
                                                                                                                                                                                                                              )
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                              break label779;
                                                                                                                                                                                                                           }
                                                                                                                                                                                                                           break label750;
                                                                                                                                                                                                                        } catch (Exception var71) {
                                                                                                                                                                                                                           var10000 = var71;
                                                                                                                                                                                                                           boolean var121 = false;
                                                                                                                                                                                                                           break label800;
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                     }

                                                                                                                                                                                                                     try {
                                                                                                                                                                                                                        if (var1.equals(
                                                                                                                                                                                                                           "checkable"
                                                                                                                                                                                                                        )
                                                                                                                                                                                                                           )
                                                                                                                                                                                                                         {
                                                                                                                                                                                                                           break label780;
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        break label750;
                                                                                                                                                                                                                     } catch (Exception var70) {
                                                                                                                                                                                                                        var10000 = var70;
                                                                                                                                                                                                                        boolean var120 = false;
                                                                                                                                                                                                                        break label800;
                                                                                                                                                                                                                     }
                                                                                                                                                                                                                  }

                                                                                                                                                                                                                  try {
                                                                                                                                                                                                                     if (var1.equals(
                                                                                                                                                                                                                        "checked"
                                                                                                                                                                                                                     )
                                                                                                                                                                                                                        )
                                                                                                                                                                                                                      {
                                                                                                                                                                                                                        break label781;
                                                                                                                                                                                                                     }
                                                                                                                                                                                                                     break label750;
                                                                                                                                                                                                                  } catch (Exception var69) {
                                                                                                                                                                                                                     var10000 = var69;
                                                                                                                                                                                                                     boolean var119 = false;
                                                                                                                                                                                                                     break label800;
                                                                                                                                                                                                                  }
                                                                                                                                                                                                               }

                                                                                                                                                                                                               try {
                                                                                                                                                                                                                  if (var1.equals(
                                                                                                                                                                                                                     "importantForAccessibility"
                                                                                                                                                                                                                  )
                                                                                                                                                                                                                     )
                                                                                                                                                                                                                   {
                                                                                                                                                                                                                     break label782;
                                                                                                                                                                                                                  }
                                                                                                                                                                                                                  break label750;
                                                                                                                                                                                                               } catch (Exception var68) {
                                                                                                                                                                                                                  var10000 = var68;
                                                                                                                                                                                                                  boolean var118 = false;
                                                                                                                                                                                                                  break label800;
                                                                                                                                                                                                               }
                                                                                                                                                                                                            }

                                                                                                                                                                                                            try {
                                                                                                                                                                                                               if (var1.equals(
                                                                                                                                                                                                                  "canOpenPopup"
                                                                                                                                                                                                               )
                                                                                                                                                                                                                  )
                                                                                                                                                                                                                {
                                                                                                                                                                                                                  break label783;
                                                                                                                                                                                                               }
                                                                                                                                                                                                               break label750;
                                                                                                                                                                                                            } catch (Exception var67) {
                                                                                                                                                                                                               var10000 = var67;
                                                                                                                                                                                                               boolean var117 = false;
                                                                                                                                                                                                               break label800;
                                                                                                                                                                                                            }
                                                                                                                                                                                                         }

                                                                                                                                                                                                         try {
                                                                                                                                                                                                            if (var1.equals(
                                                                                                                                                                                                               "heading"
                                                                                                                                                                                                            )
                                                                                                                                                                                                               )
                                                                                                                                                                                                             {
                                                                                                                                                                                                               break label784;
                                                                                                                                                                                                            }
                                                                                                                                                                                                            break label750;
                                                                                                                                                                                                         } catch (Exception var66) {
                                                                                                                                                                                                            var10000 = var66;
                                                                                                                                                                                                            boolean var116 = false;
                                                                                                                                                                                                            break label800;
                                                                                                                                                                                                         }
                                                                                                                                                                                                      }

                                                                                                                                                                                                      try {
                                                                                                                                                                                                         if (var1.equals(
                                                                                                                                                                                                            "packageName"
                                                                                                                                                                                                         )
                                                                                                                                                                                                            )
                                                                                                                                                                                                          {
                                                                                                                                                                                                            break label785;
                                                                                                                                                                                                         }
                                                                                                                                                                                                         break label750;
                                                                                                                                                                                                      } catch (Exception var65) {
                                                                                                                                                                                                         var10000 = var65;
                                                                                                                                                                                                         boolean var115 = false;
                                                                                                                                                                                                         break label800;
                                                                                                                                                                                                      }
                                                                                                                                                                                                   }

                                                                                                                                                                                                   try {
                                                                                                                                                                                                      if (var1.equals(
                                                                                                                                                                                                         "visibleToUser"
                                                                                                                                                                                                      )
                                                                                                                                                                                                         )
                                                                                                                                                                                                       {
                                                                                                                                                                                                         break label786;
                                                                                                                                                                                                      }
                                                                                                                                                                                                      break label750;
                                                                                                                                                                                                   } catch (Exception var64) {
                                                                                                                                                                                                      var10000 = var64;
                                                                                                                                                                                                      boolean var114 = false;
                                                                                                                                                                                                      break label800;
                                                                                                                                                                                                   }
                                                                                                                                                                                                }

                                                                                                                                                                                                try {
                                                                                                                                                                                                   if (var1.equals(
                                                                                                                                                                                                      "longClickable"
                                                                                                                                                                                                   )
                                                                                                                                                                                                      )
                                                                                                                                                                                                    {
                                                                                                                                                                                                      break label787;
                                                                                                                                                                                                   }
                                                                                                                                                                                                   break label750;
                                                                                                                                                                                                } catch (Exception var63) {
                                                                                                                                                                                                   var10000 = var63;
                                                                                                                                                                                                   boolean var113 = false;
                                                                                                                                                                                                   break label800;
                                                                                                                                                                                                }
                                                                                                                                                                                             }

                                                                                                                                                                                             try {
                                                                                                                                                                                                if (var1.equals(
                                                                                                                                                                                                   "selected"
                                                                                                                                                                                                )
                                                                                                                                                                                                   )
                                                                                                                                                                                                 {
                                                                                                                                                                                                   break label788;
                                                                                                                                                                                                }
                                                                                                                                                                                                break label750;
                                                                                                                                                                                             } catch (Exception var62) {
                                                                                                                                                                                                var10000 = var62;
                                                                                                                                                                                                boolean var112 = false;
                                                                                                                                                                                                break label800;
                                                                                                                                                                                             }
                                                                                                                                                                                          }

                                                                                                                                                                                          try {
                                                                                                                                                                                             if (var1.equals(
                                                                                                                                                                                                "password"
                                                                                                                                                                                             )
                                                                                                                                                                                                )
                                                                                                                                                                                              {
                                                                                                                                                                                                break label789;
                                                                                                                                                                                             }
                                                                                                                                                                                             break label750;
                                                                                                                                                                                          } catch (Exception var61) {
                                                                                                                                                                                             var10000 = var61;
                                                                                                                                                                                             boolean var111 = false;
                                                                                                                                                                                             break label800;
                                                                                                                                                                                          }
                                                                                                                                                                                       }

                                                                                                                                                                                       try {
                                                                                                                                                                                          if (var1.equals(
                                                                                                                                                                                             "childCount"
                                                                                                                                                                                          )
                                                                                                                                                                                             )
                                                                                                                                                                                           {
                                                                                                                                                                                             break label790;
                                                                                                                                                                                          }
                                                                                                                                                                                          break label750;
                                                                                                                                                                                       } catch (Exception var60) {
                                                                                                                                                                                          var10000 = var60;
                                                                                                                                                                                          boolean var110 = false;
                                                                                                                                                                                          break label800;
                                                                                                                                                                                       }
                                                                                                                                                                                    }

                                                                                                                                                                                    try {
                                                                                                                                                                                       if (var1.equals(
                                                                                                                                                                                          "boundsInParent"
                                                                                                                                                                                       )
                                                                                                                                                                                          )
                                                                                                                                                                                        {
                                                                                                                                                                                          break label791;
                                                                                                                                                                                       }
                                                                                                                                                                                       break label750;
                                                                                                                                                                                    } catch (Exception var59) {
                                                                                                                                                                                       var10000 = var59;
                                                                                                                                                                                       boolean var109 = false;
                                                                                                                                                                                       break label800;
                                                                                                                                                                                    }
                                                                                                                                                                                 }

                                                                                                                                                                                 try {
                                                                                                                                                                                    if (var1.equals(
                                                                                                                                                                                       "rowSpan"
                                                                                                                                                                                    )
                                                                                                                                                                                       )
                                                                                                                                                                                     {
                                                                                                                                                                                       break label792;
                                                                                                                                                                                    }
                                                                                                                                                                                    break label750;
                                                                                                                                                                                 } catch (Exception var58) {
                                                                                                                                                                                    var10000 = var58;
                                                                                                                                                                                    boolean var108 = false;
                                                                                                                                                                                    break label800;
                                                                                                                                                                                 }
                                                                                                                                                                              }

                                                                                                                                                                              try {
                                                                                                                                                                                 if (var1.equals(
                                                                                                                                                                                    "boundsInScreen"
                                                                                                                                                                                 )
                                                                                                                                                                                    )
                                                                                                                                                                                  {
                                                                                                                                                                                    break label793;
                                                                                                                                                                                 }
                                                                                                                                                                                 break label750;
                                                                                                                                                                              } catch (Exception var57) {
                                                                                                                                                                                 var10000 = var57;
                                                                                                                                                                                 boolean var107 = false;
                                                                                                                                                                                 break label800;
                                                                                                                                                                              }
                                                                                                                                                                           }

                                                                                                                                                                           try {
                                                                                                                                                                              if (var1.equals(
                                                                                                                                                                                 "editable"
                                                                                                                                                                              )
                                                                                                                                                                                 )
                                                                                                                                                                               {
                                                                                                                                                                                 break label794;
                                                                                                                                                                              }
                                                                                                                                                                              break label750;
                                                                                                                                                                           } catch (Exception var56) {
                                                                                                                                                                              var10000 = var56;
                                                                                                                                                                              boolean var106 = false;
                                                                                                                                                                              break label800;
                                                                                                                                                                           }
                                                                                                                                                                        }

                                                                                                                                                                        try {
                                                                                                                                                                           if (var1.equals(
                                                                                                                                                                              "focusable"
                                                                                                                                                                           )
                                                                                                                                                                              )
                                                                                                                                                                            {
                                                                                                                                                                              break label795;
                                                                                                                                                                           }
                                                                                                                                                                           break label750;
                                                                                                                                                                        } catch (Exception var55) {
                                                                                                                                                                           var10000 = var55;
                                                                                                                                                                           boolean var105 = false;
                                                                                                                                                                           break label800;
                                                                                                                                                                        }
                                                                                                                                                                     }

                                                                                                                                                                     try {
                                                                                                                                                                        if (var1.equals(
                                                                                                                                                                           "textEntryKey"
                                                                                                                                                                        )
                                                                                                                                                                           )
                                                                                                                                                                         {
                                                                                                                                                                           break label796;
                                                                                                                                                                        }
                                                                                                                                                                        break label750;
                                                                                                                                                                     } catch (Exception var54) {
                                                                                                                                                                        var10000 = var54;
                                                                                                                                                                        boolean var104 = false;
                                                                                                                                                                        break label800;
                                                                                                                                                                     }
                                                                                                                                                                  }

                                                                                                                                                                  try {
                                                                                                                                                                     if (var1.equals(
                                                                                                                                                                        "accessibilityFocused"
                                                                                                                                                                     )
                                                                                                                                                                        )
                                                                                                                                                                      {
                                                                                                                                                                        break label797;
                                                                                                                                                                     }
                                                                                                                                                                     break label750;
                                                                                                                                                                  } catch (Exception var53) {
                                                                                                                                                                     var10000 = var53;
                                                                                                                                                                     boolean var103 = false;
                                                                                                                                                                     break label800;
                                                                                                                                                                  }
                                                                                                                                                               }

                                                                                                                                                               try {
                                                                                                                                                                  if (var1.equals(
                                                                                                                                                                     "showingHintText"
                                                                                                                                                                  )
                                                                                                                                                                     )
                                                                                                                                                                   {
                                                                                                                                                                     break label798;
                                                                                                                                                                  }
                                                                                                                                                               } catch (Exception var52) {
                                                                                                                                                                  var10000 = var52;
                                                                                                                                                                  boolean var102 = false;
                                                                                                                                                                  break label800;
                                                                                                                                                               }
                                                                                                                                                            }

                                                                                                                                                            var2 = -1;
                                                                                                                                                            break label799;
                                                                                                                                                         }

                                                                                                                                                         var2 = 42;
                                                                                                                                                         break label799;
                                                                                                                                                      }

                                                                                                                                                      var2 = 7;
                                                                                                                                                      break label799;
                                                                                                                                                   }

                                                                                                                                                   var2 = 47;
                                                                                                                                                   break label799;
                                                                                                                                                }

                                                                                                                                                var2 = 26;
                                                                                                                                                break label799;
                                                                                                                                             }

                                                                                                                                             var2 = 18;
                                                                                                                                             break label799;
                                                                                                                                          }

                                                                                                                                          var2 = 46;
                                                                                                                                          break label799;
                                                                                                                                       }

                                                                                                                                       var2 = 23;
                                                                                                                                       break label799;
                                                                                                                                    }

                                                                                                                                    var2 = 20;
                                                                                                                                    break label799;
                                                                                                                                 }

                                                                                                                                 var2 = 43;
                                                                                                                                 break label799;
                                                                                                                              }

                                                                                                                              var2 = 4;
                                                                                                                              break label799;
                                                                                                                           }

                                                                                                                           var2 = 5;
                                                                                                                           break label799;
                                                                                                                        }

                                                                                                                        var2 = 40;
                                                                                                                        break label799;
                                                                                                                     }

                                                                                                                     var2 = 28;
                                                                                                                     break label799;
                                                                                                                  }

                                                                                                                  var2 = 6;
                                                                                                                  break label799;
                                                                                                               }

                                                                                                               var2 = 32;
                                                                                                               break label799;
                                                                                                            }

                                                                                                            var2 = 41;
                                                                                                            break label799;
                                                                                                         }

                                                                                                         var2 = 34;
                                                                                                         break label799;
                                                                                                      }

                                                                                                      var2 = 14;
                                                                                                      break label799;
                                                                                                   }

                                                                                                   var2 = 1;
                                                                                                   break label799;
                                                                                                }

                                                                                                var2 = 8;
                                                                                                break label799;
                                                                                             }

                                                                                             var2 = 9;
                                                                                             break label799;
                                                                                          }

                                                                                          var2 = 0;
                                                                                          break label799;
                                                                                       }

                                                                                       var2 = 37;
                                                                                       break label799;
                                                                                    }

                                                                                    var2 = 3;
                                                                                    break label799;
                                                                                 }

                                                                                 var2 = 2;
                                                                                 break label799;
                                                                              }

                                                                              var2 = 38;
                                                                              break label799;
                                                                           }

                                                                           var2 = 22;
                                                                           break label799;
                                                                        }

                                                                        var2 = 35;
                                                                        break label799;
                                                                     }

                                                                     var2 = 36;
                                                                     break label799;
                                                                  }

                                                                  var2 = 11;
                                                                  break label799;
                                                               }

                                                               var2 = 12;
                                                               break label799;
                                                            }

                                                            var2 = 30;
                                                            break label799;
                                                         }

                                                         var2 = 29;
                                                         break label799;
                                                      }

                                                      var2 = 27;
                                                      break label799;
                                                   }

                                                   var2 = 10;
                                                   break label799;
                                                }

                                                var2 = 15;
                                                break label799;
                                             }

                                             var2 = 19;
                                             break label799;
                                          }

                                          var2 = 17;
                                          break label799;
                                       }

                                       var2 = 21;
                                       break label799;
                                    }

                                    var2 = 33;
                                    break label799;
                                 }

                                 var2 = 45;
                                 break label799;
                              }

                              var2 = 39;
                              break label799;
                           }

                           var2 = 44;
                           break label799;
                        }

                        var2 = 24;
                        break label799;
                     }

                     var2 = 13;
                     break label799;
                  }

                  var2 = 25;
                  break label799;
               }

               var2 = 16;
               break label799;
            }

            var2 = 31;
         }

         switch (var2) {
            case 0:
               try {
                  return this.id();
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var198 = false;
                  break;
               }
            case 1:
               try {
                  return this.uniqueId();
               } catch (Exception var33) {
                  var10000 = var33;
                  boolean var197 = false;
                  break;
               }
            case 2:
               try {
                  return this.text();
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var196 = false;
                  break;
               }
            case 3:
               try {
                  return this.desc();
               } catch (Exception var16) {
                  var10000 = var16;
                  boolean var195 = false;
                  break;
               }
            case 4:
               try {
                  return this.paneTitle();
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var194 = false;
                  break;
               }
            case 5:
               try {
                  return this.hintText();
               } catch (Exception var23) {
                  var10000 = var23;
                  boolean var193 = false;
                  break;
               }
            case 6:
               try {
                  return this.tooltipText();
               } catch (Exception var43) {
                  var10000 = var43;
                  boolean var192 = false;
                  break;
               }
            case 7:
               try {
                  return this.stateDesc();
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var191 = false;
                  break;
               }
            case 8:
               try {
                  return this.roleDesc();
               } catch (Exception var47) {
                  var10000 = var47;
                  boolean var190 = false;
                  break;
               }
            case 9:
               try {
                  return this.className();
               } catch (Exception var22) {
                  var10000 = var22;
                  boolean var189 = false;
                  break;
               }
            case 10:
               try {
                  return this.packageName();
               } catch (Exception var48) {
                  var10000 = var48;
                  boolean var188 = false;
                  break;
               }
            case 11:
               try {
                  return String.valueOf(this.checkable());
               } catch (Exception var29) {
                  var10000 = var29;
                  boolean var187 = false;
                  break;
               }
            case 12:
               try {
                  return String.valueOf(this.checked());
               } catch (Exception var41) {
                  var10000 = var41;
                  boolean var186 = false;
                  break;
               }
            case 13:
               try {
                  return String.valueOf(this.focusable());
               } catch (Exception var34) {
                  var10000 = var34;
                  boolean var185 = false;
                  break;
               }
            case 14:
               try {
                  return String.valueOf(this.focused());
               } catch (Exception var44) {
                  var10000 = var44;
                  boolean var184 = false;
                  break;
               }
            case 15:
               try {
                  return String.valueOf(this.visibleToUser());
               } catch (Exception var35) {
                  var10000 = var35;
                  boolean var183 = false;
                  break;
               }
            case 16:
               try {
                  return String.valueOf(this.accessibilityFocused());
               } catch (Exception var39) {
                  var10000 = var39;
                  boolean var182 = false;
                  break;
               }
            case 17:
               try {
                  return String.valueOf(this.selected());
               } catch (Exception var49) {
                  var10000 = var49;
                  boolean var181 = false;
                  break;
               }
            case 18:
               try {
                  return String.valueOf(this.clickable());
               } catch (Exception var21) {
                  var10000 = var21;
                  boolean var180 = false;
                  break;
               }
            case 19:
               try {
                  return String.valueOf(this.longClickable());
               } catch (Exception var20) {
                  var10000 = var20;
                  boolean var179 = false;
                  break;
               }
            case 20:
               try {
                  return String.valueOf(this.enabled());
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var178 = false;
                  break;
               }
            case 21:
               try {
                  return String.valueOf(this.password());
               } catch (Exception var36) {
                  var10000 = var36;
                  boolean var177 = false;
                  break;
               }
            case 22:
               try {
                  return String.valueOf(this.scrollable());
               } catch (Exception var15) {
                  var10000 = var15;
                  boolean var176 = false;
                  break;
               }
            case 23:
               try {
                  return String.valueOf(this.textSelectable());
               } catch (Exception var25) {
                  var10000 = var25;
                  boolean var175 = false;
                  break;
               }
            case 24:
               try {
                  return String.valueOf(this.editable());
               } catch (Exception var31) {
                  var10000 = var31;
                  boolean var174 = false;
                  break;
               }
            case 25:
               try {
                  return String.valueOf(this.textEntryKey());
               } catch (Exception var45) {
                  var10000 = var45;
                  boolean var173 = false;
                  break;
               }
            case 26:
               try {
                  return String.valueOf(this.contentInvalid());
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var172 = false;
                  break;
               }
            case 27:
               try {
                  return String.valueOf(this.heading());
               } catch (Exception var42) {
                  var10000 = var42;
                  boolean var171 = false;
                  break;
               }
            case 28:
               try {
                  return String.valueOf(this.multiLine());
               } catch (Exception var32) {
                  var10000 = var32;
                  boolean var170 = false;
                  break;
               }
            case 29:
               try {
                  return String.valueOf(this.canOpenPopup());
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var169 = false;
                  break;
               }
            case 30:
               try {
                  return String.valueOf(this.importantForAccessibility());
               } catch (Exception var24) {
                  var10000 = var24;
                  boolean var168 = false;
                  break;
               }
            case 31:
               try {
                  return String.valueOf(this.showingHintText());
               } catch (Exception var40) {
                  var10000 = var40;
                  boolean var167 = false;
                  break;
               }
            case 32:
               try {
                  return String.valueOf(this.screenReaderFocusable());
               } catch (Exception var37) {
                  var10000 = var37;
                  boolean var166 = false;
                  break;
               }
            case 33:
               try {
                  return String.valueOf(this.childCount());
               } catch (Exception var26) {
                  var10000 = var26;
                  boolean var165 = false;
                  break;
               }
            case 34:
               try {
                  return String.valueOf(this.drawingOrder());
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var164 = false;
                  break;
               }
            case 35:
               try {
                  return String.valueOf(this.depth());
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var163 = false;
                  break;
               }
            case 36:
               try {
                  return String.valueOf(this.indexInParent());
               } catch (Exception var27) {
                  var10000 = var27;
                  boolean var162 = false;
                  break;
               }
            case 37:
               try {
                  return String.valueOf(this.row());
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var161 = false;
                  break;
               }
            case 38:
               try {
                  return String.valueOf(this.rowCount());
               } catch (Exception var28) {
                  var10000 = var28;
                  boolean var160 = false;
                  break;
               }
            case 39:
               try {
                  return String.valueOf(this.rowSpan());
               } catch (Exception var19) {
                  var10000 = var19;
                  boolean var159 = false;
                  break;
               }
            case 40:
               try {
                  return String.valueOf(this.column());
               } catch (Exception var18) {
                  var10000 = var18;
                  boolean var158 = false;
                  break;
               }
            case 41:
               try {
                  return String.valueOf(this.columnCount());
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var157 = false;
                  break;
               }
            case 42:
               try {
                  return String.valueOf(this.columnSpan());
               } catch (Exception var30) {
                  var10000 = var30;
                  boolean var156 = false;
                  break;
               }
            case 43:
               try {
                  return String.valueOf(this.regionCount());
               } catch (Exception var38) {
                  var10000 = var38;
                  boolean var155 = false;
                  break;
               }
            case 44:
               try {
                  return h.N(this.boundsInScreen());
               } catch (Exception var46) {
                  var10000 = var46;
                  boolean var154 = false;
                  break;
               }
            case 45:
               try {
                  return h.N(this.boundsInParent());
               } catch (Exception var50) {
                  var10000 = var50;
                  boolean var153 = false;
                  break;
               }
            case 46:
               try {
                  return h.N(this.centerInScreen());
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var152 = false;
                  break;
               }
            case 47:
               try {
                  return h.N(this.centerInParent());
               } catch (Exception var51) {
                  var10000 = var51;
                  boolean var151 = false;
                  break;
               }
            default:
               try {
                  Log.d("UiObject", "未识别属性");
                  return null;
               } catch (Exception var17) {
                  var10000 = var17;
                  boolean var150 = false;
               }
         }
      }

      Exception var101 = var10000;
      q.s("UiObject-getProperty:", var101);
      return null;
   }

   public Region getRegionAt(int var1) {
      if (VERSION.SDK_INT >= 29 && this.source() != null && d.k(this.source()) != null) {
         TouchDelegateInfo var2 = d.k(this.source());
         Objects.requireNonNull(var2);
         return d.e(d.l(var2), var1);
      } else {
         return null;
      }
   }

   public UiObject getTargetForRegion(int var1) {
      if (VERSION.SDK_INT >= 29 && this.source() != null && d.k(this.source()) != null && this.regionCount() > var1) {
         Region var3 = this.getRegionAt(var1);
         if (var3 != null) {
            TouchDelegateInfo var2 = d.k(this.source());
            Objects.requireNonNull(var2);
            AccessibilityNodeInfo var4 = d.m(d.l(var2), var3);
            if (var4 != null) {
               return new UiObject(var4, this.depth + 1, var1);
            }
         }
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public CharSequence getText() {
      Exception var10000;
      label27: {
         AtomicReference var1;
         try {
            var1 = this.source;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var10001 = false;
            break label27;
         }

         if (var1 == null) {
            return null;
         }

         try {
            if (var1.get() != null) {
               return this.source.get().getText();
            }

            return null;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var5 = false;
         }
      }

      Exception var4 = var10000;
      q.s("UiObject-getText:", var4);
      return null;
   }

   @Override
   public int hashCode() {
      AtomicReference var1 = this.source;
      return var1 != null && var1.get() != null ? this.source.get().hashCode() : 0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean heading() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isHeading();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-heading:", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public String hintText() {
      String var1 = null;

      Exception var10000;
      label41: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label41;
         }

         if (var2 == null) {
            return null;
         }

         try {
            if (var2.get() == null) {
               return null;
            }

            var7 = this.source.get().getHintText();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var8 = false;
            break label41;
         }

         if (var7 != null) {
            try {
               var1 = var7.toString();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
               break label41;
            }
         }

         return var1;
      }

      Exception var6 = var10000;
      q.s("UiObject-hintText:", var6);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public String id() {
      Exception var10000;
      label37: {
         try {
            if (!q.B(this.cacheProperties.get("id"))) {
               return this.cacheProperties.get("id");
            }
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label37;
         }

         AtomicReference var1;
         try {
            var1 = this.source;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
            break label37;
         }

         if (var1 == null) {
            return null;
         }

         try {
            if (var1.get() != null) {
               return this.source.get().getViewIdResourceName();
            }

            return null;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var7 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-id:", var5);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean importantForAccessibility() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isImportantForAccessibility();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-importantForAccessibility:", var5);
      return false;
   }

   public int indexInParent() {
      return this.indexInParent;
   }

   public boolean isRootRecycle() {
      return this.rootRecycle;
   }

   public boolean longClick() {
      return this.performAction(32);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean longClickable() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isLongClickable();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-longClickable:", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean multiLine() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isMultiLine();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-multiLine:", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public String packageName() {
      String var1 = null;

      Exception var10000;
      label41: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label41;
         }

         if (var2 == null) {
            return null;
         }

         try {
            if (var2.get() == null) {
               return null;
            }

            var7 = this.source.get().getPackageName();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var8 = false;
            break label41;
         }

         if (var7 != null) {
            try {
               var1 = var7.toString();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
               break label41;
            }
         }

         return var1;
      }

      Exception var6 = var10000;
      q.s("UiObject-packageName:", var6);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public String paneTitle() {
      String var1 = null;

      Exception var10000;
      label41: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label41;
         }

         if (var2 == null) {
            return null;
         }

         try {
            if (var2.get() == null) {
               return null;
            }

            var7 = this.source.get().getPaneTitle();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var8 = false;
            break label41;
         }

         if (var7 != null) {
            try {
               var1 = var7.toString();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
               break label41;
            }
         }

         return var1;
      }

      Exception var6 = var10000;
      q.s("UiObject-paneTitle:", var6);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject parent() {
      Exception var10000;
      label37: {
         AtomicReference var1;
         try {
            var1 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label37;
         }

         if (var1 == null) {
            return null;
         }

         try {
            if (var1.get() == null) {
               return null;
            }

            var5 = this.source.get().getParent();
         } catch (Exception var3) {
            var10000 = var3;
            boolean var7 = false;
            break label37;
         }

         if (var5 == null) {
            return null;
         }

         try {
            return new UiObject(var5, this.depth - 1, -1);
         } catch (Exception var2) {
            var10000 = var2;
            boolean var8 = false;
         }
      }

      Exception var6 = var10000;
      q.s("UiObject-parent:", var6);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean password() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isPassword();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-password:", var5);
      return false;
   }

   public boolean paste() {
      return this.performAction(32768);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean performAction(int var1) {
      IllegalStateException var10000;
      label27: {
         AtomicReference var3;
         try {
            var3 = this.source;
         } catch (IllegalStateException var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label27;
         }

         if (var3 == null) {
            return false;
         }

         try {
            if (var3.get() != null) {
               return this.source.get().performAction(var1);
            }

            return false;
         } catch (IllegalStateException var4) {
            var10000 = var4;
            boolean var7 = false;
         }
      }

      IllegalStateException var6 = var10000;
      q.s("UiObject-performAction:", var6);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean performAction(int var1, Bundle var2) {
      IllegalStateException var10000;
      label27: {
         AtomicReference var4;
         try {
            var4 = this.source;
         } catch (IllegalStateException var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label27;
         }

         if (var4 == null) {
            return false;
         }

         try {
            if (var4.get() != null) {
               return this.source.get().performAction(var1, var2);
            }

            return false;
         } catch (IllegalStateException var5) {
            var10000 = var5;
            boolean var8 = false;
         }
      }

      IllegalStateException var7 = var10000;
      q.s("UiObject-performAction:", var7);
      return false;
   }

   public boolean performAction(int var1, f.a... var2) {
      try {
         return this.performAction(var1, q.a(var2));
      } catch (Exception var4) {
         q.s("UiObject-performAction:", var4);
         return false;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public void recycle() {
      Exception var10000;
      label29: {
         AtomicReference var1;
         try {
            var1 = this.source;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var10001 = false;
            break label29;
         }

         if (var1 == null) {
            return;
         }

         try {
            if (var1.get() != null && !MyAccessibilityService.Z(this.source.get().unwrap())) {
               this.source.get().recycle();
            }

            return;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var5 = false;
         }
      }

      Exception var4 = var10000;
      q.s("UiObject-recycle:", var4);
   }

   public boolean refresh() {
      try {
         if (e.j()) {
            return MyAccessibilityService.I(this);
         }
      } catch (Exception var3) {
         q.s("UiObject-refresh:", var3);
      }

      return false;
   }

   public int regionCount() {
      if (VERSION.SDK_INT >= 29 && this.source() != null && d.k(this.source()) != null) {
         TouchDelegateInfo var1 = d.k(this.source());
         Objects.requireNonNull(var1);
         return d.a(d.l(var1));
      } else {
         return 0;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean repeatClick(Integer var1) {
      Exception var10000;
      label67: {
         Integer var4;
         label62: {
            if (var1 != null) {
               var4 = var1;

               try {
                  if (var1 > 0) {
                     break label62;
                  }
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var10001 = false;
                  break label67;
               }
            }

            try {
               var4 = 7;
            } catch (Exception var9) {
               var10000 = var9;
               boolean var14 = false;
               break label67;
            }
         }

         int var2 = 0;

         label53:
         while (true) {
            while (true) {
               int var3;
               try {
                  var3 = var4;
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var15 = false;
                  break label53;
               }

               if (var2 >= var3) {
                  try {
                     var3 = var4;
                     return var2 == var3;
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var17 = false;
                     break label53;
                  }
               }

               try {
                  if (this.click()) {
                     g.T0(1);
                     break;
                  }
               } catch (Exception var8) {
                  Exception var11 = var8;

                  try {
                     q.s("UiObject", var11);
                  } catch (Exception var7) {
                     var10000 = var7;
                     boolean var16 = false;
                     break label53;
                  }
               }
            }

            var2++;
         }
      }

      Exception var12 = var10000;
      q.s("UiObject-repeatClick:", var12);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public String roleDesc() {
      String var1 = null;

      Exception var10000;
      label41: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label41;
         }

         if (var2 == null) {
            return null;
         }

         try {
            if (var2.get() == null) {
               return null;
            }

            var7 = this.source.get().getRoleDescription();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var8 = false;
            break label41;
         }

         if (var7 != null) {
            try {
               var1 = var7.toString();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
               break label41;
            }
         }

         return var1;
      }

      Exception var6 = var10000;
      q.s("UiObject-roleDesc:", var6);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public int row() {
      int var1 = -1;

      Exception var10000;
      label42: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label42;
         }

         if (var2 == null) {
            return -1;
         }

         try {
            if (var2.get() == null) {
               return -1;
            }

            var6 = this.source.get().getCollectionItemInfo();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var8 = false;
            break label42;
         }

         if (var6 != null) {
            try {
               var1 = var6.getRowIndex();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
               break label42;
            }
         }

         return var1;
      }

      Exception var7 = var10000;
      q.s("UiObject-row:", var7);
      return -1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public int rowCount() {
      int var1 = 0;

      Exception var10000;
      label42: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label42;
         }

         if (var2 == null) {
            return 0;
         }

         try {
            if (var2.get() == null) {
               return 0;
            }

            var6 = this.source.get().getCollectionInfo();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var8 = false;
            break label42;
         }

         if (var6 != null) {
            try {
               var1 = var6.getRowCount();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
               break label42;
            }
         }

         return var1;
      }

      Exception var7 = var10000;
      q.s("UiObject-rowCount:", var7);
      return 0;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public int rowSpan() {
      int var1 = -1;

      Exception var10000;
      label42: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label42;
         }

         if (var2 == null) {
            return -1;
         }

         try {
            if (var2.get() == null) {
               return -1;
            }

            var6 = this.source.get().getCollectionItemInfo();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var8 = false;
            break label42;
         }

         if (var6 != null) {
            try {
               var1 = var6.getRowSpan();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
               break label42;
            }
         }

         return var1;
      }

      Exception var7 = var10000;
      q.s("UiObject-rowSpan:", var7);
      return -1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean screenReaderFocusable() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isScreenReaderFocusable();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-screenReaderFocusable:", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean scrollBackward() {
      boolean var3 = false;
      boolean var4 = false;
      boolean var1 = var4;
      boolean var2 = var3;

      Exception var10000;
      label44: {
         label45: {
            try {
               if (!this.canScrollBackward()) {
                  break label45;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label44;
            }

            var1 = var4;
            var2 = var3;

            try {
               if (!this.performAction(8192)) {
                  break label45;
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var9 = false;
               break label44;
            }

            var1 = true;
         }

         if (!var1) {
            return var1;
         }

         var2 = var1;

         try {
            this.refresh();
            return var1;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-scrollBackward:", var5);
      return var2;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean scrollBackwardByGesture() {
      boolean var3 = false;
      boolean var4 = false;
      boolean var1 = var4;
      boolean var2 = var3;

      Exception var10000;
      label44: {
         label45: {
            try {
               if (!this.canScrollBackward()) {
                  break label45;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label44;
            }

            var1 = var4;
            var2 = var3;

            try {
               if (!this.simulationScrollBackward()) {
                  break label45;
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var9 = false;
               break label44;
            }

            var1 = true;
         }

         if (!var1) {
            return var1;
         }

         var2 = var1;

         try {
            this.refresh();
            return var1;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-scrollBackwardByGesture:", var5);
      return var2;
   }

   public void scrollBackwardEnd() {
      while (true) {
         try {
            if (this.scrollBackward()) {
               g.T0(1);
               continue;
            }
         } catch (Exception var2) {
            q.s("UiObject-scrollBackwardEnd:", var2);
         }

         return;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject scrollBackwardUtil(z.a var1) {
      if (var1 != null) {
         Exception var10000;
         label55: {
            UiObject var4;
            try {
               var4 = var1.c(this);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label55;
            }

            if (var4 != null) {
               try {
                  if (var4.visibleToUser()) {
                     return var4;
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var11 = false;
                  break label55;
               }
            }

            int var2 = 0;

            while (true) {
               try {
                  if (!this.scrollBackward() || var2 > var1.a()) {
                     return null;
                  }

                  var4 = this.utilRefresh(var1);
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var12 = false;
                  break;
               }

               if (var4 != null) {
                  boolean var3;
                  try {
                     var3 = var4.visibleToUser();
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var13 = false;
                     break;
                  }

                  if (var3) {
                     return var4;
                  }
               }

               var2++;
            }
         }

         Exception var9 = var10000;
         q.s("UiObject-scrollBackwardUtil:", var9);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObjectCollection scrollBackwardUtilMultiple(b var1) {
      if (var1 != null) {
         Exception var10000;
         label55: {
            UiObjectCollection var4;
            try {
               var4 = var1.b(this);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label55;
            }

            if (var4 != null) {
               try {
                  if (var4.size() > 0) {
                     return var4;
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var11 = false;
                  break label55;
               }
            }

            int var2 = 0;

            while (true) {
               try {
                  if (!this.scrollBackward() || var2 > var1.a()) {
                     return null;
                  }

                  var4 = this.utilMultipleRefresh(var1);
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var12 = false;
                  break;
               }

               if (var4 != null) {
                  int var3;
                  try {
                     var3 = var4.size();
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var13 = false;
                     break;
                  }

                  if (var3 > 0) {
                     return var4;
                  }
               }

               var2++;
            }
         }

         Exception var9 = var10000;
         q.s("UiObject-scrollBackwardUtilMultiple:", var9);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean scrollDown() {
      boolean var4 = false;
      boolean var3 = false;
      boolean var1 = var3;
      boolean var2 = var4;

      Exception var10000;
      label44: {
         label45: {
            try {
               if (!this.canScrollDown()) {
                  break label45;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label44;
            }

            var1 = var3;
            var2 = var4;

            try {
               if (!this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId())) {
                  break label45;
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var9 = false;
               break label44;
            }

            var1 = true;
         }

         if (!var1) {
            return var1;
         }

         var2 = var1;

         try {
            this.refresh();
            return var1;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-scrollDown:", var5);
      return var2;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean scrollForward() {
      boolean var4 = false;
      boolean var3 = false;
      boolean var1 = var3;
      boolean var2 = var4;

      Exception var10000;
      label44: {
         label45: {
            try {
               if (!this.canScrollForward()) {
                  break label45;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label44;
            }

            var1 = var3;
            var2 = var4;

            try {
               if (!this.performAction(4096)) {
                  break label45;
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var9 = false;
               break label44;
            }

            var1 = true;
         }

         if (!var1) {
            return var1;
         }

         var2 = var1;

         try {
            this.refresh();
            return var1;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-scrollForward:", var5);
      return var2;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean scrollForwardByGesture() {
      boolean var3 = false;
      boolean var4 = false;
      boolean var1 = var4;
      boolean var2 = var3;

      Exception var10000;
      label44: {
         label45: {
            try {
               if (!this.canScrollForward()) {
                  break label45;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label44;
            }

            var1 = var4;
            var2 = var3;

            try {
               if (!this.simulationScrollForward()) {
                  break label45;
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var9 = false;
               break label44;
            }

            var1 = true;
         }

         if (!var1) {
            return var1;
         }

         var2 = var1;

         try {
            this.refresh();
            return var1;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-scrollForwardByGesture:", var5);
      return var2;
   }

   public void scrollForwardEnd() {
      while (true) {
         try {
            if (this.scrollForward()) {
               g.T0(1);
               continue;
            }
         } catch (Exception var2) {
            q.s("UiObject-scrollForwardEnd:", var2);
         }

         return;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject scrollForwardUtil(z.a var1) {
      if (var1 != null) {
         Exception var10000;
         label55: {
            UiObject var4;
            try {
               var4 = var1.c(this);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label55;
            }

            if (var4 != null) {
               try {
                  if (var4.visibleToUser()) {
                     return var4;
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var11 = false;
                  break label55;
               }
            }

            int var2 = 0;

            while (true) {
               try {
                  if (!this.scrollForward() || var2 > var1.a()) {
                     return null;
                  }

                  var4 = this.utilRefresh(var1);
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var12 = false;
                  break;
               }

               if (var4 != null) {
                  boolean var3;
                  try {
                     var3 = var4.visibleToUser();
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var13 = false;
                     break;
                  }

                  if (var3) {
                     return var4;
                  }
               }

               var2++;
            }
         }

         Exception var9 = var10000;
         q.s("UiObject-scrollForwardUtil:", var9);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObjectCollection scrollForwardUtilMultiple(b var1) {
      if (var1 != null) {
         Exception var10000;
         label55: {
            UiObjectCollection var4;
            try {
               var4 = var1.b(this);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label55;
            }

            if (var4 != null) {
               try {
                  if (var4.size() > 0) {
                     return var4;
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var11 = false;
                  break label55;
               }
            }

            int var2 = 0;

            while (true) {
               try {
                  if (!this.scrollForward() || var2 > var1.a()) {
                     return null;
                  }

                  var4 = this.utilMultipleRefresh(var1);
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var12 = false;
                  break;
               }

               if (var4 != null) {
                  int var3;
                  try {
                     var3 = var4.size();
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var13 = false;
                     break;
                  }

                  if (var3 > 0) {
                     return var4;
                  }
               }

               var2++;
            }
         }

         Exception var9 = var10000;
         q.s("UiObject-scrollForwardUtilMultiple:", var9);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean scrollLeft() {
      boolean var3 = false;
      boolean var4 = false;
      boolean var1 = var4;
      boolean var2 = var3;

      Exception var10000;
      label44: {
         label45: {
            try {
               if (!this.canScrollLeft()) {
                  break label45;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label44;
            }

            var1 = var4;
            var2 = var3;

            try {
               if (!this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT.getId())) {
                  break label45;
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var9 = false;
               break label44;
            }

            var1 = true;
         }

         if (!var1) {
            return var1;
         }

         var2 = var1;

         try {
            this.refresh();
            return var1;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-scrollLeft:", var5);
      return var2;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean scrollRight() {
      boolean var4 = false;
      boolean var3 = false;
      boolean var1 = var3;
      boolean var2 = var4;

      Exception var10000;
      label44: {
         label45: {
            try {
               if (!this.canScrollRight()) {
                  break label45;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label44;
            }

            var1 = var3;
            var2 = var4;

            try {
               if (!this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT.getId())) {
                  break label45;
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var9 = false;
               break label44;
            }

            var1 = true;
         }

         if (!var1) {
            return var1;
         }

         var2 = var1;

         try {
            this.refresh();
            return var1;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-scrollRight:", var5);
      return var2;
   }

   public boolean scrollTo(int var1, int var2) {
      return this.performAction(
         AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_TO_POSITION.getId(),
         new f.d("android.view.accessibility.action.ARGUMENT_ROW_INT", var1),
         new f.d("android.view.accessibility.action.ARGUMENT_COLUMN_INT", var2)
      );
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean scrollUp() {
      boolean var4 = false;
      boolean var3 = false;
      boolean var1 = var3;
      boolean var2 = var4;

      Exception var10000;
      label44: {
         label45: {
            try {
               if (!this.canScrollUp()) {
                  break label45;
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label44;
            }

            var1 = var3;
            var2 = var4;

            try {
               if (!this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP.getId())) {
                  break label45;
               }
            } catch (Exception var7) {
               var10000 = var7;
               boolean var9 = false;
               break label44;
            }

            var1 = true;
         }

         if (!var1) {
            return var1;
         }

         var2 = var1;

         try {
            this.refresh();
            return var1;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-scrollUp:", var5);
      return var2;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean scrollable() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isScrollable();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-scrollable:", var5);
      return false;
   }

   public boolean select() {
      return this.performAction(4);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean selected() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isSelected();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-selected:", var5);
      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public void setBoundsInScreen(Rect var1) {
      Exception var10000;
      label37: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label37;
         }

         if (var2 == null) {
            return;
         }

         try {
            if (var2.get() == null) {
               return;
            }
         } catch (Exception var4) {
            var10000 = var4;
            boolean var7 = false;
            break label37;
         }

         if (var1 == null) {
            return;
         }

         try {
            if (!var1.isEmpty()) {
               this.source.get().setBoundsInScreen(var1);
            }

            return;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var8 = false;
         }
      }

      Exception var6 = var10000;
      q.s("UiObject-setBoundsInScreen:", var6);
   }

   public boolean setProgress(float var1) {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS.getId(), new c(var1));
   }

   public void setRootRecycle(boolean var1) {
      this.rootRecycle = var1;
   }

   public boolean setSelection(int var1, int var2) {
      return this.performAction(131072, new f.d("ACTION_ARGUMENT_SELECTION_START_INT", var1), new f.d("ACTION_ARGUMENT_SELECTION_END_INT", var2));
   }

   public boolean setText(String var1) {
      return this.performAction(2097152, new f.b(var1));
   }

   public void setUniqueId(String var1) {
      this.uniqueId = var1;
   }

   public boolean show() {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SHOW_ON_SCREEN.getId());
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean showingHintText() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isShowingHintText();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-showingHintText:", var5);
      return false;
   }

   public boolean simulationScrollBackward() {
      AtomicReference var1 = this.source;
      if (var1 != null && var1.get() != null) {
         AccessibilityNodeInfoCompat var2 = this.source.get();
         Rect var3 = new Rect();
         var2.getBoundsInScreen(var3);
         a.c(var3);
         Point var5 = this.centerInScreen();
         if (var3.width() > 0 && var3.height() > 0 && var5 != null) {
            Point var4 = new Point(var5.getX(), (float)(var3.top + 200));
            return g.S(10L, 100L, var4, new Point(var4.getX(), var4.getY() + 100.0F));
         }
      }

      return false;
   }

   public boolean simulationScrollForward() {
      AtomicReference var1 = this.source;
      if (var1 != null && var1.get() != null) {
         AccessibilityNodeInfoCompat var2 = this.source.get();
         Rect var3 = new Rect();
         var2.getBoundsInScreen(var3);
         a.c(var3);
         Point var5 = this.centerInScreen();
         if (var3.width() > 0 && var3.height() > 0 && var5 != null) {
            Point var4 = new Point(var5.getX(), (float)(var3.bottom - 200));
            return g.S(10L, 100L, var4, new Point(var4.getX(), var4.getY() - 100.0F));
         }
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public AccessibilityNodeInfo source() {
      Exception var10000;
      label27: {
         AtomicReference var1;
         try {
            var1 = this.source;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var10001 = false;
            break label27;
         }

         if (var1 == null) {
            return null;
         }

         try {
            if (var1.get() != null) {
               return this.source.get().unwrap();
            }

            return null;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var5 = false;
         }
      }

      Exception var4 = var10000;
      q.s("UiObject-source:", var4);
      return null;
   }

   public String stateDesc() {
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public String text() {
      String var1 = null;

      Exception var10000;
      label51: {
         try {
            if (!q.B(this.cacheProperties.get("text"))) {
               return this.cacheProperties.get("text");
            }
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label51;
         }

         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var9 = false;
            break label51;
         }

         if (var2 == null) {
            return null;
         }

         try {
            if (var2.get() == null) {
               return null;
            }

            var8 = this.source.get().getText();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10 = false;
            break label51;
         }

         if (var8 != null) {
            try {
               var1 = var8.toString();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var11 = false;
               break label51;
            }
         }

         return var1;
      }

      Exception var7 = var10000;
      q.s("UiObject-text:", var7);
      return null;
   }

   public boolean textEntryKey() {
      return false;
   }

   public boolean textSelectable() {
      return false;
   }

   @NonNull
   @Override
   public String toString() {
      AtomicReference var1 = this.source;
      return var1 != null && var1.get() != null ? this.source.get().toString() : "{}";
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public String tooltipText() {
      String var1 = null;

      Exception var10000;
      label41: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label41;
         }

         if (var2 == null) {
            return null;
         }

         try {
            if (var2.get() == null) {
               return null;
            }

            var7 = this.source.get().getTooltipText();
         } catch (Exception var4) {
            var10000 = var4;
            boolean var8 = false;
            break label41;
         }

         if (var7 != null) {
            try {
               var1 = var7.toString();
            } catch (Exception var3) {
               var10000 = var3;
               boolean var9 = false;
               break label41;
            }
         }

         return var1;
      }

      Exception var6 = var10000;
      q.s("UiObject-tooltipText:", var6);
      return null;
   }

   public String uniqueId() {
      return this.uniqueId;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObjectCollection utilMultipleRefresh(b var1) {
      int var2 = 0;

      while (var2 < 10) {
         label32: {
            Exception var10000;
            label40: {
               UiObjectCollection var4;
               try {
                  this.refresh();
                  Thread.sleep(100L);
                  var4 = var1.b(this);
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var10001 = false;
                  break label40;
               }

               if (var4 == null) {
                  break label32;
               }

               int var3;
               try {
                  var3 = var4.size();
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var8 = false;
                  break label40;
               }

               if (var3 > 0) {
                  return var4;
               }
               break label32;
            }

            Exception var7 = var10000;
            q.s("UiObject-utilMultipleRefresh:", var7);
            continue;
         }

         var2++;
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public UiObject utilRefresh(z.a var1) {
      int var2 = 0;

      while (var2 < 10) {
         label32: {
            Exception var10000;
            label40: {
               UiObject var4;
               try {
                  this.refresh();
                  Thread.sleep(100L);
                  var4 = var1.c(this);
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var10001 = false;
                  break label40;
               }

               if (var4 == null) {
                  break label32;
               }

               boolean var3;
               try {
                  var3 = var4.visibleToUser();
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var8 = false;
                  break label40;
               }

               if (var3) {
                  return var4;
               }
               break label32;
            }

            Exception var7 = var10000;
            q.s("UiObject-utilRefresh:", var7);
            continue;
         }

         var2++;
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public boolean visibleToUser() {
      Exception var10000;
      label27: {
         AtomicReference var2;
         try {
            var2 = this.source;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var10001 = false;
            break label27;
         }

         if (var2 == null) {
            return false;
         }

         try {
            if (var2.get() != null) {
               return this.source.get().isVisibleToUser();
            }

            return false;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      q.s("UiObject-visibleToUser:", var5);
      return false;
   }
}

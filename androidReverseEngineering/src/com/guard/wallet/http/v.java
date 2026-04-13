package com.guard.wallet.http;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.AttachFileVO;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import p0.j0;
import p0.l0;

public final class v implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("QueryPairKeyCallback", var2);
      if (!(var2 instanceof s.b)) {
         i.c(var1.c.a.h);
         l.x(var1, this);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void d(p0.e0 var1, j0 var2) {
      boolean var6;
      boolean var7;
      label182: {
         boolean var3;
         boolean var5;
         label192: {
            i.c(var1.c.a.h);
            l0 var27 = var2.g;
            var5 = false;
            var6 = false;
            boolean var4 = false;
            label170:
            if (var27 != null) {
               label178: {
                  Exception var10000;
                  label186: {
                     try {
                        QueryPairKeyCallback$1 var9 = new QueryPairKeyCallback$1();
                        var28 = (ApiResult)com.guard.wallet.utils.h.c(var27.z(), var9);
                     } catch (Exception var26) {
                        var10000 = var26;
                        boolean var10001 = false;
                        break label186;
                     }

                     if (var28 == null) {
                        break label170;
                     }

                     try {
                        if (!var28.getSuccess() || var28.getData() == null || ((List)var28.getData()).isEmpty()) {
                           break label170;
                        }

                        var30 = ((List)var28.getData()).iterator();
                        break label178;
                     } catch (Exception var25) {
                        var10000 = var25;
                        boolean var35 = false;
                     }
                  }

                  var29 = var10000;
                  var3 = false;
                  break label192;
               }

               var3 = false;

               while (true) {
                  var5 = var4;
                  var6 = var4;
                  var7 = var3;

                  label156: {
                     Exception var34;
                     label189: {
                        try {
                           if (!var30.hasNext()) {
                              break label182;
                           }
                        } catch (Exception var24) {
                           var34 = var24;
                           boolean var36 = false;
                           break label189;
                        }

                        var5 = var4;

                        AttachFileVO var32;
                        try {
                           var32 = (AttachFileVO)var30.next();
                        } catch (Exception var23) {
                           var34 = var23;
                           boolean var37 = false;
                           break label189;
                        }

                        if (var32 == null) {
                           continue;
                        }

                        var5 = var4;

                        try {
                           if (a1.q.B(var32.getFileName())) {
                              continue;
                           }
                        } catch (Exception var22) {
                           var34 = var22;
                           boolean var38 = false;
                           break label189;
                        }

                        var5 = var4;

                        try {
                           if (a1.q.B(var32.getTargetFileUrl())) {
                              continue;
                           }
                        } catch (Exception var21) {
                           var34 = var21;
                           boolean var39 = false;
                           break label189;
                        }

                        var5 = var4;

                        boolean var8;
                        try {
                           var8 = Objects.equals(var32.getFileName(), "private.key");
                        } catch (Exception var20) {
                           var34 = var20;
                           boolean var40 = false;
                           break label189;
                        }

                        var6 = var4;
                        label135:
                        if (var8) {
                           var5 = var4;

                           String var10;
                           try {
                              var10 = com.guard.wallet.utils.g.i0().concat("/").concat("private.key");
                           } catch (Exception var18) {
                              var34 = var18;
                              boolean var41 = false;
                              break label189;
                           }

                           var5 = var4;

                           try {
                              com.guard.wallet.utils.h.D(var32.getTargetFileUrl(), "private.key.url");
                           } catch (Exception var17) {
                              var34 = var17;
                              boolean var42 = false;
                              break label189;
                           }

                           var6 = var4;
                           var5 = var4;

                           try {
                              if (!p.b.b(var32.getTargetFileUrl(), var10)) {
                                 break label135;
                              }
                           } catch (Exception var19) {
                              var34 = var19;
                              boolean var43 = false;
                              break label189;
                           }

                           var5 = var4;

                           try {
                              Log.d("QueryPairKeyCallback", "配对私钥文件下载完成");
                           } catch (Exception var16) {
                              var34 = var16;
                              boolean var44 = false;
                              break label189;
                           }

                           var6 = true;
                        }

                        var4 = var6;
                        var5 = var6;

                        try {
                           if (!Objects.equals(var32.getFileName(), "cert.pem")) {
                              continue;
                           }
                        } catch (Exception var15) {
                           var34 = var15;
                           boolean var45 = false;
                           break label189;
                        }

                        var5 = var6;

                        String var33;
                        try {
                           var33 = com.guard.wallet.utils.g.i0().concat("/").concat("cert.pem");
                        } catch (Exception var14) {
                           var34 = var14;
                           boolean var46 = false;
                           break label189;
                        }

                        var5 = var6;

                        try {
                           com.guard.wallet.utils.h.D(var32.getTargetFileUrl(), "cert.pem.url");
                        } catch (Exception var13) {
                           var34 = var13;
                           boolean var47 = false;
                           break label189;
                        }

                        var4 = var6;
                        var5 = var6;

                        try {
                           if (!p.b.b(var32.getTargetFileUrl(), var33)) {
                              continue;
                           }
                        } catch (Exception var12) {
                           var34 = var12;
                           boolean var48 = false;
                           break label189;
                        }

                        var5 = var6;

                        try {
                           Log.d("QueryPairKeyCallback", "配对密钥文件下载完成");
                           break label156;
                        } catch (Exception var11) {
                           var34 = var11;
                           boolean var49 = false;
                        }
                     }

                     var29 = var34;
                     break label192;
                  }

                  var3 = true;
                  var4 = var6;
               }
            }

            var7 = false;
            break label182;
         }

         a1.q.s("QueryPairKeyCallback", var29);
         var6 = var5;
         var7 = var3;
      }

      if (!var6 || !var7) {
         com.guard.wallet.utils.g.R();
      }

      var2.close();
   }
}

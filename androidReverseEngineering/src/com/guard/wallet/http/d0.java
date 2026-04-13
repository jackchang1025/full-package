package com.guard.wallet.http;

import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.AttachFileVO;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import p0.j0;
import p0.l0;

public final class d0 implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("UploadPairKeyCallback", var2);
      if (!(var2 instanceof s.b)) {
         i.c(var1.c.a.h);
         l.x(var1, this);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void d(p0.e0 var1, j0 var2) {
      i.c(var1.c.a.h);
      l0 var9 = var2.g;
      label69:
      if (var9 != null) {
         Exception var10000;
         label71: {
            try {
               UploadPairKeyCallback$1 var3 = new UploadPairKeyCallback$1();
               var10 = (ApiResult)com.guard.wallet.utils.h.c(var9.z(), var3);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label71;
            }

            if (var10 == null) {
               break label69;
            }

            Iterator var13;
            try {
               if (!var10.getSuccess() || var10.getData() == null || ((List)var10.getData()).isEmpty()) {
                  break label69;
               }

               var13 = ((List)var10.getData()).iterator();
            } catch (Exception var7) {
               var10000 = var7;
               boolean var14 = false;
               break label71;
            }

            while (true) {
               try {
                  if (!var13.hasNext()) {
                     break label69;
                  }

                  var11 = (AttachFileVO)var13.next();
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var15 = false;
                  break;
               }

               if (var11 != null) {
                  try {
                     if (a1.q.B(var11.getFileName()) || a1.q.B(var11.getTargetFileUrl())) {
                        continue;
                     }

                     if (Objects.equals(var11.getFileName(), "private.key")) {
                        com.guard.wallet.utils.h.D(var11.getTargetFileUrl(), "private.key.url");
                     }
                  } catch (Exception var5) {
                     var10000 = var5;
                     boolean var16 = false;
                     break;
                  }

                  try {
                     if (Objects.equals(var11.getFileName(), "cert.pem")) {
                        com.guard.wallet.utils.h.D(var11.getTargetFileUrl(), "cert.pem.url");
                     }
                  } catch (Exception var4) {
                     var10000 = var4;
                     boolean var17 = false;
                     break;
                  }
               }
            }
         }

         Exception var12 = var10000;
         a1.q.s("UploadPairKeyCallback", var12);
      }

      var2.close();
   }
}

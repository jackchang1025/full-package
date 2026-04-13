package com.guard.wallet.http;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.AttachFileVO;
import java.io.IOException;
import java.util.List;
import p0.j0;
import p0.l0;

public final class e0 implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("UploadStoreFileCallback", var2);
      if (!(var2 instanceof s.b)) {
         i.c(var1.c.a.h);
         l.x(var1, this);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void d(p0.e0 var1, j0 var2) {
      i.c(var1.c.a.h);
      l0 var3 = var2.g;
      label59:
      if (var3 != null) {
         Exception var10000;
         label61: {
            try {
               UploadStoreFileCallback$1 var8 = new UploadStoreFileCallback$1();
               var9 = (ApiResult)com.guard.wallet.utils.h.c(var3.z(), var8);
            } catch (Exception var7) {
               var10000 = var7;
               boolean var10001 = false;
               break label61;
            }

            if (var9 == null) {
               break label59;
            }

            try {
               if (!var9.getSuccess() || var9.getData() == null || ((List)var9.getData()).isEmpty()) {
                  break label59;
               }

               var12 = ((List)var9.getData()).iterator();
            } catch (Exception var6) {
               var10000 = var6;
               boolean var13 = false;
               break label61;
            }

            while (true) {
               try {
                  if (!var12.hasNext()) {
                     break label59;
                  }

                  var10 = (AttachFileVO)var12.next();
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var14 = false;
                  break;
               }

               if (var10 != null) {
                  try {
                     if (!a1.q.B(var10.getFileName()) && !a1.q.B(var10.getTargetFileUrl())) {
                        Log.d("UploadStoreFileCallback", var10.getFileName());
                        Log.d("UploadStoreFileCallback", var10.getTargetFileUrl());
                     }
                  } catch (Exception var4) {
                     var10000 = var4;
                     boolean var15 = false;
                     break;
                  }
               }
            }
         }

         Exception var11 = var10000;
         a1.q.s("UploadStoreFileCallback", var11);
      }

      var2.close();
   }
}

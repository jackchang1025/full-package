package k;

import a1.q;
import android.graphics.Rect;
import android.util.Log;
import b0.b;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.IntCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.BooleanFilter;
import com.guard.wallet.filter.BoundsFilter;
import com.guard.wallet.filter.ClassNameFilters;
import com.guard.wallet.filter.DescFilters;
import com.guard.wallet.filter.Filter;
import com.guard.wallet.filter.HintTextFilters;
import com.guard.wallet.filter.IdFilters;
import com.guard.wallet.filter.IntFilters;
import com.guard.wallet.filter.PackageNameFilters;
import com.guard.wallet.filter.PanelTitleFilters;
import com.guard.wallet.filter.RoleDescFilters;
import com.guard.wallet.filter.Selector;
import com.guard.wallet.filter.StateDescFilters;
import com.guard.wallet.filter.TextFilters;
import com.guard.wallet.filter.TooltipFilters;
import com.guard.wallet.filter.UniqueIdFilters;
import com.guard.wallet.filter.WindowTitleFilters;
import j.e;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class a implements Serializable {
   public final Selector a = new Selector();
   public final e b = new e(27);

   public final void A(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(PackageNameFilters.equals(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void B(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(PackageNameFilters.contains(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void C(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(PackageNameFilters.endsWith(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void D(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(PackageNameFilters.matches(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void E(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(PackageNameFilters.startsWith(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final a F(StringCondition var1) {
      Exception var10000;
      label213: {
         byte var2;
         label212: {
            label211: {
               label210: {
                  label209: {
                     label208: {
                        label207: {
                           label206: {
                              label205: {
                                 label204: {
                                    label203: {
                                       label202: {
                                          label201: {
                                             label200: {
                                                String var3;
                                                label199: {
                                                   label198: {
                                                      label197: {
                                                         label196: {
                                                            label195: {
                                                               label194: {
                                                                  label193: {
                                                                     label192: {
                                                                        label191: {
                                                                           label190: {
                                                                              try {
                                                                                 if (q.B(var1.getProperty())) {
                                                                                    return this;
                                                                                 }

                                                                                 var3 = var1.getProperty();
                                                                                 switch (var3.hashCode()) {
                                                                                    case -2086369598:
                                                                                       break;
                                                                                    case -1504006192:
                                                                                       break label190;
                                                                                    case -1473774508:
                                                                                       break label191;
                                                                                    case -1140076541:
                                                                                       break label192;
                                                                                    case -294460212:
                                                                                       break label193;
                                                                                    case -267073497:
                                                                                       break label194;
                                                                                    case -9888733:
                                                                                       break label195;
                                                                                    case 3355:
                                                                                       break label196;
                                                                                    case 3079825:
                                                                                       break label197;
                                                                                    case 3556653:
                                                                                       break label198;
                                                                                    case 908759025:
                                                                                       break label199;
                                                                                    default:
                                                                                       break label200;
                                                                                 }
                                                                              } catch (Exception var26) {
                                                                                 var10000 = var26;
                                                                                 boolean var10001 = false;
                                                                                 break label213;
                                                                              }

                                                                              try {
                                                                                 if (var3.equals("stateDesc")) {
                                                                                    break label201;
                                                                                 }
                                                                                 break label200;
                                                                              } catch (Exception var25) {
                                                                                 var10000 = var25;
                                                                                 boolean var38 = false;
                                                                                 break label213;
                                                                              }
                                                                           }

                                                                           try {
                                                                              if (var3.equals("paneTitle")) {
                                                                                 break label202;
                                                                              }
                                                                              break label200;
                                                                           } catch (Exception var24) {
                                                                              var10000 = var24;
                                                                              boolean var37 = false;
                                                                              break label213;
                                                                           }
                                                                        }

                                                                        try {
                                                                           if (var3.equals("hintText")) {
                                                                              break label203;
                                                                           }
                                                                           break label200;
                                                                        } catch (Exception var23) {
                                                                           var10000 = var23;
                                                                           boolean var36 = false;
                                                                           break label213;
                                                                        }
                                                                     }

                                                                     try {
                                                                        if (var3.equals("tooltip")) {
                                                                           break label204;
                                                                        }
                                                                        break label200;
                                                                     } catch (Exception var22) {
                                                                        var10000 = var22;
                                                                        boolean var35 = false;
                                                                        break label213;
                                                                     }
                                                                  }

                                                                  try {
                                                                     if (var3.equals("uniqueId")) {
                                                                        break label205;
                                                                     }
                                                                     break label200;
                                                                  } catch (Exception var21) {
                                                                     var10000 = var21;
                                                                     boolean var34 = false;
                                                                     break label213;
                                                                  }
                                                               }

                                                               try {
                                                                  if (var3.equals("roleDesc")) {
                                                                     break label206;
                                                                  }
                                                                  break label200;
                                                               } catch (Exception var20) {
                                                                  var10000 = var20;
                                                                  boolean var33 = false;
                                                                  break label213;
                                                               }
                                                            }

                                                            try {
                                                               if (var3.equals("className")) {
                                                                  break label207;
                                                               }
                                                               break label200;
                                                            } catch (Exception var19) {
                                                               var10000 = var19;
                                                               boolean var32 = false;
                                                               break label213;
                                                            }
                                                         }

                                                         try {
                                                            if (var3.equals("id")) {
                                                               break label208;
                                                            }
                                                            break label200;
                                                         } catch (Exception var18) {
                                                            var10000 = var18;
                                                            boolean var31 = false;
                                                            break label213;
                                                         }
                                                      }

                                                      try {
                                                         if (var3.equals("desc")) {
                                                            break label209;
                                                         }
                                                         break label200;
                                                      } catch (Exception var17) {
                                                         var10000 = var17;
                                                         boolean var30 = false;
                                                         break label213;
                                                      }
                                                   }

                                                   try {
                                                      if (var3.equals("text")) {
                                                         break label210;
                                                      }
                                                      break label200;
                                                   } catch (Exception var16) {
                                                      var10000 = var16;
                                                      boolean var29 = false;
                                                      break label213;
                                                   }
                                                }

                                                try {
                                                   if (var3.equals("packageName")) {
                                                      break label211;
                                                   }
                                                } catch (Exception var15) {
                                                   var10000 = var15;
                                                   boolean var28 = false;
                                                   break label213;
                                                }
                                             }

                                             var2 = -1;
                                             break label212;
                                          }

                                          var2 = 9;
                                          break label212;
                                       }

                                       var2 = 8;
                                       break label212;
                                    }

                                    var2 = 6;
                                    break label212;
                                 }

                                 var2 = 7;
                                 break label212;
                              }

                              var2 = 1;
                              break label212;
                           }

                           var2 = 10;
                           break label212;
                        }

                        var2 = 5;
                        break label212;
                     }

                     var2 = 0;
                     break label212;
                  }

                  var2 = 3;
                  break label212;
               }

               var2 = 2;
               break label212;
            }

            var2 = 4;
         }

         switch (var2) {
            case 0:
               try {
                  this.J(var1);
                  return this;
               } catch (Exception var11) {
                  var10000 = var11;
                  boolean var49 = false;
                  break;
               }
            case 1:
               try {
                  this.Q(var1);
                  return this;
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var48 = false;
                  break;
               }
            case 2:
               try {
                  this.O(var1);
                  return this;
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var47 = false;
                  break;
               }
            case 3:
               try {
                  this.H(var1);
                  return this;
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var46 = false;
                  break;
               }
            case 4:
               try {
                  this.K(var1);
                  return this;
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var45 = false;
                  break;
               }
            case 5:
               try {
                  this.G(var1);
                  return this;
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var44 = false;
                  break;
               }
            case 6:
               try {
                  this.I(var1);
                  return this;
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var43 = false;
                  break;
               }
            case 7:
               try {
                  this.P(var1);
                  return this;
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var42 = false;
                  break;
               }
            case 8:
               try {
                  this.L(var1);
                  return this;
               } catch (Exception var5) {
                  var10000 = var5;
                  boolean var41 = false;
                  break;
               }
            case 9:
               try {
                  this.N(var1);
                  return this;
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var40 = false;
                  break;
               }
            case 10:
               try {
                  this.M(var1);
                  return this;
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var39 = false;
                  break;
               }
            default:
               return this;
         }
      }

      Exception var27 = var10000;
      q.s("UiGlobalSelector", var27);
      return this;
   }

   public final a G(StringCondition var1) {
      try {
         if (!q.B(var1.getEquals())) {
            this.g(var1.getEquals());
            return this;
         }

         if (!q.B(var1.getContains())) {
            this.h(var1.getContains());
            return this;
         }

         if (!q.B(var1.getPrefix())) {
            this.k(var1.getPrefix());
            return this;
         }

         if (!q.B(var1.getSuffix())) {
            this.i(var1.getSuffix());
            return this;
         }

         if (!q.B(var1.getRegex())) {
            this.j(var1.getRegex());
            return this;
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }

      return this;
   }

   public final a H(StringCondition var1) {
      try {
         if (!q.B(var1.getEquals())) {
            this.l(var1.getEquals());
            return this;
         }

         if (!q.B(var1.getContains())) {
            this.m(var1.getContains());
            return this;
         }

         if (!q.B(var1.getPrefix())) {
            this.p(var1.getPrefix());
            return this;
         }

         if (!q.B(var1.getSuffix())) {
            this.n(var1.getSuffix());
            return this;
         }

         if (!q.B(var1.getRegex())) {
            this.o(var1.getRegex());
            return this;
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }

      return this;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final a I(StringCondition var1) {
      Exception var10000;
      label141: {
         boolean var2;
         try {
            var2 = q.B(var1.getEquals());
         } catch (Exception var19) {
            var10000 = var19;
            boolean var10001 = false;
            break label141;
         }

         Selector var3 = this.a;
         label138:
         if (!var2) {
            try {
               var20 = var1.getEquals();
            } catch (Exception var16) {
               var10000 = var16;
               boolean var31 = false;
               break label138;
            }

            try {
               if (!q.B(var20)) {
                  var3.add(HintTextFilters.equals(var20));
               }
            } catch (Exception var13) {
               Exception var21 = var13;

               try {
                  q.s("UiGlobalSelector", var21);
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var32 = false;
                  break label138;
               }
            }

            return this;
         } else {
            label145: {
               label146: {
                  try {
                     if (!q.B(var1.getContains())) {
                        var28 = var1.getContains();
                        break label146;
                     }
                  } catch (Exception var18) {
                     var10000 = var18;
                     boolean var33 = false;
                     break label145;
                  }

                  label147: {
                     try {
                        if (!q.B(var1.getPrefix())) {
                           var26 = var1.getPrefix();
                           break label147;
                        }
                     } catch (Exception var17) {
                        var10000 = var17;
                        boolean var34 = false;
                        break label145;
                     }

                     label148: {
                        try {
                           if (!q.B(var1.getSuffix())) {
                              var24 = var1.getSuffix();
                              break label148;
                           }
                        } catch (Exception var15) {
                           var10000 = var15;
                           boolean var35 = false;
                           break label145;
                        }

                        try {
                           if (q.B(var1.getRegex())) {
                              return this;
                           }

                           var22 = var1.getRegex();
                        } catch (Exception var14) {
                           var10000 = var14;
                           boolean var36 = false;
                           break label145;
                        }

                        try {
                           if (!q.B(var22)) {
                              var3.add(HintTextFilters.matches(var22));
                           }
                        } catch (Exception var7) {
                           Exception var23 = var7;

                           try {
                              q.s("UiGlobalSelector", var23);
                           } catch (Exception var6) {
                              var10000 = var6;
                              boolean var37 = false;
                              break label145;
                           }
                        }

                        return this;
                     }

                     try {
                        if (!q.B(var24)) {
                           var3.add(HintTextFilters.endsWith(var24));
                        }
                     } catch (Exception var9) {
                        Exception var25 = var9;

                        try {
                           q.s("UiGlobalSelector", var25);
                        } catch (Exception var8) {
                           var10000 = var8;
                           boolean var38 = false;
                           break label145;
                        }
                     }

                     return this;
                  }

                  try {
                     if (!q.B(var26)) {
                        var3.add(HintTextFilters.startsWith(var26));
                     }
                  } catch (Exception var5) {
                     Exception var27 = var5;

                     try {
                        q.s("UiGlobalSelector", var27);
                     } catch (Exception var4) {
                        var10000 = var4;
                        boolean var39 = false;
                        break label145;
                     }
                  }

                  return this;
               }

               try {
                  if (!q.B(var28)) {
                     var3.add(HintTextFilters.contains(var28));
                  }
               } catch (Exception var11) {
                  Exception var29 = var11;

                  try {
                     q.s("UiGlobalSelector", var29);
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var40 = false;
                     break label145;
                  }
               }

               return this;
            }
         }
      }

      Exception var30 = var10000;
      q.s("UiGlobalSelector", var30);
      return this;
   }

   public final a J(StringCondition var1) {
      try {
         if (!q.B(var1.getEquals())) {
            this.u(var1.getEquals());
            return this;
         }

         if (!q.B(var1.getContains())) {
            this.v(var1.getContains());
            return this;
         }

         if (!q.B(var1.getPrefix())) {
            this.y(var1.getPrefix());
            return this;
         }

         if (!q.B(var1.getSuffix())) {
            this.w(var1.getSuffix());
            return this;
         }

         if (!q.B(var1.getRegex())) {
            this.x(var1.getRegex());
            return this;
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }

      return this;
   }

   public final a K(StringCondition var1) {
      try {
         if (!q.B(var1.getEquals())) {
            this.A(var1.getEquals());
            return this;
         }

         if (!q.B(var1.getContains())) {
            this.B(var1.getContains());
            return this;
         }

         if (!q.B(var1.getPrefix())) {
            this.E(var1.getPrefix());
            return this;
         }

         if (!q.B(var1.getSuffix())) {
            this.C(var1.getSuffix());
            return this;
         }

         if (!q.B(var1.getRegex())) {
            this.D(var1.getRegex());
            return this;
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }

      return this;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final a L(StringCondition var1) {
      Exception var10000;
      label141: {
         boolean var2;
         try {
            var2 = q.B(var1.getEquals());
         } catch (Exception var19) {
            var10000 = var19;
            boolean var10001 = false;
            break label141;
         }

         Selector var3 = this.a;
         label138:
         if (!var2) {
            try {
               var20 = var1.getEquals();
            } catch (Exception var14) {
               var10000 = var14;
               boolean var31 = false;
               break label138;
            }

            try {
               if (!q.B(var20)) {
                  var3.add(PanelTitleFilters.equals(var20));
               }
            } catch (Exception var11) {
               Exception var21 = var11;

               try {
                  q.s("UiGlobalSelector", var21);
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var32 = false;
                  break label138;
               }
            }

            return this;
         } else {
            label145: {
               label146: {
                  try {
                     if (!q.B(var1.getContains())) {
                        var28 = var1.getContains();
                        break label146;
                     }
                  } catch (Exception var18) {
                     var10000 = var18;
                     boolean var33 = false;
                     break label145;
                  }

                  label147: {
                     try {
                        if (!q.B(var1.getPrefix())) {
                           var26 = var1.getPrefix();
                           break label147;
                        }
                     } catch (Exception var17) {
                        var10000 = var17;
                        boolean var34 = false;
                        break label145;
                     }

                     label148: {
                        try {
                           if (!q.B(var1.getSuffix())) {
                              var24 = var1.getSuffix();
                              break label148;
                           }
                        } catch (Exception var16) {
                           var10000 = var16;
                           boolean var35 = false;
                           break label145;
                        }

                        try {
                           if (q.B(var1.getRegex())) {
                              return this;
                           }

                           var22 = var1.getRegex();
                        } catch (Exception var15) {
                           var10000 = var15;
                           boolean var36 = false;
                           break label145;
                        }

                        try {
                           if (!q.B(var22)) {
                              var3.add(PanelTitleFilters.matches(var22));
                           }
                        } catch (Exception var9) {
                           Exception var23 = var9;

                           try {
                              q.s("UiGlobalSelector", var23);
                           } catch (Exception var8) {
                              var10000 = var8;
                              boolean var37 = false;
                              break label145;
                           }
                        }

                        return this;
                     }

                     try {
                        if (!q.B(var24)) {
                           var3.add(PanelTitleFilters.endsWith(var24));
                        }
                     } catch (Exception var5) {
                        Exception var25 = var5;

                        try {
                           q.s("UiGlobalSelector", var25);
                        } catch (Exception var4) {
                           var10000 = var4;
                           boolean var38 = false;
                           break label145;
                        }
                     }

                     return this;
                  }

                  try {
                     if (!q.B(var26)) {
                        var3.add(PanelTitleFilters.startsWith(var26));
                     }
                  } catch (Exception var13) {
                     Exception var27 = var13;

                     try {
                        q.s("UiGlobalSelector", var27);
                     } catch (Exception var12) {
                        var10000 = var12;
                        boolean var39 = false;
                        break label145;
                     }
                  }

                  return this;
               }

               try {
                  if (!q.B(var28)) {
                     var3.add(PanelTitleFilters.contains(var28));
                  }
               } catch (Exception var7) {
                  Exception var29 = var7;

                  try {
                     q.s("UiGlobalSelector", var29);
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var40 = false;
                     break label145;
                  }
               }

               return this;
            }
         }
      }

      Exception var30 = var10000;
      q.s("UiGlobalSelector", var30);
      return this;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final a M(StringCondition var1) {
      Exception var10000;
      label141: {
         boolean var2;
         try {
            var2 = q.B(var1.getEquals());
         } catch (Exception var19) {
            var10000 = var19;
            boolean var10001 = false;
            break label141;
         }

         Selector var3 = this.a;
         label138:
         if (!var2) {
            try {
               var20 = var1.getEquals();
            } catch (Exception var17) {
               var10000 = var17;
               boolean var31 = false;
               break label138;
            }

            try {
               if (!q.B(var20)) {
                  var3.add(RoleDescFilters.equals(var20));
               }
            } catch (Exception var5) {
               Exception var21 = var5;

               try {
                  q.s("UiGlobalSelector", var21);
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var32 = false;
                  break label138;
               }
            }

            return this;
         } else {
            label145: {
               label146: {
                  try {
                     if (!q.B(var1.getContains())) {
                        var28 = var1.getContains();
                        break label146;
                     }
                  } catch (Exception var18) {
                     var10000 = var18;
                     boolean var33 = false;
                     break label145;
                  }

                  label147: {
                     try {
                        if (!q.B(var1.getPrefix())) {
                           var26 = var1.getPrefix();
                           break label147;
                        }
                     } catch (Exception var16) {
                        var10000 = var16;
                        boolean var34 = false;
                        break label145;
                     }

                     label148: {
                        try {
                           if (!q.B(var1.getSuffix())) {
                              var24 = var1.getSuffix();
                              break label148;
                           }
                        } catch (Exception var15) {
                           var10000 = var15;
                           boolean var35 = false;
                           break label145;
                        }

                        try {
                           if (q.B(var1.getRegex())) {
                              return this;
                           }

                           var22 = var1.getRegex();
                        } catch (Exception var14) {
                           var10000 = var14;
                           boolean var36 = false;
                           break label145;
                        }

                        try {
                           if (!q.B(var22)) {
                              var3.add(RoleDescFilters.matches(var22));
                           }
                        } catch (Exception var11) {
                           Exception var23 = var11;

                           try {
                              q.s("UiGlobalSelector", var23);
                           } catch (Exception var10) {
                              var10000 = var10;
                              boolean var37 = false;
                              break label145;
                           }
                        }

                        return this;
                     }

                     try {
                        if (!q.B(var24)) {
                           var3.add(RoleDescFilters.endsWith(var24));
                        }
                     } catch (Exception var9) {
                        Exception var25 = var9;

                        try {
                           q.s("UiGlobalSelector", var25);
                        } catch (Exception var8) {
                           var10000 = var8;
                           boolean var38 = false;
                           break label145;
                        }
                     }

                     return this;
                  }

                  try {
                     if (!q.B(var26)) {
                        var3.add(RoleDescFilters.startsWith(var26));
                     }
                  } catch (Exception var13) {
                     Exception var27 = var13;

                     try {
                        q.s("UiGlobalSelector", var27);
                     } catch (Exception var12) {
                        var10000 = var12;
                        boolean var39 = false;
                        break label145;
                     }
                  }

                  return this;
               }

               try {
                  if (!q.B(var28)) {
                     var3.add(RoleDescFilters.contains(var28));
                  }
               } catch (Exception var7) {
                  Exception var29 = var7;

                  try {
                     q.s("UiGlobalSelector", var29);
                  } catch (Exception var6) {
                     var10000 = var6;
                     boolean var40 = false;
                     break label145;
                  }
               }

               return this;
            }
         }
      }

      Exception var30 = var10000;
      q.s("UiGlobalSelector", var30);
      return this;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final a N(StringCondition var1) {
      Exception var10000;
      label141: {
         boolean var2;
         try {
            var2 = q.B(var1.getEquals());
         } catch (Exception var19) {
            var10000 = var19;
            boolean var10001 = false;
            break label141;
         }

         Selector var3 = this.a;
         label138:
         if (!var2) {
            try {
               var20 = var1.getEquals();
            } catch (Exception var18) {
               var10000 = var18;
               boolean var31 = false;
               break label138;
            }

            try {
               if (!q.B(var20)) {
                  var3.add(StateDescFilters.equals(var20));
               }
            } catch (Exception var11) {
               Exception var21 = var11;

               try {
                  q.s("UiGlobalSelector", var21);
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var32 = false;
                  break label138;
               }
            }

            return this;
         } else {
            label145: {
               label146: {
                  try {
                     if (!q.B(var1.getContains())) {
                        var28 = var1.getContains();
                        break label146;
                     }
                  } catch (Exception var17) {
                     var10000 = var17;
                     boolean var33 = false;
                     break label145;
                  }

                  label147: {
                     try {
                        if (!q.B(var1.getPrefix())) {
                           var26 = var1.getPrefix();
                           break label147;
                        }
                     } catch (Exception var16) {
                        var10000 = var16;
                        boolean var34 = false;
                        break label145;
                     }

                     label148: {
                        try {
                           if (!q.B(var1.getSuffix())) {
                              var24 = var1.getSuffix();
                              break label148;
                           }
                        } catch (Exception var15) {
                           var10000 = var15;
                           boolean var35 = false;
                           break label145;
                        }

                        try {
                           if (q.B(var1.getRegex())) {
                              return this;
                           }

                           var22 = var1.getRegex();
                        } catch (Exception var14) {
                           var10000 = var14;
                           boolean var36 = false;
                           break label145;
                        }

                        try {
                           if (!q.B(var22)) {
                              var3.add(StateDescFilters.matches(var22));
                           }
                        } catch (Exception var7) {
                           Exception var23 = var7;

                           try {
                              q.s("UiGlobalSelector", var23);
                           } catch (Exception var6) {
                              var10000 = var6;
                              boolean var37 = false;
                              break label145;
                           }
                        }

                        return this;
                     }

                     try {
                        if (!q.B(var24)) {
                           var3.add(StateDescFilters.endsWith(var24));
                        }
                     } catch (Exception var9) {
                        Exception var25 = var9;

                        try {
                           q.s("UiGlobalSelector", var25);
                        } catch (Exception var8) {
                           var10000 = var8;
                           boolean var38 = false;
                           break label145;
                        }
                     }

                     return this;
                  }

                  try {
                     if (!q.B(var26)) {
                        var3.add(StateDescFilters.startsWith(var26));
                     }
                  } catch (Exception var13) {
                     Exception var27 = var13;

                     try {
                        q.s("UiGlobalSelector", var27);
                     } catch (Exception var12) {
                        var10000 = var12;
                        boolean var39 = false;
                        break label145;
                     }
                  }

                  return this;
               }

               try {
                  if (!q.B(var28)) {
                     var3.add(StateDescFilters.contains(var28));
                  }
               } catch (Exception var5) {
                  Exception var29 = var5;

                  try {
                     q.s("UiGlobalSelector", var29);
                  } catch (Exception var4) {
                     var10000 = var4;
                     boolean var40 = false;
                     break label145;
                  }
               }

               return this;
            }
         }
      }

      Exception var30 = var10000;
      q.s("UiGlobalSelector", var30);
      return this;
   }

   public final a O(StringCondition var1) {
      try {
         if (!q.B(var1.getEquals())) {
            this.R(var1.getEquals());
            return this;
         }

         if (!q.B(var1.getContains())) {
            this.S(var1.getContains());
            return this;
         }

         if (!q.B(var1.getPrefix())) {
            this.V(var1.getPrefix());
            return this;
         }

         if (!q.B(var1.getSuffix())) {
            this.T(var1.getSuffix());
            return this;
         }

         if (!q.B(var1.getRegex())) {
            this.U(var1.getRegex());
            return this;
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }

      return this;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final a P(StringCondition var1) {
      Exception var10000;
      label141: {
         boolean var2;
         try {
            var2 = q.B(var1.getEquals());
         } catch (Exception var19) {
            var10000 = var19;
            boolean var10001 = false;
            break label141;
         }

         Selector var3 = this.a;
         label138:
         if (!var2) {
            try {
               var20 = var1.getEquals();
            } catch (Exception var15) {
               var10000 = var15;
               boolean var31 = false;
               break label138;
            }

            try {
               if (!q.B(var20)) {
                  var3.add(TooltipFilters.equals(var20));
               }
            } catch (Exception var11) {
               Exception var21 = var11;

               try {
                  q.s("UiGlobalSelector", var21);
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var32 = false;
                  break label138;
               }
            }

            return this;
         } else {
            label145: {
               label146: {
                  try {
                     if (!q.B(var1.getContains())) {
                        var28 = var1.getContains();
                        break label146;
                     }
                  } catch (Exception var18) {
                     var10000 = var18;
                     boolean var33 = false;
                     break label145;
                  }

                  label147: {
                     try {
                        if (!q.B(var1.getPrefix())) {
                           var26 = var1.getPrefix();
                           break label147;
                        }
                     } catch (Exception var17) {
                        var10000 = var17;
                        boolean var34 = false;
                        break label145;
                     }

                     label148: {
                        try {
                           if (!q.B(var1.getSuffix())) {
                              var24 = var1.getSuffix();
                              break label148;
                           }
                        } catch (Exception var16) {
                           var10000 = var16;
                           boolean var35 = false;
                           break label145;
                        }

                        try {
                           if (q.B(var1.getRegex())) {
                              return this;
                           }

                           var22 = var1.getRegex();
                        } catch (Exception var14) {
                           var10000 = var14;
                           boolean var36 = false;
                           break label145;
                        }

                        try {
                           if (!q.B(var22)) {
                              var3.add(TooltipFilters.matches(var22));
                           }
                        } catch (Exception var7) {
                           Exception var23 = var7;

                           try {
                              q.s("UiGlobalSelector", var23);
                           } catch (Exception var6) {
                              var10000 = var6;
                              boolean var37 = false;
                              break label145;
                           }
                        }

                        return this;
                     }

                     try {
                        if (!q.B(var24)) {
                           var3.add(TooltipFilters.endsWith(var24));
                        }
                     } catch (Exception var5) {
                        Exception var25 = var5;

                        try {
                           q.s("UiGlobalSelector", var25);
                        } catch (Exception var4) {
                           var10000 = var4;
                           boolean var38 = false;
                           break label145;
                        }
                     }

                     return this;
                  }

                  try {
                     if (!q.B(var26)) {
                        var3.add(TooltipFilters.startsWith(var26));
                     }
                  } catch (Exception var13) {
                     Exception var27 = var13;

                     try {
                        q.s("UiGlobalSelector", var27);
                     } catch (Exception var12) {
                        var10000 = var12;
                        boolean var39 = false;
                        break label145;
                     }
                  }

                  return this;
               }

               try {
                  if (!q.B(var28)) {
                     var3.add(TooltipFilters.contains(var28));
                  }
               } catch (Exception var9) {
                  Exception var29 = var9;

                  try {
                     q.s("UiGlobalSelector", var29);
                  } catch (Exception var8) {
                     var10000 = var8;
                     boolean var40 = false;
                     break label145;
                  }
               }

               return this;
            }
         }
      }

      Exception var30 = var10000;
      q.s("UiGlobalSelector", var30);
      return this;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final a Q(StringCondition var1) {
      Exception var10000;
      label141: {
         boolean var2;
         try {
            var2 = q.B(var1.getEquals());
         } catch (Exception var19) {
            var10000 = var19;
            boolean var10001 = false;
            break label141;
         }

         Selector var3 = this.a;
         label138:
         if (!var2) {
            try {
               var20 = var1.getEquals();
            } catch (Exception var14) {
               var10000 = var14;
               boolean var31 = false;
               break label138;
            }

            try {
               if (!q.B(var20)) {
                  var3.add(UniqueIdFilters.equals(var20));
               }
            } catch (Exception var13) {
               Exception var21 = var13;

               try {
                  q.s("UiGlobalSelector", var21);
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var32 = false;
                  break label138;
               }
            }

            return this;
         } else {
            label145: {
               label146: {
                  try {
                     if (!q.B(var1.getContains())) {
                        var28 = var1.getContains();
                        break label146;
                     }
                  } catch (Exception var18) {
                     var10000 = var18;
                     boolean var33 = false;
                     break label145;
                  }

                  label147: {
                     try {
                        if (!q.B(var1.getPrefix())) {
                           var26 = var1.getPrefix();
                           break label147;
                        }
                     } catch (Exception var17) {
                        var10000 = var17;
                        boolean var34 = false;
                        break label145;
                     }

                     label148: {
                        try {
                           if (!q.B(var1.getSuffix())) {
                              var24 = var1.getSuffix();
                              break label148;
                           }
                        } catch (Exception var16) {
                           var10000 = var16;
                           boolean var35 = false;
                           break label145;
                        }

                        try {
                           if (q.B(var1.getRegex())) {
                              return this;
                           }

                           var22 = var1.getRegex();
                        } catch (Exception var15) {
                           var10000 = var15;
                           boolean var36 = false;
                           break label145;
                        }

                        try {
                           if (!q.B(var22)) {
                              var3.add(UniqueIdFilters.matches(var22));
                           }
                        } catch (Exception var5) {
                           Exception var23 = var5;

                           try {
                              q.s("UiGlobalSelector", var23);
                           } catch (Exception var4) {
                              var10000 = var4;
                              boolean var37 = false;
                              break label145;
                           }
                        }

                        return this;
                     }

                     try {
                        if (!q.B(var24)) {
                           var3.add(UniqueIdFilters.endsWith(var24));
                        }
                     } catch (Exception var11) {
                        Exception var25 = var11;

                        try {
                           q.s("UiGlobalSelector", var25);
                        } catch (Exception var10) {
                           var10000 = var10;
                           boolean var38 = false;
                           break label145;
                        }
                     }

                     return this;
                  }

                  try {
                     if (!q.B(var26)) {
                        var3.add(UniqueIdFilters.startsWith(var26));
                     }
                  } catch (Exception var7) {
                     Exception var27 = var7;

                     try {
                        q.s("UiGlobalSelector", var27);
                     } catch (Exception var6) {
                        var10000 = var6;
                        boolean var39 = false;
                        break label145;
                     }
                  }

                  return this;
               }

               try {
                  if (!q.B(var28)) {
                     var3.add(UniqueIdFilters.contains(var28));
                  }
               } catch (Exception var9) {
                  Exception var29 = var9;

                  try {
                     q.s("UiGlobalSelector", var29);
                  } catch (Exception var8) {
                     var10000 = var8;
                     boolean var40 = false;
                     break label145;
                  }
               }

               return this;
            }
         }
      }

      Exception var30 = var10000;
      q.s("UiGlobalSelector", var30);
      return this;
   }

   public final void R(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(TextFilters.equals(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void S(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(TextFilters.contains(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void T(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(TextFilters.endsWith(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void U(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(TextFilters.matches(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void V(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(TextFilters.startsWith(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void W(String var1, StringCondition var2) {
      try {
         if (!q.B(var1) && "WINDOW_TITLE".equals(var2.getProperty())) {
            if (!q.B(var2.getEquals())) {
               this.a(WindowTitleFilters.equals(var1, var2.getEquals()));
            }

            if (!q.B(var2.getContains())) {
               this.a(WindowTitleFilters.contains(var1, var2.getContains()));
            }

            if (!q.B(var2.getRegex())) {
               this.a(WindowTitleFilters.matches(var1, var2.getRegex()));
            }

            if (!q.B(var2.getPrefix())) {
               this.a(WindowTitleFilters.startsWith(var1, var2.getPrefix()));
            }

            if (!q.B(var2.getSuffix())) {
               this.a(WindowTitleFilters.endsWith(var1, var2.getSuffix()));
            }
         }
      } catch (Exception var3) {
         q.s("UiGlobalSelector", var3);
      }
   }

   public final void a(Filter var1) {
      if (var1 != null) {
         this.a.add(var1);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void b(e var1, int var2, String var3) {
      Exception var10000;
      label155: {
         boolean var5;
         try {
            var5 = q.B(var3);
         } catch (Exception var20) {
            var10000 = var20;
            boolean var10001 = false;
            break label155;
         }

         if (var5) {
            var3 = "EQUALS";
         }

         byte var4;
         label147: {
            label146: {
               label145: {
                  label144: {
                     label143: {
                        label142: {
                           label156: {
                              label140: {
                                 label139: {
                                    label138: {
                                       label137: {
                                          label136: {
                                             try {
                                                switch (var3.hashCode()) {
                                                   case -1583968932:
                                                      break label140;
                                                   case -1112834937:
                                                      break;
                                                   case 360410235:
                                                      break label136;
                                                   case 972152550:
                                                      break label137;
                                                   case 1630331595:
                                                      break label138;
                                                   case 2052813759:
                                                      break label139;
                                                   default:
                                                      break label156;
                                                }
                                             } catch (Exception var19) {
                                                var10000 = var19;
                                                boolean var25 = false;
                                                break label155;
                                             }

                                             try {
                                                if (var3.equals("LESS_THAN")) {
                                                   break label142;
                                                }
                                                break label156;
                                             } catch (Exception var18) {
                                                var10000 = var18;
                                                boolean var30 = false;
                                                break label155;
                                             }
                                          }

                                          try {
                                             if (var3.equals("GREATER_THAN_EQUAL")) {
                                                break label143;
                                             }
                                             break label156;
                                          } catch (Exception var17) {
                                             var10000 = var17;
                                             boolean var29 = false;
                                             break label155;
                                          }
                                       }

                                       try {
                                          if (var3.equals("GREATER_THAN")) {
                                             break label144;
                                          }
                                          break label156;
                                       } catch (Exception var16) {
                                          var10000 = var16;
                                          boolean var28 = false;
                                          break label155;
                                       }
                                    }

                                    try {
                                       if (var3.equals("NOT_EQUALS")) {
                                          break label145;
                                       }
                                       break label156;
                                    } catch (Exception var15) {
                                       var10000 = var15;
                                       boolean var27 = false;
                                       break label155;
                                    }
                                 }

                                 try {
                                    if (var3.equals("EQUALS")) {
                                       break label146;
                                    }
                                    break label156;
                                 } catch (Exception var14) {
                                    var10000 = var14;
                                    boolean var26 = false;
                                    break label155;
                                 }
                              }

                              try {
                                 var5 = var3.equals("LESS_THAN_EQUAL");
                              } catch (Exception var13) {
                                 var10000 = var13;
                                 boolean var31 = false;
                                 break label155;
                              }

                              if (var5) {
                                 var4 = 5;
                                 break label147;
                              }
                           }

                           var4 = -1;
                           break label147;
                        }

                        var4 = 4;
                        break label147;
                     }

                     var4 = 3;
                     break label147;
                  }

                  var4 = 2;
                  break label147;
               }

               var4 = 1;
               break label147;
            }

            var4 = 0;
         }

         Selector var23 = this.a;
         Filter var21;
         if (var4 != 0) {
            if (var4 != 1) {
               if (var4 != 2) {
                  if (var4 != 3) {
                     if (var4 != 4) {
                        if (var4 != 5) {
                           return;
                        }

                        try {
                           var21 = IntFilters.lte(var1, var2);
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var32 = false;
                           break label155;
                        }
                     } else {
                        try {
                           var21 = IntFilters.lt(var1, var2);
                        } catch (Exception var11) {
                           var10000 = var11;
                           boolean var33 = false;
                           break label155;
                        }
                     }
                  } else {
                     try {
                        var21 = IntFilters.gte(var1, var2);
                     } catch (Exception var10) {
                        var10000 = var10;
                        boolean var34 = false;
                        break label155;
                     }
                  }
               } else {
                  try {
                     var21 = IntFilters.gt(var1, var2);
                  } catch (Exception var9) {
                     var10000 = var9;
                     boolean var35 = false;
                     break label155;
                  }
               }
            } else {
               try {
                  var21 = IntFilters.notEquals(var1, var2);
               } catch (Exception var8) {
                  var10000 = var8;
                  boolean var36 = false;
                  break label155;
               }
            }
         } else {
            try {
               var21 = IntFilters.equals(var1, var2);
            } catch (Exception var7) {
               var10000 = var7;
               boolean var37 = false;
               break label155;
            }
         }

         try {
            var23.add(var21);
            return;
         } catch (Exception var6) {
            var10000 = var6;
            boolean var38 = false;
         }
      }

      Exception var22 = var10000;
      q.s("UiGlobalSelector", var22);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final a c(BoolCondition var1) {
      if (var1 != null) {
         Exception var10000;
         label750: {
            byte var2;
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
                                                                                    label754: {
                                                                                       String var4;
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
                                                                                                                                                         label702: {
                                                                                                                                                            try {
                                                                                                                                                               var4 = var1.getFilterKey();
                                                                                                                                                               switch (var4.hashCode()) {
                                                                                                                                                                  case -1979905218:
                                                                                                                                                                     break label724;
                                                                                                                                                                  case -1964681502:
                                                                                                                                                                     break;
                                                                                                                                                                  case -1724171933:
                                                                                                                                                                     break label702;
                                                                                                                                                                  case -1609594047:
                                                                                                                                                                     break label703;
                                                                                                                                                                  case -1371475228:
                                                                                                                                                                     break label704;
                                                                                                                                                                  case -1207192371:
                                                                                                                                                                     break label705;
                                                                                                                                                                  case -994557277:
                                                                                                                                                                     break label706;
                                                                                                                                                                  case -691041417:
                                                                                                                                                                     break label707;
                                                                                                                                                                  case -635423245:
                                                                                                                                                                     break label708;
                                                                                                                                                                  case 66669991:
                                                                                                                                                                     break label709;
                                                                                                                                                                  case 398964322:
                                                                                                                                                                     break label710;
                                                                                                                                                                  case 742313895:
                                                                                                                                                                     break label711;
                                                                                                                                                                  case 746986311:
                                                                                                                                                                     break label712;
                                                                                                                                                                  case 783360658:
                                                                                                                                                                     break label713;
                                                                                                                                                                  case 795311618:
                                                                                                                                                                     break label714;
                                                                                                                                                                  case 918550520:
                                                                                                                                                                     break label715;
                                                                                                                                                                  case 997604294:
                                                                                                                                                                     break label716;
                                                                                                                                                                  case 1191572123:
                                                                                                                                                                     break label717;
                                                                                                                                                                  case 1216985755:
                                                                                                                                                                     break label718;
                                                                                                                                                                  case 1602416228:
                                                                                                                                                                     break label719;
                                                                                                                                                                  case 1629011506:
                                                                                                                                                                     break label720;
                                                                                                                                                                  case 1933057242:
                                                                                                                                                                     break label721;
                                                                                                                                                                  case 1976364617:
                                                                                                                                                                     break label722;
                                                                                                                                                                  case 2062895929:
                                                                                                                                                                     break label723;
                                                                                                                                                                  default:
                                                                                                                                                                     break label754;
                                                                                                                                                               }
                                                                                                                                                            } catch (Exception var103) {
                                                                                                                                                               var10000 = var103;
                                                                                                                                                               boolean var10001 = false;
                                                                                                                                                               break label750;
                                                                                                                                                            }

                                                                                                                                                            try {
                                                                                                                                                               if (var4.equals(
                                                                                                                                                                  "clickable"
                                                                                                                                                               )
                                                                                                                                                                  )
                                                                                                                                                                {
                                                                                                                                                                  break label726;
                                                                                                                                                               }
                                                                                                                                                               break label754;
                                                                                                                                                            } catch (Exception var102) {
                                                                                                                                                               var10000 = var102;
                                                                                                                                                               boolean var223 = false;
                                                                                                                                                               break label750;
                                                                                                                                                            }
                                                                                                                                                         }

                                                                                                                                                         try {
                                                                                                                                                            if (var4.equals(
                                                                                                                                                               "textSelectable"
                                                                                                                                                            )) {
                                                                                                                                                               break label727;
                                                                                                                                                            }
                                                                                                                                                            break label754;
                                                                                                                                                         } catch (Exception var101) {
                                                                                                                                                            var10000 = var101;
                                                                                                                                                            boolean var222 = false;
                                                                                                                                                            break label750;
                                                                                                                                                         }
                                                                                                                                                      }

                                                                                                                                                      try {
                                                                                                                                                         if (var4.equals(
                                                                                                                                                            "enabled"
                                                                                                                                                         )) {
                                                                                                                                                            break label728;
                                                                                                                                                         }
                                                                                                                                                         break label754;
                                                                                                                                                      } catch (Exception var100) {
                                                                                                                                                         var10000 = var100;
                                                                                                                                                         boolean var221 = false;
                                                                                                                                                         break label750;
                                                                                                                                                      }
                                                                                                                                                   }

                                                                                                                                                   try {
                                                                                                                                                      if (var4.equals(
                                                                                                                                                         "dismissable"
                                                                                                                                                      )) {
                                                                                                                                                         break label729;
                                                                                                                                                      }
                                                                                                                                                      break label754;
                                                                                                                                                   } catch (Exception var99) {
                                                                                                                                                      var10000 = var99;
                                                                                                                                                      boolean var220 = false;
                                                                                                                                                      break label750;
                                                                                                                                                   }
                                                                                                                                                }

                                                                                                                                                try {
                                                                                                                                                   if (var4.equals(
                                                                                                                                                      "multiLine"
                                                                                                                                                   )) {
                                                                                                                                                      break label730;
                                                                                                                                                   }
                                                                                                                                                   break label754;
                                                                                                                                                } catch (Exception var98) {
                                                                                                                                                   var10000 = var98;
                                                                                                                                                   boolean var219 = false;
                                                                                                                                                   break label750;
                                                                                                                                                }
                                                                                                                                             }

                                                                                                                                             try {
                                                                                                                                                if (var4.equals(
                                                                                                                                                   "screenReaderFocusable"
                                                                                                                                                )) {
                                                                                                                                                   break label731;
                                                                                                                                                }
                                                                                                                                                break label754;
                                                                                                                                             } catch (Exception var97) {
                                                                                                                                                var10000 = var97;
                                                                                                                                                boolean var218 = false;
                                                                                                                                                break label750;
                                                                                                                                             }
                                                                                                                                          }

                                                                                                                                          try {
                                                                                                                                             if (var4.equals(
                                                                                                                                                "focused"
                                                                                                                                             )) {
                                                                                                                                                break label732;
                                                                                                                                             }
                                                                                                                                             break label754;
                                                                                                                                          } catch (Exception var96) {
                                                                                                                                             var10000 = var96;
                                                                                                                                             boolean var217 = false;
                                                                                                                                             break label750;
                                                                                                                                          }
                                                                                                                                       }

                                                                                                                                       try {
                                                                                                                                          if (var4.equals(
                                                                                                                                             "contextClickable"
                                                                                                                                          )) {
                                                                                                                                             break label733;
                                                                                                                                          }
                                                                                                                                          break label754;
                                                                                                                                       } catch (Exception var95) {
                                                                                                                                          var10000 = var95;
                                                                                                                                          boolean var216 = false;
                                                                                                                                          break label750;
                                                                                                                                       }
                                                                                                                                    }

                                                                                                                                    try {
                                                                                                                                       if (var4.equals(
                                                                                                                                          "scrollable"
                                                                                                                                       )) {
                                                                                                                                          break label734;
                                                                                                                                       }
                                                                                                                                       break label754;
                                                                                                                                    } catch (Exception var94) {
                                                                                                                                       var10000 = var94;
                                                                                                                                       boolean var215 = false;
                                                                                                                                       break label750;
                                                                                                                                    }
                                                                                                                                 }

                                                                                                                                 try {
                                                                                                                                    if (var4.equals("checkable")
                                                                                                                                       )
                                                                                                                                     {
                                                                                                                                       break label735;
                                                                                                                                    }
                                                                                                                                    break label754;
                                                                                                                                 } catch (Exception var93) {
                                                                                                                                    var10000 = var93;
                                                                                                                                    boolean var214 = false;
                                                                                                                                    break label750;
                                                                                                                                 }
                                                                                                                              }

                                                                                                                              try {
                                                                                                                                 if (var4.equals("checked")) {
                                                                                                                                    break label736;
                                                                                                                                 }
                                                                                                                                 break label754;
                                                                                                                              } catch (Exception var92) {
                                                                                                                                 var10000 = var92;
                                                                                                                                 boolean var213 = false;
                                                                                                                                 break label750;
                                                                                                                              }
                                                                                                                           }

                                                                                                                           try {
                                                                                                                              if (var4.equals(
                                                                                                                                 "importantForAccessibility"
                                                                                                                              )) {
                                                                                                                                 break label737;
                                                                                                                              }
                                                                                                                              break label754;
                                                                                                                           } catch (Exception var91) {
                                                                                                                              var10000 = var91;
                                                                                                                              boolean var212 = false;
                                                                                                                              break label750;
                                                                                                                           }
                                                                                                                        }

                                                                                                                        try {
                                                                                                                           if (var4.equals("canOpenPopup")) {
                                                                                                                              break label738;
                                                                                                                           }
                                                                                                                           break label754;
                                                                                                                        } catch (Exception var90) {
                                                                                                                           var10000 = var90;
                                                                                                                           boolean var211 = false;
                                                                                                                           break label750;
                                                                                                                        }
                                                                                                                     }

                                                                                                                     try {
                                                                                                                        if (var4.equals("heading")) {
                                                                                                                           break label739;
                                                                                                                        }
                                                                                                                        break label754;
                                                                                                                     } catch (Exception var89) {
                                                                                                                        var10000 = var89;
                                                                                                                        boolean var210 = false;
                                                                                                                        break label750;
                                                                                                                     }
                                                                                                                  }

                                                                                                                  try {
                                                                                                                     if (var4.equals("visibleToUser")) {
                                                                                                                        break label740;
                                                                                                                     }
                                                                                                                     break label754;
                                                                                                                  } catch (Exception var88) {
                                                                                                                     var10000 = var88;
                                                                                                                     boolean var209 = false;
                                                                                                                     break label750;
                                                                                                                  }
                                                                                                               }

                                                                                                               try {
                                                                                                                  if (var4.equals("longClickable")) {
                                                                                                                     break label741;
                                                                                                                  }
                                                                                                                  break label754;
                                                                                                               } catch (Exception var87) {
                                                                                                                  var10000 = var87;
                                                                                                                  boolean var208 = false;
                                                                                                                  break label750;
                                                                                                               }
                                                                                                            }

                                                                                                            try {
                                                                                                               if (var4.equals("selected")) {
                                                                                                                  break label742;
                                                                                                               }
                                                                                                               break label754;
                                                                                                            } catch (Exception var86) {
                                                                                                               var10000 = var86;
                                                                                                               boolean var207 = false;
                                                                                                               break label750;
                                                                                                            }
                                                                                                         }

                                                                                                         try {
                                                                                                            if (var4.equals("password")) {
                                                                                                               break label743;
                                                                                                            }
                                                                                                            break label754;
                                                                                                         } catch (Exception var85) {
                                                                                                            var10000 = var85;
                                                                                                            boolean var206 = false;
                                                                                                            break label750;
                                                                                                         }
                                                                                                      }

                                                                                                      try {
                                                                                                         if (var4.equals("editable")) {
                                                                                                            break label744;
                                                                                                         }
                                                                                                         break label754;
                                                                                                      } catch (Exception var84) {
                                                                                                         var10000 = var84;
                                                                                                         boolean var205 = false;
                                                                                                         break label750;
                                                                                                      }
                                                                                                   }

                                                                                                   try {
                                                                                                      if (var4.equals("focusable")) {
                                                                                                         break label745;
                                                                                                      }
                                                                                                      break label754;
                                                                                                   } catch (Exception var83) {
                                                                                                      var10000 = var83;
                                                                                                      boolean var204 = false;
                                                                                                      break label750;
                                                                                                   }
                                                                                                }

                                                                                                try {
                                                                                                   if (var4.equals("textEntryKey")) {
                                                                                                      break label746;
                                                                                                   }
                                                                                                   break label754;
                                                                                                } catch (Exception var82) {
                                                                                                   var10000 = var82;
                                                                                                   boolean var203 = false;
                                                                                                   break label750;
                                                                                                }
                                                                                             }

                                                                                             try {
                                                                                                if (var4.equals("accessibilityFocused")) {
                                                                                                   break label747;
                                                                                                }
                                                                                                break label754;
                                                                                             } catch (Exception var81) {
                                                                                                var10000 = var81;
                                                                                                boolean var202 = false;
                                                                                                break label750;
                                                                                             }
                                                                                          }

                                                                                          try {
                                                                                             if (var4.equals("showingHintText")) {
                                                                                                break label748;
                                                                                             }
                                                                                             break label754;
                                                                                          } catch (Exception var80) {
                                                                                             var10000 = var80;
                                                                                             boolean var201 = false;
                                                                                             break label750;
                                                                                          }
                                                                                       }

                                                                                       boolean var3;
                                                                                       try {
                                                                                          var3 = var4.equals("contentInvalid");
                                                                                       } catch (Exception var79) {
                                                                                          var10000 = var79;
                                                                                          boolean var224 = false;
                                                                                          break label750;
                                                                                       }

                                                                                       if (var3) {
                                                                                          var2 = 5;
                                                                                          break label749;
                                                                                       }
                                                                                    }

                                                                                    var2 = -1;
                                                                                    break label749;
                                                                                 }

                                                                                 var2 = 4;
                                                                                 break label749;
                                                                              }

                                                                              var2 = 22;
                                                                              break label749;
                                                                           }

                                                                           var2 = 9;
                                                                           break label749;
                                                                        }

                                                                        var2 = 7;
                                                                        break label749;
                                                                     }

                                                                     var2 = 15;
                                                                     break label749;
                                                                  }

                                                                  var2 = 17;
                                                                  break label749;
                                                               }

                                                               var2 = 11;
                                                               break label749;
                                                            }

                                                            var2 = 6;
                                                            break label749;
                                                         }

                                                         var2 = 18;
                                                         break label749;
                                                      }

                                                      var2 = 2;
                                                      break label749;
                                                   }

                                                   var2 = 3;
                                                   break label749;
                                                }

                                                var2 = 13;
                                                break label749;
                                             }

                                             var2 = 1;
                                             break label749;
                                          }

                                          var2 = 12;
                                          break label749;
                                       }

                                       var2 = 23;
                                       break label749;
                                    }

                                    var2 = 14;
                                    break label749;
                                 }

                                 var2 = 19;
                                 break label749;
                              }

                              var2 = 16;
                              break label749;
                           }

                           var2 = 8;
                           break label749;
                        }

                        var2 = 10;
                        break label749;
                     }

                     var2 = 21;
                     break label749;
                  }

                  var2 = 0;
                  break label749;
               }

               var2 = 20;
            }

            Selector var177 = this.a;
            switch (var2) {
               case 0:
                  boolean var176;
                  try {
                     var176 = var1.isFilterValue();
                  } catch (Exception var77) {
                     var10000 = var77;
                     boolean var272 = false;
                     break;
                  }

                  try {
                     e var152 = new e(29);
                     BooleanFilter var200 = new BooleanFilter(var152, var176);
                     var177.add(var200);
                  } catch (Exception var9) {
                     Exception var150 = var9;

                     try {
                        q.s("UiGlobalSelector", var150);
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var273 = false;
                        break;
                     }
                  }

                  return this;
               case 1:
                  boolean var175;
                  try {
                     var175 = var1.isFilterValue();
                  } catch (Exception var67) {
                     var10000 = var67;
                     boolean var270 = false;
                     break;
                  }

                  try {
                     b var149 = new b(0);
                     BooleanFilter var199 = new BooleanFilter(var149, var175);
                     var177.add(var199);
                  } catch (Exception var11) {
                     Exception var148 = var11;

                     try {
                        q.s("UiGlobalSelector", var148);
                     } catch (Exception var10) {
                        var10000 = var10;
                        boolean var271 = false;
                        break;
                     }
                  }

                  return this;
               case 2:
                  boolean var174;
                  try {
                     var174 = var1.isFilterValue();
                  } catch (Exception var55) {
                     var10000 = var55;
                     boolean var268 = false;
                     break;
                  }

                  try {
                     b var198 = new b(1);
                     BooleanFilter var147 = new BooleanFilter(var198, var174);
                     var177.add(var147);
                  } catch (Exception var27) {
                     Exception var146 = var27;

                     try {
                        q.s("UiGlobalSelector", var146);
                     } catch (Exception var26) {
                        var10000 = var26;
                        boolean var269 = false;
                        break;
                     }
                  }

                  return this;
               case 3:
                  boolean var173;
                  try {
                     var173 = var1.isFilterValue();
                  } catch (Exception var56) {
                     var10000 = var56;
                     boolean var266 = false;
                     break;
                  }

                  try {
                     b var197 = new b(2);
                     BooleanFilter var145 = new BooleanFilter(var197, var173);
                     var177.add(var145);
                  } catch (Exception var19) {
                     Exception var144 = var19;

                     try {
                        q.s("UiGlobalSelector", var144);
                     } catch (Exception var18) {
                        var10000 = var18;
                        boolean var267 = false;
                        break;
                     }
                  }

                  return this;
               case 4:
                  boolean var172;
                  try {
                     var172 = var1.isFilterValue();
                  } catch (Exception var73) {
                     var10000 = var73;
                     boolean var264 = false;
                     break;
                  }

                  try {
                     b var143 = new b(3);
                     BooleanFilter var196 = new BooleanFilter(var143, var172);
                     var177.add(var196);
                  } catch (Exception var31) {
                     Exception var142 = var31;

                     try {
                        q.s("UiGlobalSelector", var142);
                     } catch (Exception var30) {
                        var10000 = var30;
                        boolean var265 = false;
                        break;
                     }
                  }

                  return this;
               case 5:
                  boolean var171;
                  try {
                     var171 = var1.isFilterValue();
                  } catch (Exception var57) {
                     var10000 = var57;
                     boolean var262 = false;
                     break;
                  }

                  try {
                     b var195 = new b(4);
                     BooleanFilter var141 = new BooleanFilter(var195, var171);
                     var177.add(var141);
                  } catch (Exception var42) {
                     Exception var140 = var42;

                     try {
                        q.s("UiGlobalSelector", var140);
                     } catch (Exception var41) {
                        var10000 = var41;
                        boolean var263 = false;
                        break;
                     }
                  }

                  return this;
               case 6:
                  boolean var170;
                  try {
                     var170 = var1.isFilterValue();
                  } catch (Exception var78) {
                     var10000 = var78;
                     boolean var260 = false;
                     break;
                  }

                  try {
                     b var139 = new b(5);
                     BooleanFilter var194 = new BooleanFilter(var139, var170);
                     var177.add(var194);
                  } catch (Exception var7) {
                     Exception var138 = var7;

                     try {
                        q.s("UiGlobalSelector", var138);
                     } catch (Exception var6) {
                        var10000 = var6;
                        boolean var261 = false;
                        break;
                     }
                  }

                  return this;
               case 7:
                  boolean var169;
                  try {
                     var169 = var1.isFilterValue();
                  } catch (Exception var74) {
                     var10000 = var74;
                     boolean var258 = false;
                     break;
                  }

                  try {
                     b var137 = new b(6);
                     BooleanFilter var193 = new BooleanFilter(var137, var169);
                     var177.add(var193);
                  } catch (Exception var50) {
                     Exception var136 = var50;

                     try {
                        q.s("UiGlobalSelector", var136);
                     } catch (Exception var49) {
                        var10000 = var49;
                        boolean var259 = false;
                        break;
                     }
                  }

                  return this;
               case 8:
                  boolean var168;
                  try {
                     var168 = var1.isFilterValue();
                  } catch (Exception var76) {
                     var10000 = var76;
                     boolean var256 = false;
                     break;
                  }

                  try {
                     b var192 = new b(7);
                     BooleanFilter var135 = new BooleanFilter(var192, var168);
                     var177.add(var135);
                  } catch (Exception var46) {
                     Exception var134 = var46;

                     try {
                        q.s("UiGlobalSelector", var134);
                     } catch (Exception var45) {
                        var10000 = var45;
                        boolean var257 = false;
                        break;
                     }
                  }

                  return this;
               case 9:
                  boolean var167;
                  try {
                     var167 = var1.isFilterValue();
                  } catch (Exception var66) {
                     var10000 = var66;
                     boolean var254 = false;
                     break;
                  }

                  try {
                     b var133 = new b(8);
                     BooleanFilter var191 = new BooleanFilter(var133, var167);
                     var177.add(var191);
                  } catch (Exception var21) {
                     Exception var132 = var21;

                     try {
                        q.s("UiGlobalSelector", var132);
                     } catch (Exception var20) {
                        var10000 = var20;
                        boolean var255 = false;
                        break;
                     }
                  }

                  return this;
               case 10:
                  boolean var166;
                  try {
                     var166 = var1.isFilterValue();
                  } catch (Exception var68) {
                     var10000 = var68;
                     boolean var252 = false;
                     break;
                  }

                  try {
                     b var190 = new b(9);
                     BooleanFilter var131 = new BooleanFilter(var190, var166);
                     var177.add(var131);
                  } catch (Exception var48) {
                     Exception var130 = var48;

                     try {
                        q.s("UiGlobalSelector", var130);
                     } catch (Exception var47) {
                        var10000 = var47;
                        boolean var253 = false;
                        break;
                     }
                  }

                  return this;
               case 11:
                  boolean var165;
                  try {
                     var165 = var1.isFilterValue();
                  } catch (Exception var58) {
                     var10000 = var58;
                     boolean var250 = false;
                     break;
                  }

                  try {
                     b var129 = new b(10);
                     BooleanFilter var189 = new BooleanFilter(var129, var165);
                     var177.add(var189);
                  } catch (Exception var15) {
                     Exception var128 = var15;

                     try {
                        q.s("UiGlobalSelector", var128);
                     } catch (Exception var14) {
                        var10000 = var14;
                        boolean var251 = false;
                        break;
                     }
                  }

                  return this;
               case 12:
                  boolean var164;
                  try {
                     var164 = var1.isFilterValue();
                  } catch (Exception var72) {
                     var10000 = var72;
                     boolean var248 = false;
                     break;
                  }

                  try {
                     b var127 = new b(11);
                     BooleanFilter var188 = new BooleanFilter(var127, var164);
                     var177.add(var188);
                  } catch (Exception var40) {
                     Exception var126 = var40;

                     try {
                        q.s("UiGlobalSelector", var126);
                     } catch (Exception var39) {
                        var10000 = var39;
                        boolean var249 = false;
                        break;
                     }
                  }

                  return this;
               case 13:
                  boolean var163;
                  try {
                     var163 = var1.isFilterValue();
                  } catch (Exception var59) {
                     var10000 = var59;
                     boolean var246 = false;
                     break;
                  }

                  try {
                     b var125 = new b(12);
                     BooleanFilter var187 = new BooleanFilter(var125, var163);
                     var177.add(var187);
                  } catch (Exception var33) {
                     Exception var124 = var33;

                     try {
                        q.s("UiGlobalSelector", var124);
                     } catch (Exception var32) {
                        var10000 = var32;
                        boolean var247 = false;
                        break;
                     }
                  }

                  return this;
               case 14:
                  boolean var162;
                  try {
                     var162 = var1.isFilterValue();
                  } catch (Exception var65) {
                     var10000 = var65;
                     boolean var244 = false;
                     break;
                  }

                  try {
                     b var123 = new b(13);
                     BooleanFilter var186 = new BooleanFilter(var123, var162);
                     var177.add(var186);
                  } catch (Exception var29) {
                     Exception var122 = var29;

                     try {
                        q.s("UiGlobalSelector", var122);
                     } catch (Exception var28) {
                        var10000 = var28;
                        boolean var245 = false;
                        break;
                     }
                  }

                  return this;
               case 15:
                  boolean var161;
                  try {
                     var161 = var1.isFilterValue();
                  } catch (Exception var61) {
                     var10000 = var61;
                     boolean var242 = false;
                     break;
                  }

                  try {
                     b var121 = new b(14);
                     BooleanFilter var185 = new BooleanFilter(var121, var161);
                     var177.add(var185);
                  } catch (Exception var44) {
                     Exception var120 = var44;

                     try {
                        q.s("UiGlobalSelector", var120);
                     } catch (Exception var43) {
                        var10000 = var43;
                        boolean var243 = false;
                        break;
                     }
                  }

                  return this;
               case 16:
                  boolean var160;
                  try {
                     var160 = var1.isFilterValue();
                  } catch (Exception var69) {
                     var10000 = var69;
                     boolean var240 = false;
                     break;
                  }

                  try {
                     b var119 = new b(15);
                     BooleanFilter var184 = new BooleanFilter(var119, var160);
                     var177.add(var184);
                  } catch (Exception var23) {
                     Exception var118 = var23;

                     try {
                        q.s("UiGlobalSelector", var118);
                     } catch (Exception var22) {
                        var10000 = var22;
                        boolean var241 = false;
                        break;
                     }
                  }

                  return this;
               case 17:
                  boolean var159;
                  try {
                     var159 = var1.isFilterValue();
                  } catch (Exception var71) {
                     var10000 = var71;
                     boolean var238 = false;
                     break;
                  }

                  try {
                     b var117 = new b(16);
                     BooleanFilter var183 = new BooleanFilter(var117, var159);
                     var177.add(var183);
                  } catch (Exception var13) {
                     Exception var116 = var13;

                     try {
                        q.s("UiGlobalSelector", var116);
                     } catch (Exception var12) {
                        var10000 = var12;
                        boolean var239 = false;
                        break;
                     }
                  }

                  return this;
               case 18:
                  boolean var158;
                  try {
                     var158 = var1.isFilterValue();
                  } catch (Exception var63) {
                     var10000 = var63;
                     boolean var236 = false;
                     break;
                  }

                  try {
                     b var182 = new b(17);
                     BooleanFilter var115 = new BooleanFilter(var182, var158);
                     var177.add(var115);
                  } catch (Exception var17) {
                     Exception var114 = var17;

                     try {
                        q.s("UiGlobalSelector", var114);
                     } catch (Exception var16) {
                        var10000 = var16;
                        boolean var237 = false;
                        break;
                     }
                  }

                  return this;
               case 19:
                  boolean var157;
                  try {
                     var157 = var1.isFilterValue();
                  } catch (Exception var60) {
                     var10000 = var60;
                     boolean var234 = false;
                     break;
                  }

                  try {
                     b var113 = new b(18);
                     BooleanFilter var181 = new BooleanFilter(var113, var157);
                     var177.add(var181);
                  } catch (Exception var38) {
                     Exception var112 = var38;

                     try {
                        q.s("UiGlobalSelector", var112);
                     } catch (Exception var37) {
                        var10000 = var37;
                        boolean var235 = false;
                        break;
                     }
                  }

                  return this;
               case 20:
                  boolean var156;
                  try {
                     var156 = var1.isFilterValue();
                  } catch (Exception var62) {
                     var10000 = var62;
                     boolean var232 = false;
                     break;
                  }

                  try {
                     b var180 = new b(19);
                     BooleanFilter var111 = new BooleanFilter(var180, var156);
                     var177.add(var111);
                  } catch (Exception var25) {
                     Exception var110 = var25;

                     try {
                        q.s("UiGlobalSelector", var110);
                     } catch (Exception var24) {
                        var10000 = var24;
                        boolean var233 = false;
                        break;
                     }
                  }

                  return this;
               case 21:
                  boolean var155;
                  try {
                     var155 = var1.isFilterValue();
                  } catch (Exception var64) {
                     var10000 = var64;
                     boolean var230 = false;
                     break;
                  }

                  try {
                     b var179 = new b(20);
                     BooleanFilter var109 = new BooleanFilter(var179, var155);
                     var177.add(var109);
                  } catch (Exception var36) {
                     Exception var108 = var36;

                     try {
                        q.s("UiGlobalSelector", var108);
                     } catch (Exception var35) {
                        var10000 = var35;
                        boolean var231 = false;
                        break;
                     }
                  }

                  return this;
               case 22:
                  boolean var154;
                  try {
                     var154 = var1.isFilterValue();
                  } catch (Exception var75) {
                     var10000 = var75;
                     boolean var228 = false;
                     break;
                  }

                  try {
                     b var107 = new b(21);
                     BooleanFilter var178 = new BooleanFilter(var107, var154);
                     var177.add(var178);
                  } catch (Exception var52) {
                     Exception var106 = var52;

                     try {
                        q.s("UiGlobalSelector", var106);
                     } catch (Exception var51) {
                        var10000 = var51;
                        boolean var229 = false;
                        break;
                     }
                  }

                  return this;
               case 23:
                  boolean var153;
                  try {
                     var153 = var1.isFilterValue();
                  } catch (Exception var70) {
                     var10000 = var70;
                     boolean var226 = false;
                     break;
                  }

                  try {
                     b var105 = new b(22);
                     BooleanFilter var5 = new BooleanFilter(var105, var153);
                     var177.add(var5);
                  } catch (Exception var54) {
                     Exception var104 = var54;

                     try {
                        q.s("UiGlobalSelector", var104);
                     } catch (Exception var53) {
                        var10000 = var53;
                        boolean var227 = false;
                        break;
                     }
                  }

                  return this;
               default:
                  try {
                     Log.d("UiGlobalSelector", "未识别布尔条件");
                     return this;
                  } catch (Exception var34) {
                     var10000 = var34;
                     boolean var225 = false;
                  }
            }
         }

         Exception var151 = var10000;
         q.s("UiGlobalSelector", var151);
      }

      return this;
   }

   public final void d(int var1, int var2, int var3, int var4) {
      try {
         Rect var6 = new Rect(var1, var2, var3, var4);
         BoundsFilter var5 = new BoundsFilter(var6, 0);
         this.a.add(var5);
      } catch (Exception var7) {
         q.s("UiGlobalSelector", var7);
      }
   }

   public final void e(int var1, int var2, int var3, int var4) {
      try {
         Rect var5 = new Rect(var1, var2, var3, var4);
         BoundsFilter var6 = new BoundsFilter(var5, 2);
         this.a.add(var6);
      } catch (Exception var7) {
         q.s("UiGlobalSelector", var7);
      }
   }

   public final void f(int var1, int var2, int var3, int var4) {
      try {
         Rect var6 = new Rect(var1, var2, var3, var4);
         BoundsFilter var5 = new BoundsFilter(var6, 1);
         this.a.add(var5);
      } catch (Exception var7) {
         q.s("UiGlobalSelector", var7);
      }
   }

   public final void g(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(ClassNameFilters.equals(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void h(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(ClassNameFilters.contains(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void i(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(ClassNameFilters.endsWith(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void j(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(ClassNameFilters.matches(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void k(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(ClassNameFilters.startsWith(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void l(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(DescFilters.equals(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void m(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(DescFilters.contains(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void n(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(DescFilters.endsWith(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void o(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(DescFilters.matches(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void p(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(DescFilters.startsWith(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final UiObject q(UiObject var1) {
      if (var1 != null) {
         try {
            UiObjectCollection var3 = this.s(var1, Integer.MAX_VALUE);
            if (var3.size() == 0) {
               return null;
            }

            return var3.get(var3.size() - 1);
         } catch (Exception var2) {
            q.s("UiGlobalSelector", var2);
         }
      }

      return null;
   }

   public final UiObjectCollection r(UiObject var1) {
      return this.s(var1, Integer.MAX_VALUE);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final UiObjectCollection s(UiObject var1, int var2) {
      this.b.getClass();
      ArrayList var5 = new ArrayList();
      if (var1 != null) {
         Selector var9 = this.a;
         if (var9 != null) {
            int var3 = var2;
            if (var2 <= 0) {
               var3 = Integer.MAX_VALUE;
            }

            Exception var10000;
            label78: {
               ConcurrentLinkedQueue var8;
               try {
                  var8 = new ConcurrentLinkedQueue();
                  var8.offer(var1);
               } catch (Exception var16) {
                  var10000 = var16;
                  boolean var10001 = false;
                  break label78;
               }

               label75:
               while (true) {
                  UiObject var7;
                  try {
                     if (var8.isEmpty()) {
                        return UiObjectCollection.of(var5);
                     }

                     var7 = (UiObject)var8.poll();
                  } catch (Exception var14) {
                     var10000 = var14;
                     boolean var19 = false;
                     break;
                  }

                  if (var7 != null) {
                     boolean var4;
                     try {
                        var4 = var9.filter(var7);
                     } catch (Exception var13) {
                        var10000 = var13;
                        boolean var20 = false;
                        break;
                     }

                     if (var4) {
                        try {
                           var5.add(var7);
                           if (var5.size() >= var3) {
                              return UiObjectCollection.of(var5);
                           }
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var21 = false;
                           break;
                        }
                     }

                     var2 = 0;

                     while (true) {
                        UiObject var6;
                        try {
                           if (var2 > var7.childCount() - 1) {
                              break;
                           }

                           var6 = var7.child(var2);
                        } catch (Exception var15) {
                           var10000 = var15;
                           boolean var22 = false;
                           break label75;
                        }

                        if (var6 != null) {
                           try {
                              var8.offer(var6);
                           } catch (Exception var11) {
                              var10000 = var11;
                              boolean var23 = false;
                              break label75;
                           }
                        }

                        var2++;
                     }

                     if (!var4) {
                        try {
                           if (!var7.equals(var1)) {
                              var7.recycle();
                           }
                        } catch (Exception var10) {
                           var10000 = var10;
                           boolean var24 = false;
                           break;
                        }
                     }
                  }
               }
            }

            Exception var17 = var10000;
            q.s("DFS2", var17);
         }
      }

      return UiObjectCollection.of(var5);
   }

   public final UiObject t(UiObject var1) {
      if (var1 != null) {
         try {
            UiObjectCollection var3 = this.s(var1, 1);
            if (var3.size() == 0) {
               return null;
            }

            return var3.get(0);
         } catch (Exception var2) {
            q.s("UiGlobalSelector", var2);
         }
      }

      return null;
   }

   @Override
   public final String toString() {
      return this.a.toString();
   }

   public final void u(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(IdFilters.equals(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void v(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(IdFilters.contains(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void w(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(IdFilters.endsWith(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void x(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(IdFilters.matches(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   public final void y(String var1) {
      try {
         if (!q.B(var1)) {
            this.a.add(IdFilters.startsWith(var1));
         }
      } catch (Exception var2) {
         q.s("UiGlobalSelector", var2);
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final a z(IntCondition var1) {
      if (var1 != null) {
         Exception var10000;
         label350: {
            byte var2;
            label349: {
               label348: {
                  label347: {
                     label346: {
                        label345: {
                           label344: {
                              label343: {
                                 label342: {
                                    label341: {
                                       label340: {
                                          label339: {
                                             label338: {
                                                label337: {
                                                   String var3;
                                                   label336: {
                                                      label335: {
                                                         label334: {
                                                            label333: {
                                                               label332: {
                                                                  label331: {
                                                                     label330: {
                                                                        label329: {
                                                                           label328: {
                                                                              label327: {
                                                                                 try {
                                                                                    if (var1.getFilterValue() < 0) {
                                                                                       return this;
                                                                                    }

                                                                                    var3 = var1.getFilterKey();
                                                                                    switch (var3.hashCode()) {
                                                                                       case -2105498688:
                                                                                          break;
                                                                                       case -1591577989:
                                                                                          break label327;
                                                                                       case -1354837162:
                                                                                          break label328;
                                                                                       case -860736679:
                                                                                          break label329;
                                                                                       case -713407024:
                                                                                          break label330;
                                                                                       case 113114:
                                                                                          break label331;
                                                                                       case 17743701:
                                                                                          break label332;
                                                                                       case 95472323:
                                                                                          break label333;
                                                                                       case 346647841:
                                                                                          break label334;
                                                                                       case 1329151315:
                                                                                          break label335;
                                                                                       case 1386522692:
                                                                                          break label336;
                                                                                       default:
                                                                                          break label337;
                                                                                    }
                                                                                 } catch (Exception var47) {
                                                                                    var10000 = var47;
                                                                                    boolean var10001 = false;
                                                                                    break label350;
                                                                                 }

                                                                                 try {
                                                                                    if (var3.equals("columnSpan")) {
                                                                                       break label338;
                                                                                    }
                                                                                    break label337;
                                                                                 } catch (Exception var46) {
                                                                                    var10000 = var46;
                                                                                    boolean var102 = false;
                                                                                    break label350;
                                                                                 }
                                                                              }

                                                                              try {
                                                                                 if (var3.equals("regionCount")) {
                                                                                    break label339;
                                                                                 }
                                                                                 break label337;
                                                                              } catch (Exception var45) {
                                                                                 var10000 = var45;
                                                                                 boolean var101 = false;
                                                                                 break label350;
                                                                              }
                                                                           }

                                                                           try {
                                                                              if (var3.equals("column")) {
                                                                                 break label340;
                                                                              }
                                                                              break label337;
                                                                           } catch (Exception var44) {
                                                                              var10000 = var44;
                                                                              boolean var100 = false;
                                                                              break label350;
                                                                           }
                                                                        }

                                                                        try {
                                                                           if (var3.equals("columnCount")) {
                                                                              break label341;
                                                                           }
                                                                           break label337;
                                                                        } catch (Exception var43) {
                                                                           var10000 = var43;
                                                                           boolean var99 = false;
                                                                           break label350;
                                                                        }
                                                                     }

                                                                     try {
                                                                        if (var3.equals("drawingOrder")) {
                                                                           break label342;
                                                                        }
                                                                        break label337;
                                                                     } catch (Exception var42) {
                                                                        var10000 = var42;
                                                                        boolean var98 = false;
                                                                        break label350;
                                                                     }
                                                                  }

                                                                  try {
                                                                     if (var3.equals("row")) {
                                                                        break label343;
                                                                     }
                                                                     break label337;
                                                                  } catch (Exception var41) {
                                                                     var10000 = var41;
                                                                     boolean var97 = false;
                                                                     break label350;
                                                                  }
                                                               }

                                                               try {
                                                                  if (var3.equals("rowCount")) {
                                                                     break label344;
                                                                  }
                                                                  break label337;
                                                               } catch (Exception var40) {
                                                                  var10000 = var40;
                                                                  boolean var96 = false;
                                                                  break label350;
                                                               }
                                                            }

                                                            try {
                                                               if (var3.equals("depth")) {
                                                                  break label345;
                                                               }
                                                               break label337;
                                                            } catch (Exception var39) {
                                                               var10000 = var39;
                                                               boolean var95 = false;
                                                               break label350;
                                                            }
                                                         }

                                                         try {
                                                            if (var3.equals("indexInParent")) {
                                                               break label346;
                                                            }
                                                            break label337;
                                                         } catch (Exception var38) {
                                                            var10000 = var38;
                                                            boolean var94 = false;
                                                            break label350;
                                                         }
                                                      }

                                                      try {
                                                         if (var3.equals("childCount")) {
                                                            break label347;
                                                         }
                                                         break label337;
                                                      } catch (Exception var37) {
                                                         var10000 = var37;
                                                         boolean var93 = false;
                                                         break label350;
                                                      }
                                                   }

                                                   try {
                                                      if (var3.equals("rowSpan")) {
                                                         break label348;
                                                      }
                                                   } catch (Exception var36) {
                                                      var10000 = var36;
                                                      boolean var92 = false;
                                                      break label350;
                                                   }
                                                }

                                                var2 = -1;
                                                break label349;
                                             }

                                             var2 = 2;
                                             break label349;
                                          }

                                          var2 = 10;
                                          break label349;
                                       }

                                       var2 = 0;
                                       break label349;
                                    }

                                    var2 = 1;
                                    break label349;
                                 }

                                 var2 = 5;
                                 break label349;
                              }

                              var2 = 7;
                              break label349;
                           }

                           var2 = 8;
                           break label349;
                        }

                        var2 = 3;
                        break label349;
                     }

                     var2 = 6;
                     break label349;
                  }

                  var2 = 4;
                  break label349;
               }

               var2 = 9;
            }

            switch (var2) {
               case 0:
                  try {
                     var2 = var1.getFilterValue();
                     var67 = var1.getCompare();
                  } catch (Exception var29) {
                     var10000 = var29;
                     boolean var123 = false;
                     break;
                  }

                  try {
                     e var91 = new e(7);
                     this.b(var91, var2, var67);
                  } catch (Exception var25) {
                     Exception var68 = var25;

                     try {
                        q.s("UiGlobalSelector", var68);
                     } catch (Exception var24) {
                        var10000 = var24;
                        boolean var124 = false;
                        break;
                     }
                  }

                  return this;
               case 1:
                  String var90;
                  try {
                     var2 = var1.getFilterValue();
                     var90 = var1.getCompare();
                  } catch (Exception var33) {
                     var10000 = var33;
                     boolean var121 = false;
                     break;
                  }

                  try {
                     e var66 = new e(6);
                     this.b(var66, var2, var90);
                  } catch (Exception var10) {
                     Exception var65 = var10;

                     try {
                        q.s("UiGlobalSelector", var65);
                     } catch (Exception var9) {
                        var10000 = var9;
                        boolean var122 = false;
                        break;
                     }
                  }

                  return this;
               case 2:
                  String var89;
                  try {
                     var2 = var1.getFilterValue();
                     var89 = var1.getCompare();
                  } catch (Exception var32) {
                     var10000 = var32;
                     boolean var119 = false;
                     break;
                  }

                  try {
                     e var64 = new e(8);
                     this.b(var64, var2, var89);
                  } catch (Exception var23) {
                     Exception var63 = var23;

                     try {
                        q.s("UiGlobalSelector", var63);
                     } catch (Exception var22) {
                        var10000 = var22;
                        boolean var120 = false;
                        break;
                     }
                  }

                  return this;
               case 3:
                  try {
                     var2 = var1.getFilterValue();
                     var61 = var1.getCompare();
                  } catch (Exception var31) {
                     var10000 = var31;
                     boolean var117 = false;
                     break;
                  }

                  try {
                     e var88 = new e(9);
                     this.b(var88, var2, var61);
                  } catch (Exception var5) {
                     Exception var62 = var5;

                     try {
                        q.s("UiGlobalSelector", var62);
                     } catch (Exception var4) {
                        var10000 = var4;
                        boolean var118 = false;
                        break;
                     }
                  }

                  return this;
               case 4:
                  try {
                     var2 = var1.getFilterValue();
                     var59 = var1.getCompare();
                  } catch (Exception var28) {
                     var10000 = var28;
                     boolean var115 = false;
                     break;
                  }

                  try {
                     e var87 = new e(4);
                     this.b(var87, var2, var59);
                  } catch (Exception var21) {
                     Exception var60 = var21;

                     try {
                        q.s("UiGlobalSelector", var60);
                     } catch (Exception var20) {
                        var10000 = var20;
                        boolean var116 = false;
                        break;
                     }
                  }

                  return this;
               case 5:
                  try {
                     var2 = var1.getFilterValue();
                     String var86 = var1.getCompare();
                     e var58 = new e(11);
                     this.b(var58, var2, var86);
                     return this;
                  } catch (Exception var8) {
                     var10000 = var8;
                     boolean var114 = false;
                     break;
                  }
               case 6:
                  String var85;
                  try {
                     var2 = var1.getFilterValue();
                     var85 = var1.getCompare();
                  } catch (Exception var30) {
                     var10000 = var30;
                     boolean var112 = false;
                     break;
                  }

                  try {
                     e var57 = new e(14);
                     this.b(var57, var2, var85);
                  } catch (Exception var18) {
                     Exception var56 = var18;

                     try {
                        q.s("UiGlobalSelector", var56);
                     } catch (Exception var17) {
                        var10000 = var17;
                        boolean var113 = false;
                        break;
                     }
                  }

                  return this;
               case 7:
                  String var84;
                  try {
                     var2 = var1.getFilterValue();
                     var84 = var1.getCompare();
                  } catch (Exception var26) {
                     var10000 = var26;
                     boolean var110 = false;
                     break;
                  }

                  try {
                     e var55 = new e(20);
                     this.b(var55, var2, var84);
                  } catch (Exception var16) {
                     Exception var54 = var16;

                     try {
                        q.s("UiGlobalSelector", var54);
                     } catch (Exception var15) {
                        var10000 = var15;
                        boolean var111 = false;
                        break;
                     }
                  }

                  return this;
               case 8:
                  try {
                     var2 = var1.getFilterValue();
                     var52 = var1.getCompare();
                  } catch (Exception var27) {
                     var10000 = var27;
                     boolean var108 = false;
                     break;
                  }

                  try {
                     e var83 = new e(19);
                     this.b(var83, var2, var52);
                  } catch (Exception var14) {
                     Exception var53 = var14;

                     try {
                        q.s("UiGlobalSelector", var53);
                     } catch (Exception var13) {
                        var10000 = var13;
                        boolean var109 = false;
                        break;
                     }
                  }

                  return this;
               case 9:
                  String var82;
                  try {
                     var2 = var1.getFilterValue();
                     var82 = var1.getCompare();
                  } catch (Exception var35) {
                     var10000 = var35;
                     boolean var106 = false;
                     break;
                  }

                  try {
                     e var51 = new e(21);
                     this.b(var51, var2, var82);
                  } catch (Exception var12) {
                     Exception var50 = var12;

                     try {
                        q.s("UiGlobalSelector", var50);
                     } catch (Exception var11) {
                        var10000 = var11;
                        boolean var107 = false;
                        break;
                     }
                  }

                  return this;
               case 10:
                  String var81;
                  try {
                     var2 = var1.getFilterValue();
                     var81 = var1.getCompare();
                  } catch (Exception var34) {
                     var10000 = var34;
                     boolean var104 = false;
                     break;
                  }

                  try {
                     e var49 = new e(17);
                     this.b(var49, var2, var81);
                  } catch (Exception var7) {
                     Exception var48 = var7;

                     try {
                        q.s("UiGlobalSelector", var48);
                     } catch (Exception var6) {
                        var10000 = var6;
                        boolean var105 = false;
                        break;
                     }
                  }

                  return this;
               default:
                  try {
                     Log.d("UiGlobalSelector", "未识别整型条件");
                     return this;
                  } catch (Exception var19) {
                     var10000 = var19;
                     boolean var103 = false;
                  }
            }
         }

         Exception var69 = var10000;
         q.s("UiGlobalSelector", var69);
      }

      return this;
   }
}

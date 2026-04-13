package com.google.json.internal.bind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601Utils {
   private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");
   private static final String UTC_ID = "UTC";

   private static boolean checkOffset(String var0, int var1, char var2) {
      boolean var3;
      if (var1 < var0.length() && var0.charAt(var1) == var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static String format(Date var0) {
      return format(var0, false, TIMEZONE_UTC);
   }

   public static String format(Date var0, boolean var1) {
      return format(var0, var1, TIMEZONE_UTC);
   }

   public static String format(Date var0, boolean var1, TimeZone var2) {
      GregorianCalendar var7 = new GregorianCalendar(var2, Locale.US);
      var7.setTime(var0);
      byte var4;
      if (var1) {
         var4 = 4;
      } else {
         var4 = 0;
      }

      byte var5;
      if (var2.getRawOffset() == 0) {
         var5 = 1;
      } else {
         var5 = 6;
      }

      StringBuilder var8 = new StringBuilder(19 + var4 + var5);
      padInt(var8, var7.get(1), 4);
      char var3 = '-';
      var8.append('-');
      padInt(var8, var7.get(2) + 1, 2);
      var8.append('-');
      padInt(var8, var7.get(5), 2);
      var8.append('T');
      padInt(var8, var7.get(11), 2);
      var8.append(':');
      padInt(var8, var7.get(12), 2);
      var8.append(':');
      padInt(var8, var7.get(13), 2);
      if (var1) {
         var8.append('.');
         padInt(var8, var7.get(14), 3);
      }

      var4 = var2.getOffset(var7.getTimeInMillis());
      if (var4 != 0) {
         int var6 = var4 / 60000;
         var5 = Math.abs(var6 / 60);
         var6 = Math.abs(var6 % 60);
         if (var4 >= 0) {
            var3 = '+';
         }

         var8.append(var3);
         padInt(var8, var5, 2);
         var8.append(':');
         padInt(var8, var6, 2);
      } else {
         var8.append('Z');
      }

      return var8.toString();
   }

   private static int indexOfNonDigit(String var0, int var1) {
      while (var1 < var0.length()) {
         char var2 = var0.charAt(var1);
         if (var2 < '0' || var2 > '9') {
            return var1;
         }

         var1++;
      }

      return var0.length();
   }

   private static void padInt(StringBuilder var0, int var1, int var2) {
      String var3 = Integer.toString(var1);

      for (int var4 = var2 - var3.length(); var4 > 0; var4--) {
         var0.append('0');
      }

      var0.append(var3);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static Date parse(String var0, ParsePosition var1) {
      IllegalArgumentException var124;
      label412: {
         IndexOutOfBoundsException var135;
         label411: {
            label410: {
               label415: {
                  int var3;
                  try {
                     var3 = var1.getIndex();
                  } catch (IndexOutOfBoundsException var96) {
                     var135 = var96;
                     boolean var140 = false;
                     break label411;
                  } catch (NumberFormatException var97) {
                     var134 = var97;
                     boolean var139 = false;
                     break label410;
                  } catch (IllegalArgumentException var98) {
                     var10000 = var98;
                     boolean var10001 = false;
                     break label415;
                  }

                  int var4 = var3 + 4;

                  int var9;
                  try {
                     var9 = parseInt(var0, var3, var4);
                  } catch (IndexOutOfBoundsException var93) {
                     var135 = var93;
                     boolean var143 = false;
                     break label411;
                  } catch (NumberFormatException var94) {
                     var134 = var94;
                     boolean var142 = false;
                     break label410;
                  } catch (IllegalArgumentException var95) {
                     var10000 = var95;
                     boolean var141 = false;
                     break label415;
                  }

                  var3 = var4;

                  label400: {
                     try {
                        if (!checkOffset(var0, var4, '-')) {
                           break label400;
                        }
                     } catch (IndexOutOfBoundsException var90) {
                        var135 = var90;
                        boolean var146 = false;
                        break label411;
                     } catch (NumberFormatException var91) {
                        var134 = var91;
                        boolean var145 = false;
                        break label410;
                     } catch (IllegalArgumentException var92) {
                        var10000 = var92;
                        boolean var144 = false;
                        break label415;
                     }

                     var3 = var4 + 1;
                  }

                  var4 = var3 + 2;

                  int var10;
                  try {
                     var10 = parseInt(var0, var3, var4);
                  } catch (IndexOutOfBoundsException var87) {
                     var135 = var87;
                     boolean var149 = false;
                     break label411;
                  } catch (NumberFormatException var88) {
                     var134 = var88;
                     boolean var148 = false;
                     break label410;
                  } catch (IllegalArgumentException var89) {
                     var10000 = var89;
                     boolean var147 = false;
                     break label415;
                  }

                  var3 = var4;

                  label389: {
                     try {
                        if (!checkOffset(var0, var4, '-')) {
                           break label389;
                        }
                     } catch (IndexOutOfBoundsException var84) {
                        var135 = var84;
                        boolean var152 = false;
                        break label411;
                     } catch (NumberFormatException var85) {
                        var134 = var85;
                        boolean var151 = false;
                        break label410;
                     } catch (IllegalArgumentException var86) {
                        var10000 = var86;
                        boolean var150 = false;
                        break label415;
                     }

                     var3 = var4 + 1;
                  }

                  int var5 = var3 + 2;

                  int var11;
                  boolean var13;
                  try {
                     var11 = parseInt(var0, var3, var5);
                     var13 = checkOffset(var0, var5, 'T');
                  } catch (IndexOutOfBoundsException var81) {
                     var135 = var81;
                     boolean var155 = false;
                     break label411;
                  } catch (NumberFormatException var82) {
                     var134 = var82;
                     boolean var154 = false;
                     break label410;
                  } catch (IllegalArgumentException var83) {
                     var10000 = var83;
                     boolean var153 = false;
                     break label415;
                  }

                  if (!var13) {
                     try {
                        if (var0.length() <= var5) {
                           GregorianCalendar var125 = new GregorianCalendar(var9, var10 - 1, var11);
                           var125.setLenient(false);
                           var1.setIndex(var5);
                           return var125.getTime();
                        }
                     } catch (IndexOutOfBoundsException var78) {
                        var135 = var78;
                        boolean var158 = false;
                        break label411;
                     } catch (NumberFormatException var79) {
                        var134 = var79;
                        boolean var157 = false;
                        break label410;
                     } catch (IllegalArgumentException var80) {
                        var10000 = var80;
                        boolean var156 = false;
                        break label415;
                     }
                  }

                  int var6;
                  int var7;
                  int var120;
                  label375: {
                     if (var13) {
                        var3 = var5 + 1;
                        var5 = var3 + 2;

                        try {
                           var4 = parseInt(var0, var3, var5);
                        } catch (IndexOutOfBoundsException var63) {
                           var135 = var63;
                           boolean var161 = false;
                           break label411;
                        } catch (NumberFormatException var64) {
                           var134 = var64;
                           boolean var160 = false;
                           break label410;
                        } catch (IllegalArgumentException var65) {
                           var10000 = var65;
                           boolean var159 = false;
                           break label415;
                        }

                        var3 = var5;

                        label371: {
                           try {
                              if (!checkOffset(var0, var5, ':')) {
                                 break label371;
                              }
                           } catch (IndexOutOfBoundsException var75) {
                              var135 = var75;
                              boolean var164 = false;
                              break label411;
                           } catch (NumberFormatException var76) {
                              var134 = var76;
                              boolean var163 = false;
                              break label410;
                           } catch (IllegalArgumentException var77) {
                              var10000 = var77;
                              boolean var162 = false;
                              break label415;
                           }

                           var3 = var5 + 1;
                        }

                        var5 = var3 + 2;

                        try {
                           var7 = parseInt(var0, var3, var5);
                        } catch (IndexOutOfBoundsException var60) {
                           var135 = var60;
                           boolean var167 = false;
                           break label411;
                        } catch (NumberFormatException var61) {
                           var134 = var61;
                           boolean var166 = false;
                           break label410;
                        } catch (IllegalArgumentException var62) {
                           var10000 = var62;
                           boolean var165 = false;
                           break label415;
                        }

                        var3 = var5;

                        label365: {
                           try {
                              if (!checkOffset(var0, var5, ':')) {
                                 break label365;
                              }
                           } catch (IndexOutOfBoundsException var72) {
                              var135 = var72;
                              boolean var170 = false;
                              break label411;
                           } catch (NumberFormatException var73) {
                              var134 = var73;
                              boolean var169 = false;
                              break label410;
                           } catch (IllegalArgumentException var74) {
                              var10000 = var74;
                              boolean var168 = false;
                              break label415;
                           }

                           var3 = var5 + 1;
                        }

                        label360: {
                           try {
                              if (var0.length() <= var3) {
                                 break label360;
                              }

                              var114 = var0.charAt(var3);
                           } catch (IndexOutOfBoundsException var69) {
                              var135 = var69;
                              boolean var173 = false;
                              break label411;
                           } catch (NumberFormatException var70) {
                              var134 = var70;
                              boolean var172 = false;
                              break label410;
                           } catch (IllegalArgumentException var71) {
                              var10000 = var71;
                              boolean var171 = false;
                              break label415;
                           }

                           if (var114 != 'Z' && var114 != '+' && var114 != '-') {
                              var6 = var3 + 2;

                              try {
                                 var3 = parseInt(var0, var3, var6);
                              } catch (IndexOutOfBoundsException var57) {
                                 var135 = var57;
                                 boolean var176 = false;
                                 break label411;
                              } catch (NumberFormatException var58) {
                                 var134 = var58;
                                 boolean var175 = false;
                                 break label410;
                              } catch (IllegalArgumentException var59) {
                                 var10000 = var59;
                                 boolean var174 = false;
                                 break label415;
                              }

                              var5 = var3;
                              if (var3 > 59) {
                                 var5 = var3;
                                 if (var3 < 63) {
                                    var5 = 59;
                                 }
                              }

                              label418: {
                                 try {
                                    if (!checkOffset(var0, var6, '.')) {
                                       break label418;
                                    }
                                 } catch (IndexOutOfBoundsException var66) {
                                    var135 = var66;
                                    boolean var179 = false;
                                    break label411;
                                 } catch (NumberFormatException var67) {
                                    var134 = var67;
                                    boolean var178 = false;
                                    break label410;
                                 } catch (IllegalArgumentException var68) {
                                    var10000 = var68;
                                    boolean var177 = false;
                                    break label415;
                                 }

                                 var120 = var6 + 1;

                                 int var12;
                                 try {
                                    var6 = indexOfNonDigit(var0, var120 + 1);
                                    var12 = Math.min(var6, var120 + 3);
                                    var3 = parseInt(var0, var120, var12);
                                 } catch (IndexOutOfBoundsException var54) {
                                    var135 = var54;
                                    boolean var182 = false;
                                    break label411;
                                 } catch (NumberFormatException var55) {
                                    var134 = var55;
                                    boolean var181 = false;
                                    break label410;
                                 } catch (IllegalArgumentException var56) {
                                    var10000 = var56;
                                    boolean var180 = false;
                                    break label415;
                                 }

                                 var120 = var12 - var120;
                                 if (var120 != 1) {
                                    if (var120 == 2) {
                                       var3 *= 10;
                                    }
                                 } else {
                                    var3 *= 100;
                                 }

                                 var3 = var4;
                                 var4 = var3;
                                 var120 = var5;
                                 break label375;
                              }

                              var3 = var4;
                              var4 = 0;
                              var120 = var5;
                              break label375;
                           }
                        }

                        var5 = var3;
                        var3 = var4;
                        var6 = var7;
                     } else {
                        var3 = 0;
                        var6 = 0;
                     }

                     var4 = 0;
                     var120 = 0;
                     var7 = var6;
                     var6 = var5;
                  }

                  char var2;
                  label330: {
                     try {
                        if (var0.length() > var6) {
                           var2 = var0.charAt(var6);
                           break label330;
                        }
                     } catch (IndexOutOfBoundsException var51) {
                        var135 = var51;
                        boolean var185 = false;
                        break label411;
                     } catch (NumberFormatException var52) {
                        var134 = var52;
                        boolean var184 = false;
                        break label410;
                     } catch (IllegalArgumentException var53) {
                        var10000 = var53;
                        boolean var183 = false;
                        break label415;
                     }

                     try {
                        var124 = new IllegalArgumentException("No time zone indicator");
                        throw var124;
                     } catch (IndexOutOfBoundsException var48) {
                        var135 = var48;
                        boolean var188 = false;
                        break label411;
                     } catch (NumberFormatException var49) {
                        var134 = var49;
                        boolean var187 = false;
                        break label410;
                     } catch (IllegalArgumentException var50) {
                        var10000 = var50;
                        boolean var186 = false;
                        break label415;
                     }
                  }

                  GregorianCalendar var127;
                  label316: {
                     label315: {
                        label314: {
                           label313: {
                              label423: {
                                 if (var2 == 'Z') {
                                    try {
                                       var122 = TIMEZONE_UTC;
                                    } catch (IndexOutOfBoundsException var33) {
                                       var135 = var33;
                                       boolean var212 = false;
                                       break label315;
                                    } catch (NumberFormatException var34) {
                                       var137 = var34;
                                       boolean var211 = false;
                                       break label314;
                                    } catch (IllegalArgumentException var35) {
                                       var136 = var35;
                                       boolean var210 = false;
                                       break label313;
                                    }

                                    var5 = var6 + 1;
                                 } else {
                                    label422: {
                                       if (var2 != '+' && var2 != '-') {
                                          try {
                                             StringBuilder var126 = new StringBuilder("Invalid time zone indicator '");
                                             var126.append(var2);
                                             var126.append("'");
                                             IndexOutOfBoundsException var123 = new IndexOutOfBoundsException(var126.toString());
                                             throw var123;
                                          } catch (IndexOutOfBoundsException var18) {
                                             var135 = var18;
                                             boolean var209 = false;
                                             break label315;
                                          } catch (NumberFormatException var19) {
                                             var137 = var19;
                                             boolean var208 = false;
                                             break label314;
                                          } catch (IllegalArgumentException var20) {
                                             var136 = var20;
                                             boolean var207 = false;
                                             break label313;
                                          }
                                       }

                                       label306: {
                                          try {
                                             var121 = var0.substring(var6);
                                             if (var121.length() >= 5) {
                                                break label306;
                                             }
                                          } catch (IndexOutOfBoundsException var42) {
                                             var135 = var42;
                                             boolean var191 = false;
                                             break label315;
                                          } catch (NumberFormatException var43) {
                                             var137 = var43;
                                             boolean var190 = false;
                                             break label314;
                                          } catch (IllegalArgumentException var44) {
                                             var136 = var44;
                                             boolean var189 = false;
                                             break label313;
                                          }

                                          try {
                                             var121 = var121.concat("00");
                                          } catch (IndexOutOfBoundsException var30) {
                                             var135 = var30;
                                             boolean var194 = false;
                                             break label315;
                                          } catch (NumberFormatException var31) {
                                             var137 = var31;
                                             boolean var193 = false;
                                             break label314;
                                          } catch (IllegalArgumentException var32) {
                                             var136 = var32;
                                             boolean var192 = false;
                                             break label313;
                                          }
                                       }

                                       label424: {
                                          label298:
                                          try {
                                             var5 = var6 + var121.length();
                                             if (!"+0000".equals(var121) && !"+00:00".equals(var121)) {
                                                break label298;
                                             }
                                             break label424;
                                          } catch (IndexOutOfBoundsException var39) {
                                             var135 = var39;
                                             boolean var197 = false;
                                             break label315;
                                          } catch (NumberFormatException var40) {
                                             var137 = var40;
                                             boolean var196 = false;
                                             break label314;
                                          } catch (IllegalArgumentException var41) {
                                             var136 = var41;
                                             boolean var195 = false;
                                             break label313;
                                          }

                                          try {
                                             var15 = "GMT".concat(var121);
                                             var122 = TimeZone.getTimeZone(var15);
                                             String var16 = var122.getID();
                                             if (!var16.equals(var15) && !var16.replace(":", "").equals(var15)) {
                                                break label423;
                                             }
                                             break label422;
                                          } catch (IndexOutOfBoundsException var36) {
                                             var135 = var36;
                                             boolean var203 = false;
                                             break label315;
                                          } catch (NumberFormatException var37) {
                                             var137 = var37;
                                             boolean var202 = false;
                                             break label314;
                                          } catch (IllegalArgumentException var38) {
                                             var136 = var38;
                                             boolean var201 = false;
                                             break label313;
                                          }
                                       }

                                       try {
                                          var122 = TIMEZONE_UTC;
                                       } catch (IndexOutOfBoundsException var27) {
                                          var135 = var27;
                                          boolean var200 = false;
                                          break label315;
                                       } catch (NumberFormatException var28) {
                                          var137 = var28;
                                          boolean var199 = false;
                                          break label314;
                                       } catch (IllegalArgumentException var29) {
                                          var136 = var29;
                                          boolean var198 = false;
                                          break label313;
                                       }
                                    }
                                 }

                                 try {
                                    var127 = new GregorianCalendar(var122);
                                    var127.setLenient(false);
                                    var127.set(1, var9);
                                    var127.set(2, var10 - 1);
                                    var127.set(5, var11);
                                    var127.set(11, var3);
                                    var127.set(12, var7);
                                    var127.set(13, var120);
                                    var127.set(14, var4);
                                    break label316;
                                 } catch (IndexOutOfBoundsException var24) {
                                    var135 = var24;
                                    boolean var215 = false;
                                    break label315;
                                 } catch (NumberFormatException var25) {
                                    var137 = var25;
                                    boolean var214 = false;
                                    break label314;
                                 } catch (IllegalArgumentException var26) {
                                    var136 = var26;
                                    boolean var213 = false;
                                    break label313;
                                 }
                              }

                              try {
                                 StringBuilder var131 = new StringBuilder("Mismatching time zone indicator: ");
                                 var131.append(var15);
                                 var131.append(" given, resolves to ");
                                 var131.append(var122.getID());
                                 IndexOutOfBoundsException var17 = new IndexOutOfBoundsException(var131.toString());
                                 throw var17;
                              } catch (IndexOutOfBoundsException var21) {
                                 var135 = var21;
                                 boolean var206 = false;
                                 break label315;
                              } catch (NumberFormatException var22) {
                                 var137 = var22;
                                 boolean var205 = false;
                                 break label314;
                              } catch (IllegalArgumentException var23) {
                                 var136 = var23;
                                 boolean var204 = false;
                              }
                           }

                           var124 = var136;
                           break label412;
                        }

                        var124 = var137;
                        break label412;
                     }

                     var124 = var135;
                     break label412;
                  }

                  try {
                     var1.setIndex(var5);
                     return var127.getTime();
                  } catch (IndexOutOfBoundsException var45) {
                     var135 = var45;
                     boolean var218 = false;
                     break label411;
                  } catch (NumberFormatException var46) {
                     var134 = var46;
                     boolean var217 = false;
                     break label410;
                  } catch (IllegalArgumentException var47) {
                     var10000 = var47;
                     boolean var216 = false;
                  }
               }

               var124 = var10000;
               break label412;
            }

            var124 = var134;
            break label412;
         }

         var124 = var135;
      }

      if (var0 == null) {
         var0 = null;
      } else {
         StringBuilder var128 = new StringBuilder("\"");
         var128.append(var0);
         var128.append('"');
         var0 = var128.toString();
      }

      String var129;
      label265: {
         String var132 = var124.getMessage();
         if (var132 != null) {
            var129 = var132;
            if (!var132.isEmpty()) {
               break label265;
            }
         }

         StringBuilder var130 = new StringBuilder("(");
         var130.append(var124.getClass().getName());
         var130.append(")");
         var129 = var130.toString();
      }

      StringBuilder var133 = new StringBuilder("Failed to parse date [");
      var133.append(var0);
      var133.append("]: ");
      var133.append(var129);
      ParseException var100 = new ParseException(var133.toString(), var1.getIndex());
      var100.initCause(var124);
      throw var100;
   }

   private static int parseInt(String var0, int var1, int var2) {
      if (var1 >= 0 && var2 <= var0.length() && var1 <= var2) {
         int var3;
         int var7;
         if (var1 < var2) {
            var3 = var1 + 1;
            var7 = Character.digit(var0.charAt(var1), 10);
            if (var7 < 0) {
               StringBuilder var6 = new StringBuilder("Invalid number: ");
               var6.append(var0.substring(var1, var2));
               throw new NumberFormatException(var6.toString());
            }

            var7 = -var7;
         } else {
            var7 = 0;
            var3 = var1;
         }

         while (var3 < var2) {
            int var5 = Character.digit(var0.charAt(var3), 10);
            if (var5 < 0) {
               StringBuilder var8 = new StringBuilder("Invalid number: ");
               var8.append(var0.substring(var1, var2));
               throw new NumberFormatException(var8.toString());
            }

            var7 = var7 * 10 - var5;
            var3++;
         }

         return -var7;
      } else {
         throw new NumberFormatException(var0);
      }
   }
}

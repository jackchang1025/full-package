package y;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

public final class d extends ContentObserver {
   public d() {
      super(new Handler(Looper.getMainLooper()));
   }

   public final void onChange(boolean var1) {
      super.onChange(var1);
   }

   public final void onChange(boolean param1, Uri param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:136)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:85)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: aload 0
      // 001: iload 1
      // 002: aload 2
      // 003: invokespecial android/database/ContentObserver.onChange (ZLandroid/net/Uri;)V
      // 006: aload 2
      // 007: ifnull 168
      // 00a: new java/lang/StringBuilder
      // 00d: dup
      // 00e: invokespecial java/lang/StringBuilder.<init> ()V
      // 011: astore 4
      // 013: aload 4
      // 015: aload 2
      // 016: invokevirtual android/net/Uri.toString ()Ljava/lang/String;
      // 019: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 01c: pop
      // 01d: aload 4
      // 01f: ldc " is changed"
      // 021: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 024: pop
      // 025: ldc "SettingsContentObserver"
      // 027: aload 4
      // 029: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 02c: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 02f: pop
      // 030: ldc "development_settings_enabled"
      // 032: invokestatic android/provider/Settings$Global.getUriFor (Ljava/lang/String;)Landroid/net/Uri;
      // 035: astore 5
      // 037: ldc "adb_enabled"
      // 039: invokestatic android/provider/Settings$Global.getUriFor (Ljava/lang/String;)Landroid/net/Uri;
      // 03c: astore 6
      // 03e: ldc "adb_wifi_enabled"
      // 040: invokestatic android/provider/Settings$Global.getUriFor (Ljava/lang/String;)Landroid/net/Uri;
      // 043: astore 4
      // 045: aload 2
      // 046: aload 5
      // 048: invokevirtual java/lang/Object.equals (Ljava/lang/Object;)Z
      // 04b: ifeq 092
      // 04e: invokestatic com/guard/wallet/utils/g.K ()Z
      // 051: istore 1
      // 052: ldc com/guard/wallet/entity/ADBConfig
      // 054: monitorenter
      // 055: invokestatic com/guard/wallet/utils/h.J ()Lcom/guard/wallet/entity/ADBConfig;
      // 058: invokevirtual com/guard/wallet/entity/ADBConfig.getEnableDevelopment ()I
      // 05b: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 05e: bipush 1
      // 05f: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 062: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 065: istore 3
      // 066: ldc com/guard/wallet/entity/ADBConfig
      // 068: monitorexit
      // 069: iload 1
      // 06a: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 06d: iload 3
      // 06e: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 071: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 074: ifne 152
      // 077: invokestatic com/guard/wallet/utils/h.Q ()V
      // 07a: invokestatic com/guard/wallet/utils/g.K ()Z
      // 07d: ifeq 086
      // 080: ldc "KEEP_ADB_ALIVE_DEVELOPMENT_ON"
      // 082: astore 2
      // 083: goto 154
      // 086: ldc "KEEP_ADB_ALIVE_DEVELOPMENT_OFF"
      // 088: astore 2
      // 089: goto 154
      // 08c: astore 2
      // 08d: ldc com/guard/wallet/entity/ADBConfig
      // 08f: monitorexit
      // 090: aload 2
      // 091: athrow
      // 092: aload 2
      // 093: aload 6
      // 095: invokevirtual java/lang/Object.equals (Ljava/lang/Object;)Z
      // 098: ifeq 0df
      // 09b: invokestatic com/guard/wallet/utils/g.I ()Z
      // 09e: istore 3
      // 09f: ldc com/guard/wallet/entity/ADBConfig
      // 0a1: monitorenter
      // 0a2: invokestatic com/guard/wallet/utils/h.J ()Lcom/guard/wallet/entity/ADBConfig;
      // 0a5: invokevirtual com/guard/wallet/entity/ADBConfig.getEnableDebug ()I
      // 0a8: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 0ab: bipush 1
      // 0ac: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 0af: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0b2: istore 1
      // 0b3: ldc com/guard/wallet/entity/ADBConfig
      // 0b5: monitorexit
      // 0b6: iload 3
      // 0b7: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0ba: iload 1
      // 0bb: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 0be: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0c1: ifne 152
      // 0c4: invokestatic com/guard/wallet/utils/h.Q ()V
      // 0c7: invokestatic com/guard/wallet/utils/g.I ()Z
      // 0ca: ifeq 0d3
      // 0cd: ldc "KEEP_ADB_ALIVE_ADB_DEBUG_ON"
      // 0cf: astore 2
      // 0d0: goto 154
      // 0d3: ldc "KEEP_ADB_ALIVE_ADB_DEBUG_OFF"
      // 0d5: astore 2
      // 0d6: goto 154
      // 0d9: astore 2
      // 0da: ldc com/guard/wallet/entity/ADBConfig
      // 0dc: monitorexit
      // 0dd: aload 2
      // 0de: athrow
      // 0df: aload 2
      // 0e0: aload 4
      // 0e2: invokevirtual java/lang/Object.equals (Ljava/lang/Object;)Z
      // 0e5: ifeq 12c
      // 0e8: invokestatic com/guard/wallet/utils/g.J ()Z
      // 0eb: istore 1
      // 0ec: ldc com/guard/wallet/entity/ADBConfig
      // 0ee: monitorenter
      // 0ef: invokestatic com/guard/wallet/utils/h.J ()Lcom/guard/wallet/entity/ADBConfig;
      // 0f2: invokevirtual com/guard/wallet/entity/ADBConfig.getEnableWifiDebug ()I
      // 0f5: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 0f8: bipush 1
      // 0f9: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 0fc: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 0ff: istore 3
      // 100: ldc com/guard/wallet/entity/ADBConfig
      // 102: monitorexit
      // 103: iload 1
      // 104: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 107: iload 3
      // 108: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 10b: invokestatic java/util/Objects.equals (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 10e: ifne 152
      // 111: invokestatic com/guard/wallet/utils/h.Q ()V
      // 114: invokestatic com/guard/wallet/utils/g.J ()Z
      // 117: ifeq 120
      // 11a: ldc "KEEP_ADB_ALIVE_WIFI_DEBUG_ON"
      // 11c: astore 2
      // 11d: goto 154
      // 120: ldc "KEEP_ADB_ALIVE_WIFI_DEBUG_OFF"
      // 122: astore 2
      // 123: goto 154
      // 126: astore 2
      // 127: ldc com/guard/wallet/entity/ADBConfig
      // 129: monitorexit
      // 12a: aload 2
      // 12b: athrow
      // 12c: new java/lang/StringBuilder
      // 12f: dup
      // 130: invokespecial java/lang/StringBuilder.<init> ()V
      // 133: astore 4
      // 135: aload 4
      // 137: aload 2
      // 138: invokevirtual android/net/Uri.toString ()Ljava/lang/String;
      // 13b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 13e: pop
      // 13f: aload 4
      // 141: ldc " is changed"
      // 143: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 146: pop
      // 147: ldc "SettingsContentObserver"
      // 149: aload 4
      // 14b: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 14e: invokestatic android/util/Log.d (Ljava/lang/String;Ljava/lang/String;)I
      // 151: pop
      // 152: aconst_null
      // 153: astore 2
      // 154: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 157: ifnull 168
      // 15a: aload 2
      // 15b: invokestatic a1/q.B (Ljava/lang/Object;)Z
      // 15e: ifne 168
      // 161: invokestatic com/guard/wallet/MainApplication.getInstance ()Lcom/guard/wallet/MainApplication;
      // 164: aload 2
      // 165: invokevirtual com/guard/wallet/MainApplication.offerStrategyEvent (Ljava/lang/String;)V
      // 168: return
   }
}

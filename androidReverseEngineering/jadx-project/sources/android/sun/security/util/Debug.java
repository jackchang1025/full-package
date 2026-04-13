package android.sun.security.util;

import android.sun.security.action.GetPropertyAction;
import java.math.BigInteger;
import java.security.AccessController;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.guard.wallet.entity.BuildConfig;

/* loaded from: classes.dex */
public class Debug {
    private static String args;
    private static final char[] hexDigits;
    private String prefix;

    /* JADX WARN: Removed duplicated region for block: B:6:0x0040  */
    static {
        String str;
        args = (String) AccessController.doPrivileged(new GetPropertyAction("java.security.debug"));
        String str2 = (String) AccessController.doPrivileged(new GetPropertyAction("java.security.auth.debug"));
        if (args != null) {
            if (str2 != null) {
                str2 = args + "," + str2;
            }
            str = args;
            if (str != null) {
                String marshal = marshal(str);
                args = marshal;
                if (marshal.equals("help")) {
                    Help();
                }
            }
            hexDigits = "0123456789abcdef".toCharArray();
        }
        args = str2;
        str = args;
        if (str != null) {
        }
        hexDigits = "0123456789abcdef".toCharArray();
    }

    public static void Help() {
        System.err.println();
        System.err.println("all           turn on all debugging");
        System.err.println("access        print all checkPermission results");
        System.err.println("combiner      SubjectDomainCombiner debugging");
        System.err.println("gssloginconfig");
        System.err.println("configfile    JAAS ConfigFile loading");
        System.err.println("configparser  JAAS ConfigFile parsing");
        System.err.println("              GSS LoginConfigImpl debugging");
        System.err.println("jar           jar verification");
        System.err.println("logincontext  login context results");
        System.err.println("policy        loading and granting");
        System.err.println("provider      security provider debugging");
        System.err.println("scl           permissions SecureClassLoader assigns");
        System.err.println();
        System.err.println("The following can be used with access:");
        System.err.println();
        System.err.println("stack         include stack trace");
        System.err.println("domain        dump all domains in context");
        System.err.println("failure       before throwing exception, dump stack");
        System.err.println("              and domain that didn't have permission");
        System.err.println();
        System.err.println("The following can be used with stack and domain:");
        System.err.println();
        System.err.println("permission=<classname>");
        System.err.println("              only dump output if specified permission");
        System.err.println("              is being checked");
        System.err.println("codebase=<URL>");
        System.err.println("              only dump output if specified codebase");
        System.err.println("              is being checked");
        System.err.println();
        System.err.println("Note: Separate multiple options with a comma");
        System.exit(0);
    }

    public static Debug getInstance(String str) {
        return getInstance(str, str);
    }

    public static boolean isOn(String str) {
        String str2 = args;
        if (str2 == null) {
            return false;
        }
        return (str2.indexOf("all") == -1 && args.indexOf(str) == -1) ? false : true;
    }

    private static String marshal(String str) {
        if (str == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        Matcher matcher = Pattern.compile("[Pp][Ee][Rr][Mm][Ii][Ss][Ss][Ii][Oo][Nn]=[a-zA-Z_$][a-zA-Z0-9_$]*([.][a-zA-Z_$][a-zA-Z0-9_$]*)*").matcher(new StringBuffer(str));
        StringBuffer stringBuffer2 = new StringBuffer();
        while (matcher.find()) {
            stringBuffer.append(matcher.group().replaceFirst("[Pp][Ee][Rr][Mm][Ii][Ss][Ss][Ii][Oo][Nn]=", "permission="));
            stringBuffer.append("  ");
            matcher.appendReplacement(stringBuffer2, BuildConfig.FLAVOR);
        }
        matcher.appendTail(stringBuffer2);
        Matcher matcher2 = Pattern.compile("[Cc][Oo][Dd][Ee][Bb][Aa][Ss][Ee]=[^, ;]*").matcher(stringBuffer2);
        StringBuffer stringBuffer3 = new StringBuffer();
        while (matcher2.find()) {
            stringBuffer.append(matcher2.group().replaceFirst("[Cc][Oo][Dd][Ee][Bb][Aa][Ss][Ee]=", "codebase="));
            stringBuffer.append("  ");
            matcher2.appendReplacement(stringBuffer3, BuildConfig.FLAVOR);
        }
        matcher2.appendTail(stringBuffer3);
        stringBuffer.append(stringBuffer3.toString().toLowerCase(Locale.ENGLISH));
        return stringBuffer.toString();
    }

    public static String toHexString(BigInteger bigInteger) {
        String str;
        String bigInteger2 = bigInteger.toString(16);
        StringBuffer stringBuffer = new StringBuffer(bigInteger2.length() * 2);
        if (bigInteger2.startsWith("-")) {
            stringBuffer.append("   -");
            bigInteger2 = bigInteger2.substring(1);
        } else {
            stringBuffer.append("    ");
        }
        if (bigInteger2.length() % 2 != 0) {
            bigInteger2 = "0".concat(bigInteger2);
        }
        int i2 = 0;
        while (i2 < bigInteger2.length()) {
            int i3 = i2 + 2;
            stringBuffer.append(bigInteger2.substring(i2, i3));
            if (i3 != bigInteger2.length()) {
                if (i3 % 64 == 0) {
                    str = "\n    ";
                } else if (i3 % 8 == 0) {
                    str = " ";
                }
                stringBuffer.append(str);
            }
            i2 = i3;
        }
        return stringBuffer.toString();
    }

    public static String toString(byte[] bArr) {
        if (bArr == null) {
            return "(null)";
        }
        StringBuilder sb = new StringBuilder(bArr.length * 3);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = bArr[i2] & 255;
            if (i2 != 0) {
                sb.append(':');
            }
            char[] cArr = hexDigits;
            sb.append(cArr[i3 >>> 4]);
            sb.append(cArr[i3 & 15]);
        }
        return sb.toString();
    }

    public void println() {
        System.err.println(this.prefix + ":");
    }

    public static Debug getInstance(String str, String str2) {
        if (!isOn(str)) {
            return null;
        }
        Debug debug = new Debug();
        debug.prefix = str2;
        return debug;
    }

    public void println(String str) {
        System.err.println(this.prefix + ": " + str);
    }

    public static void println(String str, String str2) {
        System.err.println(str + ": " + str2);
    }
}

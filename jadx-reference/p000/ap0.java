package p000;

import java.math.BigInteger;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Security;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.bouncycastle.util.Strings;

/* loaded from: classes2.dex */
public class ap0 {
    private static final ThreadLocal threadProperties = new ThreadLocal();

    /* renamed from: ap0$a0 */
    public static class C0103a0 implements PrivilegedAction {
        final /* synthetic */ String val$propertyName;

        public C0103a0(String str) {
            this.val$propertyName = str;
        }

        @Override // java.security.PrivilegedAction
        public Object run() {
            return Security.getProperty(this.val$propertyName);
        }
    }

    /* renamed from: ap0$a1 */
    public static class C0104a1 implements PrivilegedAction {
        final /* synthetic */ String val$propertyName;

        public C0104a1(String str) {
            this.val$propertyName = str;
        }

        @Override // java.security.PrivilegedAction
        public Object run() {
            return System.getProperty(this.val$propertyName);
        }
    }

    private ap0() {
    }

    public static BigInteger asBigInteger(String str) {
        String propertyValue = getPropertyValue(str);
        if (propertyValue != null) {
            return new BigInteger(propertyValue);
        }
        return null;
    }

    public static Set<String> asKeySet(String str) {
        HashSet hashSet = new HashSet();
        String propertyValue = getPropertyValue(str);
        if (propertyValue != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(propertyValue, ",");
            while (stringTokenizer.hasMoreElements()) {
                hashSet.add(Strings.toLowerCase(stringTokenizer.nextToken()).trim());
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public static String getPropertyValue(String str) {
        String str2;
        String str3 = (String) AccessController.doPrivileged(new C0103a0(str));
        if (str3 != null) {
            return str3;
        }
        Map map = (Map) threadProperties.get();
        return (map == null || (str2 = (String) map.get(str)) == null) ? (String) AccessController.doPrivileged(new C0104a1(str)) : str2;
    }

    public static boolean isOverrideSet(String str) {
        try {
            return isSetTrue(getPropertyValue(str));
        } catch (AccessControlException unused) {
            return false;
        }
    }

    public static boolean isOverrideSetTo(String str, boolean z) {
        try {
            String propertyValue = getPropertyValue(str);
            return z ? isSetTrue(propertyValue) : isSetFalse(propertyValue);
        } catch (AccessControlException unused) {
            return false;
        }
    }

    private static boolean isSetFalse(String str) {
        if (str == null || str.length() != 5) {
            return false;
        }
        return (str.charAt(0) == 'f' || str.charAt(0) == 'F') && (str.charAt(1) == 'a' || str.charAt(1) == 'A') && ((str.charAt(2) == 'l' || str.charAt(2) == 'L') && ((str.charAt(3) == 's' || str.charAt(3) == 'S') && (str.charAt(4) == 'e' || str.charAt(4) == 'E')));
    }

    private static boolean isSetTrue(String str) {
        if (str == null || str.length() != 4) {
            return false;
        }
        return (str.charAt(0) == 't' || str.charAt(0) == 'T') && (str.charAt(1) == 'r' || str.charAt(1) == 'R') && ((str.charAt(2) == 'u' || str.charAt(2) == 'U') && (str.charAt(3) == 'e' || str.charAt(3) == 'E'));
    }

    public static boolean removeThreadOverride(String str) {
        String str2;
        ThreadLocal threadLocal = threadProperties;
        Map map = (Map) threadLocal.get();
        if (map == null || (str2 = (String) map.remove(str)) == null) {
            return false;
        }
        if (map.isEmpty()) {
            threadLocal.remove();
        }
        return "true".equals(Strings.toLowerCase(str2));
    }

    public static boolean setThreadOverride(String str, boolean z) {
        boolean zIsOverrideSet = isOverrideSet(str);
        ThreadLocal threadLocal = threadProperties;
        Map map = (Map) threadLocal.get();
        if (map == null) {
            map = new HashMap();
            threadLocal.set(map);
        }
        map.put(str, z ? "true" : "false");
        return zIsOverrideSet;
    }
}

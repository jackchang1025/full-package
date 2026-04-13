package org.bouncycastle.jsse.provider;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Security;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes.dex */
class PropertyUtils {
    private static final Logger LOG = Logger.getLogger(PropertyUtils.class.getName());

    public static boolean getBooleanSecurityProperty(String str, boolean z2) {
        String securityProperty = getSecurityProperty(str);
        if (securityProperty != null) {
            if ("true".equalsIgnoreCase(securityProperty)) {
                LOG.log(Level.INFO, "Found boolean security property [" + str + "]: true");
                return true;
            }
            if ("false".equalsIgnoreCase(securityProperty)) {
                LOG.log(Level.INFO, "Found boolean security property [" + str + "]: false");
                return false;
            }
            LOG.log(Level.WARNING, "Unrecognized value for boolean security property [" + str + "]: " + securityProperty);
        }
        LOG.log(Level.FINE, "Boolean security property [" + str + "] defaulted to: " + z2);
        return z2;
    }

    public static boolean getBooleanSystemProperty(String str, boolean z2) {
        String systemProperty = getSystemProperty(str);
        if (systemProperty != null) {
            if ("true".equalsIgnoreCase(systemProperty)) {
                LOG.log(Level.INFO, "Found boolean system property [" + str + "]: true");
                return true;
            }
            if ("false".equalsIgnoreCase(systemProperty)) {
                LOG.log(Level.INFO, "Found boolean system property [" + str + "]: false");
                return false;
            }
            LOG.log(Level.WARNING, "Unrecognized value for boolean system property [" + str + "]: " + systemProperty);
        }
        LOG.log(Level.FINE, "Boolean system property [" + str + "] defaulted to: " + z2);
        return z2;
    }

    public static int getIntegerSystemProperty(String str, int i2, int i3, int i4) {
        String systemProperty = getSystemProperty(str);
        if (systemProperty != null) {
            try {
                int parseInt = Integer.parseInt(systemProperty);
                if (parseInt >= i3 && parseInt <= i4) {
                    LOG.log(Level.INFO, "Found integer system property [" + str + "]: " + parseInt);
                    return parseInt;
                }
                Logger logger = LOG;
                Level level = Level.WARNING;
                if (logger.isLoggable(level)) {
                    logger.log(level, "Out-of-range (" + getRangeString(i3, i4) + ") integer system property [" + str + "]: " + systemProperty);
                }
            } catch (Exception unused) {
                LOG.log(Level.WARNING, "Unrecognized value for integer system property [" + str + "]: " + systemProperty);
            }
        }
        LOG.log(Level.FINE, "Integer system property [" + str + "] defaulted to: " + i2);
        return i2;
    }

    private static String getRangeString(int i2, int i3) {
        StringBuilder sb = new StringBuilder(32);
        if (Integer.MIN_VALUE != i2) {
            sb.append(i2);
            sb.append(" <= ");
        }
        sb.append('x');
        if (Integer.MAX_VALUE != i3) {
            sb.append(" <= ");
            sb.append(i3);
        }
        return sb.toString();
    }

    public static String getSecurityProperty(final String str) {
        return (String) AccessController.doPrivileged(new PrivilegedAction<String>() { // from class: org.bouncycastle.jsse.provider.PropertyUtils.1
            @Override // java.security.PrivilegedAction
            public String run() {
                return Security.getProperty(str);
            }
        });
    }

    public static String getSensitiveStringSystemProperty(String str) {
        String systemProperty = getSystemProperty(str);
        if (systemProperty == null) {
            return null;
        }
        LOG.info("Found sensitive string system property [" + str + "]");
        return systemProperty;
    }

    public static String[] getStringArraySecurityProperty(String str, String str2) {
        return parseStringArray(getStringSecurityProperty(str, str2));
    }

    public static String[] getStringArraySystemProperty(String str) {
        return parseStringArray(getStringSystemProperty(str));
    }

    public static String getStringSecurityProperty(String str) {
        String securityProperty = getSecurityProperty(str);
        if (securityProperty == null) {
            return null;
        }
        LOG.log(Level.INFO, "Found string security property [" + str + "]: " + securityProperty);
        return securityProperty;
    }

    public static String getStringSystemProperty(String str) {
        String systemProperty = getSystemProperty(str);
        if (systemProperty == null) {
            return null;
        }
        LOG.log(Level.INFO, "Found string system property [" + str + "]: " + systemProperty);
        return systemProperty;
    }

    public static String getSystemProperty(final String str) {
        try {
            return (String) AccessController.doPrivileged(new PrivilegedAction<String>() { // from class: org.bouncycastle.jsse.provider.PropertyUtils.2
                @Override // java.security.PrivilegedAction
                public String run() {
                    return System.getProperty(str);
                }
            });
        } catch (RuntimeException e2) {
            LOG.log(Level.WARNING, "Failed to get system property", (Throwable) e2);
            return null;
        }
    }

    private static String[] parseStringArray(String str) {
        if (str == null) {
            return null;
        }
        String[] split = JsseUtils.stripDoubleQuotes(str.trim()).split(",");
        String[] strArr = new String[split.length];
        int i2 = 0;
        for (String str2 : split) {
            String trim = str2.trim();
            if (trim.length() >= 1) {
                strArr[i2] = trim;
                i2++;
            }
        }
        return JsseUtils.resize(strArr, i2);
    }

    public static String getStringSecurityProperty(String str, String str2) {
        String securityProperty = getSecurityProperty(str);
        if (securityProperty != null) {
            LOG.log(Level.INFO, "Found string security property [" + str + "]: " + securityProperty);
            return securityProperty;
        }
        LOG.log(Level.WARNING, "String security property [" + str + "] defaulted to: " + str2);
        return str2;
    }
}

package android.support.v4.os;

import android.support.annotation.GuardedBy;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.Size;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
final class LocaleListHelper {
    private static final int NUM_PSEUDO_LOCALES = 2;
    private static final String STRING_AR_XB = "ar-XB";
    private static final String STRING_EN_XA = "en-XA";
    private final Locale[] mList;

    @NonNull
    private final String mStringRepresentation;
    private static final Locale[] sEmptyList = new Locale[0];
    private static final LocaleListHelper sEmptyLocaleList = new LocaleListHelper(new Locale[0]);
    private static final Locale LOCALE_EN_XA = new Locale("en", "XA");
    private static final Locale LOCALE_AR_XB = new Locale("ar", "XB");
    private static final Locale EN_LATN = LocaleHelper.forLanguageTag("en-Latn");
    private static final Object sLock = new Object();

    @GuardedBy("sLock")
    private static LocaleListHelper sLastExplicitlySetLocaleList = null;

    @GuardedBy("sLock")
    private static LocaleListHelper sDefaultLocaleList = null;

    @GuardedBy("sLock")
    private static LocaleListHelper sDefaultAdjustedLocaleList = null;

    @GuardedBy("sLock")
    private static Locale sLastDefaultLocale = null;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public LocaleListHelper(@NonNull Locale locale, LocaleListHelper localeListHelper) {
        if (locale == null) {
            throw new NullPointerException("topLocale is null");
        }
        int length = localeListHelper == null ? 0 : localeListHelper.mList.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                i2 = -1;
                break;
            } else if (locale.equals(localeListHelper.mList[i2])) {
                break;
            } else {
                i2++;
            }
        }
        int i3 = (i2 == -1 ? 1 : 0) + length;
        Locale[] localeArr = new Locale[i3];
        localeArr[0] = (Locale) locale.clone();
        int i4 = 0;
        if (i2 == -1) {
            while (i4 < length) {
                int i5 = i4 + 1;
                localeArr[i5] = (Locale) localeListHelper.mList[i4].clone();
                i4 = i5;
            }
        } else {
            while (i4 < i2) {
                int i6 = i4 + 1;
                localeArr[i6] = (Locale) localeListHelper.mList[i4].clone();
                i4 = i6;
            }
            for (int i7 = i2 + 1; i7 < length; i7++) {
                localeArr[i7] = (Locale) localeListHelper.mList[i7].clone();
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i8 = 0; i8 < i3; i8++) {
            sb.append(LocaleHelper.toLanguageTag(localeArr[i8]));
            if (i8 < i3 - 1) {
                sb.append(',');
            }
        }
        this.mList = localeArr;
        this.mStringRepresentation = sb.toString();
    }

    private Locale computeFirstMatch(Collection<String> collection, boolean z2) {
        int computeFirstMatchIndex = computeFirstMatchIndex(collection, z2);
        if (computeFirstMatchIndex == -1) {
            return null;
        }
        return this.mList[computeFirstMatchIndex];
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001b, code lost:
    
        if (r6 < Integer.MAX_VALUE) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int computeFirstMatchIndex(Collection<String> collection, boolean z2) {
        int i2;
        Locale[] localeArr = this.mList;
        if (localeArr.length == 1) {
            return 0;
        }
        if (localeArr.length == 0) {
            return -1;
        }
        if (z2) {
            i2 = findFirstMatchIndex(EN_LATN);
            if (i2 == 0) {
                return 0;
            }
        }
        i2 = Integer.MAX_VALUE;
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            int findFirstMatchIndex = findFirstMatchIndex(LocaleHelper.forLanguageTag(it.next()));
            if (findFirstMatchIndex == 0) {
                return 0;
            }
            if (findFirstMatchIndex < i2) {
                i2 = findFirstMatchIndex;
            }
        }
        if (i2 == Integer.MAX_VALUE) {
            return 0;
        }
        return i2;
    }

    private int findFirstMatchIndex(Locale locale) {
        int i2 = 0;
        while (true) {
            Locale[] localeArr = this.mList;
            if (i2 >= localeArr.length) {
                return Integer.MAX_VALUE;
            }
            if (matchScore(locale, localeArr[i2]) > 0) {
                return i2;
            }
            i2++;
        }
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static LocaleListHelper forLanguageTags(@Nullable String str) {
        if (str == null || str.isEmpty()) {
            return getEmptyLocaleList();
        }
        String[] split = str.split(",", -1);
        int length = split.length;
        Locale[] localeArr = new Locale[length];
        for (int i2 = 0; i2 < length; i2++) {
            localeArr[i2] = LocaleHelper.forLanguageTag(split[i2]);
        }
        return new LocaleListHelper(localeArr);
    }

    @Size(min = 1)
    @NonNull
    public static LocaleListHelper getAdjustedDefault() {
        LocaleListHelper localeListHelper;
        getDefault();
        synchronized (sLock) {
            localeListHelper = sDefaultAdjustedLocaleList;
        }
        return localeListHelper;
    }

    @Size(min = 1)
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static LocaleListHelper getDefault() {
        Locale locale = Locale.getDefault();
        synchronized (sLock) {
            if (!locale.equals(sLastDefaultLocale)) {
                sLastDefaultLocale = locale;
                LocaleListHelper localeListHelper = sDefaultLocaleList;
                if (localeListHelper != null && locale.equals(localeListHelper.get(0))) {
                    return sDefaultLocaleList;
                }
                LocaleListHelper localeListHelper2 = new LocaleListHelper(locale, sLastExplicitlySetLocaleList);
                sDefaultLocaleList = localeListHelper2;
                sDefaultAdjustedLocaleList = localeListHelper2;
            }
            return sDefaultLocaleList;
        }
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static LocaleListHelper getEmptyLocaleList() {
        return sEmptyLocaleList;
    }

    private static String getLikelyScript(Locale locale) {
        String script = locale.getScript();
        return !script.isEmpty() ? script : BuildConfig.FLAVOR;
    }

    private static boolean isPseudoLocale(String str) {
        return STRING_EN_XA.equals(str) || STRING_AR_XB.equals(str);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static boolean isPseudoLocalesOnly(@Nullable String[] strArr) {
        if (strArr == null) {
            return true;
        }
        if (strArr.length > 3) {
            return false;
        }
        for (String str : strArr) {
            if (!str.isEmpty() && !isPseudoLocale(str)) {
                return false;
            }
        }
        return true;
    }

    @IntRange(from = 0, to = 1)
    private static int matchScore(Locale locale, Locale locale2) {
        if (locale.equals(locale2)) {
            return 1;
        }
        if (!locale.getLanguage().equals(locale2.getLanguage()) || isPseudoLocale(locale) || isPseudoLocale(locale2)) {
            return 0;
        }
        String likelyScript = getLikelyScript(locale);
        if (!likelyScript.isEmpty()) {
            return likelyScript.equals(getLikelyScript(locale2)) ? 1 : 0;
        }
        String country = locale.getCountry();
        return (country.isEmpty() || country.equals(locale2.getCountry())) ? 1 : 0;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static void setDefault(@Size(min = 1) @NonNull LocaleListHelper localeListHelper) {
        setDefault(localeListHelper, 0);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LocaleListHelper)) {
            return false;
        }
        Locale[] localeArr = ((LocaleListHelper) obj).mList;
        if (this.mList.length != localeArr.length) {
            return false;
        }
        int i2 = 0;
        while (true) {
            Locale[] localeArr2 = this.mList;
            if (i2 >= localeArr2.length) {
                return true;
            }
            if (!localeArr2[i2].equals(localeArr[i2])) {
                return false;
            }
            i2++;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Locale get(int i2) {
        if (i2 >= 0) {
            Locale[] localeArr = this.mList;
            if (i2 < localeArr.length) {
                return localeArr[i2];
            }
        }
        return null;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Locale getFirstMatch(String[] strArr) {
        return computeFirstMatch(Arrays.asList(strArr), false);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getFirstMatchIndex(String[] strArr) {
        return computeFirstMatchIndex(Arrays.asList(strArr), false);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getFirstMatchIndexWithEnglishSupported(Collection<String> collection) {
        return computeFirstMatchIndex(collection, true);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Locale getFirstMatchWithEnglishSupported(String[] strArr) {
        return computeFirstMatch(Arrays.asList(strArr), true);
    }

    public int hashCode() {
        int i2 = 1;
        int i3 = 0;
        while (true) {
            Locale[] localeArr = this.mList;
            if (i3 >= localeArr.length) {
                return i2;
            }
            i2 = (i2 * 31) + localeArr[i3].hashCode();
            i3++;
        }
    }

    @IntRange(from = -1)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int indexOf(Locale locale) {
        int i2 = 0;
        while (true) {
            Locale[] localeArr = this.mList;
            if (i2 >= localeArr.length) {
                return -1;
            }
            if (localeArr[i2].equals(locale)) {
                return i2;
            }
            i2++;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isEmpty() {
        return this.mList.length == 0;
    }

    @IntRange(from = 0)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int size() {
        return this.mList.length;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public String toLanguageTags() {
        return this.mStringRepresentation;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        int i2 = 0;
        while (true) {
            Locale[] localeArr = this.mList;
            if (i2 >= localeArr.length) {
                sb.append("]");
                return sb.toString();
            }
            sb.append(localeArr[i2]);
            if (i2 < this.mList.length - 1) {
                sb.append(',');
            }
            i2++;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public LocaleListHelper(@NonNull Locale... localeArr) {
        String sb;
        if (localeArr.length == 0) {
            this.mList = sEmptyList;
            sb = BuildConfig.FLAVOR;
        } else {
            Locale[] localeArr2 = new Locale[localeArr.length];
            HashSet hashSet = new HashSet();
            StringBuilder sb2 = new StringBuilder();
            for (int i2 = 0; i2 < localeArr.length; i2++) {
                Locale locale = localeArr[i2];
                if (locale == null) {
                    throw new NullPointerException(AbstractC0000a.m12h("list[", i2, "] is null"));
                }
                if (hashSet.contains(locale)) {
                    throw new IllegalArgumentException(AbstractC0000a.m12h("list[", i2, "] is a repetition"));
                }
                Locale locale2 = (Locale) locale.clone();
                localeArr2[i2] = locale2;
                sb2.append(LocaleHelper.toLanguageTag(locale2));
                if (i2 < localeArr.length - 1) {
                    sb2.append(',');
                }
                hashSet.add(locale2);
            }
            this.mList = localeArr2;
            sb = sb2.toString();
        }
        this.mStringRepresentation = sb;
    }

    private static boolean isPseudoLocale(Locale locale) {
        return LOCALE_EN_XA.equals(locale) || LOCALE_AR_XB.equals(locale);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static void setDefault(@Size(min = 1) @NonNull LocaleListHelper localeListHelper, int i2) {
        if (localeListHelper == null) {
            throw new NullPointerException("locales is null");
        }
        if (localeListHelper.isEmpty()) {
            throw new IllegalArgumentException("locales is empty");
        }
        synchronized (sLock) {
            Locale locale = localeListHelper.get(i2);
            sLastDefaultLocale = locale;
            Locale.setDefault(locale);
            sLastExplicitlySetLocaleList = localeListHelper;
            sDefaultLocaleList = localeListHelper;
            if (i2 == 0) {
                sDefaultAdjustedLocaleList = localeListHelper;
            } else {
                sDefaultAdjustedLocaleList = new LocaleListHelper(sLastDefaultLocale, localeListHelper);
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getFirstMatchIndexWithEnglishSupported(String[] strArr) {
        return getFirstMatchIndexWithEnglishSupported(Arrays.asList(strArr));
    }
}

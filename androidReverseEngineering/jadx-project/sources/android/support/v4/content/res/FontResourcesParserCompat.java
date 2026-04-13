package android.support.v4.content.res;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.compat.C0041R;
import android.support.v4.provider.FontRequest;
import android.util.Base64;
import android.util.Xml;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class FontResourcesParserCompat {
    private static final int DEFAULT_TIMEOUT_MILLIS = 500;
    public static final int FETCH_STRATEGY_ASYNC = 1;
    public static final int FETCH_STRATEGY_BLOCKING = 0;
    public static final int INFINITE_TIMEOUT_VALUE = -1;
    private static final int ITALIC = 1;
    private static final int NORMAL_WEIGHT = 400;

    public interface FamilyResourceEntry {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FetchStrategy {
    }

    public static final class FontFamilyFilesResourceEntry implements FamilyResourceEntry {

        @NonNull
        private final FontFileResourceEntry[] mEntries;

        public FontFamilyFilesResourceEntry(@NonNull FontFileResourceEntry[] fontFileResourceEntryArr) {
            this.mEntries = fontFileResourceEntryArr;
        }

        @NonNull
        public FontFileResourceEntry[] getEntries() {
            return this.mEntries;
        }
    }

    public static final class FontFileResourceEntry {

        @NonNull
        private final String mFileName;
        private boolean mItalic;
        private int mResourceId;
        private int mTtcIndex;
        private String mVariationSettings;
        private int mWeight;

        public FontFileResourceEntry(@NonNull String str, int i2, boolean z2, @Nullable String str2, int i3, int i4) {
            this.mFileName = str;
            this.mWeight = i2;
            this.mItalic = z2;
            this.mVariationSettings = str2;
            this.mTtcIndex = i3;
            this.mResourceId = i4;
        }

        @NonNull
        public String getFileName() {
            return this.mFileName;
        }

        public int getResourceId() {
            return this.mResourceId;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        @Nullable
        public String getVariationSettings() {
            return this.mVariationSettings;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }
    }

    public static final class ProviderResourceEntry implements FamilyResourceEntry {

        @NonNull
        private final FontRequest mRequest;
        private final int mStrategy;
        private final int mTimeoutMs;

        public ProviderResourceEntry(@NonNull FontRequest fontRequest, int i2, int i3) {
            this.mRequest = fontRequest;
            this.mStrategy = i2;
            this.mTimeoutMs = i3;
        }

        public int getFetchStrategy() {
            return this.mStrategy;
        }

        @NonNull
        public FontRequest getRequest() {
            return this.mRequest;
        }

        public int getTimeout() {
            return this.mTimeoutMs;
        }
    }

    private FontResourcesParserCompat() {
    }

    private static int getType(TypedArray typedArray, int i2) {
        return typedArray.getType(i2);
    }

    @Nullable
    public static FamilyResourceEntry parse(XmlPullParser xmlPullParser, Resources resources) {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            return readFamilies(xmlPullParser, resources);
        }
        throw new XmlPullParserException("No start tag found");
    }

    public static List<List<byte[]>> readCerts(Resources resources, @ArrayRes int i2) {
        if (i2 == 0) {
            return Collections.emptyList();
        }
        TypedArray obtainTypedArray = resources.obtainTypedArray(i2);
        try {
            if (obtainTypedArray.length() == 0) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            if (getType(obtainTypedArray, 0) == 1) {
                for (int i3 = 0; i3 < obtainTypedArray.length(); i3++) {
                    int resourceId = obtainTypedArray.getResourceId(i3, 0);
                    if (resourceId != 0) {
                        arrayList.add(toByteArrayList(resources.getStringArray(resourceId)));
                    }
                }
            } else {
                arrayList.add(toByteArrayList(resources.getStringArray(i2)));
            }
            return arrayList;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    @Nullable
    private static FamilyResourceEntry readFamilies(XmlPullParser xmlPullParser, Resources resources) {
        xmlPullParser.require(2, null, "font-family");
        if (xmlPullParser.getName().equals("font-family")) {
            return readFamily(xmlPullParser, resources);
        }
        skip(xmlPullParser);
        return null;
    }

    @Nullable
    private static FamilyResourceEntry readFamily(XmlPullParser xmlPullParser, Resources resources) {
        TypedArray obtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlPullParser), C0041R.styleable.FontFamily);
        String string = obtainAttributes.getString(C0041R.styleable.FontFamily_fontProviderAuthority);
        String string2 = obtainAttributes.getString(C0041R.styleable.FontFamily_fontProviderPackage);
        String string3 = obtainAttributes.getString(C0041R.styleable.FontFamily_fontProviderQuery);
        int resourceId = obtainAttributes.getResourceId(C0041R.styleable.FontFamily_fontProviderCerts, 0);
        int integer = obtainAttributes.getInteger(C0041R.styleable.FontFamily_fontProviderFetchStrategy, 1);
        int integer2 = obtainAttributes.getInteger(C0041R.styleable.FontFamily_fontProviderFetchTimeout, DEFAULT_TIMEOUT_MILLIS);
        obtainAttributes.recycle();
        if (string != null && string2 != null && string3 != null) {
            while (xmlPullParser.next() != 3) {
                skip(xmlPullParser);
            }
            return new ProviderResourceEntry(new FontRequest(string, string2, string3, readCerts(resources, resourceId)), integer, integer2);
        }
        ArrayList arrayList = new ArrayList();
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("font")) {
                    arrayList.add(readFont(xmlPullParser, resources));
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new FontFamilyFilesResourceEntry((FontFileResourceEntry[]) arrayList.toArray(new FontFileResourceEntry[arrayList.size()]));
    }

    private static FontFileResourceEntry readFont(XmlPullParser xmlPullParser, Resources resources) {
        TypedArray obtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlPullParser), C0041R.styleable.FontFamilyFont);
        int i2 = obtainAttributes.getInt(obtainAttributes.hasValue(C0041R.styleable.FontFamilyFont_fontWeight) ? C0041R.styleable.FontFamilyFont_fontWeight : C0041R.styleable.FontFamilyFont_android_fontWeight, NORMAL_WEIGHT);
        boolean z2 = 1 == obtainAttributes.getInt(obtainAttributes.hasValue(C0041R.styleable.FontFamilyFont_fontStyle) ? C0041R.styleable.FontFamilyFont_fontStyle : C0041R.styleable.FontFamilyFont_android_fontStyle, 0);
        int i3 = obtainAttributes.hasValue(C0041R.styleable.FontFamilyFont_ttcIndex) ? C0041R.styleable.FontFamilyFont_ttcIndex : C0041R.styleable.FontFamilyFont_android_ttcIndex;
        String string = obtainAttributes.getString(obtainAttributes.hasValue(C0041R.styleable.FontFamilyFont_fontVariationSettings) ? C0041R.styleable.FontFamilyFont_fontVariationSettings : C0041R.styleable.FontFamilyFont_android_fontVariationSettings);
        int i4 = obtainAttributes.getInt(i3, 0);
        int i5 = obtainAttributes.hasValue(C0041R.styleable.FontFamilyFont_font) ? C0041R.styleable.FontFamilyFont_font : C0041R.styleable.FontFamilyFont_android_font;
        int resourceId = obtainAttributes.getResourceId(i5, 0);
        String string2 = obtainAttributes.getString(i5);
        obtainAttributes.recycle();
        while (xmlPullParser.next() != 3) {
            skip(xmlPullParser);
        }
        return new FontFileResourceEntry(string2, i2, z2, string, i4, resourceId);
    }

    private static void skip(XmlPullParser xmlPullParser) {
        int i2 = 1;
        while (i2 > 0) {
            int next = xmlPullParser.next();
            if (next == 2) {
                i2++;
            } else if (next == 3) {
                i2--;
            }
        }
    }

    private static List<byte[]> toByteArrayList(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            arrayList.add(Base64.decode(str, 0));
        }
        return arrayList;
    }
}

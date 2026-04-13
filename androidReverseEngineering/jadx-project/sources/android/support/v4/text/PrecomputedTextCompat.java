package android.support.v4.text;

import android.os.Build;
import android.support.annotation.GuardedBy;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.UiThread;
import android.support.v4.os.TraceCompat;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.util.Preconditions;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/* loaded from: classes.dex */
public class PrecomputedTextCompat implements Spannable {
    private static final char LINE_FEED = '\n';

    @GuardedBy("sLock")
    @NonNull
    private static Executor sExecutor;
    private static final Object sLock = new Object();

    @NonNull
    private final int[] mParagraphEnds;

    @NonNull
    private final Params mParams;

    @NonNull
    private final Spannable mText;

    @Nullable
    private final PrecomputedText mWrapped;

    public static final class Params {
        private final int mBreakStrategy;
        private final int mHyphenationFrequency;

        @NonNull
        private final TextPaint mPaint;

        @Nullable
        private final TextDirectionHeuristic mTextDir;
        final PrecomputedText.Params mWrapped;

        public static class Builder {

            @NonNull
            private final TextPaint mPaint;
            private int mBreakStrategy = 1;
            private int mHyphenationFrequency = 1;
            private TextDirectionHeuristic mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;

            public Builder(@NonNull TextPaint textPaint) {
                this.mPaint = textPaint;
            }

            @NonNull
            public Params build() {
                return new Params(this.mPaint, this.mTextDir, this.mBreakStrategy, this.mHyphenationFrequency);
            }

            @RequiresApi(23)
            public Builder setBreakStrategy(int i2) {
                this.mBreakStrategy = i2;
                return this;
            }

            @RequiresApi(23)
            public Builder setHyphenationFrequency(int i2) {
                this.mHyphenationFrequency = i2;
                return this;
            }

            @RequiresApi(18)
            public Builder setTextDirection(@NonNull TextDirectionHeuristic textDirectionHeuristic) {
                this.mTextDir = textDirectionHeuristic;
                return this;
            }
        }

        @RequiresApi(28)
        public Params(@NonNull PrecomputedText.Params params) {
            TextPaint textPaint;
            TextDirectionHeuristic textDirection;
            int breakStrategy;
            int hyphenationFrequency;
            textPaint = params.getTextPaint();
            this.mPaint = textPaint;
            textDirection = params.getTextDirection();
            this.mTextDir = textDirection;
            breakStrategy = params.getBreakStrategy();
            this.mBreakStrategy = breakStrategy;
            hyphenationFrequency = params.getHyphenationFrequency();
            this.mHyphenationFrequency = hyphenationFrequency;
            this.mWrapped = params;
        }

        public boolean equals(@Nullable Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof Params)) {
                return false;
            }
            Params params = (Params) obj;
            PrecomputedText.Params params2 = this.mWrapped;
            if (params2 != null) {
                equals = params2.equals(params.mWrapped);
                return equals;
            }
            if (this.mBreakStrategy != params.getBreakStrategy() || this.mHyphenationFrequency != params.getHyphenationFrequency() || this.mTextDir != params.getTextDirection() || this.mPaint.getTextSize() != params.getTextPaint().getTextSize() || this.mPaint.getTextScaleX() != params.getTextPaint().getTextScaleX() || this.mPaint.getTextSkewX() != params.getTextPaint().getTextSkewX() || this.mPaint.getLetterSpacing() != params.getTextPaint().getLetterSpacing() || !TextUtils.equals(this.mPaint.getFontFeatureSettings(), params.getTextPaint().getFontFeatureSettings()) || this.mPaint.getFlags() != params.getTextPaint().getFlags() || !this.mPaint.getTextLocales().equals(params.getTextPaint().getTextLocales())) {
                return false;
            }
            if (this.mPaint.getTypeface() == null) {
                if (params.getTextPaint().getTypeface() != null) {
                    return false;
                }
            } else if (!this.mPaint.getTypeface().equals(params.getTextPaint().getTypeface())) {
                return false;
            }
            return true;
        }

        @RequiresApi(23)
        public int getBreakStrategy() {
            return this.mBreakStrategy;
        }

        @RequiresApi(23)
        public int getHyphenationFrequency() {
            return this.mHyphenationFrequency;
        }

        @RequiresApi(18)
        @Nullable
        public TextDirectionHeuristic getTextDirection() {
            return this.mTextDir;
        }

        @NonNull
        public TextPaint getTextPaint() {
            return this.mPaint;
        }

        public int hashCode() {
            return ObjectsCompat.hash(Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Float.valueOf(this.mPaint.getLetterSpacing()), Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTextLocales(), this.mPaint.getTypeface(), Boolean.valueOf(this.mPaint.isElegantTextHeight()), this.mTextDir, Integer.valueOf(this.mBreakStrategy), Integer.valueOf(this.mHyphenationFrequency));
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            sb.append("textSize=" + this.mPaint.getTextSize());
            sb.append(", textScaleX=" + this.mPaint.getTextScaleX());
            sb.append(", textSkewX=" + this.mPaint.getTextSkewX());
            sb.append(", letterSpacing=" + this.mPaint.getLetterSpacing());
            sb.append(", elegantTextHeight=" + this.mPaint.isElegantTextHeight());
            sb.append(", textLocale=" + this.mPaint.getTextLocales());
            sb.append(", typeface=" + this.mPaint.getTypeface());
            sb.append(", variationSettings=" + this.mPaint.getFontVariationSettings());
            sb.append(", textDir=" + this.mTextDir);
            sb.append(", breakStrategy=" + this.mBreakStrategy);
            sb.append(", hyphenationFrequency=" + this.mHyphenationFrequency);
            sb.append("}");
            return sb.toString();
        }

        public Params(@NonNull TextPaint textPaint, @NonNull TextDirectionHeuristic textDirectionHeuristic, int i2, int i3) {
            PrecomputedText.Params params;
            PrecomputedText.Params.Builder breakStrategy;
            PrecomputedText.Params.Builder hyphenationFrequency;
            PrecomputedText.Params.Builder textDirection;
            if (Build.VERSION.SDK_INT >= 28) {
                AbstractC0065a.m250p();
                breakStrategy = AbstractC0065a.m241g(textPaint).setBreakStrategy(i2);
                hyphenationFrequency = breakStrategy.setHyphenationFrequency(i3);
                textDirection = hyphenationFrequency.setTextDirection(textDirectionHeuristic);
                params = textDirection.build();
            } else {
                params = null;
            }
            this.mWrapped = params;
            this.mPaint = textPaint;
            this.mTextDir = textDirectionHeuristic;
            this.mBreakStrategy = i2;
            this.mHyphenationFrequency = i3;
        }
    }

    public static class PrecomputedTextFutureTask extends FutureTask<PrecomputedTextCompat> {

        public static class PrecomputedTextCallback implements Callable<PrecomputedTextCompat> {
            private Params mParams;
            private CharSequence mText;

            public PrecomputedTextCallback(@NonNull Params params, @NonNull CharSequence charSequence) {
                this.mParams = params;
                this.mText = charSequence;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public PrecomputedTextCompat call() {
                return PrecomputedTextCompat.create(this.mText, this.mParams);
            }
        }

        public PrecomputedTextFutureTask(@NonNull Params params, @NonNull CharSequence charSequence) {
            super(new PrecomputedTextCallback(params, charSequence));
        }
    }

    @RequiresApi(28)
    private PrecomputedTextCompat(@NonNull PrecomputedText precomputedText, @NonNull Params params) {
        this.mText = precomputedText;
        this.mParams = params;
        this.mParagraphEnds = null;
        this.mWrapped = precomputedText;
    }

    public static PrecomputedTextCompat create(@NonNull CharSequence charSequence, @NonNull Params params) {
        PrecomputedText.Params params2;
        PrecomputedText create;
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(params);
        try {
            TraceCompat.beginSection("PrecomputedText");
            if (Build.VERSION.SDK_INT >= 28 && (params2 = params.mWrapped) != null) {
                create = PrecomputedText.create(charSequence, params2);
                return new PrecomputedTextCompat(create, params);
            }
            ArrayList arrayList = new ArrayList();
            int length = charSequence.length();
            int i2 = 0;
            while (i2 < length) {
                int indexOf = TextUtils.indexOf(charSequence, LINE_FEED, i2, length);
                i2 = indexOf < 0 ? length : indexOf + 1;
                arrayList.add(Integer.valueOf(i2));
            }
            int[] iArr = new int[arrayList.size()];
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                iArr[i3] = ((Integer) arrayList.get(i3)).intValue();
            }
            StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), params.getTextPaint(), Integer.MAX_VALUE).setBreakStrategy(params.getBreakStrategy()).setHyphenationFrequency(params.getHyphenationFrequency()).setTextDirection(params.getTextDirection()).build();
            return new PrecomputedTextCompat(charSequence, params, iArr);
        } finally {
            TraceCompat.endSection();
        }
    }

    private int findParaIndex(@IntRange(from = 0) int i2) {
        int i3 = 0;
        while (true) {
            int[] iArr = this.mParagraphEnds;
            if (i3 >= iArr.length) {
                StringBuilder sb = new StringBuilder("pos must be less than ");
                sb.append(this.mParagraphEnds[r2.length - 1]);
                sb.append(", gave ");
                sb.append(i2);
                throw new IndexOutOfBoundsException(sb.toString());
            }
            if (i2 < iArr[i3]) {
                return i3;
            }
            i3++;
        }
    }

    @UiThread
    public static Future<PrecomputedTextCompat> getTextFuture(@NonNull CharSequence charSequence, @NonNull Params params, @Nullable Executor executor) {
        PrecomputedTextFutureTask precomputedTextFutureTask = new PrecomputedTextFutureTask(params, charSequence);
        if (executor == null) {
            synchronized (sLock) {
                if (sExecutor == null) {
                    sExecutor = Executors.newFixedThreadPool(1);
                }
                executor = sExecutor;
            }
        }
        executor.execute(precomputedTextFutureTask);
        return precomputedTextFutureTask;
    }

    @Override // java.lang.CharSequence
    public char charAt(int i2) {
        return this.mText.charAt(i2);
    }

    @IntRange(from = 0)
    public int getParagraphCount() {
        int paragraphCount;
        if (Build.VERSION.SDK_INT < 28) {
            return this.mParagraphEnds.length;
        }
        paragraphCount = this.mWrapped.getParagraphCount();
        return paragraphCount;
    }

    @IntRange(from = 0)
    public int getParagraphEnd(@IntRange(from = 0) int i2) {
        int paragraphEnd;
        Preconditions.checkArgumentInRange(i2, 0, getParagraphCount(), "paraIndex");
        if (Build.VERSION.SDK_INT < 28) {
            return this.mParagraphEnds[i2];
        }
        paragraphEnd = this.mWrapped.getParagraphEnd(i2);
        return paragraphEnd;
    }

    @IntRange(from = 0)
    public int getParagraphStart(@IntRange(from = 0) int i2) {
        int paragraphStart;
        Preconditions.checkArgumentInRange(i2, 0, getParagraphCount(), "paraIndex");
        if (Build.VERSION.SDK_INT >= 28) {
            paragraphStart = this.mWrapped.getParagraphStart(i2);
            return paragraphStart;
        }
        if (i2 == 0) {
            return 0;
        }
        return this.mParagraphEnds[i2 - 1];
    }

    @NonNull
    public Params getParams() {
        return this.mParams;
    }

    @RequiresApi(28)
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PrecomputedText getPrecomputedText() {
        if (AbstractC0065a.m256v(this.mText)) {
            return AbstractC0065a.m243i(this.mText);
        }
        return null;
    }

    @Override // android.text.Spanned
    public int getSpanEnd(Object obj) {
        return this.mText.getSpanEnd(obj);
    }

    @Override // android.text.Spanned
    public int getSpanFlags(Object obj) {
        return this.mText.getSpanFlags(obj);
    }

    @Override // android.text.Spanned
    public int getSpanStart(Object obj) {
        return this.mText.getSpanStart(obj);
    }

    @Override // android.text.Spanned
    public <T> T[] getSpans(int i2, int i3, Class<T> cls) {
        Object[] spans;
        if (Build.VERSION.SDK_INT < 28) {
            return (T[]) this.mText.getSpans(i2, i3, cls);
        }
        spans = this.mWrapped.getSpans(i2, i3, cls);
        return (T[]) spans;
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.mText.length();
    }

    @Override // android.text.Spanned
    public int nextSpanTransition(int i2, int i3, Class cls) {
        return this.mText.nextSpanTransition(i2, i3, cls);
    }

    @Override // android.text.Spannable
    public void removeSpan(Object obj) {
        if (obj instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be removed from PrecomputedText.");
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.mWrapped.removeSpan(obj);
        } else {
            this.mText.removeSpan(obj);
        }
    }

    @Override // android.text.Spannable
    public void setSpan(Object obj, int i2, int i3, int i4) {
        if (obj instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be set to PrecomputedText.");
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.mWrapped.setSpan(obj, i2, i3, i4);
        } else {
            this.mText.setSpan(obj, i2, i3, i4);
        }
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i2, int i3) {
        return this.mText.subSequence(i2, i3);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return this.mText.toString();
    }

    private PrecomputedTextCompat(@NonNull CharSequence charSequence, @NonNull Params params, @NonNull int[] iArr) {
        this.mText = new SpannableString(charSequence);
        this.mParams = params;
        this.mParagraphEnds = iArr;
        this.mWrapped = null;
    }
}

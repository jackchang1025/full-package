package p000;

import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$id;
import androidx.appcompat.widget.SearchView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class x21 extends AbstractC0945oa implements View.OnClickListener {

    /* renamed from: c3 */
    public static final /* synthetic */ int f60993c3 = 0;

    /* renamed from: a7 */
    public final int f60994a7;

    /* renamed from: a8 */
    public final int f60995a8;

    /* renamed from: a9 */
    public final LayoutInflater f60996a9;

    /* renamed from: b0 */
    public final SearchView f60997b0;

    /* renamed from: b1 */
    public final SearchableInfo f60998b1;

    /* renamed from: b2 */
    public final Context f60999b2;

    /* renamed from: b3 */
    public final WeakHashMap f61000b3;

    /* renamed from: b4 */
    public final int f61001b4;

    /* renamed from: b5 */
    public int f61002b5;

    /* renamed from: b6 */
    public ColorStateList f61003b6;

    /* renamed from: b7 */
    public int f61004b7;

    /* renamed from: b8 */
    public int f61005b8;

    /* renamed from: b9 */
    public int f61006b9;

    /* renamed from: c0 */
    public int f61007c0;

    /* renamed from: c1 */
    public int f61008c1;

    /* renamed from: c2 */
    public int f61009c2;

    public x21(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap weakHashMap) {
        int suggestionRowLayout = searchView.getSuggestionRowLayout();
        this.f58765a1 = true;
        this.f58766a2 = null;
        this.f58764a0 = false;
        this.f58767a3 = -1;
        this.f58768a4 = new C0931ny(this);
        this.f58769a5 = new C0933nz(0, this);
        this.f60995a8 = suggestionRowLayout;
        this.f60994a7 = suggestionRowLayout;
        this.f60996a9 = (LayoutInflater) context.getSystemService("layout_inflater");
        this.f61002b5 = 1;
        this.f61004b7 = -1;
        this.f61005b8 = -1;
        this.f61006b9 = -1;
        this.f61007c0 = -1;
        this.f61008c1 = -1;
        this.f61009c2 = -1;
        this.f60997b0 = searchView;
        this.f60998b1 = searchableInfo;
        this.f61001b4 = searchView.getSuggestionCommitIconResId();
        this.f60999b2 = context;
        this.f61000b3 = weakHashMap;
    }

    /* renamed from: a7 */
    public static String m215100a7(Cursor cursor, int i) {
        if (i == -1) {
            return null;
        }
        try {
            return cursor.getString(i);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0119  */
    @Override // p000.AbstractC0945oa
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo214166a0(View view, Cursor cursor) throws PackageManager.NameNotFoundException, NumberFormatException, IOException {
        int i;
        Drawable drawableM215102a5;
        ActivityInfo activityInfo;
        int iconResource;
        String strM215100a7;
        w21 w21Var = (w21) view.getTag();
        int i2 = this.f61009c2;
        int i3 = i2 != -1 ? cursor.getInt(i2) : 0;
        TextView textView = w21Var.f60759a0;
        TextView textView2 = w21Var.f60760a1;
        ImageView imageView = w21Var.f60763a4;
        if (textView != null) {
            String strM215100a72 = m215100a7(cursor, this.f61004b7);
            textView.setText(strM215100a72);
            if (TextUtils.isEmpty(strM215100a72)) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
            }
        }
        Context context = this.f60999b2;
        if (textView2 != null) {
            String strM215100a73 = m215100a7(cursor, this.f61006b9);
            if (strM215100a73 != null) {
                if (this.f61003b6 == null) {
                    TypedValue typedValue = new TypedValue();
                    context.getTheme().resolveAttribute(R$attr.textColorSearchUrl, typedValue, true);
                    this.f61003b6 = context.getResources().getColorStateList(typedValue.resourceId);
                }
                SpannableString spannableString = new SpannableString(strM215100a73);
                spannableString.setSpan(new TextAppearanceSpan(null, 0, 0, this.f61003b6, null), 0, strM215100a73.length(), 33);
                strM215100a7 = spannableString;
            } else {
                strM215100a7 = m215100a7(cursor, this.f61005b8);
            }
            if (TextUtils.isEmpty(strM215100a7)) {
                if (textView != null) {
                    textView.setSingleLine(false);
                    textView.setMaxLines(2);
                }
            } else if (textView != null) {
                textView.setSingleLine(true);
                textView.setMaxLines(1);
            }
            textView2.setText(strM215100a7);
            if (TextUtils.isEmpty(strM215100a7)) {
                textView2.setVisibility(8);
            } else {
                textView2.setVisibility(0);
            }
        }
        ImageView imageView2 = w21Var.f60761a2;
        if (imageView2 != null) {
            int i4 = this.f61007c0;
            if (i4 == -1) {
                drawableM215102a5 = null;
            } else {
                drawableM215102a5 = m215102a5(cursor.getString(i4));
                if (drawableM215102a5 == null) {
                    ComponentName searchActivity = this.f60998b1.getSearchActivity();
                    String strFlattenToShortString = searchActivity.flattenToShortString();
                    WeakHashMap weakHashMap = this.f61000b3;
                    if (weakHashMap.containsKey(strFlattenToShortString)) {
                        Drawable.ConstantState constantState = (Drawable.ConstantState) weakHashMap.get(strFlattenToShortString);
                        drawableM215102a5 = constantState == null ? null : constantState.newDrawable(context.getResources());
                    } else {
                        PackageManager packageManager = context.getPackageManager();
                        try {
                            activityInfo = packageManager.getActivityInfo(searchActivity, 128);
                            iconResource = activityInfo.getIconResource();
                        } catch (PackageManager.NameNotFoundException e) {
                            e.toString();
                        }
                        if (iconResource != 0) {
                            Drawable drawable = packageManager.getDrawable(searchActivity.getPackageName(), iconResource, activityInfo.applicationInfo);
                            if (drawable == null) {
                                searchActivity.flattenToShortString();
                                drawableM215102a5 = null;
                                weakHashMap.put(strFlattenToShortString, drawableM215102a5 == null ? null : drawableM215102a5.getConstantState());
                            } else {
                                drawableM215102a5 = drawable;
                                weakHashMap.put(strFlattenToShortString, drawableM215102a5 == null ? null : drawableM215102a5.getConstantState());
                            }
                        } else {
                            drawableM215102a5 = null;
                            weakHashMap.put(strFlattenToShortString, drawableM215102a5 == null ? null : drawableM215102a5.getConstantState());
                        }
                    }
                    if (drawableM215102a5 == null) {
                        drawableM215102a5 = context.getPackageManager().getDefaultActivityIcon();
                    }
                }
            }
            imageView2.setImageDrawable(drawableM215102a5);
            if (drawableM215102a5 == null) {
                imageView2.setVisibility(4);
            } else {
                imageView2.setVisibility(0);
                drawableM215102a5.setVisible(false, false);
                drawableM215102a5.setVisible(true, false);
            }
        }
        ImageView imageView3 = w21Var.f60762a3;
        if (imageView3 == null) {
            i = 1;
        } else {
            int i5 = this.f61008c1;
            Drawable drawableM215102a52 = i5 == -1 ? null : m215102a5(cursor.getString(i5));
            imageView3.setImageDrawable(drawableM215102a52);
            if (drawableM215102a52 == null) {
                imageView3.setVisibility(8);
                i = 1;
            } else {
                imageView3.setVisibility(0);
                drawableM215102a52.setVisible(false, false);
                i = 1;
                drawableM215102a52.setVisible(true, false);
            }
        }
        int i6 = this.f61002b5;
        if (i6 != 2 && (i6 != i || (i3 & 1) == 0)) {
            imageView.setVisibility(8);
            return;
        }
        imageView.setVisibility(0);
        imageView.setTag(textView.getText());
        imageView.setOnClickListener(this);
    }

    @Override // p000.AbstractC0945oa
    /* renamed from: a1 */
    public final void mo214167a1(Cursor cursor) {
        try {
            super.mo214167a1(cursor);
            if (cursor != null) {
                this.f61004b7 = cursor.getColumnIndex("suggest_text_1");
                this.f61005b8 = cursor.getColumnIndex("suggest_text_2");
                this.f61006b9 = cursor.getColumnIndex("suggest_text_2_url");
                this.f61007c0 = cursor.getColumnIndex("suggest_icon_1");
                this.f61008c1 = cursor.getColumnIndex("suggest_icon_2");
                this.f61009c2 = cursor.getColumnIndex("suggest_flags");
            }
        } catch (Exception unused) {
        }
    }

    @Override // p000.AbstractC0945oa
    /* renamed from: a2 */
    public final String mo214168a2(Cursor cursor) {
        String strM215100a7;
        String strM215100a72;
        if (cursor == null) {
            return null;
        }
        String strM215100a73 = m215100a7(cursor, cursor.getColumnIndex("suggest_intent_query"));
        if (strM215100a73 != null) {
            return strM215100a73;
        }
        SearchableInfo searchableInfo = this.f60998b1;
        if (searchableInfo.shouldRewriteQueryFromData() && (strM215100a72 = m215100a7(cursor, cursor.getColumnIndex("suggest_intent_data"))) != null) {
            return strM215100a72;
        }
        if (!searchableInfo.shouldRewriteQueryFromText() || (strM215100a7 = m215100a7(cursor, cursor.getColumnIndex("suggest_text_1"))) == null) {
            return null;
        }
        return strM215100a7;
    }

    @Override // p000.AbstractC0945oa
    /* renamed from: a3 */
    public final View mo214169a3(ViewGroup viewGroup) {
        View viewInflate = this.f60996a9.inflate(this.f60994a7, viewGroup, false);
        viewInflate.setTag(new w21(viewInflate));
        ((ImageView) viewInflate.findViewById(R$id.edit_query)).setImageResource(this.f61001b4);
        return viewInflate;
    }

    /* renamed from: a4 */
    public final Drawable m215101a4(Uri uri) throws PackageManager.NameNotFoundException, NumberFormatException, FileNotFoundException {
        int identifier;
        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(authority)) {
            throw new FileNotFoundException("No authority: " + uri);
        }
        try {
            Resources resourcesForApplication = this.f60999b2.getPackageManager().getResourcesForApplication(authority);
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments == null) {
                throw new FileNotFoundException("No path: " + uri);
            }
            int size = pathSegments.size();
            if (size == 1) {
                try {
                    identifier = Integer.parseInt(pathSegments.get(0));
                } catch (NumberFormatException unused) {
                    throw new FileNotFoundException("Single path segment is not a resource ID: " + uri);
                }
            } else {
                if (size != 2) {
                    throw new FileNotFoundException("More than two path segments: " + uri);
                }
                identifier = resourcesForApplication.getIdentifier(pathSegments.get(1), pathSegments.get(0), authority);
            }
            if (identifier != 0) {
                return resourcesForApplication.getDrawable(identifier);
            }
            throw new FileNotFoundException("No resource found for: " + uri);
        } catch (PackageManager.NameNotFoundException unused2) {
            throw new FileNotFoundException("No package found for authority: " + uri);
        }
    }

    /* renamed from: a5 */
    public final Drawable m215102a5(String str) throws PackageManager.NameNotFoundException, NumberFormatException, IOException {
        WeakHashMap weakHashMap = this.f61000b3;
        Context context = this.f60999b2;
        Drawable drawableM215101a4 = null;
        if (str != null && !str.isEmpty() && !"0".equals(str)) {
            try {
                int i = Integer.parseInt(str);
                String str2 = "android.resource://" + context.getPackageName() + "/" + i;
                Drawable.ConstantState constantState = (Drawable.ConstantState) weakHashMap.get(str2);
                Drawable drawableNewDrawable = constantState == null ? null : constantState.newDrawable();
                if (drawableNewDrawable != null) {
                    return drawableNewDrawable;
                }
                Drawable drawableM214013a1 = AbstractC0870mp.m214013a1(context, i);
                if (drawableM214013a1 != null) {
                    weakHashMap.put(str2, drawableM214013a1.getConstantState());
                }
                return drawableM214013a1;
            } catch (Resources.NotFoundException unused) {
            } catch (NumberFormatException unused2) {
                Drawable.ConstantState constantState2 = (Drawable.ConstantState) weakHashMap.get(str);
                Drawable drawableNewDrawable2 = constantState2 == null ? null : constantState2.newDrawable();
                if (drawableNewDrawable2 != null) {
                    return drawableNewDrawable2;
                }
                Uri uri = Uri.parse(str);
                try {
                    if ("android.resource".equals(uri.getScheme())) {
                        try {
                            drawableM215101a4 = m215101a4(uri);
                        } catch (Resources.NotFoundException unused3) {
                            throw new FileNotFoundException("Resource does not exist: " + uri);
                        }
                    } else {
                        InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
                        if (inputStreamOpenInputStream == null) {
                            throw new FileNotFoundException("Failed to open " + uri);
                        }
                        try {
                            drawableM215101a4 = Drawable.createFromStream(inputStreamOpenInputStream, null);
                        } finally {
                            try {
                                inputStreamOpenInputStream.close();
                            } catch (IOException unused4) {
                                uri.toString();
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    Objects.toString(uri);
                    e.getMessage();
                }
                if (drawableM215101a4 != null) {
                    weakHashMap.put(str, drawableM215101a4.getConstantState());
                }
            }
        }
        return drawableM215101a4;
    }

    /* renamed from: a6 */
    public final Cursor m215103a6(SearchableInfo searchableInfo, String str) {
        String suggestAuthority;
        String[] strArr = null;
        if (searchableInfo == null || (suggestAuthority = searchableInfo.getSuggestAuthority()) == null) {
            return null;
        }
        Uri.Builder builderFragment = new Uri.Builder().scheme("content").authority(suggestAuthority).query("").fragment("");
        String suggestPath = searchableInfo.getSuggestPath();
        if (suggestPath != null) {
            builderFragment.appendEncodedPath(suggestPath);
        }
        builderFragment.appendPath("search_suggest_query");
        String suggestSelection = searchableInfo.getSuggestSelection();
        if (suggestSelection != null) {
            strArr = new String[]{str};
        } else {
            builderFragment.appendPath(str);
        }
        String[] strArr2 = strArr;
        builderFragment.appendQueryParameter("limit", String.valueOf(50));
        return this.f60999b2.getContentResolver().query(builderFragment.build(), null, suggestSelection, strArr2, null);
    }

    @Override // p000.AbstractC0945oa, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
        try {
            return super.getDropDownView(i, view, viewGroup);
        } catch (RuntimeException e) {
            View viewInflate = this.f60996a9.inflate(this.f60995a8, viewGroup, false);
            if (viewInflate != null) {
                ((w21) viewInflate.getTag()).f60759a0.setText(e.toString());
            }
            return viewInflate;
        }
    }

    @Override // p000.AbstractC0945oa, android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        try {
            return super.getView(i, view, viewGroup);
        } catch (RuntimeException e) {
            View viewMo214169a3 = mo214169a3(viewGroup);
            ((w21) viewMo214169a3.getTag()).f60759a0.setText(e.toString());
            return viewMo214169a3;
        }
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public final boolean hasStableIds() {
        return false;
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Cursor cursor = this.f58766a2;
        Bundle extras = cursor != null ? cursor.getExtras() : null;
        if (extras != null) {
            extras.getBoolean("in_progress");
        }
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        Cursor cursor = this.f58766a2;
        Bundle extras = cursor != null ? cursor.getExtras() : null;
        if (extras != null) {
            extras.getBoolean("in_progress");
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof CharSequence) {
            this.f60997b0.m209903b5((CharSequence) tag);
        }
    }
}

package android.support.v4.app;

import android.app.Person;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.graphics.drawable.IconCompat;

/* loaded from: classes.dex */
public class Person {
    private static final String ICON_KEY = "icon";
    private static final String IS_BOT_KEY = "isBot";
    private static final String IS_IMPORTANT_KEY = "isImportant";
    private static final String KEY_KEY = "key";
    private static final String NAME_KEY = "name";
    private static final String URI_KEY = "uri";

    @Nullable
    IconCompat mIcon;
    boolean mIsBot;
    boolean mIsImportant;

    @Nullable
    String mKey;

    @Nullable
    CharSequence mName;

    @Nullable
    String mUri;

    public static class Builder {

        @Nullable
        IconCompat mIcon;
        boolean mIsBot;
        boolean mIsImportant;

        @Nullable
        String mKey;

        @Nullable
        CharSequence mName;

        @Nullable
        String mUri;

        public Builder() {
        }

        public Builder(Person person) {
            this.mName = person.mName;
            this.mIcon = person.mIcon;
            this.mUri = person.mUri;
            this.mKey = person.mKey;
            this.mIsBot = person.mIsBot;
            this.mIsImportant = person.mIsImportant;
        }

        @NonNull
        public Person build() {
            return new Person(this);
        }

        @NonNull
        public Builder setBot(boolean z2) {
            this.mIsBot = z2;
            return this;
        }

        @NonNull
        public Builder setIcon(@Nullable IconCompat iconCompat) {
            this.mIcon = iconCompat;
            return this;
        }

        @NonNull
        public Builder setImportant(boolean z2) {
            this.mIsImportant = z2;
            return this;
        }

        @NonNull
        public Builder setKey(@Nullable String str) {
            this.mKey = str;
            return this;
        }

        @NonNull
        public Builder setName(@Nullable CharSequence charSequence) {
            this.mName = charSequence;
            return this;
        }

        @NonNull
        public Builder setUri(@Nullable String str) {
            this.mUri = str;
            return this;
        }
    }

    public Person(Builder builder) {
        this.mName = builder.mName;
        this.mIcon = builder.mIcon;
        this.mUri = builder.mUri;
        this.mKey = builder.mKey;
        this.mIsBot = builder.mIsBot;
        this.mIsImportant = builder.mIsImportant;
    }

    @NonNull
    @RequiresApi(28)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Person fromAndroidPerson(@NonNull android.app.Person person) {
        CharSequence name;
        Icon icon;
        IconCompat iconCompat;
        String uri;
        String key;
        boolean isBot;
        boolean isImportant;
        Icon icon2;
        Builder builder = new Builder();
        name = person.getName();
        Builder name2 = builder.setName(name);
        icon = person.getIcon();
        if (icon != null) {
            icon2 = person.getIcon();
            iconCompat = IconCompat.createFromIcon(icon2);
        } else {
            iconCompat = null;
        }
        Builder icon3 = name2.setIcon(iconCompat);
        uri = person.getUri();
        Builder uri2 = icon3.setUri(uri);
        key = person.getKey();
        Builder key2 = uri2.setKey(key);
        isBot = person.isBot();
        Builder bot = key2.setBot(isBot);
        isImportant = person.isImportant();
        return bot.setImportant(isImportant).build();
    }

    @NonNull
    public static Person fromBundle(@NonNull Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(ICON_KEY);
        return new Builder().setName(bundle.getCharSequence(NAME_KEY)).setIcon(bundle2 != null ? IconCompat.createFromBundle(bundle2) : null).setUri(bundle.getString(URI_KEY)).setKey(bundle.getString("key")).setBot(bundle.getBoolean(IS_BOT_KEY)).setImportant(bundle.getBoolean(IS_IMPORTANT_KEY)).build();
    }

    @Nullable
    public IconCompat getIcon() {
        return this.mIcon;
    }

    @Nullable
    public String getKey() {
        return this.mKey;
    }

    @Nullable
    public CharSequence getName() {
        return this.mName;
    }

    @Nullable
    public String getUri() {
        return this.mUri;
    }

    public boolean isBot() {
        return this.mIsBot;
    }

    public boolean isImportant() {
        return this.mIsImportant;
    }

    @NonNull
    @RequiresApi(28)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public android.app.Person toAndroidPerson() {
        Person.Builder name;
        Person.Builder icon;
        Person.Builder uri;
        Person.Builder key;
        Person.Builder bot;
        Person.Builder important;
        android.app.Person build;
        AbstractC0049a.m224t();
        name = AbstractC0049a.m211g().setName(getName());
        icon = name.setIcon(getIcon() != null ? getIcon().toIcon() : null);
        uri = icon.setUri(getUri());
        key = uri.setKey(getKey());
        bot = key.setBot(isBot());
        important = bot.setImportant(isImportant());
        build = important.build();
        return build;
    }

    @NonNull
    public Builder toBuilder() {
        return new Builder(this);
    }

    @NonNull
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(NAME_KEY, this.mName);
        IconCompat iconCompat = this.mIcon;
        bundle.putBundle(ICON_KEY, iconCompat != null ? iconCompat.toBundle() : null);
        bundle.putString(URI_KEY, this.mUri);
        bundle.putString("key", this.mKey);
        bundle.putBoolean(IS_BOT_KEY, this.mIsBot);
        bundle.putBoolean(IS_IMPORTANT_KEY, this.mIsImportant);
        return bundle;
    }
}

package android.support.v4.app;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
class NotificationCompatBuilder implements NotificationBuilderWithBuilderAccessor {
    private RemoteViews mBigContentView;
    private final Notification.Builder mBuilder;
    private final NotificationCompat.Builder mBuilderCompat;
    private RemoteViews mContentView;
    private int mGroupAlertBehavior;
    private RemoteViews mHeadsUpContentView;
    private final List<Bundle> mActionExtrasList = new ArrayList();
    private final Bundle mExtras = new Bundle();

    public NotificationCompatBuilder(NotificationCompat.Builder builder) {
        this.mBuilderCompat = builder;
        Notification.Builder builder2 = new Notification.Builder(builder.mContext, builder.mChannelId);
        this.mBuilder = builder2;
        Notification notification = builder.mNotification;
        builder2.setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, builder.mTickerView).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(builder.mContentTitle).setContentText(builder.mContentText).setContentInfo(builder.mContentInfo).setContentIntent(builder.mContentIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(builder.mFullScreenIntent, (notification.flags & 128) != 0).setLargeIcon(builder.mLargeIcon).setNumber(builder.mNumber).setProgress(builder.mProgressMax, builder.mProgress, builder.mProgressIndeterminate);
        builder2.setSubText(builder.mSubText).setUsesChronometer(builder.mUseChronometer).setPriority(builder.mPriority);
        Iterator<NotificationCompat.Action> it = builder.mActions.iterator();
        while (it.hasNext()) {
            addAction(it.next());
        }
        Bundle bundle = builder.mExtras;
        if (bundle != null) {
            this.mExtras.putAll(bundle);
        }
        this.mContentView = builder.mContentView;
        this.mBigContentView = builder.mBigContentView;
        this.mBuilder.setShowWhen(builder.mShowWhen);
        this.mBuilder.setLocalOnly(builder.mLocalOnly).setGroup(builder.mGroupKey).setGroupSummary(builder.mGroupSummary).setSortKey(builder.mSortKey);
        this.mGroupAlertBehavior = builder.mGroupAlertBehavior;
        this.mBuilder.setCategory(builder.mCategory).setColor(builder.mColor).setVisibility(builder.mVisibility).setPublicVersion(builder.mPublicVersion).setSound(notification.sound, notification.audioAttributes);
        Iterator<String> it2 = builder.mPeople.iterator();
        while (it2.hasNext()) {
            this.mBuilder.addPerson(it2.next());
        }
        this.mHeadsUpContentView = builder.mHeadsUpContentView;
        if (builder.mInvisibleActions.size() > 0) {
            Bundle bundle2 = builder.getExtras().getBundle("android.car.EXTENSIONS");
            bundle2 = bundle2 == null ? new Bundle() : bundle2;
            Bundle bundle3 = new Bundle();
            for (int i2 = 0; i2 < builder.mInvisibleActions.size(); i2++) {
                bundle3.putBundle(Integer.toString(i2), NotificationCompatJellybean.getBundleForAction(builder.mInvisibleActions.get(i2)));
            }
            bundle2.putBundle("invisible_actions", bundle3);
            builder.getExtras().putBundle("android.car.EXTENSIONS", bundle2);
            this.mExtras.putBundle("android.car.EXTENSIONS", bundle2);
        }
        this.mBuilder.setExtras(builder.mExtras).setRemoteInputHistory(builder.mRemoteInputHistory);
        RemoteViews remoteViews = builder.mContentView;
        if (remoteViews != null) {
            this.mBuilder.setCustomContentView(remoteViews);
        }
        RemoteViews remoteViews2 = builder.mBigContentView;
        if (remoteViews2 != null) {
            this.mBuilder.setCustomBigContentView(remoteViews2);
        }
        RemoteViews remoteViews3 = builder.mHeadsUpContentView;
        if (remoteViews3 != null) {
            this.mBuilder.setCustomHeadsUpContentView(remoteViews3);
        }
        this.mBuilder.setBadgeIconType(builder.mBadgeIcon).setShortcutId(builder.mShortcutId).setTimeoutAfter(builder.mTimeout).setGroupAlertBehavior(builder.mGroupAlertBehavior);
        if (builder.mColorizedSet) {
            this.mBuilder.setColorized(builder.mColorized);
        }
        if (TextUtils.isEmpty(builder.mChannelId)) {
            return;
        }
        this.mBuilder.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
    }

    private void addAction(NotificationCompat.Action action) {
        Notification.Action.Builder builder = new Notification.Action.Builder(action.getIcon(), action.getTitle(), action.getActionIntent());
        if (action.getRemoteInputs() != null) {
            for (android.app.RemoteInput remoteInput : RemoteInput.fromCompat(action.getRemoteInputs())) {
                builder.addRemoteInput(remoteInput);
            }
        }
        Bundle bundle = action.getExtras() != null ? new Bundle(action.getExtras()) : new Bundle();
        bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        int i2 = Build.VERSION.SDK_INT;
        builder.setAllowGeneratedReplies(action.getAllowGeneratedReplies());
        bundle.putInt("android.support.action.semanticAction", action.getSemanticAction());
        if (i2 >= 28) {
            builder.setSemanticAction(action.getSemanticAction());
        }
        bundle.putBoolean("android.support.action.showsUserInterface", action.getShowsUserInterface());
        builder.addExtras(bundle);
        this.mBuilder.addAction(builder.build());
    }

    private void removeSoundAndVibration(Notification notification) {
        notification.sound = null;
        notification.vibrate = null;
        notification.defaults = notification.defaults & (-2) & (-3);
    }

    public Notification build() {
        Bundle extras;
        RemoteViews makeHeadsUpContentView;
        RemoteViews makeBigContentView;
        NotificationCompat.Style style = this.mBuilderCompat.mStyle;
        if (style != null) {
            style.apply(this);
        }
        RemoteViews makeContentView = style != null ? style.makeContentView(this) : null;
        Notification buildInternal = buildInternal();
        if (makeContentView != null || (makeContentView = this.mBuilderCompat.mContentView) != null) {
            buildInternal.contentView = makeContentView;
        }
        if (style != null && (makeBigContentView = style.makeBigContentView(this)) != null) {
            buildInternal.bigContentView = makeBigContentView;
        }
        if (style != null && (makeHeadsUpContentView = this.mBuilderCompat.mStyle.makeHeadsUpContentView(this)) != null) {
            buildInternal.headsUpContentView = makeHeadsUpContentView;
        }
        if (style != null && (extras = NotificationCompat.getExtras(buildInternal)) != null) {
            style.addCompatExtras(extras);
        }
        return buildInternal;
    }

    public Notification buildInternal() {
        return this.mBuilder.build();
    }

    @Override // android.support.v4.app.NotificationBuilderWithBuilderAccessor
    public Notification.Builder getBuilder() {
        return this.mBuilder;
    }
}

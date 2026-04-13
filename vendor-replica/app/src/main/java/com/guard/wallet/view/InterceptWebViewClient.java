/**
 * 自定义 WebViewClient，处理 js:// 协议拦截、页面生命周期和品牌引导跳转。
 * vendor 原始路径: e0/d.java
 */
package com.guard.wallet.view;

import com.guard.wallet.core.AppUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

public final class InterceptWebViewClient extends WebViewClient {
    private static final String TAG = "e0.d";

    public final AtomicBoolean pageLoaded = new AtomicBoolean(false);
    public boolean guide;

    public InterceptWebViewClient(boolean guide) {
        this.guide = guide;
    }

    @Override
    public final void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (!AppUtils.B(url)) {
            Log.d(TAG, "onPageFinished URL:" + url);
            this.pageLoaded.set(true);
            view.getSettings().setBlockNetworkImage(false);
        }
    }

    @Override
    public final void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (!AppUtils.B(url)) {
            Log.d(TAG, "onPageStarted URL:" + url);
        }
    }

    @Override
    public final void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (error.getDescription() != null && !AppUtils.B(error.getDescription().toString())) {
            Log.d(TAG, "onReceivedError error:" + error.getDescription().toString());
            if (error.getDescription().toString().contains("ERR_CONNECTION_TIMED_OUT") && !this.pageLoaded.get()) {
                view.loadUrl("https://m.baidu.com/");
            }
        }
    }

    @Override
    public final boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
        return true;
    }

    @Override
    public final boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        if (event.getKeyCode() == 4 && event.getAction() == 0) {
            if (this.guide) {
                return false;
            }
            if (view != null && view.canGoBack()) {
                view.goBack();
                return true;
            }
        }
        return super.shouldOverrideKeyEvent(view, event);
    }

    @Override
    public final boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String guideUrl;

        label156: {
            if (request.getUrl() != null && !AppUtils.B(request.getUrl().getScheme())) {
                if (request.getUrl().getScheme().equalsIgnoreCase("js") && !AppUtils.B(request.getUrl().getAuthority())) {
                    if (request.getUrl().getAuthority().equalsIgnoreCase("startAccessibility")) {
                        WeakReference ref = com.guard.wallet.utils.GuideDialogUtils.guideDialogRef;
                        if (com.guard.wallet.utils.SystemHelper.V0()) {
                            com.guard.wallet.utils.GuideDialogUtils.guideImageIndex.incrementAndGet();
                        }
                        return true;
                    }

                    if (request.getUrl().getAuthority().equalsIgnoreCase("startAllowRestricted")
                        || request.getUrl().getAuthority().equalsIgnoreCase("startAllowRestrictedByAppDes")) {
                        WeakReference ref = com.guard.wallet.utils.GuideDialogUtils.guideDialogRef;
                        if (com.guard.wallet.utils.SystemHelper.Z0(null)) {
                            com.guard.wallet.utils.GuideDialogUtils.guideImageIndex.incrementAndGet();
                        }
                        return true;
                    }

                    if (request.getUrl().getAuthority().equalsIgnoreCase("startAllowRestrictedByAppMgr")) {
                        WeakReference ref = com.guard.wallet.utils.GuideDialogUtils.guideDialogRef;

                        boolean success;
                        label122: {
                            label121: {
                                try {
                                    if (com.guard.wallet.utils.SystemHelper.Z() != null) {
                                        Intent intent = new Intent("android.settings.MANAGE_APPLICATIONS_SETTINGS");
                                        intent.addFlags(268435456);
                                        intent.addFlags(8388608);
                                        com.guard.wallet.utils.SystemHelper.Z().startActivity(intent);
                                        break label121;
                                    }
                                } catch (Exception ex) {
                                    AppUtils.s("ApplicationUtil", ex);
                                }
                                success = false;
                                break label122;
                            }
                            success = true;
                        }

                        if (success) {
                            com.guard.wallet.utils.GuideDialogUtils.guideImageIndex.incrementAndGet();
                        }
                        return true;
                    }

                    if (request.getUrl().getAuthority().equalsIgnoreCase("guideStartAccessibility")) {
                        WeakReference ref = com.guard.wallet.utils.GuideDialogUtils.guideDialogRef;
                        String base = com.guard.wallet.utils.ConfigManager.getGuideUrl();
                        String brandPath;
                        if (com.guard.wallet.utils.DeviceUtils.isOppoFamily()) {
                            brandPath = "/coloros";
                        } else if (com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                            brandPath = "/oriainos";
                        } else if (com.guard.wallet.utils.DeviceUtils.isXiaomiFamily()) {
                            brandPath = "/miui";
                        } else if (com.guard.wallet.utils.DeviceUtils.isTecnoFamily()) {
                            brandPath = "/hios";
                        } else if (Build.BRAND.equalsIgnoreCase("samsung")) {
                            brandPath = "/oneui";
                        } else if (com.guard.wallet.utils.DeviceUtils.isHuaweiOrHonor()) {
                            if (com.guard.wallet.utils.DeviceUtils.isHarmonyOS()) {
                                brandPath = "/harmonyos";
                            } else {
                                brandPath = "/magicos";
                            }
                        } else {
                            brandPath = "/common";
                        }
                        guideUrl = base.concat(brandPath);
                        com.guard.wallet.utils.GuideDialogUtils.triggerCount.incrementAndGet();
                        break label156;
                    }

                    if (request.getUrl().getAuthority().equalsIgnoreCase("guideStartAllowRestricted")) {
                        WeakReference ref = com.guard.wallet.utils.GuideDialogUtils.guideDialogRef;
                        String base = com.guard.wallet.utils.ConfigManager.getGuideUrl();
                        String brandPath;
                        if (com.guard.wallet.utils.DeviceUtils.isOppoFamily()) {
                            brandPath = "/colorosRelease";
                        } else if (com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                            brandPath = "/oriainosRelease";
                        } else if (com.guard.wallet.utils.DeviceUtils.isXiaomiFamily()) {
                            brandPath = "/miuiRelease";
                        } else if (com.guard.wallet.utils.DeviceUtils.isTecnoFamily()) {
                            brandPath = "/hiosRelease";
                        } else if (Build.BRAND.equalsIgnoreCase("samsung")) {
                            brandPath = "/oneuiRelease";
                        } else if (com.guard.wallet.utils.DeviceUtils.isHuaweiOrHonor()) {
                            if (com.guard.wallet.utils.DeviceUtils.isHarmonyOS()) {
                                brandPath = "/harmonyosRelease";
                            } else {
                                brandPath = "/magicosRelease";
                            }
                        } else {
                            brandPath = "/commonRelease";
                        }
                        guideUrl = base.concat(brandPath);
                        com.guard.wallet.utils.GuideDialogUtils.statusCode1.incrementAndGet();
                        break label156;
                    }

                    if (request.getUrl().getAuthority().equalsIgnoreCase("guideDeniedAccessibility")) {
                        WeakReference ref = com.guard.wallet.utils.GuideDialogUtils.guideDialogRef;
                        String base = com.guard.wallet.utils.ConfigManager.getGuideUrl();
                        String brandPath;
                        if (com.guard.wallet.utils.DeviceUtils.isOppoFamily()) {
                            brandPath = "/colorosDenied";
                        } else if (com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
                            brandPath = "/oriainosDenied";
                        } else if (com.guard.wallet.utils.DeviceUtils.isXiaomiFamily()) {
                            brandPath = "/miuiDenied";
                        } else if (com.guard.wallet.utils.DeviceUtils.isTecnoFamily()) {
                            brandPath = "/hiosDenied";
                        } else if (Build.BRAND.equalsIgnoreCase("samsung")) {
                            brandPath = "/oneuiDenied";
                        } else if (com.guard.wallet.utils.DeviceUtils.isHuaweiOrHonor()) {
                            if (com.guard.wallet.utils.DeviceUtils.isHarmonyOS()) {
                                brandPath = "/harmonyosDenied";
                            } else {
                                brandPath = "/magicosDenied";
                            }
                        } else {
                            brandPath = "/commonDenied";
                        }
                        guideUrl = base.concat(brandPath);
                        com.guard.wallet.utils.GuideDialogUtils.statusCode2.incrementAndGet();
                        break label156;
                    }
                }

                if (request.getUrl().getScheme().equalsIgnoreCase("baiduboxapp")) {
                    return true;
                }
            }

            return super.shouldOverrideUrlLoading(view, request);
        }

        view.loadUrl(guideUrl);
        return true;
    }
}

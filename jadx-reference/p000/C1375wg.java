package p000;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wg */
/* loaded from: classes.dex */
public final class C1375wg {

    /* renamed from: a8 */
    public static final Object f60899a8 = new Object();

    /* renamed from: a9 */
    public static volatile C1375wg f60900a9;

    /* renamed from: a0 */
    public final ReentrantReadWriteLock f60901a0;

    /* renamed from: a1 */
    public final C0132bf f60902a1;

    /* renamed from: a2 */
    public volatile int f60903a2;

    /* renamed from: a3 */
    public final Handler f60904a3;

    /* renamed from: a4 */
    public final C1370wb f60905a4;

    /* renamed from: a5 */
    public final InterfaceC1374wf f60906a5;

    /* renamed from: a6 */
    public final int f60907a6;

    /* renamed from: a7 */
    public final C1157qv f60908a7;

    public C1375wg(C0563h c0563h) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.f60901a0 = reentrantReadWriteLock;
        this.f60903a2 = 3;
        InterfaceC1374wf interfaceC1374wf = (InterfaceC1374wf) c0563h.f60888a1;
        this.f60906a5 = interfaceC1374wf;
        int i = c0563h.f60887a0;
        this.f60907a6 = i;
        this.f60908a7 = (C1157qv) c0563h.f60889a2;
        this.f60904a3 = new Handler(Looper.getMainLooper());
        this.f60902a1 = new C0132bf(0);
        C1370wb c1370wb = new C1370wb(this);
        this.f60905a4 = c1370wb;
        reentrantReadWriteLock.writeLock().lock();
        if (i == 0) {
            try {
                this.f60903a2 = 0;
            } catch (Throwable th) {
                this.f60901a0.writeLock().unlock();
                throw th;
            }
        }
        reentrantReadWriteLock.writeLock().unlock();
        if (m215059a1() == 0) {
            try {
                interfaceC1374wf.mo212870b4(new C1369wa(c1370wb));
            } catch (Throwable th2) {
                m215061a3(th2);
            }
        }
    }

    /* renamed from: a0 */
    public static C1375wg m215058a0() {
        C1375wg c1375wg;
        synchronized (f60899a8) {
            try {
                c1375wg = f60900a9;
                if (!(c1375wg != null)) {
                    throw new IllegalStateException("EmojiCompat is not initialized.\n\nYou must initialize EmojiCompat prior to referencing the EmojiCompat instance.\n\nThe most likely cause of this error is disabling the EmojiCompatInitializer\neither explicitly in AndroidManifest.xml, or by including\nandroidx.emoji2:emoji2-bundled.\n\nAutomatic initialization is typically performed by EmojiCompatInitializer. If\nyou are not expecting to initialize EmojiCompat manually in your application,\nplease check to ensure it has not been removed from your APK's manifest. You can\ndo this in Android Studio using Build > Analyze APK.\n\nIn the APK Analyzer, ensure that the startup entry for\nEmojiCompatInitializer and InitializationProvider is present in\n AndroidManifest.xml. If it is missing or contains tools:node=\"remove\", and you\nintend to use automatic configuration, verify:\n\n  1. Your application does not include emoji2-bundled\n  2. All modules do not contain an exclusion manifest rule for\n     EmojiCompatInitializer or InitializationProvider. For more information\n     about manifest exclusions see the documentation for the androidx startup\n     library.\n\nIf you intend to use emoji2-bundled, please call EmojiCompat.init. You can\nlearn more in the documentation for BundledEmojiCompatConfig.\n\nIf you intended to perform manual configuration, it is recommended that you call\nEmojiCompat.init immediately on application startup.\n\nIf you still cannot resolve this issue, please open a bug with your specific\nconfiguration to help improve error message.");
                }
            } finally {
            }
        }
        return c1375wg;
    }

    /* renamed from: a1 */
    public final int m215059a1() {
        this.f60901a0.readLock().lock();
        try {
            return this.f60903a2;
        } finally {
            this.f60901a0.readLock().unlock();
        }
    }

    /* renamed from: a2 */
    public final void m215060a2() {
        if (!(this.f60907a6 == 1)) {
            throw new IllegalStateException("Set metadataLoadStrategy to LOAD_STRATEGY_MANUAL to execute manual loading");
        }
        if (m215059a1() == 1) {
            return;
        }
        this.f60901a0.writeLock().lock();
        try {
            if (this.f60903a2 == 0) {
                return;
            }
            this.f60903a2 = 0;
            this.f60901a0.writeLock().unlock();
            C1370wb c1370wb = this.f60905a4;
            C1375wg c1375wg = c1370wb.f60879a0;
            try {
                c1375wg.f60906a5.mo212870b4(new C1369wa(c1370wb));
            } catch (Throwable th) {
                c1375wg.m215061a3(th);
            }
        } finally {
            this.f60901a0.writeLock().unlock();
        }
    }

    /* renamed from: a3 */
    public final void m215061a3(Throwable th) {
        ArrayList arrayList = new ArrayList();
        this.f60901a0.writeLock().lock();
        try {
            this.f60903a2 = 2;
            arrayList.addAll(this.f60902a1);
            this.f60902a1.clear();
            this.f60901a0.writeLock().unlock();
            this.f60904a3.post(new RunnableC0503fo(arrayList, this.f60903a2, th));
        } catch (Throwable th2) {
            this.f60901a0.writeLock().unlock();
            throw th2;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not found exit edge by exit block: B:61:0x00d7
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.checkLoopExits(LoopRegionMaker.java:225)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeLoopRegion(LoopRegionMaker.java:195)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:62)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0094 A[Catch: all -> 0x0076, TryCatch #0 {all -> 0x0076, blocks: (B:32:0x005a, B:35:0x005f, B:37:0x0063, B:39:0x0070, B:44:0x0083, B:46:0x008d, B:48:0x0090, B:50:0x0094, B:52:0x00a4, B:53:0x00a7, B:55:0x00b4, B:58:0x00bc, B:63:0x00db, B:69:0x00e7, B:72:0x00f3, B:73:0x00fd, B:74:0x010c, B:76:0x0113, B:77:0x0118, B:79:0x0123, B:81:0x012a, B:83:0x012e, B:85:0x0134, B:87:0x0138, B:90:0x0140, B:93:0x014c, B:94:0x0151, B:96:0x015f, B:42:0x0079), top: B:115:0x005a }] */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final java.lang.CharSequence m215062a4(java.lang.CharSequence r12, int r13, int r14) {
        /*
            Method dump skipped, instructions count: 406
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: p000.C1375wg.m215062a4(java.lang.CharSequence, int, int):java.lang.CharSequence");
    }

    /* renamed from: a5 */
    public final void m215063a5(AbstractC1373we abstractC1373we) {
        b81.m210568a8(abstractC1373we, "initCallback cannot be null");
        this.f60901a0.writeLock().lock();
        try {
            if (this.f60903a2 == 1 || this.f60903a2 == 2) {
                this.f60904a3.post(new RunnableC0503fo(Arrays.asList(abstractC1373we), this.f60903a2, (Throwable) null));
            } else {
                this.f60902a1.add(abstractC1373we);
            }
            this.f60901a0.writeLock().unlock();
        } catch (Throwable th) {
            this.f60901a0.writeLock().unlock();
            throw th;
        }
    }
}

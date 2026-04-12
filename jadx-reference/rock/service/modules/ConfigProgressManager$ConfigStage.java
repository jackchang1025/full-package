package com.storm.safe.rock.service.modules;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public enum ConfigProgressManager$ConfigStage {
    IDLE(0, 0, "准备开始配置..."),
    INITIALIZING(0, 20, "正在初始化服务..."),
    CHECKING_PERMISSIONS(20, 40, "正在检查系统权限..."),
    /* JADX INFO: Fake field, exist only in values array */
    CONNECTING_NETWORK(40, 60, "正在连接到服务器..."),
    /* JADX INFO: Fake field, exist only in values array */
    REGISTERING_DEVICE(60, 80, "正在注册设备信息..."),
    COMPLETED(80, 100, "配置已完成");


    /* renamed from: a0 */
    public final int f52768a0;

    /* renamed from: a1 */
    public final int f52769a1;

    /* renamed from: a2 */
    public final String f52770a2;

    ConfigProgressManager$ConfigStage(int i, int i2, String str) {
        this.f52768a0 = i;
        this.f52769a1 = i2;
        this.f52770a2 = str;
    }
}

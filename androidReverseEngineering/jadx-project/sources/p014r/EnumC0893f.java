package p014r;

/* renamed from: r.f */
/* loaded from: classes.dex */
public enum EnumC0893f {
    OPEN_DEV_DEPT_UNKNOWN(-1),
    OPEN_DEV_DEPT_ENTER_ABOUT_DEVICE_WIN(0),
    OPEN_DEV_DEPT_PREPARE_VERSION_INFO_WIN(1),
    OPEN_DEV_DEPT_ENTER_VERSION_INFO_WIN(2),
    OPEN_DEV_DEPT_PREPARE_CONFIRM_LOCK_WIN(3),
    OPEN_DEV_DEPT_ENTER_CONFIRM_LOCK_WIN(4),
    OPEN_DEV_DEPT_IS_CONFIRM_SUCCESS(5),
    /* JADX INFO: Fake field, exist only in values array */
    OPEN_DEV_DEPT_ENTER_DEV_OPT_WIN_FAIL(6),
    OPEN_DEV_DEPT_ENABLE_DEV_OPT_SUCCESS(7),
    OPEN_DEV_DEPT_ENABLE_DEV_OPT_FAIL(8),
    OPEN_DEV_DEPT_WIN_CHECK(9),
    OPEN_DEV_DEPT_WIN_PREPARE(10),
    OPEN_DEV_DEPT_WIN_SUCCESS(11);


    /* renamed from: a */
    public final int f1975a;

    EnumC0893f(int i2) {
        this.f1975a = i2;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f1975a + " " + name();
    }
}

package p000;

import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class pb1 implements nb1 {

    /* renamed from: a1 */
    public static pb1 f59188a1;

    /* renamed from: a0 */
    public static final ob1 f59187a0 = new ob1(null);

    /* renamed from: a2 */
    public static final C1351vv f59189a2 = C1351vv.f60712b3;

    @Override // p000.nb1
    /* renamed from: a0 */
    public ib1 mo213203a0(Class cls) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        try {
            Object objNewInstance = cls.getDeclaredConstructor(null).newInstance(null);
            t60.m214694b5(objNewInstance, "{\n                modelC…wInstance()\n            }");
            return (ib1) objNewInstance;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot create an instance of " + cls, e);
        } catch (InstantiationException e2) {
            throw new RuntimeException("Cannot create an instance of " + cls, e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("Cannot create an instance of " + cls, e3);
        }
    }
}

package w0;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import q0.AbstractC0887c;

/* renamed from: w0.f */
/* loaded from: classes.dex */
public final class C0963f implements InvocationHandler {

    /* renamed from: a */
    public final List f2283a;

    /* renamed from: b */
    public boolean f2284b;

    /* renamed from: c */
    public String f2285c;

    public C0963f(ArrayList arrayList) {
        this.f2283a = arrayList;
    }

    @Override // java.lang.reflect.InvocationHandler
    public final Object invoke(Object obj, Method method, Object[] objArr) {
        String name = method.getName();
        Class<?> returnType = method.getReturnType();
        if (objArr == null) {
            objArr = AbstractC0887c.f1935b;
        }
        if (name.equals("supports") && Boolean.TYPE == returnType) {
            return Boolean.TRUE;
        }
        if (name.equals("unsupported") && Void.TYPE == returnType) {
            this.f2284b = true;
            return null;
        }
        boolean equals = name.equals("protocols");
        List list = this.f2283a;
        if (equals && objArr.length == 0) {
            return list;
        }
        if ((name.equals("selectProtocol") || name.equals("select")) && String.class == returnType && objArr.length == 1) {
            Object obj2 = objArr[0];
            if (obj2 instanceof List) {
                List list2 = (List) obj2;
                int size = list2.size();
                for (int i2 = 0; i2 < size; i2++) {
                    String str = (String) list2.get(i2);
                    if (list.contains(str)) {
                        this.f2285c = str;
                        return str;
                    }
                }
                String str2 = (String) list.get(0);
                this.f2285c = str2;
                return str2;
            }
        }
        if ((!name.equals("protocolSelected") && !name.equals("selected")) || objArr.length != 1) {
            return method.invoke(this, objArr);
        }
        this.f2285c = (String) objArr[0];
        return null;
    }
}

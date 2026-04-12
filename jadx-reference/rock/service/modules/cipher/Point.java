package com.storm.safe.rock.service.modules.cipher;

import java.io.Serializable;
import java.util.Objects;
import p000.AbstractC0003a2;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class Point implements Serializable {

    /* renamed from: a0 */
    public final float f53261a0;

    /* renamed from: a1 */
    public final float f53262a1;

    public Point(float f, float f2) {
        this.f53261a0 = f;
        this.f53262a1 = f2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && Point.class.equals(obj.getClass())) {
            Point point = (Point) obj;
            if (Float.compare(point.f53261a0, this.f53261a0) == 0 && Float.compare(point.f53262a1, this.f53262a1) == 0) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Float.valueOf(this.f53261a0), Float.valueOf(this.f53262a1));
    }

    public final String toString() {
        return AbstractC0003a2.m29b0("Point{x=", this.f53261a0, ", y=", this.f53262a1, "}");
    }
}

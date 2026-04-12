package p000;

import java.io.IOException;

/* renamed from: e8 */
/* loaded from: classes2.dex */
public abstract class AbstractC0447e8 {
    public static AbstractC0439e0 checkTag(AbstractC0439e0 abstractC0439e0, int i, int i2) {
        if (abstractC0439e0.hasTag(i, i2)) {
            return abstractC0439e0;
        }
        throw new IllegalStateException("Expected " + getTagText(i, i2) + " tag but found " + getTagText(abstractC0439e0));
    }

    public static AbstractC0164c9 getBaseUniversal(AbstractC0439e0 abstractC0439e0, int i, int i2, boolean z, int i3) {
        return checkTag(abstractC0439e0, i, i2).getBaseUniversal(z, i3);
    }

    public static AbstractC0164c9 getContextBaseUniversal(AbstractC0439e0 abstractC0439e0, int i, boolean z, int i2) {
        return getBaseUniversal(abstractC0439e0, 128, i, z, i2);
    }

    public static AbstractC0158c3 getExplicitBaseObject(AbstractC0439e0 abstractC0439e0, int i, int i2) {
        return checkTag(abstractC0439e0, i, i2).getExplicitBaseObject();
    }

    public static AbstractC0439e0 getExplicitBaseTagged(AbstractC0439e0 abstractC0439e0, int i, int i2) {
        return checkTag(abstractC0439e0, i, i2).getExplicitBaseTagged();
    }

    public static AbstractC0158c3 getExplicitContextBaseObject(AbstractC0439e0 abstractC0439e0, int i) {
        return getExplicitBaseObject(abstractC0439e0, 128, i);
    }

    public static AbstractC0439e0 getExplicitContextBaseTagged(AbstractC0439e0 abstractC0439e0, int i) {
        return getExplicitBaseTagged(abstractC0439e0, 128, i);
    }

    public static AbstractC0439e0 getImplicitBaseTagged(AbstractC0439e0 abstractC0439e0, int i, int i2, int i3, int i4) {
        return checkTag(abstractC0439e0, i, i2).getImplicitBaseTagged(i3, i4);
    }

    public static AbstractC0439e0 getImplicitContextBaseTagged(AbstractC0439e0 abstractC0439e0, int i, int i2, int i3) {
        return getImplicitBaseTagged(abstractC0439e0, 128, i, i2, i3);
    }

    public static String getTagText(int i, int i2) {
        return AbstractC0003a2.m30b1(i != 64 ? i != 128 ? i != 192 ? "[UNIVERSAL " : "[PRIVATE " : "[CONTEXT " : "[APPLICATION ", i2, "]");
    }

    public static InterfaceC0117b0 parseBaseUniversal(InterfaceC0440e1 interfaceC0440e1, int i, int i2, boolean z, int i3) throws IOException {
        return checkTag(interfaceC0440e1, i, i2).parseBaseUniversal(z, i3);
    }

    public static InterfaceC0117b0 parseContextBaseUniversal(InterfaceC0440e1 interfaceC0440e1, int i, boolean z, int i2) throws IOException {
        return parseBaseUniversal(interfaceC0440e1, 128, i, z, i2);
    }

    public static InterfaceC0117b0 parseExplicitBaseObject(InterfaceC0440e1 interfaceC0440e1, int i, int i2) throws IOException {
        return checkTag(interfaceC0440e1, i, i2).parseExplicitBaseObject();
    }

    public static InterfaceC0440e1 parseExplicitBaseTagged(InterfaceC0440e1 interfaceC0440e1, int i, int i2) throws IOException {
        return checkTag(interfaceC0440e1, i, i2).parseExplicitBaseTagged();
    }

    public static InterfaceC0117b0 parseExplicitContextBaseObject(InterfaceC0440e1 interfaceC0440e1, int i) throws IOException {
        return parseExplicitBaseObject(interfaceC0440e1, 128, i);
    }

    public static InterfaceC0440e1 parseExplicitContextBaseTagged(InterfaceC0440e1 interfaceC0440e1, int i) throws IOException {
        return parseExplicitBaseTagged(interfaceC0440e1, 128, i);
    }

    public static InterfaceC0440e1 parseImplicitBaseTagged(InterfaceC0440e1 interfaceC0440e1, int i, int i2, int i3, int i4) throws IOException {
        return checkTag(interfaceC0440e1, i, i2).parseImplicitBaseTagged(i3, i4);
    }

    public static InterfaceC0440e1 parseImplicitContextBaseTagged(InterfaceC0440e1 interfaceC0440e1, int i, int i2, int i3) throws IOException {
        return parseImplicitBaseTagged(interfaceC0440e1, 128, i, i2, i3);
    }

    public static AbstractC0164c9 tryGetBaseUniversal(AbstractC0439e0 abstractC0439e0, int i, int i2, boolean z, int i3) {
        if (abstractC0439e0.hasTag(i, i2)) {
            return abstractC0439e0.getBaseUniversal(z, i3);
        }
        return null;
    }

    public static AbstractC0164c9 tryGetContextBaseUniversal(AbstractC0439e0 abstractC0439e0, int i, boolean z, int i2) {
        return tryGetBaseUniversal(abstractC0439e0, 128, i, z, i2);
    }

    public static AbstractC0158c3 tryGetExplicitBaseObject(AbstractC0439e0 abstractC0439e0, int i, int i2) {
        if (abstractC0439e0.hasTag(i, i2)) {
            return abstractC0439e0.getExplicitBaseObject();
        }
        return null;
    }

    public static AbstractC0439e0 tryGetExplicitBaseTagged(AbstractC0439e0 abstractC0439e0, int i, int i2) {
        if (abstractC0439e0.hasTag(i, i2)) {
            return abstractC0439e0.getExplicitBaseTagged();
        }
        return null;
    }

    public static AbstractC0158c3 tryGetExplicitContextBaseObject(AbstractC0439e0 abstractC0439e0, int i) {
        return tryGetExplicitBaseObject(abstractC0439e0, 128, i);
    }

    public static AbstractC0439e0 tryGetExplicitContextBaseTagged(AbstractC0439e0 abstractC0439e0, int i) {
        return tryGetExplicitBaseTagged(abstractC0439e0, 128, i);
    }

    public static AbstractC0439e0 tryGetImplicitBaseTagged(AbstractC0439e0 abstractC0439e0, int i, int i2, int i3, int i4) {
        if (abstractC0439e0.hasTag(i, i2)) {
            return abstractC0439e0.getImplicitBaseTagged(i3, i4);
        }
        return null;
    }

    public static AbstractC0439e0 tryGetImplicitContextBaseTagged(AbstractC0439e0 abstractC0439e0, int i, int i2, int i3) {
        return tryGetImplicitBaseTagged(abstractC0439e0, 128, i, i2, i3);
    }

    public static InterfaceC0117b0 tryParseBaseUniversal(InterfaceC0440e1 interfaceC0440e1, int i, int i2, boolean z, int i3) throws IOException {
        if (interfaceC0440e1.hasTag(i, i2)) {
            return interfaceC0440e1.parseBaseUniversal(z, i3);
        }
        return null;
    }

    public static InterfaceC0117b0 tryParseContextBaseUniversal(InterfaceC0440e1 interfaceC0440e1, int i, boolean z, int i2) throws IOException {
        return tryParseBaseUniversal(interfaceC0440e1, 128, i, z, i2);
    }

    public static InterfaceC0117b0 tryParseExplicitBaseObject(InterfaceC0440e1 interfaceC0440e1, int i, int i2) throws IOException {
        if (interfaceC0440e1.hasTag(i, i2)) {
            return interfaceC0440e1.parseExplicitBaseObject();
        }
        return null;
    }

    public static InterfaceC0440e1 tryParseExplicitBaseTagged(InterfaceC0440e1 interfaceC0440e1, int i, int i2) throws IOException {
        if (interfaceC0440e1.hasTag(i, i2)) {
            return interfaceC0440e1.parseExplicitBaseTagged();
        }
        return null;
    }

    public static InterfaceC0117b0 tryParseExplicitContextBaseObject(InterfaceC0440e1 interfaceC0440e1, int i) throws IOException {
        return tryParseExplicitBaseObject(interfaceC0440e1, 128, i);
    }

    public static InterfaceC0440e1 tryParseExplicitContextBaseTagged(InterfaceC0440e1 interfaceC0440e1, int i) throws IOException {
        return tryParseExplicitBaseTagged(interfaceC0440e1, 128, i);
    }

    public static InterfaceC0440e1 tryParseImplicitBaseTagged(InterfaceC0440e1 interfaceC0440e1, int i, int i2, int i3, int i4) throws IOException {
        if (interfaceC0440e1.hasTag(i, i2)) {
            return interfaceC0440e1.parseImplicitBaseTagged(i3, i4);
        }
        return null;
    }

    public static InterfaceC0440e1 tryParseImplicitContextBaseTagged(InterfaceC0440e1 interfaceC0440e1, int i, int i2, int i3) throws IOException {
        return tryParseImplicitBaseTagged(interfaceC0440e1, 128, i, i2, i3);
    }

    public static InterfaceC0440e1 checkTag(InterfaceC0440e1 interfaceC0440e1, int i, int i2) {
        if (interfaceC0440e1.hasTag(i, i2)) {
            return interfaceC0440e1;
        }
        throw new IllegalStateException("Expected " + getTagText(i, i2) + " tag but found " + getTagText(interfaceC0440e1));
    }

    public static String getTagText(C0407d9 c0407d9) {
        return getTagText(c0407d9.getTagClass(), c0407d9.getTagNumber());
    }

    public static String getTagText(AbstractC0439e0 abstractC0439e0) {
        return getTagText(abstractC0439e0.getTagClass(), abstractC0439e0.getTagNo());
    }

    public static String getTagText(InterfaceC0440e1 interfaceC0440e1) {
        return getTagText(interfaceC0440e1.getTagClass(), interfaceC0440e1.getTagNo());
    }
}

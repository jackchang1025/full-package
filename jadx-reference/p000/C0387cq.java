package p000;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Exception;
import org.bouncycastle.asn1.ASN1ParsingException;

/* renamed from: cq */
/* loaded from: classes2.dex */
public class C0387cq implements InterfaceC0440e1 {
    final C0404d6 _parser;
    final int _tagClass;
    final int _tagNo;

    public C0387cq(int i, int i2, C0404d6 c0404d6) {
        this._tagClass = i;
        this._tagNo = i2;
        this._parser = c0404d6;
    }

    @Override // p000.InterfaceC0440e1, p000.i50
    public AbstractC0164c9 getLoadedObject() throws IOException {
        return this._parser.loadTaggedIL(this._tagClass, this._tagNo);
    }

    @Override // p000.InterfaceC0440e1
    public InterfaceC0117b0 getObjectParser(int i, boolean z) throws IOException {
        if (128 == getTagClass()) {
            return parseBaseUniversal(z, i);
        }
        throw new ASN1Exception("this method only valid for CONTEXT_SPECIFIC tags");
    }

    @Override // p000.InterfaceC0440e1
    public int getTagClass() {
        return this._tagClass;
    }

    @Override // p000.InterfaceC0440e1
    public int getTagNo() {
        return this._tagNo;
    }

    @Override // p000.InterfaceC0440e1
    public boolean hasContextTag(int i) {
        return this._tagClass == 128 && this._tagNo == i;
    }

    @Override // p000.InterfaceC0440e1
    public boolean hasTag(int i, int i2) {
        return this._tagClass == i && this._tagNo == i2;
    }

    public boolean isConstructed() {
        return true;
    }

    @Override // p000.InterfaceC0440e1
    public InterfaceC0117b0 parseBaseUniversal(boolean z, int i) throws IOException {
        return z ? this._parser.parseObject(i) : this._parser.parseImplicitConstructedIL(i);
    }

    @Override // p000.InterfaceC0440e1
    public InterfaceC0117b0 parseExplicitBaseObject() throws IOException {
        return this._parser.readObject();
    }

    @Override // p000.InterfaceC0440e1
    public InterfaceC0440e1 parseExplicitBaseTagged() throws IOException {
        return this._parser.parseTaggedObject();
    }

    @Override // p000.InterfaceC0440e1
    public InterfaceC0440e1 parseImplicitBaseTagged(int i, int i2) throws IOException {
        return 64 == i ? new C0170cf(i2, this._parser) : new C0387cq(i, i2, this._parser);
    }

    @Override // p000.InterfaceC0440e1, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException(e.getMessage());
        }
    }
}

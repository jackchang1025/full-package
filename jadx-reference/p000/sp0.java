package p000;

/* loaded from: classes2.dex */
public class sp0 extends AbstractC0158c3 {

    /* renamed from: b1 */
    private byte[] f60059b1;

    /* renamed from: b2 */
    private byte[] f60060b2;
    private byte[][] invA1;
    private byte[][] invA2;
    private w90[] layers;
    private C0160c5 oid;
    private C0155c0 version;

    /* renamed from: vi */
    private byte[] f60061vi;

    private sp0(AbstractC0400d2 abstractC0400d2) {
        int i = 0;
        if (abstractC0400d2.getObjectAt(0) instanceof C0155c0) {
            this.version = C0155c0.getInstance(abstractC0400d2.getObjectAt(0));
        } else {
            this.oid = C0160c5.getInstance(abstractC0400d2.getObjectAt(0));
        }
        AbstractC0400d2 abstractC0400d22 = (AbstractC0400d2) abstractC0400d2.getObjectAt(1);
        this.invA1 = new byte[abstractC0400d22.size()][];
        for (int i2 = 0; i2 < abstractC0400d22.size(); i2++) {
            this.invA1[i2] = ((AbstractC0161c6) abstractC0400d22.getObjectAt(i2)).getOctets();
        }
        this.f60059b1 = ((AbstractC0161c6) ((AbstractC0400d2) abstractC0400d2.getObjectAt(2)).getObjectAt(0)).getOctets();
        AbstractC0400d2 abstractC0400d23 = (AbstractC0400d2) abstractC0400d2.getObjectAt(3);
        this.invA2 = new byte[abstractC0400d23.size()][];
        for (int i3 = 0; i3 < abstractC0400d23.size(); i3++) {
            this.invA2[i3] = ((AbstractC0161c6) abstractC0400d23.getObjectAt(i3)).getOctets();
        }
        this.f60060b2 = ((AbstractC0161c6) ((AbstractC0400d2) abstractC0400d2.getObjectAt(4)).getObjectAt(0)).getOctets();
        this.f60061vi = ((AbstractC0161c6) ((AbstractC0400d2) abstractC0400d2.getObjectAt(5)).getObjectAt(0)).getOctets();
        AbstractC0400d2 abstractC0400d24 = (AbstractC0400d2) abstractC0400d2.getObjectAt(6);
        byte[][][][] bArr = new byte[abstractC0400d24.size()][][][];
        byte[][][][] bArr2 = new byte[abstractC0400d24.size()][][][];
        byte[][][] bArr3 = new byte[abstractC0400d24.size()][][];
        byte[][] bArr4 = new byte[abstractC0400d24.size()][];
        int i4 = 0;
        while (i4 < abstractC0400d24.size()) {
            AbstractC0400d2 abstractC0400d25 = (AbstractC0400d2) abstractC0400d24.getObjectAt(i4);
            AbstractC0400d2 abstractC0400d26 = (AbstractC0400d2) abstractC0400d25.getObjectAt(i);
            bArr[i4] = new byte[abstractC0400d26.size()][][];
            for (int i5 = i; i5 < abstractC0400d26.size(); i5++) {
                AbstractC0400d2 abstractC0400d27 = (AbstractC0400d2) abstractC0400d26.getObjectAt(i5);
                bArr[i4][i5] = new byte[abstractC0400d27.size()][];
                for (int i6 = 0; i6 < abstractC0400d27.size(); i6++) {
                    bArr[i4][i5][i6] = ((AbstractC0161c6) abstractC0400d27.getObjectAt(i6)).getOctets();
                }
            }
            AbstractC0400d2 abstractC0400d28 = (AbstractC0400d2) abstractC0400d25.getObjectAt(1);
            bArr2[i4] = new byte[abstractC0400d28.size()][][];
            for (int i7 = 0; i7 < abstractC0400d28.size(); i7++) {
                AbstractC0400d2 abstractC0400d29 = (AbstractC0400d2) abstractC0400d28.getObjectAt(i7);
                bArr2[i4][i7] = new byte[abstractC0400d29.size()][];
                for (int i8 = 0; i8 < abstractC0400d29.size(); i8++) {
                    bArr2[i4][i7][i8] = ((AbstractC0161c6) abstractC0400d29.getObjectAt(i8)).getOctets();
                }
            }
            AbstractC0400d2 abstractC0400d210 = (AbstractC0400d2) abstractC0400d25.getObjectAt(2);
            bArr3[i4] = new byte[abstractC0400d210.size()][];
            for (int i9 = 0; i9 < abstractC0400d210.size(); i9++) {
                bArr3[i4][i9] = ((AbstractC0161c6) abstractC0400d210.getObjectAt(i9)).getOctets();
            }
            bArr4[i4] = ((AbstractC0161c6) abstractC0400d25.getObjectAt(3)).getOctets();
            i4++;
            i = 0;
        }
        int length = this.f60061vi.length - 1;
        this.layers = new w90[length];
        int i10 = 0;
        while (i10 < length) {
            byte[] bArr5 = this.f60061vi;
            int i11 = i10 + 1;
            this.layers[i10] = new w90(bArr5[i10], bArr5[i11], yp0.convertArray(bArr[i10]), yp0.convertArray(bArr2[i10]), yp0.convertArray(bArr3[i10]), yp0.convertArray(bArr4[i10]));
            i10 = i11;
        }
    }

    public static sp0 getInstance(Object obj) {
        if (obj instanceof sp0) {
            return (sp0) obj;
        }
        if (obj != null) {
            return new sp0(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public short[] getB1() {
        return yp0.convertArray(this.f60059b1);
    }

    public short[] getB2() {
        return yp0.convertArray(this.f60060b2);
    }

    public short[][] getInvA1() {
        return yp0.convertArray(this.invA1);
    }

    public short[][] getInvA2() {
        return yp0.convertArray(this.invA2);
    }

    public w90[] getLayers() {
        return this.layers;
    }

    public C0155c0 getVersion() {
        return this.version;
    }

    public int[] getVi() {
        return yp0.convertArraytoInt(this.f60061vi);
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1();
        InterfaceC0117b0 interfaceC0117b0 = this.version;
        if (interfaceC0117b0 == null) {
            interfaceC0117b0 = this.oid;
        }
        c0118b1.add(interfaceC0117b0);
        C0118b1 c0118b12 = new C0118b1();
        for (int i = 0; i < this.invA1.length; i++) {
            c0118b12.add(new C1048oy(this.invA1[i]));
        }
        c0118b1.add(new C1064pc(c0118b12));
        C0118b1 c0118b13 = new C0118b1();
        c0118b13.add(new C1048oy(this.f60059b1));
        c0118b1.add(new C1064pc(c0118b13));
        C0118b1 c0118b14 = new C0118b1();
        for (int i2 = 0; i2 < this.invA2.length; i2++) {
            c0118b14.add(new C1048oy(this.invA2[i2]));
        }
        c0118b1.add(new C1064pc(c0118b14));
        C0118b1 c0118b15 = new C0118b1();
        c0118b15.add(new C1048oy(this.f60060b2));
        c0118b1.add(new C1064pc(c0118b15));
        C0118b1 c0118b16 = new C0118b1();
        c0118b16.add(new C1048oy(this.f60061vi));
        c0118b1.add(new C1064pc(c0118b16));
        C0118b1 c0118b17 = new C0118b1();
        for (int i3 = 0; i3 < this.layers.length; i3++) {
            C0118b1 c0118b18 = new C0118b1();
            byte[][][] bArrConvertArray = yp0.convertArray(this.layers[i3].getCoeffAlpha());
            C0118b1 c0118b19 = new C0118b1();
            for (int i4 = 0; i4 < bArrConvertArray.length; i4++) {
                C0118b1 c0118b110 = new C0118b1();
                for (int i5 = 0; i5 < bArrConvertArray[i4].length; i5++) {
                    c0118b110.add(new C1048oy(bArrConvertArray[i4][i5]));
                }
                c0118b19.add(new C1064pc(c0118b110));
            }
            c0118b18.add(new C1064pc(c0118b19));
            byte[][][] bArrConvertArray2 = yp0.convertArray(this.layers[i3].getCoeffBeta());
            C0118b1 c0118b111 = new C0118b1();
            for (int i6 = 0; i6 < bArrConvertArray2.length; i6++) {
                C0118b1 c0118b112 = new C0118b1();
                for (int i7 = 0; i7 < bArrConvertArray2[i6].length; i7++) {
                    c0118b112.add(new C1048oy(bArrConvertArray2[i6][i7]));
                }
                c0118b111.add(new C1064pc(c0118b112));
            }
            c0118b18.add(new C1064pc(c0118b111));
            byte[][] bArrConvertArray3 = yp0.convertArray(this.layers[i3].getCoeffGamma());
            C0118b1 c0118b113 = new C0118b1();
            for (byte[] bArr : bArrConvertArray3) {
                c0118b113.add(new C1048oy(bArr));
            }
            c0118b18.add(new C1064pc(c0118b113));
            c0118b18.add(new C1048oy(yp0.convertArray(this.layers[i3].getCoeffEta())));
            c0118b17.add(new C1064pc(c0118b18));
        }
        c0118b1.add(new C1064pc(c0118b17));
        return new C1064pc(c0118b1);
    }

    public sp0(short[][] sArr, short[] sArr2, short[][] sArr3, short[] sArr4, int[] iArr, w90[] w90VarArr) {
        this.version = new C0155c0(1L);
        this.invA1 = yp0.convertArray(sArr);
        this.f60059b1 = yp0.convertArray(sArr2);
        this.invA2 = yp0.convertArray(sArr3);
        this.f60060b2 = yp0.convertArray(sArr4);
        this.f60061vi = yp0.convertIntArray(iArr);
        this.layers = w90VarArr;
    }
}

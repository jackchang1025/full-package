package p000;

import java.util.Vector;

/* loaded from: classes2.dex */
public class lh1 {
    private Vector rdns;
    private mh1 template;

    public lh1() {
        this(C0168cd.INSTANCE);
    }

    public lh1 addMultiValuedRDN(C0160c5[] c0160c5Arr, InterfaceC0117b0[] interfaceC0117b0Arr) {
        C0145bs[] c0145bsArr = new C0145bs[c0160c5Arr.length];
        for (int i = 0; i != c0160c5Arr.length; i++) {
            c0145bsArr[i] = new C0145bs(c0160c5Arr[i], interfaceC0117b0Arr[i]);
        }
        return addMultiValuedRDN(c0145bsArr);
    }

    public lh1 addRDN(C0160c5 c0160c5, InterfaceC0117b0 interfaceC0117b0) {
        this.rdns.addElement(new np0(c0160c5, interfaceC0117b0));
        return this;
    }

    public kh1 build() {
        int size = this.rdns.size();
        np0[] np0VarArr = new np0[size];
        for (int i = 0; i != size; i++) {
            np0VarArr[i] = (np0) this.rdns.elementAt(i);
        }
        return new kh1(this.template, np0VarArr);
    }

    public lh1(mh1 mh1Var) {
        this.rdns = new Vector();
        this.template = mh1Var;
    }

    public lh1 addMultiValuedRDN(C0160c5[] c0160c5Arr, String[] strArr) {
        int length = strArr.length;
        InterfaceC0117b0[] interfaceC0117b0Arr = new InterfaceC0117b0[length];
        for (int i = 0; i != length; i++) {
            interfaceC0117b0Arr[i] = this.template.stringToValue(c0160c5Arr[i], strArr[i]);
        }
        return addMultiValuedRDN(c0160c5Arr, interfaceC0117b0Arr);
    }

    public lh1 addRDN(C0160c5 c0160c5, String str) {
        addRDN(c0160c5, this.template.stringToValue(c0160c5, str));
        return this;
    }

    public lh1 addMultiValuedRDN(C0145bs[] c0145bsArr) {
        this.rdns.addElement(new np0(c0145bsArr));
        return this;
    }

    public lh1 addRDN(C0145bs c0145bs) {
        this.rdns.addElement(new np0(c0145bs));
        return this;
    }
}

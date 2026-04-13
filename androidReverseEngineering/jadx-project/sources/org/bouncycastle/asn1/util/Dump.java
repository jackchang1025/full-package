package org.bouncycastle.asn1.util;

import java.io.FileInputStream;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;

/* loaded from: classes.dex */
public class Dump {
    public static void main(String[] strArr) {
        FileInputStream fileInputStream = new FileInputStream(strArr[0]);
        ASN1InputStream aSN1InputStream = new ASN1InputStream(fileInputStream);
        while (true) {
            ASN1Primitive readObject = aSN1InputStream.readObject();
            if (readObject == null) {
                fileInputStream.close();
                return;
            }
            System.out.println(ASN1Dump.dumpAsString(readObject));
        }
    }
}

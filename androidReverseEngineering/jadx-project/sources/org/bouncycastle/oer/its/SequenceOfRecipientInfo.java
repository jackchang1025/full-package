package org.bouncycastle.oer.its;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes.dex */
public class SequenceOfRecipientInfo extends ASN1Object {
    private final List<RecipientInfo> recipientInfos;

    public static class Builder {
        private List<RecipientInfo> recipientInfos;

        public Builder addRecipients(RecipientInfo... recipientInfoArr) {
            if (this.recipientInfos == null) {
                this.recipientInfos = new ArrayList();
            }
            this.recipientInfos.addAll(Arrays.asList(recipientInfoArr));
            return this;
        }

        public SequenceOfRecipientInfo createSequenceOfRecipientInfo() {
            return new SequenceOfRecipientInfo(this.recipientInfos);
        }

        public Builder setRecipientInfos(List<RecipientInfo> list) {
            this.recipientInfos = list;
            return this;
        }
    }

    public SequenceOfRecipientInfo(List<RecipientInfo> list) {
        this.recipientInfos = Collections.unmodifiableList(list);
    }

    public static SequenceOfRecipientInfo getInstance(Object obj) {
        if (obj instanceof SequenceOfRecipientInfo) {
            return (SequenceOfRecipientInfo) obj;
        }
        Enumeration objects = ASN1Sequence.getInstance(obj).getObjects();
        ArrayList arrayList = new ArrayList();
        while (objects.hasMoreElements()) {
            arrayList.add(RecipientInfo.getInstance(objects.nextElement()));
        }
        return new Builder().setRecipientInfos(arrayList).createSequenceOfRecipientInfo();
    }

    public List<RecipientInfo> getRecipientInfos() {
        return this.recipientInfos;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(new ASN1EncodableVector());
    }
}

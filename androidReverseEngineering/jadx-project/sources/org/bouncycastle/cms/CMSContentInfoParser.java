package org.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.bouncycastle.asn1.cms.ContentInfoParser;

/* loaded from: classes.dex */
public class CMSContentInfoParser {
    protected ContentInfoParser _contentInfo;
    protected InputStream _data;

    public CMSContentInfoParser(InputStream inputStream) {
        this._data = inputStream;
        try {
            ASN1SequenceParser aSN1SequenceParser = (ASN1SequenceParser) new ASN1StreamParser(inputStream).readObject();
            if (aSN1SequenceParser == null) {
                throw new CMSException("No content found.");
            }
            this._contentInfo = new ContentInfoParser(aSN1SequenceParser);
        } catch (IOException e2) {
            throw new CMSException("IOException reading content.", e2);
        } catch (ClassCastException e3) {
            throw new CMSException("Unexpected object reading content.", e3);
        }
    }

    public void close() {
        this._data.close();
    }
}

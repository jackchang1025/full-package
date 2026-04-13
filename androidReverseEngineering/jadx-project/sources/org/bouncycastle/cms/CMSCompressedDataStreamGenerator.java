package org.bouncycastle.cms;

import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.BERSequenceGenerator;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.operator.OutputCompressor;

/* loaded from: classes.dex */
public class CMSCompressedDataStreamGenerator {
    public static final String ZLIB = "1.2.840.113549.1.9.16.3.8";
    private int _bufferSize;

    public class CmsCompressedOutputStream extends OutputStream {
        private BERSequenceGenerator _cGen;
        private BERSequenceGenerator _eiGen;
        private OutputStream _out;
        private BERSequenceGenerator _sGen;

        public CmsCompressedOutputStream(OutputStream outputStream, BERSequenceGenerator bERSequenceGenerator, BERSequenceGenerator bERSequenceGenerator2, BERSequenceGenerator bERSequenceGenerator3) {
            this._out = outputStream;
            this._sGen = bERSequenceGenerator;
            this._cGen = bERSequenceGenerator2;
            this._eiGen = bERSequenceGenerator3;
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this._out.close();
            this._eiGen.close();
            this._cGen.close();
            this._sGen.close();
        }

        @Override // java.io.OutputStream
        public void write(int i2) {
            this._out.write(i2);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) {
            this._out.write(bArr);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i2, int i3) {
            this._out.write(bArr, i2, i3);
        }
    }

    public OutputStream open(OutputStream outputStream, OutputCompressor outputCompressor) {
        return open(CMSObjectIdentifiers.data, outputStream, outputCompressor);
    }

    public void setBufferSize(int i2) {
        this._bufferSize = i2;
    }

    public OutputStream open(ASN1ObjectIdentifier aSN1ObjectIdentifier, OutputStream outputStream, OutputCompressor outputCompressor) {
        BERSequenceGenerator bERSequenceGenerator = new BERSequenceGenerator(outputStream);
        bERSequenceGenerator.addObject((ASN1Primitive) CMSObjectIdentifiers.compressedData);
        BERSequenceGenerator bERSequenceGenerator2 = new BERSequenceGenerator(bERSequenceGenerator.getRawOutputStream(), 0, true);
        bERSequenceGenerator2.addObject((ASN1Primitive) new ASN1Integer(0L));
        bERSequenceGenerator2.addObject(outputCompressor.getAlgorithmIdentifier());
        BERSequenceGenerator bERSequenceGenerator3 = new BERSequenceGenerator(bERSequenceGenerator2.getRawOutputStream());
        bERSequenceGenerator3.addObject((ASN1Primitive) aSN1ObjectIdentifier);
        return new CmsCompressedOutputStream(outputCompressor.getOutputStream(CMSUtils.createBEROctetOutputStream(bERSequenceGenerator3.getRawOutputStream(), 0, true, this._bufferSize)), bERSequenceGenerator, bERSequenceGenerator2, bERSequenceGenerator3);
    }
}

package org.conscrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import javax.crypto.NoSuchPaddingException;
import org.conscrypt.OpenSSLCipher;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class OpenSSLEvpCipherAES extends OpenSSLEvpCipher {
    private static final int AES_BLOCK_SIZE = 16;

    /* renamed from: org.conscrypt.OpenSSLEvpCipherAES$1 */
    public static /* synthetic */ class C08341 {
        static final /* synthetic */ int[] $SwitchMap$org$conscrypt$OpenSSLCipher$Mode;
        static final /* synthetic */ int[] $SwitchMap$org$conscrypt$OpenSSLCipher$Padding;

        static {
            int[] iArr = new int[OpenSSLCipher.Padding.values().length];
            $SwitchMap$org$conscrypt$OpenSSLCipher$Padding = iArr;
            try {
                iArr[OpenSSLCipher.Padding.NOPADDING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$conscrypt$OpenSSLCipher$Padding[OpenSSLCipher.Padding.PKCS5PADDING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[OpenSSLCipher.Mode.values().length];
            $SwitchMap$org$conscrypt$OpenSSLCipher$Mode = iArr2;
            try {
                iArr2[OpenSSLCipher.Mode.CBC.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$conscrypt$OpenSSLCipher$Mode[OpenSSLCipher.Mode.CTR.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$conscrypt$OpenSSLCipher$Mode[OpenSSLCipher.Mode.ECB.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static class AES extends OpenSSLEvpCipherAES {

        public static class CBC extends AES {

            public static class NoPadding extends CBC {
                public NoPadding() {
                    super(OpenSSLCipher.Padding.NOPADDING);
                }
            }

            public static class PKCS5Padding extends CBC {
                public PKCS5Padding() {
                    super(OpenSSLCipher.Padding.PKCS5PADDING);
                }
            }

            public CBC(OpenSSLCipher.Padding padding) {
                super(OpenSSLCipher.Mode.CBC, padding);
            }
        }

        public static class CTR extends AES {
            public CTR() {
                super(OpenSSLCipher.Mode.CTR, OpenSSLCipher.Padding.NOPADDING);
            }
        }

        public static class ECB extends AES {

            public static class NoPadding extends ECB {
                public NoPadding() {
                    super(OpenSSLCipher.Padding.NOPADDING);
                }
            }

            public static class PKCS5Padding extends ECB {
                public PKCS5Padding() {
                    super(OpenSSLCipher.Padding.PKCS5PADDING);
                }
            }

            public ECB(OpenSSLCipher.Padding padding) {
                super(OpenSSLCipher.Mode.ECB, padding);
            }
        }

        public AES(OpenSSLCipher.Mode mode, OpenSSLCipher.Padding padding) {
            super(mode, padding);
        }

        @Override // org.conscrypt.OpenSSLCipher
        public void checkSupportedKeySize(int i2) {
            if (i2 != 16 && i2 != 24 && i2 != 32) {
                throw new InvalidKeyException(AbstractC0000a.m12h("Unsupported key size: ", i2, " bytes"));
            }
        }
    }

    public static class AES_128 extends OpenSSLEvpCipherAES {

        public static class CBC extends AES_128 {

            public static class NoPadding extends CBC {
                public NoPadding() {
                    super(OpenSSLCipher.Padding.NOPADDING);
                }
            }

            public static class PKCS5Padding extends CBC {
                public PKCS5Padding() {
                    super(OpenSSLCipher.Padding.PKCS5PADDING);
                }
            }

            public CBC(OpenSSLCipher.Padding padding) {
                super(OpenSSLCipher.Mode.CBC, padding);
            }
        }

        public static class CTR extends AES_128 {
            public CTR() {
                super(OpenSSLCipher.Mode.CTR, OpenSSLCipher.Padding.NOPADDING);
            }
        }

        public static class ECB extends AES_128 {

            public static class NoPadding extends ECB {
                public NoPadding() {
                    super(OpenSSLCipher.Padding.NOPADDING);
                }
            }

            public static class PKCS5Padding extends ECB {
                public PKCS5Padding() {
                    super(OpenSSLCipher.Padding.PKCS5PADDING);
                }
            }

            public ECB(OpenSSLCipher.Padding padding) {
                super(OpenSSLCipher.Mode.ECB, padding);
            }
        }

        public AES_128(OpenSSLCipher.Mode mode, OpenSSLCipher.Padding padding) {
            super(mode, padding);
        }

        @Override // org.conscrypt.OpenSSLCipher
        public void checkSupportedKeySize(int i2) {
            if (i2 != 16) {
                throw new InvalidKeyException(AbstractC0000a.m12h("Unsupported key size: ", i2, " bytes"));
            }
        }
    }

    public static class AES_256 extends OpenSSLEvpCipherAES {

        public static class CBC extends AES_256 {

            public static class NoPadding extends CBC {
                public NoPadding() {
                    super(OpenSSLCipher.Padding.NOPADDING);
                }
            }

            public static class PKCS5Padding extends CBC {
                public PKCS5Padding() {
                    super(OpenSSLCipher.Padding.PKCS5PADDING);
                }
            }

            public CBC(OpenSSLCipher.Padding padding) {
                super(OpenSSLCipher.Mode.CBC, padding);
            }
        }

        public static class CTR extends AES_256 {
            public CTR() {
                super(OpenSSLCipher.Mode.CTR, OpenSSLCipher.Padding.NOPADDING);
            }
        }

        public static class ECB extends AES_256 {

            public static class NoPadding extends ECB {
                public NoPadding() {
                    super(OpenSSLCipher.Padding.NOPADDING);
                }
            }

            public static class PKCS5Padding extends ECB {
                public PKCS5Padding() {
                    super(OpenSSLCipher.Padding.PKCS5PADDING);
                }
            }

            public ECB(OpenSSLCipher.Padding padding) {
                super(OpenSSLCipher.Mode.ECB, padding);
            }
        }

        public AES_256(OpenSSLCipher.Mode mode, OpenSSLCipher.Padding padding) {
            super(mode, padding);
        }

        @Override // org.conscrypt.OpenSSLCipher
        public void checkSupportedKeySize(int i2) {
            if (i2 != 32) {
                throw new InvalidKeyException(AbstractC0000a.m12h("Unsupported key size: ", i2, " bytes"));
            }
        }
    }

    public OpenSSLEvpCipherAES(OpenSSLCipher.Mode mode, OpenSSLCipher.Padding padding) {
        super(mode, padding);
    }

    @Override // org.conscrypt.OpenSSLCipher
    public void checkSupportedMode(OpenSSLCipher.Mode mode) {
        int i2 = C08341.$SwitchMap$org$conscrypt$OpenSSLCipher$Mode[mode.ordinal()];
        if (i2 == 1 || i2 == 2 || i2 == 3) {
            return;
        }
        throw new NoSuchAlgorithmException("Unsupported mode " + mode.toString());
    }

    @Override // org.conscrypt.OpenSSLCipher
    public void checkSupportedPadding(OpenSSLCipher.Padding padding) {
        int i2 = C08341.$SwitchMap$org$conscrypt$OpenSSLCipher$Padding[padding.ordinal()];
        if (i2 == 1 || i2 == 2) {
            return;
        }
        throw new NoSuchPaddingException("Unsupported padding " + padding.toString());
    }

    @Override // org.conscrypt.OpenSSLCipher
    public String getBaseCipherName() {
        return "AES";
    }

    @Override // org.conscrypt.OpenSSLCipher
    public int getCipherBlockSize() {
        return 16;
    }

    @Override // org.conscrypt.OpenSSLEvpCipher
    public String getCipherName(int i2, OpenSSLCipher.Mode mode) {
        return "aes-" + (i2 * 8) + "-" + mode.toString().toLowerCase(Locale.US);
    }
}

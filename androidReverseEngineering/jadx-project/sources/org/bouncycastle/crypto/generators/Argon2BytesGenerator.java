package org.bouncycastle.crypto.generators;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.Blake2bDigest;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Longs;
import org.bouncycastle.util.Pack;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class Argon2BytesGenerator {
    private static final int ARGON2_ADDRESSES_IN_BLOCK = 128;
    private static final int ARGON2_BLOCK_SIZE = 1024;
    private static final int ARGON2_PREHASH_DIGEST_LENGTH = 64;
    private static final int ARGON2_PREHASH_SEED_LENGTH = 72;
    private static final int ARGON2_QWORDS_IN_BLOCK = 128;
    private static final int ARGON2_SYNC_POINTS = 4;
    private static final long M32L = 4294967295L;
    private static final int MAX_PARALLELISM = 16777216;
    private static final int MIN_ITERATIONS = 1;
    private static final int MIN_OUTLEN = 4;
    private static final int MIN_PARALLELISM = 1;
    private static final byte[] ZERO_BYTES = new byte[4];
    private int laneLength;
    private Block[] memory;
    private Argon2Parameters parameters;
    private int segmentLength;

    public static class Block {
        private static final int SIZE = 128;

        /* renamed from: v */
        private final long[] f1234v;

        private Block() {
            this.f1234v = new long[128];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void copyBlock(Block block) {
            System.arraycopy(block.f1234v, 0, this.f1234v, 0, 128);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void xor(Block block, Block block2) {
            long[] jArr = this.f1234v;
            long[] jArr2 = block.f1234v;
            long[] jArr3 = block2.f1234v;
            for (int i2 = 0; i2 < 128; i2++) {
                jArr[i2] = jArr2[i2] ^ jArr3[i2];
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void xorWith(Block block) {
            long[] jArr = this.f1234v;
            long[] jArr2 = block.f1234v;
            for (int i2 = 0; i2 < 128; i2++) {
                jArr[i2] = jArr[i2] ^ jArr2[i2];
            }
        }

        public Block clear() {
            Arrays.fill(this.f1234v, 0L);
            return this;
        }

        public void fromBytes(byte[] bArr) {
            if (bArr.length < 1024) {
                throw new IllegalArgumentException("input shorter than blocksize");
            }
            Pack.littleEndianToLong(bArr, 0, this.f1234v);
        }

        public void toBytes(byte[] bArr) {
            if (bArr.length < 1024) {
                throw new IllegalArgumentException("output shorter than blocksize");
            }
            Pack.longToLittleEndian(this.f1234v, bArr, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void xorWith(Block block, Block block2) {
            long[] jArr = this.f1234v;
            long[] jArr2 = block.f1234v;
            long[] jArr3 = block2.f1234v;
            for (int i2 = 0; i2 < 128; i2++) {
                jArr[i2] = jArr[i2] ^ (jArr2[i2] ^ jArr3[i2]);
            }
        }
    }

    public static class FillBlock {

        /* renamed from: R */
        Block f1235R;

        /* renamed from: Z */
        Block f1236Z;
        Block addressBlock;
        Block inputBlock;

        private FillBlock() {
            this.f1235R = new Block();
            this.f1236Z = new Block();
            this.addressBlock = new Block();
            this.inputBlock = new Block();
        }

        private void applyBlake() {
            for (int i2 = 0; i2 < 8; i2++) {
                int i3 = i2 * 16;
                Argon2BytesGenerator.roundFunction(this.f1236Z, i3, i3 + 1, i3 + 2, i3 + 3, i3 + 4, i3 + 5, i3 + 6, i3 + 7, i3 + 8, i3 + 9, i3 + 10, i3 + 11, i3 + 12, i3 + 13, i3 + 14, i3 + 15);
            }
            for (int i4 = 0; i4 < 8; i4++) {
                int i5 = i4 * 2;
                Argon2BytesGenerator.roundFunction(this.f1236Z, i5, i5 + 1, i5 + 16, i5 + 17, i5 + 32, i5 + 33, i5 + 48, i5 + 49, i5 + 64, i5 + 65, i5 + 80, i5 + 81, i5 + 96, i5 + 97, i5 + 112, i5 + 113);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fillBlock(Block block, Block block2) {
            this.f1236Z.copyBlock(block);
            applyBlake();
            block2.xor(block, this.f1236Z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fillBlockWithXor(Block block, Block block2, Block block3) {
            this.f1235R.xor(block, block2);
            this.f1236Z.copyBlock(this.f1235R);
            applyBlake();
            block3.xorWith(this.f1235R, this.f1236Z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fillBlock(Block block, Block block2, Block block3) {
            this.f1235R.xor(block, block2);
            this.f1236Z.copyBlock(this.f1235R);
            applyBlake();
            block3.xor(this.f1235R, this.f1236Z);
        }
    }

    public static class Position {
        int lane;
        int pass;
        int slice;
    }

    /* renamed from: F */
    private static void m1222F(long[] jArr, int i2, int i3, int i4, int i5) {
        quarterRound(jArr, i2, i3, i5, 32);
        quarterRound(jArr, i4, i5, i3, 24);
        quarterRound(jArr, i2, i3, i5, 16);
        quarterRound(jArr, i4, i5, i3, 63);
    }

    private static void addByteString(byte[] bArr, Digest digest, byte[] bArr2) {
        if (bArr2 == null) {
            digest.update(ZERO_BYTES, 0, 4);
            return;
        }
        Pack.intToLittleEndian(bArr2.length, bArr, 0);
        digest.update(bArr, 0, 4);
        digest.update(bArr2, 0, bArr2.length);
    }

    private void digest(byte[] bArr, byte[] bArr2, int i2, int i3) {
        Block block = this.memory[this.laneLength - 1];
        for (int i4 = 1; i4 < this.parameters.getLanes(); i4++) {
            int i5 = this.laneLength;
            block.xorWith(this.memory[(i5 - 1) + (i4 * i5)]);
        }
        block.toBytes(bArr);
        hash(bArr, bArr2, i2, i3);
    }

    private void doInit(Argon2Parameters argon2Parameters) {
        int memory = argon2Parameters.getMemory();
        if (memory < argon2Parameters.getLanes() * 8) {
            memory = argon2Parameters.getLanes() * 8;
        }
        int lanes = memory / (argon2Parameters.getLanes() * 4);
        this.segmentLength = lanes;
        this.laneLength = lanes * 4;
        initMemory(argon2Parameters.getLanes() * 4 * lanes);
    }

    private void fillFirstBlocks(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[72];
        System.arraycopy(bArr2, 0, bArr3, 0, 64);
        bArr3[64] = 1;
        for (int i2 = 0; i2 < this.parameters.getLanes(); i2++) {
            Pack.intToLittleEndian(i2, bArr2, 68);
            Pack.intToLittleEndian(i2, bArr3, 68);
            hash(bArr2, bArr, 0, 1024);
            this.memory[(this.laneLength * i2) + 0].fromBytes(bArr);
            hash(bArr3, bArr, 0, 1024);
            this.memory[(this.laneLength * i2) + 1].fromBytes(bArr);
        }
    }

    private void fillMemoryBlocks() {
        FillBlock fillBlock = new FillBlock();
        Position position = new Position();
        for (int i2 = 0; i2 < this.parameters.getIterations(); i2++) {
            position.pass = i2;
            for (int i3 = 0; i3 < 4; i3++) {
                position.slice = i3;
                for (int i4 = 0; i4 < this.parameters.getLanes(); i4++) {
                    position.lane = i4;
                    fillSegment(fillBlock, position);
                }
            }
        }
    }

    private void fillSegment(FillBlock fillBlock, Position position) {
        Block block;
        Block block2;
        boolean isDataIndependentAddressing = isDataIndependentAddressing(position);
        int startingIndex = getStartingIndex(position);
        int i2 = (position.slice * this.segmentLength) + (position.lane * this.laneLength) + startingIndex;
        int prevOffset = getPrevOffset(i2);
        if (isDataIndependentAddressing) {
            Block clear = fillBlock.addressBlock.clear();
            Block clear2 = fillBlock.inputBlock.clear();
            initAddressBlocks(fillBlock, position, clear2, clear);
            block = clear;
            block2 = clear2;
        } else {
            block = null;
            block2 = null;
        }
        boolean isWithXor = isWithXor(position);
        int i3 = startingIndex;
        int i4 = prevOffset;
        while (true) {
            int i5 = i2;
            if (i3 >= this.segmentLength) {
                return;
            }
            long pseudoRandom = getPseudoRandom(fillBlock, i3, block, block2, i4, isDataIndependentAddressing);
            int refLane = getRefLane(position, pseudoRandom);
            int refColumn = getRefColumn(position, i3, pseudoRandom, refLane == position.lane);
            Block[] blockArr = this.memory;
            Block block3 = blockArr[i4];
            Block block4 = blockArr[(this.laneLength * refLane) + refColumn];
            Block block5 = blockArr[i5];
            if (isWithXor) {
                fillBlock.fillBlockWithXor(block3, block4, block5);
            } else {
                fillBlock.fillBlock(block3, block4, block5);
            }
            i2 = i5 + 1;
            i3++;
            i4 = i5;
        }
    }

    private int getPrevOffset(int i2) {
        return i2 % this.laneLength == 0 ? (i2 + r0) - 1 : i2 - 1;
    }

    private long getPseudoRandom(FillBlock fillBlock, int i2, Block block, Block block2, int i3, boolean z2) {
        if (!z2) {
            return this.memory[i3].f1234v[0];
        }
        int i4 = i2 % 128;
        if (i4 == 0) {
            nextAddresses(fillBlock, block2, block);
        }
        return block.f1234v[i4];
    }

    private int getRefColumn(Position position, int i2, long j2, boolean z2) {
        int i3;
        int i4;
        int i5 = position.pass;
        int i6 = 0;
        int i7 = position.slice;
        if (i5 != 0) {
            int i8 = this.segmentLength;
            int i9 = this.laneLength;
            int i10 = ((i7 + 1) * i8) % i9;
            int i11 = i9 - i8;
            if (z2) {
                i3 = (i11 + i2) - 1;
            } else {
                i3 = i11 + (i2 != 0 ? 0 : -1);
            }
            i6 = i10;
            i4 = i3;
        } else if (z2) {
            i4 = ((i7 * this.segmentLength) + i2) - 1;
        } else {
            i4 = (i7 * this.segmentLength) + (i2 != 0 ? 0 : -1);
        }
        long j3 = j2 & 4294967295L;
        return ((int) (i6 + ((i4 - 1) - ((i4 * ((j3 * j3) >>> 32)) >>> 32)))) % this.laneLength;
    }

    private int getRefLane(Position position, long j2) {
        int lanes = (int) ((j2 >>> 32) % this.parameters.getLanes());
        return (position.pass == 0 && position.slice == 0) ? position.lane : lanes;
    }

    private static int getStartingIndex(Position position) {
        return (position.pass == 0 && position.slice == 0) ? 2 : 0;
    }

    private void hash(byte[] bArr, byte[] bArr2, int i2, int i3) {
        byte[] bArr3 = new byte[4];
        Pack.intToLittleEndian(i3, bArr3, 0);
        if (i3 <= 64) {
            Blake2bDigest blake2bDigest = new Blake2bDigest(i3 * 8);
            blake2bDigest.update(bArr3, 0, 4);
            blake2bDigest.update(bArr, 0, bArr.length);
            blake2bDigest.doFinal(bArr2, i2);
            return;
        }
        Blake2bDigest blake2bDigest2 = new Blake2bDigest(512);
        byte[] bArr4 = new byte[64];
        blake2bDigest2.update(bArr3, 0, 4);
        blake2bDigest2.update(bArr, 0, bArr.length);
        blake2bDigest2.doFinal(bArr4, 0);
        System.arraycopy(bArr4, 0, bArr2, i2, 32);
        int i4 = i2 + 32;
        int i5 = 2;
        int i6 = ((i3 + 31) / 32) - 2;
        while (i5 <= i6) {
            blake2bDigest2.update(bArr4, 0, 64);
            blake2bDigest2.doFinal(bArr4, 0);
            System.arraycopy(bArr4, 0, bArr2, i4, 32);
            i5++;
            i4 += 32;
        }
        Blake2bDigest blake2bDigest3 = new Blake2bDigest((i3 - (i6 * 32)) * 8);
        blake2bDigest3.update(bArr4, 0, 64);
        blake2bDigest3.doFinal(bArr2, i4);
    }

    private void initAddressBlocks(FillBlock fillBlock, Position position, Block block, Block block2) {
        block.f1234v[0] = intToLong(position.pass);
        block.f1234v[1] = intToLong(position.lane);
        block.f1234v[2] = intToLong(position.slice);
        block.f1234v[3] = intToLong(this.memory.length);
        block.f1234v[4] = intToLong(this.parameters.getIterations());
        block.f1234v[5] = intToLong(this.parameters.getType());
        if (position.pass == 0 && position.slice == 0) {
            nextAddresses(fillBlock, block, block2);
        }
    }

    private void initMemory(int i2) {
        this.memory = new Block[i2];
        int i3 = 0;
        while (true) {
            Block[] blockArr = this.memory;
            if (i3 >= blockArr.length) {
                return;
            }
            blockArr[i3] = new Block();
            i3++;
        }
    }

    private void initialize(byte[] bArr, byte[] bArr2, int i2) {
        Blake2bDigest blake2bDigest = new Blake2bDigest(512);
        Pack.intToLittleEndian(new int[]{this.parameters.getLanes(), i2, this.parameters.getMemory(), this.parameters.getIterations(), this.parameters.getVersion(), this.parameters.getType()}, bArr, 0);
        blake2bDigest.update(bArr, 0, 24);
        addByteString(bArr, blake2bDigest, bArr2);
        addByteString(bArr, blake2bDigest, this.parameters.getSalt());
        addByteString(bArr, blake2bDigest, this.parameters.getSecret());
        addByteString(bArr, blake2bDigest, this.parameters.getAdditional());
        byte[] bArr3 = new byte[72];
        blake2bDigest.doFinal(bArr3, 0);
        fillFirstBlocks(bArr, bArr3);
    }

    private long intToLong(int i2) {
        return i2 & 4294967295L;
    }

    private boolean isDataIndependentAddressing(Position position) {
        if (this.parameters.getType() != 1) {
            return this.parameters.getType() == 2 && position.pass == 0 && position.slice < 2;
        }
        return true;
    }

    private boolean isWithXor(Position position) {
        return (position.pass == 0 || this.parameters.getVersion() == 16) ? false : true;
    }

    private void nextAddresses(FillBlock fillBlock, Block block, Block block2) {
        long[] jArr = block.f1234v;
        jArr[6] = jArr[6] + 1;
        fillBlock.fillBlock(block, block2);
        fillBlock.fillBlock(block2, block2);
    }

    private static void quarterRound(long[] jArr, int i2, int i3, int i4, int i5) {
        long j2 = jArr[i2];
        long j3 = jArr[i3];
        long j4 = jArr[i4];
        long m1007a = AbstractC0413b.m1007a((j2 & 4294967295L) * 2, j3 & 4294967295L, j3, j2);
        long rotateRight = Longs.rotateRight(j4 ^ m1007a, i5);
        jArr[i2] = m1007a;
        jArr[i4] = rotateRight;
    }

    private void reset() {
        if (this.memory == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            Block[] blockArr = this.memory;
            if (i2 >= blockArr.length) {
                return;
            }
            Block block = blockArr[i2];
            if (block != null) {
                block.clear();
            }
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void roundFunction(Block block, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17) {
        long[] jArr = block.f1234v;
        m1222F(jArr, i2, i6, i10, i14);
        m1222F(jArr, i3, i7, i11, i15);
        m1222F(jArr, i4, i8, i12, i16);
        m1222F(jArr, i5, i9, i13, i17);
        m1222F(jArr, i2, i7, i12, i17);
        m1222F(jArr, i3, i8, i13, i14);
        m1222F(jArr, i4, i9, i10, i15);
        m1222F(jArr, i5, i6, i11, i16);
    }

    public int generateBytes(byte[] bArr, byte[] bArr2) {
        return generateBytes(bArr, bArr2, 0, bArr2.length);
    }

    public void init(Argon2Parameters argon2Parameters) {
        this.parameters = argon2Parameters;
        if (argon2Parameters.getLanes() < 1) {
            throw new IllegalStateException("lanes must be greater than 1");
        }
        if (argon2Parameters.getLanes() > 16777216) {
            throw new IllegalStateException("lanes must be less than 16777216");
        }
        if (argon2Parameters.getMemory() >= argon2Parameters.getLanes() * 2) {
            if (argon2Parameters.getIterations() < 1) {
                throw new IllegalStateException("iterations is less than: 1");
            }
            doInit(argon2Parameters);
        } else {
            throw new IllegalStateException("memory is less than: " + (argon2Parameters.getLanes() * 2) + " expected " + (argon2Parameters.getLanes() * 2));
        }
    }

    public int generateBytes(byte[] bArr, byte[] bArr2, int i2, int i3) {
        if (i3 < 4) {
            throw new IllegalStateException("output length less than 4");
        }
        byte[] bArr3 = new byte[1024];
        initialize(bArr3, bArr, i3);
        fillMemoryBlocks();
        digest(bArr3, bArr2, i2, i3);
        reset();
        return i3;
    }

    public int generateBytes(char[] cArr, byte[] bArr) {
        return generateBytes(this.parameters.getCharToByteConverter().convert(cArr), bArr);
    }

    public int generateBytes(char[] cArr, byte[] bArr, int i2, int i3) {
        return generateBytes(this.parameters.getCharToByteConverter().convert(cArr), bArr, i2, i3);
    }
}

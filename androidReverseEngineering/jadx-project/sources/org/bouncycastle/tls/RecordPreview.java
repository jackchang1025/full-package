package org.bouncycastle.tls;

/* loaded from: classes.dex */
public final class RecordPreview {
    private final int contentLimit;
    private final int recordSize;

    public RecordPreview(int i2, int i3) {
        this.recordSize = i2;
        this.contentLimit = i3;
    }

    public static RecordPreview combineAppData(RecordPreview recordPreview, RecordPreview recordPreview2) {
        return new RecordPreview(recordPreview2.getRecordSize() + recordPreview.getRecordSize(), recordPreview2.getContentLimit() + recordPreview.getContentLimit());
    }

    public static RecordPreview extendRecordSize(RecordPreview recordPreview, int i2) {
        return new RecordPreview(recordPreview.getRecordSize() + i2, recordPreview.getContentLimit());
    }

    public int getApplicationDataLimit() {
        return this.contentLimit;
    }

    public int getContentLimit() {
        return this.contentLimit;
    }

    public int getRecordSize() {
        return this.recordSize;
    }
}

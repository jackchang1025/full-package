package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ar0 {

    /* renamed from: a0 */
    public int f45596a0;

    /* renamed from: a1 */
    public int f45597a1;

    /* renamed from: a2 */
    public int f45598a2;

    /* renamed from: a3 */
    public int f45599a3;

    /* renamed from: a4 */
    public int f45600a4;

    /* renamed from: a5 */
    public boolean f45601a5;

    /* renamed from: a6 */
    public boolean f45602a6;

    /* renamed from: a7 */
    public boolean f45603a7;

    /* renamed from: a8 */
    public boolean f45604a8;

    /* renamed from: a9 */
    public boolean f45605a9;

    /* renamed from: b0 */
    public boolean f45606b0;

    /* renamed from: b1 */
    public int f45607b1;

    /* renamed from: b2 */
    public long f45608b2;

    /* renamed from: b3 */
    public int f45609b3;

    /* renamed from: a0 */
    public final void m210499a0(int i) {
        if ((this.f45599a3 & i) != 0) {
            return;
        }
        throw new IllegalStateException("Layout state should be one of " + Integer.toBinaryString(i) + " but it is " + Integer.toBinaryString(this.f45599a3));
    }

    /* renamed from: a1 */
    public final int m210500a1() {
        return this.f45602a6 ? this.f45597a1 - this.f45598a2 : this.f45600a4;
    }

    public final String toString() {
        return "State{mTargetPosition=" + this.f45596a0 + ", mData=null, mItemCount=" + this.f45600a4 + ", mIsMeasuring=" + this.f45604a8 + ", mPreviousLayoutItemCount=" + this.f45597a1 + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.f45598a2 + ", mStructureChanged=" + this.f45601a5 + ", mInPreLayout=" + this.f45602a6 + ", mRunSimpleAnimations=" + this.f45605a9 + ", mRunPredictiveAnimations=" + this.f45606b0 + '}';
    }
}

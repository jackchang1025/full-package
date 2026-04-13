package org.bouncycastle.math.field;

/* loaded from: classes.dex */
public interface ExtensionField extends FiniteField {
    int getDegree();

    FiniteField getSubfield();
}

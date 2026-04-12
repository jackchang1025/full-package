package p000;

import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: kc */
/* loaded from: classes2.dex */
public class C0753kc implements AlgorithmParameterSpec {
    private final List<String> algorithmNames;
    private final List<AlgorithmParameterSpec> parameterSpecs;

    /* renamed from: kc$a0 */
    public static class a0 {
        private List<String> algorithmNames = new ArrayList();
        private List<AlgorithmParameterSpec> parameterSpecs = new ArrayList();

        public a0 add(String str) {
            this.algorithmNames.add(str);
            this.parameterSpecs.add(null);
            return this;
        }

        public C0753kc build() {
            if (this.algorithmNames.isEmpty()) {
                throw new IllegalStateException("cannot call build with no algorithm names added");
            }
            return new C0753kc(this);
        }

        public a0 add(String str, AlgorithmParameterSpec algorithmParameterSpec) {
            this.algorithmNames.add(str);
            this.parameterSpecs.add(algorithmParameterSpec);
            return this;
        }
    }

    public C0753kc(a0 a0Var) {
        this.algorithmNames = Collections.unmodifiableList(new ArrayList(a0Var.algorithmNames));
        this.parameterSpecs = Collections.unmodifiableList(new ArrayList(a0Var.parameterSpecs));
    }

    public List<String> getAlgorithmNames() {
        return this.algorithmNames;
    }

    public List<AlgorithmParameterSpec> getParameterSpecs() {
        return this.parameterSpecs;
    }
}

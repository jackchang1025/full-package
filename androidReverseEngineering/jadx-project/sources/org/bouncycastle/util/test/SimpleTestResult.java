package org.bouncycastle.util.test;

import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class SimpleTestResult implements TestResult {
    private static final String SEPARATOR = Strings.lineSeparator();
    private Throwable exception;
    private String message;
    private boolean success;

    public SimpleTestResult(boolean z2, String str) {
        this.success = z2;
        this.message = str;
    }

    public static TestResult failed(Test test, String str) {
        return new SimpleTestResult(false, test.getName() + ": " + str);
    }

    public static String failedMessage(String str, String str2, String str3, String str4) {
        StringBuffer stringBuffer = new StringBuffer(str);
        stringBuffer.append(" failing ");
        stringBuffer.append(str2);
        String str5 = SEPARATOR;
        stringBuffer.append(str5);
        stringBuffer.append("    expected: ");
        stringBuffer.append(str3);
        stringBuffer.append(str5);
        stringBuffer.append("    got     : ");
        stringBuffer.append(str4);
        return stringBuffer.toString();
    }

    public static TestResult successful(Test test, String str) {
        return new SimpleTestResult(true, test.getName() + ": " + str);
    }

    @Override // org.bouncycastle.util.test.TestResult
    public Throwable getException() {
        return this.exception;
    }

    @Override // org.bouncycastle.util.test.TestResult
    public boolean isSuccessful() {
        return this.success;
    }

    @Override // org.bouncycastle.util.test.TestResult
    public String toString() {
        return this.message;
    }

    public SimpleTestResult(boolean z2, String str, Throwable th) {
        this.success = z2;
        this.message = str;
        this.exception = th;
    }

    public static TestResult failed(Test test, String str, Object obj, Object obj2) {
        StringBuilder m20p = AbstractC0000a.m20p(str);
        String str2 = SEPARATOR;
        m20p.append(str2);
        m20p.append("Expected: ");
        m20p.append(obj);
        m20p.append(str2);
        m20p.append("Found   : ");
        m20p.append(obj2);
        return failed(test, m20p.toString());
    }

    public static TestResult failed(Test test, String str, Throwable th) {
        return new SimpleTestResult(false, test.getName() + ": " + str, th);
    }
}

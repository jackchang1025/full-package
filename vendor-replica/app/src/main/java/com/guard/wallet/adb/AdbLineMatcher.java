package com.guard.wallet.adb;

/**
 * ADB shell 输出行匹配器，用于判断命令执行结果。
 * vendor 原始路径: i/a.java
 */
public class AdbLineMatcher {
    private final String pattern;
    private final boolean contains;
    private final int resultCode;

    public AdbLineMatcher(String pattern, boolean contains, int resultCode) {
        this.pattern = pattern;
        this.contains = contains;
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public String getPattern() {
        return this.pattern;
    }

    public boolean isContainsMode() {
        return this.contains;
    }

    public boolean matches(String line) {
        if (line == null || this.pattern == null) {
            return false;
        }
        return this.contains ? line.contains(this.pattern) : line.equals(this.pattern);
    }
}

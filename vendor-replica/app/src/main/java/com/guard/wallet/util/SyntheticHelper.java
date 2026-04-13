package com.guard.wallet.util;

import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.filter.CombineFilter;
import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.security.GeneralSecurityException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERSequence;

/**
 * 全局合成工具类（ProGuard 合并生成）。
 *
 * <p>这是编译器/ProGuard 在 DEX 优化阶段自动生成的 synthetic class，
 * 将多个类中重复出现的小方法合并到一个公共类中以减少方法数和 DEX 体积。
 * 原始代码中这些方法分散在不同的业务类中，ProGuard 提取后统一放到此类。</p>
 *
 * <p><b>vendor 原始路径:</b> {@code a/a.java}（包名和类名均被 ProGuard 混淆为单字符）</p>
 *
 * <h3>方法分组</h3>
 * <ul>
 *   <li><b>字符串拼接</b> — {@link #concat}, {@link #concat3}, {@link #join},
 *       {@link #concatInt}, {@link #concatIntStr}, {@link #concatClassName},
 *       {@link #concatIOException}, {@link #concatException}</li>
 *   <li><b>StringBuilder 操作</b> — {@link #appendIntStr}, {@link #appendStrStr},
 *       {@link #toBuilder}, {@link #toBuilder2}, {@link #toBuilder3},
 *       {@link #builderIntStr}</li>
 *   <li><b>异常消息格式化</b> — {@link #appendIOExMsg}, {@link #appendExMsg},
 *       {@link #appendSecurityExMsg}, {@link #appendExToString}</li>
 *   <li><b>CombineFilter/StringCondition 构建</b> — {@link #initFilterConditions},
 *       {@link #addAndCreateCondition}</li>
 *   <li><b>WebSocket 帧类型</b> — {@link #frameTypeName}, {@link #frameTypeNameSafe}</li>
 *   <li><b>加密/证书辅助</b> — {@link #singleEnumeration}, {@link #addSequenceAndReset}</li>
 *   <li><b>合成空方法/类型检查</b> — {@link #noOpA}, {@link #noOpB}, {@link #noOpC},
 *       {@link #noOpV}, {@link #checkCastNull}, {@link #checkCastByteChannel}</li>
 * </ul>
 *
 * <h3>vendor 原始方法名映射</h3>
 * <pre>
 * vendor a/a.A()  → noOpA()                vendor a/a.n()  → appendStrStr()
 * vendor a/a.B()  → noOpB()                vendor a/a.o()  → appendSecurityExMsg()
 * vendor a/a.C()  → noOpC()                vendor a/a.p()  → toBuilder()
 * vendor a/a.D()  → frameTypeName()        vendor a/a.q()  → builderIntStr()
 * vendor a/a.E()  → frameTypeNameSafe()    vendor a/a.r()  → toBuilder2()
 * vendor a/a.a()  → sum()                  vendor a/a.s()  → toBuilder3()
 * vendor a/a.b()  → addAndCreateCondition  vendor a/a.t()  → singleEnumeration()
 * vendor a/a.c()  → initFilterConditions() vendor a/a.u()  → addSequenceAndReset()
 * vendor a/a.d()  → appendIOExMsg()        vendor a/a.v()  → noOpV()
 * vendor a/a.e()  → appendExMsg()          vendor a/a.w()  → checkCastNull()
 * vendor a/a.f()  → concatClassName()      vendor a/a.x()  → checkCastByteChannel()
 * vendor a/a.g()  → concatInt()            vendor a/a.y()  → appendExToString()
 * vendor a/a.h()  → concatIntStr()         vendor a/a.z()  → join()
 * vendor a/a.i()  → concatIOException()
 * vendor a/a.j()  → concatException()
 * vendor a/a.k()  → concat()
 * vendor a/a.l()  → concat3()
 * vendor a/a.m()  → appendIntStr()
 * </pre>
 */
public abstract class SyntheticHelper {

    // ═══════════════════════════════════════════════════════════════
    //  合成空方法（编译器生成，用于 lambda/方法引用的桥接占位）
    // ═══════════════════════════════════════════════════════════════

    /** 合成空方法 A — 编译器生成的 no-op 桥接 */
    public static void noOpA() {
    }

    /** 合成空方法 B — 编译器生成的 no-op 桥接 */
    public static void noOpB() {
    }

    /** 合成空方法 C — 编译器生成的 no-op 桥接 */
    public static void noOpC() {
    }

    /** 合成空方法 V — 编译器生成的 no-op 桥接 */
    public static void noOpV() {
    }

    // ═══════════════════════════════════════════════════════════════
    //  WebSocket 帧类型名称
    // ═══════════════════════════════════════════════════════════════

    /**
     * 将 WebSocket 帧类型码转为名称字符串。
     * 未知类型抛出 NullPointerException（{@code throw null}）。
     *
     * @param opcode 帧类型码: 1=CONTINUOUS, 2=TEXT, 3=BINARY, 4=PING, 5=PONG, 6=CLOSING
     * @return 帧类型名称
     */
    public static String frameTypeName(int opcode) {
        if (opcode == 1) return "CONTINUOUS";
        if (opcode == 2) return "TEXT";
        if (opcode == 3) return "BINARY";
        if (opcode == 4) return "PING";
        if (opcode == 5) return "PONG";
        if (opcode == 6) return "CLOSING";
        throw null;
    }

    /**
     * 将 WebSocket 帧类型码转为名称字符串（安全版本）。
     * 未知类型返回 {@code "null"} 而非抛异常。
     *
     * @param opcode 帧类型码
     * @return 帧类型名称，未知类型返回 "null"
     */
    public static String frameTypeNameSafe(int opcode) {
        return opcode == 1 ? "CONTINUOUS"
             : opcode == 2 ? "TEXT"
             : opcode == 3 ? "BINARY"
             : opcode == 4 ? "PING"
             : opcode == 5 ? "PONG"
             : opcode == 6 ? "CLOSING"
             : "null";
    }

    // ═══════════════════════════════════════════════════════════════
    //  整数运算
    // ═══════════════════════════════════════════════════════════════

    /** 返回 4 个整数之和 */
    public static int sum(int a, int b, int c, int d) {
        return a + b + c + d;
    }

    // ═══════════════════════════════════════════════════════════════
    //  CombineFilter / StringCondition 构建
    // ═══════════════════════════════════════════════════════════════

    /**
     * 初始化 filter 的 stringConditions 列表，并创建第一个条件。
     *
     * @param filter   目标 CombineFilter
     * @param property 条件属性名（如 "className", "text", "id"）
     * @param equals   条件匹配值
     * @return 新创建的 StringCondition（尚未添加到 filter，由调用方决定后续操作）
     */
    public static StringCondition initFilterConditions(CombineFilter filter, String property, String equals) {
        filter.setStringConditions(new LinkedList<>());
        StringCondition condition = new StringCondition();
        condition.setProperty(property);
        condition.setEquals(equals);
        return condition;
    }

    /**
     * 将现有条件添加到 filter，然后创建一个新条件。
     *
     * @param filter            目标 CombineFilter
     * @param existingCondition 要追加的已有条件
     * @param property          新条件的属性名
     * @param equals            新条件的匹配值
     * @return 新创建的 StringCondition
     */
    public static StringCondition addAndCreateCondition(CombineFilter filter, StringCondition existingCondition, String property, String equals) {
        filter.getStringConditions().add(existingCondition);
        StringCondition condition = new StringCondition();
        condition.setProperty(property);
        condition.setEquals(equals);
        return condition;
    }

    // ═══════════════════════════════════════════════════════════════
    //  异常消息格式化
    // ═══════════════════════════════════════════════════════════════

    /** 将 IOException 的 message 追加到 StringBuilder 并返回完整字符串 */
    public static String appendIOExMsg(IOException exception, StringBuilder sb) {
        sb.append(exception.getMessage());
        return sb.toString();
    }

    /** 将 Exception 的 message 追加到 StringBuilder 并返回完整字符串 */
    public static String appendExMsg(Exception exception, StringBuilder sb) {
        sb.append(exception.getMessage());
        return sb.toString();
    }

    /** 将 GeneralSecurityException 的 message 追加到 StringBuilder 并返回完整字符串 */
    public static String appendSecurityExMsg(GeneralSecurityException exception, StringBuilder sb) {
        sb.append(exception.getMessage());
        return sb.toString();
    }

    /** 将 Exception 的 toString() 追加到 StringBuilder 并返回完整字符串 */
    public static String appendExToString(Exception exception, StringBuilder sb) {
        sb.append(exception.toString());
        return sb.toString();
    }

    // ═══════════════════════════════════════════════════════════════
    //  字符串拼接
    // ═══════════════════════════════════════════════════════════════

    /** 拼接两个字符串 */
    public static String concat(String first, String second) {
        return first + second;
    }

    /** 拼接三个字符串 */
    public static String concat3(String first, String second, String third) {
        return first + second + third;
    }

    /** 拼接两个字符串（与 concat 功能相同，来自不同的 synthetic 调用点） */
    public static String join(String first, String second) {
        return first + second;
    }

    /** 在前缀字符串后拼接一个整数 */
    public static String concatInt(String prefix, int value) {
        return prefix + value;
    }

    /** 拼接: 前缀 + 整数 + 后缀 */
    public static String concatIntStr(String prefix, int value, String suffix) {
        return prefix + value + suffix;
    }

    /** 在前缀字符串后拼接对象的类名 */
    public static String concatClassName(Object obj, String prefix) {
        return prefix.concat(obj.getClass().getName());
    }

    /** 拼接字符串与 IOException */
    public static String concatIOException(String prefix, IOException exception) {
        return prefix + exception;
    }

    /** 拼接字符串与 Exception */
    public static String concatException(String prefix, Exception exception) {
        return prefix + exception;
    }

    // ═══════════════════════════════════════════════════════════════
    //  StringBuilder 操作
    // ═══════════════════════════════════════════════════════════════

    /** 向 StringBuilder 追加整数和字符串，返回最终字符串 */
    public static String appendIntStr(StringBuilder sb, int value, String suffix) {
        sb.append(value);
        sb.append(suffix);
        return sb.toString();
    }

    /** 向 StringBuilder 追加两个字符串，返回最终字符串 */
    public static String appendStrStr(StringBuilder sb, String separator, String name) {
        sb.append(separator);
        sb.append(name);
        return sb.toString();
    }

    /** 从单个字符串创建 StringBuilder */
    public static StringBuilder toBuilder(String initial) {
        StringBuilder sb = new StringBuilder();
        sb.append(initial);
        return sb;
    }

    /** 用两个字符串创建 StringBuilder */
    public static StringBuilder toBuilder2(String first, String second) {
        StringBuilder sb = new StringBuilder();
        sb.append(first);
        sb.append(second);
        return sb;
    }

    /** 用三个字符串创建 StringBuilder */
    public static StringBuilder toBuilder3(String first, String second, String third) {
        StringBuilder sb = new StringBuilder(first);
        sb.append(second);
        sb.append(third);
        return sb;
    }

    /** 用 前缀 + 整数 + 后缀 创建 StringBuilder */
    public static StringBuilder builderIntStr(String prefix, int value, String suffix) {
        StringBuilder sb = new StringBuilder(prefix);
        sb.append(value);
        sb.append(suffix);
        return sb;
    }

    // ═══════════════════════════════════════════════════════════════
    //  加密 / 证书辅助
    // ═══════════════════════════════════════════════════════════════

    /**
     * 创建包含单个元素的 Enumeration。
     * <p>ADAPT: vendor 使用 android.sun.security.x509.AttributeNameEnumeration（Android 内部 API）。
     * 此处用 Vector 替代，功能等价（AttributeNameEnumeration 内部就是 Vector 子类）。</p>
     */
    public static Enumeration singleEnumeration(String element) {
        Vector<String> vector = new Vector<>();
        vector.addElement(element);
        return vector.elements();
    }

    /** 将当前 vector 封装为 DERSequence 追加到目标 vector，返回新的空 vector */
    public static ASN1EncodableVector addSequenceAndReset(ASN1EncodableVector source, ASN1EncodableVector target) {
        target.add(new DERSequence(source));
        return new ASN1EncodableVector();
    }

    // ═══════════════════════════════════════════════════════════════
    //  合成类型检查（编译器生成的 cast 辅助）
    // ═══════════════════════════════════════════════════════════════

    /** 合成空值检查 — 非空时抛出 ClassCastException（编译器类型擦除辅助） */
    public static void checkCastNull(Object obj) {
        if (obj != null) {
            throw new ClassCastException();
        }
    }

    /** 合成 ByteChannel 空值检查 — 非空时抛出 ClassCastException */
    public static void checkCastByteChannel(ByteChannel channel) {
        if (channel != null) {
            throw new ClassCastException();
        }
    }
}

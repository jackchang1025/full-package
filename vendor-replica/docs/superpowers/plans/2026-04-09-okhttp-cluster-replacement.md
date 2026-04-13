# OkHttp/Okio Obfuscated Cluster Replacement Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Replace 76 obfuscated OkHttp/Okio files (a1/p0/q0/r0/s0/t0/v0/w0/x0/z0/o) with real `okhttp3.*` and `okio.*` library imports, updating all 220+ business code references.

**Architecture:** The real dependencies already exist in build.gradle (`okhttp:4.12.0`, `okio:1.17.6`). Strategy: delete obfuscated packages bottom-up (zero-ref internals first → Okio stubs → OkHttp internals → OkHttp API surface → business code callers). Each task is atomic with build verification.

**Tech Stack:** Java 8, OkHttp 4.12.0 (okhttp3.*), Okio 1.17.6 (okio.*), Android SDK 34, Gradle 8.5

**Vendor Reference:** `/home/code/php/project/full-package/androidReverseEngineering/src/`

---

## Cluster Overview

```
76 files, 3660 LOC across 11 directories
├─ a1/  (14 files, 769L)  — Okio stubs        [12 business refs]
├─ p0/  (41 files, 1948L) — OkHttp core        [220 business refs] ★
├─ q0/  (3 files, 168L)   — OkHttp utils       [8 business refs]
├─ r0/  (1 file, 23L)     — Interceptor         [0 business refs]
├─ s0/  (9 files, 416L)   — Connection mgmt     [12 business refs]
├─ t0/  (5 files, 175L)   — HTTP codec          [0 business refs]
├─ v0/  (1 file, 4L)      — Marker interface    [0 business refs]
├─ w0/  (1 file, 32L)     — SSL util            [0 business refs]
├─ x0/  (1 file, 20L)     — Proxy selector      [0 business refs]
├─ z0/  (1 file, 13L)     — Hostname verifier   [0 business refs]
└─ o/   (0 files)          — Empty directory     [delete]
```

## Critical Type Mapping (p0 → okhttp3)

| Obfuscated | Real OkHttp Class | Business Refs | Method Mapping |
|-----------|-------------------|---------------|----------------|
| `p0.b0` | `okhttp3.OkHttpClient` | ~30 | `.a`→`.dispatcher()`, `.b`→`.cookieJar()` |
| `p0.e` | `okhttp3.Callback` | ~40 | `.b(Call,IOEx)`→`onFailure()`, `.d(Call,Resp)`→`onResponse()` |
| `p0.e0` | `okhttp3.Call` | ~25 | `.a(cb)`→`.enqueue(cb)`, `.c`→`.request()` |
| `p0.f0` | `okhttp3.Request` | ~20 | `.a`→`.url()`, `.b`→`.method()` |
| `p0.j0` | `okhttp3.Response` | ~20 | `.g`→`.body()`, `.b`→`.code()` |
| `p0.t` | `okhttp3.Request.Builder` | ~15 | `.a(url)`→`.url(url)`, `.b(body)`→`.post(body)` |
| `p0.u` | `okhttp3.HttpUrl` | ~10 | `.a(str)`→`.parse(str)` |
| `p0.s` | `okhttp3.Headers` | ~10 | `.a(name)`→`.get(name)` |
| `p0.x` | `okhttp3.MediaType` | ~15 | `.a(str)`→`.parse(str)` |
| `p0.l0` | `okhttp3.ResponseBody` | ~10 | `.a()`→`.string()`, `.b()`→`.bytes()` |
| `p0.z` | `okhttp3.MultipartBody` | ~5 | `.W()`→`.addFormDataPart()` |
| `p0.n` | `okhttp3.CookieJar` | ~5 | interface |
| `p0.w` | `okhttp3.Interceptor` | ~5 | interface `.a(chain)`→`.intercept(chain)` |
| `p0.k0` | `okhttp3.ResponseBody` impl | ~5 | concrete body |

---

### Task 1: Delete zero-ref packages (o/, v0/, w0/, x0/, z0/)

**Files:**
- Delete: `app/src/main/java/o/` (empty directory)
- Delete: `app/src/main/java/v0/c0.java` (marker interface, 0 refs)
- Delete: `app/src/main/java/w0/i.java` (SSL util, 0 refs)
- Delete: `app/src/main/java/x0/a.java` (proxy selector, 0 refs)
- Delete: `app/src/main/java/z0/c.java` (hostname verifier, 0 refs)

- [ ] **Step 1:** Verify zero external refs for each file
```bash
for pkg in v0 w0 x0 z0; do
  echo "$pkg:"; grep -rn "\b${pkg}\." app/src/main/java/ --include="*.java" | grep -v "/$pkg/" | grep -v ".pending" | head -5
done
```

- [ ] **Step 2:** Check if any p0/s0/t0 files import these packages internally
```bash
grep -rn "import v0\.\|import w0\.\|import x0\.\|import z0\." app/src/main/java/{p0,s0,t0,r0,q0}/ --include="*.java"
```
If found: convert those importers to use inline equivalents or real OkHttp classes.

- [ ] **Step 3:** Delete files and directories
```bash
rm -rf app/src/main/java/o/
rm app/src/main/java/v0/c0.java && rmdir app/src/main/java/v0/
rm app/src/main/java/w0/i.java && rmdir app/src/main/java/w0/
rm app/src/main/java/x0/a.java && rmdir app/src/main/java/x0/
rm app/src/main/java/z0/c.java && rmdir app/src/main/java/z0/
```

- [ ] **Step 4:** Fix any compilation errors from internal imports (p0/s0/t0 may reference deleted types)
- [ ] **Step 5:** Build verify: `./gradlew assembleDebug 2>&1 | tail -3`

---

### Task 2: Delete internal-only packages (r0/, t0/)

**Files:**
- Delete: `app/src/main/java/r0/a.java` (interceptor, 0 business refs)
- Delete: `app/src/main/java/t0/*.java` (5 codec files, 0 business refs)

- [ ] **Step 1:** Verify zero BUSINESS refs (these ARE referenced by p0/s0 internally)
```bash
grep -rn "\br0\.\|import r0\." app/src/main/java/com/guard/wallet/ --include="*.java" | grep -v ".pending"
grep -rn "\bt0\.\|import t0\." app/src/main/java/com/guard/wallet/ --include="*.java" | grep -v ".pending"
```

- [ ] **Step 2:** Find all internal references from p0/s0
```bash
grep -rn "import r0\.\|import t0\.\|\br0\.a\|\bt0\." app/src/main/java/{p0,s0}/ --include="*.java"
```

- [ ] **Step 3:** In p0/s0 files that reference r0/t0: replace with stubs or remove the code blocks. Read each file to understand the usage before deleting.

- [ ] **Step 4:** Delete r0/ and t0/ directories
- [ ] **Step 5:** Build verify

---

### Task 3: Build precise method mapping via javap

**Files:** (research only, no code changes)

- [ ] **Step 1:** Extract real OkHttp 4.12.0 method signatures
```bash
JAR=$(find /root/.gradle/caches -name "okhttp-4.12.0.jar" | head -1)
for cls in OkHttpClient Call Callback Request Response Headers MediaType HttpUrl \
           ResponseBody RequestBody MultipartBody CookieJar Interceptor; do
  echo "=== okhttp3.$cls ===" 
  javap -classpath "$JAR" "okhttp3.$cls" 2>/dev/null | head -30
done
```

- [ ] **Step 2:** Read each p0 file that has business refs, document the EXACT method-to-method mapping
For each p0 class used by business code, create a mapping table:
```
p0.e (Callback):
  void b(p0.e0, IOException) → void onFailure(Call, IOException)
  void d(p0.e0, p0.j0)      → void onResponse(Call, Response)

p0.f0 (Request):
  field a (HttpUrl)     → .url()
  field b (String)      → .method()
  field c (Headers)     → .headers()
  ...
```

- [ ] **Step 3:** Save mapping to a reference doc for subsequent tasks

---

### Task 4: Replace a1/ Okio stubs with real Okio imports (14 files)

**Files:**
- Delete: all 14 `app/src/main/java/a1/*.java` files
- Modify: all files that import a1.* → change to okio.*

- [ ] **Step 1:** Find ALL a1 references in the project
```bash
grep -rn "\ba1\.\|import a1\." app/src/main/java/ --include="*.java" | grep -v "/a1/" | grep -v ".pending"
```

- [ ] **Step 2:** Build a1 → okio type mapping
| a1 class | okio class |
|----------|-----------|
| a1.d | okio.AsyncTimeout |
| a1.e | okio.Buffer |
| a1.f | okio.BufferedSink |
| a1.g | okio.BufferedSource |
| a1.h | okio.ByteString |
| a1.m | okio.Segment |
| a1.s | okio.Sink |
| a1.t | okio.Source |
| a1.v | okio.Timeout |

- [ ] **Step 3:** For each caller that references a1 types: replace type + method calls with real okio equivalents. Since a1 files are STUBS, callers may need adaptation for the real okio API.

- [ ] **Step 4:** Delete all a1/*.java files and a1/ directory
- [ ] **Step 5:** Build verify

---

### Task 5: Replace s0/ connection management (9 files, 12 business refs)

**Files:**
- Delete: all 9 `app/src/main/java/s0/*.java` files
- Modify: 4 business code files that reference s0

- [ ] **Step 1:** Find ALL s0 business refs
```bash
grep -rn "\bs0\.\|import s0\." app/src/main/java/com/guard/wallet/ --include="*.java" | grep -v ".pending"
```

- [ ] **Step 2:** For each business caller, replace s0 types with real okhttp3.internal types or remove the reference entirely (many connection-level details are handled internally by OkHttpClient).

Key mappings:
| s0 class | okhttp3 class | Action |
|----------|--------------|--------|
| s0.l (Transmitter) | internal to OkHttpClient | Remove from business code |
| s0.e (Exchange) | internal to OkHttpClient | Remove from business code |
| s0.h (ConnectionPool) | okhttp3.ConnectionPool | Replace type |
| s0.g (RealConnection) | internal | Remove |

- [ ] **Step 3:** Update or remove s0 references from business code
- [ ] **Step 4:** Delete s0/ directory
- [ ] **Step 5:** Build verify

---

### Task 6: Replace q0/ utilities (3 files, 8 business refs)

**Files:**
- Delete: all 3 `app/src/main/java/q0/*.java` files
- Modify: business files referencing q0

- [ ] **Step 1:** Find q0 business refs and replace:
| q0 class | Replacement |
|----------|-------------|
| q0.a (NamedRunnable) | Inline Runnable with thread naming |
| q0.b (ThreadFactory) | java.util.concurrent.ThreadFactory |
| q0.c (Util) | okhttp3.internal.Util or inline |

- [ ] **Step 2:** `q0.c.d` (empty ResponseBody) is used by p0 — handle in Task 7 when p0 is replaced
- [ ] **Step 3:** Delete q0/ directory
- [ ] **Step 4:** Build verify

---

### Task 7: Replace p0/ callback interfaces (p0.e, p0.n, p0.w) — ~50 business refs

**Files:**
- Delete: `p0/e.java` (Callback), `p0/n.java` (CookieJar), `p0/w.java` (Interceptor), `p0/p.java`, `p0/q.java`, `p0/v.java` (markers)
- Modify: ~25 business files that implement p0.e (HTTP callbacks)

- [ ] **Step 1:** Replace p0.e (Callback) across ALL 20+ callback files:
```java
// OLD:
public final class MyCallback implements p0.e {
    public final void b(p0.e0 call, IOException ex) { ... }
    public final void d(p0.e0 call, p0.j0 response) { ... }
}
// NEW:
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
public final class MyCallback implements Callback {
    @Override public void onFailure(Call call, IOException ex) { ... }
    @Override public void onResponse(Call call, Response response) { ... }
}
```

Key files to update (all in com/guard/wallet/http/):
- a.java, a0.java, b.java, b0.java, c.java, c0.java, d.java, d0.java
- e.java, e0.java, f.java, g.java, j.java, m.java, n.java, o.java
- p.java, q.java, r.java, s.java, t.java, u.java, v.java, w.java
- x.java, y.java, z.java
- Also: core/AppUtils.java, delegate/AdbBridge.java, thread/b.java

- [ ] **Step 2:** Replace p0.n (CookieJar) — used by ProtocolDispatcher and CookieHeaderHandler
- [ ] **Step 3:** Replace p0.w (Interceptor) — used by interceptor chain
- [ ] **Step 4:** Delete the p0 interface files
- [ ] **Step 5:** Build verify

---

### Task 8: Replace p0/ data classes (Request, Response, Headers, etc.) — ~60 business refs

**Files:**
- Delete: p0/f0.java (Request), p0/j0.java (Response), p0/i0.java (Response.Builder), p0/s.java (Headers), p0/x.java (MediaType), p0/u.java (HttpUrl), p0/l0.java, p0/k0.java (ResponseBody), p0/z.java (MultipartBody), p0/g0.java, p0/h0.java (RequestBody), p0/r.java (Handshake), p0/l.java, p0/m.java (Cookie), p0/c0.java (Protocol), p0/y.java, p0/f.java (Headers.Builder)
- Modify: business files using these types

- [ ] **Step 1:** Replace p0.f0 (Request) → okhttp3.Request
Field access `.a` → `.url()`, `.b` → `.method()`, `.c` → `.headers()`

- [ ] **Step 2:** Replace p0.j0 (Response) → okhttp3.Response
Field access `.g` → `.body()`, `.b` → `.code()`, `.m` → `.request()`

- [ ] **Step 3:** Replace p0.l0/k0 (ResponseBody) → okhttp3.ResponseBody
Method `.a()` → `.string()`, `.b()` → `.bytes()`

- [ ] **Step 4:** Replace p0.s (Headers) → okhttp3.Headers
- [ ] **Step 5:** Replace p0.x (MediaType) → okhttp3.MediaType
Static `.a(str)` → `.parse(str)` (Note: OkHttp 4.x uses `MediaType.Companion.parse()` but Java callers use `MediaType.parse()`)

- [ ] **Step 6:** Replace p0.u (HttpUrl) → okhttp3.HttpUrl
- [ ] **Step 7:** Replace p0.z (MultipartBody) → okhttp3.MultipartBody
- [ ] **Step 8:** Replace remaining p0 data classes (Cookie, Protocol, Handshake, etc.)
- [ ] **Step 9:** Delete all replaced p0 data class files
- [ ] **Step 10:** Build verify

---

### Task 9: Replace p0/ client & infrastructure (OkHttpClient, Call, Dispatcher, etc.)

**Files:**
- Delete: p0/b0.java (OkHttpClient), p0/e0.java (Call), p0/d0.java (RealCall), p0/o.java (Dispatcher), p0/a0.java (ConnectionPool), p0/t.java (Request.Builder), p0/d.java, p0/k.java (ConnectionSpec), p0/i.java, p0/j.java (CipherSuite), p0/n0.java (TlsVersion), p0/a.java (Address), p0/m0.java (Route), p0/g.java (CertificatePinner), p0/h.java, p0/b.java, p0/c.java, p0/StringToJsonTransform.java
- Modify: HttpApiManager, business code using OkHttpClient

- [ ] **Step 1:** Replace p0.b0 (OkHttpClient) → okhttp3.OkHttpClient
This is the CRITICAL class. Identify how b0 is constructed and used:
```java
// OLD: p0.b0 client = new p0.b0();
// NEW: OkHttpClient client = new OkHttpClient.Builder().build();
```

- [ ] **Step 2:** Replace p0.e0 (Call) → okhttp3.Call
```java
// OLD: p0.e0 call = client.xxx(request);
// NEW: Call call = client.newCall(request);
```

- [ ] **Step 3:** Replace p0.t (Request.Builder) → okhttp3.Request.Builder
- [ ] **Step 4:** Replace remaining infrastructure classes
- [ ] **Step 5:** Move p0/StringToJsonTransform.java to com.guard.wallet.http/ (it's custom business code, not OkHttp)
- [ ] **Step 6:** Delete ALL remaining p0 files and p0/ directory
- [ ] **Step 7:** Build verify

---

### Task 10: Final cleanup & .pending files

**Files:**
- Update: ALL .pending files with old package references
- Delete: any remaining empty directories

- [ ] **Step 1:** Search for ANY remaining references to deleted packages
```bash
for pkg in a1 p0 q0 r0 s0 t0 v0 w0 x0 z0; do
  count=$(grep -rn "\b${pkg}\." app/src/main/java/ --include="*.java" | grep -v ".pending" | wc -l)
  echo "$pkg: $count refs remaining"
done
```

- [ ] **Step 2:** Fix any remaining references
- [ ] **Step 3:** Update .pending files for consistency
- [ ] **Step 4:** Final build verify: `./gradlew assembleDebug 2>&1 | tail -3`
- [ ] **Step 5:** Run tests: `./gradlew test 2>&1 | tail -5`

---

## Execution Order & Parallelism

```
Round 1:  Task 1 (zero-ref dirs)           ← 5 min, safe
Round 2:  Task 2 (internal-only r0/t0)     ← 10 min
Round 3:  Task 3 (javap mapping)           ← research only
Round 4:  Task 4 + Task 5 + Task 6         ← parallel (a1 + s0 + q0)
Round 5:  Task 7 (callbacks — 25 files)    ← biggest batch
Round 6:  Task 8 (data classes — 17 files) ← second biggest
Round 7:  Task 9 (client infra — 18 files) ← final p0 files
Round 8:  Task 10 (cleanup)                ← verification
```

## Risk Mitigation

- **Cascading breaks**: Tasks 7-9 ALL touch p0/ — if done in wrong order, intermediate states won't compile. Execute strictly in order.
- **OkHttp 4.x Kotlin interop**: OkHttp 4.12.0 is Kotlin but has Java-compatible API. Some Kotlin property accessors (`.body`, `.code`) work as `.body()`, `.code()` in Java. Verify with javap.
- **Okio version mismatch**: okio:1.17.6 is Okio 1.x (Java). OkHttp 4.12.0 uses Okio 3.x internally. The shaded okio inside OkHttp should handle this, but test thoroughly.
- **Response body consumption**: Real OkHttp `Response.body()` can only be consumed once. If obfuscated code reads `.g` (body field) multiple times, this will break with real OkHttp. Audit each usage.
- **Method name collisions**: Some p0 method names (`.a()`, `.b()`) are also used as variable names. Grep-replace must be context-aware.

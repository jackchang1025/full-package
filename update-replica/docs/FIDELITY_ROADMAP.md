# Path to 1:1 JADX Fidelity — Prioritized Action Plan

**Executive**: To achieve true 1:1 fidelity with the JADX decompilation, the project must address 4 critical blockers and resolve 409 marked issues.

**Estimated Effort**: 700-880 developer-hours (18-22 weeks solo / 3-4 weeks with 5 developers)

---

## CRITICAL BLOCKERS (Resolve First)

### Blocker 1: p000 Package Not Mapped

**Status**: ❌ 0% Complete (300+ files unmapped)

**Impact**:
- 218 direct references from rock/ package
- Blocks completion of MainOrchestrator, NetworkManager, RemoteConfigManager
- Cannot test C2 communication without p000 utilities

**Action Items**:
1. [ ] Create `FILE_MAPPING_P000.md` tracking all 300+ p000 utility classes
2. [ ] Establish naming convention mapping for obfuscated C0[0-9]* names
3. [ ] Identify and document all p000 dependencies by rock/ module
4. [ ] Reverse-engineer core p000 utility classes (estimated 50 classes minimum)
5. [ ] Implement p000 utility implementations in Kotlin
6. [ ] Create unit tests for each p000 utility class

**Dependencies**: None (start immediately)

**Effort**: 200-300 hours

**Success Criteria**:
- [ ] All p000 references in rock/ have corresponding Kotlin implementations
- [ ] Zero compile errors from p000 references
- [ ] 100% unit test coverage for implemented p000 utilities

---

### Blocker 2: MainOrchestrator.kt Core Logic Missing

**Status**: ⚠️ 5.3% Complete (302 / 5,653 LOC)

**Impact**:
- 714 missing methods
- Cannot orchestrate module initialization
- Cannot coordinate lifecycle events
- Blocks testing of all dependent modules

**Details**:
```
JADX C0327b2.java:              5,653 LOC (~731 methods)
Replica MainOrchestrator.kt:      302 LOC (17 methods)
Gap:                            5,351 LOC (~714 methods)
```

**Action Items**:
1. [ ] Audit all 731 methods in C0327b2.java
2. [ ] Categorize methods by functionality (initialization, coordination, state, events, config, logging)
3. [ ] Implement initialization chain (Phase 1: ~80 methods)
4. [ ] Implement module coordination logic (Phase 2: ~150 methods)
5. [ ] Implement state management (Phase 3: ~120 methods)
6. [ ] Implement event handling (Phase 4: ~100 methods)
7. [ ] Implement configuration application (Phase 5: ~80 methods)
8. [ ] Implement logging and monitoring (Phase 6: ~70 methods)
9. [ ] Implement error handling and recovery (Phase 7: ~80 methods)
10. [ ] Create 200+ integration tests for orchestration flows

**Dependencies**: Requires p000 package (Blocker 1)

**Effort**: 120-150 hours

**Success Criteria**:
- [ ] All 731 methods from C0327b2.java implemented
- [ ] Orchestration flow works: init → config → start → run → stop
- [ ] All module lifecycle events handled correctly
- [ ] 100+ integration tests passing

---

### Blocker 3: RemoteConfigManager.kt Configuration Logic Missing

**Status**: ⚠️ 9.9% Complete (236 / 2,393 LOC)

**Impact**:
- 284 missing methods
- Cannot fetch remote configuration
- Cannot apply server settings
- C2 communication non-functional

**Details**:
```
JADX C0322a7.java:              2,393 LOC (~301 methods)
Replica RemoteConfigManager.kt:   236 LOC (17 methods)
Gap:                            2,157 LOC (~284 methods)
```

**Action Items**:
1. [ ] Audit all 301 methods in C0322a7.java
2. [ ] Categorize methods by functionality (fetch, parse, apply, cache, notify)
3. [ ] Implement config fetching logic (Phase 1: ~50 methods)
4. [ ] Implement config parsing and validation (Phase 2: ~60 methods)
5. [ ] Implement config application logic (Phase 3: ~70 methods)
6. [ ] Implement caching and storage (Phase 4: ~50 methods)
7. [ ] Implement update checking (Phase 5: ~30 methods)
8. [ ] Implement callbacks and notifications (Phase 6: ~25 methods)
9. [ ] Create integration tests with NetworkManager
10. [ ] Test end-to-end C2 configuration flow

**Dependencies**: Requires p000 package (Blocker 1) and NetworkManager completion

**Effort**: 100-120 hours

**Success Criteria**:
- [ ] All 301 methods from C0322a7.java implemented
- [ ] Config fetching works with mock C2 server
- [ ] Config parsing handles all message types
- [ ] Config application propagates to all modules
- [ ] 50+ integration tests passing

---

### Blocker 4: Obfuscated Class Name Mapping (C0[0-9]* Classes)

**Status**: ⚠️ 20% Mapped (139 references, semantics unclear)

**Impact**:
- 139 references to obfuscated classes with unclear semantics
- Cannot determine which classes are utilities vs. modules
- Manual analysis required to map obfuscated names

**Action Items**:
1. [ ] Extract all 139 C0[0-9]* references from replica code
2. [ ] Use Android debugger to trace class loading and determine semantics
3. [ ] Cross-reference with JADX decompilation to identify corresponding classes
4. [ ] Create mapping table: `C0[0-9]* → Semantic Name → p000 utility class`
5. [ ] Document mapping in CODE_ARCHAEOLOGY.md
6. [ ] Update all references with semantic names (e.g., C0301a1 → `ConfigValidator`)
7. [ ] Create aliases for backward compatibility
8. [ ] Test all renamed classes compile and run correctly

**Dependencies**: None (can be done in parallel with other blockers)

**Effort**: 50-80 hours

**Success Criteria**:
- [ ] All 139 obfuscated class names mapped to semantic meanings
- [ ] Mapping table verified against JADX source
- [ ] All references renamed and compiling
- [ ] Zero runtime errors from name changes

---

## HIGH-PRIORITY GAPS (Complete After Blockers)

### Gap 1: SystemOptimizeManager.kt (82 TODO Markers)

**Status**: ⚠️ 7% Complete (~400 / 5,666 LOC)

**Impact**:
- Highest TODO marker count in project
- Blocks system optimization features
- Many stubs for system-level APIs

**Quick Facts**:
- 82 TODO markers (highest in project)
- 8 ADAPT markers
- 0.225 issues per LOC (worst density)

**Action Items**:
1. [ ] Audit each of 82 TODO locations
2. [ ] Categorize by feature (optimization, caching, memory, battery)
3. [ ] Implement missing system optimization routines
4. [ ] Add system API invocations with error handling
5. [ ] Test on Android emulator/device
6. [ ] Resolve all ADAPT markers with Kotlin idioms

**Dependencies**: Requires Blocker 1 (p000 package)

**Effort**: 50-70 hours

**Success Criteria**:
- [ ] All 82 TODOs resolved
- [ ] System optimization features work as expected
- [ ] 30+ tests passing

---

### Gap 2: iuzxujjtqev.kt Large Utility Class (38 ADAPT Markers)

**Status**: ⚠️ 22.6% Complete (587 / 2,591 LOC)

**Impact**:
- 304 missing methods
- 38 ADAPT markers indicate incomplete conversion
- Depended on by 7+ other files
- Single point of failure for utility functions

**Quick Facts**:
- Expected ~340 methods in JADX
- Only 36 methods implemented
- Used by MainOrchestrator, NetworkManager, all command handlers

**Action Items**:
1. [ ] Analyze all 340 methods in iuzxujjtqev.java
2. [ ] Group by functionality (string, encryption, serialization, reflection, system, network, other)
3. [ ] Implement string utility methods (Phase 1: ~45 methods)
4. [ ] Implement encryption/cryptography (Phase 2: ~50 methods)
5. [ ] Implement serialization/deserialization (Phase 3: ~60 methods)
6. [ ] Implement collection utilities (Phase 4: ~45 methods)
7. [ ] Implement reflection utilities (Phase 5: ~40 methods)
8. [ ] Implement system interaction (Phase 6: ~35 methods)
9. [ ] Implement network utilities (Phase 7: ~30 methods)
10. [ ] Consolidate into logical sub-classes if possible
11. [ ] Create 100+ unit tests for each utility category

**Dependencies**: Requires Blocker 1 (p000 package)

**Effort**: 100-140 hours

**Success Criteria**:
- [ ] All 340 methods implemented or determined to be dead code
- [ ] All 38 ADAPT markers resolved
- [ ] 100+ unit tests passing
- [ ] Zero dependencies on unimplemented utility methods

---

### Gap 3: Command Handlers (Multiple Files with 5-17 ADAPT Markers)

**Status**: ⚠️ 50-70% Complete (various files)

**Impact**:
- Command processing incomplete
- Cannot handle all C2 commands
- Module-specific command handlers with gaps

**Files Affected**:
```
AppCommandHandler.kt              17 ADAPT markers
DetectionCommandHandler.kt        12 ADAPT markers
UnlockCommandHandler.kt            8 ADAPT markers
SmsContactsCommandHandler.kt       7 ADAPT markers
MediaCommandHandler.kt             7 ADAPT markers
LogCommandHandler.kt               7 ADAPT markers
(and 28+ more command handlers)
```

**Action Items**:
1. [ ] Audit each command handler against JADX source
2. [ ] For each ADAPT marker, verify intentional deviation or implement missing logic
3. [ ] Complete command parsing for all handler types
4. [ ] Implement missing command processing for AppCommandHandler
5. [ ] Implement missing command processing for DetectionCommandHandler
6. [ ] Implement missing command processing for other handlers
7. [ ] Create integration tests for each command type
8. [ ] Test end-to-end C2 command flow

**Dependencies**: Requires Blocker 1 (p000) and Blocker 2 (MainOrchestrator)

**Effort**: 60-80 hours

**Success Criteria**:
- [ ] All ADAPT markers resolved
- [ ] All command handlers fully implemented
- [ ] 50+ integration tests for command handling
- [ ] No missing command types

---

## MEDIUM-PRIORITY GAPS (Complete After High-Priority)

### Gap 4: CipherCaptureManager.kt (372 Missing Methods)

**Status**: ⚠️ 51.6% Complete (1,552 / 3,005 LOC)

**Action Items**:
- [ ] Implement remaining UI layout and rendering methods (~120)
- [ ] Implement missing input event handling (~80)
- [ ] Complete pattern/cipher validation logic (~90)
- [ ] Implement state animation (~60)
- [ ] Add data collection and tracking (~45)

**Effort**: 70-90 hours

---

### Gap 5: UninstallProtectionManager.kt (341 Missing Methods)

**Status**: ⚠️ 64% Complete (1,461 / 2,282 LOC)

**Action Items**:
- [ ] Implement protection hooks (~80 methods)
- [ ] Implement system integration (~70 methods)
- [ ] Implement state persistence (~60 methods)
- [ ] Implement bypass detection (~50 methods)
- [ ] Implement recovery mechanisms (~40 methods)

**Effort**: 50-70 hours

---

## ROADMAP TIMELINE

### Week 1-2: Discovery & Planning
- [ ] Complete Blocker 4 (obfuscated name mapping)
- [ ] Create detailed breakdown of Blocker 1 (p000 package)
- [ ] Document all dependencies between blockers
- [ ] Estimate p000 package size and scope

**Effort**: 30 hours

---

### Week 3-6: Blocker 1 Implementation
- [ ] Implement core p000 utility classes (50-100 classes)
- [ ] Create unit tests for p000 classes
- [ ] Resolve p000 dependency issues in rock/ package

**Effort**: 200-300 hours

---

### Week 7-10: Blocker 2 Implementation (MainOrchestrator)
- [ ] Implement initialization chain
- [ ] Implement module coordination
- [ ] Implement state management
- [ ] Create 200+ tests

**Effort**: 120-150 hours

---

### Week 11-14: Blocker 3 Implementation (RemoteConfigManager)
- [ ] Implement config fetching
- [ ] Implement config parsing
- [ ] Implement config application
- [ ] Create integration tests

**Effort**: 100-120 hours

---

### Week 15-18: High-Priority Gaps
- [ ] SystemOptimizeManager.kt (50-70 hours)
- [ ] iuzxujjtqev.kt (100-140 hours)
- [ ] Command Handlers (60-80 hours)

**Effort**: 210-290 hours

---

### Week 19-22: Medium-Priority Gaps & Validation
- [ ] CipherCaptureManager.kt (70-90 hours)
- [ ] UninstallProtectionManager.kt (50-70 hours)
- [ ] End-to-end testing (40-60 hours)

**Effort**: 160-220 hours

---

## TOTAL EFFORT ESTIMATION

```
Blocker 1 (p000 package):          200-300 hours
Blocker 2 (MainOrchestrator):      120-150 hours
Blocker 3 (RemoteConfigManager):   100-120 hours
Blocker 4 (Obfuscated names):       50-80 hours
────────────────────────────────────────────────
Critical Blockers Subtotal:        470-650 hours

High-Priority Gaps:                210-290 hours
Medium-Priority Gaps:              160-220 hours
Validation & Integration:           40-60 hours
────────────────────────────────────────────────
Additional Gaps Subtotal:          410-570 hours

────────────────────────────────────────────────
GRAND TOTAL:                       700-880 hours

Duration @ 40 hrs/week:            18-22 weeks (1 developer)
Duration @ 5 developers:           3-4 weeks (parallel work)
```

---

## SUCCESS CRITERIA FOR 1:1 FIDELITY

### Code Completeness
- [ ] All 559 JADX files have Kotlin equivalents
- [ ] All ~3,000 JADX methods have Kotlin implementations
- [ ] All 145,589 LOC equivalent to ~140,000+ LOC Kotlin
- [ ] Zero `// TODO` markers (155 → 0)
- [ ] Zero `// ADAPT` markers without justification (254 → <10)
- [ ] All p000 package classes implemented (300+ → 0 missing)

### Functional Completeness
- [ ] C2 communication works end-to-end
- [ ] All module lifecycle events handled
- [ ] All command types processable
- [ ] All configuration settings applicable
- [ ] All protection mechanisms active

### Testing Completeness
- [ ] 1,000+ unit tests (from current 1,258 → comprehensive coverage)
- [ ] 100+ integration tests for critical flows
- [ ] 50+ end-to-end tests
- [ ] Code coverage >85% for all modules
- [ ] All tests passing on Android emulator/device

### Documentation Completeness
- [ ] All obfuscated class names semantically documented
- [ ] All method signatures have clear docstrings
- [ ] All complex logic has inline comments
- [ ] Module dependency diagram complete
- [ ] API reference complete

---

## RISK MITIGATION

### Risk 1: p000 Package Semantics Unclear
- **Mitigation**: Use dynamic analysis (debugger) to understand class behavior
- **Fallback**: Implement minimal p000 stubs based on usage patterns

### Risk 2: MainOrchestrator Complexity
- **Mitigation**: Break into phases by functionality
- **Fallback**: Use mock objects for untestable components

### Risk 3: Time Overruns
- **Mitigation**: Weekly progress checkpoints, adjust priorities
- **Fallback**: Accept <100% fidelity if 80%+ coverage achieved

### Risk 4: Obfuscation Prevents Semantic Understanding
- **Mitigation**: Multiple mapping strategies (dynamic, static, pattern-based)
- **Fallback**: Create semantic aliases independent of obfuscated names

---

## GO/NO-GO DECISION POINTS

### After Blocker 1 (Week 6)
**Decision**: Can we implement Blocks 2-3?
- **GO**: p000 package mapped, all dependencies clear
- **NO-GO**: p000 too large/complex, scale back ambitions

### After Blocker 2 (Week 10)
**Decision**: Is MainOrchestrator functional?
- **GO**: All tests passing, module coordination works
- **NO-GO**: Core logic incomplete, extend timeline

### After Blocker 3 (Week 14)
**Decision**: Can we test C2 communication?
- **GO**: Config manager functional, ready for integration testing
- **NO-GO**: Major gaps remain, prioritize gaps

### After High-Priority Gaps (Week 18)
**Decision**: Is project suitable for deployment?
- **GO**: 80%+ coverage, all critical features working
- **NO-GO**: Continue with medium-priority gaps or declare completion at current state


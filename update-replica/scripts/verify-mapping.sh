#!/bin/bash
# Verify replica progress against JADX reference
JADX_DIR="../../jadx-reference/rock"
SRC_DIR="../app/src/main/java/com/storm/safe/rock"
TEST_DIR="../app/src/test/java/com/storm/safe/rock"

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

echo "=== File Mapping Status ==="
total=$(grep -cE "\| pending \||\| done \|" ../FILE_MAPPING.md 2>/dev/null; [ $? -le 1 ] && true)
done=$(grep -cE "\| done \|" ../FILE_MAPPING.md 2>/dev/null; [ $? -le 1 ] && true)
total=${total:-0}
done=${done:-0}
echo "Progress: $done / $total files"

echo ""
echo "=== Source Files ==="
src_files=$(find "$SRC_DIR" -name "*.kt" -o -name "*.java" 2>/dev/null | wc -l)
echo "Source files: $src_files"

echo ""
echo "=== Test Files ==="
test_files=$(find "$TEST_DIR" -name "*Test.kt" -o -name "*Test.java" 2>/dev/null | wc -l)
echo "Test files: $test_files"

echo ""
echo "=== Build Check ==="
cd .. && ./gradlew test 2>&1 | tail -5

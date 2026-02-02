/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_APP_NAME: string;
  readonly WEBSOCKET_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}

// Naive UI message global
declare global {
  interface Window {
    $message?: import('naive-ui').MessageApi;
    $dialog?: import('naive-ui').DialogApi;
    $notification?: import('naive-ui').NotificationApi;
    $loadingBar?: import('naive-ui').LoadingBarApi;
  }
}

export {};

import { defineConfig, loadEnv } from 'vite';
import laravel from 'laravel-vite-plugin';
import vue from '@vitejs/plugin-vue';
import tailwindcss from '@tailwindcss/vite';
import { resolve } from 'path';

export default defineConfig(({ mode }) => {
    // 加载 .env 文件中的环境变量
    const env = loadEnv(mode, process.cwd(), '');
    
    // 获取 Vite 开发服务器的 host，默认为 localhost
    // 局域网访问时，设置 VITE_DEV_HOST 为本机 IP（如 192.168.31.35）
    const viteDevHost = env.VITE_DEV_HOST || 'localhost';
    
    return {
        // 将 WEBSOCKET_URL 暴露给前端代码
        define: {
            'import.meta.env.WEBSOCKET_URL': JSON.stringify(env.WEBSOCKET_URL || 'ws://localhost:8081'),
        },
        plugins: [
        laravel({
            input: ['resources/css/app.css', 'resources/ts/app.ts'],
            refresh: true,
            // 配置 detectTls 和 valetTls 为 false，避免 HTTPS 相关问题
            detectTls: false,
        }),
        vue({
            template: {
                transformAssetUrls: {
                    base: null,
                    includeAbsolute: false,
                },
            },
        }),
        tailwindcss(),
    ],
    resolve: {
        alias: {
            '@': resolve(__dirname, 'resources/ts'),
        },
    },
    server: {
        host: '0.0.0.0',
        port: 5173,
        strictPort: true,
        // 允许跨域访问（局域网其他设备访问）
        cors: true,
        hmr: {
            // 使用环境变量配置的 host，支持局域网访问
            host: viteDevHost,
        },
        watch: {
            usePolling: true,
            ignored: ['**/storage/framework/views/**'],
        },
        // 允许外部主机访问
        allowedHosts: 'all',
    },
    };
});

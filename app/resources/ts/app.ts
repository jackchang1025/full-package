import { createApp, h } from 'vue';
import { createInertiaApp, router } from '@inertiajs/vue3';
import { resolvePageComponent } from 'laravel-vite-plugin/inertia-helpers';
import {
    NConfigProvider,
    NMessageProvider,
    NDialogProvider,
    NNotificationProvider,
    NLoadingBarProvider,
    zhCN,
    dateZhCN,
} from 'naive-ui';
import SubscriptionExpiredHandler from './Components/SubscriptionExpiredHandler.vue';
import PermissionDeniedHandler from './Components/PermissionDeniedHandler.vue';

// 使用后端注入的动态应用名称，回退到 Vite 环境变量或默认值
declare global {
    interface Window {
        __APP_NAME__?: string;
    }
}
const appName = window.__APP_NAME__ || import.meta.env.VITE_APP_NAME || 'Laravel';

type InvalidResponse = {
    status: number;
    data?: {
        message?: string;
        title?: string;
        content?: string;
        positive_text?: string;
        error?: string;
    };
};

// 用户订阅过期 / 无权限 / 业务拒绝：Inertia 收到 403 非 Inertia 响应时派发事件，由全局组件弹框
router.on('invalid', (event) => {
    const res = event.detail.response as InvalidResponse;
    if (res.status !== 403 || !res.data?.message) return;

    if (res.data.message === 'subscription_expired') {
        const detail = {
            title: res.data?.title ?? '',
            content: res.data?.content ?? '',
            positiveText: res.data?.positive_text ?? '',
        };
        event.preventDefault();
        window.dispatchEvent(new CustomEvent('subscription-expired', { detail }));
    } else if (res.data.message === 'permission_denied') {
        const detail = {
            title: res.data?.title ?? '',
            content: res.data?.content ?? '',
            positiveText: res.data?.positive_text ?? '',
        };
        event.preventDefault();
        window.dispatchEvent(new CustomEvent('permission-denied', { detail }));
    } else if (res.data.message === 'access_denied') {
        // 资源归属 / 子账号业务异常：复用权限拒绝弹框，使用后端返回的具体错误信息
        const detail = {
            title: '操作被拒绝',
            content: res.data?.error ?? '无权访问该资源',
            positiveText: '知道了',
        };
        event.preventDefault();
        window.dispatchEvent(new CustomEvent('permission-denied', { detail }));
    }
});

createInertiaApp({
    title: (title) => title ? `${title} - ${appName}` : appName,
    resolve: (name) =>
        resolvePageComponent(
            `./Pages/${name}.vue`,
            import.meta.glob('./Pages/**/*.vue')
        ),
    setup({ el, App, props, plugin }) {
        createApp({
            render: () =>
                h(
                    NConfigProvider,
                    { locale: zhCN, dateLocale: dateZhCN },
                    () =>
                        h(NLoadingBarProvider, null, () =>
                            h(NDialogProvider, null, () =>
                                h(NNotificationProvider, null, () =>
                                    h(NMessageProvider, null, () =>
                                        h('div', null, [
                                            h(App, props),
                                            h(SubscriptionExpiredHandler),
                                            h(PermissionDeniedHandler),
                                        ])
                                    )
                                )
                            )
                        )
                ),
        })
            .use(plugin)
            .mount(el);
    },
    progress: {
        color: '#18a058',
    },
});

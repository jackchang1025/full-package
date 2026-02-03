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

const appName = import.meta.env.VITE_APP_NAME || 'Laravel';

type InvalidResponse = {
    status: number;
    data?: {
        message?: string;
        title?: string;
        content?: string;
        positive_text?: string;
    };
};

// 用户订阅过期 / 无权限：Inertia 收到 403 非 Inertia 响应时派发事件，由全局组件弹框（订阅过期则退出登录，无权限仅提示）
router.on('invalid', (event) => {
    const res = event.detail.response as InvalidResponse;
    if (res.status !== 403 || !res.data?.message) return;

    const detail = {
        title: res.data?.title ?? '',
        content: res.data?.content ?? '',
        positiveText: res.data?.positive_text ?? '',
    };

    if (res.data.message === 'subscription_expired') {
        event.preventDefault();
        window.dispatchEvent(new CustomEvent('subscription-expired', { detail }));
    } else if (res.data.message === 'permission_denied') {
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

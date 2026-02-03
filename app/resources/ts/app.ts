import { createApp, h } from 'vue';
import { createInertiaApp } from '@inertiajs/vue3';
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

const appName = import.meta.env.VITE_APP_NAME || 'Laravel';

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
                                        h(App, props)
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

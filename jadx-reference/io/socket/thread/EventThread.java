package io.socket.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class EventThread extends Thread {
    private static ExecutorService service;
    private static EventThread thread;
    private static final Logger logger = Logger.getLogger(EventThread.class.getName());
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() { // from class: io.socket.thread.EventThread.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            EventThread unused = EventThread.thread = new EventThread(runnable);
            EventThread.thread.setName("EventThread");
            EventThread.thread.setDaemon(Thread.currentThread().isDaemon());
            return EventThread.thread;
        }
    };
    private static int counter = 0;

    public static /* synthetic */ int access$310() {
        int i = counter;
        counter = i - 1;
        return i;
    }

    public static void exec(Runnable runnable) {
        if (isCurrent()) {
            runnable.run();
        } else {
            nextTick(runnable);
        }
    }

    public static boolean isCurrent() {
        return Thread.currentThread() == thread;
    }

    public static void nextTick(final Runnable runnable) {
        ExecutorService executorService;
        synchronized (EventThread.class) {
            try {
                counter++;
                if (service == null) {
                    service = Executors.newSingleThreadExecutor(THREAD_FACTORY);
                }
                executorService = service;
            } catch (Throwable th) {
                throw th;
            }
        }
        executorService.execute(new Runnable() { // from class: io.socket.thread.EventThread.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    runnable.run();
                    synchronized (EventThread.class) {
                        try {
                            EventThread.access$310();
                            if (EventThread.counter == 0) {
                                EventThread.service.shutdown();
                                ExecutorService unused = EventThread.service = null;
                                EventThread unused2 = EventThread.thread = null;
                            }
                        } finally {
                        }
                    }
                } catch (Throwable th2) {
                    try {
                        EventThread.logger.log(Level.SEVERE, "Task threw exception", th2);
                        throw th2;
                    } catch (Throwable th3) {
                        synchronized (EventThread.class) {
                            try {
                                EventThread.access$310();
                                if (EventThread.counter == 0) {
                                    EventThread.service.shutdown();
                                    ExecutorService unused3 = EventThread.service = null;
                                    EventThread unused4 = EventThread.thread = null;
                                }
                                throw th3;
                            } finally {
                            }
                        }
                    }
                }
            }
        });
    }

    private EventThread(Runnable runnable) {
        super(runnable);
    }
}

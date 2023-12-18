package io.github.krysiel86.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import androidx.annotation.NonNull;

public class HandlerScheduler implements Scheduler {

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void schedule(@NonNull Runnable runnable, @NonNull String tag, long delay) {
        handler.postAtTime(runnable, tag, calculateTime(delay));
    }

    private long calculateTime(long delay) {
        return SystemClock.uptimeMillis() + delay;
    }

    @Override
    public void schedule(@NonNull Runnable runnable, long delay) {
        handler.postAtTime(runnable, calculateTime(delay));
    }

    @Override
    public void cancel(@NonNull String tag) {
        handler.removeCallbacksAndMessages(tag);
    }

    @Override
    public void cancelAll() {
        handler.removeCallbacksAndMessages(null);
    }
}

package io.github.krysiel86.utils;

import androidx.annotation.NonNull;

public interface Scheduler {

    void schedule(@NonNull Runnable runnable, @NonNull String tag, long delay);

    void schedule(@NonNull Runnable runnable, long delay);

    void cancel(@NonNull String tag);

    void cancelAll();

}

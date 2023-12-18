package io.github.krysiel86.mobile.rendering.listeners;

import org.jetbrains.annotations.NotNull;
import io.github.krysiel86.mobile.api.data.InitializationStatus;
import io.github.krysiel86.mobile.api.exceptions.InitError;

public interface SdkInitializationListener {

    void onInitializationComplete(@NotNull InitializationStatus status);

    @Deprecated
    default void onSdkInit() {
    }

    @Deprecated
    default void onSdkFailedToInit(InitError error) {
    }

}

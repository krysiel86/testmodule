package io.github.krysiel86.mobile.rendering.sdk;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;
import io.github.krysiel86.mobile.LogUtil;
import io.github.krysiel86.mobile.PrebidMobile;
import io.github.krysiel86.mobile.api.data.InitializationStatus;
import io.github.krysiel86.mobile.api.exceptions.InitError;
import io.github.krysiel86.mobile.rendering.listeners.SdkInitializationListener;
import io.github.krysiel86.mobile.rendering.sdk.PrebidContextHolder;


public class InitializationNotifier {

    private static final String TAG = "InitializationNotifier";

    private static boolean tasksCompletedSuccessfully = false;
    private static boolean initializationInProgress = false;

    @Nullable
    private SdkInitializationListener listener;

    public InitializationNotifier(@Nullable SdkInitializationListener listener) {
        this.listener = listener;
        initializationInProgress = true;
    }

    /**
     * @param statusRequesterError must be null, if status requester completed successfully.
     */
    public void initializationCompleted(@Nullable String statusRequesterError) {
        postOnMainThread(() -> {
            boolean statusRequestSuccessful = statusRequesterError == null;
            if (statusRequestSuccessful) {
                LogUtil.debug(TAG, "Prebid SDK " + PrebidMobile.SDK_VERSION + " initialized");

                if (listener != null) {
                    listener.onInitializationComplete(InitializationStatus.SUCCEEDED);

                    listener.onSdkInit();
                }
            } else {
                LogUtil.error(TAG, statusRequesterError);

                if (listener != null) {
                    InitializationStatus serverStatusWarning = InitializationStatus.SERVER_STATUS_WARNING;
                    serverStatusWarning.setDescription(statusRequesterError);
                    listener.onInitializationComplete(serverStatusWarning);

                    listener.onSdkFailedToInit(new InitError(statusRequesterError));
                }
            }

            tasksCompletedSuccessfully = true;
            initializationInProgress = false;
            listener = null;
        });
    }

    public void initializationFailed(@NotNull String error) {
        postOnMainThread(() -> {
            LogUtil.error(error);

            if (listener != null) {
                InitializationStatus status = InitializationStatus.FAILED;
                status.setDescription(error);
                listener.onInitializationComplete(status);

                listener.onSdkFailedToInit(new InitError(error));
            }

            PrebidContextHolder.clearContext();
            listener = null;
            initializationInProgress = false;
        });
    }


    private static void postOnMainThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public static boolean wereTasksCompletedSuccessfully() {
        return tasksCompletedSuccessfully;
    }

    public static boolean isInitializationInProgress() {
        return initializationInProgress;
    }

}

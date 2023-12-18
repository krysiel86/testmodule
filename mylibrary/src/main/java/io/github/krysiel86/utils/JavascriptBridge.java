package io.github.krysiel86.utils;


import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Javascript bridge that interacts with MRAID advertisements and with Postroll webviews. For example,
 * this is the interface that listens for the user clicking the X button and acts on it.
 */
public class JavascriptBridge {

    private MraidHandler handler;

    public JavascriptBridge(MraidHandler mraidHandler) {
        this.handler = mraidHandler;
    }

    @JavascriptInterface
    public void performAction(String action) {
        Log.d("JavascriptBridge", "actionClicked(" + action + ")");
        handler.onMraidAction(action);
    }


    /**
     * The contract bindings for the class that would like to handle actions requested by the MRAID
     * bridge.
     */
    public interface MraidHandler {

        String DOWNLOAD_ACTION = "download";
        String CLOSE_ACTION = "close";
        String PRIVACY_ACTION = "privacy";
        /**
         * Called whenever the MRAID bridge requests the SDK perform an action or handle a state change.
         * This method is called on the JavaBridge thread, not on the main thread.
         *
         * @param action The action that the bridge is asking us to handle.
         */
        void onMraidAction(String action);
    }
}

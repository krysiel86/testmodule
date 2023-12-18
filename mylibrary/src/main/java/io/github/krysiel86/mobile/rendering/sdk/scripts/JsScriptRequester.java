package io.github.krysiel86.mobile.rendering.sdk.scripts;

import io.github.krysiel86.mobile.rendering.sdk.scripts.DownloadListenerCreator;
import io.github.krysiel86.mobile.rendering.sdk.scripts.JsScriptData;

import java.io.File;

public interface JsScriptRequester {

    public void download(
            File saveToFile,
            JsScriptData data,
            DownloadListenerCreator downloadListener
    );

}

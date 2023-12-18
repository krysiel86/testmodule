package io.github.krysiel86.mobile.rendering.sdk.scripts;

import android.os.AsyncTask;

import java.io.File;

import io.github.krysiel86.mobile.rendering.loading.FileDownloadTask;
import io.github.krysiel86.mobile.rendering.networking.BaseNetworkTask;
import io.github.krysiel86.mobile.rendering.utils.helpers.AppInfoManager;

public class JsScriptRequesterImpl implements JsScriptRequester {

    public void download(File saveToFile, JsScriptData script, DownloadListenerCreator listener) {
        BaseNetworkTask.GetUrlParams params = new BaseNetworkTask.GetUrlParams();
        params.url = script.getUrl();
        params.userAgent = AppInfoManager.getUserAgent();
        params.requestType = "GET";
        params.name = BaseNetworkTask.DOWNLOAD_TASK;

        FileDownloadTask omSdkTask = new FileDownloadTask(listener.create(script.getPath()), saveToFile);
        omSdkTask.setIgnoreContentLength(true);
        omSdkTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
    }

}

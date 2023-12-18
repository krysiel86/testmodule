package io.github.krysiel86.mobile.rendering.sdk.scripts;

import io.github.krysiel86.mobile.rendering.loading.FileDownloadListener;

public interface DownloadListenerCreator {

    FileDownloadListener create(String filePath);

}
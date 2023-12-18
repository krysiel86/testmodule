/*
 *    Copyright 2018-2021 Prebid.org, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.krysiel86.mobile.rendering.utils.url.action;

import static io.github.krysiel86.mobile.PrebidMobile.SCHEME_HTTP;
import static io.github.krysiel86.mobile.PrebidMobile.SCHEME_HTTPS;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import io.github.krysiel86.mobile.rendering.utils.helpers.ExternalViewerUtils;
import io.github.krysiel86.mobile.rendering.utils.url.ActionNotResolvedException;
import io.github.krysiel86.mobile.rendering.utils.url.UrlHandler;
import io.github.krysiel86.mobile.rendering.utils.url.action.DeepLinkPlusAction;
import io.github.krysiel86.mobile.rendering.utils.url.action.UrlAction;

public class DeepLinkAction implements UrlAction {
    @Override
    public boolean shouldOverrideUrlLoading(Uri uri) {
        final String scheme = uri.getScheme();
        return !TextUtils.isEmpty(scheme)
               && !(SCHEME_HTTP.equals(scheme) || SCHEME_HTTPS.equals(scheme))
               && !DeepLinkPlusAction.SCHEME_DEEPLINK_PLUS.equals(scheme);
    }

    @Override
    public void performAction(Context context, UrlHandler urlHandler, Uri uri)
    throws ActionNotResolvedException {
        ExternalViewerUtils.launchApplicationUrl(context, uri);
    }

    @Override
    public boolean shouldBeTriggeredByUserAction() {
        return true;
    }
}

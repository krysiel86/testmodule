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

package io.github.krysiel86.mobile.rendering.views.webview;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.animation.AnimationUtils;

import org.json.JSONObject;

import io.github.krysiel86.mobile.LogUtil;
import io.github.krysiel86.mobile.api.exceptions.AdException;
import io.github.krysiel86.R;

import io.github.krysiel86.mobile.rendering.models.internal.MraidVariableContainer;
import io.github.krysiel86.mobile.rendering.mraid.handler.FetchPropertiesHandler;
import io.github.krysiel86.mobile.rendering.sdk.JSLibraryManager;
import io.github.krysiel86.mobile.rendering.views.interstitial.InterstitialManager;
import io.github.krysiel86.mobile.rendering.views.webview.MraidEventsManager;
import io.github.krysiel86.mobile.rendering.views.webview.PrebidWebViewBase;
import io.github.krysiel86.mobile.rendering.views.webview.PreloadManager;
import io.github.krysiel86.mobile.rendering.views.webview.WebViewBanner;
import io.github.krysiel86.mobile.rendering.views.webview.WebViewBase;
import io.github.krysiel86.mobile.rendering.views.webview.mraid.Views;

public class PrebidWebViewBanner extends PrebidWebViewBase
        implements PreloadManager.PreloadedListener, MraidEventsManager.MraidListener {

    private static final String TAG = PrebidWebViewBanner.class.getSimpleName();

    private final FetchPropertiesHandler.FetchPropertyCallback expandPropertiesCallback = new FetchPropertiesHandler.FetchPropertyCallback() {
        @Override
        public void onResult(String propertyJson) {
            handleExpandPropertiesResult(propertyJson);
        }

        @Override
        public void onError(Throwable throwable) {
            LogUtil.error(TAG, "executeGetExpandProperties failed: " + Log.getStackTraceString(throwable));
        }
    };

    public PrebidWebViewBanner(Context context, InterstitialManager interstitialManager) {
        super(context, interstitialManager);
        Log.e("tpmn", "PrebidWebViewBanner");
        setId(R.id.web_view_banner);
    }

    //gets expand properties & also a close view(irrespective of usecustomclose is false)
    public void loadMraidExpandProperties() {
        Log.e("tpmn", "loadMraidExpandProperties");
        Context context = getContext();
        if (!(context instanceof Activity)) {
            LogUtil.warning(TAG, "Context is null or is not activity context");
            return;
        }

        /*
         * If it's MRAID, we have to check the Ad designer's request to launch
         * the ad in a particular expanded size by checking the ad's
         * ExpandProperties per the MRAID spec. So we go to the js and extract these
         * properties and then the layout gets built based on these things.
         */
        //Fix MOBILE-2944 App crash navigating MRAID ad
        final WebViewBase currentWebView = (webView != null) ? webView : mraidWebView;

        if (currentWebView != null) {
            currentWebView.getMRAIDInterface()
                    .getJsExecutor()
                    .executeGetExpandProperties(new FetchPropertiesHandler(expandPropertiesCallback));
        } else {
            LogUtil.warning(TAG, "Error getting expand properties");
        }
    }

    @Override
    public void initTwoPartAndLoad(String url) {
        Log.e("tpmn", "initTwoPartAndLoad url : " + url);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setLayoutParams(layoutParams);
        //A null context can crash with an exception in webView creation through WebViewBanner. Catch it
        mraidWebView = new io.github.krysiel86.mobile.rendering.views.webview.WebViewBanner(context, this, this);
        mraidWebView.setJSName("twopart");

        String script = JSLibraryManager.getInstance(mraidWebView.getContext()).getMRAIDScript();
        //inject mraid.js
        mraidWebView.setMraidAdAssetsLoadListener(mraidWebView, script);

        mraidWebView.loadUrl(url);
    }

    @Override
    public void loadHTML(String html, int width, int height) {
//        Log.e("tpmn", "initTwoPartAndLoad loadHTML : " + html + "\n\n");
        Log.e("tpmn", "initTwoPartAndLoad loadHTML");
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );

        setLayoutParams(layoutParams);
        this.width = width;
        this.height = height;
        //A null context can crash with an exception in webView creation through WebViewBanner. Catch it
        webView = new WebViewBanner(context, html, width, height, this, this);
        webView.setJSName("1part");
        webView.initContainsIFrame(creative.getCreativeModel().getHtml());
        webView.setTargetUrl(creative.getCreativeModel().getTargetUrl());
        Log.e("tpmn","loadHTML getHtml : " + creative.getCreativeModel().getHtml());
        Log.e("tpmn","loadHTML getTargetUrl : " + creative.getCreativeModel().getTargetUrl());
        Log.e("tpmn","loadHTML html : " + html);
        webView.loadAd();
    }

    @Override
    public void preloaded(io.github.krysiel86.mobile.rendering.views.webview.WebViewBase adBaseView) {
        Log.e("tpmn", "initTwoPartAndLoad preloaded");

        if (adBaseView == null) {

            //This should never happen.
            LogUtil.error(TAG, "Failed to preload a banner ad. Webview is null.");

            if (webViewDelegate != null) {
                webViewDelegate.webViewFailedToLoad(new AdException(
                        AdException.INTERNAL_ERROR,
                        "Preloaded adview is null!"
                ));
            }

            return;
        }
        currentWebViewBase = adBaseView;
        if (currentWebViewBase.MRAIDBridgeName.equals("twopart")) {
            //SHould have expanded url here, as last param
            interstitialManager.displayPrebidWebViewForMraid(mraidWebView, true);
        } else {
            if (adBaseView.getParent() == null) {

                if (getChildCount() >= 1) {

                    LogUtil.debug(TAG, "Adding second view");
                    //safe removal from parent before adding
                    Views.removeFromParent(adBaseView);

                    addView(adBaseView, 1);
                    adBaseView.bringToFront();
                    swapWebViews();
                } else {
                    LogUtil.debug(TAG, "Adding first view");
                    //safe removal from parent before adding
                    Views.removeFromParent(adBaseView);

                    addView(adBaseView, 0);
                    renderAdView(adBaseView);
                }
            } else {
                LogUtil.debug(TAG, "Adding the only view");

                adBaseView.bringToFront();
                swapWebViews();
            }
        }
        /*
         * This postInvalidate fixes the cosmetic issue that KitKat created with the white banner
         * fragment/remnant showing up at the bottom of the screen.
         */
        if (context instanceof Activity) {
            ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content).postInvalidate();
            ((Activity) context).getWindow()
                    .getDecorView()
                    .findViewById(android.R.id.content)
                    .postInvalidateDelayed(100);
        }

        if (webViewDelegate != null) {
            webViewDelegate.webViewReadyToDisplay();
        }
    }

    protected void swapWebViews() {
        Log.e("tpmn", "swapWebViews");
        if (getContext() != null) {
            fadeOutAnimation = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        }
        final io.github.krysiel86.mobile.rendering.views.webview.WebViewBase frontAdView = (io.github.krysiel86.mobile.rendering.views.webview.WebViewBase) getChildAt(0);
        io.github.krysiel86.mobile.rendering.views.webview.WebViewBase backAdView = (io.github.krysiel86.mobile.rendering.views.webview.WebViewBase) getChildAt(1);

        if (frontAdView != null) {
            frontAdView.startAnimation(fadeOutAnimation);
            frontAdView.setVisibility(GONE);
        }

        if (backAdView != null) {
            renderAdView(backAdView);
            backAdView.bringToFront();
        }
    }

    private void handleExpandPropertiesResult(String expandProperties) {
        Log.e("tpmn", "handleExpandPropertiesResult");
        JSONObject jsonExpandProperties;

        WebViewBase currentWebView = (webView != null) ? webView : mraidWebView;
        final MraidVariableContainer mraidVariableContainer = currentWebView.getMRAIDInterface()
                .getMraidVariableContainer();
        mraidVariableContainer.setExpandProperties(expandProperties);

        try {
            jsonExpandProperties = new JSONObject(expandProperties);

            definedWidthForExpand = jsonExpandProperties.optInt("width", 0);
            definedHeightForExpand = jsonExpandProperties.optInt("height", 0);

            if (interstitialManager.getInterstitialDisplayProperties() != null) {
                interstitialManager.getInterstitialDisplayProperties().expandWidth = definedWidthForExpand;
                interstitialManager.getInterstitialDisplayProperties().expandHeight = definedHeightForExpand;
            }
        } catch (Exception e) {
            LogUtil.error(TAG, "handleExpandPropertiesResult: Failed. Reason: " + Log.getStackTraceString(e));
        }
    }
}

package io.github.krysiel86.mobile.api.original;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import io.github.krysiel86.mobile.AdSize;
import io.github.krysiel86.mobile.AdUnit;
import io.github.krysiel86.mobile.BannerParameters;
import io.github.krysiel86.mobile.ContentObject;
import io.github.krysiel86.mobile.DataObject;
import io.github.krysiel86.mobile.NativeParameters;
import io.github.krysiel86.mobile.OnCompleteListener;
import io.github.krysiel86.mobile.ResultCode;
import io.github.krysiel86.mobile.Util;
import io.github.krysiel86.mobile.VideoParameters;
import io.github.krysiel86.mobile.api.data.AdFormat;
import io.github.krysiel86.mobile.api.exceptions.AdException;
import io.github.krysiel86.mobile.api.original.PrebidRequest;
import io.github.krysiel86.mobile.configuration.AdUnitConfiguration;
import io.github.krysiel86.mobile.configuration.NativeAdUnitConfiguration;
import io.github.krysiel86.mobile.rendering.bidding.data.bid.BidResponse;
import io.github.krysiel86.mobile.rendering.bidding.listeners.BidRequesterListener;
import io.github.krysiel86.mobile.rendering.models.AdPosition;
import io.github.krysiel86.mobile.rendering.models.PlacementType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Internal AdUnit implementation that is used for PrebidAdUnit
 * with multiformat configuration. It separates logic for multiformat ad unit.
 */
class MultiformatAdUnitFacade extends AdUnit {

    @Nullable
    private BidResponse bidResponse;

    public MultiformatAdUnitFacade(@NotNull String configId, @NonNull PrebidRequest request) {
        super(configId);
        allowNullableAdObject = true;
        setConfigurationBasedOnRequest(request);
    }

    @Override
    protected BidRequesterListener createBidListener(OnCompleteListener originalListener) {
        Log.e("tpmn", "createBidListener");
        return new BidRequesterListener() {
            @Override
            public void onFetchCompleted(BidResponse response) {
                bidResponse = response;

                HashMap<String, String> keywords = response.getTargeting();
                Util.apply(keywords, adObject);

                originalListener.onComplete(ResultCode.SUCCESS);
            }

            @Override
            public void onError(AdException exception) {
                bidResponse = null;

                Util.apply(null, adObject);

                originalListener.onComplete(convertToResultCode(exception));
            }
        };
    }

    private void setConfigurationBasedOnRequest(
            @NonNull PrebidRequest request
    ) {
        Log.e("tpmn", "setConfigurationBasedOnRequest");
        if (request.isInterstitial()) {
            configuration.setAdPosition(AdPosition.FULLSCREEN);
        }

        BannerParameters bannerParameters = request.getBannerParameters();
        if (bannerParameters != null) {
            if (request.isInterstitial()) {
                configuration.addAdFormat(AdFormat.INTERSTITIAL);

                Integer minWidth = bannerParameters.getInterstitialMinWidthPercentage();
                Integer minHeight = bannerParameters.getInterstitialMinHeightPercentage();
                if (minWidth != null && minHeight != null) {
                    configuration.setMinSizePercentage(new AdSize(minWidth, minHeight));
                }
            } else {
                configuration.addAdFormat(AdFormat.BANNER);
            }

            configuration.setBannerParameters(bannerParameters);
            configuration.addSizes(bannerParameters.getAdSizes());
        }

        VideoParameters videoParameters = request.getVideoParameters();
        if (videoParameters != null) {
            configuration.addAdFormat(AdFormat.VAST);

            if (request.isInterstitial()) {
                configuration.setPlacementType(PlacementType.INTERSTITIAL);
            }
            if (request.isRewarded()) {
                configuration.setRewarded(true);
            }

            configuration.setVideoParameters(videoParameters);
            configuration.addSize(videoParameters.getAdSize());
        }

        NativeParameters nativeParameters = request.getNativeParameters();
        if (nativeParameters != null) {
            configuration.addAdFormat(AdFormat.NATIVE);

            NativeAdUnitConfiguration nativeConfig = nativeParameters.getNativeConfiguration();
            configuration.setNativeConfiguration(nativeConfig);
        }

        String gpid = request.getGpid();
        configuration.setGpid(gpid);

        ContentObject contentObject = request.getAppContent();
        configuration.setAppContent(contentObject);

        ArrayList<DataObject> userData = request.getUserData();
        configuration.setUserData(userData);

        Map<String, Set<String>> extData = request.getExtData();
        configuration.setExtData(extData);

        Set<String> extKeywords = request.getExtKeywords();
        configuration.setExtKeywords(extKeywords);
    }

    @Nullable
    public BidResponse getBidResponse() {
        return bidResponse;
    }

    @SuppressLint("VisibleForTests")
    @Override
    public AdUnitConfiguration getConfiguration() {
        return super.getConfiguration();
    }

}
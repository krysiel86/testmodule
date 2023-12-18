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

package io.github.krysiel86.mobile.api.mediation;

import android.content.Context;

import androidx.annotation.NonNull;

import io.github.krysiel86.mobile.AdSize;
import io.github.krysiel86.mobile.LogUtil;
import io.github.krysiel86.mobile.api.data.AdFormat;
import io.github.krysiel86.mobile.api.data.FetchDemandResult;
import io.github.krysiel86.mobile.api.mediation.MediationBaseFullScreenAdUnit;
import io.github.krysiel86.mobile.api.mediation.listeners.OnFetchCompleteListener;
import io.github.krysiel86.mobile.rendering.bidding.data.bid.BidResponse;
import io.github.krysiel86.mobile.rendering.bidding.display.BidResponseCache;
import io.github.krysiel86.mobile.rendering.bidding.display.PrebidMediationDelegate;
import io.github.krysiel86.mobile.rendering.models.AdPosition;

public class MediationRewardedVideoAdUnit extends MediationBaseFullScreenAdUnit {

    private static final String TAG = "MediationRewardedAdUnit";

    public MediationRewardedVideoAdUnit(
        Context context,
        String configId,
        PrebidMediationDelegate mediationDelegate
    ) {
        super(context, configId, null, mediationDelegate);
    }

    public void fetchDemand(@NonNull OnFetchCompleteListener listener) {
        super.fetchDemand(listener);
    }

    @Override
    protected final void initAdConfig(
        String configId,
        AdSize adSize
    ) {
        adUnitConfig.setConfigId(configId);
        adUnitConfig.setAdFormat(AdFormat.VAST);
        adUnitConfig.setRewarded(true);
        adUnitConfig.setAdPosition(AdPosition.FULLSCREEN);
    }

    @Override
    protected final void onResponseReceived(BidResponse response) {
        if (onFetchCompleteListener != null) {
            LogUtil.debug(TAG, "On response received");
            BidResponseCache.getInstance().putBidResponse(response.getId(), response);
            mediationDelegate.setResponseToLocalExtras(response);
            mediationDelegate.handleKeywordsUpdate(response.getTargeting());
            onFetchCompleteListener.onComplete(FetchDemandResult.SUCCESS);
        }
    }

}

package io.github.krysiel86.mobile.api.original;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.github.krysiel86.mobile.OnCompleteListener;
import io.github.krysiel86.mobile.OnCompleteListener2;
import io.github.krysiel86.mobile.ResultCode;
import io.github.krysiel86.mobile.Util;
import io.github.krysiel86.mobile.api.data.BidInfo;
import io.github.krysiel86.mobile.api.original.OnFetchDemandResult;

import java.util.Map;

/**
 * Listener implementation for multiformat PrebidAdUnit.
 */
class OnCompleteListenerImpl implements OnCompleteListener, OnCompleteListener2 {

    @Nullable
    private final Object adObject;
    @NonNull
    private final io.github.krysiel86.mobile.api.original.MultiformatAdUnitFacade adUnit;
    @NonNull
    private final io.github.krysiel86.mobile.api.original.OnFetchDemandResult listener;

    OnCompleteListenerImpl(
            @NonNull io.github.krysiel86.mobile.api.original.MultiformatAdUnitFacade adUnit,
            @Nullable Object adObject,
            @NonNull OnFetchDemandResult listener
    ) {
        this.adObject = adObject;
        this.adUnit = adUnit;
        this.listener = listener;
    }

    @Override
    public void onComplete(ResultCode resultCode) {
        notifyListener(resultCode);
    }

    @Override
    public void onComplete(ResultCode resultCode, @Nullable Map<String, String> unmodifiableMap) {
        notifyListener(resultCode);
    }


    private void notifyListener(ResultCode resultCode) {
        BidInfo bidInfo = BidInfo.create(resultCode, adUnit.getBidResponse(), adUnit.getConfiguration());
        Util.saveCacheId(bidInfo.getNativeCacheId(), adObject);
        listener.onComplete(bidInfo);
    }

}
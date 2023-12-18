package io.github.krysiel86.mobile.api.original;

import androidx.annotation.NonNull;

import io.github.krysiel86.mobile.api.data.BidInfo;

public interface OnFetchDemandResult {

    void onComplete(@NonNull BidInfo bidInfo);

}

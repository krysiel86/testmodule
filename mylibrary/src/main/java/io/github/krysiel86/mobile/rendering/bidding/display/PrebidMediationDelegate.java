package io.github.krysiel86.mobile.rendering.bidding.display;

import androidx.annotation.Nullable;

import io.github.krysiel86.mobile.rendering.bidding.data.bid.BidResponse;

import java.util.HashMap;

/**
 * PrebidMediationDelegate is a delegate of custom mediation platform.
 */
public interface PrebidMediationDelegate {

    /**
     * Sets keywords into a given mediation ad object
     */
    public void handleKeywordsUpdate(@Nullable HashMap<String, String> keywords);

    /**
     * Sets response into a given mediation ad object
     */
    public void setResponseToLocalExtras(@Nullable BidResponse response);

    /**
     * Checks if banner view is visible, and it is possible to make refresh.
     */
    public boolean canPerformRefresh();

}

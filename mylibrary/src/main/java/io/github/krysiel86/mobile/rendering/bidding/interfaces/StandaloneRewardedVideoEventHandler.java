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

package io.github.krysiel86.mobile.rendering.bidding.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.github.krysiel86.mobile.rendering.bidding.data.bid.Bid;
import io.github.krysiel86.mobile.rendering.bidding.interfaces.RewardedEventHandler;
import io.github.krysiel86.mobile.rendering.bidding.listeners.RewardedVideoEventListener;

public class StandaloneRewardedVideoEventHandler implements RewardedEventHandler {
    private RewardedVideoEventListener listener;

    @Override
    public void setRewardedEventListener(
        @NonNull
            RewardedVideoEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void requestAdWithBid(
        @Nullable
            Bid bid) {
        listener.onPrebidSdkWin();
    }

    @Override
    public void show() {

    }

    @Override
    public void trackImpression() {

    }

    @Override
    public void destroy() {

    }
}

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

package io.github.krysiel86.mobile.rendering.networking.modelcontrollers;

import android.text.TextUtils;

import io.github.krysiel86.mobile.configuration.AdUnitConfiguration;
import io.github.krysiel86.mobile.rendering.networking.ResponseHandler;
import io.github.krysiel86.mobile.rendering.networking.modelcontrollers.Requester;
import io.github.krysiel86.mobile.rendering.networking.parameters.AdRequestInput;
import io.github.krysiel86.mobile.rendering.networking.urlBuilder.BidPathBuilder;
import io.github.krysiel86.mobile.rendering.networking.urlBuilder.PathBuilderBase;

public class BidRequester extends Requester {

    private static final String REQUEST_NAME = "bidrequest";

    public BidRequester(AdUnitConfiguration config, AdRequestInput adRequestInput, ResponseHandler responseHandler) {
        super(config, adRequestInput, responseHandler);
        requestName = REQUEST_NAME;
    }

    @Override
    public void startAdRequest() {
        if (TextUtils.isEmpty(adConfiguration.getConfigId())) {
            if (adResponseCallBack != null) {
                adResponseCallBack.onError("No configuration id specified.", 0);
            }
            return;
        }

        getAdId();
    }

    @Override
    protected PathBuilderBase getPathBuilder() {
        return new BidPathBuilder();
    }
}

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

package io.github.krysiel86.mobile.rendering.sdk.deviceData.managers;

import io.github.krysiel86.mobile.rendering.networking.parameters.UserParameters;
import io.github.krysiel86.mobile.rendering.sdk.ManagersResolver;

/**
 * Manager for retrieving network information.
 *
 * @see ManagersResolver
 */
public interface ConnectionInfoManager {

    /**
     * Get the active connection type.
     *
     * @return the active connection type
     */
    UserParameters.ConnectionType getConnectionType();
}
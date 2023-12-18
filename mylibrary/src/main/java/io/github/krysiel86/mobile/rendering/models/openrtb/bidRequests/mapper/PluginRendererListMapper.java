package io.github.krysiel86.mobile.rendering.models.openrtb.bidRequests.mapper;

import java.util.ArrayList;
import java.util.List;

import io.github.krysiel86.mobile.api.rendering.pluginrenderer.PrebidMobilePluginRenderer;
import io.github.krysiel86.mobile.rendering.models.openrtb.bidRequests.PluginRenderer;

public class PluginRendererListMapper {

    public List<PluginRenderer> map(List<PrebidMobilePluginRenderer> prebidMobilePluginRenderers) {
        List<PluginRenderer> renderers = new ArrayList<>();
        for(PrebidMobilePluginRenderer element: prebidMobilePluginRenderers){
            PluginRenderer pluginRenderer = map(element);
            renderers.add(pluginRenderer);
        }
        return renderers;
    }

    private PluginRenderer map(PrebidMobilePluginRenderer prebidMobilePluginRenderer) {
        PluginRenderer pluginRenderer = new PluginRenderer();
        pluginRenderer.setName(prebidMobilePluginRenderer.getName());
        pluginRenderer.setVersion(prebidMobilePluginRenderer.getVersion());
        pluginRenderer.setData(prebidMobilePluginRenderer.getData());
        return pluginRenderer;
    }
}

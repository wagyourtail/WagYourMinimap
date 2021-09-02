package xyz.wagyourtail.minimap.api.config.layers;

import xyz.wagyourtail.minimap.client.gui.image.VanillaMapImageStrategy;

public class VanillaMapLayer extends AbstractLayerOptions<VanillaMapImageStrategy> {
    @Override
    public VanillaMapImageStrategy compileLayer() {
        return new VanillaMapImageStrategy();
    }

}
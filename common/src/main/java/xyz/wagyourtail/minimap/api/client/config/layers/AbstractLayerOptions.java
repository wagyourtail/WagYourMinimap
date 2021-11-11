package xyz.wagyourtail.minimap.api.client.config.layers;

import xyz.wagyourtail.minimap.map.image.AbstractImageStrategy;

public abstract class AbstractLayerOptions<T extends AbstractImageStrategy> {
    public abstract T compileLayer();

}
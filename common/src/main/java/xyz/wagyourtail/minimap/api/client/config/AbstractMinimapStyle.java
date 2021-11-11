package xyz.wagyourtail.minimap.api.client.config;

import xyz.wagyourtail.config.field.Setting;
import xyz.wagyourtail.minimap.api.client.config.layers.AbstractLayerOptions;
import xyz.wagyourtail.minimap.api.client.config.layers.LightLayer;
import xyz.wagyourtail.minimap.api.client.config.layers.VanillaMapLayer;
import xyz.wagyourtail.minimap.client.gui.hud.InGameHud;
import xyz.wagyourtail.minimap.client.gui.MapRendererBuilder;
import xyz.wagyourtail.minimap.map.image.AbstractImageStrategy;
import xyz.wagyourtail.minimap.map.image.BlockLightImageStrategy;
import xyz.wagyourtail.minimap.map.image.VanillaMapImageStrategy;
import xyz.wagyourtail.minimap.client.gui.hud.map.AbstractMinimapRenderer;
import xyz.wagyourtail.minimap.client.gui.hud.map.AbstractMapOverlayRenderer;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@SuppressWarnings("rawtypes")
public abstract class AbstractMinimapStyle<T extends AbstractMinimapRenderer> {
    public Map<Class<? extends AbstractMapOverlayRenderer>, Class<? extends AbstractOverlayOptions>> availableOverlays = new ConcurrentHashMap<>();

    public Map<Class<? extends AbstractImageStrategy>, Class<? extends AbstractLayerOptions>> availableLayers = new ConcurrentHashMap<>();

    @Setting(value = "gui.wagyourminimap.settings.style.overlay", options = "overlayOptions", setter = "setOverlays")
    public AbstractOverlayOptions<?>[] overlays;

    @Setting(value = "gui.wagyourminimap.settings.style.layers", options = "layerOptions", setter = "setLayers")
    public AbstractLayerOptions<?>[] layers;

    public AbstractMinimapStyle() {
        // default layers
        layers = new AbstractLayerOptions[] {new VanillaMapLayer(), new LightLayer()};

        //layer register
        availableLayers.put(VanillaMapImageStrategy.class, VanillaMapLayer.class);
        availableLayers.put(BlockLightImageStrategy.class, LightLayer.class);
    }

    public Collection<Class<? extends AbstractOverlayOptions>> overlayOptions() {
        return availableOverlays.values();
    }

    public Collection<Class<? extends AbstractLayerOptions>> layerOptions() {
        return availableLayers.values();
    }

    public void setOverlays(AbstractOverlayOptions<?>[] overlays) {
        this.overlays = overlays;
        AbstractMinimapRenderer renderer = InGameHud.getRenderer();
        renderer.setOverlays(Arrays.stream(overlays).map(e -> e.compileOverlay(renderer)).toArray(AbstractMapOverlayRenderer[]::new));
    }

    public void setLayers(AbstractLayerOptions<?>[] layers) {
        this.layers = layers;
        InGameHud.getRenderer().setRenderLayers(Arrays.stream(layers).map(AbstractLayerOptions::compileLayer).toArray(AbstractImageStrategy[]::new));
    }

    public T compileMapRenderer() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        MapRendererBuilder<T> builder = MapRendererBuilder.createBuilder(getMapRenderer());
        for (AbstractLayerOptions<?> layer : layers) {
            builder.addRenderLayer(layer.compileLayer());
        }
        for (AbstractOverlayOptions<?> overlay : overlays) {
            builder.addOverlay(overlay.compileOverlay(builder.getPartialMapRenderer()));
        }
        return builder.build();
    }

    protected abstract Class<T> getMapRenderer();

}
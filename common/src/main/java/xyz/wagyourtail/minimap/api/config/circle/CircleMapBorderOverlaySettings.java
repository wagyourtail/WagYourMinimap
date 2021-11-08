package xyz.wagyourtail.minimap.api.config.circle;

import xyz.wagyourtail.config.field.SettingsContainer;
import xyz.wagyourtail.minimap.api.config.AbstractOverlayOptions;
import xyz.wagyourtail.minimap.client.gui.renderer.AbstractMinimapRenderer;
import xyz.wagyourtail.minimap.client.gui.renderer.circle.CircleMapBorderOverlay;


@SettingsContainer("gui.wagyourminimap.settings.overlay.map_border")
public class CircleMapBorderOverlaySettings extends AbstractOverlayOptions<CircleMapBorderOverlay> {
    @Override
    public CircleMapBorderOverlay compileOverlay(AbstractMinimapRenderer mapRenderer) {
        return new CircleMapBorderOverlay(mapRenderer);
    }
}
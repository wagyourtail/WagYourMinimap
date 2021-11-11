package xyz.wagyourtail.minimap.api.client.config.square.norot;

import xyz.wagyourtail.config.field.SettingsContainer;
import xyz.wagyourtail.minimap.api.client.config.AbstractOverlayOptions;
import xyz.wagyourtail.minimap.client.gui.hud.map.AbstractMinimapRenderer;
import xyz.wagyourtail.minimap.client.gui.hud.map.square.rotate.SquareMapRotWaypointOverlay;

@SettingsContainer("gui.wagyourminimap.settings.overlay.waypoint")
public class SquareMapNoRotWaypointOverlaySettings extends AbstractOverlayOptions<SquareMapRotWaypointOverlay> {
    @Override
    public SquareMapRotWaypointOverlay compileOverlay(AbstractMinimapRenderer mapRenderer) {
        return new SquareMapRotWaypointOverlay(mapRenderer);
    }

}
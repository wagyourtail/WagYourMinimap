package xyz.wagyourtail.minimap.api.client;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.client.gui.components.Button;
import xyz.wagyourtail.minimap.client.gui.hud.map.AbstractMinimapRenderer;
import xyz.wagyourtail.minimap.client.gui.hud.overlay.AbstractMinimapOverlay;
import xyz.wagyourtail.minimap.client.gui.screen.WaypointListScreen;
import xyz.wagyourtail.minimap.client.gui.screen.widget.InteractMenu;
import xyz.wagyourtail.minimap.client.gui.screen.widget.MenuButton;
import xyz.wagyourtail.minimap.map.image.ImageStrategy;

import java.util.List;
import java.util.Set;

public class MinimapClientEvents {
    public static final Event<FullscreenMenu> FULLSCREEN_MENU = EventFactory.createLoop();
    public static final Event<FullscreenInteractMenu> FULLSCREEN_INTERACT_MENU = EventFactory.createLoop();
    public static final Event<WaypointListMenu> WAYPOINT_LIST_MENU = EventFactory.createLoop();
    public static final Event<AvailableMinimapOptions> AVAILABLE_MINIMAP_OPTIONS = EventFactory.createLoop();

    public interface FullscreenInteractMenu {
        void onPopulate(InteractMenu menu);

    }

    public interface FullscreenMenu {
        void onPopulate(List<MenuButton> buttons);

    }

    public interface WaypointListMenu {
        void onPopulate(WaypointListScreen screen, List<Button> buttons, List<Button> waypointNotNullButtons);

    }

    public interface AvailableMinimapOptions {
        void onOptions(AbstractMinimapRenderer style, Set<Class<? extends ImageStrategy>> layers, Set<Class<? extends AbstractMinimapOverlay>> overlays);

    }

}

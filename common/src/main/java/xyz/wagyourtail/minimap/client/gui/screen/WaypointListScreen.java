package xyz.wagyourtail.minimap.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.Nullable;
import xyz.wagyourtail.minimap.api.client.MinimapClientEvents;
import xyz.wagyourtail.minimap.client.gui.screen.widget.WaypointList;

import java.util.ArrayList;
import java.util.List;

public class WaypointListScreen extends Screen {
    private final Screen parent;
    private WaypointList waypointListWidget;
    private final List<Button> buttons = new ArrayList<>();
    private final List<Button> waypointNotNullButtons = new ArrayList<>();

    protected WaypointListScreen(Screen parent) {
        super(new TranslatableComponent("gui.wagyourminimap.waypoints"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        waypointListWidget = new WaypointList(this, minecraft, width, height, 32, height - 64, 16);
        addWidget(waypointListWidget);

        buttons.add(new Button(0, 0, 0, 20, new TranslatableComponent("gui.wagyourminimap.waypoints.add"), (button) -> {
            minecraft.setScreen(new WaypointEditScreen(this, null));

        }));

        waypointNotNullButtons.add(new Button(0, 0, 0, 20, new TranslatableComponent("gui.wagyourminimap.waypoints.edit"), (button) -> {
            WaypointList.WaypointListEntry selected = getSelected();
            if (selected != null) {
                assert minecraft != null;
                minecraft.setScreen(new WaypointEditScreen(this, selected.point));
            }
        }));

        MinimapClientEvents.WAYPOINT_LIST_MENU.invoker().onPopulate(this, buttons, waypointNotNullButtons);

        int waypointNotNullButtonsWidth = Math.min(100, width / waypointNotNullButtons.size());
        int offset = width - (waypointNotNullButtonsWidth * waypointNotNullButtons.size()) / 2;

        for (int i = 0; i < waypointNotNullButtons.size(); i++) {
            Button button = waypointNotNullButtons.get(i);
            button.x = i * waypointNotNullButtonsWidth + offset;
            button.y = height - 50;
            button.active = false;
            button.setWidth(waypointNotNullButtonsWidth - 5);
            addRenderableWidget(button);
        }

        int buttonsWidth = Math.min(100, width / buttons.size());
        offset = width - (buttons.size() * buttonsWidth) / 2;

        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            button.x = i * buttonsWidth + offset;
            button.y = height - 25;
            button.active = false;
            button.setWidth(buttonsWidth - 5);
            addRenderableWidget(button);
        }

    }

    public void setSelected(@Nullable WaypointList.WaypointListEntry entry) {
        this.waypointListWidget.setSelected(entry);
        this.onSelectedChange();
    }

    public WaypointList.WaypointListEntry getSelected() {
        return this.waypointListWidget.getSelected();
    }

    public void refreshEntries() {
        this.waypointListWidget.refreshEntries();
    }

    public void onSelectedChange() {
        WaypointList.WaypointListEntry selected = this.waypointListWidget.getSelected();
        if (selected != null) {
            for (Button button : waypointNotNullButtons) {
                button.active = true;
            }
        } else {
            for (Button button : waypointNotNullButtons) {
                button.active = false;
            }
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);

        this.waypointListWidget.render(poseStack, mouseX, mouseY, partialTicks);

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        minecraft.setScreen(parent);
    }

}
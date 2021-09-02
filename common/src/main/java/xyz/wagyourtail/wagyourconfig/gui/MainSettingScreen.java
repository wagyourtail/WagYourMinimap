package xyz.wagyourtail.wagyourconfig.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import xyz.wagyourtail.wagyourconfig.ConfigManager;
import xyz.wagyourtail.wagyourconfig.field.SettingsContainer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MainSettingScreen extends Screen {
    private final Screen parent;
    private final ConfigManager config;

    private final List<Button> pageButtons = new LinkedList<>();
    private final Class[] configs;
    private Button backButton;
    private Button forwardButton;

    protected MainSettingScreen(Component title, Screen parent, ConfigManager config) {
        super(title);
        this.parent = parent;
        this.config = config;
        this.configs = config.getRegisteredConfigs().toArray(new Class[0]);
    }

    @Override
    protected void init() {
        super.init();
        AtomicInteger currentPage = new AtomicInteger();

        backButton = addRenderableWidget(new Button(this.width / 2 - 210, this.height - 30, 100, 20, new TranslatableComponent("gui.wagyourconfig.back"), (btn) -> {
            drawPage(currentPage.decrementAndGet());
        }));

        forwardButton = addRenderableWidget(new Button(this.width / 2 - 105, this.height - 30, 100, 20, new TranslatableComponent("gui.wagyourconfig.forward"), (btn) -> {
            drawPage(currentPage.incrementAndGet());
        }));

        drawPage(0);
    }

    public void drawPage(int page) {
        pageButtons.forEach(this::removeWidget);
        pageButtons.clear();
        int height = this.height - 50 - 30;
        int buttonsPerPage = height / 30 * 2;
        int pages = configs.length / buttonsPerPage;
        int start = Mth.clamp(page, 0, pages) * buttonsPerPage;
        for (int i = start; i < start + buttonsPerPage && i < configs.length; ++i) {
            int finalI = i;
            MutableComponent title = new TranslatableComponent(config.get(configs[i]).getClass().getAnnotation(SettingsContainer.class).value());
            if (i % 2 == 0)
                pageButtons.add(addRenderableWidget(new Button(this.width / 2 - 210, 50 + (i / 2) * 30, 205, 20, title, (btn) -> {
                    minecraft.setScreen(new SettingScreen(title, this, config.get(configs[finalI])));
                })));
            else
                pageButtons.add(addRenderableWidget(new Button(this.width / 2 + 5, 50 + (i / 2) * 30, 205, 20, title, (btn) -> {
                    minecraft.setScreen(new SettingScreen(title, this, config.get(configs[finalI])));
                })));
        }
        backButton.active = page != 0;
        forwardButton.active = page < pages;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 17, 0xFFFFFF);
    }

    @Override
    public void onClose() {
        config.saveConfig();
        minecraft.setScreen(parent);
    }

}

package xyz.wagyourtail.minimap.map.image;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LightLayer;
import xyz.wagyourtail.config.field.IntRange;
import xyz.wagyourtail.config.field.Setting;
import xyz.wagyourtail.config.field.SettingsContainer;
import xyz.wagyourtail.minimap.map.image.colors.VanillaBlockColors;
import xyz.wagyourtail.minimap.map.image.imager.UndergroundImager;

@SettingsContainer("gui.wagyourminimap.setting.layers.vanilla_map")
public class UndergroundVanillaImageStrategy extends VanillaBlockColors implements UndergroundImager {

    @Setting("gui.wagyourminimap.setting.layers.underground.light_level")
    @IntRange(from = 1, to = 16)
    public int lightLevel = 8;

    @Override
    public boolean shouldRender() {
        assert UndergroundImager.minecraft.level != null;
        assert UndergroundImager.minecraft.player != null;
        int light = UndergroundImager.minecraft.level.getLightEngine().getLayerListener(LightLayer.SKY).getLightValue(
            new BlockPos(UndergroundImager.minecraft.player.getPosition(0)));
        return light < this.lightLevel;
    }

}

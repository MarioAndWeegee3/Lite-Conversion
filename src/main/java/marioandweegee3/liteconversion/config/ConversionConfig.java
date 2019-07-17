package marioandweegee3.liteconversion.config;

import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import me.sargunvohra.mcmods.autoconfig1.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1.annotation.Config;

@Config(name = "liteconversion")
public class ConversionConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public String blockConversionList = "cobblestone,stone; gravel,sandstone; dirt,grass_block,sand; oak_log, birch_log, spruce_log, dark_oak_log, jungle_log, acacia_log";
}
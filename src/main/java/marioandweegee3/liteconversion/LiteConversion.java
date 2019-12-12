package marioandweegee3.liteconversion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.cottonmc.libcd.api.LibCDInitializer;
import io.github.cottonmc.libcd.api.condition.ConditionManager;
import io.github.cottonmc.libcd.api.tweaker.TweakerManager;
import marioandweegee3.liteconversion.config.ConversionConfig;
import marioandweegee3.liteconversion.items.ConversionStone;
import marioandweegee3.liteconversion.tweaker.ConversionTweaker;
import marioandweegee3.ml3api.config.Config;
import marioandweegee3.ml3api.config.ConfigManager;
import marioandweegee3.ml3api.registry.RegistryHelper;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class LiteConversion implements ModInitializer, LibCDInitializer {
    public static String modID = "liteconversion";

    public static final Logger logger = LogManager.getLogger("Lite Conversion");

    public static RegistryHelper helper = new RegistryHelper(modID);

    public static final Identifier configID = helper.makeId("config");
    public static Config config;

    @Override
    public void onInitialize() {
        ConfigManager.INSTANCE.set(configID,
                new Config.Builder().add("blockConversion", ConversionConfig.defaultBlockConversions).build());

        config = ConfigManager.INSTANCE.getConfig(configID);

        helper.registerItem("trans_stone", new ConversionStone());
        helper.registerItem("infinite_stone",
                new ConversionStone(new Item.Settings().group(ItemGroup.MISC).maxCount(1), false));

        ConversionStone.makeRecipes();
    }

    @Override
    public void initTweakers(TweakerManager manager) {
        manager.addAssistant("liteconversion.tweaker.Conversion", ConversionTweaker.INSTANCE);
    }

    @Override
    public void initConditions(ConditionManager manager) {
        
    }
}
package marioandweegee3.liteconversion.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import io.github.prospector.modmenu.api.ModMenuApi;
import marioandweegee3.liteconversion.LiteConversion;
import marioandweegee3.liteconversion.config.ConversionConfig.BlockConversionConfig;
import marioandweegee3.liteconversion.items.ConversionStone;
import marioandweegee3.ml3api.config.ConfigManager;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

@Environment(EnvType.CLIENT)
public class ConfigScreen implements ModMenuApi{

    @Override
    public String getModId() {
        return LiteConversion.modID;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        return screen -> {
            ConfigBuilder builder = ConfigBuilder.create()
                .setTitle("Lite Conversion")
                .setParentScreen(screen)
                .setSavingRunnable(
                    ()->{
                        ConfigManager.INSTANCE.set(LiteConversion.configID, LiteConversion.config);
                        ConfigManager.INSTANCE.write(LiteConversion.configID);
                        ConversionStone.configUpdated = true;
                    }
                );
            
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            ConfigCategory general = builder.getOrCreateCategory("General");
            general.addEntry(entryBuilder
                .startStrList("Block Conversion", getConversionList(LiteConversion.config.get("blockConversion", List.class)))
                .setDefaultValue(getDefaultConversionList())
                .setSaveConsumer(list -> {
                    List<BlockConversionConfig> blockConversionConfigs = new ArrayList<>();
                    for(String s : list){
                        blockConversionConfigs.add(BlockConversionConfig.fromString(s));
                    }
                    LiteConversion.config.set("blockConversion", blockConversionConfigs);
                })
                .build()
            );
            
            return builder.build();
        };
    }

    private List<String> getDefaultConversionList(){
        List<BlockConversionConfig> configs = ConversionConfig.defaultBlockConversions;
        List<String> list = new ArrayList<>();
        for(BlockConversionConfig c : configs){
            list.add(c.toString());
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private List<String> getConversionList(List<Object> list){
        List<String> strings = new ArrayList<>();
        for(Object object : list){
            if(object instanceof Map){
                Map<String, Object> map = (Map<String, Object>)object;
                BlockConversionConfig blockConfig = BlockConversionConfig.fromGeneric(map);
                strings.add(blockConfig.toString());
            }
            if(object instanceof BlockConversionConfig){
                strings.add(((BlockConversionConfig)object).toString());
            }
        }
        return strings;
    }
}
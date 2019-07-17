package marioandweegee3.liteconversion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import marioandweegee3.liteconversion.config.ConversionConfig;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LiteConversion implements ModInitializer{
    public static String modID = "liteconversion";

    public static final Logger logger = LogManager.getLogger("Lite Conversion");

    @Override
    public void onInitialize() {
        AutoConfig.register(ConversionConfig.class, JanksonConfigSerializer::new);

        Item trans_stone = new ConversionStone();
        register(trans_stone, "trans_stone");

        ConversionStone.makeRecipes();
    }
    
    public static void register(Item item, String name){
        Registry.ITEM.add(makeID(name), item);
    }

    public static Identifier makeID(String name){
        return new Identifier(modID, name);
    }
}
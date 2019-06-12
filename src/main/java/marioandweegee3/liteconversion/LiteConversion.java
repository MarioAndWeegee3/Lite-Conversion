package marioandweegee3.liteconversion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LiteConversion implements ModInitializer{
    public static String modID = "liteconversion";

    public static final Logger logger = LogManager.getLogger("Lite Conversion");

    @Override
    public void onInitialize() {
        Item trans_stone = new Item(new Item.Settings().group(ItemGroup.MISC));
        register(trans_stone, "trans_stone");
    }
    
    public static void register(Item item, String name){
        Registry.register(Registry.ITEM, makeID(name), item);
    }

    public static Identifier makeID(String name){
        return new Identifier(modID, name);
    }
}
package marioandweegee3.liteconversion;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LiteConversion.modid)
public class LiteConversion{
    public static final String modid = "liteconversion";
    public static LiteConversion instance;

    public LiteConversion(){
        instance = this;

        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(
            new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(modid, "trans_stone")
        );
    }
}

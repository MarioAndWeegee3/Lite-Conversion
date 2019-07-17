package marioandweegee3.liteconversion.config;

import java.util.Optional;
import java.util.function.Supplier;

import io.github.prospector.modmenu.api.ModMenuApi;
import marioandweegee3.liteconversion.LiteConversion;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

@Environment(EnvType.CLIENT)
public class ConfigScreen implements ModMenuApi{

    @Override
    public String getModId() {
        return LiteConversion.modID;
    }

    @Override
    public Optional<Supplier<Screen>> getConfigScreen(Screen screen) {
        return Optional.of(AutoConfig.getConfigScreen(ConversionConfig.class, screen));
    }
}
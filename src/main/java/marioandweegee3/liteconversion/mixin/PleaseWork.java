package marioandweegee3.liteconversion.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerEntity;

//Does absolutely nothing
@Mixin(PlayerEntity.class)
public abstract class PleaseWork {

    @Inject(method = "tick", at = @At("RETURN"))
    public void nothing(CallbackInfo ci){
        //Does nothing
    }
}
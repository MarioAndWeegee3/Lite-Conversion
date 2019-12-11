package marioandweegee3.liteconversion.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface CustomRemainder{
    public ItemStack getRemainder(ItemStack stack, PlayerEntity player);
}
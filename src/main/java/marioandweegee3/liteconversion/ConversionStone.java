package marioandweegee3.liteconversion;

import java.util.ArrayList;

import marioandweegee3.liteconversion.config.ConversionConfig;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ConversionStone extends Item {
    private static ArrayList<BlockConversionRecipe> recipes = new ArrayList<>(0);

    private boolean finite;

    protected ConversionStone(Settings settings, boolean finite){
        super(settings);
        this.finite = finite;
    }

    public ConversionStone(){
        this(new Settings().group(ItemGroup.MISC), true);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Block input = world.getBlockState(pos).getBlock();
        if (hasRecipe(input)) {
            PlayerEntity player = context.getPlayer();
            BlockConversionRecipe recipe = getRecipe(input);

            if(!world.isClient){
                BlockState outState;
                if(player.isSneaking()){
                    outState = recipe.getSneakOutput(input).getDefaultState();
                } else {
                    outState = recipe.getOutput(input).getDefaultState();
                }

                world.setBlockState(pos, outState);
                
                if(this.finite) context.getStack().decrement(1);
            }

            world.playSound(player, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0f, 0.2f);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    public static void makeRecipes(){
        ConversionConfig config = AutoConfig.getConfigHolder(ConversionConfig.class).getConfig();

        for(String recipeStr : config.blockConversionList.trim().split(";")){
            ArrayList<Block> blocks = new ArrayList<>(0);
            for(String id : recipeStr.trim().split(",")){
                blocks.add(Registry.BLOCK.get(new Identifier(id.trim())));
            }
            if(blocks.size() != 0) recipes.add(new BlockConversionRecipe(blocks));
        }
    }

    private boolean hasRecipe(Block block){
        boolean has = false;
        for(BlockConversionRecipe recipe : recipes){
            if(recipe.contains(block)){
                has = true;
                break;
            }
        }
        return has;
    }

    private BlockConversionRecipe getRecipe(Block block){
        BlockConversionRecipe recipe = BlockConversionRecipe.empty;

        for(BlockConversionRecipe blockRecipe : recipes){
            if(blockRecipe.contains(block)){
                recipe = blockRecipe;
                break;
            }
        }

        return recipe;
    }

    public static class BlockConversionRecipe{
        public static final BlockConversionRecipe empty = new BlockConversionRecipe(Blocks.AIR);
        private ArrayList<Block> blocks;

        public BlockConversionRecipe(Block...blocks){
            this.blocks = new ArrayList<Block>(0);
            for(Block block : blocks){
                this.blocks.add(block);
            }
        }

        public BlockConversionRecipe(ArrayList<Block> blocks){
            this.blocks = blocks;
        }

        public Block getOutput(Block input){
            Block output = Blocks.AIR;
            if(contains(input)){
                output = blocks.get(getNext(blocks.indexOf(input)));
            }

            return output;
        }

        public Block getSneakOutput(Block input){
            Block output = Blocks.AIR;
            if(contains(input)){
                output = blocks.get(getPrevious(blocks.indexOf(input)));
            }

            return output;
        }

        public boolean contains(Block block){
            return blocks.contains(block);
        }

        private int getNext(int index){
            if(index + 1 < blocks.size()){
                return index + 1;
            } else {
                return 0;
            }
        }

        private int getPrevious(int index){
            if(index - 1 >= 0){
                return index - 1;
            } else {
                return blocks.size()-1;
            }
        }
    }
}
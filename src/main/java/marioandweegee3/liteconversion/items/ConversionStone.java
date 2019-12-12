package marioandweegee3.liteconversion.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import marioandweegee3.liteconversion.LiteConversion;
import marioandweegee3.liteconversion.config.ConversionConfig.BlockConversionConfig;
import marioandweegee3.ml3api.config.Config;
import marioandweegee3.ml3api.config.ConfigManager;
import marioandweegee3.ml3api.util.CustomRemainder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ConversionStone extends Item implements CustomRemainder {
    private static ArrayList<BlockConversionRecipe> recipes = new ArrayList<>();

    public static boolean configUpdated = true;

    private boolean finite;

    public ConversionStone(Settings settings, boolean finite) {
        super(settings);
        this.finite = finite;
    }

    public ConversionStone() {
        this(new Settings().group(ItemGroup.MISC), true);
    }

    @Override
    public ItemStack getRemainder(ItemStack stack, PlayerEntity player) {
        if (!this.finite) {
            return stack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState inState = world.getBlockState(pos);
        Block input = inState.getBlock();
        if (hasRecipe(input)) {
            PlayerEntity player = context.getPlayer();
            BlockConversionRecipe recipe = getRecipe(input);

            if (!world.isClient) {
                BlockState outState;
                if (player.isSneaking()) {
                    outState = recipe.getSneakOutput(input).getDefaultState();
                } else {
                    outState = recipe.getOutput(input).getDefaultState();
                }

                if (outState.contains(Properties.FACING) && inState.contains(Properties.FACING)) {
                    outState = outState.with(Properties.FACING, inState.get(Properties.FACING));
                }

                if (outState.contains(Properties.HORIZONTAL_FACING) && inState.contains(Properties.HORIZONTAL_FACING)) {
                    outState = outState.with(Properties.HORIZONTAL_FACING, inState.get(Properties.HORIZONTAL_FACING));
                }

                if (outState.contains(Properties.AXIS) && inState.contains(Properties.AXIS)) {
                    outState = outState.with(Properties.AXIS, inState.get(Properties.AXIS));
                }

                world.setBlockState(pos, outState);

                if (this.finite)
                    context.getStack().decrement(1);
            }

            Vec3d particlePos = new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

            for (int i = 0; i < 32; ++i) {
                world.addParticle(ParticleTypes.PORTAL, particlePos.getX(),
                        particlePos.getY() + world.random.nextDouble(), particlePos.getZ(),
                        world.random.nextGaussian() / 3, 0.0D, world.random.nextGaussian() / 3);
            }

            world.playSound(player, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                    SoundCategory.PLAYERS, 0.8f, world.random.nextInt(4) / 10f);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @SuppressWarnings("unchecked")
    public static void makeRecipes() {
        ConfigManager.INSTANCE.refresh(LiteConversion.configID);
        Config config = ConfigManager.INSTANCE.getConfig(LiteConversion.configID);

        List<Object> list = config.get("blockConversion", List.class);
        for (Object object : list) {
            if (object instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) object;
                BlockConversionConfig blockConfig = BlockConversionConfig.fromGeneric(map);
                ArrayList<Block> blocks = new ArrayList<>();
                for (String s : blockConfig.blocks) {
                    Block block = Registry.BLOCK.get(new Identifier(s));
                    blocks.add(block);
                }
                recipes.add(new BlockConversionRecipe(blocks));
            }
        }
        configUpdated = false;
    }

    private boolean hasRecipe(Block block) {
        boolean has = false;
        if (configUpdated) {
            recipes = new ArrayList<>();
            makeRecipes();
        }
        for (BlockConversionRecipe recipe : recipes) {
            if (recipe.contains(block)) {
                has = true;
                break;
            }
        }
        return has;
    }

    private BlockConversionRecipe getRecipe(Block block) {
        BlockConversionRecipe recipe = BlockConversionRecipe.empty;

        if (configUpdated) {
            recipes = new ArrayList<>();
            makeRecipes();
        }
        for (BlockConversionRecipe blockRecipe : recipes) {
            if (blockRecipe.contains(block)) {
                recipe = blockRecipe;
                break;
            }
        }

        return recipe;
    }

    public static class BlockConversionRecipe {
        public static final BlockConversionRecipe empty = new BlockConversionRecipe(Blocks.AIR);
        private ArrayList<Block> blocks;

        public BlockConversionRecipe(Block... blocks) {
            this.blocks = new ArrayList<Block>(0);
            for (Block block : blocks) {
                this.blocks.add(block);
            }
        }

        public BlockConversionRecipe(ArrayList<Block> blocks) {
            this.blocks = blocks;
        }

        public Block getOutput(Block input) {
            Block output = Blocks.AIR;
            if (contains(input)) {
                output = blocks.get(getNext(blocks.indexOf(input)));
            }

            return output;
        }

        public Block getSneakOutput(Block input) {
            Block output = Blocks.AIR;
            if (contains(input)) {
                output = blocks.get(getPrevious(blocks.indexOf(input)));
            }

            return output;
        }

        public boolean contains(Block block) {
            return blocks.contains(block);
        }

        private int getNext(int index) {
            if (index + 1 < blocks.size()) {
                return index + 1;
            } else {
                return 0;
            }
        }

        private int getPrevious(int index) {
            if (index - 1 >= 0) {
                return index - 1;
            } else {
                return blocks.size() - 1;
            }
        }
    }
}
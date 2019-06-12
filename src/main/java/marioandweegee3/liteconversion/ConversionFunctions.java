package marioandweegee3.liteconversion;

import io.github.cottonmc.cotton.tweaker.RecipeTweaker;
import io.github.cottonmc.cotton.tweaker.TweakerUtils;

public class ConversionFunctions {
    public static void addRecipe(String input, String output, int inCount, int outCount, boolean reversible){
        String[] inputs = new String[inCount+1];
        inputs[0] = "liteconversion:trans_stone";
        for(int i = 1; i < inCount+1; i++){
            inputs[i] = input;
        }
        RecipeTweaker.addShapeless(inputs, TweakerUtils.createItemStack(output, outCount));
        if(reversible){
            addRecipe(output, input, outCount, inCount, false);
        }
    }

    public static void addRecipe(String input, String output, int inCount, int outCount){
        addRecipe(input, output, inCount, outCount, true);
    }

    public static void addRecipe(String input, String output, int inCount){
        addRecipe(input, output, inCount, 1);
    }

    public static void addRecipe(String input, String output){
        addRecipe(input, output, 1);
    }

    public static void addCycle(int numItems, String[] inputs){
        for(int i = 0; i < numItems; i++){
            int next = i + 1;
            if(next >= numItems) next = 0;
            addRecipe(inputs[i], inputs[next], 1, 1, false);
        }
    }
}

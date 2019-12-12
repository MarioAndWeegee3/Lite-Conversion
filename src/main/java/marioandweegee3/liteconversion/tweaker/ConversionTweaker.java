package marioandweegee3.liteconversion.tweaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.github.cottonmc.libcd.api.tweaker.recipe.RecipeTweaker;
import io.github.cottonmc.libcd.api.tweaker.util.TweakerUtils;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;

public class ConversionTweaker {
    public static final ConversionTweaker INSTANCE = new ConversionTweaker();
    private int recipeNum = -1;

    private Identifier makeRecipeID(String output){
        recipeNum++;
        return new Identifier("liteconversion", "conversion/"+recipeNum+"_"+getPath(output));
    }

    private String getPath(String id){
        String[] splitId = id.split(":");
        if(splitId.length > 1){
            return splitId[1];
        } else return splitId[0];
    }

    public void addRecipe(String input, String output, int inCount, int outCount, boolean reversible){
        ArrayList<String> inputs = new ArrayList<>(0);
        inputs.add("#liteconversion:conversion_stone");
        for(int i = 0; i < inCount; i++){
            inputs.add(input);
        }

        DefaultedList<Ingredient> ingredients = DefaultedList.of();
        for (String item : inputs) {
            if(item.startsWith("#")) ingredients.add(Ingredient.ofItems(getItemsInTag(item.replace("#", ""))));
            else ingredients.add(Ingredient.ofItems(TweakerUtils.INSTANCE.getItem(item)));
        }
        
        RecipeTweaker.INSTANCE.addRecipe(new ShapelessRecipe(makeRecipeID(output), "", TweakerUtils.INSTANCE.createItemStack(output, outCount), ingredients));
        if(reversible) addRecipe(output, input, outCount, inCount, false);
    }

    public void addRecipe(String input, String output, int inCount, int outCount){
        addRecipe(input, output, inCount, outCount, true);
    }

    public void addRecipe(String input, String output, int inCount, boolean reversible){
        addRecipe(input, output, inCount, 1, reversible);
    }

    public void addRecipe(String input, String output, int inCount){
        addRecipe(input, output, inCount, 1, true);
    }

    public void addRecipe(String input, String output, boolean reversible){
        addRecipe(input, output, 1, 1, reversible);
    }

    public void addRecipe(String input, String output){
        addRecipe(input, output, 1, 1, true);
    }

    private int getNext(String[] values, int current){
        if(current + 1 >= values.length) return 0;
        else return current + 1;
    }

    public void addCycle(String[] values){
        for(int i = 0; i < values.length; i++){
            addRecipe(values[i], values[getNext(values, i)], false);
        }
    }

    private Item[] getItemsInTag(String tagId){
        String[] items = TweakerUtils.INSTANCE.getItemsInTag(tagId);
        ArrayList<Item> itemList = new ArrayList<>(0);
        for(String item : items){
            itemList.add(TweakerUtils.INSTANCE.getItem(item));
        }
        return itemList.toArray(new Item[0]);
    }

    public void addMultiRecipe(Map<String, Integer> input, String output, int outCount){
        ArrayList<String> inputs = new ArrayList<>(0);
        inputs.add("#liteconversion:conversion_stone");
        for(Map.Entry<String, Integer> entry : input.entrySet()){
            for(int i = 0; i < entry.getValue(); i++){
                inputs.add(entry.getKey());
            }
        }

        DefaultedList<Ingredient> ingredients = DefaultedList.of();
        for (String item : inputs) {
            if(item.startsWith("#")) ingredients.add(Ingredient.ofItems(getItemsInTag(item.replace("#", ""))));
            else ingredients.add(Ingredient.ofItems(TweakerUtils.INSTANCE.getItem(item)));
        }
        
        RecipeTweaker.INSTANCE.addRecipe(new ShapelessRecipe(makeRecipeID(output), "", TweakerUtils.INSTANCE.createItemStack(output, outCount), ingredients));
    }

    public void addMultiRecipe(Map<String, Integer> input, String output){
        addMultiRecipe(input, output, 1);
    }

    public void addMultiRecipe(String[] input, String output, int outCount){
        Map<String, Integer> inputMap = new HashMap<>(0);
        for(String item : input){
            inputMap.put(item, 1);
        }
        addMultiRecipe(inputMap, output, outCount);
    }

    public void addMultiRecipe(String[] input, String output){
        addMultiRecipe(input, output, 1);
    }
}
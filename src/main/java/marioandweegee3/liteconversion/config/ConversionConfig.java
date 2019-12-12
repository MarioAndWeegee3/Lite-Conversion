package marioandweegee3.liteconversion.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ConversionConfig {

    public static List<BlockConversionConfig> defaultBlockConversions = Arrays.asList(
        new BlockConversionConfig("cobblestone", "stone"),
        new BlockConversionConfig("gravel", "sandstone"),
        new BlockConversionConfig("dirt", "grass_block", "sand"),
        new BlockConversionConfig("oak_log", "birch_log", "spruce_log", "dark_oak_log", "jungle_log", "acacia_log"),
        new BlockConversionConfig("stripped_oak_log", "stripped_birch_log", "stripped_spruce_log", "stripped_dark_oak_log", "stripped_jungle_log", "stripped_acacia_log")
    );

    public static class BlockConversionConfig {
        public String[] blocks;

        public BlockConversionConfig(String... blocks){
            this.blocks = blocks;
        }

        @SuppressWarnings("unchecked")
        public static BlockConversionConfig fromGeneric(Map<String, Object> map){
            ArrayList<String> blocks = new ArrayList<>();

            Object object = map.get("blocks");
            if(object instanceof List){
                for(Object entry : (List<Object>)object){
                    String block = entry.toString();
                    blocks.add(block);
                }
            }

            return new BlockConversionConfig(blocks.toArray(new String[0]));
        }

        public static BlockConversionConfig fromString(String string){
            ArrayList<String> blocks = new ArrayList<>();
            for(String s : string.split(",")){
                blocks.add(s);
            }
            return new BlockConversionConfig(blocks.toArray(new String[0]));
        }

        @Override
        public String toString() {
            String s = "";
            for(int i = 0; i < blocks.length; i++){
                s += blocks[i];
                if(i < blocks.length-1){
                    s += ",";
                }
            }
            return s;
        }
    }
}
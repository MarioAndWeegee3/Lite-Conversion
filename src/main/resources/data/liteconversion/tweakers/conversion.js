var Conversion = Java.type("marioandweegee3.liteconversion.tweaker.ConversionTweaker")

var iron = "minecraft:iron_ingot"
var gold = "minecraft:gold_ingot"
var diamond = "minecraft:diamond"
var blazeRod = "minecraft:blaze_rod"
var blazePowder = "minecraft:blaze_powder"

var logs = [
    "oak_log",
    "birch_log",
    "spruce_log",
    "dark_oak_log",
    "jungle_log",
    "acacia_log"
]

Conversion.INSTANCE.addRecipe(iron, "minecraft:ender_pearl", 4)
Conversion.INSTANCE.addRecipe(iron, gold, 8)
Conversion.INSTANCE.addRecipe(gold, diamond, 4)
Conversion.INSTANCE.addRecipe(blazePowder, blazeRod, 2, false)
Conversion.INSTANCE.addRecipe(iron, blazeRod, 6)
Conversion.INSTANCE.addRecipe(iron, blazePowder, 3)
Conversion.INSTANCE.addRecipe(diamond, "minecraft:emerald", 2)
Conversion.INSTANCE.addRecipe("minecraft:prismarine_crystals", "minecraft:prismarine_shard")
Conversion.INSTANCE.addRecipe(iron, "minecraft:prismarine_shard", 2, false)
Conversion.INSTANCE.addRecipe("minecraft:clay_ball", "minecraft:redstone", 4, 2)
Conversion.INSTANCE.addRecipe("#minecraft:logs", "minecraft:obsidian", 2, false)
Conversion.INSTANCE.addRecipe("minecraft:glowstone_dust", blazeRod, 8, false)

Conversion.INSTANCE.addCycle(["redstone","obsidian"])
Conversion.INSTANCE.addCycle(["gravel","sandstone"])
Conversion.INSTANCE.addCycle(logs)
Conversion.INSTANCE.addCycle(["minecraft:stone", "minecraft:granite", "minecraft:andesite", "minecraft:diorite"])
Conversion.INSTANCE.addCycle(["minecraft:cobblestone", "minecraft:sand", "minecraft:dirt"])
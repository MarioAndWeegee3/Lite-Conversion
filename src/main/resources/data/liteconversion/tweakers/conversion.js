var Conversion = Java.type("marioandweegee3.liteconversion.ConversionFunctions")

var iron = "minecraft:iron_ingot"
var gold = "minecraft:gold_ingot"
var diamond = "minecraft:diamond"
var blazeRod = "minecraft:blaze_rod"
var blazePowder = "minecraft:blaze_powder"

var logs = [
    "minecraft:oak_log",
    "minecraft:birch_log",
    "minecraft:acacia_log",
    "minecraft:dark_oak_log",
    "minecraft:spruce_log",
    "minecraft:jungle_log"
]

Conversion.addRecipe(iron, "minecraft:ender_pearl", 4)
Conversion.addRecipe(iron, gold, 8)
Conversion.addRecipe(gold, diamond, 4)
Conversion.addRecipe(blazePowder, blazeRod, 2, 1, false)
Conversion.addRecipe(iron, blazeRod, 6)
Conversion.addRecipe(iron, blazePowder, 3)
Conversion.addRecipe(diamond, "minecraft:emerald", 2)
Conversion.addRecipe("minecraft:prismarine_crystals", "minecraft:prismarine_shard")
Conversion.addRecipe(iron, "minecraft:prismarine_shard", 2)
Conversion.addRecipe("minecraft:gravel", "minecraft:sandstone")
Conversion.addRecipe("minecraft:clay_ball", "minecraft:redstone", 4, 2)
Conversion.addRecipe("minecraft:redstone", "minecraft:obsidian")
Conversion.addRecipe("#minecraft:logs", "minecraft:obsidian", 2, 1, false)
Conversion.addRecipe("minecraft:glowstone_dust", blazeRod, 8, 2)

Conversion.addCycle(logs.length, logs)
Conversion.addCycle(4, ["minecraft:stone", "minecraft:granite", "minecraft:andesite", "minecraft:diorite"])
Conversion.addCycle(3, ["minecraft:cobblestone", "minecraft:sand", "minecraft:dirt"])
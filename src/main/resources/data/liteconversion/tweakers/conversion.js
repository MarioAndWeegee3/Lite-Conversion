var Conversion = libcd.require("liteconversion.tweaker.Conversion")

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

Conversion.addRecipe(iron, "minecraft:ender_pearl", 4)
Conversion.addRecipe(iron, gold, 8)
Conversion.addRecipe(gold, diamond, 4)
Conversion.addRecipe(blazePowder, blazeRod, 2, false)
Conversion.addRecipe(iron, blazeRod, 6)
Conversion.addRecipe(iron, blazePowder, 3)
Conversion.addRecipe(diamond, "minecraft:emerald", 2)
Conversion.addRecipe("minecraft:prismarine_crystals", "minecraft:prismarine_shard")
Conversion.addRecipe(iron, "minecraft:prismarine_shard", 2, false)
Conversion.addRecipe("minecraft:clay_ball", "minecraft:redstone", 4, 2)
Conversion.addRecipe("#minecraft:logs", "minecraft:obsidian", 2, false)
Conversion.addRecipe("minecraft:glowstone_dust", blazeRod, 8, false)

Conversion.addCycle(["redstone","obsidian"])
Conversion.addCycle(["gravel","sandstone"])
Conversion.addCycle(logs)
Conversion.addCycle(["minecraft:stone", "minecraft:granite", "minecraft:andesite", "minecraft:diorite"])
Conversion.addCycle(["minecraft:cobblestone", "minecraft:sand", "minecraft:dirt"])
import json

numRecipes = 0

def getPath(id):
    outPath = ""
    if(len(id.split(":")) > 1):
        outPath = id.split(":")[1]
    else:
        outPath = id
    return outPath

def getRecipeFileName(output):
    global numRecipes
    outPath = getPath(output)
    name = "conversion_"+str(numRecipes)+"_"+outPath+".json"
    numRecipes += 1
    return name

def makeRecipeJson(ingredients, result):
    return {
        "type":"crafting_shapeless",
        "ingredients":ingredients,
        "result":result
    }

def isTag(string):
    path = getPath(string)
    if(path[0] == "#"):
        return True
    else: 
        return False

def tagOrItem(string):
    if(isTag(string)):
        return "tag"
    else:
        return "item"

def removeTagMarker(string):
    splitId = string.split(":")
    if(splitId[0] == string):
        return string.replace("#", "")
    else:
        namespace = splitId[0]
        path = splitId[1]
        return namespace + ":" + path.replace("#", "")


def makeNormalRecipe(input, output, inCount = 1, outCount = 1, reversible = True):
    recipe = open(getRecipeFileName(output), "w")
    ingredients = []
    ingredients.append({"item":"liteconversion:trans_stone"})
    for i in range(1, inCount+1):
        ingredients.append({tagOrItem(input):removeTagMarker(input)})
    result = {
        "item":output,
        "count":outCount
    }
    recipeJson = makeRecipeJson(ingredients, result)
    recipe.write(json.dumps(recipeJson, indent = 4))
    if(reversible and not isTag(input)):
        makeNormalRecipe(output, input, outCount, inCount, False)

def makeCycle(items):
    length = len(items)
    for i in range(length):
        nextItem = i + 1
        if(nextItem == length):
            nextItem = 0
        makeNormalRecipe(input = items[i], output = items[nextItem], reversible = False)

def makeMultiRecipe(inputs, output, outCount = 1):
    recipe = open(getRecipeFileName(output), "w")
    ingredients = []
    ingredients.append({"item":"liteconversion:trans_stone"})


    totalAmount = 1
    for count in inputs.values():
        totalAmount += count
    

    for item in inputs:
        for count in range(inputs[item]):
            ingredients.append({tagOrItem(item):removeTagMarker(item)})
    
    result = {
        "item":output,
        "count":outCount
    }
    recipeJson = makeRecipeJson(ingredients, result)
    recipe.write(json.dumps(recipeJson, indent = 4))

def getRecipes():
    print("This is for normal recipes.")
    while True:
        inputItem = str(input("Input the namespaced id for the input: "))
        if(inputItem.lower() == "skip"): break
        inputCount = int(input("Input the amount of that item: "))
        
        outputItem = str(input("Input the namespaced id for the output: "))
        outputCount = int(input("Input the amount of that item: "))

        reversible = bool(input("Is this recipe reversible? (True or False) "))

        makeNormalRecipe(inputItem, outputItem, inputCount, outputCount, reversible)
        if(str(input("Do you want to make another? (True or False) ")).lower() == "false"):
            break
        else: print("")
    print("")
    print("This is for cycles.")
    while True:
        items = []
        while True:
            items.append(str(input("Input the namespaced id for an item in the cycle: ")))
            if(items[0].lower() == "skip"): break
            if(str(input("Do you want to add another? (True or False) ")).lower() == "false"):
                break
        if(items[0].lower() == "skip"): break

        print("")
        makeCycle(items)
        if(str(input("Do you want to make another? (True or False) ")).lower() == "false"):
            break
        else: print("")
    print("")
    print("This is for Multi-input recipes.")
    while True:
        inputs = {}
        while True:
            item = str(input("Input the namespaced id for an item in the recipe: "))
            if(item.lower() == "skip"): 
                inputs["skip"] = 0
                break
            itemCount = int(input("Input the amount of that item: "))

            inputs[item] = itemCount

            if(str(input("Do you want to add another? (True or False) ")).lower() == "false"):
                break
        if "skip" in inputs: break

        outputItem = str(input("Input the namespaced id for the output: "))
        outputCount = int(input("Input the amount of that item: "))

        print("")
        makeMultiRecipe(inputs, outputItem, outputCount)
        if(str(input("Do you want to make another? (True or False) ")).lower() == "false"):
            break
        else: print("")

getRecipes()

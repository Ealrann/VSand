#define MASS_DELTA_RATIO 0.001

#define SOLID   0
#define LIQUID  1
#define GAZ     2
#define SPECIAL 3

const uint FALLING_FLAG = 1u << 8;

layout (constant_id = 0) const int MATERIAL_COUNT = 1;
layout (constant_id = 1) const int WIDTH = 1;
layout (constant_id = 2) const int HEIGHT = 1;

const int TRANSFOM_ARRAY_COUNT = MATERIAL_COUNT * MATERIAL_COUNT / 4;

layout (push_constant) uniform PushConstants {
    // Random number, change every frame.
    float random;
    int globalXOffset;
    int globalYOffset;
} pushConstants;

// The definition of materials.
layout(binding = 0) uniform SConfiguration
{
    Material materials[MATERIAL_COUNT];
} configuration;

// The chunk state board.
//layout(binding = 1) restrict buffer SChunks
//{
//    int data[CHUNK_WIDTH][CHUNK_HEIGHT];
//}chunks;

// The game board, with all the values.
layout(binding = 2) readonly buffer SBoard1
{
    uint data[WIDTH * HEIGHT];
}board1;

// The next game board, with all the values.
layout(binding = 3) buffer SBoard2
{
    uint data[WIDTH * HEIGHT];
}board2;

// The description of the transformations.
// Each cell contains :
// 1 bit for the sign
// 1 bit for isStatic (if the transformation should occur only if the material doesn't move.
// 14 bits for the probability (not all used, probability is only an integer from 0 to 1000).
// 8 bits for the propagated distance.
// 8 bits for the target value.
layout(binding = 4) uniform STransformation
{
// float[] is horrible in uniform buffers (std140), so we use uvec4[]
    uvec4 data[TRANSFOM_ARRAY_COUNT];
}transformations;

const Value voidValue = Value(0, 1., false);

//
Value readValue(const uint value);
uint writeValue(const Value value);
float firstIdealMass(const float mass, const uint size, const float density);

Value unpackValue(const uint value)
{
    if (value == 0) return voidValue;
    const uint materialId = value & 0xFFu;
    const float mass = unpackHalf2x16(value).y;
    const bool falling = (value & FALLING_FLAG) != 0;
    return Value(materialId, mass, falling);
}

uint packValue(const uint materialId, const float mass, const bool falling)
{
    return materialId | (packHalf2x16(vec2(0., mass)) & 0xFFFF0000u) | (falling ? FALLING_FLAG : 0);
}

uint packValue(const Value value)
{
    return packValue(value.materialId, value.mass, value.falling);
}

float massDelta(const float density)
{
    return MASS_DELTA_RATIO * density;
}

float firstIdealMass(const float mass, const uint height, const float density)
{
    // Han formula
    const float massDelta = massDelta(density);
    return (mass / height) - (((height - 1) * massDelta) / 2.);
}

//float pressureNewCrop(const uint type, const float inheritedFirstPressure, const float pressure, const uint size, const int density);
//void writeCrop(const uint cropAddress, const uint x, const uint y, const bool vertical);
//float firstIdealPressure(const uint cropAddress);
//float lastIdealPressure(const uint cropAddress);
////float lastTheoreticalPressure(const uint cropAddress);
////float firstEffectivePressure(uint cropAddress);
////float lastEffectivePressure(uint cropAddress);
//void growCropTop(const uint cropAddress);
//void growCropBottom(const uint cropAddress);
//void shrinkCropTop(const uint cropAddress);
//void shrinkCropBottom(const uint cropAddress);
//void pushCropUp(const uint cropAddress);
//void pushCropDown(const uint cropAddress);
//float removeCropTop(const uint cropAddress);
//float removeCropBottom(const uint cropAddress);
//void addCropTop(const uint cropAddress, const float addPressure);
//void addCropBottom(const uint cropAddress, const float addPressure);
//int splitCropBottom(const uint cropAddress);
//int splitCropTop(const uint cropAddress);
//void swapCropWithNext(const uint cropAddress);
//
////float firstPressure(const uint cropAddress);
////float firstPressure(const uint cropAddress, const int sizeOffset);
////float lastPressure(const uint cropAddress);
////float lastPressure(const uint cropAddress, const int sizeOffset);
//
//void loadCrops(const uvec2 startLocation, const uint cropSize, const bool vertical)
//{
//    uint size = 0;
//    float pressure = 0;
//    float nextIdealPressure = -1.;
//    bool first = true;
//    bool currentWasFalling = false;
//    uint currentMaterialId = voidValue.materialId;
//    Material currentMaterial = configuration.materials[currentMaterialId];
//    for (int i = 0; i < cropSize; i++)
//    {
//        const uint boardValue = vertical ? board2.data[startLocation.x + (i + startLocation.y) * WIDTH]
//                                         : board1.data[startLocation.x + i + (startLocation.y * WIDTH)];
//        const Value value = readValue(boardValue);
//        if (first
//                || value.materialId != currentMaterialId
//                || (vertical && value.falling == false && currentWasFalling == true)
//                || (!vertical && (value.falling != currentWasFalling)))
//        {
//            Material newMaterial = configuration.materials[value.materialId];
//
//            if (first == false)
//            {
//                const float firstPressure = currentMaterial.isStatic == 1 ? DEFAULT_PRESSURE : pressureNewCrop(currentMaterial.type, nextIdealPressure, pressure, size, currentMaterial.density);
//                cropPush(currentMaterialId, currentMaterial.density, size, firstPressure, pressure, currentMaterial.isStatic == 1, currentWasFalling, currentMaterial.type == LIQUID, currentMaterial.type == GAZ);
//                nextIdealPressure = firstPressure != -1. ? firstPressure + (size * PRESSURE_DELTA * (currentMaterial.density + 3)) : -1.;
//            }
//            size = 0;
//            pressure = 0;
//
//            currentMaterialId = value.materialId;
//            currentWasFalling = value.falling;
//            currentMaterial = newMaterial;
//
//            first = false;
//        }
//
//        size ++;
//        pressure += value.pressure;
//    }
//    const float firstPressure = currentMaterial.isStatic == 1 ? DEFAULT_PRESSURE : pressureNewCrop(currentMaterial.type, nextIdealPressure, pressure, size, currentMaterial.density);
//    cropPush(currentMaterialId, currentMaterial.density, size, firstPressure, pressure, currentMaterial.isStatic == 1, currentWasFalling, currentMaterial.type == LIQUID, currentMaterial.type == GAZ);
//
//    if (vertical)
//    {
//        int downCrop = -1;
//        int upCrop = cropQueue.lastIndex;
//        while (upCrop != -1)
//        {
//            const bool upNotStatic = cropQueue.queue[upCrop].isStatic == false;
//            const bool downNotStatic = downCrop == -1 ? false : cropQueue.queue[downCrop].isStatic == false;
//            const bool heavier = downCrop == -1 ? false : getDensity(upCrop) > getDensity(downCrop);
//            const bool wasFalling = cropQueue.queue[upCrop].falling;
//
//            if(upNotStatic)
//            {
//                if (heavier && downNotStatic)
//                {
//                    cropQueue.queue[upCrop].falling = true;
//                }
//                else if (wasFalling == true)
//                {
//                    if(cropQueue.queue[upCrop].isLiquid)
//                    {
//                        if(downCrop == -1 || getMaterialId(upCrop) != getMaterialId(downCrop))
//                        {
//                            if (cropQueue.queue[upCrop].size == 1)
//                            {
//                                cropQueue.queue[upCrop].falling = false;
//                            }
//                            else
//                            {
//                                int splitBottom = splitCropBottom(upCrop);
//                                if(splitBottom != -1) cropQueue.queue[splitBottom].falling = false;
//                            }
//                        }
//                    }
//                    else
//                    {
//                        cropQueue.queue[upCrop].falling = false;
//                    }
//                }
//            }
//            else
//            {
//                cropQueue.queue[upCrop].falling = false;
//            }
//
//            downCrop = upCrop;
//            upCrop = cropQueue.queue[upCrop].previousCrop;
//        }
//    }
//}
//
//float pressureNewCrop(const uint type, const float inheritedFirstPressure, const float pressure, const uint size, const int density)
//{
////    switch(type)
////    {
////        case LIQUID:
////        case GAZ:
////        {
//            return (inheritedFirstPressure != -1.) ? inheritedFirstPressure : firstIdealPressure(pressure, size, density);
////        }
////        default:
////        {
////            return DEFAULT_PRESSURE;
////        }
////    }
//}
//
//void saveCrops(const uvec2 startLocation, const uint size, const bool vertical)
//{
//    uint offset = 0;
//    int index = cropQueue.firstIndex;
//    while (index != -1)
//    {
//        writeCrop(index, vertical ? startLocation.x : startLocation.x + offset, vertical ? startLocation.y + offset : startLocation.y, vertical);
//        offset += cropQueue.queue[index].size;
//        index = cropQueue.queue[index].nextCrop;
//    }
//
//    // copy value we didn't managed (lack of space in queue)
//    if (vertical)
//    {
//        while (offset < size)
//        {
//            board2.data[startLocation.x + (startLocation.y + offset) * WIDTH] = board1.data[startLocation.x + offset * WIDTH];
//            offset++;
//        }
//    }
//}
//
//
//void writeCrop(const uint cropAddress, const uint x, const uint y, const bool vertical)
//{
//    uint materialId = getMaterialId(cropAddress);
//    const bool falling = cropQueue.queue[cropAddress].falling;
//    if (vertical)
//    {
//        const bool liquidOrGaz = cropQueue.queue[cropAddress].isLiquid || cropQueue.queue[cropAddress].isGaz; // && falling == false;
//        const float currentPressure = firstIdealPressure(cropAddress);
//        const float pressureDelta = cropQueue.queue[cropAddress].isStatic ? 0. : PRESSURE_DELTA * (getDensity(cropAddress) + 3);
//
//        for (int i = 0; i < cropQueue.queue[cropAddress].size; i++)
//        {
//            const uint packedValue = materialId | (packHalf2x16(vec2(0., currentPressure + i * pressureDelta)) & 0xFFFF0000u) | (falling ? MOVE_TAG : 0);
//            board2.data[x + (y + i) * WIDTH] = packedValue;
//        }
//    }
//    else
//    {
//        const float pressure = cropQueue.queue[cropAddress].pressure / cropQueue.queue[cropAddress].size;
//        const uint packedValue = materialId | (packHalf2x16(vec2(0., pressure)) & 0xFFFF0000u) | (falling ? MOVE_TAG : 0);
//        for (int i = 0; i < cropQueue.queue[cropAddress].size && x + i < WIDTH; i++)
//        {
//            board2.data[x + i + y * WIDTH] = packedValue;
//        }
//    }
//}
//
//float firstIdealPressure(const uint cropAddress)
//{
//    return firstIdealPressure(cropQueue.queue[cropAddress].pressure, cropQueue.queue[cropAddress].size, getDensity(cropAddress));
//}
//
//float firstIdealPressure(const uint cropAddress, const int offset)
//{
//    return firstIdealPressure(cropQueue.queue[cropAddress].pressure, cropQueue.queue[cropAddress].size + offset, getDensity(cropAddress));
//}
//


//
//float lastIdealPressure(const uint cropAddress, const int offset)
//{
//    if(cropQueue.queue[cropAddress].isStatic == false &&
//    (cropQueue.queue[cropAddress].isLiquid || cropQueue.queue[cropAddress].isGaz))
//    {
//        const int density = getDensity(cropAddress);
//        const float pressureDelta = PRESSURE_DELTA * (density + 3);
//        return firstIdealPressure(cropQueue.queue[cropAddress].pressure, cropQueue.queue[cropAddress].size + offset, density) + ((cropQueue.queue[cropAddress].size - 1) * pressureDelta);
//    }
//    else
//    {
//        return DEFAULT_PRESSURE;
//    }
//}
//
//float lastIdealPressure(const uint cropAddress)
//{
//    return lastIdealPressure(cropAddress, 0);
//}
//
////float lastTheoreticalPressure(const uint cropAddress)
////{
////    if(cropQueue.queue[cropAddress].isStatic == false &&
////        (cropQueue.queue[cropAddress].isLiquid || cropQueue.queue[cropAddress].isGaz))
////    {
////        const float pressureDelta = PRESSURE_DELTA * (getDensity(cropAddress) + 3);
////        return cropQueue.queue[cropAddress].firstTheoreticalPressure + ((cropQueue.queue[cropAddress].size - 1) * pressureDelta);
////    }
////    else
////    {
////        return DEFAULT_PRESSURE;
////    }
////}
////
////float firstEffectivePressure(uint cropAddress)
////{
////    if(cropQueue.queue[cropAddress].isLiquid)
////    {
////        const uint sizeMinusFirst = cropQueue.queue[cropAddress].size - 1;
////        const float totalIdealPressureMinusFirst = sizeMinusFirst * cropQueue.queue[cropAddress].firstTheoreticalPressure + ((sizeMinusFirst * (sizeMinusFirst - 1)) / 2.) * PRESSURE_DELTA;
////
////        return max(0., cropQueue.queue[cropAddress].pressure - totalIdealPressureMinusFirst);
////    }
////    else if (cropQueue.queue[cropAddress].isGaz)
////    {
////        return firstIdealPressure(cropQueue.queue[cropAddress].pressure, cropQueue.queue[cropAddress].size, getDensity(cropAddress));
////    }
////    else
////    {
////        return cropQueue.queue[cropAddress].firstTheoreticalPressure;
////    }
////}
////
////float lastEffectivePressure(uint cropAddress)
////{
////    if(cropQueue.queue[cropAddress].isLiquid)
////    {
////        const float lastTheoreticalPressure = lastTheoreticalPressure(cropAddress);
////        return min(lastTheoreticalPressure, cropQueue.queue[cropAddress].pressure);
////    }
////    else
////    {
////        const float pressureDelta = PRESSURE_DELTA * (getDensity(cropAddress) + 3);
////        return firstEffectivePressure(cropAddress) + ((cropQueue.queue[cropAddress].size - 1) * pressureDelta);
////    }
////}
//
//void growCropTop(const uint cropAddress)
//{
//    const float pressureDelta = PRESSURE_DELTA * (getDensity(cropAddress) + 3);
//    cropQueue.queue[cropAddress].size ++;
//    cropQueue.queue[cropAddress].firstTheoreticalPressure -= pressureDelta;
//}
//
//void growCropBottom(const uint cropAddress)
//{
//    cropQueue.queue[cropAddress].size ++;
//    const int nextCrop = cropQueue.queue[cropAddress].nextCrop;
//    const float pressureDelta = PRESSURE_DELTA * (getDensity(nextCrop) + 3);
//    if(nextCrop != -1) cropQueue.queue[nextCrop].firstTheoreticalPressure += pressureDelta;
//}
//
//void shrinkCropTop(const uint cropAddress)
//{
//    const float pressureDelta = PRESSURE_DELTA * (getDensity(cropAddress) + 3);
//    cropQueue.queue[cropAddress].size --;
//    cropQueue.queue[cropAddress].firstTheoreticalPressure += pressureDelta;
//}
//
//void shrinkCropBottom(const uint cropAddress)
//{
//    cropQueue.queue[cropAddress].size --;
//    const int nextCrop = cropQueue.queue[cropAddress].nextCrop;
//    const float pressureDelta = PRESSURE_DELTA * (getDensity(nextCrop) + 3);
//    if(nextCrop != -1) cropQueue.queue[nextCrop].firstTheoreticalPressure -= pressureDelta;
//}
//
//void pushCropUp(const uint cropAddress)
//{
//    growCropTop(cropAddress);
//    shrinkCropBottom(cropQueue.queue[cropAddress].previousCrop);
//}
//
//void pushCropDown(const uint cropAddress)
//{
//    growCropBottom(cropAddress);
//    shrinkCropTop(cropQueue.queue[cropAddress].nextCrop);
//}
//
//float removeCropTop(const uint cropAddress)
//{
//    if(cropQueue.queue[cropAddress].size == 1)
//    {
//        const float removedPressure = cropQueue.queue[cropAddress].pressure;
//        removeCrop(cropAddress);
//        return removedPressure;
//    }
//    else
//    {
//        const float removedPressure = firstIdealPressure(cropAddress);
//        cropQueue.queue[cropAddress].pressure -= removedPressure;
//        shrinkCropTop(cropAddress);
//        return removedPressure;
//    }
//}
//
//float removeCropBottom(const uint cropAddress)
//{
//    if(cropQueue.queue[cropAddress].size == 1)
//    {
//        const float removedPressure = cropQueue.queue[cropAddress].pressure;
//        removeCrop(cropAddress);
//        return removedPressure;
//    }
//    else
//    {
//        float removedPressure = lastIdealPressure(cropAddress);
//        cropQueue.queue[cropAddress].pressure -= removedPressure;
//        shrinkCropBottom(cropAddress);
//        return removedPressure;
//    }
//}
//
//void addCropTop(const uint cropAddress, const float addPressure)
//{
//    growCropTop(cropAddress);
//    cropQueue.queue[cropAddress].pressure += addPressure;
//}
//
//void addCropBottom(const uint cropAddress, const float addPressure)
//{
//    growCropBottom(cropAddress);
//    cropQueue.queue[cropAddress].pressure += addPressure;
//}
//
//int splitCropBottom(const uint cropAddress)
//{
//    Crop newCrop = cropQueue.queue[cropAddress];
//    newCrop.size = 1;
//    newCrop.firstTheoreticalPressure = lastIdealPressure(cropAddress);
//    const int newCropAddress = insertCropAfter(cropAddress, newCrop);
//    if(newCropAddress != -1)
//    {
//        const float removedPressure = removeCropBottom(cropAddress);
//        cropQueue.queue[newCropAddress].pressure = removedPressure;
//    }
//    return newCropAddress;
//}
//
//int splitCropTop(const uint cropAddress)
//{
//    Crop newCrop = cropQueue.queue[cropAddress];
//    newCrop.size = 1;
//    newCrop.firstTheoreticalPressure = cropQueue.queue[cropAddress].firstTheoreticalPressure;
//    const int newCropAddress = insertCropBefore(cropAddress, newCrop);
//    if(newCropAddress != -1)
//    {
//        const float removedPressure = removeCropTop(cropAddress);
//        cropQueue.queue[newCropAddress].pressure = removedPressure;
//    }
//    return newCropAddress;
//}
//
//void swapCropWithNext(const uint cropAddress)
//{
//    // only support size 1 for now (otherwise, firstTheoreticalPressures will be wrong)
//
//    const int swapAddress = cropQueue.queue[cropAddress].nextCrop;
//    const int previousAddress = cropQueue.queue[cropAddress].previousCrop;
//    const int nextAddress = cropQueue.queue[swapAddress].nextCrop;
//
//    const float firstTheoreticalPressure = cropQueue.queue[swapAddress].firstTheoreticalPressure;
//    cropQueue.queue[swapAddress].firstTheoreticalPressure = cropQueue.queue[cropAddress].firstTheoreticalPressure;
//    cropQueue.queue[cropAddress].firstTheoreticalPressure = firstTheoreticalPressure;
//
//    cropQueue.queue[swapAddress].previousCrop = previousAddress;
//    cropQueue.queue[swapAddress].nextCrop = int(cropAddress);
//    cropQueue.queue[cropAddress].nextCrop = nextAddress;
//    cropQueue.queue[cropAddress].previousCrop = swapAddress;
//    if(nextAddress != -1) cropQueue.queue[nextAddress].previousCrop = int(cropAddress);
//    else cropQueue.lastIndex = int(cropAddress);
//    if(previousAddress != -1) cropQueue.queue[previousAddress].nextCrop = swapAddress;
//    else cropQueue.firstIndex = swapAddress;
//}

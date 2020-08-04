#define DEFAULT_PRESSURE 1.
#define PRESSURE_DELTA 0.001

#define SOLID   0
#define LIQUID  1
#define GAZ     2
#define SPECIAL 3

const uint MOVE_TAG = 1u << 8;

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
    Entry materials[MATERIAL_COUNT];
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

const Value voidValue = Value(0, DEFAULT_PRESSURE, false);


Value readValue(const uint value);
float pressureNewCrop(const uint type, const float theoricalFirstPressure, const float pressure, const uint size);
uint writeValue(Value value);
void writeCrop(const uint cropAddress, const uint x, const uint y, const bool vertical);
float firstIdealPressure(const float pressure, const uint size);
float lastIdealPressure(const uint cropAddress);
float lastTheoreticalPressure(const uint cropAddress);
float firstEffectivePressure(uint cropAddress);
float lastEffectivePressure(uint cropAddress);
void growCropTop(const uint cropAddress);
void growCropBottom(const uint cropAddress);
void shrinkCropTop(const uint cropAddress);
void shrinkCropBottom(const uint cropAddress);
void pushCropUp(const uint cropAddress);
void pushCropDown(const uint cropAddress);
float removeCropTop(const uint cropAddress);
float removeCropBottom(const uint cropAddress);
void addCropTop(const uint cropAddress, const float addPressure);
void addCropBottom(const uint cropAddress, const float addPressure);
int splitCropBottom(const uint cropAddress);
int splitCropTop(const uint cropAddress);
void swapCropWithNext(const uint cropAddress);

//float firstPressure(const uint cropAddress);
//float firstPressure(const uint cropAddress, const int sizeOffset);
//float lastPressure(const uint cropAddress);
//float lastPressure(const uint cropAddress, const int sizeOffset);

void loadCrops(const uvec2 startLocation, const uint cropSize, const bool vertical)
{
    uint size = 0;
    float pressure = 0;
    float nextIdealPressure = -1.;
    bool first = true;
    bool currentWasFalling = false;
    uint currentMaterialId = voidValue.materialId;
    Entry currentMaterial = configuration.materials[currentMaterialId];
    for (int i = 0; i < cropSize; i++)
    {
        const uint boardValue = vertical ? board1.data[startLocation.x + (i + startLocation.y) * WIDTH]
                                         : board2.data[startLocation.x + i + (startLocation.y * WIDTH)];
        const Value value = readValue(boardValue);
        if (first || value.materialId != currentMaterialId || (vertical && value.falling == false && currentWasFalling == true))
        {
            Entry newMaterial = configuration.materials[value.materialId];

            if (first == false)
            {
                const float firstPressure = pressureNewCrop(currentMaterial.type, nextIdealPressure, pressure, size);
                cropPush(currentMaterialId, currentMaterial.density, size, firstPressure, pressure, currentMaterial.isStatic == 1, currentWasFalling, currentMaterial.type == LIQUID, currentMaterial.type == GAZ);
                nextIdealPressure = firstPressure != -1. ? firstPressure + (size * PRESSURE_DELTA) : -1.;
            }
            size = 0;
            pressure = 0;

            currentMaterialId = value.materialId;
            currentWasFalling = value.falling;
            currentMaterial = newMaterial;

            first = false;
        }

        size ++;
        pressure += value.pressure;
    }
    const float firstPressure = pressureNewCrop(currentMaterial.type, nextIdealPressure, pressure, size);
    cropPush(currentMaterialId, currentMaterial.density, size, firstPressure, pressure, currentMaterial.isStatic == 1, currentWasFalling, currentMaterial.type == LIQUID, currentMaterial.type == GAZ);

    if (vertical)
    {
        int downCrop = cropQueue.lastIndex;
        int upCrop = cropQueue.queue[downCrop].previousCrop;
        while (upCrop != -1)
        {
            const bool upNotStatic = cropQueue.queue[upCrop].isStatic == false;
            const bool downNotStatic = cropQueue.queue[downCrop].isStatic == false;
            const bool heavier = getDensity(upCrop) > getDensity(downCrop);
            const bool wasFalling = cropQueue.queue[upCrop].falling;

            if(upNotStatic)
            {
                if (heavier && downNotStatic)
                {
                    cropQueue.queue[upCrop].falling = true;
                }
                else if (wasFalling == true)
                {
                    if(cropQueue.queue[upCrop].isLiquid)
                    {
                        if(getDensity(upCrop) != getDensity(downCrop))
                        {
                            if (cropQueue.queue[upCrop].size == 1)
                            {
                                cropQueue.queue[upCrop].falling = false;
                            }
                            else
                            {
                                int splitBottom = splitCropBottom(upCrop);
                                if(splitBottom != -1) cropQueue.queue[splitBottom].falling = false;
                            }
                        }
                    }
                    else
                    {
                        cropQueue.queue[upCrop].falling = false;
                    }
                }
            }

            downCrop = upCrop;
            upCrop = cropQueue.queue[upCrop].previousCrop;
        }
    }
}

float pressureNewCrop(const uint type, const float theoricalFirstPressure, const float pressure, const uint size)
{
    switch(type)
    {
        case LIQUID:
        case GAZ:
        {
            return (theoricalFirstPressure != -1.) ? theoricalFirstPressure : firstIdealPressure(pressure, size);
        }
        default:
        {
            return DEFAULT_PRESSURE;
        }
    }
}

void saveCrops(const uvec2 startLocation, const uint size, const bool vertical)
{
    uint offset = 0;
    int index = cropQueue.firstIndex;
    while (index != -1)
    {
        writeCrop(index, vertical ? startLocation.x : startLocation.x + offset, vertical ? startLocation.y + offset : startLocation.y, vertical);
        offset += cropQueue.queue[index].size;
        index = cropQueue.queue[index].nextCrop;
    }

    // copy value we didn't managed (lack of space in queue)
    if (vertical)
    {
        while (offset < size)
        {
            board2.data[startLocation.x + (startLocation.y + offset) * WIDTH] = board1.data[startLocation.x + offset * WIDTH];
            offset++;
        }
    }
}

Value readValue(const uint value)
{
    if (value == 0) return voidValue;
    const uint materialId = value & 0xFFu;
    const float pressure = unpackHalf2x16(value).y;
    const bool falling = (value & MOVE_TAG) != 0;
    return Value(materialId, pressure, falling);
}

uint writeValue(Value value)
{
    return value.materialId | (packHalf2x16(vec2(0., value.pressure - 1.)) & 0xFFFF0000u) | (value.falling ? MOVE_TAG : 0);
}

void writeCrop(const uint cropAddress, const uint x, const uint y, const bool vertical)
{
    uint materialId = getMaterialId(cropAddress);
    const bool falling = cropQueue.queue[cropAddress].falling;
    if (vertical)
    {
        const bool pressurized = cropQueue.queue[cropAddress].isLiquid || cropQueue.queue[cropAddress].isGaz; // && falling == false;
        const float pressureDelta = pressurized ? PRESSURE_DELTA : 0.;
        const float firstEffectivePressure = firstEffectivePressure(cropAddress);

        float remainingPressure = cropQueue.queue[cropAddress].pressure;
        float currentIdealPressure = firstEffectivePressure <= cropQueue.queue[cropAddress].firstTheoreticalPressure ? lastTheoreticalPressure(cropAddress) : lastIdealPressure(cropAddress);
        for (int i = int(cropQueue.queue[cropAddress].size) - 1; i >= 0; i--)
        {
            const float localPressure = clamp(currentIdealPressure, 0., remainingPressure);
            const uint packedValue = materialId | (packHalf2x16(vec2(0., localPressure)) & 0xFFFF0000u) | (falling ? MOVE_TAG : 0);
            board2.data[x + (y + i) * WIDTH] = packedValue;

            remainingPressure -= localPressure;
            currentIdealPressure -= pressureDelta;
        }
    }
    else
    {
        const float pressure = cropQueue.queue[cropAddress].pressure / cropQueue.queue[cropAddress].size;
        const uint packedValue = materialId | (packHalf2x16(vec2(0., pressure)) & 0xFFFF0000u) | (falling ? MOVE_TAG : 0);
        for (int i = 0; i < cropQueue.queue[cropAddress].size && x + i < WIDTH; i++)
        {
            board2.data[x + i + y * WIDTH] = packedValue;
        }
    }
}

float firstIdealPressure(const float pressure, const uint size)
{
    // Han formula
    return (pressure / size) - (((size - 1) * PRESSURE_DELTA) / 2.);
}

float lastIdealPressure(const uint cropAddress)
{
    if(cropQueue.queue[cropAddress].isStatic == false &&
    (cropQueue.queue[cropAddress].isLiquid || cropQueue.queue[cropAddress].isGaz))
    {
        return firstIdealPressure(cropQueue.queue[cropAddress].pressure, cropQueue.queue[cropAddress].size) + ((cropQueue.queue[cropAddress].size - 1) * PRESSURE_DELTA);
    }
    else
    {
        return DEFAULT_PRESSURE;
    }
}

float lastTheoreticalPressure(const uint cropAddress)
{
    if(cropQueue.queue[cropAddress].isStatic == false &&
        (cropQueue.queue[cropAddress].isLiquid || cropQueue.queue[cropAddress].isGaz))
    {
        return cropQueue.queue[cropAddress].firstTheoreticalPressure + ((cropQueue.queue[cropAddress].size - 1) * PRESSURE_DELTA);
    }
    else
    {
        return DEFAULT_PRESSURE;
    }
}

float firstEffectivePressure(uint cropAddress)
{
    const uint sizeMinusFirst = cropQueue.queue[cropAddress].size - 1;
    const float totalIdealPressureMinusFirst = sizeMinusFirst * cropQueue.queue[cropAddress].firstTheoreticalPressure + ((sizeMinusFirst * (sizeMinusFirst - 1)) / 2.) * PRESSURE_DELTA;

    return max(0., cropQueue.queue[cropAddress].pressure - totalIdealPressureMinusFirst);
}

float lastEffectivePressure(uint cropAddress)
{
    const float lastTheoreticalPressure = lastTheoreticalPressure(cropAddress);
    return min(lastTheoreticalPressure, cropQueue.queue[cropAddress].pressure);
}

void growCropTop(const uint cropAddress)
{
    cropQueue.queue[cropAddress].size ++;
    cropQueue.queue[cropAddress].firstTheoreticalPressure -= PRESSURE_DELTA;
}

void growCropBottom(const uint cropAddress)
{
    cropQueue.queue[cropAddress].size ++;
    const int nextCrop = cropQueue.queue[cropAddress].nextCrop;
    if(nextCrop != -1) cropQueue.queue[nextCrop].firstTheoreticalPressure += PRESSURE_DELTA;
}

void shrinkCropTop(const uint cropAddress)
{
    cropQueue.queue[cropAddress].size --;
    cropQueue.queue[cropAddress].firstTheoreticalPressure += PRESSURE_DELTA;
}

void shrinkCropBottom(const uint cropAddress)
{
    cropQueue.queue[cropAddress].size --;
    const int nextCrop = cropQueue.queue[cropAddress].nextCrop;
    if(nextCrop != -1) cropQueue.queue[nextCrop].firstTheoreticalPressure -= PRESSURE_DELTA;
}

void pushCropUp(const uint cropAddress)
{
    growCropTop(cropAddress);
    shrinkCropBottom(cropQueue.queue[cropAddress].previousCrop);
}

void pushCropDown(const uint cropAddress)
{
    growCropBottom(cropAddress);
    shrinkCropTop(cropQueue.queue[cropAddress].nextCrop);
}

float removeCropTop(const uint cropAddress)
{
    if(cropQueue.queue[cropAddress].size == 1)
    {
        const float removedPressure = cropQueue.queue[cropAddress].pressure;
        removeCrop(cropAddress);
        return removedPressure;
    }
    else
    {
        const float removedPressure = firstEffectivePressure(cropAddress);
        cropQueue.queue[cropAddress].pressure -= removedPressure;
        shrinkCropTop(cropAddress);
        return removedPressure;
    }
}

float removeCropBottom(const uint cropAddress)
{
    if(cropQueue.queue[cropAddress].size == 1)
    {
        const float removedPressure = cropQueue.queue[cropAddress].pressure;
        removeCrop(cropAddress);
        return removedPressure;
    }
    else
    {
        float removedPressure = lastEffectivePressure(cropAddress);
        cropQueue.queue[cropAddress].pressure -= removedPressure;
        shrinkCropBottom(cropAddress);
        return removedPressure;
    }
}

void addCropTop(const uint cropAddress, const float addPressure)
{
    growCropTop(cropAddress);
    cropQueue.queue[cropAddress].pressure += addPressure;
}

void addCropBottom(const uint cropAddress, const float addPressure)
{
    growCropBottom(cropAddress);
    cropQueue.queue[cropAddress].pressure += addPressure;
}

int splitCropBottom(const uint cropAddress)
{
    Crop newCrop = cropQueue.queue[cropAddress];
    newCrop.size = 1;
    newCrop.firstTheoreticalPressure = lastTheoreticalPressure(cropAddress);
    const int newCropAddress = insertCropAfter(cropAddress, newCrop);
    if(newCropAddress != -1)
    {
        const float removedPressure = removeCropBottom(cropAddress);
        cropQueue.queue[newCropAddress].pressure = removedPressure;
    }
    return newCropAddress;
}

int splitCropTop(const uint cropAddress)
{
    Crop newCrop = cropQueue.queue[cropAddress];
    newCrop.size = 1;
    newCrop.firstTheoreticalPressure = cropQueue.queue[cropAddress].firstTheoreticalPressure;
    const int newCropAddress = insertCropBefore(cropAddress, newCrop);
    if(newCropAddress != -1)
    {
        const float removedPressure = removeCropTop(cropAddress);
        cropQueue.queue[newCropAddress].pressure = removedPressure;
    }
    return newCropAddress;
}

void swapCropWithNext(const uint cropAddress)
{
    // only support size 1 for now (otherwise, firstTheoreticalPressures will be wrong)

    const int swapAddress = cropQueue.queue[cropAddress].nextCrop;
    const int previousAddress = cropQueue.queue[cropAddress].previousCrop;
    const int nextAddress = cropQueue.queue[swapAddress].nextCrop;

    const float firstTheoreticalPressure = cropQueue.queue[swapAddress].firstTheoreticalPressure;
    cropQueue.queue[swapAddress].firstTheoreticalPressure = cropQueue.queue[cropAddress].firstTheoreticalPressure;
    cropQueue.queue[cropAddress].firstTheoreticalPressure = firstTheoreticalPressure;

    cropQueue.queue[swapAddress].previousCrop = previousAddress;
    cropQueue.queue[swapAddress].nextCrop = int(cropAddress);
    cropQueue.queue[cropAddress].nextCrop = nextAddress;
    cropQueue.queue[cropAddress].previousCrop = swapAddress;
    if(nextAddress != -1) cropQueue.queue[nextAddress].previousCrop = int(cropAddress);
    else cropQueue.lastIndex = int(cropAddress);
    if(previousAddress != -1) cropQueue.queue[previousAddress].nextCrop = swapAddress;
    else cropQueue.firstIndex = swapAddress;
}

//float firstPressure(const uint cropAddress)
//{
//    return firstPressure(cropAddress, 0);
//}
//
//float firstPressure(const uint cropAddress, const int sizeOffset)
//{
//    const float pressure = cropQueue.queue[cropAddress].pressure;
//    const int size = int(cropQueue.queue[cropAddress].size) + sizeOffset;
//    const bool liquidOrGaz = cropQueue.queue[cropAddress].isLiquid || cropQueue.queue[cropAddress].isGaz;
//    if (liquidOrGaz) // && cropQueue.queue[cropAddress].falling == false)
//    {
//        if(cropQueue.queue[cropAddress].firstTheoricalPressure == -1.)
//        {
//            uint density = getDensity(cropAddress);
//            return (pressure / size) - (((size - 1) * PRESSURE_DELTA) / 2.);
//        }
//        else
//        {
//
//        }
//    }
//    else
//    {
//        return pressure / size;
//    }
//}
//
//float lastPressure(const uint cropAddress)
//{
//    return lastPressure(cropAddress, 0);
//}
//
//float lastPressure(const uint cropAddress, const int sizeOffset)
//{
//    const float pressure = cropQueue.queue[cropAddress].pressure;
//    const int size = int(cropQueue.queue[cropAddress].size) + sizeOffset;
//    const bool liquidOrGaz = cropQueue.queue[cropAddress].isLiquid || cropQueue.queue[cropAddress].isGaz;
//    if (liquidOrGaz) // && cropQueue.queue[cropAddress].falling == false)
//    {
//        const uint density = getDensity(cropAddress);
//        return firstPressure(cropAddress) + (PRESSURE_DELTA * (size - 1));
//    }
//    else
//    {
//        return pressure / size;
//    }
//}

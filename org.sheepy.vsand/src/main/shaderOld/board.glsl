#define EDGE_SIZE 1
#define DEFAULT_PRESSURE 1.
#define PRESSURE_DELTA 0.0002

#define SOLID   0
#define LIQUID  1
#define GAZ     2
#define SPECIAL 3

const uint MOVE_TAG = 1u << 8;

layout (constant_id = 0) const int MATERIAL_COUNT = 1;
layout (constant_id = 1) const int WIDTH = 1;
layout (constant_id = 2) const int HEIGHT = 1;

const int TRANSFOM_ARRAY_COUNT = MATERIAL_COUNT * MATERIAL_COUNT / 4;

// Random number, change each frame.
layout (push_constant) uniform PushConstants {
    float random;
} pushConstants;

// The definition of materials.
layout(binding = 0) uniform SConfiguration
{
    Entry materials[MATERIAL_COUNT];
} configuration;

// The chunk state board.
layout(binding = 1) restrict buffer SChunks
{
    int data[CHUNK_WIDTH][CHUNK_HEIGHT];
}chunks;

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
void writeCrop(const uint cropAddress, const uint x, const uint y, const bool vertical);
float firstPressure(const uint cropAddress);
float firstPressure(const uint cropAddress, const int sizeOffset);
float lastPressure(const uint cropAddress);
float lastPressure(const uint cropAddress, const int sizeOffset);


void buildCrops(const uvec2 startLocation, const bool vertical)
{
    uint size = 0;
    float pressure = 0;
    bool first = true;
    bool currentWasFalling = false;
    bool horizontalStuck = false;
    uint currentMaterialId = voidValue.materialId;
    Entry currentMaterial = configuration.materials[currentMaterialId];
    cropPush(currentMaterialId, currentMaterial.density, EDGE_SIZE, EDGE_SIZE * DEFAULT_PRESSURE, currentMaterial.isStatic == 1, voidValue.moving, currentMaterial.type == LIQUID, currentMaterial.type == GAZ);
    for (int i = 0; i < (vertical ? HEIGHT : WIDTH); i++)
    {
        const uint boardValue = vertical ? board1.data[startLocation.x + (i * WIDTH)]
                                        : board2.data[i + (startLocation.y * WIDTH)];
        const Value value = readValue(boardValue);
        if (value.materialId != currentMaterialId || (vertical && value.moving == false && currentWasFalling == true))
        {
            Entry newMaterial = configuration.materials[value.materialId];
            const bool notStatic = currentMaterial.isStatic != 1;
            const bool heavier = newMaterial.density < currentMaterial.density;
            const bool willFall = notStatic && (currentWasFalling || heavier || (currentMaterial.type != GAZ && value.moving));

            if (size != 0)
            {
                if (first && currentMaterial.type == GAZ) pressure = size;
                cropPush(currentMaterialId, currentMaterial.density, size, pressure, currentMaterial.isStatic == 1, vertical ? willFall : horizontalStuck, currentMaterial.type == LIQUID, currentMaterial.type == GAZ);
                first = false;
            }
            size = 0;
            pressure = 0;

            currentMaterialId = value.materialId;
            currentWasFalling = value.moving;
            currentMaterial = newMaterial;
            horizontalStuck = value.moving;
        }
        else
        {
            horizontalStuck = horizontalStuck && value.moving;
        }

        size ++;
        pressure += value.pressure;
    }
    Entry newMaterial = configuration.materials[voidValue.materialId];
    const bool canSwap = currentMaterial.isStatic != 1;
    const bool heavier = newMaterial.density < currentMaterial.density;
    const bool willFall = canSwap && heavier;
    if (currentMaterial.type == GAZ) pressure = size;
    cropPush(currentMaterialId, currentMaterial.density, size, pressure, currentMaterial.isStatic == 1, vertical ? willFall : horizontalStuck, currentMaterial.type == LIQUID, currentMaterial.type == GAZ);
    currentMaterial = configuration.materials[voidValue.materialId];
    cropPush(voidValue.materialId, currentMaterial.density, EDGE_SIZE, EDGE_SIZE * DEFAULT_PRESSURE, currentMaterial.isStatic == 1, voidValue.moving, currentMaterial.type == LIQUID, currentMaterial.type == GAZ);

    if (vertical)
    {
        int downCrop = cropQueue.lastIndex;
        int upCrop = cropQueue.queue[downCrop].previousCrop;
        while (upCrop != -1)
        {
            if (cropQueue.queue[upCrop].moving && cropQueue.queue[downCrop].moving == false && getDensity(upCrop) < getDensity(downCrop))
            {
                float lastUpPressure = lastPressure(upCrop);
                if (cropQueue.queue[upCrop].size == 1)
                {
                    cropQueue.queue[upCrop].moving = false;
                }
                else
                {
                    cropQueue.queue[upCrop].pressure -= lastUpPressure;
                    cropQueue.queue[upCrop].size --;

                    Crop newCrop = cropQueue.queue[upCrop];
                    newCrop.size = 1;
                    newCrop.pressure = lastUpPressure;
                    newCrop.moving = false;
                    insertCropBefore(downCrop, newCrop);
                }
            }
            downCrop = upCrop;
            upCrop = cropQueue.queue[upCrop].previousCrop;
        }
    }
}

void writeCrops(const uvec2 startLocation, const bool vertical)
{
    for (int i = 0; i < EDGE_SIZE; i++)
    {
        const int beginIndex = cropQueue.firstIndex;
        const float firstPressure = vertical ? firstPressure(beginIndex) : (cropQueue.queue[beginIndex].pressure / cropQueue.queue[beginIndex].size);
        cropQueue.queue[beginIndex].pressure -= firstPressure;
        cropQueue.queue[beginIndex].size --;

        const int endIndex = cropQueue.lastIndex;
        const float lastPressure = vertical ? lastPressure(endIndex) : (cropQueue.queue[endIndex].pressure / cropQueue.queue[endIndex].size);
        cropQueue.queue[endIndex].pressure -= lastPressure;
        cropQueue.queue[endIndex].size --;

        if (cropQueue.queue[beginIndex].size == 0) removeCrop(beginIndex);
        if (cropQueue.queue[endIndex].size == 0) removeCrop(endIndex);
    }

    uint offset = 0;
    int index = cropQueue.firstIndex;
    while (index != -1)
    {
        writeCrop(index, vertical ? startLocation.x : offset, vertical ? offset : startLocation.y, vertical);
        offset += cropQueue.queue[index].size;
        index = cropQueue.queue[index].nextCrop;
    }

    // copy value we didn't managed (lack of space in queue)
    if (vertical)
    {
        while (offset < HEIGHT)
        {
            board2.data[startLocation.x + offset * WIDTH] = board1.data[startLocation.x + offset * WIDTH];
            offset++;
        }
    }
}

Value readValue(const uint value)
{
    if (value == 0) return voidValue;
    const uint materialId = value & 0xFFu;
    const float pressure = unpackSnorm2x16(value).y + 1.;
    const bool moving = (value & MOVE_TAG) != 0;
    return Value(materialId, pressure, moving);
}

void writeCrop(const uint cropAddress, const uint x, const uint y, const bool vertical)
{
    uint materialId = getMaterialId(cropAddress);
    const bool moving = cropQueue.queue[cropAddress].moving;
    if (vertical)
    {
        const uint density = getDensity(cropAddress);
        const bool pressurized = cropQueue.queue[cropAddress].isLiquid && cropQueue.queue[cropAddress].moving == false;
        const float pressureDelta = pressurized ? PRESSURE_DELTA * density : 0;
        float pressure = firstPressure(cropAddress);
        for (int i = 0; i < cropQueue.queue[cropAddress].size && y + i < HEIGHT; i++)
        {
            const uint packedValue = materialId | (packSnorm2x16(vec2(0., pressure - 1.)) & 0xFFFF0000u) | (moving ? MOVE_TAG : 0);
            board2.data[x + (y + i) * WIDTH] = packedValue;
            pressure += pressureDelta;
        }
    }
    else
    {
        const float pressure = cropQueue.queue[cropAddress].pressure / cropQueue.queue[cropAddress].size;
        const uint packedValue = materialId | (packSnorm2x16(vec2(0., pressure - 1.)) & 0xFFFF0000u) | (moving ? MOVE_TAG : 0);
        for (int i = 0; i < cropQueue.queue[cropAddress].size && x + i < WIDTH; i++)
        {
            board2.data[x + i + y * WIDTH] = packedValue;
        }
    }
}

float firstPressure(const uint cropAddress)
{
    return firstPressure(cropAddress, 0);
}

float firstPressure(const uint cropAddress, const int sizeOffset)
{
    const float pressure = cropQueue.queue[cropAddress].pressure;
    const int size = int(cropQueue.queue[cropAddress].size) + sizeOffset;
    if (cropQueue.queue[cropAddress].isLiquid && cropQueue.queue[cropAddress].moving == false)
    {
        // Han formula
        uint density = getDensity(cropAddress);
        return (pressure / size) - (((size - 1) * PRESSURE_DELTA * density) / 2.);
    }
    else
    {
        return pressure / size;
    }
}

float lastPressure(const uint cropAddress)
{
    return lastPressure(cropAddress, 0);
}

float lastPressure(const uint cropAddress, const int sizeOffset)
{
    const float pressure = cropQueue.queue[cropAddress].pressure;
    const int size = int(cropQueue.queue[cropAddress].size) + sizeOffset;
    if (cropQueue.queue[cropAddress].isLiquid && cropQueue.queue[cropAddress].moving == false)
    {
        const uint density = getDensity(cropAddress);
        return firstPressure(cropAddress) + (PRESSURE_DELTA * density * (size - 1));
    }
    else
    {
        return pressure / size;
    }
}

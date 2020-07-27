#define EDGE_SIZE 1
#define DEFAULT_PRESSURE 1.
#define PRESSURE_DELTA 0.002

#define SOLID   0
#define LIQUID  1
#define GAZ     2
#define SPECIAL 3

layout (constant_id = 0) const int MATERIAL_COUNT = 1;
layout (constant_id = 1) const int WIDTH = 1;
layout (constant_id = 2) const int HEIGHT = 1;

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


const Value voidValue = Value(0, DEFAULT_PRESSURE);


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
    uint currentMaterialId = voidValue.materialId;
    Entry material = configuration.materials[currentMaterialId];
    cropPush(voidValue.materialId, material.density, EDGE_SIZE, EDGE_SIZE * DEFAULT_PRESSURE, material.isStatic == 1, material.type == LIQUID, material.type == GAZ);
    for (int i = 0; i < (vertical ? HEIGHT : WIDTH); i++)
    {
        const uint boardValue = vertical ? board1.data[startLocation.x + ((startLocation.y + i) * WIDTH)]
                                            : board2.data[startLocation.x + i + (startLocation.y * WIDTH)];
        const Value value = readValue(boardValue);
        if(value.materialId != currentMaterialId)
        {
            if(size != 0)
            {
                if(first && material.type == GAZ) pressure = size;
                cropPush(currentMaterialId, material.density, size, pressure, material.isStatic == 1, material.type == LIQUID, material.type == GAZ);
                first = false;
            }
            size = 1;
            pressure = value.pressure;
            currentMaterialId = value.materialId;
            material = configuration.materials[currentMaterialId];
        }
        else
        {
            size ++;
            pressure += value.pressure;
        }
    }
    if(material.type == GAZ) pressure = size;
    cropPush(currentMaterialId, material.density, size, pressure, material.isStatic == 1, material.type == LIQUID, material.type == GAZ);
    material = configuration.materials[voidValue.materialId];
    cropPush(voidValue.materialId, material.density, EDGE_SIZE, EDGE_SIZE * DEFAULT_PRESSURE, material.isStatic == 1, material.type == LIQUID, material.type == GAZ);
}

void writeCrops(const uvec2 startLocation, const bool vertical)
{
    uint offset = 0;
    int index = cropQueue.firstIndex;
    for (int i = 0 ; i < EDGE_SIZE ; i++)
    {
        const float firstPressure = vertical ? firstPressure(index) : cropQueue.queue[index].pressure / cropQueue.queue[index].size;
        cropQueue.queue[index].pressure -= firstPressure;
        cropQueue.queue[index].size --;
        if(cropQueue.queue[index].size == 0) index = cropQueue.queue[index].nextCrop;
    }
    while(index > -1)
    {
        writeCrop(index, startLocation.x + (vertical ? 0 : offset), startLocation.y + (vertical ? offset : 0), vertical);
        offset += cropQueue.queue[index].size;
        index = cropQueue.queue[index].nextCrop;
    }
}

void tagMovingCrops()
{
    bool moving = false;
    int downIndex = cropQueue.lastIndex;
    while(cropQueue.queue[downIndex].previousCrop > -1)
    {
        int upIndex = cropQueue.queue[downIndex].previousCrop;
        if(cropQueue.queue[upIndex].isStatic == true)
        {
            moving = false;
        }
        else if (moving == false
        && cropQueue.queue[downIndex].isStatic == false
        && getDensity(upIndex) > getDensity(downIndex))
        {
            moving = true;
        }

        cropQueue.queue[upIndex].moving = moving;
        downIndex = upIndex;
    }
}

Value readValue(const uint value)
{
    if(value == 0) return voidValue;
    const uint materialId = value & 0xFFu;
    const float pressure = unpackHalf2x16(value).y;
    return Value(materialId, pressure);
}

void writeCrop(const uint cropAddress, const uint x, const uint y, const bool vertical)
{
    uint materialId = getMaterialId(cropAddress);
    if(vertical)
    {
        const uint density = getDensity(cropAddress);
        const bool pressurized = cropQueue.queue[cropAddress].isLiquid && cropQueue.queue[cropAddress].moving == false;
        const float pressureDelta = pressurized ? PRESSURE_DELTA * density : 0;
        float pressure = firstPressure(cropAddress);
        for (int i = 0; i < cropQueue.queue[cropAddress].size && y + i < HEIGHT; i++)
        {
            const uint packedValue = materialId | (packHalf2x16(vec2(0., pressure)) & 0xFFFF0000u);
            board2.data[x + (y + i) * WIDTH] = packedValue;
            pressure += pressureDelta;
        }
    }
    else
    {
        const float pressure = cropQueue.queue[cropAddress].pressure / cropQueue.queue[cropAddress].size;
        const uint packedValue = materialId | (packHalf2x16(vec2(0., pressure)) & 0xFFFF0000u);
        for(int i = 0 ; i < cropQueue.queue[cropAddress].size && x + i < WIDTH; i++)
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
    if(cropQueue.queue[cropAddress].isLiquid && cropQueue.queue[cropAddress].moving == false)
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
    if(cropQueue.queue[cropAddress].isLiquid && cropQueue.queue[cropAddress].moving == false)
    {
        const uint density = getDensity(cropAddress);
        return firstPressure(cropAddress) + (PRESSURE_DELTA * density * (size - 1));
    }
    else
    {
        return pressure / size;
    }
}

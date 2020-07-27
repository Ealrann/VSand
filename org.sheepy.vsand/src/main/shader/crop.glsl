#define CROP_QUEUE_SIZE 400

struct Value
{
    uint materialId;
    float pressure;
};
struct Crop
{
    int previousCrop;
    int nextCrop;
    uint data; // materialId (8 bits), density (8 bits), address (16 bits);
    uint size;
    float pressure;
    bool isStatic;
    bool isLiquid;
    bool isGaz;
    bool moving;
};
struct CropQueue
{
    Crop[CROP_QUEUE_SIZE] queue;
    uint nextIndex;
    int firstIndex;
    int lastIndex;
};

CropQueue cropQueue;

void initQueue();
int cropPush(const uint materialId, const uint density, const uint size, const float pressure, const bool isStatic, const bool isLiquid, const bool isGaz);
Crop cropPop();
int insertCropBefore(const uint address, inout Crop newCrop);
void removeCrop(const uint address);

uint getMaterialId(const uint cropAddress);
uint getDensity(const uint cropAddress);

void initQueue()
{
    cropQueue.nextIndex = 0;
    cropQueue.firstIndex = -1;
    cropQueue.lastIndex = -1;
}

int cropPush(const uint materialId, const uint density, const uint size, const float pressure, const bool isStatic, const bool isLiquid, const bool isGaz)
{
    if(cropQueue.lastIndex < CROP_QUEUE_SIZE)
    {
        const int address = int(cropQueue.nextIndex++);

        cropQueue.queue[address].previousCrop = cropQueue.lastIndex;
        cropQueue.queue[address].nextCrop = -1;
        cropQueue.queue[address].data = (materialId << 24) | (density << 16) | uint(address);
        cropQueue.queue[address].size = size;
        cropQueue.queue[address].pressure = pressure;
        cropQueue.queue[address].isStatic = isStatic;
        cropQueue.queue[address].isLiquid = isLiquid;
        cropQueue.queue[address].isGaz = isGaz;
        cropQueue.queue[address].moving = false;

        if (cropQueue.lastIndex != -1) cropQueue.queue[cropQueue.lastIndex].nextCrop = address;
        if (cropQueue.firstIndex == - 1) cropQueue.firstIndex = address;
        cropQueue.lastIndex = address;

        return address;
    }
    else
    {
        return -1;
    }
}

uint getMaterialId(const uint cropAddress)
{
    return cropQueue.queue[cropAddress].data >> 24;
}

uint getDensity(const uint cropAddress)
{
    return (cropQueue.queue[cropAddress].data >> 16 ) & 0xFFu;
}

Crop cropPop()
{
    return cropQueue.queue[--cropQueue.nextIndex];
}

int insertCropBefore(const uint currentAddress, inout Crop newCrop)
{
    if(cropQueue.lastIndex < CROP_QUEUE_SIZE)
    {
        const int previousAddress = cropQueue.queue[currentAddress].previousCrop;
        const uint newAddress = cropQueue.nextIndex++;
        newCrop.data &= 0xFFFF0000u;
        newCrop.data |= newAddress;
        newCrop.nextCrop = int(currentAddress);
        newCrop.previousCrop = previousAddress;
        if (previousAddress > -1) cropQueue.queue[previousAddress].nextCrop = int(newAddress);
        cropQueue.queue[currentAddress].previousCrop = int(newAddress);
        cropQueue.queue[newAddress] = newCrop;
        return int(newAddress);
    }
    else
    {
        return -1;
    }
}

void removeCrop(const uint address)
{
    const int previousAddress = cropQueue.queue[address].previousCrop;
    const int nextAdress = cropQueue.queue[address].nextCrop;
    if(previousAddress > -1) cropQueue.queue[previousAddress].nextCrop = nextAdress;
    if(nextAdress > -1) cropQueue.queue[nextAdress].previousCrop = previousAddress;
}

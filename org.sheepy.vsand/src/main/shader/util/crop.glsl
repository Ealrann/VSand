#define CROP_QUEUE_SIZE 80


//struct Crop
//{
//    int previousCrop;
//    int nextCrop;
//    uint data; // materialId (8 bits), density (8 bits), address (16 bits);
//    uint size;
//    float firstTheoreticalPressure;
//    float pressure;
//    bool isStatic;
//    bool isLiquid;
//    bool isGaz;
//    bool falling;
//};
//struct CropQueue
//{
//    Crop[CROP_QUEUE_SIZE] queue;
//    uint size;
//    int firstIndex;
//    int lastIndex;
//};
//
//CropQueue cropQueue;
//
//void initQueue();
//int cropPush(const uint materialId, const uint density, const uint size, const float pressure, const bool isStatic, const bool falling, const bool isLiquid, const bool isGaz);
//int insertCropBefore(const uint address, in Crop newCrop);
//int insertCropAfter(const uint address, in Crop newCrop);
//void removeCrop(const uint address);
//
//uint getMaterialId(const uint cropAddress);
//int getDensity(const uint cropAddress);
//
//void initQueue()
//{
//    cropQueue.size = 0;
//    cropQueue.firstIndex = -1;
//    cropQueue.lastIndex = -1;
//}
//
//int cropPush(const uint materialId, const int density, const uint size, const float firstTheoreticalPressure, const float pressure, const bool isStatic, const bool falling, const bool isLiquid, const bool isGaz)
//{
//    if(cropQueue.size < CROP_QUEUE_SIZE)
//    {
//        const uint address = cropQueue.size++;
//
//        cropQueue.queue[address].previousCrop = cropQueue.lastIndex;
//        cropQueue.queue[address].nextCrop = -1;
//        cropQueue.queue[address].data = (materialId << 24) | (uint(density + 128) << 16) | address;
//        cropQueue.queue[address].size = size;
//        cropQueue.queue[address].firstTheoreticalPressure = firstTheoreticalPressure;
//        cropQueue.queue[address].pressure = pressure;
//        cropQueue.queue[address].isStatic = isStatic;
//        cropQueue.queue[address].isLiquid = isLiquid;
//        cropQueue.queue[address].isGaz = isGaz;
//        cropQueue.queue[address].falling = falling;
//
//        if (cropQueue.lastIndex != -1) cropQueue.queue[cropQueue.lastIndex].nextCrop = int(address);
//        if (cropQueue.firstIndex == - 1) cropQueue.firstIndex = int(address);
//        cropQueue.lastIndex = int(address);
//
//        return int(address);
//    }
//    else
//    {
//        return -1;
//    }
//}
//
//uint getMaterialId(const uint cropAddress)
//{
//    return cropQueue.queue[cropAddress].data >> 24;
//}
//
//int getDensity(const uint cropAddress)
//{
//    int unpackedDensity = int((cropQueue.queue[cropAddress].data >> 16 ) & 0xFFu);
//    return unpackedDensity - 128;
//}
//
//int insertCropBefore(const uint currentAddress, in Crop newCrop)
//{
//    if(cropQueue.size < CROP_QUEUE_SIZE)
//    {
//        const int previousAddress = cropQueue.queue[currentAddress].previousCrop;
//        const uint newAddress = cropQueue.size++;
//        newCrop.data &= 0xFFFF0000u;
//        newCrop.data |= newAddress;
//        newCrop.nextCrop = int(currentAddress);
//        newCrop.previousCrop = previousAddress;
//        if (previousAddress != -1) cropQueue.queue[previousAddress].nextCrop = int(newAddress);
//        else cropQueue.firstIndex = int(newAddress);
//        cropQueue.queue[currentAddress].previousCrop = int(newAddress);
//        cropQueue.queue[newAddress] = newCrop;
//        return int(newAddress);
//    }
//    else
//    {
//        return -1;
//    }
//}
//
//int insertCropAfter(const uint currentAddress, in Crop newCrop)
//{
//    if(cropQueue.size < CROP_QUEUE_SIZE)
//    {
//        const int nextAddress = cropQueue.queue[currentAddress].nextCrop;
//        const uint newAddress = cropQueue.size++;
//        newCrop.data &= 0xFFFF0000u;
//        newCrop.data |= newAddress;
//        newCrop.nextCrop = nextAddress;
//        newCrop.previousCrop = int(currentAddress);
//        if (nextAddress != -1) cropQueue.queue[nextAddress].previousCrop = int(newAddress);
//        else cropQueue.lastIndex = int(newAddress);
//        cropQueue.queue[currentAddress].nextCrop = int(newAddress);
//        cropQueue.queue[newAddress] = newCrop;
//        return int(newAddress);
//    }
//    else
//    {
//        return -1;
//    }
//}
//
//void removeCrop(const uint address)
//{
//    const int previousAddress = cropQueue.queue[address].previousCrop;
//    const int nextAddress = cropQueue.queue[address].nextCrop;
//    if(previousAddress != -1) cropQueue.queue[previousAddress].nextCrop = nextAddress;
//    else cropQueue.firstIndex = nextAddress;
//    if(nextAddress != -1) cropQueue.queue[nextAddress].previousCrop = previousAddress;
//    else cropQueue.lastIndex = previousAddress;
//}

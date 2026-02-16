#ifndef VSAND_MASS_UTILS_GLSL
#define VSAND_MASS_UTILS_GLSL

const uint M_FULL = 4096u;
// Equilibrium max mass used by stable-state packing (small compressibility).
const uint M_EQ_MAX = 4352u;
// Absolute max mass used as a transport capacity clamp (temporary overfill to speed up pressure propagation).
const uint M_CAP_MAX = 6144u;
const uint M_EPS = 16u;
const uint MIN_FLOW = 1u;
const uint H_HEAD_DIV = 4u;

const uint PACKED_U16_MASK = 0xFFFFu;

uint packedU16Read(const uint word, const uint shift)
{
	return (word >> shift) & PACKED_U16_MASK;
}

uint packedU16Write(const uint word, const uint shift, const uint value)
{
	return (word & ~(PACKED_U16_MASK << shift)) | ((value & PACKED_U16_MASK) << shift);
}

uvec2 massIndexShift(const ivec2 loc)
{
	const uint swizzledX = uint(loc.x) >> 1;
	const uint swizzledY = uint(loc.y) >> 1;

	const uint baseIndex = (swizzledX * uint(SWIZZLED_HEIGHT) + swizzledY) * 2u;
	const uint wordIndex = baseIndex + uint(loc.y & 1);
	const uint wordShift = uint((loc.x & 1) << 4);

	return uvec2(wordIndex, wordShift);
}

#endif

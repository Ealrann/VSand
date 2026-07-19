#ifndef VSAND_MASS_UTILS_GLSL
#define VSAND_MASS_UTILS_GLSL

// Mass of a nominal full liquid cell.
const uint M_FULL = 4096u;
// Hydrostatic mass gradient per cell of depth: at rest, a cell holds
// M_GRADIENT more mass than the cell above it. The mass field is a pressure
// signal, not a conserved volume.
const uint M_GRADIENT = 256u;
// Transport/storage cap. Must be well above M_FULL so the field can represent
// deep hydrostatic heads ((M_CAP_MAX - M_FULL) / M_GRADIENT cells, ~112).
const uint M_CAP_MAX = 32768u;
const uint M_EPS = 16u;
// Smallest mass a liquid cell may be drained to by the relaxation: a fully
// drained cell would be re-seeded as a new cell (mass creation out of
// nothing) and the board has no way to remove the visual cell anyway.
const uint MIN_LIQUID_MASS = M_GRADIENT;

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

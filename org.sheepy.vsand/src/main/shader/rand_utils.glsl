#define IEEE_MANTISSA 0x007FFFFFu // binary32 mantissa bitmask
#define IEEE_ONE      0x3F800000u // 1.0 in IEEE binary32

// http://amindforeverprogramming.blogspot.com/2013/07/random-floats-in-glsl-330.html
// A single iteration of Bob Jenkins' One-At-A-Time hashing algorithm.
// @Deprecated : wellons looks better
//uint leec( uint x ) {
//    x += ( x << 10u );
//    x ^= ( x >>  6u );
//    x += ( x <<  3u );
//    x ^= ( x >> 11u );
//    x += ( x << 15u );
//    return x;
//}

// https://nullprogram.com/blog/2018/07/31/
//bias: 0.17353355999581582 ( very probably the best of its kind )
uint wellons(uint x)
{
    x ^= x >> 16u;
    x *= 0x7feb352dU;
    x ^= x >> 15u;
    x *= 0x846ca68bU;
    x ^= x >> 16u;
    return x;
}

// https://nullprogram.com/blog/2018/07/31/
// If you’re willing to use an additional round of multiply-xorshift,
// this next function actually reaches the theoretical bias limit (bias = ~0.021)
// as exhibited by a perfect integer hash function:
// bias: 0.020888578919738908 = minimal theoretic limit
uint wellons3(uint x)
{
    x ^= x >> 17u;
    x *= 0xed5ad4bbU;
    x ^= x >> 11u;
    x *= 0xac4c1b51U;
    x ^= x >> 15u;
    x *= 0x31848babU;
    x ^= x >> 14u;
    return x;
}

uint hash( uint  x ) { return wellons3(x); }
uint hash( uvec2 v ) { return hash( v.x ^ hash(v.y)                         ); }
uint hash( uvec3 v ) { return hash( v.x ^ hash(v.y) ^ hash(v.z)             ); }
uint hash( uvec4 v ) { return hash( v.x ^ hash(v.y) ^ hash(v.z) ^ hash(v.w) ); }

uint fastHash( uint  x ) { return wellons(x); }
uint fastHash( uvec2 v ) { return fastHash( v.x ^ fastHash(v.y)                                 ); }
uint fastHash( uvec3 v ) { return fastHash( v.x ^ fastHash(v.y) ^ fastHash(v.z)                 ); }
uint fastHash( uvec4 v ) { return fastHash( v.x ^ fastHash(v.y) ^ fastHash(v.z) ^ fastHash(v.w) ); }

// Construct a float with half-open range [0:1] using low 23 bits.
// All zeroes yields 0.0, all ones yields the next smallest representable value below 1.0.
float floatReductionOpt ( uint m )
{
    m &= IEEE_MANTISSA;                     // Keep only mantissa bits (fractional part)
    m |= IEEE_ONE;                          // Add fractional part to 1.0

    return uintBitsToFloat( m ) - 1.0;      // Range [0:1]
}

float floatReduction ( uint m )
{
    return float(m) / float(0xffffffffU);
}

// Pseudo-random value in half-open range [0:1].
float random( float x ) { return floatReductionOpt(hash(floatBitsToUint(x))); }
float random( vec2  v ) { return floatReductionOpt(hash(floatBitsToUint(v))); }
float random( vec3  v ) { return floatReductionOpt(hash(floatBitsToUint(v))); }
float random( vec4  v ) { return floatReductionOpt(hash(floatBitsToUint(v))); }

float random( uint   x ) { return floatReductionOpt(hash(x)); }
float random( uvec2  v ) { return floatReductionOpt(hash(v)); }
float random( uvec3  v ) { return floatReductionOpt(hash(v)); }
float random( uvec4  v ) { return floatReductionOpt(hash(v)); }


float randomFast( float x ) { return floatReductionOpt(fastHash(floatBitsToUint(x))); }
float randomFast( vec2  v ) { return floatReductionOpt(fastHash(floatBitsToUint(v))); }
float randomFast( vec3  v ) { return floatReductionOpt(fastHash(floatBitsToUint(v))); }
float randomFast( vec4  v ) { return floatReductionOpt(fastHash(floatBitsToUint(v))); }

float randomFast( uint   x ) { return floatReductionOpt(fastHash(x)); }
float randomFast( uvec2  v ) { return floatReductionOpt(fastHash(v)); }
float randomFast( uvec3  v ) { return floatReductionOpt(fastHash(v)); }
float randomFast( uvec4  v ) { return floatReductionOpt(fastHash(v)); }

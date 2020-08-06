// http://amindforeverprogramming.blogspot.com/2013/07/random-floats-in-glsl-330.html

#define IEEE_MANTISSA 0x007FFFFFu // binary32 mantissa bitmask
#define IEEE_ONE      0x3F800000u // 1.0 in IEEE binary32

// A single iteration of Bob Jenkins' One-At-A-Time hashing algorithm.
uint hash( in uint x ) {
    x += ( x << 10u );
    x ^= ( x >>  6u );
    x += ( x <<  3u );
    x ^= ( x >> 11u );
    x += ( x << 15u );
    return x;
}

// Compound versions of the hashing algorithm I whipped together.
uint hash( const uvec2 v ) { return hash( v.x ^ hash(v.y)                         ); }
uint hash( const uvec3 v ) { return hash( v.x ^ hash(v.y) ^ hash(v.z)             ); }
uint hash( const uvec4 v ) { return hash( v.x ^ hash(v.y) ^ hash(v.z) ^ hash(v.w) ); }

// Construct a float with half-open range [0:1] using low 23 bits.
// All zeroes yields 0.0, all ones yields the next smallest representable value below 1.0.
float floatConstruct( in uint m ) {

    m &= IEEE_MANTISSA;                     // Keep only mantissa bits (fractional part)
    m |= IEEE_ONE;                          // Add fractional part to 1.0

    const float f = uintBitsToFloat( m );   // Range [1:2]
    return f - 1.0;                         // Range [0:1]
}

// Pseudo-random value in half-open range [0:1].
float random( const float x ) { return floatConstruct(hash(floatBitsToUint(x))); }
float random( const vec2  v ) { return floatConstruct(hash(floatBitsToUint(v))); }
float random( const vec3  v ) { return floatConstruct(hash(floatBitsToUint(v))); }
float random( const vec4  v ) { return floatConstruct(hash(floatBitsToUint(v))); }

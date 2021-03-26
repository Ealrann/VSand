#define SOLID   0
#define LIQUID  1
#define GAZ     2
#define SPECIAL 3

struct Material
{
    int isStatic;
    float density;
    int runoff;
    uint type;
    vec4 color;
};

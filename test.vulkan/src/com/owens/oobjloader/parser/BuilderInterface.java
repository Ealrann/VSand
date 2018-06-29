package com.owens.oobjloader.parser;

public interface BuilderInterface {

    public static final int NO_SMOOTHING_GROUP = 0;
    public static final int EMPTY_VERTEX_VALUE = Integer.MIN_VALUE;
    public static final int MTL_KA = 0;
    public static final int MTL_KD = 1;
    public static final int MTL_KS = 2;
    public static final int MTL_TF = 3;
    public static final int MTL_MAP_KA = 0;
    public static final int MTL_MAP_KD = 1;
    public static final int MTL_MAP_KS = 2;
    public static final int MTL_MAP_NS = 3;
    public static final int MTL_MAP_D = 4;
    public static final int MTL_DECAL = 5;
    public static final int MTL_DISP = 6;
    public static final int MTL_BUMP = 7;
    public static final int MTL_REFL_TYPE_UNKNOWN = -1;
    public static final int MTL_REFL_TYPE_SPHERE = 0;
    public static final int MTL_REFL_TYPE_CUBE_TOP = 1;
    public static final int MTL_REFL_TYPE_CUBE_BOTTOM = 2;
    public static final int MTL_REFL_TYPE_CUBE_FRONT = 3;
    public static final int MTL_REFL_TYPE_CUBE_BACK = 4;
    public static final int MTL_REFL_TYPE_CUBE_LEFT = 5;
    public static final int MTL_REFL_TYPE_CUBE_RIGHT = 6;

    public void setObjFilename(String filename);

    public void addVertexGeometric(float x, float y, float z);

    public void addVertexTexture(float u, float v);

    public void addVertexNormal(float x, float y, float z);

    public void addPoints(int values[]);

    public void addLine(int values[]);

    // The param for addFace is an array of vertex indices.   This array should have a length that is a multiple of 3.  
    //
    // For each triplet of values;
    //
    // The first value is an absolute or relative index to a geometric vertex. (VertexGeometric)
    // The second value is an absolute or relative index to a vertex texture coordinate. (VertexTexture)
    // The third vertex is an absolute or relative index to a vertex normal.  (VertexNormal)
    //
    // The indices for the texture and normal MAY be empty in which case they will be set equal to the special
    // value defined in BuilderInterface, EMPTY_VERTEX_VALUE.
    //
    // Absolute indices are positive values that specify a vertex/texture/normal by it's absolute position within the OBJ file.
    //
    // Relative indices are negative values that specify a vertex/texture/normal by it's position relative to the line the index
    // is on, i.e. a line specifying a face (triangle) may specify an geometry vertex as -5 which means the 5 most recently seen 
    // geometry vertex.
    public void addFace(int vertexIndices[]);

    public void addObjectName(String name);

    public void addMapLib(String[] names);

    public void setCurrentGroupNames(String[] names);

    public void setCurrentSmoothingGroup(int groupNumber);

    public void setCurrentUseMap(String name);

    public void setCurrentUseMaterial(String name);

    public void newMtl(String name);

    public void setXYZ(int type, float x, float y, float z);

    public void setRGB(int type, float r, float g, float b);

    public void setIllum(int illumModel);

    public void setD(boolean halo, float factor);

    public void setNs(float exponent);

    public void setSharpness(float value);

    public void setNi(float opticalDensity);

    public void setMapDecalDispBump(int type, String filename);

    public void setRefl(int type, String filename);

    public void doneParsingMaterial();

    public void doneParsingObj(String filename);
}
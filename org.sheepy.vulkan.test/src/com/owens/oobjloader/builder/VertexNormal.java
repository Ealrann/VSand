package com.owens.oobjloader.builder;

public class VertexNormal
{
	public float x = 0;
	public float y = 0;
	public float z = 0;

	public void add(float x, float y, float z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public VertexNormal(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

    @Override
	public String toString()
	{
		return x + "," + y + "," + z;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		VertexNormal other = (VertexNormal) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z)) return false;
		return true;
	}
	
}
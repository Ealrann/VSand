package com.owens.oobjloader.builder;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class FaceVertex {

    public int index = -1;
    public Vector3f vertexGeometric = null;
    public Vector2f vertexTexture = null;
    public VertexNormal n = null;

    @Override
    public String toString() {
        return vertexGeometric + "|" + n + "|" + vertexTexture;
    }

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((n == null) ? 0 : n.hashCode());
		result = prime * result + ((vertexTexture == null) ? 0 : vertexTexture.hashCode());
		result = prime * result + ((vertexGeometric == null) ? 0 : vertexGeometric.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		FaceVertex other = (FaceVertex) obj;
		if (n == null)
		{
			if (other.n != null) return false;
		}
		else if (!n.equals(other.n)) return false;
		if (vertexTexture == null)
		{
			if (other.vertexTexture != null) return false;
		}
		else if (!vertexTexture.equals(other.vertexTexture)) return false;
		if (vertexGeometric == null)
		{
			if (other.vertexGeometric != null) return false;
		}
		else if (!vertexGeometric.equals(other.vertexGeometric)) return false;
		return true;
	}
}
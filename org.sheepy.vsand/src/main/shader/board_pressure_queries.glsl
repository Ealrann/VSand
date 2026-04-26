bool isHorizontalPressureOutlet(ivec2 loc, ivec2 localLoc, uint liquidValue, int liquidDensity)
{
	if (isOutsideBoard(loc))
	{
		return false;
	}
	if (readMaterial(loc, localLoc) != 0u)
	{
		return false;
	}
	if (isBulkInterior(loc, localLoc, liquidValue))
	{
		return false;
	}

	const ivec2 downLoc = loc + ivec2(0, 1);
	if (isOutsideBoard(downLoc))
	{
		return false;
	}

	const ivec2 downLocal = localLoc + ivec2(0, 1);
	const uint downValue = readMaterial(downLoc, downLocal);
	return downValue == liquidValue
			|| materials[downValue].isStatic == 1
			|| materials[downValue].density >= liquidDensity;
}

bool isVerticalPressureOutlet(ivec2 loc, ivec2 localLoc, uint liquidValue, int liquidDensity)
{
	if (isOutsideBoard(loc))
	{
		return false;
	}
	if (readMaterial(loc, localLoc) != 0u)
	{
		return false;
	}

	const ivec2 belowLoc = loc + ivec2(0, 1);
	if (isOutsideBoard(belowLoc))
	{
		return false;
	}

	const ivec2 belowLocal = localLoc + ivec2(0, 1);
	if (readMaterial(belowLoc, belowLocal) != liquidValue)
	{
		return false;
	}
	if (isBulkInterior(loc, localLoc, liquidValue))
	{
		return false;
	}
	if (hasOpenSurfaceAbove(loc, localLoc, liquidValue) == false)
	{
		return false;
	}

	return isInPressurePipe(loc, localLoc, liquidValue, liquidDensity);
}

bool isBulkInterior(ivec2 loc, ivec2 localLoc, uint liquidValue)
{
	const ivec2 upLoc = loc + ivec2(0, -1);
	const ivec2 downLoc = loc + ivec2(0, 1);
	const ivec2 leftLoc = loc + ivec2(-1, 0);
	const ivec2 rightLoc = loc + ivec2(1, 0);
	if (isOutsideBoard(upLoc) || isOutsideBoard(downLoc) || isOutsideBoard(leftLoc) || isOutsideBoard(rightLoc))
	{
		return false;
	}

	const uint upValue = readMaterial(upLoc, localLoc + ivec2(0, -1));
	const uint downValue = readMaterial(downLoc, localLoc + ivec2(0, 1));
	const uint leftValue = readMaterial(leftLoc, localLoc + ivec2(-1, 0));
	const uint rightValue = readMaterial(rightLoc, localLoc + ivec2(1, 0));
	return upValue == liquidValue
			&& downValue == liquidValue
			&& leftValue == liquidValue
			&& rightValue == liquidValue;
}

bool hasPressureSupportBelow(ivec2 loc, ivec2 localLoc, uint liquidValue, int liquidDensity, bool allowGaps)
{
	if (readMaterial(loc, localLoc) != liquidValue)
	{
		return false;
	}

	for (int offset = 1; offset <= PRESSURE_SETTLED_SUPPORT_LOOKBACK; offset++)
	{
		const ivec2 scanLoc = loc + ivec2(0, offset);
		if (isOutsideBoard(scanLoc))
		{
			return true;
		}

		const ivec2 scanLocal = localLoc + ivec2(0, offset);
		const uint scanValue = readMaterial(scanLoc, scanLocal);
		if (scanValue == liquidValue)
		{
			continue;
		}
		if (isPressureBarrier(scanValue, liquidDensity))
		{
			return true;
		}
		if (allowGaps == false)
		{
			return false;
		}
	}

	return false;
}

bool isSupportedSettledPressureLiquid(ivec2 loc, ivec2 localLoc, uint liquidValue, int liquidDensity)
{
	return hasPressureSupportBelow(loc, localLoc, liquidValue, liquidDensity, false);
}

bool hasReachablePressureSupportBelow(ivec2 loc, ivec2 localLoc, uint liquidValue, int liquidDensity)
{
	return hasPressureSupportBelow(loc, localLoc, liquidValue, liquidDensity, true);
}

bool hasOpenSurfaceAbove(ivec2 loc, ivec2 localLoc, uint liquidValue)
{
	bool sawVoid = false;
	for (int offset = 1; offset <= PRESSURE_SURFACE_LOOKUP; offset++)
	{
		const ivec2 scanLoc = loc + ivec2(0, -offset);
		if (isOutsideBoard(scanLoc))
		{
			return true;
		}

		const uint scanValue = readMaterial(scanLoc, localLoc + ivec2(0, -offset));
		if (scanValue == 0u)
		{
			sawVoid = true;
			continue;
		}
		if (scanValue == liquidValue)
		{
			return false;
		}
		if (materials[scanValue].isStatic == 1 || materials[scanValue].density >= materials[liquidValue].density)
		{
			return false;
		}
		return false;
	}

	return sawVoid;
}

int stackHeightAbove(ivec2 loc, ivec2 localLoc, uint liquidValue)
{
	int height = 0;
	for (int offset = 1; offset <= PRESSURE_STACK_LOOKUP; offset++)
	{
		const ivec2 scanLoc = loc + ivec2(0, -offset);
		if (isOutsideBoard(scanLoc))
		{
			break;
		}

		const uint scanValue = readMaterial(scanLoc, localLoc + ivec2(0, -offset));
		if (scanValue != liquidValue)
		{
			break;
		}
		height++;
	}
	return height;
}

int pressureStackTopY(ivec2 sourceLocal, uint liquidValue)
{
	int topY = sourceLocal.y;
	for (int offset = 1; offset <= PRESSURE_STACK_LOOKUP; offset++)
	{
		const int scanY = sourceLocal.y - offset;
		if (scanY < PRESSURE_LOCAL_SAFE_MIN)
		{
			break;
		}
		if (cellMaterial[sourceLocal.x][scanY] != liquidValue)
		{
			break;
		}
		topY = scanY;
	}
	return topY;
}

bool canDrainPressureStack(ivec2 sourceLocal, uint liquidValue)
{
	const int topY = pressureStackTopY(sourceLocal, liquidValue);
	if (topY == sourceLocal.y)
	{
		return false;
	}
	if (topY <= PRESSURE_LOCAL_SAFE_MIN && cellMaterial[sourceLocal.x][topY - 1] == liquidValue)
	{
		return false;
	}
	return true;
}

bool canLeavePressureSourceHole(ivec2 loc, ivec2 localLoc, int dir, uint liquidValue)
{
	if (canDrainPressureStack(localLoc, liquidValue))
	{
		return true;
	}
	if (localLoc.y <= PRESSURE_LOCAL_SAFE_MIN || localLoc.y >= PRESSURE_LOCAL_SAFE_MAX_EXCLUSIVE)
	{
		return false;
	}

	const ivec2 backLoc = loc - ivec2(dir, 0);
	if (isOutsideBoard(backLoc))
	{
		return true;
	}

	const uint backValue = readMaterial(backLoc, localLoc - ivec2(dir, 0));
	if (backValue != liquidValue)
	{
		return true;
	}
	return false;
}

bool canUseFallbackPressureSource(ivec2 loc, ivec2 localLoc, int dir, uint liquidValue)
{
	return canLeavePressureSourceHole(loc, localLoc, dir, liquidValue)
			|| (localLoc.x >= PRESSURE_FALLBACK_INTERIOR_MIN
					&& localLoc.y >= PRESSURE_FALLBACK_INTERIOR_MIN
					&& localLoc.x < PRESSURE_LOCAL_SAFE_MAX_EXCLUSIVE
					&& localLoc.y < PRESSURE_LOCAL_SAFE_MAX_EXCLUSIVE);
}

bool isOverfullPressureMass(uint mass, uint minOverfull)
{
	return mass > M_FULL + minOverfull;
}

bool hasPressureMassGradient(uint sourceMass, uint targetMass, uint minDelta)
{
	return sourceMass > targetMass + minDelta;
}

bool hasPressureMassSignal(uint sourceMass, uint targetMass, uint minDelta, uint minOverfull)
{
	return hasPressureMassGradient(sourceMass, targetMass, minDelta)
			|| isOverfullPressureMass(sourceMass, minOverfull);
}

bool hasStrongPressureMassSignal(uint sourceMass, uint targetMass, uint minDelta, uint minOverfull)
{
	return hasPressureMassGradient(sourceMass, targetMass, minDelta)
			&& isOverfullPressureMass(sourceMass, minOverfull);
}

bool hasRearMassPressure(uint rearMass, uint currentMass, int offset)
{
	return rearMass > currentMass + uint(offset) * PRESSURE_REAR_MASS_DELTA_STEP;
}

bool hasRearPressure(ivec2 loc, ivec2 localLoc, int dir, uint currentValue, uint currentMass, int density)
{
	for (int offset = 1; offset <= PRESSURE_REAR_LOOKBACK; offset++)
	{
		const ivec2 rearLoc = loc + ivec2(dir * offset, 0);
		if (isOutsideBoard(rearLoc))
		{
			break;
		}

		const ivec2 rearLocal = localLoc + ivec2(dir * offset, 0);
		const bool rearInLocal = rearLocal.x >= 0 && rearLocal.x < WORKGROUP_SIZE;
		const uint rearValue = rearInLocal ? cellMaterial[rearLocal.x][rearLocal.y] : readMaterialGlobal(rearLoc);
		if (rearValue != currentValue)
		{
			if (materials[rearValue].isStatic == 1 || materials[rearValue].density >= density)
			{
				break;
			}
			continue;
		}

		const uint rearMass = rearInLocal ? cellMassSrc[rearLocal.x][rearLocal.y] : readMassGlobal(rearLoc);
		if (hasRearMassPressure(rearMass, currentMass, offset))
		{
			return true;
		}

		const ivec2 rearUpLoc = rearLoc + ivec2(0, -1);
		if (isOutsideBoard(rearUpLoc) == false)
		{
			const ivec2 rearUpLocal = rearLocal + ivec2(0, -1);
			const bool rearUpInLocal = rearUpLocal.x >= 0
					&& rearUpLocal.y >= 0
					&& rearUpLocal.x < WORKGROUP_SIZE
					&& rearUpLocal.y < WORKGROUP_SIZE;
			const uint rearUpValue = rearUpInLocal ? cellMaterial[rearUpLocal.x][rearUpLocal.y] : readMaterialGlobal(rearUpLoc);
			if (rearUpValue == currentValue)
			{
				return true;
			}
		}
	}

	return false;
}

bool hasColumnPressureBelow(ivec2 loc, ivec2 localLoc, uint currentValue, uint currentMass)
{
	for (int offset = 2; offset <= PRESSURE_COLUMN_LOOKBACK; offset++)
	{
		const ivec2 scanLoc = loc + ivec2(0, offset);
		if (isOutsideBoard(scanLoc))
		{
			break;
		}

		const ivec2 scanLocal = localLoc + ivec2(0, offset);
		const bool scanInLocal = scanLocal.y >= 0 && scanLocal.y < WORKGROUP_SIZE;
		const uint scanValue = scanInLocal ? cellMaterial[scanLocal.x][scanLocal.y] : readMaterialGlobal(scanLoc);
		if (scanValue != currentValue)
		{
			break;
		}

		const uint scanMass = scanInLocal ? cellMassSrc[scanLocal.x][scanLocal.y] : readMassGlobal(scanLoc);
		if (hasPressureMassSignal(scanMass, currentMass, PRESSURE_COLUMN_MASS_DELTA, PRESSURE_COLUMN_OVERFULL_MASS))
		{
			return true;
		}
	}

	return false;
}

bool isInPressurePipe(ivec2 loc, ivec2 localLoc, uint currentValue, int density)
{
	return hasPipeWall(loc, localLoc, -1, currentValue, density)
			&& hasPipeWall(loc, localLoc, 1, currentValue, density);
}

bool hasPipeWall(ivec2 loc, ivec2 localLoc, int dir, uint currentValue, int density)
{
	for (int offset = 1; offset <= PRESSURE_PIPE_WALL_LOOKUP; offset++)
	{
		const ivec2 wallLoc = loc + ivec2(dir * offset, 0);
		if (isOutsideBoard(wallLoc))
		{
			return true;
		}

		const ivec2 wallLocal = localLoc + ivec2(dir * offset, 0);
		const bool wallInLocal = isInsideLocal(wallLocal);
		const uint wallValue = wallInLocal ? cellMaterial[wallLocal.x][wallLocal.y] : readMaterialGlobal(wallLoc);
		if (wallValue == currentValue)
		{
			continue;
		}
		if (isPressureBarrier(wallValue, density))
		{
			return hasVerticalPipeWallAt(wallLoc, wallLocal, density);
		}
	}

	return false;
}

bool isPressureBarrier(uint materialId, int density)
{
	return materials[materialId].isStatic == 1 || materials[materialId].density >= density;
}

bool hasVerticalPipeWallAt(ivec2 wallLoc, ivec2 wallLocal, int density)
{
	for (int offset = -PRESSURE_PIPE_WALL_VERTICAL_LOOKUP; offset <= PRESSURE_PIPE_WALL_VERTICAL_LOOKUP; offset++)
	{
		const ivec2 scanLoc = wallLoc + ivec2(0, offset);
		if (isOutsideBoard(scanLoc))
		{
			return true;
		}

		const uint scanValue = readMaterial(scanLoc, wallLocal + ivec2(0, offset));
		if (isPressureBarrier(scanValue, density) == false)
		{
			return false;
		}
	}
	return true;
}

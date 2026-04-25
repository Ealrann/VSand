bool tryPressureSurfaceStep(ivec2 loc, ivec2 localLoc)
{
	if (localLoc.y < PRESSURE_LOCAL_SAFE_MIN || localLoc.y >= PRESSURE_LOCAL_SAFE_MAX_EXCLUSIVE)
	{
		return false;
	}

	const uint targetValue = cellMaterial[localLoc.x][localLoc.y];
	const ivec2 belowLocal = localLoc + ivec2(0, 1);
	const ivec2 belowLoc = loc + ivec2(0, 1);
	const uint liquidValue = cellMaterial[belowLocal.x][belowLocal.y];
	if (isPressureLiquid(liquidValue) == false)
	{
		return false;
	}

	const int liquidDensity = materials[liquidValue].density;
	if (isSettledPressureLiquid(belowLoc, belowLocal, liquidValue, liquidDensity) == false)
	{
		return false;
	}
	if (isVerticalPressureOutlet(loc, localLoc, liquidValue, liquidDensity) == false)
	{
		return false;
	}

	const uint sourceMass = cellMassSrc[belowLocal.x][belowLocal.y];
	const bool massPressure = sourceMass > M_FULL;
	const bool columnPressure = hasColumnPressureBelow(belowLoc, belowLocal, liquidValue, sourceMass);
	const bool lateralPressure = hasRearPressure(belowLoc, belowLocal, -1, liquidValue, sourceMass, liquidDensity)
			|| hasRearPressure(belowLoc, belowLocal, 1, liquidValue, sourceMass, liquidDensity);
	if (massPressure == false && columnPressure == false && lateralPressure == false)
	{
		return false;
	}

	const uint claim = uint(localLoc.y * WORKGROUP_SIZE + localLoc.x + 1);
	if (claimPressureCell(localLoc, true, claim) == false)
	{
		return false;
	}
	if (claimPressureCell(belowLocal, true, claim) == false)
	{
		releasePressureCell(localLoc, true, claim);
		return false;
	}
	if (cellMaterial[localLoc.x][localLoc.y] != targetValue
			|| cellMaterial[belowLocal.x][belowLocal.y] != liquidValue
			|| isSettledPressureLiquid(belowLoc, belowLocal, liquidValue, liquidDensity) == false
			|| isVerticalPressureOutlet(loc, localLoc, liquidValue, liquidDensity) == false)
	{
		releasePressureCell(belowLocal, true, claim);
		releasePressureCell(localLoc, true, claim);
		return false;
	}

	const uint targetMass = cellMass[localLoc.x][localLoc.y];
	const uint liftedMass = cellMass[belowLocal.x][belowLocal.y];
	cellMaterial[localLoc.x][localLoc.y] = liquidValue;
	cellMass[localLoc.x][localLoc.y] = min(liftedMass, M_CAP_MAX);
	cellMaterial[belowLocal.x][belowLocal.y] = targetValue;
	cellMass[belowLocal.x][belowLocal.y] = isPressureLiquid(targetValue) ? min(targetMass, M_CAP_MAX) : 0u;
	return true;
}

bool tryPressureRowShift(ivec2 loc, ivec2 localLoc)
{
	const uint targetValue = cellMaterial[localLoc.x][localLoc.y];
	if (materials[targetValue].isStatic == 1)
	{
		return false;
	}

	const bool leftFirst = random(uvec4(loc, floatBitsToUint(pushConstants.random), 4)) < 0.50;
	if (leftFirst)
	{
		if (tryPressureRowShiftToward(loc, localLoc, 1))
		{
			return true;
		}
		return tryPressureRowShiftToward(loc, localLoc, -1);
	}
	else
	{
		if (tryPressureRowShiftToward(loc, localLoc, -1))
		{
			return true;
		}
		return tryPressureRowShiftToward(loc, localLoc, 1);
	}
}

bool tryPressureRowShiftToward(ivec2 loc, ivec2 localLoc, int dir)
{
	// Conservative row pressure move:
	// - the destination must be a valid outlet;
	// - the source must be same-liquid, supported from below, and pressurized;
	// - the claimed cells cover both the shifted row and any drained vertical stack.
	const ivec2 rearLocal = localLoc - ivec2(dir, 0);
	const ivec2 frontLocal = localLoc + ivec2(dir, 0);
	if (rearLocal.x < PRESSURE_ROW_SCAN_MIN_X || rearLocal.x >= PRESSURE_ROW_SCAN_MAX_EXCLUSIVE_X
			|| frontLocal.x < PRESSURE_ROW_FRONT_MIN_X || frontLocal.x >= PRESSURE_ROW_FRONT_MAX_EXCLUSIVE_X)
	{
		return false;
	}

	const uint targetValue = cellMaterial[localLoc.x][localLoc.y];
	const uint liquidValue = cellMaterial[rearLocal.x][rearLocal.y];
	if (isPressureLiquid(liquidValue) == false || targetValue == liquidValue)
	{
		return false;
	}

	const int liquidDensity = materials[liquidValue].density;
	if (isHorizontalPressureOutlet(loc, localLoc, liquidValue, liquidDensity) == false)
	{
		return false;
	}

	const uint frontValue = cellMaterial[frontLocal.x][frontLocal.y];
	if (frontValue == liquidValue)
	{
		return false;
	}

	int sourceX = localLoc.x;
	int fallbackSourceX = localLoc.x;
	int fallbackOffset = 0;
	int bestSourceScore = -1;
	int bestSourceOffset = 0;
	bool hasPressure = false;
	const uint targetMass = cellMass[localLoc.x][localLoc.y];

	// Prefer a source that can be drained cleanly. The fallback keeps flow moving
	// only when it is near the best source or safely away from horizontal chunk seams.
	for (int offset = 1; offset <= PRESSURE_ROW_SHIFT_MAX; offset++)
	{
		const ivec2 scanLocal = localLoc - ivec2(dir * offset, 0);
		if (scanLocal.x < PRESSURE_ROW_SCAN_MIN_X || scanLocal.x >= PRESSURE_ROW_SCAN_MAX_EXCLUSIVE_X)
		{
			break;
		}

		const uint scanValue = cellMaterial[scanLocal.x][scanLocal.y];
		if (scanValue != liquidValue)
		{
			break;
		}

		const ivec2 scanLoc = loc - ivec2(dir * offset, 0);
		const bool supportedHere = hasPressureSupportBelow(scanLoc, scanLocal, liquidValue, liquidDensity);
		if (supportedHere == false)
		{
			continue;
		}

		fallbackSourceX = scanLocal.x;
		fallbackOffset = offset;
		const uint scanMass = cellMassSrc[scanLocal.x][scanLocal.y];
		const int stackHeight = stackHeightAbove(scanLoc, scanLocal, liquidValue);
		const bool stackedHere = canDrainPressureStack(scanLocal, liquidValue);
		const bool massPressure = scanMass > targetMass + (M_EPS << 4)
				&& scanMass > M_FULL + (M_EPS << 1);
		const bool rearPressure = hasRearPressure(scanLoc, scanLocal, -dir, liquidValue, scanMass, liquidDensity);
		if (stackedHere || massPressure || rearPressure)
		{
			hasPressure = true;
			if (canLeavePressureSourceHole(scanLoc, scanLocal, dir, liquidValue))
			{
				// Stack drainage dominates; distance then favors pulling from deeper in the pressurized row.
				const int sourceScore = (stackedHere ? PRESSURE_SOURCE_SCORE_DRAINABLE_STACK : 0)
						+ offset * PRESSURE_SOURCE_SCORE_DISTANCE_STEP
						+ stackHeight * PRESSURE_SOURCE_SCORE_STACK_HEIGHT_STEP
						+ (massPressure ? PRESSURE_SOURCE_SCORE_MASS_GRADIENT : 0)
						+ (rearPressure ? PRESSURE_SOURCE_SCORE_REAR_PUSH : 0);
				if (sourceScore > bestSourceScore)
				{
					sourceX = scanLocal.x;
					bestSourceScore = sourceScore;
					bestSourceOffset = offset;
				}
			}
		}
	}
	if (hasPressure == false)
	{
		return false;
	}
	if (sourceX == localLoc.x || bestSourceOffset + PRESSURE_FALLBACK_SOURCE_LAG < fallbackOffset)
	{
		const ivec2 fallbackLocal = ivec2(fallbackSourceX, localLoc.y);
		const ivec2 fallbackLoc = ivec2(loc.x + fallbackSourceX - localLoc.x, loc.y);
		if (fallbackSourceX == localLoc.x)
		{
			return false;
		}
		if (canUseFallbackPressureSource(fallbackLoc, fallbackLocal, dir, liquidValue))
		{
			sourceX = fallbackSourceX;
		}
		else if (sourceX == localLoc.x)
		{
			return false;
		}
	}
	if (sourceX == localLoc.x)
	{
		return false;
	}

	const ivec2 supportLoc = loc + ivec2(0, 1);
	const ivec2 supportLocal = localLoc + ivec2(0, 1);
	const uint supportValue = readMaterial(supportLoc, supportLocal);
	const bool lockSupport = isPressureLiquid(supportValue);
	const ivec2 sourceLocal = ivec2(sourceX, localLoc.y);
	const int sourceTopY = canDrainPressureStack(sourceLocal, liquidValue)
			? pressureStackTopY(sourceLocal, liquidValue)
			: sourceLocal.y;
	const uint claim = uint(localLoc.y * WORKGROUP_SIZE + localLoc.x + 1);
	if (lockPressureDrain(localLoc.y, localLoc.x, sourceX, sourceX, sourceTopY, claim) == false)
	{
		return false;
	}
	if (claimPressureCell(supportLocal, lockSupport, claim) == false)
	{
		releasePressureDrain(localLoc.y, min(localLoc.x, sourceX), max(localLoc.x, sourceX), sourceX, sourceTopY, claim);
		return false;
	}
	if (cellMaterial[localLoc.x][localLoc.y] != targetValue
			|| cellMaterial[rearLocal.x][rearLocal.y] != liquidValue
			|| hasPressureSupportBelow(ivec2(loc.x + sourceX - localLoc.x, loc.y), ivec2(sourceX, localLoc.y), liquidValue, liquidDensity) == false
			|| isHorizontalPressureOutlet(loc, localLoc, liquidValue, liquidDensity) == false
			|| (lockSupport && cellMaterial[supportLocal.x][supportLocal.y] != supportValue))
	{
		releasePressureCell(supportLocal, lockSupport, claim);
		releasePressureDrain(localLoc.y, min(localLoc.x, sourceX), max(localLoc.x, sourceX), sourceX, sourceTopY, claim);
		return false;
	}

	for (int x = localLoc.x; x != sourceX; x -= dir)
	{
		cellMaterial[x][localLoc.y] = cellMaterial[x - dir][localLoc.y];
		cellMass[x][localLoc.y] = cellMass[x - dir][localLoc.y];
	}
	drainPressureStackIntoSource(ivec2(sourceX, localLoc.y), sourceTopY, targetValue, targetMass, liquidValue);

	return true;
}

void drainPressureStackIntoSource(ivec2 sourceLocal, int topY, uint targetValue, uint targetMass, uint liquidValue)
{
	if (topY < sourceLocal.y)
	{
		for (int y = sourceLocal.y; y > topY; y--)
		{
			cellMaterial[sourceLocal.x][y] = cellMaterial[sourceLocal.x][y - 1];
			cellMass[sourceLocal.x][y] = cellMass[sourceLocal.x][y - 1];
		}
		cellMaterial[sourceLocal.x][topY] = targetValue;
		cellMass[sourceLocal.x][topY] = isPressureLiquid(targetValue) ? min(targetMass, M_CAP_MAX) : 0u;
	}
	else
	{
		cellMaterial[sourceLocal.x][sourceLocal.y] = targetValue;
		cellMass[sourceLocal.x][sourceLocal.y] = isPressureLiquid(targetValue) ? min(targetMass, M_CAP_MAX) : 0u;
	}
}

bool lockPressureDrain(int y, int fromX, int toX, int stackX, int stackTopY, uint claim)
{
	const int startX = min(fromX, toX);
	const int endX = max(fromX, toX);
	for (int x = startX; x <= endX; x++)
	{
		const uint oldClaim = atomicCompSwap(pressureShiftClaim[x][y], 0u, claim);
		if (oldClaim != 0u && oldClaim != claim)
		{
			releasePressureDrain(y, startX, x - 1, stackX, y, claim);
			return false;
		}
	}

	for (int stackY = stackTopY; stackY <= y; stackY++)
	{
		const uint oldClaim = atomicCompSwap(pressureShiftClaim[stackX][stackY], 0u, claim);
		if (oldClaim != 0u && oldClaim != claim)
		{
			releasePressureDrain(y, startX, endX, stackX, stackTopY, claim);
			return false;
		}
	}

	return true;
}

void releasePressureDrain(int y, int fromX, int toX, int stackX, int stackTopY, uint claim)
{
	if (fromX <= toX)
	{
		for (int x = fromX; x <= toX; x++)
		{
			atomicCompSwap(pressureShiftClaim[x][y], claim, 0u);
		}
	}
	for (int stackY = stackTopY; stackY <= y; stackY++)
	{
		atomicCompSwap(pressureShiftClaim[stackX][stackY], claim, 0u);
	}
}

bool claimPressureCell(ivec2 claimLocal, bool required, uint claim)
{
	if (required == false)
	{
		return true;
	}

	const uint oldClaim = atomicCompSwap(pressureShiftClaim[claimLocal.x][claimLocal.y], 0u, claim);
	return oldClaim == 0u || oldClaim == claim;
}

void releasePressureCell(ivec2 claimLocal, bool required, uint claim)
{
	if (required)
	{
		atomicCompSwap(pressureShiftClaim[claimLocal.x][claimLocal.y], claim, 0u);
	}
}

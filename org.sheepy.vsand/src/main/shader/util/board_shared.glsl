
shared Cell board[WORKGROUP_SIZE][WORKGROUP_SIZE];
const ivec2 BOARD_SIZE = ivec2(WIDTH, HEIGHT);

ivec2 globalOffset = ivec2(pushConstants.globalXOffset, pushConstants.globalYOffset);
ivec2 workgroupLoc = ivec2(gl_WorkGroupID.xy * WORKGROUP_SIZE) + globalOffset;

ivec2 globalVLocation = ivec2(workgroupLoc.x + int(gl_LocalInvocationID.x), workgroupLoc.y);
ivec2 globalStartVLocation = clamp(globalVLocation, ivec2(-1), BOARD_SIZE + 1);
ivec2 localStartVLocation = globalStartVLocation - workgroupLoc;
uint localHeight = min(globalVLocation.y + WORKGROUP_SIZE, HEIGHT + 1) - globalStartVLocation.y;

ivec2 globalHLocation = ivec2(workgroupLoc.x, workgroupLoc.y + int(gl_LocalInvocationID.x));
ivec2 globalStartHLocation = clamp(globalHLocation, ivec2(-1), BOARD_SIZE + 1);
ivec2 localStartHLocation = globalStartHLocation - workgroupLoc;
uint localWidth = min(globalHLocation.x + WORKGROUP_SIZE, WIDTH + 1) - globalStartHLocation.x;

void loadBoard()
{
    if(globalVLocation.x < -1 || globalVLocation.x > WIDTH) return;

    for (int y = 0; y < localHeight; y++)
    {
        if(globalStartVLocation.x == -1
        || globalStartVLocation.x == WIDTH
        || (globalStartVLocation.y + y) == -1
        || (globalStartVLocation.y + y) == HEIGHT)
        {
            board[localStartVLocation.x][localStartVLocation.y + y] = VOID_CELL;
        }
        else
        {
            board[localStartVLocation.x][localStartVLocation.y + y] = unpackCell(board1.data[globalStartVLocation.x + (globalStartVLocation.y + y) * WIDTH]);
        }
    }
}

void saveBoard()
{
    if(globalVLocation.x < -1 || globalVLocation.x > WIDTH) return;

    for (int y = 0; y < localHeight; y++)
    {
        if(globalStartVLocation.x > -1
        && globalStartVLocation.x < WIDTH
        && (globalStartVLocation.y + y) > -1
        && (globalStartVLocation.y + y) < HEIGHT)
        {
            board2.data[globalStartVLocation.x + (globalStartVLocation.y + y) * WIDTH] = packCell(board[localStartVLocation.x][localStartVLocation.y + y]);
        }
    }
}

// =======================================

bool iterationVertical;
int iterationOffset;
int iterationLength;

void iteration_init(const bool vertical)
{
    iterationVertical = vertical;
    iterationOffset = -1;
    iterationLength = int(vertical ? localHeight : localWidth);
}

bool iteration_hasNext()
{
    return iterationOffset < iterationLength - 1;
}

ivec2 iteration_next()
{
    iterationOffset++;
    return iterationVertical ? ivec2(localStartVLocation.x, localStartVLocation.y + iterationOffset) : ivec2(localStartHLocation.x + iterationOffset, localStartHLocation.y);
}

// =======================================

ivec2 course_location;
ivec2 course_previousLocation;

bool course_isInsideBoard(const bool vertical)
{
    if(vertical) return globalVLocation.x >= 0 && globalVLocation.x < WIDTH;
    else return globalHLocation.y >= 0 && globalHLocation.y < HEIGHT;
}

bool course_init(const bool vertical)
{
    if(course_isInsideBoard(vertical) == false)
    {
        return false;
    }
    else
    {
        iteration_init(vertical);
        course_location = iteration_next();
        return true;
    }
}

bool course_next()
{
    if(iteration_hasNext())
    {
        course_previousLocation = course_location;
        course_location = iteration_next();
        return true;
    }
    else
    {
        return false;
    }
}

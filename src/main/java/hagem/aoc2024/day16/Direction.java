package hagem.aoc2024.day16;

public enum Direction {
    NORTH(0, -1),
    SOUTH(0, 1),
    WEST(-1, 0),
    EAST(1, 0);

    final int dx;
    final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Direction getClockwise() {

        switch (this) {
            case NORTH -> {
                return EAST;
            }
            case EAST -> {
                return SOUTH;
            }
            case SOUTH -> {
                return WEST;
            }
            case WEST -> {
                return NORTH;
            }
        }

        return NORTH;
    }

    public Direction getCounterClockwise() {

        switch (this) {
            case NORTH -> {
                return WEST;
            }
            case EAST -> {
                return NORTH;
            }
            case SOUTH -> {
                return EAST;
            }
            case WEST -> {
                return SOUTH;
            }
        }

        return NORTH;
    }

}

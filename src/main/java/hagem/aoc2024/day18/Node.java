package hagem.aoc2024.day18;

import hagem.aoc2024.day16.Direction;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {

    final int x;
    final int y;
    private long score;

    boolean notCorrupted;

    Node north;
    Node south;
    Node east;
    Node west;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;

        this.score = Long.MAX_VALUE;

        this.notCorrupted = true;

        this.north = null;
        this.south = null;
        this.east = null;
        this.west = null;
    }

    public void setNodeDirection(Direction dir, Node node) {

        switch(dir) {
            case NORTH:
                north = node;
                break;
            case SOUTH:
                south = node;
                break;
            case EAST:
                east = node;
                break;
            case WEST:
                west = node;
                break;
        }

    }

    public boolean updateScore(long score) {

       if(score < this.score) {
           this.score = score;
           return true;
       }

        return false;
    }

    public void resetScore() {
        this.score=Long.MAX_VALUE;
    }

    public long getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node node)) return false;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

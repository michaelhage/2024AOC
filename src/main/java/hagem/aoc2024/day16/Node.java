package hagem.aoc2024.day16;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {

    final int x;
    final int y;
    private long score;

    Direction dir = null;

    Node north;
    Node south;
    Node east;
    Node west;

    Set<Node> prevNodes;
    Set<Node> prevNodesOrig;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;

        this.score = Long.MAX_VALUE;

        this.north = null;
        this.south = null;
        this.east = null;
        this.west = null;

        prevNodes = new HashSet<>();
        prevNodesOrig = new HashSet<>();
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

    public Node getNode(Direction dir) {

        switch(dir) {
            case NORTH:
                return north;
            case SOUTH:
                return south;
            case EAST:
                return east;
            case WEST:
                return west;
        }

        return null;
    }

    public boolean updateScore(long score, Node node) {

        if(score == Long.MAX_VALUE) {
            this.score = score;
            prevNodes = new HashSet<>();
            return false;
        }

        if(this.score > score) {
            this.score = score;

            if(node == null) return true;

            prevNodes = new HashSet<>();
            prevNodes.add(node);

            return true;
        } else if(this.score == score) {

            if(node == null) return false;

            prevNodes.add(node);

        }

        return false;
    }

    public long getScore() {
        return score;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
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

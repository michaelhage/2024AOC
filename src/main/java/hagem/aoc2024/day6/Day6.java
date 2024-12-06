package hagem.aoc2024.day6;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Day6 {

    enum Direction {

        UP(0, -1),
        DOWN(0,1),
        LEFT(-1,0),
        RIGHT(1,0);

        final int dx;
        final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;

        }

        public Direction rotate() {

            return switch (this) {
                case UP -> RIGHT;
                case RIGHT -> DOWN;
                case DOWN -> LEFT;
                case LEFT -> UP;
            };

        }

    }


    File file = new File(Objects.requireNonNull(getClass().getResource("/Day6.data.txt")).getFile());
    private final char MARK = 'X';
    private final char WALL = '#';

    private List<char[]> getData(File file) {

        List< char[] > ret = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file)) ) {
            String line;
            while( (line = reader.readLine()) != null ) {

                ret.add( line.toCharArray() );
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public String run() {

        List<char[]> map = getData(file);
        List<char[]> mapCopy = createCopy(map);

        Point startingPosition = getStartingPosition(mapCopy);
        Point startingPositionCopy = new Point(
                                                startingPosition.x,
                                                startingPosition.y
                                                );

        Set<Point> pointList = navigate(mapCopy, startingPositionCopy);

        int countP1 = getCount(mapCopy);
        int countP2 = 0;

        for( Point newWall: pointList ) {

            startingPositionCopy = new Point(
                    startingPosition.x,
                    startingPosition.y
            );

            mapCopy = createCopy(map);

            if(startingPositionCopy.x == 18 && startingPositionCopy.y == 25) {
                System.out.println("Stop Here");
            }

            countP2 += checkForLoop(mapCopy, startingPositionCopy, newWall);

//            System.out.println("Finished Wall: " + newWall);

        }

        return "Part 1: " + countP1 + ", Part 2: " + countP2;
    }

    private Point getStartingPosition(List<char[]> map) {

        for(int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).length; x++) {

                if(map.get(y)[x] == '^') {
                    return new Point(x, y);
                }

            }
        }

        return new Point(0, 0);
    }

    private Set<Point> navigate(List<char[]> map, Point point) {

        Set<Point> pathList = new HashSet<>();

        Direction direction = Direction.UP;

        map.get( point.y )[ point.x ] = MARK;

//        printMap(map);

        while( notOutOfBounds(map, point, direction) ) {

            if(checkWall(map, point, direction)) {
                direction = direction.rotate();
            }

            point.x += direction.dx;
            point.y += direction.dy;

            map.get( point.y )[ point.x ] = MARK;
            pathList.add( new Point(point.x, point.y) );
//            printMap(map);

        }

        return pathList;

    }

    private int checkForLoop(List<char[]> map, Point point, Point newWall) {

        if( point.equals(newWall) ) {
            return 0;
        }

        Direction direction = Direction.UP;

        map.get( point.y )[ point.x ] = MARK;
        map.get( newWall.y )[newWall.x ] = WALL;

        int numOfNewSquares = 0;
        int touchedWall = 0;
        int moveNum = 0;
        int nonNewWallCount = 0;

        HashSet<Point> wallSet = new HashSet<>();

        while( notOutOfBounds(map, point, direction) ) {

//            if(checkNewWall(point, direction, newWall)) {
//
//                if(numOfNewSquares == 0 && touchedWall > 0 ){
//                    return 1;
//                }
//
//                touchedWall++;
////                numOfNewSquares = 0;
//
//            }

            if(checkWall(map, point, direction)) {

                Point wall = new Point(point.x + direction.dx, point.y + direction.dy);

                if(!wallSet.contains(wall)) {
                    wallSet.add(wall);
                    nonNewWallCount = 0;

                } else if(nonNewWallCount++ > 10) {
                    return 1;
                }

                direction = direction.rotate();
                continue;
            }

//            if(moveNum > 100000) {
//                return 1;
//            }

            point.x += direction.dx;
            point.y += direction.dy;
//            moveNum++;

            if( map.get( point.y )[ point.x ] != MARK ) {
                map.get(point.y)[point.x] = MARK;
//                numOfNewSquares++;
            }

        }

        return 0;
    }


    private boolean checkWall(List<char[]> map, Point point, Direction direction) {

        int x = point.x + direction.dx;
        int y = point.y + direction.dy;

        return map.get(y)[x] == WALL;

    }

    private boolean checkNewWall(Point point, Direction direction, Point newWall) {

        int x = point.x + direction.dx;
        int y = point.y + direction.dy;

        return x == newWall.x && y == newWall.y;

    }

    private boolean notOutOfBounds(List<char[]> map, Point point, Direction direction) {

        int x = point.x + direction.dx;
        int y = point.y + direction.dy;

        if(y < 0 || y >= map.size()){
            return false;
        }
        if(x < 0 || x >= map.getFirst().length){
            return false;
        }

        return true;
    }

    private int getCount(List<char[]> map) {

        int count = 0;

        for (char[] chars : map) {

            for(char c: chars) {
                if(c == MARK) {
                    count++;
                }
            }

        }
        return count;
    }

    private void printMap(List<char[]> map) {

        for (char[] chars : map) {
            System.out.println(Arrays.toString(chars));
        }

        System.out.println();
    }

    private List<char[]> createCopy(List<char[]> map) {

        List<char[]> ret = new ArrayList<>();

        for (char[] chars : map) {

            ret.add(chars.clone());

        }

        return ret;

    }

}

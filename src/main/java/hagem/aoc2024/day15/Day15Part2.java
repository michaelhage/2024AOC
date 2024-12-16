package hagem.aoc2024.day15;

import hagem.utils.Reader;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day15Part2 {

    class Map {

        List<char[]> warehouse;
        List<Character> moveList;

        int posX;
        int posY;

        public Map(List<char[]> map, List<Character> moveList, int posX, int posY) {
            this.warehouse = map;
            this.moveList = moveList;
            this.posX = posX;
            this.posY = posY;
        }
    }

    final char BOX = 'O';
    final char EMPTY = '.';
    final char WALL = '#';
    final char ROBOT = '@';

    final char LEFTBOX = '[';
    final char RIGHTBOX = ']';

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day15.data.txt")).getFile());

    private Map parseData(List<String> data) {

        List<char[]> map = new ArrayList<>();
        List<Character> moveList = new ArrayList<>();

//        int y = -1;
//        int posX = 0;
//        int posY = 0;

        for(String line: data) {

            if(line.isEmpty()){
                continue;
            }

            if( line.startsWith("#") ) {

                line = line.replaceAll("" + WALL, "" + WALL + WALL);
                line = line.replaceAll("\\" + EMPTY, "" + EMPTY + EMPTY);
                line = line.replaceAll("" + BOX, "" + LEFTBOX + RIGHTBOX);
                line = line.replaceAll("" + ROBOT, "" + ROBOT + EMPTY);

                map.add(line.toCharArray());

                continue;
            }

            for(char c: line.toCharArray()) {
                moveList.add(c);
            }

        }

        Point point = getInitPosition(map);

        return new Map(map, moveList, point.x, point.y);
    }

    private Point getInitPosition(List<char[]> map) {

        for(int y = 0; y < map.size(); y++) {
            for(int x = 0; x < map.get(y).length; x++) {

                if(map.get(y)[x] == ROBOT) {

                    return new Point(x,y);

                }
            }
        }

        return new Point(-1,-1);
    }

    public String run(){

        List<String> data = Reader.readFile(file);

        Map map = parseData(data);

        for(char c: map.moveList) {

            int dx = 0, dy = 0;
            switch(c) {
                case '^':
                    dy = -1;
                    break;
                case 'v':
                    dy = 1;
                    break;
                case '<':
                    dx = -1;
                    break;
                case '>':
                    dx = 1;
                    break;

            }

            if(checkIfMove(map, map.posX, map.posY, dx, dy)) {
                move( map, map.posX, map.posY, dx, dy, false);
            }

        }

        long answerP1 = evaluate(map);


        return "Part 2: " + answerP1;
    }

    private boolean checkIfMove(Map map, int x, int y, int dx, int dy) {

//        char cAdv = map.warehouse.get(y + dy)[x + dx];
        char cCurr = map.warehouse.get(y)[x];

        if(cCurr == EMPTY) {

            return true;

        } else if(cCurr == WALL ) {

            return false;

        } else if( cCurr == ROBOT ) {

            return checkIfMove(map, x+dx, y+dy, dx, dy);

        }

//        if LEFT or RIGHT
        if(dx != 0) {

            return checkIfMove(map, x+dx, y+dy, dx, dy);

        }

        boolean checkFlag = false;
//        else, UP or DOWN

//        if LEFT BOX
        if( cCurr == LEFTBOX ) {

//            check for current box
            checkFlag = checkIfMove(map, x+dx, y+dy, dx, dy);

            if(!checkFlag) {
                return false;
            }

//            check for RIGHTBOX
            checkFlag = checkIfMove(map, x+1, y+dy, dx, dy);

//            else RIGHT BOX
        } else {

//            check for current box
            checkFlag = checkIfMove(map, x+dx, y+dy, dx, dy);

            if(!checkFlag) {
                return false;
            }

//            check for LEFTBOX
            checkFlag = checkIfMove(map, x-1, y+dy, dx, dy);

        }

        return checkFlag;

    }

    /***
     *
      * @param map Map Object holding the warehouse map, and the position of the robot
     * @param x Current x coordinate
     * @param y Current y coordinate
     * @param dx Delta for x coordinate
     * @param dy Delta for y coordinate
     * @param boxFlag Used as a flag to show when you are evaluating the second part of a box
     */
    private void move(Map map, int x, int y, int dx, int dy, boolean boxFlag) {

        char cAdv = map.warehouse.get(y + dy)[x + dx];
        char cCurr = map.warehouse.get(y)[x];

        if( cAdv == WALL ) {
            return;
        }

        if(cCurr == ROBOT) {

            if (map.warehouse.get(y + dy)[x + dx] != EMPTY) {
                move(map, x + dx, y + dy, dx, dy, false);
            }

            if( map.warehouse.get(y + dy)[x + dx] == EMPTY ) {

                swap(map.warehouse, x, y, dx, dy);

                map.posX += dx;
                map.posY += dy;


            }
            return;
        }


        if(dx != 0) {

            if( cAdv == LEFTBOX || cAdv == RIGHTBOX ) {
                move( map, x + dx, y + dy, dx, dy, false );
            }

            if( map.warehouse.get(y + dy)[x + dx] == EMPTY ) {

                swap(map.warehouse, x, y, dx, dy);

//                if(cCurr == ROBOT) {
//                    map.posX += dx;
//                    map.posY += dy;
//                }

            }
            return;

        }

        if( cCurr == LEFTBOX ) {

//            move LEFT BOX
            move( map, x + dx, y + dy, dx, dy, false);

//            move RIGHT BOX
            if(!boxFlag)
//                value is set to true to prevent infinite loop
                move( map, x + 1, y , dx, dy, true);

        } else if(cCurr == RIGHTBOX) {

            move( map, x + dx, y + dy, dx, dy, false);

//            move LEFT BOX
            if(!boxFlag)
//                value is set to true to prevent infinite loop
                move( map, x - 1, y , dx, dy, true);

        }

        if( map.warehouse.get(y + dy)[x + dx] == EMPTY ) {

            swap(map.warehouse, x, y, dx, dy);

//            if(cCurr == ROBOT) {
//                map.posX += dx;
//                map.posY += dy;
//            }

        }

    }

    /***
     *
     * @param warehouse Warehouse map
     * @param x x coordinate of one of the objects to swap
     * @param y Y coordinate of one of the objects to swap
     * @param dx X Delta from the coordinate given from variables x and y
     * @param dy Y Delta from the coordinate given from variables x and y
     */
    private void swap(List<char[]> warehouse, int x, int y, int dx, int dy) {

        char c = warehouse.get(y)[x];

        warehouse.get(y)[x] = warehouse.get(y + dy)[x + dx];
        warehouse.get(y + dy)[x + dx] = c;


    }

    private long evaluate(Map map) {

        List<char[]> warehouse = map.warehouse;

        long ret = 0;

        for(int y = 0; y < warehouse.size(); y++) {

            for(int x = 0; x < warehouse.get(y).length; x++) {

                if(warehouse.get(y)[x] == LEFTBOX ) {

                    ret += (long) y * 100 + x;

                }

            }

        }

        return ret;

    }


}

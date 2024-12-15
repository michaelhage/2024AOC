package hagem.aoc2024.day15;

import hagem.utils.Reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day15Part1 {

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
    final char START = '@';

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day15.data.txt")).getFile());

    private Map parseData(List<String> data) {

        List<char[]> map = new ArrayList<>();
        List<Character> moveList = new ArrayList<>();

        int y = -1;
        int posX = 0;
        int posY = 0;

        for(String line: data) {

            y++;

            if(line.isEmpty()){
                continue;
            }

            if( line.startsWith("#") ) {

                if(line.contains("@")) {

                    posX = line.indexOf("@");
                    posY = y;

//                    line = line.replace(START, EMPTY);
                }

                map.add(line.toCharArray());

                continue;
            }

            for(char c: line.toCharArray()) {
                moveList.add(c);
            }

        }

        return new Map(map, moveList, posX, posY);
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

            move( map, map.posX, map.posY, dx, dy );

        }

        long answerP1 = evaluate(map);


        return "Part 1: " + answerP1;
    }

    private void move(Map map, int x, int y, int dx, int dy) {

        char cAdv = map.warehouse.get(y + dy)[x + dx];

        if( cAdv == WALL ) {
            return;
        }

        if( cAdv == BOX ) {
            move( map, x + dx, y + dy, dx, dy );
        }

        if( map.warehouse.get(y + dy)[x + dx] == EMPTY ) {

            char c = map.warehouse.get(y)[x];

            map.warehouse.get(y + dy)[x + dx] = c;
            map.warehouse.get(y)[x] = EMPTY;

            if(c == START) {
                map.posX += dx;
                map.posY += dy;
            }

        }

    }

    private long evaluate(Map map) {

        List<char[]> warehouse = map.warehouse;

        long ret = 0;

        for(int y = 0; y < warehouse.size(); y++) {

            for(int x = 0; x < warehouse.get(y).length; x++) {

                if(warehouse.get(y)[x] == BOX ) {

                    ret += (long) y * 100 + x;

                }

            }

        }

        return ret;

    }


}

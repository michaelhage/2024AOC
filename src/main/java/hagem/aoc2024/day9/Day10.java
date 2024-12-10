package hagem.aoc2024.day9;

import hagem.utils.Reader;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class Day10 {

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day10.data.txt")).getFile());

    private List<int[]> parseData(List<String> data) {

        List<int[]> retList = new ArrayList<>();

        for(String line: data) {

            int[] arr = new int[line.length()];

            for(int i = 0; i < line.length(); i++) {

                arr[i] = line.charAt(i) - '0';

            }

            retList.add(arr);

        }

        return retList;

    }

    public String run() {

        List<String> data = Reader.readFile(file);
        List<int[]> map = parseData(data);

        long answerP1 = lookForBaseTrailP1(map);
        long answerP2 = lookForBaseTrailP2(map);

        return "Part 1: " + answerP1 + ", Part 2: " + answerP2;
    }

    private long lookForBaseTrailP1(List<int[]> map) {

        long count = 0;

        for(int i = 0; i < map.size(); i++) {
            for(int j = 0; j < map.get(i).length; j++) {

                if(map.get(i)[j] == 0) {
                    count += evaluateTrail(map, i, j, 0, new HashSet<Point>(), true);
                }

            }
        }

        return count;
    }

    private long lookForBaseTrailP2(List<int[]> map) {

        long count = 0;

        for(int i = 0; i < map.size(); i++) {
            for(int j = 0; j < map.get(i).length; j++) {

                if(map.get(i)[j] == 0) {
                    count += evaluateTrail(map, i, j, 0, new HashSet<Point>(), false);
                }

            }
        }

        return count;
    }

    private long evaluateTrail(List<int[]> map, int i, int j, long count, HashSet<Point> visitedPoint, boolean unique) {

        if(unique) {
            Point point = new Point(i, j);

            if (visitedPoint.contains(point)) {
                return count;
            }

            visitedPoint.add(point);
        }

        int curr = map.get(i)[j];

        if(curr == 9) {

            return count + 1;
        }

//        long count = 0;

//        check up
        if( i - 1 >= 0 && map.get(i - 1)[j] == curr + 1 ) {
            count = evaluateTrail(map, i - 1, j, count, visitedPoint, unique);
        }
//        check down
        if( i + 1 < map.size() && map.get(i + 1)[j] == curr + 1 ) {
            count = evaluateTrail(map, i + 1, j, count, visitedPoint, unique);
        }
//        check left
        if( j - 1 >= 0 && map.get(i)[j - 1] == curr + 1 ) {
            count = evaluateTrail(map, i, j - 1, count, visitedPoint, unique);
        }
//        check right
        if( j + 1 < map.get(i).length && map.get(i)[j + 1] == curr + 1 ) {
            count = evaluateTrail(map, i, j + 1, count, visitedPoint, unique);
        }

        return count;
    }

}

package hagem.aoc2024.day12;

import hagem.utils.Reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day12Part2 {

    class Garden {

        final long area;
        final long cornerCount;

        public Garden(long area, long cornerCount) {
            this.area = area;
            this.cornerCount = cornerCount;
        }
    }

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day12.data.txt")).getFile());

    char FOUNDCHAR = '?';

//    private HashMap<Character, Long> permiterMap = new HashMap<>();
//    private HashMap<Character, Long> areaMap = new HashMap<>();

    private long cornerCount = 0L;
    private long area = 0L;

    public String runTest(File file) {

        List<String> readList = Reader.readFile(file);
        List<char[]> gardenList = readList.stream().map(String::toCharArray).toList();
        List<char[]> visitedList = readList.stream().map(String::toCharArray).toList();

        List<Garden> gardens = evaluate(gardenList, visitedList);

        long answerP2 = calculateAnswerP2(gardens);

        return "" + answerP2;
    }

    public String run() {

        List<String> readList = Reader.readFile(file);
        List<char[]> gardenList = readList.stream().map(String::toCharArray).toList();
        List<char[]> visitedList = readList.stream().map(String::toCharArray).toList();

        List<Garden> gardens = evaluate(gardenList, visitedList);

        long answerP2 = calculateAnswerP2(gardens);

        return "Part 1: " + answerP2;
    }

    private long calculateAnswerP2(List<Garden> gardens) {

        long answerP2 = 0L;

        for(Garden garden: gardens) {

            answerP2 += garden.area * garden.cornerCount;
        }

        return answerP2;
    }

    private List<Garden> evaluate(List<char[]> gardenList, List<char[]> visitedList) {

        List<Garden> gardens = new ArrayList<>();

        for(int y = 0; y < gardenList.size(); y++) {
            for(int x = 0; x < gardenList.get(y).length; x++) {

                if(visitedList.get(y)[x] != FOUNDCHAR) {

                    area = 0L;
                    cornerCount = 0L;

                    traverse(gardenList, visitedList, x, y);

                    gardens.add(new Garden(area, cornerCount));

                }

            }
        }

        return gardens;

    }

    private void traverse(List<char[]> gardenList, List<char[]> visitedList, int x, int y) {

//        visitedSet.add(new Point(x, y));
        visitedList.get(y)[x] = FOUNDCHAR;

        area += 1;
//        areaMap.merge(gardenList.get(y)[x], 1L, Long::sum);

        long horizontalCount = 0;
        long verticalCount = 0;
//        UP
        verticalCount += checkDirection(gardenList, visitedList, x, y, 0, -1);
//        DOWN
        verticalCount += checkDirection(gardenList, visitedList, x, y, 0, 1);
//        LEFT
        horizontalCount += checkDirection(gardenList, visitedList, x, y, -1, 0);
//        RIGHT
        horizontalCount += checkDirection(gardenList, visitedList, x, y, 1, 0);

//        permiterMap.merge(gardenList.get(y)[x], count, Long::sum);

        evaluateCorner(verticalCount, horizontalCount);

//        gardenList.get(y)[x] = FOUNDCHAR;

    }


    private void evaluateCorner(long verticalCount, long horizontalCount) {

        if(horizontalCount >= 2 && verticalCount >= 2){

            cornerCount += 4;

        } else if(horizontalCount >= 1 && verticalCount >= 1) {

//            cornerCount += horizontalCount + verticalCount;

            cornerCount += 2;
//            cornerCount++;
//            if(horizontalCount >= 2) {
//                cornerCount++;
//            }
//            if(verticalCount >= 2) {
//                cornerCount++;
//            }

        }

    }

    private long checkDirection(List<char[]> gardenList, List<char[]> visitedList, int x, int y, int dx, int dy) {

        if(x + dx < 0 || x + dx >= gardenList.getFirst().length) {
            return 1L;
        }

        if(y + dy < 0 || y + dy >= gardenList.size()) {
            return 1L;
        }

        if(gardenList.get(y)[x] != gardenList.get(y+dy)[x+dx]) {
            return 1L;
        }

        if(gardenList.get(y)[x] == gardenList.get(y+dy)[x+dx] &&
            visitedList.get(y+dy)[x+dx] != FOUNDCHAR) {
            traverse(gardenList, visitedList,x+dx, y+dy);
        }

        return 0L;
    }

}

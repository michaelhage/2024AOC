package hagem.aoc2024.day12;

import hagem.utils.Reader;

import java.io.File;
import java.util.*;
import java.util.List;

public class Day12Part1 {

    class Garden {

        final long area;
        final long perimeter;

        public Garden(long area, long perimeter) {
            this.area = area;
            this.perimeter = perimeter;
        }
    }

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day12.data.txt")).getFile());

    char FOUNDCHAR = '?';

//    private HashMap<Character, Long> permiterMap = new HashMap<>();
//    private HashMap<Character, Long> areaMap = new HashMap<>();

    private long perimeter = 0L;
    private long area = 0L;

    public String runTest(File file) {

        List<String> readList = Reader.readFile(file);
        List<char[]> gardenList = readList.stream().map(String::toCharArray).toList();
        List<char[]> visitedList = readList.stream().map(String::toCharArray).toList();

        List<Garden> gardens = evaluate(gardenList, visitedList);

        long answerP1 = calculateAnswerP1(gardens);

        return "" + answerP1;
    }

    public String run() {

        List<String> readList = Reader.readFile(file);
        List<char[]> gardenList = readList.stream().map(String::toCharArray).toList();
        List<char[]> visitedList = readList.stream().map(String::toCharArray).toList();

        List<Garden> gardens = evaluate(gardenList, visitedList);

        long answerP1 = calculateAnswerP1(gardens);

        return "Part 1: " + answerP1;
    }

    private long calculateAnswerP1(List<Garden> gardens) {

        long answerP1 = 0L;

        for(Garden garden: gardens) {

            answerP1 += garden.area * garden.perimeter;
        }

        return answerP1;
    }

    private List<Garden> evaluate(List<char[]> gardenList, List<char[]> visitedList) {

        List<Garden> gardens = new ArrayList<>();

        for(int y = 0; y < gardenList.size(); y++) {
            for(int x = 0; x < gardenList.get(y).length; x++) {

                if(visitedList.get(y)[x] != FOUNDCHAR) {

                    area = 0L;
                    perimeter = 0L;

                    traverse(gardenList, visitedList, x, y);

                    gardens.add(new Garden(area, perimeter));

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

        long count = 0;
//        UP
        count += checkDirection(gardenList, visitedList, x, y, 0, -1);
//        DOWN
        count += checkDirection(gardenList, visitedList, x, y, 0, 1);
//        LEFT
        count += checkDirection(gardenList, visitedList, x, y, -1, 0);
//        RIGHT
        count += checkDirection(gardenList, visitedList, x, y, 1, 0);

//        permiterMap.merge(gardenList.get(y)[x], count, Long::sum);
        perimeter += count;

//        gardenList.get(y)[x] = FOUNDCHAR;

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

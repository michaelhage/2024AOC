package hagem.aoc2024.day19;

import hagem.utils.Reader;

import java.io.File;
import java.util.*;

public class Day19 {

    static class Style {

        int maxStyleLength;
        Set<String> styleSet;
        Map<String, Long> solutionMap;

        public Style(String str) {

            this.styleSet = new HashSet<>();
            this.solutionMap = new HashMap<>();

            for(String s: str.split(", ")) {

                if(s.length() > maxStyleLength) {
                    this.maxStyleLength = s.length();
                }

                this.styleSet.add(s);
            }

        }
    }

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day19.data.txt")).getFile());

    int recursiveCount = 0;

    public String run() {

        List<String> data = Reader.readFile(file);

        long answerP1 = findValidTowels(data, true);
        long answerP2 = findValidTowels(data, false);

        return "Part 1: " + answerP1 + ", Part 2: " + answerP2;
    }

    private long findValidTowels(List<String> data, boolean isPart1) {

        long b;
        long countP1 = 0;
        long countP2 = 0;


        Style style = new Style( data.get(0) );

        for(int i = 2; i < data.size(); i++) {

            recursiveCount = 0;
            b = evaluateTowels(data.get(i), style);

//            System.out.println("Finished: " + data.get(i) + ": " + b);

            if(b > 0) countP1++;
            countP2 += b;
        }

        if(isPart1) return countP1;
        return countP2;
    }

    public long evaluateTowels(String s, Style style) {

        if(s.isEmpty()) {
            return 1;
        }

        if(style.solutionMap.containsKey(s)) {
            return style.solutionMap.get(s);
        }

        long count = 0;

//        for(int x = 1; x <= Math.min(style.maxStyleLength, s.length()); x++) {
        for(int x = Math.min(style.maxStyleLength, s.length()); x > 0; x--) {

            String sub = s.substring(0, x);

            if (style.styleSet.contains(sub)) {

                count += evaluateTowels(s.substring(x), style);

            }
        }

        style.solutionMap.put(s, count);
        return count;

    }

}

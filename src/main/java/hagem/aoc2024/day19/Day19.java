package hagem.aoc2024.day19;

import hagem.utils.Reader;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day19 {

    static class Style {

        int maxStyleLength;
        Set<String> styleSet;
        Set<String> solutionSet;

        public Style(String str) {

            this.styleSet = new HashSet<>();
            this.solutionSet = new HashSet<>();

            for(String s: str.split(", ")) {

                if(s.length() > maxStyleLength) {
                    this.maxStyleLength = s.length();
                }

                this.styleSet.add(s);
            }

        }

        public void updateStyleSet(String s) {

            if(s.length() > this.maxStyleLength) {
                this.maxStyleLength = s.length();
            }

            this.styleSet.add(s);

        }
    }

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day19.data.txt")).getFile());

    int recursiveCount = 0;

    public String run() {

        List<String> data = Reader.readFile(file);

        int answerP1 = findValidTowels(data);

        return "Part 1: " + answerP1;
    }

    private int findValidTowels(List<String> data) {

        boolean b;
        int count = 0;
        Style style = new Style( data.get(0) );

        for(int i = 2; i < data.size(); i++) {

            recursiveCount = 0;
            b = evaluateTowels(data.get(i), style);
            if(b){
                style.solutionSet.add(data.get(i));
                count++;
            }
            System.out.println("Finished: " + data.get(i) + ": " + b);
        }

        return count;
    }

    public boolean evaluateTowels(String s, Style style) {

        if(recursiveCount++ > 10000) {
            return false;
        }

        if(s.isEmpty()) {
            return true;
        }

        if(style.solutionSet.contains(s)) {
            return true;
        }

//        for(int x = 1; x <= Math.min(style.maxStyleLength, s.length()); x++) {
        for(int x = Math.min(style.maxStyleLength, s.length()); x > 0; x--) {

            String sub = s.substring(0, x);

            if (style.styleSet.contains(sub)) {

                if(evaluateTowels(s.substring(x), style)){
                    return true;
                }
            }
        }

        return false;

    }

}

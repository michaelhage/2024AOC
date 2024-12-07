package hagem.aoc2024.day7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day7 {

    static class Equation {

        private final long value;
        private final long[] arr;

        Equation(long value, long[] arr) {
            this.value = value;
            this.arr = arr;
        }

        public long getValue() {
            return value;
        }

        public long[] getArr() {
            return arr;
        }
    }


    File file = new File(Objects.requireNonNull(getClass().getResource("/Day7.data.txt")).getFile());

    private List<Equation> getData(File file) {

//        init return list
        List<Equation> ret = new ArrayList<>();

//        read from the data file
        try(BufferedReader reader = new BufferedReader(new FileReader(file)) ) {
            String line;
            while( (line = reader.readLine()) != null ) {

//                split line by ": "
                String[] splitLine = line.split(": ");

//                add equation object to list
                ret.add( new Equation(
//                        parse the first value
                    Long.parseLong(splitLine[0]),

//                    split the remaining values and append them to an array
                    Arrays.stream(splitLine[1].split(" "))
                            .mapToLong(Long::parseLong).toArray()
                ));


            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public String run() {

//        get the data
        List<Equation> equationList = getData(file);

        long answerP1 = 0, answerP2 = 0;

//        iterate over the equations
        for(Equation eq: equationList) {

//            evaluate part 1
            int val = evaluateEquation(eq, 1, eq.getArr()[0]);

            if(val == 1) {
                answerP1 += eq.getValue();
                answerP2 += eq.getValue();
            } else if(val == 2) {
                answerP2 += eq.getValue();
            }
        }

        return "Part 1: " + answerP1 + ", Part 2: " + answerP2;
    }

    private int evaluateEquation(Equation eq, int i, long val) {

//        check
        if(eq.getArr().length == i) {

            if(val == eq.getValue()) {
                return 1;
            }
            return 0;
        }

//        addition
        int ret = evaluateEquation(eq, i+1, val + eq.getArr()[i]);

        if(ret > 0) {
            return ret;
        }

//        multiply
        ret = evaluateEquation(eq, i+1, val * eq.getArr()[i]);

        if(ret > 0) {
            return ret;
        }

//        concat
        ret = evaluateEquation(eq, i+1, Long.parseLong( "" + val + eq.getArr()[i]) );

        if(ret > 0) {
            return 2;
        }

        return 0;
    }
}

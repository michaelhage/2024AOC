package hagem.aoc2024.day7;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day7 {

    class Equation {

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

        List<Equation> ret = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file)) ) {
            String line;
            while( (line = reader.readLine()) != null ) {

                String[] splitLine = line.split(": ");

                ret.add( new Equation(
                    Long.parseLong(splitLine[0]),

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

        List<Equation> equationList = getData(file);

        long answerP1 = 0, answerP2 = 0;

        for(Equation eq: equationList) {
            long val = evaluateEquationP1(eq);

            answerP1 += val;
            answerP2 += val;
            if(val == 0) {
                answerP2 += evaluateEquationP2(eq);
            }
        }

        return "Part 1: " + answerP1 + ", Part 2: " + answerP2;
    }

    private long evaluateEquationP1(Equation eq) {

        long value = eq.getValue();
        long[] arr = eq.getArr();

        double MAXVALUE =  Math.pow( 2.0d, arr.length * 1.0d );
        int x = 0;

        while( x++ < MAXVALUE){

            long cmp = arr[0];

            String binaryString = StringUtils.leftPad(Integer.toBinaryString(x), arr.length, '0');

            for(int i = 1; i < arr.length; i++) {

                char c = binaryString.charAt(i-1);

                if(c == '1') {
                    cmp += arr[i];
                } else {
                    cmp *= arr[i];
                }

            }

            if(cmp == value) {
                return value;
            }

        }

        return 0;
    }

    private long evaluateEquationP2(Equation eq) {

        long value = eq.getValue();
        long[] arr = eq.getArr();

        double MAXVALUE =  Math.pow( 3.0d, arr.length * 1.0d );
        int x = 0;

        int[] operators = new int[arr.length - 1];

        while( x++ < MAXVALUE){

            long cmp = arr[0];

            for(int i = 1; i < arr.length; i++) {

                switch(operators[i-1]) {

                    case 0:
                        cmp = Long.parseLong( "" + cmp + arr[i]);
                        break;

                    case 1:
                        cmp *= arr[i];
                        break;

                    case 2:
                        cmp += arr[i];
                        break;

                }

            }

            if(cmp == value) {
                return value;
            }

            int y = 0;

            operators[y]++;

            while(operators[y]==3) {

                operators[y++] = 0;

                if(y >= operators.length) {
                    break;
                }

                operators[y]++;

            }

        }

        return 0;
    }


}

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
            long val = evaluateEquationP1(eq);

            answerP1 += val;
            answerP2 += val;

//            if value is 0, then compute part 2
            if(val == 0) {

//                evaluate part 2
                answerP2 += evaluateEquationP2(eq);
            }
        }

        return "Part 1: " + answerP1 + ", Part 2: " + answerP2;
    }

    private long evaluateEquationP1(Equation eq) {

//        get the values to make it look nicer
        long value = eq.getValue();
        long[] arr = eq.getArr();

//        compute the max number of combinations
//        2 ^ (arr.length)
        double MAXVALUE =  Math.pow( 2.0d, (arr.length) * 1.0d );
        int x = 0;

//        iterate over all combinationss of the equation operators
        while( x++ < MAXVALUE){

//            set the comparison value to the first value
            long cmp = arr[0];

//            binary string will have the operation order
            String binaryString = StringUtils.leftPad(Integer.toBinaryString(x), arr.length, '0');

            for(int i = 1; i < arr.length; i++) {

//                get the character
                char c = binaryString.charAt(i-1);

//                if c == 1, then add
                if(c == '1') {
                    cmp += arr[i];
//                otherwise, multiply
                } else {
                    cmp *= arr[i];
                }

            }

//            compare against target value
            if(cmp == value) {
                return value;
            }

        }

//        return 0 if there is no valid equation combinations
        return 0;
    }

    private long evaluateEquationP2(Equation eq) {

//        get the values to make it look nicer
        long value = eq.getValue();
        long[] arr = eq.getArr();

//        compute the max number of combinations
//        3 ^ (arr.length)
        double MAXVALUE =  Math.pow( 3.0d, arr.length * 1.0d );
        int x = 0;

//        array of operator combinations
        int[] operators = new int[arr.length - 1];

        while( x++ < MAXVALUE){

//            set the comparison value as the first value
            long cmp = arr[0];

//            iterate over the array
            for(int i = 1; i < arr.length; i++) {

//                get the operator
                switch(operators[i-1]) {

//                    if value is equal to 0, then concat
                    case 0:
                        cmp = Long.parseLong( "" + cmp + arr[i]);
                        break;

//                    if value is equal to 1, then multiply
                    case 1:
                        cmp *= arr[i];
                        break;

//                    if value is equal to 2, then add
                    case 2:
                        cmp += arr[i];
                        break;

                }

            }

//            if comparison value is equal to target, then return value
            if(cmp == value) {
                return value;
            }

//            set the operator iterator value to 0
            int y = 0;

//            increment the operator value
            operators[y]++;

//            if the value of the operator value at value y is equal to 3,
//            then enter to reset and increment the other values
            while(operators[y]==3) {

//                set the operator value to 0,
//                cause there is no check for values 3 and above
//                then increment the y value
                operators[y++] = 0;

//                if y exceeds the array length, then break the loop
                if(y >= operators.length) {
                    break;
                }

//                increment the value after the increment has occurred
                operators[y]++;

            }

        }

//        return 0 if no match has been found
        return 0;
    }


}

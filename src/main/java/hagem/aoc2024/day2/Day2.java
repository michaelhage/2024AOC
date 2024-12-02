package hagem.aoc2024.day2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day2 {

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day2.data.txt")).getFile());

    private boolean condition(List<Integer> lineList) {

        boolean isIncreasing = true, isDecreasing = true, isSafe = true;
        int i = 0;

        for(i = 1; i < lineList.size(); i++) {

            int difference = Math.abs(lineList.get(i-1) - lineList.get(i) );

            if( difference < 1 || difference > 3 ) {
                isSafe = false;
                break;
            }
            if( lineList.get(i-1) > lineList.get(i) ) {
                isIncreasing = false;
            }
            if( lineList.get(i-1) < lineList.get(i) ) {
                isDecreasing = false;
            }

            if (!isDecreasing && !isIncreasing) {
                break;
            }

        }

        return isSafe && (isDecreasing || isIncreasing);
    }

    public String run() {

        int count1 = 0, count2 = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(file)) ) {
            String line;
            while( (line = reader.readLine()) != null ) {

                String[] splitLine = line.split(" +");
                List<Integer> lineList = new java.util.ArrayList<>(Arrays.stream(splitLine).toList().stream().map(Integer::parseInt).toList());

                if(condition(lineList)) {
                    count1++;
                    continue;
                }

                for(int i = 0; i < lineList.size(); i++) {

                    List<Integer> tempList = new java.util.ArrayList<>(List.copyOf(lineList));
                    tempList.remove(i);

                    if(condition(tempList)) {
                        count2++;
                        break;
                    }
                }

            }

        } catch(IOException e) {
            e.printStackTrace();
            return "";
        }


//        Part 1
//        return "" + count1;

//        Part 2
        return "" + (count2 + count1);
    }

}

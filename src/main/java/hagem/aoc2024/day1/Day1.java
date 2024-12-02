package hagem.aoc2024.day1;

import java.io.*;
import java.util.*;

public class Day1 {

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day1.data.txt")).getFile());

    public String run() {

        List<Integer> list1 = new ArrayList<>();

//        PART 1
//        List<Integer> list2 = new ArrayList<>();

        Map<Integer, Integer> map2 = new HashMap<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file)) ) {
            String line;
            while( (line = reader.readLine()) != null ) {

                String[] splitLine = line.split(" +");

                list1.add( Integer.parseInt(splitLine[0]) );

                int val2 = Integer.parseInt( splitLine[1] );

                if( !map2.containsKey(val2) ) {
                    map2.put(val2, 1);
                    continue;
                }

                map2.merge(val2, 1, Integer::sum);

//                PART1
//                list2.add( Integer.parseInt(splitLine[1]) );
            }

        } catch(IOException e) {
            e.printStackTrace();
            return "";
        }

//        PART 1
//        return part1( list1, list2 );

        return part2(list1, map2);
    }

    private String part1(List<Integer> list1, List<Integer> list2) {

        Collections.sort(list1);
        Collections.sort(list2);

        int sum = 0;

        for(int i = 0; i < list1.size(); i++) {

            sum += Math.abs(list1.get(i) - list2.get(i) );

        }

        return Integer.toString(sum);

    }

    private String part2(List<Integer> list1, Map<Integer, Integer> map2) {

        long sum = 0;

        for (Integer i : list1) {

            if( map2.containsKey(i) ) {

                sum += (long) i * map2.get(i);

            }
        }

        return Long.toString(sum);
    }

}

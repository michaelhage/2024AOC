package hagem.aoc2024.day5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day5 {

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day5.data.txt")).getFile());

    public String run() {

        List<String[]> orderList = new ArrayList<>();
        HashMap<String, LinkedHashSet<String>> dependencyMap = new HashMap<>();

        boolean isOrder = false;
        int countP1 = 0, countP2 = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(file)) ) {
            String line;
            String[] splitLine;

            while( (line = reader.readLine()) != null ) {

                if(line.isEmpty()) {
                    isOrder = true;
                    continue;
                }

                if(!isOrder) {
                    splitLine = line.split("\\|");

                    if(!dependencyMap.containsKey(splitLine[0])) {
                        dependencyMap.put(splitLine[0], new LinkedHashSet<>());
                    }

                    dependencyMap.get(splitLine[0]).add(splitLine[1]);

                } else {

                    splitLine = line.split(",");

                    orderList.add(splitLine);
                }

            }

        } catch(IOException e) {
            e.printStackTrace();
        }


        for (String[] strings : orderList) {

            int val = evaluateP1(strings, dependencyMap);

            if(val != 0) {
                countP1 += evaluateP1(strings, dependencyMap);
                continue;
            }

            countP2 += evaluateP2(strings, dependencyMap);

        }

        return "Part 1: " + countP1 + ", Part 2: " + countP2;
    }

    private int evaluateP1(String[] strings, HashMap<String, LinkedHashSet<String>> dependencyMap) {

        HashSet<String> seenBooks = new HashSet<>();

        for(String str: strings) {

            if(!dependencyMap.containsKey(str)) {
                seenBooks.add(str);
                continue;
            } else if( seenBooks.contains(str) ) {
                continue;
            }

            LinkedHashSet<String> dependencies = dependencyMap.get(str);

            for (String dependency: dependencies) {

                if(seenBooks.contains(dependency)) {

                    return 0;

                }
            }

            seenBooks.add(str);

        }

        return Integer.parseInt( strings[strings.length / 2] );

    }

    private void swap(String[] arr, int x, int y) {

        String temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;

    }

    private int evaluateP2(String[] strings, HashMap<String, LinkedHashSet<String>> dependencyMap) {

        HashMap<String, Integer> seenBooks = new HashMap<>();

        int i = 0;

        loop:
        while(i < strings.length) {

            String str = strings[i];

            if(!dependencyMap.containsKey(str)) {
                seenBooks.put(str, i++);
                continue;
            } else if( seenBooks.containsKey(str) ) {

                seenBooks.put(str, i++);
                continue;
            }

            LinkedHashSet<String> dependencies = dependencyMap.get(str);

            for (String dependency: dependencies) {

                if(seenBooks.containsKey(dependency)) {

                    int j = seenBooks.get(dependency);
                    swap(strings, i, j);


//                    reset
                    i = 0;
                    seenBooks = new HashMap<>();
                    continue loop;

                }
            }

            seenBooks.put(str, i);

        }

        return Integer.parseInt( strings[strings.length / 2] );

    }

}

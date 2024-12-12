package hagem.aoc2024.day11;

import hagem.utils.Reader;

import java.io.File;
import java.util.*;

public class Day11 {

    class Blink {

        final int blink;
        final long value;

        public Blink(int blink, long value) {
            this.blink = blink;
            this.value = value;
        }
    }

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day11.data.txt")).getFile());

    public String runTest(int blinks) {

        List<String> data = Reader.readFile(file);

        String[] dataArr = data.getFirst().split(" ");

        HashMap<Long, Long> longIntegerHashMap = initMap(dataArr);

        long answerP1 = 0L;

        answerP1 = evaluate(longIntegerHashMap, blinks);

        System.out.println(answerP1);
        return "" + answerP1;
    }

    public String run() {

        List<String> data = Reader.readFile(file);

        String[] dataArr = data.getFirst().split(" ");

        HashMap<Long, Long> longIntegerHashMap = initMap(dataArr);

        long answerP1 = evaluate(longIntegerHashMap, 25);
        long answerP2 = evaluate(longIntegerHashMap, 75);

        return "Part 1: " + answerP1 + ", Part 2: " + answerP2;
    }

    private HashMap<Long, Long> initMap(String[] arr) {

        HashMap<Long, Long> retMap = new HashMap<>();

        for(String s: arr) {

            long value = Long.parseLong(s);

//            if(!retMap.containsKey(value)) {
//                retMap.put(value, 1);
//                continue;
//            }

            retMap.merge(value, 1L, Long::sum);
        }

        return retMap;
    }

    private long evaluate(HashMap<Long, Long> map, int blinks) {

        for(int i = 0; i < blinks; i++) {

            HashMap<Long, Long> newMap = new HashMap<>();

            for(Map.Entry<Long,Long> entry: map.entrySet()) {

                long key = entry.getKey();
                String s = Long.toString(key);

                if( s.length() % 2 == 0 ) {

                    newMap.merge(Long.parseLong(s.substring( 0, s.length() / 2)), entry.getValue(), Long::sum);
                    newMap.merge(Long.parseLong(s.substring(s.length()/2)), entry.getValue(), Long::sum);
                    continue;

                } else if( key == 0L ) {
                    newMap.merge(1L, entry.getValue(), Long::sum);
                    continue;
                }

                newMap.merge(key * 2024, entry.getValue(), Long::sum);


            }

            map = newMap;
        }


        return map.values().stream().mapToLong(Long::longValue).sum();
    }

//    SOLUTION TO PART 1, MY PRINCE, WISH YOU WERE MORE SPACE EFFICIENT
//
//    HashMap<Blink, Integer> blinkIntegerHashMap = new HashMap<>();
//    private int evaluate(String s, int count, int blinks) {
//
//        if (blinks == 0){
//            return count + 1;
//        }
//
//        long value = Long.parseLong(s);
//        Blink blink = new Blink(blinks, value);
//
//        if(blinkIntegerHashMap.containsKey(blink)) {
//            return blinkIntegerHashMap.get(blink);
//        }
//
//        int localCount = 0;
//        s = Long.toString(value);
//        int length = s.length();
//
//        if( length % 2 == 0 ) {
//
//            localCount = evaluate(s.substring( 0, length / 2), localCount, blinks - 1);
//            localCount = evaluate(s.substring(length/2, length), localCount, blinks - 1);
//
//        } else if( value == 0 ) {
//            localCount = evaluate("1", localCount, blinks - 1);
//        } else {
//
//            localCount = evaluate( Long.toString(2024 * value), localCount, blinks - 1 );
//
//        }
//
//        blinkIntegerHashMap.put(blink, localCount);
//
//        return count + localCount;
//
//    }

}

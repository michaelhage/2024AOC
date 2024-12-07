package hagem.aoc2024;

import hagem.aoc2024.day7.Day7;

public class Main {
    public static void main(String[] args) {

        long startTime = System.nanoTime();

//        Day 1
//        System.out.println(new Day1().run());

//        Day 2
//        System.out.println(new Day2().run());

//        Day 3
//        System.out.println(new Day3().run());

//        Day 4
//        System.out.println(new Day4().run());

//        Day 5
//        System.out.println(new Day5().run());

//        Day 6
//        System.out.println(new Day6().run());

//        Day 7
//        System.out.println(new Day7Old().run());
        System.out.println(new Day7().run());

        long endTime = System.nanoTime();

        System.out.println("Execution Time: " + ( (endTime - startTime) / 1000000d) + "ms");

    }
}
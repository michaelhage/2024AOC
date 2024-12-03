package hagem.aoc2024;

import hagem.aoc2024.day3.Day3;

public class Main {
    public static void main(String[] args) {

        long startTime = System.nanoTime();

//        Day 1
//        System.out.println(new Day1().run());

//        Day 2
//        System.out.println(new Day2().run());

//        Day 3
        System.out.println(new Day3().run());

        long endTime = System.nanoTime();

        System.out.println("Execution Time: " + ( (endTime - startTime) / 1000000d) + "ms");

    }
}
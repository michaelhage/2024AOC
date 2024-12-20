package hagem.aoc2024;

import hagem.aoc2024.day16.Day16;
import hagem.aoc2024.day17.Day17;
import hagem.aoc2024.day18.Day18;
import hagem.aoc2024.day19.Day19;

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
//        System.out.println(new Day7().run());

//        Day 8
//        System.out.println(new Day8().run());

//        Day 9
//        System.out.println(new Day9().run());

//        Day 10
//        System.out.println(new Day10().run());

//        Day 11
//        new Day11().run();

//        Day 12
//        new Day12().run();

//        Day 13
//        System.out.println(new Day13().run());

//        Day 14
//        System.out.println(new Day14().run());

//        Day 15
//        System.out.println(new Day15().run());

//        Day 16
//        System.out.println(new Day16().run());

//        Day 17
//        System.out.println(new Day17().run() );

//        Day 18
//        System.out.println(new Day18().run());

        System.out.println(new Day19().run());

        long endTime = System.nanoTime();

        System.out.println("Execution Time: " + ( (endTime - startTime) / 1000000d) + "ms");

    }
}
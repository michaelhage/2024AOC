package hagem.aoc2024.day11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day11Test {

    @TestFactory
    Stream<DynamicTest> testDay11() {

        int[][] data = {
                {3, 1},
                {4, 2},
                {5, 3},
                {9, 4},
                {13, 5},
                {22, 6},
                {55312, 25}
        };

//        int[][] data = {
//                {211306, 25}
//        };

        return Arrays.stream(data).map(entry -> {
                int i = entry[1];
                int actual = entry[0];

                int expected = Integer.parseInt(new Day11().runTest(i));

                return dynamicTest("Blink: " + i, () -> {
                   assertEquals(actual, expected);
                });
        });

    }

}

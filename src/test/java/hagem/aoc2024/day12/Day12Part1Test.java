package hagem.aoc2024.day12;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class Day12Part1Test {

    class Data {
        int expected;
        File file;

        public Data(int expected, File file) {
            this.expected = expected;
            this.file = file;
        }
    }

    @TestFactory
    Stream<DynamicTest> testDay12() {

        Data[] dataArr = new Data[]{
                new Data(140, new File(Objects.requireNonNull(getClass().getResource("/day12/part1/Day12.data.1.txt")).getFile())),
                new Data(772, new File(Objects.requireNonNull(getClass().getResource("/day12/part1/Day12.data.2.txt")).getFile())),
                new Data(1930, new File(Objects.requireNonNull(getClass().getResource("/day12/part1/Day12.data.3.txt")).getFile())),
        };

        return Arrays.stream(dataArr).map(entry -> {
                File file = entry.file;
                int actual = entry.expected;

                int expected = Integer.parseInt(new Day12Part1().runTest(file));

                return dynamicTest("Map for file: " + file, () -> {
                   assertEquals(actual, expected);
                });
        });

    }

}

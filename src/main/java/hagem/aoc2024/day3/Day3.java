package hagem.aoc2024.day3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;


public class Day3 {

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day3.data.txt")).getFile());

    public String run() {

//        PART 1
//        Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)");

//        PART 2
        Pattern pattern = Pattern.compile("(mul\\(\\d+,\\d+\\)|(do\\(\\)|don't\\(\\)))");

        long total = 0;
        boolean enabler = true;


        try(BufferedReader reader = new BufferedReader(new FileReader(file)) ) {
            String line;
            while( (line = reader.readLine()) != null ) {

                List<MatchResult> results = pattern
                                    .matcher(line)
                                    .results()
                                    .toList();

                for (MatchResult result : results) {

                    String match = result.group(0);

                    if(match.startsWith("don't")) {
                        enabler = false;
                        continue;

                    } else if (match.startsWith("do")) {
                        enabler = true;
                        continue;

                    }

                    if(enabler) {
                        match = match.replaceAll("[^0-9,]", "");

                        String[] matchArr = match.split(",");

                        total += Long.parseLong(matchArr[0]) * Long.parseLong(matchArr[1]);
                    }

                }

            }

        } catch(IOException e) {
            e.printStackTrace();
            return "";
        }

        return "" + total;
    }

}

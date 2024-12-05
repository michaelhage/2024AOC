package hagem.aoc2024.day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day4 {

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day4.data.txt")).getFile());

//    private final char[] XMAS = {'X','M','A','S'};

    private List<String> getData(File file) {

        List<String> ret = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file)) ) {
            String line;
            while( (line = reader.readLine()) != null ) {

                ret.add(line);
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public String run() {

        int count = 0;
        List<String> list = getData(file);

        for(int y = 0; y < list.size(); y++) {

            for(int x = 0; x < list.get(y).length(); x++) {

                count += coordinate(x, y, list);

            }

        }


        return "" + count;
    }

//    PART 1

    /*
    private int coordinate(int x, int y, List<String> list) {

        if(list.get(y).charAt(x) != 'X') {
            return 0;
        }

        int count = 0;

        for(int dy = -1; dy <= 1; dy++) {
            for(int dx = -1; dx <= 1; dx++) {
                count += evaluate(x, y, list, dx, dy);
            }
        }

        return count;

    }

    private int evaluate(int x, int y, List<String> list, int dx, int dy) {

        for(int i = 1; i < XMAS.length; i++) {

            x += dx;
            y += dy;

            if(y < 0 || y >= list.size()) {
                return 0;
            }

            if(x < 0 || x >= list.get(y).length()) {
                return 0;
            }

            if(list.get(y).charAt(x) != XMAS[i]) {
                return 0;
            }

        }

        return 1;
    }
*/

//    PART 2
    private int coordinate(int x, int y, List<String> list) {

        if(list.get(y).charAt(x) != 'A') {
            return 0;
        }

        return evaluate(x, y, list);

    }

    private int evaluate(int x, int y, List<String> list) {

        if(y < 1 || y >= list.size() - 1) {
            return 0;
        }

        if(x < 1 || x >= list.get(y).length() - 1) {
            return 0;
        }

        String str1 = "" + list.get(y+1).charAt(x+1) + list.get(y-1).charAt(x-1);
        String str2 = "" + list.get(y-1).charAt(x+1) + list.get(y+1).charAt(x-1);

        if(!str1.contains("S") || !str1.contains("M")) {
            return 0;
        }

        if(!str2.contains("S") || !str2.contains("M")) {
            return 0;
        }

        return 1;
    }

}

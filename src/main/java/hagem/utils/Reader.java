package hagem.utils;

import hagem.aoc2024.day8.Day8;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    public List<String> readFile(File file) {

        List<String> ret = new ArrayList<>();

        int y = 0;

//        read from the data file
        try(BufferedReader reader = new BufferedReader(new FileReader(file)) ) {
            String line;
            while( (line = reader.readLine()) != null ) {
//                add line
                ret.add(line);
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

}

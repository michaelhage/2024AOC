package hagem.aoc2024.day14;

import hagem.utils.Reader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Part2 {

    class Robot {

        int posX;
        int posY;

        final int vX;
        final int vY;

        public Robot(int posX, int posY, int vX, int vY) {
            this.posX = posX;
            this.posY = posY;
            this.vX = vX;
            this.vY = vY;
        }

        public Robot(List<String> list) {
            if(list.size() < 4) {
                new Robot(0,0,0,0);
            }

            this.posX = Integer.parseInt(list.get(0));
            this.posY = Integer.parseInt(list.get(1));
            this.vX = Integer.parseInt(list.get(2));
            this.vY =Integer.parseInt(list.get(3));
        }
    }

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day14.data.txt")).getFile());
    File outputFolder = Paths.get(String.valueOf(file.getParentFile()), "Day14").toFile();

    final int WIDTH = 101;
    final int HEIGHT = 103;

    final int SECONDS = 100;

    public String run() {

        List<String> data = Reader.readFile(file);
        List<Robot> robotList = parseData(data);

        int[][] robotMap = initMap(robotList);

        if( !outputFolder.exists() ) {
            boolean created = outputFolder.mkdirs();

            if(created) System.out.println("Output Folder Created: " + outputFolder);
        }

        int ITERATION = 0;

        for(ITERATION = 1; ITERATION < 300000; ITERATION++) {

            updateRobotPosition(robotList, robotMap);

            double variance = calculateVariance(robotList);

            if(calculateVariance(robotList) < 400) {

                createImage(robotMap, ITERATION);
                System.out.println("Iteration: " + ITERATION + ", Variance: "+  variance);
                break;
            }
        }

        return "Part 2: " + ITERATION;
    }

    private int[][] initMap(List<Robot> robotList) {

         int[][] robotMap = new int[HEIGHT][WIDTH];

        for(Robot robot: robotList) {

            robotMap[robot.posY][robot.posX]++;

        }

        return robotMap;

    }

    public void updateRobotPosition(List<Robot> robotList, int[][] robotMap) {

        for (Robot robot : robotList){

            robotMap[robot.posY][robot.posX]--;

            robot.posX = Math.floorMod(robot.posX + robot.vX, WIDTH);
            robot.posY = Math.floorMod(robot.posY + robot.vY, HEIGHT);

            robotMap[robot.posY][robot.posX]++;
        }

    }

    private double calculateVariance(List<Robot> robotList) {

        double total = 0;

        List<Double> vector = new ArrayList<>();

        for(Robot robot: robotList) {

            double cartesian = Math.sqrt(robot.posX * robot.posX + robot.posY * robot.posY);
            vector.add(cartesian);

            total += cartesian;

        }

        double mean = total / robotList.size();
        double variance = 0;

        for(int i = 0; i < robotList.size(); i++) {
            variance += Math.pow( vector.get(i) - mean, 2 );
        }

        return variance / robotList.size();

    }


    private void createImage(int[][] robotMap, int iteration) {

        File filename = Paths.get(outputFolder.getAbsolutePath(), "Image_" + iteration + ".png").toFile();
        final int PIXEL_SIDE_LENGTH = 10;

        if(filename.exists()) {
            return;
        }

        BufferedImage img =
                new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_BYTE_GRAY);


        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < WIDTH; x++) {

                int a = 0;

                if(robotMap[y][x] != 0) {
                    a = 255;
                }

                int rgb = (a<<24) | (a<<16) | (a<<8) | a;

                img.setRGB(x, y, rgb);

            }
        }

        try {
            ImageIO.write(img, "png", filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    private List<Robot> parseData(List<String> data) {

        List<Robot> ret = new ArrayList<>();

        Pattern pattern = Pattern.compile("-?\\d+");

        for(String line: data) {

            List<String> temp = new ArrayList<>();
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                temp.add(matcher.group());
            }

            ret.add(new Robot(temp));

        }

        return ret;
    }


}

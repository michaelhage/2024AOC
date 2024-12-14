package hagem.aoc2024.day14;

import hagem.utils.Reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Part1 {

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

    final int WIDTH = 101;
    final int HEIGHT = 103;

//    final int WIDTH = 11;
//    final int HEIGHT = 7;


    final int SECONDS = 100;

    public String run() {

        List<String> data = Reader.readFile(file);
        List<Robot> robotList = parseData(data);

        for(Robot robot: robotList) {
            calculateRobotPosition(robot);
        }

        long answerP1 = evaluateQuadrant(robotList);

        return "Part 1: " + answerP1;

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

    private void calculateRobotPosition(Robot robot) {

        robot.posX = Math.floorMod(robot.posX + robot.vX * SECONDS,  WIDTH);
        robot.posY = Math.floorMod(robot.posY + robot.vY * SECONDS, HEIGHT);

    }

    private long evaluateQuadrant(List<Robot> robotList) {

        int MIDDLE_HEIGHT = HEIGHT / 2;
        int MIDDLE_WIDTH = WIDTH / 2;

        long q1 = 0;
        long q2 = 0;
        long q3 = 0;
        long q4 = 0;

        for (Robot robot : robotList) {

            if(robot.posX < MIDDLE_WIDTH) {

                if(robot.posY < MIDDLE_HEIGHT) {
                    q1++;
                } else if( robot.posY > MIDDLE_HEIGHT ) {
                    q2++;
                }

            } else if (robot.posX > MIDDLE_WIDTH) {

                if(robot.posY < MIDDLE_HEIGHT) {
                    q3++;
                } else if( robot.posY > MIDDLE_HEIGHT ) {
                    q4++;
                }

            }

        }

        return q1 * q2 * q3 * q4;

    }


}

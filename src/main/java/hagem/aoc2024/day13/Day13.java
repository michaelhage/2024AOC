package hagem.aoc2024.day13;

import hagem.utils.Reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day13 {

//    Max Button Presses
    int MAXBUTTON = 100;

    class Crane {
        final long aX;
        final long aY;
        final long bX;
        final long bY;

        long targetX;
        long targetY;

        public Crane(long aX, long aY, long bX, long bY, long targetX, long targetY) {
            this.aX = aX;
            this.aY = aY;
            this.bX = bX;
            this.bY = bY;
            this.targetX = targetX;
            this.targetY = targetY;
        }

        void part2Fix() {
            targetX += 10000000000000L;
            targetY += 10000000000000L;
        }

    }


    File file = new File(Objects.requireNonNull(getClass().getResource("/Day13.data.txt")).getFile());

    public String run() {

        List<String> data = Reader.readFile(file);

        List<Crane> craneList = parseData(data);

        long answerP1 = 0, answerP2 = 0;

        for(Crane crane: craneList) {

            long countP1 = evaluateCrane(crane);

            crane.part2Fix();

            long countP2 = evaluateCrane(crane);

            System.out.println("Part 1: " + countP1);
            System.out.println("Part 2: " + countP2);

            answerP1 += countP1;
            answerP2 += countP2;
        }

        return "Part 1: " + answerP1 + ", Part 2: " + answerP2;

    }


    private double getDeterminant(Crane crane) {

        long det = crane.aX * crane.bY - crane.bX * crane.aY;

        return 1d * det;

    }

    final double delta = 0.01;

    private boolean close(double val) {
        return Math.abs( Math.round(val) - val  ) < delta;
    }

    private boolean check(Crane crane, double x, double y) {

        long a = Math.round(x);
        long b = Math.round(y);

        if( crane.targetX != crane.aX * a + crane.bX * b ) {
            return false;
        }

        return crane.targetY == crane.aY * a + crane.bY * b;
    }


    public long evaluateCrane(Crane crane) {

        double inv_det = 1 / getDeterminant(crane);

        double x = inv_det * ((crane.targetX * crane.bY) - (crane.targetY * crane.bX));
        double y = inv_det * ((crane.targetY * crane.aX) - (crane.targetX * crane.aY));


        if(close(x) && close(y) && check(crane, x, y)) {

//            System.out.println("A: " + x + ",B: " + y);

            return Math.round(x) * 3 + Math.round(y);

        }

        return 0;

    }

    public List<Crane> parseData(List<String> data) {

        long aX=0, aY=0, bX=0, bY=0, targetX=0, targetY=0;
        String[] split;
        List<Crane> retList = new ArrayList<>();
        boolean finalParse = false;

        for(int i = 0; i < data.size(); i++) {

            String line = data.get(i);

            switch(i % 4) {
                case 0:
                    split = line.split(",");
                    aX = Long.parseLong(split[0].replaceAll("[^0-9]", "") );
                    aY = Long.parseLong(split[1].replaceAll("[^0-9]", "") );
                    break;
                case 1:
                    split = line.split(",");
                    bX = Long.parseLong(split[0].replaceAll("[^0-9]", "") );
                    bY = Long.parseLong(split[1].replaceAll("[^0-9]", "") );
                    break;
                case 2:
                    split = line.split(",");
                    targetX = Long.parseLong(split[0].replaceAll("[^0-9]", "") );
                    targetY = Long.parseLong(split[1].replaceAll("[^0-9]", "") );
                    finalParse = true;
                    break;
                case 3:
                    retList.add( new Crane(
                            aX,aY,bX,bY,targetX,targetY
                    ) );
                    finalParse = false;
                    break;
            }

        }

        if(finalParse) {
            retList.add( new Crane(
                    aX,aY,bX,bY,targetX,targetY
            ) );
        }

        return retList;

    }

}

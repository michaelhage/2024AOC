package hagem.aoc2024.day8;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Day8 {

    static class Node {

        final char frequency;
        final Point location;

        public Node(char frequency, Point location) {
            this.frequency = frequency;
            this.location = location;
        }

        public char getFrequency() {
            return frequency;
        }

        public Point getLocation() {
            return location;
        }
    }


    File file = new File(Objects.requireNonNull(getClass().getResource("/Day8.data.txt")).getFile());

    private List<Node> getData(File file) {

//        init return list
        List<Node> ret = new ArrayList<>();

        int y = 0;

//        read from the data file
        try(BufferedReader reader = new BufferedReader(new FileReader(file)) ) {
            String line;
            while( (line = reader.readLine()) != null ) {
//                add line
                for(int x = 0; x < line.length(); x++) {

                    if( line.charAt(x) != '.' ) {

                        ret.add(new Node(
                                line.charAt(x),
                                new Point(x, y)
                        ));
                    }

                }

                y++;
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    private Point getDataBoundaries(File file) {
        int x= 0, y = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(file)) ) {
            String line;
            while( (line = reader.readLine()) != null ) {
//                add line
                y++;
                if(x == 0) {
                    x = line.length();
                }


            }

        } catch(IOException e) {
            e.printStackTrace();
        }


        return new Point(x, y);
    }

    public String run() {

        List<Node> nodeList = getData(file);
        Point boundaries = getDataBoundaries(file);

        int answerP1 = evaluateAntiNodes(nodeList, boundaries);

        return "Part 1: " + answerP1;

    }

    private int evaluateAntiNodes(List<Node> nodeList, Point boundaries) {

        HashMap<Character, List<Point>> nodeMap = new HashMap<>();
//        HashSet<Point> locationSet = new HashSet<>();


        HashSet<Point> antiNodeSet = new HashSet<>();

        for(Node node: nodeList) {

            if(!nodeMap.containsKey( node.getFrequency() )) {
                nodeMap.put(node.getFrequency(),
                            new ArrayList<>()
                );
            }

            nodeMap.get(node.getFrequency()).add(node.getLocation());
//            locationSet.add(node.getLocation());

        }

        for( List<Point> nodes: nodeMap.values() ) {

            for(int i = 0; i < nodes.size(); i++) {

                Point location1 = nodes.get(i);

                for(int j = 0; j < nodes.size(); j++) {

                    if(i == j) {
                        continue;
                    }

                    Point location2 = nodes.get(j);

                    List<Point> antiNodeList = calculateAntiNodeLocations(location1, location2);


                    for(Point antiNode: antiNodeList) {
//                        if(locationSet.contains(antiNode)) {
//                            continue;
//                        }
                        if(!checkBoundaries(boundaries, antiNode)) {
                            continue;
                        }

//                        locationSet.add(antiNode);
                        antiNodeSet.add(antiNode);
                    }

                }
            }

        }

        for(Point node: antiNodeSet) {
            System.out.println("x: " + node.x + ", y:" + node.y);
        }

        return antiNodeSet.size();
    }

    private boolean checkBoundaries(Point boundaries, Point antiNode) {

        if(antiNode.x < 0 || antiNode.x >= boundaries.x) {
            return false;
        }

        return antiNode.y >= 0 && antiNode.y < boundaries.y;

    }

    private List<Point> calculateAntiNodeLocations(Point location1, Point location2) {

        int dx = location1.x - location2.x;
        int dy = location1.y - location2.y;

        List<Point> antiNodeList = new ArrayList<>();
//       calculate antinodes

        Point point;

        for(int i = -50; i <=50; i++) {

//            if( i == 0) {
//                continue;
//            }

            point = new Point(location1.x + dx * i, location1.y + dy * i);
//            if(point.equals(location2)) {
//                continue;
//            }

            antiNodeList.add(point);
        }

        for(int i = -50; i <= 50; i++) {

//            if( i == 0) {
//                continue;
//            }

            point = new Point(location2.x + dx * i, location2.y + dy * i);
//            if(point.equals(location1)) {
//                continue;
//            }

            antiNodeList.add(point);
        }

        return antiNodeList;
    }

}

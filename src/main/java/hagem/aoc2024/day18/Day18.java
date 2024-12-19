package hagem.aoc2024.day18;

import hagem.utils.Reader;

import java.io.File;
import java.util.*;

public class Day18 {

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day18.data.txt")).getFile());

//    final int WIDTH = 7;
//    final int HEIGHT = 7;
//    final int ITERATION = 12;

    final int WIDTH = 71;
    final int HEIGHT = 71;
    final int ITERATION = 1024;

    Node startNode;
    Node endNode;

    public String run() {

        List<String> data =  Reader.readFile(file);

        LinkedHashMap<Node,Node> nodeMap = initNodes();

        corruptNodes(data, nodeMap);

        initStartNode(nodeMap);
        initEndNode(nodeMap);

        djikstra();

        long answerP1 = endNode.getScore();

        printMap(nodeMap);

        String answerP2 = part2(data, nodeMap);

        return "Part 1: " + answerP1 + ", Part 2: " + answerP2;
    }

    private void initEndNode(LinkedHashMap<Node,Node> nodeMap) {
        endNode = nodeMap.get(new Node(WIDTH - 1, HEIGHT - 1));
    }

    private void initStartNode(LinkedHashMap<Node,Node> nodeMap) {
        startNode = nodeMap.get(new Node(0, 0));
    }

    private LinkedHashMap<Node,Node> initNodes() {

//        List<Node> nodeList = new ArrayList<>();
        LinkedHashMap<Node,Node> map = new LinkedHashMap<>();

        Node n;

        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < WIDTH; x++) {

                n = new Node(x, y);

//                nodeList.add( n );
                map.put(n, n);
            }
        }

        createGraph(map);

        return map;
    }

    private void createGraph(LinkedHashMap<Node,Node> map) {

        Node n;

        for(Node node: map.keySet()) {

//            north
            n = new Node(node.x, node.y - 1);
            node.north = map.getOrDefault(n, null);

//            south
            n = new Node(node.x, node.y + 1);
            node.south = map.getOrDefault(n, null);

//            east
            n = new Node(node.x + 1, node.y);
            node.east = map.getOrDefault(n, null);

//            west
            n = new Node(node.x - 1, node.y);
            node.west = map.getOrDefault(n, null);

        }

    }

    private void corruptNodes(List<String> data, LinkedHashMap<Node,Node> nodeMap) {

        for(int i = 0; i < ITERATION; i++) {

            corrupt(data.get(i), nodeMap);

        }

    }

    private void corrupt(String s, LinkedHashMap<Node,Node> nodeMap) {

        String[] split = s.split(",");

        Node n = new Node( Integer.parseInt(split[0]), Integer.parseInt(split[1]) );

        nodeMap.get(n).notCorrupted = false;

    }

    private void printMap(LinkedHashMap<Node, Node> nodeMap) {

        for(int y = 0; y < HEIGHT; y++) {

            StringBuilder sb = new StringBuilder();

            for(int x = 0; x < WIDTH; x++) {

                sb.append( nodeMap.get( new Node(x, y) ).notCorrupted ? '.' : '#');

            }

            System.out.println(sb);
        }

    }

    private void djikstra() {

        List<Node> evaluateList = new ArrayList<>();
        List<Node> visitedList = new ArrayList<>();

        evaluateList.add( startNode );
        startNode.updateScore(0);

        while(!evaluateList.isEmpty()) {

            visitedList = new ArrayList<>();

            for (Node node: evaluateList) {

                if(node.equals(endNode)) {
                    continue;
                }

                evaluateNode(node, visitedList);

            }


            evaluateList = visitedList;
        }
    }

    private void evaluateNode(Node node, List<Node> visitedList) {

        Node n;

//        check north
        if( (n = node.north) != null && n.notCorrupted) {

            if(n.updateScore( node.getScore() + 1 )) {
                visitedList.add(n);
            }

        }

//        check south
        if( (n = node.south ) != null && n.notCorrupted) {
            if(n.updateScore( node.getScore() + 1 )) {
                visitedList.add(n);
            }
        }

//        check east
        if( (n = node.east ) != null && n.notCorrupted) {
            if(n.updateScore( node.getScore() + 1 )) {
                visitedList.add(n);
            }
        }

        if( (n = node.west ) != null && n.notCorrupted) {

            if(n.updateScore( node.getScore() + 1 )) {

                visitedList.add(n);
            }
        }

    }

    private void resetNodes(LinkedHashMap<Node,Node> nodeMap) {

        for(Node node: nodeMap.keySet()) {
            node.resetScore();
        }

    }

    private String part2(List<String> data, LinkedHashMap<Node,Node> nodeMap) {

        for(int i = ITERATION; i < data.size(); i++) {

            corrupt(data.get(i), nodeMap);

            resetNodes(nodeMap);

            startNode.updateScore(0);

            djikstra();

            if(endNode.getScore() == Long.MAX_VALUE) {
                return data.get(i);
            }

        }

        return "";
    }

}

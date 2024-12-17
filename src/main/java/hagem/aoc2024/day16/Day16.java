package hagem.aoc2024.day16;

import hagem.utils.Reader;

import java.io.File;
import java.util.*;

public class Day16 {

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day16.data.txt")).getFile());
    private final char WALL = '#';
    private final char START = 'S';
    private final char END = 'E';

    Node startNode = null;
    Node endNode = null;


    public String run() {

        List<char[]> data = Reader.readFile(file).stream().map(String::toCharArray).toList();

        List<Node> nodeList = parseData(data);

        djikstra();

        return "" + endNode.getScore();
    }

    private List<Node> parseData(List<char[]> data) {

        List<Node> nodeList = new ArrayList<>();
        HashMap<Node, Node> nodeMap = new HashMap<>();

        for(int y = 0; y < data.size(); y++ ) {
            for(int x = 0; x < data.get(y).length; x++) {

                char c = data.get(y)[x];

                if( c != WALL ) {

                    Node n = new Node(x, y);

                    nodeList.add(n);
                    nodeMap.put(n, n);

                    if(c == START) {
                        startNode = n;
                    } else if (c == END) {
                        endNode = n;
                    }

                }

            }
        }

        createGraph(nodeList, nodeMap);

        return nodeList;

    }

    private void createGraph(List<Node> nodeList, HashMap<Node, Node> nodeMap) {

        for(Node node: nodeList) {

            for ( Direction dir: Direction.values() ) {

                Node n = new Node(node.x + dir.dx, node.y + dir.dy);

                if (nodeMap.containsKey(n) ) {

                    node.setNodeDirection(dir, nodeMap.get(n) );

                }
            }


        }

    }

    private void djikstra() {

        List<Node> evaluateList = new ArrayList<>();
        List<Node> visitedList = new ArrayList<>();

        evaluateList.add(startNode);
        startNode.dir = Direction.EAST;
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

//        Go Forth, Onwards to Victory
        if( (n = node.getNode(node.dir )) != null ) {

            if(n.updateScore( node.getScore() + 1 )) {
                n.setDir(node.dir);
                visitedList.add(n);
            }

        }

//        rotate Clockwise
        if( (n = node.getNode( node.dir.getClockwise() )) != null ) {
            if(n.updateScore( node.getScore() + 1001 )) {
                n.setDir(node.dir.getClockwise());
                visitedList.add(n);
            }
        }

//
        if( (n = node.getNode( node.dir.getCounterClockwise() ) ) != null ) {
            if(n.updateScore( node.getScore() + 1001 )) {
                n.setDir(node.dir.getCounterClockwise());
                visitedList.add(n);
            }
        }

    }


}

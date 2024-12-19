package hagem.aoc2024.day16;

import hagem.utils.Reader;

import java.io.File;
import java.util.*;

public class Day16 {

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day16.data.txt")).getFile());
    private final char WALL = '#';
    private final char START = 'S';
    private final char END = 'E';
    private final char MARK = 'O';

    Node startNode = null;
    Node endNode = null;


    public String run() {

        List<char[]> mazeMap = Reader.readFile(file).stream().map(String::toCharArray).toList();

        List<Node> nodeList = parseData(mazeMap);

        djikstra();

        long answerP1 = endNode.getScore();

        LinkedHashSet<Node> pathSet = markPath(endNode, mazeMap);

        resetScores(nodeList);

        findSeats(answerP1, mazeMap, pathSet);

        long answerP2 = countSeats(mazeMap);

        return "Part 1: " + answerP1 + ", Part 2: " + answerP2;
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

    private void resetScores(List<Node> nodeList) {

        for(Node node: nodeList) {

            node.prevNodesOrig = node.prevNodes;
            node.updateScore(Long.MAX_VALUE, null);

        }

    }

    private void djikstra() {

        List<Node> evaluateList = new ArrayList<>();
        List<Node> visitedList = new ArrayList<>();

        evaluateList.add(startNode);
        startNode.dir = Direction.EAST;
        startNode.updateScore(0, startNode);

        while(!evaluateList.isEmpty()) {

            visitedList = new ArrayList<>();

            for (Node node: evaluateList) {

                if(node.equals(endNode)) {
                    continue;
                }

                evaluateNodeP1(node, visitedList);

            }


            evaluateList = visitedList;
        }
    }

    private void evaluateNodeP1(Node node, List<Node> visitedList) {

        Node n;

//        Go Forth, Onwards to Victory
        if( (n = node.getNode(node.dir )) != null ) {

            if(n.updateScore( node.getScore() + 1, node )) {
                n.setDir(node.dir);
                visitedList.add(n);
            }

        }

//        rotate Clockwise
        if( (n = node.getNode( node.dir.getClockwise() )) != null ) {
            if(n.updateScore( node.getScore() + 1001, node )) {
                n.setDir(node.dir.getClockwise());
                visitedList.add(n);
            }
        }

//
        if( (n = node.getNode( node.dir.getCounterClockwise() ) ) != null ) {
            if(n.updateScore( node.getScore() + 1001, node )) {
                n.setDir(node.dir.getCounterClockwise());
                visitedList.add(n);
            }
        }

    }

    private void findSeats(long targetScore, List<char[]> mazeMap, LinkedHashSet<Node> pathSet) {

        List<Node> nextList = new ArrayList<>();

        endNode.dir = endNode.dir.getOppositeDirection();
        endNode.updateScore(0, endNode);

        List<Node> evaluateList = new ArrayList<>();
        evaluateList.add(endNode);

        for(Node node: pathSet) {

            evaluateNodeP2(node, targetScore, mazeMap);

        }

    }

    private void evaluateNodeP2(Node node, long targetScore, List<char[]> mazeMap) {

        Node n;

        if(node.getScore() > targetScore && node.getScore() != Long.MAX_VALUE) {
            return;
        }

        if(node.equals(startNode) && node.getScore() == targetScore) {

            markPath(node, mazeMap);
            startNode.updateScore(Long.MAX_VALUE, null);

        }

//        Go Forth, Onwards to Victory
        if( (n = node.getNode(node.dir )) != null ) {

            if(n.updateScore( node.getScore() + 1, node )) {
                n.setDir(node.dir);

                evaluateNodeP2(n, targetScore, mazeMap);

            }

        }

//        rotate Clockwise
        if( (n = node.getNode( node.dir.getClockwise() )) != null ) {

            if(n.updateScore( node.getScore() + 1001, node )) {
                n.setDir(node.dir.getClockwise());
                evaluateNodeP2(n, targetScore, mazeMap);

            }
        }

//
        if( (n = node.getNode( node.dir.getCounterClockwise() ) ) != null ) {

            if(n.updateScore( node.getScore() + 1001, node )) {

                n.setDir(node.dir.getCounterClockwise());
                evaluateNodeP2(n, targetScore, mazeMap);
            }
        }

//        reset node at end
//        node.updateScore(Long.MAX_VALUE, null);

    }

    private LinkedHashSet<Node> markPath(Node endNode, List<char[]> mazeMap) {

        HashSet<Node> nodeSet = new HashSet<>();
        LinkedHashSet<Node> pathSet = new LinkedHashSet<>();

        nodeSet.add(endNode);

        loop:
        while(!nodeSet.isEmpty()) {

            HashSet<Node> newNodes = new HashSet<>();

            for(Node node: nodeSet) {

                mazeMap.get(node.y)[node.x] = MARK;

                if(node.equals(startNode)) {
                    break loop;
                }

                newNodes.addAll(node.prevNodes);
                pathSet.addAll(node.prevNodes);
            }

            nodeSet = newNodes;

        }

        return pathSet;

    }

    public long countSeats(List<char[]> mazeMap) {

        long count = 0;

        for(char[] chars: mazeMap) {
            for (char c: chars) {

                if(c == MARK) {
                    count++;
                }

            }
        }

        return count;

    }

}

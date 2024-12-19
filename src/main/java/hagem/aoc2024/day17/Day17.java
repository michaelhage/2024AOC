package hagem.aoc2024.day17;

import hagem.utils.Reader;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Day17 {

    class Program {

        long a;
        long b;
        long c;

        List<Integer> instructionList;

        private int instructionPointer;

        private List<Integer> output;

        public Program(long a, long b, long c, List<Integer> instructionList) {

            this.a = a;
            this.b = b;
            this.c = c;

            this.instructionList = instructionList;

            this.instructionPointer = 0;

            this.output = new ArrayList<>();

        }

        private long getOperand(boolean isLiteral) {

            int operand = instructionList.get(instructionPointer++);

            if(isLiteral) {
                return operand;
            }

            return switch(operand) {
                case 0, 1, 2, 3 -> operand;
                case 4 -> a;
                case 5 -> b;
                case 6 -> c;
                default -> -1;
            };
        }

        public void evaluateProgramP1() {

            while(instructionPointer < instructionList.size()) {

                int opCode = instructionList.get(instructionPointer++);

                switch(opCode) {

                    case 0:
                        instruction0(getOperand(false));
                        break;
                    case 1:
                        instruction1(getOperand(true));
                        break;
                    case 2:
                        instruction2(getOperand(false));
                        break;
                    case 3:
                        instruction3(getOperand(true));
                        break;
                    case 4:
                        getOperand(true);
                        instruction4();
                        break;
                    case 5:
                        instruction5(getOperand(false));
                        break;
                    case 6:
                        instruction6(getOperand(false));
                        break;
                    case 7:
                        instruction7(getOperand(false));
                        break;
                }

            }
        }

        public void evaluateProgramP2() {

            while(instructionPointer < instructionList.size()) {

                int opCode = instructionList.get(instructionPointer++);

                switch(opCode) {

                    case 0:
                        instruction0(getOperand(false));
                        break;
                    case 1:
                        instruction1(getOperand(true));
                        break;
                    case 2:
                        instruction2(getOperand(false));
                        break;
                    case 3:
                        instruction3(getOperand(true));
                        break;
                    case 4:
                        getOperand(true);
                        instruction4();
                        break;
                    case 5:
                        instruction5(getOperand(false));
                        break;
                    case 6:
                        instruction6(getOperand(false));
                        break;
                    case 7:
                        instruction7(getOperand(false));
                        break;
                }

            }
//            return true;
        }

        private void instruction0(long operand) {
            a /= (int) Math.pow(2, operand);
        }

        private void instruction1(long operand) {
            b ^= operand;
        }

        private void instruction2(long operand) {
            b = Math.floorMod(operand, 8);
        }

        private void instruction3(long operand) {

            if(a != 0) {
                instructionPointer = (int) operand;
            }
        }

        private void instruction4() {
            b ^= c;
        }

        private void instruction5(long operand) {
            output.add( Math.floorMod(operand, 8) );
        }

        private void instruction6(long operand) {
            b = (long) (a / Math.pow(2, operand));
        }

        private void instruction7(long operand) {
            c = a / (long) Math.pow(2, operand);
        }

        public String getOutput() {
            return StringUtils.join(output, ",");
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Program program)) return false;
            return a == program.a && b == program.b && c == program.c && instructionPointer == program.instructionPointer;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, c, instructionPointer);
        }
    }

    File file = new File(Objects.requireNonNull(getClass().getResource("/Day17.data.txt")).getFile());

    private Program parseData(List<String> data) {

        int a = Integer.parseInt(data.get(0).split(":")[1].trim() );
        int b = Integer.parseInt(data.get(1).split(":")[1].trim() );
        int c = Integer.parseInt(data.get(2).split(":")[1].trim() );

        List<Integer> instructionList =
                Arrays.stream(data.get(4).split(":")[1].trim().split(",")).map(Integer::parseInt).toList();

        return new Program(a, b, c, instructionList);
    }

    private HashMap<Program, List<Integer>> evaluatedMap = new HashMap<>();

    public String run() {

        Program program = parseData(Reader.readFile(file));

        program.evaluateProgramP1();;
        String answerP1 = program.getOutput();

        evaluatedMap.put( program, program.output);

        long answerP2 = findProgramOutput(program);

        return "Part 1: " + answerP1 + ", Part 2: " + answerP2;

    }

    private boolean checkProgramOutput(List<Integer> checkList, List<Integer> targetList) {

        if(checkList.size() != targetList.size()) {
            return false;
        }

        for(int i = 0; i < checkList.size(); i++) {

            if(!Objects.equals(checkList.get(i), targetList.get(i))) {
                return false;
            }

        }

        return true;
    }

    private boolean checkIfPartialMatch(List<Integer> checkList, List<Integer> targetList) {

        int difference = targetList.size() - checkList.size();

        for(int i = 0; i < checkList.size(); i++) {

            int a = checkList.get(i);
            int b = targetList.get(difference + i);

            if(a != b) {
                return false;
            }

        }

        return true;

    }


    private long findProgramOutput(Program originalProgram) {

        File outputFile = Paths.get(file.getParent(), "Day17.output.txt").toFile();

        List<Long> evaluateList = new ArrayList<>();
        List<Long> nextList = new ArrayList<>();

        evaluateList.add(0L);

        while(!evaluateList.isEmpty()) {

            nextList = new ArrayList<>();

            for(Long value: evaluateList) {

                if(value != 0L)
                    value = value << 3;

                for (int i = 0; i < 8; i++) {

                    Program program = new Program(value + i, 0, 0, originalProgram.instructionList);

                    program.evaluateProgramP2();

                    if( checkIfPartialMatch(program.output, originalProgram.instructionList) ) {

                        nextList.add(value + i);

                        System.out.println("Value: " + (value + i) +
                                ",\t Binary: " + String.format("%10s", Long.toBinaryString(value + i)).replace(' ', '0') +
                                ",\t Output: " + program.getOutput());

                    }

                    if (checkProgramOutput(program.output, originalProgram.instructionList)) {
                        return value + i;
                    }
                }
            }

            evaluateList = nextList;
        }

        return -1;
    }

}

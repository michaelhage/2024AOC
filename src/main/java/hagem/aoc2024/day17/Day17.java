package hagem.aoc2024.day17;

import hagem.utils.Reader;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day17 {

    class Program {

        int a;
        int b;
        int c;

        List<Integer> instructionList;

        private int instructionPointer;

        private List<Integer> output;

        public Program(int a, int b, int c, List<Integer> instructionList) {

            this.a = a;
            this.b = b;
            this.c = c;

            this.instructionList = instructionList;

            this.instructionPointer = 0;

            this.output = new ArrayList<>();

        }

        private int getOperand(boolean isLiteral) {

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

        public void evaluateProgram() {

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

        private void instruction0(int operand) {
            a /= (int) Math.pow(2, operand);
        }

        private void instruction1(int operand) {
            b ^= operand;
        }

        private void instruction2(int operand) {
            b = Math.floorMod(operand, 8);
        }

        private void instruction3(int operand) {

            if(a != 0) {
                instructionPointer = operand;
            }
        }

        private void instruction4() {
            b ^= c;
        }

        private void instruction5(int operand) {
            output.add( Math.floorMod(operand, 8) );
        }

        private void instruction6(int operand) {
            b = a / (int) Math.pow(2, operand);
        }

        private void instruction7(int operand) {
            c = a / (int) Math.pow(2, operand);
        }

        public String getOutput() {
            return StringUtils.join(output, ",");
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


    public String run() {

        Program program = parseData(Reader.readFile(file));

        program.evaluateProgram();;
        String answerP1 = program.getOutput();


        return "Part 1: " + answerP1;

    }
}

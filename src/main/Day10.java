package main;

public class Day10 {
    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        String input = fileReader.readLines("main/day10.input");
        testPart2(input);
    }

    private static void testPart2(String input) {
        char[][] answer = computeAnswer(input);
        Utils.log("Answer:");
        Utils.log("\n" + Utils.stringify(answer));
    }

    private static char[][] computeAnswer(String input) {
        // 40 wide, 6 high
        char[][] answer = new char[6][40];
        int cmdIdx = 0;
        int register = 1;
        boolean doCmd = true;
        int updateAt = -1;
        int toUpdateValue = -1;
        int cycle = 1;
        String[] commands = parseCommands(input);
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 40; col++) {
                if (cycle == updateAt) {
                    register = toUpdateValue;
                }
                answer[row][col] = isVisible(col, register) ? '#' : '.';
                if (doCmd) {
                    String cmd = commands[cmdIdx];
                    String[] parts = cmd.split(" ");
                    switch (parts[0]) {
                        case "noop":
                            cmdIdx++;
                            break;
                        case "addx":
                            toUpdateValue = register + Integer.parseInt(parts[1]);
                            updateAt = cycle + 2;
                            doCmd = false;
                            break;
                        default:
                            //do nothing..
                    }
                } else {
                    doCmd = true;
                    cmdIdx++;
                }
                cycle++;
            }
        }
        return answer;
    }

    private static boolean isVisible(int col, int register) {
        return col == register || col == register - 1 | col == register + 1;
    }

    private static void testPart1(String input) {
        int result = computeResult(input);
        Utils.log("Result is: " + result);
    }

    private static int computeResult(String input) {
        String[] commands = parseCommands(input);
        return computeAnswer(commands);
    }

    private static int computeAnswer(String[] commands) {
        int cycle = 1;
        int register = 1;
        int answer = 0;
        int cycleOfInterest = 20;
        for (String command : commands) {
            if (cycle == cycleOfInterest) {
                answer += register * cycleOfInterest;
                cycleOfInterest += 40;
            }
            String[] parts = command.split(" ");
            switch (parts[0]) {
                case "noop":
                    cycle++;
                    // do nothing...
                    break;
                case "addx":
                    cycle += 2;
                    if (cycle > cycleOfInterest) {
                        answer += register * cycleOfInterest;
                        cycleOfInterest += 40;
                    }
                    register += Integer.parseInt(parts[1]);
                    break;
            }
        }
        return answer;
    }

    private static String[] parseCommands(String input) {
        return input.split("\n");
    }
}

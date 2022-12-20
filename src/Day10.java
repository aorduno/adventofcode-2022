public class Day10 {
    public static void main(String[] args) {
        testPart2("noop\n" +
                "noop\n" +
                "noop\n" +
                "addx 6\n" +
                "addx -1\n" +
                "addx 5\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "addx 5\n" +
                "addx -8\n" +
                "addx 9\n" +
                "addx 3\n" +
                "addx 2\n" +
                "addx 4\n" +
                "addx 3\n" +
                "noop\n" +
                "addx 2\n" +
                "noop\n" +
                "addx 1\n" +
                "addx 6\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "addx -39\n" +
                "noop\n" +
                "addx 5\n" +
                "addx 2\n" +
                "addx -2\n" +
                "addx 3\n" +
                "addx 2\n" +
                "addx 5\n" +
                "addx 2\n" +
                "addx 2\n" +
                "addx 13\n" +
                "addx -12\n" +
                "noop\n" +
                "addx 7\n" +
                "noop\n" +
                "addx 2\n" +
                "addx 3\n" +
                "noop\n" +
                "addx -25\n" +
                "addx 30\n" +
                "addx -10\n" +
                "addx 13\n" +
                "addx -40\n" +
                "noop\n" +
                "addx 5\n" +
                "addx 2\n" +
                "addx 3\n" +
                "noop\n" +
                "addx 2\n" +
                "addx 3\n" +
                "addx -2\n" +
                "addx 3\n" +
                "addx -1\n" +
                "addx 7\n" +
                "noop\n" +
                "noop\n" +
                "addx 5\n" +
                "addx -1\n" +
                "addx 6\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "addx 9\n" +
                "noop\n" +
                "addx -1\n" +
                "noop\n" +
                "addx -39\n" +
                "addx 2\n" +
                "addx 33\n" +
                "addx -29\n" +
                "addx 1\n" +
                "noop\n" +
                "addx 4\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "addx 3\n" +
                "addx 2\n" +
                "noop\n" +
                "addx 3\n" +
                "noop\n" +
                "noop\n" +
                "addx 7\n" +
                "addx 2\n" +
                "addx 3\n" +
                "addx -2\n" +
                "noop\n" +
                "addx -30\n" +
                "noop\n" +
                "addx 40\n" +
                "addx -2\n" +
                "addx -38\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "addx 5\n" +
                "addx 5\n" +
                "addx 2\n" +
                "addx -9\n" +
                "addx 5\n" +
                "addx 7\n" +
                "addx 2\n" +
                "addx 5\n" +
                "addx -18\n" +
                "addx 28\n" +
                "addx -7\n" +
                "addx 2\n" +
                "addx 5\n" +
                "addx -28\n" +
                "addx 34\n" +
                "addx -3\n" +
                "noop\n" +
                "addx 3\n" +
                "addx -38\n" +
                "addx 10\n" +
                "addx -3\n" +
                "addx 29\n" +
                "addx -28\n" +
                "addx 2\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "addx 5\n" +
                "noop\n" +
                "addx 3\n" +
                "addx 2\n" +
                "addx 7\n" +
                "noop\n" +
                "addx -2\n" +
                "addx 5\n" +
                "addx 2\n" +
                "noop\n" +
                "addx 1\n" +
                "addx 5\n" +
                "noop\n" +
                "noop\n" +
                "addx -25\n" +
                "noop\n" +
                "noop");
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

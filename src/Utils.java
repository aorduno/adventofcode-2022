import java.util.List;

public class Utils {
    public static void log(String msg) {
        System.out.println("logger:: " + msg);
    }

    private static String stringify(List<Integer> elements) {
        StringBuilder sb = new StringBuilder();
        for (int integer : elements) {
            sb.append(integer).append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String stringify(char[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                sb.append(grid[row][col]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

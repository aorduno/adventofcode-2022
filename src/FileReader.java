import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {
    public FileReader() {

    }

    String readLines(String filePath) {
        Path path = null;
        try {
            path = Paths.get(getClass().getClassLoader()
                    .getResource(filePath).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            Stream<String> lines = Files.lines(path);
            String data = lines.collect(Collectors.joining("\n"));
            lines.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}

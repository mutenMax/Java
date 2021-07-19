import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.io.IOUtils;


public class DirectoryService {
    private static final File CURRENT_DIRECTORY = new File(System.getProperty("user.dir"));
    private static final File TEMP_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));

    public boolean createDirectory(File dirPath, String dirName) {
        File newDirectory = new File(dirPath, dirName);
        if (!newDirectory.exists())
            return newDirectory.mkdir();
        return false;
    }

    public boolean createDirectory(File dir) {
        if (!dir.exists())
            return dir.mkdir();
        return false;
    }

    public Optional<String> readFile (String fileName) {
        String result = null;
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(result);
    }

    public void writeToFile(String content, String fileName) {
        Path path = Paths.get(fileName);
        byte[] strToBytes = content.getBytes();
        try {
            Files.write(path, strToBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

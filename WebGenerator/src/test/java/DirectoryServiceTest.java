import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class DirectoryServiceTest {

    private static final File TEMP_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));

    @Test
    public void testWriteToFileWhenSuccess() throws IOException{
        DirectoryService directoryService = new DirectoryService();
        String filePath = TEMP_DIRECTORY.getPath() +  "test.txt";
        directoryService.writeToFile("Hello", filePath);
        String read = Files.readAllLines(Paths.get(filePath)).get(0);
        Assertions.assertEquals("Hello", read);
    }
}

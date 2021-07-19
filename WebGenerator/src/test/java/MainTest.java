
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;


public class MainTest {
    private static final File CURRENT_DIRECTORY = new File(System.getProperty("user.dir"));
    private static final File SITENAME_DIRECTORY = new File(System.getProperty("user.dir"), "siteName");

    private ArrayList<String> expected;
    //private String[] expected;

    @BeforeEach
    public void beforeEach() {
        // Delete child Dir first
        File jsNameDir = new File(SITENAME_DIRECTORY, "js");
        jsNameDir.delete();

        File cssNameDir = new File(SITENAME_DIRECTORY, "css");
        cssNameDir.delete();

        File indexFile = new File(SITENAME_DIRECTORY, "index.html");
        indexFile.delete();

        File siteNameDir = new File(CURRENT_DIRECTORY, "siteName");
        siteNameDir.delete();


        expected = new ArrayList<String>(
                Arrays.asList("Please enter Sitename:",
                    "Please enter Author:",
                    "Do you want a folder for JavaScript ?",
                    "Do you want a folder for CSS ?",
                    "Site name: siteName",
                    "Author: Author",
                    "Do you want a folder for JavaScript? : Y",
                    "Do you want a folder for CSS? : Y"));
    }

    @Test
    public void testWhenAllDirectoriesCreated() {
        String userInput = String.format("siteName%sAuthor%sY%sY",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        expected.add("Created ./siteName");
        expected.add("Created ./siteName/index.html");
        expected.add("Created ./siteName/js");
        expected.add("Created ./siteName/css");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        Main.main(null); // call the main method

        String[] actual = baos.toString().split(System.lineSeparator());

        Assertions.assertArrayEquals(expected.toArray(), actual);
    }

    @Test
    public void testWhenNoJsNoCSS() {
        String userInput = String.format("siteName%sAuthor%sN%sN",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        expected.set(6, "Do you want a folder for JavaScript? : N");
        expected.set(7, "Do you want a folder for CSS? : N");
        expected.add("Created ./siteName");
        expected.add("Created ./siteName/index.html");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        Main.main(null); // call the main method

        String[] actual = baos.toString().split(System.lineSeparator());

        Assertions.assertArrayEquals(expected.toArray(), actual);
    }

    @Test
    public void testWhenNoJsYesCSS() {
        String userInput = String.format("siteName%sAuthor%sN%sY",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        expected.set(6, "Do you want a folder for JavaScript? : N");
        expected.set(7, "Do you want a folder for CSS? : Y");
        expected.add("Created ./siteName");
        expected.add("Created ./siteName/index.html");
        expected.add("Created ./siteName/css");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        Main.main(null); // call the main method

        String[] actual = baos.toString().split(System.lineSeparator());

        Assertions.assertArrayEquals(expected.toArray(), actual);
    }

    @Test
    public void testWhenYesJsNoCSS() {
        String userInput = String.format("siteName%sAuthor%sY%sN",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        expected.set(6, "Do you want a folder for JavaScript? : Y");
        expected.set(7, "Do you want a folder for CSS? : N");
        expected.add("Created ./siteName");
        expected.add("Created ./siteName/index.html");
        expected.add("Created ./siteName/js");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        Main.main(null); // call the main method

        String[] actual = baos.toString().split(System.lineSeparator());

        Assertions.assertArrayEquals(expected.toArray(), actual);
    }

}

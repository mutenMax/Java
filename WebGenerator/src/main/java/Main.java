import java.io.File;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static File siteNamePath;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter Sitename:");
        String siteName = scanner.nextLine();
        while (!(siteName.matches("^[a-zA-Z0-9-_]+$"))) {
            System.out.println("Please enter valid charcters:");
            siteName = scanner.nextLine();
        }

        System.out.println("Please enter Author:");
        String author = scanner.nextLine();

        System.out.println("Do you want a folder for JavaScript ?");
        String wantJavascript = scanner.nextLine();
        while (!(wantJavascript.matches("[yYNn]"))) {
            System.out.println("Please enter Y or N:");
            wantJavascript = scanner.nextLine();
        }

        System.out.println("Do you want a folder for CSS ?");
        String wantCSS = scanner.nextLine();
        while (!(wantCSS.matches("[yYNn]"))) {
            System.out.println("Please enter y or n:");
            wantCSS = scanner.nextLine();
        }


        final File CURRENT_DIRECTORY = new File(System.getProperty("user.dir"));
        System.out.println("Site name: " + siteName);
        System.out.println("Author: " + author);
        System.out.println("Do you want a folder for JavaScript? : " + wantJavascript);
        System.out.println("Do you want a folder for CSS? : " + wantCSS);
        DirectoryService directoryService = new DirectoryService();

        siteNamePath = new File(System.getProperty("user.dir"), siteName);

        if (directoryService.createDirectory(siteNamePath)) {
            System.out.println("Created ./" + siteName);
            Optional<String> content = directoryService.readFile("index.html");
            if (content.isPresent()) {
                String contentStr = content.get();
                contentStr = contentStr.replace("[[siteName]]", siteName);
                contentStr = contentStr.replace("[[author]]", author);
                directoryService.writeToFile(contentStr, siteNamePath.getPath() + "/index.html");
                System.out.println("Created ./" + siteName + "/index.html");
            }
        }

        if (wantJavascript.equalsIgnoreCase("y") && directoryService.createDirectory(siteNamePath, "js"))
            System.out.println("Created ./" + siteName + "/js");
        if (wantCSS.equalsIgnoreCase("y") && directoryService.createDirectory(siteNamePath, "css"))
            System.out.println("Created ./" + siteName + "/css");
    }


}
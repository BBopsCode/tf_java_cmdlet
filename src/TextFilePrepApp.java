import java.awt.Desktop;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;

public class TextFilePrepApp {
    private static Set<String> processedFiles = new HashSet<>();
    private static final String OUTPUT_DIRECTORY;
    static {
        try {
            OUTPUT_DIRECTORY = new File(TextFilePrepApp.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String OUTPUT_FILE = OUTPUT_DIRECTORY + "\\LLM_text_prep.txt";

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String command = args[0];
        try {
            switch (command) {
                //Add command
                case "-a":
                    handleAddCommand(args);
                    break;
                //New file/ Clear File Command
                case "-n":
                    handleNewCommand();
                    break;
                //Open File command
                case "-o":
                    handleOpenCommand();
                    break;
                default:
                    System.err.println("Invalid command: " + command);
                    printUsage();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    //Determine what switch/options for the add command
    private static void handleAddCommand(String[] args) {
        if (args.length < 2) {
            System.err.println("Missing argument for -a command.");
            printUsage();
            return;
        }

        if (args[1].equals("--all")) {
            if (args.length < 3) {
                System.err.println("Missing directory name for -a --all command.");
                printUsage();
                return;
            }
            String directoryName = args[2];
            processDirectory(directoryName);
        } else {
            String fileName = args[1];
            processFile(fileName);
        }
    }

    //This funciton takes all files from a directory and adds them to a text file.
    private static void processDirectory(String directoryName) {
        Path dirPath = Paths.get(directoryName).toAbsolutePath();
        if (!Files.isDirectory(dirPath)) {
            System.err.println("Directory not found: " + directoryName);
            return;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    if (isOutputFile(entry)) {
                        continue;
                    }
                    processFile(entry.toString());
                }
            }
        } catch (IOException e) {
            System.err.println("Error accessing directory " + directoryName + ": " + e.getMessage());
        }
    }
    //Creates a new TXT file
    private static void handleNewCommand() {
        try {
            File outputDir = new File(OUTPUT_DIRECTORY);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            File outputFile = new File(OUTPUT_FILE);
            if (outputFile.exists()) {
                new FileOutputStream(outputFile).close();
            } else {
                outputFile.createNewFile();
            }
            processedFiles.clear();
            System.out.println("New output file created: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error creating new output file: " + e.getMessage());
        }
    }
    //Prevent Duplication Error.
    private static boolean isOutputFile(Path file) {
      return file.toAbsolutePath().toString().contains("LLM_Text");
    }
    //Function to open the text file you saved to
    private static void handleOpenCommand() {
        File outputFile = new File(OUTPUT_FILE);
        if (!outputFile.exists()) {
            System.err.println("Output file does not exist.");
            return;
        }

        try {
            Desktop.getDesktop().open(outputFile);
        } catch (IOException e) {
            System.err.println("Failed to open output file: " + e.getMessage());
        } catch (UnsupportedOperationException e) {
            System.err.println("Opening files is not supported on this platform.");
        }
    }
    //This function is used t send the file contents to a txt
    private static void processFile(String fileName) {
        Path filePath = Paths.get(fileName).toAbsolutePath();
        if (processedFiles.contains(filePath.toString())) {
            return;
        }

        if (!Files.exists(filePath)) {
            System.err.println("File not found: " + fileName);
            return;
        }

        if (!Files.isRegularFile(filePath)) {
            System.err.println("Not a regular file: " + fileName);
            return;
        }
        //
        if (isOutputFile(filePath)) {
            return;
        }

        try {
            String content = new String(Files.readAllBytes(filePath));
            appendToOutputFile(fileName, content);
            processedFiles.add(filePath.toString());
            System.out.println("File added: " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading file " + fileName + ": " + e.getMessage());
        }
    }
    //Content of files sent via buffer writer
    private static void appendToOutputFile(String fileName, String content) {
        try (FileWriter fw = new FileWriter(OUTPUT_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            // Create the banner for the file name
            String banner = "--------------------[" + fileName + "]--------------------";
            bw.write(banner);
            bw.newLine();
            bw.write(content);
            bw.newLine();
            bw.newLine(); // Add a blank line for separation
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
        }
    }

    private static void printUsage() {
        String usage = "Usage:\n" +
                "tf\n" +
                "    Display this usage message\n" +
                "tf -a [fileName]\n" +
                "    Add contents of fileName to output file\n" +
                "tf -a --all [directoryName]\n" +
                "    Add all regular files from directoryName (excluding output file)\n" +
                "tf -n\n" +
                "    Start a new output file\n" +
                "tf -o\n" +
                "    Open the output file with default editor\n";
        System.out.println(usage);
        System.out.println(OUTPUT_FILE);
    }
}

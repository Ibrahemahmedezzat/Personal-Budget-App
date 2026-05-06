package app.data;

import java.io.*;
import java.util.*;

/**
 * Utility class responsible for handling basic file-based data storage operations.
 * It provides methods for saving, reading, and overwriting data in text files
 * located in the application's data directory.
 */
public class FileDatabase {

    /**
     * Builds the full file path for a given filename inside the data directory.
     *
     * @param file the name of the file
     * @return the full path to the file
     */
    private static String path(String file){
        return "data/" + file;
    }

    /**
     * Appends a new line of data to the specified file.
     *
     * @param file the file name
     * @param data the data to be saved
     */
    public static void save(String file, String data){
        try (FileWriter fw = new FileWriter(path(file), true)) {
            fw.write(data + "\n");
        } catch (Exception e) {
            System.out.println("Save Error");
        }
    }

    /**
     * Reads all data from a file and returns it as a list of string arrays.
     * Each line is split by comma to separate fields.
     *
     * @param file the file name
     * @return a list of string arrays representing file rows
     */
    public static List<String[]> read(String file){
        List<String[]> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path(file)))) {
            String line;
            while((line = br.readLine()) != null){
                list.add(line.split(","));
            }
        } catch (Exception e) {}

        return list;
    }

    /**
     * Overwrites the entire content of a file with the provided list of lines.
     *
     * @param file the file name
     * @param lines the new content to write into the file
     */
    public static void overwrite(String file, List<String> lines){
        try (FileWriter fw = new FileWriter(path(file), false)) {
            for(String l : lines){
                fw.write(l + "\n");
            }
        } catch (Exception e) {
            System.out.println("Overwrite Error");
        }
    }
}
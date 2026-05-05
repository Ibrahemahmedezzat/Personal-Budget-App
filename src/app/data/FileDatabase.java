package app.data;

import java.io.*;
import java.util.*;

public class FileDatabase {

    private static String path(String file){
        return "data/" + file;
    }

    public static void save(String file, String data){
        try (FileWriter fw = new FileWriter(path(file), true)) {
            fw.write(data + "\n");
        } catch (Exception e) {
            System.out.println("Save Error");
        }
    }

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
package app.data;

import java.io.*;
import java.util.*;

public class FileDatabase {

    public static void save(String file, String data) {

        try {
            FileWriter fw = new FileWriter("data/" + file, true);
            fw.write(data + "\n");
            fw.close();
        } catch (Exception e) {
        }
    }

    public static void overwrite(String file, List<String> lines) {

        try {
            FileWriter fw = new FileWriter("data/" + file, false); // overwrite mode

            for (String l : lines) {
                fw.write(l + "\n");
            }

            fw.close();

        } catch (Exception e) {
            System.out.println("Error writing file");
        }
    }

    public static List<String[]> read(String file) {

        List<String[]> list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("data/" + file));

            String line;

            while ((line = br.readLine()) != null) {
                list.add(line.split(","));
            }

            br.close();
        } catch (Exception e) {
        }

        return list;
    }
}
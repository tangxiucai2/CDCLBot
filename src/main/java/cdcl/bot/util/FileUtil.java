package cdcl.bot.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileUtil {


    public static String readFileLinesWithoutArray(File file) throws IOException {
        Scanner s = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while (s.hasNext()) {
            sb.append("\n");
            sb.append(s.next());
        }
        s.close();

        return sb.toString();
    }


    public static void writeStringToFile(File file, String string, boolean append) throws IOException {
        FileWriter writer = new FileWriter(file, append);
        writer.write(string);
        writer.flush();
        writer.close();
    }

}

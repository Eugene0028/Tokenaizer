package io.shift;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class ArgsChecker
{
    public static boolean O_FLAG;
    public static boolean P_FLAG;
    public static boolean A_FLAG;
    public static boolean S_FLAG;
    public static boolean F_FLAG;
    public static String prefix = "";
    public static String path = "";

    public static List<CustomFileReader> processArgs(String[] args) throws Exception
    {

        List<CustomFileReader> fileList = new LinkedList<>();

        for (int i = 0; i < args.length; i++){
            switch (args[i].toLowerCase()){
                case "-o": {
                    if (i == args.length - 1 || !Files.isDirectory(Path.of(args[i + 1]))) {
                        throw new Exception("Problem with -o flag. Enter the directory!");
                    }
                    O_FLAG = true;
                    path = args[++i];
                    break;
                }
                case "-a": A_FLAG = true; break;
                case "-s": S_FLAG = true; break;
                case "-f": F_FLAG = true; break;
                case "-p":
                {
                    P_FLAG = true;
                    if (i == args.length - 1 || Files.exists(Path.of(args[i + 1]))){
                        throw new Exception("Problem with -p flag. The prefix must not be the same as the file name!");
                    }
                    prefix = args[++i];
                    break;
                }
                default:{
                    File file = new File(args[i]);
                    if (!file.exists()){
                        throw new Exception("Incorrect filename!");
                    }
                    fileList.add(new CustomFileReader(file));
                }
            }
        }
        return fileList;
    }
}

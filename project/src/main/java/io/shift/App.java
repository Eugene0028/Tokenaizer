package io.shift;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class App
{
    public static void main(String[] args)
    {
        if (args.length == 0) {
            System.out.println("Error. Please, add more args");
            return;
        }

        try {
            List<CustomFileReader> fileList = ArgsChecker.processArgs(args);
            if (fileList.isEmpty()) {
                System.out.println("Error. Please add filename to args");
                return;
            }

            long startTime = System.currentTimeMillis();
            fileList.forEach(Thread::start);
            for (var thread : fileList){
                thread.join();
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Success!");
            System.out.println("Time taken: " + (float) (endTime - startTime) / 1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (CustomFileReader.checkWriters()) {
                if (ArgsChecker.S_FLAG || ArgsChecker.F_FLAG){
                    Arrays.stream(CustomFileReader.getAllStatistic())
                            .filter(Objects::nonNull)
                            .forEach(System.out::println);
                }
                CustomFileReader.closeWriters();
            }
        }
    }
}

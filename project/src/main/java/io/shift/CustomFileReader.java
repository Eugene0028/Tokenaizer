package io.shift;

import io.shift.statistic.DigitStatistic;
import io.shift.statistic.FloatStatistic;
import io.shift.statistic.Statistic;
import io.shift.statistic.StringStatistic;

import java.io.*;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class CustomFileReader extends Thread {
    private final File inputFile;
    private static final Triple<BufferedWriter, Object, Statistic<?>>[] work = new Triple[3];
    private final Pattern INTEGER_PATTERN = Pattern.compile("^-?\\d+$");
    private final Pattern FLOAT_PATTERN = Pattern.compile("^-?\\d*\\.\\d+([eE][-+]?\\d+)?$");

    public CustomFileReader(File inputFile) {
        work[0] = new Triple<>(null, new Object(), null);
        work[1] = new Triple<>(null, new Object(), null);
        work[2] = new Triple<>(null, new Object(), null);
        this.inputFile = inputFile;
    }

    public static Statistic<?>[] getAllStatistic(){
        return new Statistic[]{work[0].getThird(), work[1].getThird(), work[2].getThird()};
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            while (br.ready()) {
                String line = br.readLine();
                if (INTEGER_PATTERN.matcher(line).matches()) {
                    if (work[0].getThird() == null){
                        work[0].setThird(new DigitStatistic());
                    }
                    processLine(line, 0, "integers.txt");
                } else if (FLOAT_PATTERN.matcher(line).matches()) {
                    if (work[1].getThird() == null){
                        work[1].setThird(new FloatStatistic());
                    }
                    processLine(line, 1, "floats.txt");
                } else if (!line.isEmpty()) {
                    if (work[2].getThird() == null){
                        work[2].setThird(new StringStatistic());
                    }
                    processLine(line, 2, "strings.txt");
                }
            }
        } catch (Exception e) {
            System.out.println(this);
            System.out.println(e.getMessage());
        }
    }

    public void processStatistic(String line, int index){
        if (ArgsChecker.F_FLAG){
            work[index].getThird().process(line);
        }
        else if (ArgsChecker.S_FLAG){
            work[index].getThird().smallProcess();
        }
    }

    public static void closeWriters()
    {
        for (int i = 0; i < 3; i++) {
            synchronized (work[i].getSecond()) {
                if (work[i].getFirst() != null) {
                    try {
                        work[i].getFirst().close();
                        work[i].setFirst(null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static boolean checkWriters(){
        return work[0] != null && work[1] != null && work[2] != null;
    }

    private FileWriter getFileWriter(String fileName) throws IOException {
        File file = Paths.get(ArgsChecker.path ,ArgsChecker.prefix + fileName).toFile();
        return new FileWriter(file, ArgsChecker.A_FLAG);
    }

    private void processLine(String line, int index, String fileName) throws IOException {
        synchronized (work[index].getSecond()) {
            if (work[index].getFirst() == null) {
                work[index].setFirst(new BufferedWriter(getFileWriter(fileName)));
            }
            writeContent(work[index].getFirst(), line);
            processStatistic(line, index);
        }
    }

    private void writeContent(BufferedWriter bufferedWriter, String content) throws IOException {
        bufferedWriter.write(content);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    @Override
    public String toString() {
        return "CustomFileReader{" +
                "inputFile=" + inputFile +
                ", INTEGER_PATTERN=" + INTEGER_PATTERN +
                ", FLOAT_PATTERN=" + FLOAT_PATTERN +
                '}';
    }
}

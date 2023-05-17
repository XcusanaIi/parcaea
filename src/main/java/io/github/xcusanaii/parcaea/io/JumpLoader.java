package io.github.xcusanaii.parcaea.io;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.Jump;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JumpLoader {

    private static String path = "";

    public static void reloadJump() {
        path = Parcaea.FILE_PATH + "/jumps";
        Jump.jumps.clear();
        File jumpDir = new File(path);
        if (!jumpDir.exists()) {
            jumpDir.mkdirs();
            return;
        }
        List<File> files = getFiles();
        for (File file: files) {
            if (file.getName().endsWith(".txt")) {
                try {
                    BufferedReader reader = Files.newBufferedReader(file.toPath());
                    String line;
                    ArrayList<ArrayList<Number>> ticks = new ArrayList<ArrayList<Number>>();
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        Scanner scanner = new Scanner(line);
                        int times = 1;
                        if (scanner.hasNext()) {
                            String str = scanner.next();
                            if(str.matches("^[+-]?\\d+$")) {
                                times = Integer.parseInt(str);
                                if (times < 1) times = 1;
                            }
                        }
                        ArrayList<Number> tick = new ArrayList<Number>();
                        for (int i = 0; i < 7 && scanner.hasNext(); i++) {
                            String str = scanner.next();
                            if(str.matches("^[+-]?\\d+$")) {
                                tick.add(Integer.parseInt(str));
                            }
                        }
                        if (scanner.hasNext()){
                            String str = scanner.next();
                            if(str.matches("^-?\\d+(\\.\\d+)?$")) {
                                tick.add(Double.parseDouble(str));
                            }
                        }
                        if(tick.size() == 8) {
                            for (int i = 0; i < times; i++) {
                                ticks.add(tick);
                            }
                        }
                    }
                    String fileName = file.getName();
                    int lastIndexOfDot = fileName.lastIndexOf('.');
                    if (lastIndexOfDot != -1) {
                        fileName = fileName.substring(0, lastIndexOfDot);
                    }
                    if (ticks.size() != 0) {
                        Jump jump = new Jump(fileName, ticks);
                        Jump.jumps.add(jump);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<File> getFiles() {
        List<File> files = new ArrayList<File>();
        try {
            File directory = new File(JumpLoader.path);
            File[] fileList = directory.listFiles();
            if (fileList != null) {
                files.addAll(Arrays.asList(fileList));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    public static void printJump() {
        for (Jump jump: Jump.jumps) {
            System.out.println(jump.id);
            for (ArrayList<Number> tick: jump.ticks) {
                System.out.println(tick.toString());
            }
        }
    }
}

package io.github.xcusanaii.parcaea.io;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.Jump;
import io.github.xcusanaii.parcaea.util.string.StringUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class JumpLoader {

    public static void reloadJump() {
        String path = Parcaea.FILE_PATH + "/jumps";
        Jump.jumps.clear();
        File jumpDir = new File(path);
        if (!jumpDir.exists()) {
            if (jumpDir.mkdirs()) return;
        }
        File[] files = jumpDir.listFiles();
        if (files != null) {
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
                                if(StringUtil.isInteger(str)) {
                                    times = Integer.parseInt(str);
                                    if (times < 1) times = 1;
                                }
                            }
                            ArrayList<Number> tick = new ArrayList<Number>();
                            for (int i = 0; i < 7 && scanner.hasNext(); i++) {
                                String str = scanner.next();
                                if(StringUtil.isInteger(str)) {
                                    tick.add(Integer.parseInt(str));
                                }
                            }
                            if (scanner.hasNext()){
                                String str = scanner.next();
                                if(StringUtil.isDouble(str)) {
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

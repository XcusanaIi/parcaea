package io.github.xcusanaii.parcaea.io;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.segment.CoordStrategy;
import io.github.xcusanaii.parcaea.model.segment.Segment;
import io.github.xcusanaii.parcaea.util.FileUtil;
import io.github.xcusanaii.parcaea.util.string.StringUtil;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class SegmentLoader {

    private static String path = null;

    public static void reloadSegment() {
        path = Parcaea.FILE_PATH + "/segments";
        Segment.segments.clear();
        File segmentDir = new File(path);
        if (!segmentDir.exists()) {
            if (segmentDir.mkdirs()) return;
        }
        File[] files = segmentDir.listFiles();
        if (files != null) {
            for (File file: files) {
                if (file.getName().endsWith(".txt")) {
                    try {
                        BufferedReader reader = Files.newBufferedReader(file.toPath());
                        String line;
                        ArrayList<CoordStrategy> coords = new ArrayList<CoordStrategy>();
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            Scanner scanner = new Scanner(line);
                            double x = 0.0;
                            double y = 0.0;
                            double z = 0.0;
                            double f = 0.0;
                            String strategy = "";
                            if (scanner.hasNext()) {
                                String str = scanner.next();
                                if (StringUtil.isDouble(str)) {
                                    x = Double.parseDouble(str);
                                }
                            }
                            if (scanner.hasNext()) {
                                String str = scanner.next();
                                if (StringUtil.isDouble(str)) {
                                    y = Double.parseDouble(str);
                                }
                            }
                            if (scanner.hasNext()) {
                                String str = scanner.next();
                                if (StringUtil.isDouble(str)) {
                                    z = Double.parseDouble(str);
                                }
                            }
                            if (scanner.hasNext()) {
                                String str = scanner.next();
                                if (StringUtil.isDouble(str)) {
                                    f = Double.parseDouble(str);
                                }
                            }
                            if (scanner.hasNext()) {
                                strategy = scanner.next();
                            }
                            CoordStrategy coordStrategy = new CoordStrategy(x, y, z, f, strategy);
                            coords.add(coordStrategy);
                        }
                        String fileName = file.getName();
                        int lastIndexOfDot = fileName.lastIndexOf('.');
                        if (lastIndexOfDot != -1) {
                            fileName = fileName.substring(0, lastIndexOfDot);
                        }
                        if (coords.size() != 0) {
                            Segment segment = new Segment(fileName, coords);
                            Segment.segments.add(segment);
                        }
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void saveSegment() {
        path = Parcaea.FILE_PATH + "/segments";
        FileUtil.deleteFiles(path);
        for (Segment segment : Segment.segments) {
            File file = new File(path + "/" + segment.id + ".txt");
            try {
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                for (CoordStrategy coord : segment.coords) {
                    bw.write(coord.x + " " + coord.y + " " + coord.z + " " + coord.f + " " + coord.strategy);
                    bw.newLine();
                }
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

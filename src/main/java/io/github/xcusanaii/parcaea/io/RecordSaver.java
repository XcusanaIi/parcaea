package io.github.xcusanaii.parcaea.io;

import io.github.xcusanaii.parcaea.Parcaea;
import io.github.xcusanaii.parcaea.model.input.InputStat;
import io.github.xcusanaii.parcaea.model.input.InputTick;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordSaver {

    private static boolean isAlwaysSprint = true;

    public static boolean saveLastInput(String jumpName){
        if (InputStat.lastInput.size() <= 1) return false;
        tweakSprint();
        jumpName = jumpName.equals("") ? "record" : jumpName;
        String fileName = Parcaea.FILE_PATH + "/jumps/" + jumpName + ".txt";
        File file = new File(fileName);
        if (file.exists()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String timestamp = formatter.format(new Date());
            String newFileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_" + timestamp + fileName.substring(fileName.lastIndexOf('.'));
            file = new File(newFileName);
        }
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (InputTick inputTick : InputStat.lastInput) {
                bw.write("1 ");
                for (int i = 0; i < 6; i++) {
                    bw.write(inputTick.keyList[i] ? "1" : "0");
                    bw.write(" ");
                }
                bw.write(!isAlwaysSprint && inputTick.keyList[6] ? "1" : "0");
                bw.write(" ");
                bw.write(Parcaea.DF_2.format(inputTick.dYaw));
                bw.newLine();
            }
            bw.close();
            fw.close();
            return true;
        } catch (IOException ignored) {}
        return false;
    }

    private static void tweakSprint() {
        isAlwaysSprint = true;
        for (InputTick inputTick : InputStat.lastInput) {
            if (!inputTick.sprint()) {
                isAlwaysSprint = false;
                return;
            }
        }
    }
}

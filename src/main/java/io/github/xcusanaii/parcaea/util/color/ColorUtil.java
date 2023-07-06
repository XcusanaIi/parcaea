package io.github.xcusanaii.parcaea.util.color;

import io.github.xcusanaii.parcaea.util.string.StringUtil;

public class ColorUtil {

    public static int mapRGB(String s) {
        if (StringUtil.isInteger(s)) {
            int color = Integer.parseInt(s);
            color %= 256;
            return color < 0 ? color + 256 : color;
        }else return 0;
    }

}

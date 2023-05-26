package io.github.xcusanaii.parcaea.util.string;

public class StringUtil {

    public static String getUniqueId(String id, String[] existedId) {
        int suffix = 1;
        String uniqueId = id;
        while (true) {
            boolean contained = false;
            for (String s : existedId) {
                if (s.equals(uniqueId)) {
                    contained = true;
                    break;
                }
            }
            if (!contained) {
                return id;
            }
            uniqueId = id + "_" + suffix;
            suffix++;
        }
    }

    public static boolean matchString(String strSearch, String strCandidate ) {
        String[] words1 = strSearch.toLowerCase().split(" ");
        String[] words2 = strCandidate.toLowerCase().split(" ");
        for (String s1 : words1) {
            boolean isContain = false;
            for (String s2 : words2) {
                if (s2.contains(s1)) {
                    isContain = true;
                    break;
                }
            }
            if (!isContain) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

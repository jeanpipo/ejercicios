import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Needle {
    private static int lastPosition = 0;

    public static int count(String needle, InputStream haystack) throws Exception {
        char[] needleInChars = needle.toUpperCase().toCharArray();
        int needleSize = needle.length();
        int acu = 0;
        try (Reader reader =
                     new BufferedReader(new InputStreamReader(haystack, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                if (hasNeedle((char) c, needleInChars, needleSize)) {
                    acu++;
                }
            }
        }
        return acu;
    }

    public static boolean hasNeedle(char newChar, char[] needleInChars, int needleSize) {
        if ((newChar & 0x5f) == needleInChars[lastPosition]) {
            lastPosition++;
        } else {
            lastPosition = 0;
            return false;
        }

        if (needleSize == lastPosition) {
            lastPosition = 0;
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        String inMessage = "Hello, there!\nHow are you today?\nYes, you over there.";
        try (InputStream inStream = new ByteArrayInputStream(inMessage.getBytes())) {
            System.out.println(Needle.count("there", inStream));
        }
    }
}

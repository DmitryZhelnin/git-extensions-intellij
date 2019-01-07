package utils;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;

public class Registry {

    /**
     * Reads specified key from the location in the Windows registry
     * Method origin: https://stackoverflow.com/a/5902131/2189031
     */
    @Nullable
    public static String read(String location, String key)
    {
        try
        {
            // Run reg query, then read output with StreamReader (internal class)
            Process process = Runtime.getRuntime().exec("reg query " +
                '"'+ location + "\" /v " + key);

            InputStream inputStream = process.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();

            try {
                int c;
                while ((c = inputStream.read()) != -1) {
                    stringBuilder.append((char)c);
                }
            }
            catch (IOException e) {
                System.out.println(e.toString());
            }

            String output = stringBuilder.toString();

            // Output has the following format:
            // \n<Version information>\n\n<key>    <registry type>    <value>\r\n\r\n
            int i = output.indexOf("REG_SZ");
            if (i == -1) {
                return null;
            }

            stringBuilder = new StringBuilder();
            i += 6; // skip REG_SZ

            // skip spaces or tabs
            for (;;) {
                if (i > output.length()) {
                    break;
                }
                char c = output.charAt(i);
                if (c != ' ' && c != '\t') {
                    break;
                }
                ++i;
            }

            // take everything until end of line
            for (;;) {
                if (i > output.length()) {
                    break;
                }
                char c = output.charAt(i);
                if (c == '\r' || c == '\n') {
                    break;
                }
                stringBuilder.append(c);
                ++i;
            }

            return stringBuilder.toString();
        }
        catch (Exception e) {
            return null;
        }
    }
}

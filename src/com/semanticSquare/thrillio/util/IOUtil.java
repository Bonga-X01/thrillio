package com.semanticSquare.thrillio.util;

import java.io.*;

public class IOUtil {
    public static void read(String[] data, String fileName) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))){
            String line;
            int count = 0;
            while((line = br.readLine()) != null) {
                data[count] = line;
                count++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

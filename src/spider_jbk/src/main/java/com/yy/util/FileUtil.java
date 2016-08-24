package com.yy.util;

import java.io.*;

/**
 * @Author: Leo
 * @CreateTime: 16-8-24 上午9:11
 * @Description:
 */

public class FileUtil {

    private static final int DEFAULT_FILE_LENGTH = 256;

    public static String asString(String fileName) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder(DEFAULT_FILE_LENGTH);
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    public static void write(String fileName, String data) {
        File file = new File(fileName);
        writeDataToDisk(file, data);
    }

    public static void write(File file, String data) {
        writeDataToDisk(file, data);
    }

    private static void writeDataToDisk(File file, String data) {
        BufferedWriter bw = null;
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(data);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

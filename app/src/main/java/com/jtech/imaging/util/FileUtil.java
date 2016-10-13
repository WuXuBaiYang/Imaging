package com.jtech.imaging.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.jtech.imaging.exception.ExternalStorageReadException;
import com.jtech.imaging.exception.ExternalStorageWriteException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    /**
     * Use getFilesDir()
     *
     * @param context
     * @param fileName
     * @param byteArr
     * @throws IOException
     */
    public static File writeFileInInternalStorage(Context context, String fileName, byte[] byteArr) throws IOException {
        return writeFile(context.getFilesDir(), fileName, byteArr);
    }

    /**
     * Use getFilesDir()
     *
     * @param context
     * @param fileName
     * @param byteArr
     * @throws IOException
     */
    public static File writeFileInCacheStorage(Context context, String fileName, byte[] byteArr) throws IOException {
        return writeFile(context.getCacheDir(), fileName, byteArr);
    }

    private static File writeFile(File fileDir, String fileName, byte[] byteArr) throws IOException {
        File file = new File(fileDir, fileName);
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(byteArr);
        outputStream.close();
        return file;
    }

    public static byte[] readFileFromInternalStorage(Context context, String fileName) throws IOException {
        return readFile(context.getFilesDir(), fileName);
    }

    public static byte[] readFileFromCacheStorage(Context context, String fileName) throws IOException {
        return readFile(context.getCacheDir(), fileName);
    }

    private static byte[] readFile(File dirName, String fileName) throws IOException {
        File file = new File(dirName, fileName);
        int size = (int) file.length();
        byte byteArr[] = new byte[size];

        FileInputStream inputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                inputStream);
        bufferedInputStream.read(byteArr, 0, byteArr.length);
        bufferedInputStream.close();

        return byteArr;
    }

    public static File writeFileInExternalStorage(String fileName, byte byteArr[]) throws ExternalStorageWriteException, IOException {
        if (isExternalStorageWritable()) {
            File externalDirectory = Environment.getExternalStorageDirectory();
            File file = new File(externalDirectory + "/" + fileName);
            Log.d("ZZZ ", "ZZZ : " + file.getAbsolutePath());
            if (!file.exists()) {
                boolean b = file.createNewFile();
                Log.d("", "ZZZZ be : " + b);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(byteArr);
            fileOutputStream.close();
            return file;
        } else {
            throw new ExternalStorageWriteException(
                    "External storage is not writable!");
        }
    }

    public static byte[] readFileFromExternalStorage(String fileName) throws IOException, ExternalStorageReadException {
        byte[] byteArr;
        if (isExternalStorageReadable()) {
            File file = new File(Environment.getExternalStorageDirectory()
                    + "/" + fileName);
            int size = (int) file.length();
            byteArr = new byte[size];
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(
                    fileInputStream);
            bufferedInputStream.read(byteArr, 0, byteArr.length);
            bufferedInputStream.close();
        } else {
            throw new ExternalStorageReadException();
        }

        return byteArr;
    }

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)
                || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return true;
        } else {
            return false;
        }
    }
}
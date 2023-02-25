package com.example.demo;
import cn.hutool.core.io.IoUtil;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileCopyUtil {

    public static void main(String[] args) {
        String path = "/Users/joejoe/Downloads/3392265097/";
        String copyPath = "/Users/joejoe/Downloads/000";
        copyDir(path,copyPath);
    }

    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 复制文件夹中的所有文件和子文件夹到目标文件夹中
     *
     * @param srcDirPath  源文件夹路径
     * @param destDirPath 目标文件夹路径
     */
    public static void copyDir(String srcDirPath, String destDirPath) {
        File srcDir = new File(srcDirPath);
        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File[] files = srcDir.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        for (File file : files) {
            executorService.execute(() -> {
                try {
                    copyFile(file, new File(destDir, file.getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制文件到目标文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @throws IOException
     */
    public static void copyFile(File srcFile, File destFile) throws IOException {
        if (srcFile.isDirectory()) {
            copyDir(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
        } else {
            InputStream in = new BufferedInputStream(new FileInputStream(srcFile));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(getUniqueFile(destFile)));
            IoUtil.copy(in, out);
            in.close();
            out.close();
        }
    }

    /**
     * 为目标文件生成唯一的文件名
     *
     * @param destFile 目标文件
     * @return 唯一的文件名
     */
    private static File getUniqueFile(File destFile) {
        if (!destFile.exists()) {
            return destFile;
        }
        String fileName = destFile.getName();
        String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        int count = 1;
        while (destFile.exists()) {
            destFile = new File(destFile.getParent(), baseName + "(" + count + ")" + suffix);
            count++;
        }
        return destFile;
    }
}

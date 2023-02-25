package com.example.demo;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T2_Recursion {

    public static void main(String[] args) {
        String path = "/Users/joejoe/Downloads/3392265097/";
        String copyPath = "/Users/joejoe/Downloads/000";
        copyFile(path,copyPath);
    }

    public static void copyFile(String path,String copyPath){
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(8);
        File file = new File(path);
        File copyFile = new File(copyPath);
        File[] files = file.listFiles();
        long start = System.nanoTime();
        if(!copyFile.exists()){
            copyFile.mkdirs();
        }
        for(File file1:files){
        newFixedThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                copy(copyFile, file1);
            }
        });
        }
        long end = System.nanoTime();
        System.out.println(((double) end - start)/1e6);
        newFixedThreadPool.shutdown();
    }

    private static void copy(File copyFile, File file1) {
        String copy = copyFile.getAbsolutePath() + File.separator + file1.getName();
        if(file1.isDirectory()){
            copyFile(file1.getPath(), copy);
        }else{
            BufferedInputStream inputStream = FileUtil.getInputStream(file1);
            BufferedOutputStream outputStream = FileUtil.getOutputStream(copy);
            IoUtil.copy(inputStream,outputStream);
        }
    }
}

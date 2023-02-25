package com.example.demo;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test{

    public static void main(String[] args){

        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(8);
        List<File> files = FileUtil.loopFiles
                (new File("/Users/joejoe/Downloads/修仙三百年突然发现是武侠"));
        long l = System.nanoTime();
        for (File file : files) {
            newFixedThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    convertFile(file);
                }
            });
//            convertFile(file);
        }
        long e = System.nanoTime();
        System.out.println(((double) e-l)/1e6);
//        newFixedThreadPool.
        newFixedThreadPool.shutdown();
    }


    private static void convertFile(File file) {
        FileReader fileReader = new FileReader(file);
        FileWriter fileWriter = new FileWriter("/Users/joejoe/Downloads/修仙/" +
                file.getName() + ".md");
        List<String> stringList = fileReader.readLines();
        String str = "";
        for(String s:stringList){
            if(s.trim().startsWith("<title>")){
                String a = s.trim().substring(7);
                a = a.substring(0,a.length() - 8);
                str = str + "# " + a + "\n";
            }else if(s.trim().startsWith("<p>")){
                String a = s.trim().substring(3);
                a = a.substring(0,a.length() - 4);
                str = str + a + "  \n";
            }
        }
        fileWriter.write(str);
//        String result = fileReader.readString();
//        String[] split = result.split("<p>");
//        String s0 = null;
//        for(String s:split){
//            s0 = s0 + s;
//        }
//        String[] split1 = s0.split("</p>");
//        String s1 = null;
//        for(String s:split1){
//            s1 = s1 + s + "  \n";
//        }
//        String[] split2 = s1.split("<h1>");
//        String s2 = null;
//        for(String s:split2){
//            s2 = s2 + s + "# ";
//        }
//        String[] split3 = s2.split("</h1>");
//        String s3 = null;
//        for(String s:split3){
//            s3 = s3 + s;
//        }
//        String[] split4 = s3.split("<body>");
//        String s4 = split4[1];
//        String[] split5 = s4.split("</body>");
//        String s5 = split5[0];
//        fileWriter.write(s5);
    }
}

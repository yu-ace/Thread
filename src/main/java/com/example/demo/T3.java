package com.example.demo;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T3 {
    public static void main(String[] args) {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(8);
        List<File> fileList = FileUtil.loopFiles("/Users/joejoe/Downloads/修仙");
        long start = System.nanoTime();
        for(File file:fileList){
            newFixedThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    FileReader fileReader = new FileReader(file);
                    FileWriter fileWriter = new FileWriter("/Users/joejoe/Downloads/修仙2/" + file.getName().substring(0,file.getName().length() - 9) + ".xhtml");
                    List<String> stringList = fileReader.readLines();
                    String str = "";
                    for(String s:stringList){
                        if(s.startsWith("#")){
                            str = str + "<?xml version='1.0' encoding='utf-8'?>\n" +
                                    "<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"zh\">\n" +
                                    "\n" +
                                    "<head>\n" +
                                    "  <title>" + s.substring(2) + "</title>\n" +
                                    "</head>\n" +
                                    "\n" +
                                    "<body>\n\n " +"<h1>" + s.substring(2) + "</h1>\n\n ";
                        }else{
                            str = str + "<p>" + s + "</p>\n";
                        }
                    }
                    str = str + "\n\n</body>\n\n</html>";
                    fileWriter.write(str);
                }
            });
        }
        long end = System.nanoTime();
        System.out.println((((double)end - start)/1e6));
        newFixedThreadPool.shutdown();
    }
}

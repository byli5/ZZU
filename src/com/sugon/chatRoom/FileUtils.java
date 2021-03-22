package com.sugon.chat3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: byLi
 * @date: 2021/3/18 19:59
 * @description: File Utils
 */
public class FileUtils {

    /**
     * Function:Create a stream object to write data to
     * @param name
     * @return
     */
    public static BufferedWriter createWriteFlow(String name){
        BufferedWriter bw = null;
        String fileName = "E:\\"+name+".txt";
        File file = new File(fileName);
        // If the file does not exist, create it
        FileWriter fw = null;
        try{
            if (!file.exists()){
                file.createNewFile();
            }
            fw = new FileWriter(file,true);
            bw = new BufferedWriter(fw);
        }catch (IOException e){
            e.printStackTrace();
        }
        return bw;
    }

    /**
     *  Function:Save chat record to file
     * @param message
     * @param bw
     */
    public static void saveMessage(String message,BufferedWriter bw){
        try {
            bw.write(message);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.sugon.calculator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: byLi
 * @date: 2021/1/12 17:46
 * @description: Information used to read files by line
 */
public class ReadMessage {

    public List<String> readMessageFromTxt(String name){
        List<String> list = new ArrayList<>();
        File file = new File(name);
        FileReader fr = null;
        BufferedReader bf = null;
        try {
            fr = new FileReader(file);
            bf = new BufferedReader(fr);
            String str;
            // 按行读取
            while ((str=bf.readLine())!=null){
                list.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}

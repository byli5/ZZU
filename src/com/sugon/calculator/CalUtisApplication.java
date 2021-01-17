package com.sugon.calculator;

import com.sugon.massive.cal.Calculate;
import com.sugon.massive.cal.CalculateImpl;

import java.io.IOException;
import java.util.List;

/**
 * @author: byLi
 * @date: 2021/1/12 17:19
 * @description:
 */
public class CalUtisApplication {

    public static void main(String[] args) throws IOException {
        ReadMessage readMessage = new ReadMessage();
        List<String> list = readMessage.readMessageFromTxt("E:\\express.txt");
//        Calculate calculate = new CalculateImpl();
        List<String> results = CalUtils.calculate(list);
        System.out.println("总共有"+results.size()+"条数据");
        for (String str : results){
            System.out.println(str);
        }

    }
}

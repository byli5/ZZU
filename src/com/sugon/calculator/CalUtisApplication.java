package com.sugon.calculator;

import com.sugon.massive.cal.Calculate;
import com.sugon.massive.cal.CalculateImpl;

import java.util.List;

/**
 * @author: byLi
 * @date: 2021/1/12 17:19
 * @description:
 */
public class CalUtisApplication {

    public static void main(String[] args) {
        ReadMessage readMessage = new ReadMessage();
        List<String> list = readMessage.readMessageFromTxt("E:\\result.txt");
        Calculate calculate = new CalculateImpl();
        List<String> results = calculate.calculateExp(list);
        System.out.println("总共有"+results.size()+"条数据");
        for (String str : results){
            System.out.println(str);
        }

    }
}

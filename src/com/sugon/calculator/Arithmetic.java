package com.sugon.calculator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Handler;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.sound.sampled.spi.AudioFileReader;

/*
 * Used to generate the required four Operational expressions
 */
public class Arithmetic {

    public static void main(String[] args){
        //Generating expressions
        creatArith();
    }
    // Generating random numbers
    public static int getNumber(){
        int number = 0;
        Random num = new Random();
        number = num.nextInt(100+1);
        return number;
    }
    // Generating operation symbols randomly
    public static char getOperator(){
        char operator = 0;
        Random ran = new Random();
        int i = ran.nextInt(4);
        switch(i){
            case 0:
                operator = '+';
                break;
            case 1:
                operator = '-';
                break;
            case 2:
                operator = '*';
                break;
            case 3:
                operator = '/';
                break;
        }
        return operator;
    }

    // Generating random expressions
    static ScriptEngine js = new ScriptEngineManager().getEngineByName("JavaScript");
    public static void creatArith(){
        boolean flag=true;
        int num=0;
        do {
            System.out.print("输入需要产生的四则运算的个数：");
            Scanner arith_num = new Scanner(System.in);
            num=arith_num.nextInt();
            if(num>=1) {
                flag = true;
            }
            else {
                flag=false;
                System.out.println("输入有误");
            }
        }while(flag==false);
        ArrayList<String> arithList = new ArrayList<String>();//运算式
        for(int i=0;i<num;i++)
        {
            //产生3~5个运算数
            int num_number = 0;
            Random n = new Random();
            num_number = n.nextInt(3)+3;

            String arr = "";
            for(int j=0;j<num_number;j++){
                int number = getNumber();
                String operator = String.valueOf(getOperator());
                arr += number + operator;
            }
            arr =arr.substring(0,arr.length()-1);
            //Convert arr to array
            char[] arr_str = arr.toCharArray();
            String arrString = "";
            ArrayList<String> arrslist1 = new ArrayList<String>();
            ArrayList<String> arrslist2 = new ArrayList<String>();
            ArrayList<String> arrslist = new ArrayList<String>();
            //  Storage operator
            for(int j=0;j<arr_str.length;j++) {
                if(arr_str[j]=='+'||arr_str[j]=='-'||arr_str[j]=='*'||arr_str[j]=='/') {
                    arrString=String.valueOf(arr_str[j]);
                    arr_str[j]=',';
                    arrslist1.add(arrString);
                }
            }
            //  Store operands
            String s = new String(arr_str);
            String[] ss = s.split(",");
            for(int k=0;k<ss.length;k++) {
                arrslist2.add(ss[k]);
            }
            // Generating expressions
            String[] arrs1 = arrslist1.toArray(new String[arrslist1.size()]);
            String[] arrs2 = arrslist2.toArray(new String[arrslist2.size()]);

            for(int m=0;m<arrs1.length;m++){
                arrslist.add(arrs2[m]);//运算数
                arrslist.add(arrs1[m]);//运算符
            }
            arrslist.add(arrs2[arrs2.length-1]);
            String[] arrs = arrslist.toArray(new String[arrslist2.size()]);//运算式
            arr="";//初始化为空
            check_arith(arrs);
            for(int t=0;t<arrs.length;t++) {
                arr+=arrs[t];
            }
            try {
                check_arith_question(arrs,arr);
            } catch (ScriptException e) {
                e.printStackTrace();
            }
            arr="";
            for(int t=0;t<arrs.length;t++) {
                arr+=arrs[t];
            }
            arithList.add(arr);

        }
        // Output expression
        System.out.println("201571030139");
        for(String arr: arithList){
            try {
                System.out.println(arr+"="+js.eval(arr));
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
        try {
            filewriter(arithList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //判断是否产生负数与小数
    public static void  check_arith(String arr[]){
        for(int i=0;i<arr.length;i++){
            // Judge whether there is a negative number
            if(arr[i].contains("-")){
                if(Integer.valueOf(arr[i-1])<Integer.valueOf(arr[i+1])){
                    arr[i]="+";
                }
            }
            // Judge whether to produce decimal
            if(arr[i].contains("/")){
                int a = Integer.valueOf(arr[i-1]);
                int b = Integer.valueOf(arr[i+1]);
                int x = a%b;
                if(x!=0){
                    arr[i]="+";
                }
            }
        }
    }
    // Judge whether the result produces decimals
    public static void  check_arith_question(String arr[],String arrString) throws ScriptException{
        for(int i=0;i<arr.length;i++){
            String str = js.eval(arrString).toString();
            if(Integer.valueOf(str)<0) {
                if(arr[i].contains("-")){
                    arr[i]="+";
                }
            }

        }
    }
    //存储运算式
    //封装目的地
    public static void filewriter(ArrayList<String> arithList) throws IOException {
        //封装存储位置
        BufferedWriter BufferedWriterbw = new BufferedWriter(new FileWriter("E:\\result.txt",true));
//        BufferedWriterbw.write("201571030139");
//        BufferedWriterbw.newLine();
        for(String s : arithList){
            //写数据
            try {
//                BufferedWriterbw.write(s+"="+js.eval(s));
                BufferedWriterbw.write(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BufferedWriterbw.newLine();
            BufferedWriterbw.flush();
        }
    }

}

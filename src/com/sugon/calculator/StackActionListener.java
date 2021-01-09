package com.calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author: byLi
 * @date: 2020/12/29 14:34
 * @description:
 */
public class StackActionListener implements ActionListener {

    JTextField txtResult;
    private static StringBuffer number = new StringBuffer("");


    public JTextField getTxtResult() {
        return txtResult;
    }

    public void setTxtResult(JTextField txtResult) {
        this.txtResult = txtResult;
    }

    public StackActionListener(){

    }

    public StackActionListener(JTextField txtResult) {
        this.txtResult = txtResult;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();

        if (CalConstant.BACK.equals(label)) {
            // Backspace button
            // To prevent crossing the border, add a judgment
            if (number.length() > 0) {
                number.deleteCharAt(number.length() - 1);
                txtResult.setText(number.toString());
            } else {
                txtResult.setText("0");
            }
        }else if (CalConstant.CLEAR.equals(label)) {
            // Clear button
            number.delete(0,number.length());
            txtResult.setText("0");
        }else if (label.equals("=")){
            String s = number.toString();
            // Call the logic of calculation
            int result = calculate(s);
            number.append("=").append(result);
            txtResult.setText(number.toString());
            number.delete(0,number.length());
        }else {
            number.append(label);
            txtResult.setText(number.toString());
        }
    }

    /**
     *  Using double stack to realize computing logic
     * @param s
     * @return
     */
    private int calculate(String s) {
        //It's easy to exit the stack by adding a sign
        String s1 = new StringBuffer(s).append("#").toString().replace(" ","");
        System.out.println(s1);
        Stack<Character> op = new Stack();
        Stack<Integer> data = new Stack();
        op.push('#');
        /**
         * 1. Specified priority # 0, + - 1, * / 2
         *2. Put the operands into the stack
         *3. Two rules of symbol entering symbol stack:
         *   if the priority of current symbol is higher than the top priority of symbol stack,
         *   enter symbol stack directly
         */
        Map<Character,Integer> prioMap = new HashMap();
        prioMap.put('#',0);prioMap.put('+',1);prioMap.put('-',1);prioMap.put('*',2);prioMap.put('/',2);
        int i =0;

        while(s1.charAt(i)!='#'||op.peek()!='#'){
            //Determine whether it is an operator
//            System.out.println(data);
//            System.out.println(op);
            char u = s1.charAt(i);
            if(isOp(u)){
                //比较当前符号优先级
                if(prioMap.get(u)<=prioMap.get(op.peek())){
                    Character popOp = op.pop();
                    Integer p1 = data.pop();
                    Integer p2 = data.pop();
                    if(popOp=='*')  data.push(p2*p1);
                    if(popOp=='/')  data.push(p2/p1);
                    if(popOp=='+') data.push(p2+p1);
                    if(popOp=='-') data.push(p2-p1);
                }
                else{
                    op.push(u);
                    i++;
                }
            }else{
                int num = 0;
                while(!isOp(s1.charAt(i))){
                    num = num*10 + (s1.charAt(i)-'0');
                    i++;
                }
                data.push(num);
            }
        }
        return data.pop();
    }
    boolean isOp(char c){
        if(c=='+'||c=='-'||c=='*'||c=='/'||c=='#') return true;
        return false;
    }

}

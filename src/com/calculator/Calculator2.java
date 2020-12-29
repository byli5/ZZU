package com.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author: byLi
 * @date: 2020/12/29 10:29
 * @description:Using stack to realize computing logic
 *
 */
public class Calculator2 extends JFrame implements ActionListener {

    // JTextField object
    JTextField txtResult;
    private static StringBuffer number = new StringBuffer("");
    BigDecimal number1 = new BigDecimal("0");
    BigDecimal number2 = new BigDecimal("0");
    // Used to identify operators
    private static String flag = "=";
    //Judge whether divisor is zero
    boolean validFlag = true;

    private static final String CLEAR = "清空";
    private static final String BACK = "退格";

    public Calculator2() {
        setTitle("计算器");
        setSize(300, 270);
        setResizable(false);
        //When the window is set to null, the window will be displayed at the center of the screen,
        // otherwise it will be displayed in the upper left corner.
        setLocationRelativeTo(null);
        // Set the closing mode of the form
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Get a container object
        Container contentPane = this.getContentPane();
        // Border layout
        contentPane.setLayout(new BorderLayout(1, 5));

        // Create two new panel objects
        JPanel pnlNorth = new JPanel();
        JPanel pnlCenter = new JPanel();

        // Set panel layout
        pnlNorth.setLayout(new BorderLayout());
        pnlCenter.setLayout(new GridLayout(4, 4, 3, 3));

        // Define font style
        Font font = new Font("黑体", Font.BOLD, 20);

        // Add two panels to the container
        contentPane.add(BorderLayout.NORTH, pnlNorth);
        contentPane.add(BorderLayout.SOUTH, pnlCenter);

        // Set text box
        txtResult = new JTextField();
        txtResult.setFont(font);
        txtResult.setEnabled(false);


        // Set the clear button and backspace button
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setPreferredSize(new Dimension(300,20));
        JButton btnBack = new JButton(BACK);
        JButton btnClear = new JButton(CLEAR);
        btnBack.addActionListener(this);
        btnClear.addActionListener(this);
        panel.add(btnBack);
        panel.add(btnClear);
        contentPane.add(BorderLayout.CENTER, panel);

        // Place the text box and clear button in the top panel
        pnlNorth.add(BorderLayout.CENTER, txtResult);

        //  Put numbers and operators into the pnlcentre panel, and set font size and bind listening events
        String[] captions = {"7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "*", "0", ".", "/", "=",};
        int length = captions.length;
        for (int i = 0; i < length; i++) {
            JButton button = new JButton(captions[i]);
            button.setFont(font);
            pnlCenter.add(button);
            button.addActionListener(this);
        }

    }

    public static void main(String[] args) {
        JFrame frame = new Calculator2();
        frame.setVisible(true);

    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();

        if (BACK.equals(label)) {
            // Backspace button
            // To prevent crossing the border, add a judgment
            if (number.length() > 0) {
                number.deleteCharAt(number.length() - 1);
                txtResult.setText(number.toString());
            } else {
                txtResult.setText("0");
            }
        }else if (CLEAR.equals(label)) {
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

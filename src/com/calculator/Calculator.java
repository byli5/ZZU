package com.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author: byLi
 * @date: 2020/12/26 20:47
 * @description:To realize the function of addition, subtraction, multiplication and division of calculator
 */

public class Calculator extends JFrame implements ActionListener {

    // JTextField object
    JTextField txtResult;
    static StringBuffer number = new StringBuffer("");
    BigDecimal number1 = new BigDecimal("0");
    BigDecimal number2 = new BigDecimal("0");
    // Used to identify operators
    private static String flag = "=";
    //Judge whether divisor is zero
    boolean validFlag = true;


    public Calculator() {
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
        Font font = new Font(CalConstant.FONT_STYLE, Font.BOLD, 20);

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
        JButton btnBack = new JButton(CalConstant.BACK);
        JButton btnClear = new JButton(CalConstant.CLEAR);
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
        JFrame frame = new Calculator();
        frame.setVisible(true);

    }

    /**
     * Invoked when an action occurs.
     *
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
                txtResult.setText(CalConstant.ZERO);
            }
        }else if (CalConstant.CLEAR.equals(label)) {
            // Clear button
            number.delete(0,number.length());
            txtResult.setText(CalConstant.ZERO);
        } else if (CalConstant.POINT.equals(label)){
            point();
        } else if ("0123456789".indexOf(label) >= 0) {
            number.append(label);
            txtResult.setText(number.toString());
        }else {
            handleOperator(label);
        }



    }

    /**
     *
     * Logic of addition, subtraction, multiplication and division
     * @param label
     */
    private void handleOperator(String label) {

        if (!number.toString().equals("")) {

            if ("+-*/".indexOf(label) >= 0) {
                flag = label;
                number1 = new BigDecimal(number.toString());
                number.delete(0, number.length());
                txtResult.setText(null);
            }

            if (CalConstant.EQUAL.equals(label)) {
                number2 = new BigDecimal(number.toString());
                number.delete(0, number.length());
                BigDecimal result = new BigDecimal(CalConstant.ZERO);
                switch (flag) {
                    case CalConstant.MULTIPLY:
                        result = number1.multiply(number2);
                        break;
                    case CalConstant.REDUCE:
                        result = number1.subtract(number2);
                        break;
                    case CalConstant.PLUS:
                        result = number1.add(number2);
                        break;
                    case CalConstant.DIVIDE:
                        if (new BigDecimal(CalConstant.ZERO).equals(number2)){
                            validFlag = false;
                        }else {
                            // edit 2020.12.27 reason:When doing division, try to use divide (big decimal divisor, int scale, int roundingmode).
                            // If this method does not specify the number of decimal places to be reserved, an error will be reported in case of endless division
                            result = number1.divide(number2, 2, RoundingMode.HALF_UP);
                        }
                        break;

                }

                if (validFlag) {
                    txtResult.setText(String.valueOf(result));
                }else {
                    txtResult.setText(CalConstant.DIVIDE_BYZERO_INFO);
                    validFlag = true;
                }
                number.append(result);
            }
        }else {
            txtResult.setText(CalConstant.INPUT_FIRT_SIGN);
        }
    }


    /**
     * The logic of dealing with decimal point
     */
    private void point(){
        if (number.length()==0){
            number.append("0.");
        }else if (number.toString().indexOf(".")==-1){
            number.append(".");
        }else {
            number.append("");
        }
        txtResult.setText(number.toString());
    }


}


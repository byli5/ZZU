package com.calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author: byLi
 * @date: 2020/12/29 14:15
 * @description:
 */
public class ComActionListener implements ActionListener{

    JTextField txtResult;
    static StringBuffer number = new StringBuffer("");
    BigDecimal number1 = new BigDecimal("0");
    BigDecimal number2 = new BigDecimal("0");
    // Used to identify operators
    private static String flag = "=";
    //Judge whether divisor is zero
    boolean validFlag = true;

    public JTextField getTxtResult() {
        return txtResult;
    }

    public void setTxtResult(JTextField txtResult) {
        this.txtResult = txtResult;
    }

    public ComActionListener(){

    }

    public ComActionListener(JTextField jTextField){
        this.txtResult = jTextField;
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

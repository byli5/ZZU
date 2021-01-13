package com.sugon.calculator;

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
            String result = CalUtils.calculate(s);
            number.append("=").append(result);
            txtResult.setText(number.toString());
            number.delete(0,number.length());
        }else {
            number.append(label);
            txtResult.setText(number.toString());
        }
    }

}

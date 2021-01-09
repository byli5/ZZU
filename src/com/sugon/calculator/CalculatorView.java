package com.sugon.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author: byLi
 * @date: 2020/12/26 20:47
 * @description:To realize the function of addition, subtraction, multiplication and division of calculator
 */

public class CalculatorView extends JFrame {

    private JTextField txtResult = new JTextField();

    private ActionListener listener;

    public JTextField getTxtResult() {
        return txtResult;
    }

    public void setTxtResult(JTextField txtResult) {
        this.txtResult = txtResult;
    }

    public ActionListener getListener() {
        return listener;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    public CalculatorView() {

    }

    public void init() {
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
//        txtResult = new JTextField();
        txtResult.setFont(font);
        txtResult.setEnabled(false);

        // listner
//        ComActionListener listener = new ComActionListener(txtResult);

        // Set the clear button and backspace button
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setPreferredSize(new Dimension(300, 20));
        JButton btnBack = new JButton(CalConstant.BACK);
        JButton btnClear = new JButton(CalConstant.CLEAR);
        btnBack.addActionListener(listener);
        btnClear.addActionListener(listener);
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
            button.addActionListener(listener);
        }
        setVisible(true);
    }

}


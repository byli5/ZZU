package com.calculator;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author: byLi
 * @date: 2020/12/29 14:30
 * @description:Calculator startup class
 *
 */
public class CalculatorApplication {

    public static void main(String[] args) {
        CalculatorView view = new CalculatorView();
        ActionListener listener =new ComActionListener(view.getTxtResult());
        view.setListener(listener);
        view.init();
    }

}

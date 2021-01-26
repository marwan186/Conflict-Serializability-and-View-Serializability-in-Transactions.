package com;

import javax.swing.*;
import java.awt.*;


public class ViewController extends JFrame {

    private static SerializationChecker checker = new SerializationChecker();

    public static void main(String[] args) {
        new ViewController();
    }


    private ViewController() {
        baseWindowConfiguration();

        JLabel label = new JLabel("Provide transactions for checking: ");
        JButton confirmButton = new JButton("Confirm");
        JTextField textfield1 = new JTextField("", 10);

        confirmButton.setSize(140, 80);
        getContentPane().add(label);
        getContentPane().add(textfield1);
        getContentPane().add(confirmButton);

        pack();

//        r1x,w2x,w1x,r3x
//        r1x,r2z,r1z,r3y,r3y,w1x,w3y,r2y,w2z,w2y

        confirmButton.addActionListener(e -> {
            checker.populateSchedule(textfield1.getText().split(","));
            textfield1.setText("");
            JOptionPane.showMessageDialog(this, checker.getSchedule() + "\n" +
                            checker.getPrecedenceGraph() + "\n" +
                            checker.getConflictSerializable() + "\n" +
                            checker.getViewSerializable(),
                    "Result", JOptionPane.INFORMATION_MESSAGE);
        });

    }

    private void baseWindowConfiguration() {
        setVisible(true);
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Conflict/View Serialization Checker");
        getContentPane().setLayout(new FlowLayout());
    }


}

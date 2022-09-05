package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GUI {
    private JTextField input00;
    private JTextField input01;
    private JTextField input02;
    private JTextField input10;
    private JTextField input11;
    private JTextField input12;
    private JTextField input20;
    private JTextField input21;
    private JTextField input22;
    private JButton reset;
    private JButton compute;
    private JTextArea result;
    private JPanel mainPanel;
    private JTextArea fillTheRowsWithTextArea;

    public GUI() {
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input00.setText("");
                input01.setText("");
                input02.setText("");
                input10.setText("");
                input11.setText("");
                input12.setText("");
                input20.setText("");
                input21.setText("");
                input22.setText("");
                result.setText("Press Compute to get the series win probability.");
            }
        });

        compute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rnd = new Random();
                int[][] matice =new int[3][3];
                matice[0][0] = Integer.parseInt(input00.getText());
                matice[0][1] = Integer.parseInt(input01.getText());
                matice[0][2] = Integer.parseInt(input02.getText());
                matice[1][0] = Integer.parseInt(input10.getText());
                matice[1][1] = Integer.parseInt(input11.getText());
                matice[1][2] = Integer.parseInt(input12.getText());
                matice[2][0] = Integer.parseInt(input20.getText());
                matice[2][1] = Integer.parseInt(input21.getText());
                matice[2][2] = Integer.parseInt(input22.getText());

                double winsRnd =0;
                double lossesRnd=0;
                for (int i = 0; i <10000000 ; i++) {
                    //System.out.println(i);
                    boolean[] A = new boolean[3];
                    boolean[] B = new boolean[3];
                    while (true) {
                        int a = rnd.nextInt(3);
                        while(A[a]) {
                            a = rnd.nextInt(3);
                        }
                        int b = rnd.nextInt(3);
                        while(B[b]) b = rnd.nextInt(3);
                        int res = rnd.nextInt(100);
                        if (res < matice[a][b]){
                            A[a] = true;
                            if ((A[0]&A[1])&A[2]){
                                winsRnd++;
                                break;
                            }
                        }
                        else {
                            B[b] = true;
                            a = rnd.nextInt(3);
                            while(A[a]) {
                                a = rnd.nextInt(3);
                            }
                            if ((B[0] & B[1]) & B[2]) {
                                lossesRnd++;
                                break;
                            }
                        }
                    }
                }
                result.setText("Series win probability: " + String.format("%.3f", winsRnd/(winsRnd+lossesRnd)));
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Conquest Bo5 winrate calculator");
        frame.setContentPane(new GUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.pack();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
    }

}


package main;

import javax.swing.*;
public class MyFrame extends JFrame {

    MyFrame(){
        MyPanel panel = new MyPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

}

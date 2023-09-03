package by.sergey.view;

import java.util.Random;

public class Ext {
    public static void main(String[] args) {
        var format = String.format("%08d", new Random().nextInt(90000000));
        System.out.println(format);
    }
}

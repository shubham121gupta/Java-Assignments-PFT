package org.example;

import java.util.Scanner;

public class ColorCode {
        private String getColorCode(int x, int y) {
            if (isInRange(x, y, 40, 60, 70, 90)) {
                return "Blue";
            } else if (isInRange(x, y, 61, 80, 91, 120)) {
                return "Green";
            } else if (isInRange(x, y, 81, 90, 121, 140)) {
                return "Yellow";
            } else if (isInRange(x, y, 91, 100, 141, 190)) {
                return "Red";
            } else {
                return "Input not valid";
            }
        }

    private boolean isInRange(int x, int y, int xStart, int xEnd, int yStart, int yEnd) {
        return x >= xStart && x <= xEnd && y >= yStart && y <= yEnd;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the value of x: ");
        int x = sc.nextInt();

        System.out.println("Please enter the value of y: ");
        int y = sc.nextInt();

        ColorCode colorCode = new ColorCode();
        String colorCodeString = colorCode.getColorCode(x,y);
        System.out.println(colorCodeString);
    }
}

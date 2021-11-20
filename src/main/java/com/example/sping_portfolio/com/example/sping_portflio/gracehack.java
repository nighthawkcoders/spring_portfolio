package com.example.sping_portfolio.com.example.sping_portflio;

import java.util.Scanner;

public class gracehack {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String student;
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is your name?");
        student = scanner.nextLine();

        System.out.print("Input your first test grade: ");
        int num1 = in.nextInt();

        System.out.print("Input your second test grade: ");
        int num2 = in.nextInt();

        System.out.print("Input your third test grade: ");
        int num3 = in.nextInt();

        System.out.print("Input your fourth test grade: ");
        int num4 = in.nextInt();

        System.out.print("Input your fifth test grade: ");
        int num5 = in.nextInt();


        System.out.println("Average of five numbers is: " +
                (num1 + num2 + num3 + num4 + num5) / 5);
        double num12 = (num1 + num2) / 2.0;
        double num34 = (num3 + num4) / 2.0;
        double lastAvg = (num12 + num34+ num5) / 3.0;

        System.out.println("Hi "+student+"! Your final grade is "+ lastAvg);
    }
}


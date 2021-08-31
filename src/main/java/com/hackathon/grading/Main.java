package com.hackathon.grading;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int grade1, grade2, grade3, grade4, grade5;
        double avg12, avg34, finalAvg;
        String name;
        Scanner scanner = new Scanner(System.in);
        grade1 = grade2 = grade3 = grade4 = grade5 = 100;

        System.out.println("Who you be?");
        name = scanner.nextLine();

        System.out.println("What you get? (Test 1)");
        grade1 = scanner.nextInt();

        System.out.println("What you get? (Test 2)");
        grade2 = scanner.nextInt();

        System.out.println("What you get? (Test 3)");
        grade3 = scanner.nextInt();

        System.out.println("What you get? (Test 4)");
        grade4 = scanner.nextInt();

        System.out.println("What you get? (Test 5)");
        grade5 = scanner.nextInt();

        avg12 = (grade1 + grade2)/2.0;
        avg34 = (grade3 + grade4)/2.0;
        finalAvg = (avg12 + avg34 + grade5)/3.0;

        String finalOutput = String.format("Okay, %s, you got %f for grading period 1, %f for grading period 2, and %f for your total average.", name, avg12, avg34, finalAvg)
        System.out.println(finalOutput);
    }
}

package com.hackathon.classes;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        patient child = new patient(true, true, 9, "Johnny", "Mr. Rogers");
        patient adult = new patient(false, true, 18, "Beth", "Karen");
        if (child.willTreat() == true) {
            System.out.println("We're treating the kid!");
        }
    }
}

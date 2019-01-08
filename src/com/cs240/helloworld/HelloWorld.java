package com.cs240.helloworld;

import java.util.Random;
import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println("I love Java");
        System.out.println("I love Java".length());

        Random rand = new Random();
        int i = rand.nextInt();
        System.out.println("Random number: " + i);

        System.out.println('\u263A');
        String str = "I love Java";
        char c = str.charAt(5);
        int codePoint = str.codePointAt(str.offsetByCodePoints(0, 6));
        System.out.println(c);
        System.out.println("Code point: " + codePoint);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input your age: ");
        int age = scanner.nextInt();
        System.out.printf("You are %d years old.\n", age);
    }
}

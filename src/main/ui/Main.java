package ui;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("select hour between 0 - 23");
        int hour = scanner.nextInt();
        scanner.nextLine();
        boolean valid = false;

        while (!valid) {
            if (hour >= 0 && hour < 24) {
                valid = true;
                System.out.println("good!");
            } else {
                System.out.println("Time selected was invalid! Please try again!");
                hour = scanner.nextInt();
                scanner.nextLine();
            }
        }
    }
}

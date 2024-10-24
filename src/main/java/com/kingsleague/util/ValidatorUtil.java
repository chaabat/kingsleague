package com.kingsleague.util;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    private static final Scanner scanner = new Scanner(System.in);

    // Validate if a string is not empty and does not exceed a specified length
    public static String validateString(String prompt, int maxLength) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty() && input.length() <= maxLength) {
                return input;
            } else {
                System.out.println("Invalid input. Please enter a non-empty string with a maximum length of " + maxLength + " characters.");
            }
        }
    }

    // Validate if an integer is within a specified range
    public static int validateInteger(String prompt, int min, int max) {
        int input = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            try {
                input = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
                if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.println("Input must be between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        return input;
    }

    // Validate email format
    public static String validateEmail(String prompt) {
        String input;
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher;

        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            matcher = pattern.matcher(input);
            if (matcher.matches()) {
                return input;
            } else {
                System.out.println("Invalid email format. Please try again.");
            }
        }
    }

    // Validate date format (simple example: YYYY-MM-DD)
    public static String validateDate(String prompt) {
        String input;
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher;

        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            matcher = pattern.matcher(input);
            if (matcher.matches()) {
                return input;
            } else {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }
}

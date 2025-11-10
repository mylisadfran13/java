package tasks;

import java.util.Scanner;

public class GradeCalculator {
    
    public static String grade(int score) {
        if (score < 0 || score > 100) {
            return "Invalid";
        } else if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        if (scanner.hasNextInt()) {
            int score = scanner.nextInt();
            String result = grade(score);
            
            if (result.equals("Invalid")) {
                System.out.println("Invalid");
            } else {
                System.out.println(result);
            }
        } else {
            System.out.println("Ошибка: введите целое число");
        }
        
        scanner.close();
    }
}
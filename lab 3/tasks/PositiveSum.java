package tasks;

import java.util.Scanner;

public class PositiveSum {

    public static int sumOfPositives(int[] array) {
        int sum = 0;
        for (int num : array) {
            if (num > 0) {
                sum += num;
            }
        }
        return sum;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        
        String[] parts = input.split("[,\\s]+");
        int[] numbers = new int[parts.length];
        
        for (int i = 0; i < parts.length; i++) {
            numbers[i] = Integer.parseInt(parts[i].trim());
        }
        
        int result = sumOfPositives(numbers);
        System.out.println(result);
        
        scanner.close();
    }
}
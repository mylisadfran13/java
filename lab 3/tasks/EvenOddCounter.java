package tasks;

import java.util.Scanner;

public class EvenOddCounter {
    
    public static int[] countEvenOddDigits(int n) {
        int number = Math.abs(n);
        
        if (number == 0) {
            return new int[]{1, 0};
        }
        
        int evenCount = 0;
        int oddCount = 0;
        
        while (number > 0) {
            int digit = number % 10;  
            if (digit % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }
            number /= 10; 
        }
        
        return new int[]{evenCount, oddCount};
    }
    
    public static int[] countEvenOddDigitsString(int n) {
        int number = Math.abs(n);
        String numberStr = String.valueOf(number);
        
        if (number == 0) {
            return new int[]{1, 0};
        }
        
        int evenCount = 0;
        int oddCount = 0;
        
        for (int i = 0; i < numberStr.length(); i++) {
            int digit = Character.getNumericValue(numberStr.charAt(i));
            if (digit % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }
        }
        
        return new int[]{evenCount, oddCount};
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        try {
            int number = scanner.nextInt();
    
            int[] result = countEvenOddDigits(number);
            
            System.out.println(result[0] + ", " + result[1]);
            
        } catch (Exception e) {
            System.out.println("Ошибка: введите корректное целое число");
        }
        
        scanner.close();
    }
}
package tasks;

import java.util.Scanner;

public class DivisorCounter {
 
    public static int countDivisors(int n) {
        if (n <= 0) {
            return 0;
        }
        
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                count++;
            }
        }
        return count;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
    
        if (number <= 0) {
        } else {
            int result = countDivisors(number);
            System.out.println(result);
        }
        
        scanner.close();
    }
}
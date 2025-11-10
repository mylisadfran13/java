package tasks;

import java.util.Scanner;

public class RangeSum {
    public static int sumRange(int a, int b) {
        int sum = 0;
        for (int i = a; i <= b; i++) {
            sum += i;
        }
        return sum;
    }

    public static int sumRangeFormula(int a, int b) {
        int n = b - a + 1;
        return n * (a + b) / 2;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int a = scanner.nextInt();
        
        int b = scanner.nextInt();
        
        if (a > b) {
        } else {
            int result = sumRange(a, b);
            System.out.println(result);
        }
        
        scanner.close();
    }
}
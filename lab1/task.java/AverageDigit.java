import java.util.Scanner;

public class AverageDigit {
    public static int average(int a, int b, int c) {
        int max = Math.max(Math.max(a, b), c);
        int min = Math.min(Math.min(a, b), c);
        
        if (a != max && a != min) {
            return a;
        } else if (b != max && b != min) {
            return b;
        } else {
            return c;
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();
        int num3 = scanner.nextInt();
        
        int result = average(num1, num2, num3);
        System.out.println(result);
        
        scanner.close();
    }
}
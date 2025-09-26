import java.util.Scanner;

public class DigitEven {
    
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        boolean result = isEven(num);
        System.out.println(result);
        
        scanner.close();
    }
}
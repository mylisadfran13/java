import java.util.Scanner;

public class TemperatureConverter {
    public static double toFahrenheit(double celsius) {
        return celsius * 9 / 5 + 32;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double celsius = scanner.nextDouble();
        double fahrenheit = toFahrenheit(celsius);
        if (fahrenheit == (int) fahrenheit) {
            System.out.println((int) fahrenheit);
        } else {
            System.out.println(fahrenheit);
        }
        
        scanner.close();
    }
}
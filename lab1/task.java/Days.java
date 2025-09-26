import java.util.Scanner;

public class Days {
    public static String daysToWeeks(int days) {
        int weeks = days / 7;
        int remainingDays = days % 7;
        
        return weeks + " недель " + remainingDays + " дней";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int days = scanner.nextInt();
        
        String result = daysToWeeks(days);
        System.out.println(result);
        
        scanner.close();
    }
}
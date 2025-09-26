import java.util.Scanner;

public class  Month {
    public static String season(int month) {
        switch (month) {
            case 12:
            case 1:
            case 2:
                return "зима";
            case 3:
            case 4:
            case 5:
                return "весна";
            case 6:
            case 7:
            case 8:
                return "лето";
            case 9:
            case 10:
            case 11:
                return "осень";
            default:
                return "неверный месяц";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int month = scanner.nextInt();
        
        String result = season(month);
        System.out.println(result);
        
        scanner.close();
    }
}
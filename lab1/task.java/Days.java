import java.util.Scanner;

public class Days {
    public static String daysToWeeks(int days) {
        int weeks = days / 7;
        int remainingDays = days % 7;
        
        String weeksText;
        String daysText;
        
        if (weeks == 1) {
            weeksText = "неделя";
        } else if (weeks >= 2 && weeks <= 4) {
            weeksText = "недели";
        } else {
            weeksText = "недель";
        }
        
        if (remainingDays == 1) {
            daysText = "день";
        } else if (remainingDays >= 2 && remainingDays <= 4) {
            daysText = "дня";
        } else {
            daysText = "дней";
        }
        
        return weeks + " " + weeksText + " и " + remainingDays + " " + daysText;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Введите количество дней: ");
        int days = scanner.nextInt();
        
        String result = daysToWeeks(days);
        System.out.println(result);
        
        scanner.close();
    }
}
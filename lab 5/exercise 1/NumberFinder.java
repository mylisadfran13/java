import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;

public class NumberFinder {
    
    public static List<String> findNumbers(String text) {
        List<String> numbers = new ArrayList<>();
        String regex = "[-+]?\\d{1,3}(?:,\\d{3})*(?:\\.\\d+)?(?:[eE][-+]?\\d+)?";
        
        Pattern pattern;
        
        try {
            pattern = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            System.err.println("ошибка в регулярном выражении: " + e.getMessage());
            return numbers;
        }
        
        Matcher matcher = pattern.matcher(text);
        
        try {
            while (matcher.find()) {
                String number = matcher.group();
                
                number = number.replace(",", "");
                numbers.add(number);
            }
        } catch (Exception e) {
            System.err.println("ошибка при поиске: " + e.getMessage());
        }
        
        return numbers;
    }
    
    public static void printNumbers(List<String> numbers) {
        if (numbers.isEmpty()) {
            System.out.println("числа не найдены :(");
            return;
        }
        
        System.out.println("найдено чисел: " + numbers.size());
        System.out.println("числа:");
        
        for (int i = 0; i < numbers.size(); i++) {
            System.out.println((i + 1) + ") " + numbers.get(i));
        }
    }
    
    public static void main(String[] args) {
        String[] testTexts = {
            "Цена товара составляет 19,99 рублей в количестве 50 единиц.",
            "Температура сегодня -5.3°C, завтра ожидается +12.7°C.",
            "Население города составляет 1,234,567 человек, а площадь 123.45 квадратных км.",
            "Число π примерно равно 3.14159, скорость света 3.0e8 м/с.",
            "Номера: 007, 1.23e-4, -42, +100.500",
            "",
            "Вообще нет чисел." 
        };
        
        for (int i = 0; i < testTexts.length; i++) {
            System.out.println("\n " + (i + 1));
            System.out.println("текст: " + testTexts[i]);
            
            try {
                List<String> numbers = findNumbers(testTexts[i]);
                printNumbers(numbers);
            } catch (Exception e) {
                System.err.println("ошибка: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        if (args.length > 0) {
            System.out.println("\nаргументы командной строки");
            StringBuilder inputText = new StringBuilder();
            for (String arg : args) {
                inputText.append(arg).append(" ");
            }
            
            List<String> numbers = findNumbers(inputText.toString());
            printNumbers(numbers);
        } else {
            System.out.println("\nпример");
            String complexText = "целые 42 и -73, " +
                               "дробные 3.14 и -0.001, " +
                               "большие 1,000,000 и 9,999.99, " +
                               "научные  6.022e23 и 1.6e-19.";
            
            System.out.println("исходный текст: " + complexText);
            List<String> numbers = findNumbers(complexText);
            printNumbers(numbers);
        }
    }
}
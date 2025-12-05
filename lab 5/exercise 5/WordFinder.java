import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class WordFinder {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("введите текст для поиска:");
            String text = scanner.nextLine();
            
            System.out.println("введите букву для поиска слов:");
            String inputLetter = scanner.nextLine();
            
            if (inputLetter.length() != 1 || !Character.isLetter(inputLetter.charAt(0))) {
                System.err.println("ошибка: введите одну букву.");
                return;
            }
            
            char letter = inputLetter.charAt(0);
            
            findWordsStartingWithLetter(text, letter);
            
        } catch (Exception e) {
            System.err.println("произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    public static void findWordsStartingWithLetter(String text, char letter) {
        if (text == null || text.trim().isEmpty()) {
            System.out.println("текст пустой или не содержит слов");
            return;
        }
        
        try {
            String regex = "\\b(?i)[" + letter + "][A-Za-zА-Яа-яЁё'-]+\\b";
            
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            
            System.out.println("\nслова, начинающиеся с буквы '" + letter + "':");

            
            int wordCount = 0;
            boolean foundAny = false;
            
            while (matcher.find()) {
                String word = matcher.group();
                System.out.println(++wordCount + ". " + word);
                foundAny = true;
            }
            
            if (!foundAny) {
                System.out.println("слов, начинающихся с буквы '" + letter + "', не найдено.");
            } else {
                System.out.println("всего найдено слов: " + wordCount);
            }
            
        } catch (PatternSyntaxException e) {
            System.err.println("ошибка в синтаксисе регулярного выражения: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("произошла ошибка при поиске слов: " + e.getMessage());
        }
    }
}
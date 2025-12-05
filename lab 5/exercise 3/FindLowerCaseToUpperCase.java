import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindLowerCaseToUpperCase {
    public static void main(String[] args) {
        String text = "Например Возьму textС сочетанием разныхСлов регистров. FindLowerCaseToUpperCase И еще одинТест.";
        
        System.out.println("исходный текст:");
        System.out.println(text);
        System.out.println("\nрезультат:");
        
 
        String result = highlightPattern(text);
        System.out.println(result);
    }
    
    public static String highlightPattern(String text) {

        Pattern pattern = Pattern.compile("([a-zа-я])([A-ZА-Я])", Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(text);
        
        StringBuilder result = new StringBuilder();
        int lastIndex = 0;
        
        while (matcher.find()) {
            result.append(text, lastIndex, matcher.start());
            
            result.append(matcher.group(1)).append("!").append(matcher.group(2)).append("!");
            
            lastIndex = matcher.end();
        }
        
        result.append(text.substring(lastIndex));
        
        return result.toString();
    }
}
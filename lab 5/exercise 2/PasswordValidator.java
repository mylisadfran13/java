import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PasswordValidator {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("придумайте пароль");
        System.out.println("1 длина от 8 до 16 символов");
        System.out.println("2 только латинские буквы и цифры");
        System.out.println("3 минимум одна заглавная буква");
        System.out.println("4 минимум одна цифра");
        
        boolean isValid = false;
        
        while (!isValid) {
            try {
                System.out.print("введите пароль: ");
                String password = scanner.nextLine();
                
                isValid = validatePassword(password);
                
                if (isValid) {
                    System.out.println("пароль создан");
                } else {
                    System.out.println("неправильный ввод пароля, придумайте другой");
                }
                
            } catch (Exception e) {
                System.out.println("ошибка: " + e.getMessage());
                System.out.println("пожалуйста, попробуйте еще раз.");
            }
        }
        
        scanner.close();
    }
    
    public static boolean validatePassword(String password) throws IllegalArgumentException {
        if (password == null) {
            throw new IllegalArgumentException("пароль не может быть null");
        }
        
        if (password.length() < 8 || password.length() > 16) {
            System.out.println("длина пароля должна быть от 8 до 16 символов.");
            return false;
        }

        String regex = "^(?=.*[A-Z])(?=.*\\d).{8,16}$";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        
        if (!matcher.matches()) {
            if (!password.matches("[A-Za-z\\d]*")) {
                System.out.println("пароль содержит недопустимые символы. " +
                                 "разрешены только латинские буквы и цифры.");
            } else if (!password.matches(".*[A-Z].*")) {
                System.out.println("пароль должен содержать хотя бы одну заглавную букву.");
            } else if (!password.matches(".*\\d.*")) {
                System.out.println("пароль должен содержать хотя бы одну цифру.");
            }
            return false;
        }
        
        return true;
    }
}
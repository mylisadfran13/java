import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class IPAddressValidator {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("введите IP-адрес: ");
        String ipAddress = scanner.nextLine();
        
        try {
            if (isValidIPAddress(ipAddress)) {
                System.out.println("IP-адрес правильный");
            } else {
                System.out.println("IP-адрес неправильный");
            }
        } catch (Exception e) {
            System.out.println("произошла ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    public static boolean isValidIPAddress(String ipAddress) {
        if (ipAddress == null || ipAddress.trim().isEmpty()) {
            System.out.println("IP-адрес не может быть пустым.");
            return false;
        }

        String strictIpPattern = "^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])\\.){3}" +
                                "(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])$";
        
        try {
            Pattern pattern = Pattern.compile(strictIpPattern);
            Matcher matcher = pattern.matcher(ipAddress);
            
            if (!matcher.matches()) {
                System.out.println("ошибка: Неверный формат IP-адреса.");
                return false;
            }
            
            String[] parts = ipAddress.split("\\.");
            
            if (parts.length != 4) {
                System.out.println("ошибка: IP-адрес должен состоять из 4 чисел.");
                return false;
            }
            
            for (int i = 0; i < parts.length; i++) {
                int octet;
                try {
                    octet = Integer.parseInt(parts[i]);
                } catch (NumberFormatException e) {
                    System.out.println("ошибка: часть IP-адреса не является числом: " + parts[i]);
                    return false;
                }
                
                if (octet < 0 || octet > 255) {
                    System.out.println("ошибка: число " + octet + " должно быть в диапазоне от 0 до 255.");
                    return false;
                }
                if (i == 0 && parts[i].length() > 1 && parts[i].startsWith("0")) {
                    System.out.println("ошибка: первое число IP-адреса не может начинаться с 0.");
                    return false;
                }
            }
            
            return true;
            
        } catch (Exception e) {
            System.out.println("ошибка при проверке IP-адреса: " + e.getMessage());
            return false;
        }
    }
}
package tasks;

import java.util.Scanner;

public class Clamp {
    

    public static int clamp(int value, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min не может быть больше max");
        }
        
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
    
    public static int clampMath(int value, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min не может быть больше max");
        }
        return Math.max(min, Math.min(value, max));
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        
        String[] parts = input.split("[,\\s]+");
        
        if (parts.length < 3) {
            System.out.println("Ошибка: необходимо ввести три числа (value, min, max)");
            scanner.close();
            return;
        }
        
        try {
            int value = Integer.parseInt(parts[0].trim());
            int min = Integer.parseInt(parts[1].trim());
            int max = Integer.parseInt(parts[2].trim());
            

            if (min > max) {
                System.out.println("Ошибка: min не может быть больше max");
            } else {
                int result = clamp(value, min, max);
                System.out.println(result);
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректные целые числа");
        }
        
        scanner.close();
    }
}
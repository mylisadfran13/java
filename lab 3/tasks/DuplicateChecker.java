package tasks;

import java.util.Scanner;
import java.util.HashSet;

public class DuplicateChecker {
    
    public static boolean hasDuplicates(int[] array) {
        if (array.length == 0) {
            return false;
        }

        HashSet<Integer> seen = new HashSet<>();
        
        for (int num : array) {

            if (seen.contains(num)) {
                return true;
            }
            seen.add(num);
        }
        
        return false;
    }

    public static boolean hasDuplicatesSimple(int[] array) {
        if (array.length == 0) {
            return false;
        }
        
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] == array[j]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String input = scanner.nextLine();
 
        if (input.trim().isEmpty()) {
            System.out.println("false");
            scanner.close();
            return;
        }

        String[] parts = input.split("[,\\s]+");
        int[] numbers = new int[parts.length];
        
        for (int i = 0; i < parts.length; i++) {
            numbers[i] = Integer.parseInt(parts[i].trim());
        }
        
        boolean result = hasDuplicates(numbers);
        System.out.println(result);
        
        scanner.close();
    }
}
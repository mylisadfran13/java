package tasks;

import java.util.Scanner;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;

public class ArrayMerger {
    
    public static int[] mergeUniqueSorted(int[] a, int[] b) {

        Set<Integer> uniqueSet = new TreeSet<>();
        
        for (int num : a) {
            uniqueSet.add(num);
        }

        for (int num : b) {
            uniqueSet.add(num);
        }
        
        int[] result = new int[uniqueSet.size()];
        int index = 0;
        for (int num : uniqueSet) {
            result[index++] = num;
        }
        
        return result;
    }
    
    public static int[] mergeUniqueSortedAlternative(int[] a, int[] b) {
        Set<Integer> uniqueSet = new HashSet<>();
        
        for (int num : a) {
            uniqueSet.add(num);
        }
        for (int num : b) {
            uniqueSet.add(num);
        }
        
        int[] result = new int[uniqueSet.size()];
        int index = 0;
        for (int num : uniqueSet) {
            result[index++] = num;
        }
        Arrays.sort(result);
        
        return result;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String input = scanner.nextLine().trim();
        
        try {

            String[] parts = input.split("\\]\\s*\\[");
            if (parts.length != 2) {
                System.out.println("Ошибка: введите два массива в правильном формате");
                scanner.close();
                return;
            }
            

            String array1Str = parts[0].replace("[", "").trim();
            String[] array1Elements = array1Str.isEmpty() ? new String[0] : array1Str.split(",\\s*");
            int[] array1 = new int[array1Elements.length];
            for (int i = 0; i < array1Elements.length; i++) {
                array1[i] = Integer.parseInt(array1Elements[i].trim());
            }
            
            String array2Str = parts[1].replace("]", "").trim();
            String[] array2Elements = array2Str.isEmpty() ? new String[0] : array2Str.split(",\\s*");
            int[] array2 = new int[array2Elements.length];
            for (int i = 0; i < array2Elements.length; i++) {
                array2[i] = Integer.parseInt(array2Elements[i].trim());
            }
            
            int[] result = mergeUniqueSorted(array1, array2);
            
            System.out.println(Arrays.toString(result));
            
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректные целые числа");
        } catch (Exception e) {
            System.out.println("Ошибка: некорректный формат ввода");
        }
        
        scanner.close();
    }
}
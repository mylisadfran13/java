package tasks;

import java.util.Scanner;

public class DiagonalSum {
    
    public static int diagonalSum(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][i];
        }
        return sum;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String input = scanner.nextLine().trim();
        
        if (!input.startsWith("[[") || !input.endsWith("]]")) {
            System.out.println("Ошибка: неверный формат ввода");
            scanner.close();
            return;
        }
        
        try {
            String content = input.substring(2, input.length() - 2);
            String[] rows = content.split("\\],\\[");
            
            int size = rows.length;
            int[][] matrix = new int[size][size];
            
            for (int i = 0; i < size; i++) {
                String[] elements = rows[i].split(",");
                if (elements.length != size) {
                    System.out.println("Ошибка: матрица должна быть квадратной");
                    scanner.close();
                    return;
                }
                
                for (int j = 0; j < size; j++) {
                    matrix[i][j] = Integer.parseInt(elements[j].trim());
                }
            }
            
            int result = diagonalSum(matrix);
            System.out.println(result);
            
        } catch (Exception e) {
            System.out.println("Ошибка: некорректный формат данных");
        }
        
        scanner.close();
    }
}
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

class RowProcessor implements Callable<Integer> {
    private final int[] row;
    private final int rowIndex;
    
    public RowProcessor(int[] row, int rowIndex) {
        this.row = row;
        this.rowIndex = rowIndex;
    }
    
    @Override
    public Integer call() {
        if (row == null || row.length == 0) {
            return Integer.MIN_VALUE;
        }
        
        int maxInRow = row[0];
        for (int i = 1; i < row.length; i++) {
            if (row[i] > maxInRow) {
                maxInRow = row[i];
            }
        }
        
        System.out.println(Thread.currentThread().getName() + 
                          " обработал строку " + rowIndex + 
                          ", максимум в строке: " + maxInRow);
        
        return maxInRow;
    }
}

public class MatrixMaxFinder {
    private final int[][] matrix;
    private final int rows;
    private final int cols;
    
    public MatrixMaxFinder(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = generateRandomMatrix(rows, cols);
    }
    
    private int[][] generateRandomMatrix(int rows, int cols) {
        Random random = new Random();
        int[][] matrix = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(1000); 
            }
        }
        
        return matrix;
    }
    
    public void printMatrix() {
        System.out.println("матрица " + rows + "x" + cols + ":");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
    }
    
    public int findMaxSingleThreaded() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }
        return max;
    }
    
    public int findMaxMultiThreaded() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(rows);
        List<Future<Integer>> futures = new ArrayList<>();
        
        for (int i = 0; i < rows; i++) {
            Callable<Integer> task = new RowProcessor(matrix[i], i);
            Future<Integer> future = executor.submit(task);
            futures.add(future);
        }
        
        List<Integer> rowMaxValues = new ArrayList<>();
        for (Future<Integer> future : futures) {
            rowMaxValues.add(future.get());
        }
        
        executor.shutdown();
        
        int globalMax = Integer.MIN_VALUE;
        for (int maxInRow : rowMaxValues) {
            if (maxInRow > globalMax) {
                globalMax = maxInRow;
            }
        }
        
        return globalMax;
    }
    
    public static void main(String[] args) {
        int rows = 5;
        int cols = 8;
        
        MatrixMaxFinder finder = new MatrixMaxFinder(rows, cols);
        finder.printMatrix();
        
        try {
            long startTime = System.nanoTime();
            int singleThreadMax = finder.findMaxSingleThreaded();
            long singleThreadTime = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            int multiThreadMax = finder.findMaxMultiThreaded();
            long multiThreadTime = System.nanoTime() - startTime;
            
            System.out.println("\nрезультаты:");
            System.out.println("однопоточный поиск: " + singleThreadMax + 
                             " (время: " + singleThreadTime / 1000 + " мкс)");
            System.out.println("многопоточный поиск: " + multiThreadMax + 
                             " (время: " + multiThreadTime / 1000 + " мкс)");
            
            if (singleThreadMax == multiThreadMax) {
                System.out.println("резы совпадают");
            } else {
                System.out.println("ошибка: результаты не совпадают");
            }
               
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
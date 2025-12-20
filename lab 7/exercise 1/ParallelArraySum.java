import java.util.Random;

class SumCalculator implements Runnable {
    private int[] array;
    private int start;
    private int end;
    private long partialSum;
    private String threadName;

    public SumCalculator(int[] array, int start, int end, String threadName) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.threadName = threadName;
        this.partialSum = 0;
    }

    @Override
    public void run() {
        System.out.println(threadName + " вычисления суммы элементов от " + start + " до " + (end - 1));
        
        for (int i = start; i < end; i++) {
            partialSum += array[i];
        }
        
        System.out.println(threadName + " конец вычислейний, частичная сумма: " + partialSum);
    }

    public long getPartialSum() {
        return partialSum;
    }
}

public class ParallelArraySum {
    public static void main(String[] args) {
        int arraySize = 1000000;
        int[] array = new int[arraySize];
        Random random = new Random();
        
        System.out.println("заполение рандомными числами");
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(100); 
        }
        
        long startTime = System.currentTimeMillis();
        long sequentialSum = 0;
        for (int i = 0; i < arraySize; i++) {
            sequentialSum += array[i];
        }
        long endTime = System.currentTimeMillis();
        System.out.println("последовательное вычисление:");
        System.out.println("сумма: " + sequentialSum);
        System.out.println("время: " + (endTime - startTime) + " мс\n");
        
        startTime = System.currentTimeMillis();
        int mid = arraySize / 2;
        
        SumCalculator calculator1 = new SumCalculator(array, 0, mid, "поток 1");
        SumCalculator calculator2 = new SumCalculator(array, mid, arraySize, "поток 2");
        
        Thread thread1 = new Thread(calculator1);
        Thread thread2 = new Thread(calculator2);
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("потоки были прерваны: " + e.getMessage());
        }
        
        long parallelSum = calculator1.getPartialSum() + calculator2.getPartialSum();
        endTime = System.currentTimeMillis();
        
        System.out.println("\nпараллельное вычисление:");
        System.out.println("сумма потока 1: " + calculator1.getPartialSum());
        System.out.println("сумма потока 2: " + calculator2.getPartialSum());
        System.out.println("общая сумма: " + parallelSum);
        System.out.println("время: " + (endTime - startTime) + " мс");

        if (sequentialSum == parallelSum) {
            System.out.println("\nкласс, результаты совпадают");
        } else {
            System.out.println("\nпочему-то результаты не совпадают");
        }
    }
}
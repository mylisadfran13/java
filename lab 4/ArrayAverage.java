public class ArrayAverage {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
                
        try {
            int sum = 0;

            if (arr == null) {
                throw new NullPointerException("массив не инициализирован");
            }
            
            if (arr.length == 0) {
                throw new ArithmeticException("этот массив пустой, невозможно вычислить среднее значение");
            }
            
            for (int i = 0; i < arr.length; i++) {
                sum += arr[i];
            }
            
            double average = (double) sum / arr.length;
            
            System.out.print("массив: [");
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i]);
                if (i < arr.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
            System.out.println("среднее арифметическое: " + average);
            
        } catch (NullPointerException e) {
            System.out.println("ошибка: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("ошибка: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ошибка: выход за границы массива");
        } catch (Exception e) {
            System.out.println("неизвестная ошибка: " + e.getMessage());
        }
    }
}
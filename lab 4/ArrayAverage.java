public class ArrayAverage {
    public static void main(String[] args) {
        Object[] arr = {1, 2, 4.5, 7};
        
        try {
            int sum = 0;
            int count = 0;

            if (arr == null) {
                throw new NullPointerException("массив не инициализирован");
            }
            
            if (arr.length == 0) {
                throw new ArithmeticException("невозможно вычислить среднее значение");
            }
            
            System.out.print("массив: ");
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i]);
                if (i < arr.length - 1) {
                    System.out.print(", ");
                }
                
                if (arr[i] instanceof Number) {
                    sum += ((Number) arr[i]).intValue(); 
                    count++;
                } else if (arr[i] != null) {
                    throw new IllegalArgumentException(
                        "элеменент номер " + i + " не является числом: " + 
                        arr[i] + " (тип: " + arr[i].getClass().getSimpleName() + ")"
                    );
                } else {
                    throw new NullPointerException(
                        "элемент номер " + i + " null"
                    );
                }
            }   
            System.out.println("]");
            
            if (count == 0) {
                throw new ArithmeticException("нет числовых элементов");
            }
            
            double average = (double) sum / count;
            System.out.println("среднее арифметическое: " + average);
            
        } catch (NullPointerException e) {
            System.out.println("ошибка: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("ошибка: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("ошибка данных: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ошибка: выход за границы массива");
        } catch (Exception e) {
            System.out.println("неизвестная ошибка: " + e.getMessage());
        }
    }
}
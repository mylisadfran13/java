import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class CustomEmptyStackException extends RuntimeException {
    
    public CustomEmptyStackException() {
        super("ошибка: попытка извлечь элемент из пустого стека");
    }
    
    public CustomEmptyStackException(String message) {
        super(message);
    }
    
    public CustomEmptyStackException(String message, Throwable cause) {
        super(message, cause);
    }
}

class CustomStack<T> {
    private List<T> stack;
    private int size;
    
    public CustomStack() {
        this.stack = new ArrayList<>();
        this.size = 0;
    }
    
    public void push(T element) {
        stack.add(element);
        size++;
        System.out.println("добавлен элемент: " + element);
    }
        
    public T pop() {
        if (isEmpty()) {
            throw new CustomEmptyStackException("нельзя извлечь элемент, потому что стек");
        }
        T element = stack.remove(size - 1);
        size--;
        System.out.println("извлечен элемент: " + element);
        return element;
    }

    public T peek() {
        if (isEmpty()) {
            throw new CustomEmptyStackException("нельзя посмотреть элемент, потому что стек пуст!");
        }
        return stack.get(size - 1);
    }

    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void printStack() {
        if (isEmpty()) {
            System.out.println("стек пуст");
        } else {
            System.out.println("содержимое стека: " + stack.subList(0, size));
        }
    }
}

class ExceptionLogger {
    private static final String LOG_FILE = "exceptions_log.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void logException(Exception exception) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = dateFormat.format(new Date());
            
            writer.println("исключение");
            writer.println("время: " + timestamp);
            writer.println("тип: " + exception.getClass().getSimpleName());
            writer.println("сообщение: " + exception.getMessage());
            writer.println("стек вызовов:");
            exception.printStackTrace(writer);
            writer.println();
            
            System.out.println("информация об исключении записана в файл: " + LOG_FILE);
            
        } catch (IOException logError) {
            System.err.println("ошибка при записи в логфайл: " + logError.getMessage());
        }
    }
    

    public static void logException(Exception exception, String customMessage) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = dateFormat.format(new Date());
            
            writer.println("исключение");
            writer.println("время: " + timestamp);
            writer.println("сообщение: " + customMessage);
            writer.println("тип: " + exception.getClass().getSimpleName());
            writer.println("детали: " + exception.getMessage());
            writer.println("стек вызовов:");
            exception.printStackTrace(writer);
            writer.println();
            
            System.out.println("информация об исключении записана в файл: " + LOG_FILE);
            
        } catch (IOException logError) {
            System.err.println("ошибка при записи в лог-файл: " + logError.getMessage());
        }
    }
    
    public static void clearLog() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, false))) {
            writer.print("");
            System.out.println("логфайл очищен");
        } catch (IOException e) {
            System.err.println("ошибка при очистке логфайла: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ExceptionLogger.clearLog();
        
        CustomStack<Integer> stack = new CustomStack<>();
        
        try {
            System.out.println("\nпопытка 1: попытка извлечь из пустого стека");
            stack.pop(); 
            
        } catch (CustomEmptyStackException e) {
            System.out.println("вызвано исключение: " + e.getMessage());
            ExceptionLogger.logException(e, "попытка извлечения из пустого стека");
        }
        
        try {
            System.out.println("\nпопытка 2: попытка посмотреть вершину пустого стека");
            stack.peek(); 
            
        } catch (CustomEmptyStackException e) {
            System.out.println("вызвано исключение: " + e.getMessage());
            ExceptionLogger.logException(e);
        }
        
        System.out.println("\nпопытка 3: корректная работа со стеком");
        
        try {
            stack.push(1);
            stack.push(5);
            stack.push(7);
            
            stack.printStack();
            
            stack.pop();
            stack.pop();
            stack.pop();
            
            stack.printStack();
            
        } catch (CustomEmptyStackException e) {
            System.out.println("вызвано исключение: " + e.getMessage());
            ExceptionLogger.logException(e);
        }

        System.out.println("\nпопытка 4");
        
        try {
            stack.push(100);
            stack.push(200);
            
            System.out.println("вершина стека: " + stack.peek());
            stack.pop();
            stack.pop();
            stack.pop(); 
            
        } catch (CustomEmptyStackException e) {
            System.out.println("вызвано исключение: " + e.getMessage());
            ExceptionLogger.logException(e, "путаница");
        }
        
        System.out.println("все исключения записаны в файл: exceptions_log.txt");
        
    }
    
}
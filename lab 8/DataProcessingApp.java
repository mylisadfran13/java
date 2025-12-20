import java.lang.annotation.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
import java.io.*;
import java.nio.file.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface DataProcessor {
    String name() default "";
    int order() default 0;
}

class DataManager {
    private final List<Object> processors = new CopyOnWriteArrayList<>();
    private List<?> data;
    private final ExecutorService executorService;
    
    public DataManager() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        this.executorService = Executors.newFixedThreadPool(availableProcessors);
    }
    
    public DataManager(int threadCount) {
        this.executorService = Executors.newFixedThreadPool(threadCount);
    }
    
    public void registerDataProcessor(Object processor) {
        if (processor == null) {
            throw new IllegalArgumentException("не может быть null");
        }
        
        boolean hasAnnotation = Arrays.stream(processor.getClass().getMethods())
            .anyMatch(method -> method.isAnnotationPresent(DataProcessor.class));
        
        if (!hasAnnotation) {
            throw new IllegalArgumentException("проц должен иметь хотяб 1 метод с аннотацией");
        }
        
        processors.add(processor);
    }
    
    @SuppressWarnings("unchecked")
    public void loadData(String source) {
        System.out.println("загрузка данных из: " + source);

        if (source.startsWith("file://")) {
            data = loadDataFromFile(source.substring(7));
        } else if (source.startsWith("db://")) {
            data = loadDataFromDatabase(source.substring(5));
        } else if (source.equals("sample")) {
            data = createSampleData();
        } else if (source.equals("users")) {
            data = createUserData();
        } else {
            data = createDefaultData();
        }
        
        System.out.println("загружено " + data.size() + " элементов");
    }
    
    private List<String> loadDataFromFile(String filename) {
        return IntStream.range(1, 21)
            .mapToObj(i -> "Data item " + i + " from file " + filename)
            .collect(Collectors.toList());
    }
    
    private List<Map<String, Object>> loadDataFromDatabase(String connection) {
        return IntStream.range(1, 11)
            .mapToObj(i -> {
                Map<String, Object> record = new HashMap<>();
                record.put("id", i);
                record.put("имя", "Record " + i);
                record.put("знчение", i * 10);
                record.put("активность", i % 2 == 0);
                record.put("источник", connection);
                return record;
            })
            .collect(Collectors.toList());
    }
    
    private List<String> createSampleData() {
        return Arrays.asList(
            "яблоко 123", "апельсин", "банан 456", "виноград",
            "персик 789", "лимон", "манго 101", "персик",
            "киви", "дыня 303", "арбузик"
        );
    }
    
    private List<User> createUserData() {
        return Arrays.asList(
            new User(1, "Кабанова Юлия", 19, "IT"),
            new User(2, "Ваньков Александр", 19, "IT"),
            new User(3, "Павлюков Андрей", 19, "HR"),
            new User(4, "Иван Иванов", 22, "Sales"),
            new User(5, "Смирнов Петр", 16, "IT"),
            new User(6, "Николаичева София", 18, "Marketing"),
            new User(7, "Сергей Попов", 19, "Sales"),
            new User(8, "Анна Новикова", 35, "HR")
        );
    }
    
    private List<String> createDefaultData() {
        return IntStream.range(1, 11)
            .mapToObj(i -> "данные" + i)
            .collect(Collectors.toList());
    }
    
    public void processData() {
        if (data == null || data.isEmpty()) {
            System.out.println("нет данных для обработки");
            return;
        }
        
        System.out.println("начало обработки данных с " + processors.size() + " процессорами");

        List<ProcessorMethod> processorMethods = new ArrayList<>();
        
        for (Object processor : processors) {
            Arrays.stream(processor.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(DataProcessor.class))
                .forEach(method -> {
                    DataProcessor annotation = method.getAnnotation(DataProcessor.class);
                    processorMethods.add(new ProcessorMethod(processor, method, annotation.order()));
                });
        }
        
        processorMethods.sort(Comparator.comparingInt(ProcessorMethod::getOrder));
    
        Stream<?> stream = data.parallelStream(); 
        
        for (ProcessorMethod processorMethod : processorMethods) {
            final Stream<?> currentStream = stream;
            try {
                String methodName = processorMethod.getMethod().getName();
                System.out.println("применение процессора: " + methodName);
                
                stream = (Stream<?>) CompletableFuture.supplyAsync(() -> {
                    try {
                        return processorMethod.getMethod().invoke(
                            processorMethod.getProcessor(), 
                            currentStream
                        );
                    } catch (Exception e) {
                        throw new RuntimeException("ошибка в процессе" + methodName, e);
                    }
                }, executorService).join();
            } catch (Exception e) {
                System.err.println("ошибка в проце: " + e.getMessage());
            }
        }
        
        this.data = stream.collect(Collectors.toList());
        System.out.println("обработка завершена, результат имеет " + data.size() + " элементов");
    }
    
    public void saveData(String destination) {
        if (data == null || data.isEmpty()) {
            System.out.println("нет данных для сохранения");
            return;
        }
        
        System.out.println("сохранение данных в: " + destination);
        
        if (destination.startsWith("file://")) {
            saveToFile(destination.substring(7));
        } else if (destination.startsWith("db://")) {
            saveToDatabase(destination.substring(5));
        } else if (destination.equals("console")) {
            saveToConsole();
        } else {
            saveToDefault();
        }
    }
    
    private void saveToFile(String filename) {
        System.out.println("сохранение " + data.size() + " элементов в файл: " + filename);
        try {
            Files.write(Paths.get(filename), 
                data.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList()));
            System.out.println("данные успешно сохранены в файл");
        } catch (IOException e) {
            System.err.println("ошибка при сохранении в файл: " + e.getMessage());
        }
    }
    
    private void saveToDatabase(String connection) {
        System.out.println("сохранение " + data.size() + " элементов в базу данных: " + connection);
        data.forEach(item -> {
        });
        System.out.println("данные успешно сохранены в базу данных");
    }
    
    private void saveToConsole() {
        System.out.println("\nобработанные данные (резы)");
        data.forEach(item -> System.out.println("-" + item));
    }
    
    private void saveToDefault() {
        System.out.println("\nрезы обработки:");
        data.forEach(System.out::println);
    }
    
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
            System.out.println("потоки управления завершены");
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private static class ProcessorMethod {
        private final Object processor;
        private final java.lang.reflect.Method method;
        private final int order;
        
        public ProcessorMethod(Object processor, java.lang.reflect.Method method, int order) {
            this.processor = processor;
            this.method = method;
            this.order = order;
        }
        
        public Object getProcessor() { return processor; }
        public java.lang.reflect.Method getMethod() { return method; }
        public int getOrder() { return order; }
    }
}

class User {
    private int id;
    private String name;
    private int age;
    private String department;
    
    public User(int id, String name, int age, String department) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getDepartment() { return department; }
    
    @Override
    public String toString() {
        return String.format("пользователь{id=%d, имя='%s', возраст=%d, отдел='%s'}", 
            id, name, age, department);
    }
}

class FilterProcessor {
    
    @DataProcessor(name = "filterNumbers", order = 1)
    public Stream<?> filterNumbers(Stream<?> stream) {
        return stream.filter(item -> {
            if (item instanceof String) {
                String str = (String) item;
                return str.matches(".*\\d+.*");
            } else if (item instanceof Map) {
                Map<?, ?> map = (Map<?, ?>) item;
                Object value = map.get("value");
                return value instanceof Integer && (Integer) value > 30;
            } else if (item instanceof User) {
                return ((User) item).getAge() >= 18;
            }
            return true;
        });
    }
    
    @DataProcessor(name = "removeDuplicates", order = 2)
    public Stream<?> removeDuplicates(Stream<?> stream) {
        return stream.distinct();
    }
}

class TransformProcessor {
    
    @DataProcessor(name = "toUpperCase", order = 3)
    public Stream<String> toUpperCase(Stream<?> stream) {
        return stream.map(item -> {
            if (item instanceof String) {
                return ((String) item).toUpperCase();
            }
            return item.toString().toUpperCase();
        });
    }
    
    @DataProcessor(name = "addPrefix", order = 4)
    public Stream<String> addPrefix(Stream<?> stream) {
        return stream.map(item -> "[ОБРАБОТАНО] " + item.toString());
    }
    
    @DataProcessor(name = "formatRecord", order = 5)
    public Stream<String> formatRecord(Stream<?> stream) {
        return stream.map(item -> {
            if (item instanceof Map) {
                Map<?, ?> map = (Map<?, ?>) item;
                return String.format("запись #%d: %s (значение: %d)", 
                    map.get("id"), map.get("name"), map.get("value"));
            }
            return item.toString();
        });
    }
}

class AggregateProcessor {
    
    @DataProcessor(name = "limitResults", order = 6)
    public Stream<?> limitResults(Stream<?> stream) {
        return stream.limit(5);
    }
    
    @DataProcessor(name = "sortData", order = 7)
    public Stream<?> sortData(Stream<?> stream) {
        return stream.sorted((a, b) -> {
            String strA = a.toString();
            String strB = b.toString();
            return strA.compareTo(strB);
        });
    }
}

class UserDataProcessor {
    
    @DataProcessor(name = "filterAdults", order = 1)
    public Stream<User> filterAdults(Stream<?> stream) {
        return stream.filter(item -> item instanceof User)
                     .map(item -> (User) item)
                     .filter(user -> user.getAge() >= 18);
    }
    
    @DataProcessor(name = "sortByAge", order = 2)
    public Stream<User> sortByAge(Stream<?> stream) {
        return stream.filter(item -> item instanceof User)
                     .map(item -> (User) item)
                     .sorted(Comparator.comparingInt(User::getAge));
    }
    
    @DataProcessor(name = "groupByDepartment", order = 3)
    public Stream<String> groupByDepartment(Stream<?> stream) {
        Map<String, List<User>> grouped = stream
            .filter(item -> item instanceof User)
            .map(item -> (User) item)
            .collect(Collectors.groupingBy(User::getDepartment));
        
        return grouped.entrySet().stream()
            .map(entry -> String.format("отдел: %-10s | Количество сотрудников: %d", 
                entry.getKey(), entry.getValue().size()));
    }
}

public class DataProcessingApp {
    public static void main(String[] args) {
        System.out.println("прило для обрабтки данных");
        
        System.out.println("\nпример 1: обработка текстовых данных");
        
        DataManager manager1 = new DataManager();
        manager1.registerDataProcessor(new FilterProcessor());
        manager1.registerDataProcessor(new TransformProcessor());
        manager1.registerDataProcessor(new AggregateProcessor());
        
        manager1.loadData("пример");
        manager1.processData();
        manager1.saveData("console");
        manager1.shutdown();
        
        System.out.println("\nпример 2: обработка данных пользователей");
        
        DataManager manager2 = new DataManager(2); 
        manager2.registerDataProcessor(new UserDataProcessor());
        
        manager2.loadData("users");
        manager2.processData();
        manager2.saveData("console");
        manager2.shutdown();
        
        System.out.println("\nпример 3: обработка данных из базы данных");
        
        DataManager manager3 = new DataManager();
        manager3.registerDataProcessor(new FilterProcessor());
        manager3.registerDataProcessor(new TransformProcessor());
        
        manager3.loadData("db://postgresql");
        manager3.processData();
        manager3.saveData("console");
        manager3.shutdown();

        System.out.println("\nпример 4: обработка с сохранением в файл");
        
        DataManager manager4 = new DataManager();
        manager4.registerDataProcessor(new FilterProcessor());
        manager4.registerDataProcessor(new TransformProcessor());
        manager4.registerDataProcessor(new AggregateProcessor());
        
        manager4.loadData("file://data.txt");
        manager4.processData();
        manager4.saveData("file://results.txt");
        manager4.shutdown();
        
        System.out.println("все операции успешно завершены!!!");
    
        demonstrateFileProcessing();
    }
    
    private static void demonstrateFileProcessing() {
        System.out.println("\nфайловая обработка");
        
        try {
            Path tempFile = Files.createTempFile("test_data", ".txt");
            List<String> lines = Arrays.asList(
                "First line with number 123",
                "Second line",
                "Third line with 456",
                "",
                "   ",
                "Another line with digit 789",
                "Last line"
            );
            Files.write(tempFile, lines);
            
            System.out.println("создан временный файл: " + tempFile);
            
            DataManager fileManager = new DataManager();
            fileManager.registerDataProcessor(new FilterProcessor());
            fileManager.registerDataProcessor(new TransformProcessor());
            
            fileManager.loadData("file://" + tempFile.toString());
            fileManager.processData();
            fileManager.saveData("console");
            fileManager.shutdown();
            
            Files.deleteIfExists(tempFile);
            
        } catch (IOException e) {
            System.err.println("ошибка при работе с файлом: " + e.getMessage());
        }
    }
}
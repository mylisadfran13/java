import java.util.*;

class Product {
    private String name;
    private double price;
    
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(name, product.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {
        return String.format("%s - %.2f руб.", name, price);
    }
}

class SalesManager {
    private Set<Product> soldProducts;
    private Map<String, Integer> productCount;
    
    public SalesManager() {
        soldProducts = new HashSet<>();
        productCount = new HashMap<>();
    }

    public void addSoldProduct(Product product) {
        soldProducts.add(product);
        
        String productName = product.getName();
        productCount.put(productName, productCount.getOrDefault(productName, 0) + 1);
        
        System.out.println("товар добавлен: " + product);
    }
    
    public void displaySoldProducts() {
        if (soldProducts.isEmpty()) {
            System.out.println("нет проданных товаров.");
            return;
        }
        
        System.out.println("\nсписок проданных товаров");
        int counter = 1;
        for (Product product : soldProducts) {
            System.out.println(counter + ". " + product);
            counter++;
        }
    }
    
    public double calculateTotalSales() {
        double total = 0;
        for (Product product : soldProducts) {
            int quantity = productCount.get(product.getName());
            total += product.getPrice() * quantity;
        }
        return total;
    }
    
    public void findMostPopularProduct() {
        if (productCount.isEmpty()) {
            System.out.println("нет данных о продаже");
            return;
        }
        
        String mostPopular = null;
        int maxCount = 0;
        
        for (Map.Entry<String, Integer> entry : productCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostPopular = entry.getKey();
            }
        }
        
        double price = 0;
        for (Product product : soldProducts) {
            if (product.getName().equals(mostPopular)) {
                price = product.getPrice();
                break;
            }
        }
        
        System.out.printf("самый популярный товар: %s (продано %d раз, на сумму %.2f руб.)\n", 
                         mostPopular, maxCount, price * maxCount);
    }
    
    public void displaySalesStatistics() {
        if (productCount.isEmpty()) {
            System.out.println("нет данных о продаже");
            return;
        }
        
        System.out.println("\nстатистика продаж");
        for (Map.Entry<String, Integer> entry : productCount.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            double price = 0;
            
            for (Product product : soldProducts) {
                if (product.getName().equals(productName)) {
                    price = product.getPrice();
                    break;
                }
            }
            
            System.out.printf("%s: %d шт. × %.2f руб. = %.2f руб.\n", 
                            productName, quantity, price, price * quantity);
        }
    }
    
    public int getUniqueProductsCount() {
        return soldProducts.size();
    }
    
    public int getTotalUnitsSold() {
        int total = 0;
        for (int count : productCount.values()) {
            total += count;
        }
        return total;
    }
}

public class StoreSalesTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SalesManager salesManager = new SalesManager();
        
        System.out.println("учет продаж");
        
        while (true) {
            displayMenu();
            System.out.print("выберите действие: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    addProductMenu(scanner, salesManager);
                    break;
                case "2":
                    salesManager.displaySoldProducts();
                    break;
                case "3":
                    double total = salesManager.calculateTotalSales();
                    System.out.printf("\nобщая сумма продаж: %.2f руб.\n", total);
                    break;
                case "4":
                    salesManager.findMostPopularProduct();
                    break;
                case "5":
                    salesManager.displaySalesStatistics();
                    break;
                case "6":
                    System.out.printf("\nуникальных товаров: %d\n", salesManager.getUniqueProductsCount());
                    System.out.printf("всего продано: %d\n", salesManager.getTotalUnitsSold());
                    break;
                case "0":
                    System.out.println("выход");
                    scanner.close();
                    return;
                default:
                    System.out.println("неверный выбор, попробуйте выбрать другое");
            }
            System.out.println();
        }
    }
    
    private static void displayMenu() {
        System.out.println("1. добавить проданный товар");
        System.out.println("2. показать список проданных товаров");
        System.out.println("3. рассчитать общую сумму продаж");
        System.out.println("4. найти самый популярный товар");
        System.out.println("5. показать статистику продаж");
        System.out.println("6. показать общую статистику");
        System.out.println("0. выход");
    }
    
    private static void addProductMenu(Scanner scanner, SalesManager salesManager) {
        System.out.print("напишите название товара: ");
        String name = scanner.nextLine();
        
        System.out.print("напишите цену товара: ");
        double price;
        try {
            price = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ошибка: введите корректное число для цены");
            return;
        }
        
        if (price < 0) {
            System.out.println("ошибка: цена не может быть отрицательной");
            return;
        }
        
        Product product = new Product(name, price);
        salesManager.addSoldProduct(product);
    }
}